package eng.la.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Articolo;

@Component("articoloDAO")
public class ArticoloDAOImpl extends HibernateDaoSupport implements ArticoloDAO, CostantiDAO {

	@Autowired
	public ArticoloDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Map<Long, List<Articolo>> findArticoli(String titolo,int anno,int elementiPerPagina, int paginaNumenro, String categoria, String sottocategoria) throws ParseException {
		Map<Long, List<Articolo>> map=new HashedMap();
		Long totaleElementi = this.countArticoli(titolo, anno, categoria, sottocategoria);

		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class)
				.addOrder(Order.desc("dataCreazione"));
		
		// se non arrivo dal Search -> aziono il filtro per anno
		if(titolo==null || titolo.trim().equals("")) 
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		 
		if(titolo!=null && !titolo.trim().equals("")) {
			Criterion cri1 = Restrictions.or(Restrictions.ilike("contenuto", titolo.toLowerCase(), MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.ilike("oggetto", titolo.toLowerCase(), MatchMode.ANYWHERE),
							Restrictions.ilike("contenutoBreve", titolo.toLowerCase(), MatchMode.ANYWHERE)));
			criteria.add(cri1);
		}
		
		if (categoria != null) {
			criteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
		}
		if (sottocategoria != null) {
			criteria.add(Restrictions.eq("sottoCategoria.id", Long.parseLong(sottocategoria)));
		}
		
		int indiceStrat = elementiPerPagina * (paginaNumenro - 1);
		if (totaleElementi.intValue() < indiceStrat) {
			indiceStrat = 0;
		}
		
		List<Articolo> listAll =getHibernateTemplate().findByCriteria(criteria, indiceStrat, indiceStrat+elementiPerPagina);
		map.put(totaleElementi, listAll);
		return map;
	}

	@Override
	public long countArticoli(String titolo, int anno, String categoria, String sottocategoria) throws ParseException {
		if(anno==0)
		{
			anno = Calendar.getInstance().get(Calendar.YEAR);
		}
		String dataStartString="01/01/"+anno;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart= dateFormat.parse(dataStartString);
		
		String dataEndString="31/12/"+anno;
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
		Date dateEnd= dateFormatEnd.parse(dataEndString);
		DetachedCriteria criteria = DetachedCriteria.forClass(Articolo.class)
				.addOrder(Order.desc("dataCreazione"));
		// se non arrivo dal Search -> aziono il filtro per anno
		if(titolo==null || titolo.trim().equals("")) 
		criteria.add(Restrictions.between("dataCreazione", dateStart,dateEnd));
		 
		if(titolo!=null && !titolo.trim().equals("")) {
			Criterion cri1 = Restrictions.or(Restrictions.ilike("contenuto", titolo.toLowerCase(), MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.ilike("oggetto", titolo.toLowerCase(), MatchMode.ANYWHERE),
							Restrictions.ilike("contenutoBreve", titolo.toLowerCase(), MatchMode.ANYWHERE)));
			criteria.add(cri1);
		}
		if (categoria != null) {
			criteria.add(Restrictions.eq("categoria.id", Long.parseLong(categoria)));
		}
		if (sottocategoria != null) {
			criteria.add(Restrictions.eq("sottoCategoria.id", Long.parseLong(sottocategoria)));
		}
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
}
