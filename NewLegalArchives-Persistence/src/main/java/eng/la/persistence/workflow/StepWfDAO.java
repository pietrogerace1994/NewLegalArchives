package eng.la.persistence.workflow;

import java.util.List;

import eng.la.model.StepWf;
import eng.la.model.Utente;

public interface StepWfDAO {

	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idConfigurazione: identificativo della configurazione dello step corrente
	 * @param idObject: identificativo dell'istanza di oggetto corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	public Utente leggiAssegnatarioCorrente(long idConfigurazione, long idObject)throws Throwable;
	
	/**
	 * Crea lo step.
	 * @param vo: oggetto Step
	 * @return oggetto model StepWf creato
	 */
	public StepWf creaStepWf(StepWf vo) throws Throwable;
	
	/**
	 * Recupera lo step corrente a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	public StepWf leggiStepCorrente(long idWorkflow, String classeWf)throws Throwable;
	
	/**
	 * Aggiorna lo step modificando solo i dati aggiornabili.
	 * @param vo: lo step
	 *            
	 */
	public void modifica(StepWf vo) throws Throwable;
	
	/**
	 * Recupera gli step in carico all'utente passato in input
	 * @param matricola: matricola dell'utente
	 *            
	 */
	public List<StepWf> leggiAttivitaPendenti(String matricola) throws Throwable;
	
	/**
	 * Recupera l'istanza di step.
	 * @param id identificativo dello step
	 * @return oggetto StepWf
	 */
	public StepWf leggiStep(long id) throws Throwable;
	
	/**
	 * Recupera l'ultimo Step chiuso a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	public StepWf leggiUltimoStepChiuso(long idWorkflow, String classeWf)throws Throwable;
	
	/**
	 * Recupera l'ultimo step di un workflow terminato a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	public StepWf leggiUltimoStepWorkflow(long idWorkflow, String classeWf)throws Throwable;
	
	/**
	 * Recupera lo step chiuso di un workflow standard a partire dal workflow, dalla classe e dallo stato finale.
	 * @param idWorkflow identificatico del workflow
	 * @param classeWf la tipologia di workflow
	 * @param stepTo stato finale dello step
	 * @return oggetto model StepWf
	 */
	public StepWf leggiSpecificoStepWorkflow(long idWorkflow, String classeWf, String stateTo)throws Throwable;
	
	public Long checkPendingWf(String matricola) throws Throwable;
}
