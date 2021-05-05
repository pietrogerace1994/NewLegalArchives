package eng.la.presentation.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import eng.la.business.websocket.WebSocketSubscriber;
 

@ServerEndpoint(value = "/notifiche/dashboard/{userid}")
public class WebSocketServerEndpoint {
	
	@OnOpen
    public void onOpen(Session session) {
	 
		System.out.println("Nuova sessione aperta col websocket sessionId: "+session.getId()+"");
		
    	subscribe(session);
		 
    }
 
    private void subscribe(Session session) { 

		try { 
			String requestUri = session.getRequestURI().toString();
			String userId = getUserId(requestUri);
			System.out.println("Avvio sottoscrizione della sessionId: "+session.getId()+" ");
			WebSocketSubscriber.getInstance().subscribe(session, userId);  
			System.out.println("Completata sottoscrizione della sessionId: "+session.getId()+" ");
		} catch (Throwable e) { 
			e.printStackTrace();
		}
 
	}

	private String getUserId(String requestUri) {
		return requestUri.substring(requestUri.lastIndexOf("/")+1, requestUri.length());
	}

	@OnMessage
    public String onMessage(String message, Session session) { 
		System.out.println("OnMessage - sessionId: "+session.getId()+" - messaggio: "+ message);
		 
        return message;
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) { 
        unsubscribe(session); 
    }
    
	private void unsubscribe(Session session) {
		try { 
			System.out.println("Cancello sottoscrizione della sessionId: "+session.getId()+"");
			//String requestUri = session.getRequestURI().toString();
			//String userId = getUserId(requestUri);
			WebSocketSubscriber.getInstance().unsubscribe(session);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
 
	@OnError
    public void OnError(Throwable w) { 
		System.out.println("OnError" + w); 
	 
    }

}
