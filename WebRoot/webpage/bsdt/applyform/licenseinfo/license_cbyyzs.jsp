<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>船名:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="CM"></td>
	<td class="tab_width"><font class="tab_color">*</font>船籍港:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="32" name="CJG"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>船检登记号:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="32" name="CJDJH"></td>
	<td class="tab_width"><font class="tab_color">*</font>船舶所有人:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="128" name="CBSYR"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>经营人许可证号码:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="32" name="JYRXKZHM"></td>
	<td class="tab_width"><font class="tab_color">*</font>船舶类型:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="CBLX"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>发证日期:</td>
	<td>
	<input type="text" name="PublishDate"
		class="laydate-icon validate[required]"
		onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})"
		style="width:170px; height:26px;" />
	</td>
	<td class="tab_width"><font class="tab_color">*</font>本证使用期限至:</td>
	<td>
	<input type="text" name="ValidUntilDate"
		class="laydate-icon validate[required]"
		onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})"
		style="width:170px; height:26px;" />
	</td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>船舶材料:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="CBCL"></td>
	<td class="tab_width"><font class="tab_color">*</font>船舶总吨:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="CBZD"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>建成日期:</td>
	<td>
	<input type="text" name="JCRQ"
		class="laydate-icon validate[required]"
		onclick="laydate({istime: false, min:laydate.now(), format: 'YYYY-MM-DD'})"
		style="width:170px; height:26px;" />
	</td>
	<td class="tab_width"><font class="tab_color">*</font>主机数量:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="ZJSL"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>主机功率:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="64" name="ZJGL"></td>
	<td class="tab_width"><font class="tab_color">*</font>船舶经营人许可证核定的经营范围:</td>
	<td><input type="text" class="tab_text validate[required]" maxlength="2000" name="CBJYRXKZHDDJYFW"></td>
</tr>
<tr <c:if test="${serviceItem.ITEM_CODE!='00007GF05401'}">style="display: none;"</c:if>>
	<td class="tab_width"><font class="tab_color">*</font>本船核定的经营范围:</td>
	<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="2000" name="BCHDDJYFW"></td>
</tr>