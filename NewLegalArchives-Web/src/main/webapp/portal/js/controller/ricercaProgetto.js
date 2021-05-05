

function openProgetto(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openProgetto").submit();
}	


function caricaAzioniSuProgetto(id,nome) {
	var containerAzioni = document.getElementById("action-progetto-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "progetto/caricaAzioniProgetto.action?idProgetto=" + id+"&nomeProgetto="+nome;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniProgetto(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			var nome = row.nome;
			caricaAzioniSuProgetto(id,nome);
		}
	}
}

function getProjectTableColumns(){
	return [ {
		field : 'nome',
		title : 'PROGETTO',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'descrizione',
		title : 'DESCRIZIONE',
		align : 'center',
		valign : 'top',
		sortable : false

	}, {
		field : 'oggetto',
		title : 'OGGETTO',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'stato',
		title : 'STATO',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'dataCreazione',
		title : 'DATA CREAZIONE',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'dataChiusura',
		title : 'DATA CHIUSURA',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'azioni',
		title : '',
		align : 'left',
		valign : 'top',
		sortable : false
	} ];
}

function getProjectTableColumnsAssocia(){
	return [ {
		field : 'nome',
		title : 'PROGETTO',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'descrizione',
		title : 'DESCRIZIONE',
		align : 'center',
		valign : 'top',
		sortable : false

	}, {
		field : 'oggetto',
		title : 'OGGETTO',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'stato',
		title : 'STATO',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'dataCreazione',
		title : 'DATA CREAZIONE',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'dataChiusura',
		title : 'DATA CHIUSURA',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'radio',
		title : 'SELEZIONA',
		align : 'left',
		valign : 'top',
		sortable : false
	} ];
}

function initTabellaRicercaProgetto() {
	var projectTableColumns = getProjectTableColumns();
	var $table = $('#data-table-progetto').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'progetto/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniProgetto,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : projectTableColumns
	});
}

function initTabellaRicercaProgettoAssocia(idFascicolo) {
	var projectTableColumns = getProjectTableColumnsAssocia();
	var $table = $('#data-table-progetto').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'progetto/cerca.action?idFascicolo='+idFascicolo,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : projectTableColumns
	});
}

function applyProjectSearchFilter(idFascicolo) {
	var dataAperDal = encodeURIComponent(document.getElementById("dataAperturaDal").value || "0");
	var dataAperAl = encodeURIComponent(document.getElementById("dataAperturaAl").value || "0");
	var dataChiuDal = encodeURIComponent(document.getElementById("dataChiusuraDal").value || "0");
	var dataChiuAl = encodeURIComponent(document.getElementById("dataChiusuraAl").value || "0");
	var oggetto = encodeURIComponent(document.getElementById("oggetto").value || "0");
	var nome = encodeURIComponent(document.getElementById("nome").value || "0");
	var descrizione = encodeURIComponent(document.getElementById("descrizione").value || "0");
	
	$('#modalRicercaProgetto').modal("hide");
	
	var projectTableColumns = getProjectTableColumns();
	var filtraRicerca="dataCreateDal="+dataAperDal+"&dataCreateAl="+dataAperAl+"&dataCloseDal="+dataChiuDal+"&dataCloseAl="+dataChiuAl+"&oggetto="+oggetto+"&nome="+nome+"&descr="+descrizione+"&idFascicolo="+idFascicolo;
	
	$('#data-table-progetto').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'progetto/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniProgetto,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : projectTableColumns
	});
}


