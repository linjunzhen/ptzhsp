<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
    .gridtable {
        width: 100%;
        height: 100%
    }

    .gridtable .datagrid-htable {
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc
    }

    .gridtable .datagrid-btable {
        border-left: 1px solid #ccc;
        border-bottom: 1px solid #ccc
    }

    .gridtable .datagrid-header-row td {
        border-width: 0;
        border-left: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }

    .gridtable .datagrid-header-row td:last-child {
        border-left: 1px solid #ccc;
        border-right: 1px solid #ccc;
    }

    .gridtable .datagrid-body td {
        border-width: 0;
        border-right: 1px solid #ccc;
        border-top: 1px solid #ccc;
    }
</style>

<script>
    function toWwysrk() {
        window.open("https://www.xiaopiu.com/web/byId?type=project&id=5f435c1c1ebbb638f7ea00fe","_blank");
    }

    function toBdcdjzxjg() {
        window.open("<%=path%>/bdcApplyController.do?bdcdjsjjgImgView","_blank");
    }
    function toBdcsxtjb() {
        window.open("<%=path%>/bdcApplyController.do?bdcsxtjbView","_blank");
    }
    function toBdcjbrytjb() {
        window.open("<%=path%>/bdcApplyController.do?bdcjbrytjbView","_blank");
    }
    function toBdcbjmxb() {
        window.open("<%=path%>/bdcApplyController.do?bdcbjmxbView","_blank");
    }
</script>


<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="bjmxbStatisToolBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="toWwysrk();">不动产登记数据监管</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="toBdcdjzxjg();">不动产登记在线监管</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="toBdcsxtjb();">不动产事项统计表</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="toBdcjbrytjb();">不动产经办人员统计表</a>
                            <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="toBdcbjmxb();">不动产办件明细表</a>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

