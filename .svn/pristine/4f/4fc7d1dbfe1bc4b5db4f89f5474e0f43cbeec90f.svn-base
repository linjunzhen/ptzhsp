<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.yb.service.YbCommonService"%>
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
<script type="text/javascript">
	//数据格式化
	function dataFormat(value,json){
		var data = JSON.parse(json);
		var rtn = "";
		$.each(data, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
	//原参保身份格式化
	var cbsfObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	function formatCbsf(value,row,index){
		var json = cbsfObj.responseText;
		return dataFormat(value,json);
	}	
	//证件类型格式化
	var zjlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'idCardType'}
	});
	function formatZjlx(value,row,index){
		var json = zjlxObj.responseText;
		return dataFormat(value,json);
	}	
	//性别格式化	
	var xbObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybSex'}
	});
	function formatSex(value,row,index){
		var json = xbObj.responseText;
		return dataFormat(value,json);
	}
	//民族格式化	
	var mzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybNation'}
	});
	function formatMz(value,row,index){
		var json = mzObj.responseText;
		return dataFormat(value,json);
	}	
	//婚姻状况格式化
	var hyzkObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'maritalStatus'}
	});
	function formatHyzk(value,row,index){
		var json = hyzkObj.responseText;
		return dataFormat(value,json);
	}	
	//户口性质格式化
	var hkxzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'populateNature'}
	});
	function formatHkxz(value,row,index){
		var json = hkxzObj.responseText;
		return dataFormat(value,json);
	}

</script>
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
	

<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
	<tr>
		<th colspan="6">单位信息</th>
	</tr>				
	<tr style="height:38px;">
		<td class="tab_width" colspan="6">
			<input type="button" class="eflowbutton" onclick="showSelectDdPersons();" value="查询调动人员"/>
			<input type="button" class="eflowbutton" onclick="showSelectSqOrSchool();" value="查询社区或学校"/>
			<!-- <input type="button" class="eflowbutton" onclick="cbrCheck();" value="校验"/> -->
			<input type="button" class="eflowbutton" onclick="dealSelect();" value="勾选可处理"/>
			<input type="button" class="eflowbutton" onclick="cbrDel();" value="删除"/>
			<input type="button" class="eflowbutton"  id="pushXbBtn" style="display:none"
				onclick="pushCxjmXb();" value="续保信息推送医保"/>
		</td>
	</tr>		
	<tr style="height:35px;">					
		<td class="tab_width"><font class="tab_color ">*</font>居民单位编号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" 
			name="XB_DWBH"  readonly="readonly"/>
		</td>
		<td class="tab_width">居民单位名称：</td>
		<td >	
			<input type="text" class="tab_text validate[]" 
			name="XB_DWMC" readonly="readonly" />
		</td>
		<td class="tab_width">居民单位类别：</td>
		<td >	
			<eve:eveselect clazz="tab_text validate[]" dataParams="UnitTypeOfJm"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="XB_DWLB" >
			</eve:eveselect>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">	
	<tr>
		<th colspan="6">参保人信息</th>
	</tr>			
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false" 
			id="xbConfGrid" fitcolumns="true" checkonselect="true" nowrap="false"
			selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',onClickRow:onClickRow1,url:'ybCxjmcbxbController.do?getCbrxx&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData"
			>
			<thead>
				<tr>
				   <th data-options="field:'ck',checkbox:true"></th>
				  <!--  <th data-options="field:'JY_FLAG',width:'10%',align:'center'">校验标志</th>
				   <th data-options="field:'REASON',width:'10%',align:'center'">不通过原因</th> -->
				   <th data-options="field:'isPush',align:'center',width:'10%'">是否推送医保系统</th>
				  <!--  <th data-options="field:'bac524',width:'10%',align:'center',
				   		editor:{type:'validatebox',options:{required:true}}
				   ">新参保批次号</th> -->	
				   <th data-options="field:'bac524',width:'10%',align:'center'">新参保批次号</th>		   
				   <th data-options="field:'oldaab001',width:'10%',align:'center'">原单位编号</th>
				   <th data-options="field:'oldaab004',width:'10%',align:'center'">原参保单位</th>
				   <th data-options="field:'oldaac066',width:'10%',align:'center',formatter:formatCbsf">原参保身份</th>			
				   <th data-options="field:'aab999',width:'10%',align:'center'">原单位保险号</th>
				   <th data-options="field:'aac058',width:'10%',align:'center',formatter:formatZjlx">身份证件类型</th>
				   <th data-options="field:'aac002',width:'10%',align:'center'">公民身份号码</th>
				   <th data-options="field:'aac003',width:'10%',align:'center'">姓名</th>
				   <th data-options="field:'aac004',width:'10%',align:'center',formatter:formatSex">性别</th>
				   <th data-options="field:'aac005',width:'10%',align:'center',formatter:formatMz">民族</th>
				   <th data-options="field:'aac006',width:'10%',align:'center'">出生日期</th>
				   <th data-options="field:'aac066',width:'24%',align:'center',
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
							type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=insuredIdentity&orderType=asc'}
						}
					">参保身份
					</th>
				   <th data-options="field:'aac009',width:'10%',align:'center',formatter:formatHkxz">户口性质</th>
				   <th data-options="field:'aae004',width:'10%',align:'center'">联系人姓名</th>
				   <th data-options="field:'aae005',width:'10%',align:'center'">联系人电话</th>
				   <th data-options="field:'bae528',width:'10%',align:'center'">手机号码</th>
				   <th data-options="field:'aae006',width:'10%',align:'center'">地址</th>
				   <th data-options="field:'aae007',width:'10%',align:'center'">邮政编码</th>				
				   <th data-options="field:'aae030',width:'23%',align:'center',
					     editor:{
							type:'datebox',options:{required:true,editable:false,formatter:timeformatter,parser:parseDate}
						}
					">参保开始日期
				   </th>
		   <!--   <th data-options="field:'JZRQ',width:'10%',align:'center'">最后建账日期</th>
				  <th data-options="field:'DZRQ',width:'10%',align:'center'">最后到账日期</th>
				  <th data-options="field:'SALARY',width:'10%',align:'center'">工资</th>
				  <th data-options="field:'CBD',width:'10%',align:'center'">最后医保参保地</th>
				  <th data-options="field:'QSNY',width:'10%',align:'center'">最后医保参保地起始年月</th>
				  <th data-options="field:'JZNY',width:'10%',align:'center'">最后医保参保地截止年月</th>
				  <th data-options="field:'EMAIL',width:'10%',align:'center'">联系电子邮箱</th>
				  <th data-options="field:'HYZK',width:'10%',align:'center',formatter:formatHyzk">婚姻状况（医保）</th>
				  <th data-options="field:'ZW',width:'10%',align:'center'">职务</th>
				  <th data-options="field:'REMARK',width:'10%',align:'center'">备注</th>	    --> 
				</tr>
			</thead>
			</table>		
		</td>
	</tr>
</table>

		

