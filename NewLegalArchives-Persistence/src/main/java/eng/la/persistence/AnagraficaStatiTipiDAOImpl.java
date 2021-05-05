package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

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

@SuppressWarnings("unchecked")
@Component("anagraficaStatiTipiDAO")
public class AnagraficaStatiTipiDAOImpl extends HibernateDaoSupport implements AnagraficaStatiTipiDAO, CostantiDAO {

	@Autowired
	public AnagraficaStatiTipiDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public StatoFascicolo leggiStatoFascicolo(long idStato) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoFascicolo.class);
		criteria.add(Restrictions.eq("id", idStato));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoFascicolo statoFascicolo = (StatoFascicolo) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoFascicolo;
	}

	@Cacheable("anagraficaLeggiStatiFascicoloCache")
	@Override
	public List<StatoFascicolo> leggiStatiFascicolo() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoFascicolo.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiWfCache")
	@Override
	public List<StatoWf> leggiStatiWf() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoWf.class).addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoWf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiEsitoValutazioneProfCache")
	@Override
	public List<StatoEsitoValutazioneProf> leggiStatiEsitoValutazioneProf() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoEsitoValutazioneProf.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoEsitoValutazioneProf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiFascicoloByCodeLinguaCache")
	@Override
	public StatoFascicolo leggiStatoFascicolo(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoFascicolo.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoFascicolo statoFascicolo = (StatoFascicolo) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoFascicolo;
	}

	@Cacheable("anagraficaLeggiStatiWfByCodeLinguaCache")
	@Override
	public StatoWf leggiStatoWf(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoWf.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoWf statoWf = (StatoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoWf;
	}

	@Cacheable("anagraficaLeggiStatiEsitoValutazioneProfByCodeLinguaCache")
	@Override
	public StatoEsitoValutazioneProf leggiStatoEsitoValutazioneProf(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoEsitoValutazioneProf.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoEsitoValutazioneProf statoEsitoValutazioneProf = (StatoEsitoValutazioneProf) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoEsitoValutazioneProf;
	}

	@Cacheable("anagraficaLeggiStatiIncaricoCache")
	@Override
	public List<StatoIncarico> leggiStatiIncarico() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoIncarico.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoIncarico> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiIncaricoByCodeLinguaCache")
	@Override
	public StatoIncarico leggiStatoIncarico(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoIncarico.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoIncarico statoIncarico = (StatoIncarico) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoIncarico;
	}
	
	@Override
	public StatoSchedaFondoRischi leggiStatoSchedaFondoRischi(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoSchedaFondoRischi.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoSchedaFondoRischi statoSchedaFondoRischi = (StatoSchedaFondoRischi) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoSchedaFondoRischi;
	}
	
	@Override
	public StatoBeautyContest leggiStatoBeautyContest(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoBeautyContest.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoBeautyContest statoBeautyContest = (StatoBeautyContest) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoBeautyContest;
	}
	
	@Override
	public StatoVendorManagement leggiStatoVendorManagement(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoVendorManagement.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoVendorManagement statoVendorManagement = (StatoVendorManagement) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoVendorManagement;
	}

	@Cacheable("anagraficaLeggiStatiFascicoloByLinguaCache")
	@Override
	public List<StatoFascicolo> leggiStatiFascicolo(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoFascicolo.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiProformaCache")
	@Override
	public List<StatoProforma> leggiStatiProforma() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProforma.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoProforma> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiAttoCache")
	@Override
	public List<StatoAtto> leggiStatiAtto() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoAtto.class).addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoAtto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiProfessionistaCache")
	@Override
	public List<StatoProfessionista> leggiStatiProfessionista() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProfessionista.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoProfessionista> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiProformaByCodeLinguaCache")
	@Override
	public StatoProforma leggiStatoProforma(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProforma.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoProforma statoProforma = (StatoProforma) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoProforma;
	}

	@Cacheable("anagraficaLeggiStatiAttoByCodeLinguaCache")
	@Override
	public StatoAtto leggiStatoAtto(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoAtto.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoAtto statoAtto = (StatoAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoAtto;
	}

	@Cacheable("anagraficaLeggiStatiProfessionistaByCodeLinguaCache")
	@Override
	public StatoProfessionista leggiStatoProfessionista(String codice, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProfessionista.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoProfessionista statoProfessionista = (StatoProfessionista) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoProfessionista;
	}

	@Cacheable("anagraficaLeggiStatiWfByLinguaCache")
	@Override
	public List<StatoWf> leggiStatiWf(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoWf.class).addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoWf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiEsitoValutazioneProfByLinguaCache")
	@Override
	public List<StatoEsitoValutazioneProf> leggiStatiEsitoValutazioneProf(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoEsitoValutazioneProf.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoEsitoValutazioneProf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiIncaricoByLinguaCache")
	@Override
	public List<StatoIncarico> leggiStatiIncarico(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoIncarico.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoIncarico> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<StatoSchedaFondoRischi> leggiStatiSchedaFondoRischi(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoSchedaFondoRischi.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoSchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiProformaByLinguaCache")
	@Override
	public List<StatoProforma> leggiStatiProforma(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProforma.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.ne("codGruppoLingua", "BDI"));/*BAZZA DA INVIARE*/
		List<StatoProforma> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiAttoByLinguaCache")
	@Override
	public List<StatoAtto> leggiStatiAtto(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoAtto.class).addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoAtto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiStatiProfessionistaByLinguaCache")
	@Override
	public List<StatoProfessionista> leggiStatiProfessionista(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProfessionista.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoProfessionista> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoContenziosoCache")
	@Override
	public List<TipoContenzioso> leggiTipiContenzioso() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoContenzioso.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoContenzioso> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiClasseWfCache")
	@Override
	public List<ClasseWf> leggiClassiWf() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClasseWf.class).addOrder(Order.asc("codice"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<ClasseWf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiGruppoUtenteCache")
	@Override
	public List<GruppoUtente> leggiGruppiUtente() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class).addOrder(Order.asc("codice"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<GruppoUtente> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoContenziosoByLinguaCache")
	@Override
	public List<TipoContenzioso> leggiTipiContenzioso(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoContenzioso.class)
				.addOrder(Order.asc("codGruppoLingua"));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		List<TipoContenzioso> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoContenziosoByCodeLinguaCache")
	@Override
	public TipoContenzioso leggiTipoContenzioso(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoContenzioso.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		TipoContenzioso tipoContenzioso = (TipoContenzioso) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoContenzioso;
	}

	@Cacheable("anagraficaLeggiClasseWfByCodeCache")
	@Override
	public ClasseWf leggiClasseWf(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClasseWf.class);
		criteria.add(Restrictions.eq("codice", codice));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		ClasseWf classeWf = (ClasseWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return classeWf;
	}

	@Cacheable("anagraficaLeggiGruppoUtenteByCodeCache")
	@Override
	public GruppoUtente leggiGruppoUtente(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
		criteria.add(Restrictions.eq("codice", codice));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		GruppoUtente gruppoUtente = (GruppoUtente) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return gruppoUtente;
	}

	// Tipo Correlazione //
	@Cacheable("anagraficaLeggiTipoCorrelazioneCache")
	@Override
	public List<TipoCorrelazione> leggiTipiCorrelazione() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCorrelazione.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoCorrelazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoCorrelazioneByIdCache")
	@Override
	public TipoCorrelazione leggiTipoCorrelazione(Long tipoCorrelazioneId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCorrelazione.class);
		criteria.add(Restrictions.eq("id", tipoCorrelazioneId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoCorrelazione tipoCorrelazione = (TipoCorrelazione) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoCorrelazione;
	}

	@Cacheable("anagraficaLeggiTipoCorrelazioneByLinguaCache")
	@Override
	public List<TipoCorrelazione> leggiTipiCorrelazione(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCorrelazione.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<TipoCorrelazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoCorrelazioneByCodeLinguaCache")
	@Override
	public TipoCorrelazione leggiTipoCorrelazione(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCorrelazione.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoCorrelazione tipoCorrelazione = (TipoCorrelazione) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoCorrelazione;
	}

	@Cacheable("anagraficaLeggiValoreCausaByIdCache")
	@Override
	public ValoreCausa leggiValoreCausa(Long valoreCausaId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ValoreCausa.class);
		criteria.add(Restrictions.eq("id", valoreCausaId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		ValoreCausa valoreCausa = (ValoreCausa) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return valoreCausa;
	}

	@Cacheable("anagraficaLeggiValoreCausaByLinguaCache")
	@Override
	public List<ValoreCausa> leggiValoreCausa(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ValoreCausa.class).addOrder(Order.asc("codGruppoLingua"));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		List<ValoreCausa> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoEntitaByLinguaCache")
	@Override
	public List<TipoEntita> leggiTipoEntita(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoEntita.class).addOrder(Order.asc("codGruppoLingua"));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		List<TipoEntita> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiPosizioneSocietaBySettoreGiuridicoIdCache")
	@Override
	public List<PosizioneSocieta> leggiPosizioneSocietaPerSettoreGiuridicoId(long settoreGiuridicoId, boolean tutte)
			throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneSocieta.class)
				.addOrder(Order.asc("codGruppoLingua"));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.createAlias("RSettGiuridicoPosSocietas", "RSettGiuridicoPosSocietas");
		criteria.add(Restrictions.eq("RSettGiuridicoPosSocietas.settoreGiuridico.id", settoreGiuridicoId));
		List<PosizioneSocieta> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoEntitaByIdCache")
	@Override
	public TipoEntita leggiTipoEntita(Long tipoEntitaId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoEntita.class);
		criteria.add(Restrictions.eq("id", tipoEntitaId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoEntita tipoEntita = (TipoEntita) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoEntita;
	}

	@Cacheable("anagraficaLeggiPosizioneSocietaByIdCache")
	@Override
	public PosizioneSocieta leggiPosizioneSocieta(long idPosizioneSocieta) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneSocieta.class);
		criteria.add(Restrictions.eq("id", idPosizioneSocieta));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		PosizioneSocieta posizioneSocieta = (PosizioneSocieta) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return posizioneSocieta;
	}

	@Cacheable("anagraficaLeggiValoreCausaCodiceLinguaCache")
	@Override
	public ValoreCausa leggiValoreCausa(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ValoreCausa.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		ValoreCausa valoreCausa = (ValoreCausa) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return valoreCausa;
	}

	@Cacheable("anagraficaLeggiPosizioneSocietaCodiceLinguaCache")
	@Override
	public PosizioneSocieta leggiPosizioneSocieta(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneSocieta.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		PosizioneSocieta posizioneSocieta = (PosizioneSocieta) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return posizioneSocieta;
	}

	@Cacheable("anagraficaLeggiTipoEntitaCodiceLinguaCache")
	@Override
	public TipoEntita leggiTipoEntita(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoEntita.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		TipoEntita tipoEntita = (TipoEntita) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoEntita;
	}

	@Cacheable("anagraficaLeggiTipoSoggettoIndagatoCodiceLinguaCache")
	@Override
	public TipoSoggettoIndagato leggiTipoSoggettoIndagato(String codice, String lingua, boolean tutte) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoSoggettoIndagato.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		TipoSoggettoIndagato tipoSoggettoIndagato = (TipoSoggettoIndagato) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoSoggettoIndagato;
	}

	@Cacheable("anagraficaLeggiTipoSoggettoIndagatoLinguaCache")
	@Override
	public List<TipoSoggettoIndagato> leggiTipoSoggettoIndagato(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoSoggettoIndagato.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		List<TipoSoggettoIndagato> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoSoggettoIndagatoIdCache")
	@Override
	public TipoSoggettoIndagato leggiTipoSoggettoIndagato(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoSoggettoIndagato.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoSoggettoIndagato tipoSoggettoIndagato = (TipoSoggettoIndagato) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoSoggettoIndagato;
	}

	@Cacheable("anagraficaLeggiTipoPrestazioneNotarileLinguaCache")
	@Override
	public List<TipoPrestNotarile> leggiTipoPrestazioneNotarile(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoPrestNotarile.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoPrestNotarile> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoPrestazioneNotarileIdCache")
	@Override
	public TipoPrestNotarile leggiTipoPrestazioneNotarile(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoPrestNotarile.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoPrestNotarile tipoPrestNotarile = (TipoPrestNotarile) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoPrestNotarile;
	}

	@Cacheable("anagraficaLeggiTipoPrestazioneNotarileCodiceLinguaCache")
	@Override
	public TipoPrestNotarile leggiTipoPrestazioneNotarile(String codice, String lingua, boolean tutte)
			throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoPrestNotarile.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if (!tutte) {
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		TipoPrestNotarile tipoPrestNotarile = (TipoPrestNotarile) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoPrestNotarile;
	}

	@Cacheable("anagraficaLeggiTipoCategoriaDocumentaleCodiceLinguaCache")
	@Override
	public TipoCategDocumentale leggiTipoCategoriaDocumentale(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCategDocumentale.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoCategDocumentale tipo = (TipoCategDocumentale) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipo;
	}

	@Cacheable("anagraficaLeggiTipoProfessionistaCodiceLinguaCache")
	@Override
	public TipoProfessionista leggiTipoProfessionista(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProfessionista.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoProfessionista tipo = (TipoProfessionista) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipo;
	}

	@Cacheable("anagraficaLeggiTipoCategoriaDocumentaleLinguaCache")
	@Override
	public List<TipoCategDocumentale> leggiTipoCategoriaDocumentale(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCategDocumentale.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoCategDocumentale> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoProfessionistaLinguaCache")
	@Override
	public List<TipoProfessionista> leggiTipoProfessionista(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProfessionista.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoProfessionista> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoValutaIdCache")
	@Override
	public TipoValuta leggiTipoValuta(Long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoValuta.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoValuta tipoValuta = (TipoValuta) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoValuta;
	}

	@Cacheable("anagraficaLeggiTipoValutaCache")
	@Override
	public List<TipoValuta> leggiTipoValuta(boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoValuta.class).addOrder(Order.asc("id"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<TipoValuta> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("anagraficaLeggiTipoAutorizzazioneCache")
	@Override
	public TipoAutorizzazione leggiTipoAutorizzazione(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoAutorizzazione.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		TipoAutorizzazione tipo = (TipoAutorizzazione) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipo;
	}

	@Override
	public CategoriaMailinglist leggiCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class)
				.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		CategoriaMailinglist categoriaMailinglist = (CategoriaMailinglist) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return categoriaMailinglist;
	} 
	
	@Override
	public CategoriaMailinglist leggiCategoria(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class)
				.addOrder(Order.asc("nomeCategoria"));
		if( !tutte ){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		CategoriaMailinglist categoriaMailinglist = (CategoriaMailinglist) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return categoriaMailinglist;
	}

	//@Cacheable("anagraficaLeggiCategorieMailByLangCache")
	@Override
	public List<CategoriaMailinglist> leggiCategorie(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class).addOrder(Order.asc("id"))
				.addOrder(Order.asc("nomeCategoria"));
		 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.isNull("categoriaPadre"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	//@Cacheable("anagraficaLeggiCategorieMailByParentCache")
	@Override
	public List<CategoriaMailinglist> leggiCategorie(long idPadre, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglist.class)
				.addOrder(Order.asc("nomeCategoria"));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("categoriaPadre.id", idPadre));
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@CacheEvict(value = {/* "anagraficaLeggiCategorieMailByLangCache", "anagraficaLeggiCategorieMailByParentCache",*/ "anagraficaLeggiTipoAutorizzazioneCache", "anagraficaLeggiTipoValutaCache",
			"anagraficaLeggiTipoValutaIdCache", "anagraficaLeggiTipoCategoriaDocumentaleLinguaCache",
			"anagraficaLeggiTipoCategoriaDocumentaleCodiceLinguaCache",
			"anagraficaLeggiTipoPrestazioneNotarileCodiceLinguaCache",
			"anagraficaLeggiTipoPrestazioneNotarileLinguaCache", "anagraficaLeggiTipoPrestazioneNotarileIdCache",
			"anagraficaLeggiTipoSoggettoIndagatoIdCache", "anagraficaLeggiTipoSoggettoIndagatoLinguaCache",
			"anagraficaLeggiTipoSoggettoIndagatoCodiceLinguaCache", "anagraficaLeggiValoreCausaCodiceLinguaCache",
			"anagraficaLeggiTipoEntitaCodiceLinguaCache", "anagraficaLeggiPosizioneSocietaCodiceLinguaCache",
			"anagraficaLeggiStatiFascicoloCache", "anagraficaLeggiStatiWfCache",
			"anagraficaLeggiStatiEsitoValutazioneProfCache", "anagraficaLeggiStatiEsitoValutazioneProfCache",
			"anagraficaLeggiStatiFascicoloByCodeLinguaCache", "anagraficaLeggiStatiWfByCodeLinguaCache",
			"anagraficaLeggiStatiEsitoValutazioneProfByCodeLinguaCache",
			"anagraficaLeggiStatiEsitoValutazioneProffByCodeLinguaCache", "anagraficaLeggiStatiIncaricoCache",
			"anagraficaLeggiStatiProformaCache", "anagraficaLeggiStatiAttoCache",
			"anagraficaLeggiStatiProfessionistaCache", "anagraficaLeggiStatiIncaricoByCodeLinguaCache",
			"anagraficaLeggiStatiProformaByCodeLinguaCache", "anagraficaLeggiStatiAttoByCodeLinguaCache",
			"anagraficaLeggiStatiProfessionistaByCodeLinguaCache", "anagraficaLeggiStatiFascicoloByLinguaCache",
			"anagraficaLeggiStatiWfByLinguaCache", "anagraficaLeggiStatiEsitoValutazioneProfByLinguaCache",
			"anagraficaLeggiStatiIncaricoByLinguaCache", "anagraficaLeggiStatiProformaByLinguaCache",
			"anagraficaLeggiStatiAttoByLinguaCache", "anagraficaLeggiStatiProfessionistaByLinguaCache",
			"anagraficaLeggiTipoContenziosoCache", "anagraficaLeggiClasseWfCache", "anagraficaLeggiGruppoUtenteCache",
			"anagraficaLeggiTipoContenziosoByLinguaCache", "anagraficaLeggiTipoContenziosoByCodeLinguaCache",
			"anagraficaLeggiClasseWfByCodeCache", "anagraficaLeggiGruppoUtenteByCodeCache",
			"anagraficaLeggiTipoCorrelazioneCache", "anagraficaLeggiTipoCorrelazioneByIdCache",
			"anagraficaLeggiTipoCorrelazioneByLinguaCache", "anagraficaLeggiTipoCorrelazioneByCodeLinguaCache",
			"anagraficaLeggiValoreCausaByIdCache", "anagraficaLeggiValoreCausaByLinguaCache",
			"anagraficaLeggiTipoEntitaByLinguaCache", "anagraficaLeggiPosizioneSocietaBySettoreGiuridicoIdCache",
			"anagraficaLeggiTipoEntitaByIdCache", "anagraficaLeggiPosizioneSocietaByIdCache" }, allEntries = true)
	@Override
	public void clearCache() {
		System.out.println("Pulisco la cache delle anagrafiche degli stati");
	}

	@Override
	public List<StatoNewsletter> leggiStatiNewsletter() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoNewsletter.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<StatoNewsletter> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public StatoNewsletter leggiStatoNewsletter(String codice, String language) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoNewsletter.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", language));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoNewsletter statoFascicolo = (StatoNewsletter) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoFascicolo;
	}

	@Override
	public StatoProtocollo leggiStatoProtocollo(String cod, String language) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProtocollo.class);
		criteria.add(Restrictions.eq("codGruppoLingua", cod));
		criteria.add(Restrictions.eq("lang", language));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		StatoProtocollo statoFascicolo = (StatoProtocollo) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return statoFascicolo;
	}

	@Override
	public List<StatoProtocollo> leggiStatiProtocollo(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoProtocollo.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoProtocollo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<CategoriaMailinglistStyle> leggiCategoriaMailingListStyle() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglistStyle.class)
				.addOrder(Order.asc("id"));
		List<CategoriaMailinglistStyle> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public CategoriaMailinglistStyle leggiCategoriaMailingListStyle(Long colorselector) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaMailinglistStyle.class);
		criteria.add(Restrictions.eq("id", colorselector));
		CategoriaMailinglistStyle categoriaMailinglistStyle = (CategoriaMailinglistStyle) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return categoriaMailinglistStyle;
	}

	@Override
	public Long leggiCategoriaMailingListNextOrd() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class );
		criteria.setProjection(Projections.max("ord"));
			Long max = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return max;
	}

	@Override
	public Long leggiCategoriaMailingListNextId() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class );
		criteria.setProjection(Projections.max("id"));
			Long max = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return max;
	}
	
	@Override
	public List<StatoBeautyContest> leggiStatiBeautyContest(String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StatoBeautyContest.class)
				.addOrder(Order.asc("codGruppoLingua"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<StatoBeautyContest> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
