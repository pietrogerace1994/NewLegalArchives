package eng.la.business.jms;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eng.la.business.EmailService;
import eng.la.model.rest.MailingListRest;

public class QueueMessageListener implements MessageListener {

	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueReceiver qreceiver;
	private Queue queue;
	private EmailService emailService;
	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public String connectionFactoryJndi = "jms/connectionFactory";
	public String queueJndi = "jms/emailQueue";
	public String serviceUrl = "t3://127.0.0.1:8002"; 
	public String from = "test@test.it"; 
	
	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void onMessage(Message msg) {
		try {
			
			System.out.println("Message Received: " + msg);
			ObjectMessage objectMessage = (ObjectMessage) msg;
			MailingListRest mailingListRest = (MailingListRest) objectMessage.getObject();
			
			System.out.println("Message Received: " + mailingListRest);
			emailService.sendEmail(from, mailingListRest.getEmail(), mailingListRest.getOggettoMail(), mailingListRest.getContenutoMail());
			//emailService.sendEmailWithCID(from, mailingListRest.getEmail(), mailingListRest.getOggettoMail(), mailingListRest.getContenutoMail(), mailingListRest.getUrlImage(), mailingListRest.getFiles());
			
		} catch ( Throwable e) {
			e.printStackTrace();
			System.err.println("An exception occurred: " + e.getMessage());
		}
	}

	public QueueMessageListener() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("config");
			this.connectionFactoryJndi = bundle.getString("jms.connectionFactory");
			this.queueJndi = bundle.getString("jms.queue");
			this.serviceUrl = bundle.getString("jms.serviceUrl");
			this.from = bundle.getString("jms.email.from");
			InitialContext ic = getInitialContext(serviceUrl);
			init(ic, queueJndi);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void init(Context ctx, String queueName) throws NamingException, JMSException { 
		
		qconFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryJndi);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(queueName);
		qreceiver = qsession.createReceiver(queue);
		qreceiver.setMessageListener(this);
		qcon.start();
	}

	private static InitialContext getInitialContext(String url) throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}
}