function salvaSocieta(){
	console.log('salva societa');
	var form = document.getElementById("societaView"); 
	var op = document.getElementById("op");
	op.value="salvaSocieta";
	form.submit(); 
}

function cancellaSocieta(){
	console.log('cancella societa');
	var form = document.getElementById("societaView"); 
	var op = document.getElementById("op");
	op.value="salvaSocieta";
	setDeleteMode();
	form.submit(); 
}

function caricaDettaglioSocieta(id) {
	console.log('caricaDettaglioSocieta: '+id);
	setEditMode();
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var nome = data.getElementsByTagName("nome");
		if (nome["0"] != null) {
			nome = nome["0"].textContent;
		} 
		else {
			nome = "";
		}
		
		var ragioneSociale = data.getElementsByTagName("ragioneSociale");
		if (ragioneSociale["0"] != null) {
			ragioneSociale = ragioneSociale["0"].textContent;
		} 
		else {
			ragioneSociale = "";
		}
		
		
		var emailAmministrazione = data.getElementsByTagName("emailAmministrazione");
		var textemails = document.querySelectorAll("input[name^='emailAmministrazione[']");
		textemails[0].value = "";
		for (i = 0; i < emailAmministrazione.length; i++) {
			if(i == 0){
				textemails[i].value = emailAmministrazione[i].firstChild.data;
			} else {
				$('#divEmailEdit').append('<div class="list-group-item media" id="addEmailEdit_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
						'<div class="col-sm-9">'+
						'<input id="emailAmministrazione['+ i +']" name="emailAmministrazione['+ i +']" class="form-control" type="text"  value = "'+ emailAmministrazione[i].firstChild.data +'" >'+
					'</div>'+
					'<button type="button" onclick="removeEmailEdit(this.id)" id="'+ i +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
						'<span class="glyphicon glyphicon-minus"></span>'+
					'</button></div></div></div>' );
			}
		}
		
		var indirizzo = data.getElementsByTagName("indirizzo");
		if (indirizzo["0"] != null) {
			indirizzo = indirizzo["0"].textContent;
		} 
		else {
			indirizzo = "";
		}
		
		var cap = data.getElementsByTagName("cap");
		if (cap["0"] != null) {
			cap = cap["0"].textContent;
		} 
		else {
			cap = "";
		}
		
		var citta = data.getElementsByTagName("citta");
		if (citta["0"] != null) {
			citta = citta["0"].textContent;
		} 
		else {
			citta = "";
		}
		
		var idTipoSocieta = data.getElementsByTagName("idTipoSocieta");
		if (idTipoSocieta["0"] != null) {
			idTipoSocieta = idTipoSocieta["0"].textContent;
		} 
		else {
			idTipoSocieta = "";
		}
		
		var idNazione = data.getElementsByTagName("idNazione");
		if (idNazione["0"] != null) {
			idNazione = idNazione["0"].textContent;
		} 
		else {
			idNazione = "";
		}
		
		var codGruppoLingua = data.getElementsByTagName("codGruppoLingua");
		if (codGruppoLingua["0"] != null) {
			codGruppoLingua = codGruppoLingua["0"].textContent;
		} 
		else {
			codGruppoLingua = "";
		}
		
		$('form[name="societaEditForm"] input[name=nome]').first().val(nome); 
		$('form[name="societaEditForm"] input[name=ragioneSociale]').first().val(ragioneSociale); 
		$('form[name="societaEditForm"] input[name=emailAmministrazione]').first().val(emailAmministrazione); 
		$('form[name="societaEditForm"] input[name=indirizzo]').first().val(indirizzo); 
		$('form[name="societaEditForm"] input[name=cap]').first().val(cap); 
		$('form[name="societaEditForm"] input[name=citta]').first().val(citta);
		
		deselezionaListaTipoSocieta();
		selezionaListaTipoSocieta(idTipoSocieta);
		
		deselezionaListaNazioni();
		selezionaListaNazioni(codGruppoLingua);
		
	};
	$("[id^='addEmailEdit_']").remove();
	var url = WEBAPP_BASE_URL + "societa/caricaDettaglioSocieta.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function editCheck(){
	console.log('editCheck');
	setEditMode();
	cleanFieldsInsert();
}

function insertCheck(){
	console.log('insertCheck');
	setInsertMode();
	deselezionaListaTipoSocietaInsert();
	cleanFieldsEdit();
}

function cleanFieldsEdit() {
	deselezionaSocieta();
	$('form[name="societaEditForm"] input[name=nome]').val(""); 
	$('form[name="societaEditForm"] input[name=ragioneSociale]').val(""); 
	$('form[name="societaEditForm"] input[name="emailAmministrazione[0]"').val(""); 
	$("[id^='addEmailEdit_']").remove();
	document.getElementById("countEmailEdit").value = 0;
	$('form[name="societaEditForm"] input[name=indirizzo]').val(""); 
	$('form[name="societaEditForm"] input[name=cap]').val(""); 
	$('form[name="societaEditForm"] input[name=citta]').val("");
	deselezionaListaTipoSocieta();
	deselezionaListaNazioni();
}

function cleanFieldsInsert() {
	$('form[name="societaEditForm"] input[name=nomeIns]').val(""); 
	$('form[name="societaEditForm"] input[name=ragioneSocialeIns]').val(""); 
	$('form[name="societaEditForm"] input[name="emailAmministrazioneIns[0]"').val("");
	$("[id^='addEmailInsert_']").remove();
	document.getElementById("countEmailInsert").value = 0;
	$('form[name="societaEditForm"] input[name=indirizzoIns]').val(""); 
	$('form[name="societaEditForm"] input[name=capIns]').val(""); 
	$('form[name="societaEditForm"] input[name=cittaIns]').val("");
	deselezionaListaTipoSocietaInsert();
	deselezionaListaNazioniInsert();
}

function selezionaListaTipoSocieta(valueStr) {
	var ele = document.getElementById("idTipoSocieta");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == valueStr) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function selezionaListaNazioni(valueStr) {
	var ele = document.getElementById("nazioneCode");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == valueStr) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}


function deselezionaListaTipoSocieta() {
	var ele = document.getElementById("idTipoSocieta");
	ele.selectedIndex = 0;
}

function deselezionaListaNazioni() {
	var ele = document.getElementById("nazioneCode");
	ele.selectedIndex = 0;
}


function deselezionaListaTipoSocietaInsert() {
	var ele = document.getElementById("idTipoSocietaIns");
	ele.selectedIndex = 0;
}

function deselezionaListaNazioniInsert() {
	var ele = document.getElementById("nazioneCodeIns");
	ele.selectedIndex = 0;
}

function deselezionaSocieta() {
	var ele = document.getElementById("idSocieta");
	ele.selectedIndex = 0;
}

function setEditMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="true";
}

function setDeleteMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
}

function setInsertMode() {
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
}

function caricaDettaglioSocietaVisualizzazione(id) {
	console.log('caricaDettaglioSocieta: '+id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var nome = data.getElementsByTagName("nome");
		if (nome["0"] != null) {
			nome = nome["0"].textContent;
		} 
		else {
			nome = "";
		}
		
		var ragioneSociale = data.getElementsByTagName("ragioneSociale");
		if (ragioneSociale["0"] != null) {
			ragioneSociale = ragioneSociale["0"].textContent;
		} 
		else {
			ragioneSociale = "";
		}
		
		var emailAmministrazione = data.getElementsByTagName("emailAmministrazione");
		var textemails = document.querySelectorAll("input[name^='emailAmministrazione[']");
		textemails[0].value = "";
		for (i = 0; i < emailAmministrazione.length; i++) {
			if(i == 0){
				textemails[i].value = emailAmministrazione[i].firstChild.data;
			} else {
				$('#divEmail').append('<div class="list-group-item media" id="addEmailVis_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
						'<div class="col-sm-10">'+
						'<input id="emailAmministrazione['+ i +']" name="emailAmministrazione['+ i +']" class="form-control" type="text"  value = "'+ emailAmministrazione[i].firstChild.data +'"  readonly="true" >'+
					'</div>' );
			}
		}
		
		var indirizzo = data.getElementsByTagName("indirizzo");
		if (indirizzo["0"] != null) {
			indirizzo = indirizzo["0"].textContent;
		} 
		else {
			indirizzo = "";
		}
		
		var cap = data.getElementsByTagName("cap");
		if (cap["0"] != null) {
			cap = cap["0"].textContent;
		} 
		else {
			cap = "";
		}
		
		var citta = data.getElementsByTagName("citta");
		if (citta["0"] != null) {
			citta = citta["0"].textContent;
		} 
		else {
			citta = "";
		}
		
		var idTipoSocieta = data.getElementsByTagName("idTipoSocieta");
		if (idTipoSocieta["0"] != null) {
			idTipoSocieta = idTipoSocieta["0"].textContent;
		} 
		else {
			idTipoSocieta = "";
		}
		
		var idNazione = data.getElementsByTagName("idNazione");
		if (idNazione["0"] != null) {
			idNazione = idNazione["0"].textContent;
		} 
		else {
			idNazione = "";
		}
		
		var codGruppoLingua = data.getElementsByTagName("codGruppoLingua");
		if (codGruppoLingua["0"] != null) {
			codGruppoLingua = codGruppoLingua["0"].textContent;
		} 
		else {
			codGruppoLingua = "";
		}
		
		$('form[name="societaReadForm"] input[name=nome]').first().val(nome); 
		$('form[name="societaReadForm"] input[name=ragioneSociale]').first().val(ragioneSociale); 
		$('form[name="societaReadForm"] input[name=emailAmministrazione]').first().val(emailAmministrazione); 
		$('form[name="societaReadForm"] input[name=indirizzo]').first().val(indirizzo); 
		$('form[name="societaReadForm"] input[name=cap]').first().val(cap); 
		$('form[name="societaReadForm"] input[name=citta]').first().val(citta);
		
		deselezionaListaTipoSocieta();
		selezionaListaTipoSocieta(idTipoSocieta);
		
		deselezionaListaNazioni();
		selezionaListaNazioni(codGruppoLingua);
		
		
	};
	$("[id^='addEmailVis_']").remove();
	var url = WEBAPP_BASE_URL + "societa/caricaDettaglioSocieta.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function addEmailEdit(){
	var count = document.getElementById("countEmailEdit").value;
	count++;
	document.getElementById("countEmailEdit").value = count;
	
	$('#divEmailEdit').append(
		'<div class="list-group-item media" id="addEmailEdit_'+ count +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
			'<div class="col-sm-9">'+
			'<input id="emailAmministrazione'+ count +'" name="emailAmministrazione['+ count +']" class="form-control" type="text">'+
		'</div>'+
		'<button type="button" onclick="removeEmailEdit(this.id)" id="'+ count +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div></div>' );
}

function removeEmailEdit(idCliccato){
	$('#addEmailEdit_'+ idCliccato).remove();
	
	var count = document.getElementById("countEmailEdit").value;
	count -= 1;
	document.getElementById("countEmailEdit").value = count;
}

function addEmailInsert() {
	var count = document.getElementById("countEmailInsert").value;
	count++;
	document.getElementById("countEmailInsert").value = count;
	
	$('#divEmailInsert').append(
		'<div class="list-group-item media" id="addEmailInsert_'+ count +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
			'<div class="col-sm-9">'+
			'<input id="emailAmministrazioneIns'+ count +'" name="emailAmministrazioneIns['+ count +']" class="form-control" type="text">'+
		'</div>'+
		'<button type="button" onclick="removeEmailInsert(this.id)" id="'+ count +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div></div>' );
}

function removeEmailInsert(idCliccato){
	$('#addEmailInsert_'+ idCliccato).remove();
	
	var count = document.getElementById("countEmailInsert").value;
	count -= 1;
	document.getElementById("countEmailInsert").value = count;
}
