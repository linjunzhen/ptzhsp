<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String ywId = request.getParameter("YB_YWID");
	request.setAttribute("ywId",ywId);
	String exeId = request.getParameter("exeId");
	request.setAttribute("exeId",exeId);
	
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
	 <input name="DWBH" type="hidden"/>
	  <input name="PUSH_FLAG" type="hidden"/>
	<tr>
		<th colspan="6">单位信息</th>
	</tr>
	<!-- <tr style="height:38px;">
	    <td class="tab_width" style="text-align:left">操作：</td>
		<td colspan="5">
			<input type="button" class="eflowbutton"  id="departQueryBtn"
				onclick="departQuery();" value="单位信息查询"/>
			<input type="button" class="eflowbutton"  id="departRosterQueryBtn"
				onclick="departRosterQuery();" value="单位花名册查询"/>
		</td>
	</tr> -->
	<tr>
		<td class="tab_width"> 单位档案号：</td>
		<td>
			<input type="text" class="tab_text"  name="DWDAH" readonly="readonly"/>
			<input type="button" class="eflowbutton"  id="departQueryBtn"
				onclick="departQuery();" value="单位信息查询"/>
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="DWBXH" readonly="readonly"/>
		</td>
		<td class="tab_width"> 单位名称：</td>
		<td>
			<input type="text" class="tab_text" name="DWMC" readonly="readonly"/>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">个人基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="XM" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="idCardType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="请选择" name="ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="ZJHM" 
			onblur="getBirthAndSex('ZJHM','BIRTHDAY','SEX');" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 证件有效期始：</td>
		<td>
			<input type="text" id="ZJYXQQS" name="ZJYXQQS" 
			onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZJYXQJZ\')}'})" 
			class="tab_text Wdate validate[]" maxlength="60"  style="width:180px" />
		</td>
		<td class="tab_width"> 证件有效期止：</td>
		<td>
			<input type="text" id="ZJYXQJZ" name="ZJYXQJZ"
			onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'ZJYXQQS\')}'})" 
			class="tab_text Wdate validate[]" maxlength="60"  style="width:180px" />
		</td>
		<td class="tab_width"></td>
		<td>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ybSex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="SEX">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 民族：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="ybNation"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="NATION">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="BIRTHDAY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 解除劳动关系时间：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JCLDGXSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"> 异地参保开始时间：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="YDCBKSSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"> 参加工作日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="CJGZSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 户口性质：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="populateNature"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="HKXZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>所属行政区划：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="" name="SSXZQH">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 待遇享受级别：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="enjoyLevel"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="DYXSJB">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 婚姻状况：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="maritalStatus"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="HYZK">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 手机号码：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[mobilePhoneLoose]]" name="MOBILE" />
		</td>
		<td class="tab_width"> 联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="PHONE" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[chinaZip]]" name="POSTCODE" />
		</td>
		<td class="tab_width"> 通讯地址：</td>
		<td colspan="3">
			<input type="text" class="tab_text" style="width:440px;" name="TXDZ" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 就业状态：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="employmentStatus"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="JYZT">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 军转干部标志：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="cadreSign"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="JZGBBZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 人员生存状态：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="survivalState"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="RYSCZT">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 个人保险号：</td>
		<td>
			<input type="text" class="tab_text" name="GRBXH" />
		</td>
		<td class="tab_width"></td>
		<td>
		</td>
		<td class="tab_width"></td>
		<td>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="post"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="JOB">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 备注：</td>
		<td colspan="3">
			<input type="text" class="tab_text" style="width:440px;" name="REMARK" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 连续工龄：</td>
		<td>
			<input type="text" class="tab_text" name="LXGL" value="0" disabled="disabled"/>
		</td>		
		<td class="tab_width"> 异地参保月数：</td>
		<td>
			<input type="text" class="tab_text" name="YDCBYS" value="0" disabled="disabled"/>
		</td>
		<td class="tab_width"> 待遇开始日期：</td>
		<td>
			<input type="text" class="tab_text" name="DYKSSJ" disabled="disabled"/>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">职工缴费信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 银行行号：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="YHHH"/>
		</td>
		<td class="tab_width"> 开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="KHHMC"/>
		</td>
		<td class="tab_width"> 有效标志：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="effectiveSign"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择" name="YXBZ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 缴费账号：</td>
		<td colspan="5">
			<input type="text" class="tab_text" style="width:630px;" name="JFZH" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 缴费账号名：</td>
		<td colspan="5">
			<input type="text" class="tab_text" style="width:630px;" name="JFZHMC" />
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">职工参保信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>参保身份：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="insuredIdentity:04"
			dataInterface="dictionaryService.findDatasForSelectIn"
			defaultEmptyText="" name="CBSF">
			</eve:eveselect>
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>参保开始日期：</td>
		<td>
			<input type="text"  class="tab_text Wdate validate[required]" name="CBKSSJ" onchange="setGridDate(this.value);"
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true});" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>申报工资（元）：</td>
		<td>
			<input type="text" class="tab_text validate[required,custom[numberplus]]" name="SBGZ"/>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th>险种信息</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" style="width:100%">
	<tr>
		<td style="height:300px;">
			<table class="easyui-datagrid" rownumbers="false" pagination="false"
				id="xzxxGrid" fitcolumns="true" 
				checkonselect="true" style="width:100%;height:auto;"
				selectoncheck="true" fit="true" border="false"
				data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybLhjyController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData">
			<thead>
				<tr>
				   <th data-options="field:'isCheck',align:'left',checkbox:true"></th>
				   <th data-options="field:'aae140',width:'24%',align:'left',
					   	formatter:function(value,row){
					   		if(value==row.aae140){
						   	var diclist = xzData;
					        for (var i = 0; i < diclist.length; i++) {
						        if (diclist[i].DIC_CODE == value) {
						            return diclist[i].DIC_NAME;
						        }
					    	}
					   	}}
					">险种类型
					</th>
					<th data-options="field:'aae030',width:'24%',align:'left',
					    editor:{
							type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
						}
					"
					>开始日期
					</th>
					<th data-options="field:'aae031',align:'left',editor:{type:'datebox',options:{required:false,editable:false,formatter:endformatter,parser:parseDate}}" width="20%" >
					截止日期
					</th>
					<th data-options="field:'aac031',width:'25%',align:'left',
						formatter:function(value,row){ 
					   		if(value==row.aac031){   	
						   	var diclist = cbData;
					        for (var i = 0; i < diclist.length; i++) {
						        if (diclist[i].DIC_CODE == value) {
						            return diclist[i].DIC_NAME;
						        }
					    	}
					   	}},
					    editor:{
							type:'combobox',options:{required:false,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=insuredState&orderType=asc'}
						}
					">参保状态
					</th>
				</tr>
			 </thead>
		</table>
		</td>
	</tr>
</table> 