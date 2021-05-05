package eng.la.persistence;


import java.io.InputStream;
import java.util.List;
import java.util.Map;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;

public interface DocumentaleDdsDAO {

    public Folder leggiCartella(String percorsoCartella) throws Throwable;
    public Document leggiDocumentoUUID(String uuid) throws Throwable;
    public void verificaCreaPercorsoCartella(String cartellaPadreFascicolo)	throws Throwable;
    public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella, Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable;
    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
                                  String mimeTypeDocumento, Map<String, Object> proprietaDocumento,
                                  String cartellaFascicolo, byte[] contenuto) throws Throwable;
    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
                                  String mimeTypeDocumento, Map<String, Object> proprietaDocumento, String cartellaPadre, InputStream is);    //    public Folder leggiCartellaUUID(String uuid) throws Throwable;
    public void eliminaDocumento(String uuid) throws Throwable;
    public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;
    public void eliminaCartella(String uuid) throws Throwable;
    public List<Document> leggiDocumentiCartella(String nomeCartella)  throws Throwable;
    public byte[] leggiContenutoDocumento(String uuid) throws Throwable;
    public byte[] leggiContenutoDocumento(Document documento) throws Throwable;
    public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;
    public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable;
    public Folder modificaCartella(String uuid, String nuovoNomeCartella, Map<String, Object> proprietaCartella)         throws Throwable;

    //DONE    public void verificaCreaPercorsoCartella(String cartellaPadreFascicolo)	throws Throwable;
//DONE    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
//                                  String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, byte[] contenuto)
//            throws Throwable;
//
//DONE    public Document creaDocumento(String uuid, String titoloDocumento, String nomeClasseDocumento,
//                                  String mimeTypeDocumento, Map<String, Object> proprietaDocumento, Folder cartellaPadre, InputStream is)
//            throws Throwable;
//
//DONE    public Folder creaCartella(String uuid, String nomeCartella, String nomeClasseCartella,
//                               Map<String, Object> proprietaCartella, Folder cartellaPadre) throws Throwable;
//
//DONE    public Document leggiDocumentoUUID(String uuid) throws Throwable;
//
//

//
//DONE    public void eliminaCartella(String uuid) throws Throwable;
//
//DONE    public void eliminaDocumento(String uuid) throws Throwable;
//
//DONE    public void eliminaDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;
//
//DONE    public byte[] leggiContenutoDocumento(String uuid) throws Throwable;
//
//DONE    public List<Document> leggiDocumenti(String idOggettoLa, String nomeClasseDocumento) throws Throwable;
//
//DONE    public List<Folder> leggiCartelle(String idOggettoLa, String nomeClasseCartella) throws Throwable;
//
//DONE    public Folder modificaCartella(String uuid, String nuovoNomeCartella, Map<String, Object> proprietaCartella)
//            throws Throwable;
//

//
//DONE    public byte[] leggiContenutoDocumento(Document documento) throws Throwable;
//
//DONE    public void verificaCreaPercorsoCartellaEmail(String cartellaPadreEmail) throws Throwable;
//
//
//    public Boolean testFilenet() throws Throwable;
}
