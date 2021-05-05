package eng.la.business.workflow;

import eng.la.model.view.AttoWfView;
import eng.la.model.view.UtenteView;

public interface AttoWfService {
	
	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idAtto identificativo dell'atto
	 * @param userIdUtenteAssegnatario userId dell'utente assegnatario
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avviaWorkflow(long idAtto, String matricolaUtenteAssegnatario, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Chiude il workflow e pone l'atto in stato registrato o inviato ad altri uffici
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param stateTo stato identificativo (registrato o inviato ad altri uffici)
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean chiudiAtto(long idAttoWf, String stateTo, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param matricolaUtenteAssegnatario matricola dell'utente assegnatario
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avanzaWorkflow(long idAttoWf, String matricolaUtenteAssegnatario, String userIdUtenteConnesso) throws Throwable;

	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param utenteConnesso utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idAttoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;

	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto FascicoloWfView
	 */
	public AttoWfView leggiWorkflowInCorso(long idAtto) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto AttoWfView
	 */
	public AttoWfView leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idAtto identificativo dell'atto
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idAtto, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idAtto identificativo dell'atto
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idAtto, String userIdUtenteConnesso) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idAtto: identificativo dell'atto
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	public AttoWfView leggiUltimoWorkflowTerminato(long idAtto) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idAtto: identificativo dell'atto
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	public AttoWfView leggiUltimoWorkflowRifiutato(long idAtto) throws Throwable;

}
