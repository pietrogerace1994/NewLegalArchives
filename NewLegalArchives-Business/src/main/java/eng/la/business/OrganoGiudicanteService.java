package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.view.OrganoGiudicanteView;
import eng.la.util.ListaPaginata;

public interface OrganoGiudicanteService {
	public List<OrganoGiudicanteView> leggi() throws Throwable;

	public OrganoGiudicanteView leggi(long id) throws Throwable;

	public ListaPaginata<OrganoGiudicanteView> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable;

	public List<OrganoGiudicanteView> cerca(String nome) throws Throwable;

	public OrganoGiudicanteView inserisci(OrganoGiudicanteView organoGiudicanteView) throws Throwable;

	public void modifica(OrganoGiudicanteView organoGiudicanteView) throws Throwable;

	public void cancella(OrganoGiudicanteView organoGiudicanteView) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public OrganoGiudicanteView leggi(String codice, Locale locale) throws Throwable;

	public List<OrganoGiudicanteView> leggi(Locale locale) throws Throwable;

	public List<OrganoGiudicanteView> leggiDaRicorso(long id)throws Throwable;

	public List<OrganoGiudicanteView> leggiDaGiudizio(long id)throws Throwable;
 
}
