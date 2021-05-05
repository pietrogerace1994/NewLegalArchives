package eng.la.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.ParteCorrelata;
//import eng.la.model.RicercaParteCorrelata;
//import eng.la.model.constants.PartiCorrelateModel;
import eng.la.model.TipoCorrelazione;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità ParteCorrelata</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Parte Correlata.
 * 
 * @author ACER
 */
@Component("parteCorrelataDAO")
public class ParteCorrelataDAOImpl extends HibernateDaoSupport implements ParteCorrelataDAO{
	@Autowired
	public ParteCorrelataDAOImpl(SessionFactory sessionFactory){
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
	public ParteCorrelata inserisci(ParteCorrelata parteCorrelata) throws Throwable {
		// se non c'è data d'inserimento si imposta con quella corrente.
		if (parteCorrelata.getDataInserimento() == null)
			parteCorrelata.setDataInserimento(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().save(parteCorrelata);
		return parteCorrelata;
	}
	
	/**
	 * Metodo di lettura dati ParteCorrelata da base dati, per id.
	 * 
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public ParteCorrelata leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		ParteCorrelata parteCorrelata = (ParteCorrelata) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return parteCorrelata; 
	}

	/**
	 * _TBV : questo metodo non serve verificare.... ( commentato 20160709 )
	 * Metdodo di lettura dati ParteCorrelata da base dati, per il campo 
	 * Codice Fiscale / PIVA..
	 * 
	 * @param codFiscPIva  codice fiscale o piva per lettura occorrenza .
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */
	/*
	@Override
	public ParteCorrelata leggi(String codFisPartIva) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).add( Restrictions.eq("codFisPartIva", codFisPartIva) );
		@SuppressWarnings("unchecked")
		ParteCorrelata parteCorrelata = (ParteCorrelata) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return parteCorrelata;
	}
	*/
	
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
		ParteCorrelata parteCorrelata = leggi(id);
		parteCorrelata.setDataCancellazione(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().update(parteCorrelata);
	}
	
	/**
	 * Ritorna la lista di tutte le occorrenze presenti nella base dati.
	 * 
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<ParteCorrelata> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).addOrder(Order.asc("denominazione"));
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/**
	 * Ritorna la lista di tutte le occorrenze presenti nella base dati.
	 * paginata.
	 * <p>
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<ParteCorrelata> leggi(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).addOrder(Order.asc("denominazione"));
		// da condizionare per visibilità subordinata dall tipo di utente connesso
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( ordinamento == null){
			criteria.addOrder(Order.asc("denominazione"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				criteria.addOrder(Order.asc(ordinamento));
			}else{
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		Long numeroTotaleElementi = conta();
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria,indicePrimoElemento,elementiPerPagina);
		return lista;
	}
	
	@Override
	public List<ParteCorrelata> leggiElenco(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class );
		// da condizionare per visibilità subordinata dall tipo di utente connesso
		//criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( ordinamento == null){
			criteria.addOrder(Order.asc("denominazione"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				criteria.addOrder(Order.asc(ordinamento));
			}else{
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		Long numeroTotaleElementi = conta();
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria,indicePrimoElemento,elementiPerPagina);
		return lista;
	}
	
	
	/**
	 * Metodo che esegue la count delle occorrenze,
	 * presenti nel dbase
	 */
	@Override
	public Long conta() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParteCorrelata.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	/**
	 * Ritorna la lista delle occorrenze attive o non attive presenti nella base dati.
	 * <p>
	 * @param isAttive valore booleano true=attive;false=NoAttive
	 * @return lista
	 * @throws Throwable
	 */
	@Override
	public List<ParteCorrelata> leggi(boolean isAttive) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).addOrder(Order.asc("denominazione"));
		if (isAttive) // Per attive s'intende la data di creazione pari a null.
			criteria.add(Restrictions.isNull("dataCancellazione"));
		else // diversamente non attive data cancellazione diverso da null.
			criteria.add(Restrictions.isNotNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista;
	}

	/**
	 * _TBV ( verificare se è richiesta questo tipo di ricerca ).
	 * 
	 */
	@Override
	public List<ParteCorrelata> cerca(ParteCorrelata parteCorrelata) throws Throwable {
		return null;
	}
	
	/**
	 * Metodo di ricerca ParteCorrelata.
	 * 
	 * @param denominazione
	 * @param codFiscale
	 * @param codPartitaIva
	 * @throws Throwable 
	 */
	@Override
	public List<ParteCorrelata> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).addOrder(Order.asc("denominazione"));

		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( denominazione != null && denominazione.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("denominazione", denominazione, MatchMode.ANYWHERE) ) ;
		}
		
		if( codFiscale != null && codFiscale.trim().length() > 0 ){
			criteria.add( Restrictions.eq("codFiscale", codFiscale) ) ;
		}
		
		if( partitaIva != null && partitaIva.trim().length() > 0 ){
			criteria.add( Restrictions.eq("partitaIva", partitaIva) ) ;
		}
		
		@SuppressWarnings("unchecked")
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria);		
		
		return lista;
	}
	
	@Override
	public List<ParteCorrelata> ricerca(String denominazione, String codFiscale, String partitaIva) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ParteCorrelata.class ).addOrder(Order.asc("denominazione"));
		
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
		List<ParteCorrelata> lista = getHibernateTemplate().findByCriteria(criteria);		
		
		return lista;
	}
	
	/**
	 *
	 * 20160716 - questa operazione per la gestione della transazione verrà eseguita a livello di
	 * service. Quindi sarà un metodo non utilizzato.
	 * 
	 * Metodo di operazione modifica.
	 * Questa operazione esegue : una cancellazione logica della vecchia occorrenza e 
	 * l'inserimento di una nuova con i dati modificati.
	 * 
	 * @param parteCorrelata oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(ParteCorrelata parteCorrelata) throws Throwable {
		getHibernateTemplate().update(parteCorrelata);
	}
	
	//----------------------------------------------------------------------------------- //
	// --- Sezione ricerca come as-is, prelevando il codice dalla vecchia aplicazione --- //
	// ---------------------------------------------------------------------------------- //
	
	 private static final List<String> sigleSocieta = new ArrayList<String>(){/**
		 * 
		 */
		private static final long serialVersionUID = -1371154428066221321L;

	{
		 //ITALIA
		 add(" spa"); add(" s.p.a.");
	     add(" srl"); add(" s.r.l.");
	     add(" sas"); add(" s.a.s."); add(" soc. accomandita semplice");
	     add(" snc"); add(" s.n.c.");
	     add(" sapa"); add(" s.a.p.a"); add (" soc. accomandita p.a.");
	     add(" geie");
	     //GERMANIA
	     add(" gmbh");
	     add(" ag");
	     //FRANCIA
	     add(" sarl");
	     add(" sa");
	     //UK
	     add(" pcl");
	     add(" puc");
	     add(" plc");
	     add(" jsc");
	     //OLANDA
		 add(" bv");add(" b.v.");
		 add(" nv");add(" n.v.");
		 add(" se");add(" s.e.");
		 add(" sce");add(" s.c.e.");
	  }};

	// flag per log delle ricerche
	private Boolean writeLog = Boolean.TRUE;
	
	
	/**
	 * Ricerca parti correlate.
	 * <p>
	 * 
	 */
	@Override
	public HashMap<String, Object> cerca(String nomeUtenteVisualizzato,
										 Boolean isUtenteSegreteriaSocietaria,
										 String denominazione,
										 String codFiscale,
										 String partitaIva,
										 Date data) throws Throwable{

	    List<ParteCorrelata> partiCorList = new ArrayList<ParteCorrelata>(0);

	    Boolean ricercaValida = Boolean.FALSE;
	    Boolean denominazioneValido = Boolean.FALSE;
	    Boolean codiceFiscaleValido = Boolean.FALSE;
	    Boolean partitaIVAValido = Boolean.FALSE;

	    // Rimuove le sigle della forma societaria
	 	String denominazioneSanata= removeSigle(denominazione);
	 	// verifica la presenza dei campi...
	    Boolean INPUT_DENOMINAZIONE_EXIST = 
	    		(null != denominazioneSanata && !denominazioneSanata.isEmpty() && denominazioneSanata.length()>2) ? Boolean.TRUE : Boolean.FALSE;
	    Boolean INPUT_CODFISCALE_EXIST = 
	    		(null != codFiscale && !codFiscale.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
	    Boolean INPUT_PARTITAIVA_EXIST = 
	    		(null != partitaIva && !partitaIva.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
	    Boolean INPUT_DATA_EXIST = 
	    		(null != data) ? Boolean.TRUE : Boolean.FALSE;

	    // si costruisce la stringa SQL
	    StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("SELECT pc.DENOMINAZIONE, pc.COD_FISCALE, pc.PARTITA_IVA, pc.DATA_INSERIMENTO,pc.TIPO_CORRELAZIONE FROM PARTE_CORRELATA_VIEW pc ");
	    

	    /** Denominazione */
	    if (INPUT_DENOMINAZIONE_EXIST) {
	    	
	    	System.out.println("[PARTICORRELATE] stringa di ricerca: " + denominazioneSanata);
	        stringaSql.append("WHERE ");
	        stringaSql.append("("); // per tutte le condizioni in disgiunzione
	        
	        //devo ricavare le prime tre lettere di ogni parola imputata
	        //facendo tanti OR quanti sono i token ottenuti.
	        List<String> denominazioneTokenizzata= tokenizerMethod(denominazioneSanata);
		    if (denominazioneTokenizzata!=null && denominazioneTokenizzata.size()>=1) {
		    	
		    	int count=0;
		        for (String element : denominazioneTokenizzata) {
		        	count=count+1;
		            if (count>1){
		            	stringaSql.append(" OR ");
					}
		            // aggiunge le condizioni per token.
		            stringaSql.append("( lower(pc.TOKEN_1) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_2) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_3) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_4) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_5) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_6) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_7) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_8) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_9) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_10) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_11) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_12) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_13) LIKE '"+element+"%'");
		            stringaSql.append(" OR lower(pc.TOKEN_14) LIKE '"+element+"%'");
		            stringaSql.append(")");
		        } // end for
		        denominazioneValido = Boolean.TRUE;
		    }// end if
	        stringaSql.append(")");
	    } // end if

	    /** Codice Fiscale/Partita IVA */
	    if ( INPUT_CODFISCALE_EXIST ) {
	    	
	    	if ( denominazioneValido ) {
	    		
	    		stringaSql.append(" AND pc.COD_FISCALE = upper('").append(codFiscale).append("')");
	        } else {
	        	
	            stringaSql.append(" WHERE ");
	            stringaSql.append("pc.COD_FISCALE = upper('").append(codFiscale).append("')");
	        }
	        codiceFiscaleValido = Boolean.TRUE;
	    }
	    if ( INPUT_PARTITAIVA_EXIST ) {
	    	
	    	if ( denominazioneValido ) {
	    		
	    		stringaSql.append(" AND pc.PARTITA_IVA = upper('").append(partitaIva).append("')");
	        } else {
	        	
	            stringaSql.append(" WHERE ");
	            stringaSql.append("pc.PARTITA_IVA = upper('").append(partitaIva).append("')");
	        }
	        partitaIVAValido = Boolean.TRUE;
	    }
	    ricercaValida = denominazioneValido | codiceFiscaleValido | partitaIVAValido;
	    
	    /* presenza della data */
	    if( INPUT_DATA_EXIST ) {
	    	
	    	String dataInput = DateFormatUtils.format(data, "dd/MM/yyyy");
	    	if( !ricercaValida ) {
	    		
	    		stringaSql.append(" WHERE ");
	    		stringaSql.append(" TO_DATE('").append(dataInput).append("', 'dd/MM/yyyy') < ").append("NVL(").append("pc.data_cancellazione").append(", TO_DATE('25/09/2099', 'dd/MM/yyyy'))");
	    		stringaSql.append(" AND TO_DATE('").append(dataInput).append("', 'dd/MM/yyyy') >= ").append("pc.data_inserimento");
	        } else {
	        	
	           	stringaSql.append(" AND TO_DATE('").append(dataInput).append("', 'dd/MM/yyyy') < ").append("NVL(").append("pc.data_cancellazione").append(", TO_DATE('25/09/2099', 'dd/MM/yyyy'))");
	        	stringaSql.append(" AND TO_DATE('").append(dataInput).append("', 'dd/MM/yyyy') >= ").append("pc.data_inserimento");
	        }
	    }
	    else{
	    	
	    	stringaSql.append("AND pc.data_cancellazione IS NULL");
	    }
	    
	    stringaSql.append(" order by pc.ID_TIPO_CORRELAZIONE ASC ");
	    // stampa query di test
	    System.out.println("[PARTICORRELATE] DAO - Sql: " + stringaSql.toString());

	    if( ricercaValida || null != data || isUtenteSegreteriaSocietaria /* utente SS */) {
	    	
	    	logger.info("[PARTICORRELATE] DAO - condizioni valide per esecuzione query");
	    	partiCorList = getHibernateTemplate().execute(new HibernateCallback<List<ParteCorrelata>>() {
				@Override
				public List<ParteCorrelata> doInHibernate(Session session) throws HibernateException, SQLException {
					
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					@SuppressWarnings("unchecked")
					List<Object> queryResult = sqlQuery.list();
					System.out.println("queryResult size : "  + queryResult.size());
					List<ParteCorrelata> pcList = new ArrayList<ParteCorrelata>();					
					for (Object row:queryResult){
						Object[] fields = (Object[])row;
						ParteCorrelata pc = new ParteCorrelata();
						pc.setDenominazione((String)fields[0]);
						pc.setCodFiscale((String)fields[1]);
						pc.setPartitaIva((String)fields[2]);
						pc.setDataInserimento((Date)fields[3]);
						TipoCorrelazione tipoCorrelazione = new TipoCorrelazione();
						tipoCorrelazione.setDescrizione((String)fields[4]);
						pc.setTipoCorrelazione(tipoCorrelazione);
						pcList.add(pc);
					}		
					return pcList;
				}
			});
	    	
	    	System.out.println("DAOImpl.cerca.partiCorList :  " + partiCorList.size());
	    }

	    Boolean esitoRicerca= Boolean.FALSE;

	    if (partiCorList.size()==1){
	    	
	    	ParteCorrelata pc = partiCorList.get(0);
			if((removeSigle(pc.getDenominazione()).trim()).equals(denominazioneSanata.toLowerCase()))
				
				esitoRicerca=Boolean.TRUE;
	    } else if (partiCorList.size()>1) {
	    	
	    	ParteCorrelata pcEventuale=null;
	    	for (ParteCorrelata pc : partiCorList) {
	    		
	            if((removeSigle(pc.getDenominazione()).trim()).equals(denominazioneSanata.toLowerCase())) {
	            	
	            	logger.info("OK EXACT MATCH -> "+ pc.toString());
	            	esitoRicerca= Boolean.TRUE;
	            	pcEventuale=pc;
	            	break;
	            } // end if
	    	} // end for

	    	if (esitoRicerca) {
	    		
	    		//RITORNO LA COLLEZIONE CON QUELLA CORRETTA ALL'ULTIMO POSTO E LE PRIME COME SUGGERIMENTI
	    		partiCorList.remove(pcEventuale);
	    		partiCorList.add(pcEventuale);
	    	}
	    } // end if
	    
	    if( writeLog ) {
	    	
	    	List<ParteCorrelata> pcListToHistory = new ArrayList<ParteCorrelata>(esitoRicerca?1:partiCorList.size());
	    	if( esitoRicerca )
	    		pcListToHistory.add( partiCorList.get(partiCorList.size()-1) ); // solo quella esatta (ultima parte correlata)
	    	else
	    		pcListToHistory.addAll(partiCorList); // tutte
	    	
	    }
	    // reset log
	    writeLog = Boolean.TRUE;
	    
	    // prepara una hashmap contente valori di ritorno multipli
	    HashMap<String, Object> hashRet = new HashMap<String, Object>();
	    hashRet.put("partiCorList", partiCorList);
	    hashRet.put("esitoRicerca", esitoRicerca);
	    return hashRet;
	}

	/**
	 * Recupera dalla denominazione in input le stringhe da ricercare impostando dei vincoli.
	 * <p>
	 * @param denominazione
	 * @return
	 */
	private List<String> tokenizerMethod(String denominazione){
	    if(denominazione == null) return null;
	    List<String> list = new ArrayList<String>();
	    if (denominazione.length()>=3){
	    	String[] listIniziale= denominazione.split(" ");
	        for (String i_esima : listIniziale) {
				if(i_esima.length()>=2) // evita token con meno di 2 caratteri
					list.add(i_esima);
				if(list.size()==3) // limita il numero di token a 3
					break;
			}
	    }
	    return list;
	}

	/**
	 * Rimuove dalla denominazione l'eventuale sigla relativa alla forma societaria (spa, srl... ecc.)
	 * <p>
	 * @param denominazione
	 * @return
	 */
	//Rimuove le sigle della forma societaria
	private String removeSigle(String denominazione) {
		String denominazioneSanata="";
		if (denominazione!=null){
			denominazioneSanata=denominazione.toLowerCase();
			for (String sigla : sigleSocieta){
				if (denominazioneSanata.endsWith(sigla)){
					denominazioneSanata= denominazioneSanata.replaceAll(sigla, "");
					break;
				}
			}
		}
		return denominazioneSanata.trim();
	}
}