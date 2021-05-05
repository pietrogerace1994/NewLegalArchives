package eng.la.model.view;

import org.apache.commons.lang.StringUtils;

import eng.la.model.RUtenteFascicolo;

public class RUtenteFascicoloView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RUtenteFascicolo vo;
	private String ownerDal;
	private String ownerAl;
	private String nominativo;

	public String getNominativo() {
		if(nominativo != null && !nominativo.isEmpty()){
			return nominativo;
		}
		return StringUtils.isNotBlank(vo.getUtente().getNominativoUtil()) ? vo.getUtente().getNominativoUtil() : vo.getUtente().getUseridUtil();
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public RUtenteFascicolo getVo() {
		return vo;
	}

	public void setVo(RUtenteFascicolo vo) {
		this.vo = vo;
	}

	public String getOwnerDal() {
		return ownerDal;
	}

	public void setOwnerDal(String ownerDal) {
		this.ownerDal = ownerDal;
	}

	public String getOwnerAl() {
		return ownerAl;
	}

	public void setOwnerAl(String ownerAl) {
		this.ownerAl = ownerAl;
	}

}
