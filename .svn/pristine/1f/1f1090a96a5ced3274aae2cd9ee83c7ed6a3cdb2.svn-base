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

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
    <input type="hidden" name="PUSH_FLAG" />
	<tr>
		<th colspan="6">单位基本信息</th>
	</tr>	
<!-- 	<tr id="pushInfo" style="display:none">
		<td class="tab_width" style="text-align:center">操作：</td>
		<td colspan="5">
		   <input id="pushYbBtn" type="button" class="eflowbutton" onclick="pushYrdwCb();" value="单位参保信息推送医保"/>
		</td>
	</tr> -->	
	<tr style="height:34px">					
		<td class="tab_width">单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="DWBXH"  style="width: 180px;"  disabled="disabled"/>
		</td>
		<td class="tab_width">单位编号：</td>
		<td >	
			<input type="text" class="tab_text validate[]" 
			name="DWBH"  style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">社保编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="SBBM" style="width: 180px;" maxlength=64/>
		</td>	
	</tr>	
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>单位档案号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" 
			name="DWDAH"  style="width: 180px;" maxlength=64 />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td>	
			<input type="text" class="tab_text validate[required]" 
			name="DWMC"  style="width: 180px;" maxlength=128/>
		</td>
		<td class="tab_width">征收方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="collectionMethod"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZSFS">
			</eve:eveselect>
		</td>			
	</tr>
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TypeOfUnit"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="DWLX" id="DWLX">
			</eve:eveselect>
		</td>
		<td class="tab_width">组织机构代码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="ZZJGDM" style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>所属行政区划：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="" name="XZQH" id="XZQH" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">工商执照种类：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="GSZZZL"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZZZL">
			</eve:eveselect>
		</td>
		<td class="tab_width">工商执照号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="ZZHM" style="width: 180px;" maxlength=32/>
		</td>
		<td class="tab_width">工商发照日期：</td>
		<td>
			<input type="text" id="FZRQ" name="FZRQ"
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				class="tab_text Wdate validate[]" maxlength="32"  style="width:180px" />
		</td>	
	</tr>	
	<tr>
		<td class="tab_width">工商有效年限：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="YXNX" style="width: 180px;" maxlength=32/>
		</td>
		<td class="tab_width">行业代码：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="HYDM"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="HYDM">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DWLB"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="DWLB">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">主管部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="ZGBM" maxlength=128 style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>隶属关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="Affiliation"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="LSGX" id="LSGX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="UnitCategory"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="TSDWLB" id="TSDWLB" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>税务机构编号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" 
			name="SWJGBH" value="350128" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">税务机构名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="SWJGMC" style="width:180px;" disabled="disabled" maxlength=128/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>税号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" 
			name="SH" style="width: 180px;" maxlength=64/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">工商登记发照机关：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="FZJG" style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width">地税比对结果：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="DSBDJG"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="DSBDJG" id="DSBDJG" >
			</eve:eveselect>
		</td>
		<td class="tab_width">合同号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="HTH" style="width: 180px;" maxlength=64/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">批准成立部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="CLBM" style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width">批准成立日期：</td>
		<td>	
			<input type="text" class="tab_text Wdate validate[]" name="CLRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				readonly="true" style="width: 180px;" maxlength=32/>
		</td>
		<td class="tab_width">批准成立文号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="CLWH" style="width: 180px;" maxlength=64/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>地区编号：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DQBH"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="DQBH" id="DQBH">
			</eve:eveselect>
		</td>
		<td class="tab_width">备注：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" 
			name="REMARK" style="width: 360px;" maxlength=512/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">社会统一信用代码：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" 
			name="TYXYDM"  style="width:790px;" maxlength=64/>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
    <input type="hidden" name="PUSH_FLAG" />
	<tr>
		<th colspan="6">联系信息</th>
	</tr>			
	<tr>					
		<td class="tab_width">邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="YZBM" style="width: 180px;" maxlength=32/>
		</td>
		<td class="tab_width">地址：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[]" 
			name="DZ" style="width: 780px;" maxlength=256/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">法人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="FRXM"  style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width">法人证件号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="FRZJHM" style="width: 180px;" maxlength=64/>
		</td>	
		<td class="tab_width">法人职务：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="FRZW"  style="width: 180px;" maxlength=128/>
		</td>
	</tr>
	<tr>					
		<td class="tab_width">联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="LXRXM"  style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width">联系电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="LXDH"  style="width: 180px;" maxlength=32/>
		</td>	
		<td class="tab_width">单位电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" 
			name="DWDH" style="width: 180px;" maxlength=32/>
		</td>
	</tr>	
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">单位参保信息</th>
	</tr>		
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>银行行号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" 
			name="YHHH" style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>开户行名称：</td>
		<td >	
			<input type="text" class="tab_text validate[required]" 
			name="KHHMC" style="width: 180px;" maxlength=128/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">缴费账号：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="JFZH"  style="width: 180px;" maxlength=64/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>缴费账号名：</td>
		<td >	
			<input type="text" class="tab_text validate[required]" 
			name="JFZHM"  style="width: 180px;" maxlength=64/>
		</td>
	</tr>						
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:300px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="paramConfGrid" fitcolumns="true" toolbar="#paramConfToolBar" 
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybYrdwcbController.do?paramjson&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData"
			>
			<thead>
				<tr>
				   <th data-options="field:'ck',align:'left',checkbox:true"></th>
				   <th data-options="field:'aae140',width:'33%',align:'left',
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
					<th data-options="field:'aab033',width:'33%',align:'left',
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
							type:'combobox',options:{required:false,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=collectionMethod&orderType=asc'}
						}
					">征收方式
					</th>
					<th data-options="field:'aab050',width:'31%',align:'left',
					     editor:{
							type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
						}
					">参保日期
					</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

