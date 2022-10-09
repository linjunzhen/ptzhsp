<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
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
	<input type="hidden" id= "COMPANY_ID" name="COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
	<div class="syj-title1 ">
		<span>基本信息</span>
	</div>
	<input type="hidden" id= "SSBA_TYPE" name="SSBA_TYPE" value="${requestParams.COMPANY_TYPECODE}"/>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>企业名称：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[required] w878" 
						name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>						
						<a href="javascript:void(0);" class="eflowbutton" id="showEnterpriseInfo" onclick="showEnterpriseInfo();">查询</a>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
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
			<tr>
				<th ><font class="syj-color2">*</font>统一社会信用代码：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="COMPANY_CODE" maxlength="32" value="${busRecord.COMPANY_CODE}" placeholder="请输入统一社会信用代码"/></td>			
				<th><font class="syj-color2">*</font>公司类型：</th>
				<td>
					<input type="hidden" id= "COMPANY_TYPECODE" name="COMPANY_TYPECODE" value="${busRecord.COMPANY_TYPECODE}"/>
					<input name="COMPANY_TYPE" id="COMPANY_TYPE" class="easyui-combobox" style="width:280px;height:40px;border: 1px solid #c9deef!important;"
						data-options="
							url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
							method:'post',
							valueField:'TYPE_CODE',
							textField:'TYPE_NAME',
							panelHeight:'auto',
							required:true,
							editable:false,
			                onSelect:function(rec){
							   $('#COMPANY_SETNATURE').combobox('clear');
							   if(rec.TYPE_CODE){
							       initGsslxz(rec.TYPE_CODE);
								  $('#COMPANY_SETNATURE').combobox('setValue','');
							   }
							}
					" value="${busRecord.COMPANY_TYPE }" />	
				</td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>注册地址：</th>
				
				<td><input type="text" class="syj-input1 validate[required]" 
					name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/></td>
				<th>面积：</th>
				<td>
					<input type="text" class="syj-input1" maxlength="16"
						   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>公司设立性质：</th>
				<td colspan="3">
					<input name="COMPANY_SETNATURE" id="COMPANY_SETNATURE" class="syj-input1 easyui-combobox"
					 value="${busRecord.COMPANY_SETNATURE}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>企业联系电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
					name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
				<th><font class="syj-color2">*</font>邮政编码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

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
						class="eve-textarea w878 validate[required],maxSize[1000]" readonly="readonly"
						style="height:100px;"  placeholder="请选择经营范围" onclick="showSelectJyfwNew();" >${busRecord.BUSSINESS_SCOPE}</textarea> 					
					<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
					<input type="hidden" name="FLOW_DEFID"  value="${EFLOW_FLOWDEF.DEF_KEY }">	
					<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>				
					<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
					<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
					中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
					可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				</td>
			</tr>
		</table>
	</div>
</form>
