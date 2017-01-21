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
import org.kisspbx.model.HuntGroup;

@WebServlet(name="huntgroupController", 
			urlPatterns={"/feature/huntgroup/",
					"/feature/huntgroup/create", 
					"/feature/huntgroup/save", 
					"/feature/huntgroup/edit", 
					"/feature/huntgroup/update", 
					"/feature/huntgroup/delete"})
public class HuntGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public HuntGroupController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/feature/huntgroup/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/feature/huntgroup/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/feature/huntgroup/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/feature/huntgroup/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/feature/huntgroup/delete")) {
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
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc", Extension.class).getResultList();  
			request.setAttribute("extensions", extensions);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/create.jsp").forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/huntgroup/");
		
		} finally {
			em.close();
		}
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("huntgroup", hg);
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc", Extension.class).getResultList();
			request.setAttribute("extensions", extensions);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/edit.jsp").forward(request, response);
			
		} catch (Exception ex) {
			request.setAttribute("error", ex.getMessage());
			response.sendRedirect(request.getContextPath() + "/feature/huntgroup/");
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		HuntGroup hg = new HuntGroup();
		try {
			em.getTransaction().begin();
			hg.setName(request.getParameter("name"));
			hg.setDescription(request.getParameter("description"));
			hg.setStrategy(request.getParameter("strategy"));
			hg.setDialtime(Integer.parseInt(request.getParameter("dialtime")));
			hg.setTimeout(Integer.parseInt(request.getParameter("timeout")));
			hg.setTimes(Integer.parseInt(request.getParameter("times")));
			if (!"".equals(request.getParameter("priorityExtension")))
				hg.setPriorityExtension(em.find(Extension.class, Long.parseLong(request.getParameter("priorityExtension"))));
			if (!"".equals(request.getParameter("voicemailExtension")))
				hg.setVoicemailExtension(em.find(Extension.class, Long.parseLong(request.getParameter("voicemailExtension"))));
			hg.getExtensionGroup().clear();
			String [] group = request.getParameterValues("extensionGroup");
			for (String selected : group) {
				Extension e = em.find(Extension.class, Long.parseLong(selected));
				hg.getExtensionGroup().add(e);
			}
			
			em.persist(hg);
			em.getTransaction().commit();
			request.setAttribute("info", "Huntgroup " + hg.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/feature/huntgroup/");
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			request.setAttribute("error", ex.getMessage());
			request.setAttribute("huntgroup", hg);
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc", Extension.class).getResultList();
			request.setAttribute("extensions", extensions);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		HuntGroup hg = null;
		try {
			em.getTransaction().begin();
			hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("id")));
			hg.setVersion(Long.parseLong(request.getParameter("version")));
			hg.setName(request.getParameter("name"));
			hg.setDescription(request.getParameter("description"));
			hg.setStrategy(request.getParameter("strategy"));
			hg.setDialtime(Integer.parseInt(request.getParameter("dialtime")));
			hg.setTimeout(Integer.parseInt(request.getParameter("timeout")));
			hg.setTimes(Integer.parseInt(request.getParameter("times")));
			if (!"".equals(request.getParameter("priorityExtension")))
				hg.setPriorityExtension(em.find(Extension.class, Long.parseLong(request.getParameter("priorityExtension"))));
			else
				hg.setPriorityExtension(null);
			if (!"".equals(request.getParameter("voicemailExtension")))
				hg.setVoicemailExtension(em.find(Extension.class, Long.parseLong(request.getParameter("voicemailExtension"))));
			else
				hg.setVoicemailExtension(null);
			hg.getExtensionGroup().clear();
			String [] group = request.getParameterValues("extensionGroup");
			for (String selected : group) {
				Extension e = em.find(Extension.class, Long.parseLong(selected));
				hg.getExtensionGroup().add(e);
			}
			
			em.merge(hg);
			em.getTransaction().commit();
			request.setAttribute("info", "Huntgroup " + hg.getId() + " edited");
			response.sendRedirect(request.getContextPath() + "/feature/huntgroup/");
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			request.setAttribute("error", ex.getMessage());
			request.setAttribute("huntgroup", hg);
			List<Extension> extensions = em.createQuery("from Extension e order by e.extension asc", Extension.class).getResultList();
			request.setAttribute("extensions", extensions);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			HuntGroup hg = em.find(HuntGroup.class, Long.parseLong(request.getParameter("id")));
			em.remove(hg);
			em.getTransaction().commit();
			request.setAttribute("info", "Huntgroup " + hg.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/feature/huntgroup/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(h.id) from HuntGroup h").getSingleResult();
			Query q = em.createQuery("from HuntGroup h order by h.name asc", HuntGroup.class);
			int max = 12;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<HuntGroup> huntgroups = q.getResultList();
			request.setAttribute("huntgroups", huntgroups);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/index.jsp").forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			List<HuntGroup> huntgroups = new ArrayList<HuntGroup>();
			request.setAttribute("huntgroups", huntgroups);
			request.getRequestDispatcher("/WEB-INF/feature/huntgroup/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
