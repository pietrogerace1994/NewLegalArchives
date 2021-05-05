package eng.la.persistence.workflow;

import eng.la.model.SchedaFondoRischiWf;

public interface SchedaFondoRischiWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto SchedaFondoRischiWf da salvare
	 * @return oggetto IncaricoWf salvato
	 */
	public SchedaFondoRischiWf avviaWorkflow(SchedaFondoRischiWf vo) throws Throwable;
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto SchedaFondoRischiWf
	 */
	public SchedaFondoRischiWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(SchedaFondoRischiWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWf
	 */
	public SchedaFondoRischiWf leggiWorkflowInCorso(long idSchedaFondoRischi) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWf
	 */
	public SchedaFondoRischiWf leggiUltimoWorkflowTerminato(long idSchedaFondoRischi) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWf
	 */
	public SchedaFondoRischiWf leggiUltimoWorkflowRifiutato(long idSchedaFondoRischi) throws Throwable;
	public SchedaFondoRischiWf leggiWorkflowdaIncarico(long idEntita);
}
