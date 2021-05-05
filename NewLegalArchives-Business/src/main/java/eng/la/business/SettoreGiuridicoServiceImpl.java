package eng.la.business;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.SettoreGiuridico;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.persistence.SettoreGiuridicoDAO;
import eng.la.util.ListaPaginata;

@Service("settoreGiuridicoService")
public class SettoreGiuridicoServiceImpl extends BaseService<SettoreGiuridico,SettoreGiuridicoView> implements SettoreGiuridicoService{
	
	@Autowired
	private SettoreGiuridicoDAO settoreGiuridicoDAO;

	public void setDao(SettoreGiuridicoDAO dao) {
		this.settoreGiuridicoDAO = dao;
	}

	public SettoreGiuridicoDAO getDao() {
		return settoreGiuridicoDAO;
	}
	
	/**
	 * Ritorna la lista di SettoreGiuridicoView.  
	 * @return      List<SettoreGiuridicoView>
	 */ 
	@Override
	public List<SettoreGiuridicoView> leggi() throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.leggi();
		List<SettoreGiuridicoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}


	@Override
	public List<SettoreGiuridicoView> leggi(Locale locale) throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.leggi(locale.getLanguage().toUpperCase());
		List<SettoreGiuridicoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}


	/**
	 * Ritorna un oggetto SettoreGiuridicoView.  
	 * @param id
	 * @return      SettoreGiuridicoView con la classe entity SettoreGiuridico incapsulata
	 */ 
	@Override
	public SettoreGiuridicoView leggi(long id) throws Throwable { 
		SettoreGiuridico settoreGiuridico = settoreGiuridicoDAO.leggi(id);
		return (SettoreGiuridicoView) convertiVoInView(settoreGiuridico);
	}

	/**
	 * Ritorna una lista di SettoreGiuridicoView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param nome
	 * @return      List<SettoreGiuridicoView>
	 * @throws Throwable
	 */ 
	@Override
	public List<SettoreGiuridicoView> cerca(String nome) throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.cerca(nome);
		List<SettoreGiuridicoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna una lista paginata di SettoreGiuridicoView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param nome
	 * @param elementiPerPagina 
	 * @param numeroPagina 
	 * @param ordinamento 
	 * @param ordinamentoDirezione 
	 * @return      ListaPaginata<SettoreGiuridicoView>
	 * @throws Throwable
	 */ 
	@Override
	public ListaPaginata<SettoreGiuridicoView> cerca(String nome, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.cerca(nome, elementiPerPagina,
			 numeroPagina, ordinamento, ordinamentoDirezione);
		List<SettoreGiuridicoView> listaView = convertiVoInView(lista);
		ListaPaginata<SettoreGiuridicoView> listaRitorno = new ListaPaginata<SettoreGiuridicoView>();
		Long conta = settoreGiuridicoDAO.conta(nome); 
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta); 
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno; 
	}
	
	/**
	 * ritorna in numero di record presenti sul db in base ai criteri di filtro impostati
	 * @param nome
	 * @return Integer
	 * @throws Throwable
	 * */
	@Override
	public Long conta(String nome) throws Throwable {		 
		return settoreGiuridicoDAO.conta(nome);
	}
	
	
	/**
	 * Salva l'entity incapsulata nel bean di input e
	 * ritorna un oggetto SettoreGiuridicoView se il salvataggio viene effettuato
	 * correttamente. 
	 * @param SettoreGiuridicoView 
	 * @return SettoreGiuridicoView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public SettoreGiuridicoView inserisci(SettoreGiuridicoView settoreGiuridicoView) throws Throwable {
		SettoreGiuridico settoreGiuridico = settoreGiuridicoDAO.inserisci(settoreGiuridicoView.getVo());
		SettoreGiuridicoView view = new SettoreGiuridicoView();
		view.setVo(settoreGiuridico);
		return view;
	}

	/**
	 * Effettua la modifica dell'entity SettoreGiuridico incapsulata nel bean SettoreGiuridicoView
	 * @param SettoreGiuridicoView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(SettoreGiuridicoView settoreGiuridicoView) throws Throwable {
		settoreGiuridicoDAO.modifica(settoreGiuridicoView.getVo());
	}
	
	/**
	 * Effettua la cancellazione dell'entity SettoreGiuridico incapsulata nel bean SettoreGiuridicoView
	 * @param SettoreGiuridicoView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(SettoreGiuridicoView settoreGiuridicoView) throws Throwable {
		settoreGiuridicoDAO.cancella(settoreGiuridicoView.getVo().getId()); 
	}
	
	@Override
	public SettoreGiuridicoView leggi(String codice, Locale locale, boolean tutte) throws Throwable { 
		SettoreGiuridico settoreGiuridico = settoreGiuridicoDAO.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		SettoreGiuridicoView settoreGiuridicoView = new SettoreGiuridicoView();
		settoreGiuridicoView.setVo(settoreGiuridico);
		return settoreGiuridicoView;
	}
	 
	@Override
	public List<SettoreGiuridicoView> leggiPerTipologiaId(long id, boolean tutte) throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.leggiPerTipologiaId(id,tutte);
		List<SettoreGiuridicoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<SettoreGiuridicoView> leggiPerTipologiaId(long id) throws Throwable {
		List<SettoreGiuridico> lista = settoreGiuridicoDAO.leggiPerTipologiaId(id);
		List<SettoreGiuridicoView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
 
	@Override
	protected Class<SettoreGiuridico> leggiClassVO() {
		return SettoreGiuridico.class;
	}

	@Override
	protected Class<SettoreGiuridicoView> leggiClassView() {
		return SettoreGiuridicoView.class;
	}

}
