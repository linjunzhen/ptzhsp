<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<!-- 注销信息 -->
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">注销信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注销原因：</td>
		<td colspan="3"><input type="text" class="tab_text  validate[required]"
			name="ZXYY" value="${serviceItem.ZXYY }" style="width: 72%;"/>
			<input type="button" class="eflowbutton" value="原因模板" onclick="AppUtil.cyyjmbSelector('ygspfygzxdj_zxyy','ZXYY');">
		</td>
	</tr>
	<tr>
		<td class="tab_width">注销时间：</td>
		<td><input type="text" class="tab_text "
				   name="BDC_ZXSJ" value="${busRecord.BDC_ZXSJ }"/></td>
		<td class="tab_width">注销人：</td>
		<td><input type="text" class="tab_text validate[]" maxlength="11"
			name="BDC_ZXR" value="${busRecord.BDC_ZXR}"  /></td>
	</tr>
	<tr>
		<td class="tab_width">附记：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
			name="ZXFJ" value="${busRecord.ZXFJ}" style="width: 72%;"  />
			<input type="button" class="eflowbutton" value="附记模板" onclick="AppUtil.cyyjmbSelector('ygspfygzxdj_fj','ZXFJ');">
		</td>
	</tr>
</table>