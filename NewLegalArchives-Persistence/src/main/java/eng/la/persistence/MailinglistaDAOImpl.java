package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.Mailinglist;
import eng.la.model.MailinglistDettaglio;
import eng.la.model.NewsletterEmail;
import eng.la.model.RNewsletterArticolo;

@Component("mailinglistDAO")
public class MailinglistaDAOImpl extends HibernateDaoSupport implements MailinglistDAO, CostantiDAO {

	@Autowired
	public MailinglistaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Mailinglist> leggiMailinglist(String codiceCategoria) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Mailinglist.class);		
		criteria.setFetchMode("categoriaMailinglist", FetchMode.JOIN);	
		criteria.setFetchMode("mailinglistDettaglio", FetchMode.JOIN);
		criteria.createAlias("categoriaMailinglist", "categoriaMailinglist");
		
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("categoriaMailinglist.codGruppoLingua", codiceCategoria));  
		criteria.addOrder(Order.asc("categoriaMailinglist.nomeCategoria"));
		
		@SuppressWarnings("unchecked")
		List<Mailinglist> lista = (List<Mailinglist>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<Mailinglist> leggiMailinglist() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Mailinglist.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Mailinglist> lista = (List<Mailinglist>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Mailinglist salvaMailinglist(Mailinglist vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancellaMailinglist(long id) throws Throwable {
		Mailinglist vo = leggiMailinglist(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public Mailinglist leggiMailinglist(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Mailinglist.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Mailinglist mailinglist = (Mailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return mailinglist;
	}

	@Override
	public void salvaMailinglistDettaglio(MailinglistDettaglio vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}

	@Override
	public List<MailinglistDettaglio> leggiMailinglistDettaglio(long idMailinglist) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(MailinglistDettaglio.class).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("mailinglist.id", idMailinglist));
		@SuppressWarnings("unchecked")
		List<MailinglistDettaglio> lista = (List<MailinglistDettaglio>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<CategoriaMailinglist> listaCategoriaMailinglist(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.isNull("categoriaPadre"));
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
		 
	}
	
	@Override
	public CategoriaMailinglist getCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		CategoriaMailinglist vo = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public CategoriaMailinglist leggiCategoria(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		CategoriaMailinglist categoriaMailingList = (CategoriaMailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return categoriaMailingList; 
	}

	@Override
	public void cancellaMailingListDettaglio(long mailingListId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MailinglistDettaglio.class);
		criteria.createAlias("mailinglist", "mailinglist");		
		criteria.add(Restrictions.eq("mailinglist.id", mailingListId));
		@SuppressWarnings("unchecked")
		List<MailinglistDettaglio> lista = (List<MailinglistDettaglio>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( MailinglistDettaglio vo : lista ){
				getHibernateTemplate().delete(vo);
			}
		}
		
	}

	@Override
	public void inserisciMailingListDettagli(MailinglistDettaglio mailingListDettaglio) {
		getHibernateTemplate().save(mailingListDettaglio);
		
	}

	@Override
	public Mailinglist leggi(long id, FetchMode fetchMode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Mailinglist.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("categoriaMailinglist", fetchMode);
		criteria.setFetchMode("mailinglistDettaglio", fetchMode);
		@SuppressWarnings("unchecked")
		Mailinglist mailingList = (Mailinglist) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return mailingList;
	}
	
	@Override
	public List<MailinglistDettaglio> leggiMailingListDettagliobyId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( MailinglistDettaglio.class );
		criteria.createAlias("mailinglist", "mailinglist");
		criteria.add( Restrictions.eq("mailinglist.id", id));
		@SuppressWarnings("unchecked")
		List<MailinglistDettaglio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}

	@Override
	public void modifica(long id) throws Throwable {
		Mailinglist vo = leggiMailinglist(id);
		getHibernateTemplate().update(vo); 
	}
	
	@Override
	public void modifica(Mailinglist vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancellaArticoli(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RNewsletterArticolo.class);
		criteria.createAlias("newsletter", "newsletter");		
		criteria.add(Restrictions.eq("newsletter.id", id));
		@SuppressWarnings("unchecked")
		List<RNewsletterArticolo> lista = (List<RNewsletterArticolo>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( RNewsletterArticolo vo : lista ){
				getHibernateTemplate().delete(vo);
			}
		}
		
	}

	@Override
	public List<CategoriaMailinglist> categorieMailingList() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class );
		criteria.add(Restrictions.isNull("categoriaPadre"));
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = (List<CategoriaMailinglist>)getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}

	@Override
	public List<Mailinglist> categorieUtenteMailingList(Long rubricaId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Mailinglist.class, "mailingList" );
		
		criteria.createAlias("mailinglistDettaglio", "mailinglistDettaglio" , DetachedCriteria.LEFT_JOIN);	
		
		criteria.createAlias("mailinglistDettaglio.rubrica", "rubrica" , DetachedCriteria.LEFT_JOIN);
		
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("rubrica.id", rubricaId));
		
		@SuppressWarnings("unchecked")
		List<Mailinglist> lista = (List<Mailinglist>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public void cancellaEmail(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsletterEmail.class);
		criteria.createAlias("newsletter", "newsletter");		
		criteria.add(Restrictions.eq("newsletter.id", id));
		@SuppressWarnings("unchecked")
		List<NewsletterEmail> lista = (List<NewsletterEmail>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( NewsletterEmail vo : lista ){
				getHibernateTemplate().delete(vo);
			}
		}
		
	}

	@Override
	public List<CategoriaMailinglist> leggiSottoCategoriaMailingList(Long idCategoria) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaMailinglist.class );
		criteria.createAlias("categoriaPadre", "categoriaPadre");
		criteria.add(Restrictions.eq("categoriaPadre.id",idCategoria));
		@SuppressWarnings("unchecked")
		List<CategoriaMailinglist> lista = (List<CategoriaMailinglist>)getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}

}
