package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

//import com.filenet.api.core.Folder;

import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.service.FileNetWrapper;
import junit.framework.TestCase;

public class FileNetTest extends TestCase {
	public void testScrittura() {
		/*InputStream stream = null;
		try {
			FileNetWrapper wrapper = _connect();

			Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
			proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, 1);

			String uuid = UUID.randomUUID().toString();
			String mimeType = "text/plain";
			Folder cartellaDestinazione = wrapper.leggiCartella("/FASCICOLI/2016/GIUGNO/Facicolo di prova 1/");
			stream = FileNetTest.class.getResourceAsStream("/config.properties");
			String nomeDocumento = "config" + System.currentTimeMillis() + ".properties";

			com.filenet.api.core.Document documentoSalvato = wrapper.creaDocumento(uuid, nomeDocumento,
					FileNetClassNames.PROTOCOLLO_DOCUMENT, mimeType, proprietaDocumento, cartellaDestinazione, stream);

			_salvaContenutoDocumento(documentoSalvato.get_Id().toString(), wrapper);

			_creaCartella(wrapper);

			assertTrue(true);
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			IOUtils.closeQuietly(stream);
		}*/
	}

	private void _creaCartella(FileNetWrapper wrapper) throws Throwable {
		/*Folder cartellaPadre = wrapper.leggiCartella("/FASCICOLI/2016/GIUGNO/Facicolo di prova 1/");
		String uuid = UUID.randomUUID().toString();
		Folder cartella = wrapper.creaCartella(uuid, "CreoCartellaDiTest", FileNetClassNames.ATTO_FOLDER, null,
				cartellaPadre);
		wrapper.modificaCartella(uuid, cartella.get_FolderName() + System.currentTimeMillis(), null);
		*/
	}

	private void _salvaContenutoDocumento(String uuid, FileNetWrapper wrapper) throws Throwable {
		/*byte[] bytes = wrapper.leggiContenutoDocumento(uuid);
		if (bytes != null) {
			FileOutputStream fos = new FileOutputStream(File.createTempFile("test", ".properties"));
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			IOUtils.copy(bis, fos);
		}*/
	}

	private FileNetWrapper _connect() {
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = FileNetTest.class.getResourceAsStream("/config.properties");
			properties.load(is);
			String url = properties.getProperty("filenet.url");
			String user = properties.getProperty("filenet.username");
			String passw = properties.getProperty("filenet.password");
			String stanza = properties.getProperty("filenet.stanza");
			String osname = properties.getProperty("filenet.osname");
			FileNetWrapper wrapper = new FileNetWrapper(url, stanza, osname, user, passw);
			return wrapper;
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}
}
