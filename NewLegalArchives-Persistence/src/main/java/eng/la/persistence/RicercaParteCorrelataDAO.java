package eng.la.persistence;

import java.util.Date;
import java.util.List;

import eng.la.model.RicercaParteCorrelata;

public interface RicercaParteCorrelataDAO {
	
	public RicercaParteCorrelata inserisci(RicercaParteCorrelata ricercaParteCorrelata) throws Throwable;
	public List<RicercaParteCorrelata> leggi() throws Throwable;
	public RicercaParteCorrelata leggi(long id) throws Throwable;
	public RicercaParteCorrelata leggi(String codFiscale, String partitaIva) throws Throwable;
	public void cancella(long id) throws Throwable;
	public List<RicercaParteCorrelata> leggi(Date dataInizio, Date dataFine) throws Throwable;
	public List<RicercaParteCorrelata> cerca(String denominazione,String codFiscale, String partitaIva) throws Throwable;
	
	public List<RicercaParteCorrelata> cerca(String nomeUtenteVisualizzato,
											 Boolean isUtentePrestazioneNotarili, 
											 String denominazione, 
											 String codFiscale,
											 String partitaIva, 
											 Date data) throws Throwable;
	
	public void modifica(RicercaParteCorrelata ricercaParteCorrelata) throws Throwable;
	
}
