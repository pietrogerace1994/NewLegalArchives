package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.OrganoGiudicante;
import eng.la.model.view.OrganoGiudicanteView;
import eng.la.persistence.OrganoGiudicanteDAO;
import eng.la.util.ListaPaginata;

@Service("organoGiudicanteService")
public class OrganoGiudicanteServiceImpl extends BaseService<OrganoGiudicante,OrganoGiudicanteView> implements OrganoGiudicanteService {

	@Autowired
	private OrganoGiudicanteDAO organoGiudicanteDAO;

	public void setDao(OrganoGiudicanteDAO dao) {
		this.organoGiudicanteDAO = dao;
	}

	public OrganoGiudicanteDAO getDao() {
		return organoGiudicanteDAO;
	}

	@Override
	public List<OrganoGiudicanteView> leggi() throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.leggi();
		List<OrganoGiudicanteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public OrganoGiudicanteView leggi(long id) throws Throwable {
		OrganoGiudicante organoGiudicante = organoGiudicanteDAO.leggi(id);
		return (OrganoGiudicanteView) convertiVoInView(organoGiudicante);
	}

	@Override
	public ListaPaginata<OrganoGiudicanteView> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<OrganoGiudicanteView> listaView = convertiVoInView(lista);
		ListaPaginata<OrganoGiudicanteView> listaRitorno = new ListaPaginata<OrganoGiudicanteView>();
		Long conta = organoGiudicanteDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<OrganoGiudicanteView> cerca(String nome) throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.cerca(nome);
		List<OrganoGiudicanteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public OrganoGiudicanteView inserisci(OrganoGiudicanteView organoGiudicanteView) throws Throwable {
		OrganoGiudicante organoGiudicante = organoGiudicanteDAO.inserisci(organoGiudicanteView.getVo());
		OrganoGiudicanteView view = new OrganoGiudicanteView();
		view.setVo(organoGiudicante);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(OrganoGiudicanteView organoGiudicanteView) throws Throwable {
		organoGiudicanteDAO.modifica(organoGiudicanteView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(OrganoGiudicanteView organoGiudicanteView) throws Throwable {
		organoGiudicanteDAO.cancella(organoGiudicanteView.getVo().getId());
	}

	@Override
	public Long conta(String nome) throws Throwable {
		return organoGiudicanteDAO.conta(nome);
	}

	@Override
	public OrganoGiudicanteView leggi(String codice, Locale locale) throws Throwable {
		OrganoGiudicante organoGiudicante = organoGiudicanteDAO.leggi(codice, locale.getLanguage().toUpperCase());
		OrganoGiudicanteView organoGiudicanteView = new OrganoGiudicanteView();
		organoGiudicanteView.setVo(organoGiudicante);
		return organoGiudicanteView;
	}

	@Override
	public List<OrganoGiudicanteView> leggi(Locale locale) throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.leggi(locale.getLanguage().toUpperCase());
		List<OrganoGiudicanteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<OrganoGiudicanteView> leggiDaGiudizio(long id) throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.leggiDaGiudizio(id);
		List<OrganoGiudicanteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<OrganoGiudicanteView> leggiDaRicorso(long id) throws Throwable {
		List<OrganoGiudicante> lista = organoGiudicanteDAO.leggiDaRicorso(id);
		List<OrganoGiudicanteView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<OrganoGiudicante> leggiClassVO() {
		return OrganoGiudicante.class;
	}

	@Override
	protected Class<OrganoGiudicanteView> leggiClassView() {
		return OrganoGiudicanteView.class;
	}

}
