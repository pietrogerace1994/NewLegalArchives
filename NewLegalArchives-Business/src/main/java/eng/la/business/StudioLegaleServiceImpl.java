package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.StudioLegale;
import eng.la.model.view.StudioLegaleView;
import eng.la.persistence.StudioLegaleDAO;
import eng.la.util.ListaPaginata;

@Service("studioLegaleService")
public class StudioLegaleServiceImpl extends BaseService<StudioLegale,StudioLegaleView> implements StudioLegaleService {

	@Autowired
	private StudioLegaleDAO studioLegaleDao;

	public StudioLegaleDAO getStudioLegaleDao() {
		return studioLegaleDao;
	}

	public void setStudioLegaleDao(StudioLegaleDAO studioLegaleDao) {
		this.studioLegaleDao = studioLegaleDao;
	}

	/**
	 * Ritorna la lista di StudioLegaleView.  
	 * @return      List<StudioLegaleView>
	 */
	@Override
	public List<StudioLegaleView> leggi() throws Throwable {
		List<StudioLegale> lista = studioLegaleDao.leggi();
		List<StudioLegaleView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Ritorna un oggetto StudioLegaleView.  
	 * @param id
	 * @return      StudioLegaleView con la classe entity StudioLegale incapsulata
	 */
	@Override
	public StudioLegaleView leggi(long id) throws Throwable {
		StudioLegale studioLegale = studioLegaleDao.leggi(id);
		return (StudioLegaleView) convertiVoInView(studioLegale);
	}

	/**
	 * Ritorna una lista di StudioLegaleView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param denominazione
	 * @param nazioneId
	 * @param codiceSap
	 * @return      List<StudioLegaleView>
	 * @throws Throwable
	 */
	@Override
	public List<StudioLegaleView> cerca(String denominazione, long nazioneId, String codiceSap) throws Throwable {
		List<StudioLegale> lista = studioLegaleDao.cerca(denominazione, nazioneId, codiceSap);
		List<StudioLegaleView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna una lista paginata di StudioLegaleView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param denominazione
	 * @param nazioneId
	 * @param codiceSap
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 * @return      ListaPaginata<StudioLegaleView>
	 * @throws Throwable
	 */
	@Override
	public ListaPaginata<StudioLegaleView> cerca(String denominazione, long nazioneId, String codiceSap, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<StudioLegale> lista = studioLegaleDao.cerca(denominazione, nazioneId, codiceSap, elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		List<StudioLegaleView> listaView = convertiVoInView(lista);
		ListaPaginata<StudioLegaleView> listaRitorno = new ListaPaginata<StudioLegaleView>();
		Long conta = studioLegaleDao.conta(denominazione, nazioneId, codiceSap); 
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
	 * @param denominazione
	 * @param nazioneId
	 * @param codiceSap
	 * @return Integer
	 * @throws Throwable
	 * */
	@Override
	public Long conta(String denominazione, long nazioneId, String codiceSap) throws Throwable {
		 return studioLegaleDao.conta(denominazione, nazioneId, codiceSap);
	}
	
	/**
	 * Salva l'entity incapsulata nel bean di input e
	 * ritorna un oggetto StudioLegaleView se il salvataggio viene effettuato
	 * correttamente. 
	 * @param StudioLegaleView 
	 * @return StudioLegaleView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public StudioLegaleView inserisci(StudioLegaleView studioLegaleView) throws Throwable {
		StudioLegale studioLegale = studioLegaleDao.inserisci(studioLegaleView.getVo());
		StudioLegaleView view = new StudioLegaleView();
		view.setVo(studioLegale);
		return view;
	}
	
	/**
	 * Effettua la modifica dell'entity StudioLegale incapsulata nel bean StudioLegaleView
	 * @param StudioLegaleView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(StudioLegaleView studioLegaleView) throws Throwable {
		studioLegaleDao.modifica(studioLegaleView.getVo());
	}
	
	/**
	 * Effettua la cancellazione dell'entity StudioLegale incapsulata nel bean StudioLegaleView
	 * @param StudioLegaleView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(StudioLegaleView studioLegaleView) throws Throwable {
		studioLegaleDao.cancella(studioLegaleView.getVo().getId());
	}

	@Override
	protected Class<StudioLegale> leggiClassVO() { 
		return StudioLegale.class;
	}

	@Override
	protected Class<StudioLegaleView> leggiClassView() {
		return StudioLegaleView.class;
	}


}
