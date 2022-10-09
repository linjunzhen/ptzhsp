<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!-- 人员信息变更弹窗界面 -->
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
		$('#ryxxbgcxSelectorGrid').datagrid({
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
		var rows = $("#ryxxbgcxSelectorGrid").datagrid("getChecked");
		var ryjbxxInfos=$("#ryxxbgcxSelectorGrid").datagrid("getRows");
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
			rows[0].RYYBXX_GRBH=rows[0].aac001;
			rows[0].RYYBXX_SHBZH=rows[0].aac002;
			rows[0].RYYBXX_XM=rows[0].aac003;
			rows[0].RYYBXX_XB=rows[0].aac004;
			rows[0].RYYBXX_ZJLX=rows[0].aac058;
			rows[0].RYYBXX_ZJHM=rows[0].aac147;
			rows[0].RYYBXX_DACSRQ=rows[0].aac006;
			rows[0].RYYBXX_CJGZRQ=rows[0].aac007;
			rows[0].RYYBXX_MZ=rows[0].aac005;
			//rows[0].RYYBXX_LSJFZHBZ=rows[0].aae140;
			rows[0].RYYBXX_ZGGW=rows[0].bac180;
			rows[0].RYYBXX_HYZK=rows[0].aac017;

			//rows[0].RYYBXX_ZYJSZWXLMC=rows[0].;
			rows[0].RYYBXX_ZYJSZWDJ=rows[0].aac014;
			//rows[0].RYYBXX_GJZYZGDJ=rows[0].aac003;
			rows[0].RYYBXX_HKXZ=rows[0].aac009;
			//rows[0].RYYBXX_NMGBS=rows[0].aac058;
			rows[0].RYYBXX_XL=rows[0].aac011;
			//rows[0].RYYBXX_HKSZDQXJX=rows[0].aac006;
			rows[0].RYYBXX_HKSZDZ=rows[0].aac010;
			//rows[0].RYYBXX_RYBZ=rows[0].aac005;			

			//rows[0].JZDZ_JZDSSS=rows[0].aac001;
			rows[0].JZDZ_JZDSSCS=rows[0].cityid;
			//rows[0].JZDZ_JZDSSQX=rows[0].aac003;
			//rows[0].JZDZ_JZDSSJD=rows[0].aac004;
			//rows[0].JZDZ_JZDSSSQ=rows[0].aac058;
			rows[0].JZDZ_CBRLXDH=rows[0].aae005;
			rows[0].JZDZ_JZDYZBM=rows[0].aae007;
			rows[0].JZDZ_JZDLXDZ=rows[0].aae006;
			//rows[0].RYYBXX_RYBZ=rows[0].aac005;
				
			rows[0].DWBH=rows[0].aab001;//单位编码
			rows[0].BLRSZJG=rows[0].aab034;//机构码
			art.dialog.data("ryjbxxInfo", rows);// 存储数据
			art.dialog.data("ryjbxxInfos", ryjbxxInfos);// 存储所有数据
			AppUtil.closeLayer();
		}
		
	}
	
	//查询
	function search(){
	    var count=1;
	    var aac001 = $("input[name='aac001']").val();//个人编号
		var aac002=$("input[name='aac002']").val(); //社会保障码
	    if( typeof(aac001)!= "undefined"||typeof(aac002)!= "undefined"){
	    	AppUtil.gridDoSearch('sbgrxxbgcxToolbar','ryxxbgcxSelectorGrid');
		    $('#ryxxbgcxSelectorGrid').datagrid({
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
	function commonFormat(val,row){
		var typeCode="";
		if("性别"==this.title){
			typeCode="sex";
		}else if("单位管理类型"==this.title){
			typeCode="SBDWLX";
		}else if("险种类型"==this.title){
			typeCode="SBXZLX";
		}else if("证件类型"==this.title){
			typeCode="zjlx";
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
		var rtn = "";
		$.each(map, function(idx, dic) {
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
			<div id="sbgrxxbgcxToolbar">
				<form action="#" name="sbgrxxbgcxForm" id="sbgrxxbgcxForm">
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
									onclick="AppUtil.gridSearchReset('sbgrxxbgcxForm');" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="ryxxbgcxSelectorGrid" fitColumns="true" toolbar="#sbgrxxbgcxToolbar"
				method="post"  checkOnSelect="false"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="sbQyzgxxbgController.do?findPersonInsureInfo">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'aac001',align:'left'" width="80">人员编号</th>
						<th data-options="field:'aac002',align:'left'" width="100">社会保障号</th>
						<th data-options="field:'aac003',align:'left'" width="50">姓名</th>
						<th data-options="field:'aac004',align:'left',title:'sex',formatter:commonFormat" width="40" >性别</th>
						<th data-options="field:'aab999',align:'left'" width="80">单位管理码</th>
						<th data-options="field:'aab004',align:'left'" width="100">单位名称</th>
<!-- 						<th data-options="field:'',align:'left'" width="100">养老参保</th>
						<th data-options="field:'',align:'left'" width="80">养老缴费</th>
						<th data-options="field:'',align:'left'" width="100">工伤参保</th>
						<th data-options="field:'',align:'left'" width="100">工伤缴费</th> -->
						<th data-options="field:'aab019',align:'left',title:'SBDWLX',formatter:commonFormat" width="100">单位管理类型</th>
						<th data-options="field:'aac058',align:'left',title:'zjlx',formatter:commonFormat" width="100">证件类型</th>
						<th data-options="field:'aac147',align:'left'" width="100">证件号码</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>

