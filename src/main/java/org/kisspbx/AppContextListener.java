package org.kisspbx;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
 
@WebListener
public class AppContextListener implements ServletContextListener {
	
	private static EntityManagerFactory emf;
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	System.out.println("Initializing persistence unit");
        emf = Persistence.createEntityManagerFactory("persistenceUnit");    
   }
 
	@Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        emf.close();
    }
}