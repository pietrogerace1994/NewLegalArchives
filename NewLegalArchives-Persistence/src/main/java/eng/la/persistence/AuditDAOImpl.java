package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.AuditLog;
import eng.la.util.DateUtil;

@Component("auditDAO")
public class AuditDAOImpl extends HibernateDaoSupport implements AuditDAO, CostantiDAO{

	@Autowired
	public AuditDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<AuditLog> cerca(String tipoEntita, String userId, String dal, String al, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(tipoEntita, userId, dal, al);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(AuditLog.class);
		  
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("dataOra"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataOra", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataOra", DateUtil.getDataOra(al+" - 23:59:59")));
		}
 
		
		if( tipoEntita != null && tipoEntita.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("opzionale", tipoEntita) ) ;
		} 
		
		if( userId != null && userId.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("userId", userId) ) ;
		}
		 
		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		} 
		  
		@SuppressWarnings("unchecked")
		List<AuditLog> lista = (List<AuditLog>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		 
		return lista;  	 
	}

	@Override
	public Long conta(String tipoEntita, String userId, String dal, String al) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AuditLog.class);
		   
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataOra", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataOra", DateUtil.getDataOra(al+" - 23:59:59")));
		}
 
		
		if( tipoEntita != null && tipoEntita.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("opzionale", tipoEntita) ) ;
		} 
		
		if( userId != null && userId.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("userId", userId) ) ;
		}

		criteria.setProjection(Projections.distinct(Projections.rowCount()));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

}
