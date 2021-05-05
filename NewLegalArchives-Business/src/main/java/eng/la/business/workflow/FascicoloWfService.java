package eng.la.business.workflow;

import eng.la.model.FascicoloWf;
import eng.la.model.view.FascicoloWfView;
import eng.la.model.view.IncaricoWfView;
import eng.la.model.view.UtenteView;

public interface FascicoloWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idFascicolo identificativo del fascicolo correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avviaWorkflow(long idFascicolo, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idFascicoloWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idFascicoloWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	
	/**
	 * fa avanzare un workflow 
	 *
	 * @param idFascicoloWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean chiudiFascicolo(long idFascicoloWf, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idFascicolo identificativo del fascicolo
	 * @return oggetto FascicoloWfView
	 */
	public FascicoloWfView leggiWorkflowInCorso(long idFascicolo) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto FascicoloWfView
	 */
	public FascicoloWfView leggiWorkflow(long id) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idFascicolo: identificativo del fascicolo
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	public FascicoloWfView leggiUltimoWorkflowTerminato(long idFascicolo) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idFascicolo: identificativo del fascicolo
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	public FascicoloWfView leggiUltimoWorkflowRifiutato(long idFascicolo) throws Throwable;

	/**
	 * Riporta in completato il fascicolo.
	 * @param idFascicolo identificativo del fascicolo
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	boolean riportaInCompletatoWorkflow(long idFascicolo, String userIdUtenteConnesso) throws Throwable;
}
