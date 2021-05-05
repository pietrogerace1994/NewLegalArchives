package eng.la.model.rest;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailingListRest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 245069665315463666L;
	/**
	 * 
	 */
	
	private String nominativo;
	private String email;
	private String categoria;
	private long idrubrica;
	private String oggettoMail;
	private String contenutoMail;
	private byte[] image;
	private List<String> urlImage;
	private List<File> files;
	
	public MailingListRest(){}
	

	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}

	




	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}


	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}


	/**
	 * @return the urlImage
	 */
	public List<String> getUrlImage() {
		return urlImage;
	}


	/**
	 * @param urlImage the urlImage to set
	 */
	public void setUrlImage(List<String> urlImage) {
		this.urlImage = urlImage;
	}


	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public long getIdrubrica() {
		return idrubrica;
	}

	public void setIdrubrica(long idrubrica) {
		this.idrubrica = idrubrica;
	}

	public String getOggettoMail() {
		return oggettoMail;
	}

	public void setOggettoMail(String oggettoMail) {
		this.oggettoMail = oggettoMail;
	}

	public String getContenutoMail() {
		return contenutoMail;
	}

	public void setContenutoMail(String contenutoMail) {
		this.contenutoMail = contenutoMail;
	}


	@Override
	public String toString() {
		return "MailingListRest [nominativo=" + nominativo + ", email=" + email + ", categoria=" + categoria
				+ ", idrubrica=" + idrubrica + ", oggettoMail=" + oggettoMail
				+ "]";
	}
	
	

}
