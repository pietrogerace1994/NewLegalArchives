package eng.la.persistence;

import java.util.List;

import eng.la.model.OrganoGiudicante;

public interface OrganoGiudicanteDAO {

	public List<OrganoGiudicante> leggi() throws Throwable;

	public OrganoGiudicante leggi(long id) throws Throwable;

	public List<OrganoGiudicante> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<OrganoGiudicante> cerca(String nome) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public OrganoGiudicante leggi(String codice, String lingua) throws Throwable;

	public void modifica(OrganoGiudicante vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public OrganoGiudicante inserisci(OrganoGiudicante vo) throws Throwable;

	public List<OrganoGiudicante> leggi(String lingua) throws Throwable;

	public List<OrganoGiudicante> leggiDaGiudizio(long id) throws Throwable;

	public List<OrganoGiudicante> leggiDaRicorso(long id) throws Throwable;

}
