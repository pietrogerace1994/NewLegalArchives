if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

var ricercaFatta = false;
var arrTabellaParteCorrelata = null;

$( document ).ready(function() {

	$("#filtroListaAutGiudiziaria").focusout(function() {
		var val = $("#filtroListaAutGiudiziaria").val();
		if(val == "") {
			var strOpt = "";
			for(var i=0; i<jsonArrayAutoritaGiudiziariaMod.length; i++) {
				var obj = jsonArrayAutoritaGiudiziariaMod[i];
				var denominazione = obj.denominazione;
				var id = obj.id;
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			}
			$("#autoritaGiudiziariaIdMod").html("");
			$("#autoritaGiudiziariaIdMod").html(strOpt);
		}
	});
	
	$("#filtroListaAutGiudiziaria").keyup(function(event) {
		var indici = new Array();
		var val = $("#filtroListaAutGiudiziaria").val() ;
		var str = '<option value="0">Loading ...</option>';
		$("#autoritaGiudiziariaIdMod").html("");
		$("#autoritaGiudiziariaIdMod").html(str);
		
		// cerco
		for(var i=0; i< jsonArrayAutoritaGiudiziariaMod.length; i++) {
			var obj = jsonArrayAutoritaGiudiziariaMod[i];
			var denominazione = obj.denominazione;
			denominazione = denominazione.toLowerCase();
			val = val.toLowerCase();
			
			var start = false;
			
			var split = denominazione.split(' ');
			
			for(var j=0; j<split.length; j++){
				
				if(split[j].startsWith(val)){
					start = true;
				}
			}
			
			
			if(start) {
				indici.push(i);
			}
		}
		
		if(indici.length==0) {
			var str = '<option value="0" style="color:red">Nessuna Richiesta</option>';
			$("#autoritaGiudiziariaIdMod").html("");
			$("#autoritaGiudiziariaIdMod").html(str);
		}
		else if(indici.length > 0) {
			var strOpt = "";
			for(var i=0; i<indici.length; i++) {
				var x = indici[i];
				var obj = jsonArrayAutoritaGiudiziariaMod[x];
				var denominazione = obj.denominazione;
				var id = obj.id;
				
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			} //endfor
			
			$("#autoritaGiudiziariaIdMod").html("");
			$("#autoritaGiudiziariaIdMod").html(strOpt);
			indici = [];
		}
	});
	
	var idAutGiud = document.getElementById("autoritaGiudiziariaIdMod").value;
	if($("#editMode").val() == "true" && idAutGiud != ""){
		caricaRichiestaAutGiudMod(idAutGiud);
	}

	if($("#insertMode").val() == "true"){
		puliscoCampiInsert();
	}
	
});

function setDeleteMode() {
	document.getElementById("insertMode").value = "false";
	document.getElementById("deleteMode").value = "true";
	document.getElementById("editMode").value = "false";
}

function setInsertMode() {
	document.getElementById("insertMode").value = "true";
	document.getElementById("deleteMode").value = "false";
	document.getElementById("editMode").value = "false";
}
function setEditMode() {
	document.getElementById("insertMode").value = "false";
	document.getElementById("deleteMode").value = "false";
	document.getElementById("editMode").value = "true";
}

function puliscoAutocompletePcMod() {
	// pulisco autocomplete
	$("#filtroListaAutGiudiziaria").val('');
	
	var strOpt = "";
	for(var i=0; i< jsonArrayAutoritaGiudiziariaMod.length; i++) {
		var obj = jsonArrayAutoritaGiudiziariaMod[i];
		var denominazione = obj.denominazione;
		var id=obj.id;
		var str = '<option value="'+id+'">'+denominazione+'</option>';
		strOpt += str;
	}
	$("#autoritaGiudiziariaIdMod").html("");
	$("#autoritaGiudiziariaIdMod").html(strOpt);
}

function puliscoCampiInsert() {
	// pulisco campi Input
	document.getElementById("comboTipoRichiesta").selectedIndex = 0;
	document.getElementById("comboStatoRichiesta").selectedIndex = 0;
	document.getElementById("comboSocieta").selectedIndex = 0;
	
	$('form[name="autoritaGiudiziariaForm"] input[name=autoritaGiudiziaria]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=dataInserimento]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=dataRicezione]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=oggetto]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=fornitore]').val(''); 
}

function puliscoCampiEdit() {
	document.getElementById("comboTipoRichiestaMod").selectedIndex = 0;
	document.getElementById("comboStatoRichiestaMod").selectedIndex = 0;
	document.getElementById("comboSocietaMod").selectedIndex = 0;
	
	$('form[name="autoritaGiudiziariaForm"] input[name=autoritaGiudiziariaMod]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=dataInserimentoMod]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=dataRicezioneMod]').val(''); 
	$('form[name="autoritaGiudiziariaForm"] input[name=oggettoMod]').val('');
	$('form[name="autoritaGiudiziariaForm"] input[name=fornitoreMod]').val(''); 
	$("#step1Attach").css("display","block");
	$("#step2AttachObj1").css("display","none");
	$("#step2AttachObj2").css("display","none");
	$("#step3AttachObj1").css("display","none");
	$("#step3AttachObj2").css("display","none");
	$("#step4AttachObj").css("display","none");
	
	document.getElementById("autoritaGiudiziariaIdMod").selectedIndex = -1
}

function insertCheck(){
	console.log('insertCheck');
	
	setInsertMode();
	puliscoCampiInsert();
	puliscoCampiEdit();
	
	$(".btnCancella").css("display","none");
	$(".btnSalva").css("display","block");
}

function editCheck(){
	console.log('editCheck');
	var autoritaGiudiziariaId = document.getElementById("autoritaGiudiziariaId");
	autoritaGiudiziariaId.value = null;
	
	setEditMode();
	puliscoCampiInsert();
	puliscoCampiEdit();
	
	$(".btnCancella").css("display","block");
	$(".btnSalva").css("display","block");
}

function selezionaTipologiaRichiestaMod(tipologiaRichiestaId) {
	var ele = document.getElementById("comboTipoRichiestaMod");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == tipologiaRichiestaId) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function selezionaSocietaMod(idSocieta) {
	var ele = document.getElementById("comboSocietaMod");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == idSocieta) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function selezionaStatoRichiesta(statoRichAutGiudId) {
	var ele = document.getElementById("comboStatoRichiesta");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == statoRichAutGiudId) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function selezionaStatoRichiestaMod(statoRichAutGiudId) {
	var ele = document.getElementById("comboStatoRichiestaMod");
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == statoRichAutGiudId) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function caricaRichiestaAutGiudMod(idRichiestaAutGiud) {
	console.log('caricaRichiestaAutGiudMod: '+idRichiestaAutGiud);
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		if(data){
			$('form[name="autoritaGiudiziariaForm"] input[name=autoritaGiudiziariaMod]').val(data.autoritaGiudiziaria);
			$('form[name="autoritaGiudiziariaForm"] input[name=dataInserimentoMod]').val(data.dataInserimento);
			$('form[name="autoritaGiudiziariaForm"] input[name=dataRicezioneMod]').val(data.dataRicezione);
			$('form[name="autoritaGiudiziariaForm"] input[name=oggettoMod]').val(data.oggetto);
			$('form[name="autoritaGiudiziariaForm"] input[name=fornitoreMod]').val(data.fornitore);
			
			selezionaTipologiaRichiestaMod(data.tipologiaRichiestaId);
			selezionaStatoRichiestaMod(data.statoRichAutGiudId);
			selezionaSocietaMod(data.idSocieta);
			
			// document.getElementById("test").style.display="none";
			if(idRichiestaAutGiud && data.nomeFileStep1 && data.statoCodGruppoLingua === 'ST_RIC_1'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#step2AttachObj2").css("display","block");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);
			}else if(idRichiestaAutGiud && data.nomeFileStep1 && data.nomeFileStep2 && data.statoCodGruppoLingua === 'ST_RIC_2'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#step2AttachObj2").css("display","none");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);
				
				$("#step3AttachObj1").css("display","block");
				$("#step3AttachObj2").css("display","block");
				$("#step2FileName").html(data.nomeFileStep2);
				$("#hrefDocumentoStep2").attr("href",$("#hrefDocumentoStep2").attr("href")+data.idFileStep2);
			}else if(idRichiestaAutGiud && data.nomeFileStep1 && data.nomeFileStep2 && data.nomeFileStep3 
					&& data.statoCodGruppoLingua === 'ST_RIC_3'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#step2AttachObj2").css("display","none");
				$("#nomeFile").html(data.nomeFileStep1);
				
				$("#step3AttachObj1").css("display","block");
				$("#step3AttachObj2").css("display","none");
				$("#step2FileName").html(data.nomeFileStep2);
				
				$("#step4AttachObj").css("display","block");
				$("#step3FileName").html(data.nomeFileStep3);

				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);
				$("#hrefDocumentoStep2").attr("href",$("#hrefDocumentoStep2").attr("href")+data.idFileStep2);
				$("#hrefDocumentoStep3").attr("href",$("#hrefDocumentoStep3").attr("href")+data.idFileStep3);

			}else{
				$("#step1Attach").css("display","block");
				$("#step2AttachObj1").css("display","none");
				$("#step2AttachObj2").css("display","none");
				$("#step3AttachObj1").css("display","none");
				$("#step3AttachObj2").css("display","none");
				$("#step4AttachObj").css("display","none");
			}
			
			$(".btnCancella").css("display","block");
			$(".btnSalva").css("display","block");
		}
	};
	
	var url = WEBAPP_BASE_URL + "autoritaGiudiziaria/loadRichiestaAutGiudiziaria.action?id="+idRichiestaAutGiud;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/json", callBackFn, null);
}

function cancellaAutoritaGiudiziaria(){
	console.log('cancella Autorita Giudiziaria');
	var form = document.getElementById("autoritaGiudiziariaView");
	var op = document.getElementById("op");
	op.value="salvaAutoritaGiudiziaria";
	setDeleteMode();
	form.submit(); 
}

function salvaAutoritaGiudiziaria(){
	console.log('salva Autorita Giudiziaria');
	var form = document.getElementById("autoritaGiudiziariaView"); 
	var op = document.getElementById("op");
	op.value="salvaAutoritaGiudiziaria";
	
	form.submit();
}


