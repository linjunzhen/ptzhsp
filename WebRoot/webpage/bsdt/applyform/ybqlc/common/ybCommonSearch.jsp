<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">通用查询</th>
	</tr>
	<tr style="height:38px;">
		<td class="tab_width" style="text-align:left">操作按钮：</td>
		<td colspan="5">
			<input type="button" class="eflowbutton" id="grxxzhcxBtn"
				onclick="grxxzhcx();" value="个人信息综合查询"/>
		    <input type="button" class="eflowbutton"  id="dwxxzhcxBtn"
				onclick="dwxxzhcx();" value="单位信息综合查询"/> 
		</td>
	</tr>
</table> 
