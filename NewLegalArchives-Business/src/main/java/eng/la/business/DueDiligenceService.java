package eng.la.business;

import java.util.List;

import eng.la.model.DocumentoDueDiligence;
import eng.la.model.DueDiligence;
import eng.la.model.view.DueDiligenceView;

public interface DueDiligenceService {
	public void cancella(long id) throws Throwable;
	public DueDiligenceView inserisci(DueDiligenceView dueDiligence) throws Throwable;
	public void modifica(DueDiligenceView dueDiligence) throws Throwable;
	public DueDiligenceView inserisci(DueDiligenceView dueDiligence,byte[] documento) throws Throwable;
	public void modifica(DueDiligenceView dueDiligence,byte[] documento) throws Throwable;
	
	public List<DueDiligenceView> cerca(DueDiligenceView view) throws Throwable;
	public DueDiligenceView leggi(long id) throws Throwable;
	public List<DueDiligenceView> leggi(char ordinaDataChiusura) throws Throwable;
	public List<DueDiligenceView> leggi() throws Throwable;
	public List<DueDiligenceView> leggiOrdASC() throws Throwable;
	public List<DueDiligenceView> leggiOrdDESC() throws Throwable;
	public List<Object> elencoAnni(long idProfEsterno) throws Throwable;
	public List<DueDiligenceView> elenco(long idProfEsterno,int anno) throws Throwable;
	public void modificaVo(DueDiligence dueDiligence) throws Throwable;
	public DueDiligence addStep1Document(DueDiligence dueDiligence, long documentoStep1Id) throws Throwable;
	public DocumentoDueDiligence addStep2Document(DueDiligence dueDiligence, long documentoStep2Id) throws Throwable;
	public DueDiligence addStep3Document(DueDiligence dueDiligence, long documentoStep3Id) throws Throwable;
	public void deleteStep2Document(long documentoDueDiligenceId) throws Throwable;
	public void deleteDueDiligence(long id) throws Throwable;
	public List<DueDiligenceView> searchDueDiligenceByFilter(DueDiligenceView dueDiligenceView) throws Throwable;
}