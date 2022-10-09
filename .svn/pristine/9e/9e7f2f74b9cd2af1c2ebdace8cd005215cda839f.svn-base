<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">


function selectPeruals(){
	var userIds = $("input[name='EFLOW_PERULA_IDS']").val();
	$.dialog.open("sysUserController.do?selector&allowCount=10&noAuth=true&userIds="
			+ userIds, {
		title : "人员选择器",
		width : "1000px",
		lock : true,
		resize : false,
		height : "500px",
		close: function () {
			var selectUserInfo = art.dialog.data("selectUserInfo");
			if(selectUserInfo&&selectUserInfo.userIds){
				$("input[name='EFLOW_PERULA_IDS']").val(selectUserInfo.userIds);
				$("input[name='EFLOW_PERULA_NAMES']").val(selectUserInfo.userNames);
				art.dialog.removeData("selectUserInfo");
			}
		}
	}, false);
	
}

</script>

<c:if test="${nodeConfig.IS_ADDPERUAL==1}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="2">传阅人信息</th>
	</tr>

	<tr>
		<td><span style="width: 100px;float:left;text-align:right;">选择传阅人：</span>
		<input type="hidden" name="EFLOW_PERULA_IDS" />
		<input type="text" style="width:400px;float:left;"
		readonly="readonly" class="eve-input"
		onclick="selectPeruals();"
		name="EFLOW_PERULA_NAMES" />
		</td>
	</tr>
</table>
</c:if>