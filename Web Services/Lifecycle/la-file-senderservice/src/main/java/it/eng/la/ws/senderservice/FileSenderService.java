package it.eng.la.ws.senderservice;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface FileSenderService 
{
	public void init();
	
	public void doTask(Timer timer);
}
