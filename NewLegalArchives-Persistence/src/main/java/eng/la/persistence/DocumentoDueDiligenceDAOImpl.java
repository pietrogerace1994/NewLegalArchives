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

import eng.la.model.DocumentoDueDiligence;

@Component("documentoDueDiligenceDAO")
public class DocumentoDueDiligenceDAOImpl extends HibernateDaoSupport implements DocumentoDueDiligenceDAO{
	
	@Autowired
	public DocumentoDueDiligenceDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public DocumentoDueDiligence save(DocumentoDueDiligence vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public DocumentoDueDiligence read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDueDiligence.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("dueDiligence", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		DocumentoDueDiligence vo = (DocumentoDueDiligence) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public void deleteDocumentoDueDiligence(DocumentoDueDiligence documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}
	
}






