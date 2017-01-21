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
import org.kisspbx.model.ManagerApiUser;

@Startup
@WebServlet(name="managerController", 
			urlPatterns={"/manager/", 
					"/manager/show", 
					"/manager/create", 
					"/manager/save", 
					"/manager/edit", 
					"/manager/update", 
					"/manager/delete"})
public class ManagerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/manager/show")) {
				processShow(request, response);
				
			} else if(request.getServletPath().equals("/manager/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/manager/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/manager/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/manager/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/manager/delete")) {
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
		ManagerApiUser u = new ManagerApiUser();
		try {
			u = em.find(ManagerApiUser.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("user", u);
			request.getRequestDispatcher("/WEB-INF/manager/show.jsp").forward(request, response);
				
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/manager/");
			
		} finally {
			em.close();
		}
	}
	
	private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.getRequestDispatcher("/WEB-INF/manager/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			ManagerApiUser u = em.find(ManagerApiUser.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("user", u);
			request.getRequestDispatcher("/WEB-INF/manager/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/manager/show?id=" + request.getParameter("id"));
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		ManagerApiUser u = new ManagerApiUser();
		try {
			em.getTransaction().begin();
			u.setName(request.getParameter("name"));
			u.setPassword(request.getParameter("password"));
			u.setReadPermission(request.getParameter("readPermission"));
			u.setWritePermission(request.getParameter("writePermission"));
			u.setDeny(request.getParameter("deny"));
			u.setPermit(request.getParameter("permit"));
			u.setParameters(request.getParameter("parameters"));
			em.persist(u);
			em.getTransaction().commit();
			request.setAttribute("info", "ManagerUser " + u.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/manager/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("user", u);
			request.getRequestDispatcher("/WEB-INF/manager/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		ManagerApiUser u = new ManagerApiUser();
		
		try {
			u.setId(Long.parseLong(request.getParameter("id")));
			u.setVersion(Long.parseLong(request.getParameter("version")));
			u.setName(request.getParameter("name"));
			u.setPassword(request.getParameter("password"));
			u.setReadPermission(request.getParameter("readPermission"));
			u.setWritePermission(request.getParameter("writePermission"));
			u.setDeny(request.getParameter("deny"));
			u.setPermit(request.getParameter("permit"));
			u.setParameters(request.getParameter("parameters"));
			em.getTransaction().begin();
			em.merge(u);
			em.getTransaction().commit();
			request.setAttribute("info", "ManagerUser " + u.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/manager/show?id=" + u.getId());
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("user", u);
			request.getRequestDispatcher("/WEB-INF/manager/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			ManagerApiUser u = em.find(ManagerApiUser.class, Long.parseLong(request.getParameter("id")));
			em.remove(u);
			em.getTransaction().commit();
			request.setAttribute("info", "ManagerUser " + u.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/manager/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/manager/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(u.id) from ManagerApiUser u").getSingleResult();
			Query q = em.createQuery("from ManagerApiUser u");
			int max = 12;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<ManagerApiUser> users = q.getResultList();
			request.setAttribute("users", users);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/manager/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<ManagerApiUser> users = new ArrayList<ManagerApiUser>();
			request.setAttribute("users", users);
			request.getRequestDispatcher("/WEB-INF/manager/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
