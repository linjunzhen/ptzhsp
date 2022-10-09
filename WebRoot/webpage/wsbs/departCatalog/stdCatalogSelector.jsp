<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
    <meta name="renderer" content="webkit">
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <eve:resources
            loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
    <style>
        .layout-split-south {
            border-top-width: 0px !important;
        }

        .eve_buttons {
            position: relative !important;
        }
    </style>
    <script type="text/javascript">
        function doSubmitRows(){
            var rows = $("#stdCatalogGrid").datagrid("getChecked");
            if(rows.length==0){
                art.dialog({
                    content: "请选择一条记录!",
                    icon:"warning",
                    ok: true
                });
                return;
            }else if(rows.length>1){
                art.dialog({
                    content: "只能选择一条被操作的记录!",
                    icon:"warning",
                    ok: true
                });
                return;
            }else{
                var catalogId = "";
                var catalogName = "";
                for(var i = 0;i<rows.length;i++){
                    catalogId+=rows[i].CATALOG_ID;
                    catalogName+=rows[i].CATALOG_NAME;
                }
                art.dialog.data("selectCatalogInfo", {
                    catalogId:catalogId,
                    catalogName:catalogName,
                });
                AppUtil.closeLayer();
            }

        }
    </script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

<div class="easyui-layout eui-dialog" fit="true">
    <div data-options="region:'center',split:false">
        <div id="stdCatalogToolbar">
            <form action="#" name="StdCatalogForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">目录名称</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_T.CATALOG_NAME_LIKE" /></td>
                        <td><input type="button" value="查询"
                                   class="eve-button"
                                   onclick="AppUtil.gridDoSearch('stdCatalogToolbar','stdCatalogGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('StdCatalogForm')" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->

        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="stdCatalogGrid" fitColumns="true" toolbar="#stdCatalogToolbar" method="post"
               idField="CATALOG_ID" checkOnSelect="true" selectOnCheck="true"
               fit="true" border="false" nowrap="false"
               url="departCatalogController.do?stdDatagrid&Q_T.CATALOG_STATE_EQ=Y">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'CATALOG_ID',hidden:true" width="80">CATALOG_ID</th>
                <th data-options="field:'CATALOG_NAME',align:'left'" width="210">目录名称</th>
            </tr>
            </thead>
        </table>

    </div>

    <div data-options="region:'south',split:true,border:false">
        <div class="eve_buttons" style="text-align: right;">
            <input value="确定" type="button" onclick="doSubmitRows();"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</div>


</body>
</html>
