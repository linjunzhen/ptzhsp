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
    <title>网上评议_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">	
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,validationegine"></eve:resources>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style>
	.tab_text_wspy1 {
		width: 180px;
		height: 24px;
		line-height: 24px;
		border: 1px solid #bbb;
	}
	</style>

	<script type="text/javascript">

		$(function() {
			AppUtil.initWindowForm("commentForm", function(form, valid) {
				if (valid) {
					var formData = $("#commentForm").serialize();
					var url = $("#commentForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "提交成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								window.location.href = 	window.location.href;	
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
			}, "T_HD_COMMENT");
		});
		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='password']").val("");
			$("textarea[name='COMMENT_REMARK']").val("");	
			$("select[name='COMMENT_DEGREE']").val("");	
			$("select[name='COMMENT_JOB']").val("");				
			$("input[name='COMMENT_DEPTID']").val("");
			//$("#commentForm input[name='CONSULT_TYPE']").removeAttr("checked");
			//$("#commentForm input[name='CONSULT_TYPE']:eq(0)").prop("checked",'checked');
			setComment('fzzc_jbmy','COMMENT_FZZC',1);
			setComment('bsxl_jbmy','COMMENT_BSXL',1);
			setComment('ljqz_jbmy','COMMENT_LJQZ',1);
			setComment('yfxz_jbmy','COMMENT_YFXZ',1);
			setComment('fwzl_jbmy','COMMENT_FWZL',1);
		}
		function showSelectDeparts(){
			var departId = $("input[name='COMMENT_DEPTID']").val();
			$.dialog.open("departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
					+"checkCascadeN=", {
				title : "组织机构选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if(selectDepInfo){
						$("input[name='COMMENT_DEPTID']").val(selectDepInfo.departIds);
						$("input[name='COMMENT_DEPT']").val(selectDepInfo.departNames);
						art.dialog.removeData("selectDepInfo");
					}
				}
			}, false);
		}
		function setComment(obj,name,value){
			$("input[name='"+name+"']").val(value);
			$("#"+obj).parent().children('a').removeClass("tabOn");
			$("#"+obj).toggleClass('tabOn');
		}
	</script>
</head>

<body>
	<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>网上评议</i></div>
    	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
                    	<li><a href="webSiteController/view.do?navTarget=website/hd/zxhd">业务咨询</a></li>
                        <li class="subOn"><a href="commentController.do?wspy">网上评议</a></li>
                        <li><a href="complainController.do?tsjd">投诉监督</a></li>                        
                        <li><a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
				<form id="commentForm" method="post" action="commentController/saveComment.do">
					<!--==========隐藏域部分开始 ===========-->
					<input type="hidden" name="COMMENT_DEPTID">
					<input type="hidden" name="COMMENT_FZZC" value="1"><!--贯彻落实中央和市委市政府重大决策部署和方针政策-->	
					<input type="hidden" name="COMMENT_BSXL" value="1"><!--办事效率-->	
					<input type="hidden" name="COMMENT_LJQZ" value="1"><!--廉洁勤政-->	
					<input type="hidden" name="COMMENT_YFXZ" value="1"><!--依法行政-->					
					<input type="hidden" name="COMMENT_FWZL" value="1"><!--服务质量-->	
					<input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}">
					<!--==========隐藏域部分结束 ===========-->
					<div class="listsrh">请选择需要评议的委办局：<input class="validate[required]" type="text" name="COMMENT_DEPT" placeholder="请选择需要评议的委办局" style="width:172px;color: #000;"  readonly="readonly" onclick="showSelectDeparts();"/><a href="javascript:showSelectDeparts();">选 择</a></div>
					<table cellpadding="0" cellspacing="1" class="listtable">
						<tr>
							<th>贯彻落实中央和市委市政府<br />重大决策部署和方针政策</th>
							<td><a id="fzzc_my" href="javascript:setComment('fzzc_my','COMMENT_FZZC',2);">满意</a><a id="fzzc_jbmy" href="javascript:setComment('fzzc_jbmy','COMMENT_FZZC',1);" class="tabOn">基本满意</a><a id="fzzc_bmy" href="javascript:setComment('fzzc_bmy','COMMENT_FZZC',0);">不满意</a><a id="fzzc_blj" href="javascript:setComment('fzzc_blj','COMMENT_FZZC',-1);">不了解</a></td>
						</tr>
						<tr>
							<th>办事效率</th>
							<td><a id="bsxl_my" href="javascript:setComment('bsxl_my','COMMENT_BSXL',2);">满意</a><a id="bsxl_jbmy" href="javascript:setComment('bsxl_jbmy','COMMENT_BSXL',1);" class="tabOn">基本满意</a><a id="bsxl_bmy" href="javascript:setComment('bsxl_bmy','COMMENT_BSXL',0);">不满意</a><a id="bsxl_blj" href="javascript:setComment('bsxl_blj','COMMENT_BSXL',-1);">不了解</a></td>
						</tr>
						<tr>
							<th>廉洁勤政</th>
							<td><a id="ljqz_my" href="javascript:setComment('ljqz_my','COMMENT_LJQZ',2);">满意</a><a id="ljqz_jbmy" href="javascript:setComment('ljqz_jbmy','COMMENT_LJQZ',1);" class="tabOn">基本满意</a><a id="ljqz_bmy" href="javascript:setComment('ljqz_bmy','COMMENT_LJQZ',0);">不满意</a><a id="ljqz_blj" href="javascript:setComment('ljqz_blj','COMMENT_LJQZ',-1);">不了解</a></td>
						</tr>
						<tr>
							<th>依法行政</th>
							<td><a id="yfxz_my" href="javascript:setComment('yfxz_my','COMMENT_YFXZ',2);">满意</a><a id="yfxz_jbmy" href="javascript:setComment('yfxz_jbmy','COMMENT_YFXZ',1);" class="tabOn">基本满意</a><a id="yfxz_bmy" href="javascript:setComment('yfxz_bmy','COMMENT_YFXZ',0);">不满意</a><a id="yfxz_blj" href="javascript:setComment('yfxz_blj','COMMENT_YFXZ',-1);">不了解</a></td>
						</tr>
						<tr>
							<th>服务质量</th>
							<td><a id="fwzl_my" href="javascript:setComment('fwzl_my','COMMENT_FWZL',2);">满意</a><a id="fwzl_jbmy" href="javascript:setComment('fwzl_jbmy','COMMENT_FWZL',1);" class="tabOn">基本满意</a><a id="fwzl_bmy" href="javascript:setComment('fwzl_bmy','COMMENT_FWZL',0);">不满意</a><a id="fwzl_blj" href="javascript:setComment('fwzl_blj','COMMENT_FWZL',-1);">不了解</a></td>
						</tr>
						<tr>
							<th>补充说明</th>
							<td><textarea style="width: 430px; height: 80px;" name="COMMENT_REMARK" class="validate[],maxSize[500]"></textarea></td>
						</tr>
						<tr>
							<th><span>*</span> 姓名</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="10"   class="tab_text validate[required],custom[onlychineseLetter],maxSize[10]" name="COMMENT_NAME" value="${sessionScope.curLoginMember.YHMC}"/></p></td>
						</tr>
						<tr>
							<th><span>*</span> 年龄</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="3"   class="tab_text validate[required],min[1],max[100],custom[onlyNumberSp]" name="COMMENT_AGE"/></p></td>
						</tr>
						<tr>
							<th><span>*</span> 最高学历</th>
							<td>
								<eve:eveselect clazz="tab_text_wspy1 validate[required]" dataParams="XL" dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择最高学历" name="COMMENT_DEGREE">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><span>*</span> 您的职业</th>
							<td>
								<eve:eveselect clazz="tab_text_wspy1 validate[required]" dataParams="HDJOB" dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择您的职业" name="COMMENT_JOB">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><span>*</span> 身份证号码</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="30" name="COMMENT_IDCARD" class="tab_text validate[required],custom[vidcard],maxSize[30]" <c:if test="${sessionScope.curLoginMember.ZJLX=='SF'}"> value="${sessionScope.curLoginMember.ZJHM}"</c:if>/></p></td>
						</tr>
						<tr>
							<th><span>*</span> 电子邮箱</th>
							<td><p><input  style="width:220px;color: #000;" type="text" maxlength="30" name="COMMENT_EMAIL" class="tab_text validate[required],custom[email],maxSize[30]"  value="${sessionScope.curLoginMember.DZYX}"/></p></td>
						</tr>
						<tr>
							<th><span>*</span> 手机号码</th>
							<td><p><input  style="width:220px;color: #000;" type="text"  name="COMMENT_PHONE" class="tab_text validate[required],custom[mobilePhone]" value="${sessionScope.curLoginMember.SJHM}"/></p></td>
						</tr>
					</table>
					<div class="sbtbtn"><a href="javascript:void(0);" onclick="$('#commentForm').submit();" class="btn2">提  交</a></div>
				</form>
            </div>
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
