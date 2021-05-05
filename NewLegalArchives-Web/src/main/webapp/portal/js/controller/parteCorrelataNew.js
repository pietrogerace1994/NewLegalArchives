if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

var ricercaFatta=false;
var arrTabellaParteCorrelata=null;
$( document ).ready(function() {
	
	getDataTabellaParteCorrelata();
	
	initTabellaPartiCorrelate();
	
	$("#btnmodalAffinareRicerca").click(function() {
		ricercaFatta=true;
		
		  var denominazioneRic = $('#denominazioneRic').val();
		  var codFiscaleRic = $('#codFiscaleRic').val();
		  var partitaIvaRic = $('#partitaIvaRic').val();
		  var statoRic = $('#statoRic').val();
		  
		  var nazioneRicEle = document.getElementById("nazioneRic");
		  var nazioneRicCodice = nazioneRicEle.value;
		  var nazioneRicSelIndex = nazioneRicEle.selectedIndex;
		  var nazioneRic ="";
		  if(nazioneRicSelIndex>0) 
		      nazioneRic = nazioneRicEle[nazioneRicSelIndex].label;
		  
		  var tipoCorrelazioneRicEle = document.getElementById("comboTipoCorrelazioneRic");
		  var tipoCorrelazioneRicSelIndex = tipoCorrelazioneRicEle.selectedIndex;
		  var tipoCorrelazioneRicCodice = tipoCorrelazioneRicEle.value;
		  var tipoCorrelazioneRicLabel = "";
		  if(tipoCorrelazioneRicSelIndex>0)
		      tipoCorrelazioneRicLabel = tipoCorrelazioneRicEle[tipoCorrelazioneRicSelIndex].label;
		  
		  var rapportoRic = $('#rapportoRic').val();
		  var familiareRic = $('#familiareRic').val();
		  var consiglieriSindaciRic = $('#consiglieriSindaciRic').val();
		  var dataInserimentoRic = $('#dataInserimentoRic').val();
		  
		  
		  if( denominazioneRic == "" 
			  && codFiscaleRic==""
			  && partitaIvaRic==""
			  && statoRic == ""
			  && nazioneRicCodice  =="0"	  
			  && tipoCorrelazioneRicCodice=="0"
			  && rapportoRic==""
			  && familiareRic==""
			  && consiglieriSindaciRic==""
			  && dataInserimentoRic==""	) {
			  $('#tabellaPartiCorrelate').bootstrapTable('load', arrTabellaParteCorrelata);
			  return;
		  }
		  
		  var arrFinale1 = new Array();
		  var arrFinale2 = new Array();
		  var arrFinale3 = new Array();
		  var arrFinale4 = new Array();
		  var arrFinale5 = new Array();
		  var arrFinale6 = new Array();
		  var arrFinale7 = new Array();
		  var arrFinale8 = new Array();
		  var arrFinale9 = new Array();
		  var arrFinale10 = new Array();
		  
			
		  if( denominazioneRic != "") 
			  for(var i=0;i<arrTabellaParteCorrelata.length;i++) 
			  {
				  var trovato=false;
				  var obj = arrTabellaParteCorrelata[i];
				  if(obj.denominazione!=null && denominazioneRic != "") 
				  {
					  var n= obj.denominazione.toLowerCase().indexOf(denominazioneRic.toLowerCase());
					  if(n>=0) 
						  trovato=true;
				  }
				  			  
				  if(trovato)
					arrFinale1.push(obj);
			  }
		  else
			  arrFinale1=arrTabellaParteCorrelata;
		//end1
			  
		 if( codFiscaleRic!="")
		   for(var i=0; i<arrFinale1.length; i++) 
		   {
			  var obj = arrFinale1[i];
			  var trovato=false;
			  if(obj.codFiscale!=null && codFiscaleRic!="") 
			  {
				  var n= obj.codFiscale.toLowerCase().indexOf(codFiscaleRic.toLowerCase());
				  if(n>=0) 
					  trovato=true;
			  }
			  if(trovato)
				arrFinale2.push(obj);
			  
		   }
		 else
			 arrFinale2=arrFinale1;
		//end2	
		 
		 if( partitaIvaRic!="")
		   for(var i=0; i<arrFinale2.length; i++) 
		   {
			  var obj = arrFinale2[i];
			  var trovato=false;
			  if(obj.partitaIva!=null && partitaIvaRic!="") 
			  {
				  var n= obj.partitaIva.toLowerCase().indexOf(partitaIvaRic.toLowerCase());
				  if(n>=0) 
					  trovato=true;
			  }
			  if(trovato)
				arrFinale3.push(obj);
		   }	
		 else
			 arrFinale3=arrFinale2;
		//end3
		 
		 if( nazioneRic!="" ) 
		   for(var i=0;i<arrFinale3.length;i++) 
		   {
			  var obj = arrFinale3[i];
			  var trovato=false;
			  if(obj.nazione!=null && nazioneRic!="" && nazioneRicCodice!="0") 
			  {
				  var n= obj.nazione.toLowerCase().indexOf(nazioneRic.toLowerCase());
				  if(n>=0) 
					  trovato=true;
			  }
			   if(trovato)
				  arrFinale4.push(obj);
			  
		  }
		 else
			 arrFinale4= arrFinale3;
		//end4
		 
		 if( tipoCorrelazioneRicCodice!="0")  
		    for(var i=0;i<arrFinale4.length;i++) 
			{
				var obj = arrFinale4[i];
				var trovato=false;
				if(obj.tipoCorrelazione!=null  && tipoCorrelazioneRicCodice!="0") {
					  var n= obj.tipoCorrelazione.toLowerCase().indexOf(tipoCorrelazioneRicLabel.toLowerCase());
					  if(n>=0) 
						  trovato=true;
				}
				if(trovato)
					arrFinale5.push(obj);
			}
		 else
			 arrFinale5= arrFinale4; 
		//end5
		 
		 if( rapportoRic!="") 
		   for(var i=0;i<arrFinale5.length;i++) 
			{
				var obj = arrFinale5[i];
				var trovato=false;
				if(obj.rapporto!=null && rapportoRic!="") 
				{
					  var n= obj.rapporto.toLowerCase().indexOf(rapportoRic.toLowerCase());
					  if(n>=0) 
						  trovato=true;
				}
				if(trovato)
					arrFinale6.push(obj);			 
			}
		 else
			 arrFinale6= arrFinale5;
		//end6
		 
		 if( familiareRic!="") 
		   for(var i=0;i<arrFinale6.length;i++) 
			{
				var obj = arrFinale6[i];
				var trovato=false;
				if(obj.familiare !=null && familiareRic!="") 
				{
					  var n= obj.familiare.toLowerCase().indexOf(familiareRic.toLowerCase());
					  if(n>=0) 
						  trovato=true;
				}
				if(trovato)
					arrFinale7.push(obj);	
					  
			}
		 else
			 arrFinale7= arrFinale6;
		//end7
		 
		 if( consiglieriSindaciRic!="")
		    for(var i=0;i<arrFinale7.length;i++) 
			{
				var obj = arrFinale7[i];
				var trovato=false;
				if(obj.consiglieriSindaci !=null && consiglieriSindaciRic!="") 
				{
					  var n= obj.consiglieriSindaci.toLowerCase().indexOf(consiglieriSindaciRic.toLowerCase());
					  if(n>=0) 
						  trovato=true;
				}
				if(trovato)
					arrFinale8.push(obj);	
			}
		 else
			 arrFinale8= arrFinale7;
		 
		 if(dataInserimentoRic!="") 
		    for(var i=0;i<arrFinale8.length;i++) {
				var obj = arrFinale8[i];
				var trovato=false;
				if(obj.dataInserimento !=null && dataInserimentoRic!="") 
				{
					  var n= (obj.dataInserimento == dataInserimentoRic);
					  if(n) 
						  trovato=true;
				}
				if(trovato)
					arrFinale9.push(obj);	
			}//end8  
		 else
			 arrFinale9= arrFinale8;
		 
		 if(statoRic!="") 
			    for(var i=0;i<arrFinale9.length;i++) {
					var obj = arrFinale9[i];
					var trovato=false;
					if(obj.stato !=null && statoRic!="") 
					{
						  var n= (obj.stato == statoRic);
						  if(n) 
							  trovato=true;
					}
					if(trovato)
						arrFinale10.push(obj);	
				}//end8  
			 else
				 arrFinale10= arrFinale9;
		 
		  $('#tabellaPartiCorrelate').bootstrapTable('load', arrFinale10);
	});
	
	
	$('#dataInserimentoRic').datetimepicker({ 
			locale: 'it',
			format: "dd/MM/yyyy",
	});
	
	$("#filtroListaParteCorrelata").focusout(function() {
		
		var val=$("#filtroListaParteCorrelata").val() ;
		if(val=="") {
			var strOpt = "";
			for(var i=0; i< jsonArrayParteCorrelataMod.length; i++) {
				var obj = jsonArrayParteCorrelataMod[i];
				var denominazione=obj.denominazione;
				var id=obj.id;
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			}
			
			$("#parteCorrelataIdMod").html("");
			$("#parteCorrelataIdMod").html(strOpt);
			
		}
		
	});
	
	$("#filtroListaParteCorrelata").keyup(function(event) {
		
		var indici=new Array();
		var val=$("#filtroListaParteCorrelata").val() ;
		var str = '<option value="0">Loading ...</option>';
		$("#parteCorrelataIdMod").html("");
		$("#parteCorrelataIdMod").html(str);
		
		// cerco
		for(var i=0; i< jsonArrayParteCorrelataMod.length; i++) {
			var obj = jsonArrayParteCorrelataMod[i];
			
			var denominazione=obj.denominazione;
			
			denominazione=denominazione.toLowerCase();
			val=val.toLowerCase();
			
			
			var start = false;
			
			var n = denominazione.toLowerCase().indexOf(val.toLowerCase());
			if(n>=0) 
				start = true;
			
			if(start) {
				indici.push(i);
			}
		}
		
		if(indici.length==0) {
			var str = '<option value="0" style="color:red">Nessuna Parte Correlata</option>';
			$("#parteCorrelataIdMod").html("");
			$("#parteCorrelataIdMod").html(str);
		}
		else if(indici.length>0) {
			var strOpt = "";
			
			for(var i=0; i< indici.length; i++) {
				var x = indici[i];
				var obj=jsonArrayParteCorrelataMod[x];
				
				var denominazione=obj.denominazione;
				var id=obj.id;
				
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			}//endfor
			
			
			$("#parteCorrelataIdMod").html("");
			$("#parteCorrelataIdMod").html(strOpt);
			indici = [];
		}
	
	});
	
	
});

/*
 * Gestione della comparsa dei campi d'input, 
 * in funzione della scelta in combobox.  
 */
function selezionaTipoCorrelazione(codice){
	console.log('tipo correlazione selezionata: '+ codice);
	var familiare =  $('form[name="parteCorrelataForm"] input[name=familiare]');
	var consiglieriSindaci = $('form[name="parteCorrelataForm"] input[name=consiglieriSindaci]');
	var rapporto = $('form[name="parteCorrelataForm"] input[name=rapporto]');
	/*
	 * 20160716
	 * NB: in seguito ad una variazione di analisi da parte del cliente
	 * Vengono escluse le condizioni TCRL_1;TCRL_2;TCRL_5 (rimosse dalla tabella tipo_correlazioni)
	 * Ma, si rimane la logica per eventuali ripensamenti da parte del cliente.
	 */
	if ( codice=='TCRL_1' || codice=='TCRL_3' || codice=='TCRL_5' ) {
		$(".divConsiglieriSindaciInput").css('display','block');
		$(".divFamiliareInput").css('display','none'); 
		familiare.val('');
		$(".divRapportoInput").css('display','none');  
		rapporto.val('');
	} else if (codice=='TCRL_2') {
		$(".divConsiglieriSindaciInput").css('display','none');  
		consiglieriSindaci.val('');
		$(".divFamiliareInput").css('display','block'); 
	    $(".divRapportoInput").css('display','none');  
	    rapporto.val('');
	} else if (codice=='TCRL_4'){
		$(".divConsiglieriSindaciInput").css('display','none');
		consiglieriSindaci.val('');
		$(".divFamiliareInput").css('display','none'); 
		familiare.val('');
		$(".divRapportoInput").css('display','block');		
	} else {
		$(".divConsiglieriSindaciInput").css('display','none');
		consiglieriSindaci.val('');
		$(".divFamiliareInput").css('display','none');
		familiare.val('');
		$(".divRapportoInput").css('display','none'); 
		rapporto.val('');	
	}
}

function selTipoCorrelazioneMod(codice){
	console.log('selezionaTipoCorrelazioneMod: '+ codice);
	var consiglieriSindaci = $('form[name="parteCorrelataForm"] input[name=consiglieriSindaciMod]');
	var rapporto = $('form[name="parteCorrelataForm"] input[name=rapportoMod]');
	
	/*
	 * 20160716
	 * NB: in seguito ad una variazione di analisi da parte del cliente
	 * Vengono escluse le condizioni TCRL_1;TCRL_2;TCRL_5 (rimosse dalla tabella tipo_correlazioni)
	 * Ma, si rimane la logica per eventuali ripensamenti da parte del cliente.
	 */
	if ( codice=='TCRL_1' || codice=='TCRL_3' || codice=='TCRL_5' ) {
		$(".divConsiglieriSindaciMod").css('display','block');
		$(".divRapportoMod").css('display','none');  
	} else if (codice=='TCRL_2') {
		$(".divConsiglieriSindaciMod").css('display','none');  
	    $(".divRapportoMod").css('display','none');  
	} else if (codice=='TCRL_4'){
		$(".divConsiglieriSindaciMod").css('display','none');
		$(".divRapportoMod").css('display','block');		
	} else {
		$(".divConsiglieriSindaciMod").css('display','none');
		$(".divRapportoMod").css('display','none'); 
	}
}

/* funzione di salvataggio parte correlata */
function salvaParteCorrelata(){
	console.log('salva parteCorrelata');
	var form = document.getElementById("parteCorrelataView"); 
	var op = document.getElementById("op");
	op.value="salvaParteCorrelata";
	form.submit(); 
}

function cancellaParteCorrelata(){
	console.log('cancella parteCorrelata');
	var form = document.getElementById("parteCorrelataView"); 
	var op = document.getElementById("op");
	op.value="salvaParteCorrelata";
	setDeleteMode();
	form.submit(); 
}

function caricaAzioni(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			//caricaAzioniSuFascicolo(id);
		}

	}
}


function initTabellaPartiCorrelate(arr){
	console.log('ricerca Parti Correlate');
	
	$('#tabellaPartiCorrelate').bootstrapTable({
		data: arrTabellaParteCorrelata,
		uniqueId: 'id',
        cache: false,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 100, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'denominazione',
				title: 'denominazione',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'codFiscale',
				title: 'codFiscale',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'partitaIva',
				title: 'partitaIva',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'stato',
				title: 'stato',
				align: 'left',
				valign: 'top',
				sortable: true
			} ,{
				field: 'nazione',
				title: 'nazione',
				align: 'left',
				valign: 'top',
				sortable: true
			} ,{
				field: 'tipoCorrelazione',
				title: 'tipoCorrelazione',
				align: 'left',
				valign: 'top',
				sortable: true
			} ,{
				field: 'rapporto',
				title: 'rapporto',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'familiare',
				title: 'familiare',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'consiglieriSindaci',
				title: 'consiglieriSindaci',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'dataInserimento',
				title: 'dataInserimento',
				align: 'left',
				valign: 'top',
				sortable: true
			}
		]
    });		 
}

function caricaParteCorrelataMod(idParteCorrelata) {
	console.log('caricaParteCorrelataMod: '+idParteCorrelata);

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var id = data.getElementsByTagName("id");
		if (id["0"] != null) {
			id = id["0"].textContent;
		} 
		else {
			id = "";
		}
		
		var denominazione = data.getElementsByTagName("denominazione");
		if (denominazione["0"] != null) {
			denominazione = denominazione["0"].textContent;
		} 
		else {
			denominazione = "";
		}
		
		var codFiscale = data.getElementsByTagName("codFiscale");
		if (codFiscale["0"] != null) {
			codFiscale = codFiscale["0"].textContent;
		} 
		else {
			codFiscale = "";
		}
		
		var partitaIva = data.getElementsByTagName("partitaIva");
		if (partitaIva["0"] != null) {
			partitaIva = partitaIva["0"].textContent;
		} 
		else {
			partitaIva = "";
		}
		
		var nazioneId = data.getElementsByTagName("nazioneId");
		if (nazioneId["0"] != null) {
			nazioneId = nazioneId["0"].textContent;
		} 
		else {
			nazioneId = "";
		}
		
		var nazioneCodGruppoLingua = data.getElementsByTagName("nazioneCodGruppoLingua");
		if (nazioneCodGruppoLingua["0"] != null) {
			nazioneCodGruppoLingua = nazioneCodGruppoLingua["0"].textContent;
		} 
		else {
			nazioneCodGruppoLingua = "";
		}
		
		var tipoCorrelazioneId = data.getElementsByTagName("tipoCorrelazioneId");
		if (tipoCorrelazioneId["0"] != null) {
			tipoCorrelazioneId = tipoCorrelazioneId["0"].textContent;
		} 
		else {
			tipoCorrelazioneId = "";
		}
		
		var tipoCorrelazioneCodGruppoLingua = data.getElementsByTagName("tipoCorrelazioneCodGruppoLingua");
		if (tipoCorrelazioneCodGruppoLingua["0"] != null) {
			tipoCorrelazioneCodGruppoLingua = tipoCorrelazioneCodGruppoLingua["0"].textContent;
		} 
		else {
			tipoCorrelazioneCodGruppoLingua = "";
		}
		
		var consiglieriSindaci = data.getElementsByTagName("consiglieriSindaci");
		if (consiglieriSindaci["0"] != null) {
			consiglieriSindaci = consiglieriSindaci["0"].textContent;
		} 
		else {
			consiglieriSindaci = "";
		}
	
		var rapporto = data.getElementsByTagName("rapporto");
		if (rapporto["0"] != null) {
			rapporto = rapporto["0"].textContent;
		} 
		else {
			rapporto = "";
		}
		
		var familiare = data.getElementsByTagName("familiare");
		if (familiare["0"] != null) {
			familiare = familiare["0"].textContent;
		} 
		else {
			familiare = "";
		}
		
		$('form[name="parteCorrelataForm"] input[name=denominazioneMod]').val(denominazione); 
		$('form[name="parteCorrelataForm"] input[name=codFiscaleMod]').val(codFiscale);
		$('form[name="parteCorrelataForm"] input[name=partitaIvaMod]').val(partitaIva);
		$('form[name="parteCorrelataForm"] input[name=familiareMod]').val(familiare); 
		$('form[name="parteCorrelataForm"] input[name=rapportoMod]').val(rapporto); 
		$('form[name="parteCorrelataForm"] input[name=consiglieriSindaciMod]').val(consiglieriSindaci); 
		
		selezionaNazioneMod(nazioneCodGruppoLingua);
		selezionaTipoCorrelazioneMod(tipoCorrelazioneCodGruppoLingua);
		
		if(tipoCorrelazioneCodGruppoLingua == 'TCRL_3') {
			$(".divConsiglieriSindaciMod").css('display','block');
			$(".divRapportoMod").css('display','none');
		}
		if(tipoCorrelazioneCodGruppoLingua == 'TCRL_4') {
			$(".divRapportoMod").css('display','block');
			$(".divConsiglieriSindaciMod").css('display','none');
		}
	};
	var url = WEBAPP_BASE_URL + "parteCorrelata/caricaParteCorrelata.action?id="+idParteCorrelata;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function selezionaNazioneMod(valueStr) {
	var ele = document.getElementById("nazioneCodeMod");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == valueStr) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}
	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function selezionaTipoCorrelazioneMod(valueStr) {
	var ele = document.getElementById("comboTipoCorrelazioneMod");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == valueStr) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}
	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function puliscoAutocompletePcMod() {
	// pulisco autocomplete
	$("#filtroListaParteCorrelata").val('');
	var strOpt = "";
	for(var i=0; i< jsonArrayParteCorrelataMod.length; i++) {
		var obj = jsonArrayParteCorrelataMod[i];
		var denominazione=obj.denominazione;
		var id=obj.id;
		var str = '<option value="'+id+'">'+denominazione+'</option>';
		strOpt += str;
	}
	$("#parteCorrelataIdMod").html("");
	$("#parteCorrelataIdMod").html(strOpt);
}

function puliscoFiltroRicercaFormVis() {
	$('#denominazioneRic').val('');
	$('#codFiscaleRic').val('');
	$('#partitaIvaRic').val('');
	  
	  var nazioneRicEle = document.getElementById("nazioneRic");
	  nazioneRicEle.selectedIndex=-1;
	  
	  var tipoCorrelazioneRicEle = document.getElementById("comboTipoCorrelazioneRic");
	  tipoCorrelazioneRicEle.selectedIndex=-1;
	  
	  $('#rapportoRic').val('');
	  $('#familiareRic').val('');
	  $('#consiglieriSindaciRic').val('');
	  $('#dataInserimentoRic').val('');
} 

function editCheck(){
	console.log('editCheck');
	setEditMode();
	puliscoCampiInsert();
	
	$(".btnCancella").css("display","block");
	$(".btnSalva").css("display","block");
}

function puliscoCampiInsert() {
	// pulisco campi Input
	var ele = document.getElementById("comboTipoCorrelazione");
	ele.selectedIndex = 0;
	
	var ele = document.getElementById("nazioneCode");
	ele.selectedIndex = 0;
	
	$('form[name="parteCorrelataForm"] input[name=denominazione]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=codFiscale]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=partitaIva]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=familiare]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=rapporto]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=consiglieriSindaci]').val(''); 
	
	$(".divRapportoInput").css('display','none');
	$(".divConsiglieriSindaciInput").css('display','none');
}

function insertCheck(){
	console.log('insertCheck');
	setInsertMode();
	puliscoCampiEdit();
	
	$(".btnCancella").css("display","none");
	$(".btnSalva").css("display","block");
}

function puliscoCampiEdit() {
	// pulisco campi Edit
	var ele = document.getElementById("parteCorrelataIdMod");
	ele.selectedIndex = -1;
	
	var ele = document.getElementById("comboTipoCorrelazioneMod");
	ele.selectedIndex = 0;
	
	var ele = document.getElementById("nazioneCodeMod");
	ele.selectedIndex = 0;
	
	$('form[name="parteCorrelataForm"] input[name=denominazioneMod]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=codFiscaleMod]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=partitaIvaMod]').val('');
	$('form[name="parteCorrelataForm"] input[name=familiareMod]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=rapportoMod]').val(''); 
	$('form[name="parteCorrelataForm"] input[name=consiglieriSindaciMod]').val(''); 
	
	$(".divRapportoMod").css('display','none');
	$(".divConsiglieriSindaciMod").css('display','none');
}

function visCheck(){
	console.log('visCheck');
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
	
	puliscoCampiEdit();
	puliscoCampiInsert();
	
	$(".btnCancella").css("display","none");
	$(".btnSalva").css("display","none");
	
	if(ricercaFatta) {
		$('#tabellaPartiCorrelate').bootstrapTable('load', arrTabellaParteCorrelata);
		ricercaFatta=false;
	}
}

function setEditMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="true";
	
	
}

function setDeleteMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
}

function setInsertMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
}
 
function estraiSel(n) {
	if(n==1) {
		$("#estraiUno").addClass("active");
		$("#estraiDue").removeClass("active");
		$("#estraiTre").removeClass("active");
	}
	else if(n==2) {
		$("#estraiUno").removeClass("active");
		$("#estraiDue").addClass("active");
		$("#estraiTre").removeClass("active");
	}
	else if(n==3) {
		$("#estraiUno").removeClass("active");
		$("#estraiDue").removeClass("active");
		$("#estraiTre").addClass("active");
	}
	
}

function getDataTabellaParteCorrelata() {
	// get data
	var callBackFn = function(data, stato) {
		arrTabellaParteCorrelata = data.rows;
		$.ajaxSetup({
	    	async: true
		});
	};
    $.ajaxSetup({
    	async: false
	});
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "parteCorrelata/elencoData2.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, null, "get", "text/html", callBackFn, null);
}

function aggiungiPartiCorrelate(){
	var file = $('#filePartiCorrelate')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "parteCorrelata/uploadFile.action";
	 
	var fnCallBackSuccess = function(data){ 
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};
	var fnCallBackError = function(){
		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}
	