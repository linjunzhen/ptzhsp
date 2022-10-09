<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

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

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="jbxx">
	<tr>
		<th colspan="6">单位基本信息</th>
	</tr>
	<tr>
		<td class="tab_width" style="text-align:center">操作：</td>
		<td colspan="5">
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectUnitInfos();">单位信息查询</a>
		</td>
	</tr>	
	<tr style="float:left;height:25px">
		<td style="font-weight: bold;background: none;" colspan="6">【基本信息】</td>
	</tr>
	<tr style="height:34px">					
		<td class="tab_width">单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DWBXH" value="${recordinfo.DWBXH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位编号：</td>
		<td >	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DWBH" value="${recordinfo.DWBH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">社保编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="SBBM" value="${recordinfo.SBBM}" style="width: 180px;"/>
		</td>	
	</tr>	
	<tr>					
		<td class="tab_width">单位档案号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DWDAH" value="${recordinfo.DWDAH}" style="width: 180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="DWMC" value="${recordinfo.DWMC}" style="width: 180px;"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择单位类型" name="DWLX" id="DWLX" value="${recordinfo.DWLX}">
			</eve:eveselect>
		</td>
		<td class="tab_width">组织机构代码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZZJGDM" value="${recordinfo.ZZJGDM}" style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>所属行政区划：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="请选择" name="XZQH" id="XZQH" value="${recordinfo.XZQH}">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">工商执照种类：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择工商执照种类" name="ZZZL" id="ZZZL" value="${recordinfo.ZZZL}">
			</eve:eveselect>
		</td>
		<td class="tab_width">工商执照号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZZHM" value="${recordinfo.ZZHM}" style="width: 180px;"/>
		</td>
		<td class="tab_width">工商发照日期：</td>
		<td>
			<input type="text" id="FZRQ" name="FZRQ" value="${recordinfo.FZRQ }"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
				class="tab_text Wdate validate[]" maxlength="60"  style="width:180px" />
		</td>	
	</tr>	
	<tr>
		<td class="tab_width">工商有效年限：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YXNX" value="${recordinfo.YXNX}" style="width: 180px;"/>
		</td>
		<td class="tab_width">行业代码：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择行业代码" name="HYDM" id="HYDM" value="${recordinfo.HYDM}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择单位类别" name="DWLB" id="DWLB" value="${recordinfo.DWLB}">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">主管部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZGBM" value="${recordinfo.ZGBM}" style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>隶属关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择隶属关系" name="LSGX" id="LSGX" value="${recordinfo.LSGX}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊单位类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择特殊单位类别" name="TSDWLB" id="TSDWLB" value="${recordinfo.TSDWLB}">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>税务机构编号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="SWJGBH" value="350128" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">税务机构名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="SWJGMC" value="平潭县地方税务局" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>税号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="SH" value="" style="width: 180px;" />
		</td>	
	</tr>
	<tr>
		<td class="tab_width">工商登记发照机关：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FZJG" value="${recordinfo.FZJG}" style="width: 180px;"/>
		</td>
		<td class="tab_width">地税比对结果：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="地税比对结果" name="DSBDJG" id="DSBDJG" value="${recordinfo.FZJG}">
			</eve:eveselect>
		</td>
		<td class="tab_width">合同号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="HTH" value="${recordinfo.HTH}" style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">批准成立部门：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CLBM" value="${recordinfo.CLBM}" style="width: 180px;"/>
		</td>
		<td class="tab_width">批准成立日期：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CLRQ" value="${recordinfo.CLRQ}" style="width: 180px;"/>
		</td>
		<td class="tab_width">批准成立文号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CLWH" value="${recordinfo.CLWH}" style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>地区编号：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择地区编号" name="DQBH" id="DQBH" value="${recordinfo.DQBH}">
			</eve:eveselect>
		</td>
		<td class="tab_width">备注：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="REMARK" value="${recordinfo.REMARK}" style="width: 360px;"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">社会统一信用代码：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="TYXYDM" value="${recordinfo.TYXYDM}" style="width:780px;"/>
		</td>
	</tr>	
	<tr style="float:left,height:25px">
		<td style="font-weight: bold;background: none;" colspan="6">【联系信息】</td>
	</tr>		
	<tr>					
		<td class="tab_width">邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YZBM" value="${recordinfo.YZBM}" style="width: 180px;"/>
		</td>
		<td class="tab_width">地址：</td>
		<td colspan="3">	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DZ" value="${recordinfo.DZ}" style="width: 780px;"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">法人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRXM" value="${recordinfo.FRXM}" style="width: 180px;"/>
		</td>
		<td class="tab_width">法人证件号码：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRZJHM" value="${recordinfo.FRZJHM}" style="width: 180px;"/>
		</td>	
		<td class="tab_width">法人职务：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="FRZW" value="${recordinfo.FRZW}" style="width: 180px;"/>
		</td>
	</tr>
	<tr>					
		<td class="tab_width">联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="LXRXM" value="${recordinfo.LXRXM}" style="width: 180px;"/>
		</td>
		<td class="tab_width">联系电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="LXDH" value="${recordinfo.LXDH}" style="width: 180px;"/>
		</td>	
		<td class="tab_width">单位电话：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="DWDH" value="${recordinfo.DWDH}" style="width: 180px;"/>
		</td>
	</tr>	
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="dwcbxx">
	<tr>
		<th colspan="4">单位参保信息</th>
	</tr>		
	<tr>					
		<td class="tab_width">银行行号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YHHH" value="${recordinfo.YHHH}" style="width: 180px;" />
		</td>
		<td class="tab_width">开户行名称：</td>
		<td >	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="KHHMC" value="${recordinfo.KHHMC}" style="width: 180px;"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">缴费账号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="JFZH" value="${recordinfo.JFZH}" style="width: 180px;" />
		</td>
		<td class="tab_width">缴费账号名：</td>
		<td >	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="JFZHM" value="${recordinfo.JFZHM}" style="width: 180px;" />
		</td>
	</tr>	
	<%-- <tr>
		<td class="tab_width">征收方式设置：</td>
		<td colspan="3">	
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择征收方式" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="1">
			</eve:eveselect>
		</td>
	</tr> --%>					
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="paramConfGrid" fitcolumns="true" toolbar="#paramConfToolBar" 
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybYrdwbgController.do?paramjson',onBeforeLoad:getComboboxData"
			>
			<thead>
				<tr>
				   <th data-options="field:'ck',align:'left',checkbox:true"></th>
				   <th data-options="field:'XZ_TYPE',width:'23%',align:'left',
					   	formatter:function(value,row){ 
					   		if(value==row.XZ_TYPE){
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
					<th data-options="field:'FLAG',width:'25%',align:'left',formatter:setValue"
					>是否审核
					</th>
					<th data-options="field:'ZSFS',width:'25%',align:'left',
						formatter:function(value,row){ 
					   		if(value==row.ZSFS){
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
					<th data-options="field:'BEGIN_TIME',width:'25%',align:'left',
					    editor:{
							type:'validatebox',options:{required:true,validType:'length[1,200]'}
						}
					"
					>参保日期
					</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	
