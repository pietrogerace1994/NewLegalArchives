package eng.la.business;

import java.util.List;

import eng.la.model.view.SocietaView;
import eng.la.util.ListaPaginata;

public interface SocietaService {
	public List<SocietaView> leggi(boolean tutte) throws Throwable;

	public SocietaView leggi(long id) throws Throwable;

	public ListaPaginata<SocietaView> cerca(String ragioneSociale, long nazioneId, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<SocietaView> cerca(String ragioneSociale, long nazioneId) throws Throwable;
	
	public Long conta(String ragioneSociale, long nazioneId) throws Throwable;

	public SocietaView inserisci(SocietaView societaView) throws Throwable;

	public void modifica(SocietaView societaView) throws Throwable;

	public void cancella(SocietaView societaView) throws Throwable;

	public boolean controlla(SocietaView societaView) throws Throwable;

	public List<Long> getListaSNAM_SRG_GNL_STOGIT() throws Throwable;

}
