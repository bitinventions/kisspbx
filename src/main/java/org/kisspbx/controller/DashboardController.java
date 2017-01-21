package org.kisspbx.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kisspbx.AppContextListener;
import org.kisspbx.AsteriskConnection;
import org.kisspbx.model.Extension;

import com.google.gson.Gson;

@WebServlet(name="dashboardController", 
			urlPatterns={"/dashboard/pbx/", "/dashboard/system/",})
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Pattern versionRegex = Pattern.compile("^Asterisk\\s+(\\d+\\.\\d+\\.\\d+).*"); 
	
	public DashboardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/dashboard/pbx/")) {
				processPBX(request, response);
				
			} 
		
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private void processPBX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<>();
		
		AsteriskConnection asterisk = getAsteriskConnection();
		try {
			asterisk.login();
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("asterisk", "error");
			response.setContentType("application/json");
			response.getWriter().print(new Gson().toJson(map));
			response.getWriter().flush();
			return;
		}
		
		EntityManager em = AppContextListener.getEntityManager();
		try {
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc").getResultList();
			int num = extensions.size();
			int online = 0;
			
			StringBuffer res = new StringBuffer();
			
			asterisk.sendCommand("core show uptime", res);
			String uptime = res.toString().split("\n")[0];
			uptime = uptime.substring(uptime.indexOf(':') + 1).trim();
			
			res.setLength(0);
			asterisk.sendCommand("core show version", res);
			String version = res.toString().split("\n")[0];
			Matcher m = versionRegex.matcher(version);
			if (m.matches())  
				version = m.group(1);
			
			res.setLength(0);
			asterisk.sendCommand("core show calls", res);
			String [] lines = res.toString().split("\n");
			String actives = lines[0].substring(0, lines[0].indexOf(' '));
			String processed = lines[1].substring(0, lines[1].indexOf(' '));
			
			res.setLength(0);
			asterisk.sendCommand("core show hints", res);
			lines = res.toString().split("\n");
			for (String s : lines) {
				if (s.trim().length() == 0 || s.contains("Hints") || s.contains("hints")) continue;
				for (Extension ext : extensions) {
					String extension = String.format("%s@default-extensions", ext.getExtension());
					if (s.contains(extension) && !s.contains("State:Unavailable")) {
						online++;
						break;
					}
				}
			}
			
			System.out.println("Check iax2 extensions");
		
			res.setLength(0);
			asterisk.sendCommand("iax2 show peers", res);
			lines = res.toString().split("\n");
			for (String s : lines) {
				if (s.trim().length() == 0 || s.startsWith("Name/Username") || s.contains("iax2 peers")) continue;
				for (Extension ext : extensions) {
					if (s.startsWith(ext.getUsername() + "/") && s.contains("OK")) {
						online++;
						break;
					}
				}
			}
		
			System.out.println("Check sip registrations");
			
			int ntrunks = 0;
			int trunksOnline = 0;
			
			res = new StringBuffer();
			asterisk.sendCommand("sip show registry", res);
			lines = res.toString().split("\n");
			for (String s : lines) {
				if (s.trim().length() == 0 || s.startsWith("Host") || s.contains("SIP registrations")) continue;
				ntrunks++;
				if (s.contains("Registered")) trunksOnline++;
			}
			
			asterisk.logoff();
			
			map.put("asterisk", "ok");
			map.put("uptime", uptime);
			map.put("version", version);
			map.put("actives", actives);
			map.put("processed", processed);
			map.put("phones", num);
			map.put("phonesOnline", online);
			map.put("trunks", ntrunks);
			map.put("trunksOnline", trunksOnline);
			response.setContentType("application/json");
			response.getWriter().print(new Gson().toJson(map));
			response.getWriter().flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("application/json");
			map.put("asterisk", "error");
			response.setContentType("application/json");
			response.getWriter().print(new Gson().toJson(map));
			response.getWriter().flush();
			
		} finally {
			em.close();
		}
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
}