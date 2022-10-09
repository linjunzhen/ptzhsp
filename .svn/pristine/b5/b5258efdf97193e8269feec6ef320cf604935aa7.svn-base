<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

	/**
	 * 根据申请人的身份证号码查询在信用平台的信息
	 **/
	function getCreditCompanyInfo(){
		var sqrsfz = $("input[name='SQRSFZ']").val();
		var entName=$("input[name='COMPANY_NAME']").val();
		var url=encodeURI("exeDataController.do?companyInfoByCreditView&SQRSFZ="
				+ sqrsfz+"&entName="+entName);
		parent.$.dialog.open(url, {
			title : "获取申请人公司信息",
			width : "850px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectBusScopeInfo = art.dialog.data("CREDITCOMPANY");
				if (selectBusScopeInfo) {
					var changeRegs=$("input[name='CHANGEREGS']").val();
					if(changeRegs.indexOf("0")>-1){
						$("[name='OLD_REG_ADDR']").val(selectBusScopeInfo.OLD_REG_ADDR); //住所
					}
					if(changeRegs.indexOf("1")>-1){
						$("[name='OLD_BUS_YEARS']").val(selectBusScopeInfo.OLD_BUS_YEARS);//年限
					}
					if(changeRegs.indexOf("2")>-1){
						$("[name='OLD_BUS_SCOPE']").val(selectBusScopeInfo.OLD_BUS_SCOPE);//经营范围
					}
					$("input[name='COMPANY_NAME']").val(selectBusScopeInfo.COMPANY_NAME);
					$("[name='LEGAL_NAME']").val(selectBusScopeInfo.LEGAL_NAME);
					$("[name='BUSSINESS_ADDR']").val(selectBusScopeInfo.BUSSINESS_ADDR);
					$("[name='CONTACT_PHONE']").val(selectBusScopeInfo.CONTACT_PHONE);
					$("[name='LIAISON_NAME']").val(selectBusScopeInfo.LIAISON_NAME);
					$("[name='LIAISON_MOBILE']").val(selectBusScopeInfo.LIAISON_MOBILE);
					$("[name='LIAISON_IDNO']").val(selectBusScopeInfo.LIAISON_IDNO);
					art.dialog.removeData("CREDITCOMPANY");
				}
			}
		}, false);
	};

</script>

<form action="" id="COMPANY_FORM"  >
	<input type="hidden"   name="REGISTER_AUTHORITY" value="平潭综合实验区行政审批局" />
	<input type="hidden"   name="FILL_DATE" value="" />
	<input type="hidden" name="CHANGEREGS" value="${busRecord.CHANGEREGS}" />
	<input type="hidden" name="RECORDS"  value="${busRecord.RECORDS}"/>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>企业名称：</th>
			<td colspan="3">
				<input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
				<input type="text" class="syj-input1 validate[required]" style="width:540px;"
					   name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>
				<span  class="btnOneP " onclick="getCreditCompanyInfo()" style="width: 60px; margin-left: 20px;height: 28px;line-height:28px;">公司查询</span>
			</td>
		</tr>
		<tr>
		<th> <font class="syj-color2">*</font>名称自主选用文号：</th>
		<td colspan="3"><input type="text" class="syj-input1 validate[required]" maxlength="32"
				   name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  value="${busRecord.PREAPPROVAL_NUM}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>股东姓名/名称：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="256"
					   name="SHAREHOLDER_NAME"  value="${busRecord.SHAREHOLDER_NAME}"/></td>
			<th><font class="syj-color2">*</font>法定代表人姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="256"
					   name="LEGAL_NAME"  value="${busRecord.LEGAL_NAME}"/></td>

		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">

		<tr>
			<th><font class="syj-color2">*</font>注册地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]" style="width:740px;"  onchange="setSendLawAddr()"
								   name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/></td>
		</tr>


		<tr>
			<th> <font class="syj-color2">*</font>法律文书送达地址(同注册地址)：</td>
			<td >
				<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
						<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
				<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
						<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
			</td>
			<th><font class="syj-color2">*</font>法律文书送达地址：</th>
			<td><input  type="text" class="syj-input1 validate[required]"
						name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/></td>
			</td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>法律文书电子送达地址：</td>
			<td colspan="3">
				邮箱：<input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
						  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
				传真：<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
						  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
				即时通讯账号（如微信）：<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
								   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
			</td>
		</tr>

		<script type="text/javascript">
			function setSendLawAddr() {
				var val = $("[name='IS_REGISTER_ADDR']:checked").val();

				if(val=='1'){
					$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR']").val());
					$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
				}else{
					$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
				}
			}
			$(function () {
				setSendLawAddr();
			})
		</script>




		<tr>
			<th><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]" style="width:740px;"
								   name="BUSSINESS_ADDR" maxlength="128"  placeholder="请输入生产经营地址"  value="${busRecord.BUSSINESS_ADDR}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
					   name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					   name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

		</tr>

		<tr>
			<th><font class="syj-color2">*</font>从业人数：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[numberplus]"
					   name="STAFF_NUM"  placeholder="请输入职工人数/从业人数"  maxlength="8" value="${busRecord.STAFF_NUM}"/></td>
		</tr>

<tr >
	<th style="display: none"><font class="syj-color2">*</font>公司类型：</th>
	<td  style="display: none">
		<input type="text" class="syj-input1 inputBackgroundCcc " readonly="readonly"
			   name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
		<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
		<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>
		<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>
	</td>
</tr>
	</table>

</form>
