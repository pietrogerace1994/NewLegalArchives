package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.StatoEsitoValutazioneProf;

@Component("statoEsitoValutazioneProfDAO")
public class StatoEsitoValutazioneProfDAOImpl extends HibernateDaoSupport implements StatoEsitoValutazioneProfDAO {
	
	@Autowired
	public StatoEsitoValutazioneProfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value="statoEsitoValutazioneProfCacheLeggiPerLingua") 
	@Override
	public List<StatoEsitoValutazioneProf> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoEsitoValutazioneProf.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<StatoEsitoValutazioneProf> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 
	
	@Cacheable(value="statoEsitoValutazioneProfCacheLeggiPerCodiceLingua") 
	@Override
	public StatoEsitoValutazioneProf leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoEsitoValutazioneProf.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		StatoEsitoValutazioneProf statoEsitoValutazioneProf = (StatoEsitoValutazioneProf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return statoEsitoValutazioneProf; 
	}

} 