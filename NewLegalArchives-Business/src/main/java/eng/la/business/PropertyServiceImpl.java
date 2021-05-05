package eng.la.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Property;
import eng.la.model.view.PropertyView;
import eng.la.persistence.PropertyDAO;

@Service("propertyService")
public class PropertyServiceImpl extends BaseService<Property,PropertyView> implements PropertyService {

	@Autowired
	private PropertyDAO propertyDAO;

	public void setPropertyDao(PropertyDAO dao) {
		this.propertyDAO = dao;
	}

	@Override
	public String getValue(String key) throws Throwable {
		return propertyDAO.getValue(key);
	}

	@Override
	protected Class<Property> leggiClassVO() {
		return Property.class;
	}

	@Override
	protected Class<PropertyView> leggiClassView() {
		return PropertyView.class;
	}

}
