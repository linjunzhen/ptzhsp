<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >
<div class="syj-title1">
	<span>企业基本信息</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th ><font class="syj-color2">*</font>分支机构名称：</th>
			<td colspan="3">
			    <input type="hidden" id="BRANCH_ID" value="${busRecord.BRANCH_ID}"/>
				<input type="text" class="syj-input1 w878 validate[required]"
					name="BRANCH_NAME" id="BRANCH_NAME" maxlength="60" placeholder="请输入分支机构名称"  value="${busRecord.BRANCH_NAME}"/>
			</td>
		</tr>
		<%-- <tr>
			<th ><font class="syj-color2">*</font>营业场所：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 validate[required]" 
					name="BUSINESS_PLACE" maxlength="60"  placeholder="请输入营业场所（仅限平潭区域）"   value="${busRecord.BUSINESS_PLACE}"/>
			</td>
		</tr>
		<tr>
			<th > 生产经营地址：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 validate[]" 
					name="PREMISES" maxlength="60"  placeholder="请输入经营场所（仅限平潭区域）"   value="${busRecord.PREMISES}"/>
			</td>
		</tr> --%>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
		<tr>
			<th><font class="syj-color2">*</font>注册地址：</th>
			<td><input type="text" class="syj-input1 validate[required]" onchange="setSendLawAddr()"
				name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址，请按房产证上的地址申报" value="${busRecord.REGISTER_ADDR}"/></td>
			<th><font class="syj-color2">*</font>面积：</th>
			<td>
				<input type="text" class="syj-input1 validate[required]" maxlength="16"
					   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
				<font style="font-size: 16px;margin-left: 10px;">㎡</font>
			</td>
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
				<div class="eui-list">
					<div class="info eui-input w878">
						<p class="border">邮箱</p><input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
							  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
					</div>
				</div>
				<div class="eui-list">
					<div class="info eui-input w878">
						<p class="border">传真</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
							  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
					</div>
				</div>
				<div class="eui-list">
					<div class="info eui-input w878">
						<p class="border">即时通讯账号（如微信）</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
									   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
					</div>
				</div>
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
			<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input  type="text" class="syj-input1  validate[required]"
						name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
			<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input type="text" class="syj-input1 validate[required]"
					   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>取得方式：</th>
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="wayOfGet"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否属于军队房产：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="armyHourse"
							   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REGISTER_REMARKS','03')"
							   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
				</eve:eveselect>
			</td>
			<th>其他证明：</th>
			<td >
				<input type="text" class="syj-input1" disabled
					   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
			</td>
		</tr>
		
		<tr>
		<th ><font class="syj-color2">*</font>是否有生产经营地址：</th>
		<td>
		<input type="radio" name="IS_BUSSINESS_ADDR" value="1" <c:if test="${busRecord.IS_BUSSINESS_ADDR==1}"> checked="checked"</c:if>>是
		<input type="radio" name="IS_BUSSINESS_ADDR" value="0" <c:if test="${busRecord.IS_BUSSINESS_ADDR!=1}"> checked="checked"</c:if>>否
		</td>
		</tr>
	</table>
	<table cellpadding="0" id="bussinessAddrTable" cellspacing="0" class="syj-table2 " style="display: none;">
		<tr>
			<th>生产经营地址：</th>
			<td><input type="text" class="syj-input1 "
				name="BUSSINESS_ADDR" maxlength="128"  placeholder="请输入生产经营地址,请按房产证上的地址申报"  value="${busRecord.BUSSINESS_ADDR}"/></td>
			<th>面积：</th>
			<td >
				<input type="text"  maxlength="16" class="syj-input1"
					   name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
				<font style="font-size: 16px;margin-left: 10px;">㎡</font>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input  type="text" class="syj-input1  "
					   name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
			<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input type="text" class="syj-input1 "
					   name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>取得方式：</th>
			<td colspan="3" >
				<eve:eveselect clazz="input-dropdown w280" dataParams="wayOfGet"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="请选择取得方式" name="RESIDENCE_WAYOFGET"  value="${busRecord.RESIDENCE_WAYOFGET}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否属于军队房产：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280" dataParams="armyHourse"
							   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REMARKS','03')"
							   defaultEmptyText="请选择军队房产情况" name="ARMY_HOURSE"  value="${busRecord.ARMY_HOURSE}">
				</eve:eveselect>
			</td>
			<th>其他证明：</th>
			<td >
				<input type="text" class="syj-input1" disabled
					   name="ARMYHOURSE_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REMARKS}"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
		<%-- <tr>
			<th ><font class="syj-color2">*</font>住所产权：</th>
			<td>				
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="propertyRight"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择住所产权" name="PLACE_PROPERTY_RIGHT" value="${busRecord.PLACE_PROPERTY_RIGHT}">
				</eve:eveselect>
			</td>
			
			<th > 电子邮箱：</th>
			<td><input type="text" class="syj-input1 validate[[],custom[email]]"
				name="BRANCH_EMAIL" maxlength="32"  placeholder="请输入电子邮箱" value="${busRecord.BRANCH_EMAIL}"/></td>
		</tr> --%>		
<%--		<tr style="display: none;">--%>
<%--			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>--%>
<%--			<td>--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--			</td>--%>
<%--			<th> 名称自主选用文号：</th>--%>
<%--			<td><input type="text" class="syj-input1" maxlength="32"--%>
<%--				name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--		</tr>--%>
		<tr>
			<th ><font class="syj-color2">*</font>联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="PHONE" maxlength="16" placeholder="请输入联系电话"  value="${busRecord.PHONE}"/></td>
			
			<th ><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required,custom[chinaZip]]" readonly="readonly"
				name="POSTCODE" maxlength="6"  placeholder="请输入邮政编码" value="350400"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>从业人数：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="EMPLOYED_NUM" maxlength="4"  placeholder="请输入从业人数" value="${busRecord.EMPLOYED_NUM}"/>
			</td>
		</tr>
		<%-- <tr>
			<th><font class="syj-color2">*</font>经营期限：</th>
			<td>
				<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th> 经营期限年数：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr> --%>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea w878 validate[required],maxSize[2000]" disabled="disabled"
					style="height:100px;"  placeholder="请选择经营范围">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID}">
				<!-- <a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a> -->
				<div style="color:red;width:90%;">友情提示：<br/>1、分公司的经营范围不得超出总公司的经营范围。
				<br/>2、经营范围请到窗口现场填写。</div>
				<input type="radio" name="IS_SCOPEINREGULATION" value="1" <c:if test="${busRecord.IS_SCOPEINREGULATION==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
			</td>
		</tr>
		<tr style="display: none;">
			<th> 投资行业：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
					class="eve-textarea w878 validate[ validate[],maxSize[2000]]"
					style="height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY}</textarea> 					
				<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
			</td>
		</tr>
		<tr style="display: none;">
			<th>行业编码：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 inputBackgroundCcc"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
				name="INDUSTRY_CODE"   value="${busRecord.INDUSTRY_CODE}"/></td>
		</tr>
		<%-- <tr>
			<th> 备注：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入备注" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr> --%>
	</table>
</div>
</form>
