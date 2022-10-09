var devModel=0;//默认非开发模式
/*
 * 根不同浏览器提供不同的flashPlayer
 */
function getFlashPlayer(path,o) {
	if ($.browser.msie) {
		o.href=path+'/common/install_flash_player_ax.exe';
	}else{
		o.href=path+'/common/install_flash_player.exe';
	}
}
/* 
 * 用js创建个表单来提交，调用方式：
 * 
 */
function morepost(url, params) {
    var temp = document.createElement("form");        
    temp.action = url;        
    temp.method = "post";
    temp.target = "_blank";
    temp.style.display = "none";        
    for (var x in params) {        
        var opt = document.createElement("input");
        opt.type="text";
        opt.name = x;       
        opt.value = params[x];        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);
    temp.submit();
}  
/**
    * 图片不存在时，显示一个默认的图片  <img src="${Image}" onerror="defimg(this);" >
    * @param path 工程路径，
    * @return 
    */ 
function defimg(path,obj){
 obj.src=path+'/common/images/s.gif';
 obj.onerror = null;//控制onerror事件只触发一次
}
jQuery.fn.extend({
	/**
    * 图片新闻   
    * @param t 图片新闻div对像  
    * @return 
    */ 
    picnews:function(t){
      return this.each(function(){
        var id="#"+$(this).attr("id");
        var n = -1, i_count,interval="<ul class='picnews_ul'>";
        i_count=$(id+"_list a").length;
        for(var i=0;i<i_count;i++){
            interval+="<li>"+(i+1)+"</li>";
        }
        $(id+"_info").after(interval+"<\/ul>");
        $(id+" li").click(function(e) {
            e.stopPropagation();
            n=$(this).index();
            $(id+"_info").html($(id+"_list a").eq(n).find("img").attr('alt'));
            $(id+"_list a").filter(":visible").fadeOut(500).parent().children().eq(n).fadeIn(1000);
            $(this).addClass("picnews_on");
            $(this).siblings().removeAttr("class");
        });
        interval = setInterval(showAuto, t);
        $(this).hover(function(){clearInterval(interval)}, function(){interval = setInterval(showAuto, t)});
        function showAuto(){
            n = n >=(i_count-1) ? 0 : ++n;
            $(id+" li").eq(n).click();
        }
        showAuto();
      })
    }
});
	
function fontZoom(size){
 document.getElementById('fontzoom').style.fontSize=size+'px'
}
function nowtime(ctimes){
       var now = new Date(ctimes);  
       var year = now.getFullYear();       //年
       var month = now.getMonth() + 1;     //月
       var day = now.getDate();            //日       
       var hh = now.getHours();            //时
       var mm = now.getMinutes();          //分
       var ss = now.getSeconds();
	   
        var clock = year + "年";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "月";
       
        if(day < 10)
            clock += "0";
           
        clock += day + "日 ";
       
        if(hh < 10)
            clock += "0";
           
        clock += hh + ":";
        if (mm < 10) clock += '0'; 
        clock += mm + ":"; 
		if (ss < 10) clock += '0'; 
        clock += ss; 
		clock +=" 星期"+"日一二三四五六".charAt(now.getDay());
    //等待一秒钟后调用time方法，由于settimeout在time方法内，所以可以无限调用
		$('#shownowtime').html(clock);
		setTimeout('nowtime('+(ctimes+1000)+')',1000);
  }
  
  function nowtime1(ctimes){ 
       var now = new Date(ctimes);  
       var year = now.getFullYear();       //年
       var month = now.getMonth() + 1;     //月
       var day = now.getDate();            //日       
       var hh = now.getHours();            //时
       var mm = now.getMinutes();          //分
       var ss = now.getSeconds();
	   
        var clock = year + "年";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "月";
       
        if(day < 10)
            clock += "0";
           
        clock += day + "日<br/>";
       
        if(hh < 10)
            clock += "0";
           
        clock += hh + ":";
        if (mm < 10) clock += '0'; 
        clock += mm + ":"; 
		if (ss < 10) clock += '0'; 
        clock += ss; 
		clock +=" 星期"+"日一二三四五六".charAt(now.getDay());
    //等待一秒钟后调用time方法，由于settimeout在time方法内，所以可以无限调用
		$('#shownowtime').html(clock);
		setTimeout('nowtime1('+(ctimes+1000)+')',1000);
    }
/*
 * 显示友情链接
 */
function showLinks(element,t,e){
	if($("#links").length==0){
		$("body").append('<div id="links" onmouseover="onmouseoverHandler()" onmouseout="onmouseoutHandler()"></div>');
	};
	var linksDiv = $(element).parent().siblings().first().clone().css({'display':'block'});
	$("#links").css({'display':'none','z-index':9999}).html(linksDiv)
	//单击页面选择器以外的地方时选择器隐藏
	$("#links").stop(true);
	var linksDivWidth=$("#links div:first-child").width();
	var offsetLeft = $(element).offset().left;
	var left = $(window).width()/2<offsetLeft?(offsetLeft-linksDivWidth):(offsetLeft+$(element).width());
	//console.log($(element).offset().left-$("#links").find(".links").width());
	$("#links").css({
		'top' : ($(element).offset().top+15)+ "px",
		'left' : left+ "px",
		'position' : 'absolute'
	}).fadeIn("fast");
}


function hideLinks() {
	var $links = $("#links");
	if($links.length){
		$links.delay(500).fadeOut("slow");
	};
}

function onmouseoverHandler() {
	var $links = $("#links");
	if($links.length){
		$links.stop(true);
		$links.fadeIn("fast");
		$links.css("opacity","1");
	};
}

function onmouseoutHandler() {
	var $links = $("#links");
	if($links.length){
		$links.fadeOut("slow");
	};
}

// 显示友情链接结束

/*
 * 全文搜索
 */
function indexSearch(){
	document.getElementById("searchForm").submit();
}
//滚动图片开始
var flag = "left";
function DY_scroll(wraper,prev,next,img,speed,or){  
	var wraper = $(wraper); 
	var prev = $(prev); 
	var next = $(next); 
	var img = $(img).find('ul'); 
	var w = img.find('li').outerWidth(true);
	var s = speed; 
	next.click(function(){ 
		img.animate({'margin-left':-w}/*,1500,'easeOutBounce'*/,function(){ 
			img.find('li').eq(0).appendTo(img); 
			img.css({'margin-left':0}); 
		}); 
		flag = "left";
	}); 
	prev.click(function(){ 
		img.find('li:last').prependTo(img); 
		img.css({'margin-left':-w}); 
		img.animate({'margin-left':0}/*,1500,'easeOutBounce'*/); 
		flag = "right";
	}); 
	if (or == true){ 
		ad = setInterval(function() { flag == "left" ? next.click() : prev.click()},s*1000); 
		wraper.hover(function(){clearInterval(ad);},function(){ad = setInterval(function() {flag == "left" ? next.click() : prev.click()},s*1000);});
	} 
} 
//滚动图片结束
//滚动图片结束
/*
 * 广告
 * a=1 是生产模式 a=0编辑模式
 */
function ad123(){
	if($('a.eve_ad_close').size()>0){
		$("a.eve_ad_close").each(function(index,element){
			$(element).click(function(){
				$(this).parent().hide();
				return false;
			});
		});
	}
	//悬浮
	if($('div.ad_duilian').size()>0){
			var duilian = $("div.ad_duilian");
			duilian.css({"display":"none","top":"100px" ,"z-index": "2"});
			if(devModel==1){
				duilian.show();
			}else{
				$('div.ad_duilian').each(function(index,element){
					var starttime = new Date($(element).attr("starttime").replace(/-/g,"/")).getTime();
					var endtime = new Date($(element).attr("endtime").replace(/-/g,"/")).getTime();
					var now = new Date().getTime();
					if((now>starttime&&now<endtime)){
						$(element).show();
					};
				});
			};
			$(window).scroll(function(){
				var scrollTop = $(window).scrollTop();
				duilian.stop().animate({top:scrollTop+100});
			});
	};
	 //漂浮
	if($('div.ad_float').size()>0){
		if(devModel==1){
			$('div.ad_float').each(function(index,element){
				$(element).css("display","block").imgFloat({speed:20,xPos:index*420,yPos:index*100});
			});
		}else{
			$('div.ad_float').each(function(index,element){
				var starttime = new Date($(element).attr("starttime").replace(/-/g,"/")).getTime();
				var endtime = new Date($(element).attr("endtime").replace(/-/g,"/")).getTime();
				var now = new Date().getTime();
				if(now>starttime&&now<endtime){
					$(element).css("display","block").imgFloat({speed:20,xPos:index*420,yPos:index*100});
				};
			});
		};
		
	};
	//弹出层
	if($('div.ad_div').size()>0){
		$('div.ad_div').css({
			'left':'50%',
			'marginTop':((window.screen.availHeight-$('.ad_div').height()-30)/2)+'px',
			'marginLeft':'-'+($('.ad_div').width()/2)+'px',
			'z-index': '2'
		});
		
		setTimeout(function(){
			if($.browser.msie && ($.browser.version < "8.0")){
				if(devModel==1){
					$('div.ad_div').show();
				}else{
					var starttime = new Date($('div.ad_div').attr("starttime").replace(/-/g,"/")).getTime();
					var endtime = new Date($('div.ad_div').attr("endtime").replace(/-/g,"/")).getTime();
					var now = new Date().getTime();
					if(now>starttime&&now<endtime){
						$('div.ad_div').show().delay(($('div.ad_div').attr("showtime")*1000)).fadeOut(0);
					};
				};
			}else{
				if(devModel==1){
					$('.ad_div').slideDown(1000);
				}else{
					var starttime = new Date($('div.ad_div').attr("starttime").replace(/-/g,"/")).getTime();
					var endtime = new Date($('div.ad_div').attr("endtime").replace(/-/g,"/")).getTime();
					var now = new Date().getTime();
					if(now>starttime&&now<endtime){
						$('.ad_div').slideDown(1000).delay(($('div.ad_div').attr("showtime")*1000)).fadeOut(0);
					};
				};
			};
			
		},2000);
	};
	//弹出窗
	if($('div.ad_open').size()>0){
		$('div.ad_open').each(function(index,element){
			if(devModel==1){
				$(element).css({"display":"block","right":(index*150),"top":250});
			}else{
				$(element).css("display","none");
			};
			$(element).find(".eve_ad_close").remove();
			var starttime = new Date($('div.ad_open').attr("starttime").replace(/-/g,"/")).getTime();
			var endtime = new Date($('div.ad_open').attr("endtime").replace(/-/g,"/")).getTime();
			var now = new Date().getTime();
			if(devModel==1||(now>starttime&&now<endtime)){
				var OpenWindow = window.open("", "","left="+(index*150)+",height="+$(element).height()+", width="+$(element).width()+",toolbar=no,location=no,scrollbars=no,status=no,menubar=no");
				OpenWindow.document.write($(element).html());
				OpenWindow.document.write("<script language=\"JavaScript\">");
				OpenWindow.document.write("setTimeout(\"self.close()\","+($('div.ad_open').attr("showtime")*1000)+")");
				OpenWindow.document.write("</script>");
				OpenWindow.document.close();
			}
		});
	};
	if($('.box').find('#rightdownpop').size()>0){
		var rightdownpop = $('.box').find('#rightdownpop');
		var winh = rightdownpop.outerHeight();
	    rightdownpop.css({"right":"0px","top":$(window).scrollTop() + $(window).height() - winh+"px"});
		if(devModel==1){
			$('.box').find('#rightdownpop').show();
	    }else{
			if (!($.browser.msie && ($.browser.version == "6.0") && !$.support.style)) {
		      $('.box').find('#rightdownpop').slideDown(1000).delay(6000).fadeOut(400);
		    } else{
		      $('.box').find('#rightdownpop').show().delay(6000).hide(0);
		    }
	    }
	   $(window).scroll(function(){
			rightdownpop.css({"right":"0px","top":$(window).scrollTop() + $(window).height() - winh+"px"});
		});
	   
    }
}

/*漂浮*/
/*
* jQuery Plugins imgFloat v1011
* 使用说明：
* speed //元素移动速度
* xPos //元素一开始左距离
* yPos //元素一开始上距离
* $('#div1').imgFloat({speed:30,xPos:10,yPos:10});
* $('#div2').imgFloat(); //不给参数默认（speed:10,xPos:0,yPos:0）
*/
(function($) {
	jQuery.fn.imgFloat = function(options) {
		var own = this;
		var xD = 0;
		var yD = 0;
		var i = 1;
		var settings = {
			speed : 10,
			xPos : 10,
			yPos :10
		};
		jQuery.extend(settings, options);
		var ownTop = settings.xPos;
		var ownLeft = settings.yPos;
		own.css({
					position : "absolute",					
					cursor : "pointer"
				});
		function imgPosition() {
			var winWidth = $(window).width() - own.width();
			var winHeight = $(window).height() - own.height();
			if (xD == 0) {
				ownLeft += i;
				own.css({
							left : ownLeft
						});
				if (ownLeft >= winWidth) {
					ownLeft = winWidth;
					xD = 1;
				}
			}
			if (xD == 1) {
				ownLeft -= i;
				own.css({
							left : ownLeft
						});
				if (ownLeft <= 0)
					xD = 0;
			}
			if (yD == 0) {
				ownTop += i;
				own.css({
							top : ownTop
						});
				if (ownTop >= winHeight) {
					ownTop = winHeight;
					yD = 1;
				}
			}
			if (yD == 1) {
				ownTop -= i;
				own.css({
							top : ownTop
						});
				if (ownTop <= 0)
					yD = 0;
			}
		}
		var imgHover = setInterval(imgPosition, settings.speed);
		own.hover(function() {
					clearInterval(imgHover);
				}, function() {
					imgHover = setInterval(imgPosition, settings.speed);
				});
	}
})(jQuery);
/*
 * 修改input中值的某节点样式。input值是一个html字符串，
 * 要改变input值的名称。
 * selector:html字符串中要改变样式的jquery选择器，如 a .classname #id
 * styleName：html字符串中要改变的样式名称，如color size 
 * newStyleValueInputName:新的样式值从哪个input取。
 * 例：boxno23
 */
function ChangeInputValueStyle(inputName,selector,styleName,newStyleValueInputName){
	var input = $("input[name='"+inputName+"']");
	var oldVal = $("input[name='"+inputName+"']").val();//html字符串
	var newVal = $("input[name='"+newStyleValueInputName+"']").val();//新样式值
	var divtemp = $("<div>").append($(oldVal)); 
	divtemp.find(selector).css(styleName,newVal);
	input.val(divtemp.html());
}
/*
 * 刷新验证码
 */
function refushcode(root,id){
	var d = new Date().getTime();
	//为了避免服务器或者浏览器缓存，添加了一个额外的参数
	document.getElementById(id).src=root+"/common/imgCode/"+d;
}
