package eng.la.business.workflow;

import eng.la.model.SchedaFondoRischiWf;
import eng.la.model.view.SchedaFondoRischiWfView;

public interface SchedaFondoRischiWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idSchedaFondoRischi identificativo della schedaFondoRischi correlata
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avviaWorkflow(long idSchedaFr, String userIdUtenteConnesso) throws Throwable;
	//DARIO C **********************************************************************************************************
	public boolean avviaWorkflow(long idSchedaFr, String userIdUtenteConnesso,String matricolaDestinatario) throws Throwable;
	//*****************************************************************************************************************
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idSchedaFondoRischiWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idSchedaFrWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idSchedaFondoRischiWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avanzaWorkflow(long idSchedaFrWf, String userIdUtenteConnesso) throws Throwable;
	//DARIO C ********************************************************************************************************************
	public boolean avanzaWorkflow(long idSchedaFrWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable;
	//****************************************************************************************************************************
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idSchedaFondoRischi identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWfView
	 */
	public SchedaFondoRischiWfView leggiWorkflowInCorso(long idSchedaFr) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param idSchedaFondoRischi identificativo del workflow
	 * @return oggetto SchedaFondoRischiWfView
	 */
	public SchedaFondoRischiWfView leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idSchedaFondoRischi identificativo della schedaFondoRischi
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idSchedaFr, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idSchedaFondoRischi identificativo della schedaFondoRischi
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idSchedaFr, String userIdUtenteConnesso) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idSchedaFondoRischi: identificativo della schedaFondoRischi
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	public SchedaFondoRischiWfView leggiUltimoWorkflowTerminato(long idSchedaFr) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idSchedaFondoRischi: identificativo della schedaFondoRischi
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	public SchedaFondoRischiWfView leggiUltimoWorkflowRifiutato(long idSchedaFr) throws Throwable;
	
	public SchedaFondoRischiWf leggiWorkflowInCorsoNotView(long idSchedaFr) throws Throwable ;
}
