package eng.la.persistence.workflow;

import eng.la.model.ProformaWf;

public interface ProformaWfDAO {

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto ProformaWf da salvare
	 * @return oggetto ProformaWf salvato
	 */
	public ProformaWf avviaWorkflow(ProformaWf vo) throws Throwable;
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProformaWf
	 */
	public ProformaWf leggiWorkflow(long id) throws Throwable;
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	public void modifica(ProformaWf vo) throws Throwable;
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	public ProformaWf leggiWorkflowInCorso(long idProforma) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	public ProformaWf leggiUltimoWorkflowTerminato(long idProforma) throws Throwable;
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	public ProformaWf leggiUltimoWorkflowRifiutato(long idProforma) throws Throwable;
	public ProformaWf leggiWorkflowDaProforma(long idEntita);
}
