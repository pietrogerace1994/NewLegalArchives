package eng.la.business;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Fattura;
import eng.la.model.view.FatturaView;
import eng.la.persistence.FatturaDAO;

/**
 * <h1>Classe di business FatturaService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("fatturaService")
public class FatturaServiceImpl extends BaseService<Fattura,FatturaView> implements FatturaService {
	
	@Autowired
	private FatturaDAO fatturaDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe FatturaDAO
	 * @see FatturaDAO
	 */
	public void setFatturaDao(FatturaDAO fatturaDao) {
		this.fatturaDao = fatturaDao;
	}
	
	
	/**
	 * Elenca tutte le fatture presenti in base dati
	 * @return lista fatture
	 * @throws Throwable
	 */
	@Override
	public List<FatturaView> leggi() throws Throwable {
		List<Fattura> lista = fatturaDao.leggi();
		List<FatturaView> listaRitorno = convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<FatturaView> cerca(FatturaView view) throws Throwable {
		return null;
	}

	// metodi di scrittura
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		fatturaDao.cancella(id);
	}

	/**
	 * Lettura fattura per l'id.
	 * <p>
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public FatturaView leggi(long id) throws Throwable {
		Fattura fattura = fatturaDao.leggi(id);
		return (FatturaView) convertiVoInView(fattura);
	}
	
	/**
	 * Esegue inserimento di una fattura in base dati.
	 * <p>
	 * @param fatturaView 
	 * @return fatturaView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public FatturaView inserisci(FatturaView fatturaView) throws Throwable {
		Fattura fattura = fatturaDao.inserisci(fatturaView.getVo());
		FatturaView view = new FatturaView();
		view.setVo(fattura);
		return view;
	}

	/**
	 * Esegue la modifca di una determinata occorrenza.
	 * <p>
	 * @param fatturaView
	 * @throws Throwable
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(FatturaView fatturaView) throws Throwable {
		fatturaDao.modifica(fatturaView.getVo());
	}
	
	@Override
	protected Class<Fattura> leggiClassVO() { 
		return Fattura.class;
	}

	@Override
	protected Class<FatturaView> leggiClassView() { 
		return FatturaView.class;
	}
}