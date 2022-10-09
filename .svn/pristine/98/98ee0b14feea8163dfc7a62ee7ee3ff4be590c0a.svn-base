<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragrma","no-cache");
    response.setDateHeader("Expires",0);
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String srcPath = request.getParameter("srcPath");	
	request.setAttribute("srcPath",srcPath);
%>
<style type="text/css">
body {
	padding-top:0px;
	background-color: #CCCCCC;
}
.input_btn{
	width:60px;
	margin-right:10px;
}
.input_text{
	border:1px solid gray;
	width:60px;
}
.message{
	font-weight:bold;
	color:green;
	line-height:20px;
}
.myphoto{
	position:relative;
	background-repeat:no-repeat;
	background-position:center center;
	width:75px;
	height:75px;
	margin:5px;
	display:block;
	cursor:move;
}
.opl_delete{
	float:right;
	height:20px;
	margin-top:-20px;
	margin-right:2px;
	cursor:pointer;
}

.box {
   /*非IE的主流浏览器识别的垂直居中的方法*/
   display: table-cell;
   vertical-align:middle;

   /*设置水平居中*/
   text-align:center;

   /* 针对IE的Hack */
   *display: block;
   *font-size:175px;/*约为高度的0.873，200*0.873 约为175*/
   *font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/

   width:800px;
   height:800px;
   border: 1px solid #eee;
 }
 
.box img {
   /*设置图片垂直居中*/
   max-width:800px;
   max-height:800px;
   vertical-align: middle;
}

</style>

<script type="text/javascript">
$(function() {
	var jcrop_api;
	initJcrop();	
});

function show(c) {
	$("#x").val(Math.floor(c.x));
	$("#y").val(Math.floor(c.y));
	$("#w").val(Math.floor(c.w));
	$("#h").val(Math.floor(c.h));
};


function initJcrop() {
	$('#target').Jcrop({
		bgColor:'white',
		bgOpacity:0.3,
		onChange:show,
		onSelect:show
	},function(){
		jcrop_api = this;
		jcrop_api.release();
		jcrop_api.setOptions({ aspectRatio: 23/18 });
		jcrop_api.setOptions({ allowResize:true});
		jcrop_api.animateTo([10,10,460,360]);
		jcrop_api.setOptions({minSize:[460,360]});
		$("#x").val(10);
		$("#y").val(10);
		$("#w").val(460);
		$("#h").val(360);
		//jcrop_api.setOptions({ aspectRatio: 16/9 });
		jcrop_api.focus();
	});
};

function ScaleSize_default(){
	jcrop_api.release();
	jcrop_api.setOptions({ aspectRatio: 23/18 });
	jcrop_api.setOptions({ allowResize:true});
	jcrop_api.animateTo([10,10,460,360]);
	jcrop_api.setOptions({minSize:[460,360]});
	$("#x").val(10);
	$("#y").val(10);
	$("#w").val(460);
	$("#h").val(360);
	//jcrop_api.setOptions({ aspectRatio: 16/9 });
	jcrop_api.focus();
}

function ScaleSize_169(){
	jcrop_api.release();
	jcrop_api.setOptions({ allowResize:true});
	jcrop_api.setOptions({ aspectRatio: 16/9 });
	jcrop_api.focus();
}

function ScaleSize_43(){
	jcrop_api.release();
	jcrop_api.setOptions({ allowResize:true});
	jcrop_api.setOptions({ aspectRatio: 4/3 });
	jcrop_api.focus();
}

function ScaleSize_0(){
	jcrop_api.release();
	jcrop_api.setOptions({ allowResize:true});
	jcrop_api.setOptions({ aspectRatio: 0 });
	jcrop_api.focus();
}

function releaseSel(){
	jcrop_api.release();
	jcrop_api.setOptions({ allowResize:true});
	jcrop_api.setOptions({ aspectRatio: 0 });
	$("#x").val(Math.floor(0));
	$("#y").val(Math.floor(0));
	$("#w").val(Math.floor(0));
	$("#h").val(Math.floor(0));
	jcrop_api.focus();
}

function resizeImage(obj, MaxW, MaxH){
	var imageObject = obj;
    var state = imageObject.readyState;
	if(state!='complete'){
        setTimeout("resizeImage("+imageObject+","+MaxW+","+MaxH+")",50);
   		return;
    }
    var oldImage = new Image();
    oldImage.src = imageObject.src;
    var dW = oldImage.width;
    var dH = oldImage.height;
    if(dW>MaxW || dH>MaxH){
        a = dW/MaxW; b = dH/MaxH;
        if( b>a ) a = b;
        dW = dW/a; dH = dH/a;
    }
    if(dW > 0 && dH > 0){
        imageObject.width = dW;
   		imageObject.height = dH;
   	}
}


function AutoResizeImage(maxWidth,maxHeight,objImg){
    var img = new Image();
    img.src = objImg.src;
  //  $(img).load(function() {
        var hRatio;
    	var wRatio;
    	var Ratio = 1;
    	var w = img.width;
    	var h = img.height;
    	wRatio = maxWidth / w;
    	hRatio = maxHeight / h;
    	if (maxWidth ==0 && maxHeight==0){
    		Ratio = 1;
    	}else if (maxWidth==0){//
    		if (hRatio<1) Ratio = hRatio;
    	}else if (maxHeight==0){
    		if (wRatio<1) Ratio = wRatio;
    	}else if (wRatio<1 || hRatio<1){
    		Ratio = (wRatio<=hRatio?wRatio:hRatio);
    	}
    	if (Ratio<1){
    		w = w * Ratio;
    		h = h * Ratio;
    	}
    	var srcW = $("#srcW").val(Math.floor(w));
    	var srcH = $("#srcH").val(Math.floor(h));
    	//alert(Math.floor(w) +"x"+ Math.floor(h));
    	objImg.height = h;
    	objImg.width = w;
 //   });
}

function imgCutSubmitForm() {
	var x = $("#x").val();
	var y = $("#y").val();
	var w = $("#w").val();
	var h = $("#h").val();
	var srcPath = $("#srcPath").val();
	var srcW = $("#srcW").val();
	var srcH = $("#srcH").val();
	if(w > 0){
		$.post(rootPath + "/article/imgCutContent.do", {"image.painterWidth":800,"image.painterHeight":800,"image.x":x,"image.y":y,"image.width":w,"image.height":h,"image.srcWidth":srcW,"image.srcHeight":srcH,"image.srcPath":srcPath}, function(r) {
			if (r.success) {
				$.messager.confirm('提示',r.msg +"您确定把裁剪完图片设置为文章的缩略图吗？", function(replay) {
		    		if(replay){
		    			$("#titleImg_tag").attr("src", r.loadpath).css("display", "");
		    			$("#artTitleImgUrl").val(r.path);				
						infoForm.imgCutDialog.dialog("destroy");
		    		}
		    	});
			}else{
				alert(r.msg);
			}
		}, "json");
	}else{
		alert("未选择图片裁剪区域。");
	}
};
</script>

<table height="100%" width="100%">
 <tbody>
  <tr>
	<td align="left" valign="top">
		<table border="0" cellpadding="0" cellspacing="0" height="100%" width="612">
			 <tbody>
			   <tr>
				 <td valign="top">
					 <div id="docs_explorer" style="padding:4px;height:812px;overflow:auto;">
						<table style="font-size:12px" border="0" cellpadding="0" cellspacing="1" height="800" width="800">
						  <tbody>
								<tr height="800">
									<td valign="top" width="250">										
										 <div style="height:146px;margin-top:2px;border:1px solid gray;overflow:auto;width:116px;background:#FFF">
											<input checked="checked" value="0" name="ScaleSize" id="ScaleSize_default" onclick="ScaleSize_default()" type="radio"/>
											<label for="ScaleSize_75">市局内网缩略图</label><br>
											<input value="0" name="ScaleSize" id="ScaleSize_169" onclick="ScaleSize_169()" type="radio"/>
											<label for="ScaleSize_75">缩略图(16:9)</label><br>
											<input value="1" name="ScaleSize" id="ScaleSize_43" onclick="ScaleSize_43()" type="radio"/>
											<label for="ScaleSize_124">缩略图(4:3)</label><br>										    
											<input value="3" name="ScaleSize" id="ScaleSize_0" onclick="ScaleSize_0()" type="radio">
											<label for="ScaleSize_240">自由选择</label><br>
											<!-- 
											<input value="4" name="ScaleSize" id="ScaleSize_500" type="radio">
											<label for="ScaleSize_500">大图(500)</label><br>
											<input value="5" name="ScaleSize" id="ScaleSize_640" type="radio">
											<label for="ScaleSize_640">超大图(640)</label><br>
											-->
										</div>
										<div id="otherContainer">
											<input id="imgcutsub" class="input_btn" style="margin-left:20px;width:80px" value="剪裁" onclick="imgCutSubmitForm()" type="button">
											<div style="height:5px;overflow:hidden"></div>
											<input name="button2" class="input_btn" style="margin-left:20px;width:80px" value="撤消" onclick="releaseSel()" type="button">
											<div style="height:6px;overflow:hidden"></div>
										</div>
									</td>
									<td>
									  <div id="target" style="display: block; visibility: visible; border: medium none; margin: 0px; padding: 0px; position: absolute; top: 0px; left: 0px; width: 804px; height: 804px;">
										<div class="box" style="vertical-align:middle;text-align:center;border-style:dashed;border:1px solid #000;">
											<img src='${srcPath}' name="photo_list" style="vertical-align:middle;" onload="AutoResizeImage(800,800,this)"/>
										</div>	
									  </div>									
									</td>
								  </tr>
							</tbody>
						 </table>
					 </div>
					</td>
				</tr>
			</tbody>
		</table>
	</td>	
</tr>
</tbody>
</table> 
<form id="coords" class="coords" method="post" >
	<input type="hidden" id="x" name="image.x" />
	<input type="hidden" id="y" name="image.y" />	
	<input type="hidden" id="w" name="image.width" />
	<input type="hidden" id="h" name="image.height" />
	<input type="hidden" id="srcW" name="image.srcWidth" />
	<input type="hidden" id="srcH" name="image.srcHeight" />
	<input type="hidden" id="srcPath" name="image.srcPath" value="${srcPath}"/>
	<input type="hidden" id="pw" name="image.painterWidth" value="800"/>
	<input type="hidden" id="ph" name="image.painterHeight" value="800"/>
</form>
