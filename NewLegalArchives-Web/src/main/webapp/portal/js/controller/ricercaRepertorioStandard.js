

function openRepertorioStandard(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openRepertorioStandard").submit();
}	


function caricaAzioniSuRepertorioStandard(id) {
	var containerAzioni = document.getElementById("action-repertoriostandard-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "repertorioStandard/caricaAzioniRepertorioStandard.action?idRepertorioStandard=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniRepertorioStandard(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuRepertorioStandard(id);
		}
	}
}


function getRepertorioStandardTableColumns(){
	return [ {
		field : 'nome',
		title : 'NOME',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'nota',
		title : 'NOTA',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'societa',
		title : 'SOCIETA',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'utente',
		title : 'UTENTE',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'posizioneOrganizzativa',
		title : 'POSIZIONE ORGANIZZATIVA',
		align : 'left',
		valign : 'top',
		sortable : false

	},  {
		field : 'primoLivelloAttribuzioni',
		title : 'PRIMO LIVELLO',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'secondoLivelloAttribuzioni',
		title : 'SECONDO LIVELLO',
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


function initTabellaRicercaRepertorioStandard() {
	var repertorioStandardTableColumns = getRepertorioStandardTableColumns();
	var $table = $('#data-table-repertoriostandard').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'repertorioStandard/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniRepertorioStandard,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : repertorioStandardTableColumns
	});
}


function applyRepertorioStandardSearchFilter() {
	var nome = encodeURIComponent(document.getElementById("nome").value || "");
	var nota = encodeURIComponent(document.getElementById("nota").value || "");
	var idSocieta = encodeURIComponent(document.getElementById("idSocieta").value || "0");
	var idPosizioneOrganizzativa = encodeURIComponent(document.getElementById("idPosizioneOrganizzativa").value || "0");
	var idPrimoLivelloAttribuzioni = encodeURIComponent(document.getElementById("idPrimoLivelloAttribuzioni").value || "0");
	var idSecondoLivelloAttribuzioni = encodeURIComponent(document.getElementById("idSecondoLivelloAttribuzioni").value || "0");
	
	$('#modalRicercaRepertorioStandard').modal("hide");
	
	var repertorioStandardTableColumns = getRepertorioStandardTableColumns();
	var filtraRicerca="nome="+nome+"&nota="+nota+"&idSocieta="+idSocieta+"&idPosizioneOrganizzativa="+idPosizioneOrganizzativa+"&idPrimoLivelloAttribuzioni="+idPrimoLivelloAttribuzioni+"&idSecondoLivelloAttribuzioni="+idSecondoLivelloAttribuzioni;
	
	$('#data-table-repertoriostandard').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'repertorioStandard/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniRepertorioStandard,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : repertorioStandardTableColumns
	});
}


