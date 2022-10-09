<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
function doSelectRows(){
    var rows = $("#WtitemsGrid").datagrid("getSelections");
    if(rows.length==0){
        alert("请至少选择一条记录!");
    }else{
        var wtbdinfos = new Array();
        for(var i=0;i<rows.length;i++){
        	wtbdinfos.push({
        		bdc_name:rows[i].QLRMC,
        		bdc_dyh:rows[i].BDCDYH,
        		bdc_addr:rows[i].ZL,
        		bdc_cqzh:rows[i].BDCQZH
        	});
        }
        art.dialog.data("selectBdItemsInfo", {
        	wtbdinfos:JSON.stringify(wtbdinfos)
        });
        AppUtil.closeLayer();
    }    
}

$(function(){
	$("#WtItemsForm").validationEngine();
});

function bdcQuery(){
	var count = 0;
	var validateResult =$("#WtItemsForm").validationEngine("validate");
	if(validateResult){
		AppUtil.gridDoSearch('WtItemsToolbar','WtitemsGrid');
		$('#WtitemsGrid').datagrid({
			onLoadSuccess:function(){
				//确保初始化后只执行一次
				if(count == 0){
					var rows = $("#WtitemsGrid").datagrid("getRows");
					if(rows.length==0){
						parent.art.dialog({
							content: "无匹配数据返回，查询记录为空！",
							icon:"warning",
							ok: true
						});
						count ++;
					}
				}
			}
		});
	}
}

</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false">
            <div id="WtItemsToolbar">
                <!--====================开始编写隐藏域============== -->
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
                    <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                        <div class="e-toolbar-ct">
                            <div class="e-toolbar-overflow">
                               <div style="color:#005588;">
                                    <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                        style="position: relative; top:7px; float:left;" />&nbsp;可选标的物列表
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="#" name="WtItemsForm" id="WtItemsForm">
                    <table class="dddl-contentBorder dddl_table"
                        style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                        <tr style="height:28px;">
                        	<td style="width:78px;text-align:right;">不动产权证号</td>
                            <td style="width:210px;"><input class="eve-input validate[minSize[4]"
                                type="text" maxlength="100" style="width:200px;"
                                name="bdcqzh" /></td>
                            <td style="width:78px;text-align:right;">不动产单元号</td>
                            <td style="width:220px;"><input class="eve-input validate[custom[estateNum]"
                                type="text" maxlength="28" style="width:210px;"
                                name="bdcdyh" /></td>
                            <td style="width:58px;text-align:right;">房屋编码</td>
                            <td style="width:155px;"><input class="eve-input"
                                type="text" maxlength="100" style="width:150px;"
                                name="fwbm" /></td>                            
                            <td ><input type="button" value="查询"
                                class="eve-button"
                                onclick="bdcQuery();" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('WtItemsForm')" /></td>
                        </tr>
    
                    </table>
                </form>
            </div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="30"
                id="WtitemsGrid" fitColumns="true" toolbar="#WtItemsToolbar"
                method="post" idField="OB_ID" checkOnSelect="true" emptyMsg="无数据"
                selectOnCheck="true" fit="true" border="false" nowrap="false"
                url="bdcApplyController.do?bdDatagrid">
                <thead>
                    <tr>
                    	<th field="ck" checkbox="true"></th>
                        <th data-options="field:'OB_ID',hidden:true">OB_ID</th>
                        <th data-options="field:'QLRMC',align:'center'" width="50">权力人名称</th>
                        <th data-options="field:'BDCDYH',align:'center'" width="100">不动产单元号</th>
                        <th data-options="field:'BDCQZH',align:'center'" width="100">不动产权证号</th>
                        <th data-options="field:'ZL',align:'left'" width="200">坐落</th>
                    </tr>
                </thead>
            </table>
    
        </div>
        <div data-options="region:'south',split:true,border:false"  >
            <div class="eve_buttons">
                <input value="确定" type="button" onclick="doSelectRows();"
                    class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
                 <input
                    value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                    onclick="AppUtil.closeLayer();" />
            </div>
        </div>
    </div>

    
</body>


