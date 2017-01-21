package org.kisspbx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.kisspbx.model.Destination;
import org.kisspbx.model.DestinationType;
import org.kisspbx.model.InboundRoute;

@Startup
@WebServlet(name="inboundrouteController", 
			urlPatterns={"/inbound/",
					"/inbound/create", 
					"/inbound/save", 
					"/inbound/edit", 
					"/inbound/update", 
					"/inbound/delete"})
public class InboundRouteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public InboundRouteController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/inbound/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/inbound/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/inbound/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/inbound/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/inbound/delete")) {
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
		InboundRoute route = new InboundRoute();
		route.setName(request.getParameter("name"));
		route.setDdi(request.getParameter("ddi"));
		route.setCli(request.getParameter("cli"));
		if (request.getParameter("destinationType") != null) {
			try {
				route.setDestinationType(DestinationType.valueOf(request.getParameter("destinationType")));
			} catch (IllegalArgumentException e) { }
		}
		
		request.setAttribute("route", route);
		
		if (route.getDestinationType() != null) {
			EntityManager em = AppContextListener.getEntityManager();
			try {
				request.setAttribute("destinationCandidates", getDestinations(route.getDestinationType(), em));
			
			} catch (PersistenceException e) {
				request.setAttribute("error", e.getMessage());
				
			} finally {
				em.close();
			}
		}
		
		request.getRequestDispatcher("/WEB-INF/inbound/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			InboundRoute r = null;
			if (request.getParameter("version") == null) {
				r = em.find(InboundRoute.class, Long.parseLong(request.getParameter("id")));
			} else {
				r = new InboundRoute();
				r.setId(Long.parseLong(request.getParameter("id")));
				r.setVersion(Long.parseLong(request.getParameter("version")));
				r.setName(request.getParameter("name"));
				r.setDdi(request.getParameter("ddi"));
				r.setCli(request.getParameter("cli"));
				try {
					r.setDestinationType(DestinationType.valueOf(request.getParameter("destinationType")));
				} catch (Exception e) { }
				r.setDestination(request.getParameter("destination"));
			}
			request.setAttribute("route", r);
			if (r.getDestinationType() != null) {
				request.setAttribute("destinationCandidates", getDestinations(r.getDestinationType(), em));
			}
			request.getRequestDispatcher("/WEB-INF/inbound/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/inbound/");
			
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		InboundRoute route = new InboundRoute();
		try {
			em.getTransaction().begin();
			route.setName(request.getParameter("name"));
			route.setDdi(request.getParameter("ddi"));
			route.setCli(request.getParameter("cli"));
			try {
				route.setDestinationType(DestinationType.valueOf(request.getParameter("destinationType")));
			} catch (Exception e) { }
			route.setDestination(request.getParameter("destination"));
			em.persist(route);
			em.getTransaction().commit();
			request.setAttribute("info", "Inbound route " + route.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/inbound/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("route", route);
			if (route.getDestinationType() != null) 
				request.setAttribute("destinationCandidates", getDestinations(route.getDestinationType(), em)); 
			request.getRequestDispatcher("/WEB-INF/inbound/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		InboundRoute route = new InboundRoute();
		
		try {
			route.setId(Long.parseLong(request.getParameter("id")));
			route.setVersion(Long.parseLong(request.getParameter("version")));
			route.setName(request.getParameter("name"));
			route.setDdi(request.getParameter("ddi"));
			route.setCli(request.getParameter("cli"));
			try {
				route.setDestinationType(DestinationType.valueOf(request.getParameter("destinationType")));
			} catch (Exception e) { }
			route.setDestination(request.getParameter("destination"));
			em.getTransaction().begin();
			em.merge(route);
			em.getTransaction().commit();
			request.setAttribute("info", "Inbound route " + route.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/inbound/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("route", route);
			if (route.getDestinationType() != null)
				request.setAttribute("destinationCandidates", getDestinations(route.getDestinationType(), em));
			request.getRequestDispatcher("/WEB-INF/inbound/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			InboundRoute r = em.find(InboundRoute.class, Long.parseLong(request.getParameter("id")));
			em.remove(r);
			em.getTransaction().commit();
			request.setAttribute("info", "Inbound route " + r.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/inbound/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/inbound/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(a.id) from CustomDialplan a").getSingleResult();
			Query q = em.createQuery("from InboundRoute r");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<InboundRoute> routes = q.getResultList();
			request.setAttribute("routes", routes);
			request.setAttribute("destinations", checkBrokenDestinations(routes, em));
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/inbound/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<InboundRoute> routes = new ArrayList<InboundRoute>();
			request.setAttribute("routes", routes);
			request.setAttribute("destinations", checkBrokenDestinations(routes, em));
			request.getRequestDispatcher("/WEB-INF/inbound/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Destination> getDestinations(DestinationType type, EntityManager em) {
		try {
			if (type == DestinationType.EXTENSION) {
				return em.createQuery("from Extension e order by e.extension asc").getResultList();
			} else if (type == DestinationType.DIALPLAN) {
				return em.createQuery("from Application a where a.internalOnly = false order by a.pattern asc").getResultList();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Map<Long, Destination> checkBrokenDestinations(List<InboundRoute> routes, EntityManager em) {
		Map<Long, Destination> destinations = new HashMap<Long, Destination>();
		for (InboundRoute r : routes) {
			if (r.getDestinationType() == null || r.getDestination() == null) { 
				continue;
			}
			
			String q = "";
			if (r.getDestinationType() == DestinationType.EXTENSION)
				q = "from Extension e where e.extension = '" + r.getDestination() + "'";
			else if (r.getDestinationType() == DestinationType.DIALPLAN)
				q = "from Application a where a.internalOnly = false and a.pattern = '" + r.getDestination() + "'";
			
			try {
				Destination destination = (Destination)em.createQuery(q).getSingleResult(); 
				if (destination != null) 
					destinations.put(r.getId(), destination);
				
			} catch (Exception e) { }
		}
		return destinations;
	}
	
	/*private InboundDestination getRelatedDestination(InboundRoute route, EntityManager em) {
		if (route.getDestinationType() == null || route.getDestination() == null) 
			return null;
			
		String q = "";
		if (route.getDestinationType() == InboundType.EXTENSION)
			q = "from Extension e where e.extension = '" + route.getDestination() + "'";
		else if (route.getDestinationType() == InboundType.DIALPLAN)
			q = "from Application a where a.pattern = '" + route.getDestination() + "'";
		
		try {
			return (InboundDestination)em.createQuery(q).getSingleResult(); 
			
		} catch (Exception e) { }
		
		return null;
	}*/
}
