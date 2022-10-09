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
</style>
<script type="text/javascript">
	$(function() {});
</script>
<!-- 登记审核开始 -->
<div id="djshxx" name="djxx_djshxx">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span style="position: absolute; left: 47%;">
					登记审核
				</span>
				<a href="javascript:void(0);" style="float:right; margin: 2px 0" class="eflowbutton"
					onclick="showSelectBdcdydacx();">审批表（双方）</a>
				<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton"
					onclick="showSelectBdcdydacx();">审批表（单方）</a>				
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshxxInfo">
		<tr>
			<td class="tab_width">是否公告：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					name="DJSH_SFGG" id="DJSH_SFGG" value="${busRecord.DJSH_SFGG}">
				</eve:eveselect>
			</td>
			<td class="tab_width">公告编号：</td>
			<td>
				<input type="text" class="tab_text " maxlength="500" name="DJSH_GGBH"
					value="${busRecord.DJSH_GGBH}" id="DJSH_GGBH" />
			</td>
			<td class="tab_width">公告时长（工作日）：</td>
			<td>
				<input type="text" class="tab_text validate[], custom[JustNumber]" maxlength="32"
					name="DJSH_GGSC" value="${busRecord.DJSH_GGSC}" id="DJSH_GGSC" onBlur="setDjshxx();" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">起始公告时间：</td>
			<td>
				<input type="text" class="tab_text " maxlength="32" name="DJSH_GGQSSJ"
					value="${busRecord.DJSH_GGQSSJ}" id="DJSH_GGQSSJ" />
			</td>
			<td class="tab_width">结束公告时间：</td>
			<td>
				<input type="text" class="tab_text " maxlength="32" name="DJSH_GGJSSJ"
					value="${busRecord.DJSH_GGJSSJ}" id="DJSH_GGJSSJ" />
			</td>
			<td colspan="2">
				<a href="javascript:void(0);" style="padding: 0 20px;" class="eflowbutton"
					onclick="showSelectBdcdydacx();">打印公告</a>
			</td>
		</tr>
	</table>
</div>
<!-- 登记审核结束 -->
