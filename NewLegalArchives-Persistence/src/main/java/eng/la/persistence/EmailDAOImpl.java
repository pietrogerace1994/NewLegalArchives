package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Articolo;
import eng.la.model.DocumentoArticolo;
import eng.la.model.RNewsletterArticolo;
import eng.la.util.DateUtil;

@Component("emailDAO")
public class EmailDAOImpl extends HibernateDaoSupport implements EmailDAO, CostantiDAO {

	@Autowired
	public EmailDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Articolo leggiEmail(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Articolo email = (Articolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return email;
	}

	@Override
	public List<Articolo> leggiEmail() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class).addOrder(Order.asc("oggetto"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Articolo> lista = (List<Articolo>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Articolo salvaEmail(Articolo vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancellaEmail(long id) throws Throwable {
		Articolo vo = leggiEmail(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void modificaEmail(Articolo vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Articolo> cerca(String oggetto, String dal, String al, int elementiPerPagina, int numeroPagina,
			String ordinamento, String tipoOrdinamento, String contenutoBreve, String comboCategoria) throws Throwable {
		Long numeroTotaleElementi = conta(oggetto, dal, al);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class); 
  		criteria.createAlias("categoria", "categoria", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("oggetto"));
		} else {
			if (tipoOrdinamento == null || tipoOrdinamento.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}
  
		
		if( oggetto != null && oggetto.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("oggetto", oggetto, MatchMode.ANYWHERE) ) ;
		}
		
		if( contenutoBreve != null && contenutoBreve.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("contenutoBreve", contenutoBreve, MatchMode.ANYWHERE) ) ;
		}
		
		if (comboCategoria != null && comboCategoria.trim().length() > 0) {
			criteria.add(Restrictions.eq("categoria.codGruppoLingua", comboCategoria));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}
		
		criteria.add(Subqueries.propertyNotIn("id", DetachedCriteria.forClass(RNewsletterArticolo.class)
        .createAlias("articolo", "articolo")
        .setProjection(Property.forName("articolo.id"))));

		List<Articolo> listaRitorno = new ArrayList<Articolo>();
		 
		listaRitorno = (List<Articolo>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		
		System.out.println("DAO: "+listaRitorno);
		 
		return listaRitorno; 
	}
	
	@Override
	public Long conta(String oggetto, String dal, String al) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}
  
		
		if( oggetto != null && oggetto.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("oggetto", oggetto, MatchMode.ANYWHERE) ) ;
		} 
		
		criteria.add(Subqueries.propertyNotIn("id", DetachedCriteria.forClass(RNewsletterArticolo.class)
		        .createAlias("articolo", "articolo")
		        .setProjection(Property.forName("articolo.id"))));

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	@Override
	public List<DocumentoArticolo> leggiArticoloDocbyId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( DocumentoArticolo.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("articolo", "articolo");
		criteria.add( Restrictions.eq("articolo.id", id));
		@SuppressWarnings("unchecked")
		List<DocumentoArticolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public void cancellaDocumento(long documentoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoArticolo.class);
		criteria.createAlias("documento", "documento");		
		criteria.add(Restrictions.eq("documento.id", documentoId));
		@SuppressWarnings("unchecked")
		DocumentoArticolo vo = (DocumentoArticolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void eliminaArticoliPerCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class);
		criteria.createAlias("categoria", "categoria");
		criteria.add(Restrictions.eq("categoria.id", id));
		@SuppressWarnings("unchecked")
		List<Articolo> lista = getHibernateTemplate().findByCriteria(criteria);
		
		
		for(Articolo vo : lista){
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
		}
	}
}
