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
import org.kisspbx.model.Destination;
import org.kisspbx.model.DestinationType;
import org.kisspbx.model.HuntGroup;
import org.kisspbx.model.app.Application;
import org.kisspbx.model.app.CustomApplication;
import org.kisspbx.model.app.HuntgroupApplication;
import org.kisspbx.model.app.QueueApplication;
import org.kisspbx.model.feature.Queue;

@WebServlet(name="applicationController", 
			urlPatterns={"/app/",
					"/app/create", 
					"/app/save", 
					"/app/edit", 
					"/app/update", 
					"/app/delete"})
public class ApplicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ApplicationController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/app/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/app/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/app/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/app/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/app/delete")) {
				processDelete(request, response);
				
			} else {
				processList(request, response);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void processCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Application app = null;
			String type = request.getParameter("type"); 
			if ("queue".equals(type)) {
				app = new QueueApplication();
				List<Queue> queues = em.createQuery("from Queue q order by q.name asc", Queue.class).getResultList();
				request.setAttribute("queues", queues);
				if (request.getParameter("queue") != null) {
					Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("queue")));
					((QueueApplication)app).setQueue(q);
				}
			} else if ("huntgroup".equals(type)) {
				app = new HuntgroupApplication();
				List<HuntGroup> huntgroups = em.createQuery("from HuntGroup hg order by hg.name asc", HuntGroup.class).getResultList();
				request.setAttribute("huntgroups", huntgroups);
				if (request.getParameter("huntgroup") != null) {
					HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("huntgroup")));
					((HuntgroupApplication)app).setHuntgroup(hg);
				}
			} else {
				app = new CustomApplication();
				app.setExtension(request.getParameter("extension"));
				app.setPriority(request.getParameter("priority"));
				if (request.getParameter("dialplan") != null) {
					CustomDialplan d = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("dialplan")));
					((CustomApplication)app).setDialplan(d);
				}
				List<CustomDialplan> dialplans = em.createQuery("from CustomDialplan d order by d.name asc", CustomDialplan.class).getResultList();
				request.setAttribute("dialplans", dialplans);
			}
			app.setPattern(request.getParameter("pattern"));
			app.setDescription(request.getParameter("description"));
			app.setInternalOnly(Boolean.parseBoolean(request.getParameter("internalOnly")));
			app.setVariables(request.getParameter("variables"));
			if (request.getParameter("finalDestinationType") != null) {
				try {
					app.setFinalDestinationType(DestinationType.valueOf(request.getParameter("finalDestinationType")));
					request.setAttribute("destinationCandidates", getDestinations(app.getFinalDestinationType(), em));
					
				} catch (IllegalArgumentException e) { 
					e.printStackTrace();
				}
				
			}
			request.setAttribute("type", type);
			request.setAttribute("app", app);
			request.getRequestDispatcher("/WEB-INF/app/create.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/app/");
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			Application app = em.find(Application.class, Long.parseLong(request.getParameter("id")));
			String version = request.getParameter("version");
			if (app instanceof QueueApplication) {
				request.setAttribute("type", "queue");
				List<Queue> queues = em.createQuery("from Queue q order by q.name asc").getResultList();
				request.setAttribute("queues", queues);
				if (version != null && request.getParameter("queue") != null) {
					Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("queue")));
					((QueueApplication)app).setQueue(q);
				}
			
			} else if (app instanceof HuntgroupApplication) {
				request.setAttribute("type", "huntgroup");
				List<HuntGroup> hungroups = em.createQuery("from HuntGroup hg order by hg.name asc").getResultList();
				request.setAttribute("hungroups", hungroups);
				if (version != null && request.getParameter("hungroup") != null) {
					HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("hungroup")));
					((HuntgroupApplication)app).setHuntgroup(hg);
				}
			
			} else {
				request.setAttribute("type", "custom");
				List<CustomDialplan> dialplans = em.createQuery("from CustomDialplan d order by d.name asc").getResultList();
				request.setAttribute("dialplans", dialplans);
				if (version != null) {
					app.setExtension(request.getParameter("extension"));
					app.setPriority(request.getParameter("priority"));
					if(request.getParameter("dialplan") != null) {
						CustomDialplan d = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("dialplan")));
						((CustomApplication)app).setDialplan(d);
					}
				}
			}
			if (version != null) {
				app.setPattern(request.getParameter("pattern"));
				app.setDescription(request.getParameter("description"));
				app.setInternalOnly(Boolean.parseBoolean(request.getParameter("internalOnly")));
				app.setVariables(request.getParameter("variables"));
				if (request.getParameter("finalDestinationType") != null) {
					try {
						app.setFinalDestinationType(DestinationType.valueOf(request.getParameter("finalDestinationType")));
						
					} catch (IllegalArgumentException e) { 
						e.printStackTrace();
					}
				}
			}
			if (app.getFinalDestinationType() != null)
				request.setAttribute("destinationCandidates", getDestinations(app.getFinalDestinationType(), em));
			request.setAttribute("app", app);
			request.getRequestDispatcher("/WEB-INF/app/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/app/");
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		String type = request.getParameter("type");
		Application app = null;
		try {
			em.getTransaction().begin();
			if ("queue".equals(type)) {
				app = new QueueApplication();
				Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("queue")));
				((QueueApplication)app).setQueue(q);
				
			} else if ("huntgroup".equals(type)) {
				app = new HuntgroupApplication();
				HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("huntgroup")));
				((HuntgroupApplication)app).setHuntgroup(hg);
				
			} else {
				app = new CustomApplication();
				app.setExtension(request.getParameter("extension"));
				app.setPriority(request.getParameter("priority"));
				CustomDialplan d = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("dialplan")));
				((CustomApplication)app).setDialplan(d);
			}
			app.setPattern(request.getParameter("pattern"));
			app.setDescription(request.getParameter("description"));
			app.setInternalOnly(Boolean.parseBoolean(request.getParameter("internalOnly")));
			app.setVariables(request.getParameter("variables"));
			try {
				app.setFinalDestinationType(DestinationType.valueOf(request.getParameter("finalDestinationType")));
			} catch(Exception e) {
				app.setFinalDestinationType(null);
			}
			app.setFinalDestination(request.getParameter("finalDestination"));
			
			em.persist(app);
			em.getTransaction().commit();
			request.setAttribute("info", "Application " + app.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/app/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("type", type);
			request.setAttribute("app", app);
			if ("queue".equals(type)) {
				List<Queue> queues = em.createQuery("from Queue q order by q.name asc").getResultList();
				request.setAttribute("queues", queues);
			} else if ("huntgroup".equals(type)) {
				List<HuntGroup> huntgroups = em.createQuery("from HuntGroup hg order by hg.name asc").getResultList();
				request.setAttribute("huntgroups", huntgroups);
			} else {
				List<CustomDialplan> dialplans = em.createQuery("from CustomDialplan d order by d.name asc").getResultList();
				request.setAttribute("dialplans", dialplans);
			}
			request.getRequestDispatcher("/WEB-INF/app/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		String type = request.getParameter("type");
		Application app = null;
		try {
			em.getTransaction().begin();
			app = em.find(Application.class, Long.parseLong(request.getParameter("id")));
			app.setVersion(Long.parseLong(request.getParameter("version")));
			app.setPattern(request.getParameter("pattern"));
			app.setDescription(request.getParameter("description"));
			app.setInternalOnly(Boolean.parseBoolean(request.getParameter("internalOnly")));
			app.setVariables(request.getParameter("variables"));
			
			if ("queue".equals(type)) {
				Queue q = em.find(Queue.class, Long.parseLong(request.getParameter("queue")));
				((QueueApplication)app).setQueue(q);
			} else if ("huntgroup".equals(type)) {
				HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("huntgroup")));
				((HuntgroupApplication)app).setHuntgroup(hg);
			} else {
				app.setExtension(request.getParameter("extension"));
				app.setPriority(request.getParameter("priority"));
				try {
					CustomDialplan d = em.find(CustomDialplan.class, Long.parseLong(request.getParameter("dialplan")));
					((CustomApplication)app).setDialplan(d);
				} catch(Exception e) {}
			}
			try {
				app.setFinalDestinationType(DestinationType.valueOf(request.getParameter("finalDestinationType")));
			} catch(Exception e) {
				app.setFinalDestinationType(null);
			}
			app.setFinalDestination(request.getParameter("finalDestination"));
			
			em.merge(app);
			em.getTransaction().commit();
			request.setAttribute("info", "Application " + app.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/app/");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("type", type);
			request.setAttribute("app", app);
			if ("queue".equals(type)) {
				List<Queue> queues = em.createQuery("from Queue q order by q.name asc").getResultList();
				request.setAttribute("queues", queues);
			} else if ("huntgroup".equals(type)) {
				List<HuntGroup> huntgroups = em.createQuery("from HuntGroup hg order by hg.name asc").getResultList();
				request.setAttribute("huntgroups", huntgroups);
			} else {
				List<CustomDialplan> dialplans = em.createQuery("from CustomDialplan d where d.type = 'CONTEXT' order by d.name asc").getResultList();
				request.setAttribute("dialplans", dialplans);
			}
			request.getRequestDispatcher("/WEB-INF/app/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Application a = em.find(Application.class, Long.parseLong(request.getParameter("id")));
			em.remove(a);
			em.getTransaction().commit();
			request.setAttribute("info", "Application " + a.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/app/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/app/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(a.id) from Application a").getSingleResult();
			Query q = em.createQuery("from Application a order by a.pattern asc", Application.class);
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Application> apps = q.getResultList();
			request.setAttribute("apps", apps);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/app/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<Application> apps = new ArrayList<Application>();
			request.setAttribute("apps", apps);
			request.getRequestDispatcher("/WEB-INF/app/index.jsp").forward(request, response);
			
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
				return em.createQuery("from Application a order by a.pattern asc").getResultList();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
