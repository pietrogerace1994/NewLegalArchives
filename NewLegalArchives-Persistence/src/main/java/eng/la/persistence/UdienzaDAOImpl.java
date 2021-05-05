/*
 * @author Benedetto Giordano
 */
package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Udienza;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil; 

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità Udienza</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Udienza.
 * 
 * @author Benedetto Giordano
 */

@Component("udienzaDAO")
public class UdienzaDAOImpl extends HibernateDaoSupport implements UdienzaDAO, CostantiDAO{
	@Autowired
	public UdienzaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Udienza> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Udienza.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_UDIENZA);
		@SuppressWarnings("unchecked")
		List<Udienza> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public Udienza leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Udienza.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		Udienza udienza = (Udienza) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return udienza; 
	}
	
	@Override
	public List<Udienza> cerca(String dal, String al, String nomeFascicolo, String legaleInterno, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(dal, al, nomeFascicolo, legaleInterno);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( Udienza.class );
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
			if( ordinamento.equals("nomeFascicolo") ){		

				criteria.addOrder(Order.asc("fascicolo.nome"));
			}
			else{
				criteria.addOrder(Order.asc(ordinamento));
			}
		}else{
			if( ordinamento.equals("nomeFascicolo") ){			
				criteria.addOrder(Order.desc("fascicolo.nome"));
			}else if( ordinamento.equals("stato") ){  
				criteria.addOrder(Order.desc("statoIncarico.descrizione")); 

			}else{
				criteria.addOrder(Order.desc("id"));
			} 
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataUdienza", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataUdienza", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}
		
		if( legaleInterno != null && legaleInterno.trim().length() > 0 ){
			criteria.add( Restrictions.eq("fascicolo.legaleInterno", legaleInterno) );
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_UDIENZA);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("descrizione"), "descrizione")
				.add(Projections.property("dataModifica"), "dataModifica")
				.add(Projections.property("fascicolo"), "fascicolo")
				.add(Projections.property("dataCreazione"), "dataCreazione"))

		.setResultTransformer(Transformers.aliasToBean(Udienza.class));

		@SuppressWarnings("unchecked")
		List<Udienza> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<Udienza> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<Udienza>();
			int index = 0;
			for( Udienza i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}
		return listaRitorno;  
	}
	

	@Override
	public Long conta(String dal, String al, String nomeFascicolo, String legaleInterno ) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Udienza.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.createAlias("fascicolo", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}
		
		if( legaleInterno != null && legaleInterno.trim().length() > 0 ){
			criteria.createAlias("fascicolo", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.legaleInterno", legaleInterno) );
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataUdienza", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataUdienza", DateUtil.getDataOra(al+" - 23:59:59")));
		}
		
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_UDIENZA);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public Udienza inserisci(Udienza vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public void modifica(Udienza vo) throws Throwable {
		vo.setDataModifica(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		Udienza vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo); 
	}
}