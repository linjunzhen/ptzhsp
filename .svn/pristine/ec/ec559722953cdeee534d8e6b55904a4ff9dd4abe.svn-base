<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	/**
     * 批量审核服务事项列表记录
     */
    function prePublishDepartServiceItemGridRecord() {
        var $dataGrid = $("#DepartServiceItemPrePublish");
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要审核通过的记录!",
                icon:"warning",
                ok: true
            });
        }else{
                var colName = $dataGrid.datagrid('options').idField;  
                var selectColNames = "";
                for ( var i = 0; i < rowsData.length; i++) {
                    if(i>0){
                        selectColNames+=",";
                    }
                    selectColNames+=eval('rowsData[i].'+colName);
                }
                $.dialog.open("serviceItemController.do?auditingBackInfo&itemIds=" + selectColNames+"&fileFlag=8", {
                    title : "审核意见",
                    width: "600px",
                    height: "400px",
                    fixed: true,
                    lock : true,
                    resize : false,
                    close: function () {
                        var backinfo = art.dialog.data("backinfo");
                        if(backinfo&&backinfo.back){
                            art.dialog({
                                content: "提交成功",
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                            art.dialog.removeData("backinfo");
                            $dataGrid.datagrid('reload');
                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
                        }
                    }
                }, false);
        }
        
    };
	/**
	 *审核服务事项列表记录
	 */
	function prePublishDepartServiceItem() {
		var entityId = AppUtil.getEditDataGridRecord("DepartServiceItemPrePublish");
		if (entityId) {
			showPrePublishDepartServiceItemWindow(entityId);
		}
	}
	
	/**
     * 显示服务事项信息对话框
     */
    function showPrePublishDepartServiceItemWindow(entityId) {
        $.dialog.open("departServiceItemController.do?info&shan=1&entityId=" + entityId, {
            title : "服务事项信息",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false,
            close: function () {
                $("#DepartServiceItemPrePublish").datagrid('reload');
                //AppUtil.gridDoSearch("DepartServiceItemPrePublishToolbar","DepartServiceItemPrePublish");
            }
        }, false);
    };
	$(document).ready(function() {
		AppUtil.initAuthorityRes("DepartServiceItemPrePublishToolbar");
	});
	
	function formatFwsxzt(val,row){
		if(val=="-1"){
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>发布</b></font>";
		}else if(val=="2"||val=="5"){
			return "<font color='green'><b>审核中</b></font>";
		}else if(val=="3"){
			return "<font color='black'><b>取消</b></font>";
		}else if(val=="4"){
			return "<font color='red'><b>退回</b></font>";
        }else if(val=="8"){
        	return "<font color='blue'><b>拟发布</b></font>";
        }
	};
	
    function recoverPrePublishItemGridRecord(){
    	var $dataGrid = $("#DepartServiceItemPrePublish");
	    var rowsData = $dataGrid.datagrid('getChecked');
	    if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
	        art.dialog({
	            content: "请选择需要追回的办事事项!",
	            icon:"warning",
	            ok: true
	        });
	    }else{
	        art.dialog.confirm("您确定要追回选中的办事事项吗?", function() {
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
	                   state:"-1"
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
    }
	/**
	* 打印服务事项详细信息 拟发布库
	*/
	function printPrePublishDepartServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("DepartServiceItemPrePublish");
	 if (entityId) {
	     var dateStr = "";
		dateStr +="&ITEM_ID="+entityId;
		dateStr +="&MODELPATH=itemdetailPDF.docx";
		 $.dialog.open("printAttachController.do?printPDFItemTemplate"+dateStr, {
             title : "",
             width: "100%",
             height: "100%",
             left: "0%",
             top: "3%",
             fixed: true,
             lock : true,
             resize : false
     }, false);
	 //  window.open(__ctxPath+"/printAttachController.do?printPDFItemTemplate"+dateStr, "_blank", "menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
	 }
	}
	
	function showPreSelectDeparts(){
        var departId = $("input[id='PredepartId']").val();
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
                    $("input[id='PredepartId']").val(selectDepInfo.departIds);
                    $("input[id='PredepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showPreSelectSonDeparts(){
        var departId = $("input[id='PrechildDepartId']").val();
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
                    $("input[id='PrechildDepartId']").val(selectDepInfo.departIds);
                    $("input[id='PrechildDepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function resetPreForm(){
    	AppUtil.gridSearchReset('DepartServiceItemPrePublishForm');
    	$("input[id='PredepartId']").val('');
    	$("input[id='PrechildDepartId']").val('');
    	
    }
    
    //附件格式
    function  formatAtach(val,row){
                        var  fileids=val;
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
  //附件格式
    function  formatAtach3(val,row){
                        var  fileids=val;
                        var newhtml="";
                        var  fileName=row.FILE_NAME3;
                        if(fileName!=null){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
	    		 		newhtml+=fileName+'</a>';
	    		 		newhtml+='</p>';
	    		 	    //$("#fileListDiv").html(newhtml);
	    		 	   return  newhtml;
	    		 	   }
    }
	
	//空数据，横向滚动
	$('#DepartServiceItemPrePublish').datagrid({
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
		<div id="DepartServiceItemPrePublishToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
								<a href="#"
								class="easyui-linkbutton" reskey="Pre_DepartServiceItem"
								iconcls="eicon-check" plain="true"
								onclick="prePublishDepartServiceItem();">审核</a>  
								<a href="#"
                                class="easyui-linkbutton" reskey="Pre_DepartServiceItems"
                                iconcls="eicon-th-list" plain="true"
                                onclick="prePublishDepartServiceItemGridRecord();">批量审核</a>
                                <a href="#"
                                class="easyui-linkbutton" reskey="RECOVER_DepartPreServiceItem"
                                iconcls="eicon-rotate-left" plain="true"
                                onclick="recoverPrePublishItemGridRecord();">追回</a>
                        		<a href="#"
                                class="easyui-linkbutton" reskey="PRINT_DepartPreServiceItem"
                                iconcls="eicon-print" plain="true"
                                onclick="printPrePublishDepartServiceItemGridRecord();">打印</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DepartServiceItemPrePublishForm">
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
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showPreSelectDeparts();"
                                style="width:128px;" id="PredepartName" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" id="PredepartId" value=""  />
                            </td>
						</tr>
						<tr style="height:28px;">
                            <td style="width:68px;text-align:right;">所属子部门</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showPreSelectSonDeparts();"
                                style="width:128px;" id="PrechildDepartName" value="" readonly="readonly"/>
                                 <input type="hidden" id="PrechildDepartId" name="Q_T.ZBCS_ID_EQ" value=""  />
                            </td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServiceItemPrePublishToolbar','DepartServiceItemPrePublish')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetPreForm()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartServiceItemPrePublish" fitcolumns="false" toolbar="#DepartServiceItemPrePublishToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?prePublishdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="15%">唯一码（事项编码）</th>
					<th data-options="field:'FWSXZT',align:'left'" width="10%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="8%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>			
					<th data-options="field:'OPERATE_TIME',align:'left'" width="13%" >提交时间</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>
					<th data-options="field:'THSJ2',align:'left'" width="13%" >退回时间</th>
					<th data-options="field:'USERNAME2',align:'left'" width="10%" >退回人</th>
					<th data-options="field:'THYJ2',align:'left'" width="15%" nowrap="false">退回意见</th>
					<th data-options="field:'USERNAME3',align:'left'" width="10%" >申请人</th>
					<th data-options="field:'THYJ3',align:'left'" width="15%" nowrap="false">申请审核意见</th>
					<th data-options="field:'FILEATTACH_PATH3',align:'left'" width="15%"  formatter="formatAtach3">申请审核附件</th>
					<th data-options="field:'USERNAME1',align:'left'" width="10%" >申请审核人</th>
					<th data-options="field:'THYJ',align:'left'" width="15%" nowrap="false">审核意见</th>
					<th data-options="field:'FILEATTACH_PATH',align:'left'" width="15%"  formatter="formatAtach">审核附件</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
