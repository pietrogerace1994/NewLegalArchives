function selezionaMaterie() {
	if (materieDaSelezionare) {
		for (i = 0; i < materieDaSelezionare.length; i++) {
			document.getElementById(materieDaSelezionare[i]).checked = true;
		}
	}
}

function salvaBeautyContest() {
	console.log('salva beauty contest');
	var form = document.getElementById("beautyContestView");
	var op = document.getElementById("op");
	op.value = "salvaBeautyContest";
	waitingDialog.show('Loading...');
	
	form.submit();
}

function modificaBeautyContest(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"beautyContest/modifica.action?id="+id);
}

function dettaglioBeautyContest(id){
	  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"beautyContest/dettaglio.action?id="+id);
}

function aggiungiAllegato(id){
	var file = $('#fileAllegato')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idBeautyContest', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "beautyContest/uploadAllegato.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegati');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 

function rimuoviAllegato(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "beautyContest/rimuoviAllegato.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var params =  "";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegati');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
} 

function caricaAzioniBeautyContest(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuBeautyContest(id);
		}
	}
}

function cercaBeautyContest(){
	document.getElementById('btnApplicaFiltri').click();
}

function caricaAzioniSuBeautyContest(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaBc" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};
	var url = WEBAPP_BASE_URL
			+ "beautyContest/caricaAzioni.action?idBc=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function initTabellaRicercaBeautyContest() {

	var $table = $('#tabellaRicercaBeautyContest').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'beautyContest/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniBeautyContest,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) { 
					var titolo = encodeURIComponent( document.getElementById("txtTitolo").value) ;

					var dal = encodeURIComponent(document.getElementById("txtDataDal").value);
					
					var al = encodeURIComponent(document.getElementById("txtDataAl").value); 
					
					var statoCode = document.getElementById("statoBeautyContestCode").value
					
					var centroDiCosto = document.getElementById("txtCentroDiCosto").value
					
					params.titolo = titolo;
					params.dal = dal;
					params.al = al;
					params.statoBeautyContestCode = statoCode;
					params.centroDiCosto = centroDiCosto;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'titolo',
					title : 'TITOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'dataEmissione',
					title : 'DATA EMISSIONE',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'dataChiusura',
					title : 'DATA CHIUSURA',
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'unitaLegale',
					title : 'UNITA LEGALE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'cdc',
					title : 'CENTRO DI COSTO',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					sortable : true
				}  ]
			});
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function eliminaBc(id){
	console.log("elimino beauty contest con id: " + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		cercaBeautyContest();
	};
	var url = WEBAPP_BASE_URL + "beautyContest/elimina.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaGrigliaPermessiBeautyContest(idBc){
	console.log("caricaGrigliaPermessiBeautyContest con id: " + idBc);
	var tbody = document.getElementById("tBodyPermessiBc");
	tbody.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("tBodyPermessiBc");
		tbody.innerHTML = data;
		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "beautyContest/caricaGrigliaPermessiBeautyContest.action?id=" + idBc;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}

function estendiPermessiBeautyContest(idBc){
	console.log("estendiPermessiBeautyContest con id: " + idBc);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {   
		waitingDialog.hide();
		visualizzaMessaggio(data);
	};
	
	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var params = "";
	var checksLettura = document.getElementsByName("chkPermessiLettura");
	var checksScrittura = document.getElementsByName("chkPermessiScrittura");

	var paramsLettura ="";
	var paramsScrittura = "";
	for( i = 0; i < checksLettura.length; i++ ){
		if( checksLettura[i].checked ){
			paramsLettura += "permessoLettura="+checksLettura[i].value+"&";
		}
	}
	
	for( i = 0; i < checksScrittura.length; i++ ){
		if( checksScrittura[i].checked ){
			paramsScrittura += "permessoScrittura="+checksScrittura[i].value+"&";
		}
	}
	
	params = paramsLettura+paramsScrittura; 
	
	var url = WEBAPP_BASE_URL + "beautyContest/estendiPermessiBeautyContest.action?idBc=" + idBc;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function selezionaVincitore(idVincitore){
	
	if(idVincitore != null){
		
		var form = document.getElementById("beautyContestForm");
		
		var input1 = document.createElement("INPUT");
		input1.setAttribute("type", "hidden");
		input1.setAttribute("value", idVincitore);
		input1.setAttribute("name", "idVincitoreSelezionato");
		form.appendChild(input1);
		var op = document.getElementById("op");
		op.value = "selezionaVincitore";
		
		waitingDialog.show('Loading...');
		form.submit();
	}else{
		var form = document.getElementById("beautyContestForm");
		
		var input1 = document.createElement("INPUT");
		input1.setAttribute("type", "hidden");
		input1.setAttribute("value", null);
		input1.setAttribute("name", "idVincitoreSelezionato");
		form.appendChild(input1);
		var op = document.getElementById("op");
		op.value = "selezionaVincitore";
		
		waitingDialog.show('Loading...');
		form.submit();
	}
}

function aggiungiNotaAggiudicazioneFirmata(id){
	var file = $('#fileNotaAggiudicazioneFirmata')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idBc', id);
        data.append('CSRFToken', legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "beautyContest/uploadNotaAggiudicazioneFirmata.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxNotaAggiudicazioneFirmata');
		container.innerHTML=data;
	};
	
	var fnCallBackError = function(){
		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}

function rimuoviNotaAggiudicazioneFirmata(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "beautyContest/rimuoviNotaAggiudicazioneFirmata.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idBc="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxNotaAggiudicazioneFirmata');
		container.innerHTML=data;
	};
	
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}

//DARIO ***********************************************************
//function avviaWorkFlowBeautyContest(id){
function avviaWorkFlowBeautyContest(id, matricola_dest){
//*****************************************************************
	//DARIO ***********************************************************
	//console.log("avvio workflow bc con id: " + id);
	console.log("avviaWorkFlowBeautyContest(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaBeautyContest();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "beautyContest/avviaWorkFlowBc.action?id=" + id;
	var url = WEBAPP_BASE_URL + "beautyContest/avviaWorkFlowBc.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}
//DARIO ***********************************************************
//function avviaWorkFlowBeautyContestDaForm(id){
function avviaWorkFlowBeautyContestDaForm(id, matricola_dest){
//*****************************************************************
	//DARIO ************************************************************
	//console.log("avvio workflow bc con id: " + id);
	console.log("avviaWorkFlowBeautyContestDaForm(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		dettaglioBeautyContest(id, data);
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "beautyContest/avviaWorkFlowBc.action?id=" + id;
	var url = WEBAPP_BASE_URL + "beautyContest/avviaWorkFlowBc.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);	
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function riportaInBozzaWorkFlowBeautyContest(id){
	console.log("riporto in bozza workflow beauty contest con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaBeautyContest();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "beautyContest/riportaInBozzaWorkFlowBeautyContest.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null,  callBackFnErr);
}


function arretraWorkFlowBeautyContest(id){
	console.log("arretra workflow beauty contest con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaBeautyContest();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "beautyContest/arretraWorkFlowBeautyContest.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn,null,  callBackFnErr);
}

function caricaDettaglioBeautyContestReply(idBcr){
	console.log("caricaDettaglioBeautyContestReply con id: " + idBcr);
	var body = document.getElementById("containerBCR");
	body.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("containerBCR");
		tbody.innerHTML = data;
		if (document.getElementById("descrizioneOffertaTecnica")) {
			var instance = CKEDITOR.instances['descrizioneOffertaTecnica'];
            if (instance) {
                CKEDITOR.remove(instance);
            }
			CKEDITOR.replace('descrizioneOffertaTecnica');
		}
		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "beautyContestReply/dettaglioBeautyContestReply.action?id=" + idBcr;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}