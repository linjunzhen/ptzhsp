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
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
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

.titleButton {
	float: right;
	margin: 2px 0;
	margin-right: 10px;
	padding: 0 20px;
}

.haveButtonTitle {
	position: absolute;
	left: 47%;
}
</style>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcJzwqf.js"></script>
<script type="text/javascript">
	$(function() {});
</script>
<!-- 建筑物区分所有权业主共有部分登记开始 -->
<div id="jzwqf" name="bdc_jzwqf">
<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span class="haveButtonTitle">建筑物区分所有权业主共有部分登记 </span>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addJzwqfTable('JzwqfInfo');">新增</a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="updateJzwqfTable('JzwqfInfo');">保存</a>
			</th>
		</tr>
	</table>

	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="JzwqfInfo">
		<input type="hidden" name="JzwqfInfoTrid" id="JzwqfInfoTrid" />
		<tr id="JzwqfInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">建（构）筑物名称：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" name="JZWQF_JZWMC"
								value="${busRecord.JZWQF_JZWMC}" id="DJSH_GGBH" />
						</td>
						<td class="tab_width">不动产单元号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" name="JZWQF_BDCDYH"
								value="${busRecord.JZWQF_BDCDYH}" id="JZWQF_BDCDYH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">规划用途：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_GHYT"
								value="${busRecord.JZWQF_GHYT}" id="JZWQF_GHYT" />
						</td>
						<td class="tab_width">分摊土地面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_FTTDMJ"
								value="${busRecord.JZWQF_FTTDMJ}" id="JZWQF_FTTDMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">幢号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_ZH"
								value="${busRecord.JZWQF_ZH}" id="JZWQF_ZH" />
						</td>
						<td class="tab_width">户号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_HH"
								value="${busRecord.JZWQF_HH}" id="JZWQF_HH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">所在层：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_SZC"
								value="${busRecord.JZWQF_SZC}" id="JZWQF_SZC" />
						</td>
						<td class="tab_width">建（构）筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_JZMJ"
								value="${busRecord.JZWQF_JZMJ}" id="JZWQF_JZMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">专用建筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_ZYJZMJ"
								value="${busRecord.JZWQF_ZYJZMJ}" id="JZWQF_ZYJZMJ" />
						</td>
						<td class="tab_width">分摊建筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_FTJZMJ"
								value="${busRecord.JZWQF_FTJZMJ}" id="JZWQF_FTJZMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">附记：</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="2000" name="JZWQF_FJ"
								value="${busRecord.JZWQF_FJ}" id="JZWQF_FJ" style="width: 72%;" />
						</td>
					</tr>
				</table>

				<div class="tab_height2"></div>
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="JzwqfListTable">
					<tr>
						<!-- <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>  -->
						<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">幢号</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">户号</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">建筑面积</td>
						<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">专有建筑面积</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- 建筑物区分所有权业主共有部分登记结束 -->
