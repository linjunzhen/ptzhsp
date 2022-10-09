<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteServiceItemGridRecord() {
        var $dataGrid = $("#departServiceItemGrid");
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择您要删除的记录!",
                icon:"warning",
                ok: true
            });
        }else{
	        for ( var i = 0; i < rowsData.length; i++) {
                if(rowsData[i].FWSXZT!='-1'){
                	art.dialog({
		                content: "非草稿状态事项不可删除!",
		                icon:"warning",
		                ok: true
		            });
                    return false;
                }
            }
			AppUtil.deleteDataGridRecord("departServiceItemController.do?multiDel",
					"departServiceItemGrid");
        }
	};
	/**
     * 申请审核服务事项列表记录
     */
    
	function applydepartServiceItemGridRecord() {
		var $dataGrid = $("#departServiceItemGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		var isRightIdNotEmpty = true;
		if (rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0) {
			for ( var i = 0; i < rowsData.length; i++) {
				if(rowsData[i].RIGHT_ID == null ||rowsData[i].RIGHT_ID == 'undefined' ||rowsData[i].RIGHT_ID == ''){
					isRightIdNotEmpty = false;
				}
			}
		}
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要提交审核的记录!",
				icon : "warning",
				ok : true
			});
		}  else {
			var selectIds = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if (rowsData[i].FWSXZT != '-1' && rowsData[i].FWSXZT != '4') {
					art.dialog({
						content : "仅有草稿和退回状态的事项可申请审核!",
						icon : "warning",
						ok : true
					});
					return false;
				}
				if (i > 0) {
					selectIds += ",";
				}
				selectIds += rowsData[i].ITEM_ID;
			}
			$.post("departServiceItemController.do?checkNeed", {
				selectIds : selectIds
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					var colName = $dataGrid.datagrid('options').idField;  
					var selectColNames = "";
					for ( var i = 0; i < rowsData.length; i++) {
	                    if(i>0){
	                        selectColNames+=",";
	                    }
	                    selectColNames+=eval('rowsData[i].'+colName);
	                }
					 $.dialog.open("serviceItemController.do?applyInfo&itemIds=" + selectColNames+"&fileFlag=-1", {
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
				} else {
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			});
		}
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editdepartServiceItemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("departServiceItemGrid");
		if (entityId) {
			var $dataGrid = $("#departServiceItemGrid");
			var rowsData = $dataGrid.datagrid('getChecked');
			if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
				art.dialog({
					content : "请选择需要提交审核的记录!",
					icon : "warning",
					ok : true
				});
			} else {
				for ( var i = 0; i < rowsData.length; i++) {
					if (rowsData[i].FWSXZT != '-1' && rowsData[i].FWSXZT != '4') {
						art.dialog({
							content : "非草稿或退回状态的事项不可编辑!",
							icon : "warning",
							ok : true
						});
						return false;
					}
				}
				showdepartServiceItemWindow(entityId);
			}
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showdepartServiceItemWindow(entityId) {
		$.dialog.open("departServiceItemController.do?info&entityId="
				+ entityId, {
			title : "服务事项信息",
			width : "100%",
			height : "100%",
			left : "0%",
			top : "0%",
			fixed : true,
			lock : true,
			resize : false,
			close : function() {
				$("#departServiceItemGrid").datagrid('reload');
				//AppUtil.gridDoSearch("departServiceItemToolbar","departServiceItemGrid");
			}
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("departServiceItemToolbar");
	});

	function formatFwsxzt(val, row) {
		if (val == "-1") {
			return "<font color='red'><b>草稿</b></font>";
		} else if (val == "1") {
			return "<font color='blue'><b>发布</b></font>";
		} else if (val == "2" || val == "5") {
			return "<font color='green'><b>审核中</b></font>";
		} else if (val == "3") {
			return "<font color='black'><b>取消</b></font>";
		} else if (val == "4") {
			return "<font color='red'><b>退回</b></font>";
		}
	};

	function formatIsFromSwb (val, row) {
		if (val == "1") {
			return "<b>从省网办同步</b>";
		} else {
			return "<b> </b>";
		}
	};
    function receiveSwbItem(){
        AppUtil.ajaxProgress({
            url:"serviceItemController.do?receiveSwbItem",
            params:{
            },
            callback:function(resultJson){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                $("#departServiceItemGrid").datagrid("reload");
            }
        })
	}
    

	function receiveSwbItemList(){		
        parent.$.dialog.open("serviceItemController/swbRecSelector.do", {
            title : "接收事项列表",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
                if(selectCatalogInfo){
                    $("input[name='DEPART_ID']").val(selectCatalogInfo.departId);
                    $("input[name='DEPART_NAME']").val(selectCatalogInfo.departName);
                    $("input[name='SXXZ']").val(selectCatalogInfo.sxxz);
                    $("input[name='SXXZC']").val(selectCatalogInfo.sxxzc);
                    $("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
                    $("input[name='BILL_ID']").val(selectCatalogInfo.billId);
                    $("input[type=radio][name=FTA_FLAG][value="+selectCatalogInfo.fta+"]").attr("checked","checked");
                    
    				art.dialog.removeData("selectCatalogInfo");
                    $("#departServiceItemGrid").datagrid("reload");
                }
            }
        }, false);
	}
	function selectBillCatalog(){		
        parent.$.dialog.open("billRightController/catalogSelector.do", {
            title : "权责清单目录选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
                if(selectCatalogInfo){
                    $("input[name='DEPART_ID']").val(selectCatalogInfo.departId);
                    $("input[name='DEPART_NAME']").val(selectCatalogInfo.departName);
                    $("input[name='SXXZ']").val(selectCatalogInfo.sxxz);
                    $("input[name='SXXZC']").val(selectCatalogInfo.sxxzc);
                    $("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
                    $("input[name='BILL_ID']").val(selectCatalogInfo.billId);
                    $("input[type=radio][name=FTA_FLAG][value="+selectCatalogInfo.fta+"]").attr("checked","checked");
                    
    				art.dialog.removeData("selectCatalogInfo");
                }
            }
        }, false);
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
	$("#departServiceItemGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="departServiceItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_departServiceItem"
								iconcls="eicon-plus" plain="true"
								onclick="showdepartServiceItemWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_departServiceItem"
								iconcls="eicon-pencil" plain="true"
								onclick="editdepartServiceItemGridRecord();">编辑</a> 
<%--								<a href="#"--%>
<%--								class="easyui-linkbutton" reskey="DEL_departServiceItem"--%>
<%--								iconcls="eicon-trash-o" plain="true"--%>
<%--								onclick="deleteServiceItemGridRecord();">删除</a> --%>
								<a href="#"
                                class="easyui-linkbutton" reskey="APPLY_departServiceItem"
                                iconcls="eicon-level-up" plain="true"
                                onclick="applydepartServiceItemGridRecord();">申请审核</a>
                        <a href="#"
                                class="easyui-linkbutton" reskey=RECEIVE_SWB_ITEM
                                iconcls="eicon-cloud-download" plain="true"
                                onclick="receiveSwbItemList();">接收省网下发事项</a> 
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="departServiceItemForm">
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
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('departServiceItemToolbar','departServiceItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('departServiceItemForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="departServiceItemGrid" fitcolumns="true" toolbar="#departServiceItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?datagrid&Q_T.FWSXZT_EQ=-1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'RIGHT_ID',hidden:true">RIGHT_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="12%">唯一码（事项编码）</th>
					<th data-options="field:'FWSXZT',align:'left'" width="6%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="22%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="6%" >事项性质</th>
					<th data-options="field:'OPERATE_TIME',align:'left'" width="12%" >编辑时间</th>
					<th data-options="field:'SXLX',align:'left'" width="6%" >办件类型</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="13%" >所属部门</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>
					<th data-options="field:'IS_FROM_SWB',align:'left'" width="8%" formatter="formatIsFromSwb">事项来源</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
