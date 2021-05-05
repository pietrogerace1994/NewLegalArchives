package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.OrganoGiudicante;

@Component("organoGiudicanteDAO")
public class OrganoGiudicanteDAOImpl extends HibernateDaoSupport implements OrganoGiudicanteDAO, CostantiDAO {

	@Autowired
	public OrganoGiudicanteDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<OrganoGiudicante> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public OrganoGiudicante leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		OrganoGiudicante organoGiudicante = (OrganoGiudicante) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return organoGiudicante;
	}

	@Override
	public List<OrganoGiudicante> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class);
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
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				elementiPerPagina);
		return lista;
	}

	@Override
	public List<OrganoGiudicante> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class);
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
	public OrganoGiudicante leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		OrganoGiudicante organoGiudicante = (OrganoGiudicante) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return organoGiudicante;
	}

	@Override
	public void modifica(OrganoGiudicante vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public void cancella(long id) throws Throwable {
		OrganoGiudicante vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public OrganoGiudicante inserisci(OrganoGiudicante vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<OrganoGiudicante> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<OrganoGiudicante> leggiDaRicorso(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("RRicorsoOrganoGiudicantes", "RRicorsoOrganoGiudicantes");
		criteria.add(Restrictions.eq("RRicorsoOrganoGiudicantes.ricorso.id", id));

		@SuppressWarnings("unchecked")
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<OrganoGiudicante> leggiDaGiudizio(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(OrganoGiudicante.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("RGiudizioOrganoGiudicantes", "RGiudizioOrganoGiudicantes");
		criteria.add(Restrictions.eq("RGiudizioOrganoGiudicantes.giudizio.id", id));

		@SuppressWarnings("unchecked")
		List<OrganoGiudicante> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
}
