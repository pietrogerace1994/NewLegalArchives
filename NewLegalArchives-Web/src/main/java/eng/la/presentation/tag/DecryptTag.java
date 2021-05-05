package eng.la.presentation.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

import org.apache.commons.lang.math.NumberUtils;

import eng.la.util.SpringUtil;

public class DecryptTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idOggetto;
	private String dato;

	public void setIdOggetto(String idOggetto) {
		this.idOggetto = idOggetto;
	}

	public String getIdOggetto() {
		return idOggetto;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getDato() {
		return dato;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut(); 
		try {
			String datoDaStampareAVideo = "n.d.";
			if (idOggetto != null && NumberUtils.toLong(idOggetto) > 0) {
				  datoDaStampareAVideo = decrypt(getDato());

			}else{
				  datoDaStampareAVideo = decrypt(getDato());
			}
			out.write(datoDaStampareAVideo);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
		}
		return EVAL_PAGE;

	}
  
	public String decrypt(String bodyContent) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String valoreRitorno = null;
		try {
			DataSource ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			pst = c.prepareStatement("SELECT CIFRA_DECIFRA.decifra(?) as valore FROM DUAL");
			pst.setString(1, bodyContent);
			rs = pst.executeQuery();
			if (rs.next()) {
				valoreRitorno = rs.getString("valore");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			close(c, rs, pst);
		}
		return valoreRitorno;
	}

	private void close(Connection c, ResultSet rs, PreparedStatement pst) {
		try {
			rs.close();
		} catch (Throwable e) {
		}
		try {
			pst.close();
		} catch (Throwable e) {
		}
		try {
			c.close();
		} catch (Throwable e) {
		}
	}

}
