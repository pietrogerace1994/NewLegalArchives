package eng.la.persistence.workflow;

import eng.la.model.FascicoloWf;

public interface FascicoloWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto FascicoloWf da salvare
	 * @return oggetto FascicoloWf salvato
	 */
	public FascicoloWf avviaWorkflow(FascicoloWf vo) throws Throwable;
	
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto FascicoloWf
	 */
	public FascicoloWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(FascicoloWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idFascicolo identificativo del fascicolo
	 * @return oggetto FascicoloWf
	 */
	public FascicoloWf leggiWorkflowInCorso(long idFascicolo) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo del fascicolo
	 * @return oggetto FascicoloWf
	 */
	public FascicoloWf leggiUltimoWorkflowTerminato(long idFascicolo) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo del fascicolo
	 * @return oggetto IncaricoWf
	 */
	public FascicoloWf leggiUltimoWorkflowRifiutato(long idFascicolo) throws Throwable;
}
