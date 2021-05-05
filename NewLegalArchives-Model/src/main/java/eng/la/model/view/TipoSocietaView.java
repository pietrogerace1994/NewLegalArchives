package eng.la.model.view;

import java.util.Date;
import eng.la.model.TipoSocieta;

public class TipoSocietaView extends BaseView {

	private static final long serialVersionUID = 1L;
	private TipoSocieta vo;
	
	private String codGruppoLingua;
	private Date dataCancellazione;
	private String descrizione;
	private String lang;
	
	public TipoSocietaView() {
	}

	public void setVo(TipoSocieta vo) {
		this.vo = vo;
	}

	public TipoSocieta getVo() {
		return vo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
}
