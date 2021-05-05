package eng.la.persistence.workflow;

import eng.la.model.BeautyContestWf;

public interface BeautyContestWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto BeautyContestWf da salvare
	 * @return oggetto BeautyContestWf salvato
	 */
	public BeautyContestWf avviaWorkflow(BeautyContestWf vo) throws Throwable;
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto BeautyContestWf
	 */
	public BeautyContestWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(BeautyContestWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	public BeautyContestWf leggiWorkflowInCorso(long idBeautyContest) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	public BeautyContestWf leggiUltimoWorkflowTerminato(long idBeautyContest) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	public BeautyContestWf leggiUltimoWorkflowRifiutato(long idBeautyContest) throws Throwable;
	public BeautyContestWf leggiWorkflowdaBeautyContest(long idEntita);
}
