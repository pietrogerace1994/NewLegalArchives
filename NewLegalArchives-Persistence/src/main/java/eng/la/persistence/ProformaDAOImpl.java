package eng.la.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.SchedaValutazione;
import eng.la.model.StatoProforma;
import eng.la.model.TipoProforma;
import eng.la.model.rest.ParcellaRest;
import eng.la.model.view.TipoProformaView;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil;
import eng.la.util.SpringUtil;

@Component("proformaDAO")
public class ProformaDAOImpl extends HibernateDaoSupport implements ProformaDAO, CostantiDAO {

	@Autowired
	public ProformaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Proforma> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class).addOrder(Order.asc("nomeProforma"));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		criteria.createAlias("statoProforma", "statoProforma"); 
		criteria.add(Restrictions.ne("statoProforma.codGruppoLingua", "BDI" )) ; /*BOZZA DA INVIARE*/
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);
		@SuppressWarnings("unchecked")
		List<Proforma> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public Proforma leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.setFetchMode("RIncaricoProformaSocietas", FetchMode.JOIN);
		criteria.setFetchMode("schedaValutazione", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		Proforma proforma = (Proforma) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return proforma;
	}
 
	 
	public Proforma leggiConPermessi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.setFetchMode("RIncaricoProformaSocietas", FetchMode.JOIN);
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);
		@SuppressWarnings("unchecked")
		Proforma proforma = (Proforma) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return proforma;
	}
	
	@Override
	public List<Proforma> cerca(String nome ) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class).addOrder(Order.asc("nomeProforma"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		 
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nomeProforma", nome, MatchMode.ANYWHERE));
		}
		criteria.createAlias("statoProforma", "statoProforma"); 
		criteria.add(Restrictions.ne("statoProforma.codGruppoLingua", "BDI" )) ; /*BOZZA DA INVIARE*/
		
		
		@SuppressWarnings("unchecked")
		List<Proforma> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Proforma> cerca(List<String> parole) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Proforma.class ).addOrder(Order.asc("nomeProforma"));
		criteria.createAlias("statoProforma", "statoProforma"); 
		criteria.add(Restrictions.ne("statoProforma.codGruppoLingua", "BDI" )); /*BOZZA DA INVIARE*/
		
		if(parole.size()==1) {
			String tok=parole.get(0);

			Criterion crit_dataCanc=Restrictions.isNull("dataCancellazione") ;
			
			Criterion crit_1 = Restrictions.ilike("nomeProforma", tok, MatchMode.ANYWHERE);
			Criterion crit_2 = Restrictions.ilike("note", tok, MatchMode.ANYWHERE);
			Criterion crit_3 = Restrictions.ilike("centroDiCosto", tok, MatchMode.ANYWHERE);
			Criterion crit_4 = Restrictions.ilike("voceDiConto", tok, MatchMode.ANYWHERE);
			
			Disjunction disj=Restrictions.disjunction();
			disj.add(crit_1);
			disj.add(crit_2);
			disj.add(crit_3);
			disj.add(crit_4);
			
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
				
				Criterion crit_1 = Restrictions.ilike("nomeProforma", tok, MatchMode.ANYWHERE);
				Criterion crit_2 = Restrictions.ilike("note", tok, MatchMode.ANYWHERE);
				Criterion crit_3 = Restrictions.ilike("centroDiCosto", tok, MatchMode.ANYWHERE);
				Criterion crit_4 = Restrictions.ilike("voceDiConto", tok, MatchMode.ANYWHERE);
				
				Disjunction disj=Restrictions.disjunction();
				disj.add(crit_1);
				disj.add(crit_2);
				disj.add(crit_3);
				disj.add(crit_4);
				
				Conjunction conj=Restrictions.conjunction();
				conj.add(crit_dataCanc);
				conj.add(disj);
				
				d.add(conj);
			}
			
			criteria.add(d);
		}
		
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_PROFORMA); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Proforma> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public List<Proforma> cerca(String nome, String statoCode, String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione,String fattura,String contabilizzato) throws Throwable {

		Long numeroTotaleElementi = conta(nome, statoCode, nomeFascicolo, nomeIncarico, societaAddebito, dal, al,fattura,contabilizzato);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
  		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class);
		criteria.setFetchMode("RIncaricoProformaSocietas", FetchMode.JOIN);
		criteria.createAlias("RIncaricoProformaSocietas", "RIncaricoProformaSocietas");
		criteria.createAlias("RIncaricoProformaSocietas.incarico", "incarico");
		criteria.createAlias("RIncaricoProformaSocietas.incarico.fascicolo", "fascicolo");
		criteria.createAlias("RIncaricoProformaSocietas.societa", "societa");
	
		if(fattura!=null && fattura.equalsIgnoreCase("SI")){
 			criteria.setFetchMode("RProformaFatturas", FetchMode.JOIN);
 			criteria.createAlias("RProformaFatturas", "RProformaFatturas");
 			criteria.createAlias("RProformaFatturas.fattura", "fattura");
 		}
		
		if(contabilizzato!=null && contabilizzato.equalsIgnoreCase("SI")){
 			criteria.add(Restrictions.isNotNull("fattura.dataRegistrazione"));
 			criteria.add(Restrictions.isNotNull("fattura.nProtocolloFiscale"));
 		}
		if(contabilizzato!=null && contabilizzato.equalsIgnoreCase("NO")){
 			
 			criteria.add(Restrictions.or(Restrictions.isNull("fattura.dataRegistrazione"), Restrictions.isNull("fattura.nProtocolloFiscale")));
 		}
		
	 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nomeProforma"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		criteria.createAlias("statoProforma", "statoProforma"); 
		criteria.add(Restrictions.ne("statoProforma.codGruppoLingua", "BDI" )) ; /*BOZZA DA INVIARE*/
		
		
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataInserimento", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataInserimento", DateUtil.getDataOra(al+" - 23:59:59")));
		}
 
		
		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("fascicolo.nome", nomeFascicolo, MatchMode.ANYWHERE) ) ;
		} 
		
		if( nomeIncarico != null && nomeIncarico.trim().length() > 0 ){ 
			criteria.add( Restrictions.ilike("incarico.nomeIncarico", nomeIncarico, MatchMode.ANYWHERE) ) ;
		}
		
		if( societaAddebito > -1){ 
			criteria.add( Restrictions.eq("societa.id", societaAddebito) ) ;
		}
		
		if( statoCode != null && statoCode.trim().length() > 0 ){
			//criteria.createAlias("statoProforma", "statoProforma"); 
			criteria.add( Restrictions.eq("statoProforma.codGruppoLingua", statoCode ) ) ;
		}
		 
		
		
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeProforma", nome, MatchMode.ANYWHERE) ) ;
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);
		criteria.setProjection(Projections.projectionList()
				  .add(Projections.distinct(Projections.property("id")))
				  .add(Projections.property("id"), "id")
				  .add(Projections.property("dataUltimaModifica"), "dataUltimaModifica")
				  .add(Projections.property("statoProforma"), "statoProforma") 
				  .add(Projections.property("dataInserimento"), "dataInserimento")
				  .add(Projections.property("nomeProforma"), "nomeProforma"))
		
			    .setResultTransformer(Transformers.aliasToBean(Proforma.class));
		  
		@SuppressWarnings("unchecked")
		List<Proforma> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );
		
		List<Proforma> listaRitorno  = null;
		
		if( lista != null ){
			listaRitorno  = new ArrayList<Proforma>();
			int index = 0;
			for( Proforma p : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( p.getId() ));					
				}
			    index++;
			}
		}
		
		return listaRitorno;  	 
	}


	@Override
	public Long conta(String nome, String statoCode, String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al,String fattura,String contabilizzato) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class);
		criteria.createAlias("RIncaricoProformaSocietas", "RIncaricoProformaSocietas");
		criteria.createAlias("RIncaricoProformaSocietas.incarico", "incarico");
		criteria.createAlias("RIncaricoProformaSocietas.incarico.fascicolo", "fascicolo");
		criteria.createAlias("RIncaricoProformaSocietas.societa", "societa");
		
		if(fattura!=null && fattura.equalsIgnoreCase("SI")){
 			criteria.setFetchMode("RProformaFatturas", FetchMode.JOIN);
 			criteria.createAlias("RProformaFatturas", "RProformaFatturas");
 			criteria.createAlias("RProformaFatturas.fattura", "fattura");
 			 
 		}
		
		criteria.createAlias("statoProforma", "statoProforma"); 
		criteria.add( Restrictions.ne("statoProforma.codGruppoLingua", "BDI" )) ; /*BOZZA DA INVIARE*/
		
		if(contabilizzato!=null && contabilizzato.equalsIgnoreCase("SI")){
 			criteria.add(Restrictions.isNotNull("fattura.dataRegistrazione"));
 			criteria.add(Restrictions.isNotNull("fattura.nProtocolloFiscale"));
 		}
		if(contabilizzato!=null && contabilizzato.equalsIgnoreCase("NO")){
 			
 			criteria.add(Restrictions.or(Restrictions.isNull("fattura.dataRegistrazione"), Restrictions.isNull("fattura.nProtocolloFiscale")));
 		}
		
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){ 
			criteria.add( Restrictions.ilike("fascicolo.nome", nomeFascicolo, MatchMode.ANYWHERE) ) ;
		} 
		
		if( nomeIncarico != null && nomeIncarico.trim().length() > 0 ){ 
			criteria.add( Restrictions.ilike("incarico.nomeIncarico", nomeIncarico, MatchMode.ANYWHERE) ) ;
		}
		
		if( societaAddebito > -1){ 
			criteria.add( Restrictions.eq("societa.id", societaAddebito) ) ;
		}
		
		if( statoCode != null && statoCode.trim().length() > 0 ){
		//	criteria.createAlias("statoProforma", "statoProforma"); 
			criteria.add( Restrictions.eq("statoProforma.codGruppoLingua", statoCode ) ) ;
		}
		
		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataInserimento", DateUtil.toDate(dal)));
		}
		
		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataInserimento", DateUtil.getDataOra(al+" - 23:59:59")));
		}
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeProforma", nome, MatchMode.ANYWHERE) ) ;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	@Override
	public void cancellaSchedaValutazione(long schedaId) throws Throwable {
		getHibernateTemplate().delete(leggiScheda(schedaId));
	}
	
	private SchedaValutazione leggiScheda(long schedaId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SchedaValutazione.class).add(Restrictions.eq("id", schedaId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		SchedaValutazione schedaValutazione = (SchedaValutazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return schedaValutazione;
	}

	@Override
	public Proforma inserisci(Proforma vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void modifica(Proforma vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		Proforma vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
		
		if(vo.getRIncaricoProformaSocietas() != null && !vo.getRIncaricoProformaSocietas().isEmpty()){
			
			Set<RIncaricoProformaSocieta> rIncaricoProformaSocietas = vo.getRIncaricoProformaSocietas();
			for(RIncaricoProformaSocieta rIncaricoProformaSocieta : rIncaricoProformaSocietas){
				
				rIncaricoProformaSocieta.setDataCancellazione(new Date());
				getHibernateTemplate().update(rIncaricoProformaSocieta); 
			}
		}
	}
	
	
	@Override
	public void cancellaSocietaProforma(long proformaId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);
		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", proformaId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> incaricoProformaSocieta = (List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);
		if( incaricoProformaSocieta != null && incaricoProformaSocieta.size() > 0 ){  
			getHibernateTemplate().delete(incaricoProformaSocieta.get(0)); 
		}
	}
	 	
	@Override
	public void salvaSocietaProfoma(RIncaricoProformaSocieta incaricoProformaSocieta) throws Throwable {
		getHibernateTemplate().save(incaricoProformaSocieta);
	}
	
	@Override
	public SchedaValutazione salvaSchedaValutazione(SchedaValutazione schedaValutazione) throws Throwable {
		getHibernateTemplate().save(schedaValutazione);
		return schedaValutazione;
	}
	
	@Override
	public SchedaValutazione aggiornaSchedaValutazione(SchedaValutazione schedaValutazione) throws Throwable {
		getHibernateTemplate().update(schedaValutazione);
		return schedaValutazione;
	}
	
	/**
	 * Metodo di lettura dei proforma associati
	 * <p>
	 * @param id: identificativo dell'incarico
	 * @return lista oggetti proforma.
	 * @exception Throwable
	 */
	@Override
	public List<Proforma> leggiProformaAssociatiAIncarico(long id) throws Throwable {
		
		List<Proforma> proformaList = new ArrayList<Proforma>(0);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);
		
		criteria.createAlias("incarico", "incarico");
		criteria.add(Restrictions.eq("incarico.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RIncaricoProformaSocieta relazione:listaRelazione){
				 proformaList.add(relazione.getProforma());
				}	
			 return proformaList;
		 }
	     else
	    	 return null;
	}
	
	
	@Override
	public List<Proforma> leggiProformaAssociatiAIncaricoUnico(long id) throws Throwable {
		
		List<Proforma> proformaList = new ArrayList<Proforma>(0);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);
		
		criteria.createAlias("incarico", "incarico");
		criteria.add(Restrictions.eq("incarico.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RIncaricoProformaSocieta relazione:listaRelazione){
				 proformaList.add(relazione.getProforma());
				}	
			 return proformaList;
		 }
	    else
	    	return null;
	}
	
	
	@Override
	public List<Proforma> leggiProformaAssociatiAIncarico(List<Long> idIncarichi)throws Throwable {
		
		List<Proforma> proformaList = new ArrayList<Proforma>();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);
		
		criteria.createAlias("incarico", "incarico");
		criteria.add(Restrictions.in("incarico.id", idIncarichi));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RIncaricoProformaSocieta relazione:listaRelazione){
				 proformaList.add(relazione.getProforma());
				}	
			 return proformaList;
		 }
	    else
	    	return null;
	}
	
	//DA PROCESSARE & PROCESSATE
	@Override
	public List<Proforma> getListaProformaPerSocieta(boolean processato,long idSocieta) throws Throwable {
		
		List<Proforma> proformaList = new ArrayList<Proforma>();
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct rip.id_proforma ");
		stringaSql.append("from r_incarico_proforma_societa rip ,societa s, proforma p ");
		stringaSql.append("where s.id=rip.id_societa  ");
		stringaSql.append("and s.id=");
		stringaSql.append(idSocieta);
		stringaSql.append(" and p.id=rip.id_proforma ");
		stringaSql.append("and p.id_stato_proforma=(select sp.id from stato_proforma sp where sp.cod_gruppo_lingua='A' and sp.lang='IT') ");
		if(processato)
		stringaSql.append("and p.processato='T' "); //PROCESSATE	 IS NOT NULL
		else
		stringaSql.append("and p.processato='F' "); //DA PROCESSARE  IS NULL
	 
		
		proformaList = getHibernateTemplate().execute(new HibernateCallback<List<Proforma>>() {
			@Override
			public List<Proforma> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = sqlQuery.list();
				List<Proforma> proformaList = new ArrayList<Proforma>();					
				for (BigDecimal id:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( Proforma.class ).add( Restrictions.eq("id", id.longValue()) );
					@SuppressWarnings("unchecked")
					Proforma proforma = (Proforma) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					proformaList.add((Proforma) proforma);
				}		
				return proformaList;
			}
		});
	    if(proformaList.size() > 0)
	    	return proformaList;
	    else
	    	return null;
	}
	 
	//Fascicolo
	public List<Fascicolo> getListaProformaFascicolo(long idProforma) throws Throwable {
		
		List<Fascicolo> proformaFascicolo = new ArrayList<Fascicolo>(0);
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct fc.id ");
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, fascicolo fc ");
		stringaSql.append("where ic.id=rip.id_incarico ");
		stringaSql.append("and rip.id_proforma=");
		stringaSql.append(idProforma);
		stringaSql.append(" and fc.id =ic.id_fascicolo");
		

		proformaFascicolo = getHibernateTemplate().execute(new HibernateCallback<List<Fascicolo>>() {
			@Override
			public List<Fascicolo> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = sqlQuery.list();
				List<Fascicolo> fascicoloList = new ArrayList<Fascicolo>();					
				for (BigDecimal id:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( Fascicolo.class ).add( Restrictions.eq("id", id.longValue()) );
					@SuppressWarnings("unchecked")
					Fascicolo fascicolo = (Fascicolo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					fascicoloList.add((Fascicolo) fascicolo);
				}		
				return fascicoloList;
			}
		});
	    if(proformaFascicolo.size() > 0)
	    	return proformaFascicolo;
	    else
	    	return null;
	}


	// Controparte

	public List<Controparte> getListaProformaControparte(long idProforma) throws Throwable {
	
	List<Controparte> proformaControparte = new ArrayList<Controparte>(0);
	StringBuffer stringaSql = new StringBuffer();
	stringaSql.append("select distinct cnt.id ");
	stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, fascicolo fc,controparte cnt ");
	stringaSql.append("where ic.id=rip.id_incarico ");
	stringaSql.append("and rip.id_proforma=");
	stringaSql.append(idProforma);
	stringaSql.append(" and fc.id =ic.id_fascicolo");
	stringaSql.append(" and cnt.id_fascicolo=ic.id_fascicolo");
	
	proformaControparte = getHibernateTemplate().execute(new HibernateCallback<List<Controparte>>() {
		@Override
		public List<Controparte> doInHibernate(Session session) throws HibernateException, SQLException {
			SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
			@SuppressWarnings("unchecked")
			List<BigDecimal> queryResult = sqlQuery.list();
			List<Controparte> controparteList = new ArrayList<Controparte>();					
			for (BigDecimal id:queryResult){
				DetachedCriteria criteria = DetachedCriteria.forClass( Controparte.class ).add( Restrictions.eq("id", id.longValue()) );
				@SuppressWarnings("unchecked")
				Controparte controparte = (Controparte) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				controparteList.add((Controparte) controparte);
			}		
			return controparteList;
			}
		});
	    if(proformaControparte.size() > 0)
	    	return proformaControparte;
	    else
	    	return null;
 }	
	

	// Nazione

	public List<Nazione> getListaProformaNazione(long idProforma) throws Throwable {
		
		List<Nazione> proformaNazione = new ArrayList<Nazione>(0);
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct nz.id ");
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, fascicolo fasc, nazione nz ");
		stringaSql.append("where ic.id=rip.id_incarico ");
		stringaSql.append("and fasc.id=ic.id_fascicolo ");
		stringaSql.append("and rip.id_proforma=");
		stringaSql.append(idProforma);
		stringaSql.append(" and nz.id=fasc.id_nazione");
		
		proformaNazione = getHibernateTemplate().execute(new HibernateCallback<List<Nazione>>() {
			@Override
			public List<Nazione> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = sqlQuery.list();
				List<Nazione> nazioneList = new ArrayList<Nazione>();					
				for (BigDecimal id:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class ).add( Restrictions.eq("id", id.longValue()) );
					@SuppressWarnings("unchecked")
					Nazione nazione = (Nazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					nazioneList.add((Nazione) nazione);
				}		
				return nazioneList;
			}
		});
	    if(proformaNazione.size() > 0)
	    	return proformaNazione;
	    else
	    	return null;
	}	


	//ProfessionistaEsterno
	
	public List<ProfessionistaEsterno> getListaProformaProfessionistaEsterno(long idProforma) throws Throwable {
		
		List<ProfessionistaEsterno> proformaPEsterno = new ArrayList<ProfessionistaEsterno>(0);
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct pre.id ");
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, professionista_esterno pre ");
		stringaSql.append("where ic.id=rip.id_incarico ");
		stringaSql.append("and rip.id_proforma=");
		stringaSql.append(idProforma);
		stringaSql.append(" and pre.id =ic.id_professionista_esterno");

		proformaPEsterno = getHibernateTemplate().execute(new HibernateCallback<List<ProfessionistaEsterno>>() {
			@Override
			public List<ProfessionistaEsterno> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				@SuppressWarnings("unchecked")
				List<BigDecimal> queryResult = sqlQuery.list();
				List<ProfessionistaEsterno> professionistaList = new ArrayList<ProfessionistaEsterno>();					
				for (BigDecimal id:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).add( Restrictions.eq("id", id.longValue()) );
					@SuppressWarnings("unchecked")
					ProfessionistaEsterno professionista = (ProfessionistaEsterno) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					professionistaList.add((ProfessionistaEsterno) professionista);
				}		
				return professionistaList;
			}
		});
	    if(proformaPEsterno.size() > 0)
	    	return proformaPEsterno;
	    else
	    	return null;
	}

	@Override
	public Proforma leggiSenzaHibernate(long id) throws Throwable {
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
  			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement("select * from Proforma where id = "+ id);
			rs = st.executeQuery();
			if( rs.next() ){
				Proforma vo = new Proforma();
				vo.setAnnoEsercizioFinanziario(rs.getBigDecimal("ANNO_ESERCIZIO_FINANZIARIO"));
				vo.setAutorizzatore(rs.getString("autorizzatore"));
				vo.setCentroDiCosto(rs.getString("CENTRO_DI_COSTO"));
				vo.setCpa( rs.getBigDecimal("cpa"));
				vo.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
				vo.setDataComposizione( rs.getTimestamp("data_composizione"));
				vo.setDataUltimaModifica(rs.getTimestamp("data_ultima_modifica"));
				vo.setDataInserimento( rs.getTimestamp("data_inserimento"));
				vo.setId(id);
				vo.setDataInvioAmministrativo(rs.getTimestamp("DATA_INVIO_AMMINISTRATIVO"));
				vo.setDataProcessamento( rs.getTimestamp("data_processamento"));
				vo.setNote(rs.getString("note"));
				vo.setDataRichAutorizzazione(rs.getTimestamp("DATA_RICH_AUTORIZZAZIONE"));
				vo.setDiritti( rs.getBigDecimal("diritti"));
				vo.setEsitoVerificaProforma(rs.getString("ESITO_VERIFICA_PROFORMA"));
				vo.setNomeProforma(rs.getString("nome_proforma")); 
				vo.setNumero(rs.getString("numero"));
				vo.setOnorari(rs.getBigDecimal("onorari") );
				vo.setProcessato(rs.getString("processato"));
				vo.setSpeseImponibili(rs.getBigDecimal("spese_imponibili"));
				vo.setSpeseNonImponibili(rs.getBigDecimal("spese_non_imponibili"));
				vo.setTotaleAutorizzato( rs.getBigDecimal("totale_autorizzato"));
				vo.setTotaleImponibile(rs.getBigDecimal("totale_imponibile"));
				vo.setUltimo(rs.getString("ultimo"));
				vo.setUtenteProcessamento(rs.getString("utente_processamento"));
				vo.setVoceDiConto(rs.getString("voce_di_conto")); 
				vo.setStatoProforma( leggiStatoProformaSenzaHibernate( rs.getLong("ID_STATO_PROFORMA"), c) );
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

	private StatoProforma leggiStatoProformaSenzaHibernate(long idStato, Connection c) {
		PreparedStatement st = null;				
		ResultSet rs = null;
				
		try{
			st = c.prepareStatement("select * from stato_proforma where id = "+ idStato);		
			rs = st.executeQuery();
			if( rs.next() ){
				StatoProforma vo = new StatoProforma();
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
	
	//Per Ordinamento
	public List<ParcellaRest> getParcellaRest(boolean processato,long idSocieta,String colSort,String sort) throws Throwable {
		
		StringBuffer stringaSql = new StringBuffer();
		stringaSql.append("select distinct rip.id_proforma,p.nome_proforma as titolo,p.centro_di_costo as centro_di_costo, p.voce_di_conto as voce_di_conto, ");
		//paese
		stringaSql.append("(select distinct nz.descrizione ");
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, fascicolo fasc, nazione nz  ");
		stringaSql.append("where ic.id=rip.id_incarico  ");
		stringaSql.append("and fasc.id=ic.id_fascicolo ");
		stringaSql.append("and rip.id_proforma=p.id ");
		stringaSql.append("and nz.id=fasc.id_nazione and ROWNUM <=1) as paese, ");
		//controparte
		stringaSql.append("(select distinct cnt.nome "); 
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, fascicolo fc,controparte cnt "); 
		stringaSql.append("where ic.id=rip.id_incarico "); 
		stringaSql.append("and rip.id_proforma=p.id ");
		stringaSql.append("and fc.id =ic.id_fascicolo ");
		stringaSql.append("and cnt.id_fascicolo=ic.id_fascicolo and ROWNUM <=1) as controparte, ");
		//legaleEsterno
		stringaSql.append("(select distinct pre.nome ||' '|| pre.cognome ");
		stringaSql.append("from incarico ic,r_incarico_proforma_societa rip, professionista_esterno pre "); 
		stringaSql.append("where ic.id=rip.id_incarico "); 
		stringaSql.append("and rip.id_proforma=p.id ");
		stringaSql.append("and pre.id =ic.id_professionista_esterno and ROWNUM <=1) as legaleEsterno, ");
		stringaSql.append("p.totale_autorizzato as totale_autorizzato, ");
		//valuta  
		stringaSql.append("(select tp.nome from tipo_valuta tp where tp.id=p.id_valuta) as valuta , ");
		stringaSql.append("p.data_inserimento as data_inserimento ,p.data_autorizzazione as data_autorizzazione ");

		stringaSql.append("from r_incarico_proforma_societa rip ,societa s, proforma p "); 
		stringaSql.append("where s.id=rip.id_societa ");  
		stringaSql.append("and s.id=");
		stringaSql.append(idSocieta);
		stringaSql.append(" and p.id=rip.id_proforma "); 
		stringaSql.append("and p.id_stato_proforma=(select sp.id from stato_proforma sp where sp.cod_gruppo_lingua='A' and sp.lang='IT') "); 
		if(processato)
		stringaSql.append("and p.processato='T' "); //PROCESSATE	 IS NOT NULL
		else
		stringaSql.append("and p.processato='F' "); //DA PROCESSARE  IS NULL
		
		String sortOrder="asc",columnSort="data_inserimento";
		if(sort!=null){
			sortOrder=sort;	
		}
		if(sort!=null){
			columnSort=colSort;	
		}
		stringaSql.append("order by ");
		stringaSql.append(columnSort);
		stringaSql.append(" ");
		stringaSql.append(sortOrder);
		
		
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ParcellaRest> result=null;
		try{
  			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement(stringaSql.toString());
			rs = st.executeQuery();
			result=new ArrayList<ParcellaRest>();
			if(rs!=null)
			while(rs.next()){
				
			ParcellaRest vo = new ParcellaRest();
			vo.setId(rs.getLong("id_proforma"));
		    vo.setTitolo(rs.getString("titolo"));
		    vo.setCentrodicosto(rs.getString("centro_di_costo"));
		    vo.setDataautorizzazione(rs.getTimestamp("data_autorizzazione").toString());
		    vo.setDatainserimento(rs.getTimestamp("data_inserimento").toString());
		    vo.setTotale(rs.getBigDecimal("totale_autorizzato").toString());
		    vo.setValuta(rs.getString("valuta"));
		    vo.setVoceconto(rs.getString("voce_di_conto"));
			vo.setControparte(rs.getString("controparte"));
			vo.setLegaleesterno(rs.getString("legaleEsterno"));
			vo.setPaese(rs.getString("paese"));
			result.add(vo);
			 
			}
			return result;
			
		}catch (SQLException ex) {
         
			ex.printStackTrace();
				
		} catch(Throwable e){
			e.printStackTrace();
		}finally{
			try { if( rs != null ) rs.close();}catch(Throwable e){}
			try { if( st != null ) st.close();}catch(Throwable e){}
			try { if( c != null ) c.close();}catch(Throwable e){}
		}
		return null;
	}

	@Override
	public void inserisciDataInvioAmministrativo(long idEntita, Date date) throws Throwable {
		Proforma vo = leggi(idEntita);
		vo.setDataInvioAmministrativo(date);
		getHibernateTemplate().update(vo); 
	}				
	
	public RProformaFattura getRProformaFattura(long idProforma) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(RProformaFattura.class);	
		
		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", idProforma));

		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		
		@SuppressWarnings("unchecked")
		RProformaFattura pFattura =(RProformaFattura) DataAccessUtils.uniqueResult(getHibernateTemplate(). findByCriteria(criteria));	
		
		 if(pFattura!=null){
			 return pFattura;
		 }
	    else
	    	return null;
		}
	
	@Override
	public Integer checkStatusInviata(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaValutazione.class );
		criteria.add(Restrictions.eq("id", id));

		@SuppressWarnings("unchecked")
		SchedaValutazione schedaValutazione = (SchedaValutazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return schedaValutazione.getInviata();
	}
	
	@Override
	public Integer checkFile(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaValutazione.class );

		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNotNull("returnFile"));
		criteria.add(Restrictions.isNotNull("returnFileName"));

		@SuppressWarnings("unchecked")
		List<SchedaValutazione> lista = getHibernateTemplate().findByCriteria(criteria);
		

		return lista.size();
	}
	
	@Override
	public List<TipoProformaView> getListaTipoProforma(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProforma.class)
				.add( Restrictions.eq("lang", lingua) )
				.addOrder(Order.asc("id"))
				.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipoProforma> lista = getHibernateTemplate().findByCriteria(criteria);
		List<TipoProformaView> listaView = new ArrayList<TipoProformaView>(); 
		
		for(TipoProforma tipo : lista){
			TipoProformaView tipoView = new TipoProformaView();
			tipoView.setVo(tipo);
			listaView.add(tipoView);
		}
		
		return listaView;
	}

	@Override
	public TipoProforma leggiTipoProforma(Long idTipoProforma) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoProforma.class).add(Restrictions.eq("id", idTipoProforma));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		@SuppressWarnings("unchecked")
		TipoProforma proforma = (TipoProforma) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return proforma;
	}
	
	
	
}
