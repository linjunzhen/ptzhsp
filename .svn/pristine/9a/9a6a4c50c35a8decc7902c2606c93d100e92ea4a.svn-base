<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="syj-title1 tmargin20">
	<span>一般注销信息</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>公告平台名称：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]" 
					name="GGPTMC" id="GGPTMC" maxlength="64" placeholder="请输入公告平台名称" value="${busRecord.GGPTMC}"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
		<tr>
			<th ><font class="syj-color2">*</font>清算完结情况：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="qswjqk"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择清算完结情况" name="QSWJQK"  value="${busRecord.QSWJQK}">
				</eve:eveselect>
			</td>			
			<th><font class="syj-color2">*</font>清算完结日期：</th>
			<td>
				<input type="text" class="syj-input1 Wdate validate[required]" id="QSWJRQ" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
					readonly="readonly" name="QSWJRQ"  placeholder="请输入清算完结日期" value="${busRecord.QSWJRQ}"  maxlength="16"/>		
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>债务承接人：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
				name="ZWCJR" maxlength="32" value="全体股东" placeholder="请输入债务承接人"/>
			</td>			
			<th><font class="syj-color2">*</font>债权承接人：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
				name="ZQCJR" maxlength="32" value="全体股东" placeholder="请输入债权承接人"/>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>债权债务清理情况完成标准：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ZQZWQLQKWCBZ"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择清理情况完成标准" name="ZQZWQLQKWCBZ"  value="${busRecord.ZQZWQLQKWCBZ}">
				</eve:eveselect>
			</td>			
			<th><font class="syj-color2">*</font>清税情况：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QSQK"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择清税情况" name="QSQK"  value="${busRecord.QSQK}">
				</eve:eveselect>	
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>清算组成员备案确认文书号：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]"
				name="QSZCYBAQRWSH" maxlength="128" value="${busRecord.QSZCYBAQRWSH}" placeholder="请输入清算组成员备案确认文书号"/>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>非法人分支机构注销完毕标准：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="FFRFZJGZXWBBZ"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择注销完毕标准" name="FFRFZJGZXWBBZ"  value="${busRecord.FFRFZJGZXWBBZ}">
				</eve:eveselect>
			</td>			
			<th><font class="syj-color2">*</font>对外投资清理完毕标准：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DWTZQLWBBZ"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择清理完毕标准" name="DWTZQLWBBZ"  value="${busRecord.DWTZQLWBBZ}">
				</eve:eveselect>	
			</td>
		</tr>
	</table>
</div>
