<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript"
        src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
    function rowformater(value,row,index){
        if(value=='Y'){
            return "<font style='color:blue;'>启用</font>";
        }else if(value=='N'){
            return "<font style='color:red;'>禁用</font>";
        }
    }
    /*
	* 查看事项
	* */
    function checkServiceItem() {
        var rows = $("#departStdCatalogGrid").datagrid("getChecked");
        var entityId = rows[0].CATALOG_ID;
        if (!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)) {
            art.dialog({
                content: "请选择需要被操作的记录!",
                icon:"warning",
                ok: true
            });
            return null;
        }else if (rows.length > 1) {
            art.dialog({
                content: "只能选择一条记录进行操作!",
                icon: "warning",
                ok: true
            });
            return null;
        } else {
            $.dialog.open("departCatalogController.do?checkStdServiceItemView&entityId=" + entityId, {
                title : "查看目录事项",
                width : "700px",
                height : "300px",
                lock : true,
                resize : false
            }, false);
        }
    }
</script>

<div class="easyui-layout" fit="true">
    <div data-options="region:'center',split:false">
        <div id="departStdCatalogToolbar">
            <!--====================开始编写隐藏域============== -->

            <!--====================开始编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton"
                               iconcls="eicon-list" plain="true"
                               onclick="checkServiceItem();">查看事项</a>
                        </div>
                    </div>
                </div>
            </div>

            <form action="#" name="catalogForm">
                <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">目录名称</td>
                        <td style="width:135px;"><input class="eve-input" type="text"
                                                        maxlength="20" style="width:128px;" name="Q_T.CATALOG_NAME_LIKE" /></td>
                        <td colspan="4"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('departStdCatalogToolbar','departStdCatalogGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('catalogForm')" /></td>
                    </tr>
                </table>
            </form>
        </div>

        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="departStdCatalogGrid" fitColumns="true"
               toolbar="#departStdCatalogToolbar" method="post" idField="CATALOG_ID"
               checkOnSelect="true" selectOnCheck="true" fit="true" border="false" nowrap="false"
               data-options="onLoadSuccess:function(){
                      $(this).datagrid('enableDnd');
            }"
               url="departCatalogController.do?stdDatagrid">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>

                <th data-options="field:'CATALOG_ID',hidden:true">CATALOG_ID</th>
                <th data-options="field:'CATALOG_NAME',align:'left'" width="80%">目录名称</th>
                <th data-options="field:'CATALOG_STATE',align:'left',formatter:rowformater" width="20%">状态</th>
            </tr>
            </thead>
        </table>
    </div>
</div>