<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${gczjzxqyslList}" var="gczjzxqysl" varStatus="s">
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${s.index}GCZJZXQYSL_NAME"  placeholder="请输入姓名"  maxlength="8" value="${gczjzxqysl.GCZJZXQYSL_NAME}"/>
			</td>
			<th><font class="syj-color2">*</font>身份证号码：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${s.index}GCZJZXQYSL_IDCARD"  placeholder="请输入身份证号码"  maxlength="18" value="${gczjzxqysl.GCZJZXQYSL_IDCARD}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>注册证书编号：</th>
			<td>
				<input type="text" class="syj-input1" 
				name="${s.index}GCZJZXQYSL_CODE"  placeholder="请输入证书编号"  maxlength="32" value="${gczjzxqysl.GCZJZXQYSL_CODE}"/>
			</td>
			<th><font class="syj-color2">*</font>注册证书有效期：</th>
			<td>
				<input type="text"  placeholder="请选择有效期限" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
				readonly="readonly" name="${s.index}GCZJZXQYSL_TIME" style="width:100%;height:28px;"  value="${gczjzxqysl.GCZJZXQYSL_TIME}"
				 class="Wdate">
			</td>
		</tr>
	</table>
	<c:if test="${s.index>2}">
		<a  href="javascript:void(0);" onclick="javascript:delGczjzxqyslDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>