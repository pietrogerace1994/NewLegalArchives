package eng.la.business;

//@@DDS import com.filenet.api.core.Document;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
public interface RicercaService {
	
	public Document leggiDocumentoUUID(String uuid) throws Throwable;
	
	public Document leggiDocumentoCryptUUID(String uuid) throws Throwable;
	
	public Folder leggiCartella(String percorsoCartella) throws Throwable;
	
	public Folder leggiCartellaCrypt(String percorsoCartella) throws Throwable;

	public void bonificaFascicoliAclAnno(Integer anno) throws Throwable;

}
