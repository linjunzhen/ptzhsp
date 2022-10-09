<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
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

<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jbxx">
    <input name="YWLX" type="hidden"  />
    <input name="PUSH_FLAG" type="hidden" />
	 <tr>
		<th colspan="6">单位基本信息</th>
	</tr>	
<!-- 	<tr>
	    <td class="tab_width" >操作：</td>
		<td colspan="5">
		   <input type="button" class="eflowbutton"  
				onclick="showSelectUnitInfos();" value="单位信息查询"/>
		</td>
	</tr> -->
	<tr style="height:34px">					
		<td class="tab_width">单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="DWBXH"  style="width: 180px;" disabled="disabled"/>
			<input type="button" class="eflowbutton"  
				onclick="showSelectUnitInfos();" value="单位信息查询"/>
		</td>
		<td class="tab_width">单位编号：</td>
		<td >	
			<input type="text" class="tab_text validate[]" 
			name="DWBH"  style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">社保编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="SBBM"  style="width: 180px;"/>
		</td>	
	</tr>	
	<tr>					
		<td class="tab_width">单位档案号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="DWDAH"  style="width: 180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[required]" 
			name="DWMC"  style="width: 180px;"/>
		</td>	
	</tr>
	<%-- <tr>					
		<td class="tab_width"><font class="tab_color ">*</font>单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="请选择" name="DWLX" id="DWLX" >
			</eve:eveselect>
		</td>
		<td class="tab_width">组织机构代码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="JGDM"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>所属行政区划：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择所属行政区划" name="XZQH" id="XZQH" ">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">工商执照种类：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择工商执照种类" name="ZZZL" id="ZZZL" >
			</eve:eveselect>
		</td>
		<td class="tab_width">工商执照号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZZHM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">工商发照日期：</td>
		<td>
			<input type="text" id="FZRQ" name="FZRQ" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
				class="tab_text Wdate validate[]" maxlength="60"  style="width:180px" />
		</td>	
	</tr>	
	<tr>
		<td class="tab_width">工商有效年限：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YXNX"  style="width: 180px;"/>
		</td>
		<td class="tab_width">行业代码：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择行业代码" name="HYDM" id="HYDM" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择单位类别" name="DWLB" id="DWLB" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">主管部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZGBM"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>隶属关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择隶属关系" name="LSGX" id="LSGX" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择特殊单位类别" name="TSDWLB" id="TSDWLB" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>税务机构编号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="SWJGBH"  style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">税务机构名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="SWJGMC" value="平潭县地方税务局" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>税号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="SH"  style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">工商登记发照机关：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FZJG"  style="width: 180px;"/>
		</td>
		<td class="tab_width">地税比对结果：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="地税比对结果" name="DWBDJG" id="DWBDJG" >
			</eve:eveselect>
		</td>
		<td class="tab_width">合同号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="HTH"  style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">批准成立部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="PZCLBM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">批准成立日期：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="PZCLRQ"  style="width: 180px;"/>
		</td>
		<td class="tab_width">批准成立文号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="PZCLWH"  style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="REMARK"  style="width: 360px;"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">社会统一信用代码：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="TYXYDM"  style="width:780px;"/>
		</td>
	</tr>	
	<tr style="float:left,height:25px">
		<td style="font-weight: bold;background: none;" colspan="6">【联系信息】</td>
	</tr>		
	<tr>					
		<td class="tab_width">邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YZBM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">地址：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ADDRESS"  style="width: 700px;"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">法人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRXM" style="width: 180px;"/>
		</td>
		<td class="tab_width">法人证件号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRZJHM" style="width: 180px;"/>
		</td>	
		<td class="tab_width">法人职务：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRZW"  style="width: 180px;"/>
		</td>
	</tr>
	<tr>					
		<td class="tab_width">联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="LXRXM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">联系电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="LXDH"  style="width: 180px;"/>
		</td>	
		<td class="tab_width">单位电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DWDH"  style="width: 180px;"/>
		</td>
	</tr>	 --%>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="dwcbxx">
	<tr>
		<th colspan="4">单位参保信息</th>
	</tr>		
	<%-- <tr>					
		<td class="tab_width">银行行号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YHHH"  style="width: 180px;" />
		</td>
		<td class="tab_width">开户行名称：</td>
		<td >	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="KHHMC"  style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">缴费账号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="JFZH"  style="width: 180px;" />
		</td>
		<td class="tab_width">缴费账号名：</td>
		<td >	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="JFZHM"  style="width: 180px;" />
		</td>
	</tr>			 --%>			
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:310px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="paramConfGrid" fitcolumns="true" 
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybGwyssfwqrController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData"
			>
			<thead>
				<tr>
				   <th data-options="field:'isCheck',align:'left',checkbox:true"></th>
				   <th data-options="field:'aae140',width:'30%',align:'left',
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
						}
					">险种类型
					</th>
					<!-- <th data-options="field:'YESNO',width:'23%',align:'left',formatter:setValue"
					>是否审核
					</th> -->
					<th data-options="field:'aab033',width:'30%',align:'left',
						formatter:function(value,row){ 
					   		if(value==row.aab033){
						   	var diclist = zsData;
					        for (var i = 0; i < diclist.length; i++) {
						        if (diclist[i].DIC_CODE == value) {
						            return diclist[i].DIC_NAME;
						        }
					    	}
					   	}},
					    editor:{
							type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=collectionMethod&orderType=asc'}
						}
					">征收方式
					</th>
					<!-- <th data-options="field:'aae030',width:'30%',align:'left',
					     editor:{
							type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
						}
					">参保日期 -->
					<th data-options="field:'aae030',align:'center',width:'30%',
						 editor:{
							type:'validatebox',options:{required:true,validType:'length[1,200]'}
						}
					">参保日期</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

