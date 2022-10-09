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

    <script>
        //确定按钮
        function doSelectRows(){
            var rows = $("#fwdyxxcxGrid").datagrid("getChecked");
            var allowCount = $("input[name='allowCount']").val();
            if((rows.length>allowCount)&&allowCount!=0){
                alert("最多只能选择"+allowCount+"条记录!");
                return;
            }
            art.dialog.data("fwdyxxcxInfo",rows);
            AppUtil.closeLayer();
        }

        //去除字符串的空串
        function Trim(str)
        {
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }
        /*序号列宽度自适应-----开始-----*/
        function fixRownumber(){
            var grid = $('#fwdyxxcxGrid');
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
        $('#fwdyxxcxGrid').datagrid({
            onLoadSuccess: fixRownumber
        });

        function search(){
            $("#fwdyxxcxGrid").datagrid('clearChecked');
            var count=1;
            AppUtil.gridDoSearch('fwdyxxcxToolbar','fwdyxxcxGrid');
            $('#fwdyxxcxGrid').datagrid({
                onLoadSuccess:function(data){
                    //确保数据初始化后只执行一次该方法
                    if((count=='1')&& data.total==0){
                        art.dialog({
                            content : "无匹配数据返回，查询记录为空。",
                            icon : "warning",
                            ok : true
                        });
                        count ++;
                    }
                }
            });
        }

    </script>

</head>
<body style="margin:0px;background-color: #f7f7f7;">
<input type="hidden" name="allowCount" value="${allowCount}">
<div class="easyui-layout" fit="true" >
    <div data-options="region:'center',split:false" >
        <div id="fwdyxxcxToolbar">
            <form action="#" name="fwdyxxcxForm">
                <!--====================开始编写隐藏域============== -->

                <!--====================结束编写隐藏域============== -->
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:120px;text-align:right;">不动产单元号：</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:128px;"
                                                        name="bdcdyh" /></td>
                        <td style="width:120px;text-align:right;">房屋编码：</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:128px;"
                                                        name="fwbm" /></td>
                        <td style="width:120px;text-align:right;">不动产权证号：</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:128px;"
                                                        name="bdcqzh" /></td>
                        <td colspan="4"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="search()" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('fwdyxxcxForm');" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->

        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="fwdyxxcxGrid" fitColumns="true" toolbar="#fwdyxxcxToolbar"
               method="post" idField="FWMMHTH" checkOnSelect="false"
               selectOnCheck="false" fit="true" border="false" nowrap="false"
               url="bdcApplyController.do?fwdyxxcxDatagrid&noAuth=${noAuth}">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'BDCDYH',align:'left'" width="100">不动产单元号</th>
                <th data-options="field:'ZL',align:'left'" width="150">坐落</th>
                <th data-options="field:'ZT',align:'left'" width="100">状态</th>
                <th data-options="field:'FWJG',align:'left'" width="100">房屋结构</th>
                <th data-options="field:'GHYT',align:'left'" width="100">规划用途</th>
                <th data-options="field:'JZMJ',align:'left'" width="100">建筑面积</th>

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



