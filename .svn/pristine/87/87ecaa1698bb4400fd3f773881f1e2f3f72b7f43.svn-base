<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<style>
.eflowbutton {
	background: #3a81d0;
	border: none;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
	float: right;
	margin: 2px 0;
	margin-right: 10px;
}

.eflowbutton-disabled {
	background: #94C4FF;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #E9E9E9;
	border-radius: 5px;
}

.selectType {
	margin-left: -100px;
}

.bdcdydacxTrRed {
	color: red;
}

.haveButtonTitle {
	position: absolute;
	left: 47%;
}
</style>
<script type="text/javascript">
	$(function() {});
</script>
<!-- 套间明细表格开始 -->
<div id="bdc_tjmx" name="bdc_tjmx">
<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span class="haveButtonTitle">套间明细</span>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="addTable('PowerSourceInfo');" name="addQslyxx">批量删除幢明细 </a>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="updateTable('PowerSourceInfo');" name="updateQslyxx">查看户信息</a>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="updateTable('PowerSourceInfo');" name="updateQslyxx">查看幢信息</a>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="showZMX();" name="updateQslyxx">添加幢明细</a>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="showTJMX();" name="updateQslyxx">添加套间明细</a>
				<a href="javascript:void(0);" class="eflowbutton"
					onclick="updateTable('PowerSourceInfo');" name="updateQslyxx">打印套间明细</a>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="PowerSourceInfo">
		<input type="hidden" name="PowerSourceInfoTrid" id="PowerSourceInfoTrid" />
		<tr id="PowerSourceInfo_1">
			<td>
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="ZqqxxListTable">
					<tr>
						<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
						<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">幢号</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">总层数</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">所在层数</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">坐落</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">户号</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">建筑面积</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">专用建筑面积</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">分摊建筑面积</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">规划用途</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">房屋结构</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
						<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">楼栋IP</td>
						<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- 套间明细表格结束 -->