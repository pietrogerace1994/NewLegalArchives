package eng.la.business.workflow;


import eng.la.model.ProfessionistaEsternoWf;
import eng.la.model.view.ProfessionistaEsternoWfView;
import eng.la.model.view.UtenteView;

public interface ProfessionistaEsternoWfService {

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idProfessionista identificativo del professionistaEsterno correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 *@return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avviaWorkflow(long idProfessionista, String userIdUtenteConnesso) throws Throwable;
	/**
	 * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idProfessionistaEsternoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean rifiutaWorkflow(long idProfessionistaEsternoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable;
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idProfessionistaEsternoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	public boolean avanzaWorkflow(long idProfessionistaEsternoWf, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWfView
	 */
	public ProfessionistaEsternoWfView leggiWorkflowInCorso(long idprofessionista) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProfessionistaEsternoWfView
	 */
	public ProfessionistaEsternoWfView leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idProfessionista identificativo del professionista
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean riportaInBozzaWorkflow(long idProfessionista, String userIdUtenteConnesso) throws Throwable;
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idProfessionista identificativo del professionista
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	public boolean discardStep(long idProfessionista, String userIdUtenteConnesso) throws Throwable;
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idProfessionista: identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWfView.
	 * @exception Throwable
	 */
	public ProfessionistaEsternoWfView leggiUltimoWorkflowTerminato(long idProfessionista) throws Throwable;
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idProfessionista: identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWfView.
	 * @exception Throwable
	 */
	public ProfessionistaEsternoWfView leggiUltimoWorkflowRifiutato(long idProfessionista) throws Throwable;
	public ProfessionistaEsternoWf leggiWorkflowInCorsoNotView(long idProfessionista) throws Throwable;
}
