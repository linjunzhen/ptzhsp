<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--<%@include file="/webpage/common/baseinclude.jsp"%>--%>

<%--<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>--%>

<script>

    function downLoadSealMater() {

    }

    function sealMaterDetail() {
        /* var COMPANY_ID = AppUtil.getEditDataGridRecord("sealMaterViewGrid");
        if (COMPANY_ID != null && COMPANY_ID.length > 0) {
            $.dialog.open("commercialSetController.do?sealMaterDetailView&COMPANY_ID=" + COMPANY_ID , {
                title : "材料详细信息",
                width : "600px",
                height : "400px",
                lock : true,
                resize : false
            },false)
        } */
                
		var rowsData = $("#sealMaterViewGrid").datagrid("getChecked");
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
			var COMPANY_ID = rowsData[0].COMPANY_ID;
			var BUS_TABLENAME = rowsData[0].BUS_TABLENAME;
            $.dialog.open("commercialSetController.do?sealMaterDetailView&COMPANY_ID=" + COMPANY_ID +"&BUS_TABLENAME="+BUS_TABLENAME, {
                title : "材料详细信息",
                width : "600px",
                height : "400px",
                lock : true,
                resize : false
            },false)
		}
    }

    function reviseStatus() {
        //var COMPANY_ID = AppUtil.getEditDataGridRecord("sealMaterViewGrid");        
		var rowsData = $("#sealMaterViewGrid").datagrid("getChecked");
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
			var COMPANY_ID = rowsData[0].COMPANY_ID;
			var BUS_TABLENAME = rowsData[0].BUS_TABLENAME;
            $.dialog.open("commercialSetController.do?sealMaterReviseView&COMPANY_ID=" + COMPANY_ID +"&BUS_TABLENAME="+BUS_TABLENAME, {
                title : "修改邮寄信息",
                width : "600px",
                height : "200px",
                lock : true,
                resize : false
            },false)
		}
    }
    
	function formatIsEmail(val,row){
		if(val=="1"){
			return "是";
		}else if(val=="0"){
			return "否";
		}else{
			return "否";
		}
	}

</script>


<div class="easyui-layout eui-jh-box" fit="true">
    <div region="center">
        <!-- =========================查询面板开始========================= -->
        <div id="sealMaterViewBar">
            <!--====================开始编写隐藏域============== -->
            <!--====================结束编写隐藏域============== -->
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
<%--                            <a href="#" class="easyui-linkbutton" reskey=""--%>
<%--                               iconcls="icon-note-add" plain="true"--%>
<%--                               onclick="downLoadSealMater();">材料下载</a>--%>
                            <a href="#" class="easyui-linkbutton" reskey=""
                               iconcls="eicon-download" plain="true"
                               onclick="sealMaterDetail();">材料下载</a>
                            <a href="#" class="easyui-linkbutton" reskey=""
                               iconcls="eicon-pencil" plain="true"
                               onclick="reviseStatus();">修改</a>
                        </div>
                    </div>
                </div>
            </div>
            <form action="#" name="sealMaterViewForm">
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                    <tr style="height:28px;">
                        <td style="width:68px;text-align:right;">申报号</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_b.EXE_ID_LIKE" /></td>
                        <td style="width:68px;text-align:right;">办件名称</td>
                        <td style="width:135px;"><input class="eve-input"
                                                        type="text" maxlength="20" style="width:128px;"
                                                        name="Q_B.SUBJECT_LIKE" /></td>
                        <td colspan="2"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="AppUtil.gridDoSearch('sealMaterViewBar','sealMaterViewGrid')" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('sealMaterViewForm')" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->
        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
               id="sealMaterViewGrid" fitcolumns="true" nowrap="false"
               toolbar="#sealMaterViewBar" method="post" idfield="COMPANY_ID"
               checkonselect="true" selectoncheck="true" fit="true" border="false"
               url="commercialSetController.do?sealMaterData">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
				<th data-options="field:'BUS_TABLENAME',hidden:true">BUS_TABLENAME</th>
                <th data-options="field:'EXE_ID',align:'left'" width="12%">申报号</th>
                <th data-options="field:'COMPANY_NAME',align:'left'" width="25%">公司名称</th>
                <th data-options="field:'IS_SEALED',align:'left'" width="10%">是否已刻制</th>
                <th data-options="field:'ISEMAIL',align:'left'" width="10%" formatter="formatIsEmail">是否邮寄</th>
                <th data-options="field:'EMS_NUMBER',align:'left'" width="45%">EMS单号</th>
<%--                <th data-options="field:'CREATE_TIME',align:'left'" width="15%">申请时间</th>--%>
<%--                <th data-options="field:'BANK_LICENSE',align:'left'" width="15%">开户许可证号</th>--%>
            </tr>
            </thead>
        </table>
        <!-- =========================表格结束==========================-->
    </div>
</div>