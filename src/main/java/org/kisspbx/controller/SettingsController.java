package org.kisspbx.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kisspbx.AppContextListener;
import org.kisspbx.model.ExtraSetting;
import org.kisspbx.model.Settings;
import org.kisspbx.model.SettingsType;

/**
 * Servlet implementation class Template
 */
@Startup
@WebServlet(name="settingsController", 
			urlPatterns={
					"/settings/pbx/", "/settings/pbx/save",
					"/settings/manager/", "/settings/manager/save",
					"/settings/iax2/", "/settings/iax2/save",
					"/settings/sip/", "/settings/sip/save",
					"/settings/queue/", "/settings/queue/save",
					"/settings/voicemail/", "/settings/voicemail/save",
					"/settings/cdr/", "/settings/cdr/save"
			})
public class SettingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public SettingsController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getRequestURL());
		
		try {
			if (request.getServletPath().equals("/settings/pbx/")) {
				processShowPBX(request, response);
				
			} else if (request.getServletPath().equals("/settings/pbx/save")) {
				processSavePBX(request, response);
				
			} else if (request.getServletPath().equals("/settings/manager/")) {
				processShow(request, response, SettingsType.MANAGER);
				
			} else if (request.getServletPath().equals("/settings/manager/save")) {
				processSave(request, response, SettingsType.MANAGER);
				
			} else if (request.getServletPath().equals("/settings/sip/")) {
				processShow(request, response, SettingsType.SIP);
				
			} else if (request.getServletPath().equals("/settings/sip/save")) {
				processSave(request, response, SettingsType.SIP);
				
			} else if (request.getServletPath().equals("/settings/iax2/")) {
				processShow(request, response, SettingsType.IAX2);
				
			} else if (request.getServletPath().equals("/settings/iax2/save")) {
				processSave(request, response, SettingsType.IAX2);
				
			} else if (request.getServletPath().equals("/settings/voicemail/")) {
				processShow(request, response, SettingsType.VOICEMAIL);
				
			} else if (request.getServletPath().equals("/settings/voicemail/save")) {
				processSave(request, response, SettingsType.VOICEMAIL);
				
			} else if (request.getServletPath().equals("/settings/queue/")) {
				processShow(request, response, SettingsType.QUEUE);
				
			} else if (request.getServletPath().equals("/settings/queue/save")) {
				processSave(request, response, SettingsType.QUEUE);
				
			} else if (request.getServletPath().equals("/settings/cdr/")) {
				processShow(request, response, SettingsType.CDR);
				
			} else if (request.getServletPath().equals("/settings/cdr/save")) {
				processSave(request, response, SettingsType.CDR);
				
			}  else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void processShowPBX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setAttribute("pbxSettings", findSettings(SettingsType.PBX));
		request.setAttribute("dialplanSettings", findSettings(SettingsType.DIALPLAN));
		request.setAttribute("dialplanglobals", findExtraSettings(SettingsType.DIALPLAN_GLOBALS));
		request.setAttribute("featureSettings", findSettings(SettingsType.FEATURES));
		request.setAttribute("extrafeature", findExtraSettings(SettingsType.FEATURES));
		request.getRequestDispatcher("/WEB-INF/settings/pbx/index.jsp").forward(request, response);
	}

	private void processSavePBX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			saveSetting(em, "asterisk.host", request.getParameter("asterisk.host"), SettingsType.PBX);
			saveSetting(em, "asterisk.user", request.getParameter("asterisk.user"), SettingsType.PBX);
			saveSetting(em, "asterisk.password", request.getParameter("asterisk.password"), SettingsType.PBX);
			
			saveSetting(em, "static", request.getParameter("static"), SettingsType.DIALPLAN);
			saveSetting(em, "writeprotect", request.getParameter("writeprotect"), SettingsType.DIALPLAN);
			saveSetting(em, "extenpatternmatchnew", request.getParameter("extenpatternmatchnew"), SettingsType.DIALPLAN);
			saveSetting(em, "clearglobalvars", request.getParameter("clearglobalvars"), SettingsType.DIALPLAN);
			saveSetting(em, "extra", request.getParameter("dialplanglobals"), SettingsType.DIALPLAN_GLOBALS);
			
			saveSetting(em, "pickupexten", request.getParameter("pickupexten"), SettingsType.FEATURES);
			saveSetting(em, "extra", request.getParameter("extrafeatures"), SettingsType.FEATURES);
			
			em.getTransaction().commit();
			request.setAttribute("info", "Settings updated");
			response.sendRedirect(String.format("%s/settings/pbx/", request.getContextPath()));
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(String.format("%s/settings/pbx/", request.getContextPath()));
			
		} finally {
			em.close();
		}
	}
	
	private void processShow(HttpServletRequest request, HttpServletResponse response, SettingsType type) throws ServletException, IOException  {
		request.setAttribute("settings", findSettings(type));
		request.setAttribute("extra", findExtraSettings(type));
		request.getRequestDispatcher(
					String.format("/WEB-INF/settings/%s/index.jsp", type.toString().toLowerCase()))
				.forward(request, response);
	}

	private void processSave(HttpServletRequest request, HttpServletResponse response,SettingsType type) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Enumeration<String> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String name = params.nextElement();
				String value = request.getParameter(name);
				saveSetting(em, name, value, type);
			}
			if (request.getParameter("extra") == null || 
					"".equals(request.getParameter("extra")))
				saveSetting(em, "extra", null, type);
			
			em.getTransaction().commit();
			request.setAttribute("info", "Settings updated");
			response.sendRedirect(String.format("%s/settings/%s/", request.getContextPath(), type.toString().toLowerCase()));
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(String.format("%s/settings/%s/", request.getContextPath(), type.toString().toLowerCase()));
			
		} finally {
			em.close();
		}
	}
	
	private Map<String, String> findSettings(SettingsType type) {
		EntityManager em = AppContextListener.getEntityManager();
		List<Settings> settingsList = null;
		HashMap<String, String> settings = new HashMap<>();
		try {
			settingsList = em.createQuery("from Settings s where s.type = :type", Settings.class)
					.setParameter("type", type)
					.getResultList();
			for (Settings s : settingsList) {
				settings.put(s.getName(), s.getValue());
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return settings;
	}
	
	private String findExtraSettings(SettingsType type) {
		EntityManager em = AppContextListener.getEntityManager();
		ExtraSetting extra = null;
		try {
			extra = em.createQuery("from ExtraSetting s where s.type = :type", ExtraSetting.class)
					.setParameter("type", type)
					.getSingleResult();
				
		} catch (Exception e) {
			
		} finally {
			em.close();
		}
		
		return (extra != null)?extra.getValue():null;
	}
	
	private void saveSetting(EntityManager em, String name, String value, SettingsType type) {
		if ("extra".equals(name)) {
			ExtraSetting s = null;
			try {
				s = em.createQuery("from ExtraSetting s where s.type = :type", ExtraSetting.class)
					.setParameter("type", type)
					.getSingleResult();
				
			} catch (NoResultException e) {
				s = new ExtraSetting();
				s.setType(type);
			}
			s.setValue(value);
			em.merge(s);
			
		} else {
			Settings s = null;
			try {
				s = em.createQuery("from Settings s where s.type = :type and s.name = :name", Settings.class)
					.setParameter("type", type)
					.setParameter("name", name).getSingleResult();
				
			} catch (NoResultException e) {
				s = new Settings();
				s.setType(type);
				s.setName(name);
			}
			s.setValue(value);
			em.merge(s);
		}
	}
}
