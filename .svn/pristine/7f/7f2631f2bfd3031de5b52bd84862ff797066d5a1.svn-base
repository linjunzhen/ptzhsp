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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
	<input name="IFUPDATE" type="hidden" value="0"/>
	<tr>
		<th colspan="7">参保人员停保减员登记</th>
	</tr>
	<tr>
		<td style="width:120px;text-align:center">  
			<input type="button" class="eflowbutton"  id="pushYbBtn" style="display:none"
				onclick="pushZgTb();" value="停保信息推送平潭医保"/> 
		</td>
		<td id="cbrBtn" style="width:300px;text-align:center">  
			<input type="button" class="eflowbutton"  id="cbrQueryBtn"
				onclick="cbrQuery();" value="查询参保人"/>
			<input type="button" class="eflowbutton"  id="cbrDelBtn"
				onclick="cbrDel();" value="删除"/>	
			<input type="button" class="eflowbutton"  id="cbrClearBtn"
				onclick="clean();" value="清空"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>中断原因：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="ZDYY" value="离职" readonly />
		</td>
		<td>
			<font class="tab_color">*</font>变更原因：
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ZDYY"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择变更原因" name="BGYY" value='63'>
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>备注：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="BZ" value="职工停保登记" />
		</td>
		<%-- <td>
			是否存在死亡人员：<eve:eveselect clazz="tab_text" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="选择是否存在死亡人员" name="is_dead_person">
				</eve:eveselect>
		</td> --%>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" style="width:100%">
	<tr>
		<td style="height:400px;">
			<table rownumbers="true" pagination="false" 
				id="cbrxxGrid" fitcolumns="true" checkonselect="true"  selectoncheck="true" fit="true" border="false"
				data-options="autoSave:true,method:'post',url:'zgjbylbxController.do?tbCbrJson&ywId=${ywId}&exeId=${exeId}'">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'isPush',align:'center',width:'20%'">是否推送</th>
						<th data-options="field:'aac001',align:'center'" width="10%" >个人编号</th>
						<th data-options="field:'aab001',align:'center'" width="10%" >单位编号</th>	
						<th data-options="field:'aab999',align:'center'" width="10%" >单位保险号</th>
						<th data-options="field:'aab004',align:'center'" width="15%" >单位名称</th>
						<th data-options="field:'bac503',align:'center'" width="15%" >个人保险号</th>
						<th data-options="field:'aac002',align:'center'" width="15%" >公民身份号码</th>
						<th data-options="field:'aac003',align:'center'" width="10%" >姓名</th>
						<th data-options="field:'aac066',align:'center',formatter:cbsfformat" width="10%">参保身份</th>
						<!-- <th data-options="field:'QFYS',align:'center'" width="70" >欠费月数</th> -->
						<th data-options="field:'aae030',align:'center'" width="10%" >参保开始日期</th>
						<th data-options="field:'zhjzny',align:'center'" width="10%" >最后账目年月</th>
						<th data-options="field:'qyzrs0',align:'center'" width="10%" >企业总人数</th>
						<th data-options="field:'zzrs00',align:'center'" width="10%" >在职总人数</th>
						<th data-options="field:'txrs00',align:'center'" width="10%" >退休总人数</th>
						<!-- <th data-options="field:'YLZZRS',align:'center'" width="10%" >医疗在职人数</th> -->
						<!-- <th data-options="field:'SYZZRS',align:'center'" width="10%" >生育在职人数</th> -->
						<th data-options="field:'aac058',align:'center',formatter:zjlxformat" width="10%" >身份证件类型</th> 
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
	
	//证件类型格式化
	var zjlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'idCardType'}
	});
	function zjlxformat(value,row,index){
		var json = zjlxObj.responseText;
		return dataFormat(value,json);
	}	
</script>
