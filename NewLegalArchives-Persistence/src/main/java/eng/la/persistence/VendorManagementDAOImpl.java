package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.VendorManagement;
import eng.la.util.DateUtil2;

@Component("vendorManagementDAO")
public class VendorManagementDAOImpl extends HibernateDaoSupport implements VendorManagementDAO, CostantiDAO {

	@Autowired
	public VendorManagementDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public VendorManagement getDataValutazioneMin(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class);
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		criteria.setProjection(Projections.min("dataValutazione"));
		@SuppressWarnings("unchecked")
		Date min = (Date) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		VendorManagement vo = new VendorManagement();
		vo.setDataValutazione(min);
		return vo;
	}
	
	public VendorManagement getDataValutazioneMax(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class);
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.addOrder(Order.asc("id"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		criteria.setProjection(Projections.max("dataValutazione"));
		@SuppressWarnings("unchecked")
		Date max = (Date) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		VendorManagement vo = new VendorManagement();
		vo.setDataValutazione(max);
		return vo;
	}
	
	@Override
	public  List<VendorManagement> leggiByIdIncarico(long IdIncarico) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		criteria.createAlias("incarico", "incarico");  
		criteria.add(Restrictions.eq("incarico.id", IdIncarico));
		
		@SuppressWarnings("unchecked")
		List<VendorManagement> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public  List<VendorManagement> leggiByIdIncaricoNew(long IdIncarico) throws Throwable {

		List<Date> semestre = DateUtil2.estraiDateSemestre();

		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		Date inizioSemestre = semestre.get(0);
		Date fineSemestre = semestre.get(1);

		criteria.createAlias("incarico", "incarico");  
		criteria.add(Restrictions.eq("incarico.id", IdIncarico));
		criteria.add(Restrictions.between("dataValutazione", inizioSemestre, fineSemestre));

		@SuppressWarnings("unchecked")
		List<VendorManagement> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<VendorManagement> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		@SuppressWarnings("unchecked")
		List<VendorManagement> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	public List<VendorManagement> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		@SuppressWarnings("unchecked")
		List<VendorManagement> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	public Long conta(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		
		criteria.setProjection(Projections.rowCount());
		
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	public Long conta(String lingua, Date date1, Date date2) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.between("dataValutazione", date1, date2));
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		
		criteria.setProjection(Projections.rowCount());
		
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
	
	public List<VendorManagement> leggi(String lingua, Date date1, Date date2) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).addOrder(Order.asc("id"));
		criteria.createAlias("statoVendorManagement", "statoVendorManagement", DetachedCriteria.INNER_JOIN);
		
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		criteria.add(Restrictions.between("dataValutazione", date1, date2));
		criteria.add(Restrictions.eq("statoVendorManagement.codGruppoLingua", CostantiDAO.VENDOR_MANAGEMENT_STATO_CONFERMATO));
		
		criteria.setFetchMode("incarico", FetchMode.JOIN); 
		
		@SuppressWarnings("unchecked")
		List<VendorManagement> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public VendorManagement leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(VendorManagement.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		VendorManagement vendorManagement = (VendorManagement) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return vendorManagement;
	}

	@Override
	public VendorManagement inserisci(VendorManagement vo) throws Throwable {
		
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	@Override
	public VendorManagement modifica(VendorManagement vo) throws Throwable {
		getHibernateTemplate().update(vo);
		return vo;
	}

	@Override
	public void cancella(long id) throws Throwable {
		VendorManagement vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
		
	}

}