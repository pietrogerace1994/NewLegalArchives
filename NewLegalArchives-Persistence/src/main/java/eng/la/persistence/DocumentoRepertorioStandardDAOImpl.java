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

import eng.la.model.DocumentoRepertorioStandard;

@Component("documentoRepertorioStandardDAO")
public class DocumentoRepertorioStandardDAOImpl extends HibernateDaoSupport implements DocumentoRepertorioStandardDAO{
	
	@Autowired
	public DocumentoRepertorioStandardDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public DocumentoRepertorioStandard save(DocumentoRepertorioStandard vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public DocumentoRepertorioStandard read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoRepertorioStandard.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("repertorio", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		DocumentoRepertorioStandard vo = (DocumentoRepertorioStandard) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public void deleteDocumentoRepertorioStandard(DocumentoRepertorioStandard documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}

	@Override
	public DocumentoRepertorioStandard leggiPerIdRepertorio(long idRepertorioStandard) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoRepertorioStandard.class);
				criteria.createAlias("repertorio", "repertorio");
				criteria.add(Restrictions.eq("repertorio.id", idRepertorioStandard));
		@SuppressWarnings("unchecked")
		DocumentoRepertorioStandard vo = (DocumentoRepertorioStandard) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	
}






