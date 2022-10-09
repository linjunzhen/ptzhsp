<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>用户中心--平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css"
	href="webpage/website/common/css/style.css">
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<script type="text/javascript"
	src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
<script type="text/javascript"
	src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<!-- 引入验证库 -->
<eve:resources
	loadres="apputil,easyui,artdialog,json2,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript"> 

$(function(){
	var username = '${sessionScope.curLoginMember.YHMC}';
	if(null==username||""==username){
		window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login";
	}
        AppUtil.initWindowForm("ADDXX", function(form, valid) {
            if (valid) {
                var formData = $("#ADDXX").serialize();
                var url = $("#ADDXX").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                              alert( resultJson.msg);
                              window.location.reload()
                        } else {
                            alert( resultJson.msg);
                        }
                    }
                });
            }
        }, "T_BSFW_USERRECADD");
});

function sjdzlist(sjdzList){
	$("#sjdzlist").html("");
    var newhtml = "";
    for(var i=0; i<sjdzList.length; i++){
	    newhtml +="<tr>"; 
	    newhtml += "<td width=\"25px\" valign=\"top\">"+(i+1)+"</td>";
	    newhtml += "<td width=\"30px\" valign=\"top\">"+sjdzList[i].REC_NAME+"</td>";
	    newhtml += "<td width=\"80px\" valign=\"top\">"+sjdzList[i].PROVINCE+""+sjdzList[i].CITY+"</td>";
	    newhtml += "<td width=\"120px\" valign=\"top\">"+sjdzList[i].DETAIL_ADD+"</td>";
	    newhtml += "<td width=\"40px\" valign=\"top\">"+sjdzList[i].POSTCODE+"</td>";
	    newhtml += "<td width=\"50px\" valign=\"top\">"+sjdzList[i].MOBILE+"</td>";
	    if(sjdzList[i].IS_DEFAULT=="1"){
		    newhtml += "<td width=\"20px\" valign=\"top\">是</td>";
	    }else{
		    newhtml += "<td width=\"20px\" valign=\"top\">否</td>";
	    }
	    //newhtml += "<td width=\"70px\" valign=\"top\">&nbsp;&nbsp;&nbsp;编辑&nbsp;&nbsp;删除&nbsp;&nbsp;&nbsp;&nbsp;设为默认地址</td>";
	    newhtml += "<td width=\"70px\" valign=\"top\">";
		newhtml += "<a href=\"javascript:void(0);\" onclick=\"makeDefault('"
			+sjdzList[i].ADD_ID+"','"+sjdzList[i].USER_ID+"');\" class=\"userbtn3\">设为默认</a>";
    	newhtml += "<a href=\"javascript:void(0);\" onclick=\"deleteFlow('"
                +sjdzList[i].ADD_ID+"');\" class=\"userbtn3\">删除</a>";
		newhtml += "</td>";
	    newhtml +="</tr>"; 
    } 
    $("#sjdzlist").html(newhtml);
}


function deleteFlow(addId){
	art.dialog.confirm("您确定要删除该记录吗?", function() {
			var layload = layer.load('正在提交请求中…');  
			var selectColNames =addId;
			$.post("userInfoController.do?multiDelDraft",{
				   selectColNames:selectColNames
			    }, function(responseText, status, xhr) {
			    	layer.close(layload);
			    	var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						reload();
					}else{
						art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
			});
		});
}
function makeDefault(addId,userId){
	var layload = layer.load('正在提交请求中…');  
	var addId =addId;
	var userId =userId;
	$.post("userInfoController.do?makeDefault",{
			addId:addId,
			userId:userId
	    }, function(responseText, status, xhr) {
	    	layer.close(layload);
	    	var resultJson = $.parseJSON(responseText);
			if(resultJson.success == true){
				art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
				    ok: true
				});
				reload();
			}else{
				art.dialog({
					content: resultJson.msg,
					icon:"error",
				    ok: true
				});
			}
	});
}

function saveAddress() {
	var REC_NAME = $("input[name='REC_NAME']").val();
	var MOBILE = $("input[name='MOBILE']").val();
	var POSTCODE = $("input[name='POSTCODE']").val();
	var PROVINCE = $("input[name='PROVINCE']").val();
	var CITY = $("input[name='CITY']").val();
	var COUNTY = $("input[name='COUNTY']").val();
	var DETAIL_ADD = $("input[name='DETAIL_ADD']").val();
	if (!REC_NAME) {
		art.dialog({
			content: "请输入收件人姓名!",
			icon:"warning",
			ok: true
		});
		return false;
	}
	if (!MOBILE) {
		art.dialog({
			content: "请输入手机号码!",
			icon:"warning",
			ok: true
		});
		return false;
	}
	if (!POSTCODE) {
		art.dialog({
			content: "请输入邮政编码!",
			icon:"warning",
			ok: true
		});
		return false;
	}
	if (!(PROVINCE && CITY )) {
		art.dialog({
			content: "请选择所属省市!",
			icon:"warning",
			ok: true
		});
		return false;
	}
	if (!DETAIL_ADD) {
		art.dialog({
			content: "请输入详细地址!",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$('#ADDXX').submit();
}
</script>

</head>
<body class="userbody">
<!-- header 0 -->
<script src="https://zwfw.fujian.gov.cn/thirdSys/header.js?unid=CDFF203D00F3BE60FD220C30B8C02EC9"></script>
<!-- header 1 -->
    
	<div class="container lbpadding20">
		<div class="main_t"></div>
		<div class="nmain clearfix">
			<%--开始引入用户中心的左侧菜单 --%>
			<jsp:include page="./yhmenu.jsp">
				<jsp:param value="sjdz" name="menuKey" />
			</jsp:include>
			<%--结束引入用户中心的左侧菜单 --%>
			<div class="nmainR">
				<div class="nmainRtitle" style="margin-top:0px;">
					<h3>我的收件地址</h3>
				</div>
				<div class="nuserC">
					<form id="ADDXX" action="userInfoController.do?saveUserRecAdd">
						<input type="hidden" name="USER_ID"
							value="${sessionScope.curLoginMember.USER_ID}" /> <input
							type="hidden" name="ADD_ID" id="ADD_ID" value="" />
						<div>
							<table cellpadding="0" cellspacing="0" class="bstable3 tmargin12">
								<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
									<tr>
										<th>收件人姓名 <font class="dddl_platform_html_requiredFlag" style="color: red;">*</font>
										</th>
										<td><input type="text"
											class="eve-input validate[maxSize[30]]"
											name="REC_NAME" value="${sessionScope.curLoginMember.YHMC}" />
										</td>
										<th>手机号码 <font class="dddl_platform_html_requiredFlag" style="color: red;">*</font></th>
										<td><input type="text"
											class="eve-input validate[maxSize[11]],custom[mobilePhone]"
											name="MOBILE" value="${sessionScope.curLoginMember.SJHM}" />
										</td>
									</tr>
									<tr>
										<th>固定电话</th>
										<td><input type="text"
											class="eve-input validate[],custom[fixPhoneWithAreaCode]"
											name="FIXED_TEL" value="${sessionScope.curLoginMember.DHHM}" />
										</td>
										<th>邮政编码 <font class="dddl_platform_html_requiredFlag" style="color: red;">*</font></th>
										<td><input type="text"
											class="eve-input validate[maxSize[6],custom[chinaZip]]"
											name="POSTCODE" value="${sessionScope.curLoginMember.YZBM}" />
										</td>
									</tr>
									<tr>
										<th>所属省市</th>
				<td colspan="3">					
					<input name="PROVINCE" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);  
							   }
							}
	                " value="" />					
					<input name="CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);  
							   }
							}
	                " value="" />	  	
					<input name="COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);  
							   }
							}
	                " value="" />              					
				</td>
									</tr>
									<tr>
										<th>详细地址 <font class="dddl_platform_html_requiredFlag" style="color: red;">*</font></th>
										<td ><input type="text"
											class="eve-input validate[maxSize[500]]"
											name="DETAIL_ADD" value="" />
										</td>
									</tr>
							<input type="checkbox" class="checkbox" name="IS_DEFAULT"
								value="1">设为默认地址
								</c:if>
							</table>
							<div class="Ctext clearfix lbpadding32">
								<a href="javascript:void(0);" onclick="saveAddress();"
								   class="btn2">保 存</a>
							</div>
						</div>
					</form>
				</div>
				<div class="nmainRtitle" style="margin-top:0px;">
					<h3>收货地址信息</h3>
				</div>
				<table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
					<tr>
						<th width="40px">序号</th>
						<th width="50px">收件人</th>
						<th width="100px">所属省市</th>
						<th width="200px">详细地址</th>
						<th width="50px">邮编</th>
						<th width="80px">手机号</th>
						<th width="50px">是否默认地址</th>
						<th width="100px" style="background-image:none;">操作</th>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10"
					id="sjdzlist">
				</table>
				<%--开始引入分页JSP --%>
				<jsp:include page="../common/page.jsp">
					<jsp:param value="userInfoController.do?wdsjdzlist" name="pageURL" />
					<jsp:param value="sjdzlist" name="callpage" />
					<jsp:param value="10" name="limitNum" />
				</jsp:include>
				<%--结束引入分页JSP--%>
			</div>
		</div>
	</div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<script type="text/javascript">jQuery(".nmainR").slide({titCell:".nmainRtitle1 li",mainCell:".nuserC"})</script>
	<%--结束编写尾部文件 --%>
</body>
</html>
