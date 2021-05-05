package eng.la.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.RicercaParteCorrelata;
import eng.la.model.view.RicercaParteCorrelataView;
import eng.la.persistence.ParteCorrelataDAO;
import eng.la.persistence.RicercaParteCorrelataDAO;

/**
 * <h1>Classe di business ParteCorrelataService </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * 
 * @author 
 * @version 1.0
 * @since 2016-06-16
 */
@Service("ricercaParteCorrelataService")
public class RicercaParteCorrelataServiceImpl extends BaseService<RicercaParteCorrelata,RicercaParteCorrelataView> implements RicercaParteCorrelataService {
	@Autowired
	private RicercaParteCorrelataDAO ricercaParteCorrelataDao;
	
	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * 
	 * @param dao oggetto della classe ParteCorrelataDAO
	 * @see ParteCorrelataDAO
	 */
	public void setRicercaParteCorrelataDao(RicercaParteCorrelataDAO ricercaParteCorrelataDao) {
		this.ricercaParteCorrelataDao = ricercaParteCorrelataDao;
	}
	
	// metodi di lettura
	
	@Override
	public List<RicercaParteCorrelataView> cerca(RicercaParteCorrelataView ricercaParteCorrelata) throws Throwable {
		return null;
	}

	@Override
	public List<RicercaParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		List<RicercaParteCorrelata> lista = ricercaParteCorrelataDao.cerca(denominazione, codFiscale, partitaIva);
		List<RicercaParteCorrelataView> listaRitorno = (List<RicercaParteCorrelataView>)convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * _TBV ( verificare se necessario )
	 * Elenca tutte le parti correlate presenti in base dati
	 * @return lista perti correlate
	 */
	@Override
	public List<RicercaParteCorrelataView> leggi() throws Throwable {
		List<RicercaParteCorrelata> lista = ricercaParteCorrelataDao.leggi();
		List<RicercaParteCorrelataView> listaRitorno = (List<RicercaParteCorrelataView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	/**
	 * Lettura parteCorrelata per l'id.
	 * @param id
	 * @throws Throwable
	 */
	@Override
	public RicercaParteCorrelataView leggi(long id) throws Throwable {
		RicercaParteCorrelata ricercaParteCorrelata = ricercaParteCorrelataDao.leggi(id);
		return (RicercaParteCorrelataView) convertiVoInView(ricercaParteCorrelata);
	}
	
	/**
	 * Lettura di una parteCorrelata per il CF/PIVA.
	 * @param codFiscPIva
	 * @return ParteCorrelataView ritorna l'occorrenza trovata.
	 * @throws Throwable 
	 */
	@Override
	public RicercaParteCorrelataView leggi(String codFiscale, String partitaIva) throws Throwable {
		RicercaParteCorrelata ricercaParteCorrelata = ricercaParteCorrelataDao.leggi(codFiscale, partitaIva);
		return (RicercaParteCorrelataView) convertiVoInView(ricercaParteCorrelata);
	}

	// metodi di scrittura
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		ricercaParteCorrelataDao.cancella(id);
	}
	
	/**
	 * Esegue inserimento di una parteCorrelata in base dati.
	 * @param ricercaParteCorrelataView 
	 * @return parteCorrelataView ritorna l'occorenza inserita.
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public RicercaParteCorrelataView inserisci(RicercaParteCorrelataView ricercaParteCorrelataView) throws Throwable {
		RicercaParteCorrelata ricercaParteCorrelata = ricercaParteCorrelataDao.inserisci(ricercaParteCorrelataView.getVo());
		RicercaParteCorrelataView view = new RicercaParteCorrelataView();
		view.setVo(ricercaParteCorrelata);
		return view;
	}
	
	/**
	 * Esegue la modifca di una determinata occorrenza
	 * @param parteCorrelataView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(RicercaParteCorrelataView ricercaParteCorrelataView) throws Throwable {
		ricercaParteCorrelataDao.modifica(ricercaParteCorrelataView.getVo());
	}

	@Override
	protected Class<RicercaParteCorrelata> leggiClassVO() { 
		return RicercaParteCorrelata.class;
	}

	@Override
	protected Class<RicercaParteCorrelataView> leggiClassView() { 
		return RicercaParteCorrelataView.class;
	}
	
	@Override
	public List<RicercaParteCorrelataView> leggi(Date dataInizio, Date dataFine) throws Throwable {
		List<RicercaParteCorrelata> lista = ricercaParteCorrelataDao.leggi(dataInizio,dataFine);
		List<RicercaParteCorrelataView> listaRitorno = (List<RicercaParteCorrelataView>) convertiVoInView(lista);		
		return listaRitorno;
	}
}