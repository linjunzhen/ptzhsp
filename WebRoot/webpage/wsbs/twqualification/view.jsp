<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
<script language="javascript" src="webpage/wsbs/twqualification/js/qrcode.js"></script>
<script type="text/javascript">
	
	 /**
	 * 查看详细信息
	 */
	function showTwQualificationWindow() {
		var entityId = AppUtil.getEditDataGridRecord("TwQualifyInfoGrid");
		if (entityId) {
			$.dialog.open("twQualificationController.do?detail&entityId=" + entityId, {
			title : "台湾地区职业资格采信证书信息",
			width : "900px",
			height : "450px",
			lock : true,
			resize : false
		}, false);
		}
	}
	
    /**
     * 新增台湾地区职业资格采信证书信息对话框
     */
    function addTwQualificationWindow(entityId){
    		$.dialog.open("twQualificationController.do?info&entityId=" + entityId, {
			title : "台湾地区职业资格采信证书信息",
			width : "900px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
    }
    
    /**
	 * 编辑台湾地区职业资格采信证书列表记录
	 */
	function editTwQualificationWindow() {
		var entityId = AppUtil.getEditDataGridRecord("TwQualifyInfoGrid");
		if (entityId) {
			addTwQualificationWindow(entityId);
		}
	}
	
	 /**
	 * 查看二维码
	 */
	function showQRcode(){
		var entityId = AppUtil.getEditDataGridRecord("TwQualifyInfoGrid");
		if(entityId){
		   $.dialog.open("twQualificationController.do?showQRcode&entityId="+entityId, {
				title : "查看二维码",
				width : "300px",
				height : "180px",
				lock : true,
				resize : false
			}, false);
		} 
	}
	
	 /**
	 * 打印二维码
	 */
	function printQRcode(){
		var rowsData = $("#TwQualifyInfoGrid").datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条记录进行操作!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			LODOP.SET_PRINT_PAGESIZE(1,'208mm','145mm','');
			LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='<%=basePath%>/webpage/wsbs/twqualification/zsTemplate.png'>");
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", 1);
	 		LODOP.SET_SHOW_MODE("BKIMG_WIDTH", '208mm');
	 		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", '145mm');
	 		//打印二维码
	 		var id = rowsData[0].QUALIFICATION_ID;
	 		var qrtext = "http://xzfwzx.pingtan.gov.cn:8888/twQualificationController/QRcodeSearch.do?id="+id;
	 		var qrcode = new QRCode(document.getElementById("qrcode"), {width : 110,height : 110,text: qrtext,correctLevel:QRCode.CorrectLevel.L});
			var canvas=$("#qrcode").find('canvas').get(0);
			$("#qrcode").find('canvas').remove();
			var data = canvas.toDataURL('image/jpg');
			LODOP.ADD_PRINT_IMAGE('15mm','65mm','30mm','30mm',data);
			
			LODOP.PREVIEW();//预览
		}
		
	}

	/**
	 * 删除台湾地区职业资格采信证书列表记录
	 */
	function deleteTwQualificationWindow() {
		AppUtil.deleteDataGridRecord("twQualificationController.do?multiDel",
				"TwQualifyInfoGrid");
	};
	 
	/**
	 * excel批量导入数据
	 */
	function showExcelImportTwQualify(){
		$.dialog.open("twQualificationController.do?excelImportView", {
			title : "导入excel",
			width : "350px",
			height : "50px",
			lock : true,
			resize : false
		}, false);
	}
	
	/**
	 * excel导出数据
	 */
	function showExcelExportTwQualify(){
       AppUtil.showExcelExportWin({
           dataGridId:"TwQualifyInfoGrid",
           tplKey:"TwQualifyInfoReportTpl",
           excelName:"台湾地区职业资格采信证书信息",
           queryParams:{
              "T.TB_ZJHM":$("input[name='Q_T.TB_ZJHM_LIKE']").val()
           }
       });
	}

	$(document).ready(function() {

		AppUtil.initAuthorityRes("TwQualifyInfoToolbar");

	});
	function formatSex(val,row){
        if(val=="1"){
            return "<font ><b>男</b></font>";
        }else{
            return "<font ><b>女</b></font>";
        }
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="TwQualifyInfoToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <a href="#" class="easyui-linkbutton" 
								iconcls="eicon-file-text-o" plain="true"
								onclick="showTwQualificationWindow();">详细信息</a>
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-plus" plain="true"
								onclick="addTwQualificationWindow();">新增</a> <a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-pencil" plain="true"
								onclick="editTwQualificationWindow();">编辑</a> <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-trash-o" plain="true"
                                onclick="deleteTwQualificationWindow();">删除</a><a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-file-excel-o" plain="true"
                                onclick="showExcelImportTwQualify();">excel导入数据</a><a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-file-excel-o" plain="true"
                                onclick="showExcelExportTwQualify();">excel导出数据</a>
                                <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-qrcode" plain="true"
                                onclick="showQRcode();">查看二维码</a>
                                <a href="#"
                                class="easyui-linkbutton" 
                                iconcls="eicon-print" plain="true"
                                onclick="printQRcode();">打印二维码</a>    
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TwQualificationForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
						    <td style="width:68px;text-align:right;">台胞证号</td>
                            <td style="width:168px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:165px;"
                                name="Q_T.TB_ZJHM_LIKE" />
                            </td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('TwQualifyInfoToolbar','TwQualifyInfoGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('TwQualificationForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="TwQualifyInfoGrid" fitcolumns="false" toolbar="#TwQualifyInfoToolbar"
			method="post" idfield="QUALIFICATION_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false"
			url="twQualificationController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'QUALIFICATION_ID',hidden:true">QUALIFICATION_ID</th>
					<th data-options="field:'USERNAME',align:'left'" width="10%">姓名</th>
					<th data-options="field:'SEX',align:'left'" width="10%" formatter="formatSex">性别</th>
					<th data-options="field:'TB_ZJHM',align:'left'" width="15%">台胞证号</th>
					<th data-options="field:'ZS_NUMBER',align:'left'" width="15%">证书编号</th>
					<th data-options="field:'ZS_VALIDITY',align:'left'" width="46%">证书有效期</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
<div id="qrcode" style="display: none;"></div>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>