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

import eng.la.model.BeautyContestReply;

@Component("beautyContestReplyDAO")
public class BeautyContestReplyDAOImpl extends HibernateDaoSupport implements BeautyContestReplyDAO, CostantiDAO{


	@Autowired
	public BeautyContestReplyDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public BeautyContestReply leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContestReply.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		
		@SuppressWarnings("unchecked")
		BeautyContestReply beautyContestReply = (BeautyContestReply) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return beautyContestReply; 
	}
	
	@Override
	public List<BeautyContestReply> leggiElencoRisposte(long idBc) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeautyContestReply.class);
		
		criteria.createAlias("statoBCR", "statoBCR");
		
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.addOrder(Order.asc("dataInvio"));
		criteria.add(Restrictions.eq("beautyContest.id", idBc));
		criteria.add(Restrictions.eq("statoBCR.codGruppoLingua", CostantiDAO.BEAUTY_CONTEST_REPLY_INVIATA));
	
		@SuppressWarnings("unchecked")
		List<BeautyContestReply> lista = (List<BeautyContestReply>) getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
}
