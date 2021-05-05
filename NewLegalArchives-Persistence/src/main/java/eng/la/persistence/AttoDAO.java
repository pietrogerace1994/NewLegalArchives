package eng.la.persistence;

//import java.util.Date;
import java.util.List;

import eng.la.model.Atto;
import eng.la.model.CategoriaAtto;
import eng.la.model.EsitoAtto;
import eng.la.model.StatoAtto;

public interface AttoDAO {
	public void cancella(long id) throws Throwable;

	public Atto inserisci(Atto vo) throws Throwable;

	public void modifica(Atto vo) throws Throwable;

	public Atto leggi(long id) throws Throwable;

	public List<Atto> leggi() throws Throwable;

	public List<Atto> cerca(Atto vo) throws Throwable;

	public List<CategoriaAtto> listaCategoriaAtto(String lingua) throws Throwable;

	public CategoriaAtto getCategoria(long id) throws Throwable;

	public StatoAtto getStatoAtto(long id) throws Throwable;

	public Atto getAttoPerNumeroProtocollo() throws Throwable;

	/**
	 * Aggiunta del flag per gestire gli atti da validare
	 * @author MASSIMO CARUSO
	 */
	public List<Atto> getCercaAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto, long idSocieta,
			String tipoAtto, int elementiPerPagina, int numeroPagina,String order, boolean flagAltriUffici, boolean flagValida) throws Throwable;

	public StatoAtto getStatoAttoPerLingua(String lang, String cod_gruppo_lingua) throws Throwable;

	public CategoriaAtto getCategoriaPreLingua(String lang, String cod_gruppo_lingua) throws Throwable;

	/**
	 * Aggiunta del flag per gestire gli atti da validare
	 * @author MASSIMO CARUSO
	 */
	public Long contaAllSerch(String dal, String al, String numeroProtocollo, long idCategoriaAtto, long idSocieta,
			String tipoAtto , boolean flagAltriUffici, boolean flagValida) throws Throwable;

	public List<Atto> cerca(List<String> parole) throws Throwable;

	public Atto leggiSenzaHibernate(long id) throws Throwable;
	
	public List<StatoAtto> getListaStatoAttoPerLingua(String lang) throws Throwable ;
	//reporting
	public List<Atto> reportingAtti(Object[] params) throws Throwable;

	public List<EsitoAtto> listaEsitoAtto(String lingua) throws Throwable;

	public EsitoAtto getEsito(long id);
	public Atto leggiConPermessi(long id) throws Throwable;
	
	public Atto getAttoPerNumeroProtocollo(String numeroProtocollo);

	public EsitoAtto getEsitoByCode(String code, String lingua);
	
	/**
	 * Rimuove l'atto richiesto
	 * @author MASSIMO CARUSO
	 * @param id_atto l'id dell'atto da rimuovere
	 * @throws Throwable
	 */
	public void removeAtto(long id_atto) throws Throwable;
	
	/**
	 * Restituisce un sottoinsieme finito di atti da validare, definendo
	 * l'indice del primo elemento ed il numero di elementi da restituire.
	 * La lista viene costruita partendo dal risultato in posizione [inizio]
	 * fino al risultato in posizione [inizio + elementi].
	 * @author MASSIMO CARUSO
	 * @param inizio l'indice del primo elemento
	 * @param elementi il numero di elementi da restituire
	 * @return la lista di atti da validare di dimensione [elementi] a partire dall'elemento in posizione [inizio]
	 */
	public List<Atto> getAttiDaValidare(int inizio, int elementi) throws Throwable;
	
	/**
	 * Restituisce il numero totale di atti da validare
	 * @author MASSIMO CARUSO
	 * @return il numero di atti da validare
	 */
	public Long getNumeroAttiDaValidare() throws Throwable;
	
	/**
	 * Ricerca un atto riservato attraverso il documento associato.
	 * @param id l'id del documento associato.
	 * @return l'atto ricercato, null altrimenti
	 * @throws Throwable
	 * @author MASSIMO CARUSO
	 */
	public Atto getAttoRiservatoByIdDocumento(String id) throws Throwable;
}