package eng.la.business;


import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.VendorManagement;
import eng.la.model.view.VendorManagementView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.VendorManagementDAO;

@Service("vendorManagementService")
public class VendorManagementServiceImpl extends BaseService<VendorManagement,VendorManagementView> implements VendorManagementService {
	
	@Autowired
	private VendorManagementDAO vendorManagementDAO;
	
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiDAO;
	
	@Override
	public VendorManagementView getDataValutazioneMax(String lingua) throws Throwable  {
		VendorManagement vendorManagement = vendorManagementDAO.getDataValutazioneMax(lingua);
		return (VendorManagementView) convertiVoInView(vendorManagement);
	}
	
	@Override
	public VendorManagementView getDataValutazioneMin(String lingua) throws Throwable  {
		VendorManagement vendorManagement = vendorManagementDAO.getDataValutazioneMin(lingua);
		return (VendorManagementView) convertiVoInView(vendorManagement);
	}
	
	@Override
	public List<VendorManagementView> leggi() throws Throwable {
		List<VendorManagement> lista = vendorManagementDAO.leggi();
		List<VendorManagementView> listaRitorno = (List<VendorManagementView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		vendorManagementDAO.cancella(id);
	}

	@Override
	public VendorManagementView leggi(long id) throws Throwable {
		VendorManagement vendorManagement = vendorManagementDAO.leggi(id);
		return (VendorManagementView) convertiVoInView(vendorManagement);
	}
	
	@Override
	public List<VendorManagementView> leggiByIdIncarico(long idIncarico) throws Throwable {
		List<VendorManagement> lista = vendorManagementDAO.leggiByIdIncarico(idIncarico);
		return (List<VendorManagementView>) convertiVoInView(lista);
	}
	
	@Override
	public List<VendorManagementView> leggiByIdIncaricoNew(long idIncarico) throws Throwable {
		List<VendorManagement> lista = vendorManagementDAO.leggiByIdIncaricoNew(idIncarico);
		return (List<VendorManagementView>) convertiVoInView(lista);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public VendorManagementView inserisci(VendorManagementView vendorManagementView, Locale locale) throws Throwable {
		vendorManagementView.getVo().setStatoVendorManagement(anagraficaStatiDAO.leggiStatoVendorManagement(CostantiDAO.VENDOR_MANAGEMENT_STATO_BOZZA, locale.getLanguage().toUpperCase()));
		VendorManagement vendorManagement = vendorManagementDAO.inserisci(vendorManagementView.getVo());
		VendorManagementView view = new VendorManagementView();
		view.setVo(vendorManagement);
		return view;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(VendorManagementView vendorManagementView) throws Throwable {
		vendorManagementDAO.modifica(vendorManagementView.getVo());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<VendorManagementView> leggi(String lingua) throws Throwable {
		List<VendorManagement> lista = vendorManagementDAO.leggi(lingua);
		List<VendorManagementView> listaRitorno = (List<VendorManagementView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public List<VendorManagementView> leggi(String lingua, Date date1, Date date2) throws Throwable {
		List<VendorManagement> lista = vendorManagementDAO.leggi(lingua,date1,date2);
		List<VendorManagementView> listaRitorno = (List<VendorManagementView>) convertiVoInView(lista);		
		return listaRitorno;
	}
	
	@Override
	public Long conta(String lingua) throws Throwable {
		Long conta = vendorManagementDAO.conta(lingua);
		return conta;
	}
	
	@Override
	public Long conta(String lingua, Date date1, Date date2) throws Throwable {
		Long conta = vendorManagementDAO.conta(lingua, date1, date2);
		return conta;
	}
	
	@Override
	public VendorManagementView confermaVotazione(String idIncarico, Locale locale) throws Throwable { 
		Long id = Long.parseLong(idIncarico);
		
		List<VendorManagement> votazioni = vendorManagementDAO.leggiByIdIncaricoNew(id);
		
		if(votazioni != null){
			
			VendorManagement votazione = votazioni.get(0);
			votazione.setStatoVendorManagement(anagraficaStatiDAO.leggiStatoVendorManagement(CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO, locale.getLanguage().toUpperCase()));
			VendorManagement vendorManagement = vendorManagementDAO.modifica(votazione);
			VendorManagementView view = new VendorManagementView();
			view.setVo(vendorManagement);
			return view;
		}
		else{
			return null;
		}
	}
	
	@Override
	public VendorManagementView confermaVotazioni(List<String> ids, Locale locale) throws Throwable {
		
		if(ids != null && !ids.isEmpty()){
			
			for(String idIncarico: ids ){
				
				Long id = Long.parseLong(idIncarico);
				
				List<VendorManagement> votazioni = vendorManagementDAO.leggiByIdIncaricoNew(id);
				
				if(votazioni != null && !votazioni.isEmpty()){
					
					VendorManagement votazione = votazioni.get(0);
					votazione.setStatoVendorManagement(anagraficaStatiDAO.leggiStatoVendorManagement(CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO, locale.getLanguage().toUpperCase()));
					VendorManagement vendorManagement = vendorManagementDAO.modifica(votazione);
					VendorManagementView view = new VendorManagementView();
					view.setVo(vendorManagement);
				}
				else{
					continue;
				}
			}
			return new VendorManagementView();
		}
		else{
			return null;
		}
		
	}
	
	@Override
	protected Class<VendorManagement> leggiClassVO() { 
		return VendorManagement.class;
	}

	@Override
	protected Class<VendorManagementView> leggiClassView() { 
		return VendorManagementView.class;
	}

}