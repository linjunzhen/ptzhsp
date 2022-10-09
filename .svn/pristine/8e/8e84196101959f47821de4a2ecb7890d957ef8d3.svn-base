<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
      
  
      
      function showallSelectDeparts(){
        var departId = $("input[name='Q_T.DEPART_ID_EQ']").val();
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
                    $("input[name='Q_T.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_ALL_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showallSelectSonDeparts(){
        var departId = $("input[name='Q_T.CHILD_DEPART_ID_EQ']").val();
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
                    $("input[name='Q_T.CHILD_DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='CHILE_DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("DepartServiceAllCatalogViewToolbar");
	});
	function formatsxlx(val,row){
		if(val=="1"){
			return "即办件";
		}else if(val=="2"){
			return "普通件";
		}else if(val=="3"){
			return "特殊件";
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
        }else if(val=="8"){
        	return "<font color='blue'><b>拟发布</b></font>";
        }else if(val=="5"){
        	return "<font color='green'><b>审核取消</b></font>";
        }
	};
	 function resetForm(){
	    	AppUtil.gridSearchReset('DepartServiceAllItemForm');
	    	$("input[name='Q_T.DEPART_ID_EQ']").val('');
	    	$("input[name='Q_T.CHILD_DEPART_ID_EQ']").val('');
	    	$("input[name='Q_T.ISSWB_EQ']").val('');
	    	$("input[name='Q_T.ISWAILIAN_EQ']").val('');
	    	$("input[name='Q_T.ISWANGSHEN_EQ']").val('');
	    }
	  /**
	* 查看服务事项详细信息
	*/
	function viewAllCatalogViewDepartServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("DepartServiceAllCatalogViewGrid");
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
	
	//空数据，横向滚动
	$('#DepartServiceAllCatalogViewGrid').datagrid({
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
		<div id="DepartServiceAllCatalogViewToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#"
                                class="easyui-linkbutton" reskey="VIEW_DepartServiceItem"
                                iconcls="eicon-file-text-o" plain="true"
                                onclick="viewAllCatalogViewDepartServiceItemGridRecord();">详细信息</a>      
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DepartServiceAllItemForm" id="DepartServiceAllItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">所在库</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.FWSXZT_EQ"  
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemStatus',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">事项类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_EQ"  
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
					<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showallSelectDeparts();"
                                style="width:128px;" name="DEPART_ALL_NAME" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_T.DEPART_ID_EQ" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">主办处室</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showallSelectSonDeparts();"
                                style="width:128px;" name="CHILE_DEPART_NAME" value="" readonly="readonly"/>
                                 <input type="hidden" name="Q_T.CHILD_DEPART_ID_EQ" value=""  />
                            </td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">目录名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.CATALOG_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServiceAllCatalogViewToolbar','DepartServiceAllCatalogViewGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetForm();" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartServiceAllCatalogViewGrid" fitcolumns="false" toolbar="#DepartServiceAllCatalogViewToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?allDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CHILD_DEPART_NAME',align:'left'" width="15%" >主办处室</th>
					<!-- <th data-options="field:'CATALOG_NAME',align:'left'" width="25%" >目录名称</th> -->
					<th data-options="field:'CODE',align:'left'" width="20%" >唯一码</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="15%" >事项编码</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="30%" >事项名称</th>
					<th data-options="field:'SXLX',align:'left'" width="10%" formatter="formatsxlx">事项类型</th>
					<th data-options="field:'FWSXZT',align:'left'" width="10%" formatter="formatFwsxzt" >所在库</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
