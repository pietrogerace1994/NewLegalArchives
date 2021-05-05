package eng.la.model.view;

import java.util.List;

import eng.la.model.Incarico;

public class CollegioArbitraleView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Incarico vo;

	public Incarico getVo() {
		return vo;
	}

	public void setVo(Incarico vo) {
		this.vo = vo;
	}

	private List<ProfessionistaEsternoView> listaProfessionista;
	private String statoCollegioArbitrale;
	private String statoCollegioArbitraleCode;	

	private String dataAutorizzazione;
	private String dataRichiestaAutorizzazione;
	
	private Long collegioArbitraleId;
	private IncaricoView incaricoRiferimento;
	private Long incaricoRiferimentoId;
	private String nomeCollegioArbitrale;
	private Long arbitroDiParteId;
	private ProfessionistaEsternoView arbitroDiParte;
	private String nominativoArbitroControparte;
	private String indirizzoArbitroControparte;
	private String denominazioneStudioArbitroControparte;
	private String nominativoArbitroSegretario;
	private String indirizzoArbitroSegretario;
	private String denominazioneStudioArbitroSegretario;
	private String nominativoArbitroPresidente;
	private String indirizzoArbitroPresidente;
	private String denominazioneStudioArbitroPresidente;

	public Long getIncaricoRiferimentoId() {
		return incaricoRiferimentoId;
	}

	public void setIncaricoRiferimentoId(Long incaricoRiferimentoId) {
		this.incaricoRiferimentoId = incaricoRiferimentoId;
	}

	public String getStatoCollegioArbitrale() {
		return statoCollegioArbitrale;
	}

	public void setStatoCollegioArbitrale(String statoCollegioArbitrale) {
		this.statoCollegioArbitrale = statoCollegioArbitrale;
	}

	public String getStatoCollegioArbitraleCode() {
		return statoCollegioArbitraleCode;
	}

	public void setStatoCollegioArbitraleCode(String statoCollegioArbitraleCode) {
		this.statoCollegioArbitraleCode = statoCollegioArbitraleCode;
	}

	public String getNomeCollegioArbitrale() {
		return nomeCollegioArbitrale;
	}

	public void setNomeCollegioArbitrale(String nomeCollegioArbitrale) {
		this.nomeCollegioArbitrale = nomeCollegioArbitrale;
	}

	public Long getArbitroDiParteId() {
		return arbitroDiParteId;
	}

	public void setArbitroDiParteId(Long arbitroDiParteId) {
		this.arbitroDiParteId = arbitroDiParteId;
	}

	public ProfessionistaEsternoView getArbitroDiParte() {
		return arbitroDiParte;
	}

	public void setArbitroDiParte(ProfessionistaEsternoView arbitroDiParte) {
		this.arbitroDiParte = arbitroDiParte;
	}

	public String getNominativoArbitroControparte() {
		return nominativoArbitroControparte;
	}

	public void setNominativoArbitroControparte(String nominativoArbitroControparte) {
		this.nominativoArbitroControparte = nominativoArbitroControparte;
	}

	public String getIndirizzoArbitroControparte() {
		return indirizzoArbitroControparte;
	}

	public void setIndirizzoArbitroControparte(String indirizzoArbitroControparte) {
		this.indirizzoArbitroControparte = indirizzoArbitroControparte;
	}

	public String getDenominazioneStudioArbitroControparte() {
		return denominazioneStudioArbitroControparte;
	}

	public void setDenominazioneStudioArbitroControparte(String denominazioneStudioArbitroControparte) {
		this.denominazioneStudioArbitroControparte = denominazioneStudioArbitroControparte;
	}

	public String getNominativoArbitroSegretario() {
		return nominativoArbitroSegretario;
	}

	public void setNominativoArbitroSegretario(String nominativoArbitroSegretario) {
		this.nominativoArbitroSegretario = nominativoArbitroSegretario;
	}

	public String getIndirizzoArbitroSegretario() {
		return indirizzoArbitroSegretario;
	}

	public void setIndirizzoArbitroSegretario(String indirizzoArbitroSegretario) {
		this.indirizzoArbitroSegretario = indirizzoArbitroSegretario;
	}

	public String getDenominazioneStudioArbitroSegretario() {
		return denominazioneStudioArbitroSegretario;
	}

	public void setDenominazioneStudioArbitroSegretario(String denominazioneStudioArbitroSegretario) {
		this.denominazioneStudioArbitroSegretario = denominazioneStudioArbitroSegretario;
	}

	public String getNominativoArbitroPresidente() {
		return nominativoArbitroPresidente;
	}

	public void setNominativoArbitroPresidente(String nominativoArbitroPresidente) {
		this.nominativoArbitroPresidente = nominativoArbitroPresidente;
	}

	public String getIndirizzoArbitroPresidente() {
		return indirizzoArbitroPresidente;
	}

	public void setIndirizzoArbitroPresidente(String indirizzoArbitroPresidente) {
		this.indirizzoArbitroPresidente = indirizzoArbitroPresidente;
	}

	public String getDenominazioneStudioArbitroPresidente() {
		return denominazioneStudioArbitroPresidente;
	}

	public void setDenominazioneStudioArbitroPresidente(String denominazioneStudioArbitroPresidente) {
		this.denominazioneStudioArbitroPresidente = denominazioneStudioArbitroPresidente;
	}

	public IncaricoView getIncaricoRiferimento() {
		return incaricoRiferimento;
	}

	public void setIncaricoRiferimento(IncaricoView incaricoRiferimento) {
		this.incaricoRiferimento = incaricoRiferimento;
	}

	public Long getCollegioArbitraleId() {
		return collegioArbitraleId;
	}

	public void setCollegioArbitraleId(Long collegioArbitraleId) {
		this.collegioArbitraleId = collegioArbitraleId;
	}
 
	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getDataRichiestaAutorizzazione() {
		return dataRichiestaAutorizzazione;
	}

	public void setDataRichiestaAutorizzazione(String dataRichiestaAutorizzazione) {
		this.dataRichiestaAutorizzazione = dataRichiestaAutorizzazione;
	}

	public List<ProfessionistaEsternoView> getListaProfessionista() {
		return listaProfessionista;
	}

	public void setListaProfessionista(List<ProfessionistaEsternoView> listaProfessionista) {
		this.listaProfessionista = listaProfessionista;
	}

}
