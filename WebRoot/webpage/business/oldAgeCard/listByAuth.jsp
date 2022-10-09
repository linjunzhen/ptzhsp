<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<!-- lodop begin -->
<script type="text/javascript" src="plug-in/lodop-6.198/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/lodop-6.198/licPrint.js"></script>
<script type="text/javascript" src="plug-in/lodop-6.198/LodopFuncs.js"></script>
<!-- lodop end -->
<script type="text/javascript">
	$(document).ready(function(){
		var startTimeInput = {
	    	elem: "#OldAgeCardListByAuth.CREATE_TIME_BEGIN",
	     	format: "YYYY-MM-DD hh:mm:ss",
	     	istime: true,
	     	choose: function(data){
	         	var beginTime = data;
	  		    var endTime = $("#OldAgeCardListByAuthToolbar").find("input[name='Q_E.CREATE_TIME_<=']").val();
	  		    if(beginTime != "" && endTime != ""){
	  		    	if(beginTime > endTime){
		  		    	alert("起始时间必须小于等于截止时间，请重新输入！");
		  		    	$("#OldAgeCardListByAuthToolbar").find("input[name='Q_E.CREATE_TIME_>=']").val("");
	  		    	}
	  		   	}
	     	}
		};
	  	var endTimeInput = {
	     	elem: "#OldAgeCardListByAuth.CREATE_TIME_END",
	     	format: "YYYY-MM-DD hh:mm:ss",
	     	istime: true,
	     	choose: function(data){
	     		var beginTime = $("#OldAgeCardListByAuthToolbar").find("input[name='Q_E.CREATE_TIME_>=']").val();
			    	var endTime = data;
			    	if(beginTime != "" && endTime != ""){
			    		if(beginTime > endTime){
			    			alert("截止时间必须大于等于起始时间，请重新输入！");
			    			$("#OldAgeCardListByAuthToolbar").find("input[name='Q_E.CREATE_TIME_<=']").val("");
			    		}
			    	}
	     	}
		};
	  	laydate(startTimeInput);
	    laydate(endTimeInput);
	    AppUtil.initAuthorityRes("OldAgeCardListByAuthToolbar");
	});
    
    function formatOldAgeCardListByAuthSubject(val, row){
    	//获取流程状态
		var runStatus = row.RUN_STATUS;
		//获取流程申报号
		var exeId = row.EXE_ID;
		//当前环节
		var curTache = row.CUR_STEPNAMES;
		var href = "<a href='";
		if(runStatus == "0"){
			href += "executionController.do?goStart&exeId="+exeId;
		}else{
			href += "executionController.do?goDetail&exeId="+exeId;
		}
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
    }
    
    function formatOldAgeCardListByAuthSex(val, row){
    	if(val == '1'){
    		return '男';
    	}else if(val == '2'){
    		return '女';
    	}
    }
    
    function formatOldAgeCardListByAuthSource(val, row){
    	if(val == '0'){
    		return '现场办理';
    	}else if(val == '1'){
    		return '五彩麒麟app';
    	}else if(val == '2'){
    		return '岚岛网格采集';
    	}
    }
    
    function formatOldAgeCardListByAuthCartType(val, row){
    	if(val == '1'){
    		return "<font color='green'><b>绿证</b></font>";
    	}else if(val == '2'){
    		return "<font color='red'><b>红证</b></font>";
    	}
    }
    
    function formatOldAgeCardListByAuthCartStatus(val, row){
    	if(val == '0'){
    		return "<font color='#0368ff'><b>正常</b></font>";
    	}else if(val == '1'){
    		return "<font color='#ff4b4b'><b>注销</b></font>";
    	}
    }
    
    function doOldAgeCardNewApply(){
    	window.open("oldAgeCardController.do?newApply", "_blank");
    }
    
    function doOldAgeCardChangeApply(){
    	var $dataGrid = $("#OldAgeCardListByAuthGrid");
    	var rowsData = $dataGrid.datagrid("getChecked");
    	if(!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)){
    		art.dialog({
				content: "请选择需要被操作的记录!",
				icon: "warning",
			    ok: true
			});
    	}else if(rowsData.length > 1){
    		art.dialog({
				content: "只能选择一条记录进行操作!",
				icon: "warning",
			    ok: true
			});
    	}else{
    		var runStatus = rowsData[0].RUN_STATUS;
			if(runStatus != '2'){
				art.dialog({
					content: "已办结的优待证才能进行补换证件操作!",
					icon: "warning",
				    ok: true
				});
				return;
			}
			var busId = rowsData[0].BUSINESS_ID;
			$.dialog.open("oldAgeCardController.do?goChangeApply&busId="+busId, {
			    title: "老年人优待证-补换证件",
				width: "750px",
				lock: true,
				resize: true,
				height: "450px"
			});
    	}
    }
    
    function doOldAgeCardPrint(){
    	var $dataGrid = $("#OldAgeCardListByAuthGrid");
    	var rowsData = $dataGrid.datagrid("getChecked");
    	if(!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)){
    		art.dialog({
				content: "请选择需要被操作的记录!",
				icon: "warning",
			    ok: true
			});
    	}else if(rowsData.length > 1){
    		art.dialog({
				content: "只能选择一条记录进行操作!",
				icon: "warning",
			    ok: true
			});
    	}else{
    		var runStatus = rowsData[0].RUN_STATUS;
			if(runStatus != '2'){
				art.dialog({
					content: "已办结的优待证才能进行打印证件操作!",
					icon: "warning",
				    ok: true
				});
				return;
			}
			var cardStaus = rowsData[0].CARD_STATUS;
			if(cardStaus != '0'){
				art.dialog({
					content: "已注销的优待证不能进行打印证件操作!",
					icon: "warning",
				    ok: true
				});
				return;
			}
			var busId = rowsData[0].BUSINESS_ID;
			AppUtil.ajaxProgress({
				url: 'oldAgeCardController.do?get',
				params: {busId:busId},
				callback: function(resultJson){
					if(resultJson.success){
						var flowSubmitObj = $.parseJSON(resultJson.jsonString);
						var LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
						LODOP.PRINT_INITA(-24, -7, 343, 234, "福建省老年人优待证打印");
						LODOP.SET_PRINT_PAGESIZE(1, 1000, 700, "CreateCustomPage");
						if(flowSubmitObj.CARD_TYPE == '1'){
							LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath%>webpage/business/oldAgeCard/images/GreenOldAgeCard.jpg'/>");
							LODOP.SET_PRINT_STYLE("FontSize", 11);
							LODOP.ADD_PRINT_TEXT("1.728cm", "1.979cm", "1.664cm", "0.664cm", flowSubmitObj.SQRMC);
							if(flowSubmitObj.SQRXB == '1'){
								LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "男");
							}else if(flowSubmitObj.SQRXB == '2'){
								LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "女");
							}
							LODOP.ADD_PRINT_TEXT("1.728cm", "5.628cm", "0.799cm", "0.667cm", flowSubmitObj.APPLICANT_NATION);
							LODOP.ADD_PRINT_TEXT("2.347cm", "2.029cm", "4.27cm", "0.664cm", flowSubmitObj.SQRSFZ);
							LODOP.ADD_PRINT_TEXT("2.979cm", "1.976cm", "4.217cm", "1.064cm", flowSubmitObj.APPLICANT_ADDR);
							LODOP.ADD_PRINT_TEXT("4.006cm", "2.056cm", "3.874cm", "0.664cm", flowSubmitObj.CARD_NUM);
							LODOP.ADD_PRINT_TEXT("4.628cm", "2.056cm", "3.953cm", "0.664cm", flowSubmitObj.CERTIFICATE_DATE);
							LODOP.ADD_PRINT_TEXT("5.247cm", "2.056cm", "3.979cm", "0.717cm", flowSubmitObj.CERTIFICATE_DEPT);
						}else if(flowSubmitObj.CARD_TYPE == '2'){
							LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath%>webpage/business/oldAgeCard/images/RedOldAgeCard.jpg'/>");
							LODOP.SET_PRINT_STYLE("FontSize", 11);
							LODOP.ADD_PRINT_TEXT("1.728cm", "1.979cm", "1.664cm", "0.664cm", flowSubmitObj.SQRMC);
							if(flowSubmitObj.SQRXB == '1'){
								LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "男");
							}else if(flowSubmitObj.SQRXB == '2'){
								LODOP.ADD_PRINT_TEXT("1.728cm", "4.225cm", "0.632cm", "0.664cm", "女");
							}
							LODOP.ADD_PRINT_TEXT("1.728cm", "5.628cm", "0.799cm", "0.667cm", flowSubmitObj.APPLICANT_NATION);
							LODOP.ADD_PRINT_TEXT("2.347cm", "2.029cm", "4.27cm", "0.664cm", flowSubmitObj.SQRSFZ);
							LODOP.ADD_PRINT_TEXT("2.979cm", "1.976cm", "4.217cm", "1.064cm", flowSubmitObj.APPLICANT_ADDR);
							LODOP.ADD_PRINT_TEXT("4.006cm", "2.056cm", "3.874cm", "0.664cm", flowSubmitObj.CARD_NUM);
							LODOP.ADD_PRINT_TEXT("4.628cm", "2.056cm", "3.953cm", "0.664cm", flowSubmitObj.CERTIFICATE_DATE);
							LODOP.ADD_PRINT_TEXT("5.247cm", "2.056cm", "3.979cm", "0.717cm", flowSubmitObj.CERTIFICATE_DEPT);
						}
						LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 340);
						LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 227);
						LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", 1);
						LODOP.SET_SHOW_MODE("BKIMG_PRINT", 0);
						LODOP.PREVIEW();
					}else{
						alert(resultJson.msg);
					}
				}
			});
    	}
    }
    
    function exportOldAgeCardData(){
    	var params = $("form[name='OldAgeCardListByAuthForm']").serialize();
    	window.open('oldAgeCardController.do?export&'+params, '_blank');
    }

	//空数据，横向滚动
	$('#OldAgeCardListByAuthGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="OldAgeCardListByAuthToolbar">
            <!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index:1111;top:0px;left:0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <a href="#" class="easyui-linkbutton" iconCls="eicon-plus" reskey="NEW_OldAgeCard"  
						       plain="true" onclick="doOldAgeCardNewApply();">新证办理</a>
						    <a href="#" class="easyui-linkbutton" iconCls="eicon-edit" reskey="CHANGE_OldAgeCard"  
						       plain="true" onclick="doOldAgeCardChangeApply();">补换证件</a>
						    <a href="#" class="easyui-linkbutton" iconCls="eicon-print" reskey="PRINT_OldAgeCard"  
						       plain="true" onclick="doOldAgeCardPrint();">打印证件</a>
						    <a href="#" class="easyui-linkbutton" iconCls="eicon-file-excel-o" reskey="EXPORT_OldAgeCard"  
						       plain="true" onclick="exportOldAgeCardData();">导出数据</a>
						    <a class="easyui-linkbutton" style="border-width:0;height:0;width:0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form name="OldAgeCardListByAuthForm" action="#">
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
			        <tbody>
			            <tr style="height:28px;">
			                <td style="width:68px;text-align:right;">申报号</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_E.EXE_ID_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">流程状态</td>
			                <td style="width:140px;padding-left:4px;">
			                    <input class="easyui-combobox" name="Q_E.RUN_STATUS_=" style="width:148px;" 
								       data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:148,panelHeight:'auto'"/>
			                </td>
			                <td style="width:68px;text-align:right;">申请来源</td>
			                <td style="width:140px;padding-left:4px;">
			                    <input class="easyui-combobox" name="Q_T.BUSINESS_SOURCE_=" style="width:148px;" 
								       data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=OldAgeCardApplySource',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:148,panelHeight:'auto'"/>
			                </td>
			                <td colspan="2"></td>
			            </tr>
			            <tr style="height:28px;">
			            	<td style="width:68px;text-align:right;">申请人</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_E.SQRMC_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">身份证号</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_E.SQRSFZ_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">申请日期</td>
							<td style="width:140px;">
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardListByAuth.CREATE_TIME_BEGIN" name="Q_E.CREATE_TIME_>=" />
						    </td>
							<td style="width:30px;text-align:right;">至</td>
							<td>
							    <input type="text" style="width:128px;float:left;" class="laydate-icon"
								       id="OldAgeCardListByAuth.CREATE_TIME_END" name="Q_E.CREATE_TIME_<=" />
						    </td>
			            </tr>
			            <tr>
			                <td style="width:68px;text-align:right;">所属乡镇</td>
			                <td style="width:140px;">
			                    <input class="eve-input" type="text" name="Q_T.ACCEPTDEPT_NAME_LIKE" maxlength="20" style="width:142px;"/>
			                </td>
			                <td style="width:68px;text-align:right;">优待证类别</td>
							<td style="width:140px;padding-left:4px;">
							    <input class="easyui-combobox" name="Q_T.CARD_TYPE_=" style="width:148px;" 
								       data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=OldAgeCardType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:148,panelHeight:'auto'"/>
						    </td>
							<td style="width:68px;text-align:right;">优待证状态</td>
							<td style="width:140px;padding-left:4px;">
							    <input class="easyui-combobox" name="Q_T.CARD_STATUS_=" style="width:148px;" 
								       data-options="url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=OldAgeCardStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:148,panelHeight:'auto'"/>
						    </td>
						    <td></td>
						    <td>
						        <input type="button" value="查询" class="eve-button"
								       onclick="AppUtil.gridDoSearch('OldAgeCardListByAuthToolbar','OldAgeCardListByAuthGrid')" />
								<input type="button" value="重置" class="eve-button"
									   onclick="AppUtil.gridSearchReset('OldAgeCardListByAuthForm')" />
							</td>
			            </tr>
			        </tbody>
			    </table>
			</form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			   id="OldAgeCardListByAuthGrid" fitcolumns="true" toolbar="#OldAgeCardListByAuthToolbar"
			   method="post" idfield="BUSINESS_ID" checkonselect="true" singleSelect="true" 
			   selectoncheck="true" fit="true" border="false" nowrap="false"
			   url="oldAgeCardController.do?listByAuth">
			<thead>
			    <tr>
			        <th field="ck" checkbox="true"></th>
			        <th data-options="field:'BUSINESS_ID',hidden:true">BUSINESS_ID</th>
			        <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
			        <th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatOldAgeCardListByAuthSubject">流程标题</th>
					<th data-options="field:'BUSINESS_SOURCE',align:'left'" width="8%" formatter="formatOldAgeCardListByAuthSource">申请来源</th>
					<th data-options="field:'SQRMC',align:'left'" width="10%">申请人</th>
					<th data-options="field:'SQRXB',align:'left'" width="5%" formatter="formatOldAgeCardListByAuthSex">性别</th>
					<th data-options="field:'SQRSFZ',align:'left'" width="15%">身份证号</th>
					<th data-options="field:'SQRSJH',align:'left'" width="10%">联系电话</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>
					<th data-options="field:'ACCEPTDEPT_NAME',align:'left'" width="10%">所属乡镇</th>
					<th data-options="field:'OPERATOR_NAME',align:'left'" width="10%">窗口收件人员</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="10%" formatter="FlowUtil.formatRunStatus">流程状态</th>
				    <th data-options="field:'CARD_TYPE',align:'left'" width="10%" formatter="formatOldAgeCardListByAuthCartType">优待证类别</th>
					<th data-options="field:'CARD_STATUS',align:'left'" width="10%" formatter="formatOldAgeCardListByAuthCartStatus">优待证状态</th>
			    </tr>
			</thead>
		</table>
        <!-- =========================表格结束==========================-->
    </div>
</div>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
    <embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0"></embed>
</object>