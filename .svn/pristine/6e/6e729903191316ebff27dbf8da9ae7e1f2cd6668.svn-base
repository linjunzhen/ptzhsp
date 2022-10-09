/**
 *@Name: 入口
 */

layui.define([ 'layer', 'laytpl'], function(exports) {

	var $ = layui.jquery,
		layer = layui.layer,
		laytpl = layui.laytpl,
		device = layui.device(),
		DISABLED = 'layui-btn-disabled';
		
	
	//阻止IE7以下访问
	if (device.ie && device.ie < 10) {
		layer.alert('如果您非得使用 IE 浏览器访问，那么请使用 IE10+');
	}

	
	//数字前置补零
	layui.laytpl.digit = function(num, length, end) {
		var str = '';
		num = String(num);
		length = length || 2;
		for (var i = num.length; i < length; i++) {
			str += '0';
		}
		return num < Math.pow(10, length) ? str + (num | 0) : num;
	};
	var project = {
		dir : layui.cache.host + '/plug-in/layui-v2.4.5/layui/extend/', //模块路径     //Ajax
		json : function(url, data, success, options) {
			var that = this,
				type = typeof data === 'function';

			if (type) {
				options = success
				success = data;
				data = {};
			}

			options = options || {};

			return $.ajax({
				type : options.type || 'post',
				dataType : options.dataType || 'json',
				data : data,
				url : url,
				success : function(res) {
					if(res.code === 99){							
						window.location.href = layui.cache.host+"login";
					} else if(res.code === 400){							
						window.location.href = layui.cache.host+"400.jsp";
					} else if(res.code === 401){							
						window.location.href = layui.cache.host+"401.jsp";
					} else if(res.code === 403){							
						window.location.href = layui.cache.host+"403.jsp";
					} else if(res.code === 404){							
						window.location.href = layui.cache.host+"404.jsp";
					} else if(res.code === 500){							
						window.location.href = layui.cache.host+"500.jsp";
					} else if(res.code === 205){							
						window.location.href = layui.cache.host+"205.jsp";
					} else{							
						success && success(res);
					}								
				},
				error : function(e) {
					layer.msg('请求异常，请重试', {
						shift : 6
					});
					options.error && options.error(e);
				}
			});
		}, //计算字符长度
		charLen : function(val) {
			var arr = val.split(''),
				len = 0;
			for (var i = 0; i < val.length; i++) {
				arr[i].charCodeAt(0) < 299 ? len++ : len += 2;
			}
			return len;
		}
	};

	

	var getTpl = xmdwxxTpl.innerHTML
	,xmdwxxDiv = document.getElementById('xmdwxxDiv');
	laytpl(getTpl).render($.parseJSON(layui.cache.lerepInfoData), function(html){
		xmdwxxDiv.innerHTML = html;
	}); 
	exports('project', project);
		
});