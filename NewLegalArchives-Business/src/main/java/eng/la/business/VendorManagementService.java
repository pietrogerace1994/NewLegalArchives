package eng.la.business;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import eng.la.model.view.VendorManagementView;

public interface VendorManagementService {
	
	public void cancella(long id) throws Throwable;
	public VendorManagementView inserisci(VendorManagementView w, Locale locale) throws Throwable;
	public void modifica(VendorManagementView w) throws Throwable;	
	public VendorManagementView leggi(long id) throws Throwable;
	public List<VendorManagementView> leggi() throws Throwable;
	public List<VendorManagementView> leggi(String lingua) throws Throwable;
	public List<VendorManagementView> leggi(String lingua, Date date1, Date date2) throws Throwable;
	public VendorManagementView getDataValutazioneMax(String lingua) throws Throwable;
	public VendorManagementView getDataValutazioneMin(String lingua) throws Throwable;
	public Long conta(String lingua) throws Throwable;
	public List<VendorManagementView> leggiByIdIncarico(long idIncarico) throws Throwable;
	public List<VendorManagementView> leggiByIdIncaricoNew(long idIncarico) throws Throwable;
	public Long conta(String lingua, Date date1, Date date2) throws Throwable;
	public VendorManagementView confermaVotazione(String incaricoId, Locale locale) throws Throwable;
	public VendorManagementView confermaVotazioni(List<String> ids, Locale locale) throws Throwable;
}