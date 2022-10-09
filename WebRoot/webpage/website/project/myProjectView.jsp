<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
<eve:resources
		loadres="laydate,layer"></eve:resources>

<script type="text/javascript">
    var userType = '${sessionScope.curLoginMember.USER_TYPE}';
    $(function(){
        if(userType!='2'){
            alert("个人账号无法办理此事项，请登录法人账号!");
            location.href = "<%=path%>/userInfoController/mztLogin.do?returnUrl=projectWebsiteController.do?myProjectView";
        }
    });

	function  reload() {
		currentPage1(1);
		resetTotal('pagerMyexecution');
	}
</script>

<body>
<input name="totalData"  hidden="hidden" />
<input name="methodNum"  hidden="hidden"  value="0"/>
	<div class="eui-main">
		<jsp:include page="head.jsp"  flush="true"></jsp:include>
		<!-- 主体 -->
		<div class="eui-con">
			<div class="eui-mine">
				<div class="eui-my-pjt eui-card eui-flex vc eui-mb">
					<div class="logo eui-flex tc vc">
						<img src="<%=basePath%>/webpage/website/project/images/mine-logo.png" >
					</div>
					<div class="flex1">
						<div class="tit">${sessionScope.curLoginMember.YHMC}</div>
						<table>
							<tr>
								<td>实名认证：${sessionScope.curLoginMember.REAL_NAME_TYPE}认证</td>
								<td>法人代表：*${fn:substring(sessionScope.curLoginMember.FRDB,1,10)}</td>
								<td>手机号：${fn:substring(sessionScope.curLoginMember.SJHM,0,3)}****${fn:substring(sessionScope.curLoginMember.SJHM,7,12)}</td>
								<td></td>
							</tr>
							<tr>
								<td>企业信用状态：良好</td>
								<td>企业信用分：650</td>
								<td>社会统一信用代码：${sessionScope.curLoginMember.ZZJGDM}</td>
								<td>单位通信地址：暂无信息</td>
							</tr>
						</table>
					</div>
					<a class="eui-btn light" href="https://mztapp.fujian.gov.cn:8304/dataset/UnifiedController/goUserCenter.do?callerCode=b1d9cd6351bc31214554b415ff50dc64ede0112a">更多信息</a>
				</div>

				<div class="eui-card eui-my-pjt-con">
					<div class="slideTxtBox eui-tab4">
						<div class="hd">
							<ul class="eui-flex">
								<li  onclick="currentPage0(1);resetTotal('pager')" <c:if test="${myNav==''}">class="on" </c:if>>我的项目</li>
								<li  onclick="currentPage1(1);resetTotal('pagerMyexecution')" <c:if test="${myNav=='1'}">class="on" </c:if> >我的办件</li>
								<li>我的材料</li>
								<li>账号管理</li>
							</ul>
						</div>
						<div class="bd">
							<!-- 1我的项目 -->
							<div class="item">
								<div class="eui-card eui-table-info bjgs">
									<form id="myProjectInfoForm" >
									<table>
										<tr>
											<th>项目编码</th>
											<td><input class="ipt" type="text" name="PROJECT_CODE" placeholder="请输入项目编码"></td>
											<th>项目名称</th>
											<td><input class="ipt" type="text" name="PROJECT_NAME" placeholder="请输入项目名称"></td>
											<th>项目类型</th>
											<td>
												<eve:eveselect dataParams="PROJECTTYPE"
															   dataInterface="dictionaryService.findDatasForSelect"
															   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
												</eve:eveselect>
											</td>
											<th>
												<a class="eui-btn round" href="javascript:currentPage0(1);resetTotal('pager')">查询</a>
												<a class="eui-btn round lightblue"   onclick="restForm('myProjectInfoForm')">重置</a>
											</th>
										</tr>
									</table>
									</form>
								</div>
								<div class="eui-index-bjgs in">
									<table id="wraper">

									</table>
								</div>
								<div class="eui-page round pager clearfix" id="pager" style="margin: 30px 100px 0;">

								</div>
							</div>
							<!-- 2我的办件 -->

							<div class="item">
								<div class="eui-card eui-table-info bjgs">
									<form id="myExecutionInfoForm" >
										<table>
											<tr>
												<th>申报号</th>
												<td><input class="ipt" type="text" name="EXE_ID" placeholder="请输入申报号"></td>
												<th>申报名称</th>
												<td><input class="ipt" type="text" name="SUBJECT" placeholder="请输入申报名称"></td>
												<th>项目名称</th>
												<td>
													<input class="ipt" type="text" name="PROJECT_NAME" placeholder="请输入项目名称">
												</td>
												<th>
													<a class="eui-btn round" href="javascript:currentPage1(1);resetTotal('pagerMyexecution')">查询</a>
													<a class="eui-btn round lightblue"   onclick="restForm('myExecutionInfoForm')">重置</a>
												</th>
											</tr>
										</table>
									</form>
								</div>
								<div class="eui-index-bjgs in">
									<table id="wraperMyexecution">

									</table>
								</div>
								<div class="eui-page round pager clearfix" id="pagerMyexecution" style="margin: 30px 200px 0;">

								</div>

							</div>

							<!-- 3我的材料 -->
							<div class="item">

							</div>
							<!-- 4账号管理 -->
							<div class="item">

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 主体 end -->
		
		<!-- 底部 -->
		<div class="eui-footer">
			<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
		</div>
		<!-- 底部 end -->
	</div>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>

	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/page.css">
	<script src="<%=basePath%>/webpage/website/project/js/jquery.z-pager1.js" type="text/javascript" charset="utf-8"></script>
<eve:resources
		loadres="artdialog"></eve:resources>
	<script src="<%=basePath%>/webpage/website/project/js/userCenterOfMyProject.js" type="text/javascript" charset="utf-8"></script>
	<!--分页开始-->
	<script type="text/javascript">

		$(function() {
			//下拉框
			//$('select').selectMatch();
			// 选项卡切换
			jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
		});
	</script>
	<!--分页结束-->

</body>
</html>