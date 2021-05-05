package eng.la.model.rest;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using=EventDeserializer.class) 
public class AbstractRest {
	
	protected static final String NAMESPACE = "rest.la.eng";
	private Date dataCorrente = new Date();
	private Class type;
	
	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Date getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(Date dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	
}
