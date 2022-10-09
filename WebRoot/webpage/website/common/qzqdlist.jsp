<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    <style
function callpage(catalogList){
    $("#listContainer").html("");
    var newhtml = "<ul>";
    for(var i=0; i<catalogList.length; i++){
        alert("qzqdlist");
		var pageNum = catalogList[i].pageNum;
		var limitNum =  catalogList[i].limitNum;
		newhtml += '<li><a href="javascript:void(0);">'+(i+1+(pageNum-1)*limitNum)+'、'+catalogList[i].RIGHT_NAME+'</a>';
		newhtml += '<h3 class="clearfix">';
		var itemList = catalogList[i].itemList;
		if(null!=itemList){
			for(var j=0; j<itemList.length; j++){
                newhtml += '<a target="_blank"  href="billRightController/qzqdDetail.do?rightId='+itemList[j].RIGHT_ID+'#cjwt">';
                newhtml += '<label style="line-height:30px">（'+(j+1)+'）'+itemList[j].SUBITEM_NAME+'</label>';
                newhtml +='</a>';
			}
		}
		newhtml += '</h3>';
		newhtml += '</li>';
    //++
    } 
     newhtml += "</ul>";
    $("#listContainer").html(newhtml);
    TabHeads("#listContainer li","bslistOn","#listContainer h3");
    setShowHref("#listContainer label","bslistOn","#listContainer p");
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
	            	  window.location.href = "webSiteController/view.do?navTarget=website/yhzx/login";
	              }
              }
          
        }
    );
}
</script>
<div class="bslist bsqlqd" id="listContainer"></div>