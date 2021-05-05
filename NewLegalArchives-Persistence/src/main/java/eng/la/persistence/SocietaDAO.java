package eng.la.persistence;

import java.util.List;

import eng.la.model.Societa;

public interface SocietaDAO {
	public List<Societa> leggi(boolean tutte) throws Throwable;

	public Societa inserisci(Societa vo) throws Throwable;

	public List<Societa> cerca(String ragioneSociale, long nazioneId) throws Throwable;

	public List<Societa> cerca(String ragioneSociale, long nazioneId, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Societa leggi(long id) throws Throwable;

	public void modifica(Societa vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String ragioneSociale, long nazioneId) throws Throwable;

	public boolean controlla(String nome) throws Throwable;
	
	public Societa leggiSocietaAddebitoProforma(long id) throws Throwable;
	public List<Societa> getListaSocietaProformaProcessate(boolean processato) throws Throwable;

	public List<Societa> getListaSNAM_SRG_GNL_STOGIT() throws Throwable;
}
