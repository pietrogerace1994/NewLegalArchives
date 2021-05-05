package eng.la.persistence;

import java.util.List;

import eng.la.model.StudioLegale;

public interface StudioLegaleDAO {

	public List<StudioLegale> leggi() throws Throwable;

	public StudioLegale leggi(long id) throws Throwable;

	public List<StudioLegale> cerca(String denominazione, long nazioneId, String codiceSap) throws Throwable;

	public List<StudioLegale> cerca(String denominazione, long nazioneId, String codiceSap, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public StudioLegale inserisci(StudioLegale vo) throws Throwable;

	public StudioLegale modifica(StudioLegale vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String denominazione, long nazioneId, String codiceSap) throws Throwable;

}
