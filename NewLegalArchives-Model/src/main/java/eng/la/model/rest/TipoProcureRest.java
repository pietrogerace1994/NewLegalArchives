package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoProcureRest {
	private long id;
	private List<String> tipoProcureDesc;
	private String codGruppoLingua;
	private String descrizione;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getTipoProcureDesc() {
		return tipoProcureDesc;
	}

	public void setTipoProcureDesc(List<String> tipoProcureDesc) {
		this.tipoProcureDesc = tipoProcureDesc;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	
}
