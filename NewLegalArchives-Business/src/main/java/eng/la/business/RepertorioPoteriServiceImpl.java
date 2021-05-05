package eng.la.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.CategoriaTessere;
import eng.la.model.RepertorioPoteri;
import eng.la.model.SubCategoriaTessere;
import eng.la.model.aggregate.RepertorioPoteriAggregate;
import eng.la.model.filter.RepertorioPoteriFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.RepertorioPoteriView;
import eng.la.persistence.CategoriaTessereDAO;
import eng.la.persistence.RepertorioPoteriDAO;
import eng.la.persistence.SubCategoriaTessereDAO;
import eng.la.util.ListaPaginata;

@Service("repertorioPoteriService")
public class RepertorioPoteriServiceImpl extends BaseService<RepertorioPoteri,RepertorioPoteriView> implements RepertorioPoteriService {


	
	@Autowired
	private RepertorioPoteriDAO repertorioPoteriDAO;
	public void setDao(RepertorioPoteriDAO dao) {
		this.repertorioPoteriDAO = dao;
	}
	
	@Autowired
	private CategoriaTessereDAO categoriaTessereDAO;
	public void setCategoriaTessereDAO(CategoriaTessereDAO dao) {
		this.categoriaTessereDAO = dao;
	}
	
	@Autowired
	private SubCategoriaTessereDAO subcategoriaTessereDAO;
	public void setSubCategoriaTessereDAO(SubCategoriaTessereDAO dao) {
		this.subcategoriaTessereDAO = dao;
	}

	
	@Override
	public List<RepertorioPoteriView> leggi() throws Throwable {
		List<RepertorioPoteri> lista = repertorioPoteriDAO.leggi();
		List<RepertorioPoteriView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public RepertorioPoteriView leggi(long id) throws Throwable {
		RepertorioPoteri repertorioPoteri = repertorioPoteriDAO.leggi(id);
		return (RepertorioPoteriView) convertiVoInView(repertorioPoteri);
	}

	@Override
	public ListaPaginata<RepertorioPoteriView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		List<RepertorioPoteri> lista = repertorioPoteriDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento,
				ordinamentoDirezione);
		List<RepertorioPoteriView> listaView = convertiVoInView(lista);
		ListaPaginata<RepertorioPoteriView> listaRitorno = new ListaPaginata<RepertorioPoteriView>();
		Long conta = repertorioPoteriDAO.conta(nome);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	public List<RepertorioPoteriView> cerca(String nome) throws Throwable {
		List<RepertorioPoteri> lista = repertorioPoteriDAO.cerca(nome);
		List<RepertorioPoteriView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public RepertorioPoteriView inserisci(RepertorioPoteriView repertorioPoteriView) throws Throwable {
		
		int max=0;
		List<String> codGruppoLinguaList = repertorioPoteriDAO.getCodGruppoLinguaList(repertorioPoteriView.getVo().getLingua());
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "RPOT_"+String.valueOf(max + 1);
		repertorioPoteriView.getVo().setCodGruppoLingua(codice);
		
		RepertorioPoteri repertorioPoteri = repertorioPoteriDAO.inserisci(repertorioPoteriView.getVo());
		RepertorioPoteriView view = new RepertorioPoteriView();
		view.setVo(repertorioPoteri);
		return view;
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(RepertorioPoteriView repertorioPoteriView) throws Throwable {
		
		repertorioPoteriDAO.modifica(repertorioPoteriView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(long idRepertorioPoteri) throws Throwable {
		repertorioPoteriDAO.cancella(idRepertorioPoteri);
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return repertorioPoteriDAO.conta(nome);
	}

	@Override
	public RepertorioPoteriView leggi(String codice, Locale locale) throws Throwable {
		RepertorioPoteri repertorioPoteri = repertorioPoteriDAO.leggi(codice, locale.getLanguage().toUpperCase());
		RepertorioPoteriView repertorioPoteriView = new RepertorioPoteriView();
		repertorioPoteriView.setVo(repertorioPoteri);
		return repertorioPoteriView;
	}

	@Override
	public List<RepertorioPoteriView> leggi(Locale locale) throws Throwable {
		List<RepertorioPoteri> lista = repertorioPoteriDAO.leggi(locale.getLanguage().toUpperCase());
		List<RepertorioPoteriView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<RepertorioPoteri> leggiClassVO() {
		return RepertorioPoteri.class;
	}

	@Override
	protected Class<RepertorioPoteriView> leggiClassView() {
		return RepertorioPoteriView.class;
	}


	@Override
	public RepertorioPoteriAggregate searchPagedRepertorioPoteri(RepertorioPoteriFilter filter) throws Throwable {
		return repertorioPoteriDAO.searchPagedRepertorioPoteri(filter);
	}


	@Override
	public List<CodiceDescrizioneBean> leggiListaCategorie(Locale locale) {
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<CategoriaTessere> lista = categoriaTessereDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<CategoriaTessere> iterator = lista.iterator(); iterator.hasNext();) {
				CategoriaTessere categoriaTessere = iterator.next();
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(categoriaTessere.getId());
				bean.setDescrizione(categoriaTessere.getDescrizione());
				bean.setCategoria(categoriaTessere);
				listaBean.add(bean);
				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	}

	@Override
	public List<CodiceDescrizioneBean> leggiListaSubCategorie(Locale locale) {
		List<CodiceDescrizioneBean> listaBean = new ArrayList<>();
		try {
			List<SubCategoriaTessere> lista = subcategoriaTessereDAO.leggi(locale.getLanguage().toUpperCase());
			
			for (Iterator<SubCategoriaTessere> iterator = lista.iterator(); iterator.hasNext();) {
				SubCategoriaTessere categoriaTessere = iterator.next();
				
				CodiceDescrizioneBean bean = new CodiceDescrizioneBean();
				bean.setId(categoriaTessere.getId());
				bean.setDescrizione(categoriaTessere.getDescrizione());
				bean.setSubCategoria(categoriaTessere);
				listaBean.add(bean);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return listaBean;
	}
}
