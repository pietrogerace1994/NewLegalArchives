$( document ).ready(function() {
	
	$('#txtDataInserimento').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	
	console.log("INIT autoritaGiudiziariaSearchJson length="+ autoritaGiudiziariaSearchJson.length );
	
	if(!autoritaGiudiziariaSearchJson){
		$("#msgEmptyResult").css("display","none");
		$("#tabellaRicercaRisultati").addClass("hidden");
	}
	
	if(autoritaGiudiziariaSearchJson && autoritaGiudiziariaSearchJson.length == 0) {
		$("#msgEmptyResult").css("display","block");
		if(!$("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").addClass("hidden");
	}
	if(autoritaGiudiziariaSearchJson && autoritaGiudiziariaSearchJson.length > 0) {
		$("#msgEmptyResult").css("display","none");
		if($("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").removeClass("hidden");
	} 
	
	initTabellaRicercaRisultatiPartiCorrelate();
});

$(".btnCerca").click(function() {
	$("#resetWizard").val('Y');
});

$("#btnClear").click(function(event) {
	clearSearchFilter();
	event.stopPropagation(); 
});		
		
function clearSearchFilter() {
	document.getElementById("comboTipoRichiesta").selectedIndex = 0;
	document.getElementById("comboStatoRichiesta").selectedIndex = 0;
	document.getElementById("comboSocieta").selectedIndex = 0;
	
	$('form[name="autoritaGiudiziariaForm"] input[name=autoritaGiudiziaria]').val('');
	$('form[name="autoritaGiudiziariaForm"] input[name=dataInserimento]').val('');
	$('form[name="autoritaGiudiziariaForm"] input[name=oggetto]').val('');
	$('form[name="autoritaGiudiziariaForm"] input[name=annoRichiesta]').val('');
	$('form[name="autoritaGiudiziariaForm"] input[name=fornitore]').val('');
	
	$("#msgEmptyResult").css("display","none");
	$("#tabellaRicercaRisultati").addClass("hidden");
}

function initTabellaRicercaRisultatiPartiCorrelate(){
	console.log('ricerca risultati Parti Correlate');
	
	$('#tabellaRicercaRisultati').bootstrapTable({
		data: autoritaGiudiziariaSearchJson,
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
        },
        queryParams : function(params) {
        	console.log("queryParams");
			return params;
		},
        columns: [
			{
				field: 'denominazione',
				title: 'AUTORITA GIUDIZIARIA',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'fornitore',
				title: 'FORNITORE',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'oggetto',
				title: 'OGGETTO',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'tipologiaRichiesta',
				title: 'TIPOLOGIA RICHIESTA',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'statoRichiesta',
				title: 'STATO RICHIESTA',
				align: 'left',
				valign: 'top',
				sortable: false
			} ,{
				field: 'societa',
				title: 'SOCIETA',
				align: 'left',
				valign: 'top',
				sortable: false
			}
			,{
				field: 'dataInserimento',
				title: 'DATA INSERIMENTO',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'documentoStep1',
				title: 'DOCUMENTO RICHIESTA',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'documentoStep2',
				title: 'DOCUMENTO INOLTRO',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'documentoStep3',
				title: 'DOCUMENTO TRASMISSIONE',
				align: 'left',
				valign: 'top',
				sortable: false
			}
		]
    });		 
}

