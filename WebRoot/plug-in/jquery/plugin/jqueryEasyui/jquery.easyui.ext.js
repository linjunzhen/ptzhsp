$.extend($.fn.validatebox.defaults.rules, {
	phoneOrMobile:{//验证手机或电话
		validator : function(value) {
			return /^(13|14|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message:'请填入手机或电话号码,如13688888888或020-8888888'
	},
	currency : {// 验证货币
		validator : function(value) {
			return /^d{0,}(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确(正确如：453384319)'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	faxno : {// 验证传真
		validator : function(value) {
//			return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '传真号码不正确'
	},
	zip : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确'
	},
	name : {// 验证姓名，可以是中文或英文
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '请输入姓名'
	},
	carNo:{
		validator : function(value){
			return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value); 
		},
		message : '车牌号码无效（例：粤J12350）'
	},
	carenergin:{
		validator : function(value){
			return /^[a-zA-Z0-9]{16}$/.test(value); 
		},
		message : '发动机型号无效(例：FG6H012345654584)'
	},
	msn:{
		validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		},
		message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
	},
	
	
	
	
	isblank : { // 验证是否包含空格和非法字符
		validator : function(value) {
			return !/^\s+$/.test(value);
		},
		message : '不能为空白字符'
	},
	maxLength: { // 判断最小长度
		validator: function(value, param) {
			return value.length <= param[0];
		},
		message: '只能输入 {0} 个字符。'
	},
	minLength : { // 判断最小长度
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '最少输入 {0} 个字符。'
	},
	length : {
		validator:function(value, param) {
			var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
		message : "内容长度介于{0}和{1}之间."
	},
	floatOrInt : { // 验证是否为小数或整数
		validator : function(value) {
			return /^[-+]?(\d*\.)?\d+$/.test(value);
		},
		message : '请输入数字，并保证格式正确'
	},
	integer : {// 验证正整数
		validator : function(value) {
			return /^[+]?[0-9]+\d*$/i.test(value);
		},
		message : '请输入数字'
	},
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value);
		},
		message : '请输入中文'
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	email:{
		validator : function(value){
		return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		},
		message : '请输入有效的电子邮件账号(例：abc@126.com)'	
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^1[3|4|5|8][0-9]\d{8}$/.test(value);
		},
		message : '请输入正确的手机号码'
	},
	telephone: {
		/**
		 * 匹配格式：
		 * 3-4位区号，7-8位直播号码
		 */
		validator : function(value) {
			return /^(\d{3,4}-)*\d{7,8}$/.test(value);
		},
		message : '请输入正确的电话号码'
	},
	postcode: {// 中国邮编
		validator : function(value) {
			return /^[1-9]\d{5}$/.test(value);
		},
		message : '请输入正确的邮政编码'
	},
	idcard: {// 验证身份证
		validator : function(value) {
			return $.validIdCard(value);
		},
		message : '请输入正确的身份证号码'
	},
	vidcard:{
		validator : function(value) {
			return isChinaIDCard(value);
		},
		message : '请输入正确的身份证号码'
	},
	money: {
		validator : function(value){
			return /^-?\d{1,11}(\.\d{1,2})?$/.test(value); 
		},
		message : '请输入有效的金额格式,如:1.00'
	},
	samePassword: {
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value; 
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'	
	}
});

/**
 * 扩展datagrid，动态添加删除toolbar的项
 * 添加或删除时，都会重新计算数组下标
 */ 
$.extend($.fn.datagrid.methods, {  
    addToolbarItem: function(jq, items) {  
        return jq.each(function() {  
            var toolbar = $(this).parent().prev("div.datagrid-toolbar");
            for (var i = 0; i<items.length; i++) {
                var item = items[i];
                if (item === "-") {
                    toolbar.append('<div class="datagrid-btn-separator"></div>');
                } else {
                    var btn = $("<a href=\"javascript:void(0)\"></a>");
                    btn[0].onclick = eval(item.handler||function(){});
                    btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
                }
            }
            toolbar = null;
        });  
    },
    removeToolbarItem: function(jq, param) {  
        return jq.each(function() {  
            var btns = $(this).parent().prev("div.datagrid-toolbar").children("a");
            var cbtn = null;
            if(typeof param == "number") {
                cbtn = btns.eq(param);
            } else if (typeof param == "string") {
                var text = null;
                btns.each(function() {
                    text = $(this).data().linkbutton.options.text;
                    if (text == param) {
                        cbtn = $(this);
                        text = null;
                        return;
                    }
                });
            } 
            if (cbtn) {
                var prev = cbtn.prev()[0];
                var next = cbtn.next()[0];
                if (prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName) {
                   $(prev).remove();
                } else if (next && next.nodeName == "DIV") {
                   $(next).remove();
                } else if (prev && prev.nodeName == "DIV") {
                   $(prev).remove();
                }
                cbtn.remove();    
                cbtn= null;
            }                        
        });  
    }                 
});

/**
 * 功能：扩展treegrid方法，获得一级子节点
 */
$.extend($.fn.treegrid.methods, {
    getOneLevelChildren: function(jq, id) {
        var arr = [];
        jq.each(function(i) {
            var children = $(this).treegrid("getChildren", id);
            $.each(children, function() {
                if (this._parentId == id ) {
                    arr.push(this);
                }
            });
        });
        return arr;
    }
});  

/**
 * 功能：改写datebox的formatter方法，使返回日期格式为yyyy-mm-dd
 */
$.fn.datebox.defaults.formatter = function(date) { 
    var y = date.getFullYear(); 
    var m = date.getMonth() + 1; 
    var d = date.getDate(); 
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d); 
}; 

/**
 * 功能：改写datebox的parser方法，使其支持yyyy-mm-dd格式日期的解析
 */
$.fn.datebox.defaults.parser = function(s) { 
    if (s) { 
        var a = s.split('-'); 
        var d = new Date(parseInt(a[0]), parseInt(a[1]) - 1, parseInt(a[2])); 
        return d; 
    } else { 
        return new Date(); 
    } 
}; 

/**
 * 功能：使panel加载时提示
 * 
 */
$.fn.panel.defaults.loadingMessage = "加载中。。。";

$.extend($.fn.datagrid.defaults.editors, {   
	progressbar: {   
		init: function(container, options){   
			var bar = $('<div/>').appendTo(container);
			bar.progressbar(options); 				
			return bar;   
		},   
		getValue: function(target){   
			return $(target).progressbar('getValue');   
		},
		setValue: function(target, value){   
			$(target).progressbar('setValue',value);   
		},
		resize: function(target, width){    
			if ($.boxModel == true){   
				$(target).progressbar('resize',width - (input.outerWidth() - input.width()));
			} else {   
				$(target).progressbar('resize',width);
			}   
		}   
	},
	slider: {   
		init: function(container, options){   
			var slider = $('<div/>').appendTo(container);
			slider.slider(options); 				
			return slider;   
		},   
		getValue: function(target){   
			return $(target).slider('getValue');   
		},
		setValue: function(target, value){   
			$(target).slider('setValue',value);   
		},
		resize: function(target, width){    
			if ($.boxModel == true){   
				$(target).progressbar('slider',{width:width - (input.outerWidth() - input.width())});
			} else {   
				$(target).progressbar('slider',{width:width});
			}   
		}   
	}				
}); 