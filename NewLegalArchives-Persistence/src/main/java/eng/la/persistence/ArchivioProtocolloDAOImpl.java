package eng.la.persistence;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.ArchivioProtocollo;
import eng.la.model.Utente;
import eng.la.model.view.UtenteView;
import eng.la.util.DateUtil;

@Component("archivioProtocolloDAO")
public class ArchivioProtocolloDAOImpl extends HibernateDaoSupport implements ArchivioProtocolloDAO, CostantiDAO {
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	public ArchivioProtocolloDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Rimuove il protocollo richiesto
	 * @author MASSIMO CARUSO
	 * @param id_protocollo l'id del protocollo da rimuovere
	 * @throws Throwable
	 */
	public void removeArchivioProtocollo(long id_protocollo) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( ArchivioProtocollo.class );
		criteria.add(Restrictions.eq("id", id_protocollo));
		List<ArchivioProtocollo> to_delete = (List<ArchivioProtocollo>)getHibernateTemplate().findByCriteria(criteria);
		if(to_delete != null && to_delete.size() == 1){
			getHibernateTemplate().delete(to_delete.get(0));
		}else{
			throw new Throwable("Archivio Protocollo con id "+id_protocollo+" non presente");
		}
	}
	
	@Override
	public ArchivioProtocollo insertArchivioProtocolloFirst(ArchivioProtocollo archivioProtocollo) throws Throwable {
		getHibernateTemplate().save(archivioProtocollo);
		return archivioProtocollo;
	}

	@Override
	public ArchivioProtocollo getPrevArchivioProtocollo() throws Throwable {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ArchivioProtocollo.class)
				.addOrder(Order.desc("id"));

		@SuppressWarnings("unchecked")
		List<ArchivioProtocollo> lista = (List<ArchivioProtocollo>)getHibernateTemplate().findByCriteria(criteria);	
		ArchivioProtocollo vo = lista.get(0);		
		return vo; 
	}

	@Override
	public ArchivioProtocollo updateArchivioProtocollo(ArchivioProtocollo archivioProtocollo) throws Throwable {
		getHibernateTemplate().update(archivioProtocollo);
		return archivioProtocollo;
	}

	@Override
	public List<ArchivioProtocollo> cerca(String numeroProtocollo, String dal, String al, String nomeFascicolo,
			int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable {


		Long numeroTotaleElementi = conta(numeroProtocollo,  dal, al, nomeFascicolo, tipo, utenteConnesso, statoProtocolloCode);
		numElementiPerPagina = (int) (numElementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : numElementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( ArchivioProtocollo.class );
		
		criteria.addOrder(Order.desc("dataInserimento"));
		
		criteria.add(Restrictions.eq("tipo", tipo));
		
		criteria.createAlias("statoProtocollo", "stato");
		

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataInserimento", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataInserimento", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){ 
			criteria.createAlias("fascicoloAssociato", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}
		
		if( statoProtocolloCode != null && statoProtocolloCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("stato.codGruppoLingua", statoProtocolloCode) );
		}

		if( numeroProtocollo != null && numeroProtocollo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("numProtocollo", URLDecoder.decode(numeroProtocollo,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}

		int indicePrimoElemento = numElementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 

		criteria = aggiungiLogicaUtenze(criteria, utenteConnesso);
		
			
		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataInserimento"), "dataInserimento")
				.add(Projections.property("fascicoloAssociato"), "fascicoloAssociato")
				.add(Projections.property("mittenteMat"), "mittenteMat")
				.add(Projections.property("destinatarioMat"), "destinatarioMat")
				.add(Projections.property("mittente"), "mittente")
				.add(Projections.property("destinatario"), "destinatario")
				.add(Projections.property("oggetto"), "oggetto")
				.add(Projections.property("tipo"), "tipo")
				.add(Projections.property("numProtocollo"), "numProtocollo"))
			.setResultTransformer(Transformers.aliasToBean(ArchivioProtocollo.class));
		
		@SuppressWarnings("unchecked")
		List<ArchivioProtocollo> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+numElementiPerPagina );
		
		List<ArchivioProtocollo> listaRitorno  = null;
		
		if( lista != null ){
			listaRitorno  = new ArrayList<ArchivioProtocollo>();
			int index = 0;
			for( ArchivioProtocollo i : lista ){
				if( index < numElementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}

		return listaRitorno;  
	}
	
	private DetachedCriteria aggiungiLogicaUtenze(DetachedCriteria criteria, UtenteView utenteConnesso) {
		
		Utente topResponsabile = null;
		
		try {
			topResponsabile = utenteDAO.leggiResponsabileTop();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
//		if(utenteConnesso.getVo().getMatricolaUtil().equals(topResponsabile.getMatricolaUtil())){
//			
//			criteria.add(Restrictions.disjunction()
//	        .add(Restrictions.eq("stato.codGruppoLingua", "ASS"))
//	        .add(Restrictions.eq("stato.codGruppoLingua", "DAS"))
//	        .add(Restrictions.eq("stato.codGruppoLingua", "TRA"))
//	        .add(Restrictions.eq("stato.codGruppoLingua", "NTR")));
//		}
//		else 
			
		if(utenteConnesso.isOperatoreSegreteria() 
				|| utenteConnesso.isAmministratore() 
				|| utenteConnesso.getVo().getMatricolaUtil().equals(topResponsabile.getMatricolaUtil())){
			//criteria.add(Restrictions.eq("owner", utenteConnesso.getVo()));
		}
		else{
			criteria.add(Restrictions.eq("assegnatario", utenteConnesso.getVo()));
		}
		
		return criteria;
	}

	@Override
	public ArchivioProtocollo leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ArchivioProtocollo.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		ArchivioProtocollo archivio = (ArchivioProtocollo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return archivio; 
	}

	@Override
	public ArchivioProtocollo leggi(String numProtocollo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ArchivioProtocollo.class ).add( Restrictions.eq("numProtocollo", numProtocollo) );
		@SuppressWarnings("unchecked")
		ArchivioProtocollo archivio = (ArchivioProtocollo) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return archivio; 
	}
	
	@Override
	public Long conta(String numeroProtocollo, String dal, String al, String nomeFascicolo, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArchivioProtocollo.class);
		
		
		criteria.add(Restrictions.eq("tipo", tipo));
		
		criteria.createAlias("statoProtocollo", "stato");

		if( nomeFascicolo != null && nomeFascicolo.trim().length() > 0 ){
			criteria.createAlias("fascicoloAssociato", "fascicolo");
			criteria.add( Restrictions.eq("fascicolo.nome", nomeFascicolo) );
		}
		
		if( statoProtocolloCode != null && statoProtocolloCode.trim().length() > 0 ){
			
			criteria.add( Restrictions.eq("stato.codGruppoLingua", statoProtocolloCode) );
		
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataInserimento", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataInserimento", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( numeroProtocollo != null && numeroProtocollo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("numProtocollo", numeroProtocollo, MatchMode.ANYWHERE) ) ;
		}
		
		criteria = aggiungiLogicaUtenze(criteria, utenteConnesso);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<ArchivioProtocollo> leggiProtocolliDaAssegnare() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArchivioProtocollo.class)
				.addOrder(Order.desc("id"));

		criteria.createAlias("statoProtocollo", "stato");
		
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "DAS"));
		
		@SuppressWarnings("unchecked")
		List<ArchivioProtocollo> lista = (List<ArchivioProtocollo>)getHibernateTemplate().findByCriteria(criteria);	
		
		return lista; 
	}

	@Override
	public List<ArchivioProtocollo> leggiProtocolliAssegnati(Utente utente) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArchivioProtocollo.class)
				.addOrder(Order.desc("id"));

		criteria.createAlias("statoProtocollo", "stato");
		
		criteria.add(Restrictions.eq("stato.codGruppoLingua", "ASS"));
		
		criteria.add(Restrictions.eq("assegnatario", utente));
		
		@SuppressWarnings("unchecked")
		List<ArchivioProtocollo> lista = (List<ArchivioProtocollo>)getHibernateTemplate().findByCriteria(criteria);	
		
		return lista; 
	}


	

}
