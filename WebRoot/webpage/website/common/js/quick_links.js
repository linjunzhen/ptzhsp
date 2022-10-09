jQuery(function($){
	//创建DOM
	var quickHTML = '<div class="quick_links_panel"><div id="quick_links" class="quick_links">';
	//quickHTML += '<a href="webSiteController/view.do?navTarget=website/hd/wyjc" target="_blank"><i class="quick_icon"></i><span>我要纠错</span></a>';
	quickHTML += '<a href="webSiteController/view.do?navTarget=website/hd/wyjc" target="_blank"><i class="quick_icon"></i><span>我要纠错</span></a>';
    quickHTML += '<a href="webSiteController/view.do?navTarget=website/hd/wtzj" target="_blank"><i class="quick_icon"></i><span>堵点难点</br>问题征集</span></a>';
    quickHTML += '</div></div>',
	quickShell = $(document.createElement('div')).html(quickHTML).addClass('quick_links_wrap'),
	quickLinks = quickShell.find('.quick_links');
	quickPanel = quickLinks.parent();
	quickShell.appendTo('body');
	//通用事件处理
	var view = $(window),
	quickLinkCollapsed = !!ds.getCookie('ql_collapse'),
	getHandlerType = function(className){
		return className.replace(/current/g, '').replace(/\s+/, '');
	},
	showPopFn = function(){
		var type = getHandlerType(this.className);
		if(popDisplayed && type === prevPopType){
			return hideQuickPop();
		}
		showQuickPop(this.className);
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
		prevTrigger = $(this).addClass('current');
	},
	quickHandlers = {
		//返回顶部
		return_top: function(){
			ds.scrollTo(0, 0);
			hideReturnTop();
		}
	};
		quickShell.delegate('a', 'click', function(e){
		var type = getHandlerType(this.className);
		if(type && quickHandlers[type]){
			quickHandlers[type].call(this);
			e.preventDefault();
		}
	});
	//Return top
	var scrollTimer, resizeTimer, minWidth = 1101;

	function resizeHandler(){
		clearTimeout(scrollTimer);
		scrollTimer = setTimeout(checkScroll, 160);
	}
	function checkResize(){
		quickShell[view.width() > 1101 ? 'removeClass' : 'addClass']('quick_links_dockright');
	}
	function scrollHandler(){
		clearTimeout(resizeTimer);
		resizeTimer = setTimeout(checkResize, 160);
	}
	function checkScroll(){
		view.scrollTop()>100 ? showReturnTop() : hideReturnTop();
	}
	function showReturnTop(){
		quickPanel.addClass('quick_links_allow_gotop');
	}
	function hideReturnTop(){
		quickPanel.removeClass('quick_links_allow_gotop');
	}
	view.bind('scroll.go_top', resizeHandler).bind('resize.quick_links', scrollHandler);
	resizeHandler();
	scrollHandler();
	//滚动条滚动事件
	window.onscroll = function(){ 
		resetScroll();

		
		/*if(Number(scrollTop)<=Number(1)){
			$("#headerInfo1").css("position","");
			$("#headerInfo2").hide();
		}else{			
			$("#headerInfo1").css("position","fixed");
			$("#headerInfo2").show();
		}*/
		/* if(Number(scrollTop)>=Number(181)){
			$(".nav").css("position","fixed").css("z-index","9999").css("top","26px").css("border-top","0px");			
		}else{
			$(".nav").css("position","").css("z-index","").css("top","").css("border-top","4px solid #0084ff");;		
		} */
		
	} 
	//窗口改变大小事件
	$(window).resize(function() {
		var width = $(this).width();
		windowLink(width);
	});
	//初始化右边滚动位置
	windowLink($(window).width());
	resetScroll();
});

function resetScroll(){
    /* var scrollTop = $(document).scrollTop();
    if(Number(scrollTop)<=Number(205)){
        $(".quick_links_wrap").css("top",Number(390)-Number(scrollTop)+"px");
        
    }else{
        var height = ($(window).height())/1.64;
        if(Number(height)<=167){
            height = ($(window).height())-144;
        }
        $(".quick_links_wrap").css("top",height+"px");
    } */
}
function windowLink(width){
	//$(".quick_links_wrap").css("right","");
	//右边链接居中
	var linkWidth = parseInt($(".quick_links_panel").css("width"));
	var containerWidth = parseInt($(".container").css("width"));
	var linkLeft = (Number(width) - Number(linkWidth)*2 - Number(containerWidth))/4 + Number(width)/2 + Number(containerWidth)/2-45;
	$(".quick_links_wrap").css("left",(Number(linkLeft)+"px"));
	
}