package it.eng.la.ws.service;

import it.eng.la.ws.entity.Item;
import it.eng.la.ws.entity.Utente;

import java.io.PrintWriter;
import java.io.StringWriter;
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
public class HrServiceBean implements HrService{
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
	
	@PersistenceContext(unitName="Hr-PU")
	EntityManager emHr;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(HrServiceBean.class.getName());
		
	@SuppressWarnings("unchecked")
	@Override
	public String insert(String dataAggiornamento, List<Item> dati) {
		GENERAL_LOGGER.debug("START Metodo insert");
		String returnMessage = "";
		
		GENERAL_LOGGER.debug("dataAggiornamento: "+dataAggiornamento);
		
		try {
			getDateTime(dataAggiornamento);
		} catch (ParseException e) {
			printExec(e);
			GENERAL_LOGGER.debug("Data di Aggiornamento inserita in un formato incorretto.");
			return "Data di Aggiornamento inserita in un formato incorretto.\n\n";
		}
		
		String matricolaUtil = "";
		String codiceSocietaAppart = "";
		String codiceSocietaUtil = "";
		String codiceUnitaAppart = "";
		String codiceUnitaUtil = "";
		String descrUnitaAppart = "";
		String descrUnitaUtil = "";
		String matricolaAppart = "";
		String matricolaRespUtil = "";
		String cognome = "";
		String nome = "";
		String cognomeRespServizio = "";
		String nomeRespServizio = "";
		String ragioneSocialeSocietaAppart = "";
		String ragioneSocialeSocietaUtil = "";
		String responsabileUtil = "";
		String userIdResponsabileUtil = "";
		String useridUtil = "";
		String dataCancellazione = "";
		
		for (Item item : dati) {
			String label = item.getLabel();
			String valore = item.getValore();
			
			if(label == null){
				GENERAL_LOGGER.debug("Inserire correttamente tutte le label.");
				return "Inserire correttamente tutte le label.\n\n";
			}
			
			if(label.equalsIgnoreCase("CID_SERVIZIO")){
				matricolaUtil = valore;
			} else if(label.equalsIgnoreCase("SOCIETA_APPARTENENZA")){
				codiceSocietaAppart = valore;
			} else if(label.equalsIgnoreCase("SOCIETA_UTILIZZO")){
				codiceSocietaUtil = valore;
			} else if(label.equalsIgnoreCase("CAMPO50")){
				codiceUnitaAppart = valore;
			} else if(label.equalsIgnoreCase("COD_UNITA")){
				codiceUnitaUtil = valore;
			} else if(label.equalsIgnoreCase("CAMPO9")){
				descrUnitaAppart = valore;
			} else if(label.equalsIgnoreCase("UNITA_IT")){
				descrUnitaUtil = valore;
			} else if(label.equalsIgnoreCase("CID_RUOLO")){
				matricolaAppart = valore;
			} else if(label.equalsIgnoreCase("CID_SERVIZIO_RESP")){
				matricolaRespUtil = valore;
			} else if(label.equalsIgnoreCase("COGNOME")){
				cognome = valore;
			} else if(label.equalsIgnoreCase("NOME")){
				nome = valore;
			} else if(label.equalsIgnoreCase("COGNOME_RESP_SERVIZIO")){
				cognomeRespServizio = valore;
			} else if(label.equalsIgnoreCase("NOME_RESP_SERVIZIO")){
				nomeRespServizio = valore;
			} else if(label.equalsIgnoreCase("CAMPO1")){
				ragioneSocialeSocietaAppart = valore;
			} else if(label.equalsIgnoreCase("CAMPO2")){
				ragioneSocialeSocietaUtil = valore;
			} else if(label.equalsIgnoreCase("MANAGER_FLAG")){
				responsabileUtil = valore;
			} else if(label.equalsIgnoreCase("UTENZA_RESP")){
				userIdResponsabileUtil = valore;
			} else if(label.equalsIgnoreCase("UTENZA")){
				useridUtil = valore;
			} else if(label.equalsIgnoreCase("CAMPO4")){
				dataCancellazione = valore;
			}
			
		}
		
		GENERAL_LOGGER.debug("matricolaUtil: "+matricolaUtil);
		GENERAL_LOGGER.debug("codiceSocietaAppart: "+codiceSocietaAppart);
		GENERAL_LOGGER.debug("codiceSocietaUtil: "+codiceSocietaUtil);
		GENERAL_LOGGER.debug("codiceUnitaAppart: "+codiceUnitaAppart);
		GENERAL_LOGGER.debug("codiceUnitaUtil: "+codiceUnitaUtil);
		GENERAL_LOGGER.debug("descrUnitaAppart: "+descrUnitaAppart);
		GENERAL_LOGGER.debug("descrUnitaUtil: "+descrUnitaUtil);
		GENERAL_LOGGER.debug("matricolaAppart: "+matricolaAppart);
		GENERAL_LOGGER.debug("matricolaRespUtil: "+matricolaRespUtil);
		GENERAL_LOGGER.debug("cognome: "+cognome);
		GENERAL_LOGGER.debug("nome: "+nome);
		GENERAL_LOGGER.debug("cognomeRespServizio: "+cognomeRespServizio);
		GENERAL_LOGGER.debug("nomeRespServizio: "+nomeRespServizio);
		GENERAL_LOGGER.debug("ragioneSocialeSocietaAppart: "+ragioneSocialeSocietaAppart);
		GENERAL_LOGGER.debug("ragioneSocialeSocietaUtil: "+ragioneSocialeSocietaUtil);
		GENERAL_LOGGER.debug("responsabileUtil: "+responsabileUtil);
		GENERAL_LOGGER.debug("userIdResponsabileUtil: "+userIdResponsabileUtil);
		GENERAL_LOGGER.debug("useridUtil: "+useridUtil);
		GENERAL_LOGGER.debug("dataCancellazione: "+dataCancellazione);
		
		Utente utente = emHr.find(Utente.class, matricolaUtil);
		if(utente == null){
			GENERAL_LOGGER.debug("Utente non trovato con la matricola inserita: "+matricolaUtil);
			return "Utente non trovato con la matricola inserita: "+matricolaUtil+"\n\n";
		} else {
			
			try {
				boolean flagNom = true;
				if(cognome.equalsIgnoreCase("") || nome.equalsIgnoreCase("")){
					flagNom = false;
				}
				
				boolean flagNomResp = true;
				if(cognomeRespServizio.equalsIgnoreCase("") || nomeRespServizio.equalsIgnoreCase("")){
					flagNomResp = false;
				}
				
				utente.setCodiceSocietaAppart(codiceSocietaAppart);
				utente.setCodiceSocietaUtil(codiceSocietaUtil);
				utente.setCodiceUnitaAppart(codiceUnitaAppart);
				utente.setCodiceUnitaUtil(codiceUnitaUtil);
				utente.setDescrUnitaAppart(descrUnitaAppart);
				utente.setDescrUnitaUtil(descrUnitaUtil);
				utente.setMatricolaAppart(matricolaAppart);
				utente.setMatricolaRespUtil(matricolaRespUtil);
				utente.setNominativoAppart(flagNom?(cognome+", "+nome):"");
				utente.setNominativoRespUtil(flagNomResp?(cognomeRespServizio+", "+nomeRespServizio):"");
				utente.setNominativoUtil(flagNom?(cognome+", "+nome):"");
				utente.setRagioneSocialeSocietaAppart(ragioneSocialeSocietaAppart);
				utente.setRagioneSocialeSocietaUtil(ragioneSocialeSocietaUtil);
				utente.setResponsabileUtil(responsabileUtil);
				utente.setUserIdResponsabileUtil(userIdResponsabileUtil);
				utente.setUseridUtil(useridUtil);
				
				if(!dataCancellazione.equalsIgnoreCase("")){
				utente.setDataCancellazione(getDateTime(dataCancellazione));
				} else {
					utente.setDataCancellazione(null);
				}
				
				emHr.persist(utente);
				
				GENERAL_LOGGER.debug("Aggiornamento effettuato per la matricola: "+matricolaUtil);
				
			} catch (ParseException e) {
				printExec(e);
				GENERAL_LOGGER.debug("Data di Cancellazione inserita in un formato incorretto.");
				return "Data di Cancellazione inserita in un formato incorretto.\n\n";
			} catch (Exception e) {
				printExec(e);
				GENERAL_LOGGER.debug("Impossibile aggiornare l'Utente.");
				return "Impossibile aggiornare l'Utente.\n\n";
			}
		
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
