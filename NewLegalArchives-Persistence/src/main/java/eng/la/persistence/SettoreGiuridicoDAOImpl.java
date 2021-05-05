package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
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

import eng.la.model.SettoreGiuridico;

@Component("settoreGiuridicoDAO")
public class SettoreGiuridicoDAOImpl extends HibernateDaoSupport implements SettoreGiuridicoDAO, CostantiDAO {

	@Autowired
	public SettoreGiuridicoDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable("settoreGiuridicoCacheLeggi")
	@Override
	public List<SettoreGiuridico> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Cacheable("settoreGiuridicoCacheLeggiDaId")
	@Override
	public SettoreGiuridico leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		SettoreGiuridico settoreGiuridico = (SettoreGiuridico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return settoreGiuridico; 
	}

	@Cacheable("settoreGiuridicoCacheCercaPerNome")
	@Override
	public List<SettoreGiuridico> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nome", nome, MatchMode.ANYWHERE) ) ;
		}
		  
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	} 
 
	@Cacheable("settoreGiuridicoCacheCercaPerNomePaginato")
	@Override
	public List<SettoreGiuridico> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( ordinamento == null){
			criteria.addOrder(Order.asc("nome"));
		}else{
			if( ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				criteria.addOrder(Order.asc(ordinamento));
			}else{
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nome", nome, MatchMode.ANYWHERE) ) ;
		}
		
		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		Long numeroTotaleElementi = conta(nome);
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}
		  
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);		
		return lista; 
	} 

	@Cacheable("settoreGiuridicoCacheConta")
	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SettoreGiuridico.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nome", nome, MatchMode.ANYWHERE) ) ;
		}
		  
		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@CacheEvict(value={"settoreGiuridicoCacheConta","settoreGiuridicoCacheCercaPerNomePaginato",
			"settoreGiuridicoCacheCercaPerNome", "settoreGiuridicoCacheLeggiDaId", 
			"settoreGiuridicoCacheLeggi","settoreGiuridicoCacheLeggiDaCodiceLingua",
			"settoreGiuridicoCacheLeggiDaTipologiaId","settoreGiuridicoCacheLeggiPerLingua"}, allEntries=true)
	@Override
	public SettoreGiuridico inserisci(SettoreGiuridico vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@CacheEvict(value={"settoreGiuridicoCacheConta","settoreGiuridicoCacheCercaPerNomePaginato",
			"settoreGiuridicoCacheCercaPerNome", "settoreGiuridicoCacheLeggiDaId", 
			"settoreGiuridicoCacheLeggi","settoreGiuridicoCacheLeggiDaCodiceLingua",
			"settoreGiuridicoCacheLeggiDaTipologiaId","settoreGiuridicoCacheLeggiPerLingua"}, allEntries=true)
	@Override
	public void modifica(SettoreGiuridico vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	@CacheEvict(value={"settoreGiuridicoCacheConta","settoreGiuridicoCacheCercaPerNomePaginato",
			"settoreGiuridicoCacheCercaPerNome", "settoreGiuridicoCacheLeggiDaId", 
			"settoreGiuridicoCacheLeggi","settoreGiuridicoCacheLeggiDaCodiceLingua",
			"settoreGiuridicoCacheLeggiDaTipologiaId","settoreGiuridicoCacheLeggiPerLingua"}, allEntries=true)
	@Override
	public void cancella(long id) throws Throwable {
		SettoreGiuridico vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
	}

	@Cacheable("settoreGiuridicoCacheLeggiDaCodiceLingua")
	@Override
	public SettoreGiuridico leggi(String codice, String lingua, boolean tutte) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class )
				.add( Restrictions.eq("codGruppoLingua", codice) )
				.add( Restrictions.eq("lang", lingua) );
		if( !tutte ){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		SettoreGiuridico settoreGiuridico = (SettoreGiuridico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return settoreGiuridico;  
	}

	@Cacheable("settoreGiuridicoCacheLeggiDaTipologiaId")
	@Override
	public List<SettoreGiuridico> leggiPerTipologiaId(long id, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		if(!tutte){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.createAlias("RTipFascicoloSettGiuridicos", "RTipFascicoloSettGiuridicos");
		criteria.add( Restrictions.eq("RTipFascicoloSettGiuridicos.tipologiaFascicolo.id", id) ) ; 
		  
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	@Override
	public List<SettoreGiuridico> leggiPerTipologiaId(long id) throws Throwable  {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		criteria.createAlias("RTipFascicoloSettGiuridicos", "RTipFascicoloSettGiuridicos");
//		criteria.createAlias("RSettGiuridicoPosSocietas", "RSettGiuridicoPosSocietas");
//		criteria.setFetchMode("RSettGiuridicoPosSocietas", FetchMode.JOIN);
		criteria.setFetchMode("RTipFascicoloSettGiuridicos", FetchMode.JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		criteria.add( Restrictions.eq("RTipFascicoloSettGiuridicos.tipologiaFascicolo.id", id) ) ; 
		  
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		for(int i=0; i< lista.size(); i++ ) {
			SettoreGiuridico sg = lista.get(i);
			sg.setCentroDiCostos(null);
			sg.setRSettGiuridicoPosSocietas(null);
			sg.setRSettoreGiuridicoMaterias(null);
			sg.setFascicolos(null);
			sg.setRSettoreGiuridicoGiudizios(null);
			sg.setVoceDiContos(null);
			sg.setRTipFascicoloSettGiuridicos(null);
		}
		return lista; 
	}
	
	/*
//	@Cacheable("settoreGiuridicoCacheLeggiDaTipologiaId")
	@Override
	public List<SettoreGiuridico> leggiPerTipologiaId(long id, boolean tutte,  FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		
		if(tutte==false){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.createAlias("RTipFascicoloSettGiuridicos", "RTipFascicoloSettGiuridicos");
		criteria.createAlias("RSettGiuridicoPosSocietas", "RSettGiuridicoPosSocietas");
		criteria.add( Restrictions.eq("RTipFascicoloSettGiuridicos.tipologiaFascicolo.id", id) ) ; 
		criteria.setFetchMode("RTipFascicoloSettGiuridicos", fetchMode);
		criteria.setFetchMode("RSettGiuridicoPosSocietas", fetchMode);
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}*/
	
	@Cacheable("settoreGiuridicoCacheLeggiPerLingua")
	@Override
	public List<SettoreGiuridico> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SettoreGiuridico.class ).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));  
		criteria.add( Restrictions.eq("lang", lingua) ) ; 
		  
		@SuppressWarnings("unchecked")
		List<SettoreGiuridico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	} 
}