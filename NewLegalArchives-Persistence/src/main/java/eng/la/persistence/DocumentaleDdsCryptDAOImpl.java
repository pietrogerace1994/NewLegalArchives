package eng.la.persistence;


import eng.la.util.filenet.model.FileNetClassNames;
import it.snam.ned.libs.dds.dtos.v2.Content;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.Permission;
import it.snam.ned.libs.dds.dtos.v2.builders.DocumentBuilder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.builder.FolderBuilder;
import it.snam.ned.libs.dds.interfaces.v2.DdsDocumentDao;
import it.snam.ned.libs.dds.interfaces.v2.DdsFolderDao;
import it.snam.ned.libs.dds.utils.SSLUtil;
import it.snam.ned.libs.dds.v2.DdsDocumentDaoImpl;
import it.snam.ned.libs.dds.v2.DdsFolderDaoImpl;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class DocumentaleDdsCryptDAOImpl implements DocumentaleDdsCryptDAO {
    private static final Logger logger = Logger.getLogger(DocumentaleDdsCryptDAOImpl.class);
    private String url;
    private String username;
    private String password;
    private String stanza;
    private String osname;
    private DdsFolderDao ddsFolderDao;
    private DdsDocumentDao ddsDocumentDao;

    public DocumentaleDdsCryptDAOImpl(String url, String username, String password, String stanza, String osname) {
        this.url = url;
        this.osname = osname;
        this.username = username;
        this.password = password;
        this.stanza = stanza;
        this.ddsFolderDao = new DdsFolderDaoImpl(new RestTemplate(), osname, null);
        this.ddsDocumentDao = new DdsDocumentDaoImpl(new RestTemplate(), osname, null);
    }

    @Override
    public void verificaCreaPercorsoCartella(String cartellaPadreFascicolo) throws Throwable {
        logger.info("@@DDS _________________ DocumentaleDdsDAOImpl.verificaCreaPercorsoCartella" + cartellaPadreFascicolo);
        StringTokenizer tokenizer = new StringTokenizer(cartellaPadreFascicolo, "\\/");
        Folder cartellaPadre = leggiCartella("/");
        String path = "/";
        while( tokenizer.hasMoreTokens() ){
            String token = tokenizer.nextToken();
            path += token + "/";
            Folder folder = leggiCartella(path);
            logger.info("@@DDS _________________ ora vediamo" + path);
            if( folder == null ){
                String uuid = UUID.randomUUID().toString();
                logger.info("@@DDS _________________ vado a creare" + path);
                cartellaPadre = creaCartella(uuid, path, FileNetClassNames.GENERIC_FOLDER, null, cartellaPadre);
            }else{
                cartellaPadre = folder;
            }
        }
        logger.info("DocumentaleDdsDAOImpl.verificaCreaPercorsoCartella" + cartellaPadreFascicolo);
    }

    @Override
    public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella, Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable {

        Permission permission = new Permission();
        permission.setName("LEG_ARCAdmin");
        permission.setType("user");
        permission.setPermissionList(true, true, true, true);
        List<Permission> permissionsList = new LinkedList<>();
        permissionsList.add(permission);
        logger.info("@@DDS XXX DocumentaleDdsDAOImpl.creaCartella ___________________ nome cartella: " + nomeCartella);
        if (proprietaCartella == null){
            proprietaCartella = new HashMap<String,Object>();
        }
        Folder newFolder = FolderBuilder.buildFolder(proprietaCartella, nomeCartella, nomeClasseCartella, permissionsList, osname);
        Folder folderCreated = null;
        folderCreated = ddsFolderDao.create(newFolder);
        logger.info("@@DDS DocumentaleDdsDAOImpl.creaCartella" + newFolder.getId());

        return folderCreated;
    }

    @Override
    public Folder leggiCartella(String percorsoCartella) throws Throwable {
        logger.info("@@DDS _________________ leggiCartella" + percorsoCartella);
        Folder folder = null;
        try {
            SSLUtil.turnOffSslChecking();
            if (percorsoCartella != null) {
                int index = percorsoCartella.lastIndexOf("/");
                logger.info("@@DDS _________________ index" + index);
                if (index != 0 && index == percorsoCartella.length() - 1) {
                    percorsoCartella = percorsoCartella.substring(0, index);
                }
            }

            Folder folderInfo = ddsFolderDao.getFolderInfo(osname, percorsoCartella);
            //logger.info("@@DDS DocumentaleDdsDAOImpl.folderInfo" + folderInfo);
            folder = ddsFolderDao.read(folderInfo.getId());

        }catch (Exception e){
            logger.info("@@DDS DocumentaleDdsDAOImpl.leggiCartella errore o cartella vuota" );
        }
        return folder;
    }

    @Override
    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento, String mimeTypeDocumento, Map<String,
            Object> proprietaDocumento, String cartellaFascicolo, byte[] contenuto) throws Throwable {
        logger.debug("creaDocumento...............uuid "+uuid);
        Folder cartellaPadre = leggiCartella(cartellaFascicolo);

        Content content = new Content();
        content.setContent(contenuto);
        content.setContentsName(titoloDocumento);
        content.setContentsMimeType(mimeTypeDocumento);

        Document document = DocumentBuilder.buildDocument(proprietaDocumento, osname, nomeClasseDocumento, content);
        String newUUID = createIdWithParenthesis(uuid);
        document.setId(newUUID);
        document.setCustomPermission(Collections.singletonList(cartellaPadre.getCustomPermission()));

        if (cartellaPadre != null) {
            List<String> list = new ArrayList<String>();
            list.add(cartellaPadre.getFolderName());
            document.setFoldersParents(list);
        }
        //logger.info("@@DDS  DocumentaleDdsDAOImpl.creaDocumento - vado a creare " + document.toString());
        Document newDocument = ddsDocumentDao.create(document);
        if (newDocument == null) {
            logger.info("@@DDS documento nullo");
        }else {
            logger.info("@@DDS  DocumentaleDdsDAOImpl.creaDocumento - creato documento con id " + newDocument.getId());
        }
        return newDocument;
    }

    @Override
    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento, String mimeTypeDocumento,
                                  Map<String, Object> proprietaDocumento, String cartella, InputStream is) {
        logger.debug("creaDocumento...............InputStream uuid "+uuid);
        Folder cartellaPadre = null;
        Document newDocument =null;
        try {
            cartellaPadre = leggiCartella(cartella);


            Content content = new Content();
            //byte[] targetArray = new byte[is.available()];
            byte[] bytes = IOUtils.toByteArray(is);
            content.setContent(bytes);
            content.setContentsName(titoloDocumento);
            content.setContentsMimeType(mimeTypeDocumento);

            if(proprietaDocumento == null) {
                proprietaDocumento = new HashMap<String, Object>();
            }
            Document document = DocumentBuilder.buildDocument(proprietaDocumento, osname, nomeClasseDocumento, content);
            String newUUID = createIdWithParenthesis(uuid);
            document.setId(newUUID);
            document.setCustomPermission(Collections.singletonList(cartellaPadre.getCustomPermission()));

            if (cartellaPadre != null) {
                List<String> list = new ArrayList<String>();
                list.add(cartellaPadre.getFolderName());
                document.setFoldersParents(list);
            }
            //logger.info("@@DDS  DocumentaleDdsDAOImpl.creaDocumento - vado a creare " + document.toString());
            newDocument = ddsDocumentDao.create(document);
            if (newDocument == null) {
                logger.info("@@DDS documento nullo");
            }else {
                logger.info("@@DDS  DocumentaleDdsDAOImpl.creaDocumento - creato documento con id " + newDocument.getId());
            }
            logger.info("@@DDS creaDocumento CON PARAMETRI ____________________________TODO ");
        } catch (Throwable throwable) {
            throwable.printStackTrace();throwable.printStackTrace();
        }
        return newDocument;


    }

    @Override
    public void eliminaDocumento(String uuid) throws Throwable {
        String newUuid = createIdWithParenthesis(uuid);
        try {
            ddsDocumentDao.delete(newUuid);
            logger.info("@@DDS eliminaDocumento - " + newUuid);
        }catch (Exception e){
            logger.error("@@DDS eliminaDocumento - " +e );
        }
    }

    @Override
    public Document leggiDocumentoUUID(String uuid) throws Throwable {
        String newUuid = createIdWithParenthesis(uuid);
        Document document = ddsDocumentDao.read(newUuid);
        if (document == null) {
            logger.info("@@DDS documento nullo");
        }
        return document;
    }
    @Override
    public void eliminaCartella(String uuid) throws Throwable {
        String newUuid = createIdWithParenthesis(uuid);
        ddsFolderDao.delete(newUuid);
    }

    @Override
    public List<Document> leggiDocumentiCartella(String nomeCartella) throws Throwable{
        List<Document> list = null;
        logger.info("@@DDS ------------------------ leggiDocumentiCartella " + nomeCartella);
        try {
            Folder folder = leggiCartella(nomeCartella);
            list = ddsDocumentDao.getFolderFiles(folder);
            logger.info("@@DDS ------------------------  leggiDocumentiCartella + " + list.toString());
        }catch (Exception e){
            logger.info("@@DDS ------------------------  Errore in lettura della cartella " + nomeCartella + " - " + e );
        }
        return list;
    }

    @Override
    public byte[] leggiContenutoDocumento(String uuid) throws Throwable{
        byte[] documentByte= null;
        String newUuid = createIdWithParenthesis(uuid);
        Document document = ddsDocumentDao.read(newUuid);

        if (document != null){
            Content content = ddsDocumentDao.getLazyContent(newUuid, document.getContents().get(0).getContentsName());
            logger.info("@@DDS ------------------------ leggiContenutoDocumento" + content.toString());
            documentByte = content.getContent();
        }else{
            logger.info("@@DDS ------------------------ leggiContenutoDocumento ___read documento nullo" );
        }
        return documentByte;
    }
    @Override
    public byte[] leggiContenutoDocumento(Document documento) throws Throwable{
        byte[] documentByte= null;
        String newUuid = createIdWithParenthesis(documento.getId());
        Document document = ddsDocumentDao.read(newUuid);
        if (document != null){
            Content content = ddsDocumentDao.getLazyContent(newUuid, document.getContents().get(0).getContentsName());
            logger.info("@@DDS ------------------------ leggiContenutoDocumento by documento" + content.toString());
            documentByte = content.getContent();
        }
        return documentByte;
    }
    @Override
    public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable{
        logger.info("@@DDS ------------------------  eliminaDocumenti TODO ---- DA IMPLEMENTARE");
        List<Document> documenti = ddsDocumentDao.find("LAid = " + idOggettoLa + " AND documentalClass = '" + nomeClasseDocumento +  "'", null, null, null);
        logger.info("@@DDS ------------------------  " + documenti);
        for(Document documento:documenti){
            String newUuid = createIdWithParenthesis(documento.getId());
            logger.info("@@DDS ------------------------ TODO___________ CANCELLO IL DOC " + newUuid);
            //ddsDocumentDao.delete(newUuid);
        }
        //Document document = ddsDocumentDao.delete(newUuid);
    }

    @Override
    public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable{
           logger.info("@@DDS ------------------------  leggiDocumenti TODO ---- CON LAID DA IMPLEMENTARE");
        List<Document> documenti = ddsDocumentDao.find("LAid = " + idOggettoLa + " AND documentalClass = '" + nomeClasseDocumento +  "'", null, null, null);
        return  documenti;
       }

    @Override
    public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable{
        logger.info("@@DDS ------------------------  leggiCartelle TODO ---- CON LAID DA IMPLEMENTARE");
        List<Folder> folderList = ddsFolderDao.find("LAid = " + idOggettoLa + " AND folderClass = '" + nomeClasseCartella +  "'", null, null, null);
        return folderList;
    }

    @Override
    public Folder modificaCartella(String uuid, String nuovoNomeCartella, Map<String, Object> proprietaCartella) throws Throwable{
        logger.info("@@DDS ------------------------  modificaCartella TODO ----  DA IMPLEMENTARE");
        Folder folder = new Folder();
        return folder;
    }

    private String createIdWithParenthesis(String id) {
        String newId = id;
        if (newId != null) {
            if (!id.startsWith("{")) {
                newId = "{" + newId;
            }
            if (!id.endsWith("}")) {
                newId = newId + "}";
            }
            return newId.toUpperCase();
        }
        return null;
    }
}
