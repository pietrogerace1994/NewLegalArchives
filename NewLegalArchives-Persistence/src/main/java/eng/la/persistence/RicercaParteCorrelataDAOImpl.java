package eng.la.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.RicercaParteCorrelata;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità RicercaParteCorrelata</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Parte Correlata.
 * 
 * @author ACER
 */
@Component("ricercaParteCorrelataDAO")
public class RicercaParteCorrelataDAOImpl extends HibernateDaoSupport implements RicercaParteCorrelataDAO{
	@Autowired
	public RicercaParteCorrelataDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Metodo di inserimento dei dati in base dati.
	 * 
	 * @param ricercaParteCorrelata istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public RicercaParteCorrelata inserisci(RicercaParteCorrelata RicercaParteCorrelata) throws Throwable {
		getHibernateTemplate().save(RicercaParteCorrelata);
		return RicercaParteCorrelata;
	}
	
	@Override
	public List<RicercaParteCorrelata> leggi(Date dataInizio, Date dataFine) throws Throwable {
		String dataInizioStr = DateFormatUtils.format(dataInizio, "dd/MM/yyyy");
		
		String dataFineStr = null;
		if(dataFine!=null)
				dataFineStr = DateFormatUtils.format(dataFine, "dd/MM/yyyy");
		
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("SELECT  rpc.DENOMINAZIONE_IN, rpc.COD_FISCALE, rpc.PARTITA_IVA, rpc.DATA_IN, rpc.DATA_RICERCA, rpc.USER_RICERCA, rpc.ESITO, rpc.REPORT, rpc.DATA_CANCELLAZIONE, rpc.ID   FROM RICERCA_PARTE_CORRELATA rpc ");
		stringaSql.append(" WHERE ");
		if(dataFineStr !=null)
			stringaSql.append("TRUNC(rpc.DATA_RICERCA) <= ").append(" TO_DATE('").append(dataFineStr).append("', 'dd/MM/yyyy') ").append(" AND ");
		stringaSql.append("TRUNC(rpc.DATA_RICERCA)   >=  ").append(" TO_DATE('").append(dataInizioStr).append("', 'dd/MM/yyyy')");
		
		stringaSql.append(" order by rpc.DENOMINAZIONE_IN ASC ");
		 
		List<RicercaParteCorrelata> ricercaPartiCorList = new ArrayList<RicercaParteCorrelata>(0);
		 
	    ricercaPartiCorList = getHibernateTemplate().execute(new HibernateCallback<List<RicercaParteCorrelata>>() {
				@Override
				public List<RicercaParteCorrelata> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					@SuppressWarnings("unchecked")
					List<Object> queryResult = (List<Object>)sqlQuery.list();
					List<RicercaParteCorrelata> rpcList = new ArrayList<RicercaParteCorrelata>();					
					
					for (Object row:queryResult){
						Object[] fields = (Object[])row;
						RicercaParteCorrelata rpc = new RicercaParteCorrelata();
						
						
						if( fields[0] != null)
							rpc.setDenominazioneIn((String)fields[0]);
						else
							rpc.setDenominazioneIn(null);
						
						if( fields[1] != null)
							rpc.setCodFiscale((String)fields[1]);
						else
							rpc.setCodFiscale(null);
						
						if( fields[2] != null)
							rpc.setPartitaIva((String)fields[2]);
						else
							rpc.setPartitaIva(null);
						
						if( fields[3] != null)
							rpc.setDataIn((Date)fields[3]);
						else
							rpc.setDataIn(null);
						
						if( fields[4] != null)
							rpc.setDataRicerca((Date)fields[4]);
						else
							rpc.setDataRicerca(null);
						
						if( fields[5] != null)
							rpc.setUserRicerca((String)fields[5]);
						else
							rpc.setUserRicerca(null);
						
						if( fields[6] != null)
							rpc.setEsito( ""+  (Character)fields[6] );
						else
							rpc.setEsito( null );
							
						rpc.setReport(null);
						
						if( fields[8] != null)
							rpc.setDataCancellazione((Date)fields[8]);
						else
							rpc.setDataCancellazione(null);
						
						if( fields[9] != null) {
							java.math.BigDecimal num = (java.math.BigDecimal) fields[9];
							rpc.setId( num.longValue() );
						} else
							rpc.setId(0);
						
						rpcList.add(rpc);
					}		
					return rpcList;
				}
			});
	    	
	    	System.out.println("DAOImpl.leggi.ricercaPartiCorList :  " + ricercaPartiCorList.size()); 
	    	
	    	return ricercaPartiCorList;
	}
	
	/**
	 * Metodo di lettura dati RicercaParteCorrelata da base dati, per id.
	 * 
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public RicercaParteCorrelata leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RicercaParteCorrelata.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		RicercaParteCorrelata ricercaParteCorrelata = (RicercaParteCorrelata) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return ricercaParteCorrelata; 
	}

	/**
	 * Metdodo di lettura dati ParteCorrelata da base dati, per il campo 
	 * Codice Fiscale / PIVA..
	 * 
	 * @param codFiscPIva  codice fiscale o piva per lettura occorrenza .
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */		
	@Override
	public RicercaParteCorrelata leggi(String codFiscale, String partitaIva) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RicercaParteCorrelata.class );
		
		Criterion crit_1 =  Restrictions.eq("codFiscale", codFiscale) ;
		Criterion crit_2 =  Restrictions.eq("partitaIva", partitaIva) ;
		
		Disjunction or = Restrictions.disjunction();
		or.add(crit_1);
		or.add(crit_2);
		
		criteria.add(or);
				
		@SuppressWarnings("unchecked")
		RicercaParteCorrelata RicercaParteCorrelata = (RicercaParteCorrelata) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return RicercaParteCorrelata;
	}
	
	/**
	 * Metdodo di cancellazione logica dell'occorrenza in ParteCorrelata su base dati.
	 * La operazione è una modifca del campo Data Cancellazione con la data corrente.
	 * 
	 * @param id  identificativo dell'occorrenza da cancellare.
	 * @exception Throwable
	 */		
	@Override
	public void cancella(long id) throws Throwable {
		// per cancellazione logica, si recupera prima il rec da cancellare...
		// poi modifca per aggiunta data cancellazione.
		RicercaParteCorrelata ricercaParteCorrelata = leggi(id);
		ricercaParteCorrelata.setDataCancellazione(new Date());
		getHibernateTemplate().update(ricercaParteCorrelata);
	}	
	
	/**
	 * _TBV ( verificare se tale metodod è utile ).
	 * 
	 */
	@Override
	public List<RicercaParteCorrelata> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RicercaParteCorrelata.class ).addOrder(Order.asc("nome"));
		@SuppressWarnings("unchecked")
		List<RicercaParteCorrelata> lista = (List<RicercaParteCorrelata>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	/**
	 * Metodo di ricerca ParteCorrelata.
	 * 
	 * @param denominazione
	 * @param codFiscPartIva
	 * @throws Throwable 
	 */
	@Override
	public List<RicercaParteCorrelata> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RicercaParteCorrelata.class ).addOrder(Order.asc("denominazione"));
		if( denominazione != null && denominazione.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("denominazione", denominazione, MatchMode.ANYWHERE) ) ;
		}
		
		if( codFiscale != null && codFiscale.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("codFiscale", codFiscale, MatchMode.ANYWHERE) ) ;
		}
		
		if( partitaIva != null && partitaIva.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("partitaIva", partitaIva, MatchMode.ANYWHERE) ) ;
		}
		
		@SuppressWarnings("unchecked")
		List<RicercaParteCorrelata> lista = (List<RicercaParteCorrelata>)getHibernateTemplate().findByCriteria(criteria);		
		return lista;
	}
	
	/**
	 * Metodo di operazione modifica.
	 * 
	 * @param parteCorrelata oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(RicercaParteCorrelata ricercaParteCorrelata) throws Throwable {
		// lettura record da modificare.
		
		long id = ricercaParteCorrelata.getId(); 
		RicercaParteCorrelata _ricercaParteCorrelata = leggi(id);
		if (ricercaParteCorrelata.getCodFiscale()!=null /*_ricercaParteCorrelata.getCodFiscale() */)
			_ricercaParteCorrelata.setCodFiscale(ricercaParteCorrelata.getCodFiscale());
		
		if (ricercaParteCorrelata.getPartitaIva()!=null /*_ricercaParteCorrelata.getPartitaIva() */)
			_ricercaParteCorrelata.setPartitaIva(ricercaParteCorrelata.getPartitaIva());
		
		if (ricercaParteCorrelata.getDataCancellazione()!=null /*_ricercaParteCorrelata.getDataCancellazione()*/ )
			_ricercaParteCorrelata.setDataCancellazione(ricercaParteCorrelata.getDataCancellazione());
		
		if (ricercaParteCorrelata.getDataIn()!=null /*_ricercaParteCorrelata.getDataIn()*/ )
			_ricercaParteCorrelata.setDataIn(ricercaParteCorrelata.getDataIn());

		if (ricercaParteCorrelata.getDataRicerca()!=null /*_ricercaParteCorrelata.getDataRicerca() */)
			_ricercaParteCorrelata.setDataRicerca(ricercaParteCorrelata.getDataRicerca());

		if (ricercaParteCorrelata.getDenominazioneIn()!=null /*_ricercaParteCorrelata.getDenominazioneIn()*/ )
			_ricercaParteCorrelata.setDenominazioneIn(ricercaParteCorrelata.getDenominazioneIn());

		if (ricercaParteCorrelata.getEsito()!=null /*_ricercaParteCorrelata.getEsito()*/ )
			_ricercaParteCorrelata.setEsito(ricercaParteCorrelata.getEsito());

		if (ricercaParteCorrelata.getReport()!=null /*_ricercaParteCorrelata.getReport()*/ )
			_ricercaParteCorrelata.setReport(ricercaParteCorrelata.getReport());

		if (ricercaParteCorrelata.getUserRicerca()!=null /*_ricercaParteCorrelata.getUserRicerca()*/ )
			_ricercaParteCorrelata.setUserRicerca(ricercaParteCorrelata.getUserRicerca());

		getHibernateTemplate().update(ricercaParteCorrelata);
	}

	@Override
	public List<RicercaParteCorrelata> cerca(String nomeUtenteVisualizzato, Boolean isUtentePrestazioneNotarili,
			String denominazione, String codFiscale, String partitaIva, Date data) throws Throwable {
		return null;
	}
}






