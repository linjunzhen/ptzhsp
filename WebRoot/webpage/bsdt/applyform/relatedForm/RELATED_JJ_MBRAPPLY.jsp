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
	    	});${materForm.LEADER_JSON }
	    	//var leaderJson = $("#MBRAPPLY_FORM").find("input[name='LEADER_JSON']").val();
	    	var leaderJson = '${materForm.LEADER_JSON }';
	    	if(leaderJson != '' && leaderJson != '[]'){
	    		$('#leaderTable').MyEditableTable("loadData", JSON.parse(leaderJson));
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
	    	//var qapersonJson = $("#MBRAPPLY_FORM").find("input[name='QAPERSON_JSON']").val();
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
	    	//var staffJson = $("#MBRAPPLY_FORM").find("input[name='STAFF_JSON']").val();
	    	var staffJson = '${materForm.STAFF_JSON }';
	    	if(staffJson != '' && staffJson != '[]'){
	    		$('#staffInfoTable').MyEditableTable("loadData", JSON.parse(staffJson));
	    	}
	    	//实例化设备表格及初始化数据
	    	$('#deviceTable').MyEditableTable({
	    		onBeforeDeleteRow: function($checkedChks){
	    			var hasSelDefaultItem = false;
	    			$checkedChks.each(function(index, node){
	    				var chkIndex = parseInt($(this).attr("chkindex"), 10);
	    				if(chkIndex <= 24){
	    					hasSelDefaultItem = true;
	    					return false;
	    				}
	    			});
	    			if(hasSelDefaultItem){
	    				alert('操作失败,系统默认加载的记录行（前24行）无法进行删除操作!');
	    				return false;
	    			}
	    		},
	    		onBeforeAppendRow: function(){
	    			var existBlankRow = false;
	    			var $rows = $(this).find('tr');
	    			$rows.each(function(index, row){
	    				if(index > 23){
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
	    	//var deviceJson = $("#MBRAPPLY_FORM").find("input[name='DEVICE_JSON']").val();
	    	var deviceJson = '${materForm.DEVICE_JSON }';
	    	if(deviceJson != '' && deviceJson != '[]'){
	    		$('#deviceTable').MyEditableTable("loadDataExtend", JSON.parse(deviceJson));
	    	}
	    	
	    	function downloadDoc(){
	            var recordId = '${materForm.RECORD_ID }';
	            var fornName = '${materForm.formName }';
	            window.location.href=__ctxPath+"/domesticControllerController/downLoadRelatedMater.do?recordId="+recordId
	            +"&fornName="+fornName;
            }
	    });
	</script>
</head>
<body>
    <div class="container">
        <div class="syj-sbmain2 tmargin20">
            <div class="syj-tyys tmargin20" style="z-index:99;" id="formcontainer">
                <div class="bd margin20">
                    <form id="MBRAPPLY_FORM" action="domesticControllerController/saveRelatedMaterForm.do" method="post">
                        <input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }" id="childRecord"/>
						<input type="hidden" name="LEADER_JSON" value="${materForm.LEADER_JSON }"/>
						<input type="hidden" name="QAPERSON_JSON" value="${materForm.QAPERSON_JSON }"/>
						<input type="hidden" name="STAFF_JSON" value="${materForm.STAFF_JSON }"/>
						<input type="hidden" name="DEVICE_JSON" value="${materForm.DEVICE_JSON }"/>
                        <div class="syj-title1">
                            <a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
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
						                <a class="answer" href="javascript:void(0);" title="按详细通讯地址填写"></a>
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
						                <a class="answer" href="javascript:void(0);" title="按详细通讯地址填写"></a>
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
						                <input type="text" class="syj-input1 validate[required,custom[number2plus]]" name="COMPANY_REGAMOUNT" value="${materForm.COMPANY_REGAMOUNT }" maxlength="14" />
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
						                <a class="answer" href="javascript:void(0);" title="摩托车维修。"></a>
						            </th>
						            <td>
						                <input type="text" class="syj-input1 validate[required]" name="COMPANY_BUSTYPE" value="${materForm.COMPANY_BUSTYPE }" maxlength="30" />
						            </td>
						            <th>
						                <font class="syj-color2">*</font>经营范围：
						                <a class="answer" href="javascript:void(0);" title="摩托车整车修理、总成修理、各级维护和小修。"></a>
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
						            <th colspan="4">人员配备情况(人)</th>
						        </tr>
						        <tr>
						            <th><font class="syj-color2">*</font>质量检验人员</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="QAPERSON_COUNT" value="${materForm.QAPERSON_COUNT }" maxlength="10" style="width:95%;"/>
						            </td>
						            <th><font class="syj-color2">*</font>维修技术人员</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="MAINTAINER_COUNT" value="${materForm.MAINTAINER_COUNT }" maxlength="10" style="width:95%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th><font class="syj-color2">*</font>其他人员</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="RESTSTAFF_COUNT" value="${materForm.RESTSTAFF_COUNT }" maxlength="10" style="width:95%;"/>
						            </td>
						            <th><font class="syj-color2">*</font>合计人数</th>
						            <td>
						                <input type="text" class="syj-input1 validate[required,custom[numberplus]]" name="STAFF_TOTALCOUNT" value="${materForm.STAFF_TOTALCOUNT }" maxlength="10" style="width:95%;"/>
						            </td>
						        </tr>
						    </table>
					    </div>
					    <div class="syj-title1 tmargin20">
							<span>组织管理、人员、安全生产、环境保护和设施条件</span>
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
						            <th style="width:51px;" rowspan="4">组织管理</th>
						            <td style="width:541px;" colspan="2">1.具有国家、行业和地方有关摩托车维修技术标准文本。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECKA" value="${materForm.SELFCHECKA }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.具有承修摩托车车型的维修技术资料。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKB" value="${materForm.SELFCHECKB }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">3.制定质量管理制度、安全生产管理制度、摩托车维修档案管理制度、人员培训制度、设备管理制度、配件管理制度、环境保护制度。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKC" value="${materForm.SELFCHECKC }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">4.具有符合行业要求的摩托车维修质量承诺规定。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKD" value="${materForm.SELFCHECKD }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						    </table>
						    <table class="syj-table2" cellpadding="0" cellspacing="0" style="margin-top:-1px;">
						        <tr>
						            <th style="width:51px;" rowspan="6">人员</th>
						            <td style="width:140px;text-align:center;">总体要求</td>
						            <td style="width:401px;">质量检验人员、维修技术人员等岗位人员配备应与其经营规模相适应。</td>
						            <td style="width:299px;">
						                <input type="text" class="syj-input1" name="SELFCHECKE" value="${materForm.SELFCHECKE }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td rowspan="3">质量检验人员</td>
						            <td>不少于1人，允许一人两岗。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKF" value="${materForm.SELFCHECKF }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉摩托车维修检测作业规范，掌握摩托车维修故障诊断和质量检验的相关技术，熟悉摩托车维修服务收费标准及相关政策法规和技术规范。</td>
						            <td>
						                <input type="radio" name="SELFCHECKG" value="1" <c:if test="${materForm.SELFCHECKG==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECKG" value="0" <c:if test="${materForm.SELFCHECKG==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <td>取得与承修车型种类相适应的摩托车驾驶证。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKH" value="${materForm.SELFCHECKH }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td rowspan="2">维修技术人员</td>
						            <td>不少于2人，允许一人两岗。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKI" value="${materForm.SELFCHECKI }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td>经培训，熟悉所从事摩托车维修的技术和操作规范，了解摩托车维修及相关政策法规。</td>
						            <td>
						                <input type="radio" name="SELFCHECKJ" value="1" <c:if test="${materForm.SELFCHECKJ==1}"> checked="checked"</c:if>>已培训且符合要求<br/>
										<input type="radio" name="SELFCHECKJ" value="0" <c:if test="${materForm.SELFCHECKJ==0}"> checked="checked"</c:if>>未培训或不符合要求
						            </td>
						        </tr>
						        <tr>
						            <th>安全生产</th>
						            <td colspan="2">业户应具有与其维修作业内容相适应的安全管理制度和安全保护措施，建立并实施安全生产责任制度。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKK" value="${materForm.SELFCHECKK }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">环境保护</th>
						            <td colspan="2">1.业户应具有废油、废液、废气、废蓄电池、废轮胎和垃圾等有害物质集中收集、有效处理和保持环境整洁的环境保护管理制度。有害物质存储区应界定清楚、分类存放，必要时应有隔离、控制措施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKL" value="${materForm.SELFCHECKL }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.按生产工艺配置的处理“三废”（废油、废液、废气）、通风、吸尘、净化等设施。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKM" value="${materForm.SELFCHECKM }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <th rowspan="2">设施</th>
						            <td colspan="2">1.业户应具有与维修作业相适应的生产厂房和停车场地。作业厂房面积≥20㎡。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKN" value="${materForm.SELFCHECKN }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						        <tr>
						            <td colspan="2">2.租赁的生产厂房和停车场地应具有合法的书面合同书（合同中应体现租赁面积），租赁期限不得少于1年。</td>
						            <td>
						                <input type="text" class="syj-input1" name="SELFCHECKO" value="${materForm.SELFCHECKO }" maxlength="62" style="width:96%;"/>
						            </td>
						        </tr>
						    </table>
						</div>
						<div class="syj-title1 clear tmargin20">
						    <span>从业人员配备一览表
							    <a class="answer" href="javascript:void(0);" title="此表按业户管理负责人、质量检验人员、维修技术人员等顺序自上而下填写。"></a>
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
						            <th style="width:100px;"><font class="syj-color2">*</font>姓名</th>
						            <th style="width:50px;"><font class="syj-color2">*</font>性别</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>出生日期</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>学历</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>所学专业</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>技术职称</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>技术工种及等级</th>
						            <th style="width:100px;"><font class="syj-color2">*</font>所在岗位</th>
						            <th style="width:90px;">备注</th>
						        </tr>
						        <tr>
						            <th>1</th>
						            <th><input chkIndex="1"  type="checkbox"/></th>
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
						<div class="syj-title1 clear tmargin20">
		                    <span>设备条件</span>
		                    <c:if test="${operType=='write' }">
							    <div style="float:right;">
									<a href="javascript:void(0);" class="del-btn" onclick="$('#deviceTable').MyEditableTable('deleteRow');">删除</a>
								    <a href="javascript:void(0);" class="add-btn" onclick="$('#deviceTable').MyEditableTable('appendRow');">增加</a>
								</div>
							</c:if>
		                </div>
		                <div style="position:relative;">
		                    <table id="deviceTable" class="syj-table2" cellpadding="0" cellspacing="0">
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
		                            <td style="text-align:center;">轮胎拆装设备或专用工具</td>
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
						                <input type="radio" name="isOwned1" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned1" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>2</th>
		                            <th><input chkIndex="2" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">补胎专用工具</td>
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
						                <input type="radio" name="isOwned2" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned2" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>3</th>
		                            <th><input chkIndex="3" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">空气压缩机</td>
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
						                <input type="radio" name="isOwned3" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned3" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>4</th>
		                            <th><input chkIndex="4" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">砂轮机</td>
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
						                <input type="radio" name="isOwned4" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned4" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>5</th>
		                            <th><input chkIndex="5" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">钳工作业台及工具</td>
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
						                <input type="radio" name="isOwned5" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned5" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>6</th>
		                            <th><input chkIndex="6" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">手电钻</td>
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
						                <input type="radio" name="isOwned6" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned6" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>7</th>
		                            <th><input chkIndex="7" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">台钻</td>
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
						                <input type="radio" name="isOwned7" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned7" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>8</th>
		                            <th><input chkIndex="8" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">零件清洗设备</td>
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
						                <input type="radio" name="isOwned8" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned8" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>9</th>
		                            <th><input chkIndex="9" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">扭力扳手</td>
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
						                <input type="radio" name="isOwned9" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned9" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>10</th>
		                            <th><input chkIndex="10" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">数字式万用表</td>
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
						                <input type="radio" name="isOwned10" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned10" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>11</th>
		                            <th><input chkIndex="11" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">厚薄规</td>
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
						                <input type="radio" name="isOwned11" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned11" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>12</th>
		                            <th><input chkIndex="12" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">轮胎气压表</td>
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
						                <input type="radio" name="isOwned12" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned12" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>13</th>
		                            <th><input chkIndex="13" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">气缸压力表</td>
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
						                <input type="radio" name="isOwned13" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned13" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>14</th>
		                            <th><input chkIndex="14" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">外径千分尺</td>
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
						                <input type="radio" name="isOwned14" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned14" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>15</th>
		                            <th><input chkIndex="15" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">内径千分表</td>
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
						                <input type="radio" name="isOwned15" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned15" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>16</th>
		                            <th><input chkIndex="16" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">游标卡尺</td>
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
						                <input type="radio" name="isOwned16" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned16" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>17</th>
		                            <th><input chkIndex="17" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">充电设备</td>
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
						                <input type="radio" name="isOwned17" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned17" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>18</th>
		                            <th><input chkIndex="18" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">焊接设备</td>
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
						                <input type="radio" name="isOwned18" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned18" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>19</th>
		                            <th><input chkIndex="19" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">气门研磨设备或工具</td>
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
						                <input type="radio" name="isOwned19" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned19" iptName="isOwned" value="1"/>
						            </td>
						            <td>必备</td>
		                        </tr>
		                        <tr>
		                            <th>20</th>
		                            <th><input chkIndex="20" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">涂漆设备</td>
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
						                <input type="radio" name="isOwned20" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned20" iptName="isOwned" value="1"/>
						            </td>
						            <td>允许外协</td>
		                        </tr>
		                        <tr>
		                            <th>21</th>
		                            <th><input chkIndex="21" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">尾气分析仪</td>
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
						                <input type="radio" name="isOwned21" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned21" iptName="isOwned" value="1"/>
						            </td>
						            <td>允许外协</td>
		                        </tr>
		                        <tr>
		                            <th>22</th>
		                            <th><input chkIndex="22" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">镗缸设备</td>
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
						                <input type="radio" name="isOwned22" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned22" iptName="isOwned" value="1"/>
						            </td>
						            <td>允许外协</td>
		                        </tr>
		                        <tr>
		                            <th>23</th>
		                            <th><input chkIndex="23" disabled type="checkbox"/></th>
		                            <td style="text-align:center;">磨缸设备</td>
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
						                <input type="radio" name="isOwned23" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned23" iptName="isOwned" value="1"/>
						            </td>
						            <td>允许外协</td>
		                        </tr>
		                        <tr>
		                            <th>24</th>
		                            <th><input chkIndex="24" type="checkbox"/></th>
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
						                <input type="radio" name="isOwned24" iptName="isOwned" value="0"/>
						            </td>
						            <td style="text-align:center;">
						                <input type="radio" name="isOwned24" iptName="isOwned" value="1"/>
						            </td>
						            <td>
                                        <input type="text" class="syj-input1" iptName="devRemark" maxlength="64" style="width:90%;"/>
                                    </td>
		                        </tr>
		                    </table>
		                </div>
                    </form>
                </div>
            </div>
        </div>
        <%-- 
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#MBRAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div> --%>
    </div>
</body>
</html>