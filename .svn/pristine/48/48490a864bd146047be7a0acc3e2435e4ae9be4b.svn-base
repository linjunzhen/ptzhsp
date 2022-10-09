<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="tab_height"></div>
<div id="DATA_QUERY_2" style="display:none;">
<form action="#" id="familyInfo_form">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="familyInfo">
		<tr>
			<th >查档证明(家庭成员信息)</th>
			<td> <input type="button" class="eflowbutton"  name="addOneFamilyInfo" value="新增" onclick="addFamilyInfo();"> </td>
		</tr>
		<tr id="familyInfo_1">
			<td >
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
						<td ><input type="text" class="tab_text validate[required]" 
							name="qlrmc" maxlength="10"/></td>
						<td><font class="tab_color">*</font>证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
							defaultEmptyText="选择证件类型" name="qlrzjtype" id="qlrzjtype" value="SF">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">证件号码：</td>
						<td>
						  <input type="text" class="tab_text validate[],custom[vidcard]" maxlength="30" id="qlrzjhm" style="float: left;"
							name="qlrzjhm"  />
						</td>
						<td>原有住房信息：</td>
						<td><input type="text" class="tab_text validate[]" 
							name="qlraddr" maxlength="50"/></td>
					</tr>				
				</table>
				<!-- <div class="tab_height2"></div> -->
			</td>
			<td>
				<input type="button" class="eflowbutton" name="deleteCurrentFramilyInfo" value="删除" onclick="deleteFamilyInfo('1');">
			</td>
		</tr>	
	</table>
</form>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
	    <th colspan="2" style="align:right">
	     	（请至少添加一个家庭成员，后点击【查询】）
	     	<a style="padding:8px 50px;line-height: 50px;height:50px;font-weight: bold;margin-left: 50%;" class="readCardButton"
                                        id="bdcBtnQuerySeach"    onclick="searchBdcData();">查询</a>
	     	<a style="padding:8px 50px;line-height: 50px;height:50px;font-weight: bold;display:none;" class="readCardButton"
                                        id="bdcBtnQueryPrint"
                                        onclick="printBdcQuery();">打印查档证明</a>
	    </th>
	</tr>
</table>
<div class="tab_height"></div>
<div id="grid_div" style="margin-left:5px;width:99%;">	
  <table id="EstaeFamilyGrid">
  </table>
</div>
<!-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro1">
  <thead>
    <tr>
        <td class="tab_width1" width="50px">序号</td>
        <td class="tab_width1" width="180px">姓名（身份证）</td>
        <td class="tab_width1" width="80px">产权类型</td>        
        <td class="tab_width1" width="80px">证号</td>        
        <td class="tab_width1" width="300px">不动产坐落</td>
        <td class="tab_width1" width="80px">面积(平方米)</td>
        <td class="tab_width1" width="50px">用途</td>
        <td class="tab_width1" width="80px">房屋性质</td>
        <td class="tab_width1" width="80px">状态</td>
        <td class="tab_width1" width="120px">操作</td>
    </tr>
   </thead>
   <tbody id="result_tb">
    <tr>
    	<td colspan="10"><font color="red">（请至少添加一个家庭成员，后点击【查询】）</font></td>
    </tr>
   </tbody>
 </table> -->
 </div>
 
 <script type="text/javascript">
	
	function printBdcQuery(){
		var rowsData = $("#EstaeFamilyGrid").datagrid('getChecked');
		if(rowsData != null && rowsData.length > 0){
			printBdcQueryCdzm(rowsData[0].OB_ID);
		}else{
			art.dialog({
                content: "未选中数据",
                icon:"warning",
                ok: true
            });
		}
	}
		
	function printBdcQueryCdzm(OB_ID){
	    var exeId="${execution.EXE_ID}";
		var dateStr = "";
		dateStr +="&eveId="+exeId;
		dateStr +="&TemplatePath=bdcqueryzm.doc";
		dateStr +="&TemplateName=家庭首套房查档证明打印";
		dateStr +="&OB_ID="+OB_ID;
		//打印模版
       $.dialog.open(encodeURI("bdcApplyController.do?printBdcQueryTemplate"+dateStr), {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	}
	
	function fLoadTable(){
		$('#EstaeFamilyGrid').datagrid({
			height:150,
			fitColumns: true,
			singleSelect: true,
			rownumbers:true,
			idField:"OB_ID",
			columns:[[
				{field:'ck',checkbox: true},
				{field:'OB_ID',title:'OB_ID',width:80,hidden: true},
				{field:'NAME',title:'姓名',align: 'center',width:50},
				{field:'IDNUM',title:'身份证',align: 'center',width:100},
				{field:'PROPERTY_TYPE',align: 'center',title:'产权类型',width:80},
				{field:'PROPERTY_STATUS',align: 'center',title:'产权状态',width:50},
				{field:'PROPERTY_HUMAN',align: 'center',title:'权力人',width:50},
				{field:'PROPERTY_ADDR',title:'不动产坐落',width:200},
				{field:'PROPERTY_NUM',title:'证号',width:100},
				{field:'PROPERTY_AREA',align: 'center',title:'面积(平方米)',width:60},
				{field:'PROPERTY_USE',align: 'center',title:'用途',width:80},
				{field:'PROPERTY_NATURE',align: 'center',title:'性质',width:80}
			]],
			onLoadSuccess: function (data) {
	            if (data.total == 0) {
	                $(this).datagrid('appendRow', { NAME: '<div style="text-align:center;color:red">（查无记录。）</div>' }).datagrid('mergeCells', { index: 0, field: 'NAME', colspan: 10 });
	            }
	        }
		});	
	};
		
	function initAutoTable(flowSubmitObj){
		if(flowSubmitObj.busRecord.QUERY_TYPE == "2"){
			var familyInfoJson = flowSubmitObj.busRecord.FAMILY_JSON;
			if(null != familyInfoJson && '' != familyInfoJson){
				var familyInfos = JSON.parse(familyInfoJson);
				for(var i=0;i<familyInfos.length;i++){
					if(i==0){
						FlowUtil.initFormFieldValue(familyInfos[i],"familyInfo_1");
					}else{
						addFamilyInfo();
						FlowUtil.initFormFieldValue(familyInfos[i],"familyInfo_"+(i+1));
					}
				}
				$("input[name='deleteCurrentFramilyInfo']").remove();
				$("input[name='addOneFamilyInfo']").remove();
			}
			//初始查询结果
			var resultJsons = flowSubmitObj.busRecord.RESULT_JSON;
			if(null != resultJsons && ''!= resultJsons){
				var list = JSON.parse(resultJsons);
				if(list != null && list.length > 0){
					$('#EstaeFamilyGrid').datagrid('loadData', list);					
				}
			}
		}
	}
</script>
 