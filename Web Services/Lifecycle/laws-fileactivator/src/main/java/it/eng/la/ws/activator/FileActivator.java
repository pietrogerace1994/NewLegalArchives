package it.eng.la.ws.activator;

import it.eng.la.ws.senderservice.FileSenderService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class FileActivator extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final static Logger GENERAL_LOGGER = Logger.getLogger(FileActivator.class.getName());
	
	@EJB
	FileSenderService sender;

	@Override
	public void init() throws ServletException 
	{
		GENERAL_LOGGER.debug("FileActivator ATTIVA LA PRIMA SCHEDULAZIONE DEL TIMER!");
		super.init();
		sender.init();
	}
}