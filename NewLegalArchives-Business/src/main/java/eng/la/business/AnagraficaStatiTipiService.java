package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.CategoriaMailinglistStyle;
import eng.la.model.view.CategoriaMailinglistStyleView;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.ClasseWfView;
import eng.la.model.view.GruppoUtenteView;
import eng.la.model.view.PosizioneSocietaView;
import eng.la.model.view.StatoBeautyContestView;
import eng.la.model.view.StatoFascicoloView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.StatoNewsletterView;
import eng.la.model.view.StatoProformaView;
import eng.la.model.view.StatoProtocolloView;
import eng.la.model.view.StatoSchedaFondoRischiView;
import eng.la.model.view.StatoVendorManagementView;
import eng.la.model.view.StatoWfView;
import eng.la.model.view.TipoCategDocumentaleView;
import eng.la.model.view.TipoContenziosoView;
import eng.la.model.view.TipoCorrelazioneView;
import eng.la.model.view.TipoEntitaView;
import eng.la.model.view.TipoPrestNotarileView;
import eng.la.model.view.TipoProfessionistaView;
import eng.la.model.view.TipoSoggettoIndagatoView;
import eng.la.model.view.TipoValutaView;
import eng.la.model.view.ValoreCausaView;

public interface AnagraficaStatiTipiService {
	/**
	 * Ritorna tutti gli stati fascicolo.
	 *
	 * @return List<StatoFascicoloView>
	 * @throws Throwable
	 */
	public List<StatoFascicoloView> leggiStatiFascicolo() throws Throwable;

	/**
	 * Ritorna tutti gli stati fascicolo in base alla lingua.
	 *
	 * @param lingua
	 * @return List<StatoFascicoloView>
	 * @throws Throwable
	 */
	public List<StatoFascicoloView> leggiStatiFascicolo(String lingua) throws Throwable;

	/**
	 * Ritorna uno stato fascicolo in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoFascicoloView
	 * @throws Throwable
	 */
	public StatoFascicoloView leggiStatiFascicolo(String codice, String lingua) throws Throwable;

	/**
	 * Ritorna tutti gli stati incarico.
	 *
	 * @return List<StatoIncaricoView>
	 * @throws Throwable
	 */
	public List<StatoIncaricoView> leggiStatiIncarico() throws Throwable;

	/**
	 * Ritorna tutti gli stati incarico in base alla lingua.
	 *
	 * @param lingua
	 * @return List<StatoIncaricoView>
	 * @throws Throwable
	 */
	public List<StatoIncaricoView> leggiStatiIncarico(String lingua) throws Throwable;

	/**
	 * Ritorna uno stato incarico in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoIncaricoView
	 * @throws Throwable
	 */
	public StatoIncaricoView leggiStatoIncarico(String codice, String lingua) throws Throwable;

	/**
	 * Ritorna tutti gli stati del workflow.
	 *
	 * @return List<StatoWfView>
	 * @throws Throwable
	 */
	public List<StatoWfView> leggiStatiWf() throws Throwable;

	/**
	 * Ritorna tutti gli stati workflow in base alla lingua.
	 *
	 * @param lingua
	 * @return List<StatoWfView>
	 * @throws Throwable
	 */
	public List<StatoWfView> leggiStatiWf(String lingua) throws Throwable;

	/**
	 * Ritorna uno stato workflow in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoWfView
	 * @throws Throwable
	 */
	public StatoWfView leggiStatiWf(String codice, String lingua) throws Throwable;

	/**
	 * Ritorna tutti i tipi contenzioso
	 *
	 * @return List<TipoContenziosoView>
	 * @throws Throwable
	 */
	public List<TipoContenziosoView> leggiTipoContenzioso() throws Throwable;

	/**
	 * Ritorna tutti i tipi contenzioso in base alla lingua.
	 *
	 * @param lingua
	 * @return List<TipoContenziosoView>
	 * @throws Throwable
	 */
	public List<TipoContenziosoView> leggiTipoContenzioso(String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna un tipo contenzioso in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoIncaricoView
	 * @throws Throwable
	 */
	public TipoContenziosoView leggiTipoContenzioso(String codice, String lingua, boolean tutti) throws Throwable;

	/**
	 * Ritorna tutti i tipi workflow
	 *
	 * @return List<ClasseWfView>
	 * @throws Throwable
	 */
	public List<ClasseWfView> leggiClasseWf() throws Throwable;

	/**
	 * Ritorna tutti i gruppi utente
	 *
	 * @return List<GruppoUtenteView>
	 * @throws Throwable
	 */
	public List<GruppoUtenteView> leggiGruppoUtente() throws Throwable;

	/**
	 * Ritorna un tipo workflow in base ai parametri in input
	 *
	 * @param codice
	 * @return ClasseWfView
	 * @throws Throwable
	 */
	public ClasseWfView leggiClasseWf(String codice) throws Throwable;

	/**
	 * Ritorna un gruppo in base ai parametri in input
	 *
	 * @param codice
	 * @return GruppoUtenteView
	 * @throws Throwable
	 */
	public GruppoUtenteView leggiGruppoUtente(String codice) throws Throwable;

	// sezione tipo correlazione
	/**
	 * Ritorna tutti i tipi correlazione
	 *
	 * @return List<TipoContenziosoView>
	 * @throws Throwable
	 */
	public List<TipoCorrelazioneView> leggiTipoCorrelazione() throws Throwable;

	/**
	 * Ritorna il tipo correlazione per id
	 * <p>
	 * 
	 * @param tipoCorrelazioneId
	 * @return
	 * @throws Throwable
	 */
	public TipoCorrelazioneView leggiTipoCorrelazione(Long tipoCorrelazioneId) throws Throwable;

	/**
	 * Ritorna tutti i tipi correlazione in base alla lingua.
	 *
	 * @param lingua
	 * @return List<TipoContenziosoView>
	 * @throws Throwable
	 */
	public List<TipoCorrelazioneView> leggiTipoCorrelazione(String lingua) throws Throwable;

	/**
	 * Ritorna un tipo correlazione in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return TipoCorrelazioneView
	 * @throws Throwable
	 */
	public TipoCorrelazioneView leggiTipoCorrelazione(String codice, String lingua) throws Throwable;

	/**
	 * Ritorna tutti i ValoreCausa in base alla lingua.
	 *
	 * @param lingua
	 * @return List<ValoreCausaView>
	 * @throws Throwable
	 */
	public List<ValoreCausaView> leggiValoreCausa(String lingua, boolean tutte) throws Throwable;

	public ValoreCausaView leggiValoreCausa(String codice, String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna tutti i TipoEntita in base alla lingua.
	 *
	 * @param lingua
	 * @return List<TipoEntitaView>
	 * @throws Throwable
	 */
	public List<TipoEntitaView> leggiTipoEntita(String lingua, boolean tutte) throws Throwable;

	/**
	 * Ritorna tutti le PosizioneSocieta in base al parametro in input.
	 *
	 * @param settoreGiuridicoId
	 * @return List<PosizioneSocietaView>
	 * @throws Throwable
	 */
	public List<PosizioneSocietaView> leggiPosizioneSocietaPerSettoreGiuridicoId(long settoreGiuridicoId, boolean tutte)
			throws Throwable;

	/**
	 * Ritorna un oggetto ValoreCausaView in base al parametro in input.
	 *
	 * @param valoreCausaId
	 * @return ValoreCausaView
	 * @throws Throwable
	 */
	public ValoreCausaView leggiValoreCausa(Long valoreCausaId) throws Throwable;

	/**
	 * Ritorna un oggetto TipoEntitaView in base al parametro in input.
	 *
	 * @param tipoEntitaId
	 * @return TipoEntitaView
	 * @throws Throwable
	 */

	public TipoEntitaView leggiTipoEntita(Long tipoEntitaId) throws Throwable;

	public PosizioneSocietaView leggiPosizioneSocieta(long idPosizioneSocieta) throws Throwable;

	public PosizioneSocietaView leggiPosizioneSocieta(String codice, String lingua, boolean tutti) throws Throwable;

	public TipoEntitaView leggiTipoEntita(String codice, Locale locale, boolean tutti) throws Throwable;

	public TipoSoggettoIndagatoView leggiTipoSoggettoIndagato(String codice, Locale locale, boolean tutti)
			throws Throwable;

	/**
	 * Pulisce la cache relativa alle anagrafiche degli stati e tipi.
	 */
	public void clearCache();

	public List<TipoSoggettoIndagatoView> leggiTipoSoggettoIndagato(String lingua, boolean tutte) throws Throwable;

	public TipoSoggettoIndagatoView leggiTipoSoggettoIndagato(long id) throws Throwable;

	public List<TipoPrestNotarileView> leggiTipoPrestazioneNotarile(String lingua, boolean tutti) throws Throwable;

	public TipoPrestNotarileView leggiTipoPrestazioneNotarile(long id) throws Throwable;

	public TipoPrestNotarileView leggiTipoPrestazioneNotarile(String codice, Locale locale, boolean tutti)
			throws Throwable;

	public List<TipoCategDocumentaleView> leggiTipoCategDocumentale(String lingua) throws Throwable;

	public List<TipoProfessionistaView> leggiTipoProfessionista(String lingua) throws Throwable;

	public TipoCategDocumentaleView leggiTipoCategoriaDocumentale(String codice, Locale locale) throws Throwable;

	public TipoProfessionistaView leggiTipoProfessionista(String codice, Locale locale) throws Throwable;

	public StatoProformaView leggiStatoProforma(String codGruppoLingua, String lingua) throws Throwable;

	public List<StatoProformaView> leggiStatiProforma(String lingua) throws Throwable;

	public TipoValutaView leggiTipoValuta(Long id) throws Throwable;

	public List<TipoValutaView> leggiTipoValuta(boolean tutte) throws Throwable;

	public CategoriaMailinglistView leggiCategoria(long id) throws Throwable;

	public CategoriaMailinglistView leggiCategoria(String codice, String lingua, boolean tutte) throws Throwable;

	public List<CategoriaMailinglistView> leggiCategorie(String lingua) throws Throwable;

	public List<CategoriaMailinglistView> leggiCategorie(long idPadre, boolean tutte) throws Throwable;

	public StatoNewsletterView leggiStatoNewsletter(String newsletterStatoBozza, String language) throws Throwable;
	
	
	public StatoProtocolloView leggiStatoProtocollo(String statoProtocollo, String language) throws Throwable;
	
	public List<StatoProtocolloView> leggiStatiProtocollo(String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato scheda fondo rischi in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoSchedaFondoRischiView
	 * @throws Throwable
	 */
	public StatoSchedaFondoRischiView leggiStatoSchedaFondoRischi(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna uno stato vendor management in base ai parametri in input
	 *
	 * @param codice
	 * @param lingua
	 * @return StatoVenderManagementView
	 * @throws Throwable
	 */
	public StatoVendorManagementView leggiStatoVendorManagement(String codice, String lingua) throws Throwable;
	
	/**
	 * Ritorna tutti gli stati incarico in base alla lingua.
	 *
	 * @param lingua
	 * @return List<StatoIncaricoView>
	 * @throws Throwable
	 */
	public List<StatoSchedaFondoRischiView> leggiStatiSchedaFondoRischi(String lingua) throws Throwable;
	
	public StatoBeautyContestView leggiStatoBeautyContest(String codice, String lingua) throws Throwable;
	
	public List<CategoriaMailinglistStyleView> leggiCategoriaMailingListStyle() throws Throwable;

	public CategoriaMailinglistStyle leggiCategoriaMailingListStyle(Long colorselector) throws Throwable;

	public Long leggiCategoriaMailingListNextOrd() throws Throwable;

	public Long leggiCategoriaMailingListNextId() throws Throwable;
	
	public List<StatoBeautyContestView> leggiStatiBeautyContest(String lingua) throws Throwable;
	
}
