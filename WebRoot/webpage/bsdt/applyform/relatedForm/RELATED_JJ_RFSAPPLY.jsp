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
    <script type="text/javascript" src="<%=path%>/webpage/website/applyforms/relatedForm/js/jquery.MyEditableTable.js"></script>
    <style type="text/css">
		.add-btn {
			background: #29b609 none repeat scroll 0 0;
			color: #fff;
			font-size: 14px;
			padding: 3px 10px;
		}
		
		.add-btn a:hover {
			color: #fff;
		}
		
		.del-btn {
			background: #c61c00 none repeat scroll 0 0;
			color: #fff;
			font-size: 14px;
			padding: 3px 10px;
		}
</style>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("RFSAPPLY_FORM", function(form, valid) {
			
				if (valid) {
				
					var equipJson = $('#equipmentTable').MyEditableTable('getData'); 
					//equipJson = .replace(/\"/g, '\\\\"');
                    $("#RFSAPPLY_FORM").find("input[name='EQUIPMENT_JSON']").val(JSON.stringify(equipJson));
					
					//将提交按钮禁用,防止重复提交
					//var formData = $("#RFSAPPLY_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("RFSAPPLY_FORM");
					var url = $("#RFSAPPLY_FORM").attr("action");
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
			
			//实例化设备表格及初始化数据
            $('#equipmentTable').MyEditableTable({
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
                            $(this).find('input[iptName][type=text]').each(function(){
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
            
            
            $('#RFSAPPLY_FORM').find('input,textarea').prop("readonly", true);
            $('#RFSAPPLY_FORM').find('select').prop("disabled", "disabled");
            $('#RFSAPPLY_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
            $('#RFSAPPLY_FORM').find(".laydate-icon").attr('disabled',"disabled");
            
            
            var equipJson = '${materForm.EQUIPMENT_JSON }';
            //测试赋值
            //var equipJson = "[{\"amount\":\"1\",\"modelNum\":\"model1\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":1},{\"isOwned\":\"0\",\"amount\":\"2\",\"modelNum\":\"型号2\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":2},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":3},{\"isOwned\":\"1\",\"amount\":\"4\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":4},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":5},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":6},{\"isOwned\":\"0\",\"amount\":\"7\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":7},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":8},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":9},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":10},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":11},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":12},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":13},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":14},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":15},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":16},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":17},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":18},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":19},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":20},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":21},{\"isOwned\":\"1\",\"amount\":\"22\",\"modelNum\":\"型号22\",\"produceDate\":\"2017-01-22\",\"verifyDate\":\"2017-02-22\",\"index\":22},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":23},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":24},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":25},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":26},{\"isOwned\":\"0\",\"amount\":\"27\",\"modelNum\":\"型号27\",\"produceDate\":\"2017-01-27\",\"verifyDate\":\"2017-02-27\",\"index\":27},{\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"index\":28},{\"isOwned\":\"1\",\"amount\":\"29\",\"modelNum\":\"型号29\",\"produceDate\":\"2017-01-29\",\"verifyDate\":\"\",\"index\":29},{\"devName\":\"设备名称\",\"amount\":\"\",\"modelNum\":\"\",\"produceDate\":\"\",\"verifyDate\":\"\",\"devRemark\":\"\",\"index\":30}]";
            if(equipJson != '' && equipJson != '[]'){
                $('#equipmentTable').MyEditableTable("loadDataExtend", JSON.parse(equipJson));
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
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="RFSAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="EQUIPMENT_JSON" value="${materForm.EQUIPMENT_JSON }"/>
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
										placeholder="要求填写企业（公司）全称、企业预先核准全称或个体经营者姓名" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th>经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th> <font class="syj-color2">*</font>电话：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
										
									<th>传真：</th>
                                    <td><input type="text"
                                        class="syj-input1 validate[[],custom[fixFaxWithAreaCode]]" name="FAX" value="${materForm.FAX }"
                                        placeholder="请输入传真" maxlength="12" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="11" /></td>
									<th> 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="32" /></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
                            <span>设备条件</span>
                            <c:if test="${materForm.operType=='write' }">
                                <div style="float:right;">
                                    <a href="javascript:void(0);" class="del-btn" onclick="$('#equipmentTable').MyEditableTable('deleteRow');">删除</a>
                                    <a href="javascript:void(0);" class="add-btn" onclick="$('#equipmentTable').MyEditableTable('appendRow');">增加</a>
                                </div>
                            </c:if>
                        </div>
                        <div style="position:relative;">
                            <table id="equipmentTable" class="syj-table2" cellpadding="0" cellspacing="0">
                                <tr>
                                    <th style="width:30px;">序号</th>
                                    <th style="width:30px;"><input type="checkbox"/></th>
                                    <th style="width:120px;">装卸设备</th>
                                     <th style="width:80px;">型号</th>
                                    <th style="width:80px;">数量(台)</th>
                                    <th style="width:120px;">设施种类</th>
                                    <th style="width:120px;">面积(平方米)</th>
                                </tr>
                                <tr>
                                    <th>1</th>
                                    <th><input chkIndex="1" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="集装箱叉车"/>                                    
                                                                                集装箱叉车
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="通用仓库"/>
                                                                                通用仓库
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                               <tr>
                                    <th>2</th>
                                    <th><input chkIndex="2" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="正面吊运机"/>                                    
                                                                                正面吊运机
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="专用仓库"/>
                                                                                专用仓库
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                               <tr>
                                    <th>3</th>
                                    <th><input chkIndex="3" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1 " iptName="equipmentName" value="集装箱轨道吊机"/>                                    
                                                                                集装箱轨道吊机
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="简易仓库"/>
                                                                                简易仓库
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                 <tr>
                                    <th>4</th>
                                    <th><input chkIndex="4" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1 validate[required]" iptName="equipmentName" value="小型叉车"/>                                    
                                                                                小型叉车
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="集装箱堆场"/>
                                                                                集装箱堆场
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>5</th>
                                    <th><input chkIndex="5"  disabled type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="直举升叉车"/>                                    
                                                                                直举升叉车
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="通用堆场"/>
                                                                                通用堆场
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>6</th>
                                    <th><input chkIndex="6" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="搬运手推车"/>                                    
                                                                                搬运手推车
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="停车场"/>
                                                                                停车场
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                 <tr>
                                    <th>7</th>
                                    <th><input chkIndex="7" disabled  type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="货物传送带"/>                                    
                                                                               货物传送带
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="货物托运场所"/>
                                                                               货物托运场所
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>8</th>
                                     <th><input chkIndex="8" disabled type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="监控系统"/>                                    
                                                                               监控系统
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="有否高台"/>
                                                                               有否高台
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>9</th>
                                    <th><input chkIndex="9" disabled type="checkbox"/></th>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentName" value="计算机管理系统"/>                                    
                                                                              计算机管理系统
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 validate[required]" iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="hidden" class="syj-input1" iptName="equipmentType" value="检查口"/>
                                                                               检查口
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[required,custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                                 <tr>
                                    <th>10</th>
                                    <th><input chkIndex="10" type="checkbox"/></th>
                                    <td >
                                        <input type="text" class="syj-input1" maxlength="32" iptName="equipmentName" style="width:90%;"/>  
                                     </td>
                                  
                                    <td>
                                        <input type="text" class="syj-input1 " iptName="modelNum" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[custom[integer]]" iptName="amount" maxlength="10" style="width:78%;"/>
                                    </td>
                                    <td >
                                        <input type="text" class="syj-input1 " iptName="equipmentType" maxlength="32" style="width:90%;"/>
                                    </td>
                                    <td>
                                        <input type="text" class="syj-input1 validate[custom[number]]" iptName="area" maxlength="10" style="width:90%;"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
					</form>
				</div>
			</div>
		</div>
		
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#RFSAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
</body>