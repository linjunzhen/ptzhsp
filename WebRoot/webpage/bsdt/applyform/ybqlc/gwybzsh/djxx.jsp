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

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="cbdj">
	 <input type="hidden" name="GRBH" />
	<tr>
		<th colspan="6">登记信息</th>
	</tr>			
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="ZJHM"  style="width: 180px;" />
		   <input type="button" value="查询" class="eve-button" onclick="showSelectDjInfos()" /> 
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>姓名：</td>
		<td>	
			<input type="text" class="tab_text validate[required]" maxlength="32"
			name="XM"  style="width:180px;" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>工作状态：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[reuqired]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="GZZT" id="GZZT">
			</eve:eveselect>
		</td>
	</tr>			
	<tr>					
		<td class="tab_width"><font class="tab_color ">*</font>个人保险号：</td>
		<td>
			<input type="text" class="tab_text validate[requireds]" maxlength="64"
			name="GRBXH" style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="DWBXH" style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>分中心：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="subCenter"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="FZX" id="FZX" >
			</eve:eveselect>
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" maxlength="64"
			name="DWMC"  style="width: 180px;"/>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>特殊人员类别：</td>
		<td colspan="3">	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TSRYLB"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="TSRYLB" id="TSRYLB" >
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>危病种编码：</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[required]" dataParams="WBZBM"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="WBZBM" id="WBZBM" >
			</eve:eveselect>
		</td>
		<td class="tab_width">银行户名：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YHHM"  style="width: 180px;"/>
		</td>
		<td class="tab_width">银行账户：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="YHZH"  style="width: 180px;"/>
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color ">*</font>起始时间：</td>
		<td>
			<input type="text" id="QSSJ" name="QSSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'JZSJ\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>截止时间：</td>
		<td colspan="3">
			<input type="text" id="JZSJ" name="JZSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'QSSJ\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:180px" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="5">
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="REMARK"  style="width:50%;"/>
		</td>	
	</tr>	
</table>	

	

