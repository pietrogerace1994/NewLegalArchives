package eng.la.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.OrganoSociale;
import eng.la.model.TipoOrganoSociale;
import eng.la.model.aggregate.OrganoSocialeAggregate;
import eng.la.model.filter.OrganoSocialeFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.OrganoSocialeView;
import eng.la.persistence.OrganoSocialeDAO;
import eng.la.persistence.TipoOrganoSocialeDAO;
import eng.la.util.ListaPaginata;

@Service("organoSocialeService")
public class OrganoSocialeServiceImpl extends BaseService<OrganoSociale,OrganoSocialeView> implements OrganoSocialeService {

	
	@Autowired
	private OrganoSocialeDAO organoSocialeDAO;
	public void setDao(OrganoSocialeDAO dao) {
		this.organoSocialeDAO = dao;
	}
	
	@Autowired
	private TipoOrganoSocialeDAO tipoOrganoSocialeDAO;
	public void setTipoOrganoSocialeDAO(TipoOrganoSocialeDAO dao) {
		this.tipoOrganoSocialeDAO = dao;
	}

	
	@Autowired
	private AffariSocietariService affariSocietariService;
	public void setAffariSocietariService(AffariSocietariService affariSocietariService) {
		this.affariSocietariService = affariSocietariService;
	}
	
	@Override
	public List<OrganoSocialeView> leggi() throws Throwable {
		List<OrganoSociale> lista = organoSocialeDAO.leggi();
		List<OrganoSocialeView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public OrganoSocialeView leggi(long id) throws Throwable {
		OrganoSociale organoSociale = organoSocialeDAO.leggi(id);
		return (OrganoSocialeView) convertiVoInView(organoSociale);
	}

	@Override
	public ListaPaginata<OrganoSocialeView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<OrganoSociale> lista = organoSocialeDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<OrganoSocialeView> listaView = convertiVoInView(lista);
		ListaPaginata<OrganoSocialeView> listaRitorno = new ListaPaginata<OrganoSocialeView>();
		Long conta = organoSocialeDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<OrganoSocialeView> cerca(String nome) throws Throwable {
		List<OrganoSociale> lista = organoSocialeDAO.cerca(nome);
		List<OrganoSocialeView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public OrganoSocialeView inserisci(OrganoSocialeView organoSocialeView) throws Throwable {
		
		OrganoSociale organoSociale = organoSocialeDAO.inserisci(organoSocialeView.getVo());
		OrganoSocialeView view = new OrganoSocialeView();
		view.setVo(organoSociale);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(OrganoSocialeView organoSocialeView) throws Throwable {
		
		organoSocialeDAO.modifica(organoSocialeView.getVo());

	}



	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idOrganoSociale) throws Throwable {
		organoSocialeDAO.cancella(idOrganoSociale);
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return organoSocialeDAO.conta(nome);
	}

	@Override
	public OrganoSocialeView leggi(String codice, Locale locale) throws Throwable {
		OrganoSociale organoSociale = organoSocialeDAO.leggi(codice, locale.getLanguage().toUpperCase());
		OrganoSocialeView organoSocialeView = new OrganoSocialeView();
		organoSocialeView.setVo(organoSociale);
		return organoSocialeView;
	}

	@Override
	public List<OrganoSocialeView> leggi(Locale locale) throws Throwable {
		List<OrganoSociale> lista = organoSocialeDAO.leggi(locale.getLanguage().toUpperCase());
		List<OrganoSocialeView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<OrganoSociale> leggiClassVO() {
		return OrganoSociale.class;
	}

	@Override
	protected Class<OrganoSocialeView> leggiClassView() {
		return OrganoSocialeView.class;
	}


	@Override
	public OrganoSocialeAggregate searchPagedOrganoSociale(OrganoSocialeFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {
		return organoSocialeDAO.searchPagedOrganoSociale(filter, listaSNAM_SRG_GNL_STOGIT);
	}
	
	@Override
	public List<CodiceDescrizioneBean> leggiListaSocietaAffari() throws Throwable{
		return affariSocietariService.leggiListaSocietaControllanti();
	};

	@Override
	public List<CodiceDescrizioneBean> leggiOrganiSociali(Locale locale){
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<TipoOrganoSociale> lista = tipoOrganoSocialeDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<TipoOrganoSociale> iterator = lista.iterator(); iterator.hasNext();) {
				TipoOrganoSociale tipoOrganoSociale = iterator.next();
				
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(tipoOrganoSociale.getId());
				bean.setDescrizione(tipoOrganoSociale.getDescrizione());
				listaBean.add(bean);
				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	};
	
	@Override
	public List<OrganoSocialeView> esporta() throws Throwable{
		List<OrganoSociale> lista = organoSocialeDAO.esporta();
		List<OrganoSocialeView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

}
