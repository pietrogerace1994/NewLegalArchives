package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.CentroDiCosto;

@Component("centroDiCostoDAO")
public class CentroDiCostoDAOImpl extends HibernateDaoSupport implements CentroDiCostoDAO, CostantiDAO {

	@Autowired
	public CentroDiCostoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Cacheable("centroDiCostoLeggiCache")
	@Override
	/**
	 * Lista occorrenze "cached".
	 */
	public List<CentroDiCosto> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class).addOrder(Order.asc("unitaLegale"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<CentroDiCosto> lista = (List<CentroDiCosto>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Leggi per id. (cached)
	 * <p>
	 * @param id
	 */
	@Cacheable("centroDiCostoLeggiByIdCache")
	@Override
	public CentroDiCosto leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		CentroDiCosto centroDiCosto = (CentroDiCosto) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return centroDiCosto;
	}
	
	/**
	 * Cerca combinata di più chiavi. ( cached ).
	 * <p>
	 * @param tipologiaFascicoloid
	 * @param settoreGiuridicoId
	 * @param societaId
	 * @param unitaLegale
	 */
	@Cacheable("centroDiCostoCercaByTFIdSGIdSIdULCache")
	@Override
	public List<CentroDiCosto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class).addOrder(Order.asc("unitaLegale"));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridicoId.id", settoreGiuridicoId));
		}

		if (societaId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<CentroDiCosto> lista = (List<CentroDiCosto>) getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	
	/**
	 * Cerca combinata con più chiavi. ( cached )
	 * <p>		 
	 * @param tipologiaFascicoloid
	 * @param settoreGiuridicoId
	 * @param societaId
	 * @param unitaLegal
	 */
	@Cacheable("centroDiCostoCercaByTFIdSGIdSIdULPagCache")
	@Override
	public List<CentroDiCosto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId,
			String unitaLegale, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("unitaLegale"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridicoId.id", settoreGiuridicoId));
		}

		if (societaId > 0) {
			criteria.createAlias("societa", "societa");
			criteria.add(Restrictions.eq("societa.id", societaId));
		}

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(tipologiaFascicoloId, settoreGiuridicoId, societaId, unitaLegale);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<CentroDiCosto> lista = (List<CentroDiCosto>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				elementiPerPagina);
		return lista;
	}

	/**
	 * Conta il numero di occorrenze, combinata con più chiavi. ( cached )
	 * <p>		 
	 * @param tipologiaFascicoloid
	 * @param settoreGiuridicoId
	 * @param societaId
	 * @param unitaLegal
	 */
	@Cacheable("centroDiCostoContaByTFIdSGIdSIdULCache")
	@Override
	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, long societaId, String unitaLegale)
			throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridicoId.id", settoreGiuridicoId));
		}

		if (societaId > 0) {
			criteria.createAlias("societa", "societa");
			criteria.add(Restrictions.eq("societa.id", societaId));
		}

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Cacheable("centroDiCostoLeggiByTFIdSGIdSIdULCache")
	@Override
	public List<CentroDiCosto> leggiDaUnitaLegaleSettoreTipologiaFascicolo(String unitaLegale, long societaId, long settoreGiuridicoId,
			long tipologiaFascicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CentroDiCosto.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		 
		criteria.addOrder(Order.asc("cdc"));
		  
		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));
		} 
		
		if (societaId > 0) {
			criteria.createAlias("societa", "societa");
			criteria.add(Restrictions.eq("societa.id", societaId));
		}

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}
 

		@SuppressWarnings("unchecked")
		List<CentroDiCosto> lista = (List<CentroDiCosto>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@CacheEvict(value = {"centroDiCostoLeggiByTFIdSGIdSIdULCache","centroDiCostoLeggiCache","centroDiCostoLeggiByIdCache",
			 "centroDiCostoCercaByTFIdSGIdSIdULCache","centroDiCostoCercaByTFIdSGIdSIdULPagCache",
			 "centroDiCostoContaByTFIdSGIdSIdULCache"}, allEntries = true)
	@Override
	public CentroDiCosto inserisci(CentroDiCosto vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@CacheEvict(value = {"centroDiCostoLeggiByTFIdSGIdSIdULCache","centroDiCostoLeggiCache","centroDiCostoLeggiByIdCache",
			 "centroDiCostoCercaByTFIdSGIdSIdULCache","centroDiCostoCercaByTFIdSGIdSIdULPagCache",
			 "centroDiCostoContaByTFIdSGIdSIdULCache"}, allEntries = true)
	@Override
	public void modifica(CentroDiCosto vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@CacheEvict(value = {"centroDiCostoLeggiByTFIdSGIdSIdULCache","centroDiCostoLeggiCache","centroDiCostoLeggiByIdCache",
			 "centroDiCostoCercaByTFIdSGIdSIdULCache","centroDiCostoCercaByTFIdSGIdSIdULPagCache",
			 "centroDiCostoContaByTFIdSGIdSIdULCache"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		CentroDiCosto vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
	}
 
}
