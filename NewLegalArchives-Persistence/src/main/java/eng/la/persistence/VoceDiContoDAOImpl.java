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

import eng.la.model.VoceDiConto;

@Component("voceDiContoDAO")
public class VoceDiContoDAOImpl extends HibernateDaoSupport implements VoceDiContoDAO, CostantiDAO {

	@Autowired
	public VoceDiContoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable("voceDiContoCacheLeggi")
	@Override
	public List<VoceDiConto> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class).addOrder(Order.asc("unitaLegale"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<VoceDiConto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("voceDiContoCacheLeggiDaId")
	@Override
	public VoceDiConto leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		VoceDiConto voceDiConto = (VoceDiConto) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return voceDiConto;
	}

	@Cacheable("voceDiContoCacheCercaPaginata")
	@Override
	public List<VoceDiConto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class);
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

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(tipologiaFascicoloId, settoreGiuridicoId, unitaLegale);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}
		@SuppressWarnings("unchecked")
		List<VoceDiConto> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento,
				elementiPerPagina);
		return lista;
	}

	@Cacheable("voceDiContoCacheCerca")
	@Override
	public List<VoceDiConto> cerca(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale)
			throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class).addOrder(Order.asc("unitaLegale"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridicoId.id", settoreGiuridicoId));
		} 
		
		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}
		@SuppressWarnings("unchecked")
		List<VoceDiConto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("voceDiContoCacheConta")
	@Override
	public Long conta(long tipologiaFascicoloId, long settoreGiuridicoId, String unitaLegale) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridicoId.id", settoreGiuridicoId));
		} 

		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		Long conta = (long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Cacheable("voceDiContoCacheLeggiDaSettoreTipologiaUnitaLeg")
	@Override
	public List<VoceDiConto> leggiDaUnitaLegaleSettoreTipologiaFascicolo(long tipologiaFascicoloId,
			long settoreGiuridicoId, String unitaLegale) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VoceDiConto.class).addOrder(Order.asc("vdc"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if (tipologiaFascicoloId > 0) {
			criteria.createAlias("tipologiaFascicolo", "tipologiaFascicolo");
			criteria.add(Restrictions.eq("tipologiaFascicolo.id", tipologiaFascicoloId));
		}

		if (settoreGiuridicoId > 0) {
			criteria.createAlias("settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.id", settoreGiuridicoId));
		} 
		
		if (unitaLegale != null && unitaLegale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("unitaLegale", unitaLegale, MatchMode.ANYWHERE));
		}
		@SuppressWarnings("unchecked")
		List<VoceDiConto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@CacheEvict(value = {"voceDiContoCacheLeggiDaSettoreTipologiaUnitaLeg","voceDiContoCacheConta", "voceDiContoCacheCerca",
			"voceDiContoCacheCercaPaginata","voceDiContoCacheLeggiDaId",
			"voceDiContoCacheLeggi"}, allEntries = true)
	@Override
	public VoceDiConto inserisci(VoceDiConto vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@CacheEvict(value = {"voceDiContoCacheLeggiDaSettoreTipologiaUnitaLeg","voceDiContoCacheConta", "voceDiContoCacheCerca",
			"voceDiContoCacheCercaPaginata","voceDiContoCacheLeggiDaId",
			"voceDiContoCacheLeggi"}, allEntries = true)
	@Override
	public void modifica(VoceDiConto vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}
	
	@CacheEvict(value = {"voceDiContoCacheLeggiDaSettoreTipologiaUnitaLeg","voceDiContoCacheConta", "voceDiContoCacheCerca",
			"voceDiContoCacheCercaPaginata","voceDiContoCacheLeggiDaId",
			"voceDiContoCacheLeggi"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		VoceDiConto vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
	}

}
