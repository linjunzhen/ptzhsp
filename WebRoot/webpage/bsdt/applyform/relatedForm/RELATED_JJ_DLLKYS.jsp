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
			AppUtil.initWindowForm("DLLKYS_FORM", function(form, valid) {
				if (valid&&purchaseSubmit()) {
					//将提交按钮禁用,防止重复提交
					//var formData = $("#DLLKYS_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("DLLKYS_FORM");
					var url = $("#DLLKYS_FORM").attr("action");
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
			
			$('#DLLKYS_FORM').find('input,textarea').prop("readonly", true);
			$('#DLLKYS_FORM').find('select').prop("disabled", "disabled");
			$('#DLLKYS_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$("input[name='COMPLETION_TIME']").prop("disabled", "disabled");
			$("input[name='ACCEPTANCE_TIME']").prop("disabled", "disabled");
			$("input[name='OPERATION_TIME']").prop("disabled", "disabled");
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.PERSON_INFOMATIONS }'!=null&&'${materForm.PERSON_INFOMATIONS }'!=''){
					var purchaseJson = '${materForm.PERSON_INFOMATIONS }';
					var purchase = eval(purchaseJson);
					var jsonstr = '{"total":'+purchase.length+',"rows":'+purchaseJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#personInfoGrid').datagrid('loadData',data);
				} 
			}else {
				var APPLICANT_NAME = parent.$("input[name='COMPANY_NAME']").val();
   		 		$("input[name='APPLICANT_NAME']").val(APPLICANT_NAME);
   		 	}
		});
		function downloadDoc(){
			var recordId = '${materForm.RECORD_ID }';
			var fornName = '${materForm.formName }';
			window.location.href=__ctxPath+"/domesticControllerController/downLoadRelatedMater.do?recordId="+recordId
			+"&fornName="+fornName;
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="DLLKYS_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="PERSON_INFOMATIONS" value="${materForm.PERSON_INFOMATIONS }"/>
						<div class="syj-title1">
							<a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
							<span>申请人基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>申请人名称：</th>
									<td colspan="3" style="border-bottom: none;"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="APPLICANT_NAME" value="${materForm.APPLICANT_NAME }"
										placeholder="" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th> 负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="" maxlength="16" /></td>
									<th> 经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="" maxlength="6" /></td>
									<th> 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="" maxlength="11" /></td>
									<th> 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="" maxlength="32" /></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1  tmargin20">
							<span>申请事项</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>客运站名称：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="STATION_NAME" value="${materForm.STATION_NAME }"
									/></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>所在地址：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="STATION_ADDR" value="${materForm.STATION_ADDR }"
									/></td>
								</tr>
							</table>
							
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>占地面积：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="STATION_COVERD_AREA" value="${materForm.STATION_COVERD_AREA }"
										placeholder="" /></td>
									<th>客运站建筑面积：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="STATION_BUILD_AREA" value="${materForm.STATION_BUILD_AREA }"
										placeholder="" maxlength="32" /></td>
								</tr>
								<tr>
									<th>设计年度日旅客发送量：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="PASSENGER_VOLUME" value="${materForm.PASSENGER_VOLUME }"
										placeholder="" /></td>
									<th>竣工时间：</th>
									<td>
										<input type="text" class="Wdate validate[]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
										 name="COMPLETION_TIME"  placeholder="" value="${materForm.COMPLETION_TIME }"/>
								</tr>
								<tr>
									<th>经验收符合的站场级别：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="STATION_LEVEL" value="${materForm.STATION_LEVEL }"
										placeholder="" /></td>
									<th>验收时间：</th>
									<td>
										<input type="text" class="Wdate validate[" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
										 name="ACCEPTANCE_TIME"  placeholder="" value="${materForm.ACCEPTANCE_TIME }"/>
								</tr>
								<tr>
                                    <th>拟投入运营时间：</th>
                                    <td>
                                        <input type="text" class="Wdate validate[" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
                                         name="OPERATION_TIME"  placeholder="" value="${materForm.OPERATION_TIME }"/>
                                    <th>申请范围：</th>
                                    <td ><input type="text" maxlength="32"
                                        class="syj-input1 validate[]" name="APPLY_SCORE" value="${materForm.APPLY_SCORE }"
                                        placeholder="" /></td>
                                </tr>
							</table>
						</div>
						
						
						
						<div class="syj-title1  tmargin20">
							<span>场地设施</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>站前广场（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="SQUARE_AREA" value="${materForm.SQUARE_AREA }"
									/></td>
									<th>停车场（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="PARKING_AREA" value="${materForm.PARKING_AREA }"
									/></td>
								</tr>
								<tr>
									<th>发车位（个）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="PARKING_LOT" value="${materForm.PARKING_LOT }"
									/></td>
								</tr>
							</table>
						</div>
						
						
						<div class="syj-title1  tmargin20">
							<span>建筑设施</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>候车厅\室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="WAITING_HALL_AREA" value="${materForm.WAITING_HALL_AREA }"
									/></td>
									<th>重点旅客候车室\区（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ZDWAITING_HALL_AREA" value="${materForm.ZDWAITING_HALL_AREA }"
									/></td>
								</tr>
								<tr>
									<th>售票厅（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="TICKET_HALL_AREA" value="${materForm.TICKET_HALL_AREA }"
									/></td>
									<th>行包托运厅\处（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="BAGGAGE_HALL_AREA" value="${materForm.BAGGAGE_HALL_AREA }"
									/></td>
								</tr>
								
								<tr>
									<th>综合服务处（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="SEARVICE_ROOM" value="${materForm.SEARVICE_ROOM }"
									/></td>
									<th>站务员室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ATTENDANT_ROOM" value="${materForm.ATTENDANT_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>驾乘人员休息室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="CREW_LOUNGE" value="${materForm.CREW_LOUNGE }"
									/></td>
									<th>调度室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="DISPATCH_ROOM" value="${materForm.DISPATCH_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>治安室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="POLICE_OFFICE" value="${materForm.POLICE_OFFICE }"
									/></td>
									<th>广播室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="BROADCASTING_ROOM" value="${materForm.BROADCASTING_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>无障碍通道（米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="WHEELCHAIR_ACCESS" value="${materForm.WHEELCHAIR_ACCESS }"
									/></td>
									<th>残疾人服务设施（件）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="CJRFUCS_COUNT" value="${materForm.CJRFUCS_COUNT }"/></td>
								</tr>
								
								<tr>
									<th>智能化系统用房 （平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ZNHXTYF_ROOM" value="${materForm.ZNHXTYF_ROOM }"
									/></td>
									<th>盥洗室和旅客厕所（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="YSSHLKCS_ROOM" value="${materForm.YSSHLKCS_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>办公用房（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="BGYF_ROOM" value="${materForm.BGYF_ROOM }"
									/></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1  tmargin20">
							<span>生产辅助用房</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>汽车安全检验台（个）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="QCAQJCT_COUNT" value="${materForm.QCAQJCT_COUNT }"
									/></td>
									<th>车辆清洁、清洗台（个）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="CLQJQXT_COUNT" value="${materForm.CLQJQXT_COUNT }"
									/></td>
								</tr>
								<tr>
									<th>配电室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="PDS_ROOM" value="${materForm.PDS_ROOM }"
									/></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>基本设备</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="BASICAL_EQUIPMENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="basicalEquipment" value="${materForm.BASICAL_EQUIPMENT }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						
						
						
						
						<div class="syj-title1 tmargin20">
							<span>智能系统设备</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="INTELLIGENT_EQUIPMENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="intlligentEquipment" value="${materForm.INTELLIGENT_EQUIPMENT }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>拟聘用专业人员、管理人员情况</span>
						</div>
						<div id="purchaseDiv">
							<div style="position:relative;">
								<div id="purchaseToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addPurchase();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removePurchase();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#purchaseToolbar" id="personInfoGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post'" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">姓名</th>
											<th data-options="field:'SEX',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
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
												width="14%">性别</th>
											<th data-options="field:'AGE',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">年龄</th>
											<th data-options="field:'POST',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">工作岗位</th>
											<th data-options="field:'IDCARD',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">身份证号码</th>
											<th data-options="field:'JOB',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">职称</th>
											<th data-options="field:'PROFESSIONALNUM',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">专业证书号码</th>
										</tr>
									</thead>
								</table>
							</div>
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
		        <a href="javascript:void(0);" onclick="$('#DLLKYS_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div> --%>
	</div>
	<script type="text/javascript">
		
	   
	    
	</script>
</body>