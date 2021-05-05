if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}


function aggiungiInvoices(){
	var file = $('#fileInvoiceManager')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "invoiceManager/uploadFile.action";
	 
	var fnCallBackSuccess = function(data){ 
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};
	var fnCallBackError = function(){
		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}