<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>

	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
	<script type="text/javascript">
	$(function() {
		
		AppUtil.initWindowForm("dwinfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var info = {};
				$("#dwinfoForm").find("*[name]").each(function(){
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
				art.dialog.data("dwInfo", info);
				AppUtil.closeLayer();
				
			}
		}, "T_MSJW_SYSTEM_DICTYPE");
		
		$("[name='IDCARDTYPENUM']").attr("disabled",true);
	});
	</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="dwinfoForm" method="post">
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
					<span style="width: 140px;float:left;text-align:right;">单位名称：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPNAME" maxlength="100" placeholder="请输入单位名称" value="${info.CORPNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>				
				<td>
					<span style="width: 140px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码" value="${info.CORPCREDITCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 140px;float:left;text-align:right;">勘察单位资质等级：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="KCDWZZDJ"
					dataInterface="dictionaryService.findDatasForSelect" 
					defaultEmptyText="请选择勘察单位资质等级" name="QUALLEVEL" value="${info.QUALLEVEL}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">勘察单位资质证书号：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" 
					name="QUALCERTNO"  placeholder="请输入勘察单位资质证书号"  maxlength="32"   value="${info.QUALCERTNO}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>	
				<td>
					<span style="width: 140px;float:left;text-align:right;">组织机构代码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码" value="${info.ORGCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">法定代表人姓名：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${info.LEGAL_NAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 140px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${info.LEGAL_IDTYPE}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${info.LEGAL_IDNO}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 140px;float:left;text-align:right;">项目负责人：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${info.PERSONNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">电话号码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${info.PERSONPHONE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 140px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" id="IDCARDTYPENUM"   value="${info.IDCARDTYPENUM}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;"
					name="PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${info.PERSONIDCARD}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="width: 140px;float:left;text-align:right;">注册类型及等级：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBSPECIALTYTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect" 
					defaultEmptyText="请选择注册类型及等级" name="SPECIALTYTYPNUM"   value="${info.SPECIALTYTYPNUM}">
					</eve:eveselect>
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
