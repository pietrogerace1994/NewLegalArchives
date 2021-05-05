package eng.la.persistence;

import java.util.Date;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.DocumentoProcure;

@Component("documentoProcureDAO")
public class DocumentoProcureDAOImpl extends HibernateDaoSupport implements DocumentoProcureDAO{
	
	@Autowired
	public DocumentoProcureDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public DocumentoProcure save(DocumentoProcure vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public DocumentoProcure read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoProcure.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("procure", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		DocumentoProcure vo = (DocumentoProcure) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public void deleteDocumentoProcure(DocumentoProcure documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}
	
}
