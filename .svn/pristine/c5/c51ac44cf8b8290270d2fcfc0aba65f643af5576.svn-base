<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>	

 <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="zxxx">
 	<input type="hidden" name="ZX_RYLBID"  />
    <input type="hidden" name="ZX_WZBID"  />
     <input type="hidden" name="ZX_GRBH"  />
	<tr>
		<th colspan="6">注销信息</th>
	</tr>	
	<tr>
	    <td class="tab_width" >操作：</td>
		<td colspan="5">
		   <input type="button" value="已登记人员查询" class="eve-button" onclick="showSelectZxYdjInfos()" /> 
		</td>
	</tr>		
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZX_HM"  style="width: 180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>姓名：</td>
		<td>	
			<input type="text" class="tab_text validate[required]" maxlength="32"
			name="ZX_XM"  style="width:180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>工作状态：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZX_GZZT" id="ZX_GZZT" >
			</eve:eveselect>
		</td>
	</tr>			
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>个人保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZX_BXH"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZX_DWBXH"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>分中心：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="subCenter"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZX_FZX" id="ZX_FZX" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZX_DWMC"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊人员类别：</td>
		<td colspan="3">	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TSRYLB"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZX_RYLB" id="ZX_RYLB" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>危病种编码：</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="WBZBM"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZX_WBZBM" id="ZX_WBZBM" >
			</eve:eveselect>
		</td>
		<td class="tab_width">银行户名：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="ZX_YHHM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">银行账户：</td>
		<td>
			<input type="text" class="tab_text validate[]" 
			name="ZX_YHZH"  style="width: 180px;"/>
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color ">*</font>起始时间：</td>
		<td>
			<input type="text" id="ZX_QSNY" name="ZX_QSNY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZX_ZZNY\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>截止时间：</td>
		<td colspan="3">
			<input type="text" id="ZX_ZZNY" name="ZX_ZZNY" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'ZX_QSNY\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>备注：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZX_BZ"  style="width:50%;"/>
		</td>	
	</tr>	
</table>

 






	

	
			
		
