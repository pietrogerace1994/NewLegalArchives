package eng.la.persistence;

import java.util.List;

import eng.la.model.CentroDiCosto;

public interface CentroDiCostoDAO {

	public List<CentroDiCosto> leggi() throws Throwable;

	public CentroDiCosto leggi(long id) throws Throwable;

	public List<CentroDiCosto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale) throws Throwable;

	public List<CentroDiCosto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public CentroDiCosto inserisci(CentroDiCosto vo) throws Throwable;

	public void modifica(CentroDiCosto vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale)
			throws Throwable;

	public List<CentroDiCosto> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long societaId, long settoreGiuridicoId,
			long tipologiaFascicoloId)throws Throwable;


}
