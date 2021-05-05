$(document).ready(function () {
//	********************************************************************
//	DARIO **************************************************************
//	********************************************************************

	
	
	onOpenPanel = function(panel){
		
		
		
		$('#' + panel).on('show.bs.modal',function(e) {
			
			var this_=$(this);
			
			//Inizializzazioni output dialog
			
			var wf_user_code_sel = $(this).find('[name=wf_user_code_sel]');
			var wf_user_resp_sel = $(this).find('[name=wf_user_resp_sel]');
			//Evito di rimuovere il valore per problema loop approvazione Proforma MASSIMO CARUSO
//			wf_user_code_sel.val("");
			if(wf_user_resp_sel.length!=0){
	        	wf_user_resp_sel.val("");
            };
			//------------------------------
			
			var btn =  $(this).find('[name=BTN_START_WORKFLOW]');
			if(btn.length==0){
				return;
			}
					
			
			if($(this).find('[name=lista_assegnatari]').css('display') == 'none'){	
				btn.prop('disabled', false);
	            return;
	        }
			
			
			$(this).find('[name=wf_assegnatari_list]').css("border", "1px solid");
			
			
	       	$(this).find('[name=wf_assegnatari_list] tr').removeClass('info');
	       
	        btn.prop('disabled', true);
	        
	        
	        $(this).find('[name=wf_assegnatari_list] tr').click(function(){	
	        	
	           
	        	this_.find('[name=wf_assegnatari_list] tr').removeClass('info');
	            
	        	$(this).addClass('info');
	                       
	           
	          
	           wf_user_code_sel.val($(this).data("code"));
	           if(wf_user_resp_sel.length!=0){
		        	wf_user_resp_sel.val($(this).data("resp"));
	            };
	           
	           
	           btn.prop('disabled', false);
	           
	            
	         });
	         
	        
		});
		
	}
	//-------------------------------------------------------------------------
	gestisci_tasto_confirm_workflow = function(objPanel, backEndFunction){
		
		
		//--------------------------------------------------------------------
		var btn = objPanel.find('[name=BTN_START_WORKFLOW]');
		if(btn.length==0){
			return;
		}
		
		if(btn.data('event')!=undefined){
			return;
	    }
		btn.data('event',true);
		//-------------------------------------------------------------------		
		
		btn.click(function(e){
			
			var user_code = objPanel.find('[name=wf_user_code_sel]').val();
			var flag_resp = objPanel.find('[name=wf_user_resp_sel]').val();
			var entity_code =  objPanel.find('[name=wf_code_sel]').val();
			
			backEndFunction(user_code, flag_resp, entity_code);
			
		}); 
		
	};
	//-------------------------------------------------------------------------
	
	workflowAvviaAutomatico = function(panel,id){
   		
		onOpenPanel(panel);
		
		$('#' + panel).find('[name=wf_code_sel]').val(id);
   				
		$('#' + panel).find('[name=lista_assegnatari]').hide();
		
    	$('#' + panel).modal();
       
    
    };
	
   	
	workflowAvviaManuale = function(panel,id){
		
		onOpenPanel(panel);
		
		$('#' + panel).find('[name=wf_code_sel]').val(id);
				
		waitingDialog.show('Loading...');
		
		
		var div = $('#' + panel).find('[name=lista_assegnatari]')[0];
		
		
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	
	    	waitingDialog.hide();
	    	div.innerHTML=data;
	    	
	    	$('#' + panel).find('[name=lista_assegnatari]').show();
	    	
	    	
	    	$('#' + panel).modal();
	    	
	    };
	    var callBackFnErr = function() { 
			waitingDialog.hide(); 
		};
	    var ajaxUtil = new AjaxUtil();
	    var url = WEBAPP_BASE_URL + "/gestioneUtenti/cercaAssegnatari.action";
	    url=legalSecurity.verifyToken(url);
	    ajaxUtil.ajax(url, null, "GET", "text/html", fnCallbackSuccess, null, callBackFnErr)
		
		
		
		
    
    };
	 
    workflowAutorizzaAutomatico = function(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep){
    	
    	var panel = "panelFormWorkflow";
    	
    	onOpenPanel(panel);
    	
    	processaWorkflow(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep, false, function(){
    	
    		$('#' + panel).find('[name=lista_assegnatari]').hide();
	    	$('#' + panel).modal();
    		
    	});
    	
    }
    
    workflowAutorizzaManuale = function(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep){
    	
    	var panel = "panelFormWorkflow";
    	
    	onOpenPanel(panel);
    	
    	processaWorkflow(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep, true, function(){
    	
    		$('#' + panel).find('[name=lista_assegnatari]').show();
	    	$('#' + panel).modal();
    		
    	});
    	
    };
	
    workflowAutorizzaAttoAutomatico = function(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente){
    	
    	var panel = "panelFormWorkflowAtto";
    	
    	onOpenPanel(panel);
    	
    	processaWorkflowAtto(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente, false,function(){
    		
    		$('#' + panel).find('[name=lista_assegnatari]').hide();
    		$('#' + panel).find('[name=divSelezionaCollaboratoreAtto]').show();
    		
    		$('#' + panel).modal();
    		
    		
    	});
    	
    	
    };
    
    workflowAutorizzaAttoManuale = function(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente){
    
    	
    	var panel = "panelFormWorkflowAtto";
    	
    	onOpenPanel(panel);
    	
    	processaWorkflowAtto(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente, true,function(){
    		
    		$('#' + panel).find('[name=lista_assegnatari]').show();
    		$('#' + panel).find('[name=divSelezionaCollaboratoreAtto]').hide();
    		
    		$('#' + panel).modal();
    		
    		
    	});
    	
    	
    }
    
	
});






