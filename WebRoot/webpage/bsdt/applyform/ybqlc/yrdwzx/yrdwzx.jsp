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

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="CBXX" style="display:none">
	<tr>
		<th colspan="6">参保单位核对标记</th>
	</tr>	
	<tr style="height:34px">
		<td class="tab_width" style="text-align:center">操作：</td>
		<td colspan="5">
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectUnitFlag();">参保单位核对标记查询</a>
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="checkFlag();">核对通过</a>
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="checkFlag();">核对不通过</a>
		</td>
	</tr>	
	<tr>					
		<td class="tab_width">社保登记证编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_SBBM" value="${busRecord.CB_SBBM}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位保险号：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_DWBXH" value="${busRecord.CB_DWBXH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_DWMC" value="${busRecord.CB_DWMC}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">审核标志：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_SHBZ" value="${busRecord.CB_SHBZ}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>核对标记：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="请选择" name="CB_HDBJ" id="CB_HDBJ" value="${busRecord.CB_HDBJ}">
			</eve:eveselect>
		</td>
		<td class="tab_width">单位档案号：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_DWDAH" value="${busRecord.CB_DWDAH}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">单位类型：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_DWLX" value="${busRecord.CB_DWLX}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位编号：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CB_DWBH" value="${busRecord.CB_DWBH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">税号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CS_SH" value="${busRecord.CS_SH}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>	
	<tr>
		<td class="tab_width">隶属关系：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CS_LSGX" value="${busRecord.CS_LSGX}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位类别：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="CS_DWLB" value="${busRecord.CS_DWLB}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="ZXXX" style="display:none">
	<tr>
		<th colspan="6">注销单位信息发送地税</th>
	</tr>	
	<tr style="height:34px">
		<td class="tab_width" style="text-align:center">操作：</td>
		<td colspan="5">
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectZxUnit();">注销单位信息查询</a>
		   <a href="javascript:void(0);" class="eflowbutton"  onclick="pushUnitInfo();">注销单位信息发送地税</a>
		</td>
	</tr>	
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>发送日期：</td>
		<td>
			<input type="text" id="ZX_FSRQ" name="ZX_FSRQ" value="${busRecord.ZX_FSRQ}"
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px"/>
		</td>
		<td class="tab_width">单位保险号：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_DWBXH" value="${busRecord.ZX_DWBXH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_DWMC" value="${busRecord.ZX_DWMC}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>
	<tr>					
		<td class="tab_width">核对标记：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_HDBJ" value="${busRecord.ZX_HDBJ}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">单位人数：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_DWRS" value="${busRecord.ZX_DWRS}" style="width: 180px;" disabled="disabled"/>
		</td>	
		<td class="tab_width">税号：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_SH" value="${busRecord.ZX_SH}" style="width: 180px;" disabled="disabled"/>
		</td>
	</tr>
	<tr>					
		<td class="tab_width">组织机构编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_JGBM" value="${busRecord.ZX_JGBM}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">档案编号：</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_DABH" value="${busRecord.ZX_DABH}" style="width: 180px;" disabled="disabled"/>
		</td>
		<td class="tab_width">地税比对结果：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZX_DSBDJG" value="${busRecord.ZX_DSBDJG}" style="width: 180px;" disabled="disabled"/>
		</td>	
	</tr>	
</table>