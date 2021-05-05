package eng.la.business.websocket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import eng.la.model.rest.Event;

public class WebSocketSubscriber {

	private Map<String, WebSocketListener> mappaClientSottoscritti = null;  
	
	private static WebSocketSubscriber _instance; 
	
	private WebSocketSubscriber() {
		mappaClientSottoscritti = new HashMap<String, WebSocketListener>();
	}
	
	public static WebSocketSubscriber getInstance(){
		if( _instance == null ){
			synchronized (WebSocketSubscriber.class) {
				if( _instance == null ){
					_instance = new WebSocketSubscriber();
				}
			}
		}
		return _instance;
	}
	
	public void subscribe(Session session, String userId){
		WebSocketListener listener = new WebSocketListener(session, userId); 
		mappaClientSottoscritti.put(session.getId(), listener );
	}
	
	public void unsubscribe(Session session){
		mappaClientSottoscritti.remove(session.getId());
	}
	
	public void inviaMessaggio(Event event){
		try{
			Collection<WebSocketListener> collection = mappaClientSottoscritti.values();
			if( collection != null ){
				for( WebSocketListener listener : collection ){
					if( listener.getUserId().equals(event.getUserId()) ){
						listener.sendMessage(event);
					}
				}
			} 
		}catch(Throwable e){
			e.printStackTrace();
		}
		
	}
	
}
