<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

		$(function() {
			//设置字段不可修改	
			$("#TwQualificationForm input").each(function(index){
				$(this).attr("readonly","readonly");
			});
			$("#TwQualificationForm select").each(function(index){
				$(this).attr("disabled","disabled");
			});	
			$('#TwQualificationForm').find(".Wdate").attr('disabled',"disabled");
		});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="TwQualificationForm" method="post"
		action="twQualificationController.do?saveOrUpdate">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="QUALIFICATION_ID" value="${twQualification.QUALIFICATION_ID}"/>
		<input type="hidden" name="PHOTO_PATH" value="${twQualification.PHOTO_PATH}"/>
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>采信证书基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">姓名：</span>
					<input type="text" style="width:150px;float:left;" maxlength="7"
					class="eve-input validate[required]" value="${twQualification.USERNAME}"
					name="USERNAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				
				<td rowspan="4"><span style="width: 180px;float:left;text-align:right;">照片：</span>
				<c:if test="${twQualification.PHOTO_PATH!=null}">                   
                        <img id="iconLogo" width="130" height="125" border="0" src="<%=path%>/${twQualification.PHOTO_PATH}" style="border:0;"/>
                    </c:if>
                    <c:if test="${twQualification.PHOTO_PATH==null}">
                        <img id="iconLogo" width="130" height="125" border="0" src="<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg" style="border:0;"/>
                    </c:if>
                    </div>
                 </td>
            </tr>
            <tr>
				<td><span style="width: 180px;float:left;text-align:right;">性别：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="sex"
						dataInterface="dictionaryService.findDatasForSelect" value="${twQualification.SEX}" 
						defaultEmptyText="==选择性别==" name="SEX"></eve:eveselect><font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">出生日期：</span>
					 <input type="text" style="width:150px;float:left; height: 24px; line-height: 24px;margin-left: 4px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
					 name="BIRTHDAY" value="${twQualification.BIRTHDAY}"><font 
					 class="dddl_platform_html_requiredFlag">*</font></td>
			
			</tr>
			<tr>
				<td><span style="width: 180px;float:left;text-align:right;">台胞证件号：</span>
					<input type="text" style="width:150px;float:left;"
					 value="${twQualification.TB_ZJHM}" class="eve-input validate[required]"
					 name="TB_ZJHM"><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
				<td ><span style="width: 180px;float:left;text-align:right;">台湾地区职业资格及等级：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZYZG_LEVEL}" name="ZYZG_LEVEL" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>	
				<td ><span style="width: 180px;float:left;text-align:right;">职业（工种）及等级：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZY_LEVEL}" name="ZY_LEVEL" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>	
			</tr>
			<tr>
				<td ><span style="width: 180px;float:left;text-align:right;">证书号码：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.ZSHM}" name="ZSHM" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td ><span style="width: 180px;float:left;text-align:right;">证书编号：</span>
				    <c:if test="${twQualification.ZS_NUMBER==null}">
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
					value="${twQualification.ZS_NUMBER}" name="ZS_NUMBER" id="ZS_NUMBER"/><font
					class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
					 <c:if test="${twQualification.ZS_NUMBER!=null}">
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]" readonly='readonly'
					value="${twQualification.ZS_NUMBER}" name="ZS_NUMBER" id="ZS_NUMBER"/><font
					class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
					</td>	
			</tr>
			<tr>	
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">有效范围：</span>
					<input type="text" style="width:150px;float:left;" maxlength="30"
					class="eve-input validate[required]"
					value="${twQualification.YXFW}" name="YXFW" /><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>
			<tr>
						
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">证书有效期：</span>
					<input type="text" style="width:187px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZS_VALIDITY\')}'})" 
					 name="CXZS_TIME" id="CXZS_TIME" readonly="readonly" value="${twQualification.CXZS_TIME}">
				<span style="width: 20px;float:left;text-align:center;">至</span>
					<input type="text" style="width:187px;float:left; height: 24px; line-height: 24px;" 
					 class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'CXZS_TIME\')}'})" 
					 name="ZS_VALIDITY" id="ZS_VALIDITY" readonly="readonly" value="${twQualification.ZS_VALIDITY}">	<font
					class="dddl_platform_html_requiredFlag">*</font></td>	
			</tr>
			<tr>
				<td colspan='2'><span style="width: 180px;float:left;text-align:right;">备注：</span>
					<input type="text" style="width:550px;float:left;" maxlength="126"
					class="eve-input validate[]"
					value="${twQualification.BZ}" name="BZ" /></td>
			</tr>		
		</table>
	</form>
</body>

