<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
	.textbox{
		width: 280px!important;
		height: 40px!important;
		border: none!important;
	}
	.textbox .textbox-text{
		width: 280px!important;
		height: 40px!important;
		line-height: 40px!important;
		font-size: 16px!important;
		color: #000000!important;
		padding: 0 10px!important;
		box-sizing: border-box!important;
		border: 1px solid #c9deef!important;
	}
</style>

<form action="" id="COMPANY_FORM"  >
	<table cellpadding="0" cellspacing="0" class="syj-table2 " id="nameInfo" style="table-layout: auto;">
		<tbody>
			<tr>
				<th>原企业名称：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 w878 inputBackgroundCcc" readonly="readonly"
						name="COMPANY_NAME" maxlength="64" value="${busRecord.COMPANY_NAME}"/>
				</td>
			</tr>
			<tr>
				<th>原集团名称：</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
						   name="GROUP_NAME"  maxlength="128" value="${busRecord.GROUP_NAME}"/>
				</td>
				<th>原集团简称：</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
						   name="GROUP_ABBRE"  maxlength="64" value="${busRecord.GROUP_ABBRE}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>统一社会信用代码：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 w878 validate[required] inputBackgroundCcc"
						name="SOCIAL_CREDITUNICODE" maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE}"/>
				</td>
			</tr>
		</tbody>
		<tbody id="C_COMPANYNAME" style="display: none;">
			<tr>
				<th><font class="syj-color2">*</font>新企业名称：</th>
				<td colspan="3">
					<input type="text" class="syj-input1  w878"
						name="COMPANY_NAME_CHANGE" maxlength="64" placeholder="请输入企业名称，请按所登记的名称进行申报" value="${busRecord.COMPANY_NAME_CHANGE}"/>
				</td>
			</tr>
			<tr>
				<th>新集团名称：</th>
				<td>
					<input type="text" class="syj-input1 "
						   name="GROUP_NAME_CHANGE"  maxlength="128" placeholder="请输入集团名称" value="${busRecord.GROUP_NAME_CHANGE}"/>
				</td>
				<th>新集团简称：</th>
				<td>
					<input type="text" class="syj-input1 "
						   name="GROUP_ABBRE_CHANGE"  maxlength="64" placeholder="请输入集团简称" value="${busRecord.GROUP_ABBRE_CHANGE}"/>
				</td>
			</tr>
		</tbody>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " id="addrInfo">
		<tbody>
			<tr>
				<th>原注册地址：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 w878 inputBackgroundCcc" readonly="readonly"
						name="REGISTER_ADDR" maxlength="128" value="${busRecord.REGISTER_ADDR}"/>
				</td>
			</tr>
		</tbody>
		<tbody id="C_REGADDRESS" style="display: none;">
			<tr>
				<th><font class="syj-color2">*</font>新注册地址：</th>
				<td><input type="text" class="syj-input1 " onchange="setSendLawAddr()"
					name="REGISTER_ADDR_CHANGE" maxlength="126"  placeholder="请输入注册地址，请按房产证上的地址申报" value="${busRecord.REGISTER_ADDR_CHANGE}"/></td>
				<th><font class="syj-color2">*</font>面积：</th>
				<td>
					<input type="text" maxlength="16" class="syj-input1 w280"
						   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>法律文书送达地址(同注册地址)：</td>
				<td >
					<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
							<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
							<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
				</td>
				<th><font class="syj-color2">*</font>法律文书送达地址：</th>
				<td><input  type="text" class="syj-input1 "
							name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/></td>
				</td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>法律文书电子送达地址：</td>
				<td colspan="3">
					邮箱：<input type="text" class="syj-input1 "
							  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
					<br>传真：<input type="text" class="syj-input1 "
							  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
					<br>即时通讯账号（如微信）：<input type="text" class="syj-input1 "
									   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
				</td>
			</tr>
			<script type="text/javascript">
				function setSendLawAddr() {
					var val = $("[name='IS_REGISTER_ADDR']:checked").val();
					if(val=='1'){
						$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR_CHANGE']").val());
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
				<td><input  type="text" class="syj-input1 "
							name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
				<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="syj-input1 "
						   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>
	
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>取得方式：</th>
				<td colspan="3">
					<eve:eveselect clazz="input-dropdown w878" dataParams="wayOfGet"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否属于军队房产：</th>
				<td >
					<eve:eveselect clazz="input-dropdown w280" dataParams="armyHourse"
								   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REGISTER_REMARKS','03')"
								   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2" id="qtzm" style="display:none">*</font>其他证明：</th>
				<td >
					<input type="text" class="syj-input1" disabled
						   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
				</td>
			</tr>
			
			<tr>
				<th ><font class="syj-color2">*</font>是否有生产经营地址：</th>
				<td colspan="3">
					<input type="radio" name="IS_BUSSINESS_ADDR" value="1" <c:if test="${busRecord.IS_BUSSINESS_ADDR==1}"> checked="checked"</c:if>>是
					<input type="radio" name="IS_BUSSINESS_ADDR" value="0" <c:if test="${busRecord.IS_BUSSINESS_ADDR!=1}"> checked="checked"</c:if>>否
				</td>
			</tr>
		</tbody>
	</table>
	<table cellpadding="0" id="bussinessAddrTable" cellspacing="0" class="syj-table2 " style="display: none;">
		<tr>
			<th>生产经营地址：</th>
			<td><input type="text" class="syj-input1 "
				name="BUSSINESS_ADDR" maxlength="128"  placeholder="请输入生产经营地址，请按房产证上的地址申报"  value="${busRecord.BUSSINESS_ADDR}"/>
			</td>
			<th>面积：</th>
			<td>
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
			<td colspan="3">
				<eve:eveselect clazz="input-dropdown w280" dataParams="wayOfGet"
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
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>企业联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
				name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

		</tr>
	</table>
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 " id="companyTypeInfo">
		<tr>
			<th>原公司类型：</th>
			<td>
									
				<input name="COMPANY_TYPE" id="COMPANY_TYPE" class="syj-input1 easyui-combobox" disabled="disabled" 
					data-options="
		                url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
		                method:'post',
		                valueField:'TYPE_CODE',
		                textField:'TYPE_NAME',
		                panelHeight:'auto',
		                required:true,
		                editable:false
                " value="${busRecord.COMPANY_TYPE }" />				
			</td>
		</tr>
		<tr id="C_COMPANYTYPE" style="display:none;">
			<th><font class="syj-color2">*</font>新公司类型：</th>
			<td>
												
				<input name="COMPANY_TYPE_CHANGE" id="COMPANY_TYPE_CHANGE" class="syj-input1 easyui-combobox"
					data-options="
		                url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
		                method:'post',
		                valueField:'TYPE_CODE',
		                textField:'TYPE_NAME',
		                panelHeight:'auto',
		                required:false,
		                editable:false,
		                onSelect:function(rec){
						   $('#COMPANY_NATURE').combobox('clear');
						   if(rec.TYPE_CODE){
						       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
						       $('#COMPANY_NATURE').combobox('reload',url);
						       natureChage();  
						   }
						}
                " value="${busRecord.COMPANY_TYPE_CHANGE }" />
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>公司组织架构：</th>
			<td>								
				<input name="COMPANY_NATURE" id="COMPANY_NATURE" class="syj-input1 easyui-combobox w280" style="height: 40px; line-height: 40px;border: 1px solid #c9deef"
					data-options="			                
			                url:'dictionaryController/loadData.do?typeCode=0&orderType=asc',
			                method:'post',
			                valueField:'DIC_CODE',
			                textField:'DIC_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onChange: function(rec){
			                	natureChage();
			                }
	                " value="${busRecord.COMPANY_NATURE }" />
			</td>
		</tr>
	</table>
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 " id="businessYearInfo">
		<tr>
			<th><font class="syj-color2">*</font>原经营期限：</th>
			<td>
				<input type="radio"  name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio"  name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th>原经营期限年数：</td>
			<td><input type="text" class="syj-input1 validate[custom[numberplus]] validate[required]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr> 
		<tbody>
			<tr>
				<th>原经营期限起始：</th>
				<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" disabled="disabled"
					readonly="readonly" name="YEAR_FROM" value="${busRecord.YEAR_FROM}"/></td>
				<th>原经营期限结束：</th>
				<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" disabled="disabled"
					readonly="readonly" name="YEAR_TO" value="${busRecord.YEAR_TO}"/></td>
			</tr>
		</tbody>
		<tbody id="C_BUSINESSYEAR" style="display: none;">
			<tr>
				<th><font class="syj-color2">*</font>新经营期限：</th>
				<td>
					<input type="radio" name="OPRRATE_TERM_TYPE_CHANGE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE_CHANGE==1}"> checked="checked"</c:if>>年
					<input type="radio" name="OPRRATE_TERM_TYPE_CHANGE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE_CHANGE==0}"> checked="checked"</c:if>>长期
				</td>
				<th> 经营期限年数：</td>
				<td><input type="text" class="syj-input1 validate[custom[numberplus]]" 
					name="BUSSINESS_YEARS_CHANGE"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS_CHANGE}"/></td>
			</tr>
			<%-- <tr>
				<th><font class="syj-color2">*</font>经营期限起始：</th>
				<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
					readonly="readonly" name="YEAR_FROM_CHANGE" value="${busRecord.YEAR_FROM_CHANGE}"/></td>
				<th><font class="syj-color2">*</font>经营期限结束：</th>
				<td ><input type="text" class="Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
					readonly="readonly" name="YEAR_TO_CHANGE" value="${busRecord.YEAR_TO_CHANGE}"/></td>
			</tr> --%>
		</tbody>
	</table>
	
	<table cellpadding="0" cellspacing="0" class="syj-table2 " id="businessScopeInfo">
		<tbody>
			<tr>
				<th>经营范围：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
						class=" w878 eve-textarea inputBackgroundCcc" readonly="readonly"
						style="height:100px;" >${busRecord.BUSSINESS_SCOPE}</textarea>
				</td>
			</tr>
		</tbody>
		<tbody id="C_BUSINESSSCOPE" style="display: none;">
			<tr>
				<th><font class="syj-color2">*</font>新经营范围：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSSINESS_SCOPE_CHANGE" 
						class="eve-textarea w878 validate[maxSize[1000]]" readonly="readonly"
						style="height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwNew();">${busRecord.BUSSINESS_SCOPE_CHANGE}</textarea> 					
					<input type="hidden" name="BUSSINESS_SCOPE_ID_CHANGE"  value="${busRecord.BUSSINESS_SCOPE_ID_CHANGE}">
					<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
					<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
					<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
					中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
					可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				</td>
			</tr>
			<tr>
				<th> 新投资行业：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="INVEST_INDUSTRY_CHANGE" readonly="readonly"
						class="eve-textarea w878 validate[ validate[],maxSize[2000]]"
						style="height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY_CHANGE}</textarea> 					
					<input type="hidden" name="INVEST_INDUSTRY_ID_CHANGE" value="${busRecord.INVEST_INDUSTRY_ID_CHANGE}">
				</td>
			</tr>
			<tr>
				<th>行业编码：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 inputBackgroundCcc"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
					name="INDUSTRY_CODE_CHANGE"  value="${busRecord.INDUSTRY_CODE_CHANGE}"/></td>
			</tr>
		</tbody>
	</table>
</form>
