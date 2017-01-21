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
import org.kisspbx.model.OutboundRoute;
import org.kisspbx.model.provider.Provider;

@Startup
@WebServlet(name="outboundrouteController", 
			urlPatterns={"/outbound/",
					"/outbound/create", 
					"/outbound/save", 
					"/outbound/edit", 
					"/outbound/update", 
					"/outbound/delete"})
public class OutboundRouteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public OutboundRouteController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/outbound/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/outbound/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/outbound/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/outbound/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/outbound/delete")) {
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
		OutboundRoute route = new OutboundRoute();
		route.setName(request.getParameter("name"));
		route.setDialpattern(request.getParameter("dialpattern"));
		request.setAttribute("route", route);
		
		EntityManager em = AppContextListener.getEntityManager();
		try {
			List<Provider> providers = em.createQuery("from Provider p order by p.name asc").getResultList();
			request.setAttribute("providers", providers);
			
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			
		} finally {
			em.close();
		}
		
		request.getRequestDispatcher("/WEB-INF/outbound/create.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			OutboundRoute r = em.find(OutboundRoute.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("route", r);
			List<Provider> providers = em.createQuery("from Provider p order by p.name asc").getResultList();
			request.setAttribute("providers", providers);
			request.getRequestDispatcher("/WEB-INF/outbound/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/outbound/");
		
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		OutboundRoute route = new OutboundRoute();
		try {
			em.getTransaction().begin();
			route.setName(request.getParameter("name"));
			route.setDialpattern(request.getParameter("dialpattern"));
			route.setPrefix(Integer.parseInt(request.getParameter("prefix")));
			route.setPrepend(request.getParameter("prepend"));
			try {
				Provider p = em.find(Provider.class, Long.parseLong(request.getParameter("provider")));
				route.setProvider(p);
				
			} catch(Exception ex) {}
			em.persist(route);
			em.getTransaction().commit();
			request.setAttribute("info", "Outbound route " + route.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/outbound/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("route", route);
			List<Provider> providers = em.createQuery("from Provider p order by p.name asc").getResultList();
			request.setAttribute("providers", providers);
			request.getRequestDispatcher("/WEB-INF/outbound/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		OutboundRoute route = new OutboundRoute();
		
		try {
			route.setId(Long.parseLong(request.getParameter("id")));
			route.setVersion(Long.parseLong(request.getParameter("version")));
			route.setName(request.getParameter("name"));
			route.setDialpattern(request.getParameter("dialpattern"));
			route.setPrefix(Integer.parseInt(request.getParameter("prefix")));
			route.setPrepend(request.getParameter("prepend"));
			try {
				Provider p = em.find(Provider.class, Long.parseLong(request.getParameter("provider")));
				route.setProvider(p);
				
			} catch(Exception ex) {}
			em.getTransaction().begin();
			em.merge(route);
			em.getTransaction().commit();
			request.setAttribute("info", "Outbound route " + route.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/outbound/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("route", route);
			List<Provider> providers = em.createQuery("from Provider p order by p.name asc").getResultList();
			request.setAttribute("providers", providers);
			request.getRequestDispatcher("/WEB-INF/outbount/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			OutboundRoute r = em.find(OutboundRoute.class, Long.parseLong(request.getParameter("id")));
			em.remove(r);
			em.getTransaction().commit();
			request.setAttribute("info", "Outbound route " + r.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/outbound/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/outbound/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(o.id) from OutboundRoute o").getSingleResult();
			Query q = em.createQuery("from OutboundRoute r order by r.name asc");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<OutboundRoute> routes = q.getResultList();
			request.setAttribute("routes", routes);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/outbound/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<OutboundRoute> routes = new ArrayList<OutboundRoute>();
			request.setAttribute("routes", routes);
			request.getRequestDispatcher("/WEB-INF/outbound/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
