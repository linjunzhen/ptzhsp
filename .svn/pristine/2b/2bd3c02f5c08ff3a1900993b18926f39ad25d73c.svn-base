/**
 *@Name: 自定义验证
 */
layui.define(function(exports) {
	var formverify;
	formverify = {
		getVerify:function(){
			var v = {
			  username: function(value, item){ //value：表单的值、item：表单的DOM对象
				if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
				  return '用户名不能有特殊字符';
				}
				if(/(^\_)|(\__)|(\_+$)/.test(value)){
				  return '用户名首尾不能出现下划线\'_\'';
				}
			  }
			  ,usernameLength: [
				/^[\S]{6,12}$/
				,'用户名必须6到12位，且不能出现空格'
			  ] 
			  //我们既支持上述函数式的方式，也支持下述数组的形式
			  //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
			  ,pass: [
				/^[\S]{6,12}$/
				,'密码必须6到12位，且不能出现空格'
			  ] 
			}
			return v;
		}
	};
    exports('formverify', formverify);
});