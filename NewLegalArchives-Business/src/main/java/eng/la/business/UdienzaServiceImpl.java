/*
 * @author Benedetto Giordano
 */
package eng.la.business;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Udienza;
import eng.la.model.Utente;
import eng.la.model.view.UdienzaView;
import eng.la.persistence.UdienzaDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;

@Service("udienzaService")
public class UdienzaServiceImpl extends BaseService<Udienza,UdienzaView> implements UdienzaService {
	
	@Autowired
	private UdienzaDAO udienzaDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;

	@Override
	public List<UdienzaView> leggi() throws Throwable { 
		List<Udienza> lista = udienzaDAO.leggi();
		List<UdienzaView> listaRitorno = (List<UdienzaView>) convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public UdienzaView leggi(long id) throws Throwable {
		Udienza udienza = udienzaDAO.leggi(id);
		return (UdienzaView) convertiVoInView(udienza);
	}

	@Override
	public ListaPaginata<UdienzaView> cerca(String dal, String al,String nomeFascicolo , String legaleInterno, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<Udienza> lista = udienzaDAO.cerca(dal, al, nomeFascicolo, legaleInterno, elementiPerPagina, numeroPagina,
				ordinamento, ordinamentoDirezione);
		List<UdienzaView> listaView = (List<UdienzaView>) convertiVoInView(lista);
		ListaPaginata<UdienzaView> listaRitorno = new ListaPaginata<UdienzaView>();
		Long conta = udienzaDAO.conta(dal, al, nomeFascicolo, legaleInterno);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}
	
	@Override
	public Long conta(String dal, String al, String nomeFascicolo, String legaleInterno) throws Throwable {
		return udienzaDAO.conta(dal, al, nomeFascicolo, legaleInterno);
	}
 
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public UdienzaView inserisci(UdienzaView udienzaView) throws Throwable {
		udienzaView.getVo().setDataCreazione(new Date());
		Udienza udienza = udienzaDAO.inserisci(udienzaView.getVo());
		SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
		sessionFactory.getCurrentSession().flush();
		
		UdienzaView view = new UdienzaView();
		view.setVo(udienza);
		 
		return view;
	}
	

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void modifica(UdienzaView udienzaView) throws Throwable {	
		udienzaView.getVo().setDataModifica(new Date()); 
		udienzaDAO.modifica(udienzaView.getVo());
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public void cancella(UdienzaView udienzaView) throws Throwable {
		udienzaDAO.cancella(udienzaView.getVo().getId());
	} 
	
	@Override
	public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable {

		return utenteDAO.getListaLegaleInternoOwnerFascicolo(idFascicolo);
	}

	@Override
	protected Class<Udienza> leggiClassVO() {
		return Udienza.class;
	}

	@Override
	protected Class<UdienzaView> leggiClassView() {
		return UdienzaView.class;
	}
}
