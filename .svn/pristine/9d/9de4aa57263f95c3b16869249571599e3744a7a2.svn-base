<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript">

	$(function() {
		AppUtil.initWindowForm("ChangeFlowTaskForm", function(form, valid) {
			if (valid) {
				var curNodeNames = $("#curNodeNames").val();
				var curNodeArray = curNodeNames.split(",");
				var changeTaskList = new Array();
				for(var index in curNodeArray){
					 var oldNodeName = curNodeArray[index];
					 //获取更改环节的值
					 var changeNodeName = $("select[name='"+oldNodeName+"_CHANGENAME']").val();
					 //获取目标审核人
					 var changeHandlerId = $("input[name='"+oldNodeName+"_HANDLERID']").val();
					 if(changeNodeName&&changeHandlerId){
						//定义changeTask对象
						 var changeTaskObj = {};
						 changeTaskObj.oldNodeName = oldNodeName;
						 changeTaskObj.changeNodeName = changeNodeName;
						 changeTaskObj.changeHandlerId = changeHandlerId;
						 changeTaskList.push(changeTaskObj);
					 }
				}
				if(changeTaskList.length>0){
					var changeListJson = JSON2.stringify(changeTaskList);
					var exeId = $("#exeId").val();
					AppUtil.ajaxProgress({
						url : "flowTaskController.do?changeTask",
						params : {
							exeId :exeId,
							changeListJson:changeListJson
						},
						callback : function(resultJson) {
							if (resultJson.success == true) {
							    art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									time : 3,
									ok : function(){
										parent.$("#FlowMonitorGrid").datagrid(
										"reload");
										AppUtil.closeLayer();
									}
								});
							} else {
								art.dialog({
									content : resultJson.msg,
									icon : "error",
									ok : true
								});
							}
						}
					});
				}else{
					alert("更改环节和审核人都不能为空!");
				}
			}
		}, "JBPM6_EXECUTION");

	});
	
	function selectNodeUser(oldNodeName){
		var selectUserIds = $("input[name='"+oldNodeName+"_HANDLERID']").val();
		var selectUserNames = $("input[name='"+oldNodeName+"_HANDLERNAME']").val();
		
		$.dialog.open("sysUserController.do?selector&allowCount=1&userIds="
				+ selectUserIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectUserInfo = art.dialog.data("selectUserInfo");
    			if(selectUserInfo&&selectUserInfo.userIds){
    				var userIds = selectUserInfo.userIds;
					var userNames = selectUserInfo.userNames;
					$("input[name='"+oldNodeName+"_HANDLERNAME']").val(userNames);
					$("input[name='"+oldNodeName+"_HANDLERID']").val(userIds);
    				art.dialog.removeData("selectUserInfo");
    			}
    		}
		}, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ChangeFlowTaskForm" method="post"
		action="executionController.do?saveOrUpdate">
		<div id="ChangeFlowTaskFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="exeId" id="exeId" value="${exeId}" />
			<input type="hidden" name="curNodeNames" id="curNodeNames" value="${curNodeNames}" />
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="3" class="dddl-legend"><div class="eui-dddltit"><a>环节信息</a></div></td>
				</tr>
				<c:forEach items="${trans}" var="tran">
				 <input type="hidden" name="${tran.OLD_NODENAME}_HANDLERID" />
				 <tr>
				    <td><span style="width: 80px;float:left;text-align:right;">环节名称：</span>
						<input type="text" style="width:150px;float:left;" readonly="readonly" 
						value="${tran.OLD_NODENAME}" class="eve-input" name="${tran.OLD_NODENAME}" />
					</td>
					<td><span style="width: 80px;float:left;text-align:right;">更改环节：</span>
					   <select style="width:150px;float:left;" class="eve-input" name="${tran.OLD_NODENAME}_CHANGENAME">
					      <option  value="">请选择环节</option>
					      <c:forEach items="${tran.NODENAME_LIST}" var="nodeName">
					         <c:if test="${nodeName!=tran.OLD_NODENAME}">
					         <option  value="${nodeName}">${nodeName}</option>
					         </c:if>
					      </c:forEach>
					   </select>
					</td>
					<td><span style="width: 100px;float:left;text-align:right;">环节审核人：</span>
						<input type="text" style="width:150px;float:left;" readonly="readonly" 
						onclick="selectNodeUser('${tran.OLD_NODENAME}');"
						class="eve-input" name="${tran.OLD_NODENAME}_HANDLERNAME" />
					</td>
				</tr>
				</c:forEach>
			</table>

		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

