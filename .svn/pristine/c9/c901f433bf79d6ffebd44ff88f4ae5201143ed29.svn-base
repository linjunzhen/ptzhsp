<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	/**
     * 删除
     */
    function deleteZjkGridRecord() {
        AppUtil.deleteDataGridRecord("gcjsxfZjkController.do?multiDel",
                "GcjsxfZjkGrid");
    };

    /**
     * 编辑
     */
    function editZjkGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("GcjsxfZjkGrid");
        if (entityId) {
            showZjkWindow(entityId);
        }
    }
    
    /***
    * 新增专家库信息
    */
    function showZjkWindow(entityId) {
        $.dialog.open("gcjsxfZjkController.do?zjkinfo&entityId=" + entityId, {
            title : "专家库信息",
            width : "830px",
            lock : true,
            resize : false,
            height : "400px",
        }, false);
    };
	
	
	function formatState(val,row){
		//获取流程申报号
		if(val=="0"){
			return "<font color='#0368ff'><b>禁用</b></font>";
		}else if(val=="1"){
			return "<font color='green'><b>启用</b></font>";
		}
	}

	//空数据，横向滚动
	$('#GcjsxfZjkGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="GcjsxfZjkToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-plus" plain="true"
								onclick="showZjkWindow();">新增</a> 
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-pencil" plain="true"
								onclick="editZjkGridRecord();">编辑</a> 
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteZjkGridRecord();">删除</a> 							 								
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="GcjsxfZjkToolbarSearchForm">
			<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;" > 
			     <tbody>
			      <tr style="height:28px;"> 
			        <td style="width:70px;text-align:right;">姓名：</td> 
			        <td style="width:156px;"> 
						<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.ZJXM_LIKE" />
				    </td> 
				    <td style="width:80px;text-align:right;">工作单位：</td> 
			        <td style="width:156px;"> 
						<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.WORK_UNIT_LIKE" />
				    </td>
				    <td style="width:70px;text-align:right;">启用状态：</td> 
				    <td style="width:100px;"> 
						<select defaultemptytext="请选择状态" class="eve-input" name="Q_T.STATE_=">
							<option value="">请选择状态</option>
							<option value="1">启用</option>
							<option value="0">禁用</option>							
						</select>		
				    </td>	   
				   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('GcjsxfZjkToolbar','GcjsxfZjkGrid');" />
				       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('GcjsxfZjkToolbarSearchForm')" />
				    </td> 
			      </tr> 
			     </tbody>
			    </table> 
			</form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="GcjsxfZjkGrid" fitcolumns="true" method="post" idfield="JBXX_ID" checkonselect="true"  fit="true"
			  singleSelect="true" selectoncheck="false" border="false" toolbar="#GcjsxfZjkToolbar"
			  nowrap="false" url="gcjsxfZjkController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'JBXX_ID',hidden:true">JBXX_ID</th>					
					<th data-options="field:'ZJXM',align:'left'" width="15%">姓名</th>
					<th data-options="field:'SJH',align:'left'" width="12%">手机号</th>
					<th data-options="field:'WORK_UNIT',align:'left'" width="20%">工作单位</th>
					<th data-options="field:'JOB_DESC',align:'left'" width="28%">专业技术职称或注册执业资格</th>
					<th data-options="field:'STATE',align:'left'" width="8%" formatter="formatState">状态</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="12%">创建时间</th>		
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               