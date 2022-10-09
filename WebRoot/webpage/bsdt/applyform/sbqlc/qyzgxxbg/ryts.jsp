<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">个人参保信息</th>
	</tr>
	<tr>
		<td class="tab_width">个人编码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="JBXX_DWGLM" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" ></a>			
		</td>		
		<td class="tab_width">社会保障码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_DWMC" />
		</td>
		<td class="tab_width"> 姓名：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_DWJC" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>
		<td class="tab_width">出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_FZRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>		
		<td class="tab_width">参加工作日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_FZRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>		
	</tr>
	<tr>
		<td class="tab_width">单位管理码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRXM" />
		</td>	
		<td class="tab_width">单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRXM" />
		</td>		
		<td class="tab_width">单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>	
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">个人退收信息</th>
	</tr>
	<tr>
		<td class="tab_width">退收类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>		
		<td class="tab_width">退收利息类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>	
		<td class="tab_width"> 拨付方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>
	</tr>
	<tr>
		<td class="tab_width">单位开户银行名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRXM" />
		</td>	
		<td class="tab_width">单位银行支行网点：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[required]" name="JBXX_DZ" style="width:500px"/>
		</td>				
	</tr>
	<tr>
		<td class="tab_width">单位银行户名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRXM" />
		</td>	
		<td class="tab_width">单位银行账号：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_DZ" style="width:500px"/>
		</td>	
		<td class="tab_width"> 退收原因：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>					
	</tr>	
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]"  name="BGXX_BZ" style="width:600px"/> 
		</td>
	</tr>		
</table>





<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">参保险种信息</th>
	</tr>
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:150px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="xzConfGrid" fitcolumns="true" checkonselect="true" 
			selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',url:'ybCxjmcbxbController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}'"
			>
			<thead>
				<tr>
					<th data-options="field:'ck',align:'left',checkbox:true"></th>
					<th data-options="field:'aae141',width:'10%',align:'center'">险种类型</th>
					<th data-options="field:'aae142',width:'10%',align:'center'">人员参保状态</th>						
				   	<th data-options="field:'aae143',width:'10%',align:'center'">险种离退休标志</th>
					<th data-options="field:'aae144',width:'10%',align:'center'">开始日期</th>	
					<th data-options="field:'aae142',width:'20%',align:'center'">截止日期</th>						
				   	<th data-options="field:'aae143',width:'10%',align:'center'">用工形式</th>
					<th data-options="field:'aae144',width:'10%',align:'center'">缴费状态</th>	
					<th data-options="field:'aae143',width:'10%',align:'center'">参保身份</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

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
	//险种类型格式化
	var xzlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBXZLX'}
	});
	function formatXzlx(value,row,index){
		var json = xzlxObj.responseText;
		return dataFormat(value,json);
	}
	
	//征收方式格式化
	var zsfsObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBZSFS'}
	});
	function formatZsfs(value,row,index){
		var json = zsfsObj.responseText;
		return dataFormat(value,json);
	}	
	
	//参保状态格式化
	var cbztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBCBZT'}
	});
	function formatCbzt(value,row,index){
		var json = cbztObj.responseText;
		return dataFormat(value,json);
	}
	
	//单位暂停缴费标志格式化
	var ztjfbzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBZTJFBZ'}
	});
	function formatZtjfbz(value,row,index){
		var json = ztjfbzObj.responseText;
		return dataFormat(value,json);
	}
</script>



