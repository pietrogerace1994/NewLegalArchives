package eng.la.persistence;

import java.util.List;

import eng.la.model.VoceDiConto;

public interface VoceDiContoDAO {

	public List<VoceDiConto> leggi() throws Throwable;

	public VoceDiConto leggi(long id) throws Throwable;

	public List<VoceDiConto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<VoceDiConto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale)
			throws Throwable;

	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale) throws Throwable;

	public VoceDiConto inserisci(VoceDiConto vo) throws Throwable;

	public void modifica(VoceDiConto vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public List<VoceDiConto> leggiDaUnitaLegaleSettoreTipologiaFascicolo(long tipologiaFascicoloId,
			long settoreGiuridicoId, String unitaLegale)throws Throwable;

}
