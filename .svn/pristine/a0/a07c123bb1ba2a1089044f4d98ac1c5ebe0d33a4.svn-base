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
		<th colspan="6">居民停保减员登记</th>
	</tr>	
	<tr style="height:30px;">		
		<td class="tab_width" colspan="6">
			<input type="button" class="eflowbutton" onclick="showSelectJmInfos();" value="查询居民信息"/>
			<input type="button" class="eflowbutton" onclick="deleteJmInfos();" value="删除"/>
			<input type="button" class="eflowbutton" onclick="clearJmInfos();" value="清空"/>
			<input type="button" class="eflowbutton"  id="pushTbBtn" style="display:none"
				onclick="pushCxjmTb();" value="停保信息推送医保"/> 
		</td>
	</tr>
	<tr>
		<td class="tab_width">选择身份</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="SF" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color ">*</font>中断原因</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ZDYY"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="ZDYY"  >
			</eve:eveselect>
		</td>	
	</tr>				
</table>	
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
	<tr>
		<th colspan="6">居民信息</th>
	</tr>	
</table>
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">		
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false" 
				id="selectPersonInfosGrid" fitColumns="false" 
				method="post"  checkOnSelect="true" selectOnCheck="true"
			    fit="true" border="false" nowrap="false"
				url="ybCxjmcbxbController.do?tbxxJson&ywId=${ywId}&exeId=${exeId}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'bab507',align:'center',hidden:true">社区ID</th>	
						<th data-options="field:'aac001',align:'center',hidden:true">个人编号</th>											
						<th data-options="field:'isPush',align:'center',width:'10%'">是否推送医保系统</th>
						<th data-options="field:'bab506',align:'center'" width="10%">社区名称</th>
						<th data-options="field:'bab505',align:'center'" width="10%">社区编号</th>
						<th data-options="field:'bac503',align:'center'" width="10%">个人保险号</th>
						<th data-options="field:'aac003',align:'center'" width="10%">姓名</th>
						<th data-options="field:'aac002',align:'center'" width="10%">证件号码</th>
						<th data-options="field:'aae030',align:'center'" width="10%">参保开始日期</th>
						<th data-options="field:'aac066',align:'center',formatter:cbsfformat" width="10%">参保身份</th>
						<th data-options="field:'aac004',align:'center',formatter:xbformat" width="10%">性别</th>
						<th data-options="field:'aac005',align:'center',formatter:mzformat" width="10%">民族</th>
						<th data-options="field:'aac006',align:'center'" width="10%">出生日期</th>
						<th data-options="field:'aae005',align:'center'" width="10%">联系电话</th>
						<th data-options="field:'bae528',align:'center'" width="10%">手机号</th>
						<th data-options="field:'aae007',align:'center'" width="10%">邮编</th>
						<th data-options="field:'aae006',align:'center'" width="22%">地址</th>
					</tr>
				</thead>
			</table>	
		</td>
	</tr>
</table>
<script type="text/javascript">
	//空数据横向滚动条
	$('#selectPersonInfosGrid').datagrid({
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
	//参保身份格式化
	var cbsfObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	function cbsfformat(value,row,index){
		var json = cbsfObj.responseText;
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
	function xbformat(value,row,index){
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
	function mzformat(value,row,index){
		var json = mzObj.responseText;
		return dataFormat(value,json);
	}	
</script>
