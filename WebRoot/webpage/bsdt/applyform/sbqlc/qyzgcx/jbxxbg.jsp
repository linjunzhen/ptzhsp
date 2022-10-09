<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);	
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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<input name="PUSH_FLAG" type="hidden"/>
	<tr>
		<th colspan="6">人员一般信息</th>
	</tr>
	<tr>
		<td class="tab_width">个人编码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="JBXX_GRBM" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" ></a>			
		</td>		
		<td class="tab_width">社会保障码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_SHBZM" />
		</td>
		<td class="tab_width"> 姓名：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_XM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="sex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_XB">
			</eve:eveselect>
		</td>		
		<td class="tab_width">证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="zjlx"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width">证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_ZJHM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">档案出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_DACSRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>		
		<td class="tab_width">参加工作日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_CJGZRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>	
		<td class="tab_width">民族：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_MZ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">临时缴费账户标志：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_LSJFZHBZ">
			</eve:eveselect>
		</td>		
		<td class="tab_width">职工岗位：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_ZGGW">
			</eve:eveselect>
		</td>
		<td class="tab_width">婚姻状况：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_HYZK">
			</eve:eveselect>
		</td>		
	</tr>	
	<tr>
		<td class="tab_width">专业技术职务系列名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_ZYJSZWXLMC" />
		</td>
		<td class="tab_width">专业技术职务等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_ZYJSZWDJ">
			</eve:eveselect>
		</td>	
		<td class="tab_width">国家职业资格等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_GJZYZGDJ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">户口性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_HKXZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">农民工标识：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_NMGBS">
			</eve:eveselect>
		</td>
		<td class="tab_width">学历(文化程度)：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_XL">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">户口所在地区县街乡：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_HLSZDQXJX">
			</eve:eveselect>
		</td>
		<td class="tab_width">户口所在地址：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[required]" name="JBXX_HKSZDZ" style="width:500px"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">人员备注：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]"  name="JBXX_RYBZ" style="width:600px"/> 
		</td>
	</tr>	
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">居住地址及联系人相关信息</th>
	</tr>
	<tr>
		 <td class="tab_width">居民地所属省：</td>
         <td>
                 <eve:eveselect clazz="tab_text validate[required]" dataParams="XZQHDM"
                 dataInterface="dicTypeService.findProvince" onchange="changeCity(this.val)"
                 defaultEmptyText="--请选择--" name="QYCB_JZDS" id="QYCB_JZDS">
                 </eve:eveselect>
         </td>
         <td class="tab_width">居民地所属市：</td>
         <td>
                 <eve:eveselect clazz="tab_text validate[required]" dataParams="${busRecord.QYCB_JZDS}" onchange="changeDictinct(this)"
                 dataInterface="dicTypeService.findProvince" id="QYCB_JZDSSS"
                 defaultEmptyText="--请选择--" name="QYCB_JZDSSS">
                 </eve:eveselect>
         </td>
         <td class="tab_width">居民地所属区（县）：</td>
         <td>
                 <eve:eveselect clazz="tab_text validate[required]" dataParams="${busRecord.QYCB_JZDSSS}"
                 dataInterface="dicTypeService.findProvince" id="QYCB_JZDSSQ"
                 defaultEmptyText="--请选择--" name="QYCB_JZDSSQ">
                 </eve:eveselect>
         </td>
 	</tr>
	<tr>
		<td class="tab_width">居住地所属街道(镇)：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_JZDSSZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">居住地所属社区(村)：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_JZDSSC">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>参保人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_CBRLXDH" />
		</td>
	</tr>
	
	<tr>
		<td class="tab_width">居住地(联系)邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_JZDYZBM" />
		</td>
		<td class="tab_width">居住地(联系)地址：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[]" name="JBXX_JZDDZ" style="width:500px"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系人关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBLSGX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_LXRGX">
			</eve:eveselect>
		</td>
		<td class="tab_width">家庭联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_JTLXRXM" />
		</td>
		<td class="tab_width">家庭联系人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_JTLXRLXDH" />
		</td>		
	</tr>
	<tr>
		<td class="tab_width">家庭联系人固定电话：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_JTLXRGDDH" />
		</td>
		<td class="tab_width">家庭联系人移动电话：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_JTLXRYDDH" />
		</td>
	</tr>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">变更信息</th>
	</tr>
	<tr>
		<td class="tab_width">变更日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="JBXX_BGRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width">变更原因：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBLSGX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="JBXX_BGRQ">
			</eve:eveselect>
		</td>
		<td class="tab_width">变更备注：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_BGBZ" />
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
					<th data-options="field:'aae141',width:'23%',align:'center'">险种类型</th>
					<th data-options="field:'aae142',width:'20%',align:'center'">首次参保年月</th>						
				   	<th data-options="field:'aae143',width:'20%',align:'center'">人员参保状态</th>
					<th data-options="field:'aae144',width:'20%',align:'center'">个人缴费状态</th>						
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



