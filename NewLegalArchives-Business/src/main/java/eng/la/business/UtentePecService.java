package eng.la.business;

import java.util.List;

import eng.la.model.view.UtentePecView;

public interface UtentePecService {
	
	public List<UtentePecView> leggiPecMail(String userId)throws Throwable;
	
	public UtentePecView leggi(Long id)throws Throwable;
	
	public void riportaPec(Long id)throws Throwable;
	
	public void annullaPec(Long id)throws Throwable;
	
	public void trasformaPec(Long id)throws Throwable;
	
	public void spostProtPec(Long id)throws Throwable;
	
	public void inviaAltriUffPec(Long id, String utenteAltriUff, String emailAltriUff)throws Throwable;
	
}
