package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.PosizioneOrganizzativa;


@Component("posizioneOrganizzativaDAO")
public class PosizioneOrganizzativaDAOImpl extends HibernateDaoSupport implements PosizioneOrganizzativaDAO {

	@Autowired
	public PosizioneOrganizzativaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<PosizioneOrganizzativa> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneOrganizzativa.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<PosizioneOrganizzativa> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public PosizioneOrganizzativa leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneOrganizzativa.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		PosizioneOrganizzativa posizioneOrganizzativa = (PosizioneOrganizzativa) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return posizioneOrganizzativa;
	}
	
	@Override
	public PosizioneOrganizzativa leggi(String code, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PosizioneOrganizzativa.class).add(Restrictions.eq("lang", lingua));
		criteria.add( Restrictions.eq("codGruppoLingua", code) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		PosizioneOrganizzativa posizioneOrganizzativa = (PosizioneOrganizzativa) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return posizioneOrganizzativa;
	}

}
