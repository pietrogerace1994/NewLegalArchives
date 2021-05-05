package eng.la.persistence.workflow;

import eng.la.model.ProfessionistaEsternoWf;

public interface ProfessionistaEsternoWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto ProfessionistaEsternoWf da salvare
	 * @return oggetto ProfessionistaEsternoWf salvato
	 */
	public ProfessionistaEsternoWf avviaWorkflow(ProfessionistaEsternoWf vo) throws Throwable;
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProfessionistaEsternoWf
	 */
	public ProfessionistaEsternoWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(ProfessionistaEsternoWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idFascicolo identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	public ProfessionistaEsternoWf leggiWorkflowInCorso(long idProfessionista) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	public ProfessionistaEsternoWf leggiUltimoWorkflowTerminato(long idProfessionista) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	public ProfessionistaEsternoWf leggiUltimoWorkflowRifiutato(long idProfessionista) throws Throwable;
}
