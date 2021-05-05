package eng.la.persistence;

import java.text.ParseException;
//import java.util.Date;
import java.util.List;
import eng.la.model.DueDiligence;
import eng.la.model.view.DueDiligenceView;

public interface DueDiligenceDAO {
	// set di costanti
	public static char ASC = 'A';
	public static char DESC = 'D';

	public void cancella(long id) throws Throwable;
	public DueDiligence inserisci(DueDiligence vo) throws Throwable;
	public void modifica(DueDiligence vo) throws Throwable;

	public DueDiligence leggi(long id) throws Throwable;
	public List<DueDiligence> leggi() throws Throwable;	
	public List<DueDiligence> cerca(DueDiligence vo) throws Throwable;
	public List<DueDiligence> cerca(long idProfEsterno) throws Throwable;
	public List<DueDiligence> leggi(char ordinaDataChiusura) throws Throwable;	
	public List<Object> elencoAnni(long idProfEsterno) throws Throwable;
	public List<DueDiligence> elenco(long idProfEsterno, int anno) throws Throwable;
	public List<DueDiligence> leggi(boolean isActive) throws Throwable;
	public DueDiligence deleteDueDiligence(long id) throws Throwable;
	public List<DueDiligence> searchRichAutGiudByFilter(DueDiligenceView dueDiligenceView) throws ParseException;
}


