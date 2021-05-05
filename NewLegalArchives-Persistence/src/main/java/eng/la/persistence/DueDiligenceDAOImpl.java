package eng.la.persistence;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.DueDiligence;
import eng.la.model.view.DueDiligenceView;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità ParteCorrelata</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Parte Correlata.
 * 
 * @author ACER
 */

@Component("dueDiligenceDAO")
public class DueDiligenceDAOImpl extends HibernateDaoSupport implements DueDiligenceDAO{
	@Autowired
	public DueDiligenceDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Metodo di inserimento dei dati in base dati.
	 * 
	 * @param parteCorrelata istanza del model con i dati da inserire
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public DueDiligence inserisci(DueDiligence vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Metodo di lettura dati ParteCorrelata da base dati, per id.
	 * 
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public DueDiligence leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( DueDiligence.class ).add( Restrictions.eq("id", id) );
		// criteria.setFetchMode("documentoDueDiligences", FetchMode.JOIN);
		criteria.createAlias("documentoDueDiligences", "documentoDueDiligences", DetachedCriteria.LEFT_JOIN,Restrictions.isNull("dataCancellazione"));
		//		criteria.add(Restrictions.isNull("documentoDueDiligences.dataCancellazione"));

		@SuppressWarnings("unchecked")
		DueDiligence vo = (DueDiligence) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
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
		DueDiligence vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}

	@Override
	public DueDiligence deleteDueDiligence(long id) throws Throwable {
		DueDiligence vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
		return vo;
	}

	@Override
	public List<DueDiligence> leggi(boolean isActive) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( DueDiligence.class ).addOrder(Order.asc("id"));
		if (isActive)
			criteria.add(Restrictions.isNull("dataCancellazione"));
		else
			criteria.add(Restrictions.isNotNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<DueDiligence> lista = (List<DueDiligence>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	/**
	 * Ritorna la lista di tutte le occorrenze presenti nella base dati.
	 * 
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<DueDiligence> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( DueDiligence.class ).addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<DueDiligence> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	/**
	 * Ritorna lista occorrenze ordinate (ASC o DESC) per la data di chiusura
	 *
	 * @param ordinaDataChiusura
	 */
	@Override
	public List<DueDiligence> leggi(char ordinaDataChiusura) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( DueDiligence.class );

		if ( ordinaDataChiusura == ASC)
			criteria.addOrder(Order.asc("dataChiusura"));
		else if (ordinaDataChiusura == DESC)
			criteria.addOrder(Order.desc("dataChiusura"));
		else
			criteria.addOrder(Order.asc("dataChiusura")); //default

		@SuppressWarnings("unchecked")
		List<DueDiligence> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	/**
	 * _TBV ( verificare se è richiesta questo tipo di ricerca ).
	 * 
	 */
	@Override
	public List<DueDiligence> cerca(DueDiligence vo) throws Throwable {
		return null;
	}

	/**
	 * Metodo di operazione modifica.
	 * 
	 * @param parteCorrelata oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(DueDiligence dueDiligence) throws Throwable {
		getHibernateTemplate().update(dueDiligence);
	}

	/**
	 * Esegue ricerca occorrenze per professionista esterno.
	 * 
	 * @param idProfEsterno
	 */
	@Override
	public List<DueDiligence> cerca(long idProfEsterno) throws Throwable {
		DetachedCriteria criteria = 
				DetachedCriteria.forClass( DueDiligence.class ).add( Restrictions.eq("professionistaEsterno", idProfEsterno) );
		@SuppressWarnings("unchecked")
		List<DueDiligence> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	/**
	 * Questa funzione ritorna l'elenco degli anni per professionista esterno.
	 * Al momento la data di "aggregazione" per professionista ext si è presa la data di apertura 
	 * ( da verificare con Francesco e Fausto ).
	 * esempio query : 
	 * SELECT DISTINCT EXTRACT(year from DATA_CREAZIONE) as year FROM TEST_TABLE WHERE ID_PROF_EXT=2;
	 * @param idProfEsterno
	 */
	@Override
	public List<Object> elencoAnni(long idProfEsterno) throws Throwable {
		final String sql = "SELECT DISTINCT EXTRACT(year from DATA_APERTURA) as year FROM DUE_DILIGENCE WHERE ID_PROFESSIONISTA=" + idProfEsterno;		
		@SuppressWarnings("unchecked")
		List<Object> lista = getHibernateTemplate().execute(new HibernateCallback<List<Object>>() {
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		}); 
		return lista;
	}

	/**
	 * Questa funzione ritorna l'elenco delle richieste per prof ext per un determinato anno.
	 * ( anche in questo caso verificare con Francesco e fausto se far uso della Data Apertura 
	 * o altra...
	 * esempio query : 
	 * SELECT * FROM TEST_TABLE WHERE ID_PROF_EXT=2 and EXTRACT(year from DATA_CREAZIONE)=2016;
	 * @param idProfEsterno
	 * @param anno
	 */
	@Override
	public List<DueDiligence> elenco(long idProfEsterno, int anno) throws Throwable {
		final String sql = "SELECT * FROM DUE_DILIGENCE WHERE ID_PROFESSIONISTA=" + idProfEsterno + " and EXTRACT(year from DATA_APERTURA)=" + anno;
		@SuppressWarnings("unchecked")
		List<DueDiligence> lista = getHibernateTemplate().execute(new HibernateCallback<List<DueDiligence>>() {
			@Override
			public List<DueDiligence> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DueDiligence.class);
				return sqlQuery.list();
			}
		});
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DueDiligence> searchRichAutGiudByFilter(DueDiligenceView dueDiligenceView) throws ParseException {

		List<DueDiligence> out = null;
		if(dueDiligenceView != null){
			DetachedCriteria criteria = DetachedCriteria.forClass( DueDiligence.class ).addOrder(Order.desc("dataChiusura"));

			Long professionistaCode = dueDiligenceView.getProfessionistaCode();
			Long statoDueDiligenceCode = dueDiligenceView.getStatoDueDiligenceCode();
			String dataAperturaDal = dueDiligenceView.getDataAperturaDal();
			String dataAperturaAl = dueDiligenceView.getDataAperturaAl();
			String dataChiusuraDal = dueDiligenceView.getDataChiusuraDal();
			String dataChiusuraAl = dueDiligenceView.getDataChiusuraAl();
			criteria.setFetchMode("documentoDueDiligences", FetchMode.JOIN);
			// criteria.createAlias("documentoDueDiligences", "documentoDueDiligences", DetachedCriteria.LEFT_JOIN);

			if (professionistaCode != null){
				criteria.createAlias("professionistaEsterno", "legale");
				criteria.add(Restrictions.eq("legale.id", professionistaCode));
			}

			if (statoDueDiligenceCode != null){
				criteria.createAlias("statoDueDiligence", "stato");
				criteria.add(Restrictions.eq("stato.id", statoDueDiligenceCode));
			}

			if (dataAperturaDal != null && !dataAperturaDal.trim().isEmpty()){
				Date dateDal = new SimpleDateFormat("dd/MM/yyyy").parse(dataAperturaDal);
				criteria.add(Restrictions.ge("dataApertura", dateDal));
			}
			if (dataAperturaAl != null && !dataAperturaAl.trim().isEmpty()){
				Date dateAl = new SimpleDateFormat("dd/MM/yyyy").parse(dataAperturaAl);
				criteria.add(Restrictions.le("dataApertura", dateAl));
			}
			if (dataChiusuraDal != null && !dataChiusuraDal.trim().isEmpty()){
				Date dateDal = new SimpleDateFormat("dd/MM/yyyy").parse(dataChiusuraDal);
				criteria.add(Restrictions.ge("dataChiusura", dateDal));
			}
			if (dataChiusuraAl != null && !dataChiusuraAl.trim().isEmpty()){
				Date dateAl = new SimpleDateFormat("dd/MM/yyyy").parse(dataChiusuraAl);
				criteria.add(Restrictions.le("dataChiusura", dateAl));
			}

			out = (List<DueDiligence>)getHibernateTemplate().findByCriteria(criteria);
		}

		return out;
	}


}



