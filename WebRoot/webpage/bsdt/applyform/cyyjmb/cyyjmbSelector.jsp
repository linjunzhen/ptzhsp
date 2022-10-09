<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

function doSelectRows(){
    var rows = $("#CyyjmbGrid").datagrid("getChecked");
    if(rows.length==0){
       alert("请至少选择一条记录!");
    }else if(rows.length>1){
       alert("最多只能选择一条记录!");
    }else{
        var opnionId = "";
        var opnionContent = "";
       /*  for(var i = 0;i<rows.length;i++){
        	 if(i==0){
        		opnionContent=(i+1)+"、"
        	}
            if(i>0){
            	opnionId+=",";
            	opnionContent+="\r\n"+(i+1)+"、";
            } 
	        opnionId+=rows[i].OPINION_ID;
	        opnionContent+=rows[i].OPINION_CONTENT;
        } */
        opnionId=rows[0].OPINION_ID;
        opnionContent=rows[0].OPINION_CONTENT;
        art.dialog.data("selectCyyjmbInfo", {
        	opnionId:opnionId,
        	opnionContent:opnionContent
        });
        AppUtil.closeLayer();
    }
    
}

//显示新增注销附记对话框
function showZXFJRecord() {
	$.dialog.open("bdcDyqscdjController.do?cyjjmbInfo&businessName=${businessName}", {
		title : "常用意见",
		width : "650px",
		height : "250px",
		lock : true,
		resize : false,
		close: function(){
			$("#CyyjmbGrid").datagrid("reload");
		}
	}, false);
};


/**
 * 删除
 */
function deleteCyyjmbDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"bdcDyqscdjController.do?cyyjmbMmultiDel", "CyyjmbGrid");
};
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <div class="easyui-layout" fit="true" >
        <div data-options="region:'center',split:false" style="width: 425px;">
            <div id="CyyjmbToolbar">
                <!--====================开始编写隐藏域============== -->  
                <!--====================结束编写隐藏域============== -->
                <div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
						<div class="e-toolbar-ct">
							<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" 
									iconcls="eicon-plus" plain="true"
									onclick="showZXFJRecord();">新增</a> 
									<a href="#" class="easyui-linkbutton"
									 iconCls="eicon-trash-o" plain="true"
									onclick="deleteCyyjmbDataGridRecord();">删除</a>
							</div>
						</div>
					</div>
			   </div>
			</div>
            <!-- =========================查询面板结束========================= -->
    
            <!-- =========================表格开始==========================-->
            <table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
                id="CyyjmbGrid" fitColumns="true" toolbar="#CyyjmbToolbar"
                method="post" idField="OPINION_ID" checkOnSelect="false"
                selectOnCheck="false" fit="true" border="false" nowrap="false"
                url="bdcDyqzxdjController.do?ZXFJdatagrid&Q_T.BUSINESS_NAME_EQ=${businessName}">
                <thead>
                    <tr>
						<th field="ck" checkbox="true"></th>
                        <th data-options="field:'OPINION_ID',hidden:true">OPINION_ID</th>            
                        <th data-options="field:'OPINION_CONTENT',align:'left'" width="93%">常用意见</th>
                    </tr>
                </thead>
            </table>
        </div>
        
        <div data-options="region:'south',split:true,border:false"  >
            <div class="eve_buttons">
                <input value="确定" type="button" onclick="doSelectRows();"
                    class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
                 <input
                    value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                    onclick="AppUtil.closeLayer();" />
            </div>
        </div>
    </div>

    
</body>


