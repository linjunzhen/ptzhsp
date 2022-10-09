var mobilUtil = new Object({
	/**
	 * 验证表单是否通过
	 * @param {Object} formId
	 */
	validateForm : function(formId) {
		var items = $("#" + formId).find("[validateRules]");
		var validateResultMsg = null;
		if (items && items.length >= 1) {
			$.each(items, function() {
				//获取字段名称
				var fieldName = $(this).attr("fieldName");
				//获取验证规则配置
				var validateRules = $(this).attr("validateRules").split(",");
				//获取验证提示信息
				var validateTexts = null;
				if ($(this).attr("validateTexts")) {
					validateTexts = $(this).attr("validateTexts").split(",");
				}
				//获取字段的值
				var fieldValue = this.value;
				var resultMsg = mobilUtil.validateFields(validateRules, fieldValue, fieldName);
				
				if (resultMsg) {
					validateResultMsg = resultMsg;
					$(this).focus();
					return false;
				}
			});
		}
		return validateResultMsg;
	},
	/**
	 *验证字段
	 */
	validateFields : function(validateRules, fieldValue, fieldName) {
		var resultMsg = null;
		for (var i = 0; i < validateRules.length; i++) {
			var rule = "EveValidate." + validateRules[i];
			var ruleObj = eval(rule);
			if (validateRules[i] == "required") {
				if (!fieldValue.trim()) {
					resultMsg = fieldName + ruleObj.vText;
					break;
				}
			} else {
				//获取验证规则
				var regex = ruleObj.regex;
				if (fieldValue) {
					if (!regex.test(fieldValue)) {
						resultMsg = fieldName + ruleObj.vText;
						break;
					}
				}
			}
		}
		return resultMsg;
	},
	/**
	 * 移动端alert
	 */
	layerAlert : function(msg,time){
		if(time==null || time == undefined){
			time=null;
		}
		layer.open({
			btn: ['确定'],
		    content: msg,
		    time: time
		});
	},
	/**
	 * 移动端confirm
	 */
	layerConfirm: function(config){
		var confirmLayer = layer.open({
			content: config.content,
			btn: config.btn==undefined?['确定', '取消']:config.btn,
			shadeClose: config.shadeClose==undefined?false:config.shadeClose,
		    yes: config.yes,
		    no: config.no==undefined?function(){
		    	layer.close(confirmLayer);
		    }:config.no
		});
		return confirmLayer;
	},
	/**
	 * 移动端弹出层
	 */
	layerPage: function(config){
		var pageLayer = layer.open({
			type: 1,
			content: config.html,
			style: 'width:'+config.width+';border:none;',
			btn: ['关闭'],
			yes: function(){
				layer.close(pageLayer);
			}
		});
		return pageLayer;
	},
	/**
	 * 获取提交材料的JSON数据
	 */
	getSubmitMaterFileJson:function(){
		 var applyMatersJson = $("#applyMatersJson").val();
		 var applyMatersObj = $.parseJSON(applyMatersJson);
		 // 定义是否上传了必须上传的材料
		 var isUploadMustFile = true;
		 for(var index in applyMatersObj){
				// 获取材料编码
				var MATER_CODE = applyMatersObj[index].MATER_CODE;
				// 获取材料名称
				var MATER_NAME = applyMatersObj[index].MATER_NAME;
				// 获取是否必须上传
				var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
				if(MATER_ISNEED=="1"){
					var leftSpanSize = $("#image"+MATER_CODE).children("img").length;
					if(leftSpanSize==0){
						isUploadMustFile = false;
						break;
					}
				}
			 }
		 if(!isUploadMustFile){
			 mobilUtil.layerAlert("请上传必须上传的材料!");
			 return null;
		 }
		 var submitMaterList = [];
		 for(var index in applyMatersObj){
			// 获取材料编码
			var MATER_CODE = applyMatersObj[index].MATER_CODE;
			// 获取是否必须上传
			var MATER_ISNEED = applyMatersObj[index].MATER_ISNEED;
			// 获取收取方式
			var SQFS = 1;
			// 获取是否收取
			var SFSQ = 1;
			// 如果是上传方式
			 var uploadedFilesSpans = $("div[id^='image"+MATER_CODE+"'] > img[id]");
			 var uploadedFileIds = "";
			 $.each(uploadedFilesSpans,function(index,span) { 
			 	 var submitMater = {};
			 	 submitMater.ATTACH_KEY = MATER_CODE;
			 	 submitMater.SQFS = "1";
			 	 submitMater.SFSQ = "1";
			 	 submitMater.FILE_ID = $(this).attr("fileId");
			 	 submitMaterList.push(submitMater);
	         }); 
		 }
		 return JSON2.stringify(submitMaterList);
	},
	doPost:function(config){
		var layload = layer.open({type: 2,content: '请稍后'});
		$.post(config.url,config.params,function(responseText, status, xhr){
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(config.callback!=null){
				config.callback.call(this,resultJson);
			}
		}).error(function(xhr, errorText, errorType){
			layer.close(layload);
		});
	},
	chinaIdCard:function(num){
		if (num == 111111111111111)
			return false;
		num = num.toUpperCase();
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			return false;
		}
		//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		//下面分别分析出生日期和校验位
		if (mobilUtil.idcard_getAge(num) <= 0) {
			return false;
		}
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);

			//检查生日日期是否正确 
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {               
				return false;
			}
			else {
				//将15位身份证转成18位 
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return num;
			}
		}
		if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);

			//检查生日日期是否正确 
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {   
				return false;
			}
			else {
				//检验18位身份证的校验码是否正确。 
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1)) {
					return false;
				}
				return num;
			}
		}        
		return false;
	},
	idcard_getAge :function(id) {
		var id = String(id);
		var age;
		if (id.length == 15) {
			age = (new Date()).getFullYear()  - (new Date(id.substr(6, 2), id.substr(8, 2) - 1, id.substr(10, 2))).getFullYear();
		} else if (id.length == 18) {
			age = (new Date()).getFullYear()  - (new Date(id.slice(6, 10), id.slice(10, 12) - 1, id.slice(12, 14))).getFullYear();
		} else {
			return false;
		}
		return age;
	}
});
