package eng.la.model.view;

import eng.la.model.StatoEsitoValutazioneProf;

@SuppressWarnings("all")
public class StatoEsitoValutazioneProfView extends BaseView {

	private static final long serialVersionUID = 1L;
	private StatoEsitoValutazioneProf vo;

	
	public StatoEsitoValutazioneProfView() {
	}
	
	public StatoEsitoValutazioneProf getVo() {
		return vo;
	}

	public void setVo(StatoEsitoValutazioneProf vo) {
		this.vo = vo;
	}

	
}
