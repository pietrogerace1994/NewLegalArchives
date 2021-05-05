/* 
 * To change this license header, choose License Headers in Project Properties.
 * @author Salvatore Mariniello
 * Utility legalSecurity Token
 */

var legalSecurity = legalSecurity || false;
(function (d) {
    !legalSecurity ? legalSecurity = {
        settyng:{formName:'legalSecurityForm',elToken:'CSRFToken'},
        legalSecurityForm: function () {
            return {form: d.forms[legalSecurity.settyng.formName], token: d.forms[legalSecurity.settyng.formName].elements[legalSecurity.settyng.elToken].value, clear: function () {
                    var el = d.forms[legalSecurity.settyng.formName].elements;
                    for (i = 0; i < el.length; i++) {
                        if (el[i].name != legalSecurity.settyng.elToken)
                            el[i].parentNode.removeChild(el[i])
                    }
                }}
        },
        ctx: false,
        token: false,
        get: function () {
            return this.ctx
        },
        setContext: function (o) {
         this.ctx=o;
        return this
        },
        setToken: function (o) {
         this.token=o;
        return this
        },
        getToken: function () {
         if(!this.token) this.token=this.legalSecurityForm().token;
           return this.token;
           },
        isToken:function(context){
        	var el=context?context.elements:{name:''};
        	for (i = 0; i < el.length; i++) {
                if (el[i].name == legalSecurity.settyng.elToken)
                   return true;
            }
        	return false;
        },
        verifyToken:function (url) {
        
           if(url.split("?").length>1){
            for (var i = unescape(url.split("?")[1]), s = i.split("&"), e = 0; e < s.length; e++)
                if (pos = s[e].indexOf("="), pos > 0 && legalSecurity.settyng.elToken == s[e].substring(0, pos))
                    return url;
        }else{
         return  url+="?"+legalSecurity.settyng.elToken+"="+this.getToken();
        }
            return url+="&"+legalSecurity.settyng.elToken+"="+this.getToken();
        },
        putObj: function (e, o, a) {
           if(!this.ctx)this.ctx=this.legalSecurityForm().form;
           
            var now = d.createElement(/^<(\w+)\s*\/?>(?:<\/\1>|)$/.exec(e)[1])
            if (o) {
                for (var i in o)
                    (function (i, o) {
                        now[i] = o[i]
                    })(i, o)
            }
            if (a)
                a.appendChild(now)
            else 
                this.ctx.appendChild(now)
             
            return this;
        },
        putToken:function(context){
        	if(!this.isToken(context))
        	this.putObj('<input>',{type:'hidden',name:legalSecurity.settyng.elToken,value:legalSecurity.getToken()},context)
        },
        attachForm: function (url, type) {
            var typeUpper = type || "get";
            typeUpper = typeUpper.toUpperCase();

            this.legalSecurityForm().form.action = url.split("?")[0];
            switch (typeUpper) {
                case"GET":
                    this.legalSecurityForm().form.method = "get";
                    break;
                case "POST":
                    this.legalSecurityForm().form.method = "post";
                    break;
                default:
                    this.legalSecurityForm().form.method = "get";
            }
    
            this.legalSecurityForm().clear();
            this.setContext(this.legalSecurityForm().form);
            
            if (url.split("?").length > 1)
                for (var i = unescape(url.split("?")[1]), s = i.split("&"), e = 0; e < s.length; e++) {
                    if (pos = s[e].indexOf("="), pos > 0) {
                        this.putObj("<input>", {type: "hidden", name: s[e].substring(0, pos), id: s[e].substring(0, pos), value: s[e].substring(pos + 1)})
                    }
                }

            this.putObj("<input>", {type: "submit", value: "go!", style: "display:none"})
            .get().submit();



        },
        attachUrl: function (url, token) {
        if(token)this.setToken(token);
            location.href=this.verifyToken(url);  
        }
    } : legalSecurity
})(document)
