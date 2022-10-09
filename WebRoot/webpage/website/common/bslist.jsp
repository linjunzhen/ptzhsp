<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
function callpage(itemList){
    $("#listContainer").html("");
    var newhtml = "<ul>";
    for(var i=0; i<itemList.length; i++){
    var m = encodeURIComponent(itemList[i].ITEM_NAME);
    newhtml += "<li><a target=\"_blank\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" >"+itemList[i].ITEM_NAME+"</a>"
    newhtml += "<h3 class=\"clearfix\"><p><b>申报对象：</b>"+itemList[i].BUS_TYPENAMES+"  &nbsp;&nbsp;<b>办理机构：</b>"+itemList[i].DEPART_NAME+"</p>"
            +"<a href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" "
    		+"class=\"bsicon\" target=\"_blank\">办事指南</a><span>┆</span><a  class=\"bsicon1\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE
            +"#clqd\" target=\"_blank\">表格下载</a><span>┆</span><a target=\"_blank\"";
    newhtml += " href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE
    		+"#cjwt\" class=\"bsicon2\">常见问题</a>";
    if(itemList[i].RZBSDTFS !='in01' && itemList[i].RZBSDTFS!='in02'){	    
        newhtml += "<span>┆</span><a target=\"_blank\" href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"\" class=\"bsicon4\">在线办理</a>" ;
    }
    newhtml += "<span>┆</span><a href=\"javascript:void(0);\" class=\"bsicon13\" onclick=\"scsx('"+
    itemList[i].ITEM_CODE+"')\">收藏事项</a><span>┆</span></h3></li>";
    //++
    } 
     newhtml += "</ul>";
    $("#listContainer").html(newhtml);
    TabHeads("#listContainer li","bslistOn","#listContainer h3");
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
<div class="bslist" id="listContainer"></div>