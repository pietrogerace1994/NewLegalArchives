package eng.la.model.custom;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.multipart.MultipartFile;

public class AllegatoMultipartFile implements MultipartFile {

	private String name;
	private String originalFileName;
	private String contentType;
	private long size;
	private byte[] bytes;

	private String[] to;
	private String[] cc;
	private String from;
	private String oggetto;
	private String dataInvio;
	private String dataRicezione;

	public AllegatoMultipartFile setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
		return this;
	}

	public AllegatoMultipartFile setName(String name) {
		this.name = name;
		return this;
	}

	public AllegatoMultipartFile setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public AllegatoMultipartFile setSize(long size) {
		this.size = size;
		return this;
	}

	public AllegatoMultipartFile setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		return originalFileName;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return bytes;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		throw new NotImplementedException();
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

}
