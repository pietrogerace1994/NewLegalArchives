package eng.la.business;

import java.util.List;

import eng.la.model.view.SchedaValutazioneView;

public interface SchedaValutazioneService {
	public void cancella(long id) throws Throwable;
	public SchedaValutazioneView inserisci(SchedaValutazioneView evento) throws Throwable;
	public void modifica(SchedaValutazioneView schedaValutazione) throws Throwable;	
	public List<SchedaValutazioneView> cerca(SchedaValutazioneView view) throws Throwable;
	public SchedaValutazioneView leggi(long id) throws Throwable;
	public List<SchedaValutazioneView> leggi() throws Throwable;
	// extra
}