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
import org.kisspbx.model.feature.Queue;
import org.kisspbx.model.feature.QueueStrategy;

/**
 * Servlet implementation class Template
 */
@Startup
@WebServlet(name="queueController", 
			urlPatterns={"/feature/queue/",
					"/feature/queue/create", 
					"/feature/queue/save", 
					"/feature/queue/edit", 
					"/feature/queue/update", 
					"/feature/queue/delete"})
public class QueueController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public QueueController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/feature/queue/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/feature/queue/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/feature/queue/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/feature/queue/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/feature/queue/delete")) {
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
		Queue q = new Queue();
		request.setAttribute("queue", q);
		request.getRequestDispatcher("/WEB-INF/feature/queue/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("queue", q);
			request.getRequestDispatcher("/WEB-INF/feature/queue/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/feature/queue/");
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Queue q = new Queue();
		try {
			em.getTransaction().begin();
			q.setName(request.getParameter("name").replace(' ', '-'));
			q.setDescription(request.getParameter("description"));
			try {
				q.setStrategy(QueueStrategy.valueOf(request.getParameter("strategy")));
			} catch (IllegalArgumentException e) { }
			q.setJoinempty(Boolean.parseBoolean(request.getParameter("joinempty")));
			q.setLeavewhenempty(Boolean.parseBoolean(request.getParameter("leavewhenempty")));
			q.setMaxlen(Integer.parseInt(request.getParameter("maxlen")));
			q.setTimeout(Integer.parseInt(request.getParameter("timeout")));
			q.setRetry(Integer.parseInt(request.getParameter("retry")));
			q.setWrapuptime(Integer.parseInt(request.getParameter("wrapuptime")));
			q.setMembers(request.getParameter("members"));
			q.setParameters(request.getParameter("parameters"));
			em.persist(q);
			em.getTransaction().commit();
			request.setAttribute("info", "Queue " + q.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/feature/queue/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("queue", q);
			request.getRequestDispatcher("/WEB-INF/feature/queue/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		Queue q = new Queue();
		
		try {
			em.getTransaction().begin();
			q.setId(Long.parseLong(request.getParameter("id")));
			q.setVersion(Long.parseLong(request.getParameter("version")));
			q.setName(request.getParameter("name").replace(' ', '-'));
			q.setDescription(request.getParameter("description"));
			try {
				q.setStrategy(QueueStrategy.valueOf(request.getParameter("strategy")));
			} catch (IllegalArgumentException e) { }
			q.setJoinempty(Boolean.parseBoolean(request.getParameter("joinempty")));
			q.setLeavewhenempty(Boolean.parseBoolean(request.getParameter("leavewhenempty")));
			q.setMaxlen(Integer.parseInt(request.getParameter("maxlen")));
			q.setTimeout(Integer.parseInt(request.getParameter("timeout")));
			q.setRetry(Integer.parseInt(request.getParameter("retry")));
			q.setWrapuptime(Integer.parseInt(request.getParameter("wrapuptime")));
			q.setMembers(request.getParameter("members"));
			q.setParameters(request.getParameter("parameters"));
			em.merge(q);
			em.getTransaction().commit();
			request.setAttribute("info", "Queue " + q.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/feature/queue/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("queue", q);
			request.getRequestDispatcher("/WEB-INF/feature/queue/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("id")));
			em.remove(q);
			em.getTransaction().commit();
			request.setAttribute("info", "Queue " + q.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/feature/queue/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/feature/queue/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(q.id) from Queue q").getSingleResult();
			Query q = em.createQuery("from Queue q order by q.name asc");
			int max = 12;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Queue> queues = q.getResultList();
			request.setAttribute("queues", queues);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/feature/queue/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<Queue> queues = new ArrayList<Queue>();
			request.setAttribute("queues", queues);
			request.getRequestDispatcher("/WEB-INF/feature/queue/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
