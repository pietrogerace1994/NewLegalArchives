package eng.la.persistence;

import java.util.List;

import eng.la.model.AffariSocietari;
import eng.la.model.RSocietaAffari;
import eng.la.model.aggregate.AffariSocietariAggregate;
import eng.la.model.filter.AffariSocietariFilter;

public interface AffariSocietariDAO {

	public List<AffariSocietari> leggi() throws Throwable;

	public List<AffariSocietari> leggi(String lingua) throws Throwable;

	public AffariSocietari leggi(String codice, String lingua) throws Throwable;

	public Long conta(String nome) throws Throwable;

	public void cancella(long idAffariSocietari) throws Throwable;

	public void modifica(AffariSocietari vo) throws Throwable;

	public List<AffariSocietari> cerca(String nome) throws Throwable;

	public AffariSocietari inserisci(AffariSocietari vo) throws Throwable;

	public List<AffariSocietari> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable;

	public AffariSocietari leggi(long id) throws Throwable;

	public AffariSocietariAggregate searchPagedAffariSocietari(AffariSocietariFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;

	public AffariSocietari leggiPerPagina(long id) throws Throwable;
	
	
	public void inserisciRSocietaAffari(RSocietaAffari rSocietaAffari) throws Throwable;
	
	public void cancellaRSocietaAffari(long idAffariSocietari) throws Throwable;
	
	public List<RSocietaAffari> getRSocietaAffaris(long idAffariSocietari) throws Throwable;
	
	public List<AffariSocietari> getListaSNAM_SRG_GNL_STOGIT() throws Throwable;

}
