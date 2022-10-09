<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${currentTime}GGFBDW_NAME"  placeholder="请输入姓名"  maxlength="8"/>
			</td>
			<th><font class="syj-color2">*</font>人员类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280" dataParams="GGFBDWTYPE"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择人员类型" name="${currentTime}GGFBDW_TYPE"  >
				</eve:eveselect>
			</td>
		</tr>	
	</table>
	<a  href="javascript:void(0);" onclick="javascript:delGgfbdwDiv(this);" class="syj-closebtn"></a>
</div>