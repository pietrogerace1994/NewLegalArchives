package eng.la.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.NotificheAgendaDisplayed;
import eng.la.util.SpringUtil;

@Component("agendaNotificheDisplayedDAO")
public class AgendaNotificheDisplayedDAOImpl extends HibernateDaoSupport implements AgendaNotificheDisplayedDAO {
	
	@Autowired
	public AgendaNotificheDisplayedDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public NotificheAgendaDisplayed addNotificaDisplayed(NotificheAgendaDisplayed vo) throws Throwable {
		DataSource ds = null;
		Connection c = null;
		PreparedStatement st = null;
		try {
			ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			
			String sql= new String();
			sql += "INSERT INTO NOTIFICHE_AGENDA_DISPLAYED( MATRICOLA_UTENTE, ID_EVENTO, ID_SCADENZA, DATA_DISPLAYED, TIPO) ";
			sql += " VALUES ";
			sql += " ( ";
			sql += "'"+vo.getMatricolaUtente()+"',";
			sql += "'"+vo.getIdEvento()+"' , ";
			sql += "'"+vo.getIdScadenza()+"' , ";
			sql += "TO_DATE('"+eng.la.util.DateUtil.toString_ORACLE_yyyyMMdd_HHmmss(vo.getDataDisplayed())+"', 'yyyy/mm/dd hh24:mi:ss')  ,";
			sql += "'"+vo.getTipo()+"' ) ";
			st = c.prepareStatement(sql);
			st.executeUpdate();
			
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			try { if( st != null ) st.close();} catch(Throwable e){}
			try { if( c != null ) c.close();} catch(Throwable e){}
		}
		
		return vo;
	}
	
	@Override
	public List<NotificheAgendaDisplayed> leggi(String matricolaUtente, String tipo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificheAgendaDisplayed.class);
		criteria.add(Restrictions.eq("matricolaUtente", matricolaUtente));
		criteria.add(Restrictions.like("tipo", tipo) );
		@SuppressWarnings("unchecked")
		List<NotificheAgendaDisplayed> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificheAgendaDisplayed> leggiLe(Date data) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotificheAgendaDisplayed.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.le("dataDisplayed", data));
		List<NotificheAgendaDisplayed> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificheAgendaDisplayed> leggiLe3(Date data) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotificheAgendaDisplayed.class ).addOrder(Order.asc("id"));
		java.sql.Timestamp t=new java.sql.Timestamp(data.getTime());
		criteria.add(Restrictions.le("dataDisplayed", t));
		List<NotificheAgendaDisplayed> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificheAgendaDisplayed> leggiLe2(Date data) throws Throwable {
		StringBuffer stringaSql = new StringBuffer();
		String datastr= DateFormatUtils.format(data, "dd/MM/yyyy");
		stringaSql.append("SELECT  rpc.MATRICOLA_UTENTE, rpc.ID_EVENTO, rpc.ID_SCADENZA, rpc.TIPO, rpc.DATA_DISPLAYED, rpc.ID  FROM NOTIFICHE_AGENDA_DISPLAYED rpc ");
		stringaSql.append(" WHERE ");
		stringaSql.append("TRUNC(rpc.DATA_DISPLAYED) <= ").append(" TO_DATE('").append(datastr).append("', 'dd/MM/yyyy') ");
		stringaSql.append(" order by rpc.ID ASC ");
		 
		List<NotificheAgendaDisplayed> lista ;
		 
		lista = getHibernateTemplate().execute(new HibernateCallback<List<NotificheAgendaDisplayed>>() {
				@Override
				public List<NotificheAgendaDisplayed> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					List<Object> queryResult = sqlQuery.list();
					System.out.println("queryResult size : "  + queryResult.size());
					List<NotificheAgendaDisplayed> rpcList = new ArrayList<NotificheAgendaDisplayed>();					
					for (Object row:queryResult){
						Object[] fields = (Object[])row;
						NotificheAgendaDisplayed rpc = new NotificheAgendaDisplayed();
						
						if( fields[0] != null)
							rpc.setMatricolaUtente((String)fields[0]);
						else
							rpc.setMatricolaUtente(null);
						
						if( fields[1] != null) {
							BigDecimal bd=((BigDecimal)fields[1]);
							rpc.setIdEvento( bd.longValue()  );
						} else
							rpc.setIdEvento(null);
						
						if( fields[2] != null) {
							BigDecimal bd=((BigDecimal)fields[2]);
							rpc.setIdScadenza( bd.longValue()  );
						} else
							rpc.setIdScadenza(null);
						
						if( fields[3] != null) {
							rpc.setTipo((String)fields[3]);
						} else
							rpc.setTipo(null);
						
						if( fields[4] != null) {
							rpc.setDataDisplayed(new Date(((java.sql.Date)fields[4]).getTime()));
						} else
							rpc.setDataDisplayed(null);
						
						if( fields[5] != null) {
							rpc.setId(((BigDecimal)fields[5]).longValue());
						} else
							rpc.setId(null);
						
						
						rpcList.add(rpc);
					}		
					return rpcList;
				}
			});
	    	
	    	return lista;
	}
	
	@Override
	public void cancella(NotificheAgendaDisplayed vo) throws Throwable {
		getHibernateTemplate().delete(vo);
	}
	
}