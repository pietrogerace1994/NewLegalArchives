if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

$( document ).ready(function() {

	$("#filtroListaDueDiligence").focusout(function() {
		var val = $("#filtroListaDueDiligence").val();
		if(val == "") {
			var strOpt = "";
			for(var i=0; i<jsonArrayDueDiligenceMod.length; i++) {
				var obj = jsonArrayDueDiligenceMod[i];
				var denominazione = obj.denominazione;
				var id = obj.id;
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			}
			$("#dueDiligenceIdMod").html("");
			$("#dueDiligenceIdMod").html(strOpt);
		}
	});
	
	$("#filtroListaDueDiligence").keyup(function(event) {
		var indici = new Array();
		var val = $("#filtroListaDueDiligence").val() ;
		var str = '<option value="0">Loading ...</option>';
		$("#dueDiligenceIdMod").html("");
		$("#dueDiligenceIdMod").html(str);
		
		// cerco
		for(var i=0; i< jsonArrayDueDiligenceMod.length; i++) {
			var obj = jsonArrayDueDiligenceMod[i];
			var denominazione = obj.denominazione;
			denominazione = denominazione.toLowerCase();
			val = val.toLowerCase();
			
			var n = denominazione.startsWith(val);
			if(n) {
				indici.push(i);
			}
		}
		
		if(indici.length==0) {
			var str = '<option value="0" style="color:red">Nessuna Verifica Presente</option>';
			$("#dueDiligenceIdMod").html("");
			$("#dueDiligenceIdMod").html(str);
		}
		else if(indici.length > 0) {
			var strOpt = "";
			for(var i=0; i<indici.length; i++) {
				var x = indici[i];
				var obj = jsonArrayDueDiligenceMod[x];
				var denominazione = obj.denominazione;
				var id = obj.id;
				
				var str = '<option value="'+id+'">'+denominazione+'</option>';
				strOpt += str;
			} //endfor
			
			$("#dueDiligenceIdMod").html("");
			$("#dueDiligenceIdMod").html(strOpt);
			indici = [];
		}
	});
	
	var idDueDil = document.getElementById("dueDiligenceIdMod").value;
	if($("#editMode").val() == "true" && idDueDil != ""){
		caricaDueDiligenceMod(idDueDil);
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
	$("#filtroListaDueDiligence").val('');
	
	var strOpt = "";
	for(var i=0; i< jsonArrayDueDiligenceMod.length; i++) {
		var obj = jsonArrayDueDiligenceMod[i];
		var denominazione = obj.denominazione;
		var id=obj.id;
		var str = '<option value="'+id+'">'+denominazione+'</option>';
		strOpt += str;
	}
	$("#dueDiligenceIdMod").html("");
	$("#dueDiligenceIdMod").html(strOpt);
}

function puliscoCampiInsert() {
	// pulisco campi Input
	document.getElementById("comboStatoDueDiligence").selectedIndex = 0;
	document.getElementById("comboProfessionista").selectedIndex = 0;
	
	$('form[name="duediligenceForm"] input[name=dataApertura]').val(''); 
	$('form[name="duediligenceForm"] input[name=dataChiusura]').val(''); 
}

function puliscoCampiEdit() {
	document.getElementById("comboProfessionistaMod").selectedIndex = 0;
	document.getElementById("comboStatoDueDiligenceMod").selectedIndex = 0;
	
	$('form[name="duediligenceForm"] input[name=dataAperturaMod]').val(''); 
	$('form[name="duediligenceForm"] input[name=dataChiusuraMod]').val(''); 
	
	$("#step1Attach").css("display","block");
	$("#step2AttachObj1").css("display","none");
	$("#step2AttachObj2").css("display","none");
	$("#step2AttachObj3").css("display","none");
	$("#step3AttachObj").css("display","none");
	$("#step4AttachObj").css("display","none");
	
	document.getElementById("dueDiligenceIdMod").selectedIndex = -1
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
	var dueDiligenceId = document.getElementById("dueDiligenceId");
	dueDiligenceId.value = null;
	
	setEditMode();
	puliscoCampiInsert();
	puliscoCampiEdit();
	
	$(".btnCancella").css("display","block");
	$(".btnSalva").css("display","block");
}

function selezionaComboMod(idInput, comboName) {
	var ele = document.getElementById(comboName);
	var option = null;
	var indiceFor = -1;
	var options = ele.childNodes;
	for (i = 0; i < options.length; i++) {
		if (options[i].value == idInput) {
			option = options[i];
			indiceFor = i;
			break;
		}
	}

	if(indiceFor > 0) {
		ele.selectedIndex = options[indiceFor].index;
	}
}

function buildAndShowDocumentList(data, checkBoxDisabled) {
	var strOpt = "";
	for (i = 0; i < data.fileStep2List.length; i++) {
		strOpt += "<tr>";
		
		strOpt += "<td>"+data.fileStep2List[i].nomeFile+"</td>";
		
//		strOpt += "<td>"+data.fileStep2List[i].nomeFile+"<a href='/NewLegalArchives/download?onlyfn=0&isp=0&uuid="
//							+ data.fileStep2List[i].idDocumento
//							+ "' class='bg   waves-effect waves-circle waves-float btn-circle-mini' style='background-color: #d9d9d9;' target='_BLANK'>"
//							+ "<i class='zmdi zmdi-download icon-mini'></i></a></td>";
//		
		if(checkBoxDisabled){
			strOpt += '<td><input type="checkbox" value="'+data.fileStep2List[i].id+'" name="documentiVerificaToDelete" id="documentiVerificaToDelete'+i+'" disabled="disabled"/></td>';
		}else{
			strOpt += '<td><input type="checkbox" value="'+data.fileStep2List[i].id+'" name="documentiVerificaToDelete" id="documentiVerificaToDelete'+i+'"/></td>';
		}
		
		strOpt += "</tr>";
	}
	$("#boxDocumentiVerifica").html("");
	$("#boxDocumentiVerifica").html(strOpt);
}

function caricaDueDiligenceMod(idDueDiligence) {
	console.log('caricaDueDiligenceMod: '+idDueDiligence);
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		if(data){
			$('form[name="duediligenceForm"] input[name=dataAperturaMod]').val(data.dataApertura);
			$('form[name="duediligenceForm"] input[name=dataChiusuraMod]').val(data.dataChiusura);
			
			selezionaComboMod(data.professionistaId,   "comboProfessionistaMod");
			selezionaComboMod(data.statoDueDiligenceId, "comboStatoDueDiligenceMod");
			
			if(idDueDiligence && data.nomeFileStep1 && (!data.fileStep2List || data.fileStep2List.length <= 0)
					&& data.statoCodGruppoLingua === 'ST_DUE_1'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#step2AttachObj2").css("display","block");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);

			}else if(idDueDiligence && data.nomeFileStep1 && data.fileStep2List && data.fileStep2List.length > 0 
					&& data.statoCodGruppoLingua === 'ST_DUE_1'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);

				
				$("#step2AttachObj2").css("display","block"); // scelta file step2 sempre visibile
				$("#step2AttachObj3").css("display","block");
				if(data.fileStep2List && data.fileStep2List.length > 0){
					buildAndShowDocumentList(data, false);
				}
				
				$("#step3AttachObj").css("display","none");
				
			}else if(idDueDiligence && data.nomeFileStep1 && data.fileStep2List && data.fileStep2List.length > 0
					&& data.statoCodGruppoLingua === 'ST_DUE_2'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);
				
				$("#step2AttachObj2").css("display","none");
				$("#step2AttachObj3").css("display","block");
				$("#step2AttachObj3").css("display","block");
				if(data.fileStep2List && data.fileStep2List.length > 0){
					buildAndShowDocumentList(data, true);
				}
				
				$("#step3AttachObj").css("display","block");
				
			}else if(idDueDiligence && data.nomeFileStep1 && data.fileStep2List && data.fileStep2List.length > 0
					&& data.nomeFileStep3 && data.statoCodGruppoLingua === 'ST_DUE_3'){
				$("#step1Attach").css("display","none");
				
				$("#step2AttachObj1").css("display","block");
				$("#nomeFile").html(data.nomeFileStep1);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep1").attr("href")+data.idFileStep1);
				$("#step2AttachObj2").css("display","none");
				$("#step2AttachObj3").css("display","block");
				if(data.fileStep2List && data.fileStep2List.length > 0){
					buildAndShowDocumentList(data, true);
				}
				
				$("#step3AttachObj").css("display","none");
				$("#step4AttachObj").css("display","block");
				$("#step3FileName").html(data.nomeFileStep3);
				$("#hrefDocumentoStep1").attr("href",$("#hrefDocumentoStep3").attr("href")+data.idFileStep1);
			}else{
				$("#step1Attach").css("display","block");
				$("#step2AttachObj1").css("display","none");
				$("#step2AttachObj2").css("display","none");
				$("#step2AttachObj3").css("display","none");
				$("#step3AttachObj").css("display","none");
				$("#step4AttachObj").css("display","none");
			}
			
			$(".btnCancella").css("display","block");
			$(".btnSalva").css("display","block");
		}
	};
	
	var url = WEBAPP_BASE_URL + "duediligence/loadDueDiligence.action?id="+idDueDiligence;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/json", callBackFn, null);
}

function cancellaDueDiligence(){
	console.log('cancella Autorita Giudiziaria');
	var form = document.getElementById("dueDiligenceView");
	var op = document.getElementById("op");
	op.value="salvaDueDiligence";
	setDeleteMode();
	form.submit(); 
}

function salvaDueDiligence(){
	console.log('salva Autorita Giudiziaria');
	var form = document.getElementById("dueDiligenceView"); 
	var op = document.getElementById("op");
	op.value="salvaDueDiligence";
	
	form.submit();
}


