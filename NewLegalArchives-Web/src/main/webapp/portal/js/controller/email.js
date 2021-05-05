function salvaEmail() {	
	console.log('salva email');
	 
	var form = document.getElementById("emailView");
	var op = document.getElementById("op");
	op.value = "salvaEmail";
	waitingDialog.show('Loading...');
	
	form.submit();
}
 
 
function modificaEmail(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"articolo/modificaEmail.action?id="+id);
}
  

function selezionaCategoria(codice){
	console.log("selezionaCategoria con codice: " + codice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		waitingDialog.hide(); 
		console.log(data);
		var comboSottocateg = document.getElementById("sottoCategoriaCode"); 
		comboSottocateg.options.length = 1; 
		
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].codice);
			var optionText = document.createTextNode(data[i].nomeCategoria);
			option.appendChild(optionText);
			comboSottocateg.appendChild(option);
		}
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "articolo/selezionaCategoria.action?codice="+codice ;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/json", callBackFn, null, callBackFnErr);
}


function cercaEmail(){
	document.getElementById('btnApplicaFiltri').click();

}

function inviaEmail(idemail){
	console.log('salva email');
	 
	var form = document.getElementById("emailView");
	var op = document.getElementById("op");
	op.value = "inviaMailingList";
	
	var el = document.getElementsByName("checkRubrica");
	for( i = 0; i < el.length; i++ ){
		var check = el[i];
		if( check.checked ){
			var input = document.createElement("INPUT")
			input.setAttribute("type","hidden");
			input.setAttribute("name","arrRubricaInvioEmail");
			input.setAttribute("value",check.value);
			form.appendChild(input); 
		}
	}	
	 
	waitingDialog.show('Loading...');   
	form.submit();
}

function preparaInviaComunicazione(){ 
	console.log("preparaInviaComunicazione INIZIO" );
	var el = document.getElementsByName("chkEmail");
	var params = "";
	for( i = 0; i < el.length; i++ ){
		var check = el[i];
		if( check.checked ){ 
			params += "emailIdsArray="+check.value+"&"; 
		}
	}	
	
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  

		console.log("preparaInviaComunicazione RETURN AJAX" );
		waitingDialog.hide();  
		visualizzaMessaggio(data);
		 
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};
	
	var url = WEBAPP_BASE_URL + "articolo/preparaInviaComunicazione.action?"+params ;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, null, "post", "application/json", callBackFn, null, callBackFnErr);
}



function checkAll(id, idCheckAll){
	var isChecked = false;
	var el = document.getElementsByName(id);
	var checkAll = document.getElementById(idCheckAll);
	
	for( i = 0; i < el.length; i++ ){ 
		var check = el[i];	 
		if( !check.checked ){ 
			isChecked = true;
			break;
		}
	}
	
	
	for( i = 0; i < el.length; i++ ){
		var check = el[i];	 
		check.checked = isChecked;			
	}
	checkAll.checked = isChecked;
	
}

function caricaMailingList(idemail){
	console.log("caricaMailingList con idemail: " + idemail);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		waitingDialog.hide(); 
		console.log(data);
		var tbody = document.getElementById("tBodyInviaEmail"); 
		tbody.innerHTML = ''; 
		
		for( i = 0; i < data.length; i++ ){
			var tr = document.createElement("TR"); 
			var td = document.createElement("TD");
			var check = document.createElement("INPUT")
			check.setAttribute("type","checkbox");
			check.setAttribute("name","checkRubrica");
			check.setAttribute("value",data[i].email);
			td.appendChild(check);
			tr.appendChild(td);
		
			var tdNominativo = document.createElement("TD");
			var textNominativo = document.createTextNode(data[i].nominativo);
			tdNominativo.appendChild(textNominativo);
			tr.appendChild(tdNominativo);

			var tdEmail = document.createElement("TD");
			var textEmail = document.createTextNode(data[i].email);
			tdEmail.appendChild(textEmail);
			tr.appendChild(tdEmail);
			
			var tdCategoria = document.createElement("TD");
			var textCategoria = document.createTextNode(data[i].categoria);
			tdCategoria.appendChild(textCategoria);
			tr.appendChild(tdCategoria);
		 
			tbody.appendChild(tr);
		}
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "articolo/caricaMailingList.action?idemail="+idemail ;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/json", callBackFn, null, callBackFnErr);
} 
 
function initTabellaRicercaEmail() {
	
	

	var $table = $('#tabellaRicercaEmail').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'articolo/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniEmail,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggettoEmail").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAl").value); 
					var categoria = encodeURIComponent(document
							.getElementById("comboCategoria").value); 
					var contenutoBreve = encodeURIComponent(document
							.getElementById("txtAbstractEmail").value); 
					 
					params.oggetto = oggetto;  
					params.dal = dal; 
					params.al = al;
					params.categoria = categoria;
					params.contenutoBreve = contenutoBreve;
					return params;
				},
				columns : [  {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'oggetto',
					title : 'TITOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'dataOra',
					title : 'DATA',
					align : 'center',
					valign : 'top',
					sortable : false
				},{
					field : 'categoria',
					title : 'CATEGORIA',
					align : 'center',
					valign : 'top',
					sortable : false,
				    cellStyle: function(value, row, index){
				            	return {  css: {"color": "#"+row.colore, "font-weight": "bold"} };
			        			}
				},{
					field : 'contenutoBreve',
					title : 'ABSTRACT',
					align : 'center',
					valign : 'top',
					sortable : false
				} ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}
 

function eliminaEmail(id){
	console.log("elimino email con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaEmail();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "articolo/elimina.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}


function caricaAzioniEmail(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuEmail(id);
		}

	}
} 

function caricaAzioniSuEmail(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaEmail" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "presidionormativo/caricaAzioniEmail.action?idEmail=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}
 