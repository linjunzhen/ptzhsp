<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	.formatButton {
	    color: #fff !important;
	    background: #0c83d3;
	    cursor: pointer;
	    padding: 0 20px;
	    height: 25px;
	    border: 1px solid #0c83d3;
	    vertical-align: top;
	    border-radius: 3px;
	    display: inline-block;
	    line-height: 24px;
	    margin: 5px;
	}
	.formatButton:hover{
	    opacity: .8;
	}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		AppUtil.initAuthorityRes("projectFlowDetailToolbar");
		var start1 = {
		    elem: "#DepPub.APPLY_DATE_BEGIN",
		    format: "YYYY-MM-DD 00:00:00",
		    istime: false,
		    choose: function(datas){
		        var beginTime = $("input[name='Q_T.APPLY_DATE_>=']").val();
		    	var endTime = $("input[name='Q_T.APPLY_DATE_<=']").val();
		    	if(beginTime!=""&&endTime!=""){
		    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
		    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
		    		if(start>end){
		    			alert("开始时间必须小于等于结束时间,请重新输入!");
		    			$("input[name='Q_T.APPLY_DATE_>=']").val("");
		    		}
		    	}
		    }
		};
		var end1 = {
		    elem: "#DepPub.APPLY_DATE_END",
		    format: "YYYY-MM-DD 23:59:59",
		    istime: false,
		    choose: function(datas){
		        var beginTime = $("input[name='Q_T.APPLY_DATE_>=']").val();
		    	var endTime = $("input[name='Q_T.APPLY_DATE_<=']").val();
		    	if(beginTime!=""&&endTime!=""){
		    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
		    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
		    		if(start>end){
		    			alert("结束时间必须大于等于开始时间,请重新输入!");
		    			$("input[name='Q_T.APPLY_DATE_<=']").val("");
		    		}
		    	}
		    }
		};
		laydate(start1);
		laydate(end1);
	});
	/**
	 * 显示项目信息信息对话框
	 */
	function showProjectFlowDetailWindow(entityId) {
		/* $.dialog.open("projectFlowController.do?info&entityId=" + entityId, {
			title : "编辑项目信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false); */
		
		toUrl("projectFlowController.do?projectInfoTab&entityId=" + entityId,"");
		/* toUrl("projectFlowController.do?info&entityId=" + entityId,""); */
	};
	/**
	 * 项目事项列表
	 */
	function listDetailItemGridRecord(entityId, flowCateId, projectCode,stageId){
		if (entityId) {
			 $.dialog.open("projectDetailController/ItemDetailSelector.do?PROJECT_CODE="+projectCode+"&FLOW_CATE_ID=" + flowCateId+"&stageId="+stageId, {
				title : "事项列表",
				width : "1200px",
				height : "700px",
				lock : true,
				resize : false,
				close : function() {
					var selectProjectItemInfo = art.dialog.data("selectProjectItemInfo");
					if (selectProjectItemInfo) {
						var defKey = selectProjectItemInfo.defKeys;
						var itemCode = selectProjectItemInfo.itemCodes;													
						art.dialog.removeData("selectProjectItemInfo");	
						toUrl("executionController.do?goStart&defKey="
								+ defKey + "&itemCode=" + itemCode,PROJECT_CODE);
					}
				}
			}, false); 
		}
	}
	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#projectFlowDetailToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	function formatProjectFlowButton(value, row, index) {
		var html = "<a href=\"#\" class=\"formatButton\" onclick=\"showProjectFlowDetailWindow('"+row.ID+"');\">项目信息</a>";
		html +="&nbsp;<a href=\"#\" class=\"formatButton\" onclick=\"listDetailItemGridRecord('"+row.ID+"','"+row.FLOW_CATE_ID+"','"+row.PROJECT_CODE+"','"+row.STAGE_ID+"')\">项目事项</a>";
        return html;
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="projectFlowDetailToolbar">
			<form action="#" name="projectFlowDetailForm"> 
			    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
				    <tbody>
					    <tr style="height:28px;">
					        <td style="width:100px;text-align:right;">项目名称：</td> 
					        <td style="width:204px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_NAME_LIKE"/>
						    </td> 
					        <td style="width:100px;text-align:right;">项目编码：</td> 
					        <td style="width:204px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_CODE_LIKE"/>
						    </td>
						    <td style="width:100px;text-align:right;">项目类型：</td>
							<td style="width:204px;padding-left:4px;">
								<input class="easyui-combobox" style="width:210px;" name="Q_T.TYPE_ID_="
								data-options="
						url:'projectFlowController.do?loadConfigType',editable:false,method: 'post',valueField:'TYPE_ID',textField:'TYPE_NAME',panelWidth: 210,panelHeight: 'auto' " />
							</td>
							<td style="width:100px;text-align:right;">建设性质：</td> 
							<td style="width:204px;padding-left:4px;">
								<input class="easyui-combobox" style="width:210px;" name="Q_T.PROJECT_NATURE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=PROJECTNATURE',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 210,panelHeight: 'auto' " />
							</td>
							<td style="width:100px;text-align:right;"colspan="2"></td> 
					    </tr>
					    <tr style="height:28px;">
					        
							<td style="width:100px;text-align:right;">是否完成区域评估:</td> 
							<td style="width:204px;padding-left:4px;">
								<input class="easyui-combobox" style="width:210px;" name="Q_T.SFWCQYPG_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 210,panelHeight: 'auto' " />
							</td>
							<td style="width:100px;text-align:right;">项目投资来源：</td> 
							<td style="width:204px;padding-left:4px;">
								<input class="easyui-combobox" style="width:210px;" name="Q_T.XMTZLY_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=XMTZLY',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 210,panelHeight: 'auto' " />
							</td>
							<td style="width:100px;text-align:right;">申报起始日期：</td>
							<td style="width:204px;padding-left:4px;">
								<input class="eve-input" type="text" maxlength="20" style="width:204px;"
								id="DepPub.APPLY_DATE_BEGIN" name="Q_T.APPLY_DATE_&gt;=" />
							</td>
							<td style="width:100px;text-align:right;">申报截止日期：</td>
							<td style="width:204px;padding-left:4px;">
								<input class="eve-input" type="text" maxlength="60" style="width:204px;"
								id="DepPub.APPLY_DATE_END" name="Q_T.APPLY_DATE_&lt;=" />
							</td>
					    </tr>
					    <tr style="height:28px;">
					    	<td style="width:100px;text-align:right;">建设单位：</td> 
					        <td style="width:204px;padding-left:4px;"> 
								<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_X.ENTERPRISE_NAME_LIKE"/>
						    </td> 
						    <td style="width:100px;text-align:right;">项目阶段：</td> 
					        <td style="width:204px;padding-left:4px;">
								<select defaultemptytext="请选择项目阶段" class="eve-input" name="Q_S.NAME_=" style="width:205px;">
									<option value="">请选择项目阶段</option>
									<option value="立项用地规划许可">立项用地规划许可</option>
									<option value="工程建设许可">工程建设许可</option>
									<option value="施工许可">施工许可</option>
									<option value="竣工验收">竣工验收</option>
								</select>
							</td>
						    <td style="width:100px;text-align:center;" colspan="2">
						    	<input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('projectFlowDetailToolbar','projectFlowDetailGrid')" />
						        <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('projectFlowDetailForm')" />
						    </td> 
					    </tr>
				    </tbody>
				</table> 
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="projectFlowDetailGrid" fitcolumns="true" toolbar="#projectFlowDetailToolbar"
			method="post" idfield="ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectFlowController.do?datagrid">
			<thead>
				<tr>
					<th data-options="field:'ID',hidden:true">ID</th>
					<th data-options="field:'PROJECT_CODE',align:'left'" width="15%">项目代码</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="20%">项目名称</th>
					<th data-options="field:'STAGE_NAME',align:'left'" width="12%">项目阶段</th>
					<th data-options="field:'TYPE_NAME',align:'left'" width="18%">项目类型</th>
					<th data-options="field:'APPLY_DATE',align:'left'" width="10%">申报时间</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="10%">创建时间</th>
					<th data-options="field:'operation',align:'left'" width="15%" formatter="formatProjectFlowButton">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
