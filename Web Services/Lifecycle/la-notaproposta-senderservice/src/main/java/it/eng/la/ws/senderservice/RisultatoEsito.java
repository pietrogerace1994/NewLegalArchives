package it.eng.la.ws.senderservice;

import java.io.Serializable;
import java.sql.Blob;

public class RisultatoEsito implements Serializable
{
	private static final long serialVersionUID = 8755955215694672443L;
	private long stateid;
	private int inviata;
	private String returnMessage;
	private byte[] rFile;
	private String fileName;
	
	public RisultatoEsito() {}

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	public long getStateid() 
	{
		return stateid;
	}

	public void setStateid(long stateid) 
	{
		this.stateid = stateid;
	}

	public int getInviata() 
	{
		return inviata;
	}

	public void setInviata(int inviata) 
	{
		this.inviata = inviata;
	}

	public String getReturnMessage() 
	{
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) 
	{
		this.returnMessage = returnMessage;
	}

	public byte[] getrFile() 
	{
		return rFile;
	}

	public void setrFile(byte[] rFile) 
	{
		this.rFile = rFile;
	}
}
