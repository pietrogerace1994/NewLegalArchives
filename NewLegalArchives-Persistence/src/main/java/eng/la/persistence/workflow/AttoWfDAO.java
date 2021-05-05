package eng.la.persistence.workflow;

import eng.la.model.AttoWf;

public interface AttoWfDAO {
	
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto AttoWf da salvare
	 * @return oggetto AttoWf salvato
	 */
	public AttoWf avviaWorkflow(AttoWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto AttoWf
	 */
	public AttoWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(AttoWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	public AttoWf leggiWorkflowInCorso(long idAtto) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	public AttoWf leggiUltimoWorkflowTerminato(long idAtto) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	public AttoWf leggiUltimoWorkflowRifiutato(long idAtto) throws Throwable;

	public AttoWf leggiWorkflowDaAtto(long idEntita);
	
	public AttoWf leggiWorkflowAtto(long idAtto) throws Throwable;
}
