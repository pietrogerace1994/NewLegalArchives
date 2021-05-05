
function cancellaMailingList(){
	console.log('cancella MailingList');
	var form = document.getElementById("mailingListView"); 
	var op = document.getElementById("op");
	op.value="salvaMailingList";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	form.action=WEBAPP_BASE_URL+"mailingList/salva.action";
	form.submit(); 
}

function salvaMailingList(){
	console.log('salva mailingList');
	var form = document.getElementById("mailingListView"); 
	var op = document.getElementById("op");
	op.value="salvaMailingList";
	waitingDialog.show('Loading...');
	form.action=WEBAPP_BASE_URL+"mailingList/salva.action";
	form.submit(); 
}

function salvaEditMailingList(){
	console.log('salva mailingList da edit');
	var form = document.getElementById("mailingListView"); 
	var op = document.getElementById("op");
	op.value="salvaMailingList";
	var editMode = document.getElementById("editMode");
	editMode.value="true";
	form.action=WEBAPP_BASE_URL+"mailingList/salva.action";
	form.submit(); 
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

function selezionaCategoriaMod(codice, sotto){
	console.log("selezionaCategoria con codice: " + codice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		waitingDialog.hide(); 
		console.log(data);
		var comboSottocateg = document.getElementById("sottoCategoriaCodeMod"); 
		comboSottocateg.options.length = 1; 
		
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].codice);
			var optionText = document.createTextNode(data[i].nomeCategoria);
			option.appendChild(optionText);
			comboSottocateg.appendChild(option);
		}
		if(sotto!='')
			$('#sottoCategoriaCodeMod').val(sotto);
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "articolo/selezionaCategoria.action?codice="+codice ;
url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/json", callBackFn, null, callBackFnErr);
}

function caricaDescrizioniProfEst(id) {
	console.log('caricaDescrizioniProfEst: '+id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var nome = document.getElementById("nome");  
		nome.value = checkUndefined(data, "nome")? "": data.getElementsByTagName("nome")[0].firstChild.data;
		var cognome = document.getElementById("cognome");  
		cognome.value = checkUndefined(data, "cognome")? "": data.getElementsByTagName("cognome")[0].firstChild.data;
		var telefono = document.getElementById("telefono");  
		telefono.value = checkUndefined(data, "telefono")? "": data.getElementsByTagName("telefono")[0].firstChild.data;
		var motivazioneRichiesta = document.getElementById("motivazioneRichiesta");  
		motivazioneRichiesta.value = checkUndefined(data, "motivazioneRichiesta")? "": data.getElementsByTagName("motivazioneRichiesta")[0].firstChild.data;
		var fax = document.getElementById("fax");  
		fax.value = checkUndefined(data, "fax")? "": data.getElementsByTagName("fax")[0].firstChild.data;
		var codiceFiscale = document.getElementById("codiceFiscale");  
		codiceFiscale.value = checkUndefined(data, "codiceFiscale")? "": data.getElementsByTagName("codiceFiscale")[0].firstChild.data;
		var tipoProfessionista = document.getElementById("tipoProfessionista");  
		tipoProfessionista.value = checkUndefined(data, "tipoProfessionista")? "": data.getElementsByTagName("tipoProfessionista")[0].firstChild.data;
		var statoEsitoValutazioneProf = document.getElementById("statoEsitoValutazioneProf");  
		statoEsitoValutazioneProf.value = checkUndefined(data, "statoEsitoValutazioneProf")? "": data.getElementsByTagName("statoEsitoValutazioneProf")[0].firstChild.data;
		var statoProfessionista = document.getElementById("statoProfessionista");  
		statoProfessionista.value = checkUndefined(data, "statoProfessionista")? "": data.getElementsByTagName("statoProfessionista")[0].firstChild.data;
		var studioLegale = document.getElementById("studioLegale");  
		studioLegale.value = checkUndefined(data, "studioLegale")? "": data.getElementsByTagName("studioLegale")[0].firstChild.data;
		var listNazioni = data.getElementsByTagName("nazioneDesc");
		if(listNazioni[0] !== undefined){
			for (i = 0; i < listNazioni.length; i++) {
				num_option=document.getElementById('nazioneDesc').options.length; 
				document.getElementById('nazioneDesc').options[num_option]=new Option('',escape(listNazioni[i].firstChild.data),false,false);
				document.getElementById('nazioneDesc').options[num_option].innerHTML = listNazioni[i].firstChild.data;
			}
		}
		var listSpec = data.getElementsByTagName("specDesc");
		if(listSpec[0] !== undefined){
			for (i = 0; i < listSpec.length; i++) {
				num_option=document.getElementById('specDesc').options.length; 
				document.getElementById('specDesc').options[num_option]=new Option('',escape(listSpec[i].firstChild.data),false,false);
				document.getElementById('specDesc').options[num_option].innerHTML = listSpec[i].firstChild.data;
			}
		}
		
		var listEmail = data.getElementsByTagName("email");
		var textemails = document.querySelectorAll("input[name^='email[']");
		textemails[0].value = "";
		if(listEmail[0] !== undefined){
			for (i = 0; i < listEmail.length; i++) {
				if(i == 0){
					textemails[i].value = listEmail[i].firstChild.data;
				} else {
					$('#divEmail').append('<div class="list-group-item media" id="addEmail_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
							'<div class="col-sm-10">'+
							'<input id="email['+ i +']" name="email['+ i +']" class="form-control" type="text" value = "'+ listEmail[i].firstChild.data +'" readonly>'+
						'</div>'+
						'</div></div></div>' );
				}
			}
		}
		
		var listUuid = data.getElementsByTagName("fileUuid");
		var labelDocument = document.getElementById("documentLabel").innerHTML;
		if(listUuid[0] !== undefined){
			$("#divDocument").slideDown(200);
			for (i = 0; i < listUuid.length; i++) {
				if(i == 0){
					$('#firstDocument').append('<button type="button" onclick="downloadDoc(this.id)" id="'+ listUuid[i].firstChild.data +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
							'<span class="glyphicon glyphicon-download-alt"></span>'+
							'</button>');
				} else {
					$('#divDocument').append('<div class="list-group-item media" id="addDocument_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> '+ labelDocument +'</label>'+
							'<div class="col-sm-10">'+
							'<button type="button" onclick="downloadDoc(this.id)" id="'+ listUuid[i].firstChild.data +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
							'<span class="glyphicon glyphicon-download-alt"></span>'+
							'</button>'+
							'</div></div></div></div>' );
				}
			}
		} else {
			$("#divDocument").hide();
		}
		
	};
	var url = WEBAPP_BASE_URL + "professionistaEsterno/caricaDescrizioniProfEst.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	svuotaListe();
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaMailingListEdit(id) {
	console.log('caricaMailingListEdit: '+id);
	var mailingListId = document.getElementById("mailingListId");
	mailingListId.value = id;
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var nome = document.getElementById("nomeMod");  
		nome.value = checkUndefined(data, "nome")? "": data.getElementsByTagName("nome")[0].firstChild.data;
		
		if(data.getElementsByTagName("categoria")[0] !== undefined){
			$('#categoriaCodeMod').val(data.getElementsByTagName("categoria")[0].firstChild.data);
			
			if(data.getElementsByTagName("sottoCategoria")[0] !== undefined)
				selezionaCategoriaMod(data.getElementsByTagName("categoria")[0].firstChild.data, data.getElementsByTagName("sottoCategoria")[0].firstChild.data);
			else
				selezionaCategoriaMod(data.getElementsByTagName("categoria")[0].firstChild.data, '');
		}
		
		
		
		var listNominativi = data.getElementsByTagName("rubricaDesc");
		if(listNominativi[0] !== undefined){
			for (i = 0; i < listNominativi.length; i++) {
				var list = document.getElementsByName("nominativiAggiuntiMod");
				for (a = 0; a < list.length; a++) {
					if(list[a].value == listNominativi[i].firstChild.data){
						list[a].checked = true;
					}
				}
			}
		}
		
		
		
	};
	var url = WEBAPP_BASE_URL + "mailingList/caricaMailingListEdit.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	svuotaListeEdit();
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function alertResponse(data){
	var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
	alert(xmlstr);	
}

function checkUndefined(data, value){
	if(data.getElementsByTagName(value)[0] !== undefined){
		return false;
	} else {
		return true;
	}
}

function svuotaListe(){
	var num_option=document.getElementById('rubricaDesc').options.length;
	for(a=num_option;a>=0;a--){
		document.getElementById('rubricaDesc').options[a]=null;
	}
}

function svuotaListeEdit(){
	document.getElementById("categoriaCode").selectedIndex = "0";
	document.getElementById("sottoCategoriaCode").selectedIndex = "0";
	$("[id^='nominativiAggiunti']").removeAttr('checked');
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
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
	var mailingListId = document.getElementById("mailingListId");
	mailingListId.value = "0";
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="false";
	var editMode = document.getElementById("editMode");
	editMode.value="false";
}

function insertCheck(){
	console.log('insertCheck');
	setInsertMode();
	puliscoCampiEdit();
	
	$(".btnCancella").css("display","none");
	$(".btnSalva").css("display","block");
	
//	$("#tabAttiva").val(1);
}

function puliscoCampiEdit() {
	// pulisco campi Edit
	$("option:selected").prop("selected", false)
	
	var ele = document.getElementById("categoriaCodeMod");
	ele.selectedIndex = 0;
	
	var ele = document.getElementById("sottoCategoriaCodeMod");
	ele.selectedIndex = 0;
	
	$('form[name="mailingListForm"] input[name=nomeMod]').val(''); 
	
	$('input:checkbox').not(this).prop('checked', false);
	
}

function editCheck(){
	console.log('editCheck');
	setEditMode();
	puliscoCampiInsert();
	
	$(".btnCancella").css("display","block");
	$(".btnSalva").css("display","block");
}

function puliscoCampiInsert() {
	$("option:selected").prop("selected", false)
	
	var ele = document.getElementById("categoriaCodeMod");
	ele.selectedIndex = 0;
	
	var ele = document.getElementById("sottoCategoriaCodeMod");
	ele.selectedIndex = 0;
	
	$('form[name="mailingListForm"] input[name=nomeMod]').val(''); 
	
	$('input:checkbox').not(this).prop('checked', false);
	
}


