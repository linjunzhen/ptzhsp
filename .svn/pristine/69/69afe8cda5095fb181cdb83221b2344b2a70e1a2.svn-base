<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style>
    .datagrid-btable tr{height: 40px;}
    .datagrid-btable td{word-break: break-all;}
</style>

<script type="text/javascript">
    function formateFileName(val,row) {
        var fileId = row.FILE_ID;
        var href = "<a href=\"javascript:void(0)\" ";
        href+=("onclick=\"AppUtil.downLoadFile('"+fileId+"');\" >");
        href+=(val+"</a>");
        return href;
    }
</script>

<div class="easyui-layout" fit="true">
    <div region="center">
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="false" pagination="false"
               id="UploadFilesGrid" fitcolumns="true" nowrap="false"
               method="post" idfield="FILE_ID" checkonselect="false"
               selectoncheck="false" fit="true" border="false"
               url="fileAttachController.do?queryAllUploadFilesData&exeId=${exeId}">
            <thead>
            <tr>
                <th data-options="field:'FILE_ID',align:'left',hidden:true" width="100">材料ID</th>
                <th data-options="field:'FILE_NAME',align:'left'" style="color: #0000ee" formatter="formateFileName" width="100" >材料名称</th>
                <th data-options="field:'ATTACH_KEY',align:'left'" width="100">材料编码</th>
                <th data-options="field:'CREATE_TIME',align:'left'" width="100">上传时间</th>
            </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>