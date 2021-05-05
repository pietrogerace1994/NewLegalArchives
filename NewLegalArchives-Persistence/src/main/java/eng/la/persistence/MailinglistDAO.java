package eng.la.persistence;

import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.Mailinglist;
import eng.la.model.MailinglistDettaglio;

public interface MailinglistDAO {

	public List<Mailinglist> leggiMailinglist(String codiceCategoria) throws Throwable;

	public List<Mailinglist> leggiMailinglist() throws Throwable;

	public Mailinglist salvaMailinglist(Mailinglist vo) throws Throwable;

	public void cancellaMailinglist(long id) throws Throwable;

	public Mailinglist leggiMailinglist(long id) throws Throwable;

	public void salvaMailinglistDettaglio(MailinglistDettaglio vo) throws Throwable;

	public List<MailinglistDettaglio> leggiMailinglistDettaglio(long idMailinglist) throws Throwable;

	List<CategoriaMailinglist> listaCategoriaMailinglist(String lingua) throws Throwable;

	CategoriaMailinglist getCategoria(long id) throws Throwable;

	CategoriaMailinglist leggiCategoria(String codice, String lingua) throws Throwable;

	public void cancellaMailingListDettaglio(long id);

	public void inserisciMailingListDettagli(MailinglistDettaglio mailingListDettaglio);

	public Mailinglist leggi(long id, FetchMode fetchMode);

	List<MailinglistDettaglio> leggiMailingListDettagliobyId(long id) throws Throwable;

	public void modifica(long id) throws Throwable;

	void modifica(Mailinglist vo) throws Throwable;

	public void cancellaArticoli(long id) throws Throwable;

	public List<CategoriaMailinglist> categorieMailingList() throws Throwable;

	public List<Mailinglist> categorieUtenteMailingList(Long rubricaId) throws Throwable;

	public void cancellaEmail(long id) throws Throwable;

	public List<CategoriaMailinglist> leggiSottoCategoriaMailingList(Long idCategoria) throws Throwable;

}
