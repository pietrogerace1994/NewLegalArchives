package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.AffariSocietari;
import eng.la.model.RFascicoloMateria;
import eng.la.model.RSocietaAffari;
import eng.la.model.aggregate.AffariSocietariAggregate;
import eng.la.model.filter.AffariSocietariFilter;

@Component("affariSocietariDAO")
public class AffariSocietariDAOImpl extends HibernateDaoSupport implements AffariSocietariDAO, CostantiDAO {


	@Autowired
	public AffariSocietariDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<AffariSocietari> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class).addOrder(Order.asc("denominazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<AffariSocietari> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class).addOrder(Order.asc("denominazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public AffariSocietari leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		AffariSocietari affariSocietari = (AffariSocietari) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return affariSocietari;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("denominazione", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void cancella(long idAffariSocietari) throws Throwable {
		AffariSocietari voDB = leggi(idAffariSocietari);
		voDB.setDataUscita(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(AffariSocietari vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<AffariSocietari> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class).addOrder(Order.asc("denominazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("denominazione", nome, MatchMode.ANYWHERE));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public AffariSocietari inserisci(AffariSocietari vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<AffariSocietari> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("denominazione"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("denominazione", nome, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(nome);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Override
	public AffariSocietari leggi(long id) throws Throwable {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		AffariSocietari affariSocietari = (AffariSocietari) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return affariSocietari;
	}

	@Override
	public AffariSocietari leggiPerPagina(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class).add(Restrictions.eq("id", id));
		criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		AffariSocietari affariSocietari = (AffariSocietari) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return affariSocietari;
	}

	@Override
	public AffariSocietariAggregate searchPagedAffariSocietari(AffariSocietariFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable {

		List<AffariSocietari> returnList = null;
		AffariSocietariAggregate out = new AffariSocietariAggregate();
		if(filter != null){
			DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class); 
			if(filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc("codiceSocieta"));
				criteria.addOrder(Order.asc("id"));
			}else{
				criteria.addOrder(Order.desc("codiceSocieta"));
				criteria.addOrder(Order.desc("id"));
			}


			if(filter.getDenominazione() != null && !filter.getDenominazione().isEmpty()){
				criteria.add(Restrictions.ilike("denominazione", filter.getDenominazione(),MatchMode.ANYWHERE));
			}

			if(filter.getIdNazione() != null && filter.getIdNazione().longValue()>0){

				criteria.add(Restrictions.eq("nazione.id", filter.getIdNazione()));
			}

			if(filter.getModelloDiGovernance() != null && !filter.getModelloDiGovernance().isEmpty()){
				criteria.add(Restrictions.eq("modelloDiGovernance", filter.getModelloDiGovernance()));
			}

			if(filter.getQuotazione() != null && !filter.getQuotazione().isEmpty()){
				criteria.add(Restrictions.eq("quotazione", filter.getQuotazione()));
			}
			
			if(filter.getStorico() != null && !filter.getStorico().isEmpty()){
				if("NO".equals(filter.getStorico())){
					criteria.add(Restrictions.isNull("dataCancellazione"));
				}
			}
			else{
				criteria.add(Restrictions.isNull("dataCancellazione"));
			}
			
			
			if(listaSNAM_SRG_GNL_STOGIT != null && !listaSNAM_SRG_GNL_STOGIT.isEmpty()){
				
				if(filter.getGruppoSnam() != null && !filter.getGruppoSnam().isEmpty()){
					if("NO".equals(filter.getGruppoSnam())){
						criteria.add(Restrictions.not(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT)));
					}else{
						criteria.add(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT));
					}
				}
				else{
					criteria.add(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT));
				}
			}

			Long numeroTotaleElementi = countAffariSocietari(filter, listaSNAM_SRG_GNL_STOGIT);
			int elementiPerPagina = filter.getNumElementiPerPagina();
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);

			int numeroPagina = filter.getNumeroPagina();
			int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);

			out.setNumeroTotaleElementi(numeroTotaleElementi);

			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
			if (numeroTotaleElementi < indicePrimoElemento) {
				indicePrimoElemento = 0;
			}
			
			criteria.setFetchMode("RSocietaAffaris", FetchMode.JOIN);
			
			criteria.setProjection(Projections.projectionList()
					.add(Projections.distinct(Projections.property("id")))
					.add(Projections.property("id"), "id")
					.add(Projections.property("codiceSocieta"), "codiceSocieta"))
			.setResultTransformer(Transformers.aliasToBean(AffariSocietari.class)); 

			@SuppressWarnings("unchecked")
			List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina);
			if( lista != null ){
				returnList  = new ArrayList<AffariSocietari>();
				int index = 0;
				for( AffariSocietari f : lista ){
					if( index < elementiPerPagina){
						returnList.add(leggiPerPagina(f.getId()));					
					}
					index++;
				}
			}
		}

		out.setList(returnList);
		return out;
	}

	public Long countAffariSocietari(AffariSocietariFilter filter, List<Long> listaSNAM_SRG_GNL_STOGIT)  throws Throwable{

		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class); 

		if(filter.getDenominazione() != null && !filter.getDenominazione().isEmpty()){
			criteria.add(Restrictions.ilike("denominazione", filter.getDenominazione(),MatchMode.ANYWHERE));
		}

		if(filter.getIdNazione() != null && filter.getIdNazione().longValue()>0){

			criteria.add(Restrictions.eq("nazione.id", filter.getIdNazione()));
		}

		if(filter.getModelloDiGovernance() != null && !filter.getModelloDiGovernance().isEmpty()){
			criteria.add(Restrictions.eq("modelloDiGovernance", filter.getModelloDiGovernance()));
		}

		if(filter.getQuotazione() != null && !filter.getQuotazione().isEmpty()){
			criteria.add(Restrictions.eq("quotazione", filter.getQuotazione()));
		}

		if("NO".equals(filter.getStorico())){
			criteria.add(Restrictions.isNull("dataCancellazione"));
		}
		
		if(filter.getGruppoSnam() != null && !filter.getGruppoSnam().isEmpty()){
			if("NO".equals(filter.getGruppoSnam())){
				criteria.add(Restrictions.not(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT)));
			}else{
				criteria.add(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT));
			}
		}
		else{
			criteria.add(Restrictions.in("id", listaSNAM_SRG_GNL_STOGIT));
		}

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void inserisciRSocietaAffari(RSocietaAffari rSocietaAffari) throws Throwable {
		getHibernateTemplate().save(rSocietaAffari);
	}

	@Override
	public void cancellaRSocietaAffari(long idAffariSocietari) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RSocietaAffari.class);
		criteria.createAlias("societaAffari", "societaAffari");
		criteria.add(Restrictions.eq("societaAffari.id", idAffariSocietari));
		@SuppressWarnings("unchecked")
		List<RFascicoloMateria> lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RFascicoloMateria vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public List<RSocietaAffari> getRSocietaAffaris(long idAffariSocietari) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RSocietaAffari.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("societaAffari", "societaAffari");
		criteria.add(Restrictions.eq("societaAffari.id", idAffariSocietari));
		@SuppressWarnings("unchecked")
		List<RSocietaAffari> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;

	}
	
	@Override
	public List<AffariSocietari> getListaSNAM_SRG_GNL_STOGIT() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(AffariSocietari.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		// EVO OTTIMIZZAZIONE FILTRI & QUERIES RIDP6U9
		Disjunction disj = Restrictions.disjunction();
		disj.add(Restrictions.ilike("denominazione", "STOGIT S.P.A.", MatchMode.ANYWHERE));
		//disj.add(Restrictions.ilike("denominazione", "Snam S.p.A", MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("denominazione", "Snam", MatchMode.ANYWHERE));
		//disj.add(Restrictions.ilike("denominazione", "SNAM RETE GAS S.P.A.", MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("denominazione", "GNL ITALIA S.P.A.", MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("denominazione", "Cubogas", MatchMode.ANYWHERE));
		disj.add(Restrictions.ilike("denominazione", "Asset", MatchMode.ANYWHERE));
		criteria.add(disj);
		
		@SuppressWarnings("unchecked")
		List<AffariSocietari> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}



}
