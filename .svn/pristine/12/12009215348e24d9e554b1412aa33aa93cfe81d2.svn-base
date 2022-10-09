<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="APPLYITEMMATER_FORM"  >
	
		<input type="hidden" name="onlineMaterIdJson" /> 
		<table cellpadding="0" cellspacing="0" class="syj-table2">
		
		<tr>
			<th> 关联事项：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="ITEMNAMES" readonly="readonly"
					class="eve-textarea w878"
					>${busRecord.ITEMNAMES}</textarea>				
				<input type="hidden" name="ITEMCODES" value="${busRecord.ITEMCODES}">
					<!-- <a href="javascript:showItemSelector();" id="itemMaterchoose" class="btn1">选 择</a>
					<a href="javascript:delItemMater();" id="itemMaterdel" class="btn1">删 除</a>
					<a href="javascript:printItem();" id="printItem" class="btn1">打 印</a> -->
			</td>
		</tr>	
		</table>
		<span style="color: red;">注：只有您选择事项所需材料齐全有效后，你的申请才能被受理</span>
		<div id="mater_div">
		</div>
</form>
