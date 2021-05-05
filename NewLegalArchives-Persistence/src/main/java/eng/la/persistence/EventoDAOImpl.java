package eng.la.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Evento;
import eng.la.util.SpringUtil;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes; 

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità ParteCorrelata</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Parte Correlata.
 * 
 * @author ACER
 */

@Component("eventoDAO")
public class EventoDAOImpl extends HibernateDaoSupport implements EventoDAO{
	@Autowired
	public EventoDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Metodo di inserimento dei dati in base dati.
	 * 
	 * @param fattura istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Evento inserisci(Evento vo) throws Throwable {
		DataSource ds = null;
		Connection conn = null;
		OraclePreparedStatement st = null;
		Long retId=null;
		try {
			ds = (DataSource) SpringUtil.getBean("dataSource");
			conn = ds.getConnection();
		
			String sql= new String();
			sql += "INSERT INTO EVENTO( DATA_EVENTO,  DELAY_AVVISO, FREQUENZA_AVVISO, ID_FASCICOLO,OGGETTO,DESCRIZIONE, LANG) ";
			sql += " VALUES ";
			sql += " ( ";
			sql += "TO_DATE('"+eng.la.util.DateUtil.toString_ORACLE_yyyyMMdd(vo.getDataEvento())+"', 'yyyy/mm/dd hh24:mi:ss')  ,";
			sql += vo.getDelayAvviso()+",";
			sql += vo.getFrequenzaAvviso()+",";
			sql += vo.getFascicolo().getId()+",";
			sql += "'"+vo.getOggetto()+"',";
			sql += "'"+vo.getDescrizione()+"',";
			sql += " 'IT' )";
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
	
	/**
	 * Metodo di lettura dati Evento da base dati, per id.
	 * 
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public Evento leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Evento.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		Evento vo = (Evento) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	/**
	 * Metdodo di cancellazione logica dell'occorrenza in Ato su base dati.
	 * La operazione è una modifca del campo Data Cancellazione con la data corrente.
	 * 
	 * @param id  identificativo dell'occorrenza da cancellare.
	 * @exception Throwable
	 */		
	@Override
	public void cancella(long id) throws Throwable {
		// per cancellazione logica, si recupera prima il rec da cancellare...
		// poi modifca per aggiunta data cancellazione.
		Evento vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	/**
	 * Ritorna la lista di tutte le occorrenze presenti nella base dati.
	 * 
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<Evento> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Evento.class ).addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<Evento> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> leggi(Date inizio, Date fine) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Evento.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.between("dataEvento", inizio, fine));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<Evento> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	/**
	 * _TBV ( verificare se è richiesta questo tipo di ricerca ).
	 * 
	 */
	@Override
	public List<Evento> cerca(Evento vo) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Metodo di operazione modifica.
	 * 
	 * @param evento oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(Evento evento) throws Throwable {
		getHibernateTemplate().update(evento);
	}	
}