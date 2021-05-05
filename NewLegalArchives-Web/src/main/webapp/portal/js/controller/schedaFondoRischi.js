$(document).ready(function () {
	
	if (document.getElementById("coperturaAssicurativaFlag") != null) {
		if (document.getElementById("coperturaAssicurativaFlag").value == 1) {
			document.getElementById("coperturaAssicurativaValue").style.display="inline";
		}
	}
	
	if (document.getElementById("manlevaFlag") != null) {
		if (document.getElementById("manlevaFlag").value == 1) {
			document.getElementById("manlevaValue").style.display="inline";
		}
	}
	
	if (document.getElementById("commessaDiInvestimentoFlag") != null) {
		if (document.getElementById("commessaDiInvestimentoFlag").value == 1) {
			document.getElementById("commessaDiInvestimentoValue").style.display="inline";
		}
	}
	
	$('#coperturaAssicurativaFlag').on('change', function() {
		  var sel=this.value;
		  var coperturaAssicurativaDiv = document.getElementById("coperturaAssicurativaValue");
		  
		  if(sel != "1") {
			  coperturaAssicurativaDiv.value="";
			  coperturaAssicurativaDiv.style.display="none";
		  }
		  else {
			  coperturaAssicurativaDiv.style.display="inline";
		  }
	});
	
	$('#manlevaFlag').on('change', function() {
		  var sel=this.value;
		  var manlevaDiv = document.getElementById("manlevaValue");
		  
		  if(sel != "1") {
			  manlevaDiv.value="";
			  manlevaDiv.style.display="none";
		  }
		  else {
			  manlevaDiv.style.display="inline";
		  }
	});
	
	$('#commessaDiInvestimentoFlag').on('change', function() {
		  var sel=this.value;
		  var commessaDiInvestimentoDiv = document.getElementById("commessaDiInvestimentoValue");
		  
		  if(sel != "1") {
			  commessaDiInvestimentoDiv.value="";
			  commessaDiInvestimentoDiv.style.display="none";
		  }
		  else {
			  commessaDiInvestimentoDiv.style.display="inline";
		  }
	});
	
});

function salvaScheda() {
	console.log('salva scheda fondo rischi');
	var form = document.getElementById("schedaFondoRischiView");
	var op = document.getElementById("op");
	op.value = "salvaSchedaFondoRischi";
	waitingDialog.show('Loading...');
	
	form.submit();
}

function modificaSchedaFondoRischi(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"schedaFondoRischi/modifica.action?id="+id);
}

function aggiungiAllegatoProfessionista(id){
	var file = $('#fileAllegatoProfessionista')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idSchedaFondoRischi', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/uploadAllegatoProfessionista.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiProfessionista');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 

function rimuoviAllegatoProfessionista(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/rimuoviAllegatoProfessionista.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var params =  "";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiProfessionista');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
} 

function caricaAzioniSchedaFondoRischi(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuSchedaFondoRischi(id);
		}
	}
}

function cercaSchedaFondoRischi(){
	document.getElementById('btnApplicaFiltri').click();
}

function caricaAzioniSuSchedaFondoRischi(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaScheda" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};
	var url = WEBAPP_BASE_URL
			+ "schedaFondoRischi/caricaAzioniSchedaFr.action?idScheda=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function initTabellaRicercaSchedaFondoRischi() {

	var $table = $('#tabellaRicercaSchedaFondoRischi').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'schedaFondoRischi/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniSchedaFondoRischi,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) { 
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicolo").value) ;

					var dal = encodeURIComponent(document.getElementById("txtDataDal").value);
					
					var al = encodeURIComponent(document.getElementById("txtDataAl").value); 
					
					var statoCode = document.getElementById("statoSchedaFondoRischiCode").value
					
					var tipologiaCode = document.getElementById("tipologiaSchedaFondoRischiCode").value
					
					var rischioSoccombenzaCode = document.getElementById("rischioSoccombenzaSchedaFondoRischiCode").value
					
					params.nomeFascicolo = nomeFascicolo;
					params.dal = dal;
					params.al = al;
					params.statoSchedaFondoRischiCode = statoCode;
					params.tipologiaCode = tipologiaCode;
					params.rischioSoccombenzaCode = rischioSoccombenzaCode;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'tipologia',
					title : 'TIPOLOGIA',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO SCHEDA',
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'rischioSoccombenza',
					title : 'RISCHIO SOCCOMBENZA',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'dataCreazione',
					title : 'DATA CREAZIONE',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function eliminaScheda(id){
	console.log("elimino scheda fondo rischi con id: " + id);
	//waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		//waitingDialog.hide();
		cercaSchedaFondoRischi();
	};
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/eliminaSchedaFondoRischi.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}
//DARIO ***********************************************************
//function avviaWorkFlowSchedaFondoRischi(id){
function avviaWorkFlowSchedaFondoRischi(id,matricola_dest){	
//*****************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow scheda con id: " + id);
	console.log("avviaWorkFlowSchedaFondoRischi(" + id + "," + matricola_dest+ ")");
	//*****************************************************************	
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaSchedaFondoRischi();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "schedaFondoRischi/avviaWorkFlowScheda.action?id=" + id;
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/avviaWorkFlowScheda.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}
//DARIO ***********************************************************
//function avviaWorkFlowSchedaFRDaForm(id){
function avviaWorkFlowSchedaFRDaForm(id,matricola_dest){
//*****************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow scheda fondo rischi con id: " + id);
	console.log("avviaWorkFlowSchedaFRDaForm(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		$("#btnAvviaWorkFlowSchedaForm").hide();
		cercaSchedaFondoRischi();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "schedaFondoRischi/avviaWorkFlowScheda.action?id=" + id;
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/avviaWorkFlowScheda.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	url=legalSecurity.verifyToken(url);	
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function riportaInBozzaWorkFlowSchedaFondoRischi(id){
	console.log("riporto in bozza workflow scheda fondo rischi con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaSchedaFondoRischi();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/riportaInBozzaWorkFlowScheda.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null,  callBackFnErr);
}


function arretraWorkFlowSchedaFondoRischi(id){
	console.log("arretra workflow scheda fondo rischi con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaSchedaFondoRischi();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/arretraWorkFlowScheda.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn,null,  callBackFnErr);
}