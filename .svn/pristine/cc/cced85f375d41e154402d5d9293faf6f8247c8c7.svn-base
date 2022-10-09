<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">


$(document).ready(function(){
	    var start1 = {
	    elem: "#SPE.BEGIN_TIME_BEGIN",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_T.BEGIN_TIME_>=']").val();
	    	var endTime = $("input[name='Q_T.BEGIN_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_T.BEGIN_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#SPE.BEGIN_TIME_END",
	    format: "YYYY-MM-DD",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_T.BEGIN_TIME_>=']").val();
	    	var endTime = $("input[name='Q_T.BEGIN_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_T.BEGIN_TIME_<=']").val("");
	    		}
	    	}
	    }
	};
	laydate(start1);
	laydate(end1);

});

function formatSubject(val,row){
	//获取流程申报号
	var exeId = row.EXE_ID;
	//获取流程任务状态
	var taskStatus = row.TASK_STATUS;
	var href = "<a href='";
	href += "executionController.do?goDetail&exeId="+exeId+"'  target='_blank'";
	href += "><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
    return href;
}
function formatTIME(val,row){
	var linkName=row.LINK_NAME;
	if(linkName=='补件'){
		return "90自然日";
	}else{
		return val+"工作日";
	}
}
//是否超期
function OVERTIEM(val,row){
	if(val==-3){
		return "<span style='color:#8c97cb'>未明确</span>";
	}else if(val==-2){
		return "<span style='color:#ff4b4b'>超期</span>";
	}else if(val==0){
		if(typeof(row.END_TIME)=='undefined'){
			   return "<span style='color:#fa5800'>今天截止</span>";
			}else{
				return "<span style='color:#0368ff'>未超期</span>";
			}
	}else{
		if(typeof(row.END_TIME)=='undefined'){
		   return "<span style='color:#0368ff'>"+val+"工作日</span>";
		}else{
			return "<span style='color:#0368ff'>未超期</span>";
		}
	}
}
//是否重复挂起
function AGAINHANGUP(val,row){
	if(val==0){
		return "<span style='color:#0368ff'>否</span>";
	}else if(val==1){
		return "<span style='color:#0368ff'>是</span>";
	}
}

function exportSpeExcel(){
	var beginDate = $("#SPEForm input[name='Q_H.BEGIN_TIME_>=']").val();
	var endDate = $("#SPEForm input[name='Q_H.BEGIN_TIME_<=']").val();
    var EXE_ID = $("#SPEForm input[name='Q_E.EXE_ID_LIKE']").val();
	var ITEM_NAME = $("#SPEForm input[name='Q_S.ITEM_NAME_LIKE']").val();
	var OVERTIEM = $("#SPEForm input[name='Q_E.OVERTIEM_LIKE']").val();
	var AGAINHANGUP = $("#SPEForm input[name='Q_E.AGAINHANGUP_LIKE']").val();
	var speurl="statisticsController.do?exportSpeExcel&Q_H.BEGIN_TIME_>="+beginDate+"&Q_E.EXP_LIKE=1"
	+"&Q_H.BEGIN_TIME_<="+endDate+"&Q_S.ITEM_NAME_LIKE="+ITEM_NAME+"&Q_E.EXE_ID_LIKE="+EXE_ID
	+"&Q_E.OVERTIEM_LIKE="+OVERTIEM+"&Q_E.AGAINHANGUP_LIKE="+AGAINHANGUP;
	var gotoLink = document.createElement('a'); 
	gotoLink.href = speurl; 
	document.body.appendChild(gotoLink); 
	gotoLink.click(); 
	
}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
 <div region="center"> 
  <!-- =========================查询面板开始========================= --> 
  <div id="SPEToolbar"> 
  <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
					         <a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" 
					         onclick="exportSpeExcel();">导出查询数据</a> 
							 <a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
   <!--====================开始编写隐藏域============== --> 
   <!--====================结束编写隐藏域============== --> 
   <form action="#" name="SPEForm" id="SPEForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;">
       <td style="width:68px;text-align:right;">申报号</td>
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_E.EXE_ID_LIKE" /></td> 
       <td style="width:68px;text-align:right;">事项名称</td> 
       <td style="width:135px;"> <input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_S.ITEM_NAME_LIKE" /></td> 
 
       <td style="width:78px;text-align:right;">挂起开始日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SPE.BEGIN_TIME_BEGIN" name="Q_H.BEGIN_TIME_>=" /></td> 
       <td style="width:78px;text-align:right;">挂起结束日期</td> 
       <td style="width:135px;"> <input type="text" style="width:128px;float:left;" class="laydate-icon" id="SPE.BEGIN_TIME_END" name="Q_H.BEGIN_TIME_<=" /></td> 
      </tr> 
      <tr  style="height:28px;">
       <td style="width:68px;text-align:right;">是否超期</td>
       <td style="width:135px;padding-left:4px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:128px;" name="Q_E.OVERTIEM_LIKE"  data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td> 
        <td style="width:68px;text-align:right;">重复挂起</td>
       <td style="width:135px;padding-left:4px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:128px;" name="Q_E.AGAINHANGUP_LIKE"  data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " /></td> 
       <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('SPEToolbar','speGrid')" /> 
       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('SPEForm')" /></td> 
      </tr>
     </tbody>
    </table> 
   </form> 
  </div> 
  <!-- =========================查询面板结束========================= --> 
  <!-- =========================表格开始==========================--> 
  <table class="easyui-datagrid" rownumbers="true"  striped="true"
  pagination="true" id="speGrid" fitcolumns="false" toolbar="#SPEToolbar"  nowrap="false"
  method="post" idfield="TASK_ID"  checkonselect="false" selectoncheck="false" fit="true" border="false"
    url="flowTaskController.do?speDatagrid"> 
   <thead> 
        <tr>
			<th field="ck" checkbox="true"></th>
			<th data-options="field:'TASK_ID',hidden:true">TASK_ID</th>
			<th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
			<th data-options="field:'DEPART_NAME',align:'left'" width="15%">所属部门</th>
			<th data-options="field:'SUBJECT',align:'left'" width="25%" formatter="formatSubject">流程名称</th>
			<th data-options="field:'LINK_NAME',align:'left'" width="12%">特殊环节名称</th>
			<th data-options="field:'BEGIN_TIME',align:'left'" width="15%">挂起时间</th>
			<th data-options="field:'LINK_LIMITTIME',align:'left'" width="10%"  formatter="formatTIME">挂起时限</th>
			<th data-options="field:'END_TIME',align:'left'" width="15%">重启时间</th>
			<th data-options="field:'OVERTIME',align:'left'" width="10%"  formatter="OVERTIEM">是否超时</th>
			<th data-options="field:'AGAINHANGUP',align:'left'" width="10%" formatter="AGAINHANGUP">是否重复挂起</th>
			<!--  <th data-options="field:'SFMY',align:'left'" width="120"  formatter="formatSFMY">是否超期</th> 
			-->
			<th data-options="field:'EXPLAIN',align:'left'" width="15%">挂起说明</th>
			<th data-options="field:'FILES',align:'left'" width="15%">附件</th>
			<th data-options="field:'FULLNAME',align:'left'" width="10%">挂起操作人</th>
			<th data-options="field:'LINK_MAN',align:'left'" width="15%">特殊环节执行人</th>
			<th data-options="field:'LINK_MAN_TEL',align:'left'" width="15%">执行人电话</th>
		</tr>
   </thead> 
  </table> 
  <!-- =========================表格结束==========================--> 
 </div>
</div>