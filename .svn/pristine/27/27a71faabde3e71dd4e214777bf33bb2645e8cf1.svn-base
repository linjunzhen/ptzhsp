<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<title>预约部门</title>
		<%@include file="common.jsp"%>
		<link rel="stylesheet"
			href="${webRoot }/plug-in/assets/css/amazeui.min.css">
		<link rel="stylesheet" href="${webRoot }/plug-in/assets/css/app.css">
		<link rel="stylesheet" href="${webRoot }/plug-in/evecom/css/evecom.css">
		<script src="${webRoot}/plug-in/assets/js/jquery.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/handlebars.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/amazeui.min.js"></script>
		<script src="${webRoot}/plug-in/layer_mobile/layer.js"></script>
		<script src="${webRoot}/plug-in/evecom/mobilUtil.js"></script>
		<script src="http://101.com/JsBridge.js"></script>
		<script>
		$(function(){
			var uc= Bridge.require("sdp.uc");
			uc.getCurrentUser({"force":true},{
				success:function(args){
					var userCode = args.org_exinfo.org_user_code;
					mobilUtil.doPost({
						url:"${webRoot}/bespeakOnlineController/getUser.do",
						params:"userCode=" + userCode,
						callback : function(resultJson) {
							if(!resultJson.success){
								mobilUtil.layerAlert(resultJson.msg);
							}else{
								$("#userCode").val(userCode);
							}
						}
					});
				},
				fail:function(args){
					mobilUtil.layerAlert("获取用户信息失败，错误信息："+args);
				}
			});
		});
		function goInfoOrTime(id,count,departId,departName){
			if(count>0){
				window.location.href = "${webRoot}/bespeakOnlineController/goPortalIndex.do?parentId="+id;
			}else{
				window.location.href = "${webRoot}/bespeakOnlineController/goPortalBespeakTime.do?id="+id+"&departId="+departId+"&departName="+departName+"&userCode="+$("#userCode").val();
			}
		}
		
		function goBusinessChoose(departId,departName){
			window.location.href = "${webRoot}/bespeakOnlineController/goBusinessChoose.do?departId="+departId;
		}
		</script>
	</head>
	<body>
	<input type="hidden" id="userCode" value="">
		 <div>
			<ul class="am-list ptwglist">
			<c:forEach var="list" items="${list}">
				<%-- <li onclick="goInfoOrTime('${list.ID }','${list.COUNT }','${list.DEPART_ID }','${list.DEPART_NAME }')">
					<a>${list.DEPART_NAME }</a>
				</li> --%>
				<li onclick="goBusinessChoose('${list.DEPART_ID }','${list.DEPART_NAME }')">
					<a>${list.DEPART_NAME }</a>
				</li>
			</c:forEach>
			</ul>
		</div>

	</body>
</html>