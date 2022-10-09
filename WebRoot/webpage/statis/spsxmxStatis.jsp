<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    function exportSpsxmxExcel(){
    	var beginDate = $("#SpsxmxForm input[name='Q_T.CREATE_TIME_>=']").val();
    	var endDate = $("#SpsxmxForm input[name='Q_T.CREATE_TIME_<=']").val();
    	var bjBeginDate = $("#SpsxmxForm input[name='Q_T.END_TIME_>=']").val();
    	var bjEndDate = $("#SpsxmxForm input[name='Q_T.END_TIME_<=']").val();
    	var exeId = $("#SpsxmxForm input[name='Q_t.EXE_ID_LIKE']").val();
    	var isRemainTime=$("#SpsxmxForm input[name='Q_T.IS_REV_REMAINTIME_=']").val();
    	if(bjBeginDate!='' && bjBeginDate!=null && bjBeginDate!=undefined){
    		bjBeginDate = bjBeginDate+" 00:00:00";
    	}
    	if(bjEndDate!='' && bjEndDate!=null && bjEndDate!=undefined){
    		bjEndDate = bjEndDate+" 23:59:59";
    	}
    	if(beginDate!='' && beginDate!=null && beginDate!=undefined){
    		beginDate = beginDate+" 00:00:00";
    	}
    	if(endDate!='' && endDate!=null && endDate!=undefined){
    		endDate = endDate+" 23:59:59";
    	}
		var DEPTNAME = $("#SpsxmxForm input[name='DEPT_NAME']").val();
		if(DEPTNAME==''&&(beginDate==''&&endDate=='')){
			$.messager.alert('警告',"请选择部门或者当前年份时间，谢谢！");
			return;
		}
		var DEPART_ID = $("#SpsxmxForm input[name='Q_T.DEPART_ID_=']").val();
		var SUBJECT_LIKE = $("#SpsxmxForm input[name='Q_t.SUBJECT_LIKE']").val();
		var IS_YCTB = $("#SpsxmxForm input[name='Q_T.IS_YCTB_=']").val();
		var EVALUATE = $("#SpsxmxForm input[name='Q_t.EVALUATE_=']").val();
		var RUN_STATUS = $("#SpsxmxForm input[name='Q_T.RUN_STATUS_=']").val();
		var url="statisticsController.do?exportSpsxmxExcel&Q_T.CREATE_TIME_GE="+beginDate
		+"&Q_T.CREATE_TIME_LE="+endDate+"&Q_T.END_TIME_GE="+bjBeginDate+
		"&Q_T.END_TIME_LE="+bjEndDate+"&Q_t.SUBJECT_LIKE="+SUBJECT_LIKE+"&Q_t.EXE_ID_LIKE="+exeId
		+"&beginDate="+beginDate+"&Q_T.IS_REV_REMAINTIME_EQ="+isRemainTime+
		"&endDate="+endDate+"&DEPTNAME="+DEPTNAME+"&Q_T.DEPART_ID_EQ="+DEPART_ID+"&Q_T.RUN_STATUS_EQ="+RUN_STATUS+"&Q_T.IS_YCTB_EQ="+IS_YCTB+"&Q_t.EVALUATE_EQ="+EVALUATE;
		var encodeUrl = encodeURI(url);
    	var gotoLink = document.createElement('a'); 
    	gotoLink.href = encodeUrl;
    	document.body.appendChild(gotoLink); 
    	gotoLink.click(); 
    	
    }
    function exportSpsxmxExcelAll(){
    	/**
    	window.location.href = "statisticsController.do?exportSpsxmxExcel";
    	*/
    }
     /**
     * 导出execl
     */
    function showExcelExportSpsmx() {
        AppUtil.showExcelExportWin({
            dataGridId:"spsxmxStatisGrid",
            tplKey:"spsxmxb",
            excelName:"审批事项明细表",
            queryParams:{
                "D.DEPART_ID":$("input[name='Q_T.DEPART_ID_=']").val(),
                "E.SUBJECT":$("input[name='Q_t.SUBJECT_LIKE']").val(),
                "E.RUN_STATUS":$("input[name='Q_T.RUN_STATUS_=']").val(),
                "E.CREATE_TIME":$("input[name='Q_T.CREATE_TIME_>=']").val(),
                "Y.CREATE_TIME":$("input[name='Q_T.CREATE_TIME_<=']").val(),
                "E.END_TIME":$("input[name='Q_T.END_TIME_>=']").val(),
                "Y.CREATE_TIME":$("input[name='Q_T.END_TIME_<=']").val()
            }
        });
    };
	function formatSubject(val,row){
		//获取流程申报号
		var exeId = row.EXE_ID;
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+exeId;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
    //附件格式
    function  formatAtachfile(val,row){
                        var  fileids=val;
                        var  filePath=row.RESULT_FILE_URL;
                        var newhtml="";
                        var  fileName=row.FILE_NAME;
                        if(fileName!=null){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
	    		 		newhtml+=fileName+'</a>';
	    		 		newhtml+='</p>';
	    		 	    //$("#fileListDiv").html(newhtml);
	    		 	   return  newhtml;
	    		 	   }
    }
    
	$(document).ready(
			function() {
				var start1 = {
					elem : "#SysLogL.SPSXMX_TIME_BEGIN",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("开始时间必须小于等于结束时间,请重新输入!");
								$("input[name='Q_T.CREATE_TIME_>=']").val("");
							}
						}
					}
				};
				var end1 = {
					elem : "#SysLogL.SPSXMX_TIME_END",
					format : "YYYY-MM-DD",
					istime : false,
					choose : function(datas) {
						var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
								.val();
						var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
								.val();
						if (beginTime != "" && endTime != "") {
							var start = new Date(beginTime.replace("-", "/")
									.replace("-", "/"));
							var end = new Date(endTime.replace("-", "/")
									.replace("-", "/"));
							if (start > end) {
								alert("结束时间必须大于等于开始时间,请重新输入!");
								$("input[name='Q_T.CREATE_TIME_<=']").val("");
							}
						}
					}
				};
				laydate(start1);
				laydate(end1);
				var start2 = {
						elem : "#SysLogL.SPSXMXBJ_TIME_BEGIN",
						format : "YYYY-MM-DD",
						istime : false,
						choose : function(datas) {
							var beginTime = $("input[name='Q_T.END_TIME_>=']")
									.val();
							var endTime = $("input[name='Q_T.END_TIME_<=']")
									.val();
							if (beginTime != "" && endTime != "") {
								var start = new Date(beginTime.replace("-", "/")
										.replace("-", "/"));
								var end = new Date(endTime.replace("-", "/")
										.replace("-", "/"));
								if (start > end) {
									alert("开始时间必须小于等于结束时间,请重新输入!");
									$("input[name='Q_T.END_TIME_>=']").val("");
								}
							}
						}
					};
					var end2 = {
						elem : "#SysLogL.SPSXMXBJ_TIME_END",
						format : "YYYY-MM-DD",
						istime : false,
						choose : function(datas) {
							var beginTime = $("input[name='Q_T.END_TIME_>=']")
									.val();
							var endTime = $("input[name='Q_T.END_TIME_<=']")
									.val();
							if (beginTime != "" && endTime != "") {
								var start = new Date(beginTime.replace("-", "/")
										.replace("-", "/"));
								var end = new Date(endTime.replace("-", "/")
										.replace("-", "/"));
								if (start > end) {
									alert("结束时间必须大于等于开始时间,请重新输入!");
									$("input[name='Q_T.END_TIME_<=']").val("");
								}
							}
						}
					};
					laydate(start2);
					laydate(end2);

			});
			
	function showSelectDeparts(){
		var departId = $("input[name='Q_T.DEPART_ID_=']").val();
		$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "组织机构选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectDepInfo = art.dialog.data("selectDepInfo");
				if(selectDepInfo){
					$("input[name='Q_T.DEPART_ID_=']").val(selectDepInfo.departIds);
					$("input[name='DEPT_NAME']").val(selectDepInfo.departNames);
					art.dialog.removeData("selectDepInfo");
				}
			}
		}, false);
	}
	function resetSpsxmxForm(){
		$("#DEPARTID").val("");
		AppUtil.gridSearchReset('SpsxmxForm');
	}
	
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#spsxmxStatisGrid');  
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber*options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '12px, Microsoft YaHei');
        var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
        if(width>25){
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
			grid.datagrid("resize");
		}else{			
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#spsxmxStatisGrid').datagrid({
		onLoadSuccess: fixRownumber
	});

	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="spsxmxStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
<%--						         onclick="showExcelExportSpsmx();">导出数据</a> --%>
						         onclick="exportSpsxmxExcel();">导出查询数据</a> 
								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
<!-- 						         <a href="#" class="easyui-linkbutton" iconcls="icon-excel" plain="true"  -->
<!-- 						         onclick="exportSpsxmxExcelAll();">导出全部数据</a>  -->
<!-- 								 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a> -->
							
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SpsxmxForm" id="SpsxmxForm">
				<input type="hidden" id="DEPARTID" name="Q_T.DEPART_ID_=" value="">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">部门：</td>							
							<td style="width:100px;">							
							<input type="text"  placeholder="请选择部门"
								style="width:90px;float:left;" class="eve-input" name="DEPT_NAME" onclick="showSelectDeparts();"/>
							</td>
							<td style="width:80px;text-align:right;">申报时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SPSXMX_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SPSXMX_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>								
								
							<td style="width:80px;text-align:right;">办件名称：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.SUBJECT_LIKE" />
							</td>
							
					       <td style="width:98px;text-align:right;">是否一窗通办</td>
					       <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.IS_YCTB_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td> 
							<td style="width:98px;text-align:right;">评价信息查询</td>
							<td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_t.EVALUATE_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=PJXX',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">办件状态：</td>
<%--							<td style="width:108px;">--%>
<%--								<input class="easyui-combobox"--%>
<%--								style="width:90px;" name="Q_T.RUN_STATUS_="--%>
<%--								data-options="--%>
<%--url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=FlowRunStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 90,panelHeight: 'auto' " />--%>
<%--							</td>--%>
							<td>
								<select class="easyui-combobox" name="Q_T.RUN_STATUS_=">
									<option value="">请选择</option>
									<option value="0">草稿</option>
									<option value="1">预审不通过</option>
									<option value="2">在办</option>
									<option value="3">办结</option>
									<option value="4">审核不通过</option>
									<option value="5">退件</option>
									<option value="6">强制结束</option>
								</select>
							</td>
							<td style="width:80px;text-align:right;">办结时间从：</td>
							<td style="width:90px;"><input type="text"
								style="width:78px;float:left;" class="laydate-icon"
								id="SysLogL.SPSXMXBJ_TIME_BEGIN" name="Q_T.END_TIME_>=" /></td>
							<td style="width:125px;">到<input type="text"
								style="width:78px;float:right;" class="laydate-icon"
								id="SysLogL.SPSXMXBJ_TIME_END" name="Q_T.END_TIME_<=" /></td>
							<td style="width:80px;text-align:right;">申报号：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.EXE_ID_LIKE" />
							</td>
							<td style="width:98px;text-align:right;">是否收件超期</td>
							<td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.IS_REV_REMAINTIME_="  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('spsxmxStatisToolBar','spsxmxStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetSpsxmxForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="spsxmxStatisGrid" fitcolumns="false" toolbar="#spsxmxStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?spsxmxData">
			<thead data-options="frozen:true">
				<tr>
					<th data-options="field:'EXE_ID',align:'left'" width="12%" >申报号</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >部门</th>
					<th data-options="field:'SUBJECT',align:'left'" width="30%" formatter="formatSubject">办件名称</th>
				</tr>	
				</thead>
				<thead>	
				<tr>
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%" >审批服务项目名称</th>
					<th data-options="field:'EVALUATE',align:'left'" width="10%" >评价</th>
					<th data-options="field:'DIC_NAME',align:'left'" width="10%" >事项性质</th>
					<th data-options="field:'BJLX',align:'left'" width="10%" >办件类型</th>
					<th data-options="field:'SQR',align:'left'" width="15%" >申请姓名／单位</th>
					<th data-options="field:'JBR_MOBILE',align:'left'" width="10%" >手机号码</th>
<%--					<th data-options="field:'JBR_LXDH',align:'left'" width="100" >电话</th>--%>
<%--					<th data-options="field:'JBR_ADDRESS',align:'left'" width="150" >联系地址</th>--%>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%" >受理时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="15%" >办结时间</th>
					<th data-options="field:'CREATOR_NAME',align:'left'" width="10%" >经办人</th>
					<th data-options="field:'STATUS',align:'left'" width="10%" >办理状态</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%" >承诺时限</th>
					<th data-options="field:'JBR_NAME',align:'left'" width="10%" >联系人</th>
					<th data-options="field:'REV_REMAINTIME',align:'left'" width="10%" >收件剩余时长</th>
					<th data-options="field:'REV_STARTTIME',align:'left'" width="15%" >收件开始时间</th>
					<th data-options="field:'REV_ENDTIME',align:'left'" width="15%" >收件结束时间</th>
					<th data-options="field:'XKFILE_NUM',align:'left'" width="25%">许可文件编号</th>
					<th data-options="field:'XKFILE_NAME',align:'left'" width="25%">许可文件名称</th>
					<th data-options="field:'XKDEPT_NAME',align:'left'" width="10%">许可机关</th>
					<th data-options="field:'EFFECT_TIME',align:'left'" width="10%">生效时间</th>
					<th data-options="field:'CLOSE_TIME',align:'left'" width="10%">许可截止时间</th>
					<th data-options="field:'XKCONTENT',align:'left'" width="25%">许可内容</th>
					<th data-options="field:'RUN_MODE',align:'left'" width="10%">经营方式</th>
					<th data-options="field:'FILE_NAME',align:'left'" width="10%" hiden="true>附件名</th>
					<th data-options="field:'RESULT_FILE_URL',align:'left'" width="10%"  hidden="ture" >经营方式</th>
					<th data-options="field:'RESULT_FILE_ID',align:'left'" width="10%" formatter="formatAtachfile">附件</th>
					<th data-options="field:'CUR_NODE',align:'left'" width="10%">关联节点</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>