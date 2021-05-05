package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Disjunction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.OrganoSociale;
import eng.la.model.aggregate.OrganoSocialeAggregate;
import eng.la.model.filter.OrganoSocialeFilter;

@Component("organoSocialeDAO")
public class OrganoSocialeDAOImpl extends HibernateDaoSupport implements OrganoSocialeDAO, CostantiDAO {

	@Autowired
	public OrganoSocialeDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<OrganoSociale> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		@SuppressWarnings("unchecked")
		List<OrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<OrganoSociale> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<OrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public OrganoSociale leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		OrganoSociale organoSociale = (OrganoSociale) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return organoSociale;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void cancella(long idOrganoSociale) throws Throwable {
		OrganoSociale voDB = leggi(idOrganoSociale);
		voDB.setDataCancellazione(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(OrganoSociale vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<OrganoSociale> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<OrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public OrganoSociale inserisci(OrganoSociale vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<OrganoSociale> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class);
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

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(nome);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<OrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				elementiPerPagina);
		return lista;
	}

	@Override
	public OrganoSociale leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		OrganoSociale organoSociale = (OrganoSociale) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return organoSociale;
	}

	@Override
	public OrganoSocialeAggregate searchPagedOrganoSociale(OrganoSocialeFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {

		List<OrganoSociale> returnList = null;
		OrganoSocialeAggregate out = new OrganoSocialeAggregate();
		if (filter != null) {
			DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class);
			criteria.createAlias("societaAffari", "societaAffari");
			
			// EVO OTTIMIZZAZIONE FILTRI & QUERIES RIDP6U9
			if (filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")) {
				criteria.addOrder(Order.asc(filter.getSortBy()));
			} else {
				criteria.addOrder(Order.desc(filter.getSortBy()));
			}

			criteria.add(Restrictions.isNull("dataCancellazione"));

			if (filter.getIdSocietaAffari() != null && filter.getIdSocietaAffari().longValue() > 0) {
				criteria.add(Restrictions.eq("societaAffari.id", filter.getIdSocietaAffari()));
			}
			else{
				if(listaSNAM_SRG_GNL_STOGIT != null && !listaSNAM_SRG_GNL_STOGIT.isEmpty()){
					
					if(filter.getGruppoSnam() != null && !filter.getGruppoSnam().isEmpty()){
						if("NO".equals(filter.getGruppoSnam())){
							criteria.add(Restrictions.not(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT)));
						}else{
							criteria.add(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT));
						}
					}
					else{
						criteria.add(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT));
					}
				}
			}

			if (filter.getTipoOrganoSociale() != null && filter.getTipoOrganoSociale().longValue() > 0) {
				criteria.createAlias("tipoOrganoSociale", "tipoOrganoSociale");
				criteria.add(Restrictions.eq("tipoOrganoSociale.id", filter.getTipoOrganoSociale()));
			}

			if (filter.getCognome() != null && !filter.getCognome().isEmpty()) {
				criteria.add(Restrictions.ilike("cognome", filter.getCognome(), MatchMode.ANYWHERE));
			}

			if (filter.getNome() != null && !filter.getNome().isEmpty()) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}

			// EVO OTTIMIZZAZIONE FILTRI & QUERIES RIDP6U9
			if (filter.getIncarica() != null && !filter.getIncarica().isEmpty()) {
				if ("NO".equals(filter.getIncarica())) {
					Disjunction disj = Restrictions.disjunction();
					disj.add(Restrictions.isNull("dataAccettazioneCarica"));
					disj.add(Restrictions.lt("dataCessazione", new Date()));
					disj.add(Restrictions.lt("dataScadenza", new Date()));
					criteria.add(disj);
				}
				if ("SI".equals(filter.getIncarica())) {
					criteria.add(Restrictions.isNotNull("dataAccettazioneCarica"));
					criteria.add(Restrictions.ge("dataCessazione", new Date()));
					criteria.add(Restrictions.ge("dataScadenza", new Date()));
					
				}
			}
			
			Long numeroTotaleElementi = countOrganoSociale(filter, listaSNAM_SRG_GNL_STOGIT);
			int elementiPerPagina = filter.getNumElementiPerPagina();
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi
					: elementiPerPagina);

			int numeroPagina = filter.getNumeroPagina();
			int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);

			out.setNumeroTotaleElementi(numeroTotaleElementi);

			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi
					: elementiPerPagina);
			if (numeroTotaleElementi < indicePrimoElemento) {
				indicePrimoElemento = 0;
			}

			criteria.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("id")))
					.add(Projections.property("id"), "id")
					.add(Projections.property("societaAffari"), "societaAffari")
					.add(Projections.property("tipoOrganoSociale"), "tipoOrganoSociale")
					.add(Projections.property("dataNomina"), "dataNomina")
					.add(Projections.property("dataScadenza"), "dataScadenza")
					.add(Projections.property("dataCessazione"), "dataCessazione")
					.add(Projections.property("dataAccettazioneCarica"), "dataAccettazioneCarica"))
					.setResultTransformer(Transformers.aliasToBean(OrganoSociale.class));

			@SuppressWarnings("unchecked")
			List<OrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
					indicePrimoElemento + elementiPerPagina);
			if (lista != null) {
				returnList = new ArrayList<OrganoSociale>();
				int index = 0;
				
				List<OrganoSociale> listWithNull = new ArrayList<OrganoSociale>();
				for (OrganoSociale f : lista) {
					if (index < elementiPerPagina) {
						returnList.add(leggi(f.getId()));
					}
					index++;
				}
			}
		}
		out.setList(returnList);
		return out;
	}

	public Long countOrganoSociale(OrganoSocialeFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("societaAffari", "societaAffari");

		if (filter.getIdSocietaAffari() != null && filter.getIdSocietaAffari().longValue() > 0) {
			criteria.add(Restrictions.eq("societaAffari.id", filter.getIdSocietaAffari()));
		}
		else{
			if(listaSNAM_SRG_GNL_STOGIT != null && !listaSNAM_SRG_GNL_STOGIT.isEmpty()){
				
				if(filter.getGruppoSnam() != null && !filter.getGruppoSnam().isEmpty()){
					if("NO".equals(filter.getGruppoSnam())){
						criteria.add(Restrictions.not(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT)));
					}else{
						criteria.add(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT));
					}
				}
				else{
					criteria.add(Restrictions.in("societaAffari.id", listaSNAM_SRG_GNL_STOGIT));
				}
			}
		}

		if (filter.getTipoOrganoSociale() != null && filter.getTipoOrganoSociale().longValue() > 0) {
			criteria.createAlias("tipoOrganoSociale", "tipoOrganoSociale");
			criteria.add(Restrictions.eq("tipoOrganoSociale.id", filter.getTipoOrganoSociale()));
		}

		if (filter.getCognome() != null && !filter.getCognome().isEmpty()) {
			criteria.add(Restrictions.ilike("cognome", filter.getCognome(), MatchMode.ANYWHERE));
		}

		if (filter.getNome() != null && !filter.getNome().isEmpty()) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}

		// EVO OTTIMIZZAZIONE FILTRI & QUERIES RIDP6U9
		if (filter.getIncarica() != null && !filter.getIncarica().isEmpty()) {
			if ("NO".equals(filter.getIncarica())) {
				Disjunction disj = Restrictions.disjunction();
				disj.add(Restrictions.isNull("dataAccettazioneCarica"));
				disj.add(Restrictions.lt("dataCessazione", new Date()));
				disj.add(Restrictions.lt("dataScadenza", new Date()));
				criteria.add(disj);
			}
			if ("SI".equals(filter.getIncarica())) {
				criteria.add(Restrictions.isNotNull("dataAccettazioneCarica"));
				criteria.add(Restrictions.ge("dataCessazione", new Date()));
				criteria.add(Restrictions.ge("dataScadenza", new Date()));
			}
		}
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class);
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		criteria.add(Restrictions.eq("lingua", lingua.toUpperCase()));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<OrganoSociale> esporta() throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoSociale.class).addOrder(Order.desc("dataNomina"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<OrganoSociale> organoSocialeList = getHibernateTemplate().findByCriteria(criteria);
		return organoSocialeList;
	}

}
