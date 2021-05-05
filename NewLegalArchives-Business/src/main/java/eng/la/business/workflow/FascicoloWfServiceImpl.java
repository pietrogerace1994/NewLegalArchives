package eng.la.business.workflow;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.BaseService;
import eng.la.model.AbstractEntity;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.Fascicolo;
import eng.la.model.FascicoloWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.FascicoloWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.FascicoloWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business FascicoloWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Silvana Di Perna
 * @version 1.0
 * @since 2016-07-15
 */
@Service("fascicoloWfService")
public class FascicoloWfServiceImpl extends BaseService implements FascicoloWfService{
	
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
	
	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
 
	public ConfigurazioneStepWfDAO getConfigurazioneStepWfDAO() {
		return configurazioneStepWfDAO;
	}
	
	@Autowired
	private FascicoloWfDAO fascicoloWfDAO;
 
	public FascicoloWfDAO getFascicoloWfDAO() {
		return fascicoloWfDAO;
	}
		
	@Autowired
	private StepWfDAO stepWfDAO;
 
	public StepWfDAO getStepWfDAO() {
		return stepWfDAO;
	}
	
	@Autowired
	private FascicoloDAO fascicoloDAO;
 
	public FascicoloDAO getFascicoloDAO() {
		return fascicoloDAO;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
 
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}

	@Override
	protected Class<FascicoloWf> leggiClassVO() {
		return FascicoloWf.class;
	}

	@Override
	protected Class<FascicoloWfView> leggiClassView() {
		return FascicoloWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idFascicolo identificativo del fascicolo correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idFascicolo, String userIdUtenteConnesso) throws Throwable {
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		FascicoloWf fascicoloWf = null;
		ClasseWf classeWf = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		FascicoloWf fascicoloWfSalvato = null;
		Fascicolo fascicolo = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		
		//recupero il fascicolo correlato
		fascicolo = fascicoloDAO.leggi(idFascicolo);
		
		//se il fascicolo non è in stato Aperto genero l'eccezione
		if(!fascicolo.getStatoFascicolo().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.FASCICOLO_STATO_COMPLETATO))
			throw new LAException("Lo stato " + fascicolo.getStatoFascicolo().getDescrizione() + " del fascicolo non è compatibile con l'operazione richiesta", "errore.fascicolo.stato");

		
		//creo l'occorrenza FascicoloWf
		fascicoloWf = new FascicoloWf();
		fascicoloWf.setFascicolo(fascicolo);
		fascicoloWf.setUtenteCreazione(userIdUtenteConnesso);
		fascicoloWf.setDataCreazione(dataCreazione);
		fascicoloWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		fascicoloWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		// recupero il tipo di workflow
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.CHIUSURA_FASCICOLO);
		
		//recupero l'utente coneesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso)) //l'utente connesso è il responsabile top, per cui mi limito a chiudere il fascicolo
		{
			fascicolo.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_CHIUSO, CostantiDAO.LINGUA_ITALIANA));
			fascicolo.setDataUltimaModifica(dataCreazione);
			fascicolo.setDataChiusura(dataCreazione);
			fascicolo.setOperation(AbstractEntity.UPDATE_OPERATION);
			fascicolo.setOperationTimestamp(new Date());
			fascicoloDAO.aggiornaFascicolo(fascicolo);
			
			
			return fascicolo.getStatoFascicolo().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.FASCICOLO_STATO_CHIUSO);
		}
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
			primoRiporto = true;
			respPrimoRiporto = true;
		}
		else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		
		//recupero lo step di partenza
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
		//creo l'istanza di workflow
		fascicoloWfSalvato = fascicoloWfDAO.avviaWorkflow(fascicoloWf);  
		
		//creo lo step
		creaStepWf(fascicoloWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		//aggiorno il fascicolo
		fascicolo.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
		fascicolo.setDataUltimaModifica(dataCreazione);
		fascicolo.setOperation(AbstractEntity.UPDATE_OPERATION);
		fascicolo.setOperationTimestamp(new Date());
		fascicoloDAO.aggiornaFascicolo(fascicolo);
		
		return fascicoloWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}
	
	/**
	  * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idIncaricoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idFascicoloWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		FascicoloWf fascicoloWf = null;
		StepWf stepCorrente = null;
		Fascicolo fascicoloCorrelato = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		fascicoloWf = fascicoloWfDAO.leggiWorkflow(idFascicoloWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!fascicoloWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + fascicoloWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);		
		//aggiorno l'occorrenza FascicoloWf
			
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idFascicoloWf, CostantiDAO.CHIUSURA_FASCICOLO);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
	
		//aggiorno il workflow
		fascicoloWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		fascicoloWf.setDataChiusura(dataRifiuto);
		fascicoloWfDAO.modifica(fascicoloWf);

		
		//recupero il fascicolo
		fascicoloCorrelato = fascicoloWf.getFascicolo();
		fascicoloCorrelato.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
		//fascicoloCorrelato.setDataUltimaModifica(dataRifiuto);
		fascicoloCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		fascicoloCorrelato.setOperationTimestamp(new Date());
		fascicoloDAO.aggiornaFascicolo(fascicoloCorrelato);
		
		return fascicoloWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	 * Fa avanzare un workflow 
	 *
	 * @param idFascicoloWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean chiudiFascicolo(long idFascicoloWf, String userIdUtenteConnesso)throws Throwable {
		
		//aggiorno l'occorrenza FascicoloWf
		Timestamp dataChiusura = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		FascicoloWf fascicoloWf = null;
		Fascicolo fascicoloCorrelato = null;
		StepWf stepCorrente = null;
		ConfigurazioneStepWf configurazione = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		
		fascicoloWf = fascicoloWfDAO.leggiWorkflow(idFascicoloWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!fascicoloWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + fascicoloWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		//recupero il fascicolo
		fascicoloCorrelato = fascicoloWf.getFascicolo();

		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idFascicoloWf, CostantiDAO.CHIUSURA_FASCICOLO);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataChiusura);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza IncaricoWf
		fascicoloWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
		fascicoloWf.setDataChiusura(dataChiusura);
		fascicoloWfDAO.modifica(fascicoloWf);
		
		//aggiorno il fascicolo
		fascicoloCorrelato.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_CHIUSO, CostantiDAO.LINGUA_ITALIANA));
		fascicoloCorrelato.setDataUltimaModifica(dataChiusura);
		fascicoloCorrelato.setDataChiusura(dataChiusura);

		fascicoloCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		fascicoloCorrelato.setOperationTimestamp(new Date());
		fascicoloDAO.aggiornaFascicolo(fascicoloCorrelato);
		
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	
	/**
	 * Crea un nuovo steo workflow
	 *
	 * @param fascicoloWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(FascicoloWf fascicoloWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setFascicoloWf(fascicoloWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idFascicolo: identificativo del fascicolo
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	@Override
	public FascicoloWfView leggiWorkflowInCorso(long idFascicolo) throws Throwable {
		FascicoloWf wf = fascicoloWfDAO.leggiWorkflowInCorso(idFascicolo);
		if(wf != null)
			return (FascicoloWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	@Override
	public FascicoloWfView leggiWorkflow(long id) throws Throwable {
		FascicoloWf wf = fascicoloWfDAO.leggiWorkflow(id);
		return (FascicoloWfView) convertiVoInView(wf);
	}
	
		
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idFascicolo: identificativo del fascicolo
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	@Override
	public FascicoloWfView leggiUltimoWorkflowTerminato(long idFascicolo) throws Throwable {
		FascicoloWf wf = fascicoloWfDAO.leggiUltimoWorkflowTerminato(idFascicolo);
		if(wf != null)
		return (FascicoloWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idFascicolo: identificativo del fascicolo
	 * @return oggetto FascicoloWfView.
	 * @exception Throwable
	 */
	@Override
	public FascicoloWfView leggiUltimoWorkflowRifiutato(long idFascicolo) throws Throwable {
		FascicoloWf wf = fascicoloWfDAO.leggiUltimoWorkflowRifiutato(idFascicolo);
		if(wf != null)
		return (FascicoloWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Riporta in completato il fascicolo.
	 * @param idFascicolo identificativo del fascicolo
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInCompletatoWorkflow(long idFascicolo, String userIdUtenteConnesso) throws Throwable {

		FascicoloWfView workflowCorrente = null;
		FascicoloWf fascicoloWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		Fascicolo fascicolo = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il fascicolo correlato
		fascicolo = fascicoloDAO.leggi(idFascicolo);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idFascicolo);
		if(workflowCorrente != null){
			
			fascicoloWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.CHIUSURA_FASCICOLO);
		}
		else{
			terminato = true;
			workflowCorrente = leggiUltimoWorkflowTerminato(idFascicolo);
			if(workflowCorrente != null){
				fascicoloWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.CHIUSURA_FASCICOLO);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza il fascicolo
			{
				fascicolo.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
				fascicolo.setDataUltimaModifica(dataDiscard);
				fascicolo.setDataChiusura(null);
				fascicoloDAO.aggiornaFascicolo(fascicolo);
				return (fascicolo.getStatoFascicolo().getId() == anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_COMPLETATO, CostantiDAO.LINGUA_ITALIANA).getId());

			}
		}
		
		//effettuo il discard sullo step corrente
		if(!terminato){
			stepCorrente.setDataChiusura(dataDiscard);
			stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		}
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//chiudo il workflow settandolo in Interrotto
		
		fascicoloWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		fascicoloWf.setDataChiusura(dataDiscard);
		fascicoloWfDAO.modifica(fascicoloWf);
		
		//riporto il fascicolo in completato
		fascicolo.setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
		fascicolo.setDataUltimaModifica(dataDiscard);
		fascicolo.setDataChiusura(null);

		fascicolo.setOperation(AbstractEntity.UPDATE_OPERATION);
		fascicolo.setOperationTimestamp(new Date());
		fascicoloDAO.aggiornaFascicolo(fascicolo);
		
		return (fascicolo.getStatoFascicolo().getId() == anagraficaStatiTipiDAO.leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_COMPLETATO, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
}
