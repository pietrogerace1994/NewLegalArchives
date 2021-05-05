package it.eng.la.ws.activator;

import it.eng.la.ws.senderservice.SchedaValutazioneSenderService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class SchedaValutazioneActivator extends HttpServlet {

	protected final static Logger GENERAL_LOGGER = Logger.getLogger(SchedaValutazioneActivator.class.getName());

	@EJB
	SchedaValutazioneSenderService sender;

	@Override
	public void init() throws ServletException {
		GENERAL_LOGGER.debug("SchedaValutazioneActivator ATTIVA LA PRIMA SCHEDULAZIONE DEL TIMER!");
		super.init();
		sender.init();
	}
}