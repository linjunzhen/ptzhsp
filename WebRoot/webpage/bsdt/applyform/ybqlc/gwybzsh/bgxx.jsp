<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>	

 <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="bgxx">
    <input type="hidden" name="BG_RYLBID" />
    <input type="hidden" name="BG_WZBID" />
    <input type="hidden" name="BG_GRBH" />
	<tr>
		<th colspan="6">变更信息</th>
	</tr>	
	<tr>
	    <td class="tab_width" >操作：</td>
		<td colspan="5">
		   <input type="button" value="已登记人员查询" class="eve-button" onclick="showSelectBgYdjInfos()" /> 
		</td>
	</tr>		
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="BG_HM"  style="width: 180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>姓名：</td>
		<td>	
			<input type="text" class="tab_text validate[required]" maxlength="32"
			name="BG_XM"  style="width:180px;" />
		</td>
		<td class="tab_width">工作状态：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="BG_GZZT" id="BG_GZZT" >
			</eve:eveselect>
		</td>
	</tr>			
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>个人保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="BG_BXH"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="BG_DWBXH"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>分中心：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="subCenter"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="BG_FZX" id="BG_FZX" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="BG_DWMC"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊人员类别：</td>
		<td colspan="3">	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TSRYLB"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="BG_RYLB" id="BG_RYLB" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>危病种编码：</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="WBZBM"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="BG_WBZBM" id="BG_WBZBM" >
			</eve:eveselect>
		</td>
		<td class="tab_width">银行户名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="BG_YHHM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">银行账户：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="BG_YHZH"  style="width: 180px;"/>
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color ">*</font>起始时间：</td>
		<td>
			<input type="text" id="BG_QSNY" name="BG_QSNY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'BG_ZZNY\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>截止时间：</td>
		<td colspan="3">
			<input type="text" id="BG_ZZNY" name="BG_ZZNY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'BG_QSNY\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="BG_BZ"  style="width:50%;"/>
		</td>	
	</tr>	
</table>




	

	
			
		
