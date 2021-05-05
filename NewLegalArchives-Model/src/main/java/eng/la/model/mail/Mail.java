package eng.la.model.mail;

import java.util.Date;
import java.util.List;

import eng.la.model.Fascicolo;

public class Mail {

 private String mailFrom;

 private String mailTo;

 private String mailCc;

 private String mailBcc;

 private String mailSubject;

 private String nomeTemplate;
 
 private String nomeDocumento;
 
 private String linkAlDocumento;
 
 private String linkAlDocumentoLegaliEsterni;
 
 private String motivazioneRifiuto;
 
 private String dataAutorizzazione;
 
 private String utenteAutorizzazione;
 
 private String utenteAutorizzatore;
 
 private String utenteRegistrazione;
 
 private String dataApprovazioneInSeconda;
 
 private String utenteApprovazioneInSeconda;
 
 private String utenteRichiedente;
 
 private String utenteRifiuto;
 
 private String dataApprovazione;
 
 private String utenteApprovazione;
 
 private String fascicoloRiferimento;
 
 private String linkFascicoloRiferimento;
 
 private String studioLegale;
 
 private String indirizzo;
 
 private String citta;
 
 private String cap;
 
 private String paese;
 
 private String telefono;
 
 private String fax;
 
 private String email;
 
 private String partitaIva;
 
 private String codiceFiscale;
 
 private List<String> listaSpecializzazioni;
 
 private String totaleAutorizzato;
 
 private String societaAddebito;
 
 private String annoFinanziario;
 
 private String centroCosto;
 
 private String voceConto;
 
 private String unitaLegale;
 
 private String ultimoProforma;
 
 private String tipoProfessionista;
 
 private String tipoAllegatoIt;
 
 private String motivazioneRichiesta;
 
 private String tipoAllegatoEn;
 
 private String requestingUserID;
 
 private String mittentePec;
 
 private String destinatarioPec;
 
 private String oggettoPec;
 
 private String dataVerifica;
 
 private String utenteVerificatore;
 
 private String numeroProtocolli;
 
 private String bcSow;
 
 private String vecchioOwner;
 
 private String nuovoOwner;
 
 /**
  * LEGAL - FIX EVOLUTIVE - POST UAT - VARIANTI 2 [BEAUTY CONTEST] + VARIANTI 3 [cambio owner, nomenclatura incarico, parti correlate]
  */
 private List<Fascicolo> listaFascioliCambioOwner;
 
 

 public List<Fascicolo> getListaFascioliCambioOwner() {
	return listaFascioliCambioOwner;
}

public void setListaFascioliCambioOwner(List<Fascicolo> listaFascioliCambioOwner) {
	this.listaFascioliCambioOwner = listaFascioliCambioOwner;
}

public String getVecchioOwner() {
	return vecchioOwner;
}

public void setVecchioOwner(String vecchioOwner) {
	this.vecchioOwner = vecchioOwner;
}

public String getNuovoOwner() {
	return nuovoOwner;
}

public void setNuovoOwner(String nuovoOwner) {
	this.nuovoOwner = nuovoOwner;
}

public String getNumeroProtocolli() {
	return numeroProtocolli;
}

public void setNumeroProtocolli(String numeroProtocolli) {
	this.numeroProtocolli = numeroProtocolli;
}

public String getTipoAllegatoIt() {
	return tipoAllegatoIt;
}

public void setTipoAllegatoIt(String tipoAllegatoIt) {
	this.tipoAllegatoIt = tipoAllegatoIt;
}

public String getTipoAllegatoEn() {
	return tipoAllegatoEn;
}

public void setTipoAllegatoEn(String tipoAllegatoEn) {
	this.tipoAllegatoEn = tipoAllegatoEn;
}

public String getMailBcc() {
  return mailBcc;
 }

 public String getNomeTemplate() {
  return nomeTemplate;
 }

 public void setNomeTemplate(String nomeTemplate) {
  this.nomeTemplate = nomeTemplate;
 }

 public void setMailBcc(String mailBcc) {
  this.mailBcc = mailBcc;
 }

 public String getMailCc() {
  return mailCc;
 }

 public void setMailCc(String mailCc) {
  this.mailCc = mailCc;
 }

 public String getMailFrom() {
  return mailFrom;
 }

 public void setMailFrom(String mailFrom) {
  this.mailFrom = mailFrom;
 }

 public String getMailSubject() {
  return mailSubject;
 }

 public void setMailSubject(String mailSubject) {
  this.mailSubject = mailSubject;
 }

 public String getMailTo() {
  return mailTo;
 }

 public void setMailTo(String mailTo) {
  this.mailTo = mailTo;
 }

 public Date getMailSendDate() {
  return new Date();
 }

 
 @Override
 public String toString() {
  StringBuilder lBuilder = new StringBuilder();
  lBuilder.append("Mail From:- ").append(getMailFrom());
  lBuilder.append("Mail To:- ").append(getMailTo());
  lBuilder.append("Mail Cc:- ").append(getMailCc());
  lBuilder.append("Mail Bcc:- ").append(getMailBcc());
  lBuilder.append("Mail Subject:- ").append(getMailSubject());
  lBuilder.append("Mail Send Date:- ").append(getMailSendDate());
  return lBuilder.toString();
 }

public String getNomeDocumento() {
	return nomeDocumento;
}

public void setNomeDocumento(String nomeDocumento) {
	this.nomeDocumento = nomeDocumento;
}

public String getLinkAlDocumento() {
	return linkAlDocumento;
}

public void setLinkAlDocumento(String linkAlDocumento) {
	this.linkAlDocumento = linkAlDocumento;
}

public String getMotivazioneRifiuto() {
	return motivazioneRifiuto;
}

public void setMotivazioneRifiuto(String motivazioneRifiuto) {
	this.motivazioneRifiuto = motivazioneRifiuto;
}

public String getDataAutorizzazione() {
	return dataAutorizzazione;
}

public void setDataAutorizzazione(String dataAutorizzazione) {
	this.dataAutorizzazione = dataAutorizzazione;
}

public String getUtenteAutorizzazione() {
	return utenteAutorizzazione;
}

public void setUtenteAutorizzazione(String utenteAutorizzazione) {
	this.utenteAutorizzazione = utenteAutorizzazione;
}

public String getDataApprovazioneInSeconda() {
	return dataApprovazioneInSeconda;
}

public void setDataApprovazioneInSeconda(String dataApprovazioneInSeconda) {
	this.dataApprovazioneInSeconda = dataApprovazioneInSeconda;
}

public String getUtenteApprovazioneInSeconda() {
	return utenteApprovazioneInSeconda;
}

public void setUtenteApprovazioneInSeconda(String utenteApprovazioneInSeconda) {
	this.utenteApprovazioneInSeconda = utenteApprovazioneInSeconda;
}

public String getDataApprovazione() {
	return dataApprovazione;
}

public void setDataApprovazione(String dataApprovazione) {
	this.dataApprovazione = dataApprovazione;
}

public String getUtenteApprovazione() {
	return utenteApprovazione;
}

public void setUtenteApprovazione(String utenteApprovazione) {
	this.utenteApprovazione = utenteApprovazione;
}

public String getFascicoloRiferimento() {
	return fascicoloRiferimento;
}

public void setFascicoloRiferimento(String fascicoloRiferimento) {
	this.fascicoloRiferimento = fascicoloRiferimento;
}

public String getLinkFascicoloRiferimento() {
	return linkFascicoloRiferimento;
}

public void setLinkFascicoloRiferimento(String linkFascicoloRiferimento) {
	this.linkFascicoloRiferimento = linkFascicoloRiferimento;
}

public String getStudioLegale() {
	return studioLegale;
}

public void setStudioLegale(String studioLegale) {
	this.studioLegale = studioLegale;
}

public String getSocietaAddebito() {
	return societaAddebito;
}

public void setSocietaAddebito(String societaAddebito) {
	this.societaAddebito = societaAddebito;
}

public String getAnnoFinanziario() {
	return annoFinanziario;
}

public void setAnnoFinanziario(String annoFinanziario) {
	this.annoFinanziario = annoFinanziario;
}

public String getCentroCosto() {
	return centroCosto;
}

public void setCentroCosto(String centroCosto) {
	this.centroCosto = centroCosto;
}

public String getVoceConto() {
	return voceConto;
}

public void setVoceConto(String voceConto) {
	this.voceConto = voceConto;
}

public String getUnitaLegale() {
	return unitaLegale;
}

public void setUnitaLegale(String unitaLegale) {
	this.unitaLegale = unitaLegale;
}

public String getUltimoProforma() {
	return ultimoProforma;
}

public void setUltimoProforma(String ultimoProforma) {
	this.ultimoProforma = ultimoProforma;
}

public String getTotaleAutorizzato() {
	return totaleAutorizzato;
}

public void setTotaleAutorizzato(String totaleAutorizzato) {
	this.totaleAutorizzato = totaleAutorizzato;
}

public String getTipoProfessionista() {
	return tipoProfessionista;
}

public void setTipoProfessionista(String tipoProfessionista) {
	this.tipoProfessionista = tipoProfessionista;
}

public String getUtenteRichiedente() {
	return utenteRichiedente;
}

public void setUtenteRichiedente(String utenteRichiedente) {
	this.utenteRichiedente = utenteRichiedente;
}

public String getMotivazioneRichiesta() {
	return motivazioneRichiesta;
}

public void setMotivazioneRichiesta(String motivazioneRichiesta) {
	this.motivazioneRichiesta = motivazioneRichiesta;
}

public String getIndirizzo() {
	return indirizzo;
}

public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}

public String getCitta() {
	return citta;
}

public void setCitta(String citta) {
	this.citta = citta;
}

public String getCap() {
	return cap;
}

public void setCap(String cap) {
	this.cap = cap;
}

public String getPaese() {
	return paese;
}

public void setPaese(String paese) {
	this.paese = paese;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public String getFax() {
	return fax;
}

public void setFax(String fax) {
	this.fax = fax;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPartitaIva() {
	return partitaIva;
}

public void setPartitaIva(String partitaIva) {
	this.partitaIva = partitaIva;
}

public String getCodiceFiscale() {
	return codiceFiscale;
}

public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
}

public List<String> getListaSpecializzazioni() {
	return listaSpecializzazioni;
}

public void setListaSpecializzazioni(List<String> listaSpecializzazioni) {
	this.listaSpecializzazioni = listaSpecializzazioni;
}

public String getUtenteRifiuto() {
	return utenteRifiuto;
}

public void setUtenteRifiuto(String utenteRifiuto) {
	this.utenteRifiuto = utenteRifiuto;
}

public String getUtenteRegistrazione() {
	return utenteRegistrazione;
}

public void setUtenteRegistrazione(String utenteRegistrazione) {
	this.utenteRegistrazione = utenteRegistrazione;
}

public String getUtenteAutorizzatore() {
	return utenteAutorizzatore;
}

public void setUtenteAutorizzatore(String utenteAutorizzatore) {
	this.utenteAutorizzatore = utenteAutorizzatore;
}

public String getRequestingUserID() {
	return requestingUserID;
}

public void setRequestingUserID(String requestingUserID) {
	this.requestingUserID = requestingUserID;
}

public String getLinkAlDocumentoLegaliEsterni() {
	return linkAlDocumentoLegaliEsterni;
}

public void setLinkAlDocumentoLegaliEsterni(String linkAlDocumentoLegaliEsterni) {
	this.linkAlDocumentoLegaliEsterni = linkAlDocumentoLegaliEsterni;
}

public String getMittentePec() {
	return mittentePec;
}

public void setMittentePec(String mittentePec) {
	this.mittentePec = mittentePec;
}

public String getDestinatarioPec() {
	return destinatarioPec;
}

public void setDestinatarioPec(String destinatarioPec) {
	this.destinatarioPec = destinatarioPec;
}

public String getOggettoPec() {
	return oggettoPec;
}

public void setOggettoPec(String oggettoPec) {
	this.oggettoPec = oggettoPec;
}

public String getDataVerifica() {
	return dataVerifica;
}

public void setDataVerifica(String dataVerifica) {
	this.dataVerifica = dataVerifica;
}

public String getUtenteVerificatore() {
	return utenteVerificatore;
}

public void setUtenteVerificatore(String utenteVerificatore) {
	this.utenteVerificatore = utenteVerificatore;
}

public String getBcSow() {
	return bcSow;
}

public void setBcSow(String bcSow) {
	this.bcSow = bcSow;
}
}
