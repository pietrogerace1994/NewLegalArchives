package eng.la.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Atto;
import eng.la.model.CategoriaAtto;
import eng.la.model.EsitoAtto;
import eng.la.model.StatoAtto;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil;
import eng.la.util.SpringUtil;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità ParteCorrelata</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità Parte Correlata.
 * 
 * @author ACER
 */

@Component("attoDAO")
public class AttoDAOImpl extends HibernateDaoSupport implements AttoDAO{
	@Autowired
	public AttoDAOImpl(SessionFactory sessionFactory){
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
	public Atto inserisci(Atto vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Metodo di lettura dati Atto da base dati, per id.
	 * 
	 * @param id id dell'occorrenza da leggere.
	 * @return ritorna l'oggetto model popolato con i dati richiesti.
	 * @exception Throwable
	 */	
	@Override
	public Atto leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class ).add( Restrictions.eq("id", id) );
		criteria.setFetchMode("autorizzazioni", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		Atto vo = (Atto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	public Atto leggiConPermessi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class ).add( Restrictions.eq("id", id) );
		criteria.setFetchMode("autorizzazioni", FetchMode.JOIN);
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 
		@SuppressWarnings("unchecked")
		Atto vo = (Atto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
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
		Atto vo = leggi(id);
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
	public List<Atto> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class ).addOrder(Order.desc("id"));
		@SuppressWarnings("unchecked")
		List<Atto> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	/**
	 * _TBV ( verificare se è richiesta questo tipo di ricerca ).
	 * 
	 */
	@Override
	public List<Atto> cerca(Atto vo) throws Throwable {
		return null;
	}

	/**
	 * Metodo di operazione modifica.
	 * 
	 * @param atto oggetto model con i dati da modificare.
	 * @exception Throwable
	 */
	@Override
	public void modifica(Atto atto) throws Throwable {
		getHibernateTemplate().update(atto);
	}	



	public Atto getAttoPerNumeroProtocollo()  throws Throwable{

		String queryString = "SELECT a.numero_protocollo"
				+ "  FROM atto a"
				+ "  WHERE a.data_creazione= ("
				+ "  SELECT MAX(data_creazione)"
				+ "  FROM atto"
				+ "  )";


		Object attoNumProt=getHibernateTemplate().execute(new HibernateCallback<Object>(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(queryString); 
				Object queryResult = sqlQuery.uniqueResult();
				return queryResult;

			}


		});

		if(attoNumProt!=null){
			Atto atto = new Atto();		
			atto.setNumeroProtocollo(attoNumProt.toString());
			return atto;
		}else{
			return null;
		}

	}




	@Override
	public List<CategoriaAtto> listaCategoriaAtto(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaAtto.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<CategoriaAtto> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 

	}

	@Override
	public List<EsitoAtto> listaEsitoAtto(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( EsitoAtto.class ).addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<EsitoAtto> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 

	}

	public CategoriaAtto getCategoria(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaAtto.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		CategoriaAtto vo = (CategoriaAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	@Override
	public List<Atto> getCercaAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto,
			long idSocieta, String tipoAtto,int elementiPerPagina, int numeroPagina,String order,  boolean flagAltriUffici, boolean flagValida) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(Atto.class ); 
		if(order!=null && order.equalsIgnoreCase("asc"))
			criteria.addOrder(Order.asc("dataCreazione"));	
		else
			criteria.addOrder(Order.desc("dataCreazione"));


		criteria.add(Restrictions.isNull("fascicolo"));	

		if( (dal != null && dal.trim().length()>1) && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( (al != null && al.trim().length()>1) && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.toDate(al)));
		}

		if(numeroProtocollo!=null && numeroProtocollo.trim().length()>1){
			criteria.add(Restrictions.ilike("numeroProtocollo", numeroProtocollo, MatchMode.ANYWHERE));

		}
		if( tipoAtto!=null && tipoAtto.trim().length()>1){

			criteria.add(Restrictions.ilike("tipoAtto", tipoAtto, MatchMode.ANYWHERE));
		}

		if( idCategoriaAtto >  0 ){
			criteria.createAlias("categoriaAtto", "categoriaAtto"); 
			criteria.add(Restrictions.eq("categoriaAtto.id", idCategoriaAtto));
		}

		if( idSocieta >  0 ){
			criteria.createAlias("societa", "societa"); 
			criteria.add(Restrictions.eq("societa.id", idSocieta));
		} 
		
		criteria.createAlias("statoAtto", "statoAtto", DetachedCriteria.INNER_JOIN);
		
		if(flagAltriUffici)
			criteria.add(Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI));
		else
			criteria.add(Restrictions.ne("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI));
		
		
		
		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = contaAllSerch(dal, al, numeroProtocollo, idCategoriaAtto, idSocieta, tipoAtto, flagAltriUffici, flagValida);	
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);

		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}


		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataCreazione"), "dataCreazione") )
		.setResultTransformer(Transformers.aliasToBean(Atto.class)); 

		List<Atto> lista = null;
		
		/**
		 * Acquisisco la lista di atti a seconda della richiesta
		 * @author MASSIMO CARUSO
		 */
		if(flagValida){
			lista = getAttiDaValidare(indicePrimoElemento, elementiPerPagina);
		}else{
			criteria.add(Restrictions.ne("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_DA_VALIDARE));
			lista = (List<Atto>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		}
		
		List<Atto> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<Atto>();
			int index = 0;
			for( Atto f : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add(leggi(f.getId()));					
				}
				index++;
			}
		}

		return listaRitorno;


	}

	@Override
	public List<Atto> cerca(List<String> parole) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Atto.class ).addOrder(Order.asc("tipoAtto"));

		if(parole.size()==1) {
			String tok=parole.get(0);

			Criterion crit_dataCanc=Restrictions.isNull("dataCancellazione") ;

			Criterion crit_1 = Restrictions.ilike("numeroProtocollo", tok, MatchMode.ANYWHERE);
			Criterion crit_2 = Restrictions.ilike("parteNotificante", tok, MatchMode.ANYWHERE);
			Criterion crit_3 = Restrictions.ilike("foroCompetente", tok, MatchMode.ANYWHERE);
			Criterion crit_4 = Restrictions.ilike("tipoAtto", tok, MatchMode.ANYWHERE);
			Criterion crit_5 = Restrictions.ilike("note", tok,MatchMode.ANYWHERE);

			Disjunction disj=Restrictions.disjunction();
			disj.add(crit_1);
			disj.add(crit_2);
			disj.add(crit_3);
			disj.add(crit_4);
			disj.add(crit_5);

			Conjunction conj=Restrictions.conjunction();
			conj.add(crit_dataCanc);
			conj.add(disj);

			criteria.add(conj);

		}
		else if(parole.size()>1) 
		{

			Disjunction d=Restrictions.disjunction();

			for(int i=0;i<parole.size();i++)
			{
				String tok=parole.get(i);

				Criterion crit_dataCanc=Restrictions.isNull("dataCancellazione") ;

				Criterion crit_1 = Restrictions.ilike("numeroProtocollo", tok, MatchMode.ANYWHERE);
				Criterion crit_2 = Restrictions.ilike("parteNotificante", tok, MatchMode.ANYWHERE);
				Criterion crit_3 = Restrictions.ilike("foroCompetente", tok, MatchMode.ANYWHERE);
				Criterion crit_4 = Restrictions.ilike("tipoAtto", tok, MatchMode.ANYWHERE);
				Criterion crit_5 = Restrictions.ilike("note", tok,MatchMode.ANYWHERE);

				Disjunction disj=Restrictions.disjunction();
				disj.add(crit_1);
				disj.add(crit_2);
				disj.add(crit_3);
				disj.add(crit_4);
				disj.add(crit_5);

				Conjunction conj=Restrictions.conjunction();
				conj.add(crit_dataCanc);
				conj.add(disj);

				d.add(conj);
			}

			criteria.add(d);
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Atto> lista = (List<Atto>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}


	/**
	 * Aggiunta del flag per la gestione degli atti da validare
	 * @author MASSIMO CARUSO 
	 */
	public Long contaAllSerch(String dal, String al, String numeroProtocollo, long idCategoriaAtto,
			long idSocieta, String tipoAtto, boolean flagAltriUffici, boolean flagValida)  throws Throwable{
		
		if(flagValida)
			return getNumeroAttiDaValidare();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Atto.class);
		criteria.add(Restrictions.isNull("fascicolo"));

		
		if( (dal != null && dal.trim().length()>1) && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( (al != null && al.trim().length()>1) && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.toDate(al)));
		}

		if(numeroProtocollo!=null && numeroProtocollo.trim().length()>1){
			criteria.add(Restrictions.ilike("numeroProtocollo", numeroProtocollo, MatchMode.ANYWHERE)); 

		}
		if( tipoAtto!=null && tipoAtto.trim().length()>1){

			criteria.add(Restrictions.ilike("tipoAtto", tipoAtto, MatchMode.ANYWHERE));
		}

		if( idCategoriaAtto >  0 ){
			criteria.createAlias("categoriaAtto", "categoriaAtto"); 
			criteria.add(Restrictions.eq("categoriaAtto.id", idCategoriaAtto));
		}

		if( idSocieta >  0 ){
			criteria.createAlias("societa", "societa"); 
			criteria.add(Restrictions.eq("societa.id", idSocieta));
		}
		
		criteria.createAlias("statoAtto", "statoAtto", DetachedCriteria.INNER_JOIN);
		
		if(flagAltriUffici)
			criteria.add(Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI));
		else
			criteria.add(Restrictions.ne("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI));

		/**
		 * Rimuovo dal conteggio gli atti da validare
		 * @author MASSIMO CARUSO
		 */
		criteria.add(Restrictions.ne("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_DA_VALIDARE));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO);  

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));


		
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}





	@Override
	public StatoAtto getStatoAtto(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoAtto.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		StatoAtto vo = (StatoAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 	}

	public StatoAtto getStatoAttoPerLingua(String lang, String cod_gruppo_lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoAtto.class )
				.add( Restrictions.eq("lang", lang) )
				.add( Restrictions.eq("codGruppoLingua", cod_gruppo_lingua) );
		@SuppressWarnings("unchecked")
		StatoAtto vo = (StatoAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 	}

	@Override
	public CategoriaAtto getCategoriaPreLingua(String lang, String cod_gruppo_lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaAtto.class )
				.add( Restrictions.eq("lang", lang) )
				.add( Restrictions.eq("codGruppoLingua", cod_gruppo_lingua) );
		@SuppressWarnings("unchecked")
		CategoriaAtto vo = (CategoriaAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	@Override
	public Atto leggiSenzaHibernate(long id) throws Throwable {
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement("select * from ATTO where id = "+ id);
			rs = st.executeQuery();
			if( rs.next() ){
				Atto vo = new Atto();
				vo.setCreatoDa(rs.getString("creato_da"));
				vo.setDataCreazione(rs.getTimestamp("data_creazione"));
				vo.setDataNotifica(rs.getTimestamp("data_notifica"));
				vo.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
				vo.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
				vo.setDataUdienza(rs.getTimestamp("data_udienza"));
				vo.setDataUltimaModifica(rs.getTimestamp("data_ultima_modifica"));
				vo.setDestinatario(rs.getString("destinatario"));
				vo.setId(id);
				vo.setEmailInvioAltriUffici(rs.getString("EMAIL_INVIO_ALTRI_UFFICI"));
				vo.setForoCompetente(rs.getString("foro_competente"));
				vo.setNote(rs.getString("note"));
				vo.setNumeroProtocollo(rs.getString("numero_protocollo"));
				vo.setOwner(rs.getString("owner"));
				vo.setParteNotificante(rs.getString("parte_notificante"));
				vo.setRilevante(rs.getString("rilevante")); 
				vo.setTipoAtto(rs.getString("tipo_atto"));
				vo.setUnitaLegInvioAltriUffici(rs.getString("UNITA_LEG_INVIO_ALTRI_UFFICI") );
				vo.setUtenteInvioAltriUffici(rs.getString("UTENTE_INVIO_ALTRI_UFFICI"));
				vo.setStatoAtto( leggiStatoAttoSenzaHibernate( rs.getLong("ID_STATO_ATTO"), c) );
				return vo;
			}

		}catch(Throwable e){
			e.printStackTrace();
		}finally{
			try { if( rs != null ) rs.close();}catch(Throwable e){}
			try { if( st != null ) st.close();}catch(Throwable e){}
			try { if( c != null ) c.close();}catch(Throwable e){}
		}
		return null;
	}

	private StatoAtto leggiStatoAttoSenzaHibernate(long idStato, Connection c) {
		PreparedStatement st = null;				
		ResultSet rs = null;

		try{
			st = c.prepareStatement("select * from stato_atto where id = "+ idStato);		
			rs = st.executeQuery();
			if( rs.next() ){
				StatoAtto vo = new StatoAtto();
				vo.setId(idStato);
				vo.setDescrizione(rs.getString("descrizione"));
				vo.setCodGruppoLingua(rs.getString("cod_gruppo_lingua"));
				return vo;
			}
		}catch(Throwable e){
			e.printStackTrace();
		}finally{
			try { if( rs != null ) rs.close();}catch(Throwable e){}
			try { if( st != null ) st.close();}catch(Throwable e){} 
		}
		return null;
	}

	public List<StatoAtto> getListaStatoAttoPerLingua(String lang) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoAtto.class )
				.add( Restrictions.eq("lang", lang) );
		@SuppressWarnings("unchecked")
		List<StatoAtto> vo = (List<StatoAtto>) getHibernateTemplate().findByCriteria(criteria) ;		
		return vo; 	}

	//solo introduttivi - in corso d'opera, finali
	@SuppressWarnings("unchecked")
	@Override
	public List<Atto> reportingAtti(Object[] params) throws Throwable {

		Object attoSocieta=null,
				attoDestinatario=null,
				tipoAttoGiudiziario=null,
				attoStatoAtto=null,
				data_notifica_da=null,
				data_notifica_a=null,
				associatoAfascicolo=null;

		if(params!=null && params.length>6){
			attoSocieta=params[0];
			attoDestinatario=params[1];
			tipoAttoGiudiziario=params[2];
			attoStatoAtto=params[3];
			data_notifica_da=params[4];
			data_notifica_a=params[5];
			associatoAfascicolo=params[6];
		}

		Date dateDa=null,dateA=null;
		if(data_notifica_da!=null)
			dateDa= DateUtil.toDate(data_notifica_da.toString());
		if(data_notifica_a!=null)
			dateA=DateUtil.toDate(data_notifica_a.toString());


		DetachedCriteria criteria = DetachedCriteria.forClass(Atto.class );
		criteria.addOrder(Order.desc("id"));
		if(associatoAfascicolo!=null && associatoAfascicolo.toString().equalsIgnoreCase("SI"))
			criteria.add(Restrictions.isNotNull("fascicolo"));	
		if(associatoAfascicolo!=null && (associatoAfascicolo.toString().equalsIgnoreCase("NO") || associatoAfascicolo.toString().trim().equalsIgnoreCase("")))
			criteria.add(Restrictions.isNull("fascicolo"));	

		if(dateDa != null ){
			criteria.add(Restrictions.ge("dataCreazione", dateDa));
		}

		if(dateA != null){
			criteria.add(Restrictions.lt("dataCreazione", dateA));
		}

		if(tipoAttoGiudiziario!=null){
			criteria.add(Restrictions.ilike("tipoAtto", tipoAttoGiudiziario.toString(), MatchMode.ANYWHERE)); 
		}

		if(attoDestinatario!=null){
			criteria.add(Restrictions.in("destinatario", (List<String>)attoDestinatario));
		}

		if( attoStatoAtto!=null && !attoStatoAtto.toString().equalsIgnoreCase("TUTTI")){
			criteria.createAlias("statoAtto", "statoAtto"); 
			criteria.add(Restrictions.eq("statoAtto.codGruppoLingua", attoStatoAtto));
			criteria.add(Restrictions.eq("statoAtto.lang", "IT"));
		}
		//QUI
		if( attoSocieta!=null){
			criteria.createAlias("societa", "societa"); 
			criteria.add(Restrictions.in("societa.id", (List<Long>)attoSocieta));
		}
		criteria.createAlias("categoriaAtto", "categoriaAtto"); 			//introduttivi-in corso d'opera, finali
		criteria.add(Restrictions.in("categoriaAtto.codGruppoLingua", new Object[]{"TPAT_1","TPAT_2","TPAT_3"}));
		criteria.add(Restrictions.eq("categoriaAtto.lang", "IT"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Atto> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

	@Override
	public EsitoAtto getEsito(long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass( EsitoAtto.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		EsitoAtto vo = (EsitoAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}
	
	@Override
	public EsitoAtto getEsitoByCode(String code, String lingua) {
		DetachedCriteria criteria = DetachedCriteria.forClass( EsitoAtto.class )
				.add( Restrictions.eq("codGruppoLingua", code) )
				.add( Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		EsitoAtto vo = (EsitoAtto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	@Override
	public Atto getAttoPerNumeroProtocollo(String numeroProtocollo) {
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class ).add( Restrictions.like("numeroProtocollo", numeroProtocollo) );
		@SuppressWarnings("unchecked")
		Atto vo = (Atto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

	
	/**
	 * Rimuove l'atto richiesto
	 * @author MASSIMO CARUSO
	 * @param id_atto l'id dell'atto da rimuovere
	 * @throws Throwable
	 */
	public void removeAtto(long id_atto) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class );
		criteria.add(Restrictions.eq("id", id_atto));
		List<Atto> to_delete = (List<Atto>)getHibernateTemplate().findByCriteria(criteria);
		if(to_delete != null && to_delete.size() == 1){
			getHibernateTemplate().delete(to_delete.get(0));
		}else{
			throw new Throwable("Atto con id "+id_atto+" non presente");
		}
	}
	
	/**
	 * Restituisce un sottoinsieme finito di atti da validare o assegnati, definendo
	 * l'indice del primo elemento ed il numero di elementi da restituire.
	 * La lista viene costruita partendo dal risultato in posizione [inizio]
	 * fino al risultato in posizione [inizio + elementi].
	 * @author MASSIMO CARUSO
	 * @param inizio l'indice del primo elemento
	 * @param elementi il numero di elementi da restituire
	 * @return la lista di atti da validare di dimensione [elementi] a partire dall'elemento in posizione [inizio]
	 */
	public List<Atto> getAttiDaValidare(int inizio, int elementi) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class );
		criteria.createAlias("statoAtto", "statoAtto", DetachedCriteria.INNER_JOIN);
		Criterion crit_1 = Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_DA_VALIDARE);
		Criterion crit_2 = Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.PROTOCOLLO_ATTO_STATO_ASSEGNATO);
		criteria.add(Restrictions.or(crit_1, crit_2));
		List<Atto> to_return = (List<Atto>)getHibernateTemplate().findByCriteria(criteria,inizio,inizio+elementi);
		return to_return;
	}
	
	/**
	 * Restituisce il numero totale di atti da validare
	 * @author MASSIMO CARUSO
	 * @return il numero di atti da validare
	 */
	public Long getNumeroAttiDaValidare() throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class );
		criteria.createAlias("statoAtto", "statoAtto", DetachedCriteria.INNER_JOIN);
		Criterion crit_1 = Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.ATTO_STATO_DA_VALIDARE);
		Criterion crit_2 = Restrictions.eq("statoAtto.codGruppoLingua", CostantiDAO.PROTOCOLLO_ATTO_STATO_ASSEGNATO);
		criteria.add(Restrictions.or(crit_1, crit_2));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO);  
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long to_return = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return to_return;
	}
	
	/**
	 * Ricerca un atto riservato attraverso il documento associato.
	 * @param id l'id del documento associato.
	 * @return l'atto ricercato, null altrimenti
	 * @throws Throwable
	 * @author MASSIMO CARUSO
	 */
	@Override
	public Atto getAttoRiservatoByIdDocumento(String id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( Atto.class );
		criteria.createAlias("documento", "documento", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("documento.id", Long.parseLong(id)));
		@SuppressWarnings("unchecked")
		Atto vo = (Atto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return vo; 
	}

}