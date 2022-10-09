<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >

	<table cellpadding="0" cellspacing="0" class="syj-table2" style="table-layout: inherit;">
		<tr>
			<th><font class="syj-color2">*</font>企业名称：</th>
			<td colspan="3">
			    <input type="hidden" id= "INTERNAL_STOCK_ID" value="${busRecord.INTERNAL_STOCK_ID}"/>
				<input type="text" class="syj-input1 w878 validate[required],custom[verifyCompanyNameExist]"
					name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>
			</td>
		</tr>
		<tr>
			<th>集团名称：</th>
			<td>
				<input type="text" class="syj-input1 " name="GROUP_NAME"  maxlength="256" placeholder="请输入集团名称" value="${busRecord.GROUP_NAME}"/>
			</td>
			<th>集团简称：</th>
			<td>
				<input type="text" class="syj-input1 " name="GROUP_ABBRE"  maxlength="128" placeholder="请输入集团简称" value="${busRecord.GROUP_ABBRE}"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
<%--		<tr style="display: none;">--%>
<%--			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>--%>
<%--			<td>--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--			</td>--%>
<%--		--%>
<%--			<th><font class="syj-color2">*</font>名称预先核准文号：</th>--%>
<%--			<td><input type="text" class="syj-input1 validate[required]" maxlength="32"  disabled="disabled"--%>
<%--				name="PREAPPROVAL_NUM"  placeholder="请输入名称预先核准文号" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--		</tr>--%>
		<tr>
			<th><font class="syj-color2">*</font>企业类别：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="hhqylb"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择企业类别" name="COMPANY_CATEGORY"  value="${busRecord.COMPANY_CATEGORY}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>公司类型：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
					name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
				<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
				<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>	
				<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>				
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>注册地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" onchange="setSendLawAddr()"
				name="REGISTER_ADDR" maxlength="128"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/></td>
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
			<td><input type="text" class="syj-input1 validate[required]" 
				name="REGISTERADD_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.REGISTERADD_OWNER}"/></td>
			<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="REGISTERADD_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.REGISTERADD_TEL}"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否另设生产经营地址：</th>
			<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="1" 
					<c:if test="${busRecord.IS_OTHER_PLACE==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="0" 
					<c:if test="${busRecord.IS_OTHER_PLACE==0}"> checked="checked"</c:if>/>否
				</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3"><input disabled="false" type="text" class="syj-input1  w878 validate[required]"
				name="BUSINESS_PLACE" maxlength="64"  placeholder="请输入生产经营地址" value="${busRecord.BUSINESS_PLACE}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input disabled="false" type="text" class="syj-input1 validate[required]" 
				name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
			<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input disabled="false" type="text" class="syj-input1 validate[required]" 
				name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>住所产权：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="propertyRight"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择住所产权" name="RESIDENCE_PROPERTY_RIGHT"  value="${busRecord.RESIDENCE_PROPERTY_RIGHT}">
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>联系电话：</th>
			<td>
				<input type="text" class="syj-input1  validate[required]"
					 maxlength="16" placeholder="请输入联系电话" name="CONTACT_NUMBER"  value="${busRecord.CONTACT_NUMBER}"/>
			</td>
			<input type="hidden"  placeholder="请输入邮政编码"  name="POSTAL_CODE"  value="350400" />
		</tr>

		<tr>
			<th><font class="syj-color2">*</font>网址：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required],custom[url]"
				name="WEBSITE" maxlength="128"  placeholder="请输入网址" value="${busRecord.WEBSITE}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>指定报纸：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
				name="COMPANY_NEWSPAPER" maxlength="128"  placeholder="请输入指定报纸" value="${busRecord.COMPANY_NEWSPAPER}"/></td>
		</tr>
		<tr>
			<th>设立方式：</th>
			<td colspan="3">
			<input type="radio" name="ESTABLISH_MODE" value="1" <c:if test="${busRecord.ESTABLISH_MODE==1}"> checked="checked"</c:if>>发起设立
			<input type="radio" name="ESTABLISH_MODE" value="2" <c:if test="${busRecord.ESTABLISH_MODE==2}"> checked="checked"</c:if>>募集设立
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>注册资本(万元)：</th>
			<td colspan="3"><input type="text" class="syj-input1  w878 validate[required],custom[JustNumber]" 
				name="REGISTERED_NUM" placeholder="请输入注册资本(万元)" 
				value="${busRecord.REGISTERED_NUM}" maxlength="16"/>
			</td>
			<!-- 
			<th>实收资本(万元)：</th>
			<td><input type="text" class="syj-input1  validate[],custom[JustNumber]" 
				name="COLLECTION_NUM" placeholder="请输入实收资本(万元)" 
				value="${busRecord.COLLECTION_NUM}"/>
			</td> -->
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>从业人数：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required,custom[JustNumber]]"
				name="STAFF_NUM"  placeholder="请输入从业人数" maxlength="4"  value="${busRecord.STAFF_NUM}"/></td>
		</tr>
		<!-- 
		<tr>
			<th><font class="syj-color2">*</font>副本数：</th>
			<td><input type="text" class="syj-input1  validate[required],custom[JustNumber]" 
				name="REPLICAS_NUM" placeholder="请输入副本数" 
				value="${busRecord.REPLICAS_NUM}"/>
			</td>
			<th>电子营业执照数：</th>
			<td><input type="text" class="syj-input1  validate[required],custom[JustNumber]" 
				name="BUSINESS_LICENSES_NUM" placeholder="请输入电子营业执照数" 
				value="${busRecord.BUSINESS_LICENSES_NUM}"/>
			</td>
		</tr> -->

		<tr>
			<th><font class="syj-color2">*</font>经营期限：</th>
			<td>
				<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th> 经营期限年数：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea w878 validate[required],maxSize[2000]" readonly="readonly"
					style="height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwNew();">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
				<input type="hidden" name="FLOW_DEFID"  value="${EFLOW_FLOWDEF.DEF_KEY }">
				<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请</div>
			</td>
		</tr>
		<tr>
			<th> 投资行业：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
					class="eve-textarea w878 validate[ validate[],maxSize[2000]]"
					style="height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY}</textarea> 					
				<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
				<!--<a href="javascript:showSelectTzhy();" class="btn1">选 择</a>-->
			</td>
		</tr>
		<tr>
			<th>行业编码：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 inputBackgroundCcc"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
				name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE}"/></td>
		</tr>
		<!-- 
		<tr>
			<th> 经营范围备注描述：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr> -->
	</table>
</form>
