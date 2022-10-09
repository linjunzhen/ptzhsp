<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,ztree,json2"></eve:resources>
<script type="text/javascript">
     
    //查询
    function sbPaySearch(){
        var aab003 = $("input[name='aab003']").val();
        var type = $("input[name='type']").val();
        if((aab003 == null || aab003 == "" || aab003 == undefined) 
        || (type == null || type == "" || type == undefined)){
        	 parent.art.dialog({
                content: "单位信用代码、险种类型不能为空！",
                icon:"warning",
                ok: true
            });
            return;
        }
         var count = 0;
         //$("#payGrid").datagrid('clearChecked');
         AppUtil.gridDoSearch('payToolbar','payGrid');
         $('#payGrid').datagrid({
             onLoadSuccess:function(){
                 //确保初始化后只执行一次
                 if(count == 0){
                     var rows = $("#payGrid").datagrid("getRows");
                     if(rows.length==0){
                         parent.art.dialog({
                             content: "无匹配数据返回，查询记录为空！",
                             icon:"warning",
                             ok: true
                         });
                         count ++;
                     }
                 }
             }
         });
    }
    
	//是否秒批格式化
	function formatIsMp(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>是</b></font>";
		}else if(val=="0"){
			return "<font color='#ff4b4b'><b>否</b></font>";
		}
	}

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="payToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<!-- <div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SysRole"
								iconcls="eicon-plus" plain="true"
								onclick="showBusScopeWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysRole"
								iconcls="eicon-pencil" plain="true"
								onclick="editpayGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysRole"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletepayGridRecord();">删除</a>
                            <a href="#"
                               class="easyui-linkbutton l-btn l-btn-small l-btn-plain" reskey="IMP_IndustryCatalog"
                               iconcls="eicon-file-excel-o" plain="true"
                               onclick="impIndustryCatalog();">行业类别导入</a>
						</div>
					</div>
				</div>
			</div> -->
			<form action="#" name="payForm" id="payForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位信用代码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aab003" /></td>
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>险种类型：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:135px;" name="type"
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '110', value: '养老'},{label: '410',value: '工伤'},],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="sbPaySearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('payForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="payGrid" fitcolumns="true" toolbar="#payToolbar"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="sbPayController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<!-- <th data-options="field:'ID',hidden:true">主键ID</th> -->
					<th data-options="field:'aac003',align:'left'" width="15%">姓名</th>
					<th data-options="field:'aac002',align:'left'" width="30%">人员身份证</th>
					<th data-options="field:'aae003',align:'left'" width="20%" >对应费款所属期</th>
					<th data-options="field:'aae033',align:'left'" width="20%">费款截止期</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>