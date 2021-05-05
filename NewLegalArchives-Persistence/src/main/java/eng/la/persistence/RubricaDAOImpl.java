package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Rubrica;

@Component("rubricaDAO")
public class RubricaDAOImpl extends HibernateDaoSupport implements RubricaDAO, CostantiDAO {

	@Autowired
	public RubricaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Rubrica leggiRubrica(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Rubrica.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Rubrica rubrica = (Rubrica) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return rubrica;
	}

	@Override
	public List<Rubrica> leggiRubrica() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Rubrica.class).addOrder(Order.asc("nominativo"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Rubrica> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Rubrica salvaRubrica(Rubrica vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void cancellaRubrica(long id) throws Throwable {
		Rubrica vo = leggiRubrica(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public void modificaRubrica(Rubrica vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public Long conta(String nominativo, String email) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Rubrica.class);
		
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( nominativo != null && nominativo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nominativo", nominativo, MatchMode.ANYWHERE) ) ;
		} 
		
		if( email != null && email.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("email", email, MatchMode.ANYWHERE) ) ;
		} 

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	@Override
	public List<Rubrica> cerca(String nominativo, String email, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {

		Long numeroTotaleElementi = conta(nominativo, email);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(Rubrica.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nominativo"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		
 
		if( nominativo != null && nominativo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nominativo", nominativo, MatchMode.ANYWHERE) ) ;
		} 
		
		if( email != null && email.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("email", email, MatchMode.ANYWHERE) ) ;
		} 
		

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		  
		@SuppressWarnings("unchecked")
		List<Rubrica> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		
		List<Rubrica> listaRitorno  = null;
		
		if( lista != null ){
			listaRitorno  = new ArrayList<Rubrica>();
			int index = 0;
			for( Rubrica p : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggiRubrica( p.getId() ));					
				}
			    index++;
			}
		}
		
		return listaRitorno;  	 
	}

}
