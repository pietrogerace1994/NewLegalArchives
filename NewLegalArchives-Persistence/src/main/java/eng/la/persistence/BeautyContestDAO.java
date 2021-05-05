package eng.la.persistence;

import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.BeautyContest;
import eng.la.model.RBeautyContestMateria;
import eng.la.model.RBeautyContestProfessionistaEsterno;

public interface BeautyContestDAO {
	public List<BeautyContest> leggi() throws Throwable;

	public List<BeautyContest> cerca(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto, 
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public BeautyContest leggi(long id) throws Throwable;
	
	public BeautyContest leggiTutti(long id) throws Throwable;
	
	public BeautyContest leggi(long id, FetchMode fetchMode) throws Throwable;

	public BeautyContest inserisci(BeautyContest vo) throws Throwable;

	public void modifica(BeautyContest vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String titolo, String dal, String al,String statoBeautyContestCode, String centroDiCosto) throws Throwable;

	public void inserisciBeautyContestMateria(RBeautyContestMateria vo) throws Throwable;
	
	public void cancellaBeautyContestMaterie(long id) throws Throwable;
	
	public void inserisciBeautyContestProfessionistaEsterno(RBeautyContestProfessionistaEsterno vo) throws Throwable;
	
	public void cancellaBeautyContestProfessionistiEsterni(long id) throws Throwable;
}

