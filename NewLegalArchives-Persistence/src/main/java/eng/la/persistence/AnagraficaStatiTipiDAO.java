/*
 * @author Luigi Nardiello
 */
package eng.la.persistence;

import java.util.List;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.CategoriaMailinglistStyle;
import eng.la.model.ClasseWf;
import eng.la.model.GruppoUtente;
import eng.la.model.PosizioneSocieta;
import eng.la.model.StatoAtto;
import eng.la.model.StatoBeautyContest;
import eng.la.model.StatoEsitoValutazioneProf;
import eng.la.model.StatoFascicolo;
import eng.la.model.StatoIncarico;
import eng.la.model.StatoNewsletter;
import eng.la.model.StatoProfessionista;
import eng.la.model.StatoProforma;
import eng.la.model.StatoProtocollo;
import eng.la.model.StatoSchedaFondoRischi;
import eng.la.model.StatoVendorManagement;
import eng.la.model.StatoWf;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.TipoCategDocumentale;
import eng.la.model.TipoContenzioso;
import eng.la.model.TipoCorrelazione;
import eng.la.model.TipoEntita;
import eng.la.model.TipoPrestNotarile;
import eng.la.model.TipoProfessionista;
import eng.la.model.TipoSoggettoIndagato;
import eng.la.model.TipoValuta;
import eng.la.model.ValoreCausa;

/**
 * Interface AnagraficaStatiDAO.
 */
public interface AnagraficaStatiTipiDAO {

	/**
	 * Ritorna tutti gli stati fascicolo.
	 *
	 * @return List<StatoFascicolo>
	 * @throws Throwable
	 */
	public List<StatoFascicolo> leggiStatiFascicolo() throws Throwable;

	/**
	 * Ritorna una lista di stato fascicolo in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoFascicolo>
	 * @throws Throwable
	 */
	public List<StatoFascicolo> leggiStatiFascicolo(String lingua) throws Throwable;

	/**
	 * Ritorna uno stato fascicolo in base al parametro in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoFascicolo
	 * @throws Throwable
	 */
	public StatoFascicolo leggiStatoFascicolo(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna tutti gli stati incarico.
	 *
	 * @return List<StatoIncarico>
	 * @throws Throwable
	 */
	public List<StatoIncarico> leggiStatiIncarico() throws Throwable;

	/**
	 * Ritorna una lista di stato incarico in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoFascicolo>
	 * @throws Throwable
	 */
	public List<StatoIncarico> leggiStatiIncarico(String lingua);
	
	/**
	 * Ritorna una lista di stato scheda fondo rischi in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoSchedaFondoRischi>
	 * @throws Throwable
	 */
	public List<StatoSchedaFondoRischi> leggiStatiSchedaFondoRischi(String lingua);

	/**
	 * Ritorna uno stato incarico in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoIncarico
	 * @throws Throwable
	 */
	public StatoIncarico leggiStatoIncarico(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato scheda fondo rischi in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoIncarico
	 * @throws Throwable
	 */
	public StatoSchedaFondoRischi leggiStatoSchedaFondoRischi(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato vendor management in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoVendorManagement
	 * @throws Throwable
	 */
	public StatoVendorManagement leggiStatoVendorManagement(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna tutti gli stati professionista.
	 *
	 * @return List<StatoProfessionista>
	 * @throws Throwable
	 */
	public List<StatoProfessionista> leggiStatiProfessionista() throws Throwable;
	
		
	/**
	 * Ritorna tutti gli stati proforma.
	 *
	 * @return List<StatoProforma>
	 * @throws Throwable
	 */
	public List<StatoProforma> leggiStatiProforma() throws Throwable;
	
	/**
	 * Ritorna tutti gli stati atto.
	 *
	 * @return List<StatoAtto>
	 * @throws Throwable
	 */
	public List<StatoAtto> leggiStatiAtto() throws Throwable;

	/**
	 * Ritorna una lista di stato proforma in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoProforma>
	 * @throws Throwable
	 */
	public List<StatoProforma> leggiStatiProforma(String lingua);
	
	/**
	 * Ritorna una lista di stato atto in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoAtto>
	 * @throws Throwable
	 */
	public List<StatoAtto> leggiStatiAtto(String lingua);
	
	/**
	 * Ritorna una lista di stato professionista in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoProfessionista>
	 * @throws Throwable
	 */
	public List<StatoProfessionista> leggiStatiProfessionista(String lingua);
	
	/**
	 * Ritorna uno stato proforma in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoProforma
	 * @throws Throwable
	 */
	public StatoProforma leggiStatoProforma(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato atto in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoAtto
	 * @throws Throwable
	 */
	public StatoAtto leggiStatoAtto(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato professionista in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return StatoProfessionista
	 * @throws Throwable
	 */
	public StatoProfessionista leggiStatoProfessionista(String codice, String lingua) throws Throwable;
	
	/**
	
	/**
	 * Ritorna tutti gli stati fascicolo.
	 *
	 * @return List<StatoFascicolo>
	 * @throws Throwable
	 */
	public List<StatoWf> leggiStatiWf() throws Throwable;
	
	/**
	 * Ritorna tutti gli stati esito valutazione di un professionista.
	 *
	 * @return List<StatoEsitoValutazioneProf>
	 * @throws Throwable
	 */
	public List<StatoEsitoValutazioneProf> leggiStatiEsitoValutazioneProf() throws Throwable;

	/**
	 * Ritorna una lista di stato workflow in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoWf>
	 * @throws Throwable
	 */
	public List<StatoWf> leggiStatiWf(String lingua) throws Throwable;
	
	/**
	 * Ritorna una lista di stato esito valutazione di un professionista in base al parametro in input
	 *
	 * @param lingua
	 * @return List<StatoEsitoValutazioneProf>
	 * @throws Throwable
	 */
	public List<StatoEsitoValutazioneProf> leggiStatiEsitoValutazioneProf(String lingua) throws Throwable;

	/**
	 * Ritorna uno stato workflow in base al parametro in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoWf
	 * @throws Throwable
	 */
	public StatoWf leggiStatoWf(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato esito valutazione professionista in base al parametro in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoEsitoValutazioneProf
	 * @throws Throwable
	 */
	public StatoEsitoValutazioneProf leggiStatoEsitoValutazioneProf(String codice, String lingua) throws Throwable;

	
	/**
	 * Ritorna tutti i TipoContenzioso.
	 *
	 * @return List<TipoContenzioso>
	 * @throws Throwable
	 */
	public List<TipoContenzioso> leggiTipiContenzioso() throws Throwable;

	/**
	 * Ritorna una lista di TipoContenzioso in base al parametro in input
	 *
	 * @param lingua
	 * @return List<TipoContenzioso>
	 * @throws Throwable
	 */
	public List<TipoContenzioso> leggiTipiContenzioso(String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna uno TipoContenzioso in base al parametro in input
	 *
	 * @param lingua
	 * @param codice
	 * @return TipoContenzioso
	 * @throws Throwable
	 */
	public TipoContenzioso leggiTipoContenzioso(String codice, String lingua, boolean tutte) throws Throwable;
	
	/**
	 * Ritorna tutti i tipi di workflow.
	 *
	 * @return List<ClasseWf>
	 * @throws Throwable
	 */
	public List<ClasseWf> leggiClassiWf() throws Throwable;
	
	/**
	 * Ritorna tutti i tipi di gruppi utente.
	 *
	 * @return List<GruppoUtente>
	 * @throws Throwable
	 */
	public List<GruppoUtente> leggiGruppiUtente() throws Throwable;


	/**
	 * Ritorna un tipo workflow in base al codice in inout
	 *
	 * @param codice
	 * @return ClasseWf
	 * @throws Throwable
	 */
	public ClasseWf leggiClasseWf(String codice) throws Throwable;
	
	/**
	 * Ritorna un gruppo utente in base al codice in inout
	 *
	 * @param codice
	 * @return GruppoUtente
	 * @throws Throwable
	 */
	public GruppoUtente leggiGruppoUtente(String codice) throws Throwable;


	/**
	 * Ritorna tutti i TipoCorrelazione.
	 *
	 * @return List<TipoCorrelazione>
	 * @throws Throwable
	 */
	public List<TipoCorrelazione> leggiTipiCorrelazione() throws Throwable;

	/**
	 * Ritorna un TipoCorrelazione per id.
	 *<p>
	 * @param tipoCorrelazioneId 
	 * @return tipoCorrelazione
	 * @throws Throwable
	 */
	public TipoCorrelazione leggiTipoCorrelazione(Long tipoCorrelazioneId) throws Throwable;
	
	/**
	 * Ritorna una lista di TipoCorrelazione in base al parametro in input
	 * <p>
	 * @param lingua
	 * @return List<TipoCorrelazione>
	 * @throws Throwable
	 */
	public List<TipoCorrelazione> leggiTipiCorrelazione(String lingua) throws Throwable;

	/**
	 * Ritorna un TipoCorrelazione in base al parametro in input
	 * <p>
	 * @param lingua
	 * @param codice
	 * @return TipoCorrelazione
	 * @throws Throwable
	 */
	public TipoCorrelazione leggiTipoCorrelazione(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna una lista di ValoreCausa in base al parametro in input
	 *
	 * @param lingua
	 * @return List<ValoreCausa>
	 * @throws Throwable
	 */
	public List<ValoreCausa> leggiValoreCausa(String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna una oggetto ValoreCausa in base al parametro in input
	 * 
	 * @param valoreCausaId
	 * @return ValoreCausa
	 * @throws Throwable
	 */
	public ValoreCausa leggiValoreCausa(Long valoreCausaId) throws Throwable;

	/**
	 * Ritorna una lista di TipoEntita in base al parametro in input
	 *
	 * @param lingua
	 * @return List<TipoEntita>
	 * @throws Throwable
	 */
	public List<TipoEntita> leggiTipoEntita(String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna una lista di PosizioneSocieta in base al parametro in input
	 *
	 * @param settoreGiuridicoId
	 * @return List<PosizioneSocieta>
	 * @throws Throwable
	 */
	public List<PosizioneSocieta> leggiPosizioneSocietaPerSettoreGiuridicoId(long settoreGiuridicoId, boolean tutte) throws Throwable;

	/**
	 * Ritorna una oggetto TipoEntita in base al parametro in input
	 * 
	 * @param tipoEntitaId
	 * @return TipoEntita
	 * @throws Throwable
	 */
	public TipoEntita leggiTipoEntita(Long tipoEntitaId) throws Throwable;

	public PosizioneSocieta leggiPosizioneSocieta(long idPosizioneSocieta) throws Throwable;

	public ValoreCausa leggiValoreCausa(String codice, String lingua, boolean tutte) throws Throwable;

	public PosizioneSocieta leggiPosizioneSocieta(String codice, String lingua, boolean tutte) throws Throwable;

	public TipoEntita leggiTipoEntita(String codice, String lingua, boolean tutte) throws Throwable;

	public TipoSoggettoIndagato leggiTipoSoggettoIndagato(String codice, String lingua, boolean tutte);
	
	/**
	 * Pulisce la cache relativa alle anagrafiche degli stati.
	 */
	public void clearCache();

	public List<TipoSoggettoIndagato> leggiTipoSoggettoIndagato(String lingua, boolean tutte) throws Throwable;

	public TipoSoggettoIndagato leggiTipoSoggettoIndagato(long id) throws Throwable;

	public TipoPrestNotarile leggiTipoPrestazioneNotarile(long id)throws Throwable;

	public List<TipoPrestNotarile> leggiTipoPrestazioneNotarile(String lingua)throws Throwable;

	public TipoPrestNotarile leggiTipoPrestazioneNotarile(String codice, String lingua, boolean tutte)throws Throwable;

	public TipoCategDocumentale leggiTipoCategoriaDocumentale(String codice, String lingua)throws Throwable;
	
	public TipoProfessionista leggiTipoProfessionista(String codice, String lingua)throws Throwable;

	public List<TipoCategDocumentale> leggiTipoCategoriaDocumentale(String lingua)throws Throwable;
	
	public List<TipoProfessionista> leggiTipoProfessionista(String lingua)throws Throwable;

	public TipoValuta leggiTipoValuta(Long id)throws Throwable;

	public List<TipoValuta> leggiTipoValuta(boolean tutte)throws Throwable;

	public TipoAutorizzazione leggiTipoAutorizzazione(String codice, String lingua)throws Throwable;

	public StatoFascicolo leggiStatoFascicolo(long idStato)throws Throwable;

	public CategoriaMailinglist leggiCategoria(long id) throws Throwable;

	public CategoriaMailinglist leggiCategoria(String codice, String lingua, boolean tutte) throws Throwable;

	public List<CategoriaMailinglist> leggiCategorie(String lingua) throws Throwable;
	public List<CategoriaMailinglist> leggiCategorie(long idPadre, boolean tutte) throws Throwable;

	public List<StatoNewsletter> leggiStatiNewsletter() throws Throwable;

	public StatoNewsletter leggiStatoNewsletter(String codice, String language) throws Throwable;

	public StatoProtocollo leggiStatoProtocollo(String cod, String language) throws Throwable;
	
	public List<StatoProtocollo> leggiStatiProtocollo(String lingua) throws Throwable;

	public List<CategoriaMailinglistStyle> leggiCategoriaMailingListStyle() throws Throwable;

	public CategoriaMailinglistStyle leggiCategoriaMailingListStyle(Long colorselector) throws Throwable;

	public Long leggiCategoriaMailingListNextOrd() throws Throwable;

	public Long leggiCategoriaMailingListNextId() throws Throwable;

	public StatoBeautyContest leggiStatoBeautyContest(String codice, String lingua) throws Throwable;
	
	public List<StatoBeautyContest> leggiStatiBeautyContest(String lingua);

}
