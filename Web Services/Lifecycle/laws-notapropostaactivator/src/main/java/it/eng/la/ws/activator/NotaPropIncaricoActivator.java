package it.eng.la.ws.activator;

import it.eng.la.ws.senderservice.NotaPropIncaricoSenderService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class NotaPropIncaricoActivator extends HttpServlet {

	protected final static Logger GENERAL_LOGGER = Logger.getLogger(NotaPropIncaricoActivator.class.getName());
	
	@EJB
	NotaPropIncaricoSenderService sender;

	@Override
	public void init() throws ServletException {
		GENERAL_LOGGER.debug("NotaPropIncaricoActivator ATTIVA LA PRIMA SCHEDULAZIONE DEL TIMER!");
		super.init();
		sender.init();
	}
}