package it.eng.la.ws.service;

import it.eng.laws.entity.ContabilizzazioneEntity;
import it.eng.laws.entity.Fattura;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.mail.PasswordAuthentication;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless
public class LucyStarServiceBean implements LucyStarService{
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
	
	@PersistenceContext(unitName="La-PU")
	EntityManager emLa;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(LucyStarServiceBean.class.getName());
		
	@SuppressWarnings("unchecked")
	@Override
	public String insert(ContabilizzazioneEntity c) {
		GENERAL_LOGGER.debug("START Metodo insert");
		String returnMessage = "";
		Fattura fattura = null;

		try {
			List<Fattura> listfattura = emLa.createNamedQuery("Fattura.findByNumCode").
					setHint("javax.persistence.cache.retrieveMode", "BYPASS").
					setParameter("numeroFattura", c.getNumeroFattura()).
					setParameter("codiceSap", c.getCodiceFornitore()).getResultList();
			
			if(listfattura!=null && listfattura.size()>0){
				fattura = listfattura.get(0);
			} else {
				GENERAL_LOGGER.debug("Fattura non presente in Legal Archives.");
				return "Fattura non presente in Legal Archives.\n\n";
			}
			
		} catch (Exception e) {
			printExec(e);
			GENERAL_LOGGER.debug("Fattura non presente in Legal Archives.");
			return "Fattura non presente in Legal Archives.\n\n";
		}
		
		try {
			fattura.setnProtocolloFiscale(new BigDecimal(c.getNumeroProtFiscale()));
		} catch (Exception e) {
			printExec(e);
			GENERAL_LOGGER.debug("Numero di Protocollo Fiscale inserito in un formato incorretto.");
			return "Numero di Protocollo Fiscale inserito in un formato incorretto.\n\n";
		}
		
		try {
			fattura.setDataRegistrazione(getDateTime(c.getDataRegistrazione()));
		} catch (ParseException e) {
			printExec(e);
			GENERAL_LOGGER.debug("Data di Registrazione inserita in un formato incorretto.");
			return "Data di Registrazione inserita in un formato incorretto.\n\n";
		}
		
		try {
			emLa.persist(fattura);
		} catch (Exception e) {
			printExec(e);
			GENERAL_LOGGER.debug("Impossibile effettuare l'aggiornamento di questa fattura.");
			return "Impossibile effettuare l'aggiornamento di questa fattura.\n\n";
		}
		
		GENERAL_LOGGER.debug("END Metodo insert");
		return returnMessage;
	}
	
    public static Date getDateTime(String date) throws ParseException {
        return new Date(dateTimeFormat.parse(date).getTime());
    }
	
    public static void printExec(Exception e){
    	StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		GENERAL_LOGGER.error("Exception: "+exceptionAsString);
    }
    
    private static class Authenticator extends javax.mail.Authenticator {
        private PasswordAuthentication authentication;

        public Authenticator(String username, String password) {
                authentication = new PasswordAuthentication(username, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
        }
    }

}
