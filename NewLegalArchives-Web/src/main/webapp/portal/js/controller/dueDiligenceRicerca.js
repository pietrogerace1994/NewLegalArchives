$( document ).ready(function() {
	
	$('#txtDataAperturaDal').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	$('#txtDataAperturaAl').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	$('#txtDataChiusuraDal').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	$('#txtDataChiusuraAl').datetimepicker({ 
		locale: 'it',
		format: "dd/MM/yyyy",
	});
	
	console.log("INIT dueDiligenceSearchJson length="+ dueDiligenceSearchJson.length );
	
	if(!dueDiligenceSearchJson){
		$("#msgEmptyResult").css("display","none");
		$("#tabellaRicercaRisultati").addClass("hidden");
	}
	
	if(dueDiligenceSearchJson && dueDiligenceSearchJson.length == 0) {
		$("#msgEmptyResult").css("display","block");
		if(!$("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").addClass("hidden");
	}
	if(dueDiligenceSearchJson && dueDiligenceSearchJson.length > 0) {
		$("#msgEmptyResult").css("display","none");
		if($("#tabellaRicercaRisultati").hasClass("hidden"))
			$("#tabellaRicercaRisultati").removeClass("hidden");
	} 
	
	initTabellaRicercaRisultatiDueDiligence();
});

$(".btnCerca").click(function() {
	$("#resetWizard").val('Y');
});

$("#btnClear").click(function(event) {
	clearSearchFilter();
	event.stopPropagation(); 
});		
		
function clearSearchFilter() {
	document.getElementById("comboProfessionista").selectedIndex = 0;
	document.getElementById("comboStatoDueDiligence").selectedIndex = 0;
	
	$('form[name="dueDiligenceForm"] input[name=dataAperturaDal]').val('');
	$('form[name="dueDiligenceForm"] input[name=dataAperturaAl]').val('');
	$('form[name="dueDiligenceForm"] input[name=dataChiusuraDal]').val('');
	$('form[name="dueDiligenceForm"] input[name=dataChiusuraAl]').val('');
	
	$("#msgEmptyResult").css("display","none");
	$("#tabellaRicercaRisultati").addClass("hidden");
}

function initTabellaRicercaRisultatiDueDiligence(){
	console.log('ricerca risultati Parti Correlate');
	
	$('#tabellaRicercaRisultati').bootstrapTable({
		data: dueDiligenceSearchJson,
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
				title: 'VERIFICA ANTICORRUZIONE',
				align: 'left',
				valign: 'top',
				sortable: false
			}, {
				field: 'statoVerifica',
				title: 'STATO VERIFICA',
				align: 'left',
				valign: 'top',
				sortable: false
			}, {
				field: 'dataApertura',
				title: 'DATA APERTURA',
				align: 'left',
				valign: 'top',
				sortable: false
			}, {
				field: 'dataChiusura',
				title: 'DATA CHIUSURA',
				align: 'left',
				valign: 'top',
				sortable: false
			}, {
				field: 'documentoStep1',
				title: 'EMAIL ASSEGNAZIONE',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'documentoStep2',
				title: 'DOCUMENTI VERIFICA',
				align: 'left',
				valign: 'top',
				sortable: false
			},
			{
				field: 'documentoStep3',
				title: 'DOCUMENTO ESITO VERIFICA',
				align: 'left',
				valign: 'top',
				sortable: false
			}
		]
    });		 
}

