package eng.la.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.CategoriaMailinglist;
import eng.la.model.CategoriaMailinglistStyle;
import eng.la.model.ClasseWf;
import eng.la.model.GruppoUtente;
import eng.la.model.PosizioneSocieta;
import eng.la.model.StatoBeautyContest;
import eng.la.model.StatoFascicolo;
import eng.la.model.StatoIncarico;
import eng.la.model.StatoNewsletter;
import eng.la.model.StatoProforma;
import eng.la.model.StatoProtocollo;
import eng.la.model.StatoSchedaFondoRischi;
import eng.la.model.StatoVendorManagement;
import eng.la.model.StatoWf;
import eng.la.model.TipoCategDocumentale;
import eng.la.model.TipoContenzioso;
import eng.la.model.TipoCorrelazione;
import eng.la.model.TipoEntita;
import eng.la.model.TipoPrestNotarile;
import eng.la.model.TipoProfessionista;
import eng.la.model.TipoSoggettoIndagato;
import eng.la.model.TipoValuta;
import eng.la.model.ValoreCausa;
import eng.la.model.view.CategoriaMailinglistStyleView;
import eng.la.model.view.CategoriaMailinglistView;
import eng.la.model.view.ClasseWfView;
import eng.la.model.view.GruppoUtenteView;
import eng.la.model.view.PosizioneSocietaView;
import eng.la.model.view.StatoBeautyContestView;
import eng.la.model.view.StatoFascicoloView;
import eng.la.model.view.StatoIncaricoView;
import eng.la.model.view.StatoNewsletterView;
import eng.la.model.view.StatoProformaView;
import eng.la.model.view.StatoProtocolloView;
import eng.la.model.view.StatoSchedaFondoRischiView;
import eng.la.model.view.StatoVendorManagementView;
import eng.la.model.view.StatoWfView;
import eng.la.model.view.TipoCategDocumentaleView;
import eng.la.model.view.TipoContenziosoView;
import eng.la.model.view.TipoCorrelazioneView;
import eng.la.model.view.TipoEntitaView;
import eng.la.model.view.TipoPrestNotarileView;
import eng.la.model.view.TipoProfessionistaView;
import eng.la.model.view.TipoSoggettoIndagatoView;
import eng.la.model.view.TipoValutaView;
import eng.la.model.view.ValoreCausaView;
import eng.la.persistence.AnagraficaStatiTipiDAO;

@Service("anagraficaStatiTipiService")
public class AnagraficaStatiTipiServiceImpl extends BaseService<StatoFascicolo, StatoFascicoloView> implements AnagraficaStatiTipiService {
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;

	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}

	public void setAnagraficaStatiTipiDAO(AnagraficaStatiTipiDAO anagraficaStatiTipiDAO) {
		this.anagraficaStatiTipiDAO = anagraficaStatiTipiDAO;
	}

	@Override
	public List<StatoFascicoloView> leggiStatiFascicolo() throws Throwable {
		List<StatoFascicolo> statiFascicolo = anagraficaStatiTipiDAO.leggiStatiFascicolo();
		if (statiFascicolo != null) {
			List<StatoFascicoloView> listaStatiFascicoloView = new ArrayList<StatoFascicoloView>();
			for (StatoFascicolo stato : statiFascicolo) {
				StatoFascicoloView view = new StatoFascicoloView();
				view.setVo(stato);
				listaStatiFascicoloView.add(view);
			}
			return listaStatiFascicoloView;
		}
		return null;
	}

	@Override
	public List<StatoFascicoloView> leggiStatiFascicolo(String lingua) throws Throwable {
		List<StatoFascicolo> statiFascicolo = anagraficaStatiTipiDAO.leggiStatiFascicolo(lingua);
		if (statiFascicolo != null) {
			List<StatoFascicoloView> listaStatiFascicoloView = new ArrayList<StatoFascicoloView>();
			for (StatoFascicolo stato : statiFascicolo) {
				StatoFascicoloView view = new StatoFascicoloView();
				view.setVo(stato);
				listaStatiFascicoloView.add(view);
			}
			return listaStatiFascicoloView;
		}
		return null;
	}

	@Override
	public StatoFascicoloView leggiStatiFascicolo(String codice, String lingua) throws Throwable {
		StatoFascicolo statoFascicolo = anagraficaStatiTipiDAO.leggiStatoFascicolo(codice, lingua);
		if (statoFascicolo != null) {
			StatoFascicoloView view = new StatoFascicoloView();
			view.setVo(statoFascicolo);
			return view;
		}
		return null;
	}

	@Override
	public List<StatoWfView> leggiStatiWf() throws Throwable {
		List<StatoWf> statiWf = anagraficaStatiTipiDAO.leggiStatiWf();
		if (statiWf != null) {
			List<StatoWfView> listaStatiWfView = new ArrayList<StatoWfView>();
			for (StatoWf stato : statiWf) {
				StatoWfView view = new StatoWfView();
				view.setVo(stato);
				listaStatiWfView.add(view);
			}
			return listaStatiWfView;
		}
		return null;
	}

	@Override
	public List<StatoWfView> leggiStatiWf(String lingua) throws Throwable {
		List<StatoWf> statiWf = anagraficaStatiTipiDAO.leggiStatiWf(lingua);
		if (statiWf != null) {
			List<StatoWfView> listaStatiWfView = new ArrayList<StatoWfView>();
			for (StatoWf stato : statiWf) {
				StatoWfView view = new StatoWfView();
				view.setVo(stato);
				listaStatiWfView.add(view);
			}
			return listaStatiWfView;
		}
		return null;
	}

	@Override
	public StatoWfView leggiStatiWf(String codice, String lingua) throws Throwable {
		StatoWf statoWf = anagraficaStatiTipiDAO.leggiStatoWf(codice, lingua);
		if (statoWf != null) {
			StatoWfView view = new StatoWfView();
			view.setVo(statoWf);
			return view;
		}
		return null;
	}

	@Override
	public List<StatoIncaricoView> leggiStatiIncarico() throws Throwable {
		List<StatoIncarico> statiIncarico = anagraficaStatiTipiDAO.leggiStatiIncarico();
		if (statiIncarico != null) {
			List<StatoIncaricoView> listaStatiIncaricoView = new ArrayList<StatoIncaricoView>();
			for (StatoIncarico stato : statiIncarico) {
				StatoIncaricoView view = new StatoIncaricoView();
				view.setVo(stato);
				listaStatiIncaricoView.add(view);
			}
			return listaStatiIncaricoView;
		}
		return null;
	}

	@Override
	public List<StatoIncaricoView> leggiStatiIncarico(String lingua) throws Throwable {
		List<StatoIncarico> statiIncarico = anagraficaStatiTipiDAO.leggiStatiIncarico(lingua);
		if (statiIncarico != null) {
			List<StatoIncaricoView> listaStatiIncaricoView = new ArrayList<StatoIncaricoView>();
			for (StatoIncarico stato : statiIncarico) {
				StatoIncaricoView view = new StatoIncaricoView();
				view.setVo(stato);
				listaStatiIncaricoView.add(view);
			}
			return listaStatiIncaricoView;
		}
		return null;
	}
	
	@Override
	public List<StatoSchedaFondoRischiView> leggiStatiSchedaFondoRischi(String lingua) throws Throwable {
		List<StatoSchedaFondoRischi> statiSchedaFondoRischi = anagraficaStatiTipiDAO.leggiStatiSchedaFondoRischi(lingua);
		if (statiSchedaFondoRischi != null) {
			List<StatoSchedaFondoRischiView> listaStatiSchedaFondoRischiView = new ArrayList<StatoSchedaFondoRischiView>();
			for (StatoSchedaFondoRischi stato : statiSchedaFondoRischi) {
				StatoSchedaFondoRischiView view = new StatoSchedaFondoRischiView();
				view.setVo(stato);
				listaStatiSchedaFondoRischiView.add(view);
			}
			return listaStatiSchedaFondoRischiView;
		}
		return null;
	}
	
	@Override
	public StatoBeautyContestView leggiStatoBeautyContest(String codice, String lingua) throws Throwable {
		StatoBeautyContest statoBeautyContest = anagraficaStatiTipiDAO.leggiStatoBeautyContest(codice, lingua);
		if (statoBeautyContest != null) {
			StatoBeautyContestView view = new StatoBeautyContestView();
			view.setVo(statoBeautyContest);
			return view;
		}
		return null;
	}

	@Override
	public StatoIncaricoView leggiStatoIncarico(String codice, String lingua) throws Throwable {
		StatoIncarico statoIncarico = anagraficaStatiTipiDAO.leggiStatoIncarico(codice, lingua);
		if (statoIncarico != null) {
			StatoIncaricoView view = new StatoIncaricoView();
			view.setVo(statoIncarico);
			return view;
		}
		return null;
	}
	
	@Override
	public StatoSchedaFondoRischiView leggiStatoSchedaFondoRischi(String codice, String lingua) throws Throwable {
		StatoSchedaFondoRischi statoSchedaFondoRischi = anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(codice, lingua);
		if (statoSchedaFondoRischi != null) {
			StatoSchedaFondoRischiView view = new StatoSchedaFondoRischiView();
			view.setVo(statoSchedaFondoRischi);
			return view;
		}
		return null;
	}
	
	@Override
	public StatoVendorManagementView leggiStatoVendorManagement(String codice, String lingua) throws Throwable {
		StatoVendorManagement statoVendorManagement = anagraficaStatiTipiDAO.leggiStatoVendorManagement(codice, lingua);
		if (statoVendorManagement != null) {
			StatoVendorManagementView view = new StatoVendorManagementView();
			view.setVo(statoVendorManagement);
			return view;
		}
		return null;
	}

	@Override
	public List<TipoContenziosoView> leggiTipoContenzioso() throws Throwable {
		List<TipoContenzioso> tipiContenzioso = anagraficaStatiTipiDAO.leggiTipiContenzioso();
		if (tipiContenzioso != null) {
			List<TipoContenziosoView> listaTipi = new ArrayList<TipoContenziosoView>();
			for (TipoContenzioso tipo : tipiContenzioso) {
				TipoContenziosoView view = new TipoContenziosoView();
				view.setVo(tipo);
				listaTipi.add(view);
			}
			return listaTipi;
		}
		return null;
	}

	@Override
	public List<TipoContenziosoView> leggiTipoContenzioso(String lingua, boolean tutte) throws Throwable {
		List<TipoContenzioso> tipiContenzioso = anagraficaStatiTipiDAO.leggiTipiContenzioso(lingua, tutte);
		if (tipiContenzioso != null) {
			List<TipoContenziosoView> listaTipi = new ArrayList<TipoContenziosoView>();
			for (TipoContenzioso tipo : tipiContenzioso) {
				TipoContenziosoView view = new TipoContenziosoView();
				view.setVo(tipo);
				listaTipi.add(view);
			}
			return listaTipi;
		}
		return null;
	}

	@Override
	public TipoContenziosoView leggiTipoContenzioso(String codice, String lingua, boolean tutte) throws Throwable {
		TipoContenzioso tipo = anagraficaStatiTipiDAO.leggiTipoContenzioso(codice, lingua, tutte);
		if (tipo != null) {
			TipoContenziosoView view = new TipoContenziosoView();
			view.setVo(tipo);
			return view;
		}
		return null;
	}

	@Override
	public List<ClasseWfView> leggiClasseWf() throws Throwable {
		List<ClasseWf> classiWf = anagraficaStatiTipiDAO.leggiClassiWf();
		if (classiWf != null) {
			List<ClasseWfView> listaClassi = new ArrayList<ClasseWfView>();
			for (ClasseWf classe : classiWf) {
				ClasseWfView view = new ClasseWfView();
				view.setVo(classe);
				listaClassi.add(view);
			}
			return listaClassi;
		}
		return null;
	}

	@Override
	public List<GruppoUtenteView> leggiGruppoUtente() throws Throwable {
		List<GruppoUtente> gruppiUtente = anagraficaStatiTipiDAO.leggiGruppiUtente();
		if (gruppiUtente != null) {
			List<GruppoUtenteView> listaGruppi = new ArrayList<GruppoUtenteView>();
			for (GruppoUtente gruppo : gruppiUtente) {
				GruppoUtenteView view = new GruppoUtenteView();
				view.setVo(gruppo);
				listaGruppi.add(view);
			}
			return listaGruppi;
		}
		return null;
	}

	@Override
	public ClasseWfView leggiClasseWf(String codice) throws Throwable {
		ClasseWf tipo = anagraficaStatiTipiDAO.leggiClasseWf(codice);
		if (tipo != null) {
			ClasseWfView view = new ClasseWfView();
			view.setVo(tipo);
			return view;
		}
		return null;
	}

	@Override
	public GruppoUtenteView leggiGruppoUtente(String codice) throws Throwable {
		GruppoUtente gruppo = anagraficaStatiTipiDAO.leggiGruppoUtente(codice);
		if (gruppo != null) {
			GruppoUtenteView view = new GruppoUtenteView();
			view.setVo(gruppo);
			return view;
		}
		return null;
	}

	// sezione tipo correlazione

	@Override
	public List<TipoCorrelazioneView> leggiTipoCorrelazione() throws Throwable {
		List<TipoCorrelazione> tipiCorrelazione = anagraficaStatiTipiDAO.leggiTipiCorrelazione();
		if (tipiCorrelazione != null) {
			List<TipoCorrelazioneView> listaTipi = new ArrayList<TipoCorrelazioneView>();
			for (TipoCorrelazione tipo : tipiCorrelazione) {
				TipoCorrelazioneView view = new TipoCorrelazioneView();
				view.setVo(tipo);
				listaTipi.add(view);
			}
			return listaTipi;
		}
		return null;
	}

	@Override
	public List<TipoCorrelazioneView> leggiTipoCorrelazione(String lingua) throws Throwable {
		List<TipoCorrelazione> tipiCorrelazione = anagraficaStatiTipiDAO.leggiTipiCorrelazione(lingua);
		if (tipiCorrelazione != null) {
			List<TipoCorrelazioneView> listaTipi = new ArrayList<TipoCorrelazioneView>();
			for (TipoCorrelazione tipo : tipiCorrelazione) {
				TipoCorrelazioneView view = new TipoCorrelazioneView();
				view.setVo(tipo);
				listaTipi.add(view);
			}
			return listaTipi;
		}
		return null;
	}

	@Override
	public TipoCorrelazioneView leggiTipoCorrelazione(String codice, String lingua) throws Throwable {
		TipoCorrelazione tipo = anagraficaStatiTipiDAO.leggiTipoCorrelazione(codice, lingua);
		if (tipo != null) {
			TipoCorrelazioneView view = new TipoCorrelazioneView();
			view.setVo(tipo);
			return view;
		}
		return null;
	}

	// _TODO : da completare ( DAO, DAOImpl e Service, ServiceImpl )
	@Override
	public TipoCorrelazioneView leggiTipoCorrelazione(Long tipoCorrelazioneId) throws Throwable {
		TipoCorrelazione tipoCorrelazione = anagraficaStatiTipiDAO.leggiTipoCorrelazione(tipoCorrelazioneId);
		TipoCorrelazioneView view = new TipoCorrelazioneView();
		view.setVo(tipoCorrelazione);
		return view;
	}

	// ---

	@Override
	public ValoreCausaView leggiValoreCausa(Long valoreCausaId) throws Throwable {
		ValoreCausa valoreCausa = anagraficaStatiTipiDAO.leggiValoreCausa(valoreCausaId);
		ValoreCausaView view = new ValoreCausaView();
		view.setVo(valoreCausa);
		return view;
	}

	@Override
	public List<ValoreCausaView> leggiValoreCausa(String lingua, boolean tutte) throws Throwable {
		List<ValoreCausa> valoriCausa = anagraficaStatiTipiDAO.leggiValoreCausa(lingua, tutte);
		if (valoriCausa != null) {
			List<ValoreCausaView> listaValoriCausa = new ArrayList<ValoreCausaView>();
			for (ValoreCausa valoreCausa : valoriCausa) {
				ValoreCausaView view = new ValoreCausaView();
				view.setVo(valoreCausa);
				listaValoriCausa.add(view);
			}
			return listaValoriCausa;
		}
		return null;
	}

	@Override
	public List<TipoEntitaView> leggiTipoEntita(String lingua, boolean tutte) throws Throwable {
		List<TipoEntita> tipiEntita = anagraficaStatiTipiDAO.leggiTipoEntita(lingua, tutte);
		if (tipiEntita != null) {
			List<TipoEntitaView> listaTipoEntita = new ArrayList<TipoEntitaView>();
			for (TipoEntita tipo : tipiEntita) {
				TipoEntitaView view = new TipoEntitaView();
				view.setVo(tipo);
				listaTipoEntita.add(view);
			}
			return listaTipoEntita;
		}
		return null;
	}

	@Override
	public List<PosizioneSocietaView> leggiPosizioneSocietaPerSettoreGiuridicoId(long settoreGiuridicoId, boolean tutte)
			throws Throwable {
		List<PosizioneSocieta> posizioniSocieta = anagraficaStatiTipiDAO
				.leggiPosizioneSocietaPerSettoreGiuridicoId(settoreGiuridicoId, tutte);
		if (posizioniSocieta != null) {
			List<PosizioneSocietaView> listaPosizioniSocieta = new ArrayList<PosizioneSocietaView>();
			for (PosizioneSocieta posizione : posizioniSocieta) {
				PosizioneSocietaView view = new PosizioneSocietaView();
				view.setVo(posizione);
				listaPosizioniSocieta.add(view);
			}
			return listaPosizioniSocieta;
		}
		return null;
	}

	@Override
	public TipoEntitaView leggiTipoEntita(Long tipoEntitaId) throws Throwable {
		TipoEntita tipoEntita = anagraficaStatiTipiDAO.leggiTipoEntita(tipoEntitaId);
		TipoEntitaView view = new TipoEntitaView();
		view.setVo(tipoEntita);
		return view;
	}

	@Override
	public PosizioneSocietaView leggiPosizioneSocieta(long idPosizioneSocieta) throws Throwable {
		PosizioneSocieta posizioneSocieta = anagraficaStatiTipiDAO.leggiPosizioneSocieta(idPosizioneSocieta);
		PosizioneSocietaView view = new PosizioneSocietaView();
		view.setVo(posizioneSocieta);
		return view;
	}

	@Override
	public ValoreCausaView leggiValoreCausa(String codice, String lingua, boolean tutte) throws Throwable {
		ValoreCausa valoreCausa = anagraficaStatiTipiDAO.leggiValoreCausa(codice, lingua, tutte);
		ValoreCausaView view = new ValoreCausaView();
		view.setVo(valoreCausa);
		return view;
	}

	@Override
	public PosizioneSocietaView leggiPosizioneSocieta(String codice, String lingua, boolean tutte) throws Throwable {
		PosizioneSocieta posizioneSocieta = anagraficaStatiTipiDAO.leggiPosizioneSocieta(codice, lingua, tutte);
		PosizioneSocietaView view = new PosizioneSocietaView();
		view.setVo(posizioneSocieta);
		return view;
	}

	@Override
	public TipoEntitaView leggiTipoEntita(String codice, Locale locale, boolean tutte) throws Throwable {
		TipoEntita tipoEntita = anagraficaStatiTipiDAO.leggiTipoEntita(codice, locale.getLanguage().toUpperCase(),
				tutte);
		TipoEntitaView view = new TipoEntitaView();
		view.setVo(tipoEntita);
		return view;
	}

	@Override
	public TipoSoggettoIndagatoView leggiTipoSoggettoIndagato(String codice, Locale locale, boolean tutte)
			throws Throwable {
		TipoSoggettoIndagato tipo = anagraficaStatiTipiDAO.leggiTipoSoggettoIndagato(codice,
				locale.getLanguage().toUpperCase(), tutte);
		TipoSoggettoIndagatoView view = new TipoSoggettoIndagatoView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public List<TipoSoggettoIndagatoView> leggiTipoSoggettoIndagato(String lingua, boolean tutte) throws Throwable {
		List<TipoSoggettoIndagato> lista = anagraficaStatiTipiDAO.leggiTipoSoggettoIndagato(lingua, tutte);
		if (lista != null) {
			List<TipoSoggettoIndagatoView> listaView = new ArrayList<TipoSoggettoIndagatoView>();
			for (TipoSoggettoIndagato tipo : lista) {
				TipoSoggettoIndagatoView view = new TipoSoggettoIndagatoView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public TipoSoggettoIndagatoView leggiTipoSoggettoIndagato(long id) throws Throwable {
		TipoSoggettoIndagato tipo = anagraficaStatiTipiDAO.leggiTipoSoggettoIndagato(id);
		TipoSoggettoIndagatoView view = new TipoSoggettoIndagatoView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public List<TipoPrestNotarileView> leggiTipoPrestazioneNotarile(String lingua, boolean tutte) throws Throwable {
		List<TipoPrestNotarile> lista = anagraficaStatiTipiDAO.leggiTipoPrestazioneNotarile(lingua);
		if (lista != null) {
			List<TipoPrestNotarileView> listaView = new ArrayList<TipoPrestNotarileView>();
			for (TipoPrestNotarile tipo : lista) {
				TipoPrestNotarileView view = new TipoPrestNotarileView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public TipoPrestNotarileView leggiTipoPrestazioneNotarile(long id) throws Throwable {
		TipoPrestNotarile tipo = anagraficaStatiTipiDAO.leggiTipoPrestazioneNotarile(id);
		TipoPrestNotarileView view = new TipoPrestNotarileView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public TipoPrestNotarileView leggiTipoPrestazioneNotarile(String codice, Locale locale, boolean tutte)
			throws Throwable {
		TipoPrestNotarile tipo = anagraficaStatiTipiDAO.leggiTipoPrestazioneNotarile(codice,
				locale.getLanguage().toUpperCase(), tutte);
		TipoPrestNotarileView view = new TipoPrestNotarileView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public TipoCategDocumentaleView leggiTipoCategoriaDocumentale(String codice, Locale locale) throws Throwable {
		TipoCategDocumentale tipo = anagraficaStatiTipiDAO.leggiTipoCategoriaDocumentale(codice,
				locale.getLanguage().toUpperCase());
		TipoCategDocumentaleView view = new TipoCategDocumentaleView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public TipoProfessionistaView leggiTipoProfessionista(String codice, Locale locale) throws Throwable {
		TipoProfessionista tipo = anagraficaStatiTipiDAO.leggiTipoProfessionista(codice,
				locale.getLanguage().toUpperCase());
		TipoProfessionistaView view = new TipoProfessionistaView();
		view.setVo(tipo);
		return view;
	}

	@Override
	public List<TipoCategDocumentaleView> leggiTipoCategDocumentale(String lingua) throws Throwable {
		List<TipoCategDocumentale> lista = anagraficaStatiTipiDAO.leggiTipoCategoriaDocumentale(lingua);
		if (lista != null) {
			List<TipoCategDocumentaleView> listaView = new ArrayList<TipoCategDocumentaleView>();
			for (TipoCategDocumentale tipo : lista) {
				TipoCategDocumentaleView view = new TipoCategDocumentaleView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public List<TipoProfessionistaView> leggiTipoProfessionista(String lingua) throws Throwable {
		List<TipoProfessionista> lista = anagraficaStatiTipiDAO.leggiTipoProfessionista(lingua);
		if (lista != null) {
			List<TipoProfessionistaView> listaView = new ArrayList<TipoProfessionistaView>();
			for (TipoProfessionista tipo : lista) {
				TipoProfessionistaView view = new TipoProfessionistaView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public StatoProformaView leggiStatoProforma(String codice, String lingua) throws Throwable {
		StatoProforma stato = anagraficaStatiTipiDAO.leggiStatoProforma(codice, lingua);
		StatoProformaView view = new StatoProformaView();
		view.setVo(stato);
		return view;
	}

	@Override
	public TipoValutaView leggiTipoValuta(Long id) throws Throwable {
		TipoValuta tipoValuta = anagraficaStatiTipiDAO.leggiTipoValuta(id);
		TipoValutaView view = new TipoValutaView();
		view.setVo(tipoValuta);
		return view;
	}

	@Override
	public List<StatoProformaView> leggiStatiProforma(String lingua) throws Throwable {
		List<StatoProforma> lista = anagraficaStatiTipiDAO.leggiStatiProforma(lingua);
		if (lista != null) {
			List<StatoProformaView> listaView = new ArrayList<StatoProformaView>();
			for (StatoProforma tipo : lista) {
				StatoProformaView view = new StatoProformaView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public List<TipoValutaView> leggiTipoValuta(boolean tutte) throws Throwable {
		List<TipoValuta> lista = anagraficaStatiTipiDAO.leggiTipoValuta(tutte);
		if (lista != null) {
			List<TipoValutaView> listaView = new ArrayList<TipoValutaView>();
			for (TipoValuta tipo : lista) {
				TipoValutaView view = new TipoValutaView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public CategoriaMailinglistView leggiCategoria(long id) throws Throwable {
		CategoriaMailinglist vo = anagraficaStatiTipiDAO.leggiCategoria(id);
		if (vo != null) {
			CategoriaMailinglistView view = new CategoriaMailinglistView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	@Override
	public CategoriaMailinglistView leggiCategoria(String codice, String lingua, boolean tutte) throws Throwable {
		CategoriaMailinglist vo = anagraficaStatiTipiDAO.leggiCategoria(codice, lingua, tutte);
		if (vo != null) {
			CategoriaMailinglistView view = new CategoriaMailinglistView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	@Override
	public List<CategoriaMailinglistView> leggiCategorie(String lingua) throws Throwable {
		List<CategoriaMailinglist> lvo = anagraficaStatiTipiDAO.leggiCategorie(lingua );
		if (lvo != null) {
			List<CategoriaMailinglistView> lview = new ArrayList<CategoriaMailinglistView>();
			for (CategoriaMailinglist vo : lvo) {
				CategoriaMailinglistView view = new CategoriaMailinglistView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}

	@Override
	public List<CategoriaMailinglistView> leggiCategorie(long idPadre, boolean tutte) throws Throwable {
		List<CategoriaMailinglist> lvo = anagraficaStatiTipiDAO.leggiCategorie(idPadre, tutte);
		if (lvo != null) {
			List<CategoriaMailinglistView> lview = new ArrayList<CategoriaMailinglistView>();
			for (CategoriaMailinglist vo : lvo) {
				CategoriaMailinglistView view = new CategoriaMailinglistView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}

	@Override
	public void clearCache() {
		anagraficaStatiTipiDAO.clearCache();
	}

	@Override
	protected Class<StatoFascicolo> leggiClassVO() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Class<StatoFascicoloView> leggiClassView() {
		throw new UnsupportedOperationException();
	}

	@Override
	public StatoNewsletterView leggiStatoNewsletter(String codice, String language) throws Throwable {
		StatoNewsletter statoNewsletter = anagraficaStatiTipiDAO.leggiStatoNewsletter(codice, language);
		if (statoNewsletter != null) {
			StatoNewsletterView view = new StatoNewsletterView();
			view.setVo(statoNewsletter);
			return view;
		}
		return null;
	}

	@Override
	public StatoProtocolloView leggiStatoProtocollo(String cod, String language) throws Throwable {
		StatoProtocollo statoProtocollo = anagraficaStatiTipiDAO.leggiStatoProtocollo(cod, language);
		if (statoProtocollo != null) {
			StatoProtocolloView view = new StatoProtocolloView();
			view.setVo(statoProtocollo);
			return view;
		}
		return null;
	}

	@Override
	public List<StatoProtocolloView> leggiStatiProtocollo(String lingua) throws Throwable {
		List<StatoProtocollo> lista = anagraficaStatiTipiDAO.leggiStatiProtocollo(lingua);
		if (lista != null) {
			List<StatoProtocolloView> listaView = new ArrayList<StatoProtocolloView>();
			for (StatoProtocollo tipo : lista) {
				StatoProtocolloView view = new StatoProtocolloView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public List<CategoriaMailinglistStyleView> leggiCategoriaMailingListStyle() throws Throwable {
		List<CategoriaMailinglistStyle> lista = anagraficaStatiTipiDAO.leggiCategoriaMailingListStyle();
		if (lista != null) {
			List<CategoriaMailinglistStyleView> listaView = new ArrayList<CategoriaMailinglistStyleView>();
			for (CategoriaMailinglistStyle tipo : lista) {
				CategoriaMailinglistStyleView view = new CategoriaMailinglistStyleView();
				view.setVo(tipo);
				listaView.add(view);
			}
			return listaView;
		}
		return null;
	}

	@Override
	public CategoriaMailinglistStyle leggiCategoriaMailingListStyle(Long colorselector) throws Throwable {
		return anagraficaStatiTipiDAO.leggiCategoriaMailingListStyle(colorselector);
	}

	@Override
	public Long leggiCategoriaMailingListNextOrd() throws Throwable {
		return (long) (anagraficaStatiTipiDAO.leggiCategoriaMailingListNextOrd()+1);
	}

	@Override
	public Long leggiCategoriaMailingListNextId() throws Throwable {
		return (long) (anagraficaStatiTipiDAO.leggiCategoriaMailingListNextId()+1);
	}
	
	@Override
	public List<StatoBeautyContestView> leggiStatiBeautyContest(String lingua) throws Throwable {
		List<StatoBeautyContest> statiBeautyContest = anagraficaStatiTipiDAO.leggiStatiBeautyContest(lingua);
		if (statiBeautyContest != null) {
			List<StatoBeautyContestView> listaStatiBeautyContestView = new ArrayList<StatoBeautyContestView>();
			for (StatoBeautyContest stato : statiBeautyContest) {
				StatoBeautyContestView view = new StatoBeautyContestView();
				view.setVo(stato);
				listaStatiBeautyContestView.add(view);
			}
			return listaStatiBeautyContestView;
		}
		return null;
	}

}
