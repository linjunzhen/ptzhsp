<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,picupload"></eve:resources>
<script type="text/javascript">
var rootPath='<%=path%>';
$(function() {
	AppUtil.initWindowForm("DeployFrom", function(form, valid) {
		if (valid) {
			
			if(!$('#BUSSYS_UNITCODECombx').combobox('getValue') || $('#BUSSYS_UNITCODECombx').combobox('getValue') ==''){
				$.messager.alert('警告',"业务单位不能为空！");
				return;
			}
			if(!$('#BUSSYS_PLATCODECombx').combobox('getValue') || $('#BUSSYS_PLATCODECombx').combobox('getValue') == ''){
				$.messager.alert('警告',"业务系统不能为空！");
				return;
			}
			
			if(!$("#DEPLOY_FILEPIC").val() || $("#DEPLOY_FILEPIC").val() == ''){
				$.messager.alert('警告',"请上传部署图！");
				return;
			}
			
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#DeployFrom").serialize();
			var url = $("#DeployFrom").attr("action");
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						parent.$("#DeployGrid").datagrid("reload");
						AppUtil.closeLayer();
					} else {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}
	}, "T_LCJC_BUS_DEPLOY");
	
	 /*
	 * 图片上传控件
	 */
	 $("#deployUploadBtn").picupload({
         url: rootPath+"/UploadServlet?busTableName=T_LCJC_BUS_DEPLOY&attachKey=deploypic&uploadPath=deploypic", //上传地址
         fileName: "file_deploypic"+new Date().getTime(),    //文件名称。用于后台接收
         // 上传之后回调
         success: function(data) {
         	$("#IMAGE_PATH_IMG").attr("src",data.filePath);
         	$("#DEPLOY_FILEPIC").val(data.filePath);         	
          }
     });
});	

/**
 * 
 */
function showSelectDeparts(){
	var departId = $("#BUSSYS_UNITID").val();
	parent.$.dialog.open("departmentController.do?selector&departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
		title : "所属业务单位",
        width:"600px",
        lock: true,
        resize:false,
        height:"500px",
        close: function () {
			var selectDepInfo = art.dialog.data("selectDepInfo");
			if(selectDepInfo){
				$("#BUSSYS_UNITID").val(selectDepInfo.departIds);
				$("#BUSSYS_UNITCODE").val(selectDepInfo.departCodes);
    			$("input[name='BUSSYS_UNITNAME']").val(selectDepInfo.departNames);
    			//级联刷新业务系统下拉选择
    			if(selectDepInfo.departCodes){
    				$("#BUSSYS_PLATCODECombx").combobox("clear");
        			var url = "busSysController.do?comboxSystems&uintId="+selectDepInfo.departIds;
    			    $("#BUSSYS_PLATCODECombx").combobox("reload",url); 
    			}   			
			}
		}
	}, false);
};
</script>
 <style> 
   .divcss5{border:1px solid #666;display:table-cell;vertical-align:middle;text-align:center;width:200px; height:200px;overflow:hidden;} 
   .divcss5 img{max-width:200px;_width:expression(this.width > 200 ? "200px" : this.width);max-height:200px;_height:expression(this.height > 200 ? "200px" : this.height);vertical-align:middle;} 
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<div id="DePloyInfoTabDiv" class="easyui-layout" fit="true">
 <div data-options="region:'center'" fit="true">
	<form id="DeployFrom" method="post" action="busDeployController.do?saveOrUpdate">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ID" value="${deploy.ID}">
			<input type="hidden" id="BUSSYS_PLATNAME" name="BUSSYS_PLATNAME" value="${deploy.BUSSYS_PLATNAME}">
			<input type="hidden" id="DEPLOY_FILEPIC" name="DEPLOY_FILEPIC" value="${deploy.DEPLOY_FILEPIC}">
			<input type="hidden" id="BUSSYS_UNITNAME" name="BUSSYS_UNITNAME" value="${deploy.BUSSYS_UNITNAME}">
			<%-- <input type="hidden" id="BUSSYS_UNITID" value="${deploy.BUSSYS_UNITID}"> --%>
	
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<%-- <td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
						<input style="width:350px;height:25px;float:left;"
							class="eve-input validate[required]" value="${deploy.BUSSYS_UNITNAME}"
							name="BUSSYS_UNITNAME" readonly="readonly" onclick="showSelectDeparts();"
							 />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td> --%>	
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
								<input class="easyui-combobox" style="width:350px;" id="BUSSYS_UNITCODECombx"
									name="BUSSYS_UNITCODE" value="${deploy.BUSSYS_UNITCODE}"
									data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
									method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 350,panelHeight: '200',
									onSelect:function(rec){
									   if(rec.UNIT_ID){
									   	  $('#BUSSYS_PLATCODECombx').combobox('clear');
			        			          var url1 = 'busSysController.do?comboxSystems&uintId='+rec.UNIT_ID;
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1); 						   	  
									   }
									   if(rec.UNIT_NAME){
									   	  $('input[name=BUSSYS_UNITNAME]').val(rec.UNIT_NAME);
									   }
									},
									onLoadSuccess:function(){
									  	var unitcode = $('#BUSSYS_UNITCODECombx').combobox('getValue');
									  	if(unitcode != ''){
										  $('#BUSSYS_PLATCODECombx').combobox('clear');
			        			          var url1 = 'busSysController.do?comboxSystemsUnitCode&unitcode='+unitcode;
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1);
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('setValue','${deploy.BUSSYS_PLATCODE}');
			    			    		}
									} 
								" />
								<font class="dddl_platform_html_requiredFlag">*</font>	
							</td>				
				</tr>
				<tr>
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">业务系统：</span>
						<input style="width:350px;height:25px;float:left;" id="BUSSYS_PLATCODECombx"
							class="easyui-combobox validate[required]" value="${deploy.BUSSYS_PLATCODE}"
							name="BUSSYS_PLATCODE"
							data-options="url:'busSysController.do?comboxSystems',
											editable:false,method: 'post',valueField:'SYSTEM_CODE',
											textField:'SYSTEM_NAME',panelWidth: 350,panelHeight: '250',
											onSelect:function(rec){
											   if(rec.SYSTEM_NAME){
											   	  $('#BUSSYS_PLATNAME').val(rec.SYSTEM_NAME);
											   }
											}
										" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>					
				</tr>								
				<tr>
					<td colspan="2"><span style="width: 150px;float:left;text-align:right;">对接方式：</span>
						<input type="text" style="width:350px;float:left;" maxlength="5"
						class="easyui-combobox validate[required]" value="${deploy.DEPLOY_TYPE}"
						name="DEPLOY_TYPE"
						data-options="url:'dictionaryController.do?load&typeCode=DEPLOYTYPE',
											editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',
											panelWidth: 350,panelHeight: 'auto'" 
						/>
						<font class="dddl_platform_html_requiredFlag">*</font>
				    </td>					
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">部署网络拓扑</td>
				</tr>
				<tr>
					<td align="center">
						<div style="width:200px;  margin:0px auto;">
							<div class="divcss5">
								<c:if test="${deploy.ID == null}">						    
									<img id="IMAGE_PATH_IMG" style="border:none; max-width:100%; max-height:100%;" src="<%=path%>/webpage/flowchart/deploy/images/u5.png"/>
								</c:if>
								<c:if test="${deploy.ID != null}">
									<img id="IMAGE_PATH_IMG" style="border:none; max-width:100%; max-height:100%;" src="${deploy.DEPLOY_FILEPIC}"/>
								</c:if>
							 </div>
						 </div>
					</td>					
				</tr>
				<tr>
					<td align="center">
						<div id="deployUploadBtn" style="margin:0 0 0 15px;background:url('webpage/flowchart/deploy/images/tab_btn_sc.png') no-repeat">
						</div>						
					</td>
				</tr>				
			</table>
			<div class="eve_buttons" style="position: relative;">
				<input value="提交" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</form>
 </div>
</div>
</body>