<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#ShareLicenseGrid").datagrid("getChecked");
    	if(rows.length==0){
    		art.dialog({
				content : "请选择一条记录",
				icon : "warning",
				ok : true
			});
    	}else if(rows.length>1){
    		art.dialog({
				content : "只能选择一条记录",
				icon : "warning",
				ok : true
			});
    	}else{
    		var creditlevel = $("input[name='creditlevel']").val();
    		var PERSON_CREDIT_USE = $("input[name='PERSON_CREDIT_USE']").val();
    		var status = rows[0].STATUS;
    		if(status>=1||((creditlevel.indexOf('A')>=0||creditlevel.indexOf('B')>=0)&&PERSON_CREDIT_USE.indexOf('B')>=0)){
	    		var SERIALNUMBER = rows[0].SERIALNUMBER;
	    		art.dialog.data("selectMater", {
	    			SERIALNUMBER:SERIALNUMBER
				});
	    		AppUtil.closeLayer();
    		}else{
	    		art.dialog({
					content : "您选择的材料已失效，请选择有效材料！",
					icon : "warning",
					ok : true
				});
				$("#ShareLicenseGrid").datagrid('clearSelections');
    		}
    	}
    }

	$(function() {
		$("#ShareLicenseGrid").datagrid({
		});
	});
	
	function dosearchItem(){
		$("#ShareLicenseGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ShareLicenseToolbar','ShareLicenseGrid');
	}
	
	function rowformaterstatus(val,row,index){		
		if(val=="-1"){
			return "<font color='red'><b>废弃</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>正常</b></font>";
		}else if(val=="2"){
			return "<font color='green'><b>未签章</b></font>";
		}else if(val=="3"){
			return "<font color='green'><b>已签章</b></font>";
		}else if(val=="4"){
			return "<font color='black'><b>打印</b></font>";
		}else if(val=="0"){
			return "<font color='red'><b>发布异常</b></font>";
        }else if(val=="-2"){
			return "<font color='red'><b>过期</b></font>";
        }else if(val=="-3"){
			return "<font color='red'><b>挂起</b></font>";
        }
	}
	
	function rowformaterfrom(value,row,index){
		if(value=='1'){
			return "<font color='blue'>智能填单</font>";
		}else{
			return "<font color='red'></font>";
		}
	}
	
	
	function viewFiles(){
		var SERIALNUMBER = AppUtil.getEditDataGridRecord("ShareLicenseGrid");
		if(SERIALNUMBER){
	    	$.dialog.open("licenseCatalogController/licenseFiles.do?SERIALNUMBER="+SERIALNUMBER, {
		        title : "附件列表",
		        width: "80%",
		        height: "80%",
		        fixed: true,
		        lock : true,
		        resize : false
		    }, false);
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<div class="easyui-layout eui-dialog" fit="true" >	
		
		<div data-options="region:'center',split:false">
			<div id="ShareLicenseToolbar">				
				<!--====================开始编写隐藏域============== -->
                <input type="hidden" name="creditlevel" value="${creditlevel }"/>
                <input type="hidden" name="PERSON_CREDIT_USE" value="${PERSON_CREDIT_USE }"/>
				<!--====================结束编写隐藏域============== -->
				<form name="LicenseCatalogForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
                        <td style="width:68px;text-align:right;">目录名称</td>
                        <td style="width:135px;"><input class="eve-input"
                            type="text" maxlength="20" style="width:128px;"
                            name="CATALOG_NAME" /></td>
                        <td style="width:68px;text-align:right;">目录编码</td>
                        <td style="width:135px;"><input class="eve-input"
                            type="text" maxlength="20" style="width:128px;"
                            name="CATALOG_CODE" /></td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="dosearchItem();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('LicenseCatalogForm')" /></td>
					</tr>
				</table>
			    </form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================--> 
			<table rownumbers="true" pagination="false" striped="true"
				id="ShareLicenseGrid" fitColumns="true" toolbar="#ShareLicenseToolbar"
				method="post" idField="SERIALNUMBER" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="licenseCatalogController/shareMaterAndLicenseData.do?holderCode=${holderCode}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
	                    <th data-options="field:'SERIALNUMBER',hidden:true" width="80">SERIALNUMBER</th>
	                    <th data-options="field:'LICENSENAME',align:'left'" width="150">材料归属目录</th>
	                    <th data-options="field:'CREATE_TIME',align:'left'" width="150">创建时间</th>
	                    <th data-options="field:'STATUS',align:'left',formatter:rowformaterstatus" width="150">状态</th>
	                    <th data-options="field:'DATA_RESOURCE',align:'left',formatter:rowformaterfrom" width="150">材料来源</th>
					</tr>
				</thead>
			</table>
	
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				<input value="材料附件" type="button" onclick="viewFiles();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input value="确定" type="button" onclick="doSelectRows()"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
