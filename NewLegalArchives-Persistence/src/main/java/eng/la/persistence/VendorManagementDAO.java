package eng.la.persistence;

import java.util.Date;
import java.util.List;

import eng.la.model.VendorManagement;

public interface VendorManagementDAO {
	
	public List<VendorManagement> leggi() throws Throwable;
	
	public List<VendorManagement> leggi(String lingua) throws Throwable;

	public VendorManagement leggi(long id) throws Throwable;

	public VendorManagement inserisci(VendorManagement vo) throws Throwable;
	
	public VendorManagement modifica(VendorManagement vo) throws Throwable;

	public void cancella(long id) throws Throwable;
	
	public VendorManagement getDataValutazioneMax(String lingua) throws Throwable;

	public VendorManagement getDataValutazioneMin(String lingua) throws Throwable;
	
	public List<VendorManagement> leggi(String lingua, Date date1, Date date2) throws Throwable;
	
	public Long conta(String lingua) throws Throwable;
	
	public  List<VendorManagement> leggiByIdIncarico(long id) throws Throwable;
	
	public  List<VendorManagement> leggiByIdIncaricoNew(long id) throws Throwable;
	
	public Long conta(String lingua, Date date1, Date date2) throws Throwable;
	
}
