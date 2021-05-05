package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.persistence.MailinglistDAO;
import eng.la.persistence.NewsletterDAO;
import eng.la.util.ListaPaginata;

@Service("categoriaMailinglistService")
public class CategoriaMailinglistServiceImpl extends BaseService<CategoriaMailinglist, CategoriaMailinglistView> implements CategoriaMailinglistService {

	@Autowired
	private MailinglistDAO mailingListDao;
	public void setmailingListDao(MailinglistDAO mailingListDao) {
		this.mailingListDao = mailingListDao;
	}
	
	@Autowired
	private NewsletterDAO dao;

	@Override
	public CategoriaMailinglistView leggi(String codice, Locale locale) throws Throwable { 
		CategoriaMailinglist categoriaMailingList = mailingListDao.leggiCategoria(codice, locale.getLanguage().toUpperCase());
		CategoriaMailinglistView categoriaMailingListView = new CategoriaMailinglistView();
		categoriaMailingListView.setVo(categoriaMailingList);
		return categoriaMailingListView;
	}
	
	@Override
	protected Class<CategoriaMailinglist> leggiClassVO() { 
		return CategoriaMailinglist.class;
	}

	@Override
	protected Class<CategoriaMailinglistView> leggiClassView() { 
		return CategoriaMailinglistView.class;
	}
	
	@Override
	public ListaPaginata<CategoriaMailinglistView> cercaCategorie(int numElementiPerPagina, int numeroPagina,
			String ordinamento) throws Throwable {
		Long conta = dao.contaCategorie();
		List<CategoriaMailinglist> lista = dao.cercaCategorie(conta.intValue(), 1, ordinamento);
		List<CategoriaMailinglistView> listaView = convertiVoInView(lista);
		ListaPaginata<CategoriaMailinglistView> listaRitorno = new ListaPaginata<CategoriaMailinglistView>();
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(numElementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		return listaRitorno;
	}

	@Override
	public CategoriaMailinglistView salvaCategoria(CategoriaMailinglistView categoriaView) throws Throwable {
		CategoriaMailinglist vo = dao.salvaCategoria(categoriaView.getVo());
		return (CategoriaMailinglistView) convertiVoInView(vo, leggiClassVO(), leggiClassView());  
		
	}

	@Override
	public void modificaCategoria(CategoriaMailinglistView categoriaView) throws Throwable {
		dao.modificaCategoria(categoriaView.getVo());
		
	}

	@Override
	public void cancellaCategoria(long id) throws Throwable {
		dao.cancellaCategoria(id);
		dao.cancellaFigliCategoria(id);
	}

	@Override
	public Long findColor(String colore) throws Throwable {
		return dao.findColor(colore);
	}

	@Override
	public boolean haFigli(long id) throws Throwable {
		Long figli = dao.contaFigliCategoria(id);
		return figli>0;
	}

}
