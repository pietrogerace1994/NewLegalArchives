package eng.la.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Societa;
import eng.la.model.view.SocietaView;
import eng.la.persistence.SocietaDAO;
import eng.la.util.ListaPaginata;

@Service("societaService")
public class SocietaServiceImpl extends BaseService<Societa,SocietaView> implements SocietaService {

	@Autowired
	private SocietaDAO societaDAO;

	public void setDao(SocietaDAO dao) {
		this.societaDAO = dao;
	}

	public SocietaDAO getDao() {
		return societaDAO;
	}


	/**
	 * Ritorna la lista di SocietaView.  
	 * @return      List<SocietaView>
	 */
	@Override
	public List<SocietaView> leggi(boolean tutte) throws Throwable {
		List<Societa> lista = societaDAO.leggi(tutte);
		List<SocietaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Ritorna un oggetto SocietaView.  
	 * @param id
	 * @return  SocietaView con la classe entity Societa incapsulata
	 */
	@Override
	public SocietaView leggi(long id) throws Throwable {
		Societa societa = societaDAO.leggi(id);
		return (SocietaView) convertiVoInView(societa);
	}


	/**
	 * Ritorna una lista di SocietaView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param ragioneSociale
	 * @param nazioneId 
	 * @return      List<SocietaView>
	 * @throws Throwable
	 */ 
	@Override
	public List<SocietaView> cerca(String ragioneSociale, long nazioneId ) throws Throwable {
		List<Societa> lista = societaDAO.cerca(ragioneSociale, nazioneId);
		List<SocietaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	/**
	 * Ritorna una lista paginata di SocietaView in base ai parametri di ricerca impostati. 
	 * Sui parametri di tipo string viene effettuata una like insentitive anywhere
	 * @param ragioneSociale
	 * @param nazioneId 
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 * @return  ListaPaginata<SocietaView>
	 * @throws Throwable
	 */ 
	@Override
	public ListaPaginata<SocietaView> cerca(String ragioneSociale, long nazioneId, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<Societa> lista = societaDAO.cerca(ragioneSociale, nazioneId, elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione);
		List<SocietaView> listaView = convertiVoInView(lista);
		ListaPaginata<SocietaView> listaRitorno = new ListaPaginata<SocietaView>();
		Long conta = societaDAO.conta(ragioneSociale, nazioneId); 
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
	 * @param ragioneSociale
	 * @param nazioneId 
	 * @return Integer
	 * @throws Throwable
	 * */
	@Override
	public Long conta(String ragioneSociale, long nazioneId) throws Throwable {
		 return societaDAO.conta(ragioneSociale, nazioneId);
	}
	
	/**
	 * Salva l'entity incapsulata nel bean di input e
	 * ritorna un oggetto SocietaView se il salvataggio viene effettuato
	 * correttamente. 
	 * @param SocietaView 
	 * @return SocietaView
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public SocietaView inserisci(SocietaView societaView) throws Throwable {
		Societa societa = societaDAO.inserisci(societaView.getVo());
		SocietaView view = new SocietaView();
		view.setVo(societa);
		return view;
	}
	
	/**
	 * Effettua la modifica dell'entity Societa incapsulata nel bean SocietaView
	 * @param SocietaView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(SocietaView societaView) throws Throwable {
		societaDAO.modifica(societaView.getVo());
	}

	/**
	 * Effettua la cancellazione dell'entity Societa incapsulata nel bean SocietaView
	 * @param SocietaView 
	 * @throws Throwable
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(SocietaView societaView) throws Throwable {
		societaDAO.cancella(societaView.getIdSocieta()); 
	}
	
 
	@Override
	protected Class<Societa> leggiClassVO() { 
		return Societa.class;
	}

	@Override
	protected Class<SocietaView> leggiClassView() { 
		return SocietaView.class;
	}

	
	//@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean controlla(SocietaView societaView) throws Throwable	{
		String nome = societaView.getVo().getNome();
		return nome==null ? false : this.societaDAO.controlla(nome);
	}
	
	@Override
	public List<Long> getListaSNAM_SRG_GNL_STOGIT() throws Throwable{
		List<Societa> lista = societaDAO.getListaSNAM_SRG_GNL_STOGIT();
		
		List<Long> listaId = new ArrayList<Long>();
		for (Societa societa : lista) {
			listaId.add(societa.getId());
		}
		return listaId;
	}
}
