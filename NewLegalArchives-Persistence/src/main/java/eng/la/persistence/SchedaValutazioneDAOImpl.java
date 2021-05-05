package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.SchedaValutazione;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità SchedaValutazione</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità SchedaValutazione.
 * 
 * @author ACER
 */

@Component("schedaValutazioneDAO")
public class SchedaValutazioneDAOImpl extends HibernateDaoSupport implements SchedaValutazioneDAO{
	@Autowired
	public SchedaValutazioneDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Metodo di inserimento dei dati in base dati.
	 * <p>
	 * @param fattura istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public SchedaValutazione inserisci(SchedaValutazione vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Metodo di lettura dati SchedaValutazione da base dati, per id.
	 * <p>
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public SchedaValutazione leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaValutazione.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		SchedaValutazione vo = (SchedaValutazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	/**
	 * Metdodo di cancellazione logica dell'occorrenza in SchedaValutazione su base dati.
	 * La operazione è una modifca del campo Data Cancellazione con la data corrente.
	 * <p>
	 * @param id  identificativo dell'occorrenza da cancellare.
	 * @exception Throwable
	 */		
	@Override
	public void cancella(long id) throws Throwable {
		// per cancellazione logica, si recupera prima il rec da cancellare...
		// poi modifca per aggiunta data cancellazione.
		SchedaValutazione vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	/**
	 * Ritorna la lista di tutte le occorrenze presenti nella base dati.
	 * <p>
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<SchedaValutazione> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaValutazione.class ).addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<SchedaValutazione> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	/**
	 * _TBV ( verificare se è richiesta questo tipo di ricerca ).
	 * 
	 */
	@Override
	public List<SchedaValutazione> cerca(SchedaValutazione vo) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Metodo di operazione modifica.
	 * <p>
	 * @param evento oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(SchedaValutazione schedaValutazione) throws Throwable {
		getHibernateTemplate().update(schedaValutazione);
	}	
}