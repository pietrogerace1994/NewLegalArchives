package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.VoceDiConto;
import eng.la.model.view.VoceDiContoView;
import eng.la.persistence.VoceDiContoDAO;
import eng.la.util.ListaPaginata;

@Service("voceDiContoService")
public class VoceDiContoServiceImpl extends BaseService<VoceDiConto,VoceDiContoView> implements VoceDiContoService {

	@Autowired
	private VoceDiContoDAO voceDiContoDAO;

	public void setDao(VoceDiContoDAO dao) {
		this.voceDiContoDAO = dao;
	}

	public VoceDiContoDAO getDao() {
		return voceDiContoDAO;
	}

	@Override
	public List<VoceDiContoView> leggi() throws Throwable {
		List<VoceDiConto> lista = voceDiContoDAO.leggi();
		List<VoceDiContoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public VoceDiContoView leggi(long id) throws Throwable {
		VoceDiConto voceDiConto = voceDiContoDAO.leggi(id);
		return (VoceDiContoView) convertiVoInView(voceDiConto);
	}

	@Override
	public ListaPaginata<VoceDiContoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<VoceDiConto> lista = voceDiContoDAO.cerca(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale,
				elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		List<VoceDiContoView> listaView = convertiVoInView(lista);
		ListaPaginata<VoceDiContoView> listaRitorno = new ListaPaginata<VoceDiContoView>();
		Long conta = voceDiContoDAO.conta(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<VoceDiContoView> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale)
			throws Throwable {
		List<VoceDiConto> lista = voceDiContoDAO.cerca(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale);
		List<VoceDiContoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale) throws Throwable {
		return voceDiContoDAO.conta(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public VoceDiContoView inserisci(VoceDiContoView voceDiContoView) throws Throwable {
		VoceDiConto voceDiConto = voceDiContoDAO.inserisci(voceDiContoView.getVo());
		VoceDiContoView view = new VoceDiContoView();
		view.setVo(voceDiConto);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(VoceDiContoView voceDiContoView) throws Throwable {
		voceDiContoDAO.modifica(voceDiContoView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(VoceDiContoView voceDiContoView) throws Throwable {
		voceDiContoDAO.cancella(voceDiContoView.getVo().getId());
	}
	
	@Override
	public List<VoceDiContoView> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long settoreGiuridicoId,
			long tipologiaFascicoloId) throws Throwable {
		List<VoceDiConto> lista = voceDiContoDAO.leggiDaUnitaLegaleSettoreTipologiaFascicolo(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale);
		List<VoceDiContoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	protected Class<VoceDiConto> leggiClassVO() {
		return VoceDiConto.class;
	}

	@Override
	protected Class<VoceDiContoView> leggiClassView() {
		return VoceDiContoView.class;
	}

}
