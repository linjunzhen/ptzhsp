<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BusManageForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#BusManageForm").serialize();
				var url = $("#BusManageForm").attr("action");
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
							parent.$("#busManageGrid").datagrid("reload");
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
		},"T_CKBS_BUSINESSDATA");
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
    				art.dialog.removeData("selectDepInfo");
    			}
    		}
    	}, false);
    }
    
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BusManageForm" method="post" action="callSetController.do?saveOrUpdateBus">
	    <div  id="BusManageFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="DATA_ID" value="${busManageinfo.DATA_ID}">
		    <input type="hidden" name="DEPART_ID" value="${busManageinfo.DEPART_ID}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 120px;float:left;text-align:right;">单位/部门名称：</span> <input
						type="text" style="width:250px;float:left;" readonly="readonly"
						value="${busManageinfo.DEPART_NAME}" onclick="showSelectDeparts();"
						class="eve-input validate[required]" name="DEPART_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px;float:left;text-align:right;">业务名称：</span> <input
						type="text" style="width:250px;float:left;"
						value="${busManageinfo.BUSINESS_NAME}" maxlength="50"
						class="eve-input validate[required]" name="BUSINESS_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span
						style="width: 120px;float:left;text-align:right;">业务编码：</span> <input
						type="text" style="width:250px;float:left;" <c:if test="${busManageinfo.DATA_ID!=null}">disabled="disabled"</c:if>
						value="${busManageinfo.BUSINESS_CODE}" id="BUSINESS_CODE" maxlength="8"
						class="eve-input validate[required],ajax[ajaxVerifyValueExist],custom[onlyNumberSp]" name="BUSINESS_CODE" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px;float:left;text-align:right;">所属大厅编号：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="roomNo"
						dataInterface="dictionaryService.findDatasForSelect" value="${busManageinfo.BELONG_ROOM}"
						defaultEmptyText="选择大厅编号"  name="BELONG_ROOM">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span
						style="width: 120px;float:left;text-align:right;">是否可预约：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px"
							defaultvalue="1" value="${busManageinfo.IS_APPOINT}"
							fieldname="IS_APPOINT">
						</eve:radiogroup>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 120px;float:left;text-align:right;">是否快件业务：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px"
							defaultvalue="0" value="${busManageinfo.IS_EXPRESS}"
							fieldname="IS_EXPRESS">
						</eve:radiogroup>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span
							style="width: 120px;float:left;text-align:right;">失信人限制取号：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px"
										value="${busManageinfo.IS_CHECKLOST}"
										fieldname="IS_CHECKLOST">
						</eve:radiogroup>
						<font class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
