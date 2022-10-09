<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
      
      function showExcelallExportWindow(){
    	  
    	var CHILD_DEPART_ID = $("#DepartServiceAllItemForm input[name='Q_T.CHILD_DEPART_ID_EQ']").val();
  		var DEPART_ID = $("#DepartServiceAllItemForm input[name='Q_T.DEPART_ID_EQ']").val();
  		var FWSXZT = $("#DepartServiceAllItemForm input[name='Q_T.FWSXZT_EQ']").val();
  		var ISWANGSHEN = $("#DepartServiceAllItemForm input[name='Q_T.ISWANGSHEN_EQ']").val();
  		var ISWAILIAN = $("#DepartServiceAllItemForm input[name='Q_T.ISWAILIAN_EQ']").val();
  		var ISSWB = $("#DepartServiceAllItemForm input[name='Q_T.ISSWB_EQ']").val();
  		var SXXZ = $("#DepartServiceAllItemForm input[name='Q_T.SXXZ_EQ']").val();
  		var ITEMCODE = $("#DepartServiceAllItemForm input[name='Q_T.ITEM_CODE_LIKE']").val();
  		var ITEMNAME = $("#DepartServiceAllItemForm input[name='Q_T.ITEM_NAME_LIKE']").val();
  		var SXLX = $("#DepartServiceAllItemForm input[name='Q_T.SXLX_EQ']").val();
      	window.location.href = "statisticsController.do?exportAllItemToExcel&Q_T.CHILD_DEPART_ID_EQ="+
      			CHILD_DEPART_ID+"&Q_T.DEPART_ID_EQ="+DEPART_ID+"&Q_T.FWSXZT_EQ="+
      			FWSXZT+"&Q_T.ISSWB_EQ="+ISSWB+"&Q_T.SXXZ_EQ="+SXXZ+"&Q_T.ISWANGSHEN_EQ="
      			+ISWANGSHEN+"&Q_T.ISWAILIAN_EQ="+ISWAILIAN+"&Q_T.ITEM_NAME_LIKE="+ITEMNAME
      			+"&Q_T.ITEM_CODE_LIKE="+ITEMCODE+"&Q_T.SXLX_EQ="+SXLX;
      }
     
      function showExcelNotCanelExportWindow(){
    	  
      	var CHILD_DEPART_ID = $("#DepartServiceAllItemForm input[name='Q_T.CHILD_DEPART_ID_EQ']").val();
    		var DEPART_ID = $("#DepartServiceAllItemForm input[name='Q_T.DEPART_ID_EQ']").val();
    		var FWSXZT = $("#DepartServiceAllItemForm input[name='Q_T.FWSXZT_EQ']").val();
    		var ISWANGSHEN = $("#DepartServiceAllItemForm input[name='Q_T.ISWANGSHEN_EQ']").val();
    		var ISWAILIAN = $("#DepartServiceAllItemForm input[name='Q_T.ISWAILIAN_EQ']").val();
    		var ISSWB = $("#DepartServiceAllItemForm input[name='Q_T.ISSWB_EQ']").val();
    		var SXXZ = $("#DepartServiceAllItemForm input[name='Q_T.SXXZ_EQ']").val();
    		var ITEMCODE = $("#DepartServiceAllItemForm input[name='Q_T.ITEM_CODE_LIKE']").val();
    		var ITEMNAME = $("#DepartServiceAllItemForm input[name='Q_T.ITEM_NAME_LIKE']").val();
    		var SXLX = $("#DepartServiceAllItemForm input[name='Q_T.SXLX_EQ']").val();
        	window.location.href = "statisticsController.do?exportAllItemToExcel&Q_T.CHILD_DEPART_ID_EQ="+
        			CHILD_DEPART_ID+"&Q_T.DEPART_ID_EQ="+DEPART_ID+"&Q_T.FWSXZT_EQ="+
        			FWSXZT+"&Q_T.ISSWB_EQ="+ISSWB+"&Q_T.SXXZ_EQ="+SXXZ+"&Q_T.ISWANGSHEN_EQ="
        			+ISWANGSHEN+"&Q_T.ISWAILIAN_EQ="+ISWAILIAN+"&Q_T.ITEM_NAME_LIKE="+ITEMNAME
        			+"&Q_T.ITEM_CODE_LIKE="+ITEMCODE+"&Q_T.SXLX_EQ="+SXLX+"&Q_T.FWSXZT_NEQ=3";
        }
      
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
		//AppUtil.initAuthorityRes("DepartServiceallItemToolbar");
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
	$("#DepartServiceallItemGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
	
	//空数据，横向滚动
	$('#DepartServiceallItemGrid').datagrid({
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
		<div id="DepartServiceallItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#" class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
                                iconcls="eicon-file-excel-o" plain="true" 
                                    onclick="showExcelallExportWindow();">导出数据</a> 
                        <a href="#" class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
                                iconcls="eicon-file-excel-o" plain="true" 
                                    onclick="showExcelNotCanelExportWindow();">非取消库数据</a>         
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="DepartServiceAllItemForm" id="DepartServiceAllItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
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
                            <td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="36" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_EQ"
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">事项状态</td>
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
							<td style="width:88px;text-align:right;"  >入驻省网办</td>
							<td style="width:35px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.ISSWB_EQ"
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
                            </td>
						</tr>
						<tr style="height:28px;">
                            <td style="width:68px;text-align:right;">是否网申</td>
							<td style="width:35px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.ISWANGSHEN_EQ"
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
                            </td>
							<td style="width:68px;text-align:right;">是否外链</td>
							<td style="width:35px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.ISWAILIAN_EQ"
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
                            </td>
							<td style="width:88px;text-align:right;"  >到现场次数</td>
							<td style="width:35px;padding-left:4px;">
							<select class="easyui-combobox" name="Q_T.CKCS_EQ" style="width:128px;">
								<option value="">请选择</option>
								<option value=1>1</option>
								<option value=2>2</option>
								<option value=3>3</option>
								<option value=4>4</option>
                            </td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServiceallItemToolbar','DepartServiceallItemGrid')" />
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
			id="DepartServiceallItemGrid" fitcolumns="false" toolbar="#DepartServiceallItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?allDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'SWB_ITEM_CODE',align:'left'" width="20%" >省网办编码（唯一码）</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CHILD_DEPART_NAME',align:'left'" width="15%" >主办处室</th>
					<!-- <th data-options="field:'CATALOG_NAME',align:'left'" width="25%" >目录名称</th> -->
					<th data-options="field:'ITEM_NAME',align:'left'" width="25%" >事项名称</th>
					<th data-options="field:'DIC_NAME',align:'left'" width="10%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="10%" formatter="formatsxlx">事项类型</th>
					<th data-options="field:'FWSXZT',align:'left'" width="10%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'UPDATE_TIME',align:'left'" width="15%" >编辑时间</th>
					<th data-options="field:'APPLY_URL',align:'left'" width="25%" >链接网址</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="20%" >SXBM</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
