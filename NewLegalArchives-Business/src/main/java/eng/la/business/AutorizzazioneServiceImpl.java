package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Autorizzazione;
import eng.la.model.view.AutorizzazioneView;
import eng.la.persistence.AutorizzazioneDAO;
import eng.la.util.costants.Costanti;


/**
 * <h1>Classe di business AutorizzazioneServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di lettura
 * sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author 
 * @version 1.0
 * @since 2016-07-01
 */
@Service("autorizzazioneService")
public class AutorizzazioneServiceImpl extends BaseService<Autorizzazione,AutorizzazioneView> implements AutorizzazioneService{
	
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	
	public AutorizzazioneDAO getAutorizzazioneDAO() {
		return autorizzazioneDAO;
	}

	@Override
	public AutorizzazioneView leggiAutorizzazioneUtenteCorrente(long idEntita, String nomeClasse) throws Throwable {

		Autorizzazione autorizzazione = autorizzazioneDAO.leggiAutorizzazioneUtenteCorrente(idEntita, nomeClasse);
		return convertiVoInView(autorizzazione);
	}
	
	@Override
	public boolean isAutorizzato(Long idEntita, String tipoEntita, String tipoPermesso)  {
		try{
			AutorizzazioneView autorizzazione = leggiAutorizzazioneUtenteCorrente(idEntita, tipoEntita);			
			boolean isAuthorized = autorizzazione.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(tipoPermesso) ||
				( tipoPermesso.contains(Costanti.TIPO_PERMESSO_LETTURA) && autorizzazione.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(Costanti.TIPO_PERMESSO_SCRITTURA) ||
					( tipoPermesso.contains(Costanti.TIPO_PERMESSO_GRANT) &&
							( autorizzazione.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(Costanti.TIPO_PERMESSO_SCRITTURA) 
									|| autorizzazione.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(Costanti.TIPO_PERMESSO_LETTURA))
					)
				);
			return isAuthorized;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<AutorizzazioneView> leggiAutorizzazioni(long idEntita, String nomeClasse) throws Throwable {
		List<Autorizzazione> lista = autorizzazioneDAO.leggiAutorizzazioni(idEntita, nomeClasse);
		List<AutorizzazioneView> listaRitorno = convertiVoInView(lista); 
		return listaRitorno;
	}
	
	@Override
	public List<AutorizzazioneView> leggiAutorizzazioni2(long idEntita, String nomeClasse) throws Throwable {
		List<Autorizzazione> lista = autorizzazioneDAO.leggiAutorizzazioni2(idEntita, nomeClasse);
		List<AutorizzazioneView> listaRitorno = convertiVoInView(lista); 
		return listaRitorno;
	}
	
	@Override
	public List<String> leggiAutorizzati(List<Long> idEntitas, String nomeClasse) throws Throwable {
		List<String> lista = autorizzazioneDAO.leggiAutorizzati(idEntitas, nomeClasse);
		return lista;
	}

	@Override
	protected Class<Autorizzazione> leggiClassVO() { 
		return Autorizzazione.class;
	}

	@Override
	protected Class<AutorizzazioneView> leggiClassView() { 
		return AutorizzazioneView.class;
	}
}
