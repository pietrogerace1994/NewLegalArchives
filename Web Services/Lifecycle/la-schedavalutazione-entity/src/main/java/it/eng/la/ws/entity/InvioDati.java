package it.eng.la.ws.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="INVIO_DATI")
public class InvioDati implements Serializable 
{
	@Id
	@Column(name="TIPO")
	private String tipo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ESECUZIONE")
	private Date dataEsecuzione;

	public InvioDati() 
	{}

	public String getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}

	public Date getDataEsecuzione() 
	{
		return this.dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) 
	{
		this.dataEsecuzione = dataEsecuzione;
	}
}