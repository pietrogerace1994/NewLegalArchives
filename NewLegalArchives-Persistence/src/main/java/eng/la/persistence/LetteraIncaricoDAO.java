package eng.la.persistence;

import java.util.List;

import eng.la.model.LetteraIncarico;

public interface LetteraIncaricoDAO {
	
	public List<LetteraIncarico> leggiLettereIncaricoPerId(List<Long> listaIdLettera) throws Throwable;

	public LetteraIncarico leggiLetteraIncarico(Long id) throws Throwable;
	
	/**
	 * Restituisce l'ultimo numero di protocollo generato nell'ultimo anno dall'utente richiesto
	 * I numeri di protocollo vengono considerati nella forma NNNN/AA/UU, dove:
	 * NNNN è un valore intero riportato con 4 cifre
	 * AA è l'anno corrente riportato con 2 cifre
	 * UU è la sigla dell'utente corrente
	 * @author MASSIMO CARUSO
	 * @param nominativo il nominativo dell'utente richiesto
	 * @return l'ultimo numero di protocollo inserito dall'utente, 0 altrimenti
	 */
	public int getUltimoNumeroProtocollo(String nominativo) throws Throwable;

}
