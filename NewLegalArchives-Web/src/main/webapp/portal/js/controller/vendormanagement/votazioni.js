var g_media=0;
var listaVotazioni=[];
var votazioniDaContermare=[];

function votazioniControlloErrori() {
	// tutto off
	$("#errorMsgVotazioniAutorevolezza").hide();
	$("#errorMsgVotazioniCapacita").hide();
	$("#errorMsgVotazioniCompetenza").hide();
	$("#errorMsgVotazioniCosti").hide();
	$("#errorMsgVotazioniFlessibilita").hide();
	$("#errorMsgVotazioniTempi").hide();
	$("#errorMsgVotazioniNota").hide();
	$("#errorMsgVotazioniReperibilita").hide();
	
	// check
	var errors=false;
	var note=$("#valutazioneNota").val();  
	
	if(note=="") {
		errors=true;
		$("#errorMsgVotazioniNota").show();
	}
	
	var x1_ele = $("select[id$='AutorevolezzaVotazioniCombo']"); 
	var x2_ele = $("select[id$='CapacitaVotazioniCombo']"); 
	var x3_ele = $("select[id$='CompetenzaVotazioniCombo']"); 
	var x4_ele = $("select[id$='CostiVotazioniCombo']"); 
	var x5_ele = $("select[id$='FlessibilitaVotazioniCombo']"); 
	var x6_ele = $("select[id$='TempiVotazioniCombo']"); 
	var x7_ele = $("select[id$='ReperibilitaVotazioniCombo']"); 
	
	var x1_sel=x1_ele["0"].selectedIndex;
	var x1=parseInt(x1_ele["0"].value);
	
	var x2_sel=x2_ele["0"].selectedIndex;
	var x2=parseInt(x2_ele["0"].value);
	
	var x3_sel=x3_ele["0"].selectedIndex;
	var x3=parseInt(x3_ele["0"].value);
	
	var x4_sel=x4_ele["0"].selectedIndex;
	var x4=parseInt(x4_ele["0"].value);
	
	var x5_sel=x5_ele["0"].selectedIndex;
	var x5=parseInt(x5_ele["0"].value);
	
	var x6_sel=x6_ele["0"].selectedIndex;
	var x6=parseInt(x6_ele["0"].value);
	
	var x7_sel=x7_ele["0"].selectedIndex;
	var x7=parseInt(x7_ele["0"].value);
	
	if(x1==0) {
		errors=true;
		$("#errorMsgVotazioniAutorevolezza").show();
	}
	if(x2==0) {
		errors=true;
		$("#errorMsgVotazioniCapacita").show();
	}
	if(x3==0) {
		errors=true;
		$("#errorMsgVotazioniCompetenza").show();
	}
	if(x4==0) {
		errors=true;
		$("#errorMsgVotazioniCosti").show();
	}
	if(x5==0) {
		errors=true;
		$("#errorMsgVotazioniFlessibilita").show();
	}
	if(x6==0) {
		errors=true;
		$("#errorMsgVotazioniTempi").show();
	}
	if(x7==0) {
		errors=true;
		$("#errorMsgVotazioniReperibilita").show();
	}
	
	if(errors) {
		$("#votazioniFormErrorDiv").show();
		 $("#modalVotazioni").animate({ scrollTop: 0 }, "slow");
	}
	
	return errors;
}

$(document).ready(function () {
	
	$('#btnVotazioniSalva').on('click', function() {
		
		// controllo errori
		if(votazioniControlloErrori()) {
			return;
		}
		$('#modalVotazioni').modal('hide');
		waitingDialog.show('Loading...');
		
		var votazioniNazioneSpec_ele = $("select[id$='votazioniNazioneSpec']"); 
		var votazioniNazioneSpec_sel=votazioniNazioneSpec_ele["0"].selectedIndex;
		var valNazSpec = votazioniNazioneSpec_ele["0"][votazioniNazioneSpec_sel].value;
		
		var codNaz="";
		var codSpec="";
		var k=valNazSpec.indexOf(";");
		if(k>=0) {
			codNaz=valNazSpec.substring(k+1, valNazSpec.lenght);
			codSpec=valNazSpec.substring(0, k);
		}
		
		
		var profressionistaEsternoId=$("#votazioniProfessionistaEsternoId").val();		
		var dataValutazione = $("#votazioniDataValutazione").val();
		
		var x1_ele = $("select[id$='AutorevolezzaVotazioniCombo']"); 
		var x2_ele = $("select[id$='CapacitaVotazioniCombo']"); 
		var x3_ele = $("select[id$='CompetenzaVotazioniCombo']"); 
		var x4_ele = $("select[id$='CostiVotazioniCombo']"); 
		var x5_ele = $("select[id$='FlessibilitaVotazioniCombo']"); 
		var x6_ele = $("select[id$='TempiVotazioniCombo']"); 
		var x7_ele = $("select[id$='ReperibilitaVotazioniCombo']"); 
		
		var x1_sel=x1_ele["0"].selectedIndex;
		var x1=parseInt(x1_ele["0"].value);
		
		var x2_sel=x2_ele["0"].selectedIndex;
		var x2=parseInt(x2_ele["0"].value);
		
		var x3_sel=x3_ele["0"].selectedIndex;
		var x3=parseInt(x3_ele["0"].value);
		
		var x4_sel=x4_ele["0"].selectedIndex;
		var x4=parseInt(x4_ele["0"].value);
		
		var x5_sel=x5_ele["0"].selectedIndex;
		var x5=parseInt(x5_ele["0"].value);
		
		var x6_sel=x6_ele["0"].selectedIndex;
		var x6=parseInt(x6_ele["0"].value);
		
		var x7_sel=x7_ele["0"].selectedIndex;
		var x7=parseInt(x7_ele["0"].value);
		
		var idIncarico=$("#votazioniIdIncarico").val();
		var idsIncarichi=$("#votazioniIdIncarichi").val();
		var note=$("#valutazioneNota").val();  
		
		var params = { 
				profressionistaEsternoId: profressionistaEsternoId, 
				dataValutazione: dataValutazione,
				
				idIncarico: idIncarico,
				idsIncarichi: idsIncarichi,
				note: note,
				
				valutazioneAutorevolezza: x1,
				valutazioneCapacita: x2,
				valutazioneCompetenza: x3,
				valutazioneCosti: x4,
				valutazioneFlessibilita: x5,
				valutazioneTempi: x6,
				valutazioneReperibilita: x7,
					
				media: g_media,
				
				valSpecializzazione: codSpec,
				valNazione: codNaz,
				CSRFToken:legalSecurity.getToken()
		}; 
		
		var ajaxUtil = new AjaxUtil();
		var callBackFn = function(data, stato) {
			waitingDialog.hide();
			votazioni_showPage_Success();
			
			ricaricaTabella();
			listaVotazioni=[];
			votazioniDaContermare=[];
			$("#votaIncarichi")[0].disabled=true;
		};
		var callbackErrorFn = function() {
			waitingDialog.hide();
			votazioni_showPage_Error();
		};
		var url = WEBAPP_BASE_URL+"vendormanagement/salvaVotazione.action";
        url=legalSecurity.verifyToken(url);
		ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callbackErrorFn);
	});
	
	$('#btnVotazioniChiudi').on('click', function() {
		$('#modalVotazioni').modal('hide');
	});
});

function votazioni_showPage_Success() {
	$("#votazioniFormSuccessDiv").show();
	$("#votazioniFormErrorDiv").hide();
	$('#btnVotazioniSalva').hide();
}

function votazioni_showPage_Error() {
	$("#votazioniFormSuccessDiv").hide();
	$("#votazioniFormErrorDiv").show();
	$('#btnVotazioniSalva').hide();
}

function votazioni_showPage_All() {
	$("#votazioniFormDiv").show();
	$("#votazioniFormSuccessDiv").hide();
	//$("#votazioniFormErrorDiv").show();
	$('#btnVotazioniSalva').show();
}


function votazioni_caricaAzioniIncarico(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			votazioni_caricaAzioniSuIncarico(id);
		}

	}
}


function votazioni_caricaAzioniSuIncarico(idIncarico) {
	var containerAzioni = document.getElementById("containerAzioniRigaIncarico" + idIncarico);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;
		
		if(data.indexOf("Red") == -1 && data.indexOf("Orange") == -1){
			
			votazioni_caricaButtonRinviaVotazione(idIncarico);
		}
		
		if(data.indexOf("Orange") != -1){
			
			$("#confermaIncarichi")[0].disabled=false;
			votazioniDaContermare.push(idIncarico);
		}
	};
	var url = WEBAPP_BASE_URL
			+ "vendormanagement/caricaAzioniIncarico.action?idIncarico=" + idIncarico + "&CSRFToken="+legalSecurity.getToken();
	
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}

function votazioni_caricaButtonRinviaVotazione(idIncarico) {
	var containerButtonRinvioVotazione = document.getElementById("containerButtonRinvioVotazione" + idIncarico);
	var title = document.getElementById("containerButtonRinvioVotazione" + idIncarico).title;
	containerButtonRinvioVotazione.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerButtonRinvioVotazione.innerHTML = data;
	};
	var url = WEBAPP_BASE_URL
			+ "vendormanagement/caricaButtonRinvioVotazione.action?idIncarico=" + idIncarico + " &title=" + title 
			+ "&CSRFToken="+legalSecurity.getToken();
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function votazioni_initTabellaIncarichiAutorizzati() 
{
	for(var i=0; i<jsonArrayIncarichiAutorizzati.length; i++) {
		var obj = jsonArrayIncarichiAutorizzati[i];
		
		var id = obj.id;
		var assegnatario = obj.assegnatario;
		
		var s="<p id='containerAzioniRigaIncarico" + id + "' title='"+assegnatario+"' alt='"+assegnatario+"' >";
		s+="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
		s+="</p>";
		
		obj.azioni = s;
	}
	
	var $table = $('#tabellaVotazioniIncarico').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'vendormanagement/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : votazioni_caricaAzioniIncarico,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					return params;
				},
				columns : [ {
					field : 'check',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'nomeIncarico',
					title : 'NOME INCARICO',
					align : 'left',
					valign : 'top',
					sortable : true

				},
				{
					field : 'legaleInterno',
					title : 'LEGALE INTERNO',
					align : 'left',
					valign : 'top',
					sortable : true

				},
				{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				}, 
				{
					field : 'statoFascicolo',
					title : 'STATO FASCICOLO',
					align : 'center',
					valign : 'top',
					sortable : false
					
				}, 
				{
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},
				{
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'dataAutorizzazione',
					title : 'DATA INCARICO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'rinvioVotazione',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				}  ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function onchangeVotazioniCombo(ele) {
	var selIndice = ele.selectedIndex;
	var val=ele[selIndice].value;
	
	votazioni_calcolaMediaPonderata();
}

function votazioni_apriVotazioniModal(idIncarico) {
	ajaxSetup_SyncOrAsync("sync");
	
	//pulisco campi
	$("#valutazioneNota").val('');
	$("#valutazioneComplessiva").val('');
	
	$("#votazioniIdIncarico").val(idIncarico);
	$("#votazioniIdIncarichi").val('');
	
	var url_1 = WEBAPP_BASE_URL+"vendormanagement/getIncaricoById.action?idIncarico="+idIncarico
	+ "&CSRFToken="+legalSecurity.getToken();
	
	// load incarico
	var ajaxUtil_1 = new AjaxUtil();
	var callBackFn_1 = function(data, stato) {
		votazioni_showPage_All();
		
		var strOpts="";
		var n=data.listaNazioniRest[0];
		var s=data.listaSpecializzazioneRest[0];
		
		strOpts+="<option selected disabled value='"+s.codGruppoLingua+";"+n.codGruppoLingua+"'>"+n.descrizione+" - "+s.descrizione+"</option>";
			
		$("#votazioniNazioneSpec").html(strOpts);
		
		$("#votazioniStudioLegaleProfressionista").val(data.studioLegale  +"/"+ data.professionistaEsternoNomeCognome);		
		$("#votazioniValutatore").val(data.valutatore);
		
		$("#votazioniProfessionistaEsternoId").val(data.professionistaEsternoId);
		
		$("#semestreRiferimento").val(data.semestreRiferimento);
		
		var dataValutazione = moment().format("DD/MM/YYYY");
		$("#votazioniDataValutazione").val(dataValutazione);
		
		// nascondo errori
		$("#errorMsgVotazioniNota").hide();
		$("#errorMsgVotazioniAutorevolezza").hide();
		$("#errorMsgVotazioniCapacita").hide();
		$("#errorMsgVotazioniCompetenza").hide();
		$("#errorMsgVotazioniCosti").hide();
		$("#errorMsgVotazioniFlessibilita").hide();
		$("#errorMsgVotazioniTempi").hide();
		$("#errorMsgVotazioniReperibilita").hide();
		$("#votazioniFormErrorDiv").hide();
		
		$('#modalVotazioni').modal('show');
		
	};
	ajaxUtil_1.ajax(url_1, null, "post", "text/html", callBackFn_1, null, null);
	
	$(".votazioniRow").remove();
	
	// mostro parte di sotto
	$("#votazioniTitoloRow1").show();
	$("#votazioniValComplessivaRow").show();
	$("#valutazioneNotaRow").show();
	$("#btnVotazioniSalva").show();
	
	// --- pesi ---
//		$(".votazioniRow").remove();
		
	var descrizione=null;
	var descrizioneShort=null;
	var peso=null;
	var idAndName=null;
	
	// Tempi
	descrizione=g_arrAssiDescrizione["TEMPI"];
	descrizioneShort=g_arrAssiDescrizioneShort["TEMPI"];
	peso=g_arrAssiPercentuale["TEMPI"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
		
	$(str).insertAfter("#votazioniTitoloRow1");
			
	// Flessibilita
	descrizione=g_arrAssiDescrizione["FLESSIBILITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["FLESSIBILITA"];
	peso=g_arrAssiPercentuale["FLESSIBILITA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Costi
	descrizione=g_arrAssiDescrizione["COSTI"];
	descrizioneShort=g_arrAssiDescrizioneShort["COSTI"];
	peso=g_arrAssiPercentuale["COSTI"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
		
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Competenza
	descrizione=g_arrAssiDescrizione["COMPETENZA"];
	descrizioneShort=g_arrAssiDescrizioneShort["COMPETENZA"];
	peso=g_arrAssiPercentuale["COMPETENZA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");	
	
	// Capacita
	descrizione=g_arrAssiDescrizione["CAPACITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["CAPACITA"];
	peso=g_arrAssiPercentuale["CAPACITA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Autorevolezza
	descrizione=g_arrAssiDescrizione["AUTOREVOLEZZA"];
	descrizioneShort=g_arrAssiDescrizioneShort["AUTOREVOLEZZA"];
	peso=g_arrAssiPercentuale["AUTOREVOLEZZA"];
		
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Reperibilita
	descrizione=g_arrAssiDescrizione["REPERIBILITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["REPERIBILITA"];
	peso=g_arrAssiPercentuale["REPERIBILITA"];
		
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	ajaxSetup_SyncOrAsync("async");
}

function votazioni_apriVotazioniModalPerModifica(idIncarico) {
	ajaxSetup_SyncOrAsync("sync");
	
	//pulisco campi
	$("#valutazioneNota").val('');
	$("#valutazioneComplessiva").val('');
	
	$("#votazioniIdIncarico").val(idIncarico);
	$("#votazioniIdIncarichi").val('');
	
	var url_1 = WEBAPP_BASE_URL+"vendormanagement/getIncaricoById.action?idIncarico="+idIncarico
	+ "&CSRFToken="+legalSecurity.getToken();
	url_1=legalSecurity.verifyToken(url_1);
	
	// load incarico
	var ajaxUtil_1 = new AjaxUtil();
	var callBackFn_1 = function(data, stato) {
		votazioni_showPage_All();
		
		var strOpts="";
		var n=data.listaNazioniRest[0];
		var s=data.listaSpecializzazioneRest[0];
		
		strOpts+="<option selected disabled value='"+s.codGruppoLingua+";"+n.codGruppoLingua+"'>"+n.descrizione+" - "+s.descrizione+"</option>";
			
		$("#votazioniNazioneSpec").html(strOpts);
		
		$("#votazioniStudioLegaleProfressionista").val(data.studioLegale  +"/"+ data.professionistaEsternoNomeCognome);		
		$("#votazioniValutatore").val(data.valutatore);
		
		$("#votazioniProfessionistaEsternoId").val(data.professionistaEsternoId);
		
		$("#semestreRiferimento").val(data.semestreRiferimento);
		
		var dataValutazione = moment().format("DD/MM/YYYY");
		$("#votazioniDataValutazione").val(dataValutazione);
		
		// nascondo errori
		$("#errorMsgVotazioniNota").hide();
		$("#errorMsgVotazioniAutorevolezza").hide();
		$("#errorMsgVotazioniCapacita").hide();
		$("#errorMsgVotazioniCompetenza").hide();
		$("#errorMsgVotazioniCosti").hide();
		$("#errorMsgVotazioniFlessibilita").hide();
		$("#errorMsgVotazioniTempi").hide();
		$("#errorMsgVotazioniReperibilita").hide();
		$("#votazioniFormErrorDiv").hide();
		
		$('#modalVotazioni').modal('show');
		
		$(".votazioniRow").remove();
		
		// mostro parte di sotto
		$("#votazioniTitoloRow1").show();
		$("#votazioniValComplessivaRow").show();
		$("#valutazioneNotaRow").show();
		$("#btnVotazioniSalva").show();
		
		var descrizione=null;
		var descrizioneShort=null;
		var peso=null;
		var idAndName=null;
		
		// Tempi
		descrizione=g_arrAssiDescrizione["TEMPI"];
		descrizioneShort=g_arrAssiDescrizioneShort["TEMPI"];
		peso=g_arrAssiPercentuale["TEMPI"];
				
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
			
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
			
		$(str).insertAfter("#votazioniTitoloRow1");
				
		// Flessibilita
		descrizione=g_arrAssiDescrizione["FLESSIBILITA"];
		descrizioneShort=g_arrAssiDescrizioneShort["FLESSIBILITA"];
		peso=g_arrAssiPercentuale["FLESSIBILITA"];
				
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
			
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
		
		$(str).insertAfter("#votazioniTitoloRow1");
		
		// Costi
		descrizione=g_arrAssiDescrizione["COSTI"];
		descrizioneShort=g_arrAssiDescrizioneShort["COSTI"];
		peso=g_arrAssiPercentuale["COSTI"];
				
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
		
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
			
		$(str).insertAfter("#votazioniTitoloRow1");
		
		// Competenza
		descrizione=g_arrAssiDescrizione["COMPETENZA"];
		descrizioneShort=g_arrAssiDescrizioneShort["COMPETENZA"];
		peso=g_arrAssiPercentuale["COMPETENZA"];
				
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
			
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
		
		$(str).insertAfter("#votazioniTitoloRow1");	
		
		// Capacita
		descrizione=g_arrAssiDescrizione["CAPACITA"];
		descrizioneShort=g_arrAssiDescrizioneShort["CAPACITA"];
		peso=g_arrAssiPercentuale["CAPACITA"];
				
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
		
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
		
		$(str).insertAfter("#votazioniTitoloRow1");
		
		// Autorevolezza
		descrizione=g_arrAssiDescrizione["AUTOREVOLEZZA"];
		descrizioneShort=g_arrAssiDescrizioneShort["AUTOREVOLEZZA"];
		peso=g_arrAssiPercentuale["AUTOREVOLEZZA"];
			
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
		
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
		
		$(str).insertAfter("#votazioniTitoloRow1");
		
		// Reperibilita
		descrizione=g_arrAssiDescrizione["REPERIBILITA"];
		descrizioneShort=g_arrAssiDescrizioneShort["REPERIBILITA"];
		peso=g_arrAssiPercentuale["REPERIBILITA"];
			
		idAndName = descrizioneShort+"VotazioniCombo";
			
		sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
		sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
		sel+=' 	style="background-color: white;">';
		sel+=' 	  <option value="0"></option>';
		sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
		sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
		sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
		sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
		sel+=' </select>';
		
		str='';
		str +='<div class="row votazioniRow" >';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
		str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
		str +='</div>';
		
		$(str).insertAfter("#votazioniTitoloRow1");
		
		$("#valutazioneNota").val(data.nota);
		$("#valutazioneComplessiva").val(data.valutazioneComplessiva);
		
		$("#ReperibilitaVotazioniCombo").val(data.reperibilita);
		$("#AutorevolezzaVotazioniCombo").val(data.autorevolezza);
		$("#CapacitaVotazioniCombo").val(data.comprensione);
		$("#CompetenzaVotazioniCombo").val(data.professionalita);
		$("#CostiVotazioniCombo").val(data.costi);
		$("#FlessibilitaVotazioniCombo").val(data.flessibilita);
		$("#TempiVotazioniCombo").val(data.tempestivita);
	};
	ajaxUtil_1.ajax(url_1, null, "post", "text/html", callBackFn_1, null, null);
	
	
	
	ajaxSetup_SyncOrAsync("async");
}


function votazioni_calcolaMediaPonderata() {
	
	var arr=$("select[id$='VotazioniCombo']");
	var lun=arr.length;
	
	var p1 = g_arrAssiPercentuale["AUTOREVOLEZZA"]; 
	var p2 = g_arrAssiPercentuale["CAPACITA"];
	var p3 = g_arrAssiPercentuale["COMPETENZA"];
	var p4 = g_arrAssiPercentuale["COSTI"];
	var p5 = g_arrAssiPercentuale["FLESSIBILITA"];
	var p6 = g_arrAssiPercentuale["TEMPI"]; 
	var p7 = g_arrAssiPercentuale["REPERIBILITA"];
	
	var x1_ele = $("select[id$='AutorevolezzaVotazioniCombo']"); 
	var x2_ele = $("select[id$='CapacitaVotazioniCombo']"); 
	var x3_ele = $("select[id$='CompetenzaVotazioniCombo']"); 
	var x4_ele = $("select[id$='CostiVotazioniCombo']"); 
	var x5_ele = $("select[id$='FlessibilitaVotazioniCombo']"); 
	var x6_ele = $("select[id$='TempiVotazioniCombo']"); 
	var x7_ele = $("select[id$='ReperibilitaVotazioniCombo']"); 
	
	var x1_sel=x1_ele["0"].selectedIndex;
	var x1=parseInt(x1_ele["0"].value);
	
	var x2_sel=x2_ele["0"].selectedIndex;
	var x2=parseInt(x2_ele["0"].value);
	
	var x3_sel=x3_ele["0"].selectedIndex;
	var x3=parseInt(x3_ele["0"].value);
	
	var x4_sel=x4_ele["0"].selectedIndex;
	var x4=parseInt(x4_ele["0"].value);
	
	var x5_sel=x5_ele["0"].selectedIndex;
	var x5=parseInt(x5_ele["0"].value);
	
	var x6_sel=x6_ele["0"].selectedIndex;
	var x6=parseInt(x6_ele["0"].value);
	
	var x7_sel=x7_ele["0"].selectedIndex;
	var x7=parseInt(x7_ele["0"].value);
	
	p1= x1==0 ? 0 : p1/100;
	p2= x2==0 ? 0 : p2/100;
	p3= x3==0 ? 0 : p3/100;
	p4= x4==0 ? 0 : p4/100;
	p5= x5==0 ? 0 : p5/100;
	p6= x6==0 ? 0 : p6/100;
	p7= x7==0 ? 0 : p7/100;
	
	var m1=x1*p1;
	var m2=x2*p2;
	var m3=x3*p3;
	var m4=x4*p4;
	var m5=x5*p5;
	var m6=x6*p6;
	var m7=x7*p7;
	
	var m = m1 + m2 + m3 + m4 + m5 + m6 + m7;
	var p =p1 + p2 + p3 + p4 + p5 + p6 + p7;
	
	var result; 

	if(m == 0 && p == 0){
		result = 0;
	}else{
		result = m/p; 
	}
	
	g_media=result;
	
	var mStr = new String(result);
	
	var n = mStr.indexOf(".");
	if(n>0) {
		mStr = mStr.substring(0,n+2);
	}
	
	$("#valutazioneComplessiva").val(mStr);
	
	
}

function rinviaVotazione(idIncarico){
	waitingDialog.show('Loading...');
	$.post(WEBAPP_BASE_URL +  "vendormanagement/rinviaVotazione.action", { incaricoId: idIncarico,CSRFToken:legalSecurity.getToken() })
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
	
			waitingDialog.hide();
			ricaricaTabella();	 
		}
		else if(data.split(" ").join("")=="KO"){
		
			waitingDialog.hide();
			alert("Errore operazione non eseguita!")
		}		  
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
	            
	        });
	    }

function confermaVotazione(idIncarico){
	waitingDialog.show('Loading...');
	$.post(WEBAPP_BASE_URL +  "vendormanagement/confermaVotazione.action", { incaricoId: idIncarico,CSRFToken:legalSecurity.getToken() })
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 
			waitingDialog.hide();
			ricaricaTabella();	 
		}
		else if(data.split(" ").join("")=="KO"){
			
			waitingDialog.hide();
			alert("Errore operazione non eseguita!")
		}		  
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		    
		});
  }

function confermaVotazioni(){
	waitingDialog.show('Loading...');
	
	var idIncarichi = ""; 
	
	for(i=0;i<votazioniDaContermare.length;i++){
		
		idIncarichi += votazioniDaContermare[i] + ";"
	}
	
	$.post(WEBAPP_BASE_URL +  "vendormanagement/confermaVotazioni.action", {incarichiIds: idIncarichi, CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 
			waitingDialog.hide();
			ricaricaTabella();	 
		}
		else if(data.split(" ").join("")=="KO"){
			
			waitingDialog.hide();
			alert("Errore operazione non eseguita!")
		}
		else if(data.split(" ").join("")=="NO"){
			
			waitingDialog.hide();
			alert("Nessuna votazione da confermare!")
		}		
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		    
		});
  }


function addListaVotazioni(incarico){
  
	var selAll = $("#tabellaVotazioniIncarico  input[type='checkbox']:checked");
	var sel = [];
	
	for(i=0;i<selAll.length;i++){
		if(!selAll[i].disabled){
			sel.push(selAll[i]);
		}
	}

	if (sel.length === 1){
		listaVotazioni.push(incarico);
		$("#votaIncarichi")[0].disabled=false;
	} else 	if (sel.length === 0){
		listaVotazioni=[];
		$("#votaIncarichi")[0].disabled=true;
  }
	else{
		$("#votaIncarichi")[0].disabled=false;
		if (incarico.checked===true){
		
				var sameProfessionista=true;
				for (i = 1; i < sel.length; i++) {
					if (sel[i].getAttribute("data-professionista") != sel[i-1].getAttribute("data-professionista")){
						sameProfessionista = false;
					}
				}
		
			if (sameProfessionista){
				
				listaVotazioni.push(incarico);
			}else{
				incarico.checked=false;
			}
		}else{
			listaVotazioni.pop(incarico);
		}
	}
}

function votazioniMultiple() {
	
	if(listaVotazioni.length == 1){
		
		var row = listaVotazioni[0];
		var idIncarico = row.getAttribute("data-incarico");
		votazioni_apriVotazioniModal(idIncarico);
	}
	else{
		votazioni_apriVotazioniModalMultiIncarico();
	}
}

function votazioni_apriVotazioniModalMultiIncarico() {
	//pulisco campi
	$("#valutazioneNota").val('');
	$("#valutazioneComplessiva").val('');
	
	var idsIncarichi = "";
	var idIncarico = listaVotazioni[0].getAttribute("data-incarico");
	
	for (i = 0; i < listaVotazioni.length; i++) {
		
		idsIncarichi += listaVotazioni[i].getAttribute("data-incarico") + ",";
	}
	
	$("#votazioniIdIncarico").val(idIncarico);
	$("#votazioniIdIncarichi").val(idsIncarichi);
	
	var url_1 = WEBAPP_BASE_URL+"vendormanagement/getIncaricoById.action?idIncarico="+idIncarico + "&CSRFToken="+legalSecurity.getToken();
	
	
	// load incarico
	var ajaxUtil_1 = new AjaxUtil();
	var callBackFn_1 = function(data, stato) {
		votazioni_showPage_All();
		
		var strOpts="";
		var n=data.listaNazioniRest[0];
		var s=data.listaSpecializzazioneRest[0];
		
		strOpts+="<option selected disabled value='"+s.codGruppoLingua+";"+n.codGruppoLingua+"'>"+n.descrizione+" - "+s.descrizione+"</option>";
			
		$("#votazioniNazioneSpec").html(strOpts);
		
		$("#votazioniStudioLegaleProfressionista").val(data.studioLegale  +"/"+ data.professionistaEsternoNomeCognome);		
		$("#votazioniValutatore").val(data.valutatore);
		
		$("#votazioniProfessionistaEsternoId").val(data.professionistaEsternoId);
		
		$("#semestreRiferimento").val(data.semestreRiferimento);
		
		var dataValutazione = moment().format("DD/MM/YYYY");
		$("#votazioniDataValutazione").val(dataValutazione);
		
		// nascondo errori
		$("#errorMsgVotazioniNota").hide();
		$("#errorMsgVotazioniAutorevolezza").hide();
		$("#errorMsgVotazioniCapacita").hide();
		$("#errorMsgVotazioniCompetenza").hide();
		$("#errorMsgVotazioniCosti").hide();
		$("#errorMsgVotazioniFlessibilita").hide();
		$("#errorMsgVotazioniTempi").hide();
		$("#errorMsgVotazioniReperibilita").hide();
		$("#votazioniFormErrorDiv").hide();
		
		$('#modalVotazioni').modal('show');
		
	};
	ajaxUtil_1.ajax(url_1, null, "post", "text/html", callBackFn_1, null, null);
	
	$(".votazioniRow").remove();
	
	// mostro parte di sotto
	$("#votazioniTitoloRow1").show();
	$("#votazioniValComplessivaRow").show();
	$("#valutazioneNotaRow").show();
	$("#btnVotazioniSalva").show();
	
	var descrizione=null;
	var descrizioneShort=null;
	var peso=null;
	var idAndName=null;
	
	// Tempi
	descrizione=g_arrAssiDescrizione["TEMPI"];
	descrizioneShort=g_arrAssiDescrizioneShort["TEMPI"];
	peso=g_arrAssiPercentuale["TEMPI"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
		
	$(str).insertAfter("#votazioniTitoloRow1");
			
	// Flessibilita
	descrizione=g_arrAssiDescrizione["FLESSIBILITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["FLESSIBILITA"];
	peso=g_arrAssiPercentuale["FLESSIBILITA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Costi
	descrizione=g_arrAssiDescrizione["COSTI"];
	descrizioneShort=g_arrAssiDescrizioneShort["COSTI"];
	peso=g_arrAssiPercentuale["COSTI"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
		
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Competenza
	descrizione=g_arrAssiDescrizione["COMPETENZA"];
	descrizioneShort=g_arrAssiDescrizioneShort["COMPETENZA"];
	peso=g_arrAssiPercentuale["COMPETENZA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
		
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");	
	
	// Capacita
	descrizione=g_arrAssiDescrizione["CAPACITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["CAPACITA"];
	peso=g_arrAssiPercentuale["CAPACITA"];
			
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Autorevolezza
	descrizione=g_arrAssiDescrizione["AUTOREVOLEZZA"];
	descrizioneShort=g_arrAssiDescrizioneShort["AUTOREVOLEZZA"];
	peso=g_arrAssiPercentuale["AUTOREVOLEZZA"];
		
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	// Reperibilita
	descrizione=g_arrAssiDescrizione["REPERIBILITA"];
	descrizioneShort=g_arrAssiDescrizioneShort["REPERIBILITA"];
	peso=g_arrAssiPercentuale["REPERIBILITA"];
		
	idAndName = descrizioneShort+"VotazioniCombo";
		
	sel='<select size="1" name="'+idAndName+'" id="'+idAndName+'" ';
	sel+='  class="form-control" onchange="onchangeVotazioniCombo(this)" ';
	sel+=' 	style="background-color: white;">';
	sel+=' 	  <option value="0"></option>';
	sel+='     <option value="1">1&nbsp;&nbsp;&nbsp;(Insufficiente)</option>';
	sel+=' 	  <option value="2">2&nbsp;&nbsp;&nbsp;(Sufficiente)</option>';
	sel+='    <option value="3">3&nbsp;&nbsp;&nbsp;(Buono)</option>';
	sel+=' 	  <option value="4">4&nbsp;&nbsp;&nbsp;(Eccellente)</option>';
	sel+=' </select>';
	
	str='';
	str +='<div class="row votazioniRow" >';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+descrizione+'</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+peso+'&nbsp;&#37;&nbsp;</div>';
	str +='  <div class="col-sm-4" style="font-weight: normal">'+sel+'</div>';
	str +='</div>';
	
	$(str).insertAfter("#votazioniTitoloRow1");
	
	ajaxSetup_SyncOrAsync("async");
}

function ricaricaTabella(){
	$("#confermaIncarichi")[0].disabled=true;
	$('#tabellaVotazioniIncarico').bootstrapTable('refresh');
	listaVotazioni=[];
	votazioniDaContermare=[];
}

