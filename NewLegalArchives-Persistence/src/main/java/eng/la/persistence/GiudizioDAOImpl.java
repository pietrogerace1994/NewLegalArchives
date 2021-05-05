package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Giudizio;


/**
 * Classe implemntazione DAO.
 * <p>
 * @author
 */
@Component("giudizioDAO")
public class GiudizioDAOImpl extends HibernateDaoSupport implements GiudizioDAO, CostantiDAO{
	
	@Autowired
	public GiudizioDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	/**
	 * Lettura occorrenze senza filtro.
	 * <p>
	 */
	@Cacheable("giudizioLeggiCache")
	@Override
	public List<Giudizio> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Giudizio.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Giudizio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Leggi occorrena per id
	 * <p>
	 * @param id
	 */
	@Cacheable("giudizioLeggiByIdCache")
	@Override
	public Giudizio leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Giudizio.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Giudizio giudizio = (Giudizio) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return giudizio;
	}
	
	/**
	 * Leggi occorrenze per lingua
	 * <p>
	 * @param lingua
	 */
	@Cacheable("giudizioLeggiByLinguaCache")
	@Override
	public List<Giudizio> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Giudizio.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang",lingua));
		@SuppressWarnings("unchecked")
		List<Giudizio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Leggi occorrenze per idSettoreGiuridico
	 * <p>
	 * @param idSettoreGiuridico
	 */
	@Cacheable("giudizioLeggiDaSettoreGiuridicoIdCache")
	@Override
	public List<Giudizio> leggiDaSettoreGiuridicoId(long idSettoreGiuridico, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Giudizio.class).addOrder(Order.asc("descrizione"));
		criteria.createAlias("RSettoreGiuridicoGiudizios", "RSettoreGiuridicoGiudizios");
		
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("RSettoreGiuridicoGiudizios.settoreGiuridico.id",idSettoreGiuridico)); 
		
		@SuppressWarnings("unchecked")
		List<Giudizio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	
	/**
	 * Inserisce occorrenza 
	 * <p>
	 * @param vo
	 */
	@CacheEvict(value = {"giudizioLeggiCodiceLinguaCache","giudizioLeggiCache","giudizioLeggiByIdCache","giudizioLeggiByLinguaCache","giudizioLeggiDaSettoreGiuridicoIdCache"}, allEntries = true)
	@Override
	public Giudizio inserisci(Giudizio vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Modifica occorrenza 
	 * <p>
	 * @param vo
	 */

	@CacheEvict(value = {"giudizioLeggiCodiceLinguaCache","giudizioLeggiCache","giudizioLeggiByIdCache","giudizioLeggiByLinguaCache","giudizioLeggiDaSettoreGiuridicoIdCache"}, allEntries = true)
	@Override
	public void modifica(Giudizio vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	/**
	 * Cancella occorrenza
	 * <p>
	 * @param id 
	 */
	@CacheEvict(value = {"giudizioLeggiCodiceLinguaCache","giudizioLeggiCache","giudizioLeggiByIdCache","giudizioLeggiByLinguaCache"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Giudizio vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
		
	}

	@Cacheable("giudizioLeggiCodiceLinguaCache")
	@Override
	public Giudizio leggi(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Giudizio.class)
				.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang",lingua));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		
		@SuppressWarnings("unchecked")
		Giudizio giudizio = (Giudizio) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return giudizio;
	}
 
}