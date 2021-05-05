package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Ricorso;

@Component("ricorsoDAO")
public class RicorsoDAOImpl extends HibernateDaoSupport implements RicorsoDAO, CostantiDAO{


	@Autowired
	public RicorsoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable("ricorsoCacheLeggi")
	@Override
	public List<Ricorso> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ricorso.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Ricorso> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("ricorsoCacheLeggiDaId")
	@Override
	public Ricorso leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ricorso.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Ricorso ricorso = (Ricorso) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return ricorso;
	}

	@CacheEvict(value = {"ricorsoLeggiLingua","ricorsoLeggiCodiceLingua","ricorsoCacheLeggi", "ricorsoCacheLeggiDaId"}, 
			allEntries = true)
	@Override
	public Ricorso inserisci(Ricorso vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@CacheEvict(value = {"ricorsoLeggiLingua","ricorsoLeggiCodiceLingua","ricorsoCacheLeggi", "ricorsoCacheLeggiDaId"}, 
			allEntries = true)
	@Override
	public void modifica(Ricorso vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@CacheEvict(value = {"ricorsoLeggiLingua","ricorsoLeggiCodiceLingua","ricorsoCacheLeggi", "ricorsoCacheLeggiDaId"}, 
			allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Ricorso vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Cacheable("ricorsoLeggiCodiceLingua")
	@Override
	public Ricorso leggi(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ricorso.class)
				.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if( !tutte ){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		Ricorso ricorso = (Ricorso) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return ricorso;
	}

	@Cacheable("ricorsoLeggiLingua")
	@Override
	public List<Ricorso> leggi(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ricorso.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		List<Ricorso> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
   
}
