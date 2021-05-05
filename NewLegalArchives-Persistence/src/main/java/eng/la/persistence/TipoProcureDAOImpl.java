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

import eng.la.model.TipoProcure;


@Component("TipoProcureDAO")
public class TipoProcureDAOImpl extends HibernateDaoSupport implements TipoProcureDAO {

	@Autowired
	public TipoProcureDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

//	@Cacheable("TipoProcuraCacheLeggi")
	@Override
	public List<TipoProcure> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProcure.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<TipoProcure> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
//	@Cacheable("TipoProcuraCacheLeggiDaId")
	@Override
	public TipoProcure leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProcure.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoProcure tipoProcura = (TipoProcure) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoProcura;
	}

	@Override
	public List<TipoProcure> leggibyCodice(String tipoProcureCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProcure.class ).addOrder(Order.asc("id"));
		criteria.add( Restrictions.eq("codGruppoLingua", tipoProcureCode) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipoProcure> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}

	@Override
	public List<String> getCodGruppoLinguaList() {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProcure.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public TipoProcure inserisci(TipoProcure vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancella(long id) throws Throwable {
		TipoProcure vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void modifica(TipoProcure vo) throws Throwable{
		getHibernateTemplate().update(vo);
	}

	@Override
	public boolean controlla(String descIt) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProcure.class );
		criteria.add(Restrictions.ilike("descrizione", descIt, MatchMode.ANYWHERE) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipoProcure> lista = getHibernateTemplate().findByCriteria(criteria);
		return !lista.isEmpty(); 
	}

	@Override
	public TipoProcure leggiNotDataCancellazione(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProcure.class).add(Restrictions.eq("id", id));
		TipoProcure tipoProcura = (TipoProcure) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoProcura;
	}

	@Override
	public List<TipoProcure> leggiListNotDataCancellazione(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProcure.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		List<TipoProcure> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}


}
