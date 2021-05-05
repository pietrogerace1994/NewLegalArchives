package it.eng.laws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the UTENTE_PEC database table.
 * 
 */
@Entity
@Table(name="UTENTE_USERID_PEC")
@NamedQuery(name = "UtenteUseridPec.findByMail", query = "SELECT uup FROM UtenteUseridPec uup WHERE uup.mail = :mail")
public class UtenteUseridPec implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="UTENTE_PEC_CODE_GENERATOR", sequenceName="UTENTE_PEC_CODE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="UTENTE_PEC_CODE_GENERATOR")
	private long id;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="EMAIL")
	private String mail;

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getUserId() 
	{
		return userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getMail() 
	{
		return mail;
	}

	public void setMail(String mail) 
	{
		this.mail = mail;
	}
	
}