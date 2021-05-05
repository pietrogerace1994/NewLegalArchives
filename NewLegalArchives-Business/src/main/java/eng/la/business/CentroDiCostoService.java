package eng.la.business;

import java.util.List;

import eng.la.model.view.CentroDiCostoView;
import eng.la.util.ListaPaginata;

public interface CentroDiCostoService {
	public List<CentroDiCostoView> leggi() throws Throwable;

	public CentroDiCostoView leggi(long id) throws Throwable;

	public ListaPaginata<CentroDiCostoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;
	
	public List<CentroDiCostoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale) throws Throwable;
	
	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale) throws Throwable;

	public CentroDiCostoView inserisci(CentroDiCostoView centroDiCostoView) throws Throwable;

	public void modifica(CentroDiCostoView centroDiCostoView) throws Throwable;

	public void cancella(CentroDiCostoView centroDiCostoView) throws Throwable;

	public List<CentroDiCostoView> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long societaid, long settoreGiuridicoId, long tipologiaFascicoloId)throws Throwable;;
}
