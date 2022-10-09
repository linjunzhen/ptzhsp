<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<eve:resources loadres="apputil,layer,autocomplete"></eve:resources>
<script type="text/javascript">
function callpage(catalogList){
	var layload = parent.layer.load('正在提交请求中…');
	parent.layer.close(layload);
    $("#listContainer").html("");
    var newhtml = "<ul>";
    for(var i=0; i<catalogList.length; i++){
		
		var pageNum = catalogList[i].pageNum;
		var limitNum =  catalogList[i].limitNum;
		newhtml += '<li><a href="javascript:void(0);">'+(i+1+(pageNum-1)*limitNum)+'、'+catalogList[i].CATALOG_NAME+'</a>';
		newhtml += '<h3 class="clearfix" style="border-bottom:1px solid #e5e5e5;">';
		var itemList = catalogList[i].itemList;		
		if(null!=itemList){
			for(var j=0; j<itemList.length; j++){
				newhtml += '<div id="'+itemList[j].ITEM_ID+'div"><label>（'+(j+1)+'）'
				+itemList[j].ITEM_NAME+'</label></div>';
				newhtml += '<p id="'+itemList[j].ITEM_ID+'p" style="display:none;" class="clearfix">';
				newhtml += '<a target="_blank" href="serviceItemController/bsznDetail.do?itemCode='+itemList[j].ITEM_CODE+'" class="bsicon">办事指南</a><span>┆</span>';
				newhtml += '<a target="_blank"  href="serviceItemController/bsznDetail.do?itemCode='+itemList[j].ITEM_CODE+'#clqd" class="bsicon1">表格下载</a><span>┆</span>';
				newhtml += '<a target="_blank"  href="serviceItemController/bsznDetail.do?itemCode='+itemList[j].ITEM_CODE+'#cjwt" class="bsicon2">常见问题</a><span>┆</span>';
				//newhtml += '<a target="_blank"  href="serviceItemController/bsznDetail.do?itemCode='+itemList[j].SERVICEITEM_CODE+'" class="bsicon3">在线咨询</a><span>┆</span>';
				
				var RZBSDTFS = itemList[j].RZBSDTFS;
				if(RZBSDTFS!='in01' && RZBSDTFS!='in02'){
					newhtml += '<a target="_blank"  href="webSiteController.do?applyItem&itemCode='+itemList[j].ITEM_CODE+'" class="bsicon4">在线办理</a><span>┆</span>';
				}			
				newhtml += '<a href="javascript:void(0);" class="bsicon13" onclick="scsx(\''+itemList[j].ITEM_CODE+'\')">收藏事项</a><span>┆</span>';
				newhtml += '</p>';
			}			
		}
			
		newhtml += '</h3>';
			
		newhtml += '</li>';
    //++
    } 
     newhtml += "</ul>";
    $("#listContainer").html(newhtml);
    TabHeads("#listContainer li","bslistOn","#listContainer h3");
    setShowHref("#listContainer div","bslistOn","#listContainer p");
    jskzx();
}
function scsx(code){
    $.post( "bsscController/scsx.do",{itemCode:code},
        function(responseText, status, xhr) {
    		if(responseText!="websessiontimeout"){
    			var resultJson = $.parseJSON(responseText);
	            if (resultJson.success) {
	            	alert(resultJson.msg);
	            } else {
	            	window.location.href="<%=path%>/userInfoController/mztLogin.do?returnUrl=webSiteController/view.do?navTarget=website/grbs/grbs";
	            }
	    	}
        }
    );
}
</script>
<div class="bslist bsqlqd" id="listContainer"></div>