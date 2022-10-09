<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function reloadTabGrid(title,type)
	{
      	if ($("#tabRegion" ).tabs('exists', title)) {
      		if(window.top.reload_Abnormal_Monitor!=null&&window.top.reload_Abnormal_Monitor!="undefined"){
      			window.top.reload_Abnormal_Monitor.call();
      		}
      		if(type=='accept'){
      			$("#tabRegion" ).tabs('select', "已受理记录");
      		}
      	}
	}
</script>
<!-- 结束编写头部工具栏 --><%-- 
	<div class="call_buttons">
		<span style="font-size: 16px;">当前为</span><span style="font-size: 18px;color: blue;"> ${winNo }</span>	<span style="font-size: 16px;">号办事窗口&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<input type="button" value="叫号" class="z-dlg-button z-dialog-okbutton aui_state_highlight">&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="过号" class="z-dlg-button z-dialog-okbutton aui_state_highlight">&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="请稍等" class="z-dlg-button z-dialog-okbutton aui_state_highlight">&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="服务中" class="z-dlg-button z-dialog-okbutton aui_state_highlight">&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="其他处理" class="z-dlg-button z-dialog-okbutton aui_state_highlight">
	</div> --%>
    <div data-options="region:'center'" fit="true" style="width: 100%;height: 100%;">
       
        <div class="easyui-tabs eve-tabs" id="tabRegion" fit="true" style="width: 100%;height: 100%;">
            <div title="待处理事项" id="undo" style="width: 100%;height: 100%;" href="callController.do?goQueuingUndo">
            </div>
            <div title="已受理记录" id="done" style="width: 100%;height: 100%;" href="callController.do?goQueuingDone">
            </div>
        </div>
    </div>