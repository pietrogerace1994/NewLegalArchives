package it.eng.la.ws.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PROPERTIES database table.
 * 
 */
@Entity
@Table(name="PROPERTIES")
@NamedQueries({
    @NamedQuery(name="Property.findByKey",
                query="SELECT r FROM Property r where r.key = :key")
})
public class Property implements Serializable {

	private static final long serialVersionUID = 3279374723857876391L;

	@Id
	@Column(name="\"KEY\"")
	private String key;

	@Column(name="\"VALUE\"")
	private String value;

	public Property() {
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}