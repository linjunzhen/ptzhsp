<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 单位花名册查询弹窗界面 -->
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	//查询
	function search(){
	    var count=1;
	    AppUtil.gridDoSearch('dwhmccxSelectorToolbar','dwhmccxSelectorGrid');
		$('#dwhmccxSelectorGrid').datagrid({
    		onLoadSuccess:function(data){
   		        //确保数据初始化后只执行一次该方法
    			if((count=='1')&& data.total==0){
	    		  	 art.dialog({
						content : "无匹配数据返回，查询记录为空。",
						icon : "warning",
						ok : true
					 });
					count ++;	
	    		}  
    		}
    	}); 
	}
		
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
	function formatSex(value){
		var json = sexObj.responseText;
		return dataFormat(value,json);
	}
	//分中心格式化
	var subCenterObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'subCenter'}
	});
	function formatSubCenter(value){
		var json = subCenterObj.responseText;
		return dataFormat(value,json);
	}
	//证件类型格式化
	var idcardObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'idCardType'}
	});
	function formatCardType(value){
		var json = idcardObj.responseText;
		return dataFormat(value,json);
	}
	//参保身份格式化
	var insuredIdentityObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'insuredIdentity'}
	});
	//单位类别格式化
	function formatInsuredIdentity(value){
		var json = insuredIdentityObj.responseText;
		return dataFormat(value,json);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="dwhmccxSelectorToolbar">
				<form action="#" name="dwhmccxSelectorForm" id="dwhmccxSelectorForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">姓名：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:128px;" name="aac003" />
							</td>
							<td style="width:120px;text-align:right;">身份证件类型：</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text" dataParams="idCardType"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择" name="aac058">
								</eve:eveselect>
							</td>
							<td style="width:120px;text-align:right;">公民身份号码：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:128px;" name="aac002" />
							</td>
							<td style="width:120px;text-align:right;">参保身份：</td>
							<td style="width:135px;">
								<eve:eveselect clazz="tab_text" dataParams="insuredIdentity"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择" name="aac066">
								</eve:eveselect>
							</td>
						</tr>
						<tr style="height:28px;">
							<td colspan="7" ></td>
							<td align="left">
								<input type="button" value="查询" class="eve-button"
									onclick="search();" />
								<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('dwhmccxSelectorForm');" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="dwhmccxSelectorGrid" fitColumns="true" toolbar="#dwhmccxSelectorToolbar"
				method="post"  checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="zgjbylbxController.do?findDWHMCInfo&aab001=${unitNo}">
				<thead>
					<tr>
						<th data-options="field:'aab999',align:'left'" width="10%">单位保险号</th>
						<th data-options="field:'aab004',align:'left'" width="12%">单位名称</th>
						<th data-options="field:'aac003',align:'left'" width="10%">姓名</th>
						<th data-options="field:'aac058',align:'left',formatter:formatCardType" width="15%">身份证件类型</th>
						<th data-options="field:'aac002',align:'left'" width="10%">公民身份号码</th>
						<th data-options="field:'aac066',align:'left',formatter:formatInsuredIdentity" width="10%">参保身份</th>
						<th data-options="field:'aac004',align:'left',formatter:formatSex" width="10%">性别</th>
						<th data-options="field:'aac005',align:'left'" width="10%">民族</th>
						<th data-options="field:'orgcode',align:'left',formatter:formatSubCenter" width="11%">分中心</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="取消" type="button" onclick="AppUtil.closeLayer();" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
	</div>
</body>

