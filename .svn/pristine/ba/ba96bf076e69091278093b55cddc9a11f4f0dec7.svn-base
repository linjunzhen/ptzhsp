var mobileUtil = new Object({
	 /**
     * 验证表单是否通过
     * @param {Object} formId
     */
    initValidateForm:function(formId){
        var items = $("#"+formId).find("[validateRules]");
        if(items&&items.length>=1){
            $.each(items, function() {
            	var validateRules = $(this).attr("validateRules");
            	if(validateRules.indexOf("required") >= 0){
            		$(this).addClass("required");
            		var fieldType = $(this).attr("type");
            		if(fieldType=="hidden"){
            			$(this).parent().addClass("required");
            		}
            	}
            });
        }
    },
    /**
     * 验证表单是否通过
     * @param {Object} formId
     */
    validateForm:function(formId,isshowmsg){
    	//$(".jq_tips_box").remove();
    	document.activeElement.blur();
        var items = $("#"+formId).find("[validateRules]");
        items.parent().removeClass("input-error");
        items.parent().parent().removeClass("input-error");
        var validateResultMsg = null;
        if(items&&items.length>=1){
            $.each(items, function() {
            	//获取字段名称
                var fieldName = $(this).attr("placeholder");
				try{
					fieldName=fieldName.replace('请输入','');
					fieldName=fieldName.replace('请选择','');
				}catch(e){
					fieldName="该字段";
				}
                //获取字段类型
                var fieldType = $(this).attr("type");
                //获取字段name
                var name = $(this).attr("name");
				//获取字段id
                var id = $(this).attr("id");
                //获取验证规则配置
                var validateRules = $(this).attr("validateRules").split(",");
                //获取字段的值
                var fieldValue = this.value;
                if((fieldType== "radio" || fieldType == "checkbox")&& $("#"+formId).find("input[name='" + name + "']").size() > 0){
              	  	fieldValue =($("#"+formId).find("input[name='" + name + "']:checked").val()==null?"":$("#"+formId).find("input[name='" + name + "']:checked").val());
                }
                var resultMsg = mobileUtil.validateFields(validateRules,fieldValue,fieldName,fieldType,id);
                if(resultMsg){
                	validateResultMsg= resultMsg;
                	if(isshowmsg){
						if(fieldType=="hidden"){
							window.scrollTo(0, $("#"+id).parent().offset().top-200);
							$("#"+id).parent().parent().addClass("input-error");
							mui.toast(validateResultMsg);
							setTimeout(function(){
								$("#"+id).parent().parent().removeClass("input-error");
							},2000);
						}else{
							window.scrollTo(0, $("#"+id).parent().offset().top-200);
							$("#"+id).parent().addClass("input-error");
							mui.toast(validateResultMsg);
							setTimeout(function(){
								$("#"+id).parent().removeClass("input-error");
							},2000);
						}
					}
                	return false;
                }
	        });
        }
        return validateResultMsg;
    },
    /**
     * 验证字段
     */
    validateFields:function(validateRules,fieldValue,fieldName,fieldType,id){
        var resultMsg = null;
        for(var i =0;i<validateRules.length;i++){
        	if(validateRules[i]==null || validateRules[i]==""){
       		 break;
       	  	}
        	 // 添加函数判断
           	 if(validateRules[i].indexOf("fun_")>=0){
           		var fun = null;
           		if(validateRules[i].indexOf("[")>=0){
           			var tempfun = validateRules[i].split("[")[0];
           			var str_after = validateRules[i].split("[")[1];
           			var paramstr = str_after.split("]")[0];
           			fun = eval("EveValidateFun."+tempfun);
           			if(fieldValue){
               			var funresult = fun(fieldValue,id,paramstr);
                         if (funresult){
        	                  resultMsg = fieldName+funresult;
        	                  break;
        	             }
                     }
           		}else{
           		    fun = eval("EveValidateFun."+validateRules[i]);
           		    if(fieldValue){
           		    	var funresult = fun(fieldValue,id);
           		    	if (funresult){
           		    		resultMsg = fieldName+funresult;
           		    		break;
           		    	}
           		    }
           		}
           	 }else if(validateRules[i].indexOf("aloneFun_")>=0){
           		var fun = null;
           		if(validateRules[i].indexOf("[")>=0){
           			var tempfun = validateRules[i].split("[")[0];
           			var str_after = validateRules[i].split("[")[1];
           			var paramstr = str_after.split("]")[0];
           			fun = eval(tempfun);
           			if(fieldValue){
               			var funresult = fun(fieldValue,id,paramstr);
                         if (funresult){
        	                  resultMsg = funresult;
        	                  break;
        	             }
                     }
           		}else{
           		    fun = eval(validateRules[i]);
           		    if(fieldValue){
           		    	var funresult = fun(fieldValue,id);
           		    	if (funresult){
           		    		resultMsg = funresult;
           		    		break;
           		    	}
           		    }
           		}
           	 }else{
           		 var rule="EveValidate."+validateRules[i];
    	          var ruleObj = eval(rule);
    	          if(validateRules[i]=="required"){
    	              if(!fieldValue.trim()){
    	                  resultMsg = fieldName+ruleObj.vText;
    	                  break;
    	              }
    	          }else{
    	              // 获取验证规则
    	              var regex = ruleObj.regex;
    	              if(fieldValue){
    	            	 
    	                 if (!regex.test(fieldValue)){
    		                  resultMsg = fieldName+ruleObj.vText;
    		                  break;
    		             }
    	              }
    	          }
           	 }
             
         }
         return resultMsg;
    },
    
	/**
	 * 格式化时间
	 * 
	 * @param {}
	 *            date
	 * @param {}
	 *            format
	 */
	formatDate : function(date, format) {
		var paddNum = function(num) {
			num += "";
			return num.replace(/^(\d)$/, "0$1");
		}
		// 指定格式字符
		var cfg = {
			yyyy : date.getFullYear() // 年 : 4位
			,
			yy : date.getFullYear().toString().substring(2)// 年 : 2位
			,
			M : date.getMonth() + 1 // 月 : 如果1位的时候不补0
			,
			MM : paddNum(date.getMonth() + 1) // 月 : 如果1位的时候补0
			,
			d : date.getDate() // 日 : 如果1位的时候不补0
			,
			dd : paddNum(date.getDate())// 日 : 如果1位的时候补0
			,
			hh : paddNum(date.getHours()) // 时
			,
			mm : paddNum(date.getMinutes()) // 分
			,
			ss : paddNum(date.getSeconds())
		// 秒
		}
		format || (format = "yyyy-MM-dd hh:mm:ss");
		return format.replace(/([a-z])(\1)*/ig, function(m) {
			return cfg[m];
		});
	},
	/**
	 * 去除字符串的前后空格
	 * 
	 * @param {}
	 *            str
	 * @return {}
	 */
	trim : function(str) {
		str = str.replace(/(^\s*)|(\s*$)/g, "");
		str = str.replace(/^[\s　\t]+|[\s　\t]+$/, "");
		return str;
	},
	/**
	 * 禁用表单自动提交
	 */
	disableFormAutoSubmit : function() {
		// 禁用浏览器FORM自动提交
		document.onkeydown = function(event) {
			var target, code, tag;
			if (!event) {
				event = window.event; // 针对ie浏览器
				target = event.srcElement;
				if (target.name) {
					var controlName = target.name;
					if (controlName.indexOf("Q_") != -1) {
						code = event.keyCode;
						if (code == 13) {
							tag = target.tagName;
							if (tag == "TEXTAREA") {
								return true;
							} else {
								return false;
							}
						}
					}
				}
			} else {
				target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
				if (target.name) {
					var controlName = target.name;
					if (controlName.indexOf("Q_") != -1) {
						code = event.keyCode;
						if (code == 13) {
							tag = target.tagName;
							if (tag == "INPUT") {
								return false;
							} else {
								return true;
							}
						}
					}
				}

			}
		};
	},
	muiAjaxForForm:function(config){
		document.activeElement.blur();
		var timeout = config.timeout == null?10000:config.timeout
		var mask;
		if(config.mask){
			mask = mobileUtil.createMask(function (){return false;});//callback为用户点击蒙版时自动执行的回调；
			mask.show();//显示遮罩
		}
		mui.ajax(config.url, {
			processData:false,
			data : config.data,
			dataType : 'json',
			type : 'post',
			timeout : timeout,
			success : function(data) {
				if (config.callback != null) {
					config.callback.call(this, data);
				}
			},
			complete: function() {
				if(config.mask){
					mask._remove();
				}
		    },
			error : function(xhr, type, errorThrown) {
				mui.toast("请求服务异常，请稍后再试");
			}
		});
	},
	muiAjaxForData:function(config){
		var timeout = config.timeout == null?10000:config.timeout
		var mask;
		if(config.mask){
			mask = mobileUtil.createMask(function (){return false;});//callback为用户点击蒙版时自动执行的回调；
			mask.show();//显示遮罩
		}
		
		mui.ajax(config.url, {
			data : config.data,
			dataType : 'json',
			type : 'post',
			async : config.async==false?false:true,
			timeout : timeout,
			success : function(data) {
				if (config.callback != null) {
					config.callback.call(this, data);
				}
			},
			complete: function() {
				if(config.mask){
					mask._remove();
				}
		    },
			error : function(xhr, type, errorThrown) {
				mui.toast("请求服务异常，请稍后再试");
			}
		});
	},
	createMask:function(callback){
			var element = document.createElement('div');
			element.classList.add('mui-backdrop');
			element.addEventListener(mui.EVENT_MOVE, mui.preventDefault);
			element.addEventListener('tap', function() {
				mask.close();
			});
			
			var loadelement = document.createElement('div');
			loadelement.innerHTML = "<div class=\"eui_loader\"></div>";
			element.appendChild(loadelement);
			var mask = [element];
			mask._show = false;
			mask.show = function() {
				mask._show = true;
				element.setAttribute('style', 'opacity:1');
				document.body.appendChild(element);
				return mask;
			};
			mask._remove = function() {
				if (mask._show) {
					mask._show = false;
					element.setAttribute('style', 'opacity:0');
					mui.later(function() {
						var body = document.body;
						element.parentNode === body && body.removeChild(element);
					}, 350);
				}
				return mask;
			};
			mask.close = function() {
				if (callback) {
					if (callback() !== false) {
						mask._remove();
					}
				} else {
					mask._remove();
				}
			};
			return mask;
	},
	showNoData:function(id,errormsg){
		var errormsg = errormsg == null?"未查到相关记录":errormsg;
		var html = '<div style="width: 100%;text-align: center;">'
		html +='<img style="width: 74px;margin: 30px auto 0;" src="data:image/png;base64,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"/>'
		html +='<p style="font-size: 14px;color: #999;text-align: center;margin-top: 20px;">'+errormsg+'</p></div>'
		$("#"+id).html(html);
	},
	selectByData:function(data,textId, valueId,callback) {
		document.activeElement.blur();
		var value = document.getElementById(valueId).value;
		var picker = new mui.PopPicker();
		picker.setData(data);
		picker.pickers[0].setSelectedValue(value, 200);
		picker.show(function(SelectedItem) {
			document.getElementById(textId).value = SelectedItem[0].text;
			document.getElementById(valueId).value = SelectedItem[0].value;
			if (callback != null) {
				callback.call(this, SelectedItem);
			}
			picker.dispose();
		});
	},
	getJson:function(div,count,column){
		var json = [];
		for ( var i = 1; i < (count+1); i++) {
			if($("#"+div + i).length>0){
				var obj = new Object();
				var columns = column.split(",");
				for(var j=0;j<columns.length;j++){
					obj[columns[j]] = $("#"+columns[j] + i).val();
				}
				json.push(obj);
			}
		}
		if(json && json.length==0){
			return "";
		}else{
			return JSON.stringify(json);
		}
	},
	isUndifined:function(str){
		if(typeof(str)=="undefined"){
			str ="";
		}
		return str;
	},
	validateFieldsById:function(id){
		window.scrollTo(0, $("#" + id).parent().offset().top - 200);
		$("#" + id).parent().parent().addClass("input-error");
		mui.toast($("#" + id).attr("placeholder"));
		setTimeout(function() {
			$("#" + id).parent().parent().removeClass("input-error");
		}, 2000);
	}
});