function salvaMateria(){
	console.log('salva materia');
	var form = document.getElementById("materiaView"); 
	var op = document.getElementById("op");
	op.value="salvaMateria";
	form.submit(); 
}

function cancellaMateria(){
	console.log('cancella materia');
	var form = document.getElementById("materiaView"); 
	var op = document.getElementById("op");
	op.value="salvaMateria";
	setDeleteMode();
	form.submit(); 
}

function caricaDettaglioMateria(codGruppoLingua) {
	console.log('caricaDettaglioMateria: '+codGruppoLingua);
	setEditMode();
	if(codGruppoLingua == "0") {
		deselezionaListaSottoMateria();
	}

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var nome = data.getElementsByTagName("nome");
		if (nome["0"] != null) {
			nome = nome["0"].textContent;
		} 
		else {
			nome = "";
		}
		
		var idSottoMateria = data.getElementsByTagName("idSottoMateria");
		if (idSottoMateria["0"] != null) {
			idSottoMateria = idSottoMateria["0"].textContent;
		} 
		else {
			idSottoMateria = "";
		}
		
		$('form[name="materiaEditForm"] input[name=nome]').first().val(nome); 
		selezionaListaSottoMateria(idSottoMateria);
		selezionaLingua( $('form[name="materiaEditForm"] input[name="localeStr"]').val() );
	};
    var localeStr = $('form[name="materiaEditForm"] input[name="localeStr"]').val();
	var url = WEBAPP_BASE_URL + "materia/caricaDettaglioMateria.action?codGruppoLingua="+codGruppoLingua+"&localeStr="+localeStr;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function editCheck(){
	console.log('editCheck');
	setEditMode();
	// cancella campi Insert
	$('form[name="materiaEditForm"] select[name=settoreGiuridicoIdIns] option').remove();
//	$('form[name="materiaEditForm"] select[name=tipologiaFascicoloIdIns] option').remove();
	var ele = document.getElementById("tipologiaFascicoloIdIns");
	ele.selectedIndex = 0;
	
	$('input[name^="nomeIns"]').each(function() {
	    $(this).val("");
	});
	
	$('#treeContainerMateriePadre .easytree-container').html("");
	
}

function insertCheck(){
	console.log('insertCheck');
	setInsertMode();
	 
	// cancella campi Edit
	var ele = document.getElementById("tipologiaFascicoloId");
	ele.selectedIndex = 0;
	
	$('form[name="materiaEditForm"] select[name=settoreGiuridicoId] option').remove();

	$('input[name^="nome["]').each(function() {
	    $(this).val("");
	});
//	
	$('#treeContainerMaterie .easytree-container').html("");
}


function selezionaListaSottoMateria(valueStr) {
	var ele = document.getElementById("idSottoMateria");
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

function selezionaLingua(valueStr) {
	var ele = document.getElementById("lingua");
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

function deselezionaListaMateria() {
	var ele = document.getElementById("idMateria");
	ele.selectedIndex = 0;
}

function deselezionaListaSottoMateria() {
	var ele = document.getElementById("idSottoMateria");
	ele.selectedIndex = 0;
}

function deselezionaListaSottoMateriaInsert() {
	var ele = document.getElementById("idSottoMateriaIns");
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

function caricaMateriaByLingua(codiceLingua) {
		console.log('caricaMateriaByLingua: '+codiceLingua);
		setEditMode();
		var codMateria=$('form[name="materiaEditForm"] select[name=codMateria]').val(); 

		var ajaxUtil = new AjaxUtil();
		var callBackFn = function(data, stato) {
			
			var nome = data.getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			var idSottoMateria = data.getElementsByTagName("idSottoMateria");
			if (idSottoMateria["0"] != null) {
				idSottoMateria = idSottoMateria["0"].textContent;
			} 
			else {
				idSottoMateria = "";
			}
			
			$('form[name="materiaEditForm"] input[name=nome]').first().val(nome); 
			selezionaListaSottoMateria(idSottoMateria);
		};
		var url = WEBAPP_BASE_URL + "materia/caricaMateriaByLingua.action?codiceLingua="+codiceLingua+"&codGruppoLingua="+codMateria;
		url=legalSecurity.verifyToken(url);
		ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function deselezionaListaLingua() {
	var ele = document.getElementById("lingua");
	ele.selectedIndex = 0;
}

//-------------------------------------- VISUALIZZAZIONE -------------------------

function caricaDettaglioMateriaVisualizzazione(id) {
	console.log('caricaDettaglioMateriaVisualizzazione: '+id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var nome = data.getElementsByTagName("nome");
		if (nome["0"] != null) {
			nome = nome["0"].textContent;
		} 
		else {
			nome = "";
		}
		
		var idSottoMateria = data.getElementsByTagName("idSottoMateria");
		if (idSottoMateria["0"] != null) {
			idSottoMateria = idSottoMateria["0"].textContent;
		} 
		else {
			idSottoMateria = "";
		}
		
		var sottoMateriaNome = data.getElementsByTagName("sottoMateriaNome");
		if (sottoMateriaNome["0"] != null) {
			sottoMateriaNome = sottoMateriaNome["0"].textContent;
		} 
		else {
			sottoMateriaNome = "";
		}
		
		$('form[name="materiaReadForm"] input[name=nome]').first().val(nome); 
		$('form[name="materiaReadForm"] input[name=sottoMateriaNome]').first().val(sottoMateriaNome); 
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaDettaglioMateria.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

// ----ALBERO MATERIA-----------------------------------------------------------------------------------------------
var jsonDataMaterie; 
var materieDaSelezionare = new Array();
//materieDaSelezionare.push("${materiaCodice}");

function selezionaMaterie() {
	if (materieDaSelezionare) {
		for (i = 0; i < materieDaSelezionare.length; i++) {
			document.getElementById(materieDaSelezionare[i]).checked = true;
		}
	}
}



function scegliMateria( codGruppoLingua, nome  ) {
	console.log( 'scegliMateria ' +codGruppoLingua+" "+nome);
	$("#materiaCodGruppoLingua").val(codGruppoLingua);
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var vos = data.getElementsByTagName("vos");
		if(vos.length > 0) {
			
		}
			
		for (i = 0; i < vos.length; i++) {
					
			var id = vos[i].getElementsByTagName("id");
			if (id["0"] != null) {
				id = id["0"].textContent;
			} 
			else {
				id = "";
			}
			
			var nome = vos[i].getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			var codGruppoLingua = vos[i].getElementsByTagName("codGruppoLingua");
			if (codGruppoLingua["0"] != null) {
				codGruppoLingua = codGruppoLingua["0"].textContent;
			} 
			else {
				codGruppoLingua = "";
			}
			
			var lang = vos[i].getElementsByTagName("lang");
			if (lang["0"] != null) {
				lang = lang["0"].textContent;
			} 
			else {
				lang = "";
			}
			
			var idLingua;
			var lingua;
			for(var x=0; x<lingueDisponibili.length; x++) {
				lingua =  lingueDisponibili[x];
				if( lingua.lang == lang )
					break;
			}
			if( x < lingueDisponibili.length)
				idLingua = lingua.id;
			
			var idPadre = vos[i].getElementsByTagName("idMateriaPadre");
			if (idPadre["0"] != null) {
				idPadre = idPadre["0"].textContent;
			} 
			else {
				idPadre = "";
			}
			
			$('form[name="materiaEditForm"] input[name="nome['+idLingua+']"]').val(nome); 
			$('form[name="materiaEditForm"] input[name="materiaNomeId['+idLingua+']"]').val(id); 
			$('form[name="materiaEditForm"] input[name="materiaIdPadre['+idLingua+']"]').val(idPadre); 
		}
		
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaMateriaNome.action?codGruppoLingua="+codGruppoLingua;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}



function scegliMateriaVis( codGruppoLingua, nome  ) {
	console.log( 'scegliMateriaVis ' +codGruppoLingua+" "+nome);
	$("#materiaCodGruppoLingua").val(codGruppoLingua);
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var vos = data.getElementsByTagName("vos");
		if(vos.length > 0) {
			
		}
			
		for (i = 0; i < vos.length; i++) {
					
			var id = vos[i].getElementsByTagName("id");
			if (id["0"] != null) {
				id = id["0"].textContent;
			} 
			else {
				id = "";
			}
			
			var nome = vos[i].getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			var codGruppoLingua = vos[i].getElementsByTagName("codGruppoLingua");
			if (codGruppoLingua["0"] != null) {
				codGruppoLingua = codGruppoLingua["0"].textContent;
			} 
			else {
				codGruppoLingua = "";
			}
			
			var lang = vos[i].getElementsByTagName("lang");
			if (lang["0"] != null) {
				lang = lang["0"].textContent;
			} 
			else {
				lang = "";
			}
			
			var idLingua;
			var lingua;
			for(var x=0; x<lingueDisponibili.length; x++) {
				lingua =  lingueDisponibili[x];
				if( lingua.lang == lang )
					break;
			}
			if( x < lingueDisponibili.length)
				idLingua = lingua.id;
			
			var idPadre = vos[i].getElementsByTagName("idMateriaPadre");
			if (idPadre["0"] != null) {
				idPadre = idPadre["0"].textContent;
			} 
			else {
				idPadre = "";
			}
			
			$('form[name="materiaReadForm"] input[name="nome['+idLingua+']"]').val(nome); 
			$('form[name="materiaReadForm"] input[name="materiaNomeId['+idLingua+']"]').val(id); 
			$('form[name="materiaReadForm"] input[name="materiaIdPadre['+idLingua+']"]').val(idPadre); 
		}
		
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaMateriaNome.action?codGruppoLingua="+codGruppoLingua;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function scegliMateriaPadre( codGruppoLingua, nome  ) {
	console.log( 'scegliMateriaPadre ' +codGruppoLingua+" "+nome);
	$("#materiaPadreCodGruppoLinguaIns").val(codGruppoLingua);
}

function treeStateChanged(nodes, nodesJson) {
	console.log('treeStateChanged');
}

function caricaAlberaturaMaterie(settoreGiuridicoId) {
	console.log('caricaAlberaturaMaterie: ' + settoreGiuridicoId);
	setInsertMode();
	setSettoreGiuridicoCodGruppoLinguaCorrente(settoreGiuridicoId);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var jsonAlberaturaMaterie = data.getElementsByTagName("jsonAlberaturaMaterie");
		if (jsonAlberaturaMaterie["0"] != null) {
			jsonAlberaturaMaterie = jsonAlberaturaMaterie["0"].textContent;
		} 
		else {
			jsonAlberaturaMaterie = "";
		}
		
		var json = JSON.parse(jsonAlberaturaMaterie);
		$('#treeContainerMateriePadre').easytree({
            data: json,
            stateChanged: treeStateChanged
        });
		
		waitingDialog.hide(); 
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaAlberaturaMaterie.action?settoreGiuridicoId="+settoreGiuridicoId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAlberaturaMaterieEdit(settoreGiuridicoId) {
	console.log('caricaAlberaturaMaterieEdit: ' + settoreGiuridicoId);
	setInsertMode();
	setSettoreGiuridicoCodGruppoLinguaCorrente(settoreGiuridicoId);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var jsonAlberaturaMaterie = data.getElementsByTagName("jsonAlberaturaMaterie");
		if (jsonAlberaturaMaterie["0"] != null) {
			jsonAlberaturaMaterie = jsonAlberaturaMaterie["0"].textContent;
		} 
		else {
			jsonAlberaturaMaterie = "";
		}
		
		var json = JSON.parse(jsonAlberaturaMaterie);
		$('#treeContainerMaterie').easytree({
            data: json,
            stateChanged: treeStateChanged
        });
		
		
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "materia/caricaAlberaturaMaterieEdit.action?settoreGiuridicoId="+settoreGiuridicoId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAlberaturaMaterieVis(settoreGiuridicoId) {
	console.log('caricaAlberaturaMaterieVis: ' + settoreGiuridicoId);
	waitingDialog.show('Loading...');
	setSettoreGiuridicoCodGruppoLinguaCorrente(settoreGiuridicoId);
	if(settoreGiuridicoId == 0) {
		$('#treeContainerMaterie .easytree-container').html("");
		return;
	}
		

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var jsonAlberaturaMaterie = data.getElementsByTagName("jsonAlberaturaMaterie");
		if (jsonAlberaturaMaterie["0"] != null) {
			jsonAlberaturaMaterie = jsonAlberaturaMaterie["0"].textContent;
		} 
		else {s
			jsonAlberaturaMaterie = "";
		}
		
		var json = JSON.parse(jsonAlberaturaMaterie);
		$('#treeContainerMaterie').easytree({
            data: json,
            stateChanged: treeStateChanged
        });
		
		
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "materia/caricaAlberaturaMaterieVis.action?settoreGiuridicoId="+settoreGiuridicoId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function setSettoreGiuridicoCodGruppoLinguaCorrente(settoreGiuridicoId) {
	
	var settoreGiuridicoObj = null;
	for(var i=0; i< settoreGiuridicoArr.length; i++ ) {
		 var settoreGiuridicoObj = settoreGiuridicoArr[i];
		 if(settoreGiuridicoObj.id == settoreGiuridicoId)
			 break;
	}
	
	if(i<settoreGiuridicoArr.length) {
		$("#settoreGiuridicoCodGruppoLingua").val(settoreGiuridicoObj.codGruppoLingua);
	}
		
}

//----------------------------------------------------------------------------------------------------------
var settoreGiuridicoArr = null;
function caricaSettoreGiuridico(tipologiaFascicoloId) {
	console.log('caricaSettoreGiuridico: ' + tipologiaFascicoloId);
	setInsertMode();
	
	// pulisco campi insert
	var ele = document.getElementById("tipologiaFascicoloId");
	ele.selectedIndex = 0;
	
	$('form[name="materiaEditForm"] select[name=settoreGiuridicoId] option').remove();

	$('input[name^="nome"]').each(function() {
	    $(this).val("");
	});
	
	$('#treeContainerMaterie .easytree-container').html("");
	
	if(tipologiaFascicoloId == "0") {
		return;
	}
	
	
	
	$('form[name="materiaEditForm"] select[name=settoreGiuridicoIdIns] option').remove();
	if(tipologiaFascicoloId == "0") {
        
	}

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var listaSettoreGiuridico = data.getElementsByTagName("listaSettoreGiuridico");
		if(listaSettoreGiuridico.length > 0) {
			$('form[name="materiaEditForm"] select[name=settoreGiuridicoIdIns]').append('<option value="0">Nessuna selezione ...</option>' );
		}
			
		settoreGiuridicoArr = new Array();
		for (i = 0; i < listaSettoreGiuridico.length; i++) {
			
			var settoreGiuridico = listaSettoreGiuridico[i];
			var vo = settoreGiuridico.getElementsByTagName("vo");
			
			var codGruppoLingua = vo["0"].getElementsByTagName("codGruppoLingua");
			if (codGruppoLingua["0"] != null) {
				codGruppoLingua = codGruppoLingua["0"].textContent;
			} 
			else {
				codGruppoLingua = "";
			}
			
			var id = vo["0"].getElementsByTagName("id");
			if (id["0"] != null) {
				id = id["0"].textContent;
			} 
			else {
				id = "";
			}
			
			var lang = vo["0"].getElementsByTagName("lang");
			if (lang["0"] != null) {
				lang = lang["0"].textContent;
			} 
			else {
				lang = "";
			}
			
			var nome = vo["0"].getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			$('form[name="materiaEditForm"] select[name=settoreGiuridicoIdIns]').append('<option value="'+id+'">'+nome+'</option>' );
			
			var settoreGiuridicoObj = new Object();
			settoreGiuridicoObj.id=id;
			settoreGiuridicoObj.lang=lang;
			settoreGiuridicoObj.nome=nome;
			settoreGiuridicoObj.codGruppoLingua=codGruppoLingua; 	
			settoreGiuridicoArr.push(settoreGiuridicoObj);
		}
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaSettoreGiuridico.action?tipologiaFascicoloId="+tipologiaFascicoloId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaSettoreGiuridicoEdit(tipologiaFascicoloId) {
	console.log('caricaSettoreGiuridicoEdit: ' + tipologiaFascicoloId);
	setInsertMode();
	$('form[name="materiaEditForm"] select[name=settoreGiuridicoId] option').remove();

	$('input[name^="nome"]').each(function() {
	    $(this).val("");
	});
	
	$('#treeContainerMaterie .easytree-container').html("");
	
	if(tipologiaFascicoloId == "0") {
		return;
	}
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var listaSettoreGiuridico = data.getElementsByTagName("listaSettoreGiuridico");
		if(listaSettoreGiuridico.length > 0) {
			$('form[name="materiaEditForm"] select[name=settoreGiuridicoId]').append('<option value="0">Nessuna selezione ...</option>' );
		}
			
		settoreGiuridicoArr = new Array();
		for (i = 0; i < listaSettoreGiuridico.length; i++) {
			
			var settoreGiuridico = listaSettoreGiuridico[i];
			var vo = settoreGiuridico.getElementsByTagName("vo");
			
			var codGruppoLingua = vo["0"].getElementsByTagName("codGruppoLingua");
			if (codGruppoLingua["0"] != null) {
				codGruppoLingua = codGruppoLingua["0"].textContent;
			} 
			else {
				codGruppoLingua = "";
			}
			
			var id = vo["0"].getElementsByTagName("id");
			if (id["0"] != null) {
				id = id["0"].textContent;
			} 
			else {
				id = "";
			}
			
			var lang = vo["0"].getElementsByTagName("lang");
			if (lang["0"] != null) {
				lang = lang["0"].textContent;
			} 
			else {
				lang = "";
			}
			
			var nome = vo["0"].getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			$('form[name="materiaEditForm"] select[name=settoreGiuridicoId]').append('<option value="'+id+'">'+nome+'</option>' );
			
			var settoreGiuridicoObj = new Object();
			settoreGiuridicoObj.id=id;
			settoreGiuridicoObj.lang=lang;
			settoreGiuridicoObj.nome=nome;
			settoreGiuridicoObj.codGruppoLingua=codGruppoLingua; 	
			settoreGiuridicoArr.push(settoreGiuridicoObj);
		}
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaSettoreGiuridico.action?tipologiaFascicoloId="+tipologiaFascicoloId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function caricaSettoreGiuridicoVis(tipologiaFascicoloId) {
	console.log('caricaSettoreGiuridicoVis: ' + tipologiaFascicoloId);
	$('form[name="materiaReadForm"] select[name=settoreGiuridicoId] option').remove();
	
	$('input[name^="nome"]').each(function() {
	    $(this).val("");
	});
	
	$('#treeContainerMaterie .easytree-container').html("");
	
	if(tipologiaFascicoloId == "0") {
		return;
	}

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		var listaSettoreGiuridico = data.getElementsByTagName("listaSettoreGiuridico");
		if(listaSettoreGiuridico.length > 0) {
			$('form[name="materiaReadForm"] select[name=settoreGiuridicoId]').append('<option value="0">Nessuna selezione ...</option>' );
		}
			
		settoreGiuridicoArr = new Array();
		for (i = 0; i < listaSettoreGiuridico.length; i++) {
			
			var settoreGiuridico = listaSettoreGiuridico[i];
			var vo = settoreGiuridico.getElementsByTagName("vo");
			
			var codGruppoLingua = vo["0"].getElementsByTagName("codGruppoLingua");
			if (codGruppoLingua["0"] != null) {
				codGruppoLingua = codGruppoLingua["0"].textContent;
			} 
			else {
				codGruppoLingua = "";
			}
			
			var id = vo["0"].getElementsByTagName("id");
			if (id["0"] != null) {
				id = id["0"].textContent;
			} 
			else {
				id = "";
			}
			
			var lang = vo["0"].getElementsByTagName("lang");
			if (lang["0"] != null) {
				lang = lang["0"].textContent;
			} 
			else {
				lang = "";
			}
			
			var nome = vo["0"].getElementsByTagName("nome");
			if (nome["0"] != null) {
				nome = nome["0"].textContent;
			} 
			else {
				nome = "";
			}
			
			$('form[name="materiaReadForm"] select[name=settoreGiuridicoId]').append('<option value="'+id+'">'+nome+'</option>' );
			
			var settoreGiuridicoObj = new Object();
			settoreGiuridicoObj.id=id;
			settoreGiuridicoObj.lang=lang;
			settoreGiuridicoObj.nome=nome;
			settoreGiuridicoObj.codGruppoLingua=codGruppoLingua; 	
			settoreGiuridicoArr.push(settoreGiuridicoObj);
		}
		
	};
	var url = WEBAPP_BASE_URL + "materia/caricaSettoreGiuridico.action?tipologiaFascicoloId="+tipologiaFascicoloId;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}