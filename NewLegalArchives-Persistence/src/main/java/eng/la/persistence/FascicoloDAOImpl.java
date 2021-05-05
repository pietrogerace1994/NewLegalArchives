package eng.la.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Controparte;
import eng.la.model.Documento;
import eng.la.model.DocumentoProtCorrisp;
import eng.la.model.Fascicolo;
import eng.la.model.ParteCivile;
import eng.la.model.PersonaOffesa;
import eng.la.model.RCorrelazioneFascicoli;
import eng.la.model.RFascPrestNotar;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloMateria;
import eng.la.model.RFascicoloRicorso;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.ResponsabileCivile;
import eng.la.model.SoggettoIndagato;
import eng.la.model.StatoFascicolo;
import eng.la.model.TerzoChiamatoCausa;
import eng.la.model.TipoCategDocumentale;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil;
import eng.la.util.SpringUtil;

@Component("fascicoloDAO")
public class FascicoloDAOImpl extends HibernateDaoSupport implements FascicoloDAO, CostantiDAO {

	@Autowired
	public FascicoloDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Fascicolo> leggi() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Fascicolo leggi(long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}
	
	public Fascicolo leggiConPermessi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}

	@Override
	public Fascicolo leggiPerCronologia(long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		criteria.createAlias("incaricos", "incaricos", DetachedCriteria.LEFT_JOIN);

		criteria.createAlias("incaricos.statoIncarico", "statoIncarico", DetachedCriteria.LEFT_JOIN);

		criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno", DetachedCriteria.LEFT_JOIN);

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);

		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}

	@Override
	public List<Fascicolo> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione, String tipoPermesso) throws Throwable {

		return cerca(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria, controinteressato, dal, al,
				tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, null, null, elementiPerPagina,
				numeroPagina, ordinamento, ordinamentoDirezione, tipoPermesso,null,null);
	}

	@Override
	public List<Fascicolo> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione,
			String tipoPermesso,String societaAddebito,List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {

		Long numeroTotaleElementi = conta(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
				controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
				stato, tipoPermesso,societaAddebito,listaSNAM_SRG_GNL_STOGIT);
		if (numeroTotaleElementi == 0) {
			return new ArrayList<Fascicolo>();
		}

		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class, "fascicolo");
		criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (ordinamento == null) {
			criteria.addOrder(Order.desc("id"));
		} else {
			criteria.addOrder(Order.asc("fascicolo.owner"));
//			criteria.addOrder(Order.asc("fascicolo.permissionWrite"));
			ordinamento = ordinamento.equals("stato") ? "statoFascicolo.descrizione" : ordinamento;
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (dal != null && DateUtil.isData(dal)) {
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if (al != null && DateUtil.isData(al)) {
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59")));
		}

		if (stato != null && stato.trim().length() > 0) {
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", stato));
		}else{
			criteria.add(Restrictions.ne("statoFascicolo.codGruppoLingua", "C"));
		}

		if (owner != null && owner.trim().length() > 0) {
			criteria.add(Restrictions.eq("legaleInterno", owner));
		}

		if (controparte != null && controparte.trim().length() > 0) {
			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("contropartes.nome", controparte, MatchMode.ANYWHERE));
		}

		if (legaleEsterno != null && legaleEsterno.trim().length() > 0) {
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
					DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("professionistaEsterno.cognomeNome", legaleEsterno, MatchMode.ANYWHERE));
		}

		if (tipologiaFascicoloId != 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId != 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));

		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		if (oggetto != null && oggetto.trim().length() > 0) {
			criteria.add(Restrictions.ilike("oggettoSintetico", oggetto, MatchMode.ANYWHERE));
		}

		if (descrizione != null && descrizione.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
		}

		if (siglaCliente != null && siglaCliente.trim().length() > 0) {
			criteria.add(Restrictions.ilike("siglaCliente", siglaCliente, MatchMode.ANYWHERE));
		}

		if (autoritaGiudiziaria != null && autoritaGiudiziaria.trim().length() > 0) {
			criteria.add(Restrictions.ilike("autoritaGiudiziaria", autoritaGiudiziaria, MatchMode.ANYWHERE));
		}

		if (controinteressato != null && controinteressato.trim().length() > 0) {
			criteria.add(Restrictions.ilike("controinteressato", controinteressato, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}

		if (societaAddebito!=null && !societaAddebito.isEmpty()){
			criteria.createAlias("RFascicoloSocietas", "RFascicoloSocietas", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("RFascicoloSocietas.societa", "societa", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.eq("societa.id", new Long(societaAddebito)));
			criteria.add(Restrictions.eq("RFascicoloSocietas.tipologiaSocieta", "A"));
			criteria.add(Restrictions.isNull("societa.dataCancellazione"));

		}else{
			if (listaSNAM_SRG_GNL_STOGIT!=null && !listaSNAM_SRG_GNL_STOGIT.isEmpty()){
				criteria.createAlias("RFascicoloSocietas", "RFascicoloSocietas", DetachedCriteria.INNER_JOIN);
				criteria.createAlias("RFascicoloSocietas.societa", "societa", DetachedCriteria.INNER_JOIN);
				criteria.add(Restrictions.in("societa.id", listaSNAM_SRG_GNL_STOGIT));
				criteria.add(Restrictions.eq("RFascicoloSocietas.tipologiaSocieta", "A"));
				criteria.add(Restrictions.isNull("societa.dataCancellazione"));
			}
		}

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.alias(Projections.sqlProjection("decode(LEGALE_INTERNO,'"+currentSessionUtil.getUserId()+"','a','b') as owner", new String[]{"owner"},new Type[]{new StringType()})
						,"fascicolo.owner"))
//				.add(Projections.alias(Projections.sqlProjection("DECODE (id_tipo_autorizzazione, (select id from tipo_autorizzazione where cod_gruppo_lingua='GW'), 'a',  DECODE (id_tipo_autorizzazione, (select id from tipo_autorizzazione where cod_gruppo_lingua='UW'), 'b', 'c')) AS permissionWrite", new String[]{"permissionWrite"},new Type[]{new StringType()})
//										,"fascicolo.permissionWrite"))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataUltimaModifica"), "dataUltimaModifica")
				.add(Projections.property("dataCreazione"), "dataCreazione")
				.add(Projections.property("statoFascicolo"), "statoFascicolo")
				.add(Projections.property("statoFascicolo.descrizione"))
				.add(Projections.property("nome"), "nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				indicePrimoElemento + elementiPerPagina);

		List<Fascicolo> listaRitorno = null;

		if (lista != null) {
			listaRitorno = new ArrayList<Fascicolo>();
			int index = 0;
			for (Map<String, Object> f : lista) {
				if (index < elementiPerPagina) {
					listaRitorno.add(leggi((Long)f.get("id"), FetchMode.JOIN));
				}
				index++;
			}
		}

		return listaRitorno;
	}

	@Override
	public List<Fascicolo> cerca2(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione,
			String tipoPermesso, String matricolaOwner) throws Throwable {

		Long numeroTotaleElementi = conta2(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
				controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
				stato, tipoPermesso, matricolaOwner);
		if (numeroTotaleElementi == 0) {
			return new ArrayList<Fascicolo>();
		}

		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class, "fascicolo");
		criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (ordinamento == null) {
			criteria.addOrder(Order.desc("id"));
		} else {
			ordinamento = ordinamento.equals("stato") ? "statoFascicolo.descrizione" : ordinamento;
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (dal != null && DateUtil.isData(dal)) {
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if (al != null && DateUtil.isData(al)) {
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59")));
		}

		if (stato != null && stato.trim().length() > 0) {
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", stato));
		}

		if (owner != null && owner.trim().length() > 0) {
			criteria.add(Restrictions.eq("legaleInterno", owner));
		}

		if (controparte != null && controparte.trim().length() > 0) {
			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("contropartes.nome", controparte, MatchMode.ANYWHERE));
		}

		if (legaleEsterno != null && legaleEsterno.trim().length() > 0) {
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
					DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("professionistaEsterno.cognomeNome", legaleEsterno, MatchMode.ANYWHERE));
		}

		if (tipologiaFascicoloId != 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId != 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));

		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		if (oggetto != null && oggetto.trim().length() > 0) {
			criteria.add(Restrictions.ilike("oggettoSintetico", oggetto, MatchMode.ANYWHERE));
		}

		if (descrizione != null && descrizione.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
		}

		if (siglaCliente != null && siglaCliente.trim().length() > 0) {
			criteria.add(Restrictions.ilike("siglaCliente", siglaCliente, MatchMode.ANYWHERE));
		}

		if (autoritaGiudiziaria != null && autoritaGiudiziaria.trim().length() > 0) {
			criteria.add(Restrictions.ilike("autoritaGiudiziaria", autoritaGiudiziaria, MatchMode.ANYWHERE));
		}

		if (controinteressato != null && controinteressato.trim().length() > 0) {
			criteria.add(Restrictions.ilike("controinteressato", controinteressato, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		/*
		 * criteria.setProjection(Projections.projectionList().add(Projections.
		 * distinct(Projections.property("id")))
		 * .add(Projections.property("id"), "id")
		 * .add(Projections.property("dataUltimaModifica"),
		 * "dataUltimaModifica") .add(Projections.property("dataCreazione"),
		 * "dataCreazione") .add(Projections.property("statoFascicolo"),
		 * "statoFascicolo")
		 * .add(Projections.property("statoFascicolo.descrizione")).add(
		 * Projections.property("nome"), "nome"))
		 * .setResultTransformer(Transformers.aliasToBean(Fascicolo.class));
		 */
		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				indicePrimoElemento + elementiPerPagina);

		List<Fascicolo> listaRitorno = null;

		if (lista != null) {
			listaRitorno = new ArrayList<Fascicolo>();
			int index = 0;
			for (Fascicolo f : lista) {
				if (index < elementiPerPagina) {
					listaRitorno.add(leggi(f.getId(), FetchMode.JOIN));
				}
				index++;
			}
		}

		return listaRitorno;
	}

	@Override
	public Long conta(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria,
			String controinteressato, String dal, String al, long tipologiaFascicoloId, long settoreGiuridicoId,
			String controparte, String legaleEsterno, String owner, String stato, String tipoPermesso,String societaAddebito,List<Long> listaSNAM_SRG_GNL_STOGIT) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (dal != null && DateUtil.isData(dal)) {
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if (al != null && DateUtil.isData(al)) {
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59")));
		}

		if (controparte != null && controparte.trim().length() > 0) {
			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("contropartes.nome", controparte, MatchMode.ANYWHERE));
		}

		if (stato != null && stato.trim().length() > 0) {
			criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", stato));
		}else{
			criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ne("statoFascicolo.codGruppoLingua", "C"));
		}

		if (owner != null && owner.trim().length() > 0) {
			criteria.add(Restrictions.eq("legaleInterno", owner));
		}

		if (legaleEsterno != null && legaleEsterno.trim().length() > 0) {
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
					DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("professionistaEsterno.cognomeNome", legaleEsterno, MatchMode.ANYWHERE));
		}

		if (tipologiaFascicoloId != 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId != 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));

		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		if (oggetto != null && oggetto.trim().length() > 0) {
			criteria.add(Restrictions.ilike("oggettoSintetico", oggetto, MatchMode.ANYWHERE));
		}

		if (descrizione != null && descrizione.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
		}

		if (siglaCliente != null && siglaCliente.trim().length() > 0) {
			criteria.add(Restrictions.ilike("siglaCliente", siglaCliente, MatchMode.ANYWHERE));
		}

		if (autoritaGiudiziaria != null && autoritaGiudiziaria.trim().length() > 0) {
			criteria.add(Restrictions.ilike("autoritaGiudiziaria", autoritaGiudiziaria, MatchMode.ANYWHERE));
		}

		if (controinteressato != null && controinteressato.trim().length() > 0) {
			criteria.add(Restrictions.ilike("controinteressato", controinteressato, MatchMode.ANYWHERE));
		}

		if (societaAddebito!=null && !societaAddebito.isEmpty()){
			criteria.createAlias("RFascicoloSocietas", "RFascicoloSocietas", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("RFascicoloSocietas.societa", "societa", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.eq("societa.id", new Long(societaAddebito)));
			criteria.add(Restrictions.eq("RFascicoloSocietas.tipologiaSocieta", "A"));
			criteria.add(Restrictions.isNull("societa.dataCancellazione"));
		}else{
			if (listaSNAM_SRG_GNL_STOGIT!=null && !listaSNAM_SRG_GNL_STOGIT.isEmpty()){
				criteria.createAlias("RFascicoloSocietas", "RFascicoloSocietas", DetachedCriteria.INNER_JOIN);
				criteria.createAlias("RFascicoloSocietas.societa", "societa", DetachedCriteria.INNER_JOIN);
				criteria.add(Restrictions.in("societa.id", listaSNAM_SRG_GNL_STOGIT));
				criteria.add(Restrictions.eq("RFascicoloSocietas.tipologiaSocieta", "A"));
				criteria.add(Restrictions.isNull("societa.dataCancellazione"));
			}
		}

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public Long conta2(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria,
			String controinteressato, String dal, String al, long tipologiaFascicoloId, long settoreGiuridicoId,
			String controparte, String legaleEsterno, String owner, String stato, String tipoPermesso,
			String matricolaOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (dal != null && DateUtil.isData(dal)) {
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if (al != null && DateUtil.isData(al)) {
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59")));
		}

		if (controparte != null && controparte.trim().length() > 0) {
			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("contropartes.nome", controparte, MatchMode.ANYWHERE));
		}

		if (stato != null && stato.trim().length() > 0) {
			criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", stato));
		}

		if (owner != null && owner.trim().length() > 0) {
			criteria.add(Restrictions.eq("legaleInterno", owner));
		}

		if (legaleEsterno != null && legaleEsterno.trim().length() > 0) {
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.INNER_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
					DetachedCriteria.INNER_JOIN);
			criteria.add(Restrictions.ilike("professionistaEsterno.cognomeNome", legaleEsterno, MatchMode.ANYWHERE));
		}

		if (tipologiaFascicoloId != 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId != 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));

		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		if (oggetto != null && oggetto.trim().length() > 0) {
			criteria.add(Restrictions.ilike("oggettoSintetico", oggetto, MatchMode.ANYWHERE));
		}

		if (descrizione != null && descrizione.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
		}

		if (siglaCliente != null && siglaCliente.trim().length() > 0) {
			criteria.add(Restrictions.ilike("siglaCliente", siglaCliente, MatchMode.ANYWHERE));
		}

		if (autoritaGiudiziaria != null && autoritaGiudiziaria.trim().length() > 0) {
			criteria.add(Restrictions.ilike("autoritaGiudiziaria", autoritaGiudiziaria, MatchMode.ANYWHERE));
		}

		if (controinteressato != null && controinteressato.trim().length() > 0) {
			criteria.add(Restrictions.ilike("controinteressato", controinteressato, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<Fascicolo> cerca(List<String> parole, String dal, String al, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione, String tipoPermesso) throws Throwable {

		Long numeroTotaleElementi = conta(parole, dal, al, tipoPermesso);
		if (numeroTotaleElementi == 0) {
			return new ArrayList<Fascicolo>();
		}

		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("nome"));

		if (parole.size() == 1) {
			String parola = parole.get(0);

			Criterion crit = Restrictions.isNull("dataCancellazione");
			Conjunction conj = Restrictions.conjunction();
			conj.add(crit);

			Disjunction disj = Restrictions.disjunction();
			if (ordinamento == null) {
				criteria.addOrder(Order.desc("nome"));
			} else {
				if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
					criteria.addOrder(Order.asc(ordinamento));
				} else {
					criteria.addOrder(Order.desc(ordinamento));
				}
			}

			if (dal != null && DateUtil.isData(dal)) {

				// AND
				crit = Restrictions.ge("dataCreazione", DateUtil.toDate(dal));
				conj.add(crit);
			}

			if (al != null && DateUtil.isData(al)) {
				crit = Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59"));
				criteria.add(crit);
				conj.add(crit);
			}

			if (parola != null && parola.trim().length() > 0) {
				criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("contropartes.nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				criteria.createAlias("incaricos", "incaricos", DetachedCriteria.LEFT_JOIN);
				criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
						DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("professionistaEsterno.cognome", parola, MatchMode.ANYWHERE);
				disj.add(crit);
				crit = Restrictions.ilike("professionistaEsterno.nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				// OR
				crit = Restrictions.ilike("legaleInterno", parola);
				disj.add(crit);

				crit = Restrictions.ilike("nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("oggettoSintetico", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("descrizione", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("siglaCliente", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("autoritaGiudiziaria", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("controinteressato", parola, MatchMode.ANYWHERE);
				disj.add(crit);
			}

			criteria.add(disj);
			criteria.add(conj);
		} else {

			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno", DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);


			Criterion crit = Restrictions.isNull("dataCancellazione");
			Conjunction conj = Restrictions.conjunction();
			conj.add(crit);

			if (ordinamento == null) {
				criteria.addOrder(Order.desc("nome"));
			} else {
				if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
					criteria.addOrder(Order.asc(ordinamento));
				} else {
					criteria.addOrder(Order.desc(ordinamento));
				}
			}

			if (dal != null && DateUtil.isData(dal)) {

				// AND
				crit = Restrictions.ge("dataCreazione", DateUtil.toDate(dal));
				conj.add(crit);
			}

			if (al != null && DateUtil.isData(al)) {
				crit = Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59"));
				criteria.add(crit);
				conj.add(crit);
			}
			criteria.add(conj);

			Disjunction disj1 = Restrictions.disjunction();
			for (String parola : parole) {
				if (parola != null && parola.trim().length() > 0) {

					Disjunction disj = Restrictions.disjunction();

					crit = Restrictions.ilike("contropartes.nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("professionistaEsterno.cognome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("professionistaEsterno.nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					// OR
					crit = Restrictions.ilike("legaleInterno", parola);
					disj.add(crit);

					crit = Restrictions.ilike("nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("oggettoSintetico", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("descrizione", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("siglaCliente", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("autoritaGiudiziaria", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("controinteressato", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					disj1.add(disj);
				}
			}
			criteria.add(disj1);
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataUltimaModifica"), "dataUltimaModifica")
				.add(Projections.property("dataCreazione"), "dataCreazione")
				.add(Projections.property("nome"), "nome"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lista = (List<Map<String, Object>>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				indicePrimoElemento + elementiPerPagina);

		List<Fascicolo> listaRitorno = null;

		if (lista != null) {
			listaRitorno = new ArrayList<Fascicolo>();
			int index = 0;
			for (Map<String, Object> f : lista) {
				if (index < elementiPerPagina) {
					listaRitorno.add(leggi((Long)f.get("id"), FetchMode.JOIN));
				}
				index++;
			}
		}
		return listaRitorno;
	}

	@Override
	public List<Fascicolo> cerca(List<String> parole) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("nome"));

		if (parole.size() == 1) {
			String tok = parole.get(0);

			Criterion crit_dataCanc = Restrictions.isNull("dataCancellazione");

			Criterion crit_nome = Restrictions.ilike("nome", tok, MatchMode.ANYWHERE);
			Criterion crit_descrizione = Restrictions.ilike("descrizione", tok, MatchMode.ANYWHERE);
			Criterion crit_siglaCliente = Restrictions.ilike("siglaCliente", tok, MatchMode.ANYWHERE);
			Criterion crit_autoritaGiudiziaria = Restrictions.ilike("autoritaGiudiziaria", tok, MatchMode.ANYWHERE);
			Criterion crit_controinteressato = Restrictions.ilike("controinteressato", tok, MatchMode.ANYWHERE);

			criteria.createAlias("terzoChiamatoCausas", "terzoChiamatoCausas", DetachedCriteria.LEFT_JOIN);
			Criterion crit_terzoChiamatoInCausa = Restrictions.ilike("terzoChiamatoCausas.nome", tok,
					MatchMode.ANYWHERE);

			criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);
			Criterion crit_soggettoIndagato = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", tok,
					MatchMode.ANYWHERE);

			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
			Criterion crit_controparte = Restrictions.ilike("contropartes.nome", tok, MatchMode.ANYWHERE);

			Disjunction disj = Restrictions.disjunction();
			disj.add(crit_nome);
			disj.add(crit_descrizione);
			disj.add(crit_siglaCliente);
			disj.add(crit_autoritaGiudiziaria);
			disj.add(crit_controinteressato);
			disj.add(crit_terzoChiamatoInCausa);
			disj.add(crit_soggettoIndagato);
			disj.add(crit_controparte);

			Conjunction conj = Restrictions.conjunction();
			conj.add(crit_dataCanc);
			conj.add(disj);

			criteria.add(conj);

		} else if (parole.size() > 1) {

			Disjunction d = Restrictions.disjunction();

			for (int i = 0; i < parole.size(); i++) {
				String tok = parole.get(i);

				Criterion crit_dataCanc = Restrictions.isNull("dataCancellazione");

				Criterion crit_nome = Restrictions.ilike("nome", tok, MatchMode.ANYWHERE);
				Criterion crit_descrizione = Restrictions.ilike("descrizione", tok, MatchMode.ANYWHERE);
				Criterion crit_siglaCliente = Restrictions.ilike("siglaCliente", tok, MatchMode.ANYWHERE);
				Criterion crit_autoritaGiudiziaria = Restrictions.ilike("autoritaGiudiziaria", tok, MatchMode.ANYWHERE);
				Criterion crit_controinteressato = Restrictions.ilike("controinteressato", tok, MatchMode.ANYWHERE);

				criteria.createAlias("terzoChiamatoCausas", "terzoChiamatoCausas", DetachedCriteria.LEFT_JOIN);
				Criterion crit_terzoChiamatoInCausa = Restrictions.ilike("terzoChiamatoCausas.nome", tok,
						MatchMode.ANYWHERE);

				criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);
				Criterion crit_soggettoIndagato = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", tok,
						MatchMode.ANYWHERE);

				criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
				Criterion crit_controparte = Restrictions.ilike("contropartes.nome", tok, MatchMode.ANYWHERE);

				Disjunction disj = Restrictions.disjunction();
				disj.add(crit_nome);
				disj.add(crit_descrizione);
				disj.add(crit_siglaCliente);
				disj.add(crit_autoritaGiudiziaria);
				disj.add(crit_controinteressato);
				disj.add(crit_terzoChiamatoInCausa);
				disj.add(crit_soggettoIndagato);
				disj.add(crit_controparte);

				Conjunction conj = Restrictions.conjunction();
				conj.add(crit_dataCanc);
				conj.add(disj);

				d.add(conj);
			}

			criteria.add(d);
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Long conta(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria,
			String controinteressato, String dal, String al, long tipologiaFascicoloId, long settoreGiuridicoId,
			String controparte, String legaleEsterno, String tipoPermesso) {
		return conta(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria, controinteressato, dal, al,
				tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, null, null, tipoPermesso,null,null);
	}

	@Override
	public Long conta(List<String> parole, String dal, String al, String tipoPermesso) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("nome"));

		if (parole.size() == 1) {
			String parola = parole.get(0);

			Criterion crit = Restrictions.isNull("dataCancellazione");
			Conjunction conj = Restrictions.conjunction();
			conj.add(crit);

			Disjunction disj = Restrictions.disjunction();

			if (dal != null && DateUtil.isData(dal)) {

				// AND
				crit = Restrictions.ge("dataCreazione", DateUtil.toDate(dal));
				conj.add(crit);
			}

			if (al != null && DateUtil.isData(al)) {
				crit = Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59"));
				criteria.add(crit);
				conj.add(crit);
			}

			if (parola != null && parola.trim().length() > 0) {
				criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("contropartes.nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				criteria.createAlias("incaricos", "incaricos", DetachedCriteria.LEFT_JOIN);
				criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
						DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("professionistaEsterno.cognome", parola, MatchMode.ANYWHERE);
				disj.add(crit);
				crit = Restrictions.ilike("professionistaEsterno.nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);
				crit = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				// OR
				crit = Restrictions.ilike("legaleInterno", parola);
				disj.add(crit);

				crit = Restrictions.ilike("nome", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("oggettoSintetico", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("descrizione", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("siglaCliente", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("autoritaGiudiziaria", parola, MatchMode.ANYWHERE);
				disj.add(crit);

				crit = Restrictions.ilike("controinteressato", parola, MatchMode.ANYWHERE);
				disj.add(crit);
			}

			criteria.add(disj);
			criteria.add(conj);
		} else {

			criteria.createAlias("contropartes", "contropartes", DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("incaricos", "incaricos", DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("incaricos.professionistaEsterno", "professionistaEsterno",
					DetachedCriteria.LEFT_JOIN);
			criteria.createAlias("soggettoIndagatos", "soggettoIndagatos", DetachedCriteria.LEFT_JOIN);


			Criterion crit = Restrictions.isNull("dataCancellazione");
			Conjunction conj = Restrictions.conjunction();
			conj.add(crit);

			if (dal != null && DateUtil.isData(dal)) {

				// AND
				crit = Restrictions.ge("dataCreazione", DateUtil.toDate(dal));
				conj.add(crit);
			}

			if (al != null && DateUtil.isData(al)) {
				crit = Restrictions.lt("dataCreazione", DateUtil.getDataOra(al + " - 23:59:59"));
				criteria.add(crit);
				conj.add(crit);
			}
			criteria.add(conj);

			Disjunction disj1 = Restrictions.disjunction();
			for (String parola : parole) {
				if (parola != null && parola.trim().length() > 0) {

					Disjunction disj = Restrictions.disjunction();

					crit = Restrictions.ilike("contropartes.nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("professionistaEsterno.cognome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("professionistaEsterno.nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("soggettoIndagatos.nomeDecrypt", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					// OR
					crit = Restrictions.ilike("legaleInterno", parola);
					disj.add(crit);

					crit = Restrictions.ilike("nome", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("oggettoSintetico", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("descrizione", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("siglaCliente", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("autoritaGiudiziaria", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					crit = Restrictions.ilike("controinteressato", parola, MatchMode.ANYWHERE);
					disj.add(crit);

					disj1.add(disj);
				}
			}
			criteria.add(disj1);
		}


		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO);
		} else {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
		}
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	public List<Fascicolo> cercaAllFascicoli(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nome"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}
		criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
		criteria.add(Restrictions.eq("tipologiaFascicolo.lang", "IT"));
		criteria.add(Restrictions.eq("tipologiaFascicolo.codGruppoLingua", "TFSC_1"));

		int numeroRowInit = numeroPagina;
		int numeroRowOff = numeroPagina + elementiPerPagina;

		Long numeroTotaleElementi = contaAllSerch(nome);
		if (numeroTotaleElementi < numeroRowInit) {
			numeroRowInit = 0;
			numeroRowOff = 20;
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria, numeroRowInit, numeroRowOff);
		return lista;

	}

	public Long contaAllSerch(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
		criteria.add(Restrictions.eq("tipologiaFascicolo.id", (long) 1));

		criteria.setProjection(Projections.rowCount());

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<Fascicolo> cerca(String nome, String oggetto) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		if (oggetto != null && oggetto.trim().length() > 0) {
			criteria.add(Restrictions.ilike("oggettoSintetico", oggetto, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<Fascicolo> cerca(Integer anno, Integer mese) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		Date data_creazione_da = null;
		Date data_creazione_a = null;
		
		boolean flag = false;
		if (anno != null && anno.intValue() != 0) {
			flag = true;
			
			Calendar da = Calendar.getInstance();
			da.set(Calendar.YEAR, anno);
			da.set(Calendar.MONTH, Calendar.JANUARY);
			da.set(Calendar.DAY_OF_MONTH, da.getActualMinimum(Calendar.DAY_OF_MONTH));
            da.set(Calendar.HOUR_OF_DAY, 0);
            da.set(Calendar.MINUTE, 0);
            da.set(Calendar.SECOND, 0);
            da.set(Calendar.MILLISECOND, 0);
            data_creazione_da = da.getTime();
            
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, anno);
            a.set(Calendar.MONTH, Calendar.DECEMBER);
            a.set(Calendar.DAY_OF_MONTH, a.getActualMaximum(Calendar.DAY_OF_MONTH));
            a.set(Calendar.HOUR_OF_DAY, 23);
            a.set(Calendar.MINUTE, 59);
            a.set(Calendar.SECOND, 59);
            a.set(Calendar.MILLISECOND, 59);
            data_creazione_a = a.getTime();
			
		}
		
		if (flag && (mese != null && mese.intValue() != 0)) {
			Calendar da = Calendar.getInstance();
            da.set(Calendar.YEAR, anno);
            da.set(Calendar.MONTH, mese-1);
            da.set(Calendar.DAY_OF_MONTH, da.getActualMinimum(Calendar.DAY_OF_MONTH));
            da.set(Calendar.HOUR_OF_DAY, 0);
            da.set(Calendar.MINUTE, 0);
            da.set(Calendar.SECOND, 0);
            da.set(Calendar.MILLISECOND, 0);
            data_creazione_da = da.getTime();
            
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, anno);
            a.set(Calendar.MONTH, mese-1);
            a.set(Calendar.DAY_OF_MONTH, a.getActualMaximum(Calendar.DAY_OF_MONTH));
            a.set(Calendar.HOUR_OF_DAY, 23);
            a.set(Calendar.MINUTE, 59);
            a.set(Calendar.SECOND, 59);
            a.set(Calendar.MILLISECOND, 59);
            data_creazione_a = a.getTime();
		}
		
		if(flag){
			criteria.add(Restrictions.ge("dataCreazione", data_creazione_da));
			criteria.add(Restrictions.lt("dataCreazione", data_creazione_a));
		}

		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Fascicolo apriFascicolo(Fascicolo vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void aggiornaFascicolo(Fascicolo vo) {
		getHibernateTemplate().update(vo);
	}

	@Override
	public void eliminaFascicolo(Fascicolo vo) {
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void inserisciFascicoloSocieta(RFascicoloSocieta fascicoloSocieta) throws Throwable {
		getHibernateTemplate().save(fascicoloSocieta);
	}

	@Override
	public void inserisciFascicoloCorrelato(RCorrelazioneFascicoli fascicoloCorrelato) throws Throwable {
		getHibernateTemplate().save(fascicoloCorrelato);
	}

	@Override
	public List<String> leggiPerAutocompleteForo(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct foro from (select upper(foro) as foro from r_fascicolo_giudizio where upper(foro) like upper(?)"
								+ " union select upper(foro) as foro from r_fascicolo_ricorso where upper(foro) like upper(?) ) order by foro");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteAutoritaEmanante(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(AUTORITA_EMANANTE) as AUTORITA_EMANANTE from fascicolo where upper(AUTORITA_EMANANTE) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteControinteressato(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(CONTROINTERESSATO) as CONTROINTERESSATO from fascicolo where upper(CONTROINTERESSATO) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteAutoritaGiudiziaria(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(AUTORITA_GIUDIZIARIA) as AUTORITA_GIUDIZIARIA from fascicolo where upper(AUTORITA_GIUDIZIARIA) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteSoggettoIndagato(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(nome) as nome from SOGGETTO_INDAGATO where upper(nome) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteResponsabileCivile(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(nome) as nome from RESPONSABILE_CIVILE where upper(nome) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompleteParteCivile(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(nome) as nome from PARTE_CIVILE where upper(nome) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public List<String> leggiPerAutocompletePersonaOffesa(String term) throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select distinct upper(nome) as nome from PERSONA_OFFESA where upper(nome) like upper(?) ");
				query.setString(0, term + "%");
				return (List<String>)query.list();
			}
		});
	}

	@Override
	public String getNextNumeroFascicolo() throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<String>() {

			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select lpad(decode(to_char( max(to_number(nome) ) +1 ),'','1',to_char( max(to_number(nome) ) +1 )),5,0) as numero from fascicolo where nome like '0%' and data_creazione > to_date('01/11/2016','DD/MM/YYYY')");
				return (String) query.list().get(0);
			}
		});
	}

	@Override
	public void inserisciResponsabileCivile(ResponsabileCivile vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciParteCivile(ParteCivile vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciPersonaOffesa(PersonaOffesa vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciSoggettoIndagato(SoggettoIndagato vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciFascicoloMateria(RFascicoloMateria vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public Fascicolo leggi(long id, FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("attos", fetchMode);
		criteria.setFetchMode("contropartes", fetchMode);
		criteria.setFetchMode("fascicolo", fetchMode);
		criteria.setFetchMode("nazione", fetchMode);
		criteria.setFetchMode("progetto", fetchMode);
		criteria.setFetchMode("settoreGiuridico", fetchMode);
		criteria.setFetchMode("statoFascicolo", fetchMode);
		criteria.setFetchMode("tipologiaFascicolo", fetchMode);
		criteria.setFetchMode("tipoContenzioso", fetchMode);
		criteria.setFetchMode("valoreCausa", fetchMode);
		criteria.setFetchMode("incaricos", fetchMode);
		criteria.setFetchMode("parteCiviles", fetchMode);
		criteria.setFetchMode("responsabileCiviles", fetchMode);
		criteria.setFetchMode("personaOffesas", fetchMode);
		criteria.setFetchMode("pfrs", fetchMode);
		criteria.setFetchMode("RCorrelazioneFascicolis", fetchMode);
		criteria.setFetchMode("RFascicoloMaterias", fetchMode);
		criteria.setFetchMode("RFascicoloSocietas", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);
		criteria.setFetchMode("soggettoIndagatos", fetchMode);
		criteria.setFetchMode("terzoChiamatoCausas", fetchMode);
		criteria.setFetchMode("RFascPrestNotars", fetchMode);
		criteria.setFetchMode("RFascicoloRicorsos", fetchMode);
		criteria.setFetchMode("RFascicoloGiudizios", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);
		criteria.setFetchMode("schedaFondoRischi", fetchMode);
		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}
	
	@Override
	public Fascicolo leggiTutti(long id, FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("attos", fetchMode);
		criteria.setFetchMode("contropartes", fetchMode);
		criteria.setFetchMode("fascicolo", fetchMode);
		criteria.setFetchMode("nazione", fetchMode);
		criteria.setFetchMode("progetto", fetchMode);
		criteria.setFetchMode("settoreGiuridico", fetchMode);
		criteria.setFetchMode("statoFascicolo", fetchMode);
		criteria.setFetchMode("tipologiaFascicolo", fetchMode);
		criteria.setFetchMode("tipoContenzioso", fetchMode);
		criteria.setFetchMode("valoreCausa", fetchMode);
		criteria.setFetchMode("incaricos", fetchMode);
		criteria.setFetchMode("parteCiviles", fetchMode);
		criteria.setFetchMode("responsabileCiviles", fetchMode);
		criteria.setFetchMode("personaOffesas", fetchMode);
		criteria.setFetchMode("pfrs", fetchMode);
		criteria.setFetchMode("RCorrelazioneFascicolis", fetchMode);
		criteria.setFetchMode("RFascicoloMaterias", fetchMode);
		criteria.setFetchMode("RFascicoloSocietas", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);
		criteria.setFetchMode("soggettoIndagatos", fetchMode);
		criteria.setFetchMode("terzoChiamatoCausas", fetchMode);
		criteria.setFetchMode("RFascPrestNotars", fetchMode);
		criteria.setFetchMode("RFascicoloRicorsos", fetchMode);
		criteria.setFetchMode("RFascicoloGiudizios", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);
		criteria.setFetchMode("schedaFondoRischi", fetchMode);
		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}

	@Override
	public SoggettoIndagato leggiSoggettoIndagato(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SoggettoIndagato.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		SoggettoIndagato vo = (SoggettoIndagato) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return vo;
	}

	@Override
	public PersonaOffesa leggiPersonaOffesa(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonaOffesa.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		PersonaOffesa vo = (PersonaOffesa) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return vo;
	}

	@Override
	public ResponsabileCivile leggiResponsabileCivile(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ResponsabileCivile.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		ResponsabileCivile vo = (ResponsabileCivile) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return vo;
	}

	@Override
	public ParteCivile leggiParteCivile(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParteCivile.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		ParteCivile vo = (ParteCivile) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return vo;
	}

	@Override
	public void cancellaFascicoloResponsabileCivile(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ResponsabileCivile.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<ResponsabileCivile> lista = (List<ResponsabileCivile>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (ResponsabileCivile vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloParteCivile(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParteCivile.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<ParteCivile> lista = (List<ParteCivile>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (ParteCivile vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloPersonaOffesa(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonaOffesa.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<PersonaOffesa> lista = (List<PersonaOffesa>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (PersonaOffesa vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloSoggettoIndagato(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SoggettoIndagato.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<SoggettoIndagato> lista = (List<SoggettoIndagato>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (SoggettoIndagato vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloMaterie(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloMateria.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<RFascicoloMateria> lista = (List<RFascicoloMateria>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascicoloMateria vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaCorrelazioneFascicoli(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RCorrelazioneFascicoli.class);
		criteria.createAlias("fascicolo1", "fascicolo1");
		criteria.createAlias("fascicolo2", "fascicolo2");
		criteria.add(Restrictions.or(Restrictions.eq("fascicolo1.id", fasicoloId),
				Restrictions.eq("fascicolo2.id", fasicoloId)));

		@SuppressWarnings("unchecked")
		List<RCorrelazioneFascicoli> lista = (List<RCorrelazioneFascicoli>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RCorrelazioneFascicoli vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloControparte(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Controparte.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<Controparte> lista = (List<Controparte>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (Controparte vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloTerzoChiamatoCausa(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TerzoChiamatoCausa.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<TerzoChiamatoCausa> lista = (List<TerzoChiamatoCausa>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (TerzoChiamatoCausa vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloSocieta(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloSocieta.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<RFascicoloSocieta> lista = (List<RFascicoloSocieta>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascicoloSocieta vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloPrestazioneNotarile(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascPrestNotar.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<RFascPrestNotar> lista = (List<RFascPrestNotar>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascPrestNotar vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public DocumentoProtCorrisp aggiungiDocumento(Long fascicoloId, Long categoriaId, Long documentoId)
			throws Throwable {
		DocumentoProtCorrisp vo = new DocumentoProtCorrisp();
		Fascicolo fascicolo = leggi(fascicoloId);
		TipoCategDocumentale tipoCategDocumentale = new TipoCategDocumentale();
		tipoCategDocumentale.setId(categoriaId);
		vo.setFascicolo(fascicolo);
		vo.setTipoCategDocumentale(tipoCategDocumentale);
		Documento doc = new Documento();
		doc.setId(documentoId);
		vo.setDocumento(doc);
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancellaFascicoloRicorso(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloRicorso.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<RFascicoloRicorso> lista = (List<RFascicoloRicorso>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascicoloRicorso vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void cancellaFascicoloGiudizio(long fasicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloGiudizio.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", fasicoloId));
		@SuppressWarnings("unchecked")
		List<RFascicoloGiudizio> lista = (List<RFascicoloGiudizio>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascicoloGiudizio vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void inserisciFascicoloGiudizio(RFascicoloGiudizio vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciFascicoloRicorso(RFascicoloRicorso vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public void inserisciPrestazioneNotarile(RFascPrestNotar vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public List<Fascicolo> cercaUltimiFascicoli(long numRighe) throws Throwable {
		List<Fascicolo> fascicoloList = this.cerca(null, null, null, null, null, null, null, null, 0, 0, null, null,
				(int) numRighe, 1, "dataUltimaModifica", "desc", null);
		return fascicoloList;
	}

	@Override
	public Fascicolo leggiSenzaHibernate(long id) throws Throwable {
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement("select * from fascicolo where id = " + id);
			rs = st.executeQuery();
			if (rs.next()) {
				Fascicolo vo = new Fascicolo();
				vo.setNome(rs.getString("nome"));
				vo.setAutoritaEmanante(rs.getString("autorita_emanante"));
				vo.setAutoritaGiudiziaria(rs.getString("autorita_giudiziaria"));
				vo.setControinteressato(rs.getString("controinteressato"));
				vo.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
				vo.setDataChiusura(rs.getTimestamp("data_chiusura"));
				vo.setDataUltimaModifica(rs.getTimestamp("data_ultima_modifica"));
				vo.setDescrizione(rs.getString("descrizione"));
				vo.setId(id);
				vo.setJointVenture(rs.getString("joint_venture"));
				vo.setLegaleInterno(rs.getString("legale_interno"));
				vo.setNArchivioContenitore(rs.getBigDecimal("N_ARCHIVIO_CONTENITORE"));
				vo.setNumeroArchivio(rs.getBigDecimal("numero_archivio"));
				vo.setOggettoSintetico(rs.getString("oggetto_sintetico"));
				vo.setSiglaCliente(rs.getString("sigla_cliente"));
				vo.setRilevante(rs.getString("rilevante"));
				vo.setValoreCausaPratica(rs.getBigDecimal("VALORE_CAUSA_PRATICA"));

				vo.setStatoFascicolo(leggiStatoFascicoloSenzaHibernate(rs.getLong("id_stato_fascicolo"), c));
				return vo;
			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Throwable e) {
			}
			try {
				if (st != null)
					st.close();
			} catch (Throwable e) {
			}
			try {
				if (c != null)
					c.close();
			} catch (Throwable e) {
			}
		}
		return null;
	}

	private StatoFascicolo leggiStatoFascicoloSenzaHibernate(long idStato, Connection c) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = c.prepareStatement("select * from stato_fascicolo where id = " + idStato);
			rs = st.executeQuery();
			if (rs.next()) {
				StatoFascicolo vo = new StatoFascicolo();
				vo.setId(idStato);
				vo.setDescrizione(rs.getString("descrizione"));
				vo.setCodGruppoLingua(rs.getString("cod_gruppo_lingua"));
				return vo;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Throwable e) {
			}
			try {
				if (st != null)
					st.close();
			} catch (Throwable e) {
			}
		}
		return null;

	}

	// riassegnazione >
	@Override
	public List<Fascicolo> getListaFascicoliXRiassegna(String matricolaUtil) throws Throwable {

		List<Fascicolo> fascicoloList = new ArrayList<Fascicolo>(0);
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct r.id_fascicolo ");
		stringaSql.append("from R_UTENTE_FASCICOLO r ");
		stringaSql.append("where r.data_cancellazione is null");
		stringaSql.append(" and r.matricola_util='");
		stringaSql.append(matricolaUtil);
		stringaSql.append("'");
		fascicoloList = getHibernateTemplate().execute(new HibernateCallback<List<Fascicolo>>() {
			@Override
			public List<Fascicolo> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = (List<BigDecimal>)sqlQuery.list();
				List<Fascicolo> fascicoloList = new ArrayList<Fascicolo>();
				for (BigDecimal id : queryResult) {
					DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class)
							.add(Restrictions.eq("id", id.longValue()));
					@SuppressWarnings("unchecked")
					Fascicolo fascicolo = (Fascicolo) DataAccessUtils
					.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
					fascicoloList.add((Fascicolo) fascicolo);
				}
				return fascicoloList;
			}
		});
		if (fascicoloList.size() > 0)
			return fascicoloList;
		else
			return null;
	}

	// riassegnazione >
	@Override
	public List<BigDecimal> getIDFascicoliXRiassegna(String matricolaUtil, String idSocieta,
			String idTipologiaFascicolo, String idSettoreGiuridico, String nomeFascicolo) throws Throwable {

		List<BigDecimal> idFascicoli = new ArrayList<BigDecimal>(0);
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct r.id_fascicolo");
		stringaSql.append(" from R_UTENTE_FASCICOLO r, fascicolo fsc");
		stringaSql.append(" where r.id_fascicolo=fsc.id");
		stringaSql.append(" and r.matricola_util='");
		stringaSql.append(matricolaUtil);
		stringaSql.append("'");
		stringaSql.append(" and r.data_cancellazione is null");
		if (idSocieta != null && !idSocieta.equals("")) {
			stringaSql.append(
					" and r.id_fascicolo in(select distinct fs.id_fascicolo from r_fascicolo_societa fs where fs.id_societa=");
			stringaSql.append(idSocieta);
			stringaSql.append(")");
		}
		if (idTipologiaFascicolo != null && !idTipologiaFascicolo.equals("")) {
			stringaSql.append(" and fsc.id_tipologia_fascicolo=");
			stringaSql.append(idTipologiaFascicolo);
		}
		if (idSettoreGiuridico != null && !idSettoreGiuridico.equals("")) {
			stringaSql.append(" and fsc.id_settore_giuridico=");
			stringaSql.append(idSettoreGiuridico);
		}
		if (nomeFascicolo != null && !nomeFascicolo.trim().equals("")) {
			stringaSql.append(" and fsc.nome like '%");
			stringaSql.append(nomeFascicolo.trim());
			stringaSql.append("%'");
		}

		idFascicoli = getHibernateTemplate().execute(new HibernateCallback<List<BigDecimal>>() {
			@Override
			public List<BigDecimal> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = (List<BigDecimal>)sqlQuery.list();
				return queryResult;
			}
		});
		if (idFascicoli.size() > 0)
			return idFascicoli;
		else
			return null;
	}

	@Override
	public List<BigDecimal> getIDFascicoliXEstendiPermessi(String amministratore, String matricolaUtil, String legaleInterno, String idSocieta,
			String idTipologiaFascicolo, String idSettoreGiuridico, String nomeFascicolo) throws Throwable {

		List<BigDecimal> idFascicoli = new ArrayList<BigDecimal>(0);
		
//		StringBuffer stringaSql = new StringBuffer();
//		stringaSql.append("select distinct r.id_fascicolo");
//		stringaSql.append(" from R_UTENTE_FASCICOLO r, fascicolo fsc");
//		stringaSql.append(" where r.id_fascicolo=fsc.id");
//		
//		if(amministratore != null){
//			
//			if(legaleInterno != null){
//				stringaSql.append(" and r.matricola_util='");
//				stringaSql.append(legaleInterno);
//				stringaSql.append("'");
//			}
//		}
//		else{
//			stringaSql.append(" and r.matricola_util='");
//			stringaSql.append(matricolaUtil);
//			stringaSql.append("'");
//		}
//		stringaSql.append(" and r.data_cancellazione is null");
//		if (idSocieta != null && !idSocieta.equals("")) {
//			stringaSql.append(
//					" and r.id_fascicolo in(select distinct fs.id_fascicolo from r_fascicolo_societa fs where fs.id_societa=");
//			stringaSql.append(idSocieta);
//			stringaSql.append(")");
//		}
//		if (idTipologiaFascicolo != null && !idTipologiaFascicolo.equals("")) {
//			stringaSql.append(" and fsc.id_tipologia_fascicolo=");
//			stringaSql.append(idTipologiaFascicolo);
//		}
//		if (idSettoreGiuridico != null && !idSettoreGiuridico.equals("")) {
//			stringaSql.append(" and fsc.id_settore_giuridico=");
//			stringaSql.append(idSettoreGiuridico);
//		}
//		if (nomeFascicolo != null && !nomeFascicolo.trim().equals("")) {
//			stringaSql.append(" and fsc.nome like '%");
//			stringaSql.append(nomeFascicolo.trim());
//			stringaSql.append("%'");
//		}
//
//		idFascicoli = getHibernateTemplate().execute(new HibernateCallback<List<BigDecimal>>() {
//			@Override
//			public List<BigDecimal> doInHibernate(Session session) throws HibernateException, SQLException {
//				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
//				@SuppressWarnings("unchecked")
//				List<BigDecimal> queryResult = (List<BigDecimal>)sqlQuery.list();
//				return queryResult;
//			}
//		});
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);

		criteria.createAlias("RUtenteFascicolos", "RUtenteFascicolos");
		
		
		criteria.createAlias("RUtenteFascicolos.utente", "utente");
		
		if(amministratore != null){
			
			if(legaleInterno != null){
		
				criteria.add(Restrictions.eq("utente.matricolaUtil", legaleInterno));
			}
		}
		else{
				criteria.add(Restrictions.eq("utente.matricolaUtil", matricolaUtil));
		}
		
		criteria.add(Restrictions.isNull("RUtenteFascicolos.dataCancellazione"));
		
		if (idSocieta != null && !idSocieta.equals("")) {
		
			criteria.createAlias("RFascicoloSocietas", "RFascicoloSocietas");
			criteria.createAlias("RFascicoloSocietas.societa", "societa");
			criteria.add(Restrictions.eq("societa.id", idSocieta));

		}
		
		
		if (idTipologiaFascicolo != null && !idTipologiaFascicolo.equals("")) {
			
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", idTipologiaFascicolo));
		}
		
		
		if (idSettoreGiuridico != null && !idSettoreGiuridico.equals("")) {
			
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", idSettoreGiuridico));
		}
		
		
		if (nomeFascicolo != null && !nomeFascicolo.trim().equals("")) {
			criteria.add(Restrictions.ilike("nome", nomeFascicolo, MatchMode.ANYWHERE));
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		criteria.setProjection(Projections.property("id"));
		
		@SuppressWarnings("unchecked")
		List<Long> idFascicoliLong = getHibernateTemplate().findByCriteria(criteria);
		
		for(Long idLong : idFascicoliLong ){
			idFascicoli.add(new BigDecimal(idLong));
		}
			
			
		
		if (idFascicoli.size() > 0)
			return idFascicoli;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fascicolo> reportingFascicoli(Object[] params) throws Throwable {

		Object fascicoloTipologiaFascicolo = null, fascicoloSettoreGiuridico = null, fascicoloSocieta = null,
				fascicoloStatoFascicolo = null, fascicolo_data_creazione_da = null, fascicolo_data_creazione_a = null,
				fascicoloOwner = null, tipoContenzioso = null;

		if (params != null && params.length > 7) {
			fascicoloTipologiaFascicolo = params[0];
			fascicoloSettoreGiuridico = params[1];
			fascicoloSocieta = params[2];
			fascicoloStatoFascicolo = params[3];
			fascicolo_data_creazione_da = params[4];
			fascicolo_data_creazione_a = params[5];
			fascicoloOwner = params[6];
			tipoContenzioso = params[7];

		}

		Date dateDa = null, dateA = null;
		if (fascicolo_data_creazione_da != null)
			dateDa = DateUtil.toDate(fascicolo_data_creazione_da.toString());
		if (fascicolo_data_creazione_a != null)
			dateA = DateUtil.toDate(fascicolo_data_creazione_a.toString());

		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class);
		criteria.addOrder(Order.desc("id"));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (dateDa != null) {
			criteria.add(Restrictions.ge("dataCreazione", dateDa));
		}

		if (dateA != null) {
			criteria.add(Restrictions.lt("dataCreazione", dateA));
		}

		if (fascicoloOwner != null) {
			criteria.add(Restrictions.in("legaleInterno", (List<String>) fascicoloOwner));

		}

		if (fascicoloSocieta != null) {
			criteria.createAlias("societa", "societa");
			criteria.add(Restrictions.in("societa.id", (List<Long>) fascicoloSocieta));
		}

		if (fascicoloTipologiaFascicolo != null && !fascicoloTipologiaFascicolo.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.codGruppoLingua", fascicoloTipologiaFascicolo.toString()));
			criteria.add(Restrictions.eq("tipologiaFascicolo.lang", "IT"));
		}

		if (fascicoloSettoreGiuridico != null && !fascicoloSettoreGiuridico.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.codGruppoLingua", fascicoloSettoreGiuridico));
			criteria.add(Restrictions.eq("settoreGiuridico.lang", "IT"));
		}

		if (fascicoloStatoFascicolo != null && !fascicoloStatoFascicolo.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("statoFascicolo", "statoFascicolo");
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", fascicoloStatoFascicolo.toString()));
			criteria.add(Restrictions.eq("statoFascicolo.lang", "IT"));
		}

		if (tipoContenzioso != null) {
			criteria.createAlias("tipoContenzioso", "tipoContenzioso");
			criteria.add(Restrictions.eq("tipoContenzioso.codGruppoLingua", tipoContenzioso.toString()));
			criteria.add(Restrictions.eq("tipoContenzioso.lang", "IT"));
		}

		criteria.setFetchMode("nazione", FetchMode.JOIN);
		criteria.setFetchMode("progetto", FetchMode.JOIN);
		criteria.setFetchMode("settoreGiuridico", FetchMode.JOIN);
		criteria.setFetchMode("statoFascicolo", FetchMode.JOIN);
		criteria.setFetchMode("tipologiaFascicolo", FetchMode.JOIN);
		criteria.setFetchMode("tipoContenzioso", FetchMode.JOIN);
		criteria.setFetchMode("valoreCausa", FetchMode.JOIN);
		criteria.setFetchMode("incaricos", FetchMode.JOIN);
		criteria.setFetchMode("parteCiviles", FetchMode.JOIN);
		criteria.setFetchMode("responsabileCiviles", FetchMode.JOIN);
		criteria.setFetchMode("personaOffesas", FetchMode.JOIN);
		criteria.setFetchMode("pfrs", FetchMode.JOIN);
		criteria.setFetchMode("RCorrelazioneFascicolis", FetchMode.JOIN);
		criteria.setFetchMode("RFascicoloMaterias", FetchMode.JOIN);
		criteria.setFetchMode("RFascicoloSocietas", FetchMode.JOIN);
		criteria.setFetchMode("RUtenteFascicolos", FetchMode.JOIN);
		criteria.setFetchMode("soggettoIndagatos", FetchMode.JOIN);
		criteria.setFetchMode("terzoChiamatoCausas", FetchMode.JOIN);
		criteria.setFetchMode("RFascPrestNotars", FetchMode.JOIN);
		criteria.setFetchMode("RFascicoloRicorsos", FetchMode.JOIN);
		criteria.setFetchMode("RFascicoloGiudizios", FetchMode.JOIN);
		criteria.setFetchMode("RUtenteFascicolos", FetchMode.JOIN);

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_FASCICOLO);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Fascicolo> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista;
	}

	@Override
	public List<Fascicolo> leggiFascicoloOrdData(String idUtilMatricola) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).addOrder(Order.asc("dataCreazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		criteria.add(Restrictions.eq("legaleInterno", idUtilMatricola));

		criteria.setFetchMode("fascicolo", FetchMode.JOIN);

		// criteria.setFetchMode("statoFascicolo", FetchMode.JOIN);

		criteria.createAlias("statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", "A"));

		criteria.setFetchMode("incaricos", FetchMode.JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<Fascicolo> lista = (List<Fascicolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<RFascicoloGiudizio> getRFascicoloGiudizioDaIdFascicolo(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloGiudizio.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		@SuppressWarnings("unchecked")
		List<RFascicoloGiudizio> lista = (List<RFascicoloGiudizio>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public long getTipoCategoriaDocumentale(String codiceGruppoLingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoCategDocumentale.class);
		criteria.add(Restrictions.eq("codGruppoLingua", codiceGruppoLingua));
		criteria.add(Restrictions.eq("lang", "IT"));
		@SuppressWarnings("unchecked")
		TipoCategDocumentale tipoCategoriaDocumentale = (TipoCategDocumentale) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return tipoCategoriaDocumentale.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RFascicoloSocieta> reportingFascicoliRFS(Object[] params) throws Throwable {

		Object fascicoloTipologiaFascicolo = null, fascicoloSettoreGiuridico = null, fascicoloSocieta = null,
				fascicoloStatoFascicolo = null, fascicolo_data_creazione_da = null, fascicolo_data_creazione_a = null,
				fascicoloOwner = null, tipoContenzioso = null;

		if (params != null && params.length > 7) {
			fascicoloTipologiaFascicolo = params[0];
			fascicoloSettoreGiuridico = params[1];
			fascicoloSocieta = params[2];
			fascicoloStatoFascicolo = params[3];
			fascicolo_data_creazione_da = params[4];
			fascicolo_data_creazione_a = params[5];
			fascicoloOwner = params[6];
			tipoContenzioso = params[7];

		}

		Date dateDa = null, dateA = null;
		if (fascicolo_data_creazione_da != null)
			dateDa = DateUtil.toDate(fascicolo_data_creazione_da.toString());
		if (fascicolo_data_creazione_a != null)
			dateA = DateUtil.toDate(fascicolo_data_creazione_a.toString());

		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloSocieta.class);
		criteria.addOrder(Order.desc("id"));

		criteria.createAlias("fascicolo", "fascicolo",DetachedCriteria.INNER_JOIN);
		criteria.createAlias("societa", "societa",DetachedCriteria.INNER_JOIN);


		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (dateDa != null) {
			criteria.add(Restrictions.ge("fascicolo.dataCreazione", dateDa));
		}

		if (dateA != null) {
			criteria.add(Restrictions.lt("fascicolo.dataCreazione", dateA));
		}

		if (fascicoloOwner != null) {
			criteria.add(Restrictions.in("fascicolo.legaleInterno", (List<String>)fascicoloOwner));

		}

		if (fascicoloSocieta != null) {

			criteria.add(Restrictions.in("societa.id", (List<Long>)fascicoloSocieta));

		}

		if (fascicoloTipologiaFascicolo != null && !fascicoloTipologiaFascicolo.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("fascicolo.tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.codGruppoLingua", fascicoloTipologiaFascicolo.toString()));
			criteria.add(Restrictions.eq("tipologiaFascicolo.lang", "IT"));
		}

		if (fascicoloSettoreGiuridico != null && !fascicoloSettoreGiuridico.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("fascicolo.settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.codGruppoLingua", fascicoloSettoreGiuridico.toString()));
			criteria.add(Restrictions.eq("settoreGiuridico.lang", "IT"));
		}

		if (fascicoloStatoFascicolo != null && !fascicoloStatoFascicolo.toString().equalsIgnoreCase("TUTTI")) {
			criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo");
			criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua", fascicoloStatoFascicolo.toString()));
			criteria.add(Restrictions.eq("statoFascicolo.lang", "IT"));
		}

		if (tipoContenzioso != null) {
			criteria.createAlias("fascicolo.tipoContenzioso", "tipoContenzioso");
			criteria.add(Restrictions.eq("tipoContenzioso.codGruppoLingua", tipoContenzioso.toString()));
			criteria.add(Restrictions.eq("tipoContenzioso.lang", "IT"));
		}



		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<RFascicoloSocieta> lista = getHibernateTemplate().findByCriteria(criteria);
		List<RFascicoloSocieta> listaSoc=null;
		HashMap<String,RFascicoloSocieta> listaSocHash= new HashMap<String,RFascicoloSocieta>();
		if(lista!=null && lista.size()>0){
			listaSoc= new ArrayList<RFascicoloSocieta>();
			for(RFascicoloSocieta rsf:lista){	
				listaSocHash.put(""+rsf.getFascicolo().getId()+"-"+rsf.getSocieta().getId(), rsf);				
			}
			listaSoc.addAll(listaSocHash.values());
		}
		return listaSoc;
	}


	@Override
	public Fascicolo leggiConPermessi(long id, FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("attos", fetchMode);
		criteria.setFetchMode("contropartes", fetchMode);
		criteria.setFetchMode("fascicolo", fetchMode);
		criteria.setFetchMode("nazione", fetchMode);
		criteria.setFetchMode("progetto", fetchMode);
		criteria.setFetchMode("settoreGiuridico", fetchMode);
		criteria.setFetchMode("statoFascicolo", fetchMode);
		criteria.setFetchMode("tipologiaFascicolo", fetchMode);
		criteria.setFetchMode("tipoContenzioso", fetchMode);
		criteria.setFetchMode("valoreCausa", fetchMode);
		criteria.setFetchMode("incaricos", fetchMode);
		criteria.setFetchMode("parteCiviles", fetchMode);
		criteria.setFetchMode("responsabileCiviles", fetchMode);
		criteria.setFetchMode("personaOffesas", fetchMode);
		criteria.setFetchMode("pfrs", fetchMode);
		criteria.setFetchMode("RCorrelazioneFascicolis", fetchMode);
		criteria.setFetchMode("RFascicoloMaterias", fetchMode);
		criteria.setFetchMode("RFascicoloSocietas", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);
		criteria.setFetchMode("soggettoIndagatos", fetchMode);
		criteria.setFetchMode("terzoChiamatoCausas", fetchMode);
		criteria.setFetchMode("RFascPrestNotars", fetchMode);
		criteria.setFetchMode("RFascicoloRicorsos", fetchMode);
		criteria.setFetchMode("RFascicoloGiudizios", fetchMode);
		criteria.setFetchMode("RUtenteFascicolos", fetchMode);

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_FASCICOLO);

		@SuppressWarnings("unchecked")
		Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return fascicolo;
	}

	@Override
	public List<RFascicoloSocieta> getRFascicoloSocietas(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RFascicoloSocieta.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		@SuppressWarnings("unchecked")
		List<RFascicoloSocieta> lista = (List<RFascicoloSocieta>)getHibernateTemplate().findByCriteria(criteria);
		return lista;

	}


}
