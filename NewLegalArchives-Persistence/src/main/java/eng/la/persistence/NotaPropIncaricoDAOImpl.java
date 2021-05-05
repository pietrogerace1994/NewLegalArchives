package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;


import eng.la.model.NotaPropIncarico;

@Component("notaPropIncaricoDAO")
public class NotaPropIncaricoDAOImpl extends HibernateDaoSupport implements NotaPropIncaricoDAO, CostantiDAO{
	@Autowired
	public NotaPropIncaricoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<NotaPropIncarico> leggiNotaPropostaIncaricoPerId(List<Long> listaIdNotaProposta) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotaPropIncarico.class ).add( Restrictions.in("id", listaIdNotaProposta) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		@SuppressWarnings("unchecked")
		List<NotaPropIncarico> notaPropostaIncaricoList = getHibernateTemplate().findByCriteria(criteria);	
		return notaPropostaIncaricoList; 
	}

	@Override
	public NotaPropIncarico leggiNotaPropostaIncarico(Long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotaPropIncarico.class ).add( Restrictions.idEq(id));
		@SuppressWarnings("unchecked")
		NotaPropIncarico notaProposta = (NotaPropIncarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		return notaProposta;
	}
}
