<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
   
</script>
<!-- 结束编写头部工具栏 -->

    <div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
       
        <div class="easyui-tabs eve-tabs eui-evetabplus" fit="true" style="width: 100%;height: 100%;">
            <div title="目录管理" style="width: 100%;height: 100%;" href="catalogController.do?view">
            </div>
            <div title="草稿库" style="width: 100%;height: 100%;" href="serviceItemController.do?view">
            </div>
            <div title="退回库" style="width: 100%;height: 100%;" href="serviceItemController.do?back">
            </div>
            <div title="审核库" style="width: 100%;height: 100%;" href="serviceItemController.do?auditing">
            </div>
            <div title="发布库" style="width: 100%;height: 100%;" href="serviceItemController.do?publish">
            </div>
            <div title="取消库" style="width: 100%;height: 100%;" href="serviceItemController.do?cancel">
            </div>
        </div>
    </div>