<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<style> 
option:disabled {
    color: blue;
}
</style>
	
<!-- INIZIO - MODAL VOTAZIONI -->	
<div class="modal fade"  role="dialog" id="modalVotazioni" style="overflow-y: auto;">
  <div class="modal-dialog modal-lg" role="document" style="width: 60%;">
    <div class="modal-content">
      <div class="modal-header alert alert-warning">
        <b class="modal-title" style="font-size:150% !important;">Votazioni</b>
      </div>
      <div class="modal-body"  style="font-size: 90%;" id="{">
            
            <div id="votazioniFormSuccessDiv" class="alert alert-success">
				Operazione completata correttamente
			</div>
			
			<div id="votazioniFormErrorDiv" class="alert alert-danger" style="display:none">
				
				
				<div id="errorMsgVotazioniAutorevolezza">La valutazione Autorevolezza &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniCapacita">La valutazione Capacita &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniCompetenza">La valutazione Competenza &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniCosti">La valutazione Costi &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniFlessibilita">La valutazione Flessibilita &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniTempi">La valutazione Tempi &egrave; obbligatoria</div>
				<div id="errorMsgVotazioniReperibilita">La valutazione Reperibilita &egrave; obbligatoria</div>
					
				<div id="errorMsgVotazioniNota">La nota &egrave; obbligatoria</div>	
					
			</div>
			
           
           <div id="votazioniFormDiv"> 
        	<form:form name="votazioniForm" id="votazioniForm" method="post" modelAttribute="vendorManagementView" action="salva.action" class="form-horizontal la-form">
				<engsecurity:token regenerate="false"/>
				
				<input type="hidden" name="votazioniIdIncarico" id="votazioniIdIncarico" >
				<input type="hidden" name="votazioniIdIncarichi" id="votazioniIdIncarichi" >
				<input type="hidden" name="votazioniProfessionistaEsternoId" id="votazioniProfessionistaEsternoId" >
				<input type="hidden" name="votazioniValutatoreMatricola" id="votazioniValutatoreId" >
				
				<div class="container-fluid">
				
				<div class="row">
			    	<div class="col-sm-3" style="font-weight: normal">Studio Legale/Profressionista</div>
			      	<div class="col-sm-4" style="font-weight: normal">
			      		<input 
							name="votazioniStudioLegaleProfressionista" id="votazioniStudioLegaleProfressionista"
							class="form-control" style="text-align:center;" readonly>
			      	</div>
			      	<div class="col-sm-2" style="font-weight: normal">Data Valutazione</div>
			      	<div class="col-sm-3" style="font-weight: normal">
			      		<input 
							name="votazioniDataValutazione" id="votazioniDataValutazione"
							class="form-control" style="text-align:center;" readonly>
			      	</div>
		     	</div>
						
				<br>
				
				<div class="row">
			     	<div class="col-sm-3" style="font-weight: normal">Valutatore</div>
			      	<div class="col-sm-4" style="font-weight: normal">
			      		<input 
							name="votazioniValutatore" id="votazioniValutatore"
							class="form-control" style="text-align:center;" readonly>
			      	</div>
			      	<div class="col-sm-2" style="font-weight: normal">Semestre</div>
			      	<div class="col-sm-3" style="font-weight: normal">
			      		<input 
							name="semestreRiferimento" id="semestreRiferimento"
							class="form-control"  style="text-align:center;" readonly>
			      	</div>
			    </div>
				</div>
				
				<br>
				
				<div class="container-fluid">
					<div class="row">
				      <div class="col-sm-4" style="font-weight: normal">Nazione - Specializzazione</div>
				      <div class="col-sm-8" style="font-weight: normal" >
				
						<select size="2" id="votazioniNazioneSpec" 
						class="form-control" style="background-color: white;"></select>
						 
				      </div>
				
				</div>
				
				<br>
				
				<div class="container-fluid">
				
				  <div id="votazioniTitoloRow1" class="row" style="background-color:orange !important;">
				    <div class="col-sm-4" style="font-weight:bold">Asse</div>
				    <div class="col-sm-4" style="font-weight:bold">Peso</div>
				    <div class="col-sm-4" style="font-weight:bold">Punteggio</div>
				  </div>
				  
				  
				 
				</div> 
				  
				   
				   
				   
				  <br>
			
				<div class="container-fluid">
				
				  <div class="row" id="votazioniValComplessivaRow">
				      <div class="col-sm-4" style="font-weight: normal"></div>
				      <div class="col-sm-4" style="font-weight: normal; text-align: right;">Valutazione Complessiva</div>
				      <div class="col-sm-4" style="font-weight: normal;">
				      	<input  style="text-align: right;"
								name="valutazioneComplessiva" id="valutazioneComplessiva"
								class="form-control" readonly>
				      </div>
				     </div>
				  
				</div>
				
				<br>
				
				<div class="container-fluid">
				
				  <div class="row" id="valutazioneNotaRow">
				      <div class="col-sm-4" style="font-weight: normal; text-align:right">Nota</div>
				      <div class="col-sm-8" style="font-weight: normal">
				      	<textarea 
								name="valutazioneNota" id="valutazioneNota"
								class="form-control"
								rows="4" cols="50"
								style="font-size: 120%;">
						</textarea>
				      </div>
				     </div>
				  
				</div>
			
		 </form:form>
        
        	</div>
        	
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" style="background-color:orange !important;" id="btnVotazioniSalva">Salva</button>
        <button type="button" class="btn btn-primary" id="btnVotazioniChiudi">Chiudi</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- FINE - MODAL VOTAZIONI -->	



    