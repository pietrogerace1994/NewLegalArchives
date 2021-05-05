package eng.la.presentation.websocket;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

public class WebSocketServer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebSocketServer.class);

	public static Server runServer() {
		if (logger.isDebugEnabled()) {
			logger.debug("runServer() - start"); //$NON-NLS-1$
		}
		
		String host = null;
		String context = null;
		int port = 0;
		
		InputStream is = null;
		try {
	 
			host = System.getProperty("websocket.host");
			context = System.getProperty("websocket.context"); 
			port =  NumberUtils.toInt( System.getProperty("websocket.port") );
			System.out.println("Avvio web socket server: "+ host + ":" + port + "/" + context);
			Server server = new Server(host, port, context, null, WebSocketServerEndpoint.class);
			
			server.start();
			logger.info("Web socket server STARTED");

			if (logger.isDebugEnabled()) {
				logger.debug("runServer() - end"); //$NON-NLS-1$
			}
			return server;
		} catch (Throwable e) {
			logger.error("runServer()", e); //$NON-NLS-1$
 
			logger.error("Web socket server NOT STARTED: "+ e); 
		} finally {
			IOUtils.closeQuietly(is);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("runServer() - end"); //$NON-NLS-1$
		}
		return null; 
 
	}
}
