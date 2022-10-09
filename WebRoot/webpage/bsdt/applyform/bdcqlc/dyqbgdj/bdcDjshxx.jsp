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
<div id="djshxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span style="left: 47%;">登记审核</span>
			</th>
		</tr>
	</table>

	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshInfo">
		<tr id="djshInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">（登簿）核准意见：</td>
						<td colspan="3">
							<input type="text" class="tab_text" maxlength="500" name="DJSHXX_HZYJ"
								value="${busRecord.DJSHXX_HZYJ}" style="width: 72%;" />
							<input type="button" class="eflowbutton" value="意见模板" id="DJSHXX_HZYJ_BUTTON"
								name="DJSHXX_HZYJ_BUTTON" onclick="AppUtil.cyyjmbSelector('bdcdyqscdj','DJSHXX_HZYJ');">
						</td>
					</tr>

					<tr>
						<td class="tab_width">审核人：</td>
						<td>
							<input type="text" class="tab_text" name="DJSHXX_SHR" maxlength="100"
								value="${busRecord.DJSHXX_SHR}" />
						</td>
						<td class="tab_width">审核结束日期：</td>
						<td colspan="2">
							<input type="text" class="tab_text" name="DJSHXX_SHJSRQ" maxlength="32"
								value="${busRecord.DJSHXX_SHJSRQ}" style="float: left;" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- 登记审核结束 -->
