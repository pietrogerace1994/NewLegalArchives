package it.eng.la.ws.service;

import it.eng.laws.entity.TibcoPecEntity;
import it.eng.laws.entity.UtentePec;
import it.eng.laws.entity.UtenteUseridPec;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.mail.PasswordAuthentication;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless
public class TibcoPecServiceBean implements TibcoPecService
{
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
	
	@PersistenceContext(unitName="La-PU")
	EntityManager emLa;
	
	protected final static Logger GENERAL_LOGGER = Logger.getLogger(TibcoPecServiceBean.class.getName());
		
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public String insert(TibcoPecEntity c) 
	{
		GENERAL_LOGGER.debug("START Metodo insert");
		GENERAL_LOGGER.debug("TIBCO PEC - UUID: "+c.getUUId());
		GENERAL_LOGGER.debug("TIBCO PEC - pecMittente: "+c.getPECMittente());
		GENERAL_LOGGER.debug("TIBCO PEC - pecDestinatario: "+c.getPECDestinatario());
		
		String returnMessage = "";
		UtentePec up = new UtentePec();
		UtenteUseridPec uup =new UtenteUseridPec();
		
		/*
		try
		{
			/**********Query UtenteUseridPec**************
			uup = (UtenteUseridPec) emLa.createNamedQuery("UtenteUseridPec.findByMail").setParameter("mail", c.getIdMail()).getSingleResult();
		} 
		catch (Exception e) 
		{
			printExec(e);
			GENERAL_LOGGER.debug("E-mail non presente.");
			return "E-mail non presente.\n\n";
		}
		*/
		
		/**********Query UtenteUseridPec***************/
		List<UtenteUseridPec> listUUP = new ArrayList<UtenteUseridPec>();
		listUUP = emLa.createNamedQuery("UtenteUseridPec.findByMail").setParameter("mail", c.getPECDestinatario()).getResultList();
		
		/*******Query UtentePec*******/
		List<UtentePec> listUP = new ArrayList<UtentePec>();
		listUP  =  emLa.createNamedQuery("UtentePec.findByIdUtente").setParameter("UUId", c.getUUId()).getResultList();
		
		GENERAL_LOGGER.debug("c.getUuId(): "+c.getUUId());
		
		if(c.getUUId().equals("")||c.getUUId()==null)
		{
			return "UUID non può essere vuoto!";
		}
		
		if(c.getLaId().equals("")||c.getLaId()==null)
		{
			return "LAID non può essere vuoto!";
		}
		
		if(c.getPECDestinatario().equals("")||c.getPECDestinatario()==null)
		{
			return "E-mail destinatario non può essere vuoto!";
		}

		/*Matcher dest = checkMailDest(c);
        if (!(dest.find()))
        {
            return "Formato e-mail destinatario non corretto!";
        }*/
        
    	if(listUUP.size()==0)
		{
			return "E-mail non presente!";
		}
		
		if(c.getPECMittente().equals("")||c.getPECMittente()==null)
		{
			return "E-mail mittente non può essere vuoto!";
		}
		
    	/*Matcher mitt = checkMailMitt(c);
        if (!(mitt.find()))
        {
            return "Formato e-mail mittente non corretto!";
        }*/
		
		if(c.getPECDataRicezione().equals("")||c.getPECDataRicezione()==null)
		{
			return "Data ricezione non può essere vuoto!";
		}
	
		/*try 
		{ 
			checkData(c, up); 
		} 
		catch (ParseException e1) 
		{
			e1.printStackTrace();
			return "Formato data non corretto";
		}*/
		
		gestioneCancellato(listUP);
		up.setPecDataRicezione(c.getPECDataRicezione());
		up.setUUId(c.getUUId());
        up.setPecDestinatario(c.getPECDestinatario());
        up.setPecMittente(c.getPECMittente());
		up.setData(new Date());
		up.setPecLaId(c.getLaId());
		up.setPecOggetto(c.getPECOggetto());
		userIdMultipli(up, listUUP);  //userId
		
		try 
		{
			emLa.persist(up);
		} 
		catch (Exception e) 
		{
			printExec(e);
			GENERAL_LOGGER.debug("Impossibile effettuare l'aggiornamento.");
			return "Impossibile effettuare l'aggiornamento\n\n";
		}	
		GENERAL_LOGGER.debug("END Metodo insert");
	return returnMessage;
	}

	/*
	*//**************controllo formato pecDataRicezione*************//*
	private void checkData(TibcoPecEntity c, UtentePec up) throws ParseException 
	{
		dateFormat.setLenient(false);
		String date = c.getPECDataRicezione();
		dateFormat.parse(date);
		up.setPecDataRicezione(date);
	}

	*//***********controllo formato e-mail destinatario************//*
	private Matcher checkMailDest(TibcoPecEntity c) 
	{
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
        Pattern p = Pattern.compile(emailPattern);
        Matcher dest = p.matcher(c.getPECDestinatario());
		return dest; 
	}
	
	*//***********controllo formato e-mail mittente************//*
	private Matcher checkMailMitt(TibcoPecEntity c) 
	{
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
        Pattern p = Pattern.compile(emailPattern);
        Matcher mitt = p.matcher(c.getPECMittente());
		return mitt; 
	}*/

	/*******controllo campo cancellato in UtentePec********/
	private void gestioneCancellato(List<UtentePec> listUP) 
	{
		for(int i=0;i<listUP.size();i++)
		{
			UtentePec a = listUP.get(i);
			a.setCancellato(1); 
			emLa.persist(a);
		}
	}

	/******controllo e inserimento userId multipli in UtentePec*****/
	private void userIdMultipli(UtentePec up, List<UtenteUseridPec> listUUP) 
	{
		String x1 = null;
		for(int j=0;j<listUUP.size();j++)
		{
			UtenteUseridPec usermultipli=listUUP.get(j);
			if(j==0)
			{
				x1=usermultipli.getUserId();
			}
			if(j>0)
			{
				x1 = x1+", "+usermultipli.getUserId();
			}
		}
		up.setUserId(x1);
	}
	
    public static Date getDateTime(String date) throws ParseException 
    {
        return new Date(dateTimeFormat.parse(date).getTime());
    }
	
    public static void printExec(Exception e)
    {
    	StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		GENERAL_LOGGER.error("Exception: "+exceptionAsString);
    }
    
    private static class Authenticator extends javax.mail.Authenticator 
    {
        private PasswordAuthentication authentication;

        public Authenticator(String username, String password) 
        {
        	authentication = new PasswordAuthentication(username, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() 
        {
        	return authentication;
        }
    }
}