package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Documento;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.RProfDocumento;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;

@Component("professionistaEsternoDAO")
public class ProfessionistaEsternoDAOImpl extends HibernateDaoSupport implements ProfessionistaEsternoDAO{
	@Autowired
	public ProfessionistaEsternoDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<ProfessionistaEsterno> leggiNazioneSpecializzazione(boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).addOrder(Order.asc("cognome"));
		if(!tutti){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.setFetchMode("RProfessionistaNaziones", FetchMode.JOIN);
		criteria.setFetchMode("RProfEstSpecs", FetchMode.JOIN);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	/**
	 * Ritorna l'elenco dei professionisti esterni di una data tipologia.
	 * 
	 * @param tutti booleano per indicare se recuerare anche quelli con validita ultimata
	 * @return lista di oggetti ProfessionistaEsterno
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsterno> leggi(boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).addOrder(Order.asc("cognome"));
		if(!tutti){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	
	/**
	 * Ritorna l'elenco dei professionisti esterni di una data tipologia.
	 * 
	 * @param tipologiaProfessionista tipologia dei professionisti
	 * @param tutti booleano per indicare se recuerare anche quelli con validita ultimata
	 * @return lista di oggetti ProfessionistaEsterno
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsterno> leggiProfessionistiPerCategoria(String tipologiaProfessionista, boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).addOrder(Order.asc("cognome"));
		if(!tutti){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		criteria.createAlias("tipoProfessionista", "tipoProfessionista");
		criteria.add(Restrictions.eq("tipoProfessionista.codGruppoLingua", tipologiaProfessionista));
		
		criteria.createAlias("categoriaContest", "categoriaContest");
		criteria.add(Restrictions.ne("categoriaContest.codGruppoLingua", "ONLY"));
		
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public ProfessionistaEsterno leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		ProfessionistaEsterno professionistaEsterno = (ProfessionistaEsterno) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return professionistaEsterno; 
	}


	@Override
	public ProfessionistaEsterno leggi(long id, boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		ProfessionistaEsterno professionistaEsterno = (ProfessionistaEsterno) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return professionistaEsterno; 
	}
	
	@Override
	public List<ProfessionistaEsterno> cerca(String cognome, String nome, long studioLegaleId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).addOrder(Order.asc("cognome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( studioLegaleId > 0 ){
			criteria.createAlias("studioLegale", "studioLegale");
			criteria.add( Restrictions.eq("studioLegale.id", studioLegaleId) );
		}
		
		if( cognome != null && cognome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", cognome, MatchMode.ANYWHERE) ) ;
		}
		
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", nome, MatchMode.ANYWHERE) ) ;
		}
		 
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	} 

	@Override
	public List<ProfessionistaEsterno> cerca(String cognome, String nome, long studioLegaleId, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("cognome"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
 
		if( studioLegaleId > 0 ){
			criteria.createAlias("studioLegale", "studioLegale");
			criteria.add( Restrictions.eq("studioLegale.id", studioLegaleId) );
		}
		
		if( cognome != null && cognome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", cognome, MatchMode.ANYWHERE) ) ;
		}
		
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", nome, MatchMode.ANYWHERE) ) ;
		}
		
		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(cognome, nome, studioLegaleId);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}
		 
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);		
		return lista; 
	}

	@Override
	public List<RProfessionistaNazione> leggiProfNazionebyId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RProfessionistaNazione.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");
		criteria.add( Restrictions.eq("professionistaEsterno.id", id));
		@SuppressWarnings("unchecked")
		List<RProfessionistaNazione> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public List<RProfEstSpec> leggiProfSpecbyId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RProfEstSpec.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");
		criteria.add( Restrictions.eq("professionistaEsterno.id", id));
		@SuppressWarnings("unchecked")
		List<RProfEstSpec> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public List<RProfDocumento> leggiProfDocbyId(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RProfDocumento.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");
		criteria.add( Restrictions.eq("professionistaEsterno.id", id));
		@SuppressWarnings("unchecked")
		List<RProfDocumento> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public ProfessionistaEsterno leggi(long id, FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProfessionistaEsterno.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("studioLegale", fetchMode);
		criteria.setFetchMode("statoProfessionista", fetchMode);
		criteria.setFetchMode("statoEsitoValutazioneProf", fetchMode);
		criteria.setFetchMode("tipoProfessionista", fetchMode);
		criteria.setFetchMode("RProfessionistaNaziones", fetchMode);
		criteria.setFetchMode("RProfEstSpecs", fetchMode);
		criteria.setFetchMode("RProfDocumento", fetchMode);
		@SuppressWarnings("unchecked")
		ProfessionistaEsterno professionistaEsterno = (ProfessionistaEsterno) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return professionistaEsterno;
	}
	
	@Override
	public ProfessionistaEsterno leggi(long id, FetchMode fetchMode, boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProfessionistaEsterno.class).add(Restrictions.eq("id", id));
		
		if(!tutti)
			criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("studioLegale", fetchMode);
		criteria.setFetchMode("statoProfessionista", fetchMode);
		criteria.setFetchMode("statoEsitoValutazioneProf", fetchMode);
		criteria.setFetchMode("tipoProfessionista", fetchMode);
		criteria.setFetchMode("RProfessionistaNaziones", fetchMode);
		criteria.setFetchMode("RProfEstSpecs", fetchMode);
		criteria.setFetchMode("RProfDocumento", fetchMode);
		@SuppressWarnings("unchecked")
		ProfessionistaEsterno professionistaEsterno = (ProfessionistaEsterno) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return professionistaEsterno;
	}
	
	@Override
	public Long conta(String cognome, String nome, long studioLegaleId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( studioLegaleId > 0 ){
			criteria.createAlias("studioLegale", "studioLegale");
			criteria.add( Restrictions.eq("studioLegale.id", studioLegaleId) );
		}
		
		if( cognome != null && cognome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", cognome, MatchMode.ANYWHERE) ) ;
		}
		
		if( nome != null && nome.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("cognome", nome, MatchMode.ANYWHERE) ) ;
		}
		 
		criteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));	
		return conta; 
	}
	
	
	@Override
	public ProfessionistaEsterno inserisci(ProfessionistaEsterno vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void inserisciProfessionistaEsternoNazione(RProfessionistaNazione professionistaEsternoNazione) throws Throwable {
		getHibernateTemplate().save(professionistaEsternoNazione);
	}

	@Override
	public RProfDocumento aggiungiDocumento(Long professionistaEsternoId, Long documentoId) throws Throwable {
		RProfDocumento vo = new RProfDocumento();
		ProfessionistaEsterno professionistaEsterno = leggi(professionistaEsternoId);
		vo.setProfessionistaEsterno(professionistaEsterno);
		Documento doc = new Documento();
		doc.setId(documentoId);
		vo.setDocumento(doc);
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public void cancellaProfessionistaEsternoNazione(long professionistaEsternoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfessionistaNazione.class);
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");		
		criteria.add(Restrictions.eq("professionistaEsterno.id", professionistaEsternoId));
		@SuppressWarnings("unchecked")
		List<RProfessionistaNazione> lista = (List<RProfessionistaNazione>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( RProfessionistaNazione vo : lista ){
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void cancellaLogicProfessionistaEsternoNazione(long professionistaEsternoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfessionistaNazione.class);
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");		
		criteria.add(Restrictions.eq("professionistaEsterno.id", professionistaEsternoId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RProfessionistaNazione> lista = (List<RProfessionistaNazione>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( RProfessionistaNazione vo : lista ){
				vo.setDataCancellazione(new Date());
				getHibernateTemplate().update(vo);
			}
		}
	}
	
	@Override
	public void inserisciProfessionistaEsternoSpec(RProfEstSpec professionistaEsternoSpec) throws Throwable {
		getHibernateTemplate().save(professionistaEsternoSpec);
	}

	@Override
	public void cancellaProfessionistaEsternoSpec(long professionistaEsternoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfEstSpec.class);
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");		
		criteria.add(Restrictions.eq("professionistaEsterno.id", professionistaEsternoId));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RProfEstSpec> lista = (List<RProfEstSpec>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( RProfEstSpec vo : lista ){
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void cancellaLogicProfessionistaEsternoSpec(long professionistaEsternoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfEstSpec.class);
		criteria.createAlias("professionistaEsterno", "professionistaEsterno");		
		criteria.add(Restrictions.eq("professionistaEsterno.id", professionistaEsternoId));
		@SuppressWarnings("unchecked")
		List<RProfEstSpec> lista = (List<RProfEstSpec>)getHibernateTemplate().findByCriteria(criteria);
		if( lista != null && lista.size() >  0){
			for( RProfEstSpec vo : lista ){
				vo.setDataCancellazione(new Date());
				getHibernateTemplate().update(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void modifica(ProfessionistaEsterno vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		ProfessionistaEsterno vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	} 
	
	@Override
	public void cancellaDocumento(long documentoId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RProfDocumento.class);
		criteria.createAlias("documento", "documento");		
		criteria.add(Restrictions.eq("documento.id", documentoId));
		@SuppressWarnings("unchecked")
		RProfDocumento vo = (RProfDocumento) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	/**
	 * Ritorna l'elenco dei professionisti esterni abilitati al Beauty Contest.
	 * 
	 * @param none
	 * @return lista di oggetti ProfessionistaEsterno
	 * @throws Throwable
	 */
	@Override
	public List<ProfessionistaEsterno> leggiAbilitatiContest() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsterno.class ).addOrder(Order.asc("cognome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		criteria.createAlias("categoriaContest", "categoriaContest");
		
		Disjunction disj = Restrictions.disjunction();
		disj.add(Restrictions.eq("categoriaContest.codGruppoLingua", "ONLY"));
		disj.add(Restrictions.eq("categoriaContest.codGruppoLingua", "ALSO"));
		criteria.add(disj);
		
		@SuppressWarnings("unchecked")
		List<ProfessionistaEsterno> lista = (List<ProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	

}
