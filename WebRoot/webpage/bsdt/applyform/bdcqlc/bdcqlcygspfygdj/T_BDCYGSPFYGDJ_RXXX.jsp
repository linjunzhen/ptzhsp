<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="tab_height"></div>
<input type="hidden" name="YGYG_FILE_URL" >
<input type="hidden" name="YGYG_FILE_ID">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">

			<tr>
				<th colspan="4">人像信息</th>
			</tr>
<tr>
	<td  class="tab_width">人像信息：
	</td>
	<td colspan="3">
		<div style="width:100%;display: none;" id="YGYG_FILE_DIV"></div>
		<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
			<a href="javascript:YGYGchooseCtrl()"><img id="${applyMater.MATER_CODE}"
			src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
		</div>
		<a href="javascript:void(0);" onclick="openSlxxFileUploadDialog()">
			<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
		</a>
	</td>
</tr>
<tr>
	<td></td>
	<td colspan="3">
		<div style="width:100%;" id=YGYG_fileListDiv></div>
	</td>
</tr>

</table>