package org.kisspbx;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.CommandAction;
import org.asteriskjava.manager.response.CommandResponse;
import org.asteriskjava.manager.response.ManagerResponse;

public class AsteriskConnection {
	private ManagerConnectionFactory factory;
	private ManagerConnection conn;

	public AsteriskConnection(String host, String user, String pass) {
		factory = new ManagerConnectionFactory(host, user, pass);
	}
	
	public void login() throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {
		if (conn == null) conn = factory.createManagerConnection();
		conn.login();
	}
	
	public void logoff() {
		if (conn != null) conn.logoff();
	}
	
	public ManagerConnection getConnection() {
		return factory.createManagerConnection();
	}
	
	public void sendCommand(String command, StringBuffer res) throws InterruptedException {
		CommandAction action = new CommandAction();
		action.setActionId(""+System.currentTimeMillis());
		action.setCommand(command);
		
		try {
			ManagerResponse r = conn.sendAction(action, 2000);
			if (r != null) {
				CommandResponse cr = (CommandResponse)r;
				if (cr.getResponse().startsWith("Follows")) {
					for (String s : cr.getResult()) {
						res.append(s).append('\n');
					}
				} else {
					res.append(cr.getResponse());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
