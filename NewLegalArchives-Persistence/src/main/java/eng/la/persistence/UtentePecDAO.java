package eng.la.persistence;

import java.util.List;

import eng.la.model.UtentePec;

public interface UtentePecDAO {

	public UtentePec leggi(Long id) throws Throwable;
	
	public List<UtentePec> leggi(String userId) throws Throwable;
	
	public void riportaPec(Long id) throws Throwable;
	
	public void annullaPec(Long id) throws Throwable;
	
	public void trasformaPec(Long id) throws Throwable;
	
	public void spostProtPec(Long id) throws Throwable;
	
	public void inviaAltriUffPec(Long id, String utenteAltriUff, String emailAltriUff) throws Throwable;
	
}
