<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String operType = request.getParameter("operType");
	request.setAttribute("operType", operType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="renderer" content="webkit" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<eve:resources loadres="jquery,layer,artdialog,json2"></eve:resources>
	<!-- jquery validationEngine -->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<!-- AppUtil -->
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<!-- 自定义可编辑表格工具类 -->
	<script type="text/javascript" src="<%=path%>/webpage/website/applyforms/relatedForm/js/jquery.MyEditableTable.js"></script>
	<style type="text/css">
	    .add-btn{background: #29b609 none repeat scroll 0 0;
	    color: #fff;
	    font-size: 14px;padding:3px 10px;}
	    .add-btn a:hover{color: #fff;}
	    .del-btn{background: #c61c00 none repeat scroll 0 0;
        color: #fff;
	    font-size: 14px;padding:3px 10px;}
	</style>
	<script type="text/javascript">
	    $(document).ready(function(){
	    	//表单绑定验证插件
	    	AppUtil.initWindowForm("CCRAPPLY_FORM", function(form, valid){
	    		if(valid){
	    			//组装业户管理负责人json
	    			var leaderJson = $('#leaderTable').MyEditableTable('getData');
	    			$("#CCRAPPLY_FORM").find("input[name='LEADER_JSON']").val(JSON.stringify(leaderJson));
	    			//组装技术负责人json
	    			var techleaderJson = $('#techleaderTable').MyEditableTable('getData');
	    			$("#CCRAPPLY_FORM").find("input[name='TECHLEADER_JSON']").val(JSON.stringify(techleaderJson));
	    			//组装质量检验人员json
	    			var qapersonJson = $('#qapersonTable').MyEditableTable('getData');
	    			$("#CCRAPPLY_FORM").find("input[name='QAPERSON_JSON']").val(JSON.stringify(qapersonJson));
	    			//组装汽车综合小修业户设备json
	    			var varietySrvDevJson =$('#varietySrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='VARIETYSRVDEVICE_JSON']").val(JSON.stringify(varietySrvDevJson));
	    			//组装汽车快修业户设备json
	    			var quickSrvDevJson =$('#quickSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='QUICKSRVDEVICE_JSON']").val(JSON.stringify(quickSrvDevJson));
	    			//组装发动机维修业户设备json
	    			var engineSrvDevJson =$('#engineSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='ENGINESRVDEVICE_JSON']").val(JSON.stringify(engineSrvDevJson));
	    			//组装车身维修业户设备json
	    			var carBodySrvDevJson =$('#carBodySrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='CARBODYSRVDEVICE_JSON']").val(JSON.stringify(carBodySrvDevJson));
	    			//组装电气系统维修业户设备json
	    			var electricSysSrvDevJson =$('#electricSysSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='ELECTRICSYSSRVDEVICE_JSON']").val(JSON.stringify(electricSysSrvDevJson));
	    			//组装自动变速器维修业户设备json
	    			var variatorSrvDevJson =$('#variatorSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='VARIATORSRVDEVICE_JSON']").val(JSON.stringify(variatorSrvDevJson));
	    			//组装轮胎动平衡和修补业户设备json
	    			var tireSrvDevJson =$('#tireSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='TIRESRVDEVICE_JSON']").val(JSON.stringify(tireSrvDevJson));
	    			//组装四轮定位检测调整业户设备json
	    			var tireLocateSrvDevJson =$('#tireLocateSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='TIRELOCATESRVDEVICE_JSON']").val(JSON.stringify(tireLocateSrvDevJson));
	    			//组装汽车润滑与养护业户设备json
	    			var lubricateSrvDevJson =$('#lubricateSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='LUBRICATESRVDEVICE_JSON']").val(JSON.stringify(lubricateSrvDevJson));
	    			//组装喷油泵和喷油器维修业户设备json
	    			var injectorSrvDevJson =$('#injectorSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='INJECTORSRVDEVICE_JSON']").val(JSON.stringify(injectorSrvDevJson));
	    			//组装电控喷油泵和喷油器维修业户补充设备json
	    			var elecInjectorSrvDevJson =$('#elecInjectorSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='ELECINJECTORSRVDEVICE_JSON']").val(JSON.stringify(elecInjectorSrvDevJson));
	    			//组装曲轴修磨业户设备json
	    			var crankSrvDevJson =$('#crankSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='CRANKSRVDEVICE_JSON']").val(JSON.stringify(crankSrvDevJson));
	    			//组装气缸镗磨业户设备json
	    			var cylinderSrvDevJson =$('#cylinderSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='CYLINDERSRVDEVICE_JSON']").val(JSON.stringify(cylinderSrvDevJson));
	    			//组装散热器维修业户设备json
	    			var radiatorSrvDevJson =$('#radiatorSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='RADIATORSRVDEVICE_JSON']").val(JSON.stringify(radiatorSrvDevJson));
	    			//组装空调维修业户设备json
	    			var airCondSrvDevJson =$('#airCondSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='AIRCONDSRVDEVICE_JSON']").val(JSON.stringify(airCondSrvDevJson));
	    			//组装汽车美容装潢业户设备json
	    			var beautySrvDevJson =$('#beautySrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='BEAUTYSRVDEVICE_JSON']").val(JSON.stringify(beautySrvDevJson));
	    			//组装汽车玻璃安装及修复业户设备json
	    			var glassSrvDevJson =$('#glassSrvDeviceTable').MyEditableTable('getData'); 
	    			$("#CCRAPPLY_FORM").find("input[name='GLASSSRVDEVICE_JSON']").val(JSON.stringify(glassSrvDevJson));
	    			//组装从业人员json
	    			var staffJson = $('#staffInfoTable').MyEditableTable('getData');
	    			$("#CCRAPPLY_FORM").find("input[name='STAFF_JSON']").val(JSON.stringify(staffJson));
	    			
						//序列化表单
						//var formData = $("#CCRAPPLY_FORM").serialize();
						var formData = FlowUtil.getFormEleData("CCRAPPLY_FORM");
						var url = $("#CCRAPPLY_FORM").attr("action");
						//提交表单
		    			AppUtil.ajaxProgress({
							url: url,
							params: formData,
							callback: function(resultJson){
								if(resultJson.success){
									parent.art.dialog({
										content: resultJson.msg,
										icon: 'succeed',
										time: 3,
										ok: true
									});
									art.dialog.data("backFormInfo",{
										formName: '${materForm.formName }',
					    				recordId: resultJson.jsonString
									});
									AppUtil.closeLayer();
								}else{
									art.dialog({
										content: resultJson.msg,
										icon: 'error',
										ok: true
									});
								}
							}
		    			});
	    			
	    		}
	    	});
	    	
	    	//实例化业户管理负责人表格及初始化数据
	    	$('#leaderTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			$(this).wrap("<form id='leaderTableForm'></form>");
	    			var isInputValid = $('#leaderTableForm').validationEngine('validate');
	    			$(this).unwrap();
	    			if(!isInputValid){
	    				return false;
	    			}
	    		},
	    		onAfterAppendRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onAfterDeleteRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onLoadSuccess: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		}
	    	});
	    	//var leaderJson = $("#CCRAPPLY_FORM").find("input[name='LEADER_JSON']").val();
	    	var leaderJson = '${materForm.LEADER_JSON }';
	    	if(leaderJson != '' && leaderJson != '[]'){
	    		$('#leaderTable').MyEditableTable("loadData", JSON.parse(leaderJson));
	    	}
	    	//实例化企业技术负责人表格及初始化数据
	    	$('#techleaderTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			$(this).wrap("<form id='techleaderTableForm'></form>");
	    			var isInputValid = $('#techleaderTableForm').validationEngine('validate');
	    			$(this).unwrap();
	    			if(!isInputValid){
	    				return false;
	    			}
	    		},
	    		onAfterAppendRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onAfterDeleteRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onLoadSuccess: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		}
	    	});
	    	//var techleaderJson = $("#CCRAPPLY_FORM").find("input[name='TECHLEADER_JSON']").val();
	    	var techleaderJson = '${materForm.TECHLEADER_JSON }';
	    	if(techleaderJson != '' && techleaderJson != '[]'){
	    		$('#techleaderTable').MyEditableTable("loadData", JSON.parse(techleaderJson));
	    	}
	    	//实例化质量检验人员表格及初始化数据
	    	$('#qapersonTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			$(this).wrap("<form id='qapersonTableForm'></form>");
	    			var isInputValid = $('#qapersonTableForm').validationEngine('validate');
	    			$(this).unwrap();
	    			if(!isInputValid){
	    				return false;
	    			}
	    		},
	    		onAfterAppendRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onAfterDeleteRow: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		},
	    		onLoadSuccess: function(rowCount){
	    			var $firstRow = $(this).find('tr:first');
	    			$firstRow.find('th:first').attr("rowspan", (rowCount+1));
	    		}
	    	});
	    	//var qapersonJson = $("#CCRAPPLY_FORM").find("input[name='QAPERSON_JSON']").val();
	    	var qapersonJson = '${materForm.QAPERSON_JSON }';
	    	if(qapersonJson != '' && qapersonJson != '[]'){
	    		$('#qapersonTable').MyEditableTable("loadData", JSON.parse(qapersonJson));
	    	}
	    	//实例化汽车综合小修业户设备表格及初始化数据
	    	$('#varietySrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 30){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前30行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 29){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var varietySrvDevJson = $("#CCRAPPLY_FORM").find("input[name='VARIETYSRVDEVICE_JSON']").val();
	    	var varietySrvDevJson = '${materForm.VARIETYSRVDEVICE_JSON }';
	    	if(varietySrvDevJson != '' && varietySrvDevJson != '[]'){
	    		$('#varietySrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(varietySrvDevJson));
	    	}
	    	//实例化汽车快修业户设备表格及初始化数据
	    	$('#quickSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 20){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前20行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 19){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var quickSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='QUICKSRVDEVICE_JSON']").val();
	    	var quickSrvDevJson = '${materForm.QUICKSRVDEVICE_JSON }';
	    	if(quickSrvDevJson != '' && quickSrvDevJson != '[]'){
	    		$('#quickSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(quickSrvDevJson));
	    	}
	    	//实例化发动机维修业户设备表格及初始化数据
	    	$('#engineSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 25){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前25行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 24){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var engineSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='ENGINESRVDEVICE_JSON']").val();
	    	var engineSrvDevJson = '${materForm.ENGINESRVDEVICE_JSON }';
	    	if(engineSrvDevJson != '' && engineSrvDevJson != '[]'){
	    		$('#engineSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(engineSrvDevJson));
	    	}
	    	//实例化车身维修业户设备表格及初始化数据
	    	$('#carBodySrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 22){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前22行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 21){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var carBodySrvDevJson = $("#CCRAPPLY_FORM").find("input[name='CARBODYSRVDEVICE_JSON']").val();
	    	var carBodySrvDevJson = '${materForm.CARBODYSRVDEVICE_JSON }';
	    	if(carBodySrvDevJson != '' && carBodySrvDevJson != '[]'){
	    		$('#carBodySrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(carBodySrvDevJson));
	    	}
	    	//实例化电气系统维修业户设备表格及初始化数据
	    	$('#electricSysSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 10){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前10行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 9){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var electricSysSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='ELECTRICSYSSRVDEVICE_JSON']").val();
	    	var electricSysSrvDevJson = '${materForm.ELECTRICSYSSRVDEVICE_JSON }';
	    	if(electricSysSrvDevJson != '' && electricSysSrvDevJson != '[]'){
	    		$('#electricSysSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(electricSysSrvDevJson));
	    	}
	    	//实例化自动变速器维修业户设备表格及初始化数据
	    	$('#variatorSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 16){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前16行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 15){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var variatorSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='VARIATORSRVDEVICE_JSON']").val();
	    	var variatorSrvDevJson = '${materForm.VARIATORSRVDEVICE_JSON }';
	    	if(variatorSrvDevJson != '' && variatorSrvDevJson != '[]'){
	    		$('#variatorSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(variatorSrvDevJson));
	    	}
	    	//实例化轮胎动平衡和修补业户设备表格及初始化数据
	    	$('#tireSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 10){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前10行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 9){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var tireSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='TIRESRVDEVICE_JSON']").val();
	    	var tireSrvDevJson = '${materForm.TIRESRVDEVICE_JSON }';
	    	if(tireSrvDevJson != '' && tireSrvDevJson != '[]'){
	    		$('#tireSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(tireSrvDevJson));
	    	}
	    	//实例化四轮定位检测调整业户设备表格及初始化数据
	    	$('#tireLocateSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 5){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前5行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 4){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var tireLocateSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='TIRELOCATESRVDEVICE_JSON']").val();
	    	var tireLocateSrvDevJson = '${materForm.TIRELOCATESRVDEVICE_JSON }';
	    	if(tireLocateSrvDevJson != '' && tireLocateSrvDevJson != '[]'){
	    		$('#tireLocateSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(tireLocateSrvDevJson));
	    	}
	    	//实例化汽车润滑与养护业户设备表格及初始化数据
	    	$('#lubricateSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 9){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前9行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 8){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var lubricateSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='LUBRICATESRVDEVICE_JSON']").val();
	    	var lubricateSrvDevJson = '${materForm.LUBRICATESRVDEVICE_JSON }';
	    	if(lubricateSrvDevJson != '' && lubricateSrvDevJson != '[]'){
	    		$('#lubricateSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(lubricateSrvDevJson));
	    	}
	    	//实例化喷油泵和喷油器维修业户设备表格及初始化数据
	    	$('#injectorSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 6){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前6行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 5){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var injectorSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='INJECTORSRVDEVICE_JSON']").val();
	    	var injectorSrvDevJson = '${materForm.INJECTORSRVDEVICE_JSON }';
	    	if(injectorSrvDevJson != '' && injectorSrvDevJson != '[]'){
	    		$('#injectorSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(injectorSrvDevJson));
	    	}
	    	//实例化喷油泵和喷油器维修业户电控补充设备表格及初始化数据
	    	$('#elecInjectorSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 6){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前6行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 5){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var elecInjectorSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='ELECINJECTORSRVDEVICE_JSON']").val();
	    	var elecInjectorSrvDevJson = '${materForm.ELECINJECTORSRVDEVICE_JSON }';
	    	if(elecInjectorSrvDevJson != '' && elecInjectorSrvDevJson != '[]'){
	    		$('#elecInjectorSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(elecInjectorSrvDevJson));
	    	}
	    	//实例化曲轴修磨业户设备表格及初始化数据
	    	$('#crankSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 10){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前10行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 9){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var crankSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='CRANKSRVDEVICE_JSON']").val();
	    	var crankSrvDevJson = '${materForm.CRANKSRVDEVICE_JSON }';
	    	if(crankSrvDevJson != '' && crankSrvDevJson != '[]'){
	    		$('#crankSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(crankSrvDevJson));
	    	}
	    	//实例化气缸镗磨业户设备表格及初始化数据
	    	$('#cylinderSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 11){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前11行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 10){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var cylinderSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='CYLINDERSRVDEVICE_JSON']").val();
	    	var cylinderSrvDevJson = '${materForm.CYLINDERSRVDEVICE_JSON }';
	    	if(cylinderSrvDevJson != '' && cylinderSrvDevJson != '[]'){
	    		$('#cylinderSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(cylinderSrvDevJson));
	    	}
	    	//实例化散热器维修业户设备表格及初始化数据
	    	$('#radiatorSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 7){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前7行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 6){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var radiatorSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='RADIATORSRVDEVICE_JSON']").val();
	    	var radiatorSrvDevJson = '${materForm.RADIATORSRVDEVICE_JSON }';
	    	if(radiatorSrvDevJson != '' && radiatorSrvDevJson != '[]'){
	    		$('#radiatorSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(radiatorSrvDevJson));
	    	}
	    	//实例化空调维修业户设备表格及初始化数据
	    	$('#airCondSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 8){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前8行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 7){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var airCondSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='AIRCONDSRVDEVICE_JSON']").val();
	    	var airCondSrvDevJson = '${materForm.AIRCONDSRVDEVICE_JSON }';
	    	if(airCondSrvDevJson != '' && airCondSrvDevJson != '[]'){
	    		$('#airCondSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(airCondSrvDevJson));
	    	}
	    	//实例化汽车美容装潢业户设备表格及初始化数据
	    	$('#beautySrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 7){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前7行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 6){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var beautySrvDevJson = $("#CCRAPPLY_FORM").find("input[name='BEAUTYSRVDEVICE_JSON']").val();
	    	var beautySrvDevJson = '${materForm.BEAUTYSRVDEVICE_JSON }';
	    	if(beautySrvDevJson != '' && beautySrvDevJson != '[]'){
	    		$('#beautySrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(beautySrvDevJson));
	    	}
	    	//实例化汽车玻璃安装及修复业户设备表格及初始化数据
	    	$('#glassSrvDeviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 9){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前9行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 8){
	    					var hasInputSomething = false;
		    				var $checkedRadios = $(this).find('input[iptName][type=radio]:checked');
			    			if($checkedRadios.length != 0){
			    				hasInputSomething = true;
			    			}
			    			$(this).find('input[iptName][type=text],select[iptName]').each(function(){
			    				if($(this).val() != ''){
			    					hasInputSomething = true;
			    					return false;
			    				}
			    			});
			    			if(!hasInputSomething){
			    				existBlankRow = true;
			    				return false;
			    			}
	    				}
	    			});
	    			if(existBlankRow){
	    				alert('操作失败,请将空白记录行填写后再执行新增行操作!');
	    				return false;
	    			}
	    		}
	    	});
	    	//var glassSrvDevJson = $("#CCRAPPLY_FORM").find("input[name='GLASSSRVDEVICE_JSON']").val();
	    	var glassSrvDevJson = '${materForm.GLASSSRVDEVICE_JSON }';
	    	if(glassSrvDevJson != '' && glassSrvDevJson != '[]'){
	    		$('#glassSrvDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(glassSrvDevJson));
	    	}
	    	//实例化从业人员表格及初始化数据
	    	$('#staffInfoTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			$(this).wrap("<form id='staffInfoTableForm'></form>");
	    			var isInputValid = $('#staffInfoTableForm').validationEngine('validate');
	    			$(this).unwrap();
	    			if(!isInputValid){
	    				return false;
	    			}
	    		}
	    	});
	    	//var staffJson = $("#CCRAPPLY_FORM").find("input[name='STAFF_JSON']").val();
	    	var staffJson = '${materForm.STAFF_JSON }';
	    	if(staffJson != '' && staffJson != '[]'){
	    		$('#staffInfoTable').MyEditableTable("loadData", JSON.parse(staffJson));
	    	}
	    	
	    	if(${materForm.RECORD_ID==null }){
	    		//企业名称
				var COMPANY_NAME = parent.$("input[name='COMPANY_NAME']").val();
				$("input[name='COMPANY_NAME']").val(COMPANY_NAME);
				//邮政编码
				var POST_CODE = parent.$("input[name='POST_CODE']").val();
				$("input[name='COMPANY_POSTCODE']").val(POST_CODE);
				var itemCode = parent.$("input[name='ITEM_CODE']").val();
				var APPLY_PHONE = "";
				var MAILING_ADDR = "";
				var REGISTER_ADDR = "";
				var investment = "";
				if(itemCode=='201605170002XK00101'||itemCode=='201605170002XK00102'){
					APPLY_PHONE = parent.$("input[name='CONTACT_PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSSINESS_ADDR']").val();
					REGISTER_ADDR = parent.$("input[name='REGISTER_ADDR']").val();
					investment = parent.$("input[name='REGISTERED_CAPITAL']").val();
				}else if(itemCode=='201605170002XK00103'){
					APPLY_PHONE = parent.$("input[name='PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSINESS_ADDR']").val();
					REGISTER_ADDR = parent.$("input[name='COMPANY_ADDR']").val();
					investment = parent.$("input[name='INVESTMENT']").val();
				}
				//联系电话
				$("input[name='COMPANY_PHONE']").val(APPLY_PHONE);
				//经营地址
				$("input[name='BUSINESS_ADDR']").val(MAILING_ADDR);
				//注册地址
				if(REGISTER_ADDR.indexOf("（")!="-1"){
					REGISTER_ADDR = REGISTER_ADDR.substring(0,REGISTER_ADDR.lastIndexOf("（"));
				}
				$("input[name='REGISTER_ADDR']").val(REGISTER_ADDR);
				//注册资金
				$("input[name='COMPANY_REGAMOUNT']").val(investment);
			}
	    });
	</script>
</head>
<body>
    <div class="container">
        <div class="syj-sbmain2 tmargin20">
            <div class="syj-tyys tmargin20" style="z-index:99;" id="formcontainer">
                <div class="bd margin20">
                    <form id="CCRAPPLY_FORM" action="domesticControllerController/saveRelatedMaterForm.do" method="post">
                        <input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }" id="childRecord"/>
						<input type="hidden" name="LEADER_JSON" value="${materForm.LEADER_JSON }"/>
						<input type="hidden" name="TECHLEADER_JSON" value="${materForm.TECHLEADER_JSON }"/>
						<input type="hidden" name="QAPERSON_JSON" value="${materForm.QAPERSON_JSON }"/>
						<input type="hidden" name="VARIETYSRVDEVICE_JSON" value="${materForm.VARIETYSRVDEVICE_JSON }"/>
						<input type="hidden" name="QUICKSRVDEVICE_JSON" value="${materForm.QUICKSRVDEVICE_JSON }"/>
						<input type="hidden" name="ENGINESRVDEVICE_JSON" value="${materForm.ENGINESRVDEVICE_JSON }"/>
						<input type="hidden" name="CARBODYSRVDEVICE_JSON" value="${materForm.CARBODYSRVDEVICE_JSON }"/>
						<input type="hidden" name="ELECTRICSYSSRVDEVICE_JSON" value="${materForm.ELECTRICSYSSRVDEVICE_JSON }"/>
						<input type="hidden" name="VARIATORSRVDEVICE_JSON" value="${materForm.VARIATORSRVDEVICE_JSON }"/>
						<input type="hidden" name="TIRESRVDEVICE_JSON" value="${materForm.TIRESRVDEVICE_JSON }"/>
						<input type="hidden" name="TIRELOCATESRVDEVICE_JSON" value="${materForm.TIRELOCATESRVDEVICE_JSON }"/>
						<input type="hidden" name="LUBRICATESRVDEVICE_JSON" value="${materForm.LUBRICATESRVDEVICE_JSON }"/>
						<input type="hidden" name="INJECTORSRVDEVICE_JSON" value="${materForm.INJECTORSRVDEVICE_JSON }"/>
						<input type="hidden" name="ELECINJECTORSRVDEVICE_JSON" value="${materForm.ELECINJECTORSRVDEVICE_JSON }"/>
						<input type="hidden" name="CRANKSRVDEVICE_JSON" value="${materForm.CRANKSRVDEVICE_JSON }"/>
						<input type="hidden" name="CYLINDERSRVDEVICE_JSON" value="${materForm.CYLINDERSRVDEVICE_JSON }"/>
						<input type="hidden" name="RADIATORSRVDEVICE_JSON" value="${materForm.RADIATORSRVDEVICE_JSON }"/>
						<input type="hidden" name="AIRCONDSRVDEVICE_JSON" value="${materForm.AIRCONDSRVDEVICE_JSON }"/>
						<input type="hidden" name="BEAUTYSRVDEVICE_JSON" value="${materForm.BEAUTYSRVDEVICE_JSON }"/>
						<input type="hidden" name="GLASSSRVDEVICE_JSON" value="${materForm.GLASSSRVDEVICE_JSON }"/>
						<input type="hidden" name="STAFF_JSON" value="${materForm.STAFF_JSON }"/>
						<div class="syj-title1">
							<span>维修业户基本概况1</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>业户名称：
						                <a class="answer" href="javascript:void(0);" title="按营业执照的业户名称填写"></a>
						            </th>
						            <td colspan="3">
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_NAME" value="${materForm.COMPANY_NAME }" maxlength="62" style="width:98.5%;"/>
						            </td>
						        </tr>
						    </table>
						    <table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:-1px;">
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>经营地址：
						                <a class="answer" href="javascript:void(0);" title="按详细经营地址填写"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="BUSINESS_ADDR" value="${materForm.BUSINESS_ADDR }" maxlength="126" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>联系电话：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[fixOrMobilePhone]]" name="COMPANY_PHONE" value="${materForm.COMPANY_PHONE }" maxlength="16" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>注册地址：
						                <a class="answer" href="javascript:void(0);" title="按详细注册地址填写"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="REGISTER_ADDR" value="${materForm.REGISTER_ADDR }" maxlength="126" />
						            </td>
						            <th>
						                <font class="syj-color2"> </font>传真电话：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[[],custom[fixFaxWithAreaCode]]" name="COMPANY_FAX" value="${materForm.COMPANY_FAX }" maxlength="16" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>邮政编码：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[chinaZip]]" name="COMPANY_POSTCODE" value="${materForm.COMPANY_POSTCODE }" maxlength="6" />
						            </td>
						            <th>
						                <font class="syj-color2"> </font>电子邮箱：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[[],custom[email]]" name="COMPANY_EMAIL" value="${materForm.COMPANY_EMAIL }" maxlength="62" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>企业编码：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_CODE" value="${materForm.COMPANY_CODE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>营业执照号：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="BUSLICENSE_NUM" value="${materForm.BUSLICENSE_NUM }" maxlength="30" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>经济类型：
						                <a class="answer" href="javascript:void(0);" title="按营业执照经济类型填写"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_ECONTYPE" value="${materForm.COMPANY_ECONTYPE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>注册资金(万元)：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberp6plus]]" name="COMPANY_REGAMOUNT" value="${materForm.COMPANY_REGAMOUNT }" maxlength="14" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>开户银行：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_BANK" value="${materForm.COMPANY_BANK }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>银行账号：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[onlyNumberSp]]" name="COMPANY_BANKACCOUNT" value="${materForm.COMPANY_BANKACCOUNT }" maxlength="30" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>经营类别：
						                <a class="answer" href="javascript:void(0);" title="三类汽车维修经营类别分为汽车综合小修、快修和专项维修。若为连锁型业户应在上述经营类别之后增加“(连锁型总店)”、“(连锁型直营店)”或“(连锁型加盟店)”。"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_BUSTYPE" value="${materForm.COMPANY_BUSTYPE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>经营范围：
						                <a class="answer" href="javascript:void(0);" title="1、汽车综合小修。从事汽车故障诊断和通过修理或更换个别零件，消除车辆在运行过程或维护过程中发生或发现的故障或隐患，恢复汽车工作能力的维修业户（三类）。2、汽车快修。汽车快修是指以快速、便捷为特性，对乘用车类汽车进行诊断、维护和小修为作业内容（除汽车整车维修、总成修理和二级维护作业外）的汽车维修。3、汽车专项维修经营范围再细分为发动机维修、车身维修、电气系统维修、自动变速器维修、轮胎动平衡及修补、四轮定位检测调整、汽车润滑与养护、喷油泵和喷油器维修、曲轴修磨、气缸镗磨、散热器维修、空调维修、汽车美容装潢、汽车玻璃安装及修复等14种。"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_BUSSCOPE" value="${materForm.COMPANY_BUSSCOPE }" maxlength="30" />
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>主修车型：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="MAJOR_CARTYPE" value="${materForm.MAJOR_CARTYPE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>工时单价(元/小时)：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="LABOR_HOURPRICE" value="${materForm.LABOR_HOURPRICE }" maxlength="10" />
						            </td>
						        </tr>
						    </table>
						    <table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:-1px;">
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>所在国省道代码：
						                <a class="answer" href="javascript:void(0);" title="所处国、省道的名称，如G104"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="HIGHWAY_CODE" value="${materForm.HIGHWAY_CODE }" maxlength="8" style="width:92%;"/>
						            </td>
						            <th>
						                <font class="syj-color2">*</font>路线名称：
						                <a class="answer" href="javascript:void(0);" title="国、省道路线起讫点，如北京—福州"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="HIGHWAY_NAME" value="${materForm.HIGHWAY_NAME }" maxlength="30" style="width:92%;"/>
						            </td>
						            <th>
						                <font class="syj-color2">*</font>桩号里程：
						                <a class="answer" href="javascript:void(0);" title="所处具体路线桩号里程位置，如2329K+875"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="HIGHWAY_LANDMARK" value="${materForm.HIGHWAY_LANDMARK }" maxlength="14" style="width:92%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>是否特约维修：
						            </th>
						            <td>
						                <input type="radio" name="IS_SPECIALSERVICE" value="1" <c:if test="${materForm.IS_SPECIALSERVICE==1}"> checked="checked"</c:if> class="validate[required]">是
										<input type="radio" name="IS_SPECIALSERVICE" value="0" <c:if test="${materForm.IS_SPECIALSERVICE==0}"> checked="checked"</c:if> class="validate[required]">否
						            </td>
						            <th>
						                <font class="syj-color2">*</font>特约车型：
						            </th>
						            <td colspan="3">
						                <input type="text" class="syj-input1 validate[required]" name="SPECIAL_CARTYPE" value="${materForm.SPECIAL_CARTYPE }" maxlength="30" style="width:97.5%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>是否救援服务：
						            </th>
						            <td>
						                <input type="radio" name="IS_RESCUESERVICE" value="1" <c:if test="${materForm.IS_RESCUESERVICE==1}"> checked="checked"</c:if> class="validate[required]">是
										<input type="radio" name="IS_RESCUESERVICE" value="0" <c:if test="${materForm.IS_RESCUESERVICE==0}"> checked="checked"</c:if> class="validate[required]">否
						            </td>
						            <th>
						                <font class="syj-color2">*</font>救援车辆数：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RESCUE_CARNUM" value="${materForm.RESCUE_CARNUM }" maxlength="10" style="width:92%;"/>
						            </td>
						            <th>
						                <font class="syj-color2">*</font>24小时救援电话：
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[fixOrMobilePhone]]" name="RESCUE_PHONE" value="${materForm.RESCUE_PHONE }" maxlength="16" style="width:92%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>是否连锁经营：
						            </th>
						            <td>
						                <input type="radio" name="IS_CHAINOPR" value="1" <c:if test="${materForm.IS_CHAINOPR==1}"> checked="checked"</c:if> class="validate[required]">是
										<input type="radio" name="IS_CHAINOPR" value="0" <c:if test="${materForm.IS_CHAINOPR==0}"> checked="checked"</c:if> class="validate[required]">否
						            </td>
						            <th>
						                <font class="syj-color2">*</font>设立技术服务总部<br/>地址：
						            </th>
						            <td colspan="3">
						                <input type="text" class="syj-input1 validate[required]" name="TECHCENTER_ADDR" value="${materForm.TECHCENTER_ADDR }" maxlength="126" style="width:97.5%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">
						                <font class="syj-color2">*</font>面积<br/>(平方米)：
						            </th>
						            <th>
						                <font class="syj-color2">*</font>占地总面积
						            </th>
						            <th colspan="2">
						                <font class="syj-color2">*</font>接待室面积(客户休息室面积)
						            </th>
						            <th>
						                <font class="syj-color2">*</font>生产厂房面积
						            </th>
						            <th>
						                <font class="syj-color2">*</font>停车场面积
						            </th>
						        </tr>
						        <tr>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="TOTAL_AREA" value="${materForm.TOTAL_AREA }" maxlength="10" style="width:92%;"/>
						            </td>
						            <td colspan="2">
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="RECEPTIONROOM_AREA" value="${materForm.RECEPTIONROOM_AREA }" maxlength="10" style="width:96%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="FACTORY_AREA" value="${materForm.FACTORY_AREA }" maxlength="10" style="width:92%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="PARKINGLOT_AREA" value="${materForm.PARKINGLOT_AREA }" maxlength="10" style="width:92%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">
						                <font class="syj-color2">*</font>法人代表：
						            </th>
						            <th>
						                <font class="syj-color2">*</font>姓名
						            </th>
						            <th colspan="2">
						                <font class="syj-color2">*</font>办公电话
						            </th>
						            <th colspan="2">
						                <font class="syj-color2">*</font>手机号码
						            </th>
						        </tr>
						        <tr>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="LEGALPERSON_NAME" value="${materForm.LEGALPERSON_NAME }" maxlength="14" style="width:92%;"/>
						            </td>
						            <td colspan="2">
						                <input type="text" class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]" name="LEGALPERSON_OFFICEPHONE" value="${materForm.LEGALPERSON_OFFICEPHONE }" maxlength="14" style="width:96%;"/>
						            </td>
						            <td colspan="2">
						                <input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="LEGALPERSON_MOBILE" value="${materForm.LEGALPERSON_MOBILE }" maxlength="11" style="width:96%;"/>
						            </td>
						        </tr>
						    </table>
						    <table id="leaderTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
					            <tr>
						            <th id="leaderTh" rowspan="2" style="width:160px;">
						                <font class="syj-color2">*</font>业户管理负责人：
						                <c:if test="${operType=='write' }">
							                <div style="margin-top:10px;text-align:center;">
							                    <a href="javascript:void(0);" class="add-btn" onclick="$('#leaderTable').MyEditableTable('appendRow');">增加</a>
							                	<a href="javascript:void(0);" class="del-btn" onclick="$('#leaderTable').MyEditableTable('deleteRow');">删除</a>
							                </div>
						                </c:if>
						            </th>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:180px;">
						                <font class="syj-color2">*</font>姓名
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>办公电话
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>手机号码
						            </th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="leaderName" maxlength="14" style="width:93%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]" iptName="leaderPhone" maxlength="14" style="width:95%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" iptName="leaderMobile" maxlength="11" style="width:95%;"/>
						            </td>
						        </tr>
					        </table>
					        <table id="techleaderTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th id="techleaderTh" rowspan="2" style="width:160px;">
						                <font class="syj-color2">*</font>技术负责人：
						                <c:if test="${operType=='write' }">
							                <div style="margin-top:10px;text-align:center;">
							                    <a href="javascript:void(0);" class="add-btn" onclick="$('#techleaderTable').MyEditableTable('appendRow');">增加</a>
							                	<a href="javascript:void(0);" class="del-btn" onclick="$('#techleaderTable').MyEditableTable('deleteRow');">删除</a>
							                </div>
						                </c:if>
						            </th>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:180px;">
						                <font class="syj-color2">*</font>姓名
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>办公电话
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>手机号码
						            </th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="techleaderName" maxlength="14" style="width:93%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]" iptName="techleaderPhone" maxlength="14" style="width:95%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" iptName="techleaderMobile" maxlength="11" style="width:95%;"/>
						            </td>
						        </tr>
						    </table>
						    <table id="qapersonTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th id="qapersonTh" rowspan="2" style="width:160px;">
						                <font class="syj-color2">*</font>质量检验人员：
						                <c:if test="${operType=='write' }">
							                <div style="margin-top:10px;text-align:center;">
							                    <a href="javascript:void(0);" class="add-btn" onclick="$('#qapersonTable').MyEditableTable('appendRow');">增加</a>
							                	<a href="javascript:void(0);" class="del-btn" onclick="$('#qapersonTable').MyEditableTable('deleteRow');">删除</a>
							                </div>
						                </c:if>
						            </th>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:180px;">
						                <font class="syj-color2">*</font>姓名
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>办公电话
						            </th>
						            <th style="width:256.5px;">
						                <font class="syj-color2">*</font>手机号码
						            </th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="qapersonName" maxlength="14" style="width:93%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]" iptName="qapersonPhone" maxlength="14" style="width:95%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" iptName="qapersonMobile" maxlength="11" style="width:95%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>维修业户基本概况2</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th rowspan="2" colspan="3" style="width:305px;">人员配备情况(人)</th>
						            <th style="width:150px;">
						                <font class="syj-color2">*</font>在职总人数
						            </th>
						            <th style="width:150px;">
						                <font class="syj-color2">*</font>管理人员
						            </th>
						            <th style="width:150px;">
						                <font class="syj-color2">*</font>维修人员
						            </th>
						            <th style="width:150px;">
						                <font class="syj-color2">*</font>其他
						            </th>
						        </tr>
						        <tr>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="STAFF_TOTALCOUNT"   value="${materForm.STAFF_TOTALCOUNT }" maxlength="10" style="width:92%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="MANAGER_COUNT" value="${materForm.MANAGER_COUNT }" maxlength="10" style="width:92%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="MAINTAINER_COUNT" value="${materForm.MAINTAINER_COUNT }" maxlength="10" style="width:92%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RESTSTAFF_COUNT" value="${materForm.RESTSTAFF_COUNT }" maxlength="10" style="width:92%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="6">管理人员(人)</th>
						            <th colspan="2">
						                <font class="syj-color2">*</font>业户管理负责人
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="LEADER_COUNT" value="${materForm.LEADER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>技术负责人
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="TECHLEADER_COUNT" value="${materForm.TECHLEADER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>质量检验人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="QAPERSON_COUNT" value="${materForm.QAPERSON_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>业务接待员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RECEPTIONIST_COUNT" value="${materForm.RECEPTIONIST_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>价格结算员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="CLERK_COUNT" value="${materForm.CLERK_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>其他管理人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RESTMANAGER_COUNT" value="${materForm.RESTMANAGER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="6">维修人员(人)</th>
						            <th colspan="2">
						                <font class="syj-color2">*</font>机修人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="MECHANIC_COUNT" value="${materForm.MECHANIC_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>电器维修人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="EAMAINTAINER_COUNT" value="${materForm.EAMAINTAINER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>钣金人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="METZLER_COUNT" value="${materForm.METZLER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>涂漆人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="PAINTER_COUNT" value="${materForm.PAINTER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>检测人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="QCPERSON_COUNT" value="${materForm.QCPERSON_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>其他维修人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RESTMAINTAINER_COUNT" value="${materForm.RESTMAINTAINER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>通用条件、安全生产和环境保护条件</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th style="width:100px;">项目</th>
						            <th style="width:405px;">基本要求</th>
						            <th style="width:405px;"><font class="syj-color2">*</font>业户自查情况</th>
						        </tr>
						        <tr>
						            <th rowspan="7">通用条件</th>
						            <td>1.从事综合小修、快修或专项维修关键岗位的从业人员数量应能满足生产的需要。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMA" value="${materForm.SELFCHECK_COMMONITEMA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.具有相关的法规、标准、规章等文件以及相关的维修技术资料和工艺文件等，并确保完整有效、及时更新。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMB" value="${materForm.SELFCHECK_COMMONITEMB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>3.具有规范的业务工作流程，公开业务受理程序、服务承诺、用户抱怨受理程序等，并明示各类证、照、作业项目及计费工时定额等。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMC" value="${materForm.SELFCHECK_COMMONITEMC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.停车场地界定标志明显，不得占用道路和公共场所进行作业和停车，地面应平整坚实。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMD" value="${materForm.SELFCHECK_COMMONITEMD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>5.生产厂房的面积、结构及设施应满足综合小修、快修或专项维修作业设备的工位布置、生产工艺和正常作业要求。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEME" value="${materForm.SELFCHECK_COMMONITEME }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>6.租赁的生产厂房、停车场地应具有合法的书面合同书（合同中应体现租赁面积），租赁期限不得少于1年。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMF" value="${materForm.SELFCHECK_COMMONITEMF }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>7.设备配置应与其生产作业规模及生产工艺相适应，其技术状况应完好，符合相应的产品技术条件等国家标准或行业标准的要求，并能满足加工、检测精度的要求和使用要求。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_COMMONITEMG" value="${materForm.SELFCHECK_COMMONITEMG }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="4">安全生产与环境保护</th>
						            <td>1.配备安全生产管理人员。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYA" value="${materForm.SELFCHECK_WORKSAFETYA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.安全生产管理人员经培训，熟知国家安全生产法律法规，具有汽车维修安全生产作业知识和安全生产管理能力。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_WORKSAFETYB" value="1" <c:if test="${materForm.SELFCHECK_WORKSAFETYB==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_WORKSAFETYB" value="0" <c:if test="${materForm.SELFCHECK_WORKSAFETYB==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td>3.有所需工种和所配机电设备的安全操作规程，并将安全操作规程明示在相应的工位或设备处。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYC" value="${materForm.SELFCHECK_WORKSAFETYC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.使用与存储有毒、易燃、易爆物品和粉尘、腐蚀剂、污染物、压力容器等均应具备相应的安全防护措施和设施。按作业环境和生产工艺配置处理“四废”及采光、通风、吸尘、净化、消声等设施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYD" value="${materForm.SELFCHECK_WORKSAFETYD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>汽车综合小修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="15">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEA" value="${materForm.SELFCHECK_VARIETYSERVICEA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEB" value="${materForm.SELFCHECK_VARIETYSERVICEB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEC" value="${materForm.SELFCHECK_VARIETYSERVICEC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIETYSERVICED" value="1" <c:if test="${materForm.SELFCHECK_VARIETYSERVICED==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIETYSERVICED" value="0" <c:if test="${materForm.SELFCHECK_VARIETYSERVICED==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">质量检验人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEE" value="${materForm.SELFCHECK_VARIETYSERVICEE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEF" value="${materForm.SELFCHECK_VARIETYSERVICEF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIETYSERVICEG" value="1" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIETYSERVICEG" value="0" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">机修人员</td>
						            <td>不少于2人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEH" value="${materForm.SELFCHECK_VARIETYSERVICEH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEI" value="${materForm.SELFCHECK_VARIETYSERVICEI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIETYSERVICEJ" value="1" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIETYSERVICEJ" value="0" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">电器维修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEK" value="${materForm.SELFCHECK_VARIETYSERVICEK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEL" value="${materForm.SELFCHECK_VARIETYSERVICEL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIETYSERVICEM" value="1" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEM==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIETYSERVICEM" value="0" <c:if test="${materForm.SELFCHECK_VARIETYSERVICEM==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">业务接待员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEN" value="${materForm.SELFCHECK_VARIETYSERVICEN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">价格结算员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEO" value="${materForm.SELFCHECK_VARIETYSERVICEO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">组织管理</th>
						            <td colspan="2">具有健全的经营管理体系，设置技术负责、业务受理、质量检验、文件资料管理、材料管理、仪器设备管理、价格结算、安全生产等岗位并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEP" value="${materForm.SELFCHECK_VARIETYSERVICEP }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">具有汽车维修质量承诺、进出厂登记、检验记录及技术档案管理、标准和计量管理、设备管理、人员技术培训等制度并严格实施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICEQ" value="${materForm.SELFCHECK_VARIETYSERVICEQ }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">维修过程、配件管理、费用结算、维修档案等应实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICER" value="${materForm.SELFCHECK_VARIETYSERVICER }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICES" value="${materForm.SELFCHECK_VARIETYSERVICES }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于120㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIETYSERVICET" value="${materForm.SELFCHECK_VARIETYSERVICET }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>汽车综合小修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#varietySrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#varietySrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="varietySrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="压床"/>                                    
                                                                          压床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned1" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned1" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                          空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned2" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned2" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车故障电脑诊断仪"/>                                    
                                                                          汽车故障电脑诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned3" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned3" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式温、湿度计"/>                                    
                                                                          数字式温、湿度计</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned4" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned4" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                          数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned5" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned5" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="气缸压力表"/>                                    
                                                                          气缸压力表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned6" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned6" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="真空表"/>                                    
                                                                          真空表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned7" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned7" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="燃油压力表"/>                                    
                                                                          燃油压力表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned8" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned8" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车尾气排放检验设备"/>                                    
                                                                          汽车尾气排放检验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned9" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned9" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备，配备与承修车型相适应的尾气分析仪或不透光烟度计"/>                                    
                                                                           必备，配备与承修车型相适应的尾气分析仪或不透光烟度计</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎漏气试验设备"/>                                    
                                                                          轮胎漏气试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned10" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned10" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎气压表"/>                                    
                                                                          轮胎气压表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned11" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned11" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>12</th>
						                            <th><input chkIndex="12" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎花纹深度尺"/>                                    
                                                                          轮胎花纹深度尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned12" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned12" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>13</th>
						                            <th><input chkIndex="13" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车千斤顶"/>                                    
                                                                          汽车千斤顶</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned13" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned13" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>14</th>
						                            <th><input chkIndex="14" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎轮辋拆装设备或专用工具"/>                                    
                                                                          轮胎轮辋拆装设备或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned14" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned14" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>15</th>
						                            <th><input chkIndex="15" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎螺母拆装机或专用工具"/>                                    
                                                                          轮胎螺母拆装机或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned15" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned15" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>16</th>
						                            <th><input chkIndex="16" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车轮动平衡机"/>                                    
                                                                          车轮动平衡机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned16" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned16" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>17</th>
						                            <th><input chkIndex="17" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒鉴别设备"/>                                    
                                                                          空调冷媒鉴别设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned17" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned17" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>18</th>
						                            <th><input chkIndex="18" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒回收净化加注设备"/>                                    
                                                                          空调冷媒回收净化加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned18" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned18" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>19</th>
						                            <th><input chkIndex="19" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调专用检测设备（具有空调高低端压力和电器检测功能）"/>                                    
                                                                          空调专用检测设备（具有空调高低端压力和电器检测功能）</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned19" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned19" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>20</th>
						                            <th><input chkIndex="20" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒检漏设备"/>                                    
                                                                          空调冷媒检漏设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned20" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned20" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>21</th>
						                            <th><input chkIndex="21" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="不解体油路清洗设备"/>                                    
                                                                          不解体油路清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned21" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned21" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>22</th>
						                            <th><input chkIndex="22" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车举升机或地沟设施"/>                                    
                                                                          汽车举升机或地沟设施</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned22" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned22" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>23</th>
						                            <th><input chkIndex="23" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="废油收集设备"/>                                    
                                                                          废油收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned23" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned23" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>24</th>
						                            <th><input chkIndex="24" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="齿轮油加注设备"/>                                    
                                                                          齿轮油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned24" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned24" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>25</th>
						                            <th><input chkIndex="25" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="液压油加注设备"/>                                    
                                                                          液压油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned25" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned25" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>26</th>
						                            <th><input chkIndex="26" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="制动液更换加注器"/>                                    
                                                                          制动液更换加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned26" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned26" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>27</th>
						                            <th><input chkIndex="27" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="脂类加注器"/>                                    
                                                                          脂类加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned27" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned27" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>28</th>
						                            <th><input chkIndex="28" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车前照灯检测仪（具备前照灯远、近光检验功能的自动跟踪式前照灯检测仪）"/>                                    
                                                                          汽车前照灯检测仪（具备前照灯远、近光检验功能的自动跟踪式前照灯检测仪）</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned28" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned28" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>29</th>
						                            <th><input chkIndex="29" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="制动减速度等制动性能检验设备"/>                                    
                                                                          制动减速度等制动性能检验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned29" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned29" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备，可选用汽车轮重仪和滚筒反力式制动检验台（或滚筒反力式轮重制动复合检验台）、平板式制动检验台、便携式制动性能检测仪或非接触式速度计四种检验设备之一"/>                                    
                                                                           必备，可选用汽车轮重仪和滚筒反力式制动检验台（或滚筒反力式轮重制动复合检验台）、平板式制动检验台、便携式制动性能检测仪或非接触式速度计四种检验设备之一</td>
						                        </tr>
						                        <tr>
						                            <th>30</th>
						                            <th><input chkIndex="30" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned30" iptName="varietySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="varietySrvIsOwned30" iptName="varietySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>汽车快修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="10">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEA" value="${materForm.SELFCHECK_QUICKSERVICEA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEB" value="${materForm.SELFCHECK_QUICKSERVICEB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEC" value="${materForm.SELFCHECK_QUICKSERVICEC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_QUICKSERVICED" value="1" <c:if test="${materForm.SELFCHECK_QUICKSERVICED==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_QUICKSERVICED" value="0" <c:if test="${materForm.SELFCHECK_QUICKSERVICED==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">机修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEE" value="${materForm.SELFCHECK_QUICKSERVICEE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEF" value="${materForm.SELFCHECK_QUICKSERVICEF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_QUICKSERVICEG" value="1" <c:if test="${materForm.SELFCHECK_QUICKSERVICEG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_QUICKSERVICEG" value="0" <c:if test="${materForm.SELFCHECK_QUICKSERVICEG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">电器维修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEH" value="${materForm.SELFCHECK_QUICKSERVICEH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEI" value="${materForm.SELFCHECK_QUICKSERVICEI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_QUICKSERVICEJ" value="1" <c:if test="${materForm.SELFCHECK_QUICKSERVICEJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_QUICKSERVICEJ" value="0" <c:if test="${materForm.SELFCHECK_QUICKSERVICEJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEK" value="${materForm.SELFCHECK_QUICKSERVICEK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于100㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_QUICKSERVICEL" value="${materForm.SELFCHECK_QUICKSERVICEL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>汽车快修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#quickSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#quickSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="quickSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身清洗设备"/>                                    
                                                                          车身清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned1" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned1" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="废油收集设备"/>                                    
                                                                          废油收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned2" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned2" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="齿轮油加注设备"/>                                    
                                                                          齿轮油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned3" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned3" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="液压油加注设备"/>                                    
                                                                          液压油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned4" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned4" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="制动液更换加注器"/>                                    
                                                                          制动液更换加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned5" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned5" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="脂类加注器"/>                                    
                                                                          脂类加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned6" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned6" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车空调冷媒回收净化加注设备"/>                                    
                                                                          汽车空调冷媒回收净化加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned7" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned7" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷油器清洗及流量测量仪"/>                                    
                                                                          喷油器清洗及流量测量仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned8" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned8" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="不解体油路清洗设备"/>                                    
                                                                          不解体油路清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned9" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned9" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车举升机或地沟设施"/>                                    
                                                                          汽车举升机或地沟设施</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned10" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned10" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                          空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned11" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned11" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>12</th>
						                            <th><input chkIndex="12" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎轮辋拆装设备或专用工具"/>                                    
                                                                          轮胎轮辋拆装设备或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned12" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned12" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>13</th>
						                            <th><input chkIndex="13" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎螺母拆装机或专用工具"/>                                    
                                                                          轮胎螺母拆装机或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned13" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned13" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>14</th>
						                            <th><input chkIndex="14" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车轮动平衡机"/>                                    
                                                                          车轮动平衡机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned14" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned14" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>15</th>
						                            <th><input chkIndex="15" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="四轮定位仪"/>                                    
                                                                          四轮定位仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned15" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned15" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>16</th>
						                            <th><input chkIndex="16" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车尾气排放检验设备"/>                                    
                                                                          汽车尾气排放检验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned16" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned16" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="配备与承修车型相适应的尾气分析仪或不透光烟度计，允许外协"/>                                    
                                                                           配备与承修车型相适应的尾气分析仪或不透光烟度计，允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>17</th>
						                            <th><input chkIndex="17" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车故障电脑诊断仪"/>                                    
                                                                          汽车故障电脑诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned17" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned17" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>18</th>
						                            <th><input chkIndex="18" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                          数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned18" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned18" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>19</th>
						                            <th><input chkIndex="19" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="移动式工具车"/>                                    
                                                                          移动式工具车</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned19" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned19" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>20</th>
						                            <th><input chkIndex="20" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned20" iptName="quickSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="quickSrvIsOwned20" iptName="quickSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>发动机维修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="15">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVA" value="${materForm.SELFCHECK_ENGINESRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVB" value="${materForm.SELFCHECK_ENGINESRVB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVC" value="${materForm.SELFCHECK_ENGINESRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ENGINESRVD" value="1" <c:if test="${materForm.SELFCHECK_ENGINESRVD==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ENGINESRVD" value="0" <c:if test="${materForm.SELFCHECK_ENGINESRVD==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">质量检验人员</td>
						            <td>不少于2人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVE" value="${materForm.SELFCHECK_ENGINESRVE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVF" value="${materForm.SELFCHECK_ENGINESRVF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ENGINESRVG" value="1" <c:if test="${materForm.SELFCHECK_ENGINESRVG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ENGINESRVG" value="0" <c:if test="${materForm.SELFCHECK_ENGINESRVG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">机修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVH" value="${materForm.SELFCHECK_ENGINESRVH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVI" value="${materForm.SELFCHECK_ENGINESRVI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ENGINESRVJ" value="1" <c:if test="${materForm.SELFCHECK_ENGINESRVJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ENGINESRVJ" value="0" <c:if test="${materForm.SELFCHECK_ENGINESRVJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">电器维修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVK" value="${materForm.SELFCHECK_ENGINESRVK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVL" value="${materForm.SELFCHECK_ENGINESRVL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ENGINESRVM" value="1" <c:if test="${materForm.SELFCHECK_ENGINESRVM==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ENGINESRVM" value="0" <c:if test="${materForm.SELFCHECK_ENGINESRVM==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">业务接待员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVN" value="${materForm.SELFCHECK_ENGINESRVN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">价格结算员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVO" value="${materForm.SELFCHECK_ENGINESRVO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">组织管理</th>
						            <td colspan="2">具有健全的经营管理体系，设置技术负责、业务受理、质量检验、文件资料管理、材料管理、仪器设备管理、价格结算、安全生产等岗位并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVP" value="${materForm.SELFCHECK_ENGINESRVP }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">具有汽车维修质量承诺、进出厂登记、检验记录及技术档案管理、标准和计量管理、设备管理、人员技术培训等制度并严格实施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVQ" value="${materForm.SELFCHECK_ENGINESRVQ }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">维修过程、配件管理、费用结算、维修档案等应实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVR" value="${materForm.SELFCHECK_ENGINESRVR }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVS" value="${materForm.SELFCHECK_ENGINESRVS }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于120㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ENGINESRVT" value="${materForm.SELFCHECK_ENGINESRVT }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>发动机维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#engineSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#engineSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="engineSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="压床"/>                                    
                                                                          压床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned1" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned1" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                          空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned2" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned2" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="发动机解体清洗设备"/>                                    
                                                                          发动机解体清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned3" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned3" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="发动机等总成吊装设备"/>                                    
                                                                          发动机等总成吊装设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned4" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned4" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="发动机翻转设备"/>                                    
                                                                          发动机翻转设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned5" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned5" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="发动机诊断仪"/>                                    
                                                                          发动机诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned6" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned6" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="废油收集设备"/>                                    
                                                                          废油收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned7" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned7" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                          数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned8" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned8" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="气缸压力表"/>                                    
                                                                          气缸压力表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned9" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned9" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="真空表"/>                                    
                                                                          真空表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned10" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned10" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="量缸表"/>                                    
                                                                          量缸表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned11" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned11" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>12</th>
						                            <th><input chkIndex="12" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="正时仪"/>                                    
                                                                          正时仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned12" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned12" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>13</th>
						                            <th><input chkIndex="13" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽油喷油器清洗及流量测量仪"/>                                    
                                                                          汽油喷油器清洗及流量测量仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned13" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned13" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>14</th>
						                            <th><input chkIndex="14" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="燃油压力表"/>                                    
                                                                          燃油压力表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned14" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned14" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>15</th>
						                            <th><input chkIndex="15" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷油泵试验设备"/>                                    
                                                                          喷油泵试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned15" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned15" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>16</th>
						                            <th><input chkIndex="16" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷油器试验设备"/>                                    
                                                                          喷油器试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned16" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned16" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>17</th>
						                            <th><input chkIndex="17" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="连杆校正器"/>                                    
                                                                          连杆校正器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned17" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned17" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>18</th>
						                            <th><input chkIndex="18" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="无损探伤设备"/>                                    
                                                                          无损探伤设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned18" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned18" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>19</th>
						                            <th><input chkIndex="19" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="立式精镗床"/>                                    
                                                                          立式精镗床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned19" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned19" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>20</th>
						                            <th><input chkIndex="20" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="立式珩磨机"/>                                    
                                                                          立式珩磨机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned20" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned20" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>21</th>
						                            <th><input chkIndex="21" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴磨床"/>                                    
                                                                          曲轴磨床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned21" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned21" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>22</th>
						                            <th><input chkIndex="22" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴校正设备"/>                                    
                                                                          曲轴校正设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned22" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned22" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>23</th>
						                            <th><input chkIndex="23" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="凸轮轴磨床"/>                                    
                                                                          凸轮轴磨床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned23" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned23" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>24</th>
						                            <th><input chkIndex="24" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴、飞轮与离合器总成动平衡机"/>                                    
                                                                          曲轴、飞轮与离合器总成动平衡机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned24" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned24" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>25</th>
						                            <th><input chkIndex="25" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned25" iptName="engineSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="engineSrvIsOwned25" iptName="engineSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>车身维修业户人员、组织、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="18">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVA" value="${materForm.SELFCHECK_CARBODYSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVB" value="${materForm.SELFCHECK_CARBODYSRVB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVC" value="${materForm.SELFCHECK_CARBODYSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CARBODYSRVD" value="1" <c:if test="${materForm.SELFCHECK_CARBODYSRVD==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_CARBODYSRVD" value="0" <c:if test="${materForm.SELFCHECK_CARBODYSRVD==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">质量检验人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVE" value="${materForm.SELFCHECK_CARBODYSRVE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVF" value="${materForm.SELFCHECK_CARBODYSRVF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CARBODYSRVG" value="1" <c:if test="${materForm.SELFCHECK_CARBODYSRVG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_CARBODYSRVG" value="0" <c:if test="${materForm.SELFCHECK_CARBODYSRVG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">机修人员</td>
						            <td>不少于2人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVH" value="${materForm.SELFCHECK_CARBODYSRVH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVI" value="${materForm.SELFCHECK_CARBODYSRVI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CARBODYSRVJ" value="1" <c:if test="${materForm.SELFCHECK_CARBODYSRVJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_CARBODYSRVJ" value="0" <c:if test="${materForm.SELFCHECK_CARBODYSRVJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">钣金人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVK" value="${materForm.SELFCHECK_CARBODYSRVK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVL" value="${materForm.SELFCHECK_CARBODYSRVL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车钣金工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CARBODYSRVM" value="1" <c:if test="${materForm.SELFCHECK_CARBODYSRVM==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_CARBODYSRVM" value="0" <c:if test="${materForm.SELFCHECK_CARBODYSRVM==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">涂漆人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVN" value="${materForm.SELFCHECK_CARBODYSRVN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVO" value="${materForm.SELFCHECK_CARBODYSRVO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车涂漆工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CARBODYSRVP" value="1" <c:if test="${materForm.SELFCHECK_CARBODYSRVP==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_CARBODYSRVP" value="0" <c:if test="${materForm.SELFCHECK_CARBODYSRVP==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">业务接待员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVQ" value="${materForm.SELFCHECK_CARBODYSRVQ }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">价格结算员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVR" value="${materForm.SELFCHECK_CARBODYSRVR }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">组织管理</th>
						            <td colspan="2">具有健全的经营管理体系，设置技术负责、业务受理、质量检验、文件资料管理、材料管理、仪器设备管理、价格结算、安全生产等岗位并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVS" value="${materForm.SELFCHECK_CARBODYSRVS }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">具有汽车维修质量承诺、进出厂登记、检验记录及技术档案管理、标准和计量管理、设备管理、人员技术培训等制度并严格实施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVT" value="${materForm.SELFCHECK_CARBODYSRVT }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">维修过程、配件管理、费用结算、维修档案等应实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVU" value="${materForm.SELFCHECK_CARBODYSRVU }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVV" value="${materForm.SELFCHECK_CARBODYSRVV }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于120㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CARBODYSRVW" value="${materForm.SELFCHECK_CARBODYSRVW }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>车身维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#carBodySrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#carBodySrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="carBodySrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电焊设备"/>                                    
                                                                          电焊设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned1" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned1" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="气体保护焊设备"/>                                    
                                                                          气体保护焊设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned2" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned2" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="切割设备"/>                                    
                                                                          切割设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned3" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned3" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="压床"/>                                    
                                                                          压床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned4" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned4" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                          空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned5" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned5" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身清洗设备"/>                                    
                                                                          车身清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned6" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned6" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="打磨抛光设备"/>                                    
                                                                          打磨抛光设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned7" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned7" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="除尘除垢设备"/>                                    
                                                                          除尘除垢设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned8" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned8" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="型材切割机"/>                                    
                                                                          型材切割机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned9" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned9" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身整形设备"/>                                    
                                                                          车身整形设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned10" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned10" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身校正设备"/>                                    
                                                                          车身校正设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned11" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned11" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>12</th>
						                            <th><input chkIndex="12" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身尺寸测量设备"/>                                    
                                                                          车身尺寸测量设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned12" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned12" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>13</th>
						                            <th><input chkIndex="13" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车架校正设备"/>                                    
                                                                          车架校正设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned13" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned13" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>14</th>
						                            <th><input chkIndex="14" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷烤漆房及喷枪等设备"/>                                    
                                                                          喷烤漆房及喷枪等设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned14" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned14" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>15</th>
						                            <th><input chkIndex="15" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="调漆设备"/>                                    
                                                                          调漆设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned15" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned15" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="允许外协"/>                                    
                                                                           允许外协</td>
						                        </tr>
						                        <tr>
						                            <th>16</th>
						                            <th><input chkIndex="16" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="砂轮机"/>                                    
                                                                          砂轮机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned16" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned16" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>17</th>
						                            <th><input chkIndex="17" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="角磨机"/>                                    
                                                                          角磨机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned17" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned17" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>18</th>
						                            <th><input chkIndex="18" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车举升机或地沟设施"/>                                    
                                                                          汽车举升机或地沟设施</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned18" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned18" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>19</th>
						                            <th><input chkIndex="19" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="除锈设备"/>                                    
                                                                          除锈设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned19" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned19" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>20</th>
						                            <th><input chkIndex="20" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="吸尘、采光、通风设备"/>                                    
                                                                          吸尘、采光、通风设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned20" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned20" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>21</th>
						                            <th><input chkIndex="21" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷枪清洗设备或溶剂收集设备"/>                                    
                                                                          喷枪清洗设备或溶剂收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned21" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned21" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>22</th>
						                            <th><input chkIndex="22" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned22" iptName="carBodySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="carBodySrvIsOwned22" iptName="carBodySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>电气系统维修业户人员、组织、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="12">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVA" value="${materForm.SELFCHECK_ELECTRICSYSSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVB" value="${materForm.SELFCHECK_ELECTRICSYSSRVB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVC" value="${materForm.SELFCHECK_ELECTRICSYSSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ELECTRICSYSSRVD" value="1" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVD==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ELECTRICSYSSRVD" value="0" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVD==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">质量检验人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVE" value="${materForm.SELFCHECK_ELECTRICSYSSRVE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVF" value="${materForm.SELFCHECK_ELECTRICSYSSRVF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ELECTRICSYSSRVG" value="1" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ELECTRICSYSSRVG" value="0" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">电器维修人员</td>
						            <td>不少于2人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVH" value="${materForm.SELFCHECK_ELECTRICSYSSRVH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVI" value="${materForm.SELFCHECK_ELECTRICSYSSRVI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_ELECTRICSYSSRVJ" value="1" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_ELECTRICSYSSRVJ" value="0" <c:if test="${materForm.SELFCHECK_ELECTRICSYSSRVJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">业务接待员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVK" value="${materForm.SELFCHECK_ELECTRICSYSSRVK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">价格结算员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVL" value="${materForm.SELFCHECK_ELECTRICSYSSRVL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">组织管理</th>
						            <td colspan="2">具有健全的经营管理体系，设置技术负责、业务受理、质量检验、文件资料管理、材料管理、仪器设备管理、价格结算、安全生产等岗位并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVM" value="${materForm.SELFCHECK_ELECTRICSYSSRVM }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">具有汽车维修质量承诺、进出厂登记、检验记录及技术档案管理、标准和计量管理、设备管理、人员技术培训等制度并严格实施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVN" value="${materForm.SELFCHECK_ELECTRICSYSSRVN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">维修过程、配件管理、费用结算、维修档案等应实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVO" value="${materForm.SELFCHECK_ELECTRICSYSSRVO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVP" value="${materForm.SELFCHECK_ELECTRICSYSSRVP }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于120㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_ELECTRICSYSSRVQ" value="${materForm.SELFCHECK_ELECTRICSYSSRVQ }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>电气系统维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#electricSysSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#electricSysSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="electricSysSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                          空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned1" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned1" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车故障电脑诊断仪"/>                                    
                                                                          汽车故障电脑诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned2" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned2" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                          数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned3" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned3" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="充电机"/>                                    
                                                                          充电机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned4" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned4" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电解液比重计"/>                                    
                                                                          电解液比重计</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned5" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned5" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="高频放电叉"/>                                    
                                                                          高频放电叉</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned6" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned6" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车前照灯检测仪（具备前照灯远、近光检验功能的自动跟踪式前照灯检测仪）"/>                                    
                                                                          汽车前照灯检测仪（具备前照灯远、近光检验功能的自动跟踪式前照灯检测仪）</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned7" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned7" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电路检测设备"/>                                    
                                                                          电路检测设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned8" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned8" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="蓄电池检测、充电设备"/>                                    
                                                                          蓄电池检测、充电设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned9" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned9" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned10" iptName="electricSysSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="electricSysSrvIsOwned10" iptName="electricSysSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>自动变速器维修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="15">人员</th>
						            <td style="width:140px;text-align:center;">业户管理负责人</td>
						            <td style="width:401px;">不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVA" value="${materForm.SELFCHECK_VARIATORSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">技术负责人</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVB" value="${materForm.SELFCHECK_VARIATORSRVB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历，或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVC" value="${materForm.SELFCHECK_VARIATORSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIATORSRVD" value="1" <c:if test="${materForm.SELFCHECK_VARIATORSRVD==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIATORSRVD" value="0" <c:if test="${materForm.SELFCHECK_VARIATORSRVD==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">质量检验人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVE" value="${materForm.SELFCHECK_VARIATORSRVE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVF" value="${materForm.SELFCHECK_VARIATORSRVF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIATORSRVG" value="1" <c:if test="${materForm.SELFCHECK_VARIATORSRVG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIATORSRVG" value="0" <c:if test="${materForm.SELFCHECK_VARIATORSRVG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">机修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVH" value="${materForm.SELFCHECK_VARIATORSRVH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVI" value="${materForm.SELFCHECK_VARIATORSRVI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIATORSRVJ" value="1" <c:if test="${materForm.SELFCHECK_VARIATORSRVJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIATORSRVJ" value="0" <c:if test="${materForm.SELFCHECK_VARIATORSRVJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="3">电器维修人员</td>
						            <td>不少于1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVK" value="${materForm.SELFCHECK_VARIATORSRVK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVL" value="${materForm.SELFCHECK_VARIATORSRVL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_VARIATORSRVM" value="1" <c:if test="${materForm.SELFCHECK_VARIATORSRVM==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_VARIATORSRVM" value="0" <c:if test="${materForm.SELFCHECK_VARIATORSRVM==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">业务接待员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVN" value="${materForm.SELFCHECK_VARIATORSRVN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">价格结算员</td>
						            <td>不少于1人，允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVO" value="${materForm.SELFCHECK_VARIATORSRVO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">组织管理</th>
						            <td colspan="2">具有健全的经营管理体系，设置技术负责、业务受理、质量检验、文件资料管理、材料管理、仪器设备管理、价格结算、安全生产等岗位并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVP" value="${materForm.SELFCHECK_VARIATORSRVP }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">具有汽车维修质量承诺、进出厂登记、检验记录及技术档案管理、标准和计量管理、设备管理、人员技术培训等制度并严格实施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVQ" value="${materForm.SELFCHECK_VARIATORSRVQ }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">维修过程、配件管理、费用结算、维修档案等应实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVR" value="${materForm.SELFCHECK_VARIATORSRVR }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.设有接待室，其面积应不小于20㎡，整洁明亮，并有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVS" value="${materForm.SELFCHECK_VARIATORSRVS }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.生产厂房面积应不小于200㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_VARIATORSRVT" value="${materForm.SELFCHECK_VARIATORSRVT }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>自动变速器维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#variatorSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#variatorSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="variatorSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="自动变速器翻转设备"/>                                    
                                                                          自动变速器翻转设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned1" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned1" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="自动变速器拆解设备"/>                                    
                                                                          自动变速器拆解设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned2" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned2" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="变扭器维修设备"/>                                    
                                                                          变扭器维修设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned3" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned3" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="变扭器切割设备"/>                                    
                                                                          变扭器切割设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned4" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned4" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="变扭器焊接设备"/>                                    
                                                                          变扭器焊接设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned5" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned5" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="变扭器检测（漏）设备"/>                                    
                                                                          变扭器检测（漏）设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned6" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned6" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="零件清洗设备"/>                                    
                                                                          零件清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned7" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned7" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电控变速器测试仪"/>                                    
                                                                          电控变速器测试仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned8" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned8" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="油路总成测试机"/>                                    
                                                                          油路总成测试机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned9" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned9" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="液压油压力表"/>                                    
                                                                           液压油压力表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned10" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned10" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="自动变速器总成测试机"/>                                    
                                                                           自动变速器总成测试机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned11" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned11" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>12</th>
						                            <th><input chkIndex="12" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="自动变速器专用测量器具"/>                                    
                                                                           自动变速器专用测量器具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned12" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned12" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>13</th>
						                            <th><input chkIndex="13" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                           空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned13" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned13" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>14</th>
						                            <th><input chkIndex="14" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                           数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned14" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned14" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>15</th>
						                            <th><input chkIndex="15" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="废油收集设备"/>                                    
                                                                           废油收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned15" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned15" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>16</th>
						                            <th><input chkIndex="16" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned16" iptName="variatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="variatorSrvIsOwned16" iptName="variatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>轮胎动平衡和修补业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">轮胎维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_TIRESRVA" value="${materForm.SELFCHECK_TIRESRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_TIRESRVB" value="1" <c:if test="${materForm.SELFCHECK_TIRESRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_TIRESRVB" value="0" <c:if test="${materForm.SELFCHECK_TIRESRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于15㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_TIRESRVC" value="${materForm.SELFCHECK_TIRESRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>轮胎动平衡和修补业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#tireSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#tireSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="tireSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                           空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned1" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned1" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎漏气试验设备"/>                                    
                                                                           轮胎漏气试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned2" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned2" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎气压表"/>                                    
                                                                           轮胎气压表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned3" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned3" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎花纹深度尺"/>                                    
                                                                           轮胎花纹深度尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned4" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned4" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车千斤顶"/>                                    
                                                                           汽车千斤顶</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned5" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned5" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎螺母拆装机或专用工具"/>                                    
                                                                           轮胎螺母拆装机或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned6" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned6" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎轮辋拆装设备或专用工具"/>                                    
                                                                           轮胎轮辋拆装设备或专用工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned7" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned7" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎修补设备"/>                                    
                                                                           轮胎修补设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned8" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned8" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车轮动平衡机"/>                                    
                                                                           车轮动平衡机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned9" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned9" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned10" iptName="tireSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireSrvIsOwned10" iptName="tireSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>四轮定位检测调整业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">汽车维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_TIRELOCATESRVA" value="${materForm.SELFCHECK_TIRELOCATESRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_TIRELOCATESRVB" value="1" <c:if test="${materForm.SELFCHECK_TIRELOCATESRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_TIRELOCATESRVB" value="0" <c:if test="${materForm.SELFCHECK_TIRELOCATESRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于40㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_TIRELOCATESRVC" value="${materForm.SELFCHECK_TIRELOCATESRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>四轮定位检测调整业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#tireLocateSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#tireLocateSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="tireLocateSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车举升机"/>                                    
                                                                           汽车举升机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned1" iptName="tireLocateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned1" iptName="tireLocateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="四轮定位仪"/>                                    
                                                                           四轮定位仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned2" iptName="tireLocateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned2" iptName="tireLocateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                           空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned3" iptName="tireLocateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned3" iptName="tireLocateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="轮胎气压表"/>                                    
                                                                           轮胎气压表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned4" iptName="tireLocateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned4" iptName="tireLocateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned5" iptName="tireLocateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="tireLocateSrvIsOwned5" iptName="tireLocateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>汽车润滑与养护业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">汽车维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_LUBRICATESRVA" value="${materForm.SELFCHECK_LUBRICATESRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_LUBRICATESRVB" value="1" <c:if test="${materForm.SELFCHECK_LUBRICATESRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_LUBRICATESRVB" value="0" <c:if test="${materForm.SELFCHECK_LUBRICATESRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于40㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_LUBRICATESRVC" value="${materForm.SELFCHECK_LUBRICATESRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>汽车润滑与养护业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#lubricateSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#lubricateSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="lubricateSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="不解体油路清洗设备"/>                                    
                                                                           不解体油路清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned1" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned1" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="废油收集设备"/>                                    
                                                                           废油收集设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned2" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned2" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="齿轮油加注设备"/>                                    
                                                                           齿轮油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned3" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned3" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="液压油加注设备"/>                                    
                                                                           液压油加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned4" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned4" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="制动液更换加注器"/>                                    
                                                                           制动液更换加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned5" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned5" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="脂类加注器"/>                                    
                                                                           脂类加注器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned6" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned6" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车举升机或地沟设施"/>                                    
                                                                           汽车举升机或地沟设施</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned7" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned7" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                            空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned8" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned8" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned9" iptName="lubricateSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="lubricateSrvIsOwned9" iptName="lubricateSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>喷油泵和喷油器维修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">喷油泵喷油器维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_INJECTORSRVA" value="${materForm.SELFCHECK_INJECTORSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_INJECTORSRVB" value="1" <c:if test="${materForm.SELFCHECK_INJECTORSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_INJECTORSRVB" value="0" <c:if test="${materForm.SELFCHECK_INJECTORSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于30㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_INJECTORSRVC" value="${materForm.SELFCHECK_INJECTORSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>喷油泵和喷油器维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#injectorSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#injectorSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="injectorSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷油泵、喷油器清洗和试验设备"/>                                    
                                                                            喷油泵、喷油器清洗和试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned1" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned1" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷油泵、喷油器密封性试验设备"/>                                    
                                                                             喷油泵、喷油器密封性试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned2" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned2" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="弹簧试验仪"/>                                    
                                                                             弹簧试验仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned3" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned3" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="千分尺"/>                                    
                                                                             千分尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned4" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned4" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="厚薄规"/>                                    
                                                                             厚薄规</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned5" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned5" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned6" iptName="injectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="injectorSrvIsOwned6" iptName="injectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						                <div class="syj-title1 clear">
						                    <span>从事电控喷油泵、喷油器维修还需配备</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#elecInjectorSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#elecInjectorSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="elecInjectorSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电控喷油泵、喷油器检测台"/>                                    
                                                                             电控喷油泵、喷油器检测台</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned1" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned1" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电控喷油泵、喷油器专用拆装工具"/>                                    
                                                                             电控喷油泵、喷油器专用拆装工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned2" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned2" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="电控柴油机故障诊断仪"/>                                    
                                                                             电控柴油机故障诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned3" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned3" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="超声波清洗仪"/>                                    
                                                                             超声波清洗仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned4" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned4" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="专用工作台"/>                                    
                                                                             专用工作台</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned5" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned5" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned6" iptName="elecInjectorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="elecInjectorSrvIsOwned6" iptName="elecInjectorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>曲轴修磨业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">曲轴修磨人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_CRANKSRVA" value="${materForm.SELFCHECK_CRANKSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CRANKSRVB" value="1" <c:if test="${materForm.SELFCHECK_CRANKSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_CRANKSRVB" value="0" <c:if test="${materForm.SELFCHECK_CRANKSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于60㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CRANKSRVC" value="${materForm.SELFCHECK_CRANKSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>曲轴修磨业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#crankSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#crankSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="crankSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴磨床"/>                                    
                                                                             曲轴磨床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned1" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned1" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴校正设备"/>                                    
                                                                             曲轴校正设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned2" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned2" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="曲轴动平衡设备"/>                                    
                                                                             曲轴动平衡设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned3" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned3" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="平板"/>                                    
                                                                             平板</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned4" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned4" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="V型块"/>                                    
                                                                             V型块</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned5" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned5" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="百分表及磁力表座"/>                                    
                                                                             百分表及磁力表座</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned6" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned6" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="外径千分尺"/>                                    
                                                                             外径千分尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned7" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned7" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="无损探伤设备"/>                                    
                                                                             无损探伤设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned8" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned8" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="吊装设备"/>                                    
                                                                             吊装设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned9" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned9" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned10" iptName="crankSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="crankSrvIsOwned10" iptName="crankSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>气缸镗磨业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">气缸镗磨人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_CYLINDERSRVA" value="${materForm.SELFCHECK_CYLINDERSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_CYLINDERSRVB" value="1" <c:if test="${materForm.SELFCHECK_CYLINDERSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_CYLINDERSRVB" value="0" <c:if test="${materForm.SELFCHECK_CYLINDERSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于60㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_CYLINDERSRVC" value="${materForm.SELFCHECK_CYLINDERSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>气缸镗磨业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#cylinderSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#cylinderSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="cylinderSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="立式精镗床"/>                                    
                                                                             立式精镗床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned1" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned1" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="立式珩磨机"/>                                    
                                                                             立式珩磨机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned2" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned2" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="压床"/>                                    
                                                                             压床</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned3" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned3" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="吊装起重设备"/>                                    
                                                                             吊装起重设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned4" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned4" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="气缸体水压试验设备"/>                                    
                                                                             气缸体水压试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned5" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned5" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="量缸表"/>                                    
                                                                             量缸表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned6" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned6" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="外径千分尺"/>                                    
                                                                             外径千分尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned7" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned7" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="厚薄规"/>                                    
                                                                             厚薄规</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned8" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned8" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="平板"/>                                    
                                                               平板</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned9" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned9" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>10</th>
						                            <th><input chkIndex="10" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="激光淬火设备"/>                                    
                                                               激光淬火设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned10" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned10" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="从事激光淬火必备"/>                                    
                                                                           从事激光淬火必备</td>
						                        </tr>
						                        <tr>
						                            <th>11</th>
						                            <th><input chkIndex="11" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned11" iptName="cylinderSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="cylinderSrvIsOwned11" iptName="cylinderSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>散热器维修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">散热器维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_RADIATORSRVA" value="${materForm.SELFCHECK_RADIATORSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_RADIATORSRVB" value="1" <c:if test="${materForm.SELFCHECK_RADIATORSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_RADIATORSRVB" value="0" <c:if test="${materForm.SELFCHECK_RADIATORSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于30㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_RADIATORSRVC" value="${materForm.SELFCHECK_RADIATORSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>散热器维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#radiatorSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#radiatorSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="radiatorSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="清洗及管道疏通设备"/>                                    
                                                               清洗及管道疏通设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned1" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned1" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="气焊设备"/>                                    
                                                               气焊设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned2" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned2" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="钎焊设备"/>                                    
                                                                         钎焊设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned3" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned3" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空气压缩机"/>                                    
                                                                         空气压缩机</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned4" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned4" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="喷枪等喷漆设备"/>                                    
                                                                         喷枪等喷漆设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned5" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned5" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="散热器密封试验设备"/>                                    
                                                                          散热器密封试验设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned6" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned6" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned7" iptName="radiatorSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="radiatorSrvIsOwned7" iptName="radiatorSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>空调维修业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">汽车空调维修人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_AIRCONDSRVA" value="${materForm.SELFCHECK_AIRCONDSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_AIRCONDSRVB" value="1" <c:if test="${materForm.SELFCHECK_AIRCONDSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_AIRCONDSRVB" value="0" <c:if test="${materForm.SELFCHECK_AIRCONDSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于40㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_AIRCONDSRVC" value="${materForm.SELFCHECK_AIRCONDSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>空调维修业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#airCondSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#airCondSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="airCondSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒鉴别设备"/>                                    
                                                                          空调冷媒鉴别设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned1" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned1" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒回收净化加注设备"/>                                    
                                                                          空调冷媒回收净化加注设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned2" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned2" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调专用检测设备（具有空调高低端压力和电器检测功能）"/>                                    
                                                                          空调专用检测设备（具有空调高低端压力和电器检测功能）</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned3" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned3" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="空调冷媒检漏设备"/>                                    
                                                                          空调冷媒检漏设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned4" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned4" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式万用表"/>                                    
                                                                          数字式万用表</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned5" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned5" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="数字式温度计"/>                                    
                                                                          数字式温度计</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned6" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned6" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="汽车故障电脑诊断仪"/>                                    
                                                                          汽车故障电脑诊断仪</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned7" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned7" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned8" iptName="airCondSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="airCondSrvIsOwned8" iptName="airCondSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>汽车美容装潢业户人员、设施和设备条件</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="4">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">汽车装潢人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_BEAUTYSRVA" value="${materForm.SELFCHECK_BEAUTYSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_BEAUTYSRVB" value="1" <c:if test="${materForm.SELFCHECK_BEAUTYSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_BEAUTYSRVB" value="0" <c:if test="${materForm.SELFCHECK_BEAUTYSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;" rowspan="2">汽车清洁人员</td>
						            <td>不少于2人。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_BEAUTYSRVC" value="${materForm.SELFCHECK_BEAUTYSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_BEAUTYSRVD" value="1" <c:if test="${materForm.SELFCHECK_BEAUTYSRVD==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_BEAUTYSRVD" value="0" <c:if test="${materForm.SELFCHECK_BEAUTYSRVD==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于40㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_BEAUTYSRVE" value="${materForm.SELFCHECK_BEAUTYSRVE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>汽车美容装潢业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#beautySrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#beautySrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="beautySrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="车身清洗设备"/>                                    
                                                                          车身清洗设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned1" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned1" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="吸尘设备"/>                                    
                                                                          吸尘设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned2" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned2" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="除尘除垢设备"/>                                    
                                                                          除尘除垢设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned3" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned3" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="打蜡设备"/>                                    
                                                                          打蜡设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned4" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned4" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="抛光设备"/>                                    
                                                                          抛光设备</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned5" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned5" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="贴膜专业工具"/>                                    
                                                                          贴膜专业工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned6" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned6" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned7" iptName="beautySrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="beautySrvIsOwned7" iptName="beautySrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
						    <span>汽车玻璃安装及修复业户人员、设施和设备条件</span>
						</div>
					    <div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:50px;">项目</th>
						            <th style="width:555px;">基本要求</th>
						            <th style="width:305px;">业户自查情况</th>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="2">人员</th>
						            <td style="width:140px;text-align:center;" rowspan="2">汽车玻璃安装及修复人员</td>
						            <td style="width:401px;">不少于1人。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECK_GLASSSRVA" value="${materForm.SELFCHECK_GLASSSRVA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_GLASSSRVB" value="1" <c:if test="${materForm.SELFCHECK_GLASSSRVB==1}"> checked="checked"</c:if>>已培训<br/>
										<input type="radio" name="SELFCHECK_GLASSSRVB" value="0" <c:if test="${materForm.SELFCHECK_GLASSSRVB==0}"> checked="checked"</c:if>>未培训
						            </td>
						        </tr>
						        <tr>
						            <th>设施</th>
						            <td colspan="2">生产厂房面积应不小于30㎡；停车场面积应不小于30㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_GLASSSRVC" value="${materForm.SELFCHECK_GLASSSRVC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>设备</th>
						            <td colspan="3">
						                <div class="syj-title1 clear">
						                    <span>汽车玻璃安装及修复业户设备条件</span>
						                    <c:if test="${operType=='write' }">
											    <div style="float:right;">
													<a href="javascript:void(0);" class="del-btn" onclick="$('#glassSrvDeviceTable').MyEditableTable('deleteRow');">删除</a>
												    <a href="javascript:void(0);" class="add-btn" onclick="$('#glassSrvDeviceTable').MyEditableTable('appendRow');">增加</a>
												</div>
											</c:if>
						                </div>
						                <div style="position:relative;">
						                    <table id="glassSrvDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
						                        <tr>
						                            <th style="width:30px;">序号</th>
						            				<th style="width:30px;"><input type="checkbox"/></th>
						            				<th style="width:120px;">名称</th>
										            <th style="width:60px;">数量</th>
										            <th style="width:120px;">型号</th>
										            <th style="width:120px;">出厂日期</th>
										            <th style="width:120px;">检定日期</th>
										            <th style="width:60px;">自备</th>
										            <th style="width:60px;">外协</th>
										            <th style="width:125px;">备注</th>
						                        </tr>
						                        <tr>
						                            <th>1</th>
						                            <th><input chkIndex="1" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="工作台"/>                                    
                                                                          工作台</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned1" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned1" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>2</th>
						                            <th><input chkIndex="2" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="玻璃切割工具"/>                                    
                                                                           玻璃切割工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned2" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned2" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>3</th>
						                            <th><input chkIndex="3" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="注胶工具"/>                                    
                                                                           注胶工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned3" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned3" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>4</th>
						                            <th><input chkIndex="4" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="玻璃固定工具"/>                                    
                                                                           玻璃固定工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned4" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned4" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>5</th>
						                            <th><input chkIndex="5" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="玻璃拆装工具"/>                                    
                                                                           玻璃拆装工具</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned5" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned5" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>6</th>
						                            <th><input chkIndex="6" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="直尺"/>                                    
                                                                           直尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned6" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned6" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>7</th>
						                            <th><input chkIndex="7" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="弯尺"/>                                    
                                                                            弯尺</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned7" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned7" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>8</th>
						                            <th><input chkIndex="8" disabled type="checkbox"/></th>
						                            <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="devName" value="吸尘器"/>                                    
                                                                            吸尘器</td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned8" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned8" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                        <input type="hidden" class="syj-input1" iptName="devRemark" value="必备"/>                                    
                                                                           必备</td>
						                        </tr>
						                        <tr>
						                            <th>9</th>
						                            <th><input chkIndex="9" type="checkbox"/></th>
						                            <td>
						                                <input type="text" class="syj-input1" iptName="devName" maxlength="32" style="width:90%;"/>
						                            </td>
						                            <td>
						                                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						                            </td>
						                            <td>
										                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td>
										                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned9" iptName="glassSrvIsOwned" value="0"/>
										            </td>
										            <td style="text-align:center;">
										                <input type="radio" name="glassSrvIsOwned9" iptName="glassSrvIsOwned" value="1"/>
										            </td>
										            <td>
                                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                                    </td>
						                        </tr>
						                    </table>
						                </div>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 clear tmargin20">
						    <span>从业人员配备一览表
							    <a class="answer" href="javascript:void(0);" title="此表按业户管理负责人等管理人员、技术负责人、质量检验人员、业务接待员、价格结算员、机修人员、电器维修人员、钣金人员、涂漆人员、检测人员和其他人员等顺序自上而下填写。"></a>
							</span>
							<c:if test="${operType=='write' }">
							    <div style="float:right;">
									<a href="javascript:void(0);" class="del-btn" onclick="$('#staffInfoTable').MyEditableTable('deleteRow');">删除</a>
								    <a href="javascript:void(0);" class="add-btn" onclick="$('#staffInfoTable').MyEditableTable('appendRow');">增加</a>
								</div>
							</c:if>
						</div>
						<div style="position:relative;">
						    <table id="staffInfoTable" class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:90px;"><font class="syj-color2">*</font>姓名</th>
						            <th style="width:60px;"><font class="syj-color2">*</font>性别</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>出生年月</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>学历</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>所学专业</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>技术职称</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>技能工种及等级</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>所在岗位</th>
						            <th style="width:90px;">备注</th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="staffName" maxlength="16" style="width:89%;"/>
						            </td>
						            <td>
						                <select class="input-dropdown validate[required]" iptName="staffSex" style="width:97%;">
						                    <option value=""></option>
						                    <option value="1">男</option>
						                    <option value="2">女</option>
						                </select>
						            </td>
						            <td>
						                <input type="text" class="Wdate validate[required]" iptName="staffBirthDate" maxlength="16" style="width:89%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <!-- <input type="text" class="syj-input1 validate[required]" iptName="staffEdu" maxlength="8" style="width:89%;"/> -->
						                <select class="input-dropdown validate[required]" iptName="staffEdu" style="width:97%;">
						                    <option value=""></option>
						                    <option value="1">研究生</option>
						                    <option value="2">大学本科</option>
						                    <option value="3">大学专科</option>
						                    <option value="4">中专</option>
						                    <option value="5">技校</option>
						                    <option value="6">高中</option>
						                    <option value="7">初中</option>
						                    <option value="8">小学</option>
						                    <option value="9">文盲</option>
						                </select>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="staffSpecialty" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="staffJobtitle" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="staffWorktype" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="staffPosition" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffRemark" maxlength="64" style="width:87%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
                    </form>
                </div>
            </div>
        </div>
        <%-- <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#CCRAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div> --%>
    </div>
</body>
</html>