<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("WinForm", function(form, valid) {
			if (valid) {
				var winDepartNo = $("input[name='WINDEPART_NO']").val();
				if(winDepartNo==null||winDepartNo==''){
					parent.art.dialog({
						content: "请选择业务类",
						icon:"warning",
						ok: true
					});
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#WinForm").serialize();
				var url = $("#WinForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$("#WinGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},null);
		$("#winDepartNo").combobox('setValues','${win.WINDEPART_NO }'.split(','));
	});
	

	   
    function showSelectDeparts(){
    	var departId = $("input[name='DEPART_ID']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				$("input[name='DEPART_ID']").val(selectDepInfo.departIds);
        			$("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
    			}
    		}
    	}, false);
    }
    
    function selectUser(){
    	var userIds = $("input[name='USER_ID']").val();
    	parent.$.dialog.open("sysUserController.do?selector&allowCount=1&userIds="
				+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectUserInfo = art.dialog.data("selectUserInfo");
    			if(selectUserInfo&&selectUserInfo.userIds){
    				$("input[name='USER_ID']").val(selectUserInfo.userIds);
    				$("input[name='FULLNAME']").val(selectUserInfo.userNames);
    				$("input[name='USERNAME']").val(selectUserInfo.userAccounts);
    				$("input[name='USERNAMES']").val("("+selectUserInfo.userNames+")("+selectUserInfo.userAccounts+")");
    			}
    		}
		}, false);
    }
    
    
    function setScreen(winNo){
    	AppUtil.ajaxProgress({
			url : "callController.do?getScreenNo",
			params : {winNo:winNo},
			callback : function(resultJson) {
				$("#screenNo").val(resultJson.msg);
			}
		});
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="WinForm" method="post" action="callController.do?saveOrUpdateWin">
	    <div  id="WinFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="WIN_ID" value="${win.WIN_ID}">
		    <input type="hidden" name="DEPART_ID" value="${win.DEPART_ID}">
		    <input type="hidden" name="USER_ID" value="${win.USER_ID}">
		    <input type="hidden" name="USERNAME" value="${win.USERNAME}">
		    <input type="hidden" name="FULLNAME" value="${win.FULLNAME}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td ><span
						style="width: 100px;float:left;text-align:right;">部门名称：</span> <input
						type="text" style="width:250px;float:left;" readonly="readonly"
						value="${win.DEPART_NAME}" onclick="showSelectDeparts();"
						class="eve-input validate[required]" name="DEPART_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">所属大厅：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="roomNo"
						dataInterface="dictionaryService.findDatasForSelect" value="${win.BELONG_NO}"
						defaultEmptyText="选择大厅编号"  name="BELONG_NO">
						</eve:eveselect>	
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">窗口编号：</span>
						<eve:eveselect clazz="tab_text validate[required]" 
							dataInterface="callService.findWinsForSelect" dataParams=""
							defaultEmptyText="选择窗口" name="WIN_NO" value="${win.WIN_NO }"
							onchange="setScreen(this.value);">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td><span style="width:100px;float:left;text-align:right;">屏编号：</span>
						<input type="text" style="width:150px;float:left;"
						value="${win.SCREEN_NO}" maxlength="4" id="screenNo"
						class="eve-input " readonly="readonly"
						name="SCREEN_NO" />
					</td>
				</tr>

				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">用户名称：</span> <input
						type="text" style="width:250px;float:left;" readonly="readonly"
						value="${win.USERNAMES}" class="eve-input validate[required]" name="USERNAMES" onclick="selectUser();"/> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td clospan="2"><span style="width: 100px;float:left;text-align:right;">窗口所属业务类：</span>
						<%-- <eve:eveselect clazz="tab_text validate[required]" dataParams="winDepartNo"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择业务类" name="WINDEPART_NO" value="${win.WINDEPART_NO }">
						</eve:eveselect> --%>
						<input type="hidden" name="WINDEPART_NO" id="noid" value="${win.WINDEPART_NO }">
						<input class="easyui-combobox"
								style="width:250px;" id="winDepartNo"
								data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=winDepartNo',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 250,panelHeight: '100',multiple:true,
								onChange: function(rec){var val = $('#winDepartNo').combobox('getValues').join(',');$('#noid').val(val);} " />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
