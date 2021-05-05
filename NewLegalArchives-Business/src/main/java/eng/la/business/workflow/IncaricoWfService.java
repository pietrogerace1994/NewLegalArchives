package eng.la.business.workflow;

import eng.la.model.IncaricoWf;
import eng.la.model.view.IncaricoWfView;

public interface IncaricoWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idIncarico identificativo dell'incarico correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	
	public boolean avviaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable;
	//DARIO C **********************************************************************************************************
	public boolean avviaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale,String matricolaDestinatario) throws Throwable;
	//*****************************************************************************************************************
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idIncaricoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idIncaricoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idIncaricoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	
	public boolean avanzaWorkflow(long idIncaricoWf, String userIdUtenteConnesso) throws Throwable;
	//DARIO C ********************************************************************************************************************
	public boolean avanzaWorkflow(long idIncaricoWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable;
	//****************************************************************************************************************************
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWfView
	 */
	public IncaricoWfView leggiWorkflowInCorso(long idIncarico) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param idIncarico identificativo del workflow
	 * @return oggetto IncaricoWfView
	 */
	public IncaricoWfView leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idIncarico identificativo dell'incarico
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idIncarico identificativo dell'incarico
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idIncarico: identificativo dell'incarico
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	public IncaricoWfView leggiUltimoWorkflowTerminato(long idIncarico) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idIncarico: identificativo dell'incarico
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	public IncaricoWfView leggiUltimoWorkflowRifiutato(long idIncarico) throws Throwable;
	
	public IncaricoWf leggiWorkflowInCorsoNotView(long idIncarico) throws Throwable ;
}
