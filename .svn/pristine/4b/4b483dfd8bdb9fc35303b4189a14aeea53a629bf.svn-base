(function($){
	// 验证规则
	$.fn.validationEngineLanguage = function(){};
	$.validationEngineLanguage = {
		newLang:function(){
			$.validationEngineLanguage.allRules = {
				//验证值密码强度
				"verifyCheckPwd":{
					"url": 'baseController.do?verifyCheckPwd',     
				    "alertTextOk": "密码可以使用",//验证通过时的提示信息
			        "alertText": "密码不符合要求,请重新输入!",                     //验证不通过时的提示信息
			        "alertTextLoad": "正在验证中..."        //正在验证时的提示信息
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
					"alertText":"* 无效的正整数"
				},
				"JustNumber":{
                    // Number, including positive, negative, and floating decimal. credit:orefalo
                    "regex": /^((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
                    "alertText":"* 无效的数值"
                },
				"number":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
                    "regex": /^[\-\+]?((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
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
					"alertText":"* 只能填写英文字母、数字、特殊字符22"
				},
				//排除掉特殊字符
				"offSpecialCharacters":{
					"regex":/^[a-zA-Z\d\u4e00-\u9fa5]+$/,
					"alertText":"* 此处不能包含特殊字符"
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
					"regex": /^\d+(\.\d{1,2})?$/,
					"alertText":"* 输入有效数字，保留小数点后两位"
				},
				"numberp6plus":{
					// Number, including positive, negative, and floating decimal. credit:orefalo
					//"regex": /^[+]?[1-9][\d]*(([\.]{0,1}[\d]+)){0,1}|[1-9]([\d]*))$/,
                    //"regex": /^[+]?[1-9]{1}\d*|[1-9]{1}\d*$/,
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
					"regex":/^(13[0-9]|15[012356789]|16[257]|17[01678]|18[0-9]|19[0-9]|14[57])[0-9]{8}$/,
					"alertText":"* 无效的手机号码"
				},
				"mobilePhoneLoose":{
					"regex":/^(0[0-9]|13[0-9]|15[0-9]|16[2567]|17[0-9]|18[0-9]|19[0-9]|14[57])[0-9]{8}$/,
					"alertText":"* 无效的手机号码"
				},
                "estateNum":{
                    "regex":/^(\d{6})([a-zA-Z0-9]{6})([a-zA-Z]{2})([a-zA-Z0-9]{14})$/,
                    "alertText":"* 无效的不动产单元号"
                },
				"fixPhoneWithAreaCode":{
					"regex":/^(\d{3,4}-)\d{7,8}$/,
					"alertText":"* 无效的 固定电话号码<br/>&nbsp;&nbsp;格式必须为 区号-号码 ,其中区号3位或4位、号码7位或8位"
				},
				"fixOrMobilePhone":{
					"regex":/(^(13[0-9]|15[0-9]|16[6]|17[0-9]|18[0-9]|19[89]|14[57])[0-9]{8}$)|(^(\d{3,4}-)\d{7,8}$)/,
					"alertText":"* 无效的手机号码或固定电话<br/>&nbsp;&nbsp;手机号码必须为13、14、15、18开头的11位数字<br/>&nbsp;&nbsp;固定电话号码格式格式必须为 区号-号码 ,其中区号3位或4位、号码7位或8位"
				},
				"spfZzbm":{
					"regex":/^(\(|\（)(\d{4})(\)|\）)(岚综实房许字第)(\d{2})(号)$/,
					"alertText":"* 无效的商品房许可证编码"
				},
				"czpwZzbm":{
					"regex":/^(\d{10})(字第)(\d{4})(号)$/,
					"alertText":"* 无效的城镇排污许可证编码"
				},
				"lmcfZzbm":{
					"regex":/^(\d{4})(集采字\[)(\d{4})(\])(\d{3})(号)$/,
					"alertText":"* 无效的林木采伐许可证编码"
				},
				"chineseorLetter":{
					"regex":/^[\u4e00-\u9fa5]|[a-zA-Z]+$/,
					"alertText":"* 请输入中文或者英文字母"
				},
				"onlychineseLetter":{
					"regex":/^([\u4e00-\u9fa5]{1,100}|[a-zA-Z\.\s]{1,100})$/,
					"alertText":"* 请输入全中文或者全英文字母"
				},
				"vidcard":{
					"func" : function(field,rules,i,options) {
						return isChinaIDCard(field.val());
					},
					"alertText" : '请输入正确的身份证号码'
				},
				"Enpassword3":{
					"regex":/^(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\W]+$)(?![a-z\W]+$)(?![a-zA-Z]+$)(?![A-Z\W]+$)[a-zA-Z0-9\W_]+$/,
					"alertText":"* 请输入特殊字符加字母加数字的<br/>组合密码"
				},
				"Enpassword":{
					"regex":/([0-9].*([a-zA-Z].*[\W]|[\W].*[a-zA-Z])|[a-zA-Z].*([0-9].*[\W]|[\W].*[0-9])|[\W].*([0-9].*[a-zA-Z]|[a-zA-Z].*[0-9]))/,
					"alertText":"* 密码必须包含字母数字及特殊字符"
				},

				"allurl":{
					"func" : function(field,rules,i,options) {
						return IsURL(field.val());
					},
					"alertText" : '请输入正确的网址'
				},
				"equalsDlrZjhm":{
					"func" : function(field,rules,i,options) {
						return equalsDlrZjhm(field.val());
					},
					"alertText" : '义务人代理人不能与权利人代理人相同'
				},
				"equalsDlrSjhm":{
					"func" : function(field,rules,i,options) {
						return equalsDlrSjhm(field.val());
					},
					"alertText" : '义务人代理人手机号码不能与权利人代理人手机号码相同'
				},
				"equalsYgdyqDlrZjhm":{
					"func" : function(field,rules,i,options) {
						return equalsYgdyqDlrZjhm(field.val());
					},
					"alertText" : '义务人代理人不能与权利人代理人相同'
				},
				"equalsYgdyqDlrSjhm":{
					"func" : function(field,rules,i,options) {
						return equalsYgdyqDlrSjhm(field.val());
					},
					"alertText" : '义务人代理人手机号码不能与权利人代理人手机号码相同'
				},
				"equalsQlrSjhm":{
					"func" : function(field,rules,i,options) {
						return equalsQlrSjhm(field.val());
					},
					"alertText" : '权利人手机号码不能相同'
				},
				"equalsYwrSjhm":{
					"func" : function(field,rules,i,options) {
						return equalsYwrSjhm(field.val());
					},
					"alertText" : '义务人手机号码不能相同'
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
	num = num.toUpperCase();
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
		return false;
	}
	//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	//下面分别分析出生日期和校验位
	if (idcard_getAge(num) < 0) {
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

function IsURL (str_url) {
    var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
        + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@ 
        + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184 
        + '|' // 允许IP和DOMAIN（域名） 
        + '([0-9a-z_!~*\'()-]+.)*' // 域名- www. 
        + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名 
        + '[a-z]{2,6})' // first level domain- .com or .museum 
        + '(:[0-9]{1,4})?' // 端口- :80 
        + '((/?)|' // a slash isn't required if there is no file name 
        + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
    var re=new RegExp(strRegex);
    if (re.test(str_url)) {
        return (true);
    } else {
        return (false);
    }
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
function equalsDlrZjhm(val){
	var LZRZJHM = $("input[name='LZRZJHM']").val();
	if(val!=null&&val!=''&&LZRZJHM!=null&&LZRZJHM!=''){
		if(val==LZRZJHM){
			return false;			
		}else{			
			return true;
		}
	}        
	return true;
}
function equalsDlrSjhm(val){
	var LZRSJHM = $("input[name='LZRSJHM']").val();
	if(val!=null&&val!=''&&LZRSJHM!=null&&LZRSJHM!=''){
		if(val==LZRSJHM){
			return false;			
		}else{			
			return true;
		}
	}        
	return true;
}

function equalsYgdyqDlrZjhm(val){
	var DLR2_ZJNO = $("input[name='DLR2_ZJNO']").val();
	if(val!=null&&val!=''&&DLR2_ZJNO!=null&&DLR2_ZJNO!=''){
		if(val==DLR2_ZJNO){
			return false;			
		}else{			
			return true;
		}
	}        
	return true;
}
function equalsYgdyqDlrSjhm(val){
	var DLR2_SJHM = $("input[name='DLR2_SJHM']").val();
	if(val!=null&&val!=''&&DLR2_SJHM!=null&&DLR2_SJHM!=''){
		if(val==DLR2_SJHM){
			return false;			
		}else{			
			return true;
		}
	}        
	return true;
}
function equalsQlrSjhm(val){	
	var dataArray = []; 
	$("#qlrDiv").children("div").each(function(i){
		var MSFSJHM = $(this).find("[name$='MSFSJHM']").val();//手机号码	
		if(MSFSJHM){
			dataArray.push(MSFSJHM);		
		}
	});	
	if(isSjhmRepeat(dataArray)){
		return false;			
	}else{			
		return true;
	}
}
function equalsYwrSjhm(val){	
	var dataArray = []; 
	$("#ywrDiv").children("div").each(function(i){
		var YWR_SJHM = $(this).find("[name$='YWR_SJHM']").val();//手机号码	
		if(YWR_SJHM){
			dataArray.push(YWR_SJHM);		
		}
	});	
	if(isSjhmRepeat(dataArray)){
		return false;			
	}else{			
		return true;
	}
}

function isSjhmRepeat(arr){
	var hash = {};
	for(var i in arr) {
		if(hash[arr[i]])
			return true;
		hash[arr[i]] = true;
	}
	return false;
}
