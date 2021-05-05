package eng.la.business.websocket;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper; 

import eng.la.model.rest.AbstractRest;
import eng.la.model.rest.Event;
import eng.la.model.rest.StringRest;

public class WebSocketPublisher {
	private static WebSocketPublisher _instance;
	private String[] nodesUrlPublishEvent;
	private ObjectMapper mapper;

	private WebSocketPublisher() {
		InputStream is = null;
		try {
			is = WebSocketPublisher.class.getResourceAsStream("/config.properties");
			Properties p = new Properties();
			p.load(is);
			nodesUrlPublishEvent = p.getProperty("nodes.url.publish.event").split(";");
			mapper = new ObjectMapper(new JsonFactory());
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static WebSocketPublisher getInstance() {
		if (_instance == null) {
			synchronized (WebSocketPublisher.class) {
				if (_instance == null) {
					_instance = new WebSocketPublisher();
				}
			}
		}
		return _instance;
	}

	// METODO INVOCATO DAL CONTROLLER CHE INVIA IL MSG VS IL CLIENT
	public void sendEvent(Event event) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				WebSocketSubscriber.getInstance().inviaMessaggio(event);
			}
		});		
		t.run(); 
	}

	// PUBBLICA L'EVENTO SUI VARI NODI DEL CLUSTER
	public void publishEvent(Event event) {
		for (String nodeUri : nodesUrlPublishEvent) {
			try {
				publish(event, nodeUri);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private void publish(Event event, String url) throws Throwable {
		OutputStream os = null;
		ByteArrayInputStream is = null;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.addRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// connection.connect();
			os = connection.getOutputStream();
			String json = mapper.writeValueAsString(event);
			is = new ByteArrayInputStream(json.getBytes());
			IOUtils.copy(is, os);
			if (connection.getResponseCode() == 200) {
				System.out.println("evento inviato correttamente");
			} else {
				System.out.println("evento non inviato correttamente");
			}
		} finally {
			try {
				connection.disconnect();
			} catch (Throwable e) {

			}
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
	}

	public Event createEvent(String type, String message, String userId) {
		Event event = new Event();
		event.setUserId(userId);
		AbstractRest rest = new StringRest(message);
		event.setMessage(rest);
		event.setType(type);
		event.setUuid(UUID.randomUUID().toString());
		return event;
	}

	public Event createEvent(String type, AbstractRest rest, String userId) {
		try {
			Event event = new Event();
			event.setUserId(userId); 
			event.setMessage(rest);
			event.setType(type);
			event.setUuid(UUID.randomUUID().toString()); 
			return event;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}
