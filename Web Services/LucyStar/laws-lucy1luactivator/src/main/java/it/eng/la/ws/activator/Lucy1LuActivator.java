package it.eng.la.ws.activator;

import it.eng.la.ws.senderservice.Lucy1LuSenderService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class Lucy1LuActivator extends HttpServlet {

	protected final static Logger GENERAL_LOGGER = Logger.getLogger(Lucy1LuActivator.class.getName());
	
	@EJB
	Lucy1LuSenderService sender;

	@Override
	public void init() throws ServletException {
		GENERAL_LOGGER.debug("Lucy1LuActivator ATTIVA LA PRIMA SCHEDULAZIONE DEL TIMER!");
		
		super.init();
		sender.init();
	}
}