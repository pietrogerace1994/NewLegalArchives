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
import eng.la.model.Incarico;
import eng.la.model.IncaricoWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.IncaricoWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.IncaricoDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.IncaricoWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business IncaricoWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Silvana Di Perna
 * @version 1.0
 * @since 2016-07-14
 */
@Service("incaricoWfService")
public class IncaricoWfServiceImpl extends BaseService implements IncaricoWfService {

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
		
	@Autowired
	private IncaricoDAO incaricoDAO;
 
	public IncaricoDAO getIncaricoDAO() {
		return incaricoDAO;
	}
	
	@Autowired
	private IncaricoWfDAO incaricoWfDAO;
 
	public IncaricoWfDAO getIncaricoWfDAO() {
		return incaricoWfDAO;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
 
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
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
	protected Class<IncaricoWf> leggiClassVO() {
		return IncaricoWf.class;
	}

	@Override
	protected Class<IncaricoWfView> leggiClassView() {
		return IncaricoWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idIncarico identificativo dell'incarico correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C**********************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable {
		return avviaWorkflow(idIncarico, userIdUtenteConnesso, isArbitrale, "");
	}
	//*****************************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		IncaricoWf incaricoWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		IncaricoWf incaricoWfSalvato = null;
		Incarico incarico = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'incarico correlato
		if( isArbitrale ){
			incarico = incaricoDAO.leggiCollegioArbitrale(idIncarico);
		}else{
			incarico = incaricoDAO.leggi(idIncarico);
		}
		
		//se l'incarico non è in stato Bozza genero l'eccezione
		if(!incarico.getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.INCARICO_STATO_BOZZA)){
			if( isArbitrale )
				throw new LAException("Lo stato " + incarico.getStatoIncarico().getDescrizione() + " dell'arbitrato non è compatibile con l'operazione richiesta", "errore.arbitrato.stato");
			else
				throw new LAException("Lo stato " + incarico.getStatoIncarico().getDescrizione() + " dell'incarico non è compatibile con l'operazione richiesta", "errore.incarico.stato");

		}
			

		
		//creo l'occorrenza IncaricoWf
		incaricoWf = new IncaricoWf();
		incaricoWf.setIncarico(incarico);
		incaricoWf.setCollegioArbitrale(incarico.getCollegioArbitrale());
		incaricoWf.setUtenteCreazione(userIdUtenteConnesso);
		incaricoWf.setDataCreazione(dataCreazione);
		incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		incaricoWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		//DARIO C ************************************************************************************
		if (matricolaDestinatario.trim().length()!=0){
			incaricoWf.setUtenteAssegnatario(matricolaDestinatario);	
		}
		//********************************************************************************************
		// recupero il tipo di workflow
//		if(incarico.getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
//			classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE);
//		else
			classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INCARICO);	
		
		
			
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))//l'utente connesso è il responsabile top, per cui mi limito ad autorizzare l'incarico
		{
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			incarico.setDataModifica(dataCreazione);
			incarico.setDataAutorizzazione(dataCreazione);
			incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
			incarico.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incarico);
			return incarico.getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.INCARICO_STATO_AUTORIZZATO);
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
		
						
		String matricola_responsabile = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : utenteConnesso.getMatricolaRespUtil(); 
		if(matricola_responsabile.equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
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
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(classeWf.getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
//		else
//			//calcolo lo step da mandare in assegnazione
//			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
//		if(primoRiporto || respPrimoRiporto)
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //non c'è alcuno step successivo, per cui mi limito ad aggiornare lo stato dell'incarico in Autorizzato
			
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			incarico.setDataModifica(dataCreazione);
			incarico.setDataAutorizzazione(dataCreazione);
			incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
			incarico.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incarico);
			return incarico.getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.INCARICO_STATO_AUTORIZZATO);
		}
				

		//creo l'istanza di workflow
		incaricoWfSalvato = incaricoWfDAO.avviaWorkflow(incaricoWf);  
		
		//creo lo step
		creaStepWf(incaricoWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
//		//aggiorno l'incarico
//		if(configurazioneStart == null)
//			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(configurazione.getStateFrom(), CostantiDAO.LINGUA_ITALIANA));
//		else
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			
		incarico.setDataModifica(dataCreazione);
		incarico.setDataRichiestaAutorIncarico(dataCreazione);

		incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
		incarico.setOperationTimestamp(new Date());
		incaricoDAO.modifica(incarico);
		
		return incaricoWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}
	
	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idIncarico identificativo dell'incarico correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	//@Override
	public boolean avviaWorkflow_old(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable {
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		IncaricoWf incaricoWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		IncaricoWf incaricoWfSalvato = null;
		Incarico incarico = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'incarico correlato
		if( isArbitrale ){
			incarico = incaricoDAO.leggiCollegioArbitrale(idIncarico);
		}else{
			incarico = incaricoDAO.leggi(idIncarico);
		}
		
		//creo l'occorrenza IncaricoWf
		incaricoWf = new IncaricoWf();
		incaricoWf.setIncarico(incarico);
		incaricoWf.setCollegioArbitrale(incarico.getCollegioArbitrale());
		incaricoWf.setUtenteCreazione(userIdUtenteConnesso);
		incaricoWf.setDataCreazione(dataCreazione);
		incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		incaricoWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		// recupero il tipo di workflow
		if(incarico.getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
			classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE);
		else
			classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INCARICO);	
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		
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
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(classeWf.getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
		else
			//calcolo lo step da mandare in assegnazione
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		if(primoRiporto || respPrimoRiporto)
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazione, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);

		//creo l'istanza di workflow
		incaricoWfSalvato = incaricoWfDAO.avviaWorkflow(incaricoWf);  
		
		//creo lo step
		creaStepWf(incaricoWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		//aggiorno l'incarico
		if(configurazioneStart == null)
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(configurazione.getStateFrom(), CostantiDAO.LINGUA_ITALIANA));
		else
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			
		incarico.setDataModifica(dataCreazione);
		incarico.setDataRichiestaAutorIncarico(dataCreazione);

		incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
		incarico.setOperationTimestamp(new Date());
		incaricoDAO.modifica(incarico);
		 
		
		return incaricoWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
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
	public boolean rifiutaWorkflow(long idIncaricoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		IncaricoWf incaricoWf = null;
		StepWf stepCorrente = null;
		Incarico incaricoCorrelato = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		incaricoWf = incaricoWfDAO.leggiWorkflow(idIncaricoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!incaricoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + incaricoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idIncaricoWf, CostantiDAO.AUTORIZZAZIONE_INCARICO);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza IncaricoWf
		
		incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		incaricoWf.setDataChiusura(dataRifiuto);
		incaricoWfDAO.modifica(incaricoWf);
				
		//recupero l'incarico
		incaricoCorrelato = incaricoWf.getIncarico();
		incaricoCorrelato.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		//incaricoCorrelato.setDataModifica(dataRifiuto);

		incaricoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		incaricoCorrelato.setOperationTimestamp(new Date());
		incaricoDAO.modifica(incaricoCorrelato);
		
		return incaricoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idIncaricoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C ************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idIncaricoWf, String userIdUtenteConnesso) throws Throwable {
		return avanzaWorkflow(idIncaricoWf, userIdUtenteConnesso,"");
	}
	//********************************************************************************************************	
	
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idIncaricoWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		//aggiorno l'occorrenza IncaricoWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean respTop = false;
		
		IncaricoWf incaricoWf = null;
		Incarico incaricoCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente utenteConnesso = null;
		Utente responsabileDiretto = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		incaricoWf = incaricoWfDAO.leggiWorkflow(idIncaricoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!incaricoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + incaricoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'incarico
		incaricoCorrelato = incaricoWf.getIncarico();
	
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idIncaricoWf, CostantiDAO.AUTORIZZAZIONE_INCARICO);
		
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
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INCARICO).getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INCARICO).getId(), CostantiDAO.LINGUA_ITALIANA);
		
		//calcolo lo step da mandare in assegnazione
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //Workflow finito
			
			//aggiorno l'occorrenza IncaricoWf
			incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
			incaricoWf.setDataChiusura(dataAvanzamento);
			incaricoWfDAO.modifica(incaricoWf);
			
			//aggiorno l'incarico
			incaricoCorrelato.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			//incaricoCorrelato.setDataModifica(dataAvanzamento);
			incaricoCorrelato.setDataAutorizzazione(dataAvanzamento);

			incaricoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			incaricoCorrelato.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incaricoCorrelato);
		
		}
		else{

			//DARIO C ************************************************************************************
			if (matricolaDestinatario.trim().length()!=0){
				incaricoWf.setUtenteAssegnatario(matricolaDestinatario);
				incaricoWfDAO.modifica(incaricoWf);
			}
			
			//********************************************************************************************
			
			//creo lo step
			creaStepWf(incaricoWf,configurazione, dataAvanzamento, utenteConnesso);
			
			//aggiorno l'incarico
			incaricoCorrelato.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			incaricoCorrelato.setDataModifica(dataAvanzamento);
			

			incaricoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			incaricoCorrelato.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incaricoCorrelato);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	 * Crea un nuovo steo workflow
	 *
	 * @param incaricoWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(IncaricoWf incaricoWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setIncaricoWf(incaricoWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idIncarico: identificativo dell'incarico
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	@Override
	public IncaricoWfView leggiWorkflowInCorso(long idIncarico) throws Throwable {
		IncaricoWf wf = incaricoWfDAO.leggiWorkflowInCorso(idIncarico);
		if(wf != null)
			return (IncaricoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	public IncaricoWf leggiWorkflowInCorsoNotView(long idIncarico) throws Throwable {
		IncaricoWf wf = incaricoWfDAO.leggiWorkflowInCorso(idIncarico);
		if(wf != null)
			return wf;
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idIncarico: identificativo dell'incarico
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	@Override
	public IncaricoWfView leggiUltimoWorkflowTerminato(long idIncarico) throws Throwable {
		IncaricoWf wf = incaricoWfDAO.leggiUltimoWorkflowTerminato(idIncarico);
		if(wf != null)
		return (IncaricoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idIncarico: identificativo dell'incarico
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	@Override
	public IncaricoWfView leggiUltimoWorkflowRifiutato(long idIncarico) throws Throwable {
		IncaricoWf wf = incaricoWfDAO.leggiUltimoWorkflowRifiutato(idIncarico);
		if(wf != null)
		return (IncaricoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto IncaricoWfView.
	 * @exception Throwable
	 */
	@Override
	public IncaricoWfView leggiWorkflow(long id) throws Throwable {
		IncaricoWf wf = incaricoWfDAO.leggiWorkflow(id);
		return (IncaricoWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idIncarico identificativo dell'incarico
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable {

		IncaricoWfView workflowCorrente = null;
		IncaricoWf incaricoWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		Incarico incarico = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'incarico correlato
		if( isArbitrale ){
			incarico = incaricoDAO.leggiCollegioArbitrale(idIncarico); 
		}else{
			incarico = incaricoDAO.leggi(idIncarico);
		}
		
		
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idIncarico);
		incaricoWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno l'incarico
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			incarico.setDataModifica(dataDiscard);
			incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
			incarico.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incarico);
		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
		
			incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			incaricoWf.setDataChiusura(dataDiscard);
			incaricoWfDAO.modifica(incaricoWf);
			
			//riporto l'incarico in Bozza
			incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			incarico.setDataModifica(dataDiscard);
			incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
			incarico.setOperationTimestamp(new Date());
			incaricoDAO.modifica(incarico);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idIncarico identificativo dell'incarico
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idIncarico, String userIdUtenteConnesso, boolean isArbitrale) throws Throwable {

		IncaricoWfView workflowCorrente = null;
		IncaricoWf incaricoWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		Incarico incarico = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'incarico correlato
		if( isArbitrale ){
			incarico = incaricoDAO.leggiCollegioArbitrale(idIncarico); 
		}else{
			incarico = incaricoDAO.leggi(idIncarico);
		}
		
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idIncarico);
		if(workflowCorrente != null){
			
			incaricoWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
		}
		else{
			
			terminato = true;
			workflowCorrente = leggiUltimoWorkflowTerminato(idIncarico);
			if(workflowCorrente != null){
				incaricoWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza l'incarico
			{
				incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				incarico.setDataModifica(dataDiscard);
				incaricoDAO.modifica(incarico);
				return (incarico.getStatoIncarico().getId() == anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());

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
		
		incaricoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		incaricoWf.setDataChiusura(dataDiscard);
		incaricoWfDAO.modifica(incaricoWf);
		
		//riporto l'incarico in Bozza
		incarico.setStatoIncarico(anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		incarico.setDataModifica(dataDiscard);
		incarico.setOperation(AbstractEntity.UPDATE_OPERATION);
		incarico.setOperationTimestamp(new Date());
		incaricoDAO.modifica(incarico);
		return (incarico.getStatoIncarico().getId() == anagraficaStatiTipiDAO.leggiStatoIncarico(CostantiDAO.INCARICO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	
}
