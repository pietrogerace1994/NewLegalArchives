package eng.la.business;

import java.util.List;

import org.hibernate.FetchMode;

//@@DDS import com.filenet.api.core.Document;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.RProfDocumentoView;
import eng.la.model.view.RProfEstSpecView;
import eng.la.model.view.RProfessionistaNazioneView;
import eng.la.util.ListaPaginata;
 
public interface ProfessionistaEsternoService {
	
	public List<ProfessionistaEsternoView> leggi(boolean tutti) throws Throwable;
	
	public List<ProfessionistaEsternoView> leggiNazioneSpecializzazione(boolean tutti) throws Throwable;

	public ProfessionistaEsternoView leggi(long id) throws Throwable;
	
	public ListaPaginata<ProfessionistaEsternoView> cerca( String cognome, String nome, long studioLegaleId, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDescrizione  ) throws Throwable;
	
	public List<ProfessionistaEsternoView> cerca( String cognome, String nome, long studioLegaleId ) throws Throwable;
	
	public Long conta( String cognome, String nome, long studioLegaleId ) throws Throwable;
		
	public ProfessionistaEsternoView inserisci( ProfessionistaEsternoView professionistaEsternoView ) throws Throwable;
	
	public void modifica( ProfessionistaEsternoView professionistaEsternoView ) throws Throwable;
	
	public void cancella(long id) throws Throwable;
	
	public void cancella( ProfessionistaEsternoView professionistaEsternoView ) throws Throwable;
	
	public List<RProfessionistaNazioneView> leggiProfNazionebyId(long id) throws Throwable;
	
	public List<RProfEstSpecView> leggiProfSpecbyId(long id) throws Throwable;
	
	public List<RProfDocumentoView> leggiProfDocbyId(long id) throws Throwable;
	
	public ProfessionistaEsternoView leggi(long id, FetchMode fetchMode) throws Throwable;
	
	public Document leggiDocumentoUUID(String uuid) throws Throwable;

	public ProfessionistaEsternoView leggi(long id, boolean tutti) throws Throwable;

	public List<ProfessionistaEsternoView> leggiProfessionistiPerCategoria(String tipologiaProfessionista, boolean tutti) throws Throwable;
	
	public List<ProfessionistaEsternoView> leggiProfessionistiAbilitatiBeautyContest() throws Throwable;

	public byte[] leggiContenutoDocumentoUUID(String uuid) throws Throwable;
	
}
