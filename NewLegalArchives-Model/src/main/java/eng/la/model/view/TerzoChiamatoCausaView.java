package eng.la.model.view;

import eng.la.model.TerzoChiamatoCausa;

public class TerzoChiamatoCausaView extends BaseView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TerzoChiamatoCausa vo;
	private String nomeTerzoChiamatoCausa;
	private String tipoTerzoChiamatoCausa;
	private String legaleRiferimento;
	private String tipoTerzoChiamatoCausaCode;
	
	public TerzoChiamatoCausa getVo() {
		return vo;
	}

	public void setVo(TerzoChiamatoCausa vo) {
		this.vo = vo;
	}

	public String getLegaleRiferimento() {
		return legaleRiferimento;
	}

	public void setLegaleRiferimento(String legaleRiferimento) {
		this.legaleRiferimento = legaleRiferimento;
	}

	public String getNomeTerzoChiamatoCausa() {
		return nomeTerzoChiamatoCausa;
	}

	public void setNomeTerzoChiamatoCausa(String nomeTerzoChiamatoCausa) {
		this.nomeTerzoChiamatoCausa = nomeTerzoChiamatoCausa;
	}

	public String getTipoTerzoChiamatoCausa() {
		return tipoTerzoChiamatoCausa;
	}

	public void setTipoTerzoChiamatoCausa(String tipoTerzoChiamatoCausa) {
		this.tipoTerzoChiamatoCausa = tipoTerzoChiamatoCausa;
	}

	public String getTipoTerzoChiamatoCausaCode() {
		return tipoTerzoChiamatoCausaCode;
	}

	public void setTipoTerzoChiamatoCausaCode(String tipoTerzoChiamatoCausaCode) {
		this.tipoTerzoChiamatoCausaCode = tipoTerzoChiamatoCausaCode;
	}

	 
	
	
}
