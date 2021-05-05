package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.AffariSocietari;
import eng.la.model.DocumentoAffariSocietari;
import eng.la.model.aggregate.AffariSocietariAggregate;
import eng.la.model.filter.AffariSocietariFilter;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.view.AffariSocietariView;
import eng.la.util.ListaPaginata;

public interface AffariSocietariService {
	public List<AffariSocietariView> leggi() throws Throwable;

	public AffariSocietariView leggi(long id) throws Throwable;

	public ListaPaginata<AffariSocietariView> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public List<AffariSocietariView> cerca(String nome) throws Throwable;

	public AffariSocietariView inserisci(AffariSocietariView progettoView) throws Throwable;

	public long modifica(AffariSocietariView progettoView) throws Throwable;

	public void cancella(long idAffariSocietari) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public AffariSocietariView leggi(String codice, Locale locale) throws Throwable;

	public List<AffariSocietariView> leggi(Locale locale) throws Throwable;

	public AffariSocietariAggregate searchPagedAffariSocietari(AffariSocietariFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;


	public DocumentoAffariSocietari addDocument(AffariSocietari repertorio, long documentoId) throws Throwable;

	public List<CodiceDescrizioneBean> leggiListaSocietaControllanti()  throws Throwable;
	
	public List<Long> getListaSNAM_SRG_GNL_STOGIT() throws Throwable;


}
