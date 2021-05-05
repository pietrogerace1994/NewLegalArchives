

function openRepertorioPoteri(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openRepertorioPoteri").submit();
}	


function caricaAzioniSuRepertorioPoteri(id) {
	var containerAzioni = document.getElementById("action-repertoriopoteri-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "repertorioPoteri/caricaAzioniRepertorioPoteri.action?idRepertorioPoteri=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniRepertorioPoteri(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuRepertorioPoteri(id);
		}
	}
}


function getRepertorioPoteriTableColumns(){
	return [ {
		field : 'codice',
		title : 'CODICE',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'descrizione',
		title : 'DESCRIZIONE',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'testo',
		title : 'TESTO',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'categoria',
		title : 'CATEGORIA',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'subcategoria',
		title : 'SUBCATEGORIA',
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


function initTabellaRicercaRepertorioPoteri() {
	var repertorioPoteriTableColumns = getRepertorioPoteriTableColumns();
	var $table = $('#data-table-repertoriopoteri').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'repertorioPoteri/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniRepertorioPoteri,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : repertorioPoteriTableColumns
	});
}


function applyRepertorioPoteriSearchFilter() {
	var codice = encodeURIComponent(document.getElementById("codice").value || "");
	var descrizione = encodeURIComponent(document.getElementById("descrizione").value || "");
	var testo = encodeURIComponent(document.getElementById("testo").value || "");
	var idCategoria = encodeURIComponent(document.getElementById("idCategoria").value || "0");
	var idSubcategoria = encodeURIComponent(document.getElementById("idSubcategoria").value || "0");
	
	$('#modalRicercaRepertorioPoteri').modal("hide");
	
	var repertorioPoteriTableColumns = getRepertorioPoteriTableColumns();
	var filtraRicerca="codice="+codice+"&descrizione="+descrizione+"&testo="+testo+"&idCategoria="+idCategoria+"&idSubcategoria="+idSubcategoria;
	
	$('#data-table-repertoriopoteri').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'repertorioPoteri/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniRepertorioPoteri,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : repertorioPoteriTableColumns
	});
}


