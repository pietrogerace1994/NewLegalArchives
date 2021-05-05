package eng.la.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.Mailinglist;
import eng.la.model.MailinglistDettaglio;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.MailinglistDettaglioView;
import eng.la.model.view.MailinglistView;
import eng.la.model.view.RubricaView;
import eng.la.persistence.MailinglistDAO;

@Service("mailinglistService")
public class MailinglistServiceImpl extends BaseService<Mailinglist,MailinglistView> implements MailinglistService {

	@Autowired
	private MailinglistDAO dao;


	@Autowired
	AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Override
	public Collection<MailinglistView> leggiMailinglist(String codiceCategoria) throws Throwable {
		List<Mailinglist> lvo = dao.leggiMailinglist(codiceCategoria);
		if (lvo != null) {
			List<MailinglistView> lview = new ArrayList<MailinglistView>();
			for (Mailinglist vo : lvo) {
				MailinglistView view = new MailinglistView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}

	@Override
	public Collection<MailinglistView> leggiMailinglist() throws Throwable {
		List<Mailinglist> lvo = dao.leggiMailinglist();
		if (lvo != null) {
			List<MailinglistView> lview = new ArrayList<MailinglistView>();
			for (Mailinglist vo : lvo) {
				MailinglistView view = new MailinglistView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}

	@Override
	public MailinglistView salvaMailinglist(MailinglistView mailinglistView) throws Throwable {
		
		
		Mailinglist mailingList = dao.salvaMailinglist(mailinglistView.getVo());
		
		salvaDettaglioMailingList(mailinglistView, mailingList);
		
		
		
		MailinglistView view = new MailinglistView();
		view.setVo(mailingList);
		return view;
		

	}

	private void salvaDettaglioMailingList(MailinglistView mailinglistView, Mailinglist mailingList) {
		if( mailinglistView.getVo().getMailinglistDettaglio()!= null && mailinglistView.getVo().getMailinglistDettaglio().size() > 0 ){
			dao.cancellaMailingListDettaglio(mailingList.getId());
			Collection<MailinglistDettaglio> listaMailingListDettaglio = mailinglistView.getVo().getMailinglistDettaglio();
			for( MailinglistDettaglio mailingListDettaglio : listaMailingListDettaglio){
				mailingListDettaglio.setMailinglist(mailingList);
				dao.inserisciMailingListDettagli(mailingListDettaglio); 
			}
		} 
		
	}

	@Override
	public void salvaMailinglist(String codiceCategoria, String nome, List<RubricaView> listaRubricaView)
			throws Throwable {
		CategoriaMailinglistView categoriaMailinglistView = anagraficaStatiTipiService.leggiCategoria(codiceCategoria,
				Locale.ITALIAN.getLanguage().toUpperCase(),false);
		Mailinglist vo = new Mailinglist();
		vo.setCategoriaMailinglist(categoriaMailinglistView.getVo());
		vo.setNome(nome);
		MailinglistView view = new MailinglistView();
		view.setVo(vo);
		salvaMailinglist(view);
		for (RubricaView rubricaView : listaRubricaView) {
			MailinglistDettaglio dettaglio = new MailinglistDettaglio();
			dettaglio.setRubrica(rubricaView.getVo());
			dettaglio.setMailinglist(vo);
			MailinglistDettaglioView dettaglioView = new MailinglistDettaglioView();
			dettaglioView.setVo(dettaglio);
			salvaMailinglistDettaglio(dettaglioView);
		}
	}

	@Override
	public void salvaMailinglistDettaglio(MailinglistDettaglioView dettaglioView) throws Throwable {
		dao.salvaMailinglistDettaglio(dettaglioView.getVo());
	}

	@Override
	public void cancellaMailinglist(long id) throws Throwable {
		dao.cancellaMailinglist(id);
		dao.cancellaMailingListDettaglio(id);
	}

	@Override
	protected Class<Mailinglist> leggiClassVO() {
		return Mailinglist.class;
	}

	@Override
	protected Class<MailinglistView> leggiClassView() {
		return MailinglistView.class;
	}

	@Override
	public Collection<MailinglistDettaglioView> leggiMailinglistDettaglio(long idMailinglist) throws Throwable {
		List<MailinglistDettaglio> lvo = dao.leggiMailinglistDettaglio(idMailinglist);
		if (lvo != null) {
			List<MailinglistDettaglioView> lview = new ArrayList<MailinglistDettaglioView>();
			for (MailinglistDettaglio vo : lvo) {
				MailinglistDettaglioView view = new MailinglistDettaglioView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}
	
	@Override
	public List<CategoriaMailinglistView> listaCategoriaMailinglist(String lingua) throws Throwable{
		List<CategoriaMailinglist> lvo= dao.listaCategoriaMailinglist(lingua);
		
		if (lvo != null) {
			List<CategoriaMailinglistView> lview = new ArrayList<CategoriaMailinglistView>();
			for (CategoriaMailinglist vo : lvo) {
				CategoriaMailinglistView view = new CategoriaMailinglistView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;

	}
	
	@Override
	public CategoriaMailinglist getCategoria(long id) throws Throwable {
		CategoriaMailinglist categoriaAtto=(CategoriaMailinglist)dao.getCategoria(id);
		return categoriaAtto;
	}
	
	@Override
	public MailinglistView leggi(long id, FetchMode fetchMode) throws Throwable {
		Mailinglist vo = dao.leggi(id, fetchMode); 
		return (MailinglistView) convertiVoInView(vo);
	}
	
	@Override
	public List<MailinglistDettaglioView> leggiMailingListDettagliobyId(long id) throws Throwable {
		List<MailinglistDettaglio> lista = dao.leggiMailingListDettagliobyId(id);
		
		if(lista != null){
			List<MailinglistDettaglioView> listaRitorno = new ArrayList<MailinglistDettaglioView>();
			for( MailinglistDettaglio vo : lista ){
				MailinglistDettaglioView view = new MailinglistDettaglioView();
				view.setVo(vo);
				listaRitorno.add(view);
			}
			return listaRitorno;
		}
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(long id) throws Throwable {
		
		dao.modifica(id);
		
		Mailinglist mailingList = dao.leggiMailinglist(id);
		
		MailinglistView mailingListView = (MailinglistView) convertiVoInView(mailingList,Mailinglist.class, MailinglistView.class);
		
		
		salvaDettaglioMailingList(mailingListView, mailingList);
		
	}

	@Override
	public void modifica(MailinglistView mailingListView) throws Throwable {
		
		System.out.println("***************************+ mailingListView.getVo"+mailingListView.getVo().toString());
		
		dao.modifica(mailingListView.getVo());
			
		System.out.println("***************************+ HO fatto la modifica"+mailingListView.getVo());

		salvaDettaglioMailingList(mailingListView, mailingListView.getVo());

		System.out.println("***************************+ HO modificato il dettaglio");
		
	}

	@Override
	public MailinglistView leggi(Long mailingListId) throws Throwable {
		Mailinglist vo = dao.leggiMailinglist(mailingListId);
		if (vo != null) {
			MailinglistView view = new MailinglistView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	@Override
	public void cancellaArticoli(long id) throws Throwable {
		dao.cancellaArticoli(id);
		
	}

	@Override
	public List<CategoriaMailinglist> leggiCategorieMailingList() throws Throwable {
		return dao.categorieMailingList();
	}

	@Override
	public List<Long> getCategorieUtente(Long rubricaId) throws Throwable {
		
		List<Mailinglist> mailingList = dao.categorieUtenteMailingList(rubricaId);
		
		List<Long> categorie = new ArrayList<Long>();
		
		for(Mailinglist list : mailingList){
			
			categorie.add(list.getCategoriaMailinglist().getId());
		}
		
		return categorie;
	}

	@Override
	public void cancellaEmail(long id) throws Throwable {
		dao.cancellaEmail(id);
	}

	@Override
	public List<CategoriaMailinglist> leggiSottoCategoriaMailingList(Long idCategoria) throws Throwable {
		return dao.leggiSottoCategoriaMailingList(idCategoria);
	}
	
	

}

