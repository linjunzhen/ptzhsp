<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer"></eve:resources>
<style type="text/css">
ul,ol {
	list-style: none;
}
.eve-layout{padding:20px 0 0 20px;}
.eve-layout ul li {
	float: left;
	margin: 0 10px 10px 10px;
	width: 150px;
	height:180px;
	text-align: center;
}
</style>
<script type="text/javascript">
	function changeSystem(){
		var value = $("input:radio:checked").val();
		window.top.location.href="loginController.do?index&resKey="+value;
	}
	$(function(){
		var curUserResKeys = $("#curUserResKeys").val();
		if(curUserResKeys!="__ALL"){
		   if(curUserResKeys.indexOf("CmsManager")==-1){
			   $("#CmsManager").attr("style","display: none;");
		   }
		   if(curUserResKeys.indexOf("SystemManager")==-1){
			   $("#SystemManager").attr("style","display: none;");
		   }
		   if(curUserResKeys.indexOf("PersonnelSubManager")==-1){
			   $("#PersonnelSubManager").attr("style","display: none;");
		   }
		   if(curUserResKeys.indexOf("BillproposeManager")==-1){
			   $("#BillproposeManager").attr("style","display: none;");
		   }
		   if(curUserResKeys.indexOf("MeetingSubManager")==-1){
			   $("#MeetingSubManager").attr("style","display: none;");
		   }
		   if(curUserResKeys.indexOf("PerformManager")==-1){
			   $("#PerformManager").attr("style","display: none;");
		   }
		 }
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ChangeSubSystemForm" method="post"
		action="controlConfigController.do?saveOrUpdate">
		<input type="hidden" id="curUserResKeys" name="curUserResKeys" value="${sessionScope.curLoginUser.resKeys}">
		<div id="ChangeSubSystemFormDiv">
			<div class="eve-layout">
				<ul>
					<li><img src="webpage/main/images/pt_system.png" />
						<p>
							<input type="radio" id="PersonnelSubManager"  name="SubSystemKey" value="PersonnelSubManager"
							    <c:if test="${resKey=='PersonnelSubManager'}">checked="checked"</c:if>
								 />
						</p></li>
					<li ><img src="webpage/main/images/pt_system1.png" />
						<p>
							<input type="radio" id="BillproposeManager" name="SubSystemKey" value="BillproposeManager" 
							<c:if test="${resKey=='BillproposeManager'}">checked="checked"</c:if>
							/>
						</p></li>
					<li ><img src="webpage/main/images/pt_system2.png" />
						<p>
							<input type="radio" id="CmsManager" name="SubSystemKey" value="CmsManager" 
							<c:if test="${resKey=='CmsManager'}">checked="checked"</c:if>
							/>
						</p></li>
					<li ><img src="webpage/main/images/pt_system3.png" />
						<p>
							<input type="radio" id="MeetingSubManager" name="SubSystemKey" value="MeetingSubManager" 
							<c:if test="${resKey=='MeetingSubManager'}">checked="checked"</c:if>
							/>
						</p></li>
					<li ><img src="webpage/main/images/pt_system4.png" />
						<p>
							<input type="radio" id="PerformManager" name="SubSystemKey" value="PerformManager" 
							<c:if test="${resKey=='PerformManager'}">checked="checked"</c:if>
							/>
						</p></li>
					<li ><img src="webpage/main/images/pt_system5.png" />
						<p>
							<input type="radio" id="SystemManager" name="SubSystemKey" value="SystemManager" 
							<c:if test="${resKey=='SystemManager'}">checked="checked"</c:if>
							/>
						</p></li>
				</ul>
			</div>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="button" onclick="changeSystem();"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>
