package eng.la.model.rest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CriterioRest {
	
	private long id;
	private BigDecimal peso;
	private String asseDescrizione;
	private String asseDescrizioneShort;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	public String getAsseDescrizione() {
		return asseDescrizione;
	}
	public void setAsseDescrizione(String asseDescrizione) {
		this.asseDescrizione = asseDescrizione;
	}
	public String getAsseDescrizioneShort() {
		return asseDescrizioneShort;
	}
	public void setAsseDescrizioneShort(String asseDescrizioneShort) {
		this.asseDescrizioneShort = asseDescrizioneShort;
	}
	
	
	
}
