package eng.la.business.jms;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import eng.la.business.mail.EmailNotificationServiceImpl;
import weblogic.jms.extensions.WLMessageProducer;
 
public class QueuePublisher {


	private static QueuePublisher _instance;
	
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender queueSender;
	private Queue queue;

	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public String connectionFactoryJndi = "jms/connectionFactory";
	public String queueJndi = "jms/emailQueue";
	public String serviceUrl = "t3://127.0.0.1:7001"; 
	
	private static final Logger logger = Logger.getLogger(QueuePublisher.class);
	

	
	public QueuePublisher() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("config");
			this.connectionFactoryJndi = bundle.getString("jms.connectionFactory");
			this.queueJndi = bundle.getString("jms.queue");
			this.serviceUrl = bundle.getString("jms.serviceUrl");
			InitialContext ic = getInitialContext(serviceUrl);
			init(ic, queueJndi);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static QueuePublisher getInstance(){
		if( _instance == null ){
			synchronized (QueuePublisher.class) {
				if( _instance == null ){
					_instance = new QueuePublisher();
				}
			}
		}
		
		return _instance;
	}

	public void sendMessage(Serializable serializable) throws Throwable{
		logger.debug("Send message");
		Message msg = qsession.createObjectMessage(serializable);
		queueSender.send(msg);
	}

	public void sendMessage(Serializable serializable, Date dataInvio) throws Throwable{ 
		logger.debug("Send message");
		Message msg = qsession.createObjectMessage(serializable);		 
		weblogic.jms.extensions.WLMessageProducer wlmp = (WLMessageProducer) queueSender;
		wlmp.setTimeToDeliver(dataInvio.getTime()-System.currentTimeMillis());
		wlmp.send(msg);		
	}
	
	public void init(Context ctx, String queueName) throws NamingException, JMSException { 
		
		try {
			qconFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryJndi);
			qcon = qconFactory.createQueueConnection();
			qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = (Queue) ctx.lookup(queueName);
			queueSender = qsession.createSender(queue);
			qcon.start();
			logger.debug("Connessione alla coda completata");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static InitialContext getInitialContext(String url) throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}

}
