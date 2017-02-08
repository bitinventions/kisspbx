package org.kisspbx.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.kisspbx.AppContextListener;
import org.kisspbx.model.CustomDialplan;
import org.kisspbx.model.DestinationType;
import org.kisspbx.model.Extension;
import org.kisspbx.model.ExtraSetting;
import org.kisspbx.model.InboundRoute;
import org.kisspbx.model.ManagerApiUser;
import org.kisspbx.model.OutboundRoute;
import org.kisspbx.model.Settings;
import org.kisspbx.model.Template;
import org.kisspbx.model.app.Application;
import org.kisspbx.model.feature.Queue;
import org.kisspbx.model.provider.SIPProvider;

public class Configurator {
	
	private String path;
	private List<FileConfig> files = new ArrayList<>();
	
	public Configurator(String p) {
		path = p;
	}
	
	private FileConfig getOrCreate(String filename) {
		FileConfig file = null;
		for (FileConfig f : files) {
			if (f.getFilename().equals(filename)) {
				file = f;
				break;
			}
		}
		if (file == null) {
			file = new FileConfig();
			file.setFilename(filename);
			files.add(file);
		}
		return file;
	}
	
	public String execute() {
		StringBuffer result = new StringBuffer();
		
		try {
			FileConfig modules = getOrCreate("modules.conf"); 
			modules.getOrCreate("modules").getLines().add(new Line("autoload","yes"));
			modules.getOrCreate("globals").getLines().add(new NewLine());
			
			result.append("Processing SIP ...");
			processSIP();
			result.append("OK\n");
			
			result.append("Processing IAX2 ...");
			processIAX();
			result.append("OK\n");
			
			result.append("Processing Manager ...");
			processManager();
			result.append("OK\n");
			
			result.append("Processing Queues ...");
			processQueue();
			result.append("OK\n");
			
			result.append("Processing Voicemail ...");
			processVoicemail();
			result.append("OK\n");
			
			result.append("Processing features ...");
			processFeatures();
			result.append("OK\n");
			
			result.append("Processing cdr ...");
			processCDR();
			result.append("OK\n");
			
			result.append("Processing dialplan ...");
			processDialplan();
			result.append("OK\n");

			String ts = "" + System.currentTimeMillis();
			for (FileConfig f : files) {
				result.append("Write ").append(f.getFilename()).append(" ...");
				new FileWriter(path, f, ts).write();
				result.append("success\n");
			}
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
			result.append("ERROR (").append(e.getMessage()).append(")\n");
		} catch (IOException e) {
			e.printStackTrace();
			result.append("ERROR (").append(e.getMessage()).append(")\n");
		}
		
		return result.toString();
	}
	
	private void processSIP() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("sip.conf");
		
		try {
			List<Settings> general = em.createQuery("from Settings s where s.type = 'SIP'", Settings.class).getResultList();
			List<Template> templates = em.createQuery("from Template t where t.type = 'SIP' order by t.name asc", Template.class).getResultList();
			List<SIPProvider> trunks = em.createQuery("from SIPProvider t order by t.name asc", SIPProvider.class).getResultList();
			List<Extension> extensions = em.createQuery("from Extension e where e.type = 'SIP' order by e.extension asc", Extension.class).getResultList();
			ExtraSetting advanced = null;
			try {
				advanced = em.createQuery("from ExtraSetting s where s.type = 'SIP'", ExtraSetting.class).getSingleResult();
			} catch(NoResultException e) {}
		
			// general settings
			Category generalCategory = f.getOrCreate("general");
			for (Settings s : general) 
				generalCategory.getLines().add(new Line(s.getName(), s.getValue()));
			
			if (advanced != null) 
				generalCategory.addRaw(advanced.getValue());
			
			generalCategory.getLines().add(new NewLine());
				
			// registrations
			for (SIPProvider t : trunks) {
				if (t.isRegister()) {
					String value = String.format("%s:%s@%s", t.getUsername(), t.getPassword(), t.getHost());
					if (t.getDid() != null && !t.getDid().equals("")) 
						value += "/" + t.getDid();
					generalCategory.getLines().add(new CodeLine("register", value));
				}
			}
			generalCategory.getLines().add(new NewLine());
				
			// authentication section
			f.getOrCreate("authentication").getLines().add(new NewLine());
				
			// templates
			for (Template t : templates) {
				Category c = f.getOrCreate(t.getName());
				c.setTemplate(true);
				c.getLines().add(new Line("context", "default-from-private"));
				c.addRaw(t.getTemplate());
				c.getLines().add(new NewLine());
			}
				
			// trunk peers
			for (SIPProvider t : trunks) {
				Category c = f.getOrCreate(t.getName());
				c.getLines().add(new Line("type", "peer"));
				if (t.isInsecure())
					c.getLines().add(new Line("insecure", "port,invite"));
				c.getLines().add(new Line("context", "default-from-public"));
				c.getLines().add(new Line("host", t.getHost()));
				c.getLines().add(new Line("username", t.getUsername()));
				c.getLines().add(new Line("secret", t.getPassword()));
				c.getLines().add(new Line("fromuser", t.getUsername()));
				c.getLines().add(new Line("fromdomain", t.getHost()));
				if (t.getParameters() != null)
					c.addRaw(t.getParameters());
				c.getLines().add(new NewLine());
			}
				
			// extensions
			for (Extension e : extensions) {
				Category c = f.getOrCreate(e.getUsername());
				c.setInherits(e.getTemplate().getName());
				c.getLines().add(new Line("username", e.getUsername()));
				c.getLines().add(new Line("secret", e.getPassword()));
				c.getLines().add(new Line("callerid", String.format("%s <%s>", e.getCallerid(), e.getExtension())));
				if (e.isVoicemail())
					c.getLines().add(new Line("mailbox", String.format("%s@default", e.getExtension())));
				if (e.getCallgroups() != null && !e.getCallgroups().equals(""))
					c.getLines().add(new Line("namedcallgroup", e.getCallgroups()));
				if (e.getPickupgroups() != null && !e.getPickupgroups().equals(""))
					c.getLines().add(new Line("namedpickupgroup", e.getPickupgroups()));
				if (e.getParameters() != null)
					c.addRaw(e.getParameters());
				c.getLines().add(new NewLine());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processIAX() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("iax.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'IAX2'", Settings.class).getResultList();
			List<Template> templates = em.createQuery("from Template t where t.type = 'IAX2' order by t.name asc", Template.class).getResultList();
			//List<Provider> trunks = em.createQuery("from Trunk t where t.type = 'IAX2' order by t.name asc").getResultList();
			List<Extension> extensions = em.createQuery("from Extension e where e.type = 'IAX2' order by e.extension asc", Extension.class).getResultList();
			ExtraSetting advanced = null;
			try {
				advanced = em.createQuery("from ExtraSetting s where s.type = 'IAX2'", ExtraSetting.class).getSingleResult();
			} catch(NoResultException e) {}
		
			
			// general settings
			Category general = f.getOrCreate("general");
			for (Settings s : settings)
				general.getLines().add(new Line(s.getName(), s.getValue()));
			if (advanced != null) 
				general.addRaw(advanced.getValue());
			general.getLines().add(new NewLine());

			/* registrations
			sb.setLength(0);
			for (Provider t : trunks) {
				if (t.getRegister() != null)
					sb.append("register => ").append(t.getRegister()).append("\n\n");
			}
			fs.write(sb.toString().getBytes());
			*/
			
			// templates
			for (Template t : templates) {
				Category c = f.getOrCreate(t.getName());
				c.setTemplate(true);
				c.getLines().add(new Line("context", "default-from-private"));
				c.addRaw(t.getTemplate());
				c.getLines().add(new NewLine());
			}
			
			/* trunks
			for (Provider t : trunks) {
				if (t.getParameters() == null) continue;
				sb.setLength(0);
				sb.append("[").append(t.getName()).append("]\n");
				sb.append("context=from-public").append("\n");
				sb.append(t.getParameters()).append("\n\n");
				fs.write(sb.toString().getBytes());
			}*/
			
			// extensions
			for (Extension e : extensions) {
				Category c = f.getOrCreate(e.getUsername());
				c.setInherits(e.getTemplate().getName());
				c.getLines().add(new Line("username", e.getUsername()));
				c.getLines().add(new Line("secret", e.getPassword()));
				c.getLines().add(new Line("callerid", String.format("%s <%s>", e.getCallerid(), e.getExtension())));
				if (e.isVoicemail())
					c.getLines().add(new Line("mailbox", String.format("%s@default", e.getExtension())));
				if (e.getParameters() != null)
					c.addRaw(e.getParameters());
				c.getLines().add(new NewLine());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processManager() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("manager.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'MANAGER'", Settings.class).getResultList();
			List<ManagerApiUser> users = em.createQuery("from ManagerApiUser as u order by u.name asc", ManagerApiUser.class).getResultList();
			ExtraSetting advanced = null;
			try {
				advanced = em.createQuery("from ExtraSetting s where s.type='MANAGER'", ExtraSetting.class).getSingleResult();
			} catch (NoResultException e) {}
			
			// general settings
			Category general = f.getOrCreate("general");
			for (Settings s : settings) 
				general.getLines().add(new Line(s.getName(), s.getValue()));
			if (advanced != null) 
				general.addRaw(advanced.getValue());
			general.getLines().add(new NewLine());
			
			// users
			for (ManagerApiUser u : users) {
				Category c = f.getOrCreate(u.getName());
				c.getLines().add(new Line("secret", u.getPassword()));
				c.getLines().add(new Line("read", u.getReadPermission()));
				c.getLines().add(new Line("write", u.getWritePermission()));
				if (u.getDeny() != null) {
					for (String s : u.getDeny().split(",")) 
						c.getLines().add(new Line("deny", s));
				}
				if (u.getPermit() != null) {
					for (String s : u.getPermit().split(",")) 
						c.getLines().add(new Line("permit", s));
				}
				c.getLines().add(new NewLine());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processQueue() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("queues.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'QUEUE'", Settings.class).getResultList();
			List<Queue> queues = em.createQuery("from Queue q order by q.name asc", Queue.class).getResultList();
			ExtraSetting advanced = null;
			try {
				advanced = em.createQuery("from ExtraSetting s where s.type = 'QUEUE'", ExtraSetting.class).getSingleResult();
			} catch(NoResultException e) {}
			
			// general settings
			Category general = f.getOrCreate("general");
			for(Settings s : settings)
				general.getLines().add(new Line(s.getName(), s.getValue()));
			if (advanced != null)
				general.addRaw(advanced.getValue());
			general.getLines().add(new NewLine());
			
			// queues
			for (Queue q : queues) {
				Category c = f.getOrCreate(q.getName());
				c.getLines().add(new Line("strategy", q.getStrategy().toString().toLowerCase()));
				c.getLines().add(new Line("joinempty", q.isJoinempty()?"yes":"no"));
				c.getLines().add(new Line("leavewhenempty", q.isLeavewhenempty()?"yes":"no"));
				c.getLines().add(new Line("maxlen", Integer.toString(q.getMaxlen())));
				c.getLines().add(new Line("timeout", Integer.toString(q.getTimeout())));
				c.getLines().add(new Line("retry", Integer.toString(q.getRetry())));
				c.getLines().add(new Line("wrapuptime", Integer.toString(q.getWrapuptime())));
				c.addRaw(q.getParameters());
				if (q.getMembers() != null) {
					for (String str : q.getMembers().split("\n"))
						c.getLines().add(new CodeLine("member", str.trim()));
				}
				c.getLines().add(new NewLine());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processVoicemail() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("voicemail.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'VOICEMAIL'", Settings.class).getResultList();
			List<Extension> extensions = em.createQuery("from Extension e where e.voicemail = 1 order by e.extension asc", Extension.class).getResultList();
			ExtraSetting advanced = null;
			try {
				advanced = em.createQuery("from ExtraSetting s where s.type = 'VOICEMAIL'", ExtraSetting.class).getSingleResult();
			} catch (NoResultException e) {}
			
			// general settings
			Category general = f.getOrCreate("general");
			for(Settings s : settings) 
				general.getLines().add(new Line(s.getName(), s.getValue()));
			if (advanced != null)
				general.addRaw(advanced.getValue());
			general.getLines().add(new NewLine());
			
			// extensions
			Category c = f.getOrCreate("default");
			for (Extension e : extensions) {
				c.getLines().add(new CodeLine(e.getExtension(), 
									String.format("%s,%s,%s,,attach=yes",
											e.getVmpin(), e.getCallerid(), 
											e.getVmemail() != null?e.getVmemail():" ")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processDialplan() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("extensions.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'DIALPLAN'", Settings.class).getResultList();
			
			ExtraSetting globals = null;
			try {
				globals = em.createQuery("from ExtraSetting s where s.type = 'DIALPLAN_GLOBALS'", ExtraSetting.class).getSingleResult();
			} catch (NoResultException e) {}
			
			List<InboundRoute> inbounds = em.createQuery("from InboundRoute r order by r.ddi asc", InboundRoute.class).getResultList();
			List<OutboundRoute> outbounds = em.createQuery("from OutboundRoute r order by r.dialpattern asc", OutboundRoute.class).getResultList();
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc", Extension.class).getResultList();
			List<CustomDialplan> dialplans = em.createQuery("from CustomDialplan a order by a.name asc", CustomDialplan.class).getResultList();
			List<Application> apps = em.createQuery("from Application a order by a.pattern asc", Application.class).getResultList();
			//List<Conference> conferences = em.createQuery("from Conference c order by c.name asc", Conference.class).getResultList();
			
			// general settings
			Category general = f.getOrCreate("general");
			for (Settings s : settings) {
				general.getLines().add(new Line(s.getName(), s.getValue()));
			}
			general.getLines().add(new NewLine());
			
			// general settings
			Category globalCat = f.getOrCreate("global");
			if (globals != null)
				globalCat.addRaw(globals.getValue());
			globalCat.getLines().add(new NewLine());
			
			//if (globals != null && globals.getConfiguration() != null) {
			//	sb.append(globals.getConfiguration());
			//}
			
			// inbound routes
			Category inboundCat = f.getOrCreate("default-from-public");
			inboundCat.setComment("calls coming from outside");
			for(InboundRoute i : inbounds) {
				String pattern = i.getDdi();
				if (pattern == null) pattern = "_X.";
				if (i.getCli() != null && !i.getCli().trim().equals("")) pattern += ("/" + i.getCli().trim());
				if (i.getDestination() != null) {
					inboundCat.getLines().add(new CodeLine("exten", String.format("%s,1,NoOp(Inbound route: %s)", pattern, i.getName())));
					String context = "invalid";
					if (i.getDestinationType() == DestinationType.EXTENSION) 
						context = "default-extensions";
					else if (i.getDestinationType() == DestinationType.DIALPLAN)
						context = "default-applications";
					inboundCat.getLines().add(new CodeLine(" same", String.format("n,Goto(%s,%s,1)", context, i.getDestination())));
				}
			}
			inboundCat.getLines().add(new NewLine());

			// outbound routes
			Category outboundCat = f.getOrCreate("default-to-public");
			outboundCat.setComment("calls sent to outside");
			for(OutboundRoute o : outbounds) {
				outboundCat.getLines().add(new CodeLine("exten", String.format("%s,1,NoOp(Outbound route %s)", o.getDialpattern(), o.getName())));
				String destination = (o.getPrefix() > 0)?("${EXTEN:" + o.getPrefix() + "}"):"${EXTEN}";
				if (o.getPrepend() != null)
					destination = o.getPrepend() + destination;
				outboundCat.getLines().add(new CodeLine(" same", String.format("n,Dial(%s/%s,60)", o.getProvider().getDevice(), destination)));
			}
			outboundCat.getLines().add(new NewLine());

			// private context
			Category privateCat = f.getOrCreate("default-from-private");
			privateCat.setComment("context for local extensions");
			privateCat.getLines().add(new CodeLine("include", "default-to-public"));
			privateCat.getLines().add(new CodeLine("include", "default-extensions"));
			privateCat.getLines().add(new CodeLine("include", "default-applications"));
			privateCat.getLines().add(new NewLine());
			
			// extensions
			Category extensionsCat = f.getOrCreate("default-extensions");
			extensionsCat.setComment("definition of local extensions");
			for (Extension e : extensions) {
				extensionsCat.getLines().add(new CodeLine("exten", String.format("%s,hint,%s", e.getExtension(), e.getDialString())));
				extensionsCat.getLines().add(new CodeLine(" same", String.format("1,Set(EXTEN_NUMBER=%s)", e.getExtension())));
				extensionsCat.getLines().add(new CodeLine(" same", String.format("n,Set(EXTEN_DEVICE=%s)", e.getDialString())));
				if (e.isVoicemail())
					extensionsCat.getLines().add(new CodeLine(" same", String.format("n,Set(EXTEN_VOICEMAIL=%s)", e.getExtension())));
				extensionsCat.getLines().add(new CodeLine(" same", "n,Goto(route-extension,s,1)"));
				extensionsCat.getLines().add(new NewLine());
			}

			// local-applications
			Category appsCat = f.getOrCreate("default-applications");
			appsCat.setComment("routes to local dialplan features");
			for (Application a : apps) {
				appsCat.getLines().add(new CodeLine("exten", String.format("%s,1,NoOp(Application %s)", a.getPattern(), a.getDescription())));
				// set application variables
				for (String var : a.getAppVariables())
					appsCat.getLines().add(new CodeLine(" same", String.format("n,Set(%s)", var.trim())));
				
				// set variables
				for (String var : a.getVariableList()) {
					appsCat.getLines().add(new CodeLine(" same", String.format("n,Set(%s)", var.trim())));
				}
				
				// jump to application's dialplan
				appsCat.getLines().add(
						new CodeLine(" same", 
								String.format("n,Gosub(%s,%s,%s)", 
									a.getApplication(),
									(a.getExtension() != null && !a.getExtension().equals(""))?a.getExtension():"s",
									(a.getPriority() != null && !a.getPriority().equals(""))?a.getPriority():"1")));
				
				// jump to destination if configured
				if (a.getFinalDestination() != null && !a.getFinalDestination().equals("")) {
					String context = "invalid";
					if (a.getFinalDestinationType() == DestinationType.EXTENSION) 
						context = "default-extensions";
					else if (a.getFinalDestinationType() == DestinationType.DIALPLAN)
						context = "default-applications";
					appsCat.getLines().add(new CodeLine(" same", String.format("n,Goto(%s,%s,1)", context, a.getFinalDestination())));
				}
				
				appsCat.getLines().add(new NewLine());
			}
			
			// write custom dialplan
			for (CustomDialplan a : dialplans) {
				Category c = f.getOrCreate(a.getName());
				c.addRaw(a.getDialplan());
				c.getLines().add(new NewLine());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}

	private void processFeatures() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig f = getOrCreate("features.conf");
		
		try {
			List<Settings> settings = em.createQuery("from Settings s where s.type = 'FEATURES'", Settings.class).getResultList();
			
			// general settings
			Category general = f.getOrCreate("general");
			for (Settings s : settings) {
				general.getLines().add(new Line(s.getName(), s.getValue()));
			}
			general.getLines().add(new NewLine());
			
			f.getOrCreate("featuremap").getLines().add(new NewLine());
			
			f.getOrCreate("applicationmap").getLines().add(new NewLine());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}
	
	private void processCDR() throws ConfigurationException {
		EntityManager em = AppContextListener.getEntityManager();
		FileConfig modules = getOrCreate("modules.conf");
		FileConfig odbc = getOrCreate("res_odbc.conf");
		FileConfig cdr = getOrCreate("cdr_adaptive_odbc.conf");
		
		try {
			List<Settings> results = em.createQuery("from Settings s where s.type = 'CDR'", Settings.class).getResultList();
			Map<String, Settings> settings = new HashMap<>();
			for (Settings s : results)
				settings.put(s.getName(), s);
			
			// disable odbc cdr 
			Category modulesCat = modules.getOrCreate("modules");
			modulesCat.getLines().add(new CodeLine("noload", "cdr_odbc.so"));
			
			if (settings.containsKey("enabled") && settings.get("enabled").getValue().equals("true")) {
				// create res_odbc dsn config
				Category res_odbc = odbc.getOrCreate("asterisk-cdr");
				res_odbc.getLines().add(new CodeLine("enabled", "yes"));
				res_odbc.getLines().add(new CodeLine("dsn", settings.get("dsn").getValue()));
				res_odbc.getLines().add(new CodeLine("database", settings.get("database").getValue()));
				res_odbc.getLines().add(new CodeLine("username", settings.get("username").getValue()));
				res_odbc.getLines().add(new CodeLine("password", settings.get("password").getValue()));
				res_odbc.getLines().add(new CodeLine("pre-connect", "yes"));
				
				// create cdr config
				Category cdr_odbc = cdr.getOrCreate("asterisk-cdr");
				cdr_odbc.getLines().add(new Line("connection", "asterisk-cdr"));
				cdr_odbc.getLines().add(new Line("table", "CDR"));
				cdr_odbc.getLines().add(new CodeLine("alias start", "calldate"));
				cdr_odbc.getLines().add(new CodeLine("alias callerid", "cid"));
				
			} else {
				modulesCat.getLines().add(new CodeLine("noload", "cdr_adaptive_odbc.so"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
			
		} finally {
			em.close();
		}
	}
}
