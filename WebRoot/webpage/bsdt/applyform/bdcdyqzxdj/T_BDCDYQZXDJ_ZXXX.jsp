<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%--抵押权注销登记  注销信息--%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="2">注销信息</th>
			</tr>
			<tr>
			   	<td class="tab_width"><font class="tab_color ">*</font>注销原因：</td>
		    	<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="ZXYY" value="${busRecord.ZXYY}" style="width: 72%;"  />
						<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('dyqzxyy','ZXYY');">
				</td>
			</tr>
			<tr>
			   	<td class="tab_width"><font class="tab_color ">*</font>注销附记：</td>
		    	<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="ZXFJ" value="${busRecord.ZXFJ}" style="width: 72%;"  />
						<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('dyqzxfj','ZXFJ');">
				</td>
			</tr>		
		</table>	