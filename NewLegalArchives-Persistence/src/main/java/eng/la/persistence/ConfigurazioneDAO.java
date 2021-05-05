package eng.la.persistence;

public interface ConfigurazioneDAO {
	String leggiValore(String key) throws Throwable;

	void aggiornaMatricolaTopResponsabile(String matricolaAutorizzatore) throws Throwable;
}
