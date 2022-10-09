<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>工程名称:</td>
	<td><input type="text" maxlength="128" name="GCMC"
		class="tab_text validate[required]"></td>
	<td class="tab_width"><font class="tab_color">*</font>总建筑面积:</td>
	<td><input type="text" maxlength="128" name="ZJZMJ"
		class="tab_text validate[required]"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>施工单位:</td>
	<td><input type="text" maxlength="128" name="SGDW"
		class="tab_text validate[required]"></td>
	<td class="tab_width"><font class="tab_color">*</font>监理单位:</td>
	<td><input type="text" maxlength="128" name="JLDW"
		class="tab_text validate[required]"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>设计单位:</td>
	<td><input type="text" maxlength="128" name="SJDW"
		class="tab_text validate[required]"></td>
	<td class="tab_width"><font class="tab_color">*</font>勘察单位:</td>
	<td><input type="text" maxlength="256" name="KCDW"
		class="tab_text validate[required]"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>合同价格:</td>
	<td colspan="3"><input type="text" maxlength="128" name="HTJG"
		class="tab_text validate[required]"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>证照废止列表地址:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="512" name="AbolishedUrl"></td>
	<td class="tab_width"><font class="tab_color">*</font>证照受限使用列表地址:
	</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="512" name="LimitingUrl"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>软件环境:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="512" name="SoftwareEnvironment"></td>
	<td class="tab_width"><font class="tab_color">*</font>提取码:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="16" name="SelCode"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>在线验证地址:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="512" name="VerifyUrl"></td>
	<td class="tab_width"><font class="tab_color">*</font>在线查询地址:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="512" name="QueryUrl"></td>
</tr>
<tr
	<c:if test="${serviceItem.ITEM_CODE!='00007XK01301'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>来源:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="128" name="Source"></td>
	<td class="tab_width"><font class="tab_color">*</font>语言:</td>
	<td><input type="text" class="tab_text validate[required]"
		maxlength="64" name="Langaue"></td>
</tr>
