package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
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

import eng.la.model.Materia;
import eng.la.model.RSettoreGiuridicoMateria;

@Component("materiaDAO")
public class MateriaDAOImpl extends HibernateDaoSupport implements MateriaDAO, CostantiDAO {

	@Autowired
	public MateriaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Leggi occorrenze
	 * 
	 */
	@Cacheable("materiaLeggiCache")
	@Override
	public List<Materia> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Materia> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	public List<Materia> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<Materia> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Leggi occorrenza per id
	 * @param id
	 */
	@Cacheable("materiaLeggiCacheById")
	@Override
	public Materia leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Materia materia = (Materia) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return materia;
	}

	/**
	 * Cerca per Nome
	 * @param nome
	 */
	@Cacheable("materiaCercaByNomeCache")
	@Override
	public List<Materia> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<Materia> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Cerca per nome paginata.
	 * @param nome
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 */
	@Cacheable("materiaCercaByNomePagCache")
	@Override
	public List<Materia> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nome"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		 
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(nome);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<Materia> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	/**
	 * Conta per nome
	 * @param nome
	 */
	@Cacheable("materiaContaByNomeCache")
	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@CacheEvict(value = {"materiaLeggiMaterieChildrenCache","materiaLeggiMaterieRootDaSettoreIdCache","materiaLeggiCache","materiaLeggiCacheById","materiaCercaByNomeCache",
						 "materiaCercaByNomePagCache","materiaContaByNomeCache","materiaLeggiDaSettoreIdCache"}, allEntries = true)
	@Override
	public Materia inserisci(Materia vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	
	@Override
	public RSettoreGiuridicoMateria inserisciRSettoreGiuridicoMateria(RSettoreGiuridicoMateria vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	

	@CacheEvict(value = {"materiaLeggiMaterieChildrenCache","materiaLeggiMaterieRootDaSettoreIdCache","materiaLeggiCache","materiaLeggiCacheById","materiaCercaByNomeCache",
			 			"materiaCercaByNomePagCache","materiaContaByNomeCache","materiaLeggiDaSettoreIdCache"}, allEntries = true)
	@Override
	public void modifica(Materia vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@CacheEvict(value = {"materiaLeggiMaterieChildrenCache","materiaLeggiMaterieRootDaSettoreIdCache","materiaLeggiCache","materiaLeggiCacheById","materiaCercaByNomeCache",
			 			"materiaCercaByNomePagCache","materiaContaByNomeCache","materiaLeggiDaSettoreIdCache"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Materia vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
		
	}


	@Cacheable("materiaLeggiDaSettoreIdCache")
	@Override
	public List<Materia> leggiDaSettoreId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class);
		criteria.createAlias("RSettoreGiuridicoMaterias", "RSettoreGiuridicoMaterias");
		criteria.add(Restrictions.eq("RSettoreGiuridicoMaterias.settoreGiuridico.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}


	@Cacheable("materiaLeggiMaterieRootDaSettoreIdCache")
	@Override
	public List<Materia> leggiMaterieRootDaSettoreId(long id, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.createAlias("RSettoreGiuridicoMaterias", "RSettoreGiuridicoMaterias");
		if( id > 0 ){
			criteria.add(Restrictions.eq("RSettoreGiuridicoMaterias.settoreGiuridico.id", id));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.isNull("materiaPadre"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}
	
	@Override
	public List<Materia> leggiMaterieRootTutte(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.isNull("materiaPadre"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}
	 
	@Cacheable("materiaLeggiMaterieChildrenCache")
	@Override
	public List<Materia> leggiMaterieFiglie(long idPadre, long idSettoreGiuridico, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.createAlias("RSettoreGiuridicoMaterias", "RSettoreGiuridicoMaterias");
		if( idSettoreGiuridico > 0 ){
			criteria.add(Restrictions.eq("RSettoreGiuridicoMaterias.settoreGiuridico.id", idSettoreGiuridico));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("materiaPadre.id", idPadre));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}
	
	@Override
	public List<Materia> leggiMaterieFiglie(long idPadre, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("materiaPadre.id", idPadre));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}
 
	@Cacheable("materiaLeggiCodiceLinguaCache")
	@Override
	public Materia leggi(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class)
				.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.eq("lang", lingua));
		if( !tutte ){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		Materia materia = (Materia) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return materia;
	}
	
	@Override
	public List<Materia> leggiPerCodice(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class)
				.add(Restrictions.eq("codGruppoLingua", codice));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Materia> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<String> getCodGruppoLinguaList() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Materia.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Materia> leggiDaBCId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Materia.class);
		criteria.createAlias("RBeautyContestMaterias", "RBeautyContestMaterias");
		criteria.add(Restrictions.eq("RBeautyContestMaterias.beautyContest.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Materia> listaMateria = (List<Materia>)getHibernateTemplate().findByCriteria(criteria);
		return listaMateria;
	}
}