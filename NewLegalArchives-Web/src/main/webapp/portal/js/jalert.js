/**
 * @autor mssalvo
 * modal custom alert
 * @method
 * open - close - autoclose
 * @usage
 * jalert.open(String message ,Object options)
 * 
 * jalert.autoClose(String message ,Number time,Object options)
 *
 * @example custom callBack
 * jalert.open('message!',{onClose:function(){ fai qualcosa! } })
 * 
 */
var jalert = jalert || (function ($) {
    'use strict';

var fnAlert={
		defaulOpt:{},
		open: function (msg, opt) {
			var options=opt||{}, message=msg || '';
			fnAlert.defaulOpt = $.extend(defaulOption, options);
			jtemplate_.find('.modal-dialog')
			.attr('class', 'modal-dialog')
			.css("width",fnAlert.defaulOpt.width)
			.css("minHeight",fnAlert.defaulOpt.minHeight);
			
			jtemplate_.find('.modal-header')
			.css("background",fnAlert.defaulOpt.headerBackground);
			
		    jtemplate_.find('h4.modal-title')
			.css("color",fnAlert.defaulOpt.colorTitle)
			.css("fontSize",fnAlert.defaulOpt.fontSizeTitle)
			.html(fnAlert.defaulOpt.modalTitle);
			
			jtemplate_.find('#info-message')
			.css("minHeight",fnAlert.defaulOpt.minHeight)
			.css("color",fnAlert.defaulOpt.textColor)
			.css("fontSize",fnAlert.defaulOpt.fontSize)
			.css("fontWeight",fnAlert.defaulOpt.fontWeight)
			.html(message);
			
			if(!fnAlert.defaulOpt.isButtonClose){
			jtemplate_.find('button').css("display","none");	
			if (typeof fnAlert.defaulOpt.onClose === 'function') {
			jtemplate_.off('hidden.bs.modal', function (e) {
				fnAlert.defaulOpt.onClose.call(jtemplate_);
					});
				}	
			}else{
			jtemplate_.find('button').css("display","block");	
			}

			if (fnAlert.defaulOpt.isButtonClose && typeof fnAlert.defaulOpt.onClose === 'function') {
			jtemplate_.find('button').on('click', function (e) {
				fnAlert.defaulOpt.onClose.call(jtemplate_);
			});
		}
		
			jtemplate_.modal();
			/* risetto i parametri di default */
			fnAlert.defaulOpt = $.extend(defaulOption, {
				width:'400px',
				minHeight:'100px',
				fontSize:'16px',
				fontWeight:'bold',
				headerBackground:'#66926c',
				textColor:'#000000',
				modalTitle:'Infomation!',
				colorTitle:'#fff',
				fontSizeTitle:'14px',
				onClose: null,
				isButtonClose: true
			}); 
		},
		
		/* Parametri: String message- Number time - Object options */
		autoClose:function () {
			var opt_={},time_=3300,message='';
			for(var i in arguments){
			(function(type){
			if(typeof type=='string' && isNaN(type)) 
			message= type;	
			if(typeof type=='string' && !isNaN(type)) 
			time_= Number(type);	
			if(typeof type=='object')
			opt_= type;	
			if(typeof type=='number')
			time_= type;	
			})(arguments[i])
			}
			fnAlert.open(message,opt_);
			window.setTimeout(function(){ 
			return fnAlert.close();
			},time_);
		},
		
		close: function () {
			jtemplate_.modal('hide');
		}

		
		
	};
	var defaulOption = {
				width:'400px',
				minHeight:'100px',
				fontSize:'16px',
				fontWeight:'bold',
				headerBackground:'#66926c',
				textColor:'#000000',
				modalTitle:'Infomation!',
				colorTitle:'#fff',
				fontSizeTitle:'14px',
				onClose: null,
				isButtonClose: true
			},
	jtemplate_ = $(
		'<div class="modal fade" id="modal-jalert" tabindex="-1" role="dialog" aria-hidden="true">' +
		'<div class="modal-dialog modal-default" style="width:400px">' +
		'<div class="modal-content" style="border-radius: 12px;">' +
		'<div class="modal-header" style="padding: 15px 26px;background: #66926c;border-radius: 10px 10px 0px 0px;">' +
		'<h4 class="modal-title" style="color:#fff;font-size:14px;"> </h4>' +
		'</div>' +
		'<div class="modal-body">' +
		'<fieldset>' +
		'<div id="info-message" style="width:100%;color:#000;min-height:100px;font-size:16px;font-weight:bold;padding-top: 30px;">' +
		'</div>' +
		'</fieldset>' +
		'<div class="modal-footer">' +
		'<button type="button" class="btn btn-warning" style="float:right;" data-dismiss="modal">Close</button>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>');
		
	return fnAlert;
})(jQuery);
		