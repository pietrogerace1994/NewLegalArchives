package eng.la.persistence;

import java.util.List;

import eng.la.model.OrganoSociale;
import eng.la.model.aggregate.OrganoSocialeAggregate;
import eng.la.model.filter.OrganoSocialeFilter;;

public interface OrganoSocialeDAO {

	public List<OrganoSociale> leggi() throws Throwable;

	public List<OrganoSociale> leggi(String lingua) throws Throwable;

	public OrganoSociale leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idOrganoSociale) throws Throwable;

	public void modifica(OrganoSociale vo) throws Throwable;

	public List<OrganoSociale> cerca(String nome) throws Throwable;

	public OrganoSociale inserisci(OrganoSociale vo) throws Throwable;

	public List<OrganoSociale> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public OrganoSociale leggi(long id) throws Throwable;

	public OrganoSocialeAggregate searchPagedOrganoSociale(OrganoSocialeFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;

	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable;
	
	public List<OrganoSociale> esporta() throws Throwable;

}
