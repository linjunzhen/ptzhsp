<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String ywId = request.getParameter("YB_YWID");
	request.setAttribute("ywId",ywId);
	String exeId = request.getParameter("exeId");
	request.setAttribute("exeId",exeId);
	
%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
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
.selectType{
	margin-left: -100px;
}
</style>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
    <input name="DWID" type="hidden" />
    <input name="PUSH_FLAG" type="hidden" />
	<tr>
		<th colspan="6">居民单位信息</th>
	</tr>			
	<tr style="height:35px;">					
		<td class="tab_width"><font class="tab_color ">*</font>居民单位编号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="DWBH" readonly="readonly"/>
			<input type="button" class="eflowbutton" onclick="showSelectUnitInfos();" value="居民单位信息查询"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>居民单位名称：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[required]" name="DWMC" readonly="readonly" />
		</td>
	</tr>	
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">个人基本信息</th>
	</tr>		
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="ZJHM" 
			onblur="getBirthAndSex('ZJHM','BIRTHDAY','SEX');"/>
			<input type="button" class="eflowbutton" onclick="checkIsHaveDd();" value="校验"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>证件类型：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="idCardType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="请选择" name="ZJLX" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="NAME" />
		</td>	
	</tr>
	<tr>
		<td class="tab_width">证件有效期始：</td>
		<td>
			<input type="text" id="ZJ_BEGIN_TIME" name="ZJ_BEGIN_TIME" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZJ_END_TIME\')}'})" 
				class="tab_text Wdate validate[]" readonly="true" />
		</td>
		<td class="tab_width">证件有效期止：</td>
		<td colspan="3">
			<input type="text" id="ZJ_END_TIME" name="ZJ_END_TIME" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'ZJ_BEGIN_TIME\')}'})" 
				class="tab_text Wdate validate[]" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>性别：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ybSex"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="SEX" id="SEX" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>民族：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ybNation"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="MZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>出生日期：</td>
		<td>
			<input type="text" id="BIRTHDAY" name="BIRTHDAY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true"
				class="tab_text Wdate validate[required]" />
		</td>	
	</tr>
	<tr>
		<td class="tab_width">户口性质：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="populateNature"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="HKXZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>所属行政区划：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="XZQH">
			</eve:eveselect>
		</td>
		<td class="tab_width">手机号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="MOBILE"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>是否新生儿：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SXE"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="XSE" >
			</eve:eveselect>
		</td>
		<td class="tab_width">邮政编码：</td>
		<td >	
			<input type="text" class="tab_text validate[]" name="YZBM" />
		</td>
		<td class="tab_width">个人保险号：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="GRBXH" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">婚姻状况：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="maritalStatus"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="HYZK">
			</eve:eveselect>
		</td>
		<td class="tab_width">军转干部标志：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="cadreSign"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="JZGBBZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>人员生存状态：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="survivalState"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="RYSCZT">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="PHONE"/>
		</td>
		<td class="tab_width">通讯地址：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" name="TXDZ" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">入学时间：</td>
		<td>
			<input type="text"  name="RXSJ" onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				class="tab_text Wdate validate[]" readonly="true"/>
		</td>
		<td class="tab_width">拟毕业时间：</td>
		<td>
			<input type="text" name="BYSJ" value="${recordinfo.BYSJ}"
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				class="tab_text Wdate validate[]"  readonly="true"/>
		</td>
		<td class="tab_width">院系名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="YXMC" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">年级：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="NJ" />
		</td>
		<td class="tab_width">班级：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="BJ" />
		</td>
		<td class="tab_width">学号：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="XH" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">是否贫困生：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="YESNO"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="PKS" >
			</eve:eveselect>
		</td>
		<td class="tab_width">待遇开始日期：</td>
		<td>
			<input type="text" id="DY_BEGIN_TIME" name="DY_BEGIN_TIME" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true"
				class="tab_text Wdate validate[]" />
		</td>
		<td class="tab_width">备注：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="REMARK" />
		</td>	
	</tr>
	<tr>
		<td class="tab_width">户主身份证：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="HZSFZ" />
		</td>
		<td class="tab_width">与户主关系</td>
		<td colspan="3">	
			<eve:eveselect clazz="tab_text validate[]" dataParams="HZGX"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="YHZGX">
			</eve:eveselect>
		</td>				
	</tr>	
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">			
	<tr>
		<th colspan="6">居民参保信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>参保身份</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="CBSF">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>参保开始日期：</td>
		<td colspan="3">
			<input type="text" name="CB_BEGIN_TIME" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})"  
				onchange="setGridDate(this.value);"
				class="tab_text Wdate validate[required]" readonly="true"/>
		</td>		
	</tr>		
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">险种信息</th>
	</tr>
</table>			
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:300px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="xzConfGrid" fitcolumns="true" checkonselect="true" 
			selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybCxjmcbxbController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData"
			>
			<thead>
				<tr>
				   <th data-options="field:'ck',align:'left',checkbox:true"></th>	
				   <th data-options="field:'aae140',width:'24%',align:'left',
					   	formatter:function(value,row){ 
					   		if(value==row.aae140){
						   	var diclist = xzData;
					        for (var i = 0; i < diclist.length; i++) {
						        if (diclist[i].DIC_CODE == value) {
						            return diclist[i].DIC_NAME;
						        }
					    	}
					   	}},
					    editor:{
							type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=TypeOfInsurance&orderType=asc'}
						}">险种类型
					</th>
					<th data-options="field:'aae030',width:'24%',align:'left',
					    editor:{
							type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
						}">开始日期
					</th>
					<th data-options="field:'aae031',align:'left', width:'20%',
						editor:{
							type:'datebox',options:{required:false,editable:false,formatter:endformatter,parser:parseDate}
							}">截止日期
					</th>
					<th data-options="field:'aac066',width:'25%',align:'left',
						formatter:function(value,row){ 
					   		if(value==row.aac066){   	
						   	var diclist = cbsfData;
					        for (var i = 0; i < diclist.length; i++) {
						        if (diclist[i].DIC_CODE == value) {
						            return diclist[i].DIC_NAME;
						        }
					    	}
					   	}},
					    editor:{
							type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=insuredIdentity&orderType=asc&defaultEmptyText=参保身份'}
						}">参保身份
						
					</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

