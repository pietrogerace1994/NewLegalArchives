package eng.la.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.LetteraIncarico;

@Component("letteraIncaricoDAO")
public class LetteraIncaricoDAOImpl extends HibernateDaoSupport implements LetteraIncaricoDAO, CostantiDAO {
	@Autowired
	public LetteraIncaricoDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<LetteraIncarico> leggiLettereIncaricoPerId(List<Long> listaIdLettera) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LetteraIncarico.class ).add( Restrictions.in("id", listaIdLettera) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		@SuppressWarnings("unchecked")
		List<LetteraIncarico> letteraIncaricoList = getHibernateTemplate().findByCriteria(criteria);	
		return letteraIncaricoList; 
	}

	@Override
	public LetteraIncarico leggiLetteraIncarico(Long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LetteraIncarico.class ).add( Restrictions.idEq(id));
		@SuppressWarnings("unchecked")
		LetteraIncarico lettera = (LetteraIncarico) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		return lettera;
	}
	
	/**
	 * Restituisce l'ultimo numero di protocollo generato nell'ultimo anno dall'utente richiesto
	 * I numeri di protocollo vengono considerati nella forma NNNN/AA/UU, dove:
	 * NNNN è un valore intero riportato con 4 cifre
	 * AA è l'anno corrente riportato con 2 cifre
	 * @author MASSIMO CARUSO
	 * UU è la sigla dell'utente corrente
	 * @param nominativo il nominativo dell'utente richiesto
	 * @return l'ultimo numero di protocollo inserito dall'utente, 0 altrimenti
	 */
	@Override
	public int getUltimoNumeroProtocollo(String nominativo)  throws Throwable{
		String temp = "";
		int max = 0;
		int temp_max = 0;
		Date actual = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yy");
		DetachedCriteria criteria = DetachedCriteria.forClass( LetteraIncarico.class );
		criteria.add(Restrictions.like("legaleInterno",nominativo+"%"));
		criteria.add(Restrictions.like("protocollo","%/"+format.format(actual)+"/%"));
		
		List<LetteraIncarico> letteraIncaricoList = getHibernateTemplate().findByCriteria(criteria);
		if(letteraIncaricoList != null && letteraIncaricoList.size() > 0){
			for(LetteraIncarico lettera : letteraIncaricoList){
				temp = lettera.getProtocollo().replaceAll("\\D+", "");
				temp_max = Integer.parseInt(temp.substring(0, 4));
				max = max>temp_max? max : temp_max;
			}
		}
		
		return max;
	}
}
