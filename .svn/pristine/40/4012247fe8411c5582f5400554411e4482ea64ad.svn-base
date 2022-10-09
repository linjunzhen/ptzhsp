<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="renderer" content="webkit">
	<title>审批事项信息界面</title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/project/approveItem/css/eui.css">
	<script src="<%=basePath%>/webpage/project/approveItem/plugins/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>/webpage/project/approveItem/plugins/superslide/jquery.SuperSlide.2.1.3.js" type="text/javascript" charset="utf-8"></script>
	
	<eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
	<script type="text/javascript">
		var __ctxPath="<%=request.getContextPath() %>";
		$(function() {
			var returnInfo = document.getElementById("returnInfo").value;
			if(returnInfo.length>0){
				alert(returnInfo);
			}
		});
		function lookUserInfo(){
			var userInfo = document.getElementById("userInfo").value;
			console.log(userInfo);
		}
	</script>
</head>

<body>
	<div class="eui-title">${projectMap.PROJECT_NAME }</div>
	<div class="eui-content">
		<!-- 选项卡 -->
		<div class="slideTxtBox eui-slideTab">
			<div class="hd">
				<ul>
					<li><a href="javascript:void(0)">基本信息</a></li>
					<li><a href="javascript:void(0)">材料列表</a></li>
					<li><a href="javascript:void(0)">核查意见</a></li>
					<li><a href="javascript:void(0)">办理过程</a></li>
					<li><a href="javascript:void(0)">并联审批办理情况汇总</a></li>
				</ul>					
			</div>
			<div class="bd">
				
				<!--基本信息-->
				<div class="tm30">
					<table class="eui-table">
						<thead>
							<tr>
								<input type="hidden" id="userInfo" value="${sessionScope.curLoginMember}"/>
								<input type="hidden" id="returnInfo" value="${returnInfo}"/>
								<th colspan="4" style="text-align: left;">申报事项信息</th>
							</tr>
						</thead>
						<tbody>
							<tr class="trBg">
								<th width="10%">项目名称</th>
								<td width="40%">
									${projectMap.PROJECT_NAME }
									<!-- <a href="#" onclick="lookUserInfo()" class="look">查看项目单</a> -->
								</td>
								<th width="10%">项目代码</th>
								<td width="40%">${projectMap.PROJECT_CODE }</td>
							</tr>
							<tr>
								<th>项目（法人）单位</th>
								<td>${lerepMap.enterprise_name }</td>
								<th>单位证件号码</th>
								<td>${lerepMap.lerep_certno }</td>
							</tr>
							<tr class="trBg">
								<th>办件类型</th>
								<td>${serviceItem.SXLX}</td>
								<th>受理单位<font>*</font></th>
								<td>${serviceItem.SSBMMC }</td>
							</tr>
							<tr>
								<th>事项名称<font>*</font></th>
								<td>${serviceItem.ITEM_NAME }</td>
								<th>事项编号</th>
								<td>${serviceItem.ITEM_CODE }</td>
							</tr>
						</tbody>
					</table>
					<table class="eui-table tm10">
						<thead>
							<tr>
								<th colspan="4" style="text-align: left;">办理信息</th>
							</tr>
						</thead>
						<tbody>
							<tr class="trBg">
								<th width="10%">办理单号</th>
								<td width="40%">${exeInfo.EXE_ID }</td>
								<th width="10%">办理状态<font>*</font></th>
								<td width="40%">${exeInfo.runStatus }</td>
							</tr>
							<tr>
								<th>是否计时</th>
								<td></td>
								<th>承诺时限(工作日)<font>*</font></th>
								<td>${serviceItem.CNQXGZR }</td>
							</tr>
							<tr class="trBg">
								<th>承诺办结时间</th>
								<td colspan="3">${exeInfo.HANDLE_OVERTIME }</td>
							</tr>
							<tr>
								<th>最新业务操作</th>
								<td>${exeInfo.CUR_STEPNAMES }</td>
								<th>最后操作时间</th>
								<td>${exeInfo.END_TIME }</td>
							</tr>
							<tr class="trBg">
								<th>申请时间</th>
								<td colspan="3">${exeInfo.REV_STARTTIME }</td>
							</tr>
							<tr>
								<th>登记时间</th>
								<td colspan="3">${exeInfo.CREATE_TIME }</td>
							</tr>
							<tr class="trBg">
								<th>受理时间</th>
								<td>${exeInfo.REV_ENDTIME }</td>
								<th>受理核准备注</th>
								<td></td>
							</tr>
							<tr>
								<th>办结时间</th>
								<td>${exeInfo.END_TIME }</td>
								<th>办结结果</th>
								<td>${exeInfo.FINAL_HANDLEOPINION }</td>
							</tr>
							<!-- <tr class="trBg">
								<th>出文文件<font>*</font></th>
								<td colspan="3">
									<a href="#" class="download">建筑工程施工许可证核发.ssp</a>
									共1个附件<a href="#" class="downloadAll">全部下载</a>
								</td>
							</tr> -->
						</tbody>
					</table>
					
					<table class="eui-table">
						<thead>
							<tr>
								<th colspan="4" style="text-align: left;">申请信息</th>
							</tr>
						</thead>
						<tbody>
							<tr class="trBg">
								<th width="10%">申报来源</th>
								<td colspan="3">${exeInfo.applyType }</td>
							</tr>
							<tr>
								<th>申请备注</th>
								<td colspan="3"></td>
							</tr>
							<tr class="trBg">
								<th>申请附件文件</th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th>登记备注</th>
								<td colspan="3"></td>
							</tr>
							<tr class="trBg">
								<th>登记附件文件</th>
								<td colspan="3"></td>
							</tr>
						</tbody>
					</table>
					
					<table class="eui-table">
						<thead>
							<tr>
								<th colspan="4" style="text-align: left;">联系人信息</th>
							</tr>
						</thead>
						<tbody>
							<tr class="trBg">
								<th width="10%">申请人名称<font>*</font></th>
								<td width="40%">${exeInfo.SQRMC}</td>
								<th width="10%">联系人证件号<font>*</font></th>
								<td width="40%">${exeInfo.SQRSFZ}</td>
							</tr>
							<tr>
								<th>联系人手机<font>*</font></th>
								<td>${exeInfo.SQRSJH}</td>
								<th>联系人电话</th>
								<td>${exeInfo.SQRDHH}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--材料列表-->
				<div class="tm30">
					<!-- <div class="eui-sTt">行政审批_办件申请单从表_应提交材料<a href="#">一键下载</a></div> -->
					<div class="eui-sCon">
						<table class="eui-table eui-tabBlue">
							<thead>
								<tr>
									<th width="5%">序号</th>
									<th width="*">材料名称</th>
									<th width="8%">应交纸质数量</th>
									<th width="8%">应交电子数量</th>
									<th width="8%">实交纸质数量</th>
									<th width="8%">实交电子数量</th>
									<th width="10%">递交附件</th>
								</tr>
							</thead>
							<tbody>
								<!-- <tr>
									<td colspan="7">施工许可阶段（小型社会投资项目）共性材料</td>
								</tr> -->
								<c:if test="${applyMatersValue == true }">
									<c:forEach var="materInfo" items="${materList}" varStatus="am">
										<tr>
											<td style="text-align: center;">${am.index+1 }</td>
											<td>${materInfo.MATER_NAME}</td>
											<td style="text-align: center;">${materInfo.YJZZCL }</td>
											<td style="text-align: center;">${materInfo.YJDZCL }</td>
											<td style="text-align: center;">${materInfo.SJZZCL }</td>
											<td style="text-align: center;">${materInfo.SJDZCL }</td>
											<td style="text-align: center;">
												<c:forEach var="uploadFile" items="${materInfo.uploadFiles}">
													<c:if test="${materInfo.UPLOADED_SIZE!=0}">	
														<p id="${uploadFile.FILE_ID}" >
												             <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');"
							                                 	style="cursor: pointer;">
												             	<font color="blue">${uploadFile.FILE_NAME}</font>
												             </a>
												        </p><br/>
													</c:if>
												</c:forEach>
												<%-- 共${materInfo.UPLOADED_SIZE}个附件
												<a href="#" class="downloadAll">全部下载</a> --%>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<div class="eui-sTt tm20">补件材料</div>
					<div class="eui-sCon"></div>
				</div>
				<!--核查意见-->
				<div class="tm30">
					<table class="eui-table">
						<thead>
							<tr>
								<th colspan="1" style="text-align: left;">核查意见</th>
							</tr>
						</thead>
					</table>
				</div>
				<!--办理过程-->
				<div class="tm30">
					<table class="eui-table">
						<thead>
							<tr>
								<th>序号</th>
								<th>环节名称</th>
								<th>办理人</th>
								<th>提交时间</th>
								<th>操作情况</th>
								<th>备注/意见/原由</th>
								<th>相关文件</th>
								<th>回执单</th>
								<th>短信发送情况</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${handleListValue == true }">
								<c:forEach var="handle" items="${handleList}" varStatus="st">
									<tr>
										<td style="text-align: center;">${st.index+1 }</td>
										<td>${handle.TASK_NODENAME }</td>
										<td>${handle.TEAM_NAMES }</td>
										<td>${handle.END_TIME }</td>
										<td>${handle.EXE_HANDLEDESC }</td>
										<td>${handle.HANDLE_OPINION }</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
				<!--并联审批办理情况汇总-->
				<div class="tm30">
					<table class="eui-table">
						<thead>
							<tr>
								<th colspan="5" style="text-align: left;">并联审批办理情况汇总</th>
							</tr>
						</thead>
						<thead>
							<tr>
								<th>事项名称</th>
								<th>审批部门</th>
								<th>当前状态</th>
								<th>相关文件</th>
								<th>查阅办理过程</th>
							</tr>
						</thead>
						<!-- <tbody>
							<tr>
								<td>征求意见</td>
								<td>XXX市建设局</td>
								<td>提交核查意见</td>
								<td></td>
								<td>查阅办理过程</td>
							</tr>
						</tbody> -->
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//选项卡切换
		jQuery(".slideTxtBox").slide({effect:"fade",trigger: 'click'});
	</script>
</body>
</html>