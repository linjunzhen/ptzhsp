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
	    	AppUtil.initWindowForm("ABCRAPPLY_FORM", function(form, valid){
	    		if(valid){
	    			//组装企业管理负责人json
	    			var leaderJson = $('#leaderTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='LEADER_JSON']").val(JSON.stringify(leaderJson));
	    			//组装技术负责人json
	    			var techleaderJson = $('#techleaderTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='TECHLEADER_JSON']").val(JSON.stringify(techleaderJson));
	    			//组装质量检验人员json
	    			var qapersonJson = $('#qapersonTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='QAPERSON_JSON']").val(JSON.stringify(qapersonJson));
	    			//组装从业人员json
	    			var staffJson = $('#staffInfoTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='STAFF_JSON']").val(JSON.stringify(staffJson));
	    			//组装仪表工具json
	    			var instrumentJson = $('#instrumentTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='INSTRUMENT_JSON']").val(JSON.stringify(instrumentJson));
	    			//组装专用设备json
	    			var dedicatedDeviceJson = $('#dedicatedDeviceTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='DEDICATEDDEVICE_JSON']").val(JSON.stringify(dedicatedDeviceJson));
	    			//组装检测设备json
	    			var inspectDeviceJson = $('#inspectDeviceTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='INSPECTDEVICE_JSON']").val(JSON.stringify(inspectDeviceJson));
	    			//组装通用设备json
	    			var commonDeviceJson = $('#commonDeviceTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='COMMONDEVICE_JSON']").val(JSON.stringify(commonDeviceJson));
	    			//组装危险货物运输专用设施设备json
	    			var specialDeviceJson = $('#sepcialDeviceTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='SPECIALDEVICE_JSON']").val(JSON.stringify(specialDeviceJson));
	    			//组装安全管理人员json
	    			var safetyAdminJson = $('#safetyAdminTable').MyEditableTable('getData');
	    			$("#ABCRAPPLY_FORM").find("input[name='SAFETYADMIN_JSON']").val(JSON.stringify(safetyAdminJson));
					
						//序列化表单
						var formData = $("#ABCRAPPLY_FORM").serialize();
						var url = $("#ABCRAPPLY_FORM").attr("action");
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
	    	
	    	//实例化企业管理负责人表格及初始化数据
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
	    	//var leaderJson = $("#ABCRAPPLY_FORM").find("input[name='LEADER_JSON']").val();
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
	    	//var techleaderJson = $("#ABCRAPPLY_FORM").find("input[name='TECHLEADER_JSON']").val();
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
	    	//var qapersonJson = $("#ABCRAPPLY_FORM").find("input[name='QAPERSON_JSON']").val();
	    	var qapersonJson = '${materForm.QAPERSON_JSON }';
	    	if(qapersonJson != '' && qapersonJson != '[]'){
	    		$('#qapersonTable').MyEditableTable("loadData", JSON.parse(qapersonJson));
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
	    	//var staffJson = $("#ABCRAPPLY_FORM").find("input[name='STAFF_JSON']").val();
	    	var staffJson = '${materForm.STAFF_JSON }';
	    	if(staffJson != '' && staffJson != '[]'){
	    		$('#staffInfoTable').MyEditableTable("loadData", JSON.parse(staffJson));
	    	}
	    	//实例化仪表工具表格及初始化数据
	    	$('#instrumentTable').MyEditableTable();
	    	//var instrumentJson = $("#ABCRAPPLY_FORM").find("input[name='INSTRUMENT_JSON']").val();
	    	var instrumentJson = '${materForm.INSTRUMENT_JSON }';
	    	if(instrumentJson != '' && instrumentJson != '[]'){
	    		$('#instrumentTable').MyEditableTable("loadDataExtend", JSON.parse(instrumentJson));
	    	}
	    	//实例化专用设备表格及初始化数据
	    	$('#dedicatedDeviceTable').MyEditableTable();
	    	//var dedicatedDeviceJson = $("#ABCRAPPLY_FORM").find("input[name='DEDICATEDDEVICE_JSON']").val();
	    	var dedicatedDeviceJson = '${materForm.DEDICATEDDEVICE_JSON }';
	    	if(dedicatedDeviceJson != '' && dedicatedDeviceJson != '[]'){
	    		$('#dedicatedDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(dedicatedDeviceJson));
	    	}
	    	//实例化检测设备表格及初始化数据
	    	$('#inspectDeviceTable').MyEditableTable();
	    	//var inspectDeviceJson = $("#ABCRAPPLY_FORM").find("input[name='INSPECTDEVICE_JSON']").val();
	    	var inspectDeviceJson = '${materForm.INSPECTDEVICE_JSON }';
	    	if(inspectDeviceJson != '' && inspectDeviceJson != '[]'){
	    		$('#inspectDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(inspectDeviceJson));
	    	}
	    	//实例化通用设备表格及初始化数据
	    	$('#commonDeviceTable').MyEditableTable();
	    	//var commonDeviceJson = $("#ABCRAPPLY_FORM").find("input[name='COMMONDEVICE_JSON']").val();
	    	var commonDeviceJson = '${materForm.COMMONDEVICE_JSON }';
	    	if(commonDeviceJson != '' && commonDeviceJson != '[]'){
	    		$('#commonDeviceTable').MyEditableTable("loadDataExtend", JSON.parse(commonDeviceJson));
	    	}
	    	//实例化危险运输车维修专用设施设备表格及初始化数据
	    	$('#sepcialDeviceTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 0){
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
	    	//var specialDeviceJson = $("#ABCRAPPLY_FORM").find("input[name='SPECIALDEVICE_JSON']").val();
	    	var specialDeviceJson = '${materForm.SPECIALDEVICE_JSON }';
	    	if(specialDeviceJson != '' && specialDeviceJson != '[]'){
	    		$('#sepcialDeviceTable').MyEditableTable("loadData", JSON.parse(specialDeviceJson));
	    	}
	    	//实例化安全管理人员表格及初始化数据
	    	$('#safetyAdminTable').MyEditableTable({
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 0){
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
	    	//var safetyAdminJson = $("#ABCRAPPLY_FORM").find("input[name='SAFETYADMIN_JSON']").val();
	    	var safetyAdminJson = '${materForm.SAFETYADMIN_JSON }';
	    	if(safetyAdminJson != '' && safetyAdminJson != '[]'){
	    		$('#safetyAdminTable').MyEditableTable("loadData", JSON.parse(safetyAdminJson));
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
                    <form id="ABCRAPPLY_FORM" action="domesticControllerController/saveRelatedMaterForm.do" method="post">
                        <input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }" id="childRecord"/>
						<input type="hidden" name="LEADER_JSON" value="${materForm.LEADER_JSON }"/>
						<input type="hidden" name="TECHLEADER_JSON" value="${materForm.TECHLEADER_JSON }"/>
						<input type="hidden" name="QAPERSON_JSON" value="${materForm.QAPERSON_JSON }"/>
						<input type="hidden" name="STAFF_JSON" value="${materForm.STAFF_JSON }"/>
						<input type="hidden" name="INSTRUMENT_JSON" value="${materForm.INSTRUMENT_JSON }"/>
						<input type="hidden" name="DEDICATEDDEVICE_JSON" value="${materForm.DEDICATEDDEVICE_JSON }"/>
						<input type="hidden" name="INSPECTDEVICE_JSON" value="${materForm.INSPECTDEVICE_JSON }"/>
						<input type="hidden" name="COMMONDEVICE_JSON" value="${materForm.COMMONDEVICE_JSON }"/>
						<input type="hidden" name="SPECIALDEVICE_JSON" value="${materForm.SPECIALDEVICE_JSON }"/>
						<input type="hidden" name="SAFETYADMIN_JSON" value="${materForm.SAFETYADMIN_JSON }"/>
						<div class="syj-title1">
							<span>维修企业基本概况1</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th>
						                <font class="syj-color2">*</font>企业名称：
						                <a class="answer" href="javascript:void(0);" title="按营业执照的企业名称填写"></a>
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
						                <input type="text" class="syj-input1 validate[required,custom[numberp6plus]" name="COMPANY_REGAMOUNT" value="${materForm.COMPANY_REGAMOUNT }" maxlength="14" />
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
						                <a class="answer" href="javascript:void(0);" title="分为一类汽车维修和二类汽车维修。一、二类汽车维修企业若为连锁型企业，应在上述经营类别之后增加“(连锁型总店)”、“(连锁型直营店)”或“(连锁型加盟店)”。"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_BUSTYPE" value="${materForm.COMPANY_BUSTYPE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>经营范围：
						                <a class="answer" href="javascript:void(0);" title="一、二类汽车维修企业经营范围分为乘用车维修、客车维修(小型客车维修)、客车维修、货车维修(小型货车维修)和货车维修。经营危险货物运输车辆维修的一类汽车维修企业，在经营范围中应增加“危险货物运输车辆维修”。"></a>
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
						                <font class="syj-color2">*</font>企业管理负责人：
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
							<span>维修企业基本概况2</span>
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
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="STAFF_TOTALCOUNT" value="${materForm.STAFF_TOTALCOUNT }" maxlength="10" style="width:92%;"/>
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
						                <font class="syj-color2">*</font>企业管理负责人
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
						                <font class="syj-color2">*</font>钣金(车身修复)人员
						            </th>
						            <td colspan="4">
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="METZLER_COUNT" value="${materForm.METZLER_COUNT }" maxlength="10" style="width:98%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="2">
						                <font class="syj-color2">*</font>涂漆(车身涂装)人员
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
							<span>组织管理条件</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th style="width:100px;">项目</th>
						            <th style="width:405px;">基本要求</th>
						            <th style="width:405px;"><font class="syj-color2">*</font>企业自查情况</th>
						        </tr>
						        <tr>
						            <th rowspan="5">经营管理</th>
						            <td>1.应建立健全组织管理机构，设置经营、技术、业务、质量、配件、检验、档案、设备、生产和安全环保等管理部门并落实责任人。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MANAGEA" value="${materForm.SELFCHECK_MANAGEA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.具有现行有效的与汽车维修有关的法律、法规、规章和标准等文件资料。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MANAGEB" value="${materForm.SELFCHECK_MANAGEB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>3.具有规范的业务工作流程，公开业务受理程序、服务承诺和用户抱怨受理程序等，并明示经营许可证、标志牌、配件价格、工时定额和价格标准等。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MANAGEC" value="${materForm.SELFCHECK_MANAGEC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.建立并执行价格备案及公示、汽车维修合同、汽车维修费用结算清单、汽车维修记录、统计信息报送和安全生产管理等制度。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MANAGED" value="${materForm.SELFCHECK_MANAGED }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>5.维修过程、配件管理、费用结算和维修档案等实现电子化管理。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MANAGEE" value="${materForm.SELFCHECK_MANAGEE }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="4">质量管理</th>
						            <td>1.建立完善的质量管理体系。建立并执行汽车维修质量承诺、进出厂登记、检验、竣工出厂合格证管理、汽车维修档案管理、标准和计量管理、设备管理、配件管理、文件资料有效控制和人员培训等制度。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QUALITYA" value="${materForm.SELFCHECK_QUALITYA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.汽车维修档案应包括维修合同（托修单），进厂、过程、竣工检验记录，竣工出厂合格证存根，维修结算清单，材料清单等。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QUALITYB" value="${materForm.SELFCHECK_QUALITYB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>3.配件管理制度应规定配件采购、检查验收、库房管理、信息追溯、配件登记及台账、索赔等要求。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QUALITYC" value="${materForm.SELFCHECK_QUALITYC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.应具有所维修车型的维修技术资料及工艺文件，确保完整有效并及时更新。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QUALITYD" value="${materForm.SELFCHECK_QUALITYD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>人员条件</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th style="width:100px;">岗位</th>
						            <th style="width:405px;">基本要求</th>
						            <th style="width:405px;"><font class="syj-color2">*</font>企业自查情况</th>
						        </tr>
						        <tr>
						            <th>总体要求</th>
						            <td>应具有维修企业管理负责人、维修技术负责人、维修质量检验人员、维修业务接待员、维修价格结算员、机修人员、电器维修人员、钣金（车身修复）人员和涂漆（车身涂装）人员。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PERSON" value="${materForm.SELFCHECK_PERSON }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>企业管理负责人</th>
						            <td>一类企业应至少配备1人，不能兼职。二类企业允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_LEADER" value="${materForm.SELFCHECK_LEADER }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">技术负责人</th>
						            <td>一类企业应至少配备1人，不能兼职。二类企业允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_TECHLEADERA" value="${materForm.SELFCHECK_TECHLEADERA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有汽车维修或相关专业大专及以上学历或者中级及以上专业技术职称（或技师）。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_TECHLEADERB" value="${materForm.SELFCHECK_TECHLEADERB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修业务，并掌握汽车维修及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_TECHLEADERC" value="1" <c:if test="${materForm.SELFCHECK_TECHLEADERC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_TECHLEADERC" value="0" <c:if test="${materForm.SELFCHECK_TECHLEADERC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">质量检验人员</th>
						            <td>一类汽车维修企业≥3人，不能兼职。二类汽车维修企业≥2人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QAPERSONA" value="${materForm.SELFCHECK_QAPERSONA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有高中及以上学历，并持有与承修车型种类相适应的机动车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_QAPERSONB" value="${materForm.SELFCHECK_QAPERSONB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车维修检测作业规范，掌握汽车维修故障诊断和质量检验的相关技术，熟悉汽车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_QAPERSONC" value="1" <c:if test="${materForm.SELFCHECK_QAPERSONC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_QAPERSONC" value="0" <c:if test="${materForm.SELFCHECK_QAPERSONC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th>业务接待员</th>
						            <td>一类企业应至少配备1人，不能兼职。二类企业允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_RECEPTIONIST" value="${materForm.SELFCHECK_RECEPTIONIST }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th>价格结算员</th>
						            <td>一类企业应至少各配备1人，不能兼职。二类企业允许一人两岗，可由允许兼职的其它岗位人员兼任。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_CLERK" value="${materForm.SELFCHECK_CLERK }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">机修人员</th>
						            <td>一类汽车维修企业≥5人，不能兼职。二类汽车维修企业≥3人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MECHANICA" value="${materForm.SELFCHECK_MECHANICA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_MECHANICB" value="${materForm.SELFCHECK_MECHANICB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车机修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_MECHANICC" value="1" <c:if test="${materForm.SELFCHECK_MECHANICC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_MECHANICC" value="0" <c:if test="${materForm.SELFCHECK_MECHANICC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">电器维修人员</th>
						            <td>一、二类汽车维修企业各≥1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_EAMAINTAINERA" value="${materForm.SELFCHECK_EAMAINTAINERA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_EAMAINTAINERB" value="${materForm.SELFCHECK_EAMAINTAINERB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车电器维修工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_EAMAINTAINERC" value="1" <c:if test="${materForm.SELFCHECK_EAMAINTAINERC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_EAMAINTAINERC" value="0" <c:if test="${materForm.SELFCHECK_EAMAINTAINERC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">钣金（车身修复）人员</th>
						            <td>一、二类汽车维修企业各≥1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_METZLERA" value="${materForm.SELFCHECK_METZLERA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_METZLERB" value="${materForm.SELFCHECK_METZLERB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车钣金工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_METZLERC" value="1" <c:if test="${materForm.SELFCHECK_METZLERC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_METZLERC" value="0" <c:if test="${materForm.SELFCHECK_METZLERC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">涂漆（车身涂装）人员</th>
						            <td>一、二类汽车维修企业各≥1人，不能兼职。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PAINTERA" value="${materForm.SELFCHECK_PAINTERA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>具有初中及以上学历。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PAINTERB" value="${materForm.SELFCHECK_PAINTERB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事汽车涂漆工种的维修技术和操作规范，了解机动车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_PAINTERC" value="1" <c:if test="${materForm.SELFCHECK_PAINTERC==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_PAINTERC" value="0" <c:if test="${materForm.SELFCHECK_PAINTERC==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">燃气汽车专修人员</th>
						            <td>燃气汽车维修企业应配备燃气汽车燃气供给系统维修作业及检验人员不少于1人。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_GASCARMAINTAINERA" value="${materForm.SELFCHECK_GASCARMAINTAINERA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉汽车燃气供给系统专业技术。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_GASCARMAINTAINERB" value="1" <c:if test="${materForm.SELFCHECK_GASCARMAINTAINERB==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_GASCARMAINTAINERB" value="0" <c:if test="${materForm.SELFCHECK_GASCARMAINTAINERB==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 clear tmargin20">
						    <span>从业人员配备一览表
							    <a class="answer" href="javascript:void(0);" title="此表按企业管理负责人等管理人员、技术负责人、质量检验人员、业务接待员、价格结算员、机修人员、电器维修人员、钣金人员、涂漆人员、检测人员、安全生产管理人员和其他 等顺序自上而下填写。"></a>
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
						                <!-- <input type="text" class="syj-input1 validate[required]" iptName="staffBirthDate" maxlength="16" style="width:89%;"/> -->
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
						<div class="syj-title1 tmargin20">
							<span>安全生产与环境保护条件</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th style="width:100px;">项目</th>
						            <th style="width:405px;">基本要求</th>
						            <th style="width:405px;"><font class="syj-color2">*</font>企业自查情况</th>
						        </tr>
						        <tr>
						            <th rowspan="7">安全生产</th>
						            <td>1.建立并实施与其维修作业内容相适应的安全管理制度和安全保护措施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYA" value="${materForm.SELFCHECK_WORKSAFETYA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.制定各类机电设备的安全操作规程，并明示在相应的工位或设备处。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYB" value="${materForm.SELFCHECK_WORKSAFETYB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>3.使用与存储有毒、易燃、易爆物品和粉尘、腐蚀剂、污染物、压力容器等，均应具备相应的安全防护措施和设施。安全防护设施应有明显的警示、禁令标志。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYC" value="${materForm.SELFCHECK_WORKSAFETYC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.安全、消防设施的设置地点应明示管理要求和操作规程。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYD" value="${materForm.SELFCHECK_WORKSAFETYD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>5.具有安全生产事故的应急预案。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYE" value="${materForm.SELFCHECK_WORKSAFETYE }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>6.配备安全生产管理人员。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_WORKSAFETYF" value="${materForm.SELFCHECK_WORKSAFETYF }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>7.安全生产管理人员经培训，熟知国家安全生产法律法规，具有汽车维修安全生产作业知识和安全生产管理能力。</td>
						            <td>
						                <input type="radio" name="SELFCHECK_WORKSAFETYG" value="1" <c:if test="${materForm.SELFCHECK_WORKSAFETYG==1}"> checked="checked"</c:if> class="validate[required]">已培训且符合要求<br/>
										<input type="radio" name="SELFCHECK_WORKSAFETYG" value="0" <c:if test="${materForm.SELFCHECK_WORKSAFETYG==0}"> checked="checked"</c:if> class="validate[required]">未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="4">环境保护</th>
						            <td>1.具有废油、废液、废气、废水（以下简称“四废”）、废蓄电池、废轮胎、含石棉废料及有害垃圾等物质集中收集、有效处理和保持环境整洁的环境保护管理制度，并有效执行。有害物质存储区域应界定清楚，必要时应有隔离、控制措施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_ENVIRPROTECTA" value="${materForm.SELFCHECK_ENVIRPROTECTA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>2.按生产工艺配置处理“四废”及采光、通风、吸尘、净化、消声等设施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_ENVIRPROTECTB" value="${materForm.SELFCHECK_ENVIRPROTECTB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>3.涂漆车间应设有专用的废水排放及处理设施，采用干打磨工艺的，应有粉尘收集装置和除尘设备，并应设有通风设备。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_ENVIRPROTECTC" value="${materForm.SELFCHECK_ENVIRPROTECTC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>4.调试车间或调试工位应设置汽车尾气收集净化装置。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_ENVIRPROTECTD" value="${materForm.SELFCHECK_ENVIRPROTECTD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>设施条件</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th style="width:100px;">项目</th>
						            <th style="width:405px;">基本要求</th>
						            <th style="width:405px;"><font class="syj-color2">*</font>企业自查情况</th>
						        </tr>
						        <tr>
						            <th rowspan="2">接待室(含客户休息室)</th>
						            <td>设有接待室。一类企业的接待室面积不小于80㎡，二类企业的接待室面积不小于20㎡。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_RECEPTIONROOMA" value="${materForm.SELFCHECK_RECEPTIONROOMA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>接待室应整洁明亮，明示各类证、照、主修车型、作业项目、工时定额及单价等，并应有供客户休息的设施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_RECEPTIONROOMB" value="${materForm.SELFCHECK_RECEPTIONROOMB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="6">生产厂房及场地</th>
						            <td>生产厂房地面应平整坚实。生产厂房面积应能满足设备的工位布置、生产工艺和正常作业，并与其经营业务相适应。一类企业的生产厂房面积不小于800㎡。二类乘用车类、小型客车类和小型货车类专业维修企业的生产厂房面积不小于300㎡，其他二类企业生产厂房面积不小于400㎡。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYA" value="${materForm.SELFCHECK_FACTORYA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>生产厂房内应设有总成维修间。一类企业总成维修间面积不小于30㎡，二类企业总成维修间面积不小于20㎡，并设置总成维修所需的工作台、拆装工具、计量器具等。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYB" value="${materForm.SELFCHECK_FACTORYB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>生产厂房内应设有预检工位，预检工位应有相应的故障诊断、检测设备。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYC" value="${materForm.SELFCHECK_FACTORYC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>租赁的生产厂房应具有合法的书面合同书（合同中应体现租赁面积），租赁期限不得少于1年。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYD" value="${materForm.SELFCHECK_FACTORYD }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>从事燃气汽车维修的企业，应有专用维修厂房，厂房应为永久性建筑，不得使用易燃建筑材料，面积应与生产规模相适应。厂房内通风良好，不得堆放可能危及安全的物品。厂房周围5m内不得有任何可能危及安全的设施。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYE" value="${materForm.SELFCHECK_FACTORYE }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>从事燃气汽车维修的企业，还应设有密封性检查、卸压操作的专用场地，可以设在室外。应远离火源，应明示防明火、防静电的标志。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_FACTORYF" value="${materForm.SELFCHECK_FACTORYF }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="3">停车场</th>
						            <td>应有与承修车型、经营规模相适应的合法停车场地，并保证车辆行驶通畅。一类乘用车类、小型客车类和小型货车类专业维修企业的停车场面积不小于200㎡，其他一类企业停车场面积不小于300㎡。二类企业的停车场面积不小于150㎡。不得占用公共用地。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PARKINGLOTA" value="${materForm.SELFCHECK_PARKINGLOTA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>停车场地面应平整坚实，区域界定标志明显。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PARKINGLOTB" value="${materForm.SELFCHECK_PARKINGLOTB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>租赁的停车场地应具有合法的书面合同书（合同中应体现租赁面积），租赁期限不得少于1年。</td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="SELFCHECK_PARKINGLOTC" value="${materForm.SELFCHECK_PARKINGLOTC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>汽车维修企业设备配备条件
							    <a class="answer" href="javascript:void(0);" title="1.各种设备应能满足加工、检测精度的要求和使用的要求，并应符合相关国家标准和行业标准的要求。2.允许外协的设备，应具有合法的合同书，其技术状况应符合相关国家标准和行业标准要求。"></a>
							</span>
						</div>
						<div style="position:relative;">
						    <table class="syj-table2" cellpadding="0" cellspacing="0">
						        <tr>
						            <th style="width:58px;">序号</th>
						            <th style="width:115px;">名称</th>
						            <th style="width:58px;"><font class="syj-color2">*</font>数量</th>
						            <th style="width:115px;"><font class="syj-color2">*</font>型号</th>
						            <th style="width:115px;"><font class="syj-color2">*</font>出厂日期</th>
						            <th style="width:115px;"><font class="syj-color2">*</font>检定日期</th>
						            <th style="width:58px;"><font class="syj-color2">*</font>自备</th>
						            <th style="width:58px;"><font class="syj-color2">*</font>外协</th>
						            <th style="width:220px;">备注</th>
						        </tr>
						    </table>
						    <table id="instrumentTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:58px;">一</th>
						            <th style="width:115px;">仪表工具</th>
						            <th style="width:58px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:220px;"></th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="数字式万用表"/>                                    
                                                                           数字式万用表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned1" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned1" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/>     </td>
						        </tr>
						        <tr>
						            <th>2</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="气缸压力表"/>                                    
                                                                           气缸压力表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned2" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned2" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>3</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="燃油压力表"/>                                    
                                                                            燃油压力表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned3" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned3" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/>   </td>
						        </tr>
						        <tr>
						            <th>4</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="真空表"/>                                    
                                                                            真空表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned4" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned4" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>5</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="轮胎气压表"/>                                    
                                                                            轮胎气压表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned5" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned5" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>6</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="轮胎花纹深度尺"/>                                    
                                                                            轮胎花纹深度尺</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned6" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned6" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>7</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="外径千分尺"/>                                    
                                                                            外径千分尺</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned7" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned7" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>8</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="内径千分尺"/>                                    
                                                                            内径千分尺</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned8" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned8" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>9</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="量缸表"/>                                    
                                                                            量缸表</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned9" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned9" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>10</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="游标卡尺"/>                                    
                                                                            游标卡尺</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned10" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned10" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>11</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="扭力扳手"/>                                    
                                                                            扭力扳手</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned11" iptName="instrumentIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned11" iptName="instrumentIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>12</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="气体压力及流量检测仪"/>                                    
                                                                            气体压力及流量检测仪</th>
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
						                <input type="radio" name="instrumentIsOwned12" iptName="instrumentIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned12" iptName="instrumentIsOwned" value="1"/>
						            </td>
						            <td>燃气汽车维修企业必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="燃气汽车维修企业必备"/></td>
						        </tr>
						        <tr>
						            <th>13</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="便携式气体检漏仪"/>                                    
                                                                            便携式气体检漏仪</th>
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
						                <input type="radio" name="instrumentIsOwned13" iptName="instrumentIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="instrumentIsOwned13" iptName="instrumentIsOwned" value="1"/>
						            </td>
						            <td>燃气汽车维修企业必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="燃气汽车维修企业必备"/></td>
						        </tr>
						    </table>
						    <table id="dedicatedDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:58px;">二</th>
						            <th style="width:115px;">专用设备</th>
						            <th style="width:58px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:220px;"></th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="废油收集设备"/>                                    
                                                                            废油收集设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned1" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned1" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>2</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="齿轮油加注设备"/>                                    
                                                                            齿轮油加注设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned2" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned2" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>3</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="液压油加注设备"/>                                    
                                                                            液压油加注设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned3" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned3" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>4</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="制动液更换加注器"/>                                    
                                                                            制动液更换加注器</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned4" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned4" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>5</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="脂类加注器"/>                                    
                                                                            脂类加注器</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned5" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned5" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>6</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="轮胎轮辋拆装设备"/>                                    
                                                                            轮胎轮辋拆装设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned6" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned6" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>7</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="轮胎螺母拆装机"/>                                    
                                                                            轮胎螺母拆装机</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned7" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned7" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、货车类必备<input type="hidden" class="syj-input1" iptName="remark" value="客车类、货车类必备"/></td>
						        </tr>
						        <tr>
						            <th>8</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车轮动平衡机"/>                                    
                                                                            车轮动平衡机</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned8" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned8" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>9</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="四轮定位仪"/>                                    
                                                                            四轮定位仪</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned9" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned9" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>一类乘用车类必备，二类乘用车允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="一类乘用车类必备，二类乘用车允许外协"/></td>
						        </tr>
						        <tr>
						            <th>10</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="转向轮定位仪或四轮定位仪"/>                                    
                                                                            转向轮定位仪或四轮定位仪</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned10" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned10" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>一类客车、货车类必备，二类客车、货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="一类客车、货车类必备，二类客车、货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>11</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="制动鼓和制动盘维修设备"/>                                    
                                                                            制动鼓和制动盘维修设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned11" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned11" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、货车类必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、货车类必备"/></td>
						        </tr>
						        <tr>
						            <th>12</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="空调冷媒鉴别设备"/>                                    
                                                                            空调冷媒鉴别设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned12" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned12" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备，货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备，货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>13</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="空调冷媒回收净化加注设备"/>                                    
                                                                            空调冷媒回收净化加注设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned13" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned13" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备，货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备，货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>14</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="空调专用检测设备（具备空调高低端压力和电器检测功能）"/>                                    
                                                                            空调专用检测设备（具备空调高低端压力和电器检测功能）</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned14" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned14" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备，货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备，货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>15</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="空调冷媒检漏设备"/>                                    
                                                                            空调冷媒检漏设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned15" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned15" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备，货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备，货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>16</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="总成吊装设备或变速箱等总成顶举设备"/>                                    
                                                                            总成吊装设备或变速箱等总成顶举设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned16" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned16" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>17</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="汽车举升机或具有安全逃生通道的地沟"/>                                    
                                                                            汽车举升机或具有安全逃生通道的地沟</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned17" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned17" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备，一类不少于5台（个），二类不少于2台（个）
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备，一类不少于5台（个），二类不少于2台（个）"/></td>
						        </tr>
						        <tr>
						            <th>18</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="汽车故障电脑诊断仪"/>                                    
                                                                            汽车故障电脑诊断仪</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned18" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned18" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>19</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="蓄电池检查、充电设备"/>                                    
                                                                            蓄电池检查、充电设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned19" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned19" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						             <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>20</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="无损探伤设备"/>                                    
                                                                            无损探伤设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned20" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned20" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类必备 <input type="hidden" class="syj-input1" iptName="remark" value="客车类必备"/></td>
						        </tr>
						        <tr>
						            <th>21</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车身清洗设备"/>                                    
                                                                            车身清洗设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned21" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned21" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>22</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="打磨抛光设备"/>                                    
                                                                            打磨抛光设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned22" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned22" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备<input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备"/></td>
						        </tr>
						        <tr>
						            <th>23</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="除尘除垢设备"/>                                    
                                                                            除尘除垢设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned23" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned23" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备<input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备"/></td>
						        </tr>
						        <tr>
						            <th>24</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车身整形设备"/>                                    
                                                                            车身整形设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned24" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned24" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>25</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车身校正设备"/>                                    
                                                                            车身校正设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned25" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned25" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>一类乘用车类必备，二类乘用车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="一类乘用车类必备，二类乘用车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>26</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车身尺寸测量设备"/>                                    
                                                                            车身尺寸测量设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned26" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned26" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>一类乘用车类必备，二类乘用车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="一类乘用车类必备，二类乘用车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>27</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="车架校正设备"/>                                    
                                                                            车架校正设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned27" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned27" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>一类客车、货车类必备，二类客车、货车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="一类客车、货车类必备，二类客车、货车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>28</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="悬架试验台"/>                                    
                                                                            悬架试验台</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned28" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned28" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>乘用车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="乘用车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>29</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="喷烤漆房及喷枪等设备"/>                                    
                                                                            喷烤漆房及喷枪等设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:80%;"/>
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
						                <input type="radio" name="dedicatedDeviceIsOwned29" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned29" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>乘用车类必备，客车类允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="乘用车类必备，客车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>30</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="调漆设备"/>                                    
                                                                            调漆设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned30" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned30" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类允许外协
						             <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类允许外协"/></td>
						        </tr>
						        <tr>
						            <th>31</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="喷油泵试验设备"/>                                    
                                                                             喷油泵试验设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned31" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned31" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>允许外协（针对柴油车）
						            <input type="hidden" class="syj-input1" iptName="remark" value="允许外协（针对柴油车）"/></td>
						        </tr>
						        <tr>
						            <th>32</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="喷油器试验设备"/>                                    
                                                                              喷油器试验设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned32" iptName="dedicatedDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned32" iptName="dedicatedDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>33</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="自动变速器维修设备"/>                                    
                                                                              自动变速器维修设备</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned33" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned33" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>具备自动变速器维修业户设备条件，允许外协
						            <input type="hidden" class="syj-input1" iptName="remark" value="具备自动变速器维修业户设备条件，允许外协"/></td>
						        </tr>
						        <tr>
						            <th>34</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="氮气置换装置"/>                                    
                                                                              氮气置换装置</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned34" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned34" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类必备（针对燃气汽车维修企业）
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备（针对燃气汽车维修企业）"/></td>
						        </tr>
						        <tr>
						            <th>35</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="气瓶支架强度校验装置"/>                                    
                                                                              气瓶支架强度校验装置</th>
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
						                <input type="radio" name="dedicatedDeviceIsOwned35" iptName="dedicatedDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="dedicatedDeviceIsOwned35" iptName="dedicatedDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、乘用车类允许外协（针对燃气汽车维修企业）
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、乘用车类必备（针对燃气汽车维修企业）"/></td>
						        </tr>
						    </table>
						    <table id="inspectDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:58px;">三</th>
						            <th style="width:115px;">检测设备</th>
						            <th style="width:58px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:220px;"></th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="汽车尾气排放检验设备"/>                                    
                                                                              汽车尾气排放检验设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned1" iptName="inspectDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned1" iptName="inspectDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备，配备与承修车型相适应的汽车尾气分析仪或不透光烟度计
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备，配备与承修车型相适应的汽车尾气分析仪或不透光烟度计"/></td>
						        </tr>
						        <tr>
						            <th>2</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="汽车前照灯检测仪"/>                                    
                                                                               汽车前照灯检测仪</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned2" iptName="inspectDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned2" iptName="inspectDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>3</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="侧滑试验台"/>                                    
                                                                               侧滑试验台</th>
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
						                <input type="radio" name="inspectDeviceIsOwned3" iptName="inspectDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned3" iptName="inspectDeviceIsOwned" value="1"/>
						            </td>
						            <td>1.客车类和小型货车类维修企业可配备单转向桥双板联动式侧滑检验台；2.货车类维修企业应配备具有单、双转向桥检验功能的双板联动式侧滑检验台；3.乘用车类视情选配
						            <input type="hidden" class="syj-input1" iptName="remark" value="1.客车类和小型货车类维修企业可配备单转向桥双板联动式侧滑检验台；2.货车类维修企业应配备具有单、双转向桥检验功能的双板联动式侧滑检验台；3.乘用车类视情选配"/></td>
						        </tr>
						        <tr>
						            <th>4</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="制动性能检验设备"/>                                    
                                                                               制动性能检验设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned4" iptName="inspectDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned4" iptName="inspectDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备，可选用汽车轮重仪和滚筒反力式制动检验台（或滚筒反力式轮重制动复合检验台）、平板式制动检验台、便携式制动性能检测仪或非接触式速度计四种检验设备之一
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备，可选用汽车轮重仪和滚筒反力式制动检验台（或滚筒反力式轮重制动复合检验台）、平板式制动检验台、便携式制动性能检测仪或非接触式速度计四种检验设备之一"/></td>
						        </tr>
						        <tr>
						            <th>5</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="转向盘转向力/角测量仪"/>                                    
                                                                                转向盘转向力/角测量仪</th>
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
						                <input type="radio" name="inspectDeviceIsOwned5" iptName="inspectDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="inspectDeviceIsOwned5" iptName="inspectDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、货车类必备，乘用车类视情选配
						            <input type="hidden" class="syj-input1" iptName="remark" value="客车类、货车类必备，乘用车类视情选配"/></td>
						        </tr>
						    </table>
						    <table id="commonDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:58px;">四</th>
						            <th style="width:115px;">通用设备</th>
						            <th style="width:58px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:115px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:58px;"></th>
						            <th style="width:220px;"></th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="计算机"/>                                    
                                                                                计算机</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned1" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned1" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>2</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="砂轮机"/>                                    
                                                                                砂轮机</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned2" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned2" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备
						            <input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>3</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="台钻"/>                                    
                                                                                台钻</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned3" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned3" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>4</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="台钳"/>                                    
                                                                                台钳</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned4" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned4" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>5</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="压床"/>                                    
                                                                                压床</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned5" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned5" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>6</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="电焊设备"/>                                    
                                                                                电焊设备</th>
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
						                <input type="radio" name="commonDeviceIsOwned6" iptName="commonDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned6" iptName="commonDeviceIsOwned" value="1"/>
						            </td>
						            <td>客车类、货车类必备<input type="hidden" class="syj-input1" iptName="remark" value="客车类、货车类必备"/></td>
						        </tr>
						        <tr>
						            <th>7</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="气体保护焊设备"/>                                    
                                                                                气体保护焊设备</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned7" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned7" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>8</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="空气压缩机"/>                                    
                                                                                空气压缩机</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" iptName="amount" maxlength="10" style="width:78%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned8" iptName="commonDeviceIsOwned" value="0" class="validate[required]">
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned8" iptName="commonDeviceIsOwned" value="1" class="validate[required]">
						            </td>
						            <td>必备<input type="hidden" class="syj-input1" iptName="remark" value="必备"/></td>
						        </tr>
						        <tr>
						            <th>9</th>
						            <th>
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="抢修服务车"/>                                    
                                                                                抢修服务车</th>
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
						                <input type="radio" name="commonDeviceIsOwned9" iptName="commonDeviceIsOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="commonDeviceIsOwned9" iptName="commonDeviceIsOwned" value="1"/>
						            </td>
						            <td>视情选配<input type="hidden" class="syj-input1" iptName="remark" value="视情选配"/></td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>从事危险货物运输车辆维修企业附加申请表
							    <a class="answer" href="javascript:void(0);" title="从事危险货物运输车辆维修的一类汽车整车维修企业填报"></a>
							</span>
						</div>
						<div style="position:relative;">
						    <table cellpadding="0" cellspacing="0" class="syj-table2">
						        <tr>
						            <th>专用维修车间</th>
						        </tr>
						    </table>
						    <table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:-1px;">
						        <tr>
						            <th style="width:50px;">序号</th>
						            <th style="width:430px;">标准要求</th>
						            <th style="width:430px;">企业自查情况</th>
						        </tr>
						        <tr>
						            <td style="text-align:center;">1</td>
						            <td>具有与其作业内容相适应的专用维修车间，其面积不少于100㎡(该专用维修车间面积可计入所在一类汽车维修企业生产厂房面积内)。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_RISKROOMA" value="${materForm.SELFCHECK_RISKROOMA }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">2</td>
						            <td>专用维修车间通风良好，其照明灯具和电器设备应具有防爆功能。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_RISKROOMB" value="${materForm.SELFCHECK_RISKROOMB }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td style="text-align:center;">3</td>
						            <td>专用维修车间与普通车辆维修车间应相对隔离，设置明显的指示性标志。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECK_RISKROOMC" value="${materForm.SELFCHECK_RISKROOMC }" maxlength="62" style="width:97%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th colspan="3">专用设施设备
						                <c:if test="${operType=='write' }">
							                <div style="float:right;">
												<a href="javascript:void(0);" class="del-btn" onclick="$('#sepcialDeviceTable').MyEditableTable('deleteRow');">删除</a>
											    <a href="javascript:void(0);" class="add-btn" onclick="$('#sepcialDeviceTable').MyEditableTable('appendRow');">增加</a>
											</div>
										</c:if>
						            </th>
						        </tr>
						    </table>
						    <table id="sepcialDeviceTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:120px;">名称</th>
						            <th style="width:120px;">型号或规格</th>
						            <th style="width:100px;">数量</th>
						            <th style="width:120px;">出厂日期</th>
						            <th style="width:120px;">检定日期</th>
						            <th>备注</th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1" iptName="deviceName" maxlength="16" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1 validate[custom[numberplus]]" iptName="amount" maxlength="10" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="produceDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="Wdate" iptName="verifyDate" maxlength="16" style="width:90%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="remark" maxlength="64" style="width:93%;"/>
						            </td>
						        </tr>
						    </table>
						    <table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:-1px;">
						        <tr>
						            <th>安全生产事故应急预案
						            	<a class="answer" href="javascript:void(0);" title="安全生产事故应急预案主要内容应包括：组织机构及其职责、报警系统和报告程序、应急设备设施、应急指挥以及处置措施等"></a>
						            </th>
						        </tr>
						        <tr>
						            <td>
						                <textarea name="RISKCAR_EMERGENCYPLAN" class="validate[maxSize[2000]]" style="width:99.5%;height:200px;">${materForm.RISKCAR_EMERGENCYPLAN }</textarea>
						            </td>
						        </tr>
						        <tr>
						            <th>安全管理人员配备一览表
						                <c:if test="${operType=='write' }">
							                <div style="float:right;">
												<a href="javascript:void(0);" class="del-btn" onclick="$('#safetyAdminTable').MyEditableTable('deleteRow');">删除</a>
											    <a href="javascript:void(0);" class="add-btn" onclick="$('#safetyAdminTable').MyEditableTable('appendRow');">增加</a>
											</div>
										</c:if>
						            </th>
						        </tr>
						    </table>
						    <table id="safetyAdminTable" class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:30px;">序号</th>
						            <th style="width:30px;"><input type="checkbox"/></th>
						            <th style="width:90px;">姓名</th>
						            <th style="width:60px;">性别</th>
						            <th style="width:100px;">出生年月</th>
						            <th style="width:100px;">学历</th>
						            <th style="width:100px;">所学专业</th>
						            <th style="width:100px;">技术职称</th>
						            <th style="width:100px;">技能工种及等级</th>
						            <th style="width:100px;">所在岗位</th>
						            <th style="width:90px;">备注</th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1" type="checkbox"/></th>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffName" maxlength="16" style="width:89%;"/>
						            </td>
						            <td>
						                <select class="input-dropdown" iptName="staffSex" style="width:97%;">
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
						                <input type="text" class="syj-input1" iptName="staffSpecialty" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffJobtitle" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffWorktype" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffPosition" maxlength="32" style="width:89%;"/>
						            </td>
						            <td>
						                <input type="text" class="syj-input1" iptName="staffRemark" maxlength="64" style="width:87%;"/>
						            </td>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th>安全操作规程、指示性标志设置资料</th>
						        </tr>
						    </table>
						    <table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:-1px;">
						        <tr>
						            <th style="width:5%;">项目</th>
						            <th>具体内容</th>
						        </tr>
						        <tr>
						            <th>安全操作规程</th>
						            <td>
						                <textarea name="SAFETYOPRRULE" class="validate[maxSize[2000]]" style="width:99.5%;height:200px;">${materForm.SAFETYOPRRULE }</textarea>
						            </td>
						        </tr>
						        <tr>
						            <th>指示性标志设置</th>
						            <td>
						                <textarea name="INDICATIVEMARKSETTING" class="validate[maxSize[2000]]" style="width:99.5%;height:200px;">${materForm.INDICATIVEMARKSETTING }</textarea>
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
		        <a href="javascript:void(0);" onclick="$('#ABCRAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div> --%>
    </div>
</body>
</html>