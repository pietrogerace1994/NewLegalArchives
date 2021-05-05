package eng.la.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Criterio;
import eng.la.util.SpringUtil;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

@Component("criterioDAO")
public class CriterioDAOImpl extends HibernateDaoSupport implements CriterioDAO, CostantiDAO {

	@Autowired
	public CriterioDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public Criterio leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Criterio.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		Criterio criterio = (Criterio) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return criterio;
	}
	
	public List<Criterio> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Criterio.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		//criteria.createAlias("vendorManagements", "vendorManagements");
		criteria.setFetchMode("vendorManagements", FetchMode.JOIN);
		
		@SuppressWarnings("unchecked")
		List<Criterio> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public Criterio inserisci(Criterio vo) throws Throwable {
		DataSource ds = null;
		Connection conn = null;
		OraclePreparedStatement st = null;
		Long retId=null;
		try {
			ds = (DataSource) SpringUtil.getBean("dataSource");
			conn = ds.getConnection();
			
			String attivo = vo.getAttivo()==null? "T" : vo.getAttivo();
			BigDecimal PERC_REPERIBILITA = vo.getPercReperibilita();
			BigDecimal PERC_TEMPI = vo.getPercTempi();
			BigDecimal PERC_COSTI = vo.getPercCosti();
			BigDecimal PERC_FLESSIBILITA = vo.getPercFlessibilita();
			BigDecimal PERC_COMPETENZE = vo.getPercCompetenze();
			BigDecimal PERC_CAPACITA = vo.getPercCapacita();
			BigDecimal PERC_AUTOREVOLEZZA = vo.getPercAutorevolezza();
			
			String PERC_REPERIBILITA_str = PERC_REPERIBILITA.toString();
			String PERC_TEMPI_str = PERC_TEMPI.toString();
			String PERC_COSTI_str =PERC_COSTI.toString();
			String PERC_FLESSIBILITA_str = PERC_FLESSIBILITA.toString();
			String PERC_COMPETENZE_str = PERC_COMPETENZE.toString();
			String PERC_CAPACITA_str =PERC_CAPACITA.toString();
			String PERC_AUTOREVOLEZZA_str = PERC_AUTOREVOLEZZA.toString();
			
			String sql= new String();
			
			sql += "INSERT INTO CRITERIO( ATTIVO, PERC_REPERIBILITA, PERC_TEMPI, PERC_COSTI, PERC_FLESSIBILITA, PERC_COMPETENZE, PERC_CAPACITA, PERC_AUTOREVOLEZZA ) ";
			sql += " VALUES ";
			sql += " ( ";
			sql += "'"+attivo+"',";
			sql += PERC_REPERIBILITA_str+" , ";
			sql += PERC_TEMPI_str+" , ";
			sql += PERC_COSTI_str+" , ";
			sql += PERC_FLESSIBILITA_str+" , ";
			sql += PERC_COMPETENZE_str+" , ";
			sql += PERC_CAPACITA_str+" , ";
			sql += PERC_AUTOREVOLEZZA_str+" ) ";
			sql += " RETURNING ID INTO ?";
			
			st = (OraclePreparedStatement) conn.prepareStatement(sql); 
			st.registerReturnParameter(1, OracleTypes.INTEGER);
			int count = st.executeUpdate();
			if (count>0)
			{
				
			  ResultSet rs = st.getReturnResultSet(); 
			  while( rs.next() )
			  {
				  retId=rs.getLong(1); 
			  }
			  
			}
			
			vo.setId(retId);
			
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			try { if( st != null ) st.close();} catch(Throwable e){}
			try { if( conn != null ) conn.close();} catch(Throwable e){}
		}
		
		return vo;
	}
}
