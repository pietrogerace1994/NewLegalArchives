package eng.la.business.mail;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.Utente;
import eng.la.model.mail.Mail;
import eng.la.model.view.UtentePecView;

public interface EmailNotificationService {
	 public void inviaNotifica(String tipoNotifica, String tipoEntita, long idEntita, String lang, String userIdUtenteConnesso) throws Throwable;
	 
	 //DARIO C **************************************************************************************************************************************************************
	 public void inviaNotifica(String tipoNotifica, String tipoEntita, long idEntita, String lang, String userIdUtenteConnesso, String altro_responsabile) throws Throwable;
	 //**********************************************************************************************************************************************************************
	 
	 public void inviaNotificaInvioAltriUffici(long idEntita, String lang, String userIdUtenteConnesso,MultipartFile file ) throws Throwable;
	 public void inviaNotificaInvioAltriUfficiPec(UtentePecView utentePecView, String lang, String userIdUtenteConnesso, String utenteAltriUff, String emailAltriUff) throws Throwable;
	 public void inviaNotificaProfessionistaEsterno(long idEntita, String lang, String userIdUtenteConnesso ) throws Throwable;
	 public void inviaNotificaAmministrativoSocieta(long idEntita, String lang, String userIdUtenteConnesso) throws Throwable;
	 public void inviaSollecitoVotazioni(List<String> emailVotanti, String lang, String userIdUtenteConnesso)throws Throwable;
	 public void inviaNotificaBonifica(long idEntita) throws Throwable;
	 public void inviaNotificaPecOperatoriSegreteria(long idEntita, String lang, String userIdUtenteConnesso ) throws Throwable;
	 public void inviaNotificaProtocollo() throws Throwable;
	 public void inviaNotificaCambioOwner(Long idFascicolo, Utente utenteOld, Utente utenteNuovo) throws Throwable;
	 public void inviaNotificaCambioOwner(Mail mail, Utente utenteOld, Utente utenteNuovo) throws Throwable;
	 public void inviaNotificaBCVincitoreSelezionato(String lang, long idEntita) throws Throwable;
}
