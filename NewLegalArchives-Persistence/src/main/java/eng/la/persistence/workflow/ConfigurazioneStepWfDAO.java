package eng.la.persistence.workflow;

import java.util.List;

import eng.la.model.ConfigurazioneStepWf;

public interface ConfigurazioneStepWfDAO {

	public ConfigurazioneStepWf leggiConfigurazioneStepCorrente(long idObject, long idClasseWf, String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneStepSuccessivo(long idClasseWf, String stateFromEffettivo, Boolean respPrimoRiporto, String lingua) throws Throwable;
//	public ConfigurazioneStepWf leggiConfigurazionePrimoStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, String matricola) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, Boolean topResponsabile, String matricola, String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazionePrimoStepFascicolo(String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazione(long id) throws Throwable;
	
	
	public ConfigurazioneStepWf leggiConfigurazioneManuale(long idClasseWf, String matricola, String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneSuccessivaStandard(ConfigurazioneStepWf configurazione, boolean primoRiporto, boolean respPrimoRiporto, String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneSuccessivaStandard(ConfigurazioneStepWf configurazione, boolean primoRiporto, boolean respPrimoRiporto, boolean amministratore, String lingua) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneStepNumeroUno(long idClasseWf, String lingua)throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazioneStep(long idClasseWf, String stateFrom, String stateTo, String direzione, String lingua)throws Throwable;

	public ConfigurazioneStepWf leggiConfigurazioneTradotta(String code, String lingua)throws Throwable;
	
	public List<ConfigurazioneStepWf> leggiConfigurazioni(long idClasseWf,String idMatricolaUtil,String lingua,String tipoAssegnazione) throws Throwable;
	public ConfigurazioneStepWf leggiConfigurazione(long idClasseWf,String idMatricolaUtil,String lingua,String tipoAssegnazione) throws Throwable;
	
	public ConfigurazioneStepWf leggiAutorizzatore() throws Throwable;
	public ConfigurazioneStepWf leggiApprovatore() throws Throwable;
	public void aggiornaMatricolaById(Long id, String matricola) throws Throwable;
	public void aggiornaEmailById(Long id, String email) throws Throwable;
	public ConfigurazioneStepWf leggiAutorizzatoreRigaIdWf(long numWf) throws Throwable;
	public ConfigurazioneStepWf leggiApprovatoreRigaIdWf(long numWf) throws Throwable;
}
