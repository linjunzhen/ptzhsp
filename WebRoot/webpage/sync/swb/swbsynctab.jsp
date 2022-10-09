<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
</script>
    <div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
       
        <div class="easyui-tabs eve-tabs eui-evetabplus" id="tabRegion" fit="true" style="width: 100%;height: 100%;">
            <div title="办件信息同步" id="undo" style="width: 100%;height: 100%;" href="swbDataController.do?goBjxxSync">
            </div>
            <div title="过程信息同步" id="done" style="width: 100%;height: 100%;" href="swbDataController.do?goGcxxSync">
            </div>
            <div title="结果信息同步" id="undo" style="width: 100%;height: 100%;" href="swbDataController.do?goJgxxSync">
            </div>
        </div>
    </div>