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

import eng.la.model.DocumentoProgetto;

@Component("documentoProgettoDAO")
public class DocumentoProgettoDAOImpl extends HibernateDaoSupport implements DocumentoProgettoDAO{
	
	@Autowired
	public DocumentoProgettoDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public DocumentoProgetto save(DocumentoProgetto vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public DocumentoProgetto read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoProgetto.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("progetto", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		DocumentoProgetto vo = (DocumentoProgetto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public void deleteDocumentoProgetto(DocumentoProgetto documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}
	
}






