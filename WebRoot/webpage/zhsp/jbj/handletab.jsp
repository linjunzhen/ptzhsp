<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
   
</script>
<!-- 结束编写头部工具栏 -->

    <div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
       
        <div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" style="width: 100%;height: 100%;">
            <div title="待我审批" style="width: 100%;height: 100%;" href="serviceHandleController.do?goNeedHandle&handleType=1">
            </div>
            <div title="经我审批" style="width: 100%;height: 100%;" href="serviceHandleController.do?goHandleByMe&handleType=1">
            </div>
            <div title="数据查询" style="width: 100%;height: 100%;" href="serviceHandleController.do?goHandleMonitor&handleType=1">
            </div>
        </div>
    </div>