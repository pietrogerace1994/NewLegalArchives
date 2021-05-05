package eng.la.persistence;

import java.util.List;

import eng.la.model.Articolo;
import eng.la.model.DocumentoArticolo;

public interface EmailDAO {
	public Articolo leggiEmail(long id) throws Throwable;

	public List<Articolo> leggiEmail() throws Throwable;

	public Articolo salvaEmail(Articolo vo) throws Throwable;

	public void cancellaEmail(long id) throws Throwable;

	public void modificaEmail(Articolo vo) throws Throwable;

	public List<Articolo> cerca(String oggetto, String dal, String al, int elementiPerPagina, int numeroPagina,
			String ordinamento, String tipoOrdinamento, String contenutoBreve, String comboCategoria) throws Throwable;

	public Long conta(String oggetto, String dal, String al) throws Throwable;

	public List<DocumentoArticolo> leggiArticoloDocbyId(long id) throws Throwable;

	public void cancellaDocumento(long documentoId) throws Throwable;

	public void eliminaArticoliPerCategoria(long id) throws Throwable;
}
