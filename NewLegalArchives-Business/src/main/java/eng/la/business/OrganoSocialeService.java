package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.aggregate.OrganoSocialeAggregate;
import eng.la.model.filter.OrganoSocialeFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.OrganoSocialeView;
import eng.la.util.ListaPaginata;

public interface OrganoSocialeService {
	public List<OrganoSocialeView> leggi() throws Throwable;

	public OrganoSocialeView leggi(long id) throws Throwable;

	public ListaPaginata<OrganoSocialeView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<OrganoSocialeView> cerca(String nome) throws Throwable;

	public OrganoSocialeView inserisci(OrganoSocialeView progettoView) throws Throwable;

	public void modifica(OrganoSocialeView progettoView) throws Throwable;

	public void cancella(long idOrganoSociale) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public OrganoSocialeView leggi(String codice, Locale locale) throws Throwable;

	public List<OrganoSocialeView> leggi(Locale locale) throws Throwable;

	public OrganoSocialeAggregate searchPagedOrganoSociale(OrganoSocialeFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;

	public List<CodiceDescrizioneBean> leggiListaSocietaAffari() throws Throwable;

	public List<CodiceDescrizioneBean> leggiOrganiSociali(Locale locale);
	
	public List<OrganoSocialeView> esporta() throws Throwable;


}
