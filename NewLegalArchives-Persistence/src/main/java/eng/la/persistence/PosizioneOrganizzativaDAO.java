package eng.la.persistence;

import java.util.List;

import eng.la.model.PosizioneOrganizzativa;

public interface PosizioneOrganizzativaDAO {
	
	public List<PosizioneOrganizzativa> leggi(String lingua) throws Throwable;
	
	public PosizioneOrganizzativa leggi(long id) throws Throwable;
	
	public PosizioneOrganizzativa leggi(String code, String lingua) throws Throwable;

}
