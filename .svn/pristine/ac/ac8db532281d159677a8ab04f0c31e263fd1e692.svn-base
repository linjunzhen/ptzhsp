<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("RDGTAPPLY_FORM", function(form, valid) {
				//if (valid&&purchaseSubmit()&&purchasePlanSubmit()&&currentSubmit()) {
				if (valid && employeesSubmit()&&purchaseBusSubmit()&&inpurchaseBusSubmit()&&operatingBusSubmit()) {
					purchaseBusSubmit();
					inpurchaseBusSubmit();
					operatingBusSubmit();
					employeesSubmit();
					
					//将提交按钮禁用,防止重复提交
					//var formData = $("#RDGTAPPLY_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("RDGTAPPLY_FORM");
					var url = $("#RDGTAPPLY_FORM").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if(resultJson.success){
							  	parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
								art.dialog.data("backFormInfo",{
									formName : '${materForm.formName }',
				    				recordId:resultJson.jsonString
								});
								AppUtil.closeLayer();
							}else{
								parent.art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});
							}
						}
					});
				}
			},null);
			
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.PURCHASEBUSJSON }'!=null&&'${materForm.PURCHASEBUSJSON }'!=''){
					var purchasebusJson = '${materForm.PURCHASEBUSJSON }';
					var purchasebus = eval(purchasebusJson);
					var jsonstr = '{"total":'+purchasebus.length+',"rows":'+purchasebusJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#purchaseBusGrid').datagrid('loadData',data);
				} 
				if('${materForm.INPURCHASEBUSJSON }'!=null&&'${materForm.INPURCHASEBUSJSON }'!=''){
					var inpurchasebusJson = '${materForm.INPURCHASEBUSJSON }';
					var inpurchasebus = eval(inpurchasebusJson);
					var jsonstr = '{"total":'+inpurchasebus.length+',"rows":'+inpurchasebusJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#inpurchaseBusGrid').datagrid('loadData',data);
				} 
				
				if('${materForm.OPERATINGBUSJSON }'!=null&&'${materForm.OPERATINGBUSJSON }'!=''){
					var operatingbusJson = '${materForm.OPERATINGBUSJSON }';
					var operatingbus = eval(operatingbusJson);
					var jsonstr = '{"total":'+operatingbus.length+',"rows":'+operatingbusJson+'}';
					var data = $.parseJSON(jsonstr);
					
					$('#operatingBusGrid').datagrid('loadData',data);
				} 
				if('${materForm.PLAN_EMPLOYEES }'!=null&&'${materForm.PLAN_EMPLOYEES }'!=''){
					var planemplyeesJson = '${materForm.PLAN_EMPLOYEES }';
					var planemplyees = eval(planemplyeesJson);
					var jsonstr = '{"total":'+planemplyees.length+',"rows":'+planemplyeesJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#employeesGrid').datagrid('loadData',data);
				} 
				
				if('${materForm.APPLY_TYPE }'=='1'){
					$("#expand").find("input,textarea").prop("disabled", "disabled");
					$('#expand').find('input,textarea').val('');
					$('#expand').find(":checkbox").attr('checked',false);
				}else{
					$("#first").find("input,textarea").prop("disabled", "disabled");
					$('#first').find('input,textarea').val('');
					$('#first').find(":checkbox").attr('checked',false);
				}
			}else{
				//企业名称
				var APPLICANT_NAME = parent.$("input[name='COMPANY_NAME']").val();
				$("input[name='APPLICANT_NAME']").val(APPLICANT_NAME);
				//邮政编码
				var POST_CODE = parent.$("input[name='POST_CODE']").val();
				$("input[name='POST_CODE']").val(POST_CODE);
				var itemCode = parent.$("input[name='ITEM_CODE']").val();
				var APPLY_PHONE = "";
				var MAILING_ADDR = "";			
				var handlerName = "";
				if(itemCode=='201605170002XK00101'||itemCode=='201605170002XK00102'){
					APPLY_PHONE = parent.$("input[name='CONTACT_PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSSINESS_ADDR']").val();
					handlerName = parent.$("input[name='OPERATOR_NAME']").val();
				}else if(itemCode=='201605170002XK00103'){
					APPLY_PHONE = parent.$("input[name='PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSINESS_ADDR']").val();
				}
				//联系电话
				$("input[name='APPLY_PHONE']").val(APPLY_PHONE);
				//生产地址/通信地址
				$("input[name='MAILING_ADDR']").val(MAILING_ADDR);
				//经办人
				$("input[name='HANDLER_NAME']").val(handlerName);
			}
		});
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="RDGTAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="PURCHASEBUSJSON" value="${materForm.PURCHASEBUSJSON }"/>
						<input type="hidden" name="INPURCHASEBUSJSON" value="${materForm.INPURCHASEBUSJSON }"/>
						<input type="hidden" name="OPERATINGBUSJSON" value="${materForm.OPERATINGBUSJSON }"/>
						<input type="hidden" name="PLAN_EMPLOYEES" value="${materForm.PLAN_EMPLOYEES }"/>
						<div class="syj-title1">
							<span>申请人基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom:none;"><font class="syj-color2">*</font>申请人名称：</th>
									<td colspan="3" style="border-bottom:none;"><input type="text"
										class="syj-input1 validate[required]" name="APPLICANT_NAME" value="${materForm.APPLICANT_NAME }"
										placeholder="要求填写企业（公司）全称、企业预先核准全称" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th > 负责人姓名：</th>
									<td><input type="text"
										class="syj-input1" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th > 经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 " name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="请输入邮编" maxlength="6" /></td>
									<th > 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="11" /></td>
									<th > 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="32" /></td>
								</tr>
							</table>
						</div>
						
						<!--运营范围选择-->
						<div class="syj-title1 tmargin20">
							<span>申请许可内容（首次申请道路旅客运输经营许可或申请扩大道路旅客运输经营范围，请选择拟申请的道路旅客运输经营范围）</span>
						</div>
						
						<div style="position:relative;">
						<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th >班车客运：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="SHBUS" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPort" value="${materForm.SHBUS }">
										</eve:excheckbox>
									</td>
								</tr>
								
								<tr>
									<th >包车客运：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CHBUS" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPort" value="${materForm.CHBUS }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>如申请扩大道路旅客运输经营范围，请选择现有的道路旅客运输经营范围</span>
						</div>
						
						<div style="position:relative;">
						<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th >班车客运：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="SHBUS1" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPort" value="${materForm.SHBUS1 }">
										</eve:excheckbox>
									</td>
								</tr>
								
								<tr>
									<th >包车客运：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CHBUS1" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPort" value="${materForm.CHBUS1 }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						<!--运营范围选择-->
						
						<!--已购置营运客车情况-->
						<div class="syj-title1 tmargin20">
							<span>营运客车信息</span>
						</div>
						<div class="syj-title1 tmargin20">
							<span>已购置营运客车情况</span>
						</div>
						<div id="purchaseDiv">
							<div style="position:relative;">
								<div id="purchaseBusToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addPurchaseBus();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removePurchaseBus();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#purchaseBusToolbar" id="purchaseBusGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowPurchaseBus"
									allowHeaderWrap="true">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="10%">厂牌型号</th>
											<th data-options="field:'NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:9999999}}"
												width="5%">数量</th>
											<th data-options="field:'BUS_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:9999999}}"
												width="10%">座位数（个）</th>
											<th data-options="field:'BUS_TYPELEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">车辆类型及等级</th>
											<th data-options="field:'BUS_LEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">车辆技术等级</th>
											<th data-options="field:'BUS_WEIGHT',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,32]'}}"
												width="15%">车辆外廓长宽高</th>
											<th data-options="field:'GET_TIME',align:'center',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="10%">购置时间</th>
											<th data-options="field:'REMARK',align:'center',editor:{type:'validatebox',options:{validType:'length[1,32]'}}"
												width="19%">备注</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<!--已购置营运客车情况-->
						
						<!--拟购置营运客车情况-->
						<div class="syj-title1 tmargin20">
							<span>拟购置营运客车情况</span>
						</div>
						<div id="purchasePlanDiv">
							<div style="position:relative;">
								<div id="inpurchaseBusToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addInpurchaseBus();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeInpurchaseBus();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#inpurchaseBusToolbar" id="inpurchaseBusGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowInpurchaseBus">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">厂牌型号</th>
											<th data-options="field:'NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:9999999}}"
												width="5%">数量</th>
											<th data-options="field:'SEAT_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:9999999}}"
												width="10%">座位数（个）</th>
											<th data-options="field:'CAR_TYPELEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">车辆类型及等级</th>
											<th data-options="field:'CAR_TECHLEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">车辆技术等级</th>
											<th data-options="field:'CAR_WIDTH',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,32]'}}"
												width="19%">车辆外廓长宽高</th>
											<th data-options="field:'REMARK',align:'center',editor:{type:'validatebox',options:{validType:'length[1,32]'}}"
												width="20%">备注</th>
									</thead>
								</table>
							</div>
						</div>
						<!--拟购置营运客车情况-->
						
						
						
						
						<!--现有营运客车情况-->
						<div class="syj-title1 tmargin20">
							<span>现有营运客车情况</span>
						</div>
						<div id="currentDiv">
							<div style="position:relative;">
								<div id="operatingBusToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addOperatingBus();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeOperatingBus();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#operatingBusToolbar" id="operatingBusGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowOperatingBus">
									<thead>
										<tr>
											<th data-options="field:'TRAN_CERT',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="13%">道路运输证号</th>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="12%">厂牌型号</th>
											<th data-options="field:'LICENSE',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="10%">车牌号码</th>
											<th data-options="field:'NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:9999999}}"
												width="10%">座位数（个）</th>
											<th data-options="field:'BUS_TYPELEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">车辆类型及等级</th>
											<th data-options="field:'BUS_LEVEL',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="10%">车辆技术等级</th>
											<th data-options="field:'BUS_WEIGHT',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,32]'}}"
												width="14%">车辆外廓长宽高</th>
											<th data-options="field:'GET_TIME',align:'center',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="15%">购置时间</th>
									</thead>
								</table>
							</div>
						</div>
						<!--现有营运客车情况-->
						
						
						
						<!--拟聘用营运客车驾驶员情况-->
						<div class="syj-title1 tmargin20">
							<span>拟聘用营运客车驾驶员情况</span>
						</div>
						
						<div id="driveruserDiv">
							<div style="position:relative;">
								<div id="employeesToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addEmployees();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeEmployees();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#employeesToolbar" id="employeesGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowEmployees">
									<thead>
										<tr>
											<th data-options="field:'USERNAME',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,6]'}}"
												width="10%">姓名</th>
											<th data-options="field:'SEX',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:50,
											data:[  
												{'id':'1','text':'男'},  
												{'id':'2','text':'女'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '男';
											        if(value=='2') return '女';
												}"
												width="10%">性别</th>
											<th data-options="field:'AGE',align:'center',editor:{type:'numberbox',options:{required:true,min:0,max:99999}}"
												width="5%">年龄</th>
											<th data-options="field:'GETTIME',align:'center',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="15%">取得相应驾驶证时间</th>
											<th data-options="field:'QUCODE',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">从业资格证号</th>
											<th data-options="field:'QUTYPE',align:'center',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="15%">从业资格证类型</th>
											<th data-options="field:'ISTRAACC',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:50,
											data:[  
												{'id':'0','text':'是'},  
												{'id':'1','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='0') return '是';
											        if(value=='1') return '否';
												}"
												width="29%">三年内是否发生重大以上交通责任事故</th>
									</thead>
								</table>
							</div>
						</div>
						<!--拟聘用营运客车驾驶员情况-->
					</form>
				</div>
			</div>
		</div>
		
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#RDGTAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
	
	<script type="text/javascript">
	/**已购置客车 可编辑表格开始*/
		var editIndexPurchaseBus = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingPurchaseBus(){
	    	if (editIndexPurchaseBus == undefined){return true}
	    	if ($("#purchaseBusGrid").datagrid("validateRow", editIndexPurchaseBus)){
	    		$("#purchaseBusGrid").datagrid("endEdit", editIndexPurchaseBus);
	    		editIndexPurchaseBus = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addPurchaseBus(){
	    	if (endEditingPurchaseBus()){
	    		$("#purchaseBusGrid").datagrid("appendRow",{});
	    		editIndexPurchaseBus = $("#purchaseBusGrid").datagrid("getRows").length-1;
	    		$("#purchaseBusGrid").datagrid("selectRow", editIndexPurchaseBus)
	    				.datagrid("beginEdit", editIndexPurchaseBus);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removePurchaseBus() {
	    	if (editIndexPurchaseBus == undefined){return}
	    	$("#purchaseBusGrid").datagrid("cancelEdit", editIndexPurchaseBus)
	    			.datagrid("deleteRow", editIndexPurchaseBus);
	    	editIndexPurchaseBus = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmPurchaseBus(){
	    	var e = endEditingPurchaseBus();
	        if (e){
	            $("#purchaseBusGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowPurchaseBus(index){
	    	if (editIndexPurchaseBus != index){
	    		if (endEditingPurchaseBus()){
	    			$("#purchaseBusGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexPurchaseBus = index;
	    		} else {
	    			$("#purchaseBusGrid").datagrid("selectRow", editIndexPurchaseBus);
	    		}
	    	}
	    }
	    /**
	     * 提交方法
	     */
	    function purchaseBusSubmit(){
	    	if(!confirmPurchaseBus()){
	            alert("“已购置营运客车情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#purchaseBusGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"BRAND_MODEL":rows[i].BRAND_MODEL,
						"NUM":rows[i].NUM,
						"BUS_NUM":rows[i].BUS_NUM,
						"BUS_TYPELEVEL":rows[i].BUS_TYPELEVEL,
						"BUS_LEVEL":rows[i].BUS_LEVEL,
						"BUS_WEIGHT":rows[i].BUS_WEIGHT,
						"GET_TIME":rows[i].GET_TIME,
						"REMARK":rows[i].REMARK
					});
				}
				$("input[type='hidden'][name='PURCHASEBUSJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**已购置可编辑表格结束*/
	    
	    
		 /**拟购置可编辑表格开始*/
		var editIndexInpurchaseBus = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingInpurchaseBus(){
	    	if (editIndexInpurchaseBus == undefined){return true}
	    	if ($("#inpurchaseBusGrid").datagrid("validateRow", editIndexInpurchaseBus)){
	    		$("#inpurchaseBusGrid").datagrid("endEdit", editIndexInpurchaseBus);
	    		editIndexInpurchaseBus = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addInpurchaseBus(){
	    	if (endEditingInpurchaseBus()){
	    		$("#inpurchaseBusGrid").datagrid("appendRow",{});
	    		editIndexInpurchaseBus = $("#inpurchaseBusGrid").datagrid("getRows").length-1;
	    		$("#inpurchaseBusGrid").datagrid("selectRow", editIndexInpurchaseBus)
	    				.datagrid("beginEdit", editIndexInpurchaseBus);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeInpurchaseBus() {
	    	if (editIndexInpurchaseBus == undefined){return}
	    	$("#inpurchaseBusGrid").datagrid("cancelEdit", editIndexInpurchaseBus)
	    			.datagrid("deleteRow", editIndexInpurchaseBus);
	    	editIndexInpurchaseBus = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmInpurchaseBus(){
	    	var e = endEditingInpurchaseBus();
	        if (e){
	            $("#inpurchaseBusGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowInpurchaseBus(index){
	    	if (editIndexInpurchaseBus != index){
	    		if (endEditingInpurchaseBus()){
	    			$("#inpurchaseBusGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexInpurchaseBus = index;
	    		} else {
	    			$("#inpurchaseBusGrid").datagrid("selectRow", editIndexInpurchaseBus);
	    		}
	    	}
	    }
	    /**
	     * 提交方法
	     */
	    function inpurchaseBusSubmit(){
	    	if(!confirmInpurchaseBus()){
	            alert("“拟购置营运客车情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#inpurchaseBusGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					 datas.push({
					 	"BRAND_MODEL":rows[i].BRAND_MODEL,
						"NUM":rows[i].NUM,
						"SEAT_NUM":rows[i].SEAT_NUM,
						"CAR_TYPELEVEL":rows[i].CAR_TYPELEVEL,
						"CAR_TECHLEVEL":rows[i].CAR_TECHLEVEL,
						"CAR_WIDTH":rows[i].CAR_WIDTH,
						"REMARK":rows[i].REMARK
					}); 
				}
				$("input[type='hidden'][name='INPURCHASEBUSJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**拟购置可编辑表格结束*/
	    
	    
	    
	    /**现有营运客车情况编辑表格开始*/
		var editIndexOperatingBus = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingOperatingBus(){
	    	if (editIndexOperatingBus == undefined){return true}
	    	if ($("#operatingBusGrid").datagrid("validateRow", editIndexOperatingBus)){
	    		$("#operatingBusGrid").datagrid("endEdit", editIndexOperatingBus);
	    		editIndexOperatingBus = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addOperatingBus(){
	    	if (endEditingOperatingBus()){
	    		$("#operatingBusGrid").datagrid("appendRow",{});
	    		editIndexOperatingBus = $("#operatingBusGrid").datagrid("getRows").length-1;
	    		$("#operatingBusGrid").datagrid("selectRow", editIndexOperatingBus)
	    				.datagrid("beginEdit", editIndexOperatingBus);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeOperatingBus() {
	    	if (editIndexOperatingBus == undefined){return}
	    	$("#operatingBusGrid").datagrid("cancelEdit", editIndexOperatingBus)
	    			.datagrid("deleteRow", editIndexOperatingBus);
	    	editIndexOperatingBus = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmOperatingBus(){
	    	var e = endEditingOperatingBus();
	        if (e){
	            $("#operatingBusGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowOperatingBus(index){
	    	if (editIndexOperatingBus != index){
	    		if (endEditingOperatingBus()){
	    			$("#operatingBusGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexOperatingBus = index;
	    		} else {
	    			$("#operatingBusGrid").datagrid("selectRow", editIndexOperatingBus);
	    		}
	    	}
	    }
	    /**
	     * 提交方法
	     */
	    function operatingBusSubmit(){
	    	if(!confirmOperatingBus()){
	            alert("“现有营运客车情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#operatingBusGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					 datas.push({
					 	"TRAN_CERT":rows[i].TRAN_CERT,
						"BRAND_MODEL":rows[i].BRAND_MODEL,
						"LICENSE":rows[i].LICENSE,
						"NUM":rows[i].NUM,
						"BUS_TYPELEVEL":rows[i].BUS_TYPELEVEL,
						"BUS_LEVEL":rows[i].BUS_LEVEL,
						"BUS_WEIGHT":rows[i].BUS_WEIGHT,
						"AXLE_NUM":rows[i].AXLE_NUM,
						"GET_TIME":rows[i].GET_TIME
					}); 
				}
				$("input[type='hidden'][name='OPERATINGBUSJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**现有营运客车情况编辑表格结束*/
	    
	    
	    
	     /**驾驶员信息开始*/
		var editIndexEmployees = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingEmployees(){
	    	if (editIndexEmployees == undefined){return true}
	    	if ($("#employeesGrid").datagrid("validateRow", editIndexEmployees)){
	    		$("#employeesGrid").datagrid("endEdit", editIndexEmployees);
	    		editIndexEmployees = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addEmployees(){
	    	if (endEditingEmployees()){
	    		$("#employeesGrid").datagrid("appendRow",{});
	    		editIndexEmployees = $("#employeesGrid").datagrid("getRows").length-1;
	    		$("#employeesGrid").datagrid("selectRow", editIndexEmployees)
	    				.datagrid("beginEdit", editIndexEmployees);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeEmployees() {
	    	if (editIndexEmployees == undefined){return}
	    	$("#employeesGrid").datagrid("cancelEdit", editIndexEmployees)
	    			.datagrid("deleteRow", editIndexEmployees);
	    	editIndexEmployees = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmEmployees(){
	    	var e = endEditingEmployees();
	        if (e){
	            $("#employeesGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowEmployees(index){
	    	if (editIndexEmployees != index){
	    		if (endEditingEmployees()){
	    			$("#employeesGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexEmployees = index;
	    		} else {
	    			$("#employeesGrid").datagrid("selectRow", editIndexEmployees);
	    		}
	    	}
	    }
	    
	    /**
	     * 提交方法
	     */
	    function employeesSubmit(){
	    	if(!confirmEmployees()){
	            alert("“拟聘用营运货车驾驶员情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#employeesGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"USERNAME":rows[i].USERNAME,
						"SEX":rows[i].SEX,
						"AGE":rows[i].AGE,
						"GETTIME":rows[i].GETTIME,
						"QUCODE":rows[i].QUCODE,
						"QUTYPE":rows[i].QUTYPE,
						"ISTRAACC":rows[i].ISTRAACC
					});
				}
				$("input[type='hidden'][name='PLAN_EMPLOYEES']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**驾驶员信息结束*/
	    
	</script>
</body>