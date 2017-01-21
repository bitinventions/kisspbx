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
import org.kisspbx.model.feature.Conference;

/**
 * Servlet implementation class Template
 */
@Startup
@WebServlet(name="conferenceController", 
			urlPatterns={"/feature/conference/", 
					"/feature/conference/show", 
					"/feature/conference/create", 
					"/feature/conference/save", 
					"/feature/conference/edit", 
					"/feature/conference/update", 
					"/feature/conference/delete"})
public class ConferenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ConferenceController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/feature/conference/show")) {
				processShow(request, response);
				
			} else if(request.getServletPath().equals("/feature/conference/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/feature/conference/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/feature/conference/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/feature/conference/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/feature/conference/delete")) {
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
	
	private void processShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Conference c = new Conference();
		try {
			c = em.find(Conference.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("conference", c);
			request.getRequestDispatcher("/WEB-INF/feature/conference/show.jsp").forward(request, response);
				
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/feature/conference/");
			
		} finally {
			em.close();
		}
	}
	
	private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		Conference c = new Conference();
		request.setAttribute("conference", c);
		request.getRequestDispatcher("/WEB-INF/feature/conference/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Conference c = em.find(Conference.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("conference", c);
			request.getRequestDispatcher("/WEB-INF/feature/conference/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/feature/conference/show?id=" + request.getParameter("id"));
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Conference c = new Conference();
		try {
			em.getTransaction().begin();
			c.setName(request.getParameter("name"));
			c.setDescription(request.getParameter("description"));
			em.persist(c);
			em.getTransaction().commit();
			request.setAttribute("info", "Conference " + c.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/feature/conference/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("conference", c);
			request.getRequestDispatcher("/WEB-INF/feature/conference/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Conference c = new Conference();
		
		try {
			em.getTransaction().begin();
			c.setId(Long.parseLong(request.getParameter("id")));
			c.setVersion(Long.parseLong(request.getParameter("version")));
			c.setName(request.getParameter("name"));
			c.setDescription(request.getParameter("description"));
			em.merge(c);
			em.getTransaction().commit();
			request.setAttribute("info", "Conference " + c.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/feature/conference/show?id=" + c.getId());
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("conference", c);
			request.getRequestDispatcher("/WEB-INF/feature/conference/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Conference c = em.find(Conference.class, Long.parseLong(request.getParameter("id")));
			em.remove(c);
			em.getTransaction().commit();
			request.setAttribute("info", "Conference " + c.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/feature/conference/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/feature/conference/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(c.id) from Conference c").getSingleResult();
			Query q = em.createQuery("from Conference c order by c.name asc");
			int max = 12;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Conference> confs = q.getResultList();
			request.setAttribute("conferences", confs);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/feature/conference/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<Conference> confs = new ArrayList<Conference>();
			request.setAttribute("conferences", confs);
			request.getRequestDispatcher("/WEB-INF/feature/conference/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
