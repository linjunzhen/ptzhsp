<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 批量审核服务事项列表记录
	 */
	function cancelServiceItemGridRecord() {
	    var $dataGrid = $("#ServicePublishItemGrid");
	    var rowsData = $dataGrid.datagrid('getChecked');
	    if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
	        art.dialog({
	            content: "请选择需要取消的办事事项!",
	            icon:"warning",
	            ok: true
	        });
	    }else{
	        art.dialog.confirm("您确定要取消选中的办事事项吗?", function() {
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
                       state:"5"
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
		AppUtil.initAuthorityRes("ServicePublishItemToolbar");
	});
	
	function formatFwdx(val,row){
		if(val=="1"){
			return "公众";
		}else if(val=="2"){
			return "企业";
		}else if(val=="3"){
			return "部门";
		}
	}
	
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
     * 编辑服务事项列表记录
     */
    function editPublishServiceItemGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("ServicePublishItemGrid");
        if (entityId) {
            showPublishServiceItemWindow(entityId);
        }
    }

    /**
     * 显示服务事项信息对话框
     */
    function showPublishServiceItemWindow(entityId) {
        $.dialog.open("serviceItemController.do?info&entityId=" + entityId, {
            title : "服务事项信息",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false,
            close: function () {
                AppUtil.gridDoSearch("ServicePublishItemToolbar","ServicePublishItemGrid");
            }
        }, false);
    };
    
    /**
     * 导出execl
     */
    function showExcelExportWindow() {
        AppUtil.showExcelExportWin({
            dataGridId:"ServicePublishItemGrid",
            tplKey:"ServiceItemReportTpl",
            excelName:"服务事项数据",
            queryParams:{
                "T.ITEM_CODE":$("input[name='Q_T.ITEM_CODE_LIKE']").val(),
                "T.ITEM_NAME":$("input[name='Q_T.ITEM_NAME_LIKE']").val(),
                "T.SXXZ":$("input[name='Q_T.SXXZ_=']").val()
            }
        });
    };
    
    $(document).ready(function() {
        var catalogTreeSetting = {
            edit : {
                enable : false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                selectedMulti : false
            },
            callback : {
                onClick : onCatalogTreeClick
            },
            async : {
                enable : true,
                url : "catalogController.do?tree"
            }
        };
        $.fn.zTree.init($("#catalogTree"), catalogTreeSetting);
        
        $("#ServiceItemXzywGrid").datagrid({
            onDblClickRow: function(index, row){
                showServiceItemXzywWindow(row.ITEM_ID);
            }
        });
    });
    
    function gbUrl(){
        var  cname = $("#mlmc").val();
        var zTree = $.fn.zTree.getZTreeObj("catalogTree");
        zTree.setting.async.url = "catalogController.do?tree";
        zTree.setting.async.otherParam = {
            "Q_SC.CATALOG_NAME_LIKE" : cname,
        }
        zTree.reAsyncChildNodes(null, "refresh");
    }
    /**
     * 点击树形节点触发的事件
     */
    function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
            $("#ServicePublishItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(treeNode.id);
            AppUtil.gridDoSearch("ServicePublishItemToolbar","ServicePublishItemGrid");
        }else if(event.target.tagName == "SPAN"&&treeNode.id==0){
            $("#ServicePublishItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val("");
            AppUtil.gridDoSearch("ServicePublishItemToolbar","ServicePublishItemGrid");
        }
    }
    //保存排序
    function updateCsn(){
            var rows = $("#ServicePublishItemGrid").datagrid("getRows"); 
            var itemIds = [];
            for(var i=0;i<rows.length;i++){
            	itemIds.push(rows[i].ITEM_ID);
            }
            if(itemIds.length>0){
                AppUtil.ajaxProgress({
                    url:"serviceItemController.do?updateSn",
                    params:{
                    	itemIds:itemIds
                    },
                    callback:function(resultJson){
                          parent.art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                        $("#ServicePublishItemGrid").datagrid("reload");
                    }
                })
            }
        
    }
    
    function showSelectDeparts(){
        var departId = $("input[name='Q_D.DEPART_ID_EQ']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&noAuth=true&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_D.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showSelectSonDeparts(){
        var departId = $("input[name='Q_DC.DEPART_ID_EQ']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&noAuth=true&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属子部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_DC.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='SON_DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    /**
	* 查看服务事项详细信息
	*/
	function viewPublishServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("ServicePublishItemGrid");
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
	/**
	* 服务事项详细信息
	*/
	function printPublishServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("ServicePublishItemGrid");
	 if (entityId) {
	     var dateStr = "";
		dateStr +="&ITEM_ID="+entityId;
		dateStr +="&typeId="+3;
		dateStr +="&TemplatePath=itemdetail.doc";
		dateStr +="&TemplateName=服务事项详细信息";
		//打印模版
       $.dialog.open(encodeURI("printAttachController.do?printItemTemplate"+dateStr), {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	 }
	}
    
    function resetPublishForm(){
    	AppUtil.gridSearchReset('ServicePublishItemForm');
    	$("input[name='Q_D.DEPART_ID_EQ']").val('');
    	$("input[name='Q_DC.DEPART_ID_EQ']").val('');
    	
    }
	
	//空数据，横向滚动
	$('#ServicePublishItemGrid').datagrid({
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
    <div data-options="region:'west',split:false"
        style="width:250px;">
        <div class="right-con">
            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">                
                <div class="e-toolbar-ct">
                    <div class="e-toolbar-overflow">
                        <div style="color:#005588;">
                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                style="position: relative; top:7px; float:left;" />&nbsp;服务目录树
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            目录名称：<input class="eve-input" style="width:90px;" name="mlmc" id="mlmc"/>
        <input type="button" value="查询" class="eve-button" onclick="gbUrl();" />
        <ul id="catalogTree" class="ztree"></ul>
    </div>
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="ServicePublishItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#"
                                class="easyui-linkbutton"
                                iconcls="eicon-pencil" plain="true"
                                onclick="editPublishServiceItemGridRecord();">编辑</a>
                                <a href="#" class="easyui-linkbutton" 
                                resKey="SAVECSN_ServiceItem" iconCls="eicon-check" plain="true"
                                onclick="updateCsn();">保存排序</a>
                                <a href="#"
                                class="easyui-linkbutton" reskey="CANCEL_ServiceItem"
                                iconcls="eicon-remove" plain="true"
                                onclick="cancelServiceItemGridRecord();">申请取消</a> 
                                <a href="#" class="easyui-linkbutton" reskey="Expor_ServiceItem"
                                iconcls="eicon-file-excel-o" plain="true" 
                                    onclick="showExcelExportWindow();">导出数据</a> 
						<a href="#"
                                class="easyui-linkbutton"
                                iconcls="eicon-file-text-o" plain="true"
                                onclick="viewPublishServiceItemGridRecord();">详细信息</a>
                        <a href="#"
                                class="easyui-linkbutton" reskey="PRINT_ServiceItem"
                                iconcls="eicon-print" plain="true"
                                onclick="printPublishServiceItemGridRecord();">打印</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServicePublishItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项编码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showSelectDeparts();"
                                style="width:128px;" name="DEPART_NAME" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">所属子部门</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showSelectSonDeparts();"
                                style="width:128px;" name="SON_DEPART_NAME" value="" readonly="readonly"/>
                                 <input type="hidden" name="Q_DC.DEPART_ID_EQ" value=""  />
                            </td>
							<td colspan=""><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('ServicePublishItemToolbar','ServicePublishItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetPublishForm();" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ServicePublishItemGrid" fitcolumns="true" toolbar="#ServicePublishItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			data-options="onLoadSuccess:function(){
                      $(this).datagrid('enableDnd');
            }"
			url="serviceItemController.do?publishdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="18%">事项编码</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="30%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="10%" >办件类型</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<!--  
					<th data-options="field:'FWDX',align:'left'" width="100" formatter="formatFwdx">服务对象</th>
					-->
					<th data-options="field:'CNQXGZR',align:'left'" width="14%">承诺时限工作日</th>
					<th data-options="field:'FWSXZT',align:'left'" width="10%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'C_SN',align:'left'" width="10%">排序值</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
