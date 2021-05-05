package eng.la.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.SchedaFondoRischi;
import eng.la.model.StoricoSchedaFondoRischi;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil;

@Component("schedaFondoRischiDAO")
public class SchedaFondoRischiDAOImpl extends HibernateDaoSupport implements SchedaFondoRischiDAO, CostantiDAO{


	@Autowired
	public SchedaFondoRischiDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<SchedaFondoRischi> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class ).addOrder(Order.asc("ragioneSociale"));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<SchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public SchedaFondoRischi leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		SchedaFondoRischi schedaFondoRischi = (SchedaFondoRischi) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return schedaFondoRischi; 
	}
	
	@Override
	public List<SchedaFondoRischi> cerca(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaCode, String nomeFascicolo, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(dal, al, statoSchedaFondoRischiCode, tipologiaSchedaCode, rischioSoccombenzaCode, nomeFascicolo);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class );
		criteria.createAlias("statoSchedaFondoRischi", "statoSchedaFondoRischi");
		criteria.createAlias("tipologiaSchedaFr", "tipologiaSchedaFr");
		criteria.createAlias("rischioSoccombenza", "rischioSoccombenza");
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
			if( ordinamento.equals("nomeFascicolo") ){		

				criteria.addOrder(Order.asc("fascicolo.nome"));
			}
			else if( ordinamento.equals("stato") ){ 
				criteria.addOrder(Order.asc("statoSchedaFondoRischi.descrizione")); 
			}
			else if( ordinamento.equals("tipologia") ){ 
				criteria.addOrder(Order.asc("tipologiaSchedaFr.descrizione")); 
			}
			else if( ordinamento.equals("rischioSoccombenza") ){ 
				criteria.addOrder(Order.asc("rischioSoccombenza.descrizione")); 
			}
			else{
				criteria.addOrder(Order.asc(ordinamento));
			}
		}else{
			if( ordinamento.equals("nomeFascicolo") ){			
				criteria.addOrder(Order.desc("fascicolo.nome"));
			}else if( ordinamento.equals("stato") ){  
				criteria.addOrder(Order.desc("statoSchedaFondoRischi.descrizione")); 

			}else{
				criteria.addOrder(Order.desc(ordinamento));
			} 
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataCreazione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataCreazione", DateUtil.getDataOra(al+" - 23:59:59")));
		}
		
		if( statoSchedaFondoRischiCode != null && statoSchedaFondoRischiCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("statoSchedaFondoRischi.codGruppoLingua", statoSchedaFondoRischiCode) );
		}
		
		if( tipologiaSchedaCode != null && tipologiaSchedaCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("tipologiaSchedaFr.codGruppoLingua", tipologiaSchedaCode) );
		}
		
		if( rischioSoccombenzaCode != null && rischioSoccombenzaCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("rischioSoccombenza.codGruppoLingua", rischioSoccombenzaCode) );
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){ 
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_SCHEDA_FONDO_RISCHI);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataModifica"), "dataModifica")
				.add(Projections.property("statoSchedaFondoRischi"), "statoSchedaFondoRischi")
				.add(Projections.property("statoSchedaFondoRischi.descrizione"))
				.add(Projections.property("tipologiaSchedaFr"), "tipologiaSchedaFr")
				.add(Projections.property("tipologiaSchedaFr.descrizione"))
				.add(Projections.property("rischioSoccombenza"), "rischioSoccombenza")
				.add(Projections.property("rischioSoccombenza.descrizione"))
				.add(Projections.property("fascicolo"), "fascicolo")
				.add(Projections.property("dataCreazione"), "dataCreazione"))

		.setResultTransformer(Transformers.aliasToBean(SchedaFondoRischi.class));

		@SuppressWarnings("unchecked")
		List<SchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<SchedaFondoRischi> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<SchedaFondoRischi>();
			int index = 0;
			for( SchedaFondoRischi i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}

		return listaRitorno;  
	}
	

	@Override
	public Long conta(String dal, String al, String statoSchedaFondoRischiCode, String tipologiaSchedaCode, String rischioSoccombenzaSchedaCode, String nomeFascicolo ) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SchedaFondoRischi.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));

		if( statoSchedaFondoRischiCode != null && statoSchedaFondoRischiCode.trim().length() > 0 ){
			criteria.createAlias("statoSchedaFondoRischi", "statoSchedaFondoRischi");
			criteria.add( Restrictions.eq("statoSchedaFondoRischi.codGruppoLingua", statoSchedaFondoRischiCode) );
		}
		
		if( tipologiaSchedaCode != null && tipologiaSchedaCode.trim().length() > 0 ){
			criteria.createAlias("tipologiaSchedaFr", "tipologiaSchedaFr");
			criteria.add( Restrictions.eq("tipologiaSchedaFr.codGruppoLingua", tipologiaSchedaCode) );
		}
		
		if( rischioSoccombenzaSchedaCode != null && rischioSoccombenzaSchedaCode.trim().length() > 0 ){
			criteria.createAlias("rischioSoccombenza", "rischioSoccombenza");
			criteria.add( Restrictions.eq("rischioSoccombenza.codGruppoLingua", rischioSoccombenzaSchedaCode) );
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
		
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_SCHEDA_FONDO_RISCHI);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<SchedaFondoRischi> cerca(Date dal, Date al) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class );
		criteria.addOrder(Order.desc("id"));
		criteria.createAlias("statoSchedaFondoRischi", "statoSchedaFondoRischi");
		criteria.createAlias("tipologiaSchedaFr", "tipologiaSchedaFr");
		criteria.createAlias("rischioSoccombenza", "rischioSoccombenza");
		criteria.createAlias("fascicolo", "fascicolo");
		
		if( al != null ){
			criteria.add(Restrictions.lt("dataCreazione", al));
		}
		
		Conjunction cnjDataCancellazione = Restrictions.conjunction();
		cnjDataCancellazione.add(Restrictions.between("dataCancellazione", dal, al));
		cnjDataCancellazione.add(Restrictions.eq("statoSchedaFondoRischi.codGruppoLingua", CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO));
		
		Disjunction disjDataCancellazione = Restrictions.disjunction();
		disjDataCancellazione.add(Restrictions.isNull("dataCancellazione"));
		disjDataCancellazione.add(cnjDataCancellazione);
		criteria.add(disjDataCancellazione);
		
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_SCHEDA_FONDO_RISCHI);
		
		@SuppressWarnings("unchecked")
		List<SchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;  
	}
	
	@Override
	public List<StoricoSchedaFondoRischi> leggiVersioniPrecedenti(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StoricoSchedaFondoRischi.class );
		criteria.addOrder(Order.asc("dataModifica"));
		criteria.createAlias("schedaFondoRischi", "schedaFondoRischi");
		criteria.add( Restrictions.eq("schedaFondoRischi.id", id) );
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<StoricoSchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	@Override
	public String getNextNumeroSchedaFondoRischi() throws Throwable {
		return getHibernateTemplate().execute(new HibernateCallback<String>() {

			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery("select lpad(decode(to_char( max(to_number(nome_incarico) ) +1 ),'','1',to_char( max(to_number(nome_incarico) ) +1 )),5,0) as numero from schedaFondoRischi where nome_incarico like '0%'");				 		 
				return (String) query.list().get(0);
			}
		});
	}
	
	@Override
	public SchedaFondoRischi inserisci(SchedaFondoRischi vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public StoricoSchedaFondoRischi inserisci(StoricoSchedaFondoRischi vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void modifica(SchedaFondoRischi vo) throws Throwable {
		vo.setDataModifica(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		SchedaFondoRischi vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo); 
	}

	@Override
	public List<SchedaFondoRischi> getIncaricoDaIdFascicolo(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		@SuppressWarnings("unchecked")
		List<SchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista;
	}

	@Override
	public List<SchedaFondoRischi> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischi.class );

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
		criteria.createAlias("fascicolo.statoFascicolo", "statoFascicolo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("statoFascicolo.codGruppoLingua",CostantiDAO.FASCICOLO_STATO_APERTO));

		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.add(Restrictions.eq("collegioArbitrale",FALSE_CHAR+""));
		criteria.add(Restrictions.isNotNull("dataAutorizzazione"));

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_INCARICO);
		@SuppressWarnings("unchecked")
		List<SchedaFondoRischi> lista = getHibernateTemplate().findByCriteria(criteria);

		return lista; 
	}

}
