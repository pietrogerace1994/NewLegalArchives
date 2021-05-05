package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.TipologiaFascicolo;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.persistence.TipologiaFascicoloDAO;
import eng.la.util.ListaPaginata;

@Service("tipologiaFascicoloService")
public class TipologiaFascicoloServiceImpl extends BaseService<TipologiaFascicolo,TipologiaFascicoloView> implements TipologiaFascicoloService {

	@Autowired
	private TipologiaFascicoloDAO tipologiaFascicoloDAO;
	
	public void setTipologiaFascicoloDAO(TipologiaFascicoloDAO tipologiaFascicoloDAO) {
		this.tipologiaFascicoloDAO = tipologiaFascicoloDAO;
	}
	
	public TipologiaFascicoloDAO getTipologiaFascicoloDAO() {
		return tipologiaFascicoloDAO;
	}
	
	/**
	 * Ritorna la lista di TipologiaFascicoloView.  
	 * @return      List<TipologiaFascicoloView>
	 * @throws Throwable
	 */
	@Override
	public List<TipologiaFascicoloView> leggi() throws Throwable {
		List<TipologiaFascicolo> lista = tipologiaFascicoloDAO.leggi();
		List<TipologiaFascicoloView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Ritorna un oggetto TipologiaFascicoloView.  
	 * @param id
	 * @return      TipologiaFascicoloView con la classe entity TipologiaFascicolo incapsulata
	 * @throws Throwable
	 */
	@Override
	public TipologiaFascicoloView leggi(long id) throws Throwable {
		TipologiaFascicolo tipologiaFascicolo = tipologiaFascicoloDAO.leggi(id);
		return (TipologiaFascicoloView) convertiVoInView(tipologiaFascicolo);
	}

	/**
	 * Ritorna una lista di TipologiaFascicoloView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param nome
	 * @return      List<TipologiaFascicoloView>
	 * @throws Throwable
	 */
	@Override
	public List<TipologiaFascicoloView> cerca(String nome) throws Throwable {
		List<TipologiaFascicolo> lista = tipologiaFascicoloDAO.cerca(nome); 
		List<TipologiaFascicoloView> listaRitorno = convertiVoInView(lista);
	  	return listaRitorno;
	}
	
	/**
	 * Ritorna una lista paginata di TipologiaFascicoloView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param nome
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 * @return      ListaPaginata<TipologiaFascicoloView>
	 * @throws Throwable
	 */
	@Override
	public ListaPaginata<TipologiaFascicoloView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione ) throws Throwable {
		List<TipologiaFascicolo> lista = tipologiaFascicoloDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		Long conta = tipologiaFascicoloDAO.conta(nome); 
		List<TipologiaFascicoloView> listaView = convertiVoInView(lista);
		ListaPaginata<TipologiaFascicoloView> listaRitorno = new ListaPaginata<TipologiaFascicoloView>();
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta); 
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	/**
	 * Salva l'entity incapsulata nel bean di input e
	 * ritorna un oggetto TipologiaFascicoloView se il salvataggio viene effettuato
	 * correttamente. 
	 * @param TipologiaFascicoloView 
	 * @return TipologiaFascicoloView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public TipologiaFascicoloView inserisci(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable {
		TipologiaFascicolo tipologiaFascicolo = tipologiaFascicoloDAO.inserisci(tipologiaFascicoloView.getVo());
		TipologiaFascicoloView view = new TipologiaFascicoloView();
		view.setVo(tipologiaFascicolo);
		return view;
	}
		
	/**
	 * Effettua la modifica dell'entity TipologiaFascicolo incapsulata nel bean TipologiaFascicoloView
	 * @param TipologiaFascicoloView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable {
		tipologiaFascicoloDAO.modifica(tipologiaFascicoloView.getVo());
	}

	/**
	 * Effettua la cancellazione dell'entity TipologiaFascicolo incapsulata nel bean TipologiaFascicoloView
	 * @param TipologiaFascicoloView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(TipologiaFascicoloView tipologiaFascicoloView) throws Throwable {
		tipologiaFascicoloDAO.cancella(tipologiaFascicoloView.getVo().getId());
	}

	@Override
	public List<TipologiaFascicoloView> leggi(Locale locale, boolean tutte) throws Throwable{
		List<TipologiaFascicolo> lista = tipologiaFascicoloDAO.leggi(locale.getLanguage().toUpperCase(),tutte);
		List<TipologiaFascicoloView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public TipologiaFascicoloView leggi(String codice, Locale locale, boolean tutte) throws Throwable {
		TipologiaFascicolo tipologiaFascicolo = tipologiaFascicoloDAO.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		return (TipologiaFascicoloView) convertiVoInView(tipologiaFascicolo);
	}
	
	@Override
	public List<TipologiaFascicoloView> leggiPerSettoreGiuridicoId(long id, boolean tutte) throws Throwable {
		List<TipologiaFascicolo> lista = tipologiaFascicoloDAO.leggiPerSettoreGiuridicoId(id,tutte);
		List<TipologiaFascicoloView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	protected Class<TipologiaFascicolo> leggiClassVO() { 
		return TipologiaFascicolo.class;
	}

	@Override
	protected Class<TipologiaFascicoloView> leggiClassView() {
		return TipologiaFascicoloView.class;
	}
 
}
