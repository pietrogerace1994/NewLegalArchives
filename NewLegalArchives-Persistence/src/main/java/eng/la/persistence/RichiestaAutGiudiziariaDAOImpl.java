package eng.la.persistence;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Documento;
import eng.la.model.RichAutGiud;
import eng.la.model.view.AutoritaGiudiziariaView;

@Component("richiestaAutGiudiziariaDAO")
public class RichiestaAutGiudiziariaDAOImpl extends HibernateDaoSupport implements RichiestaAutGiudiziariaDAO {
	
	@Autowired
	public RichiestaAutGiudiziariaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public RichAutGiud save(RichAutGiud richAutGiud) throws Throwable {
		getHibernateTemplate().save(richAutGiud);
		return richAutGiud;
	}
	

	@Override
	public RichAutGiud update(RichAutGiud richAutGiud) throws Throwable {
		getHibernateTemplate().update(richAutGiud);
		return richAutGiud;
	}
	
	@Override
	public void delete(Long id) throws Throwable {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		RichAutGiud richAutGiud = read(id);
		richAutGiud.setDataCancellazione(timestamp);
		getHibernateTemplate().update(richAutGiud);
		
		Documento documentoStep1 = richAutGiud.getDocumentoStep1();
		if(documentoStep1 != null){
			documentoStep1.setDataCancellazione(timestamp);
			getHibernateTemplate().update(documentoStep1);
		}
		
		Documento documentoStep2 = richAutGiud.getDocumentoStep2();
		if(documentoStep2 != null){
			documentoStep2.setDataCancellazione(timestamp);
			getHibernateTemplate().update(documentoStep2);
		}
		
		Documento documentoStep3 = richAutGiud.getDocumentoStep3();
		if(documentoStep3 != null){
			documentoStep3.setDataCancellazione(timestamp);
			getHibernateTemplate().update(documentoStep3);
		}
	}
	
	@Override
	public List<RichAutGiud> searchRichAutGiudByFilter(AutoritaGiudiziariaView autoritaGiudiziariaView) throws Throwable {
		List<RichAutGiud> out = null;
				
		if(autoritaGiudiziariaView != null){
			DetachedCriteria criteria = DetachedCriteria.forClass( RichAutGiud.class ).addOrder(Order.asc("societa.nome"))
					.addOrder(Order.desc("dataInserimento"));
			
			criteria.createAlias("societa", "societa");
			if (autoritaGiudiziariaView.getIdSocieta() != null){
				criteria.add(Restrictions.eq("societa.id", autoritaGiudiziariaView.getIdSocieta()));
			}
			
			if (autoritaGiudiziariaView.getTipologiaRichiestaCode() != null){
				criteria.createAlias("tipologiaRichiesta", "tipologiaRichiesta");
				criteria.add(Restrictions.eq("tipologiaRichiesta.id", autoritaGiudiziariaView.getTipologiaRichiestaCode()));
			}
			
			if (autoritaGiudiziariaView.getStatoRichiestaCode() != null){
				criteria.createAlias("statoRichAutGiud", "statoRichAutGiud");
				criteria.add(Restrictions.eq("statoRichAutGiud.id", autoritaGiudiziariaView.getStatoRichiestaCode()));
			}
			
			if (autoritaGiudiziariaView.getOggetto() != null && !autoritaGiudiziariaView.getOggetto().isEmpty()){
				criteria.add(Restrictions.ilike("oggetto", autoritaGiudiziariaView.getOggetto(), MatchMode.ANYWHERE));
			}
			
			if (autoritaGiudiziariaView.getFornitore() != null && !autoritaGiudiziariaView.getFornitore().isEmpty()){
				criteria.add(Restrictions.ilike("fornitore", autoritaGiudiziariaView.getFornitore(), MatchMode.ANYWHERE));
			}
			
			if (autoritaGiudiziariaView.getAutoritaGiudiziaria() != null && !autoritaGiudiziariaView.getAutoritaGiudiziaria().isEmpty()){
				criteria.add(Restrictions.ilike("autoritaGiudiziaria", autoritaGiudiziariaView.getAutoritaGiudiziaria(), MatchMode.ANYWHERE));
			}
			
			if (autoritaGiudiziariaView.getDataInserimento() != null && !autoritaGiudiziariaView.getDataInserimento().isEmpty()){
				Date dateIns = new SimpleDateFormat("dd/MM/yyyy").parse(autoritaGiudiziariaView.getDataInserimento());
				criteria.add(Restrictions.eq("dataInserimento", dateIns));
			}
			
			if (autoritaGiudiziariaView.getAnnoRichiesta() != null && !autoritaGiudiziariaView.getAnnoRichiesta().isEmpty()){
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, Integer.parseInt(autoritaGiudiziariaView.getAnnoRichiesta()));
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date firstDayOfYear = cal.getTime();
				
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DAY_OF_MONTH, 31);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 999);
				Date lastDayOfYear = cal.getTime();
				
				criteria.add(Restrictions.ge("dataInserimento", firstDayOfYear));
				criteria.add(Restrictions.le("dataInserimento", lastDayOfYear));
			}
			
			out = getHibernateTemplate().findByCriteria(criteria);
		}
		
		return out;
	}
	
	@Override
	public List<RichAutGiud> search(boolean isActive) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RichAutGiud.class ).addOrder(Order.asc("autoritaGiudiziaria"));
		if (isActive)
			criteria.add(Restrictions.isNull("dataCancellazione"));
		else
			criteria.add(Restrictions.isNotNull("dataCancellazione"));
		
		@SuppressWarnings("unchecked")
		List<RichAutGiud> out = getHibernateTemplate().findByCriteria(criteria);		
		return out;
	}
	
	@Override
	public RichAutGiud read(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RichAutGiud.class).add(Restrictions.eq("id", id));
		@SuppressWarnings("unchecked")
		RichAutGiud richAutGiud = (RichAutGiud) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));		
		return richAutGiud;
	}
	
} 

