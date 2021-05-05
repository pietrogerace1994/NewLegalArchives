var protocollo_checkedRows = [];


$(document).on('change','#destINid',function(){
	  $('#unitaApp').val($(this).children(":selected").attr("id"));
});

$(document).on('change','#mittOUTid',function(){
	  $('#unitaAppOut').val($(this).children(":selected").attr("id"));
});


function generaProtocollo(type){
	
	if(type=='IN'){
		var mittente = $('#mittente').val();
		var destinatario = $('#destINid').children(":selected").val();
		var $table = $('#tabellaRicercaProtocolloIN');
		var unita = $('#unitaApp').val();
		var oggetto = $('#oggetto').val();
		
		if(mittente!='' && destinatario!='' && oggetto!=''){
			$('#load').css('visibility','visible');
			$.ajax({
				url: WEBAPP_BASE_URL + "rest/generaNumeroProtocollo.action?mittente="+mittente+"&destinatario="+destinatario+"&unitaAppart="+unita+"&oggetto="+oggetto+"&tipo="+type+"&CSRFToken="+legalSecurity.getToken()
			}).then(function(data) {
				if(data.stato=='OK'){
					$('#numeroProtocollo').val(data.messaggio);
					$('#load').css('visibility','collapse');
					$table.bootstrapTable('refresh');
				}
			});
		}
		else{
			$('#numeroProtocollo').val('');
				if(mittente=='')
					$('#mittente').css('background-color','rgba(255, 0, 0, 0.38);');
				if(destinatario==undefined)
					$('#destINid').css('background-color','rgba(255, 0, 0, 0.38);');
				if(oggetto=='')
					$('#oggetto').css('background-color','rgba(255, 0, 0, 0.38);');
		}
	}
	else if(type=='OUT'){
		var mittente = $('#mittOUTid').children(":selected").val();
		var destinatario = $('#destinatarioOut').val()
		var $table = $('#tabellaRicercaProtocolloOUT');
		var unita = $('#unitaAppOut').val();
		var oggetto = $('#oggettoOut').val();
		
		if(mittente!='' && destinatario!='' && oggetto!=''){
			$('#loadOut').css('visibility','visible');
			$.ajax({
				url: WEBAPP_BASE_URL + "rest/generaNumeroProtocollo.action?mittente="+mittente+"&destinatario="+destinatario+"&unitaAppart="+unita+"&oggetto="+oggetto+"&tipo="+type+"&CSRFToken="+legalSecurity.getToken()
			}).then(function(data) {
				if(data.stato=='OK'){
					$('#numeroProtocolloOut').val(data.messaggio);
					$('#loadOut').css('visibility','collapse');
					$table.bootstrapTable('refresh');
				}
			});
		}
		else{
			$('#numeroProtocolloOut').val('');
				if(destinatario=='')
					$('#destinatarioOut').css('background-color','rgba(255, 0, 0, 0.38);');
				if(mittente==undefined)
					$('#mittOUTid').css('background-color','rgba(255, 0, 0, 0.38);');
				if(oggetto=='')
					$('#oggettoOut').css('background-color','rgba(255, 0, 0, 0.38);');
		}
	}	
	
	
}

function caricaAzioniProtocollo(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuProtocollo(id);
		}

	}
}

function caricaAzioniSuProtocollo(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaProtocollo" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "protocollo/caricaAzioniProtocollo.action?idProtocollo=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}

function initTabellaRicercaProtocolloIN() {

	var $table = $('#tabellaRicercaProtocolloIN').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'protocollo/ricerca.action?type=IN',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniProtocollo,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNumProtocollo").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAl").value); 
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicolo").value) ;
					var statoCode = document.getElementById("statoProtocolloCode").value;
					params.numeroProtocollo = nome; 
					params.dal = dal;
					params.al = al;
					params.nomeFascicolo = nomeFascicolo;
					params.statoProtocolloCode = statoCode;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'numeroProtocollo',
					title : 'NUMERO PROTOCOLLO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'stato',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'mittente',
					title : 'MITTENTE',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'destinatario',
					title : 'DESTINATARIO',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'unita',
					title : 'UNITA DI APPARTENENZA',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function initTabellaRicercaProtocolloOUT() {

	var $table = $('#tabellaRicercaProtocolloOUT').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'protocollo/ricerca.action?type=OUT',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniProtocollo,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNumProtocolloOut").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalOut").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlOut").value); 
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicoloOut").value) ;
					var statoCode = document.getElementById("statoProtocolloCodeOUT").value
					params.statoProtocolloCode = statoCode;
					params.numeroProtocollo = nome; 
					params.dal = dal;
					params.al = al;
					params.nomeFascicolo = nomeFascicolo;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'numeroProtocollo',
					title : 'NUMERO PROTOCOLLO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'stato',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'mittente',
					title : 'MITTENTE',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'destinatario',
					title : 'DESTINATARIO',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'unita',
					title : 'UNITA DI APPARTENENZA',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

/**
 * Metodo di supporto per popolare la tabella degli atti protocollati
 * da assegnare
 * @author MASSIMO CARUSO
 */
function initTabellaAttiProtocollati() {

	var $table = $('#tabellaRicercaAttiProtocollati').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'protocollo/ricerca.action?type=ACT',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniProtocollo,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNumProtocolloATTI").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalATTI").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlATTI").value); 
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicoloATTI").value) ;
					var statoCode = document.getElementById("statoProtocolloCodeATTI").value;
					params.statoProtocolloCode = statoCode;
					params.numeroProtocollo = nome; 
					params.dal = dal;
					params.al = al;
					params.nomeFascicolo = nomeFascicolo;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'numeroProtocollo',
					title : 'NUMERO PROTOCOLLO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'stato',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
					field : 'mittente',
					title : 'MITTENTE',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'destinatario',
					title : 'DESTINATARIO',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'unita',
					title : 'UNITA DI APPARTENENZA',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

$('#panelUpload').on('show.bs.modal', function(e) {
    
    var $modal = $(this),
        numProtocollo = $(e.relatedTarget).attr('data-name');
    	idProtocollo = $(e.relatedTarget).attr('data-id');

            $modal.find('#modalNum').html(numProtocollo);
            $modal.find('#idProt').val(idProtocollo);
})

$('#panelAssegna').on('shown.bs.modal', function(e) {
    
    var $modal = $(this),
        matricola = $(e.relatedTarget).attr('data-name');
    	idProtocollo = $(e.relatedTarget).attr('data-id');
    	nome = $(e.relatedTarget).attr('data-nome');
    	//aggiunta tipo protocollo ed id documento per atti da assegnare MASSIMO CARUSO
    	tipo = $(e.relatedTarget).attr('data-tipoprotocollo');
    	if(tipo === "ACT"){
    		idDocumentoAtto = $(e.relatedTarget).attr('data-iddocumento');
    		$modal.find('#idDocumentoAtto').val(idDocumentoAtto);
    		$modal.find('#utenteAss').hide();
    		matricola = "0910004738";
    	}else{
    		$modal.find('#utenteAss').show();
    	}
    	//FINE aggiunta tipo protocollo ed id documento per atti da assegnare MASSIMO CARUSO
            
    	$modal.find('#modalNumA').html(nome);
        $modal.find('#idProtA').val(idProtocollo);
        //aggiunta tipo protocollo MASSIMO CARUSO
        $('#tipoProtocollo').val(tipo);
        //FINE aggiunta tipo protocollo MASSIMO CARUSO
        $('#utenteAss').val(matricola);
           
            
           
})

$('#panelRiassegna').on('shown.bs.modal', function(e) {
    
    var $modal = $(this),
        matricola = $(e.relatedTarget).attr('data-name');
    	idProtocollo = $(e.relatedTarget).attr('data-id');
    	nome = $(e.relatedTarget).attr('data-nome');

            $modal.find('#modalNumA').html(nome);
            $modal.find('#idProtARi').val(idProtocollo);
            	
            var newOptions;
            
            var select = $('#utenteRiass');
            
			$.ajax({
				url: WEBAPP_BASE_URL + "protocollo/getCollaboratori.action?matricola="+matricola
				
				//+"&CSRFToken="+legalSecurity.getToken()
			}).then(function(data) {
				newOptions = data.collaboratore;
				
				$.each(newOptions, function(i, item) {
          			$('<option />', {
      			 	value: item.value,
      			 	text: item.name,
      				id: item.id
  			 		}).appendTo(select)
          		 });
			});
        

            
           
})

$('#panelUpload').on('hide.bs.modal', function(e) {
    
    $('#progress').css('display','none');
    $('#done').css('display','none');
    $('#completato').css('display','none');
    $('#nomefile').val('');
    $('#sendfile').prop('disabled', true);
})

$(function() {

  // We can attach the `fileselect` event to all file inputs on the page
  $(document).on('change', ':file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, " "+label]);
    $('#sendfile').prop('disabled', false);
  });

  // We can watch for our custom `fileselect` event like this
  $(document).ready( function() {
      $(':file').on('fileselect', function(event, numFiles, label) {

          var input = $(this).parents('.input-group').find(':text'),
              log = numFiles > 1 ? numFiles + ' files selected' : label;

          if( input.length ) {
              input.val(log);
          } else {
              if( log ) alert(log);
          }

      });
  });
  
});


$('#uploadForm').submit(function(e){
		e.preventDefault();
		var form_data = new FormData(this); 
	//	form_data.append("file", file[0]);
		form_data.append("id", $('#idProt').val());
		form_data.append('CSRFToken',legalSecurity.getToken());
		$('#sendfile').prop('disabled', true);
		$('#progress').css('display','inline-block');
		$('#incorso').css('display','block');
		var $table = $('#tabellaRicercaProtocolloIN');
		var $table2 = $('#tabellaRicercaProtocolloOUT');
		//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		var $table3 = $('#tabellaRicercaAttiProtocollati');
		//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO

		$.ajax({
			type: 'post',
			url:  WEBAPP_BASE_URL + 'protocollo/caricaDocumentoProtocollo.action',
			data: form_data,
			enctype: 'multipart/form-data',
			cache: false,
			contentType: false,
			processData: false,
			success: function(answ){
				$('#done').css('display','block');
				$('#progress').css('display','none');
				$('#incorso').css('display','none');
				$('#completato').css('display','block');
				
				setTimeout(function(){
					$('#panelUpload').modal("hide");
				}, 2000);
				
				$table.bootstrapTable('refresh');
				$table2.bootstrapTable('refresh');
				//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
				$table3.bootstrapTable('refresh');
				//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
			}
		})
})


$('#assegnaForm').submit(function(e){
		e.preventDefault();

		var $table = $('#tabellaRicercaProtocolloIN');
		var $table2 = $('#tabellaRicercaProtocolloOUT');
		//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		var $table3 = $('#tabellaRicercaAttiProtocollati');
		//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		
		$('#done2').css('display','none');
		$('#completato2').css('display','none');
		$('#progress2').css('display','inline-block');
		$('#incorso2').css('display','block');
		
		var assegnatario = $('#utenteAss').val();
		var commento = $('#commento').val();
		var idProt = $('#idProtA').val();
		
		//aggiunta tipo protocollo per distinguere atti protocollati MASSIMO CARUSO
		var tipo = $('#tipoProtocollo').val();
		
		if(tipo === "ACT"){
			
			var idDocumento = $('#idDocumentoAtto').val();
			//inserisco l'atto da validare a DB MASSIMO CARUSO
			$.ajax({
				type: 'post',
				url: WEBAPP_BASE_URL + "rest/inserisciAttoDaValidare.action?assegnatario="+assegnatario+"&idDocumento="+idDocumento+"&idProtocollo="+idProt+"&CSRFToken="+legalSecurity.getToken(),
				success: function(answ){
					$('#done2').css('display','block');
					$('#progress2').css('display','none');
					$('#incorso2').css('display','none');
					$('#completato2').css('display','block');
					$('#commento').val('');
					setTimeout(function(){
						$('#panelAssegna').modal("hide");
					}, 1000);
				
					$table.bootstrapTable('refresh');
					$table2.bootstrapTable('refresh');
					//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
					$table3.bootstrapTable('refresh');
					//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
					$('#done2').css('display','none');
					$('#completato2').css('display','none');
				}
			})
			
		}//FINE aggiunta tipo protocollo per distinguere atti protocollati MASSIMO CARUSO
		else{
		
			$.ajax({
				type: 'post',
				url: WEBAPP_BASE_URL + "protocollo/assegnaProtocollo.action?assegnatario="+assegnatario+"&commento="+commento+"&idProt="+idProt+"&CSRFToken="+legalSecurity.getToken(),
				success: function(answ){
					$('#done2').css('display','block');
					$('#progress2').css('display','none');
					$('#incorso2').css('display','none');
					$('#completato2').css('display','block');
					$('#commento').val('');
					setTimeout(function(){
						$('#panelAssegna').modal("hide");
					}, 1000);
				
					$table.bootstrapTable('refresh');
					$table2.bootstrapTable('refresh');
					//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
					$table3.bootstrapTable('refresh');
					//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
					$('#done2').css('display','none');
					$('#completato2').css('display','none');
				}
			})
		}
		
		//FINE aggiunta protocollo per atti da assegnare MASSIMO CARUSO
})

$('#riassegnaForm').submit(function(e){
		e.preventDefault();

		var $table = $('#tabellaRicercaProtocolloIN');
		var $table2 = $('#tabellaRicercaProtocolloOUT');
		//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		var $table3 = $('#tabellaRicercaAttiProtocollati');
		//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		$('#done3').css('display','none');
		$('#completato3').css('display','none');
		$('#progress3').css('display','inline-block');
		$('#incorso3').css('display','block');
		
		var assegnatario = $('#utenteRiass').val();
		var commento = $('#commentoRi').val();
		var idProt = $('#idProtARi').val();

		$.ajax({
			type: 'post',
			url: WEBAPP_BASE_URL + "protocollo/assegnaProtocollo.action?assegnatario="+assegnatario+"&commento="+commento+"&idProt="+idProt+"&CSRFToken="+legalSecurity.getToken(),
			success: function(answ){
				$('#done3').css('display','block');
				$('#progress3').css('display','none');
				$('#incorso3').css('display','none');
				$('#completato3').css('display','block');
				$('#commentoRi').val('');
				setTimeout(function(){
					$('#panelRiassegna').modal("hide");
				}, 1000);
				
				$table.bootstrapTable('refresh');
				$table2.bootstrapTable('refresh');
				//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
				$table3.bootstrapTable('refresh');
				//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
				$('#done3').css('display','none');
				$('#completato3').css('display','none');
			}
		})
})

$('#protocolloPanelCercaSelezionaPadreFascicolo').on('show.bs.modal', function(e) {
	    var div = document.getElementById("containerRicercaModaleFascicoloPadre");
	    var $modal = $(this),
    		idProtocollo = $(e.relatedTarget).attr('data-id');
    		nome = $(e.relatedTarget).attr('data-name');
    		
	    $modal.find('#modalNumASSO').html(nome);
	    $modal.find('#modalidProtocollo').val(idProtocollo);
	    
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	
	    	div.innerHTML=data;
	    	protocollo_initTabellaRicercaFascicoliModale(false);
	    };
	    var ajaxUtil = new AjaxUtil();
	    var url = WEBAPP_BASE_URL + "fascicolo/cercaModale.action?multiple=protocollo";
url=legalSecurity.verifyToken(url);
	    ajaxUtil.ajax(url, null, "GET", "text/html", fnCallbackSuccess, null, null)
	});

function protocollo_initTabellaRicercaFascicoliModale(isMultiple) {

	var $table = $('#tabellaRicercaFascicoli').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'fascicolo/ricerca2.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					if(isMultiple && document.getElementsByName("btSelectAll") ){
						document.getElementsByName("btSelectAll")[0].style.display="none";
					}
					
					var nome = "";
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggettoModal").value);
					var legaleEsterno = encodeURIComponent(document
							.getElementById("txtLegaleEsternoModal").value);
					var controparte = encodeURIComponent(document
							.getElementById("txtControparteModal").value);
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalModal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlModal").value);
					var tipologiaFascicoloCode = encodeURIComponent(document
							.getElementById("tipologiaFascicoloCodeModal").value);
					var settoreGiuridicoCode = encodeURIComponent(document
							.getElementById("settoreGiuridicoCodeModal").value);
					params.nome = nome;
					params.oggetto = oggetto;
					params.legaleEsterno = legaleEsterno;
					params.controparte = controparte;
					params.settoreGiuridicoCode = settoreGiuridicoCode;
					params.tipologiaFascicoloCode = tipologiaFascicoloCode;
					params.dal = dal;
					params.al = al;
					return params;
				},
				columns : 
				[ {
					checkbox: isMultiple, 
					radio: !isMultiple,
					title : '',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'nome',
					title : 'NUMERO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				},
				{
					field : 'owner',
					title : 'owner',
					align : 'left',
					valign : 'top',
					sortable : true
				},
				{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'legaleEsterno',
					title : 'LEGALE ESTERNO INCAR.',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				},  {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				} ]
			});

			$('#tabellaRicercaFascicoli').on('check.bs.table', function (e, row) {
			  if( isMultiple ) {
				  protocollo_checkedRows.push({id: row.id, nome:row.nome });
			  }else{
				  protocollo_checkedRows = [{id: row.id, nome:row.nome}]; 
			  }
			  //console.log(agenda_checkedRows);
			  
			  $("#btnAggiungiFascicoloPadreProtocollo").css('display','block');
			  $("#btnAggiungiFascicoloPadreProtocollo").css('visibility','visible');
			  
			
			});
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function protocollo_selezionaFascicoloChiudi() {
	protocollo_checkedRows=new Array();
	$('#protocolloPanelCercaSelezionaPadreFascicolo').modal('hide');
}

function protocollo_selezionaFascicolo(){
	
	$('#done3').css('display','none');
	$('#completato3').css('display','none');
	
	console.log(protocollo_checkedRows[0].id);
	
	var idFascicolo = protocollo_checkedRows[0].id;
	var idProtocollo = $('#modalidProtocollo').val();
	
	var $table = $('#tabellaRicercaProtocolloIN');
	var $table2 = $('#tabellaRicercaProtocolloOUT');
	//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
	var $table3 = $('#tabellaRicercaAttiProtocollati');
	//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
	
	$('#progress3').css('display','inline-block');
	$('#incorso3').css('display','block');

	$.ajax({
		type: 'post',
		url: WEBAPP_BASE_URL + "protocollo/spostaSuFascicolo.action?idFascicolo="+idFascicolo+"&idProtocollo="+idProtocollo+ "&CSRFToken="+legalSecurity.getToken(),
		success: function(answ){
			$('#done3').css('display','block');
			$('#progress3').css('display','none');
			$('#incorso3').css('display','none');
			$('#completato3').css('display','block');
			setTimeout(function(){
				$('#protocolloPanelCercaSelezionaPadreFascicolo').modal("hide");
			}, 2000);
			
			$table.bootstrapTable('refresh');
			$table2.bootstrapTable('refresh');
			//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
			$table3.bootstrapTable('refresh');
			//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		}
	})
	
}

function lasciaInArchivioProtocollo(idProtocollo){
	
	
	var $table = $('#tabellaRicercaProtocolloIN');
	var $table2 = $('#tabellaRicercaProtocolloOUT');
	//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
	var $table3 = $('#tabellaRicercaAttiProtocollati');
	//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
	

	$.ajax({
		type: 'post',
		url: WEBAPP_BASE_URL + "protocollo/lasciaSuArchivioProtocollo.action?idProtocollo="+idProtocollo+ "&CSRFToken="+legalSecurity.getToken(),
		success: function(answ){
			$table.bootstrapTable('refresh');
			$table2.bootstrapTable('refresh');
			//aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
			$table3.bootstrapTable('refresh');
			//FINE aggiunta tabella atti protocollati da assegnare MASSIMO CARUSO
		}
	})
	
}


