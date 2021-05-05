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

import eng.la.model.DocumentoAffariSocietari;

@Component("documentoAffariSocietariDAO")
public class DocumentoAffariSocietariDAOImpl extends HibernateDaoSupport implements DocumentoAffariSocietariDAO{
	
	@Autowired
	public DocumentoAffariSocietariDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public DocumentoAffariSocietari save(DocumentoAffariSocietari vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public DocumentoAffariSocietari read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoAffariSocietari.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("affariSocietari", FetchMode.JOIN);
		criteria.setFetchMode("documento", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		DocumentoAffariSocietari vo = (DocumentoAffariSocietari) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public void deleteDocumentoAffariSocietari(DocumentoAffariSocietari documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}

	@Override
	public DocumentoAffariSocietari leggiPerIdAffariSocietari(long idAffariSocietari) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoAffariSocietari.class);
				criteria.createAlias("affariSocietari", "affariSocietari");
				criteria.add(Restrictions.eq("affariSocietari.id", idAffariSocietari));
		@SuppressWarnings("unchecked")
		DocumentoAffariSocietari vo = (DocumentoAffariSocietari) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	
}






