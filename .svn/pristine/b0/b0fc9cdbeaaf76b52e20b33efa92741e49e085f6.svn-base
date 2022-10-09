<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>业务咨询_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/plug-in/passJs/des.js" charset="UTF-8"></script> 
	
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,validationegine,autocomplete"></eve:resources>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">

		$(function() {
			AppUtil.initWindowForm("consultForm", function(form, valid) {
				if (valid) {
					//var formData = $("#consultForm").serialize();
					var x = $("#USER_INFO_FORM").serializeArray();
				var param="";
  				$.each(x, function(i, field){
  					if(field.name=="userPass"){
  						param+=field.name+"="+strEnc(field.value,"1","2","3")+"&";
  					}else{
  						param+=field.name+"="+field.value+"&";
  					}
  				});
  					param=param.substr(0,param.length-1);
					var url = $("#consultForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : param,//formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "提交成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								window.location.href = window.location.href ;	
							} else {
								art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});	
							}
						}
					});
				}
			}, "T_WSBS_SERVICEITEM");
			//加载自动补全数据
			loadAutoCompleteDatas();
			//咨询类型点击
			$("input[name='CONSULT_TYPE']").on("click", function(event) {
				if (this.value === "0") {					
					$("#deptTr").hide();
					$("#itemTr").show();
					$("input[name='CONSULT_DEPT']").attr("disabled",true);
					$("input[name='CONSULT_ITEMS']").attr("disabled",false);
					$("input[name='CONSULT_DEPTID']").val("");	
					$("input[name='CONSULT_DEPT']").val("");					
				} else {
					$("#deptTr").show();
					$("#itemTr").hide();	
					$("input[name='CONSULT_DEPT']").attr("disabled",false);
					$("input[name='CONSULT_ITEMS']").attr("disabled",true);
					$("input[name='CONSULT_ITEMID']").val("");		
					$("input[name='CONSULT_ITEMS']").val("");	
				}
			});
			var deptId = '${deptId}';
			if(null!=deptId&&''!=deptId){
				$("input[name='CONSULT_TYPE'][value='1']").click();
			}
		});
		function pressKey(event){				
			
		}
		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='password']").val("");
			$("textarea[name='CONSULT_CONTENT']").val("");
			$("input[name='CONSULT_TYPE'][value='0']").click();			
			$("input[name='CONSULT_DEPTID']").val("");			
			$("input[name='CONSULT_ITEMID']").val("");
		}
		function showSelectDeparts(){
			var departId = $("input[name='CONSULT_DEPTID']").val();
			var url = "departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
			+"checkCascadeN=";
			$.dialog.open(url, {
				title : "部门选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if(selectDepInfo){
						$("input[name='CONSULT_DEPTID']").val(selectDepInfo.departIds);
						$("input[name='CONSULT_DEPT']").val(selectDepInfo.departNames);
					}
				}
			}, false);
		};
		//自动补全
		function loadAutoCompleteDatas() {
			$.post("consultController/load.do", {

			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				$("#AutoCompleteInput").autocomplete(
						resultJson,
					{
						matchContains : true,
						mustMatch : true,
						formatItem : function(row, i, max) {
							//下拉框显示
							return "<div>" + row.ITEM_NAME+"</div>";
						},
						formatMatch : function(row) {
							//查询条件
							return row.ITEM_NAME+row.PINYIN;
						},
						formatResult : function(row, i, max) {
							//显示在文本框中
							return row.ITEM_NAME;
						}
					}).result(
					function(event, data, formatted) {
						if(null!=data){							
							$("input[name='CONSULT_ITEMID']").val(data.ITEM_ID);
						}
					}

					);
				});
			}
	function bindItem(){
		$.dialog.open("consultController/selector.do", {
			title : "事项选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectItemInfo = art.dialog.data("selectItemInfo");
				if(selectItemInfo){
					$("input[name='CONSULT_ITEMID']").val(selectItemInfo.itemIds);
					$("input[name='CONSULT_ITEMS']").val(selectItemInfo.itemNames);
					art.dialog.removeData("selectItemInfo");
				}
			}
		}, false);
	}
	</script>
	
  </head>
  
  <body>
    <%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
    
    <div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>业务咨询</i></div>
<%--    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>咨询互动</i> > <i>业务咨询</i></div>--%>
    	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
<%--                    	<li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/zxhd">业务咨询</a></li>--%>
                        <li><a href="commentController.do?wspy">网上评议</a></li>
<%--                        <li><a href="complainController.do?tsjd">投诉监督</a></li>--%>
                        <li><a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
				<form id="consultForm" method="post" action="consultController/saveConsult.do">
					<!--==========隐藏域部分开始 ===========-->
					<input type="hidden" name="CONSULT_DEPTID" value="${deptId}">
					<input type="hidden" name="CONSULT_ITEMID" value="${CONSULT_ITEMID}">
					<!--==========隐藏域部分结束 ===========-->
					<table cellpadding="0" cellpadding="0" class="tableno">
						<tr>
							<th>咨询类型：</th>
							<td><input type="radio" name="CONSULT_TYPE" value="0" checked="checked" /> 办事咨询</td>
							<td colspan="2"><input type="radio"  name="CONSULT_TYPE" value="1" /> 其他咨询</td>
						</tr>
						<tr id="itemTr">
						<th valign="top"><span><i>*</i></span> 咨询事项：</th>
                        <td valign="top" colspan="3">
						<p>
							<input  value="${CONSULT_ITEMS}" class="validate[required]" type="text" id="AutoCompleteInput" placeholder="请选择需要咨询的事项" style="width:530px" name="CONSULT_ITEMS" onkeyup="pressKey(event)" onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"/><a href="javascript:bindItem();">选 择</a>
						</p>
						</td></tr>						
						<tr id="deptTr" style="display:none;">
						<th valign="top"><span><i>*</i></span> 咨询部门：</th>
						<td valign="top" colspan="3"><p><input onclick="showSelectDeparts();" value="${deptName}" class="validate[required]" type="text" name="CONSULT_DEPT" placeholder="请选择需要咨询的部门" style="width:530px"  readonly="readonly"  disabled="disabled"/><a href="javascript:showSelectDeparts();">选 择</a></p><p>* 若不知道对应部门，请选择行政服务中心</p></td>
						</tr>						
						<tr>
							<th valign="top"><span><i>*</i></span> 咨询标题：</th>
							<td colspan="3"><p><input class="validate[required],maxSize[30]"  type="text" name="CONSULT_TITLE" style="width:588px; color: #000;" maxlength="30"/></p></td>
						</tr>
						<tr>
							<th valign="top"><span><i>*</i></span> 咨询内容：</th>
							<td colspan="3"><textarea class="validate[required],maxSize[500]"  name="CONSULT_CONTENT"></textarea></td>
						</tr>
						<c:if test="${sessionScope.curLoginMember==null}">
							<tr>
								<th><span><i>*</i></span> 账号：</th>
								<td><p><input  class="validate[required],maxSize[20]" type="text" style="width:220px" name="userName" /></p></td>
								<th><span><i>*</i></span> 密码：</th>
								<td><p><input  class="validate[required],maxSize[20],minSize[6]" type="password" style="width:220px" name="userPass" autocomplete="off"/></p></td>
							</tr>
						</c:if>
					</table>
					<div class="sbtbtn"><a  href="javascript:void(0);" onclick="$('#consultForm').submit();" class="btn2">提  交</a></div>
				</form>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
