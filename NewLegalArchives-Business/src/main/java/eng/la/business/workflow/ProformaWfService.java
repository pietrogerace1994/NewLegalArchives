package eng.la.business.workflow;


import eng.la.model.ProformaWf;
import eng.la.model.view.ProformaWfView;
import eng.la.model.view.UtenteView;

public interface ProformaWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idProforma identificativo del proforma correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avviaWorkflow(long idProforma, String userIdUtenteConnesso) throws Throwable;
	//DARIO C **********************************************************************************************************
	public boolean avviaWorkflow(long idProforma, String userIdUtenteConnesso,String matricolaDestinatario) throws Throwable;
	//*****************************************************************************************************************
	
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idProformaWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idProformaWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idProformaWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avanzaWorkflow(long idProformaWf, String userIdUtenteConnesso) throws Throwable;
	//DARIO C ********************************************************************************************************************
	public boolean avanzaWorkflow(long idProformaWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable;
	//****************************************************************************************************************************
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWfView
	 */
	public ProformaWfView leggiWorkflowInCorso(long idProforma) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProformaWfView
	 */
	public ProformaWfView leggiWorkflow(long id) throws Throwable;
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idProforma identificativo del proforma
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idProforma, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idProforma identificativo del proforma
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idProforma, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idProforma: identificativo del proforma
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	public ProformaWfView leggiUltimoWorkflowTerminato(long idProforma) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idProforma: identificativo del proforma
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	public ProformaWfView leggiUltimoWorkflowRifiutato(long idProforma) throws Throwable;
	
	public ProformaWf leggiWorkflowInCorsoNotView(long idProforma) throws Throwable ;
		
	 
}

