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
    <input type="hidden" name="DWDAH" />
    <input type="hidden" name="DWBH" />
	<tr>
		<th colspan="6">单位信息</th>
	</tr>
	<tr style="height:38px;">		
		<td class="tab_width" style="text-align:center">操作：</td>
		<td colspan="5">
			<input type="button" class="eflowbutton" onclick="departQuery();" value="单位信息查询"/>
		</td>
	</tr>
	<tr>		
		<td class="tab_width"  style="text-align:center"><font class="tab_color">*</font>单位保险号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="DWBXH" readonly="readonly"/>
		</td>
		<td class="tab_width"  style="text-align:center"> 单位名称：</td>
		<td>
			<input type="text" class="tab_text" name="DWMC" readonly="readonly"/>
			<input type="hidden" name="DWBH"/>
		</td>
		<td class="tab_width"  style="text-align:center"><font class="tab_color">人数：</font></td>
		<td>
			<input type="text" style="color:red;" class="tab_text" 
				name="DWRS" eadonly="readonly"/>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th>参保人信息</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" style="width:100%">
	<tr style="height:38px;">
		<td id="cbrBtn" style="text-align:center">
			<input type="button" class="eflowbutton"  id="cbrQueryBtn"
				onclick="cbrQuery();" value="查询参保人"/>
			<input type="button" class="eflowbutton"  id="cbrCheckBtn"
				onclick="cbrCheck();" value="校验"/>
			<input type="button" class="eflowbutton"  id="cbrSelectBtn"
				onclick="dealSelect();" value="勾选可处理"/>
			<input type="button" class="eflowbutton"  id="cbrDelBtn"
				onclick="cbrDel();" value="删除"/>
			<input type="button" class="eflowbutton"  id="cbrClearBtn"
				onclick="clean();" value="清空"/>
		</td>
		<td>
			参保开始日期：<input type="text" name="INSURED_START_TIME" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" 
				class="tab_text Wdate validate[]" style="width:180px" onblur="change()" />
			参保身份：<eve:eveselect clazz="tab_text" dataParams="insuredIdentity" id="INSURED_IDENTITY"
				dataInterface="dictionaryService.findDatasForSelect" name="INSURED_IDENTITY"
				defaultEmptyText="选择参保身份">
				</eve:eveselect>
			工资：<input type="text" class="tab_text validate[[],custom[integerplus]]" name="WAGES" 
			    onblur="change()" />
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			    id="cbrxxGrid" fitcolumns="true" nowrap="false"
			    method="post" checkonselect="true" selectoncheck="true" fit="true" border="false"
				data-options="autoSave:true,method:'post',onClickRow:onClickRow,url:'ybLhjyController.do?cbrxxJson&ywId=${ywId}&exeId=${exeId}',onBeforeLoad:getComboboxData">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'isPush',align:'center',width:'10%'">是否推送</th>
						<th data-options="field:'bae619',align:'center',width:'10%'">校验标志</th>
						<th data-options="field:'bae620',align:'center',width:'15%'">不通过原因</th>		
						<th data-options="field:'aac002',align:'center',width:'15%'">公民身份号码</th>
						<th data-options="field:'aac003',align:'center',width:'10%'">姓名</th>			
						<th data-options="field:'oldaab001',align:'center',width:'10%'">原单位编号</th>
						<th data-options="field:'oldaae030',align:'center',width:'10%'">原参保开始日期</th>
						<th data-options="field:'oldaac066',align:'center',width:'10%'">原参保身份</th>
						<th data-options="field:'oldaae033',align:'center',width:'10%'">最后账目年月</th>
						<th data-options="field:'oldaae003',align:'center',width:'10%'">最后到账年月</th>			
						<th data-options="field:'oldsyaae033',align:'center',width:'10%'">生育最后账目日期</th>
						<th data-options="field:'oldsyaae003',align:'center',width:'10%'">生育最后到账日期</th>		
					    <th data-options="field:'bae618',align:'center',width:'10%',
							  formatter:function(value,row){ 
						   		if(value==row.bae618){   	
							   	var diclist = sfbjData;
						        for (var i = 0; i < diclist.length; i++) {
							        if (diclist[i].DIC_CODE == value) {
							            return diclist[i].DIC_NAME;
							        }
						    	}
						   	}},
						   	 editor:{
								type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=YESNO&orderType=asc&defaultEmptyText=是否补缴'}
							}
						">是否补缴</th>				
						<th data-options="field:'aae030',align:'center',width:'15%',
							 editor:{
								type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
							}
						">参保开始日期</th>	
						
						<th data-options="field:'syksrq',align:'center',width:'15%',
							 editor:{
								type:'datebox',options:{required:true,editable:false,formatter:beginformatter,parser:parseDate}
							}
						">生育参保开始日期</th>	
									
						<th data-options="field:'aac040',align:'center',width:'15%',
							 editor:{
								type:'validatebox',options:{required:true,validType:'length[1,200]'}
							}
						">工资</th>				
						<th data-options="field:'aac066',width:'24%',align:'center',
						    formatter:function(value,row){ 
						   		if(value==row.aac066){   	
							   	var diclist = cbrData;
						        for (var i = 0; i < diclist.length; i++) {
							        if (diclist[i].DIC_CODE == value) {
							            return diclist[i].DIC_NAME;
							        }
						    	}
						   	}},
						   	 editor:{
								type:'combobox',options:{required:true,textField:'DIC_NAME',valueField:'DIC_CODE',url:'dictionaryController/loadData.do?typeCode=insuredIdentity&orderType=asc&defaultEmptyText=参保身份'}
							}
					   	">参保身份</th>
						<th data-options="field:'aac004',align:'center',width:'10%',formatter:formatSex">性别</th>
						<th data-options="field:'aac005',align:'center',width:'10%',formatter:formatMz">民族</th>
						<th data-options="field:'bae528',align:'center',width:'10%'">手机号码</th>
						<th data-options="field:'aac006',align:'center',width:'10%'">出生日期</th>
						<th data-options="field:'aac007',align:'center',width:'10%'">首次参加工作时间</th>
						<th data-options="field:'aac009',align:'center',width:'10%',formatter:formatHkxz">户口性质</th>
						<th data-options="field:'aac016',align:'center',width:'10%',formatter:formatJyzt">就业状态</th>
						<th data-options="field:'aac058',align:'center',width:'13%',formatter:formatZjlx">身份证件类型</th>
						<th data-options="field:'aac060',align:'center',width:'10%',formatter:formatSczt">生存状态</th>
						<th data-options="field:'aae007',align:'center',width:'10%'">邮政编码</th>
						<th data-options="field:'aae159',align:'center',width:'10%'">联系电子邮箱</th>
						<th data-options="field:'aac020',align:'center',width:'10%'">职务</th>
						<th data-options="field:'bac503',align:'center',width:'10%'">个人保险号</th>
						<th data-options="field:'aae013',align:'center',width:'10%'">备注</th>
						<th data-options="field:'aab999',align:'center',width:'10%'">单位保险号</th>
						<th data-options="field:'bac524',align:'center',width:'15%'">新参保批次号</th>
					</tr>
				</thead>
			</table>		
		</td>
	</tr>
</table>
<script type="text/javascript">
	$('#cbrxxGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
	
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
	
	//性别格式化
	var sexObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'ybSex'}
	});
	function formatSex(value,row,index){
		var json = sexObj.responseText;
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
	
	//就业状态格式化
	var jyztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'employmentStatus'}
	});
	function formatJyzt(value,row,index){
		var json = jyztObj.responseText;
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
	
	//生存状态格式化
	var scztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'survivalState'}
	});
	function formatSczt(value,row,index){
		var json = scztObj.responseText;
		return dataFormat(value,json);
	}
	
</script>

