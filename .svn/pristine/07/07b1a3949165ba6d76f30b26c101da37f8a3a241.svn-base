<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);
%>
<style>
	.eflowbutton{
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height:26px;
		color: #fff;
		border-radius: 5px;

	}
</style>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">人员基本信息查询&nbsp;
		</th>
	</tr>
	<tr>
		<td class="tab_width">个人编号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GRBH" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="totalSearch()"></a>
		</td>
		<td class="tab_width">社会保障号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="SHBZHM"   />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="totalSearch()"></a>
		</td>
		<td class="tab_width">姓名：</td>
		<td>
			<input type="text" class="tab_text" name="XM"  />
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="3">
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon-print"    onclick="printPaiedDetail('payDetail.docx','基本养老个人历年缴费明细表')">打印基本养老个人历年缴费明细表&nbsp;</a>					
			<a href="javascript:void(0);" style="margin-left: 50px;" class="easyui-linkbutton" iconCls="icon-print"    onclick="printPaiedDetail('payDetail.docx','工伤保险个人历年缴费明细表')">打印工伤保险个人历年缴费明细表&nbsp;</a>
		</th>
		<th colspan="3">
		</th>
	</tr>
</table>

<div class="easyui-tabs" id="ryxx">
	<div title="基本信息">
		<jsp:include page="./baseInfo.jsp"/>
	</div>
	<div title="人员参保信息">
		<jsp:include page="./insuredDetail.jsp"/>
	</div>
<%--     <div title="信息变更记录">
        <jsp:include page="./changedInfo.jsp"/>
    </div>
	<div title="工资基数">
		<jsp:include page="./salaryBase.jsp"/>
	</div>
	<div title="银行信息">
		<jsp:include page="./bankinfo.jsp"/>
	</div>
	<div title="养老账号信息">
		<jsp:include page="./pensionAccountInfo.jsp"/>
	</div>  --%>
    <div title="缴费明细">
		<jsp:include page="./payDetail.jsp"/>
	</div> 
</div> 



