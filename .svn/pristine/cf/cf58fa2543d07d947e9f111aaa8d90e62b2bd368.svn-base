<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 人员基本和参保信息查询弹窗界面 -->
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/sbqlc/qyzgzjy/js/qyzgzjy.js"></script>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	
	$(function (){
		//空数据，横向滚动
		$('#ryjbxxcxSelectorGrid').datagrid({
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
		var rows = $("#ryjbxxcxSelectorGrid").datagrid("getChecked");
		var dwjbxxInfos=$("#ryjbxxcxSelectorGrid").datagrid("getRows");
    	if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			rows[0].QYJY_GRBH=rows[0].aac001;
			rows[0].QYJY_SHBZHM=rows[0].aac002;
			rows[0].QYJY_XM=rows[0].aac003;
			rows[0].QYJY_XB=rows[0].aac004;
			rows[0].QYJY_CSRQ=rows[0].aac006;
			rows[0].QYJY_GZRQ=rows[0].aac007;
			rows[0].QYJY_DWGLM=rows[0].aab999;
			rows[0].QYJY_DWMC=rows[0].aab004;
			rows[0].QYJY_DWLX=rows[0].aab019;
			rows[0].QYJY_XZLX=rows[0].aae140;
			rows[0].QYJY_DWBH=rows[0].aab001;
			rows[0].QYJY_SCCBLX=rows[0].aac049;
			rows[0].QYJY_JFZT=rows[0].baeaf1;
			rows[0].QYCB_JBJGDM=rows[0].aab034;
			art.dialog.data("dwjbxxInfo", rows);// 存储数据
			art.dialog.data("dwjbxxInfos", dwjbxxInfos);// 存储所有数据
			AppUtil.closeLayer();
		}
		
	}
	
	//查询
	function search(){
	    var count=1;
	    var aac001 = $("input[name='aac001']").val();//单位编号
		var aac002=$("input[name='aac002']").val(); //社会保障码
	    if((aac001!=null && aac001!=undefined && aac001!="")||
			(aac002!=null && aac002!=undefined && aac002!="")){
	    	AppUtil.gridDoSearch('ryjbxxcxSelectorToolbar','ryjbxxcxSelectorGrid');
		    $('#ryjbxxcxSelectorGrid').datagrid({
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
				content : "请先输入完整的个人编号或者社会保障码！",
				icon : "warning",
				ok : true
			 });
	    }
	}


	//数据格式化
	function commonFormat(val,row){
		var typeCode="";
		if("性别"==this.title){
			typeCode="sex";
		}else if("单位类型"==this.title){
			typeCode="SBDWLX";
		}else if("险种类型"==this.title){
			typeCode="SBXZLX";
		}else if("缴费状态"==this.title){
			typeCode="SBJBZT";
		}
		var map = getDicNameByTypeCode(typeCode);
		return dataFormatByMap(val,map);
	}
	function getDicNameByTypeCode(typeCode){
		var datas;
		$.ajax({
			url: "dictionaryController.do?load",
			type: "POST",
			data:{typeCode:typeCode},
			dataType: "json",
			async: false,
			success: function(data){
				datas= data;
			},
			error:function(data){
				datas= data;
			}
		});
		return datas;
	}
	//数据格式化
	function dataFormatByMap(value,map){
		var rtn = value;
		$.each(map, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="ryjbxxcxSelectorToolbar">
				<form action="#" name="ryjbxxcxSelectorForm" id="ryjbxxcxSelectorForm">
					<!--====================开始编写隐藏域============== -->
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<tr style="height:28px;">
							<td style="width:100px;text-align:right;"><font class="tab_color">*</font>个人编号：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="aac001" value='${aac001}' />
							</td>
							<td style="width:100px;text-align:right;">社会保障码：</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" style="width:186px;" name="aac002" value="${aac002}" />
							</td>
							<td>
								<input type="button" value="查询" class="eve-button"
								onclick="search();" />
								<input type="button" value="重置" class="eve-button"
									onclick="AppUtil.gridSearchReset('ryjbxxcxSelectorForm');" />
							</td>
						</tr>						
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table  rownumbers="true" pagination="false"
				id="ryjbxxcxSelectorGrid" fitColumns="true" toolbar="#ryjbxxcxSelectorToolbar"
				method="post"  checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="sbQyzjyController.do?findPersonInsureInfo">
				<thead>
					<tr>
						<th field="ck" checkbox="true" ></th>						
						<th data-options="field:'aac001',align:'center'" width="10%">个人编号</th>
						<th data-options="field:'aac002',align:'center'" width="10%">社会保障码</th>
						<th data-options="field:'aac003',align:'center'" width="8%">姓名</th>
						<th data-options="field:'aac004',align:'center',title:'sex',formatter:commonFormat"  width="8%">性别</th>
						<th data-options="field:'aac006',align:'center'" width="8%">出生日期</th>
						<th data-options="field:'aac007',align:'center'" width="8%">参加工作日期</th>
						<th data-options="field:'aab999',align:'center'" width="10%">单位管理码</th>
						<th data-options="field:'aab001',align:'center'" width="10%">单位编号</th>
						<th data-options="field:'aab004',align:'center'" width="10%">单位名称</th>
						<th data-options="field:'aab019',align:'center',title:'SBDWLX',formatter:commonFormat" width="10%">单位类型</th>
						<th data-options="field:'aae140',align:'center',title:'SBXZLX',formatter:commonFormat" width="10%">险种类型</th>
						<th data-options="field:'aac049',align:'center'" width="10%"  hidden="hidden">首次参保年月</th>
<%--					<th data-options="field:'JBXX_BZ',align:'center'" width="10%" hidden="hidden" >本次参保日期</th>--%>
						<th data-options="field:'aac031',align:'center',title:'SBJBZT',formatter:commonFormat" width="10%" >缴费状态</th>
						<th data-options="field:'aab034',align:'center'" width="10%" >社保机构代码</th>
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

