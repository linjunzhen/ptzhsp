<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
 * 发送状态
 */
function formatType(val,row){
	 if(val=="0"){
         return "<font ><b>未发送</b></font>";
     }else if(val=="1"){
         return "<font ><b>发送成功</b></font>";
     }else if(val=="2"){
         return "<font ><b>发送失败</b></font>";
     }else if(val=="3"){
         return "<font ><b>不发送</b></font>";
     }else if(val=="8"){
         return "<font ><b>正在发送处理</b></font>";
     }else if(val=="9"){
    	 return "<font ><b>不发送</b></font>";
     }
}

$(document).ready(function(){
	    var start1 = {
	    elem: "#SysMsg.SEND_TIME_BEGIN",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_M.SEND_TIME_>=']").val();
	    	var endTime = $("input[name='Q_M.SEND_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_M.SEND_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#SysMsg.SEND_TIME_END",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_M.SEND_TIME_>=']").val();
	    	var endTime = $("input[name='Q_M.SEND_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_M.SEND_TIME_<=']").val("");
	    		}
	    	}
	    }
	};
	laydate(start1);
	laydate(end1);

});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="SysMsgToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <form action="#" name="SysMsgForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">短信内容</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_M.CONTENT_LIKE" /></td> 
       <td style="width:68px;text-align:right;">接受人手机号</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_M.RECEIVER_MOB_LIKE" /></td> 
       <td style="width:68px;text-align:right;">发送状态</td> 
       <td style="width:68px;text-align:left;">
       <select style="width:128px;" class="easyui-combobox" name="Q_M.SEND_STATUS_LIKE">
									<option value="">请选择</option>
									<option value="0">未发送</option>
									<option value="1">发送成功</option>
									<option value="2">发送失败</option>
									<option value="3">不发送（多次失败）</option>
									<option value="8">正在发送处理</option>
									<option value="9">不发送</option>
								</select>
	  </td>
      </tr> 
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">发送起始日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SysMsg.SEND_TIME_BEGIN" name="Q_M.SEND_TIME_&gt;=" /></td> 
       <td style="width:68px;text-align:right;">发送截止日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SysMsg.SEND_TIME_END" name="Q_M.SEND_TIME_&lt;=" /></td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('SysMsgToolbar','SysMsgGrid')" /> 
       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('SysMsgForm')" /></td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true" nowrap="false" striped="true"
  pagination="true" id="SysMsgGrid" fitcolumns="false" toolbar="#SysMsgToolbar" 
  method="post" idfield="MSG_ID"  checkonselect="false" selectoncheck="false" fit="true" border="false"
    url="sysMsgController.do?datagrid"> 
   <thead> 
        <tr>
			<th field="ck" checkbox="true"></th>
			<th data-options="field:'MSG_ID',hidden:true">MSG_ID</th>
			<th data-options="field:'RECEIVER_MOB',align:'left'" width="15%">接收人手机号</th>
			<th data-options="field:'CONTENT',align:'left'" width="40%">短信内容</th>
			<th data-options="field:'SEND_TIME',align:'left'" width="15%">发送时间</th>
			<th data-options="field:'CREATE_TIME',align:'left'" width="15%">创建时间</th>
			<th data-options="field:'SEND_STATUS',align:'left'" width="10%" formatter="formatType">发送状态</th>
		</tr>
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>