package eng.la.business.workflow;

import eng.la.model.BeautyContestWf;
import eng.la.model.view.BeautyContestWfView;

public interface BeautyContestWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idBeautyContest identificativo dell'incarico correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	
	
	public boolean avviaWorkflow(long idBeautyContest, String userIdUtenteConnesso) throws Throwable;
	//DARIO C **********************************************************************************************************
	public boolean avviaWorkflow(long idBeautyContest, String userIdUtenteConnesso,String matricolaDestinatario) throws Throwable;
	//*****************************************************************************************************************
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idBeautyContestWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idBeautyContestWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idIncaricoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avanzaWorkflow(long idBeautyContestWf, String userIdUtenteConnesso) throws Throwable;
	//DARIO C ********************************************************************************************************************
	public boolean avanzaWorkflow(long idBeautyContestWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable;
	//****************************************************************************************************************************
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo del bc
	 * @return oggetto BeautyContestWfView
	 */
	public BeautyContestWfView leggiWorkflowInCorso(long idBeautyContest) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param idIncarico identificativo del workflow
	 * @return oggetto BeautyContestWfView
	 */
	public BeautyContestWfView leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idIncarico identificativo del bc
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idBeautyContest, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idIncarico identificativo del bc
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idBeautyContest, String userIdUtenteConnesso) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	public BeautyContestWfView leggiUltimoWorkflowTerminato(long idBeautyContest) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	public BeautyContestWfView leggiUltimoWorkflowRifiutato(long idBeautyContest) throws Throwable;
	
	public BeautyContestWf leggiWorkflowInCorsoNotView(long idBeautyContest) throws Throwable ;
}
