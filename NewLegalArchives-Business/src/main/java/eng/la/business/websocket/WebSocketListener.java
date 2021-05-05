package eng.la.business.websocket;

import javax.websocket.Session;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class WebSocketListener  {
	private Session session;
	private String userId;
	private ObjectMapper mapper;
	
	public WebSocketListener(Session session, String userId) {
		this.session = session;
		this.userId = userId;
		mapper = new ObjectMapper(new JsonFactory());
	}
	
	public void sendMessage(Object o)throws Throwable{
		System.out.println("sendMessage >>> "+  session.getId() + " o: "+ o);
		String json =  mapper.writeValueAsString(o);
		session.getAsyncRemote().sendObject(json); 
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
