package org.kisspbx.controller;

import java.io.IOException;
import java.net.ConnectException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.kisspbx.AppContextListener;
import org.kisspbx.AsteriskConnection;
import org.kisspbx.ConfigurationChangedMonitor;
import org.kisspbx.config.Configurator;
import org.kisspbx.model.CustomDialplan;
import org.kisspbx.model.app.CustomApplication;

@WebServlet(name="asteriskController", 
			urlPatterns={"/asterisk/apply",
					"/asterisk/dashboard"})
public class AsteriskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AsteriskController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/asterisk/apply")) {
				processApply(request, response);
				
			} else if (request.getServletPath().equals("/asterisk/dashboard")) {
				processDashboard(request, response);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void processApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get configuration path
		String path = System.getenv("asteriskpath");
		if (path == null) {
			if (System.getProperty("os.name").toLowerCase().contains("windows"))
				path = "c:/asterisk";
			else
				path = "/etc/asterisk";
		}
		
		// check basic functions
		restoreBasicFunctions();
		
		// process configuration
		Configurator configurator = new Configurator(path);
		request.setAttribute("result", configurator.execute());

		// reload configuration
		StringBuffer result = new StringBuffer();
		try {
			reloadConfiguration(result);
			ConfigurationChangedMonitor.clearChanged();
			
		} catch (Exception e) {
			e.printStackTrace();
			result.append("error: " + e.getMessage() + "\n");
		} 
		request.setAttribute("reload", result.toString());
		
		request.getRequestDispatcher("/WEB-INF/asterisk/apply.jsp").forward(request, response);

	}
	
	/*@SuppressWarnings("unchecked")
	private void createConfBridgeConfiguration(String path, StringBuffer res) throws IOException {
		res.append("Creating confbridge.conf ... ");
		
		// prepare directory
		try {
			Files.createDirectories(Paths.get(path + "/backup/"));
			Files.copy(Paths.get(path + "/confbridge.conf"),
					Paths.get(path + "/backup/confbridge.conf."+System.currentTimeMillis()),
							StandardCopyOption.REPLACE_EXISTING);
			Files.delete(Paths.get(path + "/confbridge.conf"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//EntityManager em = AppContextListener.getEntityManager();
		FileOutputStream fs = null;
		
		try {
			fs = new FileOutputStream(new File(path+"/confbridge.conf"));
			
			//Settings s = (Settings) em.createQuery("from Settings s where s.type = 'IAX2' and s.context = 'general'").getSingleResult();
			//List<Conference> conferences = em.createQuery("from Conference c order by c.name asc").getResultList();
			
			// general settings
			StringBuilder sb = new StringBuilder();
			sb.append("[general]\n");
			sb.append("\n");
			fs.write(sb.toString().getBytes());
			
			res.append("success\n");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			res.append("error\n");
			
		} finally {
			if (fs != null) fs.close();
			//em.close();
		}
	}*/

	private void reloadConfiguration(StringBuffer res) throws IllegalArgumentException, IllegalStateException, IOException, TimeoutException, InterruptedException {
		AsteriskConnection asterisk = getAsteriskConnection();
		try {
			asterisk.login();
			
		} catch (AuthenticationFailedException e) {
			res.append("Authentication failed\n");
			return;
		} catch (ConnectException e) {
			res.append("Connection refused\n");
			return;
		}
		
		res.append("Reloading configuration ... ");
		asterisk.sendCommand("core reload", res);
		
		asterisk.logoff();
	}

	private void processDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//AsteriskConnection asterisk = (AsteriskConnection)getServletContext().getAttribute("asterisk");
		AsteriskConnection asterisk = getAsteriskConnection();
		try {
			asterisk.login();
			
		} catch (Exception e) {
			request.setAttribute("error", "Asterisk connection not available");
		} 
		
		StringBuffer res = new StringBuffer();
		try {
			asterisk.sendCommand("sip show peers", res);
			request.setAttribute("sippeers", res.toString());
			res.setLength(0);
			asterisk.sendCommand("iax2 show peers", res);
			request.setAttribute("iax2peers", res.toString());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		asterisk.logoff();
		
		request.getRequestDispatcher("/WEB-INF/asterisk/dashboard.jsp").forward(request, response);
	}
	
	private AsteriskConnection getAsteriskConnection() {
		EntityManager em = AppContextListener.getEntityManager();
		String host = null;
		String user = null;
		String pass = null;
		try {
			host = (String)em.createQuery("select s.value from Settings s where s.type = 'PBX' and s.name = 'asterisk.host'").getSingleResult();
			user = (String)em.createQuery("select s.value from Settings s where s.type = 'PBX' and s.name = 'asterisk.user'").getSingleResult();
			pass = (String)em.createQuery("select s.value from Settings s where s.type = 'PBX' and s.name = 'asterisk.password'").getSingleResult();
				
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			em.close();
		}
		
		return new AsteriskConnection(host, user, pass);
	}

	private void restoreBasicFunctions() {
		EntityManager em = AppContextListener.getEntityManager();
			        
		try {
			em.getTransaction().begin();

			long stdextenExists = (long)em.createQuery("select count(c.id) from CustomDialplan c where c.name='route-extension'").getSingleResult();
			if (stdextenExists == 0) {
				CustomDialplan c = new CustomDialplan();
				c.setName("route-extension");
				StringBuffer sbmacro = new StringBuffer();
				sbmacro.append(";; vars: EXTEN_DEVICE, ?EXTEN_VOICEMAIL\n");
				sbmacro.append("exten => s,1,Dial(${EXTEN_DEVICE},45)\n");
				sbmacro.append(" same => n,GotoIf($[\"${EXTEN_VOICEMAIL}\" != \"\"]?s-${DIALSTATUS},1)\n");
				sbmacro.append("exten => s-NOANSWER,1,Voicemail(${EXTEN_VOICEMAIL},u)\n");
				sbmacro.append(" same => n,Hangup\n");
				sbmacro.append("exten => s-BUSY,1,Voicemail(${EXTEN_VOICEMAIL},b)\n");
				sbmacro.append(" same => n,Hangup\n");
				sbmacro.append("exten => _s-.,1,Goto(s-NOANSWER,1)\n");
				c.setDialplan(sbmacro.toString());
				em.persist(c);
			}
		
			CustomDialplan vmfeature = null;
			try {
				vmfeature = (CustomDialplan)em.createQuery("from CustomDialplan c where c.name='vm-feature'").getSingleResult();
			} catch (NoResultException e) {}
			
			if (vmfeature == null) {
				vmfeature = new CustomDialplan();
				vmfeature.setName("vm-feature");
				StringBuffer dialplan = new StringBuffer();
				dialplan.append(";; Voicemail feature functions\n");
				dialplan.append("exten => vm,1,VoicemailMain()\n");
				dialplan.append("exten => _vmX.,1,VoicemailMain(${EXTEN:2})\n"); 
				dialplan.append("exten => _*X.,1,Voicemail(${EXTEN:1})\n");
				vmfeature.setDialplan(dialplan.toString());
				em.persist(vmfeature);
			}
			
			long myvm = (long)em.createQuery("select count(c.id) from CustomApplication c where c.pattern = '*97'").getSingleResult();
			if (myvm == 0) {
				CustomApplication vm = new CustomApplication();
				vm.setDescription("My Voicemail");
				vm.setPattern("*97");
				vm.setExtension("vm${CALLERID(num)}");
				vm.setPriority("1");
				vm.setInternalOnly(true);
				vm.setDialplan(vmfeature);
				em.persist(vm);
			}
			
			long mainvm = (long)em.createQuery("select count(c.id) from CustomApplication c where c.pattern = '*98'").getSingleResult();
			if (mainvm == 0) {
				CustomApplication vm = new CustomApplication();
				vm.setDescription("Voicemail");
				vm.setPattern("*98");
				vm.setExtension("vm");
				vm.setPriority("1");
				vm.setInternalOnly(true);
				vm.setDialplan(vmfeature);
				em.persist(vm);
			}
			
			CustomDialplan qfeature = null;
			try {
				qfeature = (CustomDialplan)em.createQuery("from CustomDialplan c where c.name='queue-feature'").getSingleResult();
			} catch (NoResultException e) {}
			
			if (qfeature == null) {
				CustomDialplan c = new CustomDialplan();
				c.setName("queue-feature");
				StringBuffer sbmacro = new StringBuffer();
				sbmacro.append(";; params: QUEUE_NAME\n");
				sbmacro.append("exten => s,1,NoOp(Queueing ${CALLERID(num) in ${QUEUE_NAME})\n");
				sbmacro.append(" same => n,Set(CALLERID(name)=${QUEUE_NAME}: ${CALLERID(num)})\n");
				sbmacro.append(" same => n,Answer\n");
				sbmacro.append(" same => n,Queue(${QUEUE_NAME},tc)\n");
				sbmacro.append(" same => n,NoOp(Exit queue ${QUEUE_NAME} with status ${QUEUESTATUS})\n");
				sbmacro.append(" same => n,Return\n\n");
				sbmacro.append(";; params: QUEUE_NAME\n");
				sbmacro.append("exten => login,1,NoOp(Login ${CHANNEL} into queue ${QUEUE_NAME})\n");
				sbmacro.append(" same => n,AddQueueMember(${QUEUE_NAME},${CHANNEL}),,,${CALLERID(num)})\n");
				sbmacro.append(" same => n,Answer\n");
				sbmacro.append(" same => n,SayDigits(${CALLERID(num)})\n");
				sbmacro.append(" same => n,Playback(agent-loginok)\n");
				sbmacro.append(" same => n,Wait(0.5)\n");
				sbmacro.append(" same => n,Hangup\n\n");
				sbmacro.append(";; params: QUEUE_NAME\n");
				sbmacro.append("exten => logoff,1,NoOp(Logoff ${CHANNEL} from queue ${QUEUE_NAME})\n");
				sbmacro.append(" same => n,RemoveQueueMember(${QUEUE_NAME},${CHANNEL})\n");
				sbmacro.append(" same => n,Answer\n");
				sbmacro.append(" same => n,SayDigits(${CALLERID(num)})\n");
				sbmacro.append(" same => n,Playback(agent-loggedoff)\n");
				sbmacro.append(" same => n,Wait(0.5)\n");
				sbmacro.append(" same => n,Hangup\n\n");
				sbmacro.append(";; params: none\n");
				sbmacro.append("exten => pause,1,NoOp(Pause ${CHANNEL} in all queues)\n");
				sbmacro.append(" same => n,PauseQueueMember(,${CHANNEL})\n");
				sbmacro.append(" same => n,Answer\n");
				sbmacro.append(" same => n,SayDigits(${CALLERID(num)})\n");
				sbmacro.append(" same => n,Playback(dictate/paused)\n");
				sbmacro.append(" same => n,Wait(0.5)\n");
				sbmacro.append(" same => n,Hangup\n\n");
				sbmacro.append(";; params: none\n");
				sbmacro.append("exten => resume,1,NoOp(Resume ${CHANNEL} in all queues)\n");
				sbmacro.append(" same => n,UnPauseQueueMember(,${CHANNEL})\n");
				sbmacro.append(" same => n,Answer\n");
				sbmacro.append(" same => n,SayDigits(${CALLERID(num)})\n");
				sbmacro.append(" same => n,Playback(available)\n");
				sbmacro.append(" same => n,Wait(0.5)\n");
				sbmacro.append(" same => n,Hangup\n\n");
				
				c.setDialplan(sbmacro.toString());
				em.persist(c);
			}
			
			CustomDialplan hfeature = null;
			try {
				hfeature = em.createQuery("from CustomDialplan c where c.name=:name", CustomDialplan.class)
						.setParameter("name", "huntgroup-feature").getSingleResult();
			} catch (NoResultException e) {}
			
			if (hfeature == null) {
				hfeature = new CustomDialplan();
				hfeature.setName("huntgroup-feature");
				StringBuilder sb = new StringBuilder()
						.append(";; PRIORITY_EXTEN: first extension to call\n")
						.append(";; STRATEGY: sequential|all\n")
						.append(";; GROUP: & separated channels\n")
						.append(";; DIALTIME: time to dial an extension\n")
						.append(";; TIMEOUT: time to dial group of extensions\n")
						.append(";; TIMES: number of loops in sequential huntgroup\n")
						.append(";; VOICEMAIL: optional voicemail if nobody picks up\n")
						.append("exten => s,1,GotoIf($[\"${PRIORITY_EXTEN}\" = \"\"]?strategy)\n")
						.append(" same => n,Dial(${PRIORITY_EXTEN},${DIALTIME})\n")
						.append(" same => n(strategy),GotoIf($[\"${STRATEGY}\" = \"sequential\"]?seq,1:all,1)\n\n")
						.append("exten => all,1,Dial(${GROUP},${TIMEOUT})\n")
						.append(" same => n,Goto(end,1)\n\n")
						.append("exten => seq,1,Set(TMPGROUP=${GROUP})\n")
						.append(" same => n,While($[\"${SET(DEVICE=${SHIFT(TMPGROUP,&)})}\" != \"\"])\n")
						.append(" same => n,Dial(${DEVICE},${DIALTIME})\n")
						.append(" same => n,EndWhile\n")
						.append(" same => n,GotoIf($[\"${TIMES}\" = \"\"]?decrement)\n")
						.append(" same => n,Set(TIMES=1)\n")
						.append(" same => n(decrement),Set(TIMES=$[${TIMES}-1])\n")
						.append(" same => n,GotoIf($[${TIMES} > 0]?1:end,1)\n\n")
						.append("exten => end,1,GotoIf($[\"${VOICEMAIL}\" = \"\"]?end)\n")
						.append(" same => n,Voicemail(${VOICEMAIL},u)\n")
						.append(" same => n(end),Hangup)\n");
				hfeature.setDialplan(sb.toString());
				em.persist(hfeature);
			}
			
			em.getTransaction().commit();
			
		} catch(Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			
		} finally {
			em.close();
		}
	}
}