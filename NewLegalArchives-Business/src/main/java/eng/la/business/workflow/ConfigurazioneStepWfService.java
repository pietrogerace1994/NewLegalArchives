package eng.la.business.workflow;

import java.util.List;

import eng.la.model.view.ConfigurazioneStepWfView;

public interface ConfigurazioneStepWfService {
	
	public ConfigurazioneStepWfView leggiConfigurazioneStepCorrente(long idObject, long idClasse, String lingua) throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazioneStepSuccessivo(long idClasseWf, String stateFrom, Boolean respPrimoRiporto, String lingua) throws Throwable;
//	public ConfigurazioneStepWfView leggiConfigurazionePrimoStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, String matricola) throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazioneStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, Boolean topResponsabile, String matricola, String lingua) throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazionePrimoStepFascicolo(String lingua) throws Throwable;
	
	public ConfigurazioneStepWfView leggiConfigurazioneManuale(long idClasseWf, String matricola, String lingua) throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazioneSuccessivaStandard(long idConfigurazione, boolean primoRiporto, boolean respPrimoRiporto, String lingua) throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazioneStepNumeroUno(long idClasseWf)throws Throwable;
	public ConfigurazioneStepWfView leggiConfigurazioneTradotta(String code, String lingua)throws Throwable;
	
	public ConfigurazioneStepWfView leggiConfigurazione(long idClasseWf, String idMatricolaUtil, String lingua, String tipoAssegnazione) throws Throwable;
	public List<ConfigurazioneStepWfView> leggiConfigurazioni(long idClasseWf, String idMatricolaUtil, String lingua, String tipoAssegnazione) throws Throwable;
	public ConfigurazioneStepWfView leggiAutorizzatore() throws Throwable;
	public ConfigurazioneStepWfView leggiApprovatore() throws Throwable;
	public void aggiornaMatricolaById(long id, String matricola) throws Throwable;
	public void aggiornaEmailById(long id, String email) throws Throwable;
	public ConfigurazioneStepWfView leggiAutorizzatoreRigaIdWf(long numWf) throws Throwable;
	public ConfigurazioneStepWfView leggiApprovatoreRigaIdWf(long numWf) throws Throwable;
}
