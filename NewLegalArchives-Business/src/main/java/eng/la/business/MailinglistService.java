package eng.la.business;

import java.util.Collection;
import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.MailinglistDettaglioView;
import eng.la.model.view.MailinglistView;
import eng.la.model.view.RubricaView;

public interface MailinglistService {

	public Collection<MailinglistView> leggiMailinglist() throws Throwable;

	public Collection<MailinglistView> leggiMailinglist(String codiceCategoria) throws Throwable;

	public MailinglistView salvaMailinglist(MailinglistView mailinglistView) throws Throwable;

	public void salvaMailinglist(String codiceCategoria, String nome, List<RubricaView> listaRubricaView)
			throws Throwable;

	public void cancellaMailinglist(long id) throws Throwable;

	public void salvaMailinglistDettaglio(MailinglistDettaglioView dettaglioView) throws Throwable;

	public Collection<MailinglistDettaglioView> leggiMailinglistDettaglio(long idMailinglist) throws Throwable;

	CategoriaMailinglist getCategoria(long id) throws Throwable;

	List<CategoriaMailinglistView> listaCategoriaMailinglist(String lingua) throws Throwable;

	MailinglistView leggi(long id, FetchMode fetchMode) throws Throwable;

	List<MailinglistDettaglioView> leggiMailingListDettagliobyId(long id) throws Throwable;

	void modifica(MailinglistView mailingListView) throws Throwable;

	void modifica(long id) throws Throwable;

	public MailinglistView leggi(Long mailingListId) throws Throwable;

	public void cancellaArticoli(long id) throws Throwable;

	public List<CategoriaMailinglist> leggiCategorieMailingList() throws Throwable;

	public List<Long> getCategorieUtente(Long rubricaId) throws Throwable;

	public void cancellaEmail(long id) throws Throwable;

	public List<CategoriaMailinglist> leggiSottoCategoriaMailingList(Long idCategoria) throws Throwable;
}
