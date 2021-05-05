package eng.la.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Configurazione;
import eng.la.util.SpringUtil;

@Component("configurazioneDAO")
public class ConfigurazioneDAOImpl extends HibernateDaoSupport implements ConfigurazioneDAO, CostantiDAO{

	@Autowired
	public ConfigurazioneDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public String leggiValore(String key) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( Configurazione.class ).add( Restrictions.eq("cdKey", key) );
		@SuppressWarnings("unchecked")
		
		Configurazione configurazione = (Configurazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		if(configurazione != null)
			return configurazione.getCdValue();
		else
			return null;
	}

	@Override
	public void aggiornaMatricolaTopResponsabile(String matricolaAutorizzatore) throws Throwable {	
		DataSource ds = null;
		Connection c = null;
		PreparedStatement st = null;
		try {
			ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
		
			String query = "UPDATE CONFIGURAZIONE SET CD_VALUE='"+ matricolaAutorizzatore +"' WHERE CD_KEY='TOP_RESPONSABILE'  " ;
			st = c.prepareStatement(query);
			st.executeUpdate(query);
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			try { if( st != null ) st.close();} catch(Throwable e){}
			try { if( c != null ) c.close();} catch(Throwable e){}
		}
		
	}
	
	

}
