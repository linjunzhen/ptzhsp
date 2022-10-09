<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<!-- 受理信息 -->

<%--抵押JSON信息--%>
<input type="hidden" name="YGXX_JSON">
<input type="hidden" name="FWXX_JSON">
<div class="bsbox clearfix" id="slxxInfo">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>受理信息</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th><span class="bscolor1">*</span>登记类型：</th>
				<td><input type="text" class="tab_text " disabled="disabled" 
					name="CATALOG_NAME" value="预告登记"/></td>
				<th><span class="bscolor1">*</span>权利类型：</th>
				<td><input type="text" class="tab_text " disabled="disabled" name="QLLX" value="预购商品房抵押预告登记"/></td>
			</tr>
			<tr>
				<th><span class="bscolor1 ">*</span>不动产单元号：</th>
				<td colspan='3'><input type="text" class="tab_text w280 validate[required,custom[estateNum]]" onblur="checkIsAuditPass();"
						   name="ESTATE_NUM" id="ESTATE_NUM" value="${busRecord.ESTATE_NUM }" maxlength="64"  style="background-color: #f2f2f2;" readonly="readonly"/>
					<input type="button" value="不动产预告登记查询" name="bdcygdjcxWw" class="eflowbutton" onclick="showSelectBdcYgdacx();" style="margin:0 5px;"/> 
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1 ">*</span>申请人(单位)：</th>
				<td><input type="text" class="tab_text validate[required]" maxlength="32"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
				<th><span class="bscolor1">*</span>持证类型：</th>
				<td style="width:430px">
					<eve:eveselect clazz="tab_text w280	 validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="1">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1 ">*</span>坐落：</th>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="LOCATED" value="${busRecord.LOCATED}" style="width:850px;"  />
				</td>
			</tr>
			<tr>
				<th>通知人姓名：</th>
				<td><input type="text" class="tab_text " maxlength="32"
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<th>通知人电话：</th>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" style="width: 850px;"  />
				</td>
			</tr>
		</table>
	</div>
</div>

<%-- <div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>领证人信息</span></li>
		</ul>
	</div>
	<div style="padding: 10px">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th>领证人姓名：</th>
				<td><input type="text" class="tab_text " name="QZR_NAME"  maxlength="8" value="${busRecord.QZR_NAME }"/></td>
				<th>领证人证件号：</th>
				<td><input type="text" class="tab_text validate[]" maxlength="18"
					name="QZR_ZJH" value="${busRecord.QZR_ZJH}"  /></td>
			</tr>
		</table>
	</div>
</div> --%>