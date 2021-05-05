package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Property;

@Component("propertyDAO")
public class PropertyDAOImpl extends HibernateDaoSupport implements PropertyDAO {

	@Autowired
	public PropertyDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public String getValue(String key) throws Throwable {
		getHibernateTemplate().clear();
		@SuppressWarnings("unchecked")
		List<Property> list = getHibernateTemplate().findByNamedQueryAndNamedParam("Property.findByKey", "key", key);
		
		Property prop = list.iterator().next();
		return prop.getValue();
	}


}
