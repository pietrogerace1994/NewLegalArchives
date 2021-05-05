package eng.la.business.workflow;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;

import eng.la.business.BaseService;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.view.ConfigurazioneStepWfView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;

/**
 * <h1>Classe di business ConfigurazioneStepWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di lettura
 * sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author 
 * @version 1.0
 * @since 2016-07-01
 */
@Service("configurazioneStepWfService")
public class ConfigurazioneStepWfServiceImpl extends BaseService implements ConfigurazioneStepWfService{

	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
	

	/**
	 * Metodo di lettura dello step corrente del workflow.
	 * <p>
	 * @param idObject: identificativo dell'istanza di oggetto corrente
	 * @param idClasseWf: identificativo della classe del workflow
	 * @return oggetto ConfigurazioneStepWfView
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneStepCorrente(long idObject, long idClasse, String lingua) throws Throwable {
		// TODO Auto-generated method stub
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepCorrente(idObject, idClasse, lingua);
	
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	}
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * <p>
	 * @param dao oggetto della classe ConfigurazioneStepWfDAO
	 * @see ConfigurazioneStepWfDAO
	 */
	public void setConfigurazioneStepWfDAO(ConfigurazioneStepWfDAO configurazioneStepWfDAO) {
		this.configurazioneStepWfDAO = configurazioneStepWfDAO;
	}

	/**
	 * Metodo di lettura del successivo step del workflow.
	 * <p>
	 * @param idClasseWf: identificativo della classe del workflow
	 * @param stateFrom: nome dello step corrente del workflow
	 * @param respPrimoRiporto: booleano che indica se il responsabile dell'assegnatario attuale dello step è un responsabile di primo riporto
	 * @return oggetto ConfigurazioneStepWfView
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneStepSuccessivo(long idClasseWf, String stateFrom, Boolean respPrimoRiporto, String lingua)
			throws Throwable {
		
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepSuccessivo(idClasseWf, stateFrom, respPrimoRiporto, lingua);
	
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	
	}
	
//	/**
//	 * Metodo di lettura del primo step del workflow.
//	 * <p>
//	 * @param idClasseWf: identificativo della classe del workflow
//	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
//	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
//	 * @param matricola: matricola del creatore dello step
//	 * @return oggetto ConfigurazioneStepWfView.
//	 * @exception Throwable
//	 */
//	@Override
//	public ConfigurazioneStepWfView leggiConfigurazionePrimoStep(long idClasseWf, Boolean primoRiporto,
//			Boolean respPrimoRiporto, String matricola) throws Throwable {
//		@SuppressWarnings("rawtypes")
//		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazionePrimoStep(idClasseWf, primoRiporto, respPrimoRiporto, matricola);
//		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
//	}
	
	/**
	 * Metodo di lettura dello step del workflow.
	 * <p>
	 * @param idClasseWf: identificativo della classe del workflow
	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
	 * @param matricola: matricola del creatore dello step
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneStep(long idClasseWf, Boolean primoRiporto,
			Boolean respPrimoRiporto, Boolean topResponsabile, String matricola, String lingua) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(idClasseWf, primoRiporto, respPrimoRiporto, topResponsabile, matricola, lingua);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	}
	
	@Override
	public List<ConfigurazioneStepWfView> leggiConfigurazioni(long idClasseWf, String idMatricolaUtil, String lingua, String tipoAssegnazione) throws Throwable {
		@SuppressWarnings("rawtypes")
		List<ConfigurazioneStepWf> configurazioni = configurazioneStepWfDAO.leggiConfigurazioni(idClasseWf, idMatricolaUtil, lingua, tipoAssegnazione);
		return (List<ConfigurazioneStepWfView>) convertiVoInView(configurazioni);
	}
	
	@Override
	public ConfigurazioneStepWfView leggiConfigurazione(long idClasseWf, String idMatricolaUtil, String lingua, String tipoAssegnazione) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioni = configurazioneStepWfDAO.leggiConfigurazione(idClasseWf, idMatricolaUtil, lingua, tipoAssegnazione);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioni);
	}
	
	@Override
	public ConfigurazioneStepWfView leggiAutorizzatore() throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioni = configurazioneStepWfDAO.leggiAutorizzatore();
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioni);
	}
	
	@Override
	public ConfigurazioneStepWfView leggiApprovatore() throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioni = configurazioneStepWfDAO.leggiApprovatore();
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioni);
	}
	
	@Override
	public ConfigurazioneStepWfView leggiAutorizzatoreRigaIdWf(long numWf) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioni = configurazioneStepWfDAO.leggiAutorizzatoreRigaIdWf(numWf);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioni);
	}

	@Override
	public ConfigurazioneStepWfView leggiApprovatoreRigaIdWf(long numWf) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioni = configurazioneStepWfDAO.leggiApprovatoreRigaIdWf(numWf);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioni);
	}
	
	/**
	 * Metodo di lettura del primo step del workflow chiusura fascicolo.
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazionePrimoStepFascicolo(String lingua) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazionePrimoStepFascicolo(lingua);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	}
	
	/**
	 * Metodo di lettura dello step del workflow.
	 * <p>
	 * @param idClasseWf: identificativo della classe del workflow
	 * @param matricola: matricola del creatore dello step
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneManuale(long idClasseWf, String matricola, String lingua) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneManuale(idClasseWf, matricola, lingua);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	}
	
	/**
	 * Metodo di lettura dello step del workflow.
	 * <p>
	 * @param idConfigurazione: identificativo della configurazione dello step in corso
	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneSuccessivaStandard(long idConfigurazione, boolean primoRiporto, boolean respPrimoRiporto, String lingua) throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazioneCorrente = configurazioneStepWfDAO.leggiConfigurazione(idConfigurazione);
		ConfigurazioneStepWf configurazioneSuccessiva = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneCorrente,  primoRiporto, respPrimoRiporto, lingua);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazioneSuccessiva);
	}
	
	/**
	 * Metodo di lettura dello step numero uno del workflow relativo alla classe in input .
	 * <p>
	 * @param idClasseWf: identificativo della classe del workflow
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneStepNumeroUno(long idClasseWf)throws Throwable {
		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(idClasseWf, "IT");
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	}
	
	/**
	 * Metodo di lettura della configurazione nella lingua corrente a partire da quella italiana.
	 * <p>
	 * @param code: codice lingua
	 * @param lingua: lingua di ricerca
	 * @return oggetto ConfigurazioneStepWfView.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWfView leggiConfigurazioneTradotta(String code, String lingua) throws Throwable {

		@SuppressWarnings("rawtypes")
		ConfigurazioneStepWf configurazione = configurazioneStepWfDAO.leggiConfigurazioneTradotta(code, lingua);
		return (ConfigurazioneStepWfView) convertiVoInView(configurazione);
	

	}
	
	@Override
	public void aggiornaMatricolaById(long id, String matricola) throws Throwable {
		configurazioneStepWfDAO.aggiornaMatricolaById(id, matricola);		
	}
	
	@Override
	public void aggiornaEmailById(long id, String email) throws Throwable {
		configurazioneStepWfDAO.aggiornaEmailById(id, email);		
	}
	
	// metodi extra
	@Override
	protected Class leggiClassVO() {
		return ConfigurazioneStepWf.class;
	}

	@Override
	protected Class leggiClassView() {
		return ConfigurazioneStepWfView.class;
	}

}
