$( document ).ready(function() {
	
	$('#dataValidita').datetimepicker({ 
		
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	
	console.log("INIT listaRicercaRisultatiJson length="+ listaRicercaRisultatiJson.length );
	console.log("INIT listaRicercaRisultatiMatchJson length="+ listaRicercaRisultatiMatchJson.length );
	
	if(listaRicercaRisultatiJson.length == 0) {
		$("#msgSuggerimenti").css("display","none");
		if(!$("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").addClass("hidden");
	} 
	if(listaRicercaRisultatiJson.length > 0) {
		$("#msgSuggerimenti").css("display","block");
		if($("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").removeClass("hidden");
	} 
	
	if(listaRicercaRisultatiMatchJson.length == 0) {
		if(!$("#tabellaRicercaRisultatiMatch").hasClass("hidden"))
			$("#tabellaRicercaRisultatiMatch").addClass("hidden");
	}
	if(listaRicercaRisultatiMatchJson.length > 0) {
		if($("#tabellaRicercaRisultatiMatch").hasClass("hidden"))
			$("#tabellaRicercaRisultatiMatch").removeClass("hidden");
	}
	
	if(esitoRic == 'N' ) {
		$("#msgReportMatch").css("display","none");
		$("#msgReportSuggerimenti").css("display","block");
	}
	if(esitoRic == 'P' ) {
		$("#msgReportMatch").css("display","block");
		$("#msgReportSuggerimenti").css("display","none");
	}
	
	initTabellaRicercaRisultatiPartiCorrelate();
	
	initTabellaRicercaRisultatiMatchPartiCorrelate();
});

$(".btnCerca").click(function() {
	$("#resetWizard").val('Y');
});

function initTabellaRicercaRisultatiPartiCorrelate(){
	console.log('ricerca risultati Parti Correlate');
	
	$('#tabellaRicercaRisultati').bootstrapTable({
		data: listaRicercaRisultatiJson,
        cache: false,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row,ele) {
			console.log("onClickRow" + row+" "+ele);
			var denominazione=ele[0].firstChild.innerHTML;
			
			var n=denominazione.search("&amp;");
			if(n>0) {
				denominazione=denominazione.replace("&amp;", "&");
			}
			
			var codFiscale=ele[0].childNodes[1].innerHTML;
			var partitaIva=ele[0].childNodes[2].innerHTML;
			$("#denominazione").val(denominazione);
			$("#codFiscale").val(codFiscale);
			$("#partitaIva").val(partitaIva);
			waitingDialog.show('Loading...');
			$("#stepMsg").val('2');
			$("#parteCorrelataForm").submit();
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
				sortable: false
			},{
				field: 'codFiscale',
				title: 'codFiscale',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'partitaIva',
				title: 'partitaIva',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'nazione',
				title: 'nazione',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'tipoCorrelazione',
				title: 'tipoCorrelazione',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'rapporto',
				title: 'rapporto',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'familiare',
				title: 'familiare',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'consiglieriSindaci',
				title: 'consiglieri Sindaci',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'dataInserimento',
				title: 'data Inserimento',
				align: 'left',
				valign: 'top',
				sortable: false
			}
		]
    });		 
}



function initTabellaRicercaRisultatiMatchPartiCorrelate(){
	console.log('ricerca risultati Parti Correlate Match');
	
	$('#tabellaRicercaRisultatiMatch').bootstrapTable({
		data: listaRicercaRisultatiMatchJson,
        cache: false,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row,ele) {
			console.log("onClickRow match" + row+" "+ele);
			var denominazione=ele[0].firstChild.innerHTML;
			
			var n=denominazione.search("&amp;");
			if(n>0) {
				denominazione=denominazione.replace("&amp;", "&");
			}
			
			var codFiscale=ele[0].childNodes[1].innerHTML;
			var partitaIva=ele[0].childNodes[2].innerHTML;
			$("#denominazione").val(denominazione);
			$("#codFiscale").val(codFiscale);
			$("#partitaIva").val(partitaIva);
			waitingDialog.show('Loading...');
			$("#stepMsg").val('2');
			$("#parteCorrelataForm").submit();
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
				sortable: false
			},{
				field: 'codFiscale',
				title: 'codFiscale',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'partitaIva',
				title: 'partitaIva',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'nazione',
				title: 'nazione',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'tipoCorrelazione',
				title: 'tipoCorrelazione',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'rapporto',
				title: 'rapporto',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'familiare',
				title: 'familiare',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'consiglieriSindaci',
				title: 'consiglieri Sindaci',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'dataInserimento',
				title: 'data Inserimento',
				align: 'left',
				valign: 'top',
				sortable: false
			}
		]
    });		 
}




