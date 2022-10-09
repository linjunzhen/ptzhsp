$(function(){
	var userAgent = navigator.userAgent.toLowerCase(); 
	browser={ 
	version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [0,'0'])[1], 
	safari: /webkit/.test( userAgent ), 
	opera: /opera/.test( userAgent ), 
	msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ), 
	mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent ) 
	} 
	if(browser.msie&&(parseInt(browser.version)<=7)){ 
		layer.alert("您的浏览器正处于ie7或以下级别的文档模式，不适合本系统的运维操作，建议下载并安装<a href='plug-in/browser-1.0/ie8.rar'>ie8</a>或<a href='plug-in/browser-1.0/Firefox-full-latest.exe'>火狐浏览器</a>。"); 
	} 
	
	var username = $("input[name=username]").val();
	if(username!=""&&username!=null){
		$("#usernameInputHint").html("");
	}
	var password = $("input[name=password]").val();
	if(password!=""&&password!=null){
		$("#passwordInputHint").html("");
	}
});

function enterSystem(){
	//判断浏览器是否为ie6,7,8
    if (!$.support.leadingWhitespace) {
       if(event.keyCode == 13){
	    	checkUserInfo();
	    }
    }else{
    	event=arguments.callee.caller.arguments[0] || window.event; 
	    if(event.keyCode == 13){
	    	checkUserInfo();
	    }
    }
}

function checkUserInfo(){
	var username = $("#username").val();
	var password = $("#password").val();
	if(!(username!=null&&username!="")){
		alert("请输入用户帐号!");
		return;
	}
	if(!(password!=null&&password!="")){
		alert("请输入密码!");
		return;
	}
	$("#loginForm").submit();
}
/**
 * 清除输入提示
 * @param {} hintId
 */
function clearInputHint(hintId){
	$("#"+hintId).html("");
};
/**
 * 显示输入提示
 * @param {} hintId
 */
function showInputHint(hintId){
	if(hintId=="usernameInputHint"){
		var username = $("input[name=username]").val();
		if(!(username!=""&&username!=null)){
			$("#"+hintId).html("请输入用户帐号");
		}
	}else{
		var password = $("input[name=password]").val();
		if(!(password!=""&&password!=null)){
			$("#"+hintId).html("请输入密码");
		}
	}
}