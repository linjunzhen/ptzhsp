<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 单位基本信息查询弹窗界面 -->
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/sbqlc/qyzgbgdj/js/qyzgbgdj.js"></script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	
	$(function (){
		//空数据，横向滚动
		$('#dwjbxxcxSelectorGrid').datagrid({
			onLoadSuccess: function(data){
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});	
	});
		
		
    //确定
	function doSelectRows(){
		var rows = $("#dwjbxxcxSelectorGrid").datagrid("getChecked");
    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rows.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			art.dialog.data("dwjbxxInfo", rows);// 存储数据
			AppUtil.closeLayer();
		}
		
	}
	
	//查询
	function search(){
	    var count=1;
	    var dwglm = $("input[name='aab999']").val();//单位管理码
	    if( ""!=dwglm && "undefined"!=dwglm && null!=dwglm){
	    	AppUtil.gridDoSearch('dwjbxxcxSelectorToolbar','dwjbxxcxSelectorGrid');
		    $('#dwjbxxcxSelectorGrid').datagrid({
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
	    }else{
	    	art.dialog({
				content : "请先输入完整的单位管理码！",
				icon : "warning",
				ok : true
			 });
	    }
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
	//单位状态格式化
	var dwztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBDWZT'}
	});
	function formatDwzt(value){
		var json = dwztObj.responseText;
		return dataFormat(value,json);
	}
	//证照类型格式化
	var zzlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBZZLX'}
	});
	function formatZzlx(value){
		var json = zzlxObj.responseText;
		return dataFormat(value,json);
	}
	//行业风险类别格式化
	var fxlbObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBHYFXLB'}
	});
	function formatFxlb(value){
		var json = fxlbObj.responseText;
		return dataFormat(value,json);
	}
	
	//单位类型格式化
	var dwlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBDWLX'}
	});
	function formatDwlx(value){
		var json = dwlxObj.responseText;
		return dataFormat(value,json);
	}
	
	//隶属关系格式化
	var lsgxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBLSGX'}
	});
	function formatLsgx(value){
		var json = lsgxObj.responseText;
		return dataFormat(value,json);
	}
	
	//单位管理类型格式化	
	var gllxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBDWGLLX'}
	});
	function formatGllx(value){
		var json = gllxObj.responseText;
		return dataFormat(value,json);
	}
	
	//经济类型格式化
	var jjlxObj = $.ajax({
       method:'post',
       url:'dictionaryController.do?load',
       async:false,
       dataType:'json',
       data:{typeCode:'SBJJLX'}
	});
	function formatJjlx(value){
		var json = jjlxObj.responseText;
		return dataFormat(value,json);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="dwjbxxcxSelectorToolbar">
				<form action="#" name="dwjbxxcxSelectorForm" id="dwjbxxcxSelectorForm">
					<!--====================开始编写隐藏域============== -->
					<input name='queryExtraFlag' type='hidden' value="1">
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:100px;text-align:right;"><font class="tab_color">*</font>单位管理码：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="aab999" value='${dwglm}' />
							</td>
							<td style="width:100px;text-align:right;">证照号码：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="aab003" />
							</td>
							<td style="width:100px;text-align:right;">单位名称：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="aab004" />
							</td>
							<td>
								<input type="button" value="查询" class="eve-button"
								onclick="search();" />
								<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('dwjbxxcxSelectorForm');" />
							</td>
						</tr>						
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table  rownumbers="true" pagination="false"
				id="dwjbxxcxSelectorGrid" fitColumns="true" toolbar="#dwjbxxcxSelectorToolbar"
				method="post"  checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="sbQyzgbgdjController.do?findDWJBXX">
				<thead>
					<tr>
						<th field="ck" checkbox="true" ></th>						
						<th data-options="field:'JBXX_DWGLM',align:'center'" width="10%">单位管理码</th>
						<th data-options="field:'JBXX_DWMC',align:'center'" width="10%">单位名称</th>
						<th data-options="field:'SWJGXX_SH',align:'center'" width="8%">税号</th>
						<th data-options="field:'JBXX_DWZT',align:'center',formatter:formatDwzt" width="8%">单位状态</th>
						<th data-options="field:'GJXX_ZZLX',align:'center',formatter:formatZzlx" width="8%">证照类型</th>
						<th data-options="field:'GJXX_ZZHM',align:'center'" width="8%">证照号码</th>
						<th data-options="field:'GJXX_FXLB',align:'center',formatter:formatFxlb" width="10%">行业风险类别</th>
						<th data-options="field:'GJXX_DWLX',align:'center',formatter:formatDwlx" width="10%">单位类型</th>
						<th data-options="field:'GJXX_LSGX',align:'center',formatter:formatLsgx" width="10%">隶属关系</th>
						<th data-options="field:'GJXX_DWGLLX',align:'center',formatter:formatGllx" width="10%">单位管理类型</th>
						<th data-options="field:'GJXX_JQLX',align:'center',formatter:formatJjlx" width="10%" >经济类型</th>
						<th data-options="field:'JBXX_BZ',align:'center'" width="10%" >备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div data-options="region:'south',split:true,border:false" >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="取消" type="button" onclick="AppUtil.closeLayer();" 
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />			
			</div>
		</div>
	</div>
</body>

