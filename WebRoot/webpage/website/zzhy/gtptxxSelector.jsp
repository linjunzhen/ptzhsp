<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <style>
        .layout-split-south{border-top-width:0px !important;}
        .eve_buttons{position: relative !important;}
    </style>
    <script type="text/javascript">

        //确定按钮
        function doSelectRows(){
            var rows = $("#gtptxxglSelectorGrid").datagrid("getChecked");
            var allowCount = $("input[name='allowCount']").val();
            if((rows.length>allowCount)&&allowCount!=0){
                alert("最多只能选择"+allowCount+"条记录!");
                return;
            }
            if(rows.length>0){               
				art.dialog.data("gtptxxInfo",rows[0]);
            }
            AppUtil.closeLayer();

        }

        //去除字符串的空串
        function Trim(str)
        {
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }
        /*序号列宽度自适应-----开始-----*/
        function fixRownumber(){
            var grid = $('#gtptxxglSelectorGrid');
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
        $('#gtptxxglSelectorGrid').datagrid({
            onLoadSuccess: fixRownumber
        });
		//查询
        function gtptxxglSearch(){
            $("#gtptxxglSelectorGrid").datagrid('clearChecked');
			$("#gtptxxglSelectorGrid").datagrid('clearSelections').datagrid('clearChecked');
            AppUtil.gridDoSearch('gtptxxglSelectorToolbar','gtptxxglSelectorGrid')
           
        }
    </script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<input type="hidden" name="allowCount" value="${allowCount}">
<div class="easyui-layout" fit="true" >
    <div data-options="region:'center',split:false" >
        <div id="gtptxxglSelectorToolbar">
            <form action="#" name="gtptxxglSelectorForm">
                <!--====================开始编写隐藏域============== -->

                <!--====================结束编写隐藏域============== -->
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:100px;text-align:right;">平台名称：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PT_NAME_LIKE"/>
						</td> 
                        <td colspan="4"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="gtptxxglSearch()" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('gtptxxglSelectorForm');" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->

        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="gtptxxglSelectorGrid" fitColumns="true" toolbar="#gtptxxglSelectorToolbar"
               method="post" idField="PT_ID" checkOnSelect="false"
               selectOnCheck="false" fit="true" border="false" nowrap="false"
               url="gtptxxglController.do?datagridWw">
            <thead>
            <tr>
				<th field="ck" checkbox="true"></th>
				<th data-options="field:'PT_ID',hidden:true">PT_ID</th>	
				<th data-options="field:'PT_NAME',align:'left'" width="96%">平台名称</th>	
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
</html>

