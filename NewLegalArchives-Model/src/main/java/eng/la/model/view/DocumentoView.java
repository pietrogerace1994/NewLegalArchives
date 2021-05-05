package eng.la.model.view;

import java.io.File;

import eng.la.model.Documento;

public class DocumentoView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Documento vo;

	public Documento getVo() {
		return vo;
	}

	public void setVo(Documento vo) {
		this.vo = vo;
	}

	private File file;
	private String nomeFile;
	private boolean nuovoDocumento;
	private String contentType;
	private String uuid;
	
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public boolean isNuovoDocumento() {
		return nuovoDocumento;
	}

	public void setNuovoDocumento(boolean nuovoDocumento) {
		this.nuovoDocumento = nuovoDocumento;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
