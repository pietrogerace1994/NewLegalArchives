if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

$( document ).ready(function() {
	var radioStudioLegale = document.querySelectorAll("input[name^='tipoStudioLegale'");
	if(radioStudioLegale !== undefined){
		for (var i=0;i<radioStudioLegale.length;i++){
			if(radioStudioLegale[i].checked==true){
				selectRadio(radioStudioLegale[i].value);
			}
		}
	}
	
	/*var insertMode = document.getElementById("insertMode");
	if(insertMode == null ){
		var id = $( "#professionistaEsternoId" ).val();
		if(id !== null){
			caricaDescrizioniEditProfEst(id);
		}
	} */
	
	var viewMode = document.getElementById("viewMode");
	if(viewMode !== null ){
		var idProf = getUrlParameter('idProf');
		if(idProf !== undefined){
			$( "#professionistaEsternoId" ).val(idProf);
			caricaDescrizioniProfEst(idProf);
		}
	}
	
	$("#filtroListaProfessionistaEsterno").focusout(function() {
		
		var val=$("#filtroListaProfessionistaEsterno").val() ;
		if(val=="") {
			var strOpt = "";
			for(var i=0; i< jsonArrayProfessionistaEsterno.length; i++) {
				var obj = jsonArrayProfessionistaEsterno[i];
				
				var cognome= '';
				if(obj.cognome != null){
					cognome = obj.cognome;
				}
				
				var nome= '';
				if(obj.nome != null){
					nome = obj.nome;
				}
				
				var codiceFiscale= '';
				if(obj.codiceFiscale != null){
					codiceFiscale = obj.codiceFiscale;
				}

				var option = cognome + ' ' + nome + ' ' + codiceFiscale;
				var id=obj.id;
				var str = '<option value="'+id+'">'+ option +'</option>';
				strOpt += str;
			}
			
			$("#professionistaEsternoId").html("");
			$("#professionistaEsternoId").html(strOpt);
			
		}
		
	});
	
	$("#filtroListaProfessionistaEsterno").keyup(function(event) {
		
		var indici=new Array();
		var val=$("#filtroListaProfessionistaEsterno").val() ;
		val=val.toLowerCase();
		var str = '<option value="0">Loading ...</option>';
		$("#professionistaEsternoId").html("");
		$("#professionistaEsternoId").html(str);
		
		// cerco
		for(var i=0; i< jsonArrayProfessionistaEsterno.length; i++) {
			var obj = jsonArrayProfessionistaEsterno[i];
	
			var cognome=obj.cognome;
			var nome=obj.nome;
			var codiceFiscale=obj.codiceFiscale;
			
			var okCognome = false;
			var okNome = false;
			var okCodiceFiscale = false;
			
			if(cognome != null){
				cognome = cognome.toLowerCase();
				okCognome = cognome.startsWith(val);
			}
			
			if(nome != null){
				nome = nome.toLowerCase();
				okNome = nome.startsWith(val);
			}
			
			if(codiceFiscale != null){
				codiceFiscale = codiceFiscale.toLowerCase();
				okCodiceFiscale = codiceFiscale.startsWith(val);
			}
			
			if(okCognome || okNome || okCodiceFiscale) {
				indici.push(i);
			}
		}
		
		if(indici.length==0) {
			var str = '<option value="0" style="color:red">Nessuna Professionista Esterno</option>';
			$("#professionistaEsternoId").html("");
			$("#professionistaEsternoId").html(str);
		}
		else if(indici.length>0) {
			var strOpt = "";
			
			for(var i=0; i< indici.length; i++) {
				var x = indici[i];
				var obj=jsonArrayProfessionistaEsterno[x];
				
				var cognome= '';
				if(obj.cognome != null){
					cognome = obj.cognome;
				}
				
				var nome= '';
				if(obj.nome != null){
					nome = obj.nome;
				}
				
				var codiceFiscale= '';
				if(obj.codiceFiscale != null){
					codiceFiscale = obj.codiceFiscale;
				}
				
				var option = cognome + ' ' + nome + ' ' + codiceFiscale;
				var id=obj.id;
				
				var str = '<option value="'+id+'">'+option+'</option>';
				strOpt += str;
			}//endfor
			
			
			$("#professionistaEsternoId").html("");
			$("#professionistaEsternoId").html(strOpt);
			indici = [];
		}
	
	});
});

function cancellaProfessionistaEsterno(){
	console.log('cancella professionista Esterno');
	var form = document.getElementById("professionistaEsternoView"); 
	var op = document.getElementById("op");
	op.value="salvaProfessionistaEsterno";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	form.submit(); 
}

function salvaProfessionistaEsterno(){
	console.log('salva professionista Esterno');
	var form = document.getElementById("professionistaEsternoView"); 
	var op = document.getElementById("op");
	op.value="salvaProfessionistaEsterno";
	form.submit(); 
}



/*
function downloadDoc(id){
	console.log('downloadDoc - uuid: '+id);
	var form = document.createElement("form");
	form.action="./download.action";
	form.method="GET";
	var inp=document.createElement("input");
	inp.type="hidden";
	inp.name="uuid";
	inp.value=id;
	form.appendChild(inp);
	form.submit(); 
}

*/

function salvaEditProfessionistaEsterno(){
	console.log('salva professionista Esterno');
	var form = document.getElementById("professionistaEsternoView"); 
	var op = document.getElementById("op");
	op.value="salvaProfessionistaEsterno";
	var editMode = document.getElementById("editMode");
	editMode.value="true";
	form.submit(); 
}

function selectRadio(value){
	if(value == "1"){
		$("#nuovoStudioLegale").slideDown(200);
		$("#studioLegaleEsistente").hide();
		$("#boxContainerStudioLegale").removeClass("hidden");
	}else {
		$("#studioLegaleEsistente").slideDown(200);
		$("#nuovoStudioLegale").hide();
		$("#boxContainerStudioLegale").addClass("hidden");
	}
}

function addEmail(){
	var max = findMax();
	max = max +1;
	$('#divEmail').append('<div class="list-group-item media" id="addEmail_'+ max +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
			'<div class="col-sm-9">'+
			'<input id="email['+ max +']" name="email['+ max +']" class="form-control" type="text">'+
		'</div>'+
		'<button type="button" onclick="removeEmail(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div></div>' );
}

function addEmailForm(value, emailToAdd){
	
	emailToAdd = emailToAdd.replace('[', '').replace(']', '');
	var emailArray = emailToAdd.split(",");
	for (i = 1; i < value; i++) {
		var max = findMax();
		max = max +1;
		$('#divEmail').append('<div class="list-group-item media" id="addEmail_'+ max +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> </label>'+
				'<div class="col-sm-9">'+
				'<input id="email['+ max +']" name="email['+ max +']" value="'+ emailArray[i] +'" class="form-control" type="text">'+
			'</div>'+
			'<button type="button" onclick="removeEmail(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
				'<span class="glyphicon glyphicon-minus"></span>'+
			'</button></div></div></div>' );
	}
}

function removeEmail(idCliccato){
	$('#addEmail_'+ idCliccato).remove();
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
		var giudizio = document.getElementById("giudizio");  
		giudizio.value = checkUndefined(data, "giudizio")? "": data.getElementsByTagName("giudizio")[0].firstChild.data;
		var categoriaContest = document.getElementById("categoriaContest");  
		categoriaContest.value = checkUndefined(data, "categoriaContest")? "": data.getElementsByTagName("categoriaContest")[0].firstChild.data;
		var statoEsitoValutazioneProf = document.getElementById("statoEsitoValutazioneProf");  
		statoEsitoValutazioneProf.value = checkUndefined(data, "statoEsitoValutazioneProf")? "": data.getElementsByTagName("statoEsitoValutazioneProf")[0].firstChild.data;
		var statoProfessionista = document.getElementById("statoProfessionista");  
		statoProfessionista.value = checkUndefined(data, "statoProfessionista")? "": data.getElementsByTagName("statoProfessionista")[0].firstChild.data;
		var studioLegale = document.getElementById("studioLegale");  
		
		if(data.getElementsByTagName("studioLegaleDenominazione")[0] !== undefined){
			$('#studioLegaleDenominazione').val(data.getElementsByTagName("studioLegaleDenominazione")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleIndirizzo")[0] !== undefined){
			$('#studioLegaleIndirizzo').val(data.getElementsByTagName("studioLegaleIndirizzo")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCap")[0] !== undefined){
			$('#studioLegaleCap').val(data.getElementsByTagName("studioLegaleCap")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCitta")[0] !== undefined){
			$('#studioLegaleCitta').val(data.getElementsByTagName("studioLegaleCitta")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleEmail")[0] !== undefined){
			$('#studioLegaleEmail').val(data.getElementsByTagName("studioLegaleEmail")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleTelefono")[0] !== undefined){
			$('#studioLegaleTelefono').val(data.getElementsByTagName("studioLegaleTelefono")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleFax")[0] !== undefined){
			$('#studioLegaleFax').val(data.getElementsByTagName("studioLegaleFax")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleNazioneCode")[0] !== undefined){
			$('#studioLegaleNazioneDescrizione').val(data.getElementsByTagName("studioLegaleNazioneCode")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegaleCodiceSap")[0] !== undefined){
			$('#studioLegaleCodiceSap').val(data.getElementsByTagName("studioLegaleCodiceSap")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegalePartitaIva")[0] !== undefined){
			$('#studioLegalePartitaIva').val(data.getElementsByTagName("studioLegalePartitaIva")[0].firstChild.data);
		}

		if(studioLegale != null){
			studioLegale.value = checkUndefined(data, "studioLegale")? "": data.getElementsByTagName("studioLegale")[0].firstChild.data;
		}
		
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
		
		var listDoc = data.getElementsByTagName("doc");
		var labelDocument = document.getElementById("documentLabel").innerHTML;
		if(listDoc[0] !== undefined){
			$("#divDocument").slideDown(200);
			for (i = 0; i < listDoc.length; i++) {
				if(i == 0){
					$('#firstDocument').append(listDoc[i].firstChild.textContent+'&nbsp;&nbsp;<button type="button" id="download-prof" docname="'+ listDoc[i].firstChild.textContent +'" uuid="'+ listDoc[i].lastChild.textContent +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float" style="position: relative!important;">'+
							'<span class="glyphicon glyphicon-download-alt"></span>'+
							'</button>');
				} else {
					$('#divDocument').append('<div class="list-group-item media" id="addDocument_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> '+ labelDocument +'</label>'+
							'<div class="col-sm-10">'+listDoc[i].firstChild.textContent+'&nbsp;&nbsp;<button type="button" id="download-prof" docname="'+ listDoc[i].firstChild.textContent +'" uuid="'+ listDoc[i].lastChild.textContent +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float" style="position: relative!important;">'+
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

function caricaDescrizioniEditProfEst(id) {
	console.log('caricaDescrizioniEditProfEst: '+id);
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
		
		if(data.getElementsByTagName("tipoProfessionista")[0] !== undefined){
			$('#tipoProfessionistaCode').val(data.getElementsByTagName("tipoProfessionista")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("giudizio")[0] !== undefined){
			$('#giudizio').val(data.getElementsByTagName("giudizio")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("categoriaContest")[0] !== undefined){
			$('#categoriaContestCode').val(data.getElementsByTagName("categoriaContest")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("statoEsitoValutazioneProf")[0] !== undefined){
			$('#statoEsitoValutazioneProfCode').val(data.getElementsByTagName("statoEsitoValutazioneProf")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("statoProfessionista")[0] !== undefined){
			$('#statoProfessionistaCode').val(data.getElementsByTagName("statoProfessionista")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("statoProfessionista")[0] !== undefined && data.getElementsByTagName("statoProfessionista")[0].firstChild.data == "A"){
			$( '#statoEsitoValutazioneProfCode' ).prop( "disabled", false );
		}
		else{
			$( '#statoEsitoValutazioneProfCode' ).prop( "disabled", true );
		}
		
		if(data.getElementsByTagName("studioLegale")[0] !== undefined){
			$('#studioLegaleId').val(data.getElementsByTagName("studioLegale")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleDenominazione")[0] !== undefined){
			$('#studioLegaleDenominazione').val(data.getElementsByTagName("studioLegaleDenominazione")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleIndirizzo")[0] !== undefined){
			$('#studioLegaleIndirizzo').val(data.getElementsByTagName("studioLegaleIndirizzo")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCap")[0] !== undefined){
			$('#studioLegaleCap').val(data.getElementsByTagName("studioLegaleCap")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCitta")[0] !== undefined){
			$('#studioLegaleCitta').val(data.getElementsByTagName("studioLegaleCitta")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleEmail")[0] !== undefined){
			$('#studioLegaleEmail').val(data.getElementsByTagName("studioLegaleEmail")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleTelefono")[0] !== undefined){
			$('#studioLegaleTelefono').val(data.getElementsByTagName("studioLegaleTelefono")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleFax")[0] !== undefined){
			$('#studioLegaleFax').val(data.getElementsByTagName("studioLegaleFax")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleNazioneCode")[0] !== undefined){
			$('#studioLegaleNazioneCode').val(data.getElementsByTagName("studioLegaleNazioneCode")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegaleCodiceSap")[0] !== undefined){
			$('#studioLegaleCodiceSap').val(data.getElementsByTagName("studioLegaleCodiceSap")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegalePartitaIva")[0] !== undefined){
			$('#studioLegalePartitaIva').val(data.getElementsByTagName("studioLegalePartitaIva")[0].firstChild.data);
		}
		
		
		
		var listNazioni = data.getElementsByTagName("nazioneDesc");
		if(listNazioni[0] !== undefined){
			for (i = 0; i < listNazioni.length; i++) {
				var list = document.getElementsByName("nazioniAggiunte");
				for (a = 0; a < list.length; a++) {
					if(list[a].value == listNazioni[i].firstChild.data){
						list[a].checked = true;
					}
				}
			}
		}
		
		var listSpec = data.getElementsByTagName("specDesc");
		if(listSpec[0] !== undefined){
			for (i = 0; i < listSpec.length; i++) {
				var list = document.getElementsByName("specializzazioniAggiunte");
				for (a = 0; a < list.length; a++) {
					if(list[a].value == listSpec[i].firstChild.data){
						list[a].checked = true;
					}
				}
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
						'<div class="col-sm-9">'+
						'<input id="email['+ i +']" name="email['+ i +']" class="form-control" type="text"  value = "'+ listEmail[i].firstChild.data +'" >'+
					'</div>'+
					'<button type="button" onclick="removeEmail(this.id)" id="'+ i +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
						'<span class="glyphicon glyphicon-minus"></span>'+
					'</button></div></div></div>' );
				}
			}
		}
		
		var listDoc = data.getElementsByTagName("doc");
		var labelDocument = document.getElementById("documentLabel").innerHTML;
		var documentLabelDelete = $('#documentLabelDelete').val();
		if(listDoc[0] !== undefined){
			$("#divDocument").slideDown(200);
			for (i = 0; i < listDoc.length; i++) {
				if(i == 0){
					$('#firstDocument').append('<div style="float:left;">'+listDoc[i].firstChild.textContent+'&nbsp;&nbsp;<button type="button" id="download-prof" uuid="'+ listDoc[i].lastChild.textContent +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float" style="position: relative!important;">'+
							'<span class="glyphicon glyphicon-download-alt"></span></div>'+
							'</button>&nbsp;&nbsp;<div><span><label class="col-sm-3 control-label"> '+ documentLabelDelete +'</label><input type="checkbox" name="documentiDaEliminare" id="documentiDaEliminare'+i+'" value="'+ listDoc[i].lastChild.textContent +'"/></span></div>');
				} else {
					$('#divDocument').append('<div class="list-group-item media" id="addDocument_'+ i +'"><div class="media-body"><div class="form-group"><label class="col-sm-2 control-label"> '+ labelDocument +'</label>'+
							'<div class="col-sm-10">'+
							'<div style="float:left;">'+listDoc[i].firstChild.textContent+'&nbsp;&nbsp;<button type="button" id="download-prof" uuid="'+ listDoc[i].lastChild.textContent +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float" style="position: relative!important;">'+
							'<span class="glyphicon glyphicon-download-alt"></span></button></div>'+
							'<div><div><label class="col-sm-3 control-label"> '+ documentLabelDelete +'</label><input type="checkbox" name="documentiDaEliminare" id="documentiDaEliminare'+ i +'" value="'+ listDoc[i].lastChild.textContent +'"/></div>'+	
							'</div>'+
							'</div></div></div></div>' );
				}

			}
		} else {
			$("#divDocument").hide();
		}
		
	};
	var url = WEBAPP_BASE_URL + "professionistaEsterno/caricaDescrizioniEditProfEst.action?id=" + id;
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
	var num_option=document.getElementById('nazioneDesc').options.length;
	for(a=num_option;a>=0;a--){
		document.getElementById('nazioneDesc').options[a]=null;
	}
	num_option=document.getElementById('specDesc').options.length;
	for(a=num_option;a>=0;a--){
		document.getElementById('specDesc').options[a]=null;
	}
	$("[id^='addEmail_']").remove();
	$("[id^='addDocument_']").remove();
	$("#firstDocument").empty();
}

function svuotaListeEdit(){
	document.getElementById("tipoProfessionistaCode").selectedIndex = "0";
	document.getElementById("categoriaContestCode").selectedIndex = "0";
	document.getElementById("giudizio").selectedIndex = "0";
	document.getElementById("statoEsitoValutazioneProfCode").selectedIndex = "0";
	document.getElementById("studioLegaleId").selectedIndex = "0";
	$("[id^='nazioniAggiunte']").removeAttr('checked');
	$("[id^='specializzazioniAggiunte']").removeAttr('checked');
	$("[id^='addEmail_']").remove();
	$("[id^='addDocument_']").remove();
	$("#firstDocument").empty();
}

function findMax(){
	
	var textemails = document.querySelectorAll("input[name^='email[']");
	if(textemails.length == 1){
		return 0;
	}
	
	var ar = new Array();
	$('[id^="email["]').each(
		 function(){
			ar.push(
					parseInt( $(this).attr('id').replace('email[','').replace(']','') )
				  );
		   });
	return Math.max.apply( Math, ar );
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


function rimuoviAllegatoProfEst(){
		console.log('rimuoviAllegatoProfEst');
		var form = document.getElementById("professionistaEsternoView");
		
		form.action = "rimuoviAllegatoProfEst.action";
		waitingDialog.show('Loading...');
		form.submit();
}


function fnDownloadProff(uuid, docname){
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "professionistaEsterno/download.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	downloadFilePDF(url, docname);
}

function downloadFilePDF(urlToSend, docname) {
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "blob";
    req.onload = function (event) {
        var blob = req.response;
        var fileName = docname;
        var link=document.createElement('a');
        link.href=window.URL.createObjectURL(blob);
        link.download=fileName;
        link.click();
        waitingDialog.hide();
    };
    req.send();
}


function valorizzaDettaglioStudio(nazione){

	var idNazione = nazione.value;
	
	
	console.log('valorizzaDettaglioStudio: '+idNazione);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		if(data.getElementsByTagName("studioLegaleDenominazione")[0] !== undefined){
			$('#studioLegaleDenominazione').val(data.getElementsByTagName("studioLegaleDenominazione")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleIndirizzo")[0] !== undefined){
			$('#studioLegaleIndirizzo').val(data.getElementsByTagName("studioLegaleIndirizzo")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCap")[0] !== undefined){
			$('#studioLegaleCap').val(data.getElementsByTagName("studioLegaleCap")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleCitta")[0] !== undefined){
			$('#studioLegaleCitta').val(data.getElementsByTagName("studioLegaleCitta")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleEmail")[0] !== undefined){
			$('#studioLegaleEmail').val(data.getElementsByTagName("studioLegaleEmail")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleTelefono")[0] !== undefined){
			$('#studioLegaleTelefono').val(data.getElementsByTagName("studioLegaleTelefono")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleFax")[0] !== undefined){
			$('#studioLegaleFax').val(data.getElementsByTagName("studioLegaleFax")[0].firstChild.data);
		}
		
		if(data.getElementsByTagName("studioLegaleNazioneCode")[0] !== undefined){
			$('#studioLegaleNazioneCode').val(data.getElementsByTagName("studioLegaleNazioneCode")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegaleCodiceSap")[0] !== undefined){
			$('#studioLegaleCodiceSap').val(data.getElementsByTagName("studioLegaleCodiceSap")[0].firstChild.data);
		}

		if(data.getElementsByTagName("studioLegalePartitaIva")[0] !== undefined){
			$('#studioLegalePartitaIva').val(data.getElementsByTagName("studioLegalePartitaIva")[0].firstChild.data);
		}
		
	};
	var url = WEBAPP_BASE_URL + "professionistaEsterno/valorizzaDettaglioStudio.action?id=" + idNazione;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}



$(document).on('click','#download-prof',function(){
	console.log('download-prof onclick ');
	var uuid=$(this).attr("uuid");
	var docname=$(this).attr("docname");
	return fnDownloadProff(uuid, docname);

});