package it.eng.laws.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.crypto.Data;

public class TibcoPecEntity implements Serializable 
{
	private static final long serialVersionUID = 326157732349248837L;

	private String UUId;
	private String LaId;
	private String PECMittente;
	private String PECDestinatario;
	private String PECOggetto;
	private String PECDataRicezione;
	
	public String getUUId() 
	{
		return UUId;
	}

	public void setUUId(String uUId) 
	{
		UUId = uUId;
	}

	public String getLaId() 
	{
		return LaId;
	}
	
	public void setLaId(String laId) 
	{
		LaId = laId;
	}
	
	public String getPECMittente() 
	{
		return PECMittente;
	}
	
	public void setPECMittente(String pECMittente) 
	{
		PECMittente = pECMittente;
	}
	
	public String getPECDestinatario() 
	{
		return PECDestinatario;
	}
	
	public void setPECDestinatario(String pECDestinatario) 
	{
		PECDestinatario = pECDestinatario;
	}
	
	public String getPECOggetto() 
	{
		return PECOggetto;
	}
	
	public void setPECOggetto(String pECOggetto) 
	{
		PECOggetto = pECOggetto;
	}
	
	public String getPECDataRicezione() 
	{
		return PECDataRicezione;
	}
	
	public void setPECDataRicezione(String pECDataRicezione) 
	{
		PECDataRicezione = pECDataRicezione;
	}

}
