package org.kisspbx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kisspbx.AppContextListener;
import org.kisspbx.model.Template;
import org.kisspbx.model.TemplateType;

/**
 * Servlet implementation class Template
 */
@Startup
@WebServlet(name="templateController", 
			urlPatterns={"/template/",
					"/template/create", 
					"/template/save", 
					"/template/edit", 
					"/template/update", 
					"/template/delete"})
public class TemplateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public TemplateController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/template/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/template/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/template/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/template/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/template/delete")) {
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
	
	private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.getRequestDispatcher("/WEB-INF/template/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Template t = em.find(Template.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("template", t);
			request.getRequestDispatcher("/WEB-INF/template/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/template/");
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Template t = new Template();
		try {
			em.getTransaction().begin();
			t.setName(request.getParameter("name").replace(' ', '-'));
			try {
				t.setType(TemplateType.valueOf(request.getParameter("type")));
			} catch (IllegalArgumentException e) { }
			t.setTemplate(request.getParameter("template"));
			em.persist(t);
			em.getTransaction().commit();
			request.setAttribute("info", "Template " + t.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/template/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("template", t);
			request.getRequestDispatcher("/WEB-INF/template/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Template t = new Template();
		
		try {
			t.setId(Long.parseLong(request.getParameter("id")));
			t.setVersion(Long.parseLong(request.getParameter("version")));
			t.setName(request.getParameter("name").replace(' ', '-'));
			try {
				t.setType(TemplateType.valueOf(request.getParameter("type")));
			} catch (IllegalArgumentException e) { }
			t.setTemplate(request.getParameter("template"));
			
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
			request.setAttribute("info", "Template " + t.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/template/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("template", t);
			request.getRequestDispatcher("/WEB-INF/template/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("template", t);
			request.getRequestDispatcher("/WEB-INF/template/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Template t = em.find(Template.class, Long.parseLong(request.getParameter("id")));
			em.remove(t);
			em.getTransaction().commit();
			request.setAttribute("info", "Template " + t.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/template/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/template/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(t.id) from Template t").getSingleResult();
			Query q = em.createQuery("from Template t");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Template> templates = q.getResultList();
			request.setAttribute("templates", templates);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/template/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<Template> templates = new ArrayList<Template>();
			request.setAttribute("templates", templates);
			request.getRequestDispatcher("/WEB-INF/template/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
