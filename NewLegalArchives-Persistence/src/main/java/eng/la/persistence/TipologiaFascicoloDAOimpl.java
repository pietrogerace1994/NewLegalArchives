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

import eng.la.model.TipologiaFascicolo;

@Component("tipologiaFascicoloDAO")
public class TipologiaFascicoloDAOimpl extends HibernateDaoSupport implements TipologiaFascicoloDAO, CostantiDAO {

	@Autowired
	public TipologiaFascicoloDAOimpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value = "tipologiaFascicoloCacheLeggi")
	@Override
	public List<TipologiaFascicolo> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipologiaFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable(value = "tipologiaFascicoloCacheLeggiDaId")
	@Override
	public TipologiaFascicolo leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipologiaFascicolo tipologiaFascicolo = (TipologiaFascicolo) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipologiaFascicolo;
	}
  
	@Cacheable(value = "tipologiaFascicoloCacheLeggiPerLingua")
	@Override
	public List<TipologiaFascicolo> leggi(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class).addOrder(Order.asc("descrizione"));
		
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<TipologiaFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
 
	@Cacheable(value = "tipologiaFascicoloCacheLeggiDaCodiceLingua")
	@Override
	public TipologiaFascicolo leggi(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class);
		if( !tutte){ 
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("codGruppoLingua", codice));
		@SuppressWarnings("unchecked")
		TipologiaFascicolo tipologiaFascicolo = (TipologiaFascicolo) DataAccessUtils
				.uniqueResult( getHibernateTemplate().findByCriteria(criteria) );
		return tipologiaFascicolo;
	}
	
	@Override
	public List<TipologiaFascicolo> leggiPerSettoreGiuridicoId(long id, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipologiaFascicolo.class ).addOrder(Order.asc("descrizione"));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.createAlias("RTipFascicoloSettGiuridicos", "RTipFascicoloSettGiuridicos");
		criteria.add( Restrictions.eq("RTipFascicoloSettGiuridicos.settoreGiuridico.id", id) ) ; 
		@SuppressWarnings("unchecked")
		List<TipologiaFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	 
	@Cacheable(value = "tipologiaFascicoloCacheCercaPerNomePaginata")
	@Override
	public List<TipologiaFascicolo> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( ordinamento == null){
			criteria.addOrder(Order.asc("descrizione"));
		}else{
			if( ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				criteria.addOrder(Order.asc(ordinamento));
			}else{
				criteria.addOrder(Order.desc(ordinamento));
			}
		} 

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", nome, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		Long numeroTotaleElementi = conta(nome);
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}
		@SuppressWarnings("unchecked")
		List<TipologiaFascicolo> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}
	 
	@Cacheable(value = "tipologiaFascicoloCacheCercaPerNome")
	@Override
	public List<TipologiaFascicolo> cerca(String nome ) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", nome, MatchMode.ANYWHERE));
		}
		@SuppressWarnings("unchecked")
		List<TipologiaFascicolo> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	

	@Cacheable(value = "tipologiaFascicoloCacheContaDaNome")
	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaFascicolo.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("descrizione", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@CacheEvict(value = {"tipologiaFascicoloCacheContaDaNome","tipologiaFascicoloCacheCercaPerNome",
			"tipologiaFascicoloCacheCercaPerNomePaginata", "tipologiaFascicoloCacheLeggiDaCodiceLingua",
			"tipologiaFascicoloCacheLeggiPerLingua","tipologiaFascicoloCacheLeggi",
			"tipologiaFascicoloCacheLeggiDaId"}, allEntries = true)
	@Override
	public TipologiaFascicolo inserisci(TipologiaFascicolo vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@CacheEvict(value = {"tipologiaFascicoloCacheContaDaNome","tipologiaFascicoloCacheCercaPerNome",
			"tipologiaFascicoloCacheCercaPerNomePaginata", "tipologiaFascicoloCacheLeggiDaCodiceLingua",
			"tipologiaFascicoloCacheLeggiPerLingua","tipologiaFascicoloCacheLeggi",
			"tipologiaFascicoloCacheLeggiDaId"}, allEntries = true)
	@Override
	public void modifica(TipologiaFascicolo vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@CacheEvict(value = {"tipologiaFascicoloCacheContaDaNome","tipologiaFascicoloCacheCercaPerNome",
			"tipologiaFascicoloCacheCercaPerNomePaginata", "tipologiaFascicoloCacheLeggiDaCodiceLingua",
			"tipologiaFascicoloCacheLeggiPerLingua","tipologiaFascicoloCacheLeggi",
			"tipologiaFascicoloCacheLeggiDaId"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		TipologiaFascicolo vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
	}
}