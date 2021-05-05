package eng.la.persistence;

import java.net.URLDecoder;
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

import eng.la.model.Acconti;
import eng.la.model.Bonus;
import eng.la.model.Documento;
import eng.la.model.Fascicolo;
import eng.la.model.Fattura;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.ListaRiferimento;
import eng.la.model.NotaPropIncarico;
import eng.la.model.Procura;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.StatoIncarico;
import eng.la.model.TipoValuta;
import eng.la.model.VerificaAnticorruzione;
import eng.la.model.VerificaPartiCorrelate;
import eng.la.model.custom.Stanziamenti;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.HibernateDaoUtil;
import eng.la.util.SpringUtil;

@Component("incaricoDAO")
public class IncaricoDAOImpl extends HibernateDaoSupport implements IncaricoDAO, CostantiDAO{


	@Autowired
	public IncaricoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Incarico> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("ragioneSociale"));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public Incarico leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		@SuppressWarnings("unchecked")
		Incarico incarico = (Incarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return incarico; 
	}
	
	@Override
	public Incarico leggiTutti(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		@SuppressWarnings("unchecked")
		Incarico incarico = (Incarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return incarico; 
	}
	
	@Override
	public List<Incarico> cerca(String nome, long professionistaEsternoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("nomeIncarico"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		if( professionistaEsternoId > 0 ){
			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", professionistaEsternoId) );
		}

		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nome", nome, MatchMode.ANYWHERE) ) ;
		}

		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public List<Incarico> cerca(List<String> parole) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("nomeIncarico"));

		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));

		if(parole.size()==1) {
			String tok=parole.get(0);

			Criterion crit_dataCanc=Restrictions.isNull("dataCancellazione") ;

			Criterion crit_1 = Restrictions.ilike("nomeIncarico", tok, MatchMode.ANYWHERE);
			Criterion crit_2 = Restrictions.ilike("commento", tok, MatchMode.ANYWHERE);
			Criterion crit_3 = Restrictions.ilike("motivazione", tok, MatchMode.ANYWHERE);

			Disjunction disj=Restrictions.disjunction();
			disj.add(crit_1);
			disj.add(crit_2);
			disj.add(crit_3);

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

				Criterion crit_1 = Restrictions.ilike("nomeIncarico", tok, MatchMode.ANYWHERE);
				Criterion crit_2 = Restrictions.ilike("commento", tok, MatchMode.ANYWHERE);
				Criterion crit_3 = Restrictions.ilike("motivazione", tok, MatchMode.ANYWHERE);

				Disjunction disj=Restrictions.disjunction();
				disj.add(crit_1);
				disj.add(crit_2);
				disj.add(crit_3);

				Conjunction conj=Restrictions.conjunction();
				conj.add(crit_dataCanc);
				conj.add(disj);

				d.add(conj);
			}

			criteria.add(d);
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_INCARICO); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public List<Incarico> cerca(String nome, String commento, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(nome, commento,professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );
		criteria.createAlias("statoIncarico", "statoIncarico");
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		if( ordinamento == null){
			criteria.addOrder(Order.asc("nomeIncarico"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				if( ordinamento.equals("nomeFascicolo") ){		

					criteria.addOrder(Order.asc("fascicolo.nome"));
				}else if( ordinamento.equals("stato") ){ 
					criteria.addOrder(Order.asc("statoIncarico.descrizione")); 

				}else{
					criteria.addOrder(Order.asc(ordinamento));
				}
			}else{
				if( ordinamento.equals("nomeFascicolo") ){			
					criteria.addOrder(Order.desc("fascicolo.nome"));
				}else if( ordinamento.equals("stato") ){  
					criteria.addOrder(Order.desc("statoIncarico.descrizione")); 

				}else{
					criteria.addOrder(Order.desc(ordinamento));
				} 
			}
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( professionistaEsternoId > 0 ){
			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", professionistaEsternoId) );
		}

		if( statoIncaricoCode != null && statoIncaricoCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("statoIncarico.codGruppoLingua", statoIncaricoCode) );
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}

		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeIncarico", URLDecoder.decode(nome,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}

		if( commento != null && commento.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeIncarico", URLDecoder.decode(commento,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}  

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataModifica"), "dataModifica")
				.add(Projections.property("statoIncarico"), "statoIncarico")
				.add(Projections.property("statoIncarico.descrizione"))
				.add(Projections.property("fascicolo"), "fascicolo")
				.add(Projections.property("dataCreazione"), "dataCreazione")
				.add(Projections.property("nomeIncarico"), "nomeIncarico"))

		.setResultTransformer(Transformers.aliasToBean(Incarico.class));

		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<Incarico> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<Incarico>();
			int index = 0;
			for( Incarico i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}

		return listaRitorno;  
	}

	@Override
	public List<Incarico> cercaArbitrale(String nome, String nominativoArbitroControparte, String indirizzoArbitroControparte, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(nome, null, professionistaEsternoId, dal, al, statoIncaricoCode, nomeFascicolo);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );
		criteria.createAlias("statoIncarico", "statoIncarico");
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",TRUE_CHAR+""));
		if( ordinamento == null){
			criteria.addOrder(Order.asc("nomeIncarico"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				if( ordinamento.equals("stato") ){ 
					criteria.addOrder(Order.asc("statoIncarico.descrizione")); 

				}else{
					criteria.addOrder(Order.asc(ordinamento));
				}
			}else{
				if( ordinamento.equals("stato") ){ 
					criteria.addOrder(Order.desc("statoIncarico.descrizione")); 

				}else{
					criteria.addOrder(Order.desc(ordinamento));
				} 
			}
		}

		if( statoIncaricoCode != null && statoIncaricoCode.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("statoIncarico.codGruppoLingua", statoIncaricoCode) );
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.createAlias("fascicolo", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( professionistaEsternoId > 0 ){
			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", professionistaEsternoId) );
		}

		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeCollegioArbitrale", URLDecoder.decode(nome,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}

		if( nominativoArbitroControparte != null && nominativoArbitroControparte.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nominativoArbitroControparte", URLDecoder.decode(nome,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}

		if( indirizzoArbitroControparte != null && indirizzoArbitroControparte.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("indirizzoArbitroControparte", URLDecoder.decode(nome,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataModifica"), "dataModifica")
				.add(Projections.property("statoIncarico"), "statoIncarico")
				.add(Projections.property("statoIncarico.descrizione"))
				.add(Projections.property("fascicolo"), "fascicolo")
				.add(Projections.property("dataCreazione"), "dataCreazione")
				.add(Projections.property("nomeIncarico"), "nomeIncarico"))

		.setResultTransformer(Transformers.aliasToBean(Incarico.class));

		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<Incarico> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<Incarico>();
			int index = 0;
			for( Incarico i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggiCollegioArbitrale( i.getId() ));					
				}
				index++;
			}
		}

		return listaRitorno;  	 
	} 

	@Override
	public List<Incarico> cercaArbitrale(List<String> parole) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("nomeCollegioArbitrale"));

		criteria.add(Restrictions.eq("collegioArbitrale",TRUE_CHAR+""));

		if(parole.size()==1) {
			String tok=parole.get(0);

			Criterion crit_dataCanc = Restrictions.isNull("dataCancellazione") ;

			Criterion crit_1 = Restrictions.ilike("nomeCollegioArbitrale", tok, MatchMode.ANYWHERE);
			Criterion crit_2 = Restrictions.ilike("nominativoArbitroControparte", tok, MatchMode.ANYWHERE);
			Criterion crit_3 = Restrictions.ilike("indirizzoArbitroControparte", tok, MatchMode.ANYWHERE);
			Criterion crit_4 = Restrictions.ilike("denominazStudioControparte", tok, MatchMode.ANYWHERE);
			Criterion crit_5 = Restrictions.ilike("nominativoSegretario", tok,MatchMode.ANYWHERE);
			Criterion crit_6 = Restrictions.ilike("indirizzoArbitroSegretario", tok,MatchMode.ANYWHERE);
			Criterion crit_7 = Restrictions.ilike("denominStudioArbitroSegret", tok,MatchMode.ANYWHERE);
			Criterion crit_8 = Restrictions.ilike("nominativoPresidente", tok,MatchMode.ANYWHERE);
			Criterion crit_9 = Restrictions.ilike("indirizzoArbitroPresidente", tok,MatchMode.ANYWHERE);

			Disjunction disj=Restrictions.disjunction();
			disj.add(crit_1);
			disj.add(crit_2);
			disj.add(crit_3);
			disj.add(crit_4);
			disj.add(crit_5);
			disj.add(crit_6);
			disj.add(crit_7);
			disj.add(crit_8);
			disj.add(crit_9);

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

				Criterion crit_1 = Restrictions.ilike("nomeCollegioArbitrale", tok, MatchMode.ANYWHERE);
				Criterion crit_2 = Restrictions.ilike("nominativoArbitroControparte", tok, MatchMode.ANYWHERE);
				Criterion crit_3 = Restrictions.ilike("indirizzoArbitroControparte", tok, MatchMode.ANYWHERE);
				Criterion crit_4 = Restrictions.ilike("denominazStudioControparte", tok, MatchMode.ANYWHERE);
				Criterion crit_5 = Restrictions.ilike("nominativoSegretario", tok,MatchMode.ANYWHERE);
				Criterion crit_6 = Restrictions.ilike("indirizzoArbitroSegretario", tok,MatchMode.ANYWHERE);
				Criterion crit_7 = Restrictions.ilike("denominStudioArbitroSegret", tok,MatchMode.ANYWHERE);
				Criterion crit_8 = Restrictions.ilike("nominativoPresidente", tok,MatchMode.ANYWHERE);
				Criterion crit_9 = Restrictions.ilike("indirizzoArbitroPresidente", tok,MatchMode.ANYWHERE);

				Disjunction disj=Restrictions.disjunction();
				disj.add(crit_1);
				disj.add(crit_2);
				disj.add(crit_3);
				disj.add(crit_4);
				disj.add(crit_5);
				disj.add(crit_6);
				disj.add(crit_7);
				disj.add(crit_8);
				disj.add(crit_9);

				Conjunction conj=Restrictions.conjunction();
				conj.add(crit_dataCanc);
				conj.add(disj);

				d.add(conj);
			}

			criteria.add(d);
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_INCARICO); 
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	} 

	@Override
	public Long contaArbitrale(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo ) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Incarico.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",TRUE_CHAR+""));
		if( professionistaEsternoId > 0 ){
			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", professionistaEsternoId) );
		}

		if( statoIncaricoCode != null && statoIncaricoCode.trim().length() > 0 ){
			criteria.createAlias("statoIncarico", "statoIncarico");
			criteria.add( Restrictions.eq("statoIncarico.codGruppoLingua", statoIncaricoCode) );
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.createAlias("fascicolo", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeCollegioArbitrale", nome, MatchMode.ANYWHERE) ) ;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public Long conta(String nome, String commento, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo ) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Incarico.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		if( professionistaEsternoId > 0 ){
			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", professionistaEsternoId) );
		}

		if( statoIncaricoCode != null && statoIncaricoCode.trim().length() > 0 ){
			criteria.createAlias("statoIncarico", "statoIncarico");
			criteria.add( Restrictions.eq("statoIncarico.codGruppoLingua", statoIncaricoCode) );
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.createAlias("fascicolo", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("nomeIncarico", nome, MatchMode.ANYWHERE) ) ;
		}

		if( commento != null && commento.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("commento", commento, MatchMode.ANYWHERE) ) ;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public VerificaPartiCorrelate creaVerificaPartiCorrelate(Documento documento) throws Throwable {
		VerificaPartiCorrelate verificaPartiCorrelate = new VerificaPartiCorrelate(); 
		verificaPartiCorrelate.setDocumento(documento);
		getHibernateTemplate().save(verificaPartiCorrelate);
		return verificaPartiCorrelate;
	}

	@Override
	public Procura creaProcura(Documento documento) throws Throwable {
		Procura procura = new Procura(); 
		procura.setDocumento(documento);
		getHibernateTemplate().save(procura);
		return procura;
	} 

	@Override
	public VerificaAnticorruzione creaVerificaAnticorruzione(Documento documento) throws Throwable {
		VerificaAnticorruzione verificaAnticorruzione = new VerificaAnticorruzione(); 
		verificaAnticorruzione.setDocumento(documento);
		getHibernateTemplate().save(verificaAnticorruzione);
		return verificaAnticorruzione;
	}  

	@Override
	public ListaRiferimento creaListaRiferimento(Documento documento) throws Throwable {
		ListaRiferimento listaRiferimento = new ListaRiferimento(); 
		listaRiferimento.setDocumento(documento);
		getHibernateTemplate().save(listaRiferimento);
		return listaRiferimento;
	}

	@Override
	public String getNextNumeroIncarico() throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<String>() {

			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery("select lpad(decode(to_char( max(to_number(nome_incarico) ) +1 ),'','1',to_char( max(to_number(nome_incarico) ) +1 )),5,0) as numero from incarico where nome_incarico like '0%'");				 		 
				return (String) query.list().get(0);
			}
		});
	}

	@Override
	public Incarico inserisci(Incarico vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Aggiorna l'incarico modificando solo i dati aggiornabili.
	 *
	 * @param vo: l'incarico
	 *            
	 */
	@Override
	public void modifica(Incarico vo) throws Throwable {
		vo.setDataModifica(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		Incarico vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo); 
	}

	@Override
	public LetteraIncarico inserisciLetteraIncarico(LetteraIncarico letteraIncarico) throws Throwable {
		logger.info("############# PROTOCOLLO : " + letteraIncarico.getProtocollo());
		getHibernateTemplate().save(letteraIncarico);
		return letteraIncarico;
	}

	@Override
	public void aggiornaLetteraIncarico(LetteraIncarico letteraIncarico) throws Throwable {
		getHibernateTemplate().update(letteraIncarico);
	}

	@Override
	public NotaPropIncarico inserisciNotaPropostaIncarico(NotaPropIncarico notaPropIncarico) throws Throwable {
		getHibernateTemplate().save(notaPropIncarico);
		return notaPropIncarico;
	}

	@Override
	public void aggiornaNotaPropostaIncarico(NotaPropIncarico notaPropIncarico) throws Throwable {
		getHibernateTemplate().update(notaPropIncarico);
	}

	@Override
	public void cancellaListaRiferimento(ListaRiferimento listaRiferimento) throws Throwable {
		listaRiferimento.setDataCancellazione(new Date());
		getHibernateTemplate().update(listaRiferimento);
	}

	@Override
	public void cancellaProcura(Procura procura) throws Throwable {
		procura.setDataCancellazione(new Date());
		getHibernateTemplate().update(procura);
	}

	@Override
	public void cancellaVerificaAnticorruzione(VerificaAnticorruzione verificaAnticorruzione) throws Throwable {
		verificaAnticorruzione.setDataCancellazione(new Date());
		getHibernateTemplate().update(verificaAnticorruzione);
	}

	@Override
	public void cancellaVerificaPartiCorrelate(VerificaPartiCorrelate verificaPartiCorrelate) throws Throwable {
		verificaPartiCorrelate.setDataCancellazione(new Date());
		getHibernateTemplate().update(verificaPartiCorrelate);
	}

	/**
	 * Metodo di lettura dell'incarico associato
	 * <p>
	 * @param id: identificativo del proforma
	 * @return oggetto Incarico.
	 * @exception Throwable
	 */
	@Override
	public Incarico leggiIncaricoAssociatoAProforma(long id) throws Throwable {

		List<Incarico> incaricoList = new ArrayList<Incarico>(0);

		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);

		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	

		if(listaRelazione.size() > 0){
			for (RIncaricoProformaSocieta relazione:listaRelazione){
				incaricoList.add(relazione.getIncarico());
			}	
			return incaricoList.get(0);
		}
		else
			return null;
	}

	@Override
	public Incarico inserisciCollegioArbitrale(Incarico vo) throws Throwable {
		vo.setDataCreazione(new Date());
		vo.setCollegioArbitrale(TRUE_CHAR+""); 
		getHibernateTemplate().save(vo);
		return vo; 
	}

	@Override
	public Incarico leggiCollegioArbitrale(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("collegioArbitrale",TRUE_CHAR+""));

		@SuppressWarnings("unchecked")
		Incarico incarico = (Incarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return incarico; 
	}

	@Override
	public void cancellaCollegioArbitrale(long id) throws Throwable {
		Incarico vo = leggiCollegioArbitrale(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void modificaCollegioArbitrale(Incarico vo) throws Throwable {
		vo.setDataModifica(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public Incarico leggiSenzaHibernate(long id) throws Throwable {
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement("select * from incarico where id = "+ id);
			rs = st.executeQuery();
			if( rs.next() ){
				Incarico vo = new Incarico();
				vo.setCollegioArbitrale( rs.getString("collegio_arbitrale"));
				vo.setDataCreazione(rs.getTimestamp("data_creazione"));
				vo.setCommento(rs.getString("commento"));
				vo.setDataAutorizzazione(rs.getTimestamp("data_autorizzazione"));
				vo.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
				vo.setDataRichiestaAutorIncarico(rs.getTimestamp("DATA_RICHIESTA_AUTOR_INCARICO"));
				vo.setDataModifica(rs.getTimestamp("data_modifica"));
				vo.setDenominazStudioControparte( rs.getString("DENOMINAZ_STUDIO_CONTROPARTE"));
				vo.setId(id);
				vo.setDenominStudioArbitroSegret(rs.getString("DENOMIN_STUDIO_ARBITRO_SEGRET"));
				vo.setDenomStudioArbitroPresiden( rs.getString("DENOM_STUDIO_ARBITRO_PRESIDEN"));
				vo.setIndirizzoArbitroControparte(rs.getString("INDIRIZZO_ARBITRO_CONTROPARTE"));
				vo.setIndirizzoArbitroPresidente( rs.getString("INDIRIZZO_ARBITRO_PRESIDENTE"));
				vo.setIndirizzoArbitroSegretario( rs.getString("INDIRIZZO_ARBITRO_SEGRETARIO"));
				vo.setMotivazione(rs.getString("MOTIVAZIONE"));
				vo.setNomeCollegioArbitrale(rs.getString("NOME_COLLEGIO_ARBITRALE")); 
				vo.setNomeIncarico( rs.getString("NOME_INCARICO"));
				vo.setNominativoArbitroControparte( rs.getString("NOMINATIVO_ARBITRO_CONTROPARTE") );
				vo.setNominativoPresidente(rs.getString("NOMINATIVO_PRESIDENTE"));
				vo.setNominativoSegretario(rs.getString("NOMINATIVO_SEGRETARIO")); 
				vo.setStatoIncarico( leggiStatoiNCARICOSenzaHibernate(rs.getLong("ID_STATO_INCARICO"), c) );
				Fascicolo f = new Fascicolo();
				f.setId(rs.getLong("ID_FASCICOLO"));
				vo.setFascicolo(f);
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

	private StatoIncarico leggiStatoiNCARICOSenzaHibernate(long idStato, Connection c) {
		PreparedStatement st = null;				
		ResultSet rs = null;

		try{
			st = c.prepareStatement("select * from STATO_INCARICO where id = "+ idStato);		
			rs = st.executeQuery();
			if( rs.next() ){
				StatoIncarico vo = new StatoIncarico();
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
	//reporting
	public List<Proforma> leggiProformaAssociatoIncarico(long id) throws Throwable {

		List<Proforma> incaricoList = new ArrayList<Proforma>(0);

		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);

		criteria.createAlias("incarico", "incarico");
		criteria.add(Restrictions.eq("incarico.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	

		if(listaRelazione.size() > 0){
			for (RIncaricoProformaSocieta relazione:listaRelazione){
				incaricoList.add(relazione.getProforma());
			}	
			return incaricoList;
		}
		else
			return null;
	}

	//reporting
	/*
	@Override
	public List<RIncaricoProformaSocieta> leggiRIncaricoProformaSocieta(String codGruppoLingua,String[] params) throws Throwable {



		 String proformaSocieta=null,
				data_autorizzazione_da=null,
				data_autorizzazione_a=null,
				proformaSettoreGiuridico=null,
				proformaOwner=null;

				 if(params!=null && params.length>4){
					 proformaSocieta=params[0];
					 data_autorizzazione_da=params[1];
					 data_autorizzazione_a=params[2];
					 proformaSettoreGiuridico=params[3];
					 proformaOwner=params[4];


				 }


				 Date dateDa=null,dateA=null;
				 if(data_autorizzazione_da!=null)
				 dateDa= DateUtil.toDate(data_autorizzazione_da);
				 if(data_autorizzazione_a!=null)
				 dateA=DateUtil.toDate(data_autorizzazione_a);


		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);	
		criteria.createAlias("proforma", "proforma");
		criteria.createAlias("proforma.statoProforma", "statoProforma");
		criteria.add(Restrictions.eq("statoProforma.codGruppoLingua", codGruppoLingua));
		criteria.add(Restrictions.eq("statoProforma.lang", "IT"));


		if(dateDa != null ){
			criteria.add(Restrictions.ge("proforma.dataAutorizzazione", dateDa));
		}

		if(dateA != null){
			criteria.add(Restrictions.lt("proforma.dataAutorizzazione", dateA));
		}

		if(proformaOwner!=null || (proformaSettoreGiuridico!=null && !proformaSettoreGiuridico.equalsIgnoreCase("TUTTI")))
		criteria.createAlias("incarico", "incarico");
		criteria.createAlias("incarico.fascicolo", "fascicolo");

		if(proformaOwner!=null)
		criteria.add(Restrictions.eq("fascicolo.legaleInterno", proformaOwner));

		if(proformaSettoreGiuridico!=null && !proformaSettoreGiuridico.equalsIgnoreCase("TUTTI")){
		criteria.createAlias("fascicolo.settoreGiuridico", "settoreGiuridico");
		criteria.add(Restrictions.eq("settoreGiuridico.codGruppoLingua", proformaSettoreGiuridico));
		}

		if(proformaSocieta!=null){
		criteria.createAlias("societa", "societa");
		criteria.add(Restrictions.eq("societa.id", new Long(proformaSocieta).longValue()));
		}

		criteria.add(Restrictions.isNull("dataCancellazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);

		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	

		 if(listaRelazione.size() > 0){
			 return listaRelazione;
		 }
	    else
	    	return null;
		}
	 */



	@SuppressWarnings("unchecked")
	@Override
	public List<RIncaricoProformaSocieta> leggiRIncaricoProformaSocieta(String codGruppoLingua,Object[] params) throws Throwable {

		Object proformaSocieta=null,
				data_autorizzazione_da=null,
				data_autorizzazione_a=null,
				proformaSettoreGiuridico=null,
				proformaOwner=null,
				proformaProfessionistaEsterno=null;

		if(params!=null && params.length>5){
			proformaSocieta=params[0];
			data_autorizzazione_da=params[1];
			data_autorizzazione_a=params[2];
			proformaSettoreGiuridico=params[3];
			proformaOwner=params[4];
			proformaProfessionistaEsterno=params[5];
		}

		Date dateDa=null,dateA=null;
		if(data_autorizzazione_da!=null)
			dateDa= DateUtil.toDate(data_autorizzazione_da.toString());
		if(data_autorizzazione_a!=null)
			dateA=DateUtil.toDate(data_autorizzazione_a.toString());


		DetachedCriteria criteria = DetachedCriteria.forClass(Proforma.class);

		criteria.setFetchMode("RIncaricoProformaSocietas", FetchMode.JOIN);


		criteria.createAlias("RIncaricoProformaSocietas", "RIncaricoProformaSocietas"); 
		criteria.createAlias("RIncaricoProformaSocietas.incarico", "incarico");
		criteria.createAlias("incarico.fascicolo", "fascicolo");

		criteria.add(Restrictions.isNull("dataCancellazione"));

		criteria.createAlias("statoProforma", "statoProforma");
		criteria.add(Restrictions.eq("statoProforma.codGruppoLingua", codGruppoLingua));
		criteria.add(Restrictions.eq("statoProforma.lang", "IT"));


		if(dateDa != null ){
			criteria.add(Restrictions.ge("dataAutorizzazione", dateDa));
		}

		if(dateA != null){
			criteria.add(Restrictions.lt("dataAutorizzazione", dateA));
		}
		
		if(proformaOwner!=null || (proformaSettoreGiuridico!=null && !proformaSettoreGiuridico.toString().equalsIgnoreCase("TUTTI")))

			if(proformaOwner!=null)
				criteria.add(Restrictions.in("fascicolo.legaleInterno", (List<String>)proformaOwner));

		if(proformaSettoreGiuridico!=null && !proformaSettoreGiuridico.toString().equalsIgnoreCase("TUTTI")){
			criteria.createAlias("fascicolo.settoreGiuridico", "settoreGiuridico");
			criteria.add(Restrictions.eq("settoreGiuridico.codGruppoLingua", proformaSettoreGiuridico));
		}

		if(proformaSocieta!=null){
			criteria.createAlias("RIncaricoProformaSocietas.societa", "societa");
			criteria.add(Restrictions.in("societa.id", (List<Long>)proformaSocieta));
		}

		if( proformaProfessionistaEsterno!=null && !((String) proformaProfessionistaEsterno).equalsIgnoreCase("TUTTI")){

			criteria.createAlias("incarico.professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", new Long(((String) proformaProfessionistaEsterno)).longValue()) );
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROFORMA);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("nomeProforma"), "nomeProforma"))

		.setResultTransformer(Transformers.aliasToBean(Proforma.class));


		List<Proforma> lista =(List<Proforma>) getHibernateTemplate().findByCriteria(criteria );

		List<RIncaricoProformaSocieta> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<RIncaricoProformaSocieta>();

			for( Proforma p : lista ){
				if( p!=null){	
					List<RIncaricoProformaSocieta> listaR=this.getRIncProfSocPerIdProforma(p.getId());
					if(listaR!=null && listaR.size()>0)
						for(RIncaricoProformaSocieta rp:listaR ){
							listaRitorno.add(rp);
						}
				}
			}
		}
		return listaRitorno;  	 
	}


	private List<RIncaricoProformaSocieta> getRIncProfSocPerIdProforma(long idProforma) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);	

		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", idProforma));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	

		if(listaRelazione.size() > 0){
			return listaRelazione;
		}
		else
			return null;
	}

	//Per Contabilizzazione
	@Override
	public List<RProformaFattura> getRProformaFattura(long idProforma) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass(RProformaFattura.class);	

		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", idProforma));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		List<RProformaFattura> listaRelazione =(List<RProformaFattura>) getHibernateTemplate().findByCriteria(criteria);	

		if(listaRelazione.size() > 0){
			return listaRelazione;
		}
		else
			return null;
	}


	//Reporting
	@Override
	public List<Incarico> leggiIncarichiReporting(String[] params) throws Throwable {

		String  data_creazione_da=null,
				data_creazione_a=null,
				incaricoProfessionistaEsterno=null,
				statoIncarico=null;
		if(params!=null && params.length>3){
			data_creazione_da=params[0];
			data_creazione_a=params[1];
			incaricoProfessionistaEsterno=params[2];
			statoIncarico=params[3];

		}

		Date dateDa=null,dateA=null;
		if(data_creazione_da!=null)
			dateDa= DateUtil.toDate(data_creazione_da);
		if(data_creazione_a!=null)
			dateA=DateUtil.toDate(data_creazione_a);



		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("nomeIncarico"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		//criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));

		if(dateDa != null ){
			criteria.add(Restrictions.ge("dataCreazione", dateDa));
		}

		if(dateA != null){
			criteria.add(Restrictions.lt("dataCreazione", dateA));
		}


		if( incaricoProfessionistaEsterno!=null && !incaricoProfessionistaEsterno.equalsIgnoreCase("TUTTI")){

			criteria.createAlias("professionistaEsterno", "professionistaEsterno");
			criteria.add( Restrictions.eq("professionistaEsterno.id", new Long(incaricoProfessionistaEsterno).longValue()) );
		}

		if( statoIncarico!=null && !statoIncarico.equalsIgnoreCase("TUTTI")){

			criteria.createAlias("statoIncarico", "statoIncarico");
			criteria.add( Restrictions.eq("statoIncarico.codGruppoLingua",statoIncarico) );
			criteria.add( Restrictions.eq("statoIncarico.lang","IT") );
		}


		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);

		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public List<Incarico> getIncaricoDaIdFascicolo(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista;
	}

	@Override
	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		if(sortByFieldName==null || sortByFieldName.isEmpty()) {
			sortByFieldName="id";
		}

		if(orderAscOrDesc==null || orderAscOrDesc.isEmpty()) {
			criteria.addOrder(Order.asc(sortByFieldName));
		}
		if(orderAscOrDesc!=null && !orderAscOrDesc.isEmpty()) {

			if(orderAscOrDesc.equals("asc")) {
				criteria.addOrder(Order.asc(sortByFieldName));
			}
			else if(orderAscOrDesc.equals("desc")) {
				criteria.addOrder(Order.desc(sortByFieldName));
			}
		}

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("fascicolo.legaleInterno", userIdOwner));
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

	@Override
	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner, String fascicoloId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		if(sortByFieldName==null || sortByFieldName.isEmpty()) {
			sortByFieldName="id";
		}

		if(orderAscOrDesc==null) {
			criteria.addOrder(Order.asc(sortByFieldName));
		}
		if(orderAscOrDesc!=null) {

			if(orderAscOrDesc.equals("asc")) {
				criteria.addOrder(Order.asc(sortByFieldName));
			}
			else if(orderAscOrDesc.equals("desc")) {
				criteria.addOrder(Order.desc(sortByFieldName));
			}
		}

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("fascicolo.legaleInterno", userIdOwner));

		if(fascicoloId != null && !"".equals(fascicoloId)){
			long idFascicolo = Long.parseLong(fascicoloId);
			criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		}

		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

	@Override
	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, List<String> userIdOwner) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		if(sortByFieldName==null || sortByFieldName.isEmpty()) {
			sortByFieldName="id";
		}

		if(orderAscOrDesc==null) {
			criteria.addOrder(Order.asc(sortByFieldName));
		}
		if(orderAscOrDesc!=null) {

			if(orderAscOrDesc.equals("asc")) {
				criteria.addOrder(Order.asc(sortByFieldName));
			}
			else if(orderAscOrDesc.equals("desc")) {
				criteria.addOrder(Order.desc(sortByFieldName));
			}
		}

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.in("fascicolo.legaleInterno", userIdOwner));
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long contaIncarichiAutorizzati() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		criteria.setProjection(Projections.rowCount());

		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> estraiListaFascicoli(Date begin, Date end) throws Throwable {

		List<Long> listaIdFascicolo = new ArrayList<Long>();

		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));

		List<Date> dateInizioFine = DateUtil2.estraiDateSemestre();

		Disjunction disjDataRinvioVotazione = Restrictions.disjunction();
		disjDataRinvioVotazione.add(Restrictions.isNull("dataRinvioVotazione"));
		disjDataRinvioVotazione.add(Restrictions.lt("dataRinvioVotazione",dateInizioFine.get(0)));
		disjDataRinvioVotazione.add(Restrictions.gt("dataRinvioVotazione",dateInizioFine.get(1)));

		criteria.add(disjDataRinvioVotazione);

		Conjunction conjDataAutorizzazione = Restrictions.conjunction();
		conjDataAutorizzazione.add(Restrictions.isNotNull("dataAutorizzazione"));
		conjDataAutorizzazione.add(Restrictions.lt("dataAutorizzazione", end));
		criteria.createAlias("statoIncarico", "statoIncarico", DetachedCriteria.INNER_JOIN);
		conjDataAutorizzazione.add(Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
		criteria.add(conjDataAutorizzazione);

		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(Restrictions.gt("dataCancellazione", begin));
		criteria.add(disjDataCancellazione);

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);

		Conjunction conjFascicoloApertoCreatoPrima = Restrictions.conjunction();
		conjFascicoloApertoCreatoPrima.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloApertoCreatoPrima.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Disjunction disjDataChiusuraFascicolo = Restrictions.disjunction();
		disjDataChiusuraFascicolo.add(Restrictions.isNull("fascicolo.dataChiusura"));
		disjDataChiusuraFascicolo.add(Restrictions.gt("fascicolo.dataChiusura", begin));

		Disjunction disjDataCancellazioneFascicolo = Restrictions.disjunction();
		disjDataCancellazioneFascicolo.add(Restrictions.isNull("fascicolo.dataCancellazione"));
		disjDataCancellazioneFascicolo.add(Restrictions.gt("fascicolo.dataCancellazione", begin));

		Conjunction conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataChiusuraFascicolo);
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataCancellazioneFascicolo);

		Conjunction conjFascicoloChiusoValidoNelRange = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRange.add(Restrictions.ne("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloChiusoValidoNelRange.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Conjunction conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjFascicoloChiusoValidoNelRange);
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		Disjunction disjFascicoloApertoORChiuso = Restrictions.disjunction();
		disjFascicoloApertoORChiuso.add(conjFascicoloApertoCreatoPrima);
		disjFascicoloApertoORChiuso.add(conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		criteria.add(disjFascicoloApertoORChiuso);

		criteria.setProjection(Projections.distinct(Projections.property("fascicolo.id")));

		listaIdFascicolo = (List<Long>) getHibernateTemplate().findByCriteria(criteria);
		return listaIdFascicolo;
	}

	@Override
	public Long contaIncarichiAutorizzati(Date begin, Date end) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));

		List<Date> dateInizioFine = DateUtil2.estraiDateSemestre();

		Disjunction disjDataRinvioVotazione = Restrictions.disjunction();
		disjDataRinvioVotazione.add(Restrictions.isNull("dataRinvioVotazione"));
		disjDataRinvioVotazione.add(Restrictions.lt("dataRinvioVotazione",dateInizioFine.get(0)));
		disjDataRinvioVotazione.add(Restrictions.gt("dataRinvioVotazione",dateInizioFine.get(1)));

		criteria.add(disjDataRinvioVotazione);

		Conjunction conjDataAutorizzazione = Restrictions.conjunction();
		conjDataAutorizzazione.add(Restrictions.isNotNull("dataAutorizzazione"));
		conjDataAutorizzazione.add(Restrictions.lt("dataAutorizzazione", end));
		criteria.createAlias("statoIncarico", "statoIncarico", DetachedCriteria.INNER_JOIN);
		conjDataAutorizzazione.add(Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
		criteria.add(conjDataAutorizzazione);

		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(Restrictions.gt("dataCancellazione", begin));
		criteria.add(disjDataCancellazione);

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);

		Conjunction conjFascicoloApertoCreatoPrima = Restrictions.conjunction();
		conjFascicoloApertoCreatoPrima.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloApertoCreatoPrima.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Disjunction disjDataChiusuraFascicolo = Restrictions.disjunction();
		disjDataChiusuraFascicolo.add(Restrictions.isNull("fascicolo.dataChiusura"));
		disjDataChiusuraFascicolo.add(Restrictions.gt("fascicolo.dataChiusura", begin));

		Disjunction disjDataCancellazioneFascicolo = Restrictions.disjunction();
		disjDataCancellazioneFascicolo.add(Restrictions.isNull("fascicolo.dataCancellazione"));
		disjDataCancellazioneFascicolo.add(Restrictions.gt("fascicolo.dataCancellazione", begin));

		Conjunction conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataChiusuraFascicolo);
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataCancellazioneFascicolo);

		Conjunction conjFascicoloChiusoValidoNelRange = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRange.add(Restrictions.ne("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloChiusoValidoNelRange.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Conjunction conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjFascicoloChiusoValidoNelRange);
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		Disjunction disjFascicoloApertoORChiuso = Restrictions.disjunction();
		disjFascicoloApertoORChiuso.add(conjFascicoloApertoCreatoPrima);
		disjFascicoloApertoORChiuso.add(conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		criteria.add(disjFascicoloApertoORChiuso);

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public Long contaIncarichiAutorizzati(Date begin, Date end, Long idProfessionistaEsterno) throws Throwable {
		long id = idProfessionistaEsterno;

		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));

		List<Date> dateInizioFine = DateUtil2.estraiDateSemestre();

		Disjunction disjDataRinvioVotazione = Restrictions.disjunction();
		disjDataRinvioVotazione.add(Restrictions.isNull("dataRinvioVotazione"));
		disjDataRinvioVotazione.add(Restrictions.lt("dataRinvioVotazione",dateInizioFine.get(0)));
		disjDataRinvioVotazione.add(Restrictions.gt("dataRinvioVotazione",dateInizioFine.get(1)));

		criteria.add(disjDataRinvioVotazione);

		criteria.createAlias("professionistaEsterno", "professionistaEsterno", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("professionistaEsterno.id", id));

		Conjunction conjDataAutorizzazione = Restrictions.conjunction();
		conjDataAutorizzazione.add(Restrictions.isNotNull("dataAutorizzazione"));
		conjDataAutorizzazione.add(Restrictions.lt("dataAutorizzazione", end));
		criteria.createAlias("statoIncarico", "statoIncarico", DetachedCriteria.INNER_JOIN);
		conjDataAutorizzazione.add(Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
		criteria.add(conjDataAutorizzazione);

		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(Restrictions.gt("dataCancellazione", begin));
		criteria.add(disjDataCancellazione);

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);

		Conjunction conjFascicoloApertoCreatoPrima = Restrictions.conjunction();
		conjFascicoloApertoCreatoPrima.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloApertoCreatoPrima.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Disjunction disjDataChiusuraFascicolo = Restrictions.disjunction();
		disjDataChiusuraFascicolo.add(Restrictions.isNull("fascicolo.dataChiusura"));
		disjDataChiusuraFascicolo.add(Restrictions.gt("fascicolo.dataChiusura", begin));

		Disjunction disjDataCancellazioneFascicolo = Restrictions.disjunction();
		disjDataCancellazioneFascicolo.add(Restrictions.isNull("fascicolo.dataCancellazione"));
		disjDataCancellazioneFascicolo.add(Restrictions.gt("fascicolo.dataCancellazione", begin));

		Conjunction conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataChiusuraFascicolo);
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataCancellazioneFascicolo);

		Conjunction conjFascicoloChiusoValidoNelRange = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRange.add(Restrictions.ne("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloChiusoValidoNelRange.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Conjunction conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjFascicoloChiusoValidoNelRange);
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		Disjunction disjFascicoloApertoORChiuso = Restrictions.disjunction();
		disjFascicoloApertoORChiuso.add(conjFascicoloApertoCreatoPrima);
		disjFascicoloApertoORChiuso.add(conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		criteria.add(disjFascicoloApertoORChiuso);

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}


	@Override
	public void cancellaBonusAcconti(LetteraIncarico letteraIncarico) throws Throwable {
		getHibernateTemplate().deleteAll(letteraIncarico.getAcconti());
		getHibernateTemplate().deleteAll(letteraIncarico.getBonus());
		getHibernateTemplate().flush();
	}


	@Override
	public List<Incarico> leggiIncarichiAutorizzati(Long idProfessionistaEsterno) throws Throwable {
		long id = idProfessionistaEsterno;
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class );

		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.createAlias("professionistaEsterno", "professionistaEsterno", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("professionistaEsterno.id", id));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

	@Override
	public void deleteBonus(Long letteraIncaricoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Bonus.class );

		criteria.createAlias("letteraIncarico", "letteraIncarico", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("letteraIncarico.id", letteraIncaricoId));

		@SuppressWarnings("unchecked")
		List<Bonus> lista = getHibernateTemplate().findByCriteria(criteria);

		getHibernateTemplate().deleteAll(lista);

		getHibernateTemplate().flush();

	}

	@Override
	public void deleteAcconti(Long letteraIncaricoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Acconti.class );

		criteria.createAlias("letteraIncarico", "letteraIncarico", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("letteraIncarico.id", letteraIncaricoId));

		@SuppressWarnings("unchecked")
		List<Acconti> lista = getHibernateTemplate().findByCriteria(criteria);

		getHibernateTemplate().deleteAll(lista);

		getHibernateTemplate().flush();
	}

	@Override
	public List<Incarico> leggiIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		
		Long numeroTotaleElementi = contaIncarichiAutorizzati(begin, end,userIdOwner, isGestoreVendor);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).addOrder(Order.asc("nomeIncarico"));
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		
		if(!isGestoreVendor)
			criteria.add(Restrictions.eq("fascicolo.legaleInterno", userIdOwner));
		
		Conjunction conjDataAutorizzazione = Restrictions.conjunction();
		conjDataAutorizzazione.add(Restrictions.isNotNull("dataAutorizzazione"));
		conjDataAutorizzazione.add(Restrictions.lt("dataAutorizzazione", end));
		
		criteria.createAlias("statoIncarico", "statoIncarico", DetachedCriteria.INNER_JOIN);
		conjDataAutorizzazione.add(Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
		
		criteria.add(conjDataAutorizzazione);

		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(Restrictions.gt("dataCancellazione", begin));
		criteria.add(disjDataCancellazione);

		Conjunction conjFascicoloApertoCreatoPrima = Restrictions.conjunction();
		conjFascicoloApertoCreatoPrima.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloApertoCreatoPrima.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Disjunction disjDataChiusuraFascicolo = Restrictions.disjunction();
		disjDataChiusuraFascicolo.add(Restrictions.isNull("fascicolo.dataChiusura"));
		disjDataChiusuraFascicolo.add(Restrictions.gt("fascicolo.dataChiusura", begin));

		Disjunction disjDataCancellazioneFascicolo = Restrictions.disjunction();
		disjDataCancellazioneFascicolo.add(Restrictions.isNull("fascicolo.dataCancellazione"));
		disjDataCancellazioneFascicolo.add(Restrictions.gt("fascicolo.dataCancellazione", begin));

		Conjunction conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataChiusuraFascicolo);
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataCancellazioneFascicolo);

		Conjunction conjFascicoloChiusoValidoNelRange = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRange.add(Restrictions.ne("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloChiusoValidoNelRange.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Conjunction conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjFascicoloChiusoValidoNelRange);
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		Disjunction disjFascicoloApertoORChiuso = Restrictions.disjunction();
		disjFascicoloApertoORChiuso.add(conjFascicoloApertoCreatoPrima);
		disjFascicoloApertoORChiuso.add(conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		criteria.add(disjFascicoloApertoORChiuso);
		
		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 
		
		//HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO, "W");
		
		@SuppressWarnings("unchecked")
		List<Incarico> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<Incarico> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<Incarico>();
			int index = 0;
			for( Incarico i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}

		return listaRitorno;  
	}
	
	@Override
	public Long contaIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Incarico.class);
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.createAlias("fascicolo", "fascicolo", DetachedCriteria.INNER_JOIN);
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		
		if(!isGestoreVendor)
			criteria.add(Restrictions.eq("fascicolo.legaleInterno", userIdOwner));
		
		Conjunction conjDataAutorizzazione = Restrictions.conjunction();
		conjDataAutorizzazione.add(Restrictions.isNotNull("dataAutorizzazione"));
		conjDataAutorizzazione.add(Restrictions.lt("dataAutorizzazione", end));
		
		criteria.createAlias("statoIncarico", "statoIncarico", DetachedCriteria.INNER_JOIN);
		conjDataAutorizzazione.add(Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
		
		criteria.add(conjDataAutorizzazione);

		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(Restrictions.gt("dataCancellazione", begin));
		criteria.add(disjDataCancellazione);

		Conjunction conjFascicoloApertoCreatoPrima = Restrictions.conjunction();
		conjFascicoloApertoCreatoPrima.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloApertoCreatoPrima.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Disjunction disjDataChiusuraFascicolo = Restrictions.disjunction();
		disjDataChiusuraFascicolo.add(Restrictions.isNull("fascicolo.dataChiusura"));
		disjDataChiusuraFascicolo.add(Restrictions.gt("fascicolo.dataChiusura", begin));

		Disjunction disjDataCancellazioneFascicolo = Restrictions.disjunction();
		disjDataCancellazioneFascicolo.add(Restrictions.isNull("fascicolo.dataCancellazione"));
		disjDataCancellazioneFascicolo.add(Restrictions.gt("fascicolo.dataCancellazione", begin));

		Conjunction conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataChiusuraFascicolo);
		conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(disjDataCancellazioneFascicolo);

		Conjunction conjFascicoloChiusoValidoNelRange = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRange.add(Restrictions.ne("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
		conjFascicoloChiusoValidoNelRange.add(Restrictions.lt("fascicolo.dataCreazione", end));

		Conjunction conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo = Restrictions.conjunction();
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjFascicoloChiusoValidoNelRange);
		conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo.add(conjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		Disjunction disjFascicoloApertoORChiuso = Restrictions.disjunction();
		disjFascicoloApertoORChiuso.add(conjFascicoloApertoCreatoPrima);
		disjFascicoloApertoORChiuso.add(conjFascicoloChiusoValidoNelRangeANDconjdisjDataChiusuraFascicoloANDdisjDataCancellazioneFascicolo);

		criteria.add(disjFascicoloApertoORChiuso);
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public Incarico rinviaVotazione(Long incaricoId) throws Throwable {
		Incarico incarico = leggi(incaricoId);
		if(incarico != null){
			incarico.setDataRinvioVotazione(new Date());
			getHibernateTemplate().update(incarico);
		}
		return incarico;
	}

	@Override
	public Long getEuroValuta() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoValuta.class );

		criteria.add(Restrictions.like("nome", "EURO"));

		@SuppressWarnings("unchecked")
		TipoValuta valuta = (TipoValuta) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));


		return valuta.getId();
	}

	@Override
	public Integer checkFile(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LetteraIncarico.class );

		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNotNull("returnFile"));
		criteria.add(Restrictions.isNotNull("returnFileName"));

		@SuppressWarnings("unchecked")
		List<LetteraIncarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista.size();
	}

	@Override
	public Integer checkStatusInviata(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LetteraIncarico.class );
		criteria.add(Restrictions.eq("id", id));

		@SuppressWarnings("unchecked")
		LetteraIncarico lettera = (LetteraIncarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return lettera.getInviata();
	}
	
	public Incarico leggiConPermessi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Incarico.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		Incarico incarico = (Incarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return incarico; 
	}
	
	public List<Stanziamenti> leggiIncarichiStanziamenti(int anno) throws Throwable {
		 
		return this.getStanziamentiAccontiPerAnno(""+anno);
	}
	
	private List<Stanziamenti> getStanziamentiAccontiPerAnno(String anno) throws Throwable {

		String  data_creazione_da="01/01/"+anno,
				data_creazione_a="31/12/"+anno;

		Date dateDa=null,dateA=null;

		dateDa= DateUtil.toDate(data_creazione_da);
		dateA=DateUtil.toDate(data_creazione_a);

		DetachedCriteria criteria = DetachedCriteria.forClass(Acconti.class);	

		criteria.add(Restrictions.eq("anno", anno));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Acconti> listaAcconti =(List<Acconti>) getHibernateTemplate().findByCriteria(criteria);	

		List<Stanziamenti> ltsStanziamenti =null;

		if(listaAcconti!=null && listaAcconti.size() > 0){

			ltsStanziamenti=new ArrayList<Stanziamenti>();

			for(Acconti acc:listaAcconti){

				//Inizializzo l'oggetto Stanziamenti
				Stanziamenti stanziamento= new Stanziamenti();
				stanziamento.setAnnoEsercizioFinanziario(Integer.parseInt(acc.getAnno()));  
				stanziamento.setFatturaContabilizzata(""); 
				stanziamento.setIdStanziamento("");
				stanziamento.setImporto(acc.getImporto().doubleValue());
				stanziamento.setNomeFascicolo(""); 
				stanziamento.setNomeIncarico("");
				stanziamento.setNomeProforma("");
				stanziamento.setStatoProforma("");

				//Prelevo la LetteraIncarico
				DetachedCriteria criteriaLetteraIncarico = DetachedCriteria.forClass(LetteraIncarico.class);	
				criteriaLetteraIncarico.add(Restrictions.eq("id", acc.getLetteraIncarico().getId()));
				@SuppressWarnings("unchecked")
				LetteraIncarico letteraIncarico = (LetteraIncarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaLetteraIncarico) );

				if(letteraIncarico!=null){

					//Prelevo l'Incarico in stato Autorizzato con Fascicolo in stato Aperto
					DetachedCriteria criteriaIncarico = DetachedCriteria.forClass(Incarico.class);
					criteriaIncarico.createAlias("letteraIncarico", "letteraIncarico");
					criteriaIncarico.add(Restrictions.eq("letteraIncarico.id", letteraIncarico.getId()));
					criteriaIncarico.createAlias("statoIncarico", "statoIncarico");
					criteriaIncarico.add( Restrictions.eq("statoIncarico.codGruppoLingua",CostantiDAO.INCARICO_STATO_AUTORIZZATO));
					criteriaIncarico.add( Restrictions.eq("statoIncarico.lang","IT") );
					criteriaIncarico.createAlias("fascicolo", "fascicolo",DetachedCriteria.INNER_JOIN);
					criteriaIncarico.createAlias("fascicolo.statoFascicolo", "statoFascicolo");
					criteriaIncarico.add( Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));
					criteriaIncarico.add( Restrictions.eq("statoFascicolo.lang","IT") );
					HibernateDaoUtil.aggiungiLogicaPermessi(criteriaIncarico, NOME_CLASSE_INCARICO);
					@SuppressWarnings("unchecked")
					Incarico incarico = (Incarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaIncarico));

					if(incarico!=null){

						stanziamento.setNomeFascicolo(incarico.getFascicolo().getNome()); 
						stanziamento.setNomeIncarico(incarico.getNomeIncarico());

						//Prelevo i Proforma associati allincarico  --r_incarico_proforma_societa
						DetachedCriteria criteriaProforma = DetachedCriteria.forClass(Proforma.class);
						criteriaProforma.add(Restrictions.isNull("dataCancellazione"));
						criteriaProforma.setFetchMode("RIncaricoProformaSocietas", FetchMode.JOIN);
						criteriaProforma.createAlias("RIncaricoProformaSocietas", "RIncaricoProformaSocietas"); 
						criteriaProforma.createAlias("RIncaricoProformaSocietas.incarico", "incarico");
						criteriaProforma.add(Restrictions.eq("incarico.id", incarico.getId()));

						criteriaProforma.add(Restrictions.between("dataInserimento", dateDa,dateA));

						@SuppressWarnings("unchecked")
						List<Proforma> listaProforma =(List<Proforma>) getHibernateTemplate().findByCriteria(criteriaProforma);

						if(listaProforma!=null && listaProforma.size()>0){

							for(Proforma pro:listaProforma){
								
								Stanziamenti newStanziamento = new Stanziamenti();
								newStanziamento.setAnnoEsercizioFinanziario(stanziamento.getAnnoEsercizioFinanziario());
								newStanziamento.setIdStanziamento(stanziamento.getIdStanziamento());
								newStanziamento.setImporto(stanziamento.getImporto());
								newStanziamento.setNomeFascicolo(stanziamento.getNomeFascicolo());
								newStanziamento.setNomeIncarico(stanziamento.getNomeIncarico());
								newStanziamento.setNomeProforma(pro.getNomeProforma());	
								newStanziamento.setStatoProforma(pro.getStatoProforma().getDescrizione());
								
								if(pro.getTotaleAutorizzato() != null){
									Double tot = pro.getTotaleAutorizzato().doubleValue();
									newStanziamento.setImportoProforma(tot);
								}
								else
									newStanziamento.setImportoProforma(new Double(0));
								
								DetachedCriteria criteriaProformaFattura = DetachedCriteria.forClass(RProformaFattura.class);	
								criteriaProformaFattura.createAlias("proforma", "proforma"); 
								criteriaProformaFattura.add(Restrictions.eq("proforma.id", pro.getId()));	
								@SuppressWarnings("unchecked")
								RProformaFattura rProformaFattura = (RProformaFattura) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaProformaFattura));
								if(rProformaFattura!=null){
									Fattura fattura=rProformaFattura.getFattura();	

									if(fattura!=null && fattura.getDataRegistrazione()!=null && fattura.getnProtocolloFiscale()!=null){
										newStanziamento.setFatturaContabilizzata("SI"); 	
									}
									else if(fattura!=null && fattura.getnProtocolloFiscale()==null){
										newStanziamento.setFatturaContabilizzata("NO"); 	
									}
								}
								ltsStanziamenti.add(newStanziamento);
							}
						}
						else{
							ltsStanziamenti.add(stanziamento);
						}
					}
				}
			} 
		}
		return ltsStanziamenti;
	}

	@Override
	public Integer checkStatusInviataNot(long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotaPropIncarico.class );
		criteria.add(Restrictions.eq("id", id));

		@SuppressWarnings("unchecked")
		NotaPropIncarico not = (NotaPropIncarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return not.getInviata();
	}

	@Override
	public Integer checkFileNot(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( NotaPropIncarico.class );

		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNotNull("returnFile"));
		criteria.add(Restrictions.isNotNull("returnFileName"));

		@SuppressWarnings("unchecked")
		List<NotaPropIncarico> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista.size();
	}
	
}
