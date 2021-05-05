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

import eng.la.model.Nazione;

@Component("nazioneDAO")
public class NazioneDAOImpl extends HibernateDaoSupport implements NazioneDAO {
	
	@Autowired
	public NazioneDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value="nazioneCacheLeggi")
	@Override
	public List<Nazione> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).addOrder(Order.asc("descrizione"));
		 
		criteria.add(Restrictions.isNull("dataCancellazione"));		
		 
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		 
		return lista; 
	}

	@Cacheable(value="nazioneCacheLeggiDaId")
	@Override
	public Nazione leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Nazione nazione = (Nazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return nazione; 
	}
  
	@Cacheable(value="nazioneCacheLeggiPerLingua") 
	@Override
	public List<Nazione> leggi(String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).addOrder(Order.asc("descrizione"));
		if(!tutte)
		{
			criteria.add(Restrictions.isNull("dataCancellazione"));		
		}
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 

	@Cacheable(value="nazioneCacheLeggiPerLinguaPartiCorrelate") 
	@Override
	public List<Nazione> leggiPartiCorrelate(String lingua, boolean partiCorrelate) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("soloParteCorrelata", partiCorrelate==true?"T":"F"));
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public List<Nazione> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Cacheable(value="nazioneCacheLeggiPerCodiceLingua") 
	@Override
	public Nazione leggi(String codice, String lingua, boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		if(!tutte)
		{
			criteria.add(Restrictions.isNull("dataCancellazione"));		
		}
		@SuppressWarnings("unchecked")
		Nazione nazione = (Nazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return nazione; 
	}
	
	@Cacheable(value="nazioneCacheLeggiPerCodice") 
	@Override
	public List<Nazione> leggibyCodice(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).addOrder(Order.asc("id"));
		criteria.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	/**
	 * Metdodo di cancellazione logica dell'occorrenza in Nazione su base dati.
	 * La operazione è una modifica del campo Data Cancellazione con la data corrente.
	 * 
	 * @param id  identificativo dell'occorrenza da cancellare.
	 * @exception Throwable
	 */	
	@CacheEvict(value = {"nazioneCacheLeggi","nazioneCacheLeggiDaId","nazioneCacheLeggiPerLingua",
			"nazioneCacheLeggiPerLinguaPartiCorrelate","nazioneCacheLeggiPerCodiceLingua",
			"nazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Nazione vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	@CacheEvict(value = {"nazioneCacheLeggi","nazioneCacheLeggiDaId","nazioneCacheLeggiPerLingua",
			"nazioneCacheLeggiPerLinguaPartiCorrelate","nazioneCacheLeggiPerCodiceLingua",
			"nazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public void modifica(Nazione nazione) throws Throwable {
		getHibernateTemplate().update(nazione);
	}
	
	@Override
	public List<String> getCodGruppoLinguaList() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public boolean controlla(String descrizione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class );
		criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Nazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return !lista.isEmpty(); 
	}
	
	/**
	 * Metodo di inserimento dei dati in base dati.
	 * 
	 * @param nazione istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@CacheEvict(value = {"nazioneCacheLeggi","nazioneCacheLeggiDaId","nazioneCacheLeggiPerLingua",
			"nazioneCacheLeggiPerLinguaPartiCorrelate","nazioneCacheLeggiPerCodiceLingua",
			"nazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public Nazione inserisci(Nazione vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Metodo di lettura della nazione nella lingua corrente a partire dal codice.
	 * <p>
	 * @param code: codice lingua
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model Nazione popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Nazione leggiNazioneTradotta(String code, String lingua) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class );
		criteria.add( Restrictions.isNull("dataCancellazione")) ;
		criteria.add( Restrictions.eq("codGruppoLingua", code) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		return (Nazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		

	}
	
	
} 