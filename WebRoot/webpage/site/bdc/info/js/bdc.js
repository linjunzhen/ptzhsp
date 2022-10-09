AOS.init({
  offset: 10,
  duration: 600,
});

$(function(){
	//下拉框美化
	$('select').selectlist({
		zIndex: 10,
		height: 30,
	});		
	//幻灯片
	jQuery(".eui-slide").hover(function(){
		jQuery(this).find(".prev,.next").stop(true,true).fadeTo("show",0.4) },function(){ jQuery(this).find(".prev,.next").fadeOut() });
	/*SuperSlide图片切换*/
	jQuery(".eui-slide").slide({titCell:".point", mainCell:".pic", vis:"auto", scroll:1, autoPage:true,effect:"fold", autoPlay:true, delayTime:600, trigger:"click"});
	
	jQuery(".index-tab").slide({effect:"fade"});
	
	
	jQuery(".index-link").slide({ 
		type:"menu",// 效果类型，针对菜单/导航而引入的参数（默认slide）
		titCell:".item", //鼠标触发对象
		targetCell:".sub", //titCell里面包含的要显示/消失的对象
		effect:"slideDown", //targetCell下拉效果
		delayTime:300 , //效果时间
		triggerTime:0, //鼠标延迟触发时间（默认150）
		returnDefault:true //鼠标移走后返回默认状态，例如默认频道是“预告片”，鼠标移走后会返回“预告片”（默认false）
	});

})