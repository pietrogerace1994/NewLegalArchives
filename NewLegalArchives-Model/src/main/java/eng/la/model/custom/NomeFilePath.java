package eng.la.model.custom;


public class NomeFilePath {

	private String nomeFile;
	private String path;
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "NomeFilePath [nomeFile=" + nomeFile + ", path=" + path + "]";
	}
	
	
}
