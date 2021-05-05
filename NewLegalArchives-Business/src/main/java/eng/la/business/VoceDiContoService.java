package eng.la.business;

import java.util.List;

import eng.la.model.view.VoceDiContoView;
import eng.la.util.ListaPaginata;

public interface VoceDiContoService {
	public List<VoceDiContoView> leggi() throws Throwable;

	public VoceDiContoView leggi(long id) throws Throwable;

	public ListaPaginata<VoceDiContoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<VoceDiContoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale)
			throws Throwable;

	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId,  String unitaLegale) throws Throwable;

	public VoceDiContoView inserisci(VoceDiContoView voceDiContoView) throws Throwable;

	public void modifica(VoceDiContoView voceDiContoView) throws Throwable;

	public void cancella(VoceDiContoView voceDiContoView) throws Throwable;

	public List<VoceDiContoView> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long settoreGiuridicoId, long tipologiaFascicoloId)throws Throwable;
}
