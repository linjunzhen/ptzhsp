<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteDepartServiceCancelItemGridRecord() {
	    AppUtil.deleteDataGridRecord("serviceItemController.do?multiDel",
	            "DepartServiceCancelItemGrid");
	};
	/**
	 * 批量审核服务事项列表记录
	 */
	function regainServiceItemWindow() {
	    var $dataGrid = $("#DepartServiceCancelItemGrid");
	    var rowsData = $dataGrid.datagrid('getChecked');
	    if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
	        art.dialog({
	            content: "请选择需要恢复的办事事项!",
	            icon:"warning",
	            ok: true
	        });
	    }else{
	        art.dialog.confirm("您确定要恢复选中的办事事项吗?", function() {
	            var layload = layer.load('正在提交请求中…');
	            var colName = $dataGrid.datagrid('options').idField;  
	            var selectColNames = "";
	            for ( var i = 0; i < rowsData.length; i++) {
	                if(i>0){
	                    selectColNames+=",";
	                }
	                selectColNames+=eval('rowsData[i].'+colName);
	            }
	            $.post("serviceItemController.do?updateFwsxzt",{
	                   selectColNames:selectColNames,
	                   state:"1"
	                }, function(responseText, status, xhr) {
	                    layer.close(layload);
	                    var resultJson = $.parseJSON(responseText);
	                    if(resultJson.success == true){
	                        art.dialog({
	                            content: resultJson.msg,
	                            icon:"succeed",
	                            time:3,
	                            ok: true
	                        });
	                            $dataGrid.datagrid('reload');
	                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
	                    }else{
	                        art.dialog({
	                            content: resultJson.msg,
	                            icon:"error",
	                            ok: true
	                        });
	                    }
	            });
	        });
	    }
	};
	$(document).ready(function() {
		AppUtil.initAuthorityRes("DepartServiceCancelItemToolbar");
	});
	$(document).ready(function(){
	    var start1 = {
	    elem: "#DepCan.OPERATE_TIME_BEGIN",
	    format: "YYYY-MM-DD 00:00:00",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_QL.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_QL.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_QL.OPERATE_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#DepCan.OPERATE_TIME_END",
	    format: "YYYY-MM-DD 23:59:59",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_QL.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_QL.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_QL.OPERATE_TIME_<=']").val("");
	    		}
	    	}
	    }
	};
	laydate(start1);
	laydate(end1);

});
	function formatFwsxzt(val,row){
		if(val=="-1"){
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>发布</b></font>";
		}else if(val=="2"){
			return "<font color='green'><b>审核中</b></font>";
		}else if(val=="3"){
			return "<font color='black'><b>取消</b></font>";
		}else if(val=="4"){
            return "<font color='red'><b>退回</b></font>";
        }
	};
	/**
	* 查看服务事项详细信息
	*/
	function viewCancelDepartServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("DepartServiceCancelItemGrid");
	 if (entityId) {
	     $.dialog.open("serviceItemController.do?detailedInfo&entityId=" + entityId, {
		     title : "详细信息",
		     width: "100%",
		     height: "100%",
		     left: "0%",
		     top: "0%",
		     fixed: true,
		     lock : true,
		     resize : false,
		     close: function () {
		         //AppUtil.gridDoSearch("ServiceItemXzywToolbar","ServiceItemXzywGrid");
		     }
		 }, false);
	 }
	}
	function showCSelectDeparts(){
        var departId = $("input[id='CdepartId']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[id='CdepartId']").val(selectDepInfo.departIds);
                    $("input[id='CdepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showCSelectSonDeparts(){
        var departId = $("input[id='CchildDepartId']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属子部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[id='CchildDepartId']").val(selectDepInfo.departIds);
                    $("input[id='CchildDepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function resetCancelForm(){
    	AppUtil.gridSearchReset('DepartServiceCancelItemForm');
    	$("input[id='CdepartId']").val('');
    	$("input[id='CchildDepartId']").val('');
    	
    }
	
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});
	$("#DepartServiceCancelItemGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
	
	//空数据，横向滚动
	$('#DepartServiceCancelItemGrid').datagrid({
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
		<div id="DepartServiceCancelItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#" class="easyui-linkbutton" reskey="REGAIN_DepartServiceItem"
                                iconcls="eicon-reply" plain="true"
                                onclick="regainServiceItemWindow();">恢复</a>
							<a href="#"
                                class="easyui-linkbutton" reskey="DEL_DepartServiceItemCancel"
                                iconcls="eicon-trash-o" plain="true"
                                onclick="deleteDepartServiceCancelItemGridRecord();">删除</a> 
                            <a href="#"
                                class="easyui-linkbutton" reskey="VIEW_DepartServiceItem"
                                iconcls="eicon-file-text-o" plain="true"
                                onclick="viewCancelDepartServiceItemGridRecord();">详细信息</a>
                                
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DepartServiceCancelItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="36" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">起始日期</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								id="DepCan.OPERATE_TIME_BEGIN" 
								name="Q_QL.OPERATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">截止日期</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								id="DepCan.OPERATE_TIME_END"
								name="Q_QL.OPERATE_TIME_&lt;=" /></td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showCSelectDeparts();"
                                style="width:128px;" id="CdepartName" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" id="CdepartId" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">所属子部门</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showCSelectSonDeparts();"
                                style="width:128px;" id="CchildDepartName" value="" readonly="readonly"/>
                                 <input type="hidden" id="CchildDepartId" name="Q_T.ZBCS_ID_EQ" value=""  />
                            </td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServiceCancelItemToolbar','DepartServiceCancelItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetCancelForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartServiceCancelItemGrid" fitcolumns="true" toolbar="#DepartServiceCancelItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?datagrid&Q_T.FWSXZT_EQ=3">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="15%">唯一码（事项编码）</th>
					<th data-options="field:'FWSXZT',align:'left'" width="8%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="20%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="8%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>						
					<th data-options="field:'OPERATE_TIME',align:'left'" width="13%" >取消时间</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>
					<th data-options="field:'BACKOR_NAME',align:'left'" width="10%" >审核人</th>
					<th data-options="field:'THYJ',align:'left'" width="15%" nowrap="false">审核意见</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
