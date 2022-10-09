(function($){
	// 验证规则
	$.fn.validationEngineLanguage = function(){};
	$.validationEngineLanguage = {
		newLang:function(){
			$.validationEngineLanguage.allRules = {
				//验证公司英文名重名
				"verifyCompanyEngExist":{
					"func" : function(field,rules,i,options) {
						return verifyCompanyEngExist(field);
					},
					"alertText" : '公司英文名称重名请重新输入！'
				},
				//验证公司名称是否重复
				"verifyCompanyNameExist":{
					"func" : function(field,rules,i,options) {
						return verifyCompanyNameExist(field);
					},
					"alertText" : '公司名称重名请重新输入！请确保其他申报件没有引用该公司名称。若发现重名办件，可将旧件修改名称保存草稿后新件可提交！！！'
				},
				//验证值是否有重复
				"ajaxVerifyValueExist":{
					"url": 'baseController.do?verifyValueExist',     
				    "alertTextOk": "该值可以使用",//验证通过时的提示信息
			        "alertText": "该值已经存在,请重新输入!",                     //验证不通过时的提示信息
			        "alertTextLoad": "正在验证中..."        //正在验证时的提示信息
				},
				"required":{ // Add your regex rules here, you can take telephone as an example
					"regex":"none",
					"alertText":"* 此处不可空白",
					"alertTextCheckboxMultiple":"* 请选择一个项目",
					"alertTextCheckboxe":"* 该选项为必选",
					"alertTextDateRange":"* 日期范围不可空白"
				},
				"dateRange":{
					"regex":"none",
					"alertText":"* 无效的 ",
					"alertText2":" 日期范围"
				},
				"dateTimeRange":{
					"regex":"none",
					"alertText":"* 无效的 ",
					"alertText2":" 时间范围"
				},
				"minSize":{
					"regex":"none",
					"alertText":"* 最少 ",
					"alertText2":" 个字符"
				},
				"maxSize":{
					"regex":"none",
					"alertText":"* 最多 ",
					"alertText2":" 个字符"
				},
				"groupRequired":{
					"regex":"none",
					"alertText":"* 至少填写其中一项"
				},
				"min":{
					"regex":"none",
					"alertText":"* 最小值为 "
				},
				"max":{
					"regex":"none",
					"alertText":"* 最大值为 "
				},
				"past":{
					"regex":"none",
					"alertText":"* 日期需在 ",
					"alertText2":" 之前"
				},
				"future":{
					"regex":"none",
					"alertText":"* 日期需在 ",
					"alertText2":" 之后"
				},	
				"maxCheckbox":{
					"regex":"none",
					"alertText":"* 最多选择 ",
					"alertText2":" 个项目"
				},
				"minCheckbox":{
					"regex":"none",
					"alertText":"* 最少选择 ",
					"alertText2":" 个项目"
				},
				"equals":{
					"regex":"none",
					"alertText":"* 两次输入的密码不一致"
				},
                "creditCard": {
                    "regex": "none",
                    "alertText": "* 无效的信用卡号码"
                },
				"phone":{
					// credit:jquery.h5validate.js / orefalo
					"regex":/^([\+][0-9]{1,3}[ \.\-])?([\(]{1}[0-9]{2,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
					"alertText":"* 无效的电话号码"
				},
				"email":{
					// Shamelessly lifted from Scott Gonzalez via the Bassistance Validation plugin http://projects.scottsplayground.com/email_address_validation/
					"regex":/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
					"alertText":"* 无效的邮件地址"
				},
				"integer":{
					"regex":/^[\-\+]?\d+$/,
					"alertText":"* 无效的整数"
				},
				"integerplus":{
					"regex":/^[\+]?\d+$/,
					"alertText":"* 无效的整数"
				},
				"number":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
                    "regex": /^[\-\+]?((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
					"alertText":"* 无效的数值"
				},
				"JustNumber":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
                    "regex": /^((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
					"alertText":"* 无效的数值"
				},
				"date":{
					"regex":/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
					"alertText":"* 无效的日期，格式必需为 YYYY-MM-DD"
				},
				"ipv4":{
					"regex":/^((([01]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))[.]){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))$/,
					"alertText":"* 无效的 IP 地址"
				},
				"url":{
					"regex":/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
					"alertText":"* 无效的网址"
				},
				"onlyNumberSp":{
					"regex":/^[0-9]+$/,
					"alertText":"* 只能填写数字"
				},
				"onlyLetterSp":{
					"regex":/^[a-zA-Z\ \']+$/,
					"alertText":"* 只能填写英文字母"
				},
				"onlyLetterNumber":{
					"regex":/^[0-9a-zA-Z]+$/,
					"alertText":"* 只能填写数字与英文字母"
				},
				"onlyLetterNumberUnderLine":{
					"regex":/^[0-9a-zA-Z-_]+$/,
					"alertText":"* 只能填写英文字母、数字、下划线"
				},
				"onlyLetterNumberSpec":{
					"regex":/^[\w#\$\+-\.\/:=@\[\]\^_\'\|\&\(\)\?]+$/,
					"alertText":"* 只能填写英文字母、数字、特殊字符"
				},
				//tls warning:homegrown not fielded 
				"dateFormat":{
					"regex":/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/,
					"alertText":"* 无效的日期格式"
				},
				//tls warning:homegrown not fielded 
				"dateTimeFormat":{
					"regex":/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1}$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^((1[012]|0?[1-9]){1}\/(0?[1-9]|[12][0-9]|3[01]){1}\/\d{2,4}\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1})$/,
					"alertText":"* 无效的日期或时间格式",
					"alertText2":"可接受的格式： ",
					"alertText3":"mm/dd/yyyy hh:mm:ss AM|PM 或 ", 
					"alertText4":"yyyy-mm-dd hh:mm:ss AM|PM"
				},
				"numberplus":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
					//"regex": /^[+]?[1-9][\d]*(([\.]{0,1}[\d]+)){0,1}|[1-9]([\d]*))$/,
                    //"regex": /^[+]?[1-9]{1}\d*|[1-9]{1}\d*$/,
					"regex": /^[0-9]{1}\d*$/,
					"alertText":"* 无效的数值"
				},
				"numberplusNoZero":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
					//"regex": /^[+]?[1-9][\d]*(([\.]{0,1}[\d]+)){0,1}|[1-9]([\d]*))$/,
                    //"regex": /^[+]?[1-9]{1}\d*|[1-9]{1}\d*$/,
					"regex": /^[1-9]{1}\d*$/,
					"alertText":"* 必须大于0的整数"
				},
				"number2plus":{
					"regex": /^\d+(\.\d{1,2})?$/,
					"alertText":"* 输入有效数字，保留小数点后两位"
				},
				"numberp6plus":{
					"regex": /^\d+(\.\d{1,6})?$/,
					"alertText":"* 输入有效数字，最多保留小数点后6位"
				},
				
				/**
				 * 正则验证规则补充
				 * Author: ciaoca@gmail.com
				 * Date: 2013-10-12
				 */
				"chinese":{
					"regex":/^[\u4E00-\u9FA5]+$/,
					"alertText":"* 只能填写中文汉字"
				},
				"chinaId":{
					/**
					 * 2013年1月1日起第一代身份证已停用，此处仅验证 18 位的身份证号码
					 * 如需兼容 15 位的身份证号码，请使用宽松的 chinaIdLoose 规则
					 * /^[1-9]\d{5}[1-9]\d{3}(
					 * 	(
					 * 		(0[13578]|1[02])
					 * 		(0[1-9]|[12]\d|3[01])
					 * 	)|(
					 * 		(0[469]|11)
					 * 		(0[1-9]|[12]\d|30)
					 * 	)|(
					 * 		02
					 * 		(0[1-9]|[12]\d)
					 * 	)
					 * )(\d{4}|\d{3}[xX])$/i
					 */
					"regex":/^[1-9]\d{5}[1-9]\d{3}(((0[13578]|1[02])(0[1-9]|[12]\d|3[0-1]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[12]\d)))(\d{4}|\d{3}[xX])$/,
					"alertText":"* 无效的身份证号码"
				},
				"chinaIdLoose":{
					"regex":/^(\d{18}|\d{15}|\d{17}[xX])$/,
					"alertText":"* 无效的身份证号码"
				},
				"chinaZip":{
					"regex":/^\d{6}$/,
					"alertText":"* 无效的邮政编码"
				},
				"qq":{
					"regex":/^[1-9]\d{4,10}$/,
					"alertText":"* 无效的 QQ 号码"
				},
				"mobilePhone":{
					"regex":/^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|19[0-9]|14[57]|166)[0-9]{8}$/,
					"alertText":"* 无效的手机号码"
				},
				"mobilePhoneLoose":{
					"regex":/^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|19[0-9]|14[57]|166)[0-9]{8}$/,
					"alertText":"* 无效的手机号码"
				},
                "estateNum":{
                    "regex":/^[a-zA-Z]{13,14}$/,
                    "alertText":"* 无效的不动产单元号"
                },
				"fixPhoneWithAreaCode":{
					"regex":/^(\d{3,4}-)\d{7,8}$/,
					"alertText":"* 无效的 固定电话号码<br/>&nbsp;&nbsp;格式必须为 区号-号码 ,其中区号3位或4位、号码7位或8位"
				},
				"fixFaxWithAreaCode":{
					"regex":/^(\d{3,4}-)\d{7,8}$/,
					"alertText":"* 无效的传真号码<br/>&nbsp;&nbsp;格式必须为 区号-号码 ,其中区号3位或4位、号码7位或8位"
				},
				"fixOrMobilePhone":{
					"regex":/(^1[3|4|5|7|8][0-9]\d{8}$)|(^(\d{3,4}-)\d{7,8}$)/,
					"alertText":"* 无效的手机号码或固定电话<br/>&nbsp;&nbsp;手机号码必须为13、14、15、17、18开头的11位数字<br/>&nbsp;&nbsp;固定电话号码格式格式必须为 区号-号码 ,其中区号3位或4位、号码7位或8位"
				},
				"chineseorLetter":{
					"regex":/^[\u4e00-\u9fa5]|[a-zA-Z]+$/,
					"alertText":"* 请输入中文或者英文字母"
				},
				"onlychineseLetter":{
					"regex":/^([\u4e00-\u9fa5]{1,100}|[a-zA-Z\.\s]{1,100})$/,
					"alertText":"* 请输入全中文或者全英文字母"
				},
				"onlychinese":{
					"regex":/^([\u4e00-\u9fa5]{1,100})$/,
					"alertText":"* 请输入全中文"
				},
				"onlychineseToFH":{
					"regex":/^([^\x00-\xff]{1,100})$/,
					"alertText":"* 请输入中文"
				},
				"vidcard":{
					"func" : function(field,rules,i,options) {
						return isChinaIDCard(field.val());
					},
					"alertText" : '请输入正确的身份证号码<br/>且年龄必须大于等于18岁'
				},
				"mustUpper":{
					"func" : function(field,rules,i,options) {
						return mustUpper(field.val());
					},
					"alertText" : '证件号码带有字母的需要大写'
				},
				"Enpassword":{
					"regex":/^(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\W]+$)(?![a-z\W]+$)(?![a-zA-Z]+$)(?![A-Z\W]+$)[a-zA-Z0-9\W_]+$/,
					"alertText":"* 请输入特殊字符加字母加数字的<br/>组合密码"
				},
				"ajaxVerifyMobileValueExist":{
					"url": 'userInfoController/verifyMobileValueExist.do',     
				    "alertTextOk": "该手机号码可以使用",//验证通过时的提示信息
			        "alertText": "该手机号码已被注册,请重新输入!",                     //验证不通过时的提示信息
			        "alertTextLoad": "正在验证中..."        //正在验证时的提示信息
				},
				"minCapital":{
					"func" : function(field,rules,i,options) {
						return isMinCapital(field.val());
					},
					"alertText" : '投资总额必须大于等于注册资本'
				},
				"equalsLegalIdno":{
					"func" : function(field,rules,i,options) {
						return equalsLegalIdno(field.val());
					},
					"alertText" : '财务代表人不能与法定代表人相同'
				},
				"equalsDirectorIdno":{
                    "func" : function(field,rules,i,options) {
                        return equalsDirectorIdno(field.val());
                    },
                    "alertText" : '董事人员不能重复'
                },
				"equalsDirectorOrManagerIdno":{
					"func" : function(field,rules,i,options) {
						return equalsDirectorOrManagerIdno(field.val());
					},
					"alertText" : '监事不能与董事、经理相同'
				},
				"equalHhShareHolder":{
					"func" : function(field,rules,i,options) {
						return equalHhShareHolder(field.val());
					},
					"alertText" : '委派代表不能与任何一位有限合伙人相同'
				},
				"equalsJsIdNo":{
					"func" : function(field,rules,i,options) {
						return equalsJsIdNo(field.val());
					},
					"alertText" : '监事人员不能重复'
				},
				"IdTypeOlnySF":{
					"func" : function(field,rules,i,options) {
						return equalSF(field.val());
					},
					"alertText" : '证件类型只能选择身份证'
				},
				"IdTypeNotSF":{
					"func" : function(field,rules,i,options) {
						return !equalSF(field.val());
					},
					"alertText" : '证件类型不能选择身份证'
				},
				"yearPaymentperiod":{
					"func" : function(field,rules,i,options) {
						return yearPaymentperiod(field.val());
					},
					"alertText" : '出资期限不得超过经营期限'
				},
				"isGtCurrentTime":{
					"func" : function(field,rules,i,options) {
						return isGtCurrentTime(field.val());
					},
					"alertText" : '请确认该时间前是否能全部到资'
				},
				"numMustBiggerZero":{
					"func" : function(field,rules,i,options) {
						var patrn=/^((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\\.]([0-9]+))?$/;
						if (patrn.exec(field.val())&&field.val()>0){
							return true;
						}else{
							return false;
						}
					},
					"alertText" : '数值必须大于0'
				},
				"isEighteen":{
					"func" : function(field,rules,i,options) {
						return isEighteen(field.val());
					},
					"alertText" : '必须满18周岁'
				},
				"checkShareHolderName":{
					"func" : function(field,rules,i,options) {
						return checkShareHolderName(field.val());
					},
					"alertText" : '请确认该股东是否具备独立法人资格'
				},"checkHhShareHolderName":{
					"func" : function(field,rules,i,options) {
						return checkHhShareHolderName(field.val());
					},
					"alertText" : '该类型合伙人无法通过该系统申报，请通过“企业开办全程网上办”系统申报。'
				},
				"fundsYjcjcze":{
					"func" : function(field,rules,i,options) {
						var fundsZzygrs = $("[name='FUNDS_ZZYGRS']").val();
						if(!fundsZzygrs){
							fundsZzygrs = 1;
						}
						var v = field.val();
						if (Number(fundsZzygrs)*Number(1720)<=v&&v<=Number(fundsZzygrs)*Number(23850)){
							return true;
						}else{
							return false;
						}
					},
					"alertText" : '必须大于“1720*在职员工数”且小于“23850*在职员工数”'
				},
				"fixProposerRule":{
					"regex":/^[a-zA-Z\(\)\d\u4e00-\u9fa5]+$/,
					"alertText":"申请单位名称或申请人名称不能有特殊符号，符号中只允许有英文的()"
				},
				"equalsSgsStr":{//双公示字符判断
					"func" : function(field,rules,i,options) {
						return equalsSgsStr(field.val());
					},
					"alertText" : '不得为空、无等词或包含*、null、/、test 等词，<br/>且长度必须大于一个汉字或大于三个字符'
				}
				/**
				 * 自定义公用提示信息：
				 * 外部通过 $.validationEngineLanguage.allRules.validate2fields.alertText 可获取
				 *
				 * 	"validate2fields": {
				 * 		"alertText": "* 请输入 HELLO"
				 *	 },
				 * 	
				 *
				 * 自定义规则示例：
				 * 	"requiredInFunction": { 
				 * 		"func": function(field,rules,i,options){
				 * 			return (field.val()=="test") ? true : false;
				 * 		},
				 * 		"alertText": "* Field must equal test"
				 * 	},
				 *
				 *
				 * Ajax PHP 验证示例
				 * 	"ajaxUserCallPhp": {
				 * 		"url": "phpajax/ajaxValidateFieldUser.php",
				 * 		// you may want to pass extra data on the ajax call
				 * 		"extraData": "name=eric",
				 * 		// if you provide an "alertTextOk", it will show as a green prompt when the field validates
				 * 		"alertTextOk": "* 此帐号名称可以使用",
				 * 		"alertText": "* 此名称已被其他人使用",
				 * 		"alertTextLoad": "* 正在确认帐号名称是否有其他人使用，请稍等。"
				 * 	},
				 * 	"ajaxNameCallPhp": {
				 * 		// remote json service location
				 * 		"url": "phpajax/ajaxValidateFieldName.php",
				 * 		// error
				 * 		"alertText": "* 此名称已被其他人使用",
				 * 		// speaks by itself
				 * 		"alertTextLoad": "* 正在确认名称是否有其他人使用，请稍等。"
				 * 	}
				 */
			};
			
		}
	};
	$.validationEngineLanguage.newLang();
})(jQuery);
/**
*身份证验证
*/
function isChinaIDCard(num) {
	if (num == 111111111111111)
		return false;
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
		return false;
	}
	//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	//下面分别分析出生日期和校验位
	if (idcard_getAge(num) <= 17) {
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
}

function equalsSgsStr(val){
	if(val=='空'||val=='无'||val.indexOf("*")>-1||val.toLowerCase().indexOf("null")>-1
		||val.indexOf("/")>-1||val.toLowerCase().indexOf("test")>-1){
		return false;			
	}else if(getStrLength(val)<3){
		return false;	
	}else{			
		return true;
	}
}
//获得字符长度
function getStrLength(val) {  
	var str = new String(val);  
	var bytesCount = 0;  
	for (var i = 0 ,n = str.length; i < n; i++) {  
		var c = str.charCodeAt(i);  
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			bytesCount += 1;  
		} else {  
			bytesCount += 2;  
		}  
	}  
	return bytesCount;  
} 
function  checkShareHolderName(val) {
	var SSSBLX = $("input[name='SSSBLX']").val();
	var companyTypeCode = $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
	if (SSSBLX == '1' &&companyTypeCode == 'frdz') {
		if (val.indexOf("有限责任公司") < 0 && val.indexOf("有限公司") < 0) {
			return false;
		}
		if (val.indexOf("分公司") > -1 || val.indexOf("分店") > -1 || val.indexOf("分行") > -1 || val.indexOf("支行") > -1 || val.indexOf("营业厅") > -1
			|| val.indexOf("服务部") > -1 || val.indexOf("合伙") > -1
		) {
			return false;
		}
	}
	return true;
}
function  mustUpper(val) {
	if(val==val.toUpperCase()){
		return true;
	}else{
		return false;
	}
}

function  checkHhShareHolderName(val) {
	var SSSBLX = $("input[name='SSSBLX']").val();
	if (SSSBLX == '1' ) {
		if (val.indexOf("有限责任公司") >-1 || val.indexOf("有限公司")  > -1 || val.indexOf("合伙企业")  > -1) {
			return true;
		}
	}
	return false;
}



function idcard_getAge(id) {
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

function isMinCapital(val){
	var REGISTERED_CAPITAL = $("input[name='REGISTERED_CAPITAL']").val();
	if(val!=null&&val!=''){
		if(Number(val)<Number(REGISTERED_CAPITAL)){
			return false;			
		}else{			
			return true;
		}
	}        
	return false;
}
function equalsLegalIdno(val){
	var LEGAL_IDNO = $("input[name='LEGAL_IDNO']").val();
	if(val!=null&&val!=''&&LEGAL_IDNO!=null&&LEGAL_IDNO!=''){
		if(val==LEGAL_IDNO){
			return false;			
		}else{			
			return true;
		}
	}        
	return false;
}
function equalsDirectorOrManagerIdno(val){
	var isok = true;
	$("[name$='DIRECTOR_IDNO']").each(function(i){		
		var DIRECTOR_IDNO = $(this).val();
		if(DIRECTOR_IDNO==val){
			isok = false;	
		}
	});
	$("[name$='MANAGER_IDNO']").each(function(i){		
		var MANAGER_IDNO = $(this).val();
		if(MANAGER_IDNO==val){
			isok = false;	
		}
	});
	return isok;
}
function equalHhShareHolder(val) {
	var isok = true;
	var companyTypeCode = $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
	if (companyTypeCode == 'yxhhqy') {
		$("#gdxxDiv>div").each(function(i) {
			var DUTY_MODE = $(this).find("[name$='DUTY_MODE']").val();//股东姓名/名称
			var LICENCE_NO = $(this).find("[name$='LICENCE_NO']").val();//股东类型
			if(LICENCE_NO==val&&DUTY_MODE=='有限责任'){
				isok=false;
			}
		});
	}
	return isok;
}

function equalSF(val){
	var isok = true;
	if("SF"!=val){
		isok=false;
	}
	return isok;
}
function yearPaymentperiod(val){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var pamentPeriod=$("input[name='PAYMENT_PERIOD']").val();
	var pmonth=pamentPeriod.substr(5,2);
	pamentPeriod=pamentPeriod.substr(0,4);
	var num=pamentPeriod-year;
	var bussinessYears=$("input[name='BUSSINESS_YEARS']").val();
	var isok = true;
	if((num==bussinessYears&&bussinessYears!=''&&month<pmonth)||(num>bussinessYears&&bussinessYears!='')){
		isok=false;
	}
	return isok;
}

function isGtCurrentTime(val){
	var date = new Date();
	var valDate=new Date(val);
	if(valDate>date){
		return true;
	}else{
		return false;
	}
}


function  isEighteen(val) {
	var birthYear = val.substring(6,10);
	var nowYear = new Date().getFullYear();
	if(nowYear - birthYear>=18){
		return true;
	}else{
		return false;
	}
}


function equalsDirectorIdno(val){
	var arry={};
    var isok = true;
    $("#dsxxDiv").find("[name$='DIRECTOR_IDNO']").each(function(i){
        var DIRECTOR_IDNO = $(this).val();
        if(DIRECTOR_IDNO!=''){
			if(arry[DIRECTOR_IDNO]){
				isok = false;
			}
		}
        arry[DIRECTOR_IDNO]=true;
    });
    return isok;
}
function equalsJsIdNo(val){
	var arry={};
	var isok = true;
	var SSSBLX = $("input[name='SSSBLX']").val();
	if(SSSBLX=='1') {
		$("[name$='SUPERVISOR_IDNO']").each(function (i) {
			var SUPERVISOR_IDNO = $(this).val();
			if(SUPERVISOR_IDNO!=''){
			if (arry[SUPERVISOR_IDNO]) {
				isok = false;
			}
			}
			arry[SUPERVISOR_IDNO] = true;
		});
	}
	return isok;
}
function verifyCompanyNameExist(field){
	var companyId = $("#COMPANY_ID").val();
	var individualId = $("#INDIVIDUAL_ID").val();
	var solelyId = $("#SOLELY_ID").val();
	var SSSBLX = $("input[name='SSSBLX']").val();
	var itemCode = $("input[name='ITEM_CODE']").val();
	var fieldValue = field.val();
	if (SSSBLX == '1'&&(itemCode=='201605170002XK00101'||itemCode=='201605170002XK00105')) {
		 fieldValue = $("input[name='COMPANY_NAME']").val();
	}
	var resultJson=null;
	$.ajax({
        type: "GET",
        url: "baseController.do?verifyCompanyNameExist",
        data: { "companyId": companyId, "individualId": individualId
        	,"fieldValue":fieldValue,"solelyId":solelyId
        },
        async: false, 
        cache: false,
        success: function (responseText) {
        	resultJson = $.parseJSON(responseText);
        }
    });
	if(resultJson=='true'){
		return true;
	}else{
		if (SSSBLX == '1' && itemCode=='201605170002XK00104') {
			$("[name='ZH_NUM']").show();
			$("[name='ZH_NUM']").css("width","125px");
		}
		return false;
	}
}
function verifyCompanyEngExist(field){
	var companyId = $("#COMPANY_ID").val();
	var fieldValue=field.val();
	var resultJson=null;
	$.ajax({
	    type: "GET",
	    url: "baseController.do?verifyCompanyEngExist",
	    data: { "companyId": companyId,"fieldValue":fieldValue},
	    async: false, 
	    cache: false,
	    success: function (responseText) {
	        resultJson = $.parseJSON(responseText);
	    }
	});
	if(resultJson=='true'){
		return true;
	}else{
		return false;
	}	
}

