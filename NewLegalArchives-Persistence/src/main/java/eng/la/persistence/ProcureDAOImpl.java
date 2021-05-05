package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Procure;
import eng.la.model.aggregate.ProcureAggregate;
import eng.la.model.filter.ProcureFilter;
import eng.la.util.HibernateDaoUtil;

@Component("procureDAO")
public class ProcureDAOImpl extends HibernateDaoSupport implements ProcureDAO, CostantiDAO {


	@Autowired
	public ProcureDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Procure> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class).addOrder(Order.asc("nomeProcuratore"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Procure> lista = (List<Procure>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<Procure> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class).addOrder(Order.asc("nomeProcuratore"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<Procure> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Procure leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		Procure procure = (Procure) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return procure;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void cancella(long idProcure) throws Throwable {
		Procure voDB = leggi(idProcure);
		voDB.setDataCancellazione(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(Procure vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<Procure> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class).addOrder(Order.asc("nomeProcuratore"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<Procure> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Procure inserisci(Procure vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<Procure> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nomeProcuratore"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nomeProcuratore", nome, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(nome);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<Procure> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Override
	public Procure leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Procure procure = (Procure) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return procure;
	}

	@Override
	public ProcureAggregate searchPagedProcure(ProcureFilter filter) throws Throwable {
		
		List<Procure> returnList = null;
		ProcureAggregate out = new ProcureAggregate();
		if(filter != null){
			DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class); 
			if(filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc("dataConferimento"));
			}else{
				criteria.addOrder(Order.desc("dataConferimento"));
			}
					
			criteria.add(Restrictions.isNull("dataCancellazione"));
			
			if(filter.getDataConferimentoDal() != null){
				criteria.add(Restrictions.ge("dataConferimento", filter.getDataConferimentoDal()));
			}
			if(filter.getDataConferimentoAl() != null){
				criteria.add(Restrictions.le("dataConferimento", filter.getDataConferimentoAl()));
			}
			if(filter.getDataRevocaDal() != null){
				criteria.add(Restrictions.ge("dataRevoca", filter.getDataRevocaDal()));
			}
			if(filter.getDataRevocaAl() != null){
				criteria.add(Restrictions.le("dataRevoca", filter.getDataRevocaAl()));
			}

			if(filter.getNomeProcuratore() != null && !filter.getNomeProcuratore().isEmpty()){
				criteria.add(Restrictions.ilike("nomeProcuratore", filter.getNomeProcuratore(), MatchMode.ANYWHERE));
			}
			if(filter.getNumeroRepertorio() != null && !filter.getNumeroRepertorio().isEmpty()){
				criteria.add(Restrictions.eq("numeroRepertorio", filter.getNumeroRepertorio()));
			}
			
			if(filter.getUtente() != null && !filter.getUtente().isEmpty()){
				criteria.add(Restrictions.ilike("utente", filter.getUtente(), MatchMode.ANYWHERE));
			}
			
			if(filter.getTipologia() != null && filter.getTipologia().longValue()>0){
				criteria.createAlias("tipoProcure", "tipoProcure");
				criteria.add(Restrictions.eq("tipoProcure.id", filter.getTipologia()));
			}
			
			if(filter.getIdSocieta() != null && filter.getIdSocieta().longValue()>0){
				criteria.createAlias("societa", "societa");
				criteria.add(Restrictions.eq("societa.id", filter.getIdSocieta()));
			}
			
			if(filter.getIdNotaio() != null && filter.getIdNotaio().longValue()>0){
				criteria.createAlias("notaio", "notaio");
				criteria.add(Restrictions.eq("notaio.id", filter.getIdNotaio()));
			}
			
			if(filter.getPosizioneOrganizzativa() != null && filter.getPosizioneOrganizzativa().longValue()>0){
				criteria.createAlias("posizioneOrganizzativa", "posizioneOrganizzativa");
				criteria.add(Restrictions.eq("posizioneOrganizzativa.id", filter.getPosizioneOrganizzativa()));
			}
			
			if(filter.getLivelloAttribuzioniI() != null && filter.getLivelloAttribuzioniI().longValue()>0){
				criteria.createAlias("livelloAttribuzioniI", "livelloAttribuzioniI");
				criteria.add(Restrictions.eq("livelloAttribuzioniI.id", filter.getLivelloAttribuzioniI()));
			}
			
			if(filter.getLivelloAttribuzioniII() != null && filter.getLivelloAttribuzioniII().longValue()>0){
				criteria.createAlias("livelloAttribuzioniII", "livelloAttribuzioniII");
				criteria.add(Restrictions.eq("livelloAttribuzioniII.id", filter.getLivelloAttribuzioniII()));
			}
			
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);

			
			Long numeroTotaleElementi = countProcure(filter);
			int elementiPerPagina = filter.getNumElementiPerPagina();
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);

			int numeroPagina = filter.getNumeroPagina();
			int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
			
			out.setNumeroTotaleElementi(numeroTotaleElementi);
			
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
			if (numeroTotaleElementi < indicePrimoElemento) {
				indicePrimoElemento = 0;
			}
			
			// HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 
			criteria.setProjection(Projections.projectionList()
			.add(Projections.distinct(Projections.property("id")))
			.add(Projections.property("id"), "id")
			.add(Projections.property("dataConferimento"), "dataConferimento") )
			.setResultTransformer(Transformers.aliasToBean(Procure.class)); 
//			criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

			
			@SuppressWarnings("unchecked")
			List<Procure> lista = (List<Procure>)getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina);
			if( lista != null ){
				returnList  = new ArrayList<Procure>();
				int index = 0;
				for( Procure f : lista ){
					if( index < elementiPerPagina){
						returnList.add(leggi(f.getId()));					
					}
					index++;
				}
			}
		}
		
		out.setList(returnList);
		return out;
	}
	
	public Long countProcure(ProcureFilter filter)  throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class); 
				
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(filter.getDataConferimentoDal() != null){
			criteria.add(Restrictions.ge("dataConferimento", filter.getDataConferimentoDal()));
		}
		if(filter.getDataConferimentoAl() != null){
			criteria.add(Restrictions.le("dataConferimento", filter.getDataConferimentoAl()));
		}
		if(filter.getDataRevocaDal() != null){
			criteria.add(Restrictions.ge("dataRevoca", filter.getDataRevocaDal()));
		}
		if(filter.getDataRevocaAl() != null){
			criteria.add(Restrictions.le("dataRevoca", filter.getDataRevocaAl()));
		}

		if(filter.getNomeProcuratore() != null && !filter.getNomeProcuratore().isEmpty()){
			criteria.add(Restrictions.ilike("nomeProcuratore", filter.getNomeProcuratore(), MatchMode.ANYWHERE));
		}
		if(filter.getNumeroRepertorio() != null && !filter.getNumeroRepertorio().isEmpty()){
			criteria.add(Restrictions.eq("numeroRepertorio", filter.getNumeroRepertorio()));
		}
		
		if(filter.getTipologia() != null && filter.getTipologia().longValue()>0){
			criteria.createAlias("tipoProcure", "tipoProcure");
			criteria.add(Restrictions.eq("tipoProcure.id", filter.getTipologia()));
		}
		
		if(filter.getIdSocieta() != null && filter.getIdSocieta().longValue()>0){
			criteria.createAlias("societa", "societa");
			criteria.add(Restrictions.eq("societa.id", filter.getIdSocieta()));
		}
		
		if(filter.getIdNotaio() != null && filter.getIdNotaio().longValue()>0){
			criteria.createAlias("notaio", "notaio");
			criteria.add(Restrictions.eq("notaio.id", filter.getIdNotaio()));
		}
		
		if(filter.getUtente() != null && !filter.getUtente().isEmpty()){
			criteria.add(Restrictions.ilike("utente", filter.getUtente(), MatchMode.ANYWHERE));
		}
		
		if(filter.getPosizioneOrganizzativa() != null && filter.getPosizioneOrganizzativa().longValue()>0){
			criteria.createAlias("posizioneOrganizzativa", "posizioneOrganizzativa");
			criteria.add(Restrictions.eq("posizioneOrganizzativa.id", filter.getPosizioneOrganizzativa()));
		}
		
		if(filter.getLivelloAttribuzioniI() != null && filter.getLivelloAttribuzioniI().longValue()>0){
			criteria.createAlias("livelloAttribuzioniI", "livelloAttribuzioniI");
			criteria.add(Restrictions.eq("livelloAttribuzioniI.id", filter.getLivelloAttribuzioniI()));
		}
		
		if(filter.getLivelloAttribuzioniII() != null && filter.getLivelloAttribuzioniII().longValue()>0){
			criteria.createAlias("livelloAttribuzioniII", "livelloAttribuzioniII");
			criteria.add(Restrictions.eq("livelloAttribuzioniII.id", filter.getLivelloAttribuzioniII()));
		}
		
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		 
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	@Override
	public List<Procure> leggiDaFascicolo(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Procure.class);
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Procure> lista = (List<Procure>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
