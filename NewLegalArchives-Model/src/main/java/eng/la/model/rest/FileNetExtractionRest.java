package eng.la.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileNetExtractionRest {
	private long id;
	private String nomeIncarico;
	private String nomeFascicolo; 
	private String owner;
	private String dataAutorizzazione; 
	private List<String> files = new ArrayList<String>();
	private String conteggio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeIncarico() {
		return nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}
 
	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getConteggio() {
		return conteggio;
	}

	public void setConteggio(String conteggio) {
		this.conteggio = conteggio;
	}
}
