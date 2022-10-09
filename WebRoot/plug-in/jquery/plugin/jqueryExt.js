$.extend({
	/**
	 * 获取项目根路径,依赖于/js/dynamic.jsp文件，要先于此js文件引入
	 */
	"getRootPath" : function() {
		return __ctxPath ? __ctxPath : "/";
	},
	/**
	 * 功能：获取URL上的参数值，searchString：查询字符串，paras：参数名 
	 * 
	 * 用例：$.getParamFromUrl("name=ted&sex=male", "name") ==> "ted"
	 */
	"getParamFromUrl" : function(searchString, paras) { 
        var paraString = searchString.split("&"); 
        var paraObj = {}; 
        for (i = 0; j = paraString[i]; i++) { 
        	if (paraObj[j.substring(0, j.indexOf("=")).toLowerCase()]) {
        		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] + "," +j.substring(j.indexOf("=") + 1, j.length); 
        	} else {
        		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length); 
        	}
        } 
        var returnValue = paraObj[paras.toLowerCase()]; 
        if (typeof(returnValue) == "undefined") { 
            return ""; 
        } else { 
            return returnValue; 
        }
    },
	/**
	 * 功能：获取当前URL上的参数值 
	 * http://localhost:8080/fzgaweb/page/onlineWork/sceneNavi/cjs.html?type=%E6%88%B7%E6%94%BF%E5%8A%9E%E7%90%86
	 * 用例：$.getUrlParam("type") ==> "%E6%88%B7%E6%94%BF%E5%8A%9E%E7%90%86"
	 */
    "getUrlParams": function () {
		var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for (var i = 0; i < hashes.length; i++) {
	      	hash = hashes[i].split('=');
	      	if (vars[hash[0]]) {
	      		vars[hash[0]] = vars[hash[0]] + "," + hash[1];
	      	} else {
	      		vars.push(hash[0]);
	      		vars[hash[0]] = hash[1];
	      	}
	    }
	    return vars;
    },
	/**
	 * 功能：获取当前URL上的参数值 
	 * http://localhost:8080/fzgaweb/page/onlineWork/sceneNavi/cjs.html?type=%E6%88%B7%E6%94%BF%E5%8A%9E%E7%90%86
	 * 用例：$.getUrlParam("type") ==> "%E6%88%B7%E6%94%BF%E5%8A%9E%E7%90%86"
	 */
	"getUrlParam": function(name) {
		return $.getUrlParams()[name];
	},    
	
	/**
	 * 两个问题：
	 * （1）获取值的时候，要判断是不是默认提示的值
	 * （2）浏览器后退时候，历史关键字变成提示值
	 * 
	 * 要求的dom元素
	 * <input id="keyword" name="keyword" type="text" class="input2" style="color:#CCC;" value="请输入要查询的办事指南关键字"/>
	 */
	"blurEffect": function (id) {
		var _inputStr = "";
		var _color = "";
		$("input#" + id).focus(function() {
			if ($(this).attr("ischange") != "1") {
				_inputStr = $(this).attr("value");
				_color = $(this).css("color");
				$(this).css("color", "#000").attr({
					"value" : ""
				});
			}
		}).blur(function() {
			if ($(this).attr("value") == "") {
				$(this).attr({
					"value" : _inputStr,
					"ischange" : 0
				}).css("color", _color);
			}
		}).change(function() {
			$(this).attr("ischange", 1)
		});
	},
	
	/**
	 * 功能：new Date() 对象格式化字符串
	 * 
	 * 用例：$.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	 */
	"formatDate" : function (date, fmt) {
		var o = {
			"M+" : date.getMonth() + 1, // 月份
			"d+" : date.getDate(), // 日
			"h+" : date.getHours(), // 小时
			"m+" : date.getMinutes(), // 分
			"s+" : date.getSeconds(), // 秒
			"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds() // 毫秒
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for (var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	}, 
	
	/**
	 * 功能：获取网页可视高度 支持IE6789 Firefox Chrome
	 * 
	 * 用例：$.getClientHeight()
	 */	
	"getClientHeight" : function() {
		var clientHeight = document.body.clientHeight;//其它浏览器默认值
		if (navigator.userAgent.indexOf("MSIE 6.0") != -1) {
			clientHeight = document.body.clientHeight;
		} else if (navigator.userAgent.indexOf("MSIE") != -1) {
			//IE7 IE8
			clientHeight = document.documentElement.offsetHeight
		}
		if (navigator.userAgent.indexOf("Chrome") != -1) {
			clientHeight = document.body.scrollHeight;
		}
		if (navigator.userAgent.indexOf("Firefox") != -1) {
			clientHeight = document.documentElement.scrollHeight;
		}
		return clientHeight;
	},
	
	/**
	 * 功能：获取网页内容高度 支持IE6789 Firefox Chrome
	 * 
	 * 用例：$.getContentHeight()
	 */	
	"getContentHeight" : function() {
		var ContentHeight = document.body.scrollHeight;//其它浏览器默认值
		if (navigator.userAgent.indexOf("Chrome") != -1) {
			ContentHeight = document.body.clientHeight;
		}
		if (navigator.userAgent.indexOf("Firefox") != -1) {
			ContentHeight = document.body.offsetHeight;
		}
		return ContentHeight;
	},
	
	/**
	 * 功能：增加命名空间到jquery下
	 *  
	 * 用例：$.namespace("$.a.b", "$.b.a") 创建了两个命名空间
	 */	
	"createNameSpace" : function() {
	    var a = arguments, o = null;   
	    for (var i = 0; i < arguments.length; i++) {
	        var d = a[i].split(".");   
	        o = $;   
	        for (var j = (d[0] == "$" ? 1 : 0); j < d.length ; j++) {
	            o[d[j]] = o[d[j]] || {};   
	            o = o[d[j]];   
	        }   
	    }   
	    return o;   
	},
	/**
	 * 功能：将表单元素序列化成对象
	 *  
	 * 用例：$.serializeObject($(form)) ---> {"name" : "张三", "sex" : 1 }
	 */	
	"serializeObject" : function(form) {
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
		return o;
	},
	/**
	 * 身份证15位编码规则：dddddd yymmdd xx p dddddd：地区码 yymmdd: 出生年月日 xx: 顺序类编码，无法确定 p: 性别，奇数为男，偶数为女
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y dddddd：地区码 yyyymmdd: 出生年月日 xxx:顺序类编码，无法确定，奇数为男，偶数为女 y: 校验码，该位数值可通过前17位计算获得
	 * 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]
	 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ] 
	 * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
	 * i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置
	 */
	"validIdCard": function(cardno) {
		var o = {
			"Wi": [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1], // 加权因子
			"ValideCode": [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2], // 身份证验证位值.10代表X
			"isTrueValidateCodeBy18IdCard": function(a_idCard) {
				var sum = 0; // 声明加权求和变量
				if (a_idCard[17].toLowerCase() == 'x') {
					a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作
				}
				for (var i = 0; i < 17; i++) {
					sum += this.Wi[i] * a_idCard[i];// 加权求和
				}
				valCodePosition = sum % 11;// 得到验证码所位置
				if (a_idCard[17] == this.ValideCode[valCodePosition]) {
					return true;
				} else {
					return false;
				}
			},
			"maleOrFemalByIdCard": function(idCard) {
				idCard = $.trim(idCard.replace(/ /g, ""));// 对身份证号码做处理。包括字符间有空格。
				if (idCard.length == 15) {
					if (idCard.substring(14, 15) % 2 == 0) {
						return 'female';
					} else {
						return 'male';
					}
				} else if (idCard.length == 18) {
					if (idCard.substring(14, 17) % 2 == 0) {
						return 'female';
					} else {
						return 'male';
					}
				} else {
					return null;
				}
			},
			"isValidityBrithBy18IdCard": function (idCard18) {
				var year = idCard18.substring(6, 10);
				var month = idCard18.substring(10, 12);
				var day = idCard18.substring(12, 14);
				var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
				// 这里用getFullYear()获取年份，避免千年虫问题
				if (temp_date.getFullYear() != parseFloat(year)
						|| temp_date.getMonth() != parseFloat(month) - 1
						|| temp_date.getDate() != parseFloat(day)) {
					return false;
				} else {
					return true;
				}
			},
			"isValidityBrithBy15IdCard": function (idCard15) {
				var year = idCard15.substring(6, 8);
				var month = idCard15.substring(8, 10);
				var day = idCard15.substring(10, 12);
				var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
				// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
				if (temp_date.getYear() != parseFloat(year)
						|| temp_date.getMonth() != parseFloat(month) - 1
						|| temp_date.getDate() != parseFloat(day)) {
					return false;
				} else {
					return true;
				}
			},
			"IdCardValidate": function (idCard) {
				if (/ /g.test(idCard)) { // 判断空格
					return false;
				}
				idCard = $.trim(idCard.replace(/ /g, ""));
				if (idCard.length == 15) {
					return this.isValidityBrithBy15IdCard(idCard);
				} else if (idCard.length == 18) {
					var a_idCard = idCard.split("");// 得到身份证数组
					if (this.isValidityBrithBy18IdCard(idCard)
							&& this.isTrueValidateCodeBy18IdCard(a_idCard)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		};
		return o.IdCardValidate(cardno);
	},
	/**
	 * 功能：判断ie版本,目前只检测ie6，ie7，ie7文档模式
	 * 返回值：ie6，ie7，ie7Compatible
	 *  
	 * 用例：1、存在easyui库：$.checkIeVersion() ---> 检测完会弹出easyui的alert
	 * 		 2、不存在easyui库：$.checkIeVersion(function(retValue, msg) {}); 自己扩展回调函数做处理
	 */	
	"checkIeVersion": function(rootPath, fn) {
		var isIE=!!window.ActiveXObject;
   		var ieMode = document.documentMode;
   		var isIE6 = !window.XMLHttpRequest;
   		var isIE7 = isIE&&!isIE6 && !ieMode;
   		var isIE7Compatible = ieMode <= 7;
   		var retValue = "";
		if (isIE6) {
			retValue = "ie6";
 		} else if (isIE7) {
 			retValue = "ie7";
 		} else if (isIE7Compatible) {
 			retValue = "ie7Compatible";
 		}
		function retMsg(msg, rootPath, isCompatible) {
   			if (isCompatible) {
  				return "您的浏览器正处于" + msg + "或以下级别的文档模式，不适合本系统的运维操作，建议下载并安装<a href='"+rootPath+"/help/Firefox-full-latest.exe'><span style='color:blue'>火狐浏览器</span></a>。";
   			} else {
  				return "您现在使用的是" + msg + "版本的浏览器，不适合本系统的运维操作，建议下载并安装<a href='"+rootPath+"/help/Firefox-full-latest.exe'><span style='color:blue'>火狐浏览器</span></a>。";
   			}
   		}
 		if (retValue != "") {
 			if (typeof fn === "function") {
 				fn(retValue, retMsg(retValue, rootPath, retValue === "ie7Compatible"? true : false));
 			} else if ($.messager) {
				$.messager.alert("", retMsg(retValue, rootPath, retValue === "ie7Compatible"? true : false));
 			} 
 		}
	},
	"mask": function() {
		$.blockUI({ 
			message: "<h4><img src='" + this.getRootPath()  + "/images/busy.gif' />&nbsp;操作比较耗时，请等待...</h4>",
			"css": {
				top: '20%',
				left: '35%'
			}
		}); 
	},
	"unmask": function() {
		$.unblockUI(); 
	}
});
