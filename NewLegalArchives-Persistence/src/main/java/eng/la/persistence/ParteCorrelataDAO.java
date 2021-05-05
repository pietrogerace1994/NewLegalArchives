package eng.la.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import eng.la.model.ParteCorrelata;

public interface ParteCorrelataDAO {
	public ParteCorrelata inserisci(ParteCorrelata parteCorrelata) throws Throwable;
	public ParteCorrelata leggi(long id) throws Throwable;
	public void cancella(long id) throws Throwable;
	public List<ParteCorrelata> leggi() throws Throwable;
	public List<ParteCorrelata> leggi(boolean isAttive) throws Throwable;
	public List<ParteCorrelata> leggi(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione)throws Throwable;
	public List<ParteCorrelata> leggiElenco(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	public Long conta();
	
	public List<ParteCorrelata> cerca(ParteCorrelata parteCorrelata) throws Throwable;
	public List<ParteCorrelata> cerca(String denominazione,String codFiscale, String partitaIva) throws Throwable;
	public List<ParteCorrelata> ricerca(String denominazione, String codFiscale, String partitaIva) throws Throwable;
	public void modifica(ParteCorrelata parteCorrelata) throws Throwable;
	public HashMap<String, Object> cerca(String nomeUtenteVisualizzato,Boolean isUtenteSegreteriaSocietaria,
			 							 String denominazione,String codFiscale,String codPartitaIva,Date data ) throws Throwable;

}
