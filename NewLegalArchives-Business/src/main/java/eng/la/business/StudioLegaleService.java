package eng.la.business;

import java.util.List;

import eng.la.model.view.StudioLegaleView;
import eng.la.util.ListaPaginata;

public interface StudioLegaleService {
 
	public List<StudioLegaleView> leggi() throws Throwable;
	
	public StudioLegaleView leggi(long id) throws Throwable;
	
	public List<StudioLegaleView> cerca( String denominazione, long nazioneId, String codiceSap) throws Throwable;
	
	public ListaPaginata<StudioLegaleView> cerca( String denominazione, long nazioneId, String codiceSap, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	
	public StudioLegaleView inserisci( StudioLegaleView studioLegaleView ) throws Throwable;
	
	public void modifica( StudioLegaleView studioLegaleView ) throws Throwable;
	
	public void cancella( StudioLegaleView studioLegaleView ) throws Throwable;
	
	public Long conta( String denominazione, long nazioneId, String codiceSap) throws Throwable;
		
}
