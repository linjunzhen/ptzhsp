<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%><%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	function openNewConsultWindow(){
		window.open("consultController/viewYwzx.do?itemId=${serviceItem.ITEM_ID }&itemName="+encodeURIComponent('${serviceItem.ITEM_NAME }'));
	}
	function scsx(code){
		$.post( "bsscController/scsx.do",{itemCode:code},
			function(responseText, status, xhr) {
			  if(responseText!="websessiontimeout"){
				  var resultJson = $.parseJSON(responseText);
					  if (resultJson.success) {
						  alert(resultJson.msg);
					  } else {
						  window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login&returnUrl=webSiteController.do?applyItem&itemCode="+code;
					  }
				  }
			  
			}
		);
	}
	function hash(id){
		window.location.hash = "#"+id;
	};
	var userType = '${sessionScope.curLoginMember.USER_TYPE}';
	$(function(){
		if(userType=='1'&&"${busTypenames}".indexOf("个人")>=0){
			
		}else if(userType=='2'&&"${busTypenames}".indexOf("企业")>=0){
			
		}else{
			if(userType=='1'){
				alert("个人账号无法办理此事项!")
			}else if(userType=='2'){
				alert("企业账号无法办理此事项!")
			}
			location.href = "<%=path%>/serviceItemController/bsznDetail.do?itemCode=${serviceItem.ITEM_CODE}";
		}
		
	});
</script>
<div class="bsMain1 clearfix">
	<div class="bsMainL1" style="width: 918px;">
		<h2 class="bstitle1">${serviceItem.ITEM_NAME}</h2>
		<div class="Absbtn3">
			<a href="javascript:scsx('${serviceItem.ITEM_CODE}');" class="bsbtn3"><img src="webpage/website/common/images/bsIcon17.png" />收&nbsp;&nbsp;藏</a>
		</div>
	</div>
	<div class="bsMainR1">
		<div class="bstitle">您当前所处阶段：</div>
		<div class="Ctext">
			<div <c:if test="${fn:length(flowMappedInfo.YS_NAME)>15}">style="height: 80px;" </c:if> class="bsnowlc">
<c:if test="${empty flowMappedInfo.YS_NAME}">
				办理中
</c:if>
				<c:if test="${!empty flowMappedInfo.YS_NAME}">
					${flowMappedInfo.YS_NAME}
				</c:if>
			</div>
			<c:if test="${EFLOWOBJ.SHRMC!=null}">
			<p class="nowname">当前操作者：${EFLOWOBJ.SHRMC}</p>
			</c:if>
		</div>
	</div>
</div>
