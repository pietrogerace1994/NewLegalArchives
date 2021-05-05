package eng.la.business.workflow;

import java.util.List;

import eng.la.model.StepWf;
import eng.la.model.view.StepWfView;


public interface StepWfService {

	/**
	 * Recupera gli step in carico all'utente passato in input
	 * @param matricola: matricola dell'utente
	 * @param lang: lingua di visualizzazione           
	 */
	public List<StepWfView> leggiAttivitaPendenti(String matricola, String lang) throws Throwable;
	
	/**
	 * Recupera l'istanza di step.
	 * @param id identificativo dello step
	 * @return oggetto StepWfView
	 */
	public StepWfView leggiStep(long id) throws Throwable;
	
	/**
	 * Recupera lo step corrente a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto StepWfView
	 */
	public StepWfView leggiStepCorrente(long idWorkflow, String classeWf) throws Throwable;
	
	public Long checkPendingWf(String matricola) throws Throwable;
	
}
