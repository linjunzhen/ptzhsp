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

	function showSelectEnterprise(id,name,code){	
		$.dialog.open("projectSgxkController.do?enterpriseSelector&allowCount=1", {
			title : "企业查询",
			width:"800px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var enterpriseInfo = art.dialog.data("enterpriseInfo");
				if(enterpriseInfo){
					$("#"+id).find("[name='"+name+"']").val(enterpriseInfo[0].name);
					$("#"+id).find("[name='"+code+"']").val(enterpriseInfo[0].socialcreditcode);
				}
			}
		}, false);
	};
	</script>
<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
</style>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="dwinfoForm" method="post">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">单位名称：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPNAME" maxlength="100" placeholder="请输入单位名称" value="${info.CORPNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
					<c:if test="${type!=0}">
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('dwinfoForm','CORPNAME','CORPCREDITCODE');">查询</a>
					</c:if>
				</td>				
				<td>
					<span style="width: 130px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码" value="${info.CORPCREDITCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>	
				<td>
					<span style="width: 130px;float:left;text-align:right;">组织机构代码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码" value="${info.ORGCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">法定代表人姓名：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${info.LEGAL_NAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${info.LEGAL_IDTYPE}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${info.LEGAL_IDNO}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">项目负责人：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${info.PERSONNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">电话号码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${info.PERSONPHONE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" id="IDCARDTYPENUM"   value="${info.IDCARDTYPENUM}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;"
					name="PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${info.PERSONIDCARD}"/>
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
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="art.dialog.close();"/>
			</div>
		</div>
	</form>
</body>