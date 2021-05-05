package eng.la.persistence;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Nazione;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.Societa;

@Component("societaDAO")
public class SocietaDAOImpl extends HibernateDaoSupport implements SocietaDAO {

	@Autowired
	public SocietaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable("societaCacheLeggi")
	@Override
	public List<Societa> leggi(boolean tutte) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class).addOrder(Order.asc("ragioneSociale"));
		if( !tutte){ 
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		@SuppressWarnings("unchecked")
		List<Societa> lista = (List<Societa>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("societaCacheLeggiDaId")
	@Override
	public Societa leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Societa societa = (Societa) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return societa;
	}

	@Cacheable("societaCacheCerca")
	@Override
	public List<Societa> cerca(String ragioneSociale, long nazioneId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class).addOrder(Order.asc("ragioneSociale"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nazioneId > 0) {
			criteria.createAlias("nazione", "nazione");
			criteria.add(Restrictions.eq("nazione.id", nazioneId));
		}

		if (ragioneSociale != null && ragioneSociale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("ragioneSociale", ragioneSociale, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<Societa> lista = (List<Societa>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Cacheable("societaCacheCarcaPaginata")
	@Override
	public List<Societa> cerca(String ragioneSociale, long nazioneId, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("ragioneSociale"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (nazioneId > 0) {
			criteria.createAlias("nazione", "nazione");
			criteria.add(Restrictions.eq("nazione.id", nazioneId));
		}

		if (ragioneSociale != null && ragioneSociale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("ragioneSociale", ragioneSociale, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(ragioneSociale, nazioneId);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<Societa> lista = (List<Societa>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Cacheable("societaCacheConta")
	@Override
	public Long conta(String ragioneSociale, long nazioneId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nazioneId > 0) {
			criteria.createAlias("nazione", "nazione");
			criteria.add(Restrictions.eq("nazione.id", nazioneId));
		}

		if (ragioneSociale != null && ragioneSociale.trim().length() > 0) {
			criteria.add(Restrictions.ilike("ragioneSociale", ragioneSociale, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@CacheEvict(value = { "societaCacheConta", "societaCacheCarcaPaginata", "societaCacheCerca",
			"societaCacheLeggiDaId", "societaCacheLeggi" }, allEntries = true)
	@Override
	public Societa inserisci(Societa vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@CacheEvict(value = { "societaCacheConta", "societaCacheCarcaPaginata", "societaCacheCerca",
			"societaCacheLeggiDaId", "societaCacheLeggi" }, allEntries = true)
	@Override
	public void modifica(Societa vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@CacheEvict(value = { "societaCacheConta", "societaCacheCarcaPaginata", "societaCacheCerca",
			"societaCacheLeggiDaId", "societaCacheLeggi" }, allEntries = true)
	@Override
	public void cancella(long id) throws Throwable {
		Societa vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public boolean controlla(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Nazione.class );
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Societa> lista = (List<Societa>)getHibernateTemplate().findByCriteria(criteria);
		return !lista.isEmpty(); 
	}
	
	/**
	 * Metodo di lettura dell'incarico associato
	 * <p>
	 * @param id: identificativo del proforma
	 * @return oggetto Incarico.
	 * @exception Throwable
	 */
	@Override
	public Societa leggiSocietaAddebitoProforma(long id) throws Throwable {
		
		List<Societa> societaList = new ArrayList<Societa>(0);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RIncaricoProformaSocieta.class);
		
		criteria.createAlias("proforma", "proforma");
		criteria.add(Restrictions.eq("proforma.id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RIncaricoProformaSocieta> listaRelazione =(List<RIncaricoProformaSocieta>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RIncaricoProformaSocieta relazione:listaRelazione){
				 societaList.add(relazione.getSocieta());
				}	
			 return societaList.get(0);
		 }
	    else
	    	return null;
	}
	//DA PROCESSARE & PROCESSATE
		@Override
		public List<Societa> getListaSocietaProformaProcessate(boolean processato) throws Throwable {
			
			List<Societa> societaLista = new ArrayList<Societa>(0);
			StringBuffer stringaSql = new StringBuffer();
			stringaSql.append("select distinct rip.id_societa ");
			stringaSql.append("from r_incarico_proforma_societa rip ,societa s, proforma p ");
			stringaSql.append("where s.id=rip.id_societa  ");
			stringaSql.append("and p.id=rip.id_proforma ");
			stringaSql.append("and p.id_stato_proforma=(select sp.id from stato_proforma sp where sp.cod_gruppo_lingua='A' and sp.lang='IT') ");
			if(processato)
			stringaSql.append("and p.processato='T' "); //PROCESSATE IS NOT NULL	
			else
			stringaSql.append("and p.processato='F' "); //DA PROCESSARE IS NULL

		    societaLista = getHibernateTemplate().execute(new HibernateCallback<List<Societa>>() {
				@Override
				public List<Societa> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					@SuppressWarnings("unchecked")
					List<BigDecimal> queryResult = (List<BigDecimal>)sqlQuery.list();
					List<Societa> societaList = new ArrayList<Societa>();					
					for (BigDecimal id:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Societa.class ).add( Restrictions.eq("id", id.longValue()) );
						@SuppressWarnings("unchecked")
						Societa societa = (Societa) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						societaList.add((Societa) societa);
					}		
					return societaList;
				}
			});
		    if(societaLista.size() > 0)
		    	return societaLista;
		    else
		    	return null;
		}
		
		@Override
		public List<Societa> getListaSNAM_SRG_GNL_STOGIT() throws Throwable {
			DetachedCriteria criteria = DetachedCriteria.forClass(Societa.class);
			criteria.add(Restrictions.isNull("dataCancellazione"));
			
			//criteria.add(Restrictions.in("nome", new String[]{"Stogit S.p.A.","Snam S.p.A.","Gnl Italia S.p.A.","Snam Rete Gas S.p.A."}));
			
			Disjunction disj = Restrictions.disjunction();
			disj.add(Restrictions.ilike("nome", "Stogit S.p.A.", MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("nome", "Snam S.p.A.", MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("nome", "Gnl Italia S.p.A.", MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("nome", "Snam Rete Gas", MatchMode.ANYWHERE));
			disj.add(Restrictions.ilike("nome", "Snam ReteGas", MatchMode.ANYWHERE));
			
			criteria.add(disj);
			
			@SuppressWarnings("unchecked")
			List<Societa> lista =(List<Societa>) getHibernateTemplate().findByCriteria(criteria);	
			return lista;
		}

}
