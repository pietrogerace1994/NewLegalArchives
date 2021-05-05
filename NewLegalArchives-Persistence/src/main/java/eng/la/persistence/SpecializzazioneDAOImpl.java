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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.RProfEstSpec;
import eng.la.model.Specializzazione;

@Component("specializzazioneDAO")
public class SpecializzazioneDAOImpl extends HibernateDaoSupport implements SpecializzazioneDAO {
	
	@Autowired
	public SpecializzazioneDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value="specializzazioneCacheLeggi")
	@Override
	public List<Specializzazione> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Specializzazione> lista = getHibernateTemplate().findByCriteria(criteria);
		 
		return lista; 
	}

	@Cacheable(value="specializzazioneCacheLeggiDaId")
	@Override
	public Specializzazione leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Specializzazione specializzazione = (Specializzazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return specializzazione; 
	}
  
	@Cacheable(value="specializzazioneCacheLeggiPerLingua") 
	@Override
	public List<Specializzazione> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<Specializzazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 

	@Cacheable(value="specializzazioneCacheLeggiPerCodiceLingua") 
	@Override
	public Specializzazione leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Specializzazione specializzazione = (Specializzazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return specializzazione; 
	}
	
	@Cacheable(value="specializzazioneCacheLeggiPerCodice") 
	@Override
	public List<Specializzazione> leggibyCodice(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class ).addOrder(Order.asc("id"));
		criteria.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Specializzazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	/**
	 * Metdodo di cancellazione logica dell'occorrenza in Specializzazione su base dati.
	 * La operazione è una modifica del campo Data Cancellazione con la data corrente.
	 * 
	 * @param id  identificativo dell'occorrenza da cancellare.
	 * @exception Throwable
	 */	
	@CacheEvict(value = {"specializzazioneCacheLeggi","specializzazioneCacheLeggiDaId","specializzazioneCacheLeggiPerLingua",
			"specializzazioneCacheLeggiPerCodiceLingua","specializzazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Specializzazione vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	@CacheEvict(value = {"specializzazioneCacheLeggi","specializzazioneCacheLeggiDaId","specializzazioneCacheLeggiPerLingua",
			"specializzazioneCacheLeggiPerCodiceLingua","specializzazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public void modifica(Specializzazione specializzazione) throws Throwable {
		getHibernateTemplate().update(specializzazione);
	}
	
	@Override
	public List<String> getCodGruppoLinguaList() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public boolean controlla(String descrizione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Specializzazione.class );
		criteria.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Specializzazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return !lista.isEmpty(); 
	}
	
	/**
	 * Metodo di inserimento dei dati in base dati.
	 * 
	 * @param specializzazione istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@CacheEvict(value = {"specializzazioneCacheLeggi","specializzazioneCacheLeggiDaId","specializzazioneCacheLeggiPerLingua",
			"specializzazioneCacheLeggiPerCodiceLingua","specializzazioneCacheLeggiPerCodice"}, allEntries = true)
	@Override
	public Specializzazione inserisci(Specializzazione vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Metodo di lettura delle specializzazioni associate ad un professionista
	 * <p>
	 * @param id: identificativo del professionista
	 * @return lista oggetti specializzazione.
	 * @exception Throwable
	 */
	@Override
	public List<Specializzazione> leggiSpecializzazioniAssociatiAProfessionista(long id) throws Throwable {
		
		List<Specializzazione> specializzazioniList = new ArrayList<Specializzazione>(0);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfEstSpec.class);
		
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");
		criteria.add(Restrictions.eq("professionistaEsterno.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RProfEstSpec> listaRelazione =(List<RProfEstSpec>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RProfEstSpec relazione:listaRelazione){
				 specializzazioniList.add(relazione.getSpecializzazione());
				}	
			 return specializzazioniList;
		 }
	    else
	    	return null;
	}
	
} 