<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>

	<script type="text/javascript">
	$(function() {
		
		AppUtil.initWindowForm("sgryForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var info = {};
				$("#sgryForm").find("*[name]").each(function(){
					  var inputName= $(this).attr("name");
					  var inputValue = $(this).val();
					  //获取元素的类型
					  var fieldType = $(this).attr("type");
					  if(fieldType=="radio"){
						  var isChecked = $(this).is(':checked');
						  if(isChecked){
							  info[inputName] = inputValue;
						  }
					  }else if(fieldType=="checkbox"){
						  var inputValues = FlowUtil.getCheckBoxValues(inputName);
						  info[inputName] = inputValues;
					  }else if(fieldType!="button"){
						  info[inputName] = inputValue;
					  }          
				});
				art.dialog.data("sgryInfo", info);
				AppUtil.closeLayer();
				
			}
		}, "T_MSJW_SYSTEM_DICTYPE");
		
		$("[name='IDCARDTYPENUM']").attr("disabled",true);
	});
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="sgryForm" method="post">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="index" value="${index}">
	    <input type="hidden" name="currentTime" value="${currentTime}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			
			
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">姓名：</span>
					<input type="text" value="${info.STATION}" maxlength="16" class="eve-input validate[required]" name="STATION" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">工作岗位：</span>
					<eve:eveselect clazz="eve-input w280 validate[required] field_width" dataParams="PERSONNAME"
					dataInterface="dictionaryService.findDatasForSelect" value="${info.PERSONNAME}"
					defaultEmptyText="请选择工作岗位" name="PERSONNAME" id="PERSONNAME">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">证件类型：</span>
					<eve:eveselect clazz="eve-input w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" value="1">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">证件号码：</span>
					<input type="text" style="width:186px;float:left;" value="${info.PERSONIDENTITY}" maxlength="18" class="eve-input validate[required],custom[vidcard]" name="PERSONIDENTITY" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">联系电话：</span>
					<input type="text" style="width:186px;float:left;" value="${info.PERSONTEL}" maxlength="15" class="eve-input validate[required]" name="PERSONTEL" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">职称：</span>					
					<eve:eveselect clazz="eve-input w280 validate[required] field_width" dataParams="PERSONTITLE"
					dataInterface="dictionaryService.findDatasForSelect" value="${info.PERSONTITLE}"
					defaultEmptyText="请选择职称" name="PERSONTITLE" id="PERSONTITLE">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">专业：</span>
					<input type="text" style="width:186px;float:left;" value="${info.PERSONPROF}" maxlength="32" class="eve-input validate[required]" name="PERSONPROF" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">证书编号：</span>
					<input type="text" style="width:186px;float:left;" value="${info.PERSONCERTID}" maxlength="32" class="eve-input validate[required]" name="PERSONCERTID" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 100px;float:left;text-align:right;">从业年限：</span>
					<input type="text" style="width:186px;float:left;" value="${info.OBTAINEDYEAR}" maxlength="15" class="eve-input validate[required],custom[integerplus]" name="OBTAINEDYEAR" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 100px;float:left;text-align:right;">证书发证机关：</span>
					<input type="text" style="width:186px;float:left;" value="${info.CERTISSUINGORGAN}" maxlength="32" class="eve-input validate[required]" name="CERTISSUINGORGAN" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<c:if test="${type!=0}">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				</c:if>
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
