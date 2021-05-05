package eng.la.business.workflow;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.BaseService;
import eng.la.model.AbstractEntity;
import eng.la.model.BeautyContest;
import eng.la.model.BeautyContestWf;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.BeautyContestWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.BeautyContestDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.BeautyContestWfDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business BeautyContestWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Benedetto Giordano
 * @version 1.0
 * @since 2017-07-25
 */
@Service("beautyContestWfService")
public class BeautyContestWfServiceImpl extends BaseService implements BeautyContestWfService {

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
		
	@Autowired
	private BeautyContestDAO beautyContestDAO;
 
	@Autowired
	private BeautyContestWfDAO beautyContestWfDAO;
 
	@Autowired
	private UtenteDAO utenteDAO;
 
	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
 
	public ConfigurazioneStepWfDAO getConfigurazioneStepWfDAO() {
		return configurazioneStepWfDAO;
	}
	
	@Autowired
	private StepWfDAO stepWfDAO;
 
	public StepWfDAO getStepWfDAO() {
		return stepWfDAO;
	}
	
	@Override
	protected Class<BeautyContestWf> leggiClassVO() {
		return BeautyContestWf.class;
	}

	@Override
	protected Class<BeautyContestWfView> leggiClassView() {
		return BeautyContestWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idBeautyContest identificativo dell'incarico correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C**********************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idBeautyContest, String userIdUtenteConnesso) throws Throwable {
		return avviaWorkflow(idBeautyContest, userIdUtenteConnesso,"");
	}
		
	//*****************************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idBeautyContest, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		BeautyContestWf beautyContestWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		BeautyContestWf beautyContestWfSalvato = null;
		BeautyContest beautyContest = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		beautyContest = beautyContestDAO.leggi(idBeautyContest);
		
		//se il bc non è in stato Bozza genero l'eccezione
		if(!beautyContest.getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA)){
			throw new LAException("Lo stato " + beautyContest.getStatoBeautyContest().getDescrizione() + " del beautyContest non è compatibile con l'operazione richiesta", "errore.beautyContest.stato");

		}
		
		//creo l'occorrenza BeautyContestWf
		beautyContestWf = new BeautyContestWf();
		beautyContestWf.setBeautyContest(beautyContest);
		beautyContestWf.setUtenteCreazione(userIdUtenteConnesso);
		beautyContestWf.setDataCreazione(dataCreazione);
		beautyContestWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		beautyContestWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		//DARIO C ************************************************************************************
		if (matricolaDestinatario.trim().length()!=0){
			beautyContestWf.setUtenteAssegnatario(matricolaDestinatario);	
		}
		//********************************************************************************************
		
		
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);	
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))//l'utente connesso è il responsabile top, per cui mi limito ad autorizzare il bc
		{
			beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			beautyContest.setDataModifica(dataCreazione);
			beautyContest.setDataAutorizzazione(dataCreazione);
			beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContest.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContest);
			return beautyContest.getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO);
		}
		
		//DARIO C **************************************************************************************************
//		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
//			primoRiporto = true;
//			respPrimoRiporto = true;
//		}
//		else{
//			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
//			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
//				respPrimoRiporto = true;
//		}


		matricolaDestinatario = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : utenteConnesso.getMatricolaRespUtil(); 
		if(matricolaDestinatario.equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
			primoRiporto = true;
			respPrimoRiporto = true;
		}else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(matricolaDestinatario);
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		//************************************************************************************************************
		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(classeWf.getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //non c'è alcuno step successivo, per cui mi limito ad aggiornare lo stato dell'incarico in Autorizzato
			
			beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			beautyContest.setDataModifica(dataCreazione);
			beautyContest.setDataAutorizzazione(dataCreazione);
			beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContest.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContest);
			return beautyContest.getStatoBeautyContest().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO);
		}
				

		//creo l'istanza di workflow
		beautyContestWfSalvato = beautyContestWfDAO.avviaWorkflow(beautyContestWf);  
		
		//creo lo step
		creaStepWf(beautyContestWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			
		beautyContest.setDataModifica(dataCreazione);
		beautyContest.setDataRichiestaAutorBc(dataCreazione);

		beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
		beautyContest.setOperationTimestamp(new Date());
		beautyContestDAO.modifica(beautyContest);
		
		return beautyContestWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}
	
	/** 
	  * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idBeautyContestWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idBeautyContestWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		BeautyContestWf beautyContestWf = null;
		StepWf stepCorrente = null;
		BeautyContest beautyContestCorrelato = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		beautyContestWf = beautyContestWfDAO.leggiWorkflow(idBeautyContestWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!beautyContestWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + beautyContestWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idBeautyContestWf, CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza IncaricoWf
		
		beautyContestWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		beautyContestWf.setDataChiusura(dataRifiuto);
		beautyContestWfDAO.modifica(beautyContestWf);
				
		//recupero il bc
		beautyContestCorrelato = beautyContestWf.getBeautyContest();
		beautyContestCorrelato.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		
		beautyContestCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		beautyContestCorrelato.setOperationTimestamp(new Date());
		beautyContestDAO.modifica(beautyContestCorrelato);
		
		return beautyContestWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idBeautyContestWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C ************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idSchedaFondoRischiWf, String userIdUtenteConnesso) throws Throwable {
		return avanzaWorkflow(idSchedaFondoRischiWf, userIdUtenteConnesso, "");
	}
	//********************************************************************************************************	
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idBeautyContestWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		//aggiorno l'occorrenza IncaricoWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean respTop = false;
		
		BeautyContestWf beautyContestWf = null;
		BeautyContest beautyContestCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente utenteConnesso = null;
		Utente responsabileDiretto = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		beautyContestWf = beautyContestWfDAO.leggiWorkflow(idBeautyContestWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!beautyContestWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + beautyContestWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero il bc
		beautyContestCorrelato = beautyContestWf.getBeautyContest();
	
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idBeautyContestWf, CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		
		//verifico se sia il responsabile top
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))
			respTop = true;
		
		//DARIO C ****************************************************************************************************
//		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()) || respTop){
//				primoRiporto = true;
//				respPrimoRiporto = true;
//		}
//		else{
//			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
//			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
//				respPrimoRiporto = true;
//		}
		
		String matricola_responsabile = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : utenteConnesso.getMatricolaRespUtil(); 
		if(matricola_responsabile.equalsIgnoreCase(responsabileTop.getMatricolaUtil()) || respTop){
			primoRiporto = true;
			respPrimoRiporto = true;
		}else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(matricola_responsabile);
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		//************************************************************************************************************		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST).getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST).getId(), CostantiDAO.LINGUA_ITALIANA);
		
		//calcolo lo step da mandare in assegnazione
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //Workflow finito
			
			//aggiorno l'occorrenza BeautyContestWf
			beautyContestWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
			beautyContestWf.setDataChiusura(dataAvanzamento);
			beautyContestWfDAO.modifica(beautyContestWf);
			
			//aggiorno il bc
			beautyContestCorrelato.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			beautyContestCorrelato.setDataAutorizzazione(dataAvanzamento);

			beautyContestCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContestCorrelato.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContestCorrelato);
		
		}
		else{

			//DARIO C ************************************************************************************
			if (matricolaDestinatario.trim().length()!=0){
				beautyContestWf.setUtenteAssegnatario(matricolaDestinatario);
				beautyContestWfDAO.modifica(beautyContestWf);
			}
			//********************************************************************************************
			
			//creo lo step
			creaStepWf(beautyContestWf,configurazione, dataAvanzamento, utenteConnesso);
			
			//aggiorno il bc
			beautyContestCorrelato.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			beautyContestCorrelato.setDataModifica(dataAvanzamento);
			

			beautyContestCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContestCorrelato.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContestCorrelato);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	 * Crea un nuovo step workflow
	 *
	 * @param beautyContestWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(BeautyContestWf beautyContestWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setBeautyContestWf(beautyContestWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	@Override
	public BeautyContestWfView leggiWorkflowInCorso(long idBeautyContest) throws Throwable {
		BeautyContestWf wf = beautyContestWfDAO.leggiWorkflowInCorso(idBeautyContest);
		if(wf != null)
			return (BeautyContestWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	public BeautyContestWf leggiWorkflowInCorsoNotView(long idBeautyContest) throws Throwable {
		BeautyContestWf wf = beautyContestWfDAO.leggiWorkflowInCorso(idBeautyContest);
		if(wf != null)
			return wf;
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	@Override
	public BeautyContestWfView leggiUltimoWorkflowTerminato(long idBeautyContest) throws Throwable {
		BeautyContestWf wf = beautyContestWfDAO.leggiUltimoWorkflowTerminato(idBeautyContest);
		if(wf != null)
		return (BeautyContestWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	@Override
	public BeautyContestWfView leggiUltimoWorkflowRifiutato(long idBeautyContest) throws Throwable {
		BeautyContestWf wf = beautyContestWfDAO.leggiUltimoWorkflowRifiutato(idBeautyContest);
		if(wf != null)
		return (BeautyContestWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto BeautyContestWfView.
	 * @exception Throwable
	 */
	@Override
	public BeautyContestWfView leggiWorkflow(long id) throws Throwable {
		BeautyContestWf wf = beautyContestWfDAO.leggiWorkflow(id);
		return (BeautyContestWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idBeautyContest identificativo del bc
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idBeautyContest, String userIdUtenteConnesso) throws Throwable {

		BeautyContestWfView workflowCorrente = null;
		BeautyContestWf beautyContestWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		BeautyContest beautyContest = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		beautyContest = beautyContestDAO.leggi(idBeautyContest);
		
		
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idBeautyContest);
		beautyContestWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno il bc
			beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			beautyContest.setDataModifica(dataDiscard);
			beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContest.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContest);
		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
			beautyContestWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			beautyContestWf.setDataChiusura(dataDiscard);
			beautyContestWfDAO.modifica(beautyContestWf);
			
			//riporto il bc in Bozza
			beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			beautyContest.setDataModifica(dataDiscard);
			beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
			beautyContest.setOperationTimestamp(new Date());
			beautyContestDAO.modifica(beautyContest);
		}
		return (stepCorrente.getDataChiusura() != null);
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idBeautyContest identificativo del bc
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idBeautyContest, String userIdUtenteConnesso) throws Throwable {

		BeautyContestWfView workflowCorrente = null;
		BeautyContestWf beautyContestWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		BeautyContest beautyContest = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il bc correlato
		beautyContest = beautyContestDAO.leggi(idBeautyContest);
		
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idBeautyContest);
		if(workflowCorrente != null){
			
			beautyContestWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		}
		else{
			
			terminato = true;
			workflowCorrente = leggiUltimoWorkflowTerminato(idBeautyContest);
			if(workflowCorrente != null){
				beautyContestWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza il bc
			{
				beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				beautyContest.setDataModifica(dataDiscard);
				beautyContestDAO.modifica(beautyContest);
				return (beautyContest.getStatoBeautyContest().getId() == anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
			}
		}
		
		//effettuo il discard sullo step corrente
		if(!terminato ){
			stepCorrente.setDataChiusura(dataDiscard);
			stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		}
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//chiudo il workflow settandolo in Interrotto
		
		beautyContestWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		beautyContestWf.setDataChiusura(dataDiscard);
		beautyContestWfDAO.modifica(beautyContestWf);
		
		//riporto il bc in Bozza
		beautyContest.setStatoBeautyContest(anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		beautyContest.setDataModifica(dataDiscard);
		beautyContest.setOperation(AbstractEntity.UPDATE_OPERATION);
		beautyContest.setOperationTimestamp(new Date());
		beautyContestDAO.modifica(beautyContest);
		return (beautyContest.getStatoBeautyContest().getId() == anagraficaStatiTipiDAO.leggiStatoBeautyContest(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	
}
