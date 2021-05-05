package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Articolo;
import eng.la.model.Documento;
import eng.la.model.DocumentoArticolo;

@Component("documentoDAO")
public class DocumentoDAOImpl extends HibernateDaoSupport implements DocumentoDAO, CostantiDAO{
	
	@Autowired
	public DocumentoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void cancellaDocumento(Documento documento) throws Throwable { 
		documento.setDataCancellazione(new Date());
		getHibernateTemplate().update(documento);
		
	}
	
	@Override
	public Documento creaDocumentoDB(String uuid, String classeDocumentale, String nomeFile, String contentType)
			throws Throwable {
		Documento documento = new Documento();
		documento.setUuid(uuid);
		documento.setContentType(contentType);
		documento.setNomeFile(nomeFile);
		documento.setClasseDocumentale(classeDocumentale);
		getHibernateTemplate().save(documento);
		return documento;
	}

	@Override
	public Documento leggi(String uuid) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Documento.class);
		criteria.add(Restrictions.ilike("uuid", uuid));
		
		@SuppressWarnings("unchecked")
		Documento documento = (Documento)DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return documento;
	}
	
	@Override
	public Documento leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Documento.class ).add( Restrictions.eq("id", id) );
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Documento documento = (Documento) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return documento; 
	}
	
	@Override
	public void cancella(String uuid) throws Throwable {
		Documento vo = leggi(uuid);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void creaDocumentoArticolo(Documento doc, Articolo articolo) throws Throwable {
		DocumentoArticolo doc_art = new DocumentoArticolo();
		doc_art.setArticolo(articolo);
		doc_art.setDocumento(doc);
		getHibernateTemplate().save(doc_art);
		
	}
	
	/**
	 * Ricerca un documento per nome e restituisce il documento con l'id maggiore
	 * @author MASSIMO CARUSO
	 * @param nome il nome del documento da ricercare
	 * @return il documento con l'id maggiore, null altrimenti
	 * @throws Throwable
	 */
	@Override
	public Documento cercaDocumento(String nome) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass(Documento.class);
		criteria.add(Restrictions.eq("nomeFile", nome));
		criteria.addOrder(Order.desc("id"));
		
		@SuppressWarnings("unchecked")
		List<Documento> results = (List<Documento>)getHibernateTemplate().findByCriteria(criteria);
		return (results != null && results.size() == 1)?results.get(0):null;
	}
	
	
	/**
	 * Elimina la entry di un documento dal DB
	 * @author MASSIMO CARUSO
	 * @param id_documento l'id del documento da rimuovere
	 * @throws Throwable
	 */
	@Override
	public void cancellaDocumentoDB(long id_documento) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass(Documento.class);
		criteria.add(Restrictions.eq("id", id_documento));
		
		@SuppressWarnings("unchecked")
		List<Documento> results = (List<Documento>)getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().delete(results.get(0));
	}
}
