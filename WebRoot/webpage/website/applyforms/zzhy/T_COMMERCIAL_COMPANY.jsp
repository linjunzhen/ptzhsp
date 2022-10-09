<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form action="" id="COMPANY_FORM"  >
	<table cellpadding="0" cellspacing="0" class="syj-table2 " style="table-layout: auto;">
		<tr>
			<th><font class="syj-color2">*</font>企业名称：</th>
			<td colspan="3">
			    <input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
				<input type="text" class="syj-input1 validate[required],custom[verifyCompanyNameExist] w878"
					name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称，请按所登记的名称进行申报" value="${busRecord.COMPANY_NAME}"/>
			</td>
		</tr>
		<tr>
			<th>集团名称：</th>
			<td>
				<input type="text" class="syj-input1 "
					   name="GROUP_NAME"  maxlength="256" placeholder="请输入集团名称" value="${busRecord.GROUP_NAME}"/>
			</td>
			<th>集团简称：</th>
			<td>
				<input type="text" class="syj-input1 "
					   name="GROUP_ABBRE"  maxlength="128" placeholder="请输入集团简称" value="${busRecord.GROUP_ABBRE}"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " >
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
			<div class="eui-list" style="width: 830px;">
				<div class="info eui-input">
					<p class="border">邮箱</p><input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
						  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
				</div>
			</div>
			<div class="eui-list" style="width: 830px;">
				<div class="info eui-input">
					<p class="border">传真</p><input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
						  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
				</div>
			</div>
			<div class="eui-list" style="width: 830px;">
				<div class="info eui-input">
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
				<eve:eveselect clazz="input-dropdown w878 validate[required]" dataParams="wayOfGet"
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
		<%--<tr>--%>
			<%--<th rowspan="4"><font class="syj-color2">*</font>是否仅作为联络地址：</th>--%>
			<%--<td rowspan="4">--%>
				<%--<input type="radio" name="IS_ONLY_CONTACT" value="1" <c:if test="${busRecord.IS_ONLY_CONTACT!=0}"> checked="checked"</c:if>>是--%>
				<%--<input type="radio" name="IS_ONLY_CONTACT" value="0" <c:if test="${busRecord.IS_ONLY_CONTACT==0}"> checked="checked"</c:if>>否--%>
			<%--</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<th>出租（借）方：</th>--%>
			<%--<td><input type="text" class="syj-input1"--%>
				<%--name="LESSOR" maxlength="32"  placeholder="请输入出租(借)方" disabled="disabled" value="${busRecord.LESSOR}"/></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<th>出租（借）时间：</th>--%>
			<%--<td><input type="text" style="width:45%;height:20px;"--%>
<%--&lt;%&ndash;				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'LEASE_END_DATE\',{y:-1})}'})"&ndash;%&gt;--%>
				<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"--%>
				<%--readonly="true" id="LEASE_START_DATE"  --%>
				<%--name="LEASE_START_DATE" disabled="disabled"  value="${busRecord.LEASE_START_DATE}"/>至--%>
				<%--<input type="text" --%>
<%--&lt;%&ndash;				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'LEASE_START_DATE\',{y:1})}'})" &ndash;%&gt;--%>
				<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"--%>
				<%--style="width:45%;height:20px;" id="LEASE_END_DATE" --%>
				<%--name="LEASE_END_DATE" readonly="true"  disabled="disabled"   value="${busRecord.LEASE_END_DATE}"/></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<th>租赁合同签订时间：</th>--%>
			<%--<td><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" id="sign" readonly="readonly"--%>
				<%--name="SINGING_TIME"  style="width:45%;height:20px;" disabled="disabled" value="${busRecord.SINGING_TIME}"/></td>--%>
		<%--</tr>--%>
		<%----%>
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
			<td>
				<input type="text"  class="syj-input1 " maxlength="16" name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
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
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w878" dataParams="wayOfGet"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="请选择取得方式" name="RESIDENCE_WAYOFGET"  value="${busRecord.RESIDENCE_WAYOFGET}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否属于军队房产：</th>
			<td >
				<eve:eveselect clazz="input-dropdown  w280" dataParams="armyHourse"
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
	<table cellpadding="0" cellspacing="0" class="syj-table2 " >
		<tr>
			<th><font class="syj-color2">*</font>企业联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
				name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>公司类型：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
				<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
				<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>	
				<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>				
			</td>
			<th><font class="syj-color2">*</font>企业规模：</th>
			<td>
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjqyfl"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择企业规模" name="ENTERPRISE_STATEMENT"  value="${busRecord.ENTERPRISE_STATEMENT}">
				</eve:eveselect>
				&nbsp;<a target="_blank" class="answer" href="<%=path%>/contentController/view.do?contentId=996" title="企业规模帮助"></a>
			</td>
		</tr>
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
					class="eve-textarea validate[required],maxSize[1000] w878" readonly="readonly"
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
					class="eve-textarea validate[ validate[],maxSize[2000]] w878"
					style="height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY}</textarea> 					
				<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
				<!--<a href="javascript:showSelectTzhy();" class="btn1">选 择</a>-->
			</td>
		</tr>
		<tr>
			<th>行业编码：</th>
			<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc w878"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
				name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE}"/></td>
		</tr>
		<!--<tr>
			<th> 经营范围备注描述：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr>-->
		<tr>
			<th><font class="syj-color2">*</font>职工人数/从业人数：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]"
				name="STAFF_NUM"  placeholder="请输入职工人数/从业人数"  maxlength="8" value="${busRecord.STAFF_NUM}"/></td>
			<th> 所在地行政区：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" name="LOCAL_REGION" value="福州市平潭县"   placeholder="请输入所在地行政区"
				readonly="readonly"/></td>
		</tr>
	</table>
</form>
