<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
   
</script>
<!-- 结束编写头部工具栏 -->

    <div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
       
        <div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" style="width: 100%;height: 100%;">
            <div title="草稿库" style="width: 100%;height: 100%;" href="billOfRightController.do?billOfRightDraft">
            </div>
            <div title="审核库" style="width: 100%;height: 100%;" href="billOfRightController.do?billOfRightAudit">
            </div>
            <div title="发布库" style="width: 100%;height: 100%;" href="billOfRightController.do?billOfRightPublish">
            </div>
            <div title="取消库" style="width: 100%;height: 100%;" href="billOfRightController.do?billOfRightCancel">
            </div>
            <div title="已承接" style="width: 100%;height: 100%;" href="billOfRightController.do?billOfRightUndertake">
            </div>
            
        </div>
    </div>