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
<!-- 收费环节-收费信息开始 -->
<div class="tab_height"></div>
<div id="sssb_sfxx" name="sssb_sfxx" style="display:none;">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>收费信息</th>
		</tr>
		<tr>
		  <td style="padding: 10px;">
		      <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		        <tr>
		           <td class="tab_width"><font class="tab_color">*</font>收费方式：</td>
		           <td>
		           		<input type="radio" class="validate[required]" name="SSSB_SFFS"  value="0" onclick="showSfxx(this.value);" <c:if test="${busRecord.SSSB_SFFS=='0'}">checked="checked"</c:if> >在线收费
			       		<input type="radio" class="validate[required]" name="SSSB_SFFS"  value="1" onclick="showSfxx(this.value);" <c:if test="${busRecord.SSSB_SFFS=='1'}">checked="checked"</c:if> >窗口收费
		       	        <font class="tab_color" id="cksf" style="display:none">已收费</font>
		       	   </td>
		        </tr>
		    </table>
		  </td>
		</tr>
	</table>
</div>
<!-- 收费环节-收费信息结束 -->
