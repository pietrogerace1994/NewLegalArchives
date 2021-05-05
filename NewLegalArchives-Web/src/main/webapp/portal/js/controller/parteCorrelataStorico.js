$( document ).ready(function() {
	
	$('#dataInizio').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	$('#dataFine').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	initTabellaStoricoRisultatiPartiCorrelate();
	
});
	
function initTabellaStoricoRisultatiPartiCorrelate(){
	console.log('storico risultati Parti Correlate');
	
	$('#tabellaStoricoRisultati').bootstrapTable({
		data: listaStoricoRisultatiJson,
        cache: false,
        processing: true,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'client',
        showRefresh: false,
        clickToSelect: true,
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'utenteRicerca',
				title: 'utente',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'esitoLungo',
				title: 'esito',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'dataRicerca',
				title: 'del',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'denominazione',
				title: 'denominazione',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'report',
				title: 'report',
				align: 'left',
				valign: 'top',
				sortable: false
			},
		]
    });		 
}

function scaricaReportStorico(id) 
{
	var url = urlServer+'/parteCorrelata/downloadStorico.action?id='+id;
	url=legalSecurity.verifyToken(url);
	window.open( url,'_blank'); 
}
