package org.kisspbx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kisspbx.AppContextListener;
import org.kisspbx.model.Extension;
import org.kisspbx.model.ExtensionType;
import org.kisspbx.model.Template;
import org.kisspbx.model.TemplateType;

@WebServlet(name="extensionController", 
			urlPatterns={"/extension/",
					"/extension/create", 
					"/extension/save", 
					"/extension/edit", 
					"/extension/update", 
					"/extension/delete"})
public class ExtensionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ExtensionController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/extension/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/extension/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/extension/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/extension/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/extension/delete")) {
				processDelete(request, response);
				
			} else {
				processList(request, response);
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
	
	@SuppressWarnings("unchecked")
	private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Extension e = new Extension();
			e.setType(ExtensionType.valueOf(request.getParameter("type")));
			TemplateType ttype = TemplateType.valueOf(request.getParameter("type"));
			List<Template> templates = em.createQuery("from Template t where t.type=:type").setParameter("type", ttype).getResultList();
			request.setAttribute("extension", e);
			request.setAttribute("templates", templates);
			request.getRequestDispatcher("/WEB-INF/extension/create.jsp").forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/extension/");
		
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Extension e = em.find(Extension.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("extension", e);
			TemplateType type = TemplateType.valueOf(e.getType().toString());
			List<Template> templates = em.createQuery("from Template t where t.type=:type").setParameter("type", type).getResultList();
			request.setAttribute("templates", templates);
			request.getRequestDispatcher("/WEB-INF/extension/edit.jsp").forward(request, response);
			
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			response.sendRedirect(request.getContextPath() + "/extension/");
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Extension e = new Extension();
		try {
			em.getTransaction().begin();
			e.setType(ExtensionType.valueOf(request.getParameter("type")));
			e.setExtension(request.getParameter("extension"));
			e.setUsername(request.getParameter("username"));
			e.setPassword(request.getParameter("password"));
			e.setCallerid(request.getParameter("callerid"));
			e.setCallgroups(request.getParameter("callgroups"));
			e.setPickupgroups(request.getParameter("pickupgroups"));
			e.setVoicemail("true".equals(request.getParameter("voicemail")));
			e.setVmpin(request.getParameter("vmpin"));
			e.setVmemail(request.getParameter("vmemail"));
			e.setParameters(request.getParameter("parameters"));
			try {
				Template t = em.find(Template.class, Long.parseLong(request.getParameter("template")));
				e.setTemplate(t);
				
			} catch(Exception ex) {}
			
			em.persist(e);
			em.getTransaction().commit();
			request.setAttribute("info", "Extension " + e.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/extension/");
			
		} catch (PersistenceException ex) {
			em.getTransaction().rollback();
			request.setAttribute("error", ex.getMessage());
			request.setAttribute("extension", e);
			List<Template> templates = em.createQuery("from Template t").getResultList();
			request.setAttribute("templates", templates);
			request.getRequestDispatcher("/WEB-INF/extension/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Extension e = null;
		try {
			e = em.find(Extension.class, Long.parseLong(request.getParameter("id")));
			e.setVersion(Long.parseLong(request.getParameter("version")));
			e.setExtension(request.getParameter("extension"));
			e.setUsername(request.getParameter("username"));
			e.setPassword(request.getParameter("password"));
			e.setCallerid(request.getParameter("callerid"));
			e.setCallgroups(request.getParameter("callgroups"));
			e.setPickupgroups(request.getParameter("pickupgroups"));
			e.setVoicemail("true".equals(request.getParameter("voicemail")));
			e.setVmpin(request.getParameter("vmpin"));
			e.setVmemail(request.getParameter("vmemail"));
			e.setParameters(request.getParameter("parameters"));
			Template t = em.find(Template.class, Long.parseLong(request.getParameter("template")));
			e.setTemplate(t);
			em.getTransaction().begin();
			em.merge(e);
			em.getTransaction().commit();
			request.setAttribute("info", "Extension " + e.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/extension/");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			request.setAttribute("error", ex.getMessage());
			List<Template> templates = em.createQuery("from Template t").getResultList();
			request.setAttribute("templates", templates);
			request.setAttribute("extension", e);
			request.getRequestDispatcher("/WEB-INF/extension/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Extension e = em.find(Extension.class, Long.parseLong(request.getParameter("id")));
			em.remove(e);
			em.getTransaction().commit();
			request.setAttribute("info", "Extension " + e.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/extension/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/extension/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(e.id) from Extension e").getSingleResult();
			Query q = em.createQuery("from Extension e order by e.extension asc");
			int max = 12;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Extension> extensions = q.getResultList();
			request.setAttribute("extensions", extensions);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/extension/index.jsp").forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			List<Extension> extensions = new ArrayList<Extension>();
			request.setAttribute("templates", extensions);
			request.getRequestDispatcher("/WEB-INF/extension/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
