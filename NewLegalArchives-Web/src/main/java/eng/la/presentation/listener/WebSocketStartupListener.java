package eng.la.presentation.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.glassfish.tyrus.server.Server;

import eng.la.presentation.websocket.WebSocketServer;

public class WebSocketStartupListener implements ServletContextListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WebSocketStartupListener.class);

	private Server server;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (logger.isDebugEnabled()) {
			logger.debug("contextInitialized(ServletContextEvent) - start"); //$NON-NLS-1$
		}

		server = WebSocketServer.runServer();

		
		if (logger.isDebugEnabled()) {
			logger.debug("contextInitialized(ServletContextEvent) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (logger.isDebugEnabled()) {
			logger.debug("contextDestroyed(ServletContextEvent) - start"); //$NON-NLS-1$
		}
 
		try{ 
			
			server.stop();
			
		}catch(Throwable e){
			logger.error("contextDestroyed(ServletContextEvent)", e); //$NON-NLS-1$

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("contextDestroyed(ServletContextEvent) - end"); //$NON-NLS-1$
		}
	}

}
