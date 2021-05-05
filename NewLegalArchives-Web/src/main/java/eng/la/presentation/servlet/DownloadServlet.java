package eng.la.presentation.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@@DDS import com.filenet.api.core.Document;
import eng.la.persistence.*;
import org.apache.commons.io.IOUtils;

//@@DDS import com.filenet.api.core.Document;
import it.snam.ned.libs.dds.dtos.v2.Document;

import eng.la.business.DocumentoService;
import eng.la.model.view.DocumentoView;
import eng.la.util.SpringUtil;
import org.apache.log4j.Logger;

public class DownloadServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(DownloadServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean onlyfn = false;
		boolean isPenale = false;
		logger.info("DownloadServlet ->>>>>>>> sto scaricando il file --- INIZIO");
		if (req.getParameter("uuid") != null) { 
			onlyfn = "1".equals(req.getParameter("onlyfn")) ? true : false; 
			isPenale = "1".equals(req.getParameter("isp")) ? true : false; 
			
			String uuid = req.getParameter("uuid");
			ByteArrayInputStream is = null;
			OutputStream os = null;
			byte[] contenuto = null;
			try {
				String nomeFile = null;
				String contentType = null;

				if (isPenale) {
					//@@DDS DocumentaleCryptDAO daoCrypt = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
					DocumentaleDdsCryptDAO daoDdsCrypt = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
					//@@DDS com.filenet.api.core.Document document = daoDdsCrypt.leggiDocumentoUUID(uuid);
					Document document = daoDdsCrypt.leggiDocumentoUUID(uuid);
					if (document != null && document.getContents()!= null && document.getContents().get(0)!= null) {
						//@@DDS nomeFile = document.get_Name();
						//@@DDS contentType = document.get_MimeType();
						nomeFile = document.getContents().get(0).getContentsName();
						contentType = document.getContents().get(0).getContentsMimeType();
					} else {
						resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Non hai i permessi per scaricare il documento");
					}

					//@@DDS contenuto = daoCrypt.leggiContenutoDocumento(uuid);
					contenuto = daoDdsCrypt.leggiContenutoDocumento(uuid);
					logger.debug("@@DDS --------------------- contenuto " + contenuto);
				} else {


					//@@DDSDocumentaleDAO dao = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
					DocumentaleDdsDAO daoDds = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
					Document document = null;
					DocumentoView documento = null;
					if (onlyfn) {

						//@@DDS com.filenet.api.core.Document document = dao.leggiDocumentoUUID(uuid);
						document = daoDds.leggiDocumentoUUID(uuid);

						if (document != null && document.getContents() != null && document.getContents().get(0) != null) {
							//@@DDS nomeFile = document.get_Name();
							//@@DDS contentType = document.get_MimeType();
							nomeFile = document.getContents().get(0).getContentsName();
							contentType = document.getContents().get(0).getContentsMimeType();
						} else {
							resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
									"Non hai i permessi per scaricare il documento");
						}
					} else {
						DocumentoService documentoService = (DocumentoService) SpringUtil.getBean("documentoService");
						documento = documentoService.leggi(uuid);
						if (documento != null && documento.getVo() != null) {
							nomeFile = documento.getVo().getNomeFile();
							contentType = documento.getVo().getContentType();

						} else {
							resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
									"Non hai i permessi per scaricare il documento");
						}
					}
					//@@DDS contenuto = dao.leggiContenutoDocumento(uuid);
					contenuto = daoDds.leggiContenutoDocumento(uuid);
					logger.debug("@@DDS --------------------- contenuto " + contenuto);
				}
				if (contenuto != null) {
					resp.setContentLength(contenuto.length);
				}
				logger.info("DownloadServlet ->>>>>>>> sto scaricando il file FINE");
				resp.setContentType(contentType);
				resp.setHeader("Content-Disposition", "attachment;filename=" + nomeFile);
				resp.setHeader("Cache-control", "");
				is = new ByteArrayInputStream(contenuto);
				os = resp.getOutputStream();
				IOUtils.copy(is, os);

			} catch (Throwable e) {
				e.printStackTrace();
				resp.sendError(500, "Error download document by uuid: " + uuid);

			} finally {
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

	}
}
