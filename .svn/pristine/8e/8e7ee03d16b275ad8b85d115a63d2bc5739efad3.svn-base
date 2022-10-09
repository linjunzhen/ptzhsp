<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >一般注销信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>公告平台名称：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" 
				name="GGPTMC" id="GGPTMC" maxlength="64" placeholder="请输入公告平台名称" value="${busRecord.GGPTMC}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>清算完结情况：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="qswjqk"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择清算完结情况" name="QSWJQK"  value="${busRecord.QSWJQK}">
			</eve:eveselect>
		</td>			
		<td class="tab_width"><font class="tab_color">*</font>清算完结日期：</td>
		<td>
			<input type="text" class="Wdate validate[required]" id="QSWJRQ" style="height: 28px;  line-height: 28px;"
				readonly="readonly" name="QSWJRQ"  placeholder="请输入清算完结日期" value="${busRecord.QSWJRQ}"  maxlength="16"/>		
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>债务承接人：</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[required]" readonly="readonly"
			name="ZWCJR" maxlength="32" value="全体股东" placeholder="请输入债务承接人"/>
		</td>			
		<td class="tab_width"><font class="tab_color">*</font>债权承接人：</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[required]" readonly="readonly"
			name="ZQCJR" maxlength="32" value="全体股东" placeholder="请输入债权承接人"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>债权债务清理情况完成标准：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="ZQZWQLQKWCBZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择清理情况完成标准" name="ZQZWQLQKWCBZ"  value="${busRecord.ZQZWQLQKWCBZ}">
			</eve:eveselect>
		</td>			
		<td class="tab_width"><font class="tab_color">*</font>清税情况：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="QSQK"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择清税情况" name="QSQK"  value="${busRecord.QSQK}">
			</eve:eveselect>	
		</td>
	</tr>
	<tr>
		<td  class="tab_width"><font class="tab_color">*</font>清算组成员备案确认文书号：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]"
			name="QSZCYBAQRWSH" maxlength="128" value="${busRecord.QSZCYBAQRWSH}" placeholder="请输入清算组成员备案确认文书号"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>非法人分支机构注销完毕标准：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="FFRFZJGZXWBBZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择注销完毕标准" name="FFRFZJGZXWBBZ"  value="${busRecord.FFRFZJGZXWBBZ}">
			</eve:eveselect>
		</td>			
		<td class="tab_width"><font class="tab_color">*</font>对外投资清理完毕标准：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="DWTZQLWBBZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择清理完毕标准" name="DWTZQLWBBZ"  value="${busRecord.DWTZQLWBBZ}">
			</eve:eveselect>	
		</td>
	</tr>
</table>
