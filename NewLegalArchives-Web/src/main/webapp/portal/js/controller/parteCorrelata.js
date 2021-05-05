/*
function selezionaTipoCorrelazione(codice){
	console.log('tipo correlazione selezionata: '+ codice);
	var form = document.getElementById("parteCorrelataView"); 
	var op = document.getElementById("op");
	op.value="selezionaTipoCorrelazione";
	form.submit(); 
}
*/

/*
 * Gestione della comparsa dei campi d'input, 
 * in funzione della scelta in combobox.  
 */
function selezionaTipoCorrelazione(codice){
	console.log('tipo correlazione selezionata: '+ codice);
	//alert("valore : " + codice);
	// div attribute
	var divFamiliareInput = document.getElementById("divFamiliareInput");
	var divConsiglieriSindaciInput = document.getElementById("divConsiglieriSindaciInput");
	var divRapportoInput = document.getElementById("divRapportoInput");
	// input attribute
	var familiare = document.getElementById("familiare");
	var consiglieriSindaci = document.getElementById("consiglieriSindaci");
	var rapporto = document.getElementById("rapporto");
	
	/*
	 * 20160716
	 * NB: in seguito ad una variazione di analisi da parte del cliente
	 * Vengono escluse le condizioni TCRL_1;TCRL_2;TCRL_5 (rimosse dalla tabella tipo_correlazioni)
	 * Ma, si rimane la logica per eventuali ripensamenti da parte del cliente.
	 */
	if ( codice=='TCRL_1' || codice=='TCRL_3' || codice=='TCRL_5' ) {
		divConsiglieriSindaciInput.style.display='block';
		divFamiliareInput.style.display='none'; familiare.value='';
		divRapportoInput.style.display='none'; rapporto.value='';
	} else if (codice=='TCRL_2') {
		divConsiglieriSindaciInput.style.display='none'; consiglieriSindaci.value='';
		divFamiliareInput.style.display='block';
		divRapportoInput.style.display='none'; rapporto.value='';
	} else if (codice=='TCRL_4'){
		divConsiglieriSindaciInput.style.display='none'; consiglieriSindaci.value='';
		divFamiliareInput.style.display='none'; familiare.value='';
		divRapportoInput.style.display='block';		
	} else {
		divConsiglieriSindaciInput.style.display='none'; consiglieriSindaci.value='';
		divFamiliareInput.style.display='none'; familiare.value='';
		divRapportoInput.style.display='none'; rapporto.value='';	
	}
}


/* funzione di salvataggio parte correlata */
function salvaParteCorrelata(){
	console.log('salva parteCorrelata');
	var form = document.getElementById("parteCorrelataView"); 
	var op = document.getElementById("op");
	op.value="salvaParteCorrelata";
	form.submit(); 
}



function initTabellaRicercaPartiCorrelate(){
	console.log('ricerca Parti Correlate');
	$('#tabellaPartiCorrelate').bootstrapTable({
        method: 'get',
        url: WEBAPP_BASE_URL+'parteCorrelata/elencoSample.action',
        cache: false,
        height: 600,
        striped: true,
        pagination: true,
        pageSize: 10, 
        search: false,
        sidePagination: 'server',
        showRefresh: false,
        clickToSelect: true,
		onClickRow: function (row) {
        },
        columns: [
			{
				field: 'id',
				title: 'id',
				align: 'left',
				valign: 'top',
				sortable: true
			},{
				field: 'denominazione',
				title: 'denominazione',
				align: 'left',
				valign: 'top',
				sortable: true
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
			},{
				field: 'familiare',
				title: 'familiare',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'consiglieriSindaci',
				title: 'consiglieriSindaci',
				align: 'left',
				valign: 'top',
				sortable: false
			},{
				field: 'dataInserimento',
				title: 'dataInserimento',
				align: 'left',
				valign: 'top',
				sortable: false
			}
		]
    });		 
}