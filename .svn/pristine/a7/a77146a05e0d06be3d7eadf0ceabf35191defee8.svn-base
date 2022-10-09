<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DepartBusForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#DepartBusForm").serialize();
				var url = $("#DepartBusForm").attr("action");
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
							parent.$("#departBusGrid").datagrid("reload");
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
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="DepartBusForm" method="post" action="callController.do?saveOrUpdateDepartBus">
	    <div  id="DepartBusFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${departBus.RECORD_ID}">
		    <input type="hidden" name="DEPART_ID" value="${departBus.DEPART_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">部门名称：</span> <input
						type="text" style="width:200px;float:left;" readonly="readonly"
						value="${departBus.DEPART_NAME}" onclick="showSelectDeparts();"
						class="eve-input validate[required]" name="DEPART_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">部门所属业务类：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="winDepartNo"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择业务类" name="BUS_CODE" value="${departBus.BUS_CODE }">
						</eve:eveselect>
						<%-- <input type="hidden" name="WINDEPART_NO" id="noid" value="${win.WINDEPART_NO }">
						<input class="easyui-combobox"
								style="width:250px;" id="winDepartNo"
								data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=winDepartNo',editable:false,
								method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 250,panelHeight: '100',multiple:true,
								onSelect: function(rec){var val = $('#winDepartNo').combobox('getValues').join(',');$('#noid').val(val);} " />
						<font class="dddl_platform_html_requiredFlag">*</font> --%>
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
