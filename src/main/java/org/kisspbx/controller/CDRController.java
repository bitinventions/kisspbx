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
import org.kisspbx.model.CDR;

@WebServlet(name="CDRController", urlPatterns={"/cdr/"})
public class CDRController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CDRController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processList(request, response);
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
	private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		EntityManager em = AppContextListener.getEntityManager();
		
		try {
			long total = (long) em.createQuery("select count(c.id) from CDR c").getSingleResult();
			Query q = em.createQuery("from CDR c order by c.calldate desc");
			int max = 25;
			if (request.getParameter("max") != null)
				max = Integer.parseInt(request.getParameter("max"));
			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			q.setMaxResults(max);
			q.setFirstResult((page-1)*max);
			
			List<CDR> cdrs = q.getResultList();
			request.setAttribute("cdrs", cdrs);
			request.setAttribute("total", total);
			request.setAttribute("max", max);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/cdr/index.jsp").forward(request, response);
		
		} catch (PersistenceException e) {
			request.setAttribute("error", e.getMessage());
			List<CDR> cdrs = new ArrayList<CDR>();
			request.setAttribute("cdrs", cdrs);
			request.getRequestDispatcher("/WEB-INF/cdr/index.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
