<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="eve" uri="/evetag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-cn">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit">
<title>平潭综合实验区产业奖补审批表</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/bsdt/applyform/cyjb/css/eui.css">

<!-- 将不需要打印的部分，标记为class="noprint" -->
<style type="text/css" media=print>
.noprint {
    display: none
}
</style>

<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery3.min.js"></script>
<script type="text/javascript">
   function cyjb_print() {
       window.print();
   }
</script>

<body>
    <div id="print_toolbar" class="noprint" style="z-index:999;text-align:center;margin:30px;position:absolute;top:0;left:500px;right:0;bottom:0;">
		<a href="javascript:cyjb_print();" style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">打 印</a> 
		<a href="javascript:window.opener=null;window.open('','_self');window.close();" style="background:#3a81d0;border:none;padding:10px 20px;height:30px;cursor:pointer;margin:10px;color:#fff;border-radius:3px;">关 闭</a>
    </div>
    <div class="eui-print">
    	<div class="eui-pic"><img src="${busRecord.attach_url}"/></div>		
    </div> 
	
	<div class="eui-print">
		<table>
		    <tr>
					<th>区税务局意见</th>
					<td colspan="2">
						<label style="height:65px">${busRecord.QTEX_JB_CONTENT}</label>
						<div class="eui-display-table qm">
							<div class="th">经办：</div>
							<div class="td"><label>${busRecord.QTEX_JB_PERSON}</label></div>
							<div class="th">复核：</div>
							<div class="td"><label>${busRecord.QTEX_JB_AUDIT}</label></div>
						</div>
						<div class="eui-display-table date">
							<div class="td">
								<label>${fn:substring(busRecord.QTEX_JB_JBTIME,0,4)}</label>年
								<label>${fn:substring(busRecord.QTEX_JB_JBTIME,5,7)}</label>月
								<label>${fn:substring(busRecord.QTEX_JB_JBTIME,8,11)}</label>日
							</div>
							<div class="td">
								<label>${fn:substring(busRecord.QTEX_JB_AUDITTIME,0,4)}</label>年
								<label>${fn:substring(busRecord.QTEX_JB_AUDITTIME,5,7)}</label>月
								<label>${fn:substring(busRecord.QTEX_JB_AUDITTIME,8,11)}</label>日
							</div>
						</div>
					</td>
				</tr>
			<tr>
				<th colspan="3">审核单位填写</th>
			</tr>
			<tr>
				<th rowspan="3">奖补明细情况</th>
				<th>经营贡献奖励</th>
				<td>
					<label>${busRecord.JBMX_JYGXJL}</label>
				</td>
			</tr>
			<tr>
				<th>个人所得税补助</th>
				<td>
					<label style="height:65px">${busRecord.JBMX_GRSDSBZ}</label>
				</td>
			</tr>
			<tr>
				<th>其他</th>
				<td>
					<label style="height:65px">${busRecord.JBMX_OTHER}</label>
				</td>
			</tr>
			<tr height="200px">
				<th>行业主管部门审核意见</th>				
				<td colspan="2">
					<label>${busRecord.HYZG_CONTENT}</label>
					<br/>
					<div class="eui-display-table qm">
						<div class="th">经办：</div>
						<div class="td">
							<c:if test="${isShowJb == 'true'}">
								<label>${busRecord.HYZG_JB_PERSON}</label>
							</c:if>
							<c:if test="${isShowJb == 'false'}">
								<label></label>
							</c:if>
						</div>
						<div class="th">复核：</div>
						<div class="td"><label>${busRecord.HYZG_JB_AUDIT}</label></div>
						<div class="th">终审：</div>
						<div class="td"><label>${busRecord.HYZG_JB_LEADER}</label></div>
					</div>
					<div class="eui-display-table date">
						<div class="td">
							<label>${fn:substring(busRecord.HYZG_JB_TIME,0,4)}</label>年
							<label>${fn:substring(busRecord.HYZG_JB_TIME,5,7)}</label>月
							<label>${fn:substring(busRecord.HYZG_JB_TIME,8,11)}</label>日
						</div>
						<div class="td">
							<label>${fn:substring(busRecord.HYZG_AUDIT_TIME,0,4)}</label>年
							<label>${fn:substring(busRecord.HYZG_AUDIT_TIME,5,7)}</label>月
							<label>${fn:substring(busRecord.HYZG_AUDIT_TIME,8,11)}</label>日
						</div>
					</div>
				</td>
			</tr>
			<tr height="198px">
				<th>区行政审批局意见</th>
				<td colspan="2">
					<label>${busRecord.QXZSPJ_CONTENT}</label>
					<br/>
					<div class="eui-display-table qm">
						<div class="th">经办：　　　　处室领导：　　　　局领导：</div>
					</div>
					<div class="eui-display-table date">
						<div class="tr">
							<div class="td" style="text-align: right;">（单位公章）</div>
						</div>
						<div class="tr">
							<div class="td" style="text-align: right;">
								<label>${fn:substring(busRecord.QXZSPJ_AUDIT_TIME,0,4)}</label>年
								<label>${fn:substring(busRecord.QXZSPJ_AUDIT_TIME,5,7)}</label>月
								<label>${fn:substring(busRecord.QXZSPJ_AUDIT_TIME,8,11)}</label>日
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div class="eui-tips">注：本表双面打印，一式肆份，区行政审批局、主管部门、国库支付中心、申报单位各一份。</div>
    </div>
</body>
</html>
