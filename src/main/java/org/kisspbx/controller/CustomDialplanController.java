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
import org.kisspbx.model.CustomDialplan;

@WebServlet(name="customDialplanController", 
			urlPatterns={"/custom/",
					"/custom/create", 
					"/custom/save", 
					"/custom/edit", 
					"/custom/update", 
					"/custom/delete"})
public class CustomDialplanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CustomDialplanController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/custom/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/custom/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/custom/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/custom/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/custom/delete")) {
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
		CustomDialplan app = new CustomDialplan();
		request.setAttribute("app", app);
		request.getRequestDispatcher("/WEB-INF/custom/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			CustomDialplan app = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("app", app);
			request.getRequestDispatcher("/WEB-INF/custom/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/custom/");
			
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		CustomDialplan app = new CustomDialplan();
		try {
			em.getTransaction().begin();
			app.setName(request.getParameter("name").replace(' ', '-'));
			app.setDialplan(request.getParameter("dialplan"));
			em.persist(app);
			em.getTransaction().commit();
			request.setAttribute("info", "Custom application " + app.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/custom/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("app", app);
			request.getRequestDispatcher("/WEB-INF/custom/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		CustomDialplan app = new CustomDialplan();
		
		try {
			em.getTransaction().begin();
			app.setId(Long.parseLong(request.getParameter("id")));
			app.setVersion(Long.parseLong(request.getParameter("version")));
			app.setName(request.getParameter("name").replace(' ', '-'));
			app.setDialplan(request.getParameter("dialplan"));
			em.merge(app);
			em.getTransaction().commit();
			request.setAttribute("info", "Dialplan " + app.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/custom/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("app", app);
			request.getRequestDispatcher("/WEB-INF/custom/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			CustomDialplan app = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("id")));
			em.remove(app);
			em.getTransaction().commit();
			request.setAttribute("info", "Dialplan " + app.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/custom/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/custom/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(a.id) from CustomDialplan a").getSingleResult();
			Query q = em.createQuery("from CustomDialplan a order by a.name asc");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<CustomDialplan> apps = q.getResultList();
			request.setAttribute("apps", apps);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/custom/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<CustomDialplan> apps = new ArrayList<CustomDialplan>();
			request.setAttribute("apps", apps);
			request.getRequestDispatcher("/WEB-INF/custom/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
