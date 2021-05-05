package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.CentroDiCosto;
import eng.la.model.view.CentroDiCostoView;
import eng.la.persistence.CentroDiCostoDAO;
import eng.la.util.ListaPaginata;

@Service("centroDiCostoService")
public class CentroDiCostoServiceImpl extends BaseService<CentroDiCosto,CentroDiCostoView> implements CentroDiCostoService {

	@Autowired
	private CentroDiCostoDAO centroDiCostoServiceDAO;

	public void setDao(CentroDiCostoDAO dao) {
		this.centroDiCostoServiceDAO = dao;
	}

	public CentroDiCostoDAO getDao() {
		return centroDiCostoServiceDAO;
	}

	@Override
	public List<CentroDiCostoView> leggi() throws Throwable {
		List<CentroDiCosto> lista = centroDiCostoServiceDAO.leggi();
		List<CentroDiCostoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public CentroDiCostoView leggi(long id) throws Throwable {
		CentroDiCosto centroDiCosto = centroDiCostoServiceDAO.leggi(id);
		return convertiVoInView(centroDiCosto);
	}

	@Override
	public ListaPaginata<CentroDiCostoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<CentroDiCosto> lista = centroDiCostoServiceDAO.cerca(tipologiaFascicoloId, settoreGiuridicoId, societaId,
				unitaLegale, elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		List<CentroDiCostoView> listaView = convertiVoInView(lista);
		ListaPaginata<CentroDiCostoView> listaRitorno = new ListaPaginata<CentroDiCostoView>();
		Long conta = centroDiCostoServiceDAO.conta(tipologiaFascicoloId, settoreGiuridicoId, societaId, unitaLegale);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<CentroDiCostoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale) throws Throwable {
		List<CentroDiCosto> lista = centroDiCostoServiceDAO.cerca(tipologiaFascicoloId, settoreGiuridicoId, societaId,
				unitaLegale);
		List<CentroDiCostoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale)
			throws Throwable {
		return centroDiCostoServiceDAO.conta(tipologiaFascicoloId, settoreGiuridicoId, societaId, unitaLegale);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public CentroDiCostoView inserisci(CentroDiCostoView centroDiCostoView) throws Throwable {
		CentroDiCosto centroDiCosto = centroDiCostoServiceDAO.inserisci(centroDiCostoView.getVo());
		CentroDiCostoView view = new CentroDiCostoView();
		view.setVo(centroDiCosto);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(CentroDiCostoView centroDiCostoView) throws Throwable {
		centroDiCostoServiceDAO.modifica(centroDiCostoView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(CentroDiCostoView centroDiCostoView) throws Throwable {
		centroDiCostoServiceDAO.cancella(centroDiCostoView.getVo().getId());
	}
	
	@Override
	public List<CentroDiCostoView> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long societaId,
			long settoreGiuridicoId, long tipologiaFascicoloId) throws Throwable {
		List<CentroDiCosto> lista = centroDiCostoServiceDAO.leggiDaUnitaLegaleSettoreTipologiaFascicolo( unitaLegale, societaId,
				 settoreGiuridicoId,  tipologiaFascicoloId);
		List<CentroDiCostoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<CentroDiCosto> leggiClassVO() {
		return CentroDiCosto.class;
	}

	@Override
	protected Class<CentroDiCostoView> leggiClassView() {
		return CentroDiCostoView.class;
	}

}
