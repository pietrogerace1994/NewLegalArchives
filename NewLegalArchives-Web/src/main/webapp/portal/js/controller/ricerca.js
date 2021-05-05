function ricercaGoToCercaFascicolo(id) {
	console.log('ricercaGoToCercaFascicolo()');
	waitingDialog.show('Loading...');
	window.open(legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/cerca.action?id="+id), "_self");
}
function ricercaGoToCercaAtto(id) {
	console.log('ricercaGoToCercaAtto()');
}
function ricercaGoToCercaIncarico(id) {
	console.log('ricercaGoToCercaIncarico()');
}
function ricercaGoToCercaCollegioArbitrale(id) {
	console.log('ricercaGoToCercaCollegioArbitrale()');
}
function ricercaGoToCercaCosti(id) {
	console.log('ricercaGoToCercaCosti()');
}
function ricercaGoToCercaFile(id) {
	console.log('ricercaGoToCercaFile()');
}
$(document).ready(function () {
	
	for(var i=0; i<jsonArrayFascicolo.length; i++) {
		var obj = jsonArrayFascicolo[i];
		
		var id = obj.id;
		
		var str = "";
		str += '<ul class="actions" >';
		str += '    <li class="dropdown">';
		str += '		<a href="#" onclick="caricaAzioniSuFascicoloContenuto('+id+'); return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
		str += '  		<ul id="containerAzioniFascicolo'+id+'" class="dropdown-menu dropdown-menu-left" style="position: relative;">';
		str += '        </ul>'		
		str += '    </li> ';
		str += '</ul>';
	
		obj.azioni = str;
	}
	
	
	$.ajaxSetup({
    	async: false
	});
	for(var i=0; i<jsonArrayAtto.length; i++) {
		var obj = jsonArrayAtto[i];
		
		var id = obj.id;
		var str = "";
		str += '<p id="containerAzioniAtto'+id+'" style="position: relative;"></p>';
		
		getAzioniSuAttoContenuto(id);
		str += retAzioniAtto;
		
		obj.azioni = str;
	}
	$.ajaxSetup({
    	async: true
	});
	
	
	for(var i=0; i<jsonArrayIncarico.length; i++) {
		var obj = jsonArrayIncarico[i];
		
		var id = obj.id;
		
		var str = "";
		str += '<ul class="actions" >';
		str += '    <li class="dropdown">';
		str += '		<a href="#" onclick="caricaAzioniSuIncaricoContenuto('+id+'); return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
		str += '  		<ul id="containerAzioniIncarico'+id+'" class="dropdown-menu dropdown-menu-left" style="position: relative;">';
		str += '        </ul>'	
		str += '    </li> ';
		str += '</ul>';
	
		obj.azioni = str;
	}
	
	for(var i=0; i<jsonArrayCollegioArbitrale.length; i++) {
		var obj = jsonArrayCollegioArbitrale[i];
		
		var id = obj.id;
		
		var str = "";
		str += '<ul class="actions" >';
		str += '    <li class="dropdown">';
		str += '		<a href="#" onclick="caricaAzioniSuIncaricoArbitraleContenuto('+id+'); return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
		str += '  		<ul id="containerAzioniArbitrale'+id+'" class="dropdown-menu dropdown-menu-left" style="position: relative;">';
		str += '        </ul>'			
		str += '    </li> ';
		str += '</ul>';
	
		obj.azioni = str;
	}
	
	for(var i=0; i<jsonArrayCosti.length; i++) {
		var obj = jsonArrayCosti[i];
		
		var id = obj.id;
		
		var str = "";
		str += '<ul class="actions" >';
		str += '    <li class="dropdown">';
		str += '		<a href="#" onclick="caricaAzioniSuProformaContenuto('+id+'); return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
		str += '  		<ul id="containerAzioniProforma'+id+'" class="dropdown-menu dropdown-menu-left" style="position: relative;">';
		str += '        </ul>'		
		str += '    </li> ';
		str += '</ul>';
	
		obj.azioni = str;
	}

	for(var i=0; i<jsonArrayFile.length; i++) {
		var obj = jsonArrayFile[i];
		
		var id = obj.id;
		var isPenale = obj.isPenale;
		if (isPenale === undefined) {
			isPenale = 0;
	    }
		
		var a = "caricaAzioniSuFileContenuto('"+id+"',"+isPenale+")";
		
		var str = "";
		str += '<ul class="actions" >';
		str += '    <li class="dropdown">';
		str += '		<a href="#" onclick="'+a+'; return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
		str += '  		<ul id="containerAzioniFile'+id+'" class="dropdown-menu dropdown-menu-left" style="position: relative;">';
		str += '        </ul>'		
		str += '    </li> ';
		str += '</ul>';
	
		obj.azioni = str;
	}
	

	initTabellaRicercaRisultatiFascicolo();
	initTabellaRicercaRisultatiAtto();
	initTabellaRicercaRisultatiIncarico();
	initTabellaRicercaRisultatiCollegioArbitrale();
	initTabellaRicercaRisultatiCosti();
	initTabellaRicercaRisultatiFile();
});

function initTabellaRicercaRisultatiFascicolo(){
	console.log('Ricerca - Tabella Fascicolo');
	$('#tabellaRisultatiFascicolo').bootstrapTable({
		data: jsonArrayFascicolo,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'nome',
				title: 'nome',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'owner',
				title: 'owner',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'stato',
				title: 'stato',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'anno',
				title: 'anno',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}


function initTabellaRicercaRisultatiAtto(){
	console.log('Ricerca - Tabella Atto');
	$('#tabellaRisultatiAtto').bootstrapTable({
		data: jsonArrayAtto,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'numeroProtocollo',
				title: 'numeroProtocollo',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}

function initTabellaRicercaRisultatiIncarico(){
	console.log('Ricerca - Tabella Incarico');
	$('#tabellaRisultatiIncarico').bootstrapTable({
		data: jsonArrayIncarico,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'nome',
				title: 'nome',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}

function initTabellaRicercaRisultatiCollegioArbitrale(){
	console.log('Ricerca - Tabella CollegioArbitrale');
	$('#tabellaRisultatiCollegioArbitrale').bootstrapTable({
		data: jsonArrayCollegioArbitrale,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'nome',
				title: 'nome',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}

function initTabellaRicercaRisultatiCosti(){
	console.log('Ricerca - Tabella Costi');
	$('#tabellaRisultatiCosti').bootstrapTable({
		data: jsonArrayCosti,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'nome',
				title: 'nome',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}

function initTabellaRicercaRisultatiFile(){
	console.log('Ricerca - Tabella File');
	$('#tabellaRisultatiFile').bootstrapTable({
		data: jsonArrayFile,
        cache: false,
//        height: 600,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
			console.log("onClickRow");
           // document.location.href='apri-partCorrelata.html';
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'nome',
				title: 'nome',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'cartella',
				title: 'cartella',
				align: 'left',
				valign: 'top',
				sortable: true
			},
			{
				field: 'azioni',
				title: '',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			
		]
    });		 
}


// pulsante ...
function caricaAzioniSuFascicoloContenuto(id) {
	var containerAzioni = document.getElementById("containerAzioniFascicolo"+ id);
	
	containerAzioni.innerHTML="<li><img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'></li>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "fascicolo/caricaAzioniFascicolo.action?onlyContent=1&idFascicolo=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}	

function caricaAzioniSuIncaricoContenuto(id) {
	var containerAzioni = document.getElementById("containerAzioniIncarico" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniIncarico.action?onlyContent=1&idIncarico=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}	
function caricaAzioniSuProformaContenuto(id) {
	var containerAzioni = document.getElementById("containerAzioniProforma" + id);
	
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "proforma/caricaAzioniProforma.action?onlyContent=1&idProforma=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniSuIncaricoArbitraleContenuto(id) {
	var containerAzioni = document.getElementById("containerAzioniArbitrale" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniArbitrale.action?onlyContent=1&idIncarico=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniSuFileContenuto(id, isPenale) {
	var containerAzioni = document.getElementById("containerAzioniFile" + id);
	
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	containerAzioni.innerHTML="<li><a href='"+WEBAPP_BASE_URL+"download?onlyfn=1&isp="+isPenale+"&uuid="+id+"&CSRFToken="+legalSecurity.getToken()+"' class=\"edit\" target=\"_BLANK\">"+
	"<i class=\"fa fa-download\" aria-hidden=\"true\"></i> Download</a></li>";
}

var retAzioniAtto=null;
function getAzioniSuAttoContenuto(id) {
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		retAzioniAtto=data;
	};
	var url =WEBAPP_BASE_URL+"atto/caricaAzioniAtto.action?idAtto=" + id+"&statoCodice=S"; 
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function openAtto(id,azione) {
	window.open(legalSecurity.verifyToken(WEBAPP_BASE_URL+"atto/visualizza.action?id="+id+"&azione="+azione), "_self");
}