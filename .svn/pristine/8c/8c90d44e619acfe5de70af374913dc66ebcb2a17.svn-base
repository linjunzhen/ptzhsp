<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			var childname;
			if('${materForm.CHILD_FORM_NAME }'!=null&&'${materForm.CHILD_FORM_NAME }'!=''&&'${materForm.CHILD_FORM_NAME }'!=undefined){
				childname = '${materForm.CHILD_FORM_NAME }';
			}else{
				childname = 'RELATED_JJ_ABCRAPPLY';
			}
			var childRecordId = "";
			$.post("domesticControllerController/onLineForm.do",{
				formName : childname,
				operType : 'write',
				exeId : '${materForm.EXE_ID }',
				recordId : childRecordId
			}, function(responseText, status, xhr) {
				$("#childForm").html(responseText);
				$('#childForm').find('input,textarea').attr("readonly", true);
				$('#childForm').find('select').attr("disabled", "disabled");
				$('#childForm').find(":radio,:checkbox").attr('disabled',"disabled");
				$('#childForm').find(".laydate-icon").attr('disabled',"disabled");
				$('#childForm').find(".add-btn,.del-btn").attr('style',"display:none");
			});
			
			$('#MVMAPPLY_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
		});
		
		function downloadDoc(){
			var recordId = $("#childRecord").val();
			var fornName = '${materForm.CHILD_FORM_NAME }';
			window.location.href=__ctxPath+"/domesticControllerController/downLoadRelatedMater.do?recordId="+recordId
			+"&fornName="+fornName;
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="MVMAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }" id="recordId"/>
						<div class="syj-title1">
							<a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
							<span>申请信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>登记维修类别：</th>
									<td>
										<input type="radio" name="CHILD_FORM_NAME" value="RELATED_JJ_ABCRAPPLY" <c:if test="${materForm.CHILD_FORM_NAME!='RELATED_JJ_CCRAPPLY'&&materForm.CHILD_FORM_NAME!='RELATED_JJ_MBRAPPLY' }">checked="checked"</c:if> >一、二类汽车维修
										<input type="radio" name="CHILD_FORM_NAME" value="RELATED_JJ_CCRAPPLY" <c:if test="${materForm.CHILD_FORM_NAME=='RELATED_JJ_CCRAPPLY' }">checked="checked"</c:if> >三类汽车维修
										<input type="radio" name="CHILD_FORM_NAME" value="RELATED_JJ_MBRAPPLY" <c:if test="${materForm.CHILD_FORM_NAME=='RELATED_JJ_MBRAPPLY' }">checked="checked"</c:if> >摩托车维修
									</td>
								</tr>
							</table>
						</div>
						
					</form>
				</div>
			</div>
		</div>
		<div id="childForm">
			
		</div>
		
        <%-- <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="gosubmit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div> --%>
	</div>
</body>