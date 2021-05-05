package eng.la.business;

import java.util.List;

import eng.la.model.view.AutorizzazioneView;

public interface AutorizzazioneService {
	
	public AutorizzazioneView leggiAutorizzazioneUtenteCorrente(long idEntita, String nomeClasse) throws Throwable;

	public boolean isAutorizzato(Long idEntita, String tipoEntita, String tipoPermesso);
	
	public List<AutorizzazioneView> leggiAutorizzazioni(long idEntita, String nomeClasse) throws Throwable;
	public List<AutorizzazioneView> leggiAutorizzazioni2(long idEntita, String nomeClasse) throws Throwable;
	public List<String> leggiAutorizzati(List<Long> idEntitas, String nomeClasse) throws Throwable;
}
