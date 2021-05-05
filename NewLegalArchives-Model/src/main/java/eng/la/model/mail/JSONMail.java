package eng.la.model.mail;

import java.io.Serializable;

public class JSONMail implements Serializable
{

	private long idFascicolo;
    private String userId;
    private String roles;
    private String nomeFile;
    private String mimeType;
    private String[] to;
    private String[] cc;
    private String from;
    private String oggetto;
    private String dataInvio;
    private String dataRicezione;
    private String base64;
    private String docType;

    public JSONMail(){
    	
    }
    
    public void setIdFascicolo(long arg){
    	idFascicolo = arg;
    }
    
    public long getIdFascicolo(){
    	return idFascicolo;
    }
    
    public void setUserId(String arg){
    	userId = arg;
    }
    
    public String getUserId(){
    	return userId;
    }
    
    public void setRoles(String arg){
    	roles = arg;
    }
    
    public String getRoles(){
    	return roles;
    }
    
    public void setNomeFile(String arg){
    	nomeFile = arg;
    }
    
    public String getNomeFile(){
    	return nomeFile;
    }
    
    public void setMimeType(String arg){
    	mimeType = arg;
    }
    
    public String getMimeType(){
    	return mimeType;
    }
    
    public void setTo(String[] arg){
    	to = arg;
    }
    
    public String[] getTo(){
    	return to;
    }
    
    public void setCc(String[] arg){
    	cc = arg;
    }
    
    public String[] getCc(){
    	return cc;
    }
    
    public void setFrom(String arg){
    	from = arg;
    }
    
    public String getFrom(){
    	return from;
    }
    
    public void setOggetto(String arg){
    	oggetto = arg;
    }
    
    public String getOggetto(){
    	return oggetto;
    }
    
    public void setDataInvio(String arg){
    	dataInvio = arg;
    }
    
    public String getDataInvio(){
    	return dataInvio;
    }
    
    public void setDataRicezione(String arg){
    	dataRicezione = arg;
    }
    
    public String getDataRicezione(){
    	return dataRicezione;
    }
    
    public void setBase64(String arg){
    	base64 = arg;
    }
    
    public String getBase64(){
    	return base64;
    }
    
    public void setDocType(String arg){
    	docType = arg;
    }
    
    public String getDocType(){
    	return docType;
    }
}
