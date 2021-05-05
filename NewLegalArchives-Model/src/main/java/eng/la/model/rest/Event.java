package eng.la.model.rest;
 
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement(namespace = AbstractRest.NAMESPACE) 
public class Event {
	private String uuid;
	private String type;
	private String userId;
	
	private AbstractRest message;
	
	public Event() {
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
 
	public void setMessage(AbstractRest message) {
		this.message = message;
	}
 
	public AbstractRest getMessage() {
		return message;
	}

}
