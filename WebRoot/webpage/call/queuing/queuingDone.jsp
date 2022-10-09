<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	window.top["reload_Abnormal_Monitor"]=function(){
		$("#QueuingDoneGrid").datagrid("reload");
	};
	function formateStatus(value,row,index){
		if(value=='1'){
			if(row.EVALUATE!=null&&row.EVALUATE!="undefined"){
				return '已评价';
			}else{
				return '已受理';
			}
		}else if(value=='2'){
			return '过号';
		}else if(value=='3'){
			return '处理完毕（领照）';
		}else if(value=='4'){
			return '处理完毕（咨询）';
		}else if(value=='5'){
			return '处理完毕（其他）';
		}else if(value=='6'){
			return '叫号中……';
		}
	}
	function getCurrentDate(){
		var date = new Date();
	    var seperator1 = "-";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	}
	function rowformater(value,row,index){
		var recordId = row.RECORD_ID;
		var isContinue = '${isContinue}';
		var date = row.CREATE_TIME.substr(0,10);
		var currentdate = getCurrentDate();
		if(row.EVALUATE!=null&&row.EVALUATE!="undefined"&&row.TAKENOWAY!=3){
			return "";
		}else if(row.CALL_STATUS==1&&date==currentdate&&isContinue==1&&row.TAKENOWAY==3){
			return "<a href=\"javascript:void(0);\" onclick=\"evaluateInfo('"+recordId+"')\">发起评价</a>"
			+"&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onclick='acceptDone("+index+")'>继续受理</a>"
			+"&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"checkInfo('"+recordId+"')\">查看取号信息</a>";
		}else if((row.CALL_STATUS==1||row.CALL_STATUS==3||row.CALL_STATUS==4||row.CALL_STATUS==5)&&row.TAKENOWAY==3){
			return "<a href=\"javascript:void(0);\" onclick=\"evaluateInfo('"+recordId+"')\">发起评价</a>"
			+"&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"checkInfo('"+recordId+"')\">查看取号信息</a>";
		}else if(row.CALL_STATUS==1&&date==currentdate&&isContinue==1){
			return "<a href=\"javascript:void(0);\" onclick=\"evaluateInfo('"+recordId+"')\">发起评价</a>"
			+"&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onclick='acceptDone("+index+")'>继续受理</a>";
		}else if(row.CALL_STATUS==1||row.CALL_STATUS==3||row.CALL_STATUS==4||row.CALL_STATUS==5){
			return "<a href=\"javascript:void(0);\" onclick=\"evaluateInfo('"+recordId+"')\">发起评价</a>";
		}else if(row.TAKENOWAY==3){
			return "<a href=\"javascript:void(0);\" onclick=\"checkInfo('"+recordId+"')\">查看取号信息</a>";
		}else{
			return "";
		}
	}
	  function checkInfo(recordId){
			$.dialog.open("callController.do?noInfo&selectTaskIds="+recordId, {
				title : "取号信息",
				width : "720px",
				height : "320px",
				lock : true,
				resize : false
			}, false);
	  }
	//受理
	function acceptDone(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);
		var row = $('#QueuingDoneGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("serviceItemController/selector.do", {
				title : "事项选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						var defKey = selectItemInfo.defKeys;
						var itemCode = selectItemInfo.itemCodes;
						art.dialog.removeData("selectItemInfo");
						var url=encodeURI("executionController.do?goStart&defKey="
                            + defKey + "&itemCode=" + itemCode
                            + "&lineName=" + row.LINE_NAME + "&lineCard="
                            + row.LINE_CARDNO + "&lineId=" + row.RECORD_ID
                            + "&zjlx="+row.LINE_ZJLX,
                            "_blank");
						window.open(url);
					}
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}
	
	function evaluateInfo(recordId){
		$.dialog.open("callController.do?evaluateInfo&recordId=" + recordId, {
			title : "？评价",
			width : "250px",
			height : "130px",
			lock : true,
			resize : false
		}, false);
	}
	  
  	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#QueuingDoneGrid');  
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber*options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '12px, Microsoft YaHei');
        var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
        if(width>25){
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
			grid.datagrid("resize");
		}else{			
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#QueuingDoneGrid').datagrid({
		onLoadSuccess: fixRownumber
	});
	/*序号列宽度自适应-----结束-----*/
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="QueuingDoneToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
			</div>			
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="15" pageList="[15,30,50]"
			id="QueuingDoneGrid" nowrap="false"
			toolbar="#QueuingDoneToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="true" border="false"
			url="callController.do?queuingDoneGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'LINE_NO',align:'left'" width="80">排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="100">经办人姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true" width="150" >证件类型</th>
					<th data-options="field:'ZJLX',align:'left'" width="150" >证件类型</th>
					<th data-options="field:'LINE_CARDNO',align:'left'" width="170" >证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="150" >取号时间</th>
					<th data-options="field:'OPERATOR',align:'left'" width="100" >办理人</th>
					<th data-options="field:'OPER_TIME',align:'left'" width="150" >办理时间</th>
					<th data-options="field:'CALL_STATUS',align:'left',formatter:formateStatus" width="100" >办理状态</th>
					<th data-options="field:'id',formatter:rowformater" width="140">操作</th>
					<th data-options="field:'EVALUATETIME',align:'left'" width="150" >评价时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>