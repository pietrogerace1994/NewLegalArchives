package eng.la.persistence.workflow;

import eng.la.model.IncaricoWf;

public interface IncaricoWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto IncaricoWf da salvare
	 * @return oggetto IncaricoWf salvato
	 */
	public IncaricoWf avviaWorkflow(IncaricoWf vo) throws Throwable;
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto IncaricoWf
	 */
	public IncaricoWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(IncaricoWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	public IncaricoWf leggiWorkflowInCorso(long idIncarico) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	public IncaricoWf leggiUltimoWorkflowTerminato(long idIncarico) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	public IncaricoWf leggiUltimoWorkflowRifiutato(long idIncarico) throws Throwable;
	public IncaricoWf leggiWorkflowdaIncarico(long idEntita);
}
