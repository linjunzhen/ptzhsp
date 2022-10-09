<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**
 * 是否满意
 */
function formatSFMY(val,row){
	 if(val=="1"){
         return "<font ><b>满意</b></font>";
     }else if(val=="0"){
         return "<font ><b>不满意</b></font>";
     }
}

$(document).ready(function(){
	    var start1 = {
	    elem: "#BSPJ.CREATE_TIME_BEGIN",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_T.CREATE_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#BSPJ.CREATE_TIME_END",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_T.CREATE_TIME_<=']").val("");
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
  <div id="BSPJToolbar"> 
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <form action="#" name="BSPJForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
       <td style="width:68px;text-align:right;">申报号</td>
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.EXE_ID_LIKE" /></td> 
       <td style="width:68px;text-align:right;">事项名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_S.ITEM_NAME_LIKE" /></td> 
       
       <td style="width:68px;text-align:right;">起始日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="BSPJ.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td> 
       <td style="width:68px;text-align:right;">截止日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="BSPJ.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td> 
       
      </tr>
      <tr style="height:28px;">
      	<td style="width:68px;text-align:right;">是否满意</td> 
       <td style="width:68px;text-align:left;padding-left:4px;">
       <select style="width:128px;" class="easyui-combobox" name="Q_T.SFMY_LIKE">
									<option value="">请选择</option>
									<option value="1">满意</option>
									<option value="0">不满意</option>
								</select>
	  </td>
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('BSPJToolbar','pjmxGrid')" /> 
       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('BSPJForm')" /></td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true"  striped="true"
  pagination="true" id="pjmxGrid" fitcolumns="false" toolbar="#BSPJToolbar"  nowrap="false"
  method="post" idfield="PJ_ID"  checkonselect="false" selectoncheck="false" fit="true" border="false"
    url="bspjController.do?datagrid"> 
   <thead> 
        <tr>
			<th field="ck" checkbox="true"></th>
			<th data-options="field:'PJ_ID',hidden:true">PJ_ID</th>
			<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
			<th data-options="field:'ITEM_NAME',align:'left'" width="30%">事项名称</th>
			<th data-options="field:'YHZH',align:'left'" width="10%">用户账号</th>
			<th data-options="field:'PJNR',align:'left'" width="20%">评价内容</th>
			<th data-options="field:'SFMY',align:'left'" width="10%"  formatter="formatSFMY">是否满意</th>
			<th data-options="field:'CREATE_TIME',align:'left'" width="13%">创建时间</th>
		</tr>
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>