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
import org.kisspbx.model.provider.Provider;
import org.kisspbx.model.provider.SIPProvider;

@WebServlet(name="sipProviderController", 
			urlPatterns={"/provider/sip/",
					"/provider/sip/create", 
					"/provider/sip/save", 
					"/provider/sip/edit", 
					"/provider/sip/update", 
					"/provider/sip/delete"})
public class SIPProviderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SIPProviderController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getServletPath());
		
		try {
			if(request.getServletPath().equals("/provider/sip/create")) {
				processCreate(request, response);
				
			} else if(request.getServletPath().equals("/provider/sip/save")) {
				processSave(request, response);
				
			} else if(request.getServletPath().equals("/provider/sip/edit")) {
				processEdit(request, response);
				
			} else if(request.getServletPath().equals("/provider/sip/update")) {
				processUpdate(request, response);
			
			} else if(request.getServletPath().equals("/provider/sip/delete")) {
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
		request.getRequestDispatcher("/WEB-INF/provider/sip/create.jsp").forward(request, response);
	}
	
	private void processEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			SIPProvider p = em.find(SIPProvider.class, Long.parseLong(request.getParameter("id")));
			request.setAttribute("provider", p);
			request.getRequestDispatcher("/WEB-INF/provider/sip/edit.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/provider/sip/");
		
		} finally {
			em.close();
		}
	}
	
	private void processSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		SIPProvider p = new SIPProvider();
		try {
			em.getTransaction().begin();
			p.setName(request.getParameter("name").replace(' ', '-'));
			p.setDid(request.getParameter("did"));
			p.setHost(request.getParameter("host"));
			p.setUsername(request.getParameter("username"));
			p.setPassword(request.getParameter("password"));
			p.setParameters(request.getParameter("parameters"));
			p.setRegister("true".equals(request.getParameter("register")));
			p.setInsecure("true".equals(request.getParameter("insecure")));
			em.persist(p);
			em.getTransaction().commit();
			request.setAttribute("info", "Provider " + p.getId() + " created");
			response.sendRedirect(request.getContextPath() + "/provider/sip/");
			
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			request.setAttribute("error", e.getMessage());
			request.setAttribute("provider", p);
			request.getRequestDispatcher("/WEB-INF/provider/sip/create.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		SIPProvider p = null;
		try {
			em.getTransaction().begin();
			p = em.find(SIPProvider.class, Long.parseLong(request.getParameter("id")));
			p.setName(request.getParameter("name").replace(' ', '-'));
			p.setDid(request.getParameter("did"));
			p.setHost(request.getParameter("host"));
			p.setUsername(request.getParameter("username"));
			p.setPassword(request.getParameter("password"));
			p.setParameters(request.getParameter("parameters"));
			p.setRegister("true".equals(request.getParameter("register")));
			p.setInsecure("true".equals(request.getParameter("insecure")));
			em.merge(p);
			em.getTransaction().commit();
			request.setAttribute("info", "Trunk " + p.getId() + " updated");
			response.sendRedirect(request.getContextPath() + "/provider/sip/");
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			request.setAttribute("error", ex.getMessage());
			request.setAttribute("provider", p);
			request.getRequestDispatcher("/WEB-INF/provider/sip/edit.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	private void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		try {
			em.getTransaction().begin();
			Provider t = em.find(Provider.class, Long.parseLong(request.getParameter("id")));
			em.remove(t);
			em.getTransaction().commit();
			request.setAttribute("info", "Provider " + t.getId() + " deleted");
			response.sendRedirect(request.getContextPath() + "/provider/sip/");
			
		} catch (PersistenceException e) {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			request.setAttribute("error", (e.getCause() != null)?e.getCause().toString():e.getMessage());
			request.getRequestDispatcher("/WEB-INF/provider/sip/").forward(request, response);
			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(p.id) from SIPProvider p").getSingleResult();
			Query q = em.createQuery("from SIPProvider p order by p.name asc");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<Provider> trunks = q.getResultList();
			request.setAttribute("providers", trunks);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/provider/sip/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<Provider> trunks = new ArrayList<Provider>();
			request.setAttribute("trunks", trunks);
			request.getRequestDispatcher("/WEB-INF/provider/sip/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
