package eng.la.business.workflow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import eng.la.business.BaseService;
import eng.la.business.mail.EmailNotificationServiceImpl;
import eng.la.model.AbstractEntity;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.Proforma;
import eng.la.model.ProformaWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.ProformaWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.ProformaDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.ProformaWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business ProformaWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Silvana Di Perna
 * @version 1.0
 * @since 2016-07-14
 */
@Service("proformaWfService")
public class ProformaWfServiceImpl extends BaseService implements ProformaWfService {

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
		
	@Autowired
	private ProformaDAO proformaDAO;
 
	public ProformaDAO getProformaDAO() {
		return proformaDAO;
	}
	
	@Autowired
	private ProformaWfDAO proformaWfDAO;
 
	public ProformaWfDAO getProformaWfDAO() {
		return proformaWfDAO;
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
	protected Class<ProformaWf> leggiClassVO() {
		return ProformaWf.class;
	}

	@Override
	protected Class<ProformaWfView> leggiClassView() {
		return ProformaWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idProforma identificativo del proforma correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C**********************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idProforma, String userIdUtenteConnesso) throws Throwable {
		return avviaWorkflow(idProforma, userIdUtenteConnesso, "");
	}
	//*****************************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idProforma, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		ProformaWf proformaWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		ProformaWf proformaWfSalvato = null;
		Proforma proforma = null;
		
		//recupero l'utente conesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		//recupero il proforma correlato
		proforma = proformaDAO.leggi(idProforma);
		
		//se il proforma non è in stato Bozza genero l'eccezione
		if(!proforma.getStatoProforma().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFORMA_STATO_BOZZA))
			throw new LAException("Lo stato " + proforma.getStatoProforma().getDescrizione() + " del proforma non è compatibile con l'operazione richiesta", "errore.proforma.stato");

		
		//creo l'occorrenza ProformaWf
		proformaWf = new ProformaWf();
		proformaWf.setProforma(proforma);
		proformaWf.setUtenteCreazione(userIdUtenteConnesso);
		proformaWf.setDataCreazione(dataCreazione);
		proformaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		proformaWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		//DARIO C ************************************************************************************
		if (matricolaDestinatario.trim().length()!=0){
			proformaWf.setUtenteAssegnatario(matricolaDestinatario);	
		}
		//********************************************************************************************	
		
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_PROFORMA);	
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))//l'utente connesso è il responsabile top, per cui mi limito ad autorizzare il proforma
		{
			proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			proforma.setDataUltimaModifica(dataCreazione);
			proforma.setDataAutorizzazione(dataCreazione);
			proforma.setAutorizzatore(userIdUtenteConnesso);
			proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
			proforma.setOperationTimestamp(new Date());
			proformaDAO.modifica(proforma);
			return proforma.getStatoProforma().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO);
		}

		//DARIO C **************************************************************************************************
//		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
//				primoRiporto = true;
//				respPrimoRiporto = true;
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
//				else
//					//calcolo lo step da mandare in assegnazione
//					configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
//				if(primoRiporto || respPrimoRiporto)
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ ////non c'è alcuno step successivo, per cui mi limito ad aggiornare lo stato del proforma in Autorizzato
			
			//aggiorno il proforma
			proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			proforma.setDataUltimaModifica(dataCreazione);
			proforma.setDataAutorizzazione(dataCreazione);
			proforma.setAutorizzatore(userIdUtenteConnesso);
			proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
			proforma.setOperationTimestamp(new Date());
			proformaDAO.modifica(proforma);
			return proforma.getStatoProforma().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO);

		}
		//creo l'istanza di workflow
		proformaWfSalvato = proformaWfDAO.avviaWorkflow(proformaWf);  
		
		//creo lo step
		creaStepWf(proformaWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		//aggiorno il proforma
//		if(configurazioneStart == null)
//			proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(configurazione.getStateFrom(), CostantiDAO.LINGUA_ITALIANA));
//		else
		proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			
		proforma.setDataUltimaModifica(dataCreazione);
		proforma.setDataRichAutorizzazione(dataCreazione);

		proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
		proforma.setOperationTimestamp(new Date());
		proformaDAO.modifica(proforma);
		
		return proformaWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}

	/**
	  * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idProformaWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idProformaWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		ProformaWf proformaWf = null;
		StepWf stepCorrente = null;
		Proforma proformaCorrelato = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		proformaWf = proformaWfDAO.leggiWorkflow(idProformaWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!proformaWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + proformaWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idProformaWf, CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza ProformaWf
		
		proformaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		proformaWf.setDataChiusura(dataRifiuto);
		proformaWfDAO.modifica(proformaWf);
		
		//recupero il proforma
		proformaCorrelato = proformaWf.getProforma();
		proformaCorrelato.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		//proformaCorrelato.setDataUltimaModifica(dataRifiuto);
		proformaCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		proformaCorrelato.setOperationTimestamp(new Date());
		proformaDAO.modifica(proformaCorrelato);
		
		return proformaWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idProformaWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C ************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idProformaWf, String userIdUtenteConnesso) throws Throwable {
		return avanzaWorkflow(idProformaWf, userIdUtenteConnesso, "");
	}
	//********************************************************************************************************	
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idProformaWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		//aggiorno l'occorrenza ProformaWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean respTop = false;
		
		ProformaWf proformaWf = null;
		Proforma proformaCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		//recuper l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		//recupero il workflow
		proformaWf = proformaWfDAO.leggiWorkflow(idProformaWf);

		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!proformaWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + proformaWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!proformaWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + proformaWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero il proforma
		proformaCorrelato = proformaWf.getProforma();
		
			
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idProformaWf, CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		
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
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_PROFORMA).getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_PROFORMA).getId(), CostantiDAO.LINGUA_ITALIANA);
		
		//calcolo lo step da mandare in assegnazione
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //Workflow finito
			
			//aggiorno l'occorrenza ProformaWf
			proformaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
			proformaWf.setDataChiusura(dataAvanzamento);
			proformaWfDAO.modifica(proformaWf);
			
			//aggiorno il proforma
			proformaCorrelato.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			//proformaCorrelato.setDataUltimaModifica(dataAvanzamento);
			proformaCorrelato.setDataAutorizzazione(dataAvanzamento);
			proformaCorrelato.setAutorizzatore(userIdUtenteConnesso);
			proformaCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			proformaCorrelato.setOperationTimestamp(new Date());
			proformaDAO.modifica(proformaCorrelato);
		
		}
		else{

			//DARIO C ************************************************************************************
			if (matricolaDestinatario.trim().length()!=0){
				proformaWf.setUtenteAssegnatario(matricolaDestinatario);
				proformaWfDAO.modifica(proformaWf);
			}
			//********************************************************************************************
			
			//creo lo step
			creaStepWf(proformaWf,configurazione, dataAvanzamento, utenteConnesso);
			
			//aggiorno il proforma
			proformaCorrelato.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			proformaCorrelato.setDataUltimaModifica(dataAvanzamento);
			proformaCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			proformaCorrelato.setOperationTimestamp(new Date());
			proformaDAO.modifica(proformaCorrelato);
		}
		
		/**
		 * Richiamo il Web Service per l'invio delle informazioni del proforma
		 * verso Lucy se il proforma è nello stato autorizzato
		 * @author MASSIMO CARUSO
		 */
		if(proformaWf.getStatoWf().getCodGruppoLingua().equals(CostantiDAO.STATO_WF_COMPLETATO)){
			//invoco il servizio di richiesta verso Lucy
			inviaRichiestaLucy(proformaCorrelato);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	 * Crea un nuovo steo workflow
	 *
	 * @param proformaWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(ProformaWf proformaWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setProformaWf(proformaWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idProforma: identificativo del proforma
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	@Override
	public ProformaWfView leggiWorkflowInCorso(long idProforma) throws Throwable {
		ProformaWf wf = proformaWfDAO.leggiWorkflowInCorso(idProforma);
		if(wf != null)
			return (ProformaWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	
	public ProformaWf leggiWorkflowInCorsoNotView(long idProforma) throws Throwable {
		ProformaWf wf = proformaWfDAO.leggiWorkflowInCorso(idProforma);
		if(wf != null)
			return wf;
		else
			return null;
	}
	
	
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idProforma: identificativo del proforma
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	@Override
	public ProformaWfView leggiUltimoWorkflowTerminato(long idProforma) throws Throwable {
		ProformaWf wf = proformaWfDAO.leggiUltimoWorkflowTerminato(idProforma);
		if(wf != null)
		return (ProformaWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idProforma: identificativo del proforma
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	@Override
	public ProformaWfView leggiUltimoWorkflowRifiutato(long idProforma) throws Throwable {
		ProformaWf wf = proformaWfDAO.leggiUltimoWorkflowRifiutato(idProforma);
		if(wf != null)
		return (ProformaWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto ProformaWfView.
	 * @exception Throwable
	 */
	@Override
	public ProformaWfView leggiWorkflow(long id) throws Throwable {
		ProformaWf wf = proformaWfDAO.leggiWorkflow(id);
		return (ProformaWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idProforma identificativo del proforma
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idProforma, String userIdUtenteConnesso) throws Throwable {

		ProformaWfView workflowCorrente = null;
		ProformaWf proformaWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		Proforma proforma = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il proforma correlato
		proforma = proformaDAO.leggi(idProforma);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idProforma);
		proformaWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno il proforma
			proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			proforma.setDataUltimaModifica(dataDiscard);
			proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
			proforma.setOperationTimestamp(new Date());
			proformaDAO.modifica(proforma);
		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
		
			proformaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			proformaWf.setDataChiusura(dataDiscard);
			proformaWfDAO.modifica(proformaWf);
			
			//riporto il proforma in Bozza
			proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			proforma.setDataUltimaModifica(dataDiscard);
			proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
			proforma.setOperationTimestamp(new Date());
			proformaDAO.modifica(proforma);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idProforma identificativo del proforma
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idProforma, String userIdUtenteConnesso) throws Throwable {

		ProformaWfView workflowCorrente = null;
		ProformaWf proformaWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		Proforma proforma = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il proforma correlato
		proforma = proformaDAO.leggi(idProforma);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idProforma);
		if(workflowCorrente != null){
			
			proformaWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		}
		else{
			
			terminato = true;
			workflowCorrente = leggiUltimoWorkflowTerminato(idProforma);
			if(workflowCorrente != null){
				proformaWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza il proforma
			{
				proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				proforma.setDataUltimaModifica(dataDiscard);
				proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
				proforma.setOperationTimestamp(new Date());
				proformaDAO.modifica(proforma);
				proforma.setDataAutorizzazione(null);
				return (proforma.getStatoProforma().getId() == anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());

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
		
		proformaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		proformaWf.setDataChiusura(dataDiscard);
		proformaWfDAO.modifica(proformaWf);
		
		//riporto il proforma in Bozza
		proforma.setStatoProforma(anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		proforma.setDataUltimaModifica(dataDiscard);
		proforma.setDataAutorizzazione(null);
		proforma.setOperation(AbstractEntity.UPDATE_OPERATION);
		proforma.setOperationTimestamp(new Date());
		proformaDAO.modifica(proforma);
		
		return (proforma.getStatoProforma().getId() == anagraficaStatiTipiDAO.leggiStatoProforma(CostantiDAO.PROFORMA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	/**
	 * Genera una nuova richiesta di invio dati del proforma
	 * verso il servizio Lucy.
	 * @param proforma i dati del proforma da inviare.
	 * @author MASSIMO CARUSO
	 */
	private void inviaRichiestaLucy(Proforma proforma){
		
		System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
		InputStream is = null;
		String endpoint = null;
		try {
			is = ProformaWfServiceImpl.class.getResourceAsStream("/config.properties");
			Properties properties = new Properties();
			properties.load(is);
			endpoint = properties.getProperty("fatture.link");
		}
		catch (Exception e) {
			System.out.println("Errore durante la lettura delle properties per endpoint service di lucy.\n"+ e.getMessage());
			e.printStackTrace();
		} 
		
		
		
		SOAPConnectionFactory connection_factory = null;
		
		SOAPConnection connection = null;
		SOAPMessage messageToSend = null;
		SOAPMessage messageToReceive = null;
		
		try{
			
			connection_factory = SOAPConnectionFactory.newInstance();
			
			connection = connection_factory.createConnection();
			messageToSend = generaMessaggioSOAP(proforma);
			
			messageToReceive = connection.call(messageToSend, endpoint);
				        
			connection.close();
			
			//verifica dell'esito dell'operazione
			SOAPBody received_body = messageToReceive.getSOAPBody();
			NodeList status_list = received_body.getElementsByTagName("status");
			Node status = status_list.item(0);
			String status_value = status.getNodeValue();
			
			if(!status_value.equals("OK")){
		
				NodeList message_list = received_body.getElementsByTagName("message");
				Node message = message_list.item(0);
				String message_value = message.getNodeValue();
				System.out.println("Errore durante l'invio delle informazioni del proforma con id ["+proforma.getId()+"].\n"+message_value);
			
			}
			
		}catch(SOAPException e){
			
			System.out.println("Errore SOAP durante l'invio dati verso il servizio Lucy.\n"+e.getMessage());
			e.printStackTrace();
		
		}catch(Exception e){
			
			System.out.println("Errore durante l'invio dati verso il servizio Lucy.\n"+e.getMessage());
			e.printStackTrace();
		
		}
	}

	/**
	 * Crea il messaggio SOAP di richiesta per il servizio Lucy.
	 * @param proforma le informazioni del proforma da inviare.
	 * @return il messaggio generato, null altrimenti.
	 * @author MASSIMO CARUSO
	 */
	private SOAPMessage generaMessaggioSOAP(Proforma proforma){
		
		String soapenv = null;
		String namespace = null;
		String uri = null;
		String service = null;
		MessageFactory message_factory = null;
		SOAPMessage message = null;
		SOAPPart part = null;
		SOAPEnvelope envelope = null;
		SOAPHeader header = null;
		SOAPBody body = null;
		SOAPElement root = null;
		SOAPElement arg0 = null;
		SOAPElement PROFORMAID = null;
		SOAPElement ESERCIZIO = null;
		SOAPElement PIVACLI = null;
		SOAPElement DATAAUT = null;
		SOAPElement AUTORIZZATORE = null;
		SOAPElement IMPORTOAUT = null;
		SOAPElement NOTE = null;
		SOAPElement CONTO = null;
		SOAPElement CENTRO_DI_COSTO = null;	
		
		try{
			
			soapenv = "soapenv";
			service = "sendLucyRequest";
			namespace = "ser";
			uri = "http://service.ibm.com/";
			message_factory = MessageFactory.newInstance("SOAP 1.1 Protocol");
			message = message_factory.createMessage();
			part = message.getSOAPPart();
			
			envelope = part.getEnvelope();
			envelope.removeNamespaceDeclaration(envelope.getPrefix());
	        envelope.setPrefix("soapenv");
			envelope.addNamespaceDeclaration(namespace, uri);
			
			header = envelope.getHeader();
			header.removeNamespaceDeclaration(header.getPrefix());
			header.setPrefix("soapenv");
			
			body = envelope.getBody();
			body.removeNamespaceDeclaration(body.getPrefix());
			body.setPrefix("soapenv");
			
			root = body.addChildElement(service, namespace);
			arg0 = root.addChildElement("arg0");
			PROFORMAID = arg0.addChildElement("PROFORMAID");
			ESERCIZIO = arg0.addChildElement("ESERCIZIO");
			PIVACLI = arg0.addChildElement("PIVACLI");
			DATAAUT = arg0.addChildElement("DATAAUT");
			AUTORIZZATORE = arg0.addChildElement("AUTORIZZATORE");
			IMPORTOAUT = arg0.addChildElement("IMPORTOAUT");
			NOTE = arg0.addChildElement("NOTE");
			CONTO = arg0.addChildElement("CONTO");
			CENTRO_DI_COSTO = arg0.addChildElement("CENTRO_DI_COSTO");
			PROFORMAID.addTextNode(proforma.getId()+"");
			ESERCIZIO.addTextNode(proforma.getAnnoEsercizioFinanziario()+"");
			PIVACLI.addTextNode("0000000000000000");
			DATAAUT.addTextNode(proforma.getDataAutorizzazione().toString());
			AUTORIZZATORE.addTextNode(proforma.getAutorizzatore());
			IMPORTOAUT.addTextNode(proforma.getTotaleAutorizzato()+"");
			NOTE.addTextNode(proforma.getNote());
			CONTO.addTextNode(proforma.getVoceDiConto());
			CENTRO_DI_COSTO.addTextNode(proforma.getCentroDiCosto());
			message.saveChanges();		

		}catch(SOAPException e){
		
			System.out.println("Errore durante la generazione del messaggio SOAP di richiesta del servizio Lucy.\n"+e.getMessage());
			e.printStackTrace();
			return null;
		
		}
		
		return message;
	}
}
