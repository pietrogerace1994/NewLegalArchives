package eng.la.persistence;

import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.ProfessionistaEsterno;
import eng.la.model.RProfDocumento;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;

public interface ProfessionistaEsternoDAO {

	public List<ProfessionistaEsterno> leggi(boolean tutti) throws Throwable;
	
	public List<ProfessionistaEsterno> leggiNazioneSpecializzazione(boolean tutti) throws Throwable;

	public List<ProfessionistaEsterno> cerca(String cognome, String nome, long studioLegaleId) throws Throwable;

	public List<ProfessionistaEsterno> cerca(String cognome, String nome, long studioLegaleId, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	
	public ProfessionistaEsterno leggi(long id) throws Throwable;

	public ProfessionistaEsterno inserisci(ProfessionistaEsterno vo) throws Throwable;

	public void modifica(ProfessionistaEsterno vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String cognome, String nome, long studioLegaleId) throws Throwable;
	
	public void inserisciProfessionistaEsternoNazione(RProfessionistaNazione professionistaEsternoNazione) throws Throwable;

	public void inserisciProfessionistaEsternoSpec(RProfEstSpec professionistaEsternoSpec) throws Throwable;
	
	public void cancellaProfessionistaEsternoNazione(long professionistaEsternoId) throws Throwable;
	
	public void cancellaProfessionistaEsternoSpec(long professionistaEsternoId) throws Throwable;
	
	public void cancellaLogicProfessionistaEsternoNazione(long professionistaEsternoId) throws Throwable;
	
	public void cancellaLogicProfessionistaEsternoSpec(long professionistaEsternoId) throws Throwable;
	
	public List<RProfessionistaNazione> leggiProfNazionebyId(long id) throws Throwable;
	
	public List<RProfEstSpec> leggiProfSpecbyId(long id) throws Throwable;
	
	public List<RProfDocumento> leggiProfDocbyId(long id) throws Throwable;
	
	public ProfessionistaEsterno leggi(long id, FetchMode fetchMode) throws Throwable;
	
	public ProfessionistaEsterno leggi(long id, FetchMode fetchMode, boolean tutti) throws Throwable;
	
	public RProfDocumento aggiungiDocumento(Long professionistaEsternoId, Long documentoId) throws Throwable;
	
	public void cancellaDocumento(long documentoId) throws Throwable;

	public ProfessionistaEsterno leggi(long id, boolean tutti) throws Throwable;

	public List<ProfessionistaEsterno> leggiProfessionistiPerCategoria(String tipologiaProfessionista, boolean tutti) throws Throwable;
	
	public List<ProfessionistaEsterno> leggiAbilitatiContest() throws Throwable;
}
