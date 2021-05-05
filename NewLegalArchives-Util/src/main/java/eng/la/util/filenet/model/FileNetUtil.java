package eng.la.util.filenet.model;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

/*import com.filenet.api.collection.AccessPermissionList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.constants.AccessRight;
import com.filenet.api.constants.AccessType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.property.Properties;
import com.filenet.api.security.AccessPermission;
import com.filenet.apiimpl.core.DocumentImpl;
import com.filenet.apiimpl.core.EngineObjectImpl;*/

import eng.la.util.DateUtil;

public class FileNetUtil {
public static void main(String[] args) { 
}

	private static final Logger logger = Logger.getLogger(FileNetUtil.class);
	public static MessageFormat userFormat = new MessageFormat("CN={0},CN=BusinessUsers,CN=ExternalUsers,CN=Users,DC=snamretegas,DC=it");
	public static MessageFormat groupFormat = new MessageFormat("CN={0},CN=Groups,CN=LA,CN=Applications,CN=Groups,DC=snamretegas,DC=it");
	
	/*private static final int WRITER = AccessRight.READ_ACL.getValue() | AccessRight.CHANGE_STATE.getValue() | AccessRight.CREATE_INSTANCE.getValue()
	           | AccessRight.VIEW_CONTENT.getValue() | AccessRight.MINOR_VERSION.getValue() | AccessRight.UNLINK.getValue() | AccessRight.MODIFY_OBJECTS.getValue()
	           | AccessRight.LINK.getValue() | AccessRight.WRITE.getValue() | AccessRight.READ.getValue() | AccessRight.CREATE_CHILD.getValue() 
	           | AccessRight.WRITE_ACL.getValue() | AccessRight.MAJOR_VERSION.getValue() | AccessRight.MODIFY_RETENTION.getValue() 
	           | AccessRight.ADD_MARKING.getValue() | AccessRight.CONNECT.getValue() | AccessRight.DELETE.getValue() | AccessRight.STORE_OBJECTS.getValue()
	           | AccessRight.PRIVILEGED_WRITE.getValue() | AccessRight.PUBLISH.getValue() | AccessRight.REMOVE_MARKING.getValue() | AccessRight.REMOVE_OBJECTS.getValue()
	           //| AccessRight.WRITE_ANY_OWNER.getValue() | AccessRight.WRITE_OWNER.getValue()
	           ;
	
	private static final int VIEWER = AccessRight.VIEW_CONTENT.getValue() | AccessRight.READ_ACL.getValue() | AccessRight.READ.getValue() 
	           | AccessRight.LINK.getValue();*/

	public static String getFascicoloCartella(Date dataCreazioneFascicolo, String nomeFascicolo){

		int anno = DateUtil.getAnno(dataCreazioneFascicolo);
		int mese = DateUtil.getMese(dataCreazioneFascicolo); 
		
		String meseConZero = mese < 10 ? "0"+mese : mese+""; 
		String cartellaFascicolo= "/" + CostantiFileNet.FOLDER_GROUP_FASCICOLI + "/"+ anno + "/" + meseConZero + "/" + normalizzaNomeCatella(nomeFascicolo) + "/";
		 
		return cartellaFascicolo;		
	}

	public static String getFascicoloCartellaPadre(Date dataCreazioneFascicolo){

		int anno = DateUtil.getAnno(dataCreazioneFascicolo); 
		int mese = DateUtil.getMese(dataCreazioneFascicolo); 
		String meseConZero = mese < 10 ? "0"+mese : mese+"";  
		String cartellaPadreFascicolo= "/" + CostantiFileNet.FOLDER_GROUP_FASCICOLI + "/"+ anno + "/" + meseConZero + "/";
		 
		return cartellaPadreFascicolo;		
	}
	
	public static String getRichiestaAutGiudParentFolder(Date dataRichiesta){
		int anno = DateUtil.getAnno(dataRichiesta); 
		int mese = DateUtil.getMese(dataRichiesta);
		String meseConZero = mese < 10 ? "0"+mese : mese+"";
		// TODO Aggiungere la folder, sotto Archivi, RICHIESTA_AUTORITA_GIUDIZIARIA
		String richiestaParentFolder = "/" + CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.RICHIESTA_AUTORITA_GIUDIZIARIA
				+ "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}
	
	public static String getDueDiligenceParentFolder(Date dataApertura){
		int anno = DateUtil.getAnno(dataApertura);
		int mese = DateUtil.getMese(dataApertura);
		String meseConZero = mese < 10 ? "0" + mese : mese + "";
		String richiestaParentFolder = "/" + CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.VERIFICA_LEGALE_ANTICORRUZIONE
				+ "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}
	
	public static String getProgettoParentFolder(Date dataApertura){
		int anno = DateUtil.getAnno(dataApertura);
		int mese = DateUtil.getMese(dataApertura);
		String meseConZero = mese < 10 ? "0" + mese : mese + "";
		String richiestaParentFolder = "/" + CostantiFileNet.FOLDER_GROUP_PROGETTI + "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}
	
	public static String getProcureParentFolder(Date dataApertura){
		int anno = DateUtil.getAnno(dataApertura);
		int mese = DateUtil.getMese(dataApertura);
		String meseConZero = mese < 10 ? "0" + mese : mese + "";
		String richiestaParentFolder = "/"  + CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.FOLDER_GROUP_PROCURE + "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}
	
	public static String getRepertorioStandardParentFolder(Date dataApertura) {
		int anno = DateUtil.getAnno(dataApertura);
		int mese = DateUtil.getMese(dataApertura);
		String meseConZero = mese < 10 ? "0" + mese : mese + "";
		String richiestaParentFolder = "/"  + CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/"+ CostantiFileNet.FOLDER_GROUP_PROCURE + "/" + CostantiFileNet.FOLDER_GROUP_REPERTORIO_STANDARD + "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}
	
	public static String getIncaricoCartella(long idIncarico, Date dataCreazioneFascicolo, String nomeFascicolo, String nomeIncarico){

		return getFascicoloCartella(dataCreazioneFascicolo, nomeFascicolo) + normalizzaNomeCatella(idIncarico + "-" + nomeIncarico);
		  	
	}
	
	public static String getProfessionistaEsternoCartella(String nome, String cognome, String codiceFiscale){

		String cartella = nome + "_" + cognome + "_" + codiceFiscale;
		
		String ProfessionistaEsterno= "/" + CostantiFileNet.FOLDER_GROUP_PROFESSIONISTI_ESTERNI + "/"+ cartella + "/";
		 
		return ProfessionistaEsterno;		
	}
	
	
	public static String getAttoCartella(Date dataCreazione, String nomeAtto){ 
		int anno = DateUtil.getAnno(dataCreazione);
		int mese =DateUtil.getMese(dataCreazione); 
		String nomeFold=normalizzaNomeCatella(nomeAtto);
		String cartellaPadreAtto= "/"+CostantiFileNet.FOLDER_GROUP_ATTI +"/"+ anno + "/" + mese + "/"+nomeFold+"/";  
		return cartellaPadreAtto;		
	}
	
	public static String normalizzaNomeCatella(String nomeCartella){
		String nomeCartellaNomalizzato = nomeCartella.replaceAll("\\/", "-").toUpperCase();
		return nomeCartellaNomalizzato;
	}
	
	public static String getProformaCartella(long idProforma, Date dataCreazioneFascicolo, String nomeFascicolo, String nomeProforma) {
		return getFascicoloCartella(dataCreazioneFascicolo, nomeFascicolo) + normalizzaNomeCatella(idProforma + "-" + nomeProforma); 
	}
	
//	public static String getEmailCartella(Date dataCreazione, String nomeAtto){ 
//		int anno = DateUtil.getAnno(dataCreazione);
//		int mese =DateUtil.getMese(dataCreazione); 
//		String nomeFold=normalizzaNomeCatella(nomeAtto);
//		String cartellaPadreEmail= "/"+CostantiFileNet.FOLDER_GROUP_PRESIDIO_NORMATIVO +"/"+ anno + "/" + mese + "/"+nomeFold+"/";  
//		return cartellaPadreEmail;		
//	}
//	
//	public static String getCopertinaCartella(Date dataCreazione, String nomeAtto){ 
//		int anno = DateUtil.getAnno(dataCreazione);
//		int mese =DateUtil.getMese(dataCreazione); 
//		String nomeFold=normalizzaNomeCatella(nomeAtto);
//		String cartellaPadreEmail= "/"+CostantiFileNet.FOLDER_GROUP_NEWSLETTER_COPERTINA +"/"+ anno + "/" + mese + "/"+nomeFold+"/";  
//		return cartellaPadreEmail;		
//	}
//	
	public static String getEmailCartellaPadre(Date dataCreazioneFascicolo){

		int anno = DateUtil.getAnno(dataCreazioneFascicolo); 
		int mese = DateUtil.getMese(dataCreazioneFascicolo); 
		String meseConZero = mese < 10 ? "0"+mese : mese+"";  
		String cartellaPadreFascicolo= "/"+ CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.FOLDER_GROUP_PRESIDIO_NORMATIVO + "/"+ CostantiFileNet.FOLDER_GROUP_ARTICOLI + "/"+ anno + "/" + meseConZero + "/";
		 
		return cartellaPadreFascicolo;		
	} 
	
	public static String getCopertinaCartellaPadre(Date dataCreazioneFascicolo){

		int anno = DateUtil.getAnno(dataCreazioneFascicolo); 
		int mese = DateUtil.getMese(dataCreazioneFascicolo); 
		String meseConZero = mese < 10 ? "0"+mese : mese+"";  
		String cartellaPadreFascicolo= "/"+ CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.FOLDER_GROUP_PRESIDIO_NORMATIVO + "/"+ CostantiFileNet.FOLDER_GROUP_NEWSLETTER + "/"+ anno + "/" + meseConZero + "/";
		 
		return cartellaPadreFascicolo;		
	} 
	
	public static String getProtocolloCartellaPadre(Date dataCreazioneFascicolo){

		int anno = DateUtil.getAnno(dataCreazioneFascicolo); 
		int mese = DateUtil.getMese(dataCreazioneFascicolo); 
		String meseConZero = mese < 10 ? "0"+mese : mese+"";  
		String cartellaPadreFascicolo= "/"+ CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/" + CostantiFileNet.FOLDER_GROUP_PROTOCOLLO + "/"+ anno + "/" + meseConZero + "/";
		 
		return cartellaPadreFascicolo;		
	}
	
	public static String getSchedaFondoRischiCartella(long idScheda, Date dataCreazioneFascicolo, String nomeFascicolo, String dataCreazioneScheda){

		return getFascicoloCartella(dataCreazioneFascicolo, nomeFascicolo) + normalizzaNomeCatella(idScheda + "-" + dataCreazioneScheda);
		  	
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	/*public static Integer getLaid(Document documento){
		Properties properties = documento.getProperties();
		Integer value = null;
		try{
			
			value = properties.getInteger32Value(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID);
			
		} catch(Exception e){
			return null;
		}
		
		return value;
	}
	
	public static Integer getLaid(Folder folder){
		Properties properties = folder.getProperties();
		Integer value = null;
		try{
			
			value = properties.getInteger32Value(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID);
			
		} catch(Exception e){
			return null;
		}
		
		return value;
	}
	
	public static String getPathFolder(Document documento){
		FolderSet fs = documento.get_FoldersFiledIn();
		Iterator iterFs = fs.iterator();
		String pathFolder = "";
        while (iterFs.hasNext()){
            Folder folder = (Folder) iterFs.next();
            pathFolder = folder.get_PathName();
        }
        return pathFolder;
	}*/
	public static String getAffariSocietariParentFolder(Date dataCostituzione) {
		int anno = DateUtil.getAnno(dataCostituzione);
		int mese = DateUtil.getMese(dataCostituzione);
		String meseConZero = mese < 10 ? "0" + mese : mese + "";
		String richiestaParentFolder = "/"  + CostantiFileNet.FOLDER_GROUP_ARCHIVI + "/"+ CostantiFileNet.FOLDER_GROUP_AFFARI_SOCIETARI + "/"+ anno + "/" + meseConZero + "/";
		return richiestaParentFolder;		
	}

	/*public static void addAclFile(boolean userGroup, String userId, Folder cartellaFascicolo, boolean rights) throws Throwable{

		String granteeName = "";
		if(userGroup){
			granteeName = userFormat.format(new String[]{userId});
		} else {
			granteeName = groupFormat.format(new String[]{userId});
		}
		
		logger.debug("addAclFile Folder: "+cartellaFascicolo.get_FolderName());
		logger.debug("addAclFile AccessMask: "+(rights?"WRITER":"VIEWER"));
		logger.debug("addAclFile GranteeName: "+granteeName);
		
		AccessPermission ap = Factory.AccessPermission.createInstance();
		ap.set_GranteeName(granteeName);
		ap.set_AccessType(AccessType.ALLOW); 
		ap.set_AccessMask(rights?WRITER:VIEWER); 
		ap.set_InheritableDepth(-1);
		
		DocumentSet documenti = cartellaFascicolo.get_ContainedDocuments();
		
		logger.debug("addAclFile NOME Folder: "+cartellaFascicolo.get_FolderName());
		
		if (documenti != null) {
			
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					logger.debug("addAclFile NOME DOCUMENTO: "+documento.get_Name());
					
					AccessPermissionList apl = documento.get_Permissions();
					apl.add(ap);
					documento.set_Permissions(apl);
					documento.save(RefreshMode.REFRESH);
				}
			}
		}
		
		FolderSet subFolders = cartellaFascicolo.get_SubFolders();
		for (Iterator<Folder> i = subFolders.iterator(); i.hasNext();) {
			Folder subFolder = i.next();
			
			logger.debug("addAclFile NOME subFolder: "+subFolder.get_FolderName());
			
			DocumentSet documentiSubFolder = subFolder.get_ContainedDocuments();
				
			if (documentiSubFolder != null) {
				
				PageIterator it = documentiSubFolder.pageIterator();
				while (it.nextPage()) {
					EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
					for (EngineObjectImpl objDocumento : documentiArray) {
						DocumentImpl documento = (DocumentImpl) objDocumento;
						
						logger.debug("addAclFile NOME DOCUMENTO: "+documento.get_Name());
						
						AccessPermissionList apl = documento.get_Permissions();
						apl.add(ap);
						documento.set_Permissions(apl);
						documento.save(RefreshMode.REFRESH);
					}
				}
			}
		}
		
	}
	
	public static void addAclFolder(boolean userGroup, String userId, Folder cartellaFascicolo, boolean rights) throws Throwable{
		
		String granteeName = "";
		if(userGroup){
			granteeName = userFormat.format(new String[]{userId});
		} else {
			granteeName = groupFormat.format(new String[]{userId});
		}

		logger.debug("addAclFolder Folder: "+cartellaFascicolo.get_FolderName());
		logger.debug("addAclFolder AccessMask: "+(rights?"WRITER":"VIEWER"));
		logger.debug("addAclFolder GranteeName: "+granteeName);
		
		AccessPermission ap = Factory.AccessPermission.createInstance();
		ap.set_GranteeName(granteeName);
		ap.set_AccessType(AccessType.ALLOW); 
		ap.set_AccessMask(rights?WRITER:VIEWER); 
		ap.set_InheritableDepth(-1);
		
		AccessPermissionList apl = cartellaFascicolo.get_Permissions();
		apl.add(ap);
		cartellaFascicolo.set_Permissions(apl);
		cartellaFascicolo.save(RefreshMode.REFRESH);
		
	}*/
	
	
	/*public static void addAclFile(Folder cartellaFascicolo) throws Throwable{

		DocumentSet documenti = cartellaFascicolo.get_ContainedDocuments();
		
		logger.debug("addAclFile NOME Folder: "+cartellaFascicolo.get_FolderName());
		
		if (documenti != null) {
			
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					logger.debug("addAclFile NOME DOCUMENTO: "+documento.get_Name());
					documento.set_SecurityFolder(cartellaFascicolo);
					documento.save(RefreshMode.REFRESH);
				}
			}
		}
		
		FolderSet subFolders = cartellaFascicolo.get_SubFolders();
		for (Iterator<Folder> i = subFolders.iterator(); i.hasNext();) {
			Folder subFolder = i.next();
			
			logger.debug("addAclFile NOME subFolder: "+subFolder.get_FolderName());
			
			DocumentSet documentiSubFolder = subFolder.get_ContainedDocuments();
				
			if (documentiSubFolder != null) {
				
				PageIterator it = documentiSubFolder.pageIterator();
				while (it.nextPage()) {
					EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
					for (EngineObjectImpl objDocumento : documentiArray) {
						DocumentImpl documento = (DocumentImpl) objDocumento;
						logger.debug("addAclFile NOME DOCUMENTO: "+documento.get_Name());
						documento.set_SecurityFolder(subFolder);
						documento.save(RefreshMode.REFRESH);
					}
				}
			}
		}
		
	}
	*/
	public static String getCartellaPadreBeautyContest(Date dataEmissione){

		int anno = DateUtil.getAnno(dataEmissione); 
		int mese = DateUtil.getMese(dataEmissione); 
		String meseConZero = mese < 10 ? "0"+mese : mese+"";  
		String cartellaBeautyContest= "/" + CostantiFileNet.FOLDER_GROUP_BEAUTY_CONTEST + "/"+ anno + "/" + meseConZero + "/";
		 
		return cartellaBeautyContest;		
	}
	
	public static String getBeautyContestCartella(long idBeautyContest, Date dataEmissione){

		return getCartellaPadreBeautyContest(dataEmissione) + idBeautyContest + "";
		  	
	}
	

}
