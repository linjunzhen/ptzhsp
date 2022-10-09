<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 批量审核服务事项列表记录
	 */
	function cancelDepartServiceItemGridRecord() {
	       var $dataGrid = $("#DepartServicePublishItemGrid");
	        var rowsData = $dataGrid.datagrid('getChecked');
	        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
	            art.dialog({
	                content: "请选择需要取消的办事事项!",
	                icon:"warning",
	                ok: true
	            });
	        }else{
	                var colName = $dataGrid.datagrid('options').idField;  
	                var selectColNames = "";
	                for ( var i = 0; i < rowsData.length; i++) {
	                    if(i>0){
	                        selectColNames+=",";
	                    }
	                    selectColNames+=eval('rowsData[i].'+colName);
	                }
	                $.dialog.open("serviceItemController.do?applyInfo&itemIds=" + selectColNames+"&fileFlag=1", {
	                    title : "审核意见",
	                    width: "600px",
	                    height: "400px",
	                    fixed: true,
	                    lock : true,
	                    resize : false,
	                    close: function () {
	                        var backinfo = art.dialog.data("backinfo");
	                        if(backinfo&&backinfo.back){
	                            art.dialog({
	                                content: "提交成功",
	                                icon:"succeed",
	                                time:3,
	                                ok: true
	                            });
	                            art.dialog.removeData("backinfo");
	                            $dataGrid.datagrid('reload');
	                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
	                        }
	                    }
	                }, false);
	        }
	};
	$(document).ready(function() {
		AppUtil.initAuthorityRes("DepartServicePublishItemToolbar");
	});
	$(document).ready(function(){
	    var start1 = {
	    elem: "#DepPub.OPERATE_TIME_BEGIN",
	    format: "YYYY-MM-DD 00:00:00",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_L.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_L.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='Q_L.OPERATE_TIME_>=']").val("");
	    		}
	    	}
	    }
	};
	var end1 = {
	    elem: "#DepPub.OPERATE_TIME_END",
	    format: "YYYY-MM-DD 23:59:59",
	    istime: false,
	    choose: function(datas){
	        var beginTime = $("input[name='Q_L.OPERATE_TIME_>=']").val();
	    	var endTime = $("input[name='Q_L.OPERATE_TIME_<=']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime.replace("-", "/").replace("-", "/"));
	    		var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='Q_L.OPERATE_TIME_<=']").val("");
	    		}
	    	}
	    }
	};
	laydate(start1);
	laydate(end1);

});
	function formatFwsxzt(val,row){
		if(val=="-1"){
			return "<font color='red'><b>草稿</b></font>";
		}else if(val=="1"){
			return "<font color='blue'><b>发布</b></font>";
		}else if(val=="2"){
			return "<font color='green'><b>审核中</b></font>";
		}else if(val=="3"){
			return "<font color='black'><b>取消</b></font>";
		}else if(val=="4"){
			return "<font color='red'><b>退回</b></font>";
        }
	};
	
	   /**
     * 编辑服务事项列表记录
     */
    function editPublishDepartServiceItemGridRecord() {
        var entityId = AppUtil.getEditDataGridRecord("DepartServicePublishItemGrid");
        if (entityId) {
            showPublishDepartServiceItemWindow(entityId);
        }
    }

    /**
     * 显示服务事项信息对话框
     */
    function showPublishDepartServiceItemWindow(entityId) {
        $.dialog.open("departServiceItemController.do?info&entityId=" + entityId, {
            title : "服务事项信息",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false,
            close: function () {
                
				$("#DepartServicePublishItemGrid").datagrid('reload');
                //AppUtil.gridDoSearch("DepartServicePublishItemToolbar","DepartServicePublishItemGrid");
            }
        }, false);
    };
    function showSwbExcelExportWindow(){
		$.dialog.open("statisticsController.do?exportSwbView", {
    		title : "导出省网办数据",
            width:"400px",
            lock: true,
            resize:false,
            height:"300px",
    	}, false);
	}
    function receiveSwbItem(){
        AppUtil.ajaxProgress({
            url:"serviceItemController.do?receiveSwbItem",
            params:{
            },
            callback:function(resultJson){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                $("#DepartServicePublishItemGrid").datagrid("reload");
            }
        })
	}
    /**
     * 导出execl
     */
    function showExcelExportWindow() {
        AppUtil.showExcelExportWin({
            dataGridId:"DepartServicePublishItemGrid",
            tplKey:"ServiceItemReportTpl",
            excelName:"服务事项数据",
            queryParams:{
                "T.ITEM_CODE":($("#DepartServicePublishItemToolbar input[name='Q_T.ITEM_CODE_LIKE']").val()).trim(),
                "T.ITEM_NAME":($("#DepartServicePublishItemToolbar input[name='Q_T.ITEM_NAME_LIKE']").val()).trim(),
                "D.DEPART_ID":$("#DepartServicePublishItemToolbar input[name='Q_D.DEPART_ID_EQ']").val(),
//                "SC.CHILD_DEPART_ID":$("#DepartServicePublishItemToolbar input[name='Q_DC.DEPART_ID_EQ']").val(),
				"T.ZBCS_ID":$("#DepartServicePublishItemToolbar input[name='Q_T.ZBCS_ID_EQ']").val(),
                "T.SXLX":$("#DepartServicePublishItemToolbar input[name='Q_T.SXLX_=']").val(),
//                "T.CATALOG_CODE":$("#DepartServicePublishItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(),
                "T.SXXZ":$("#DepartServicePublishItemToolbar input[name='Q_T.SXXZ_=']").val()
            }
        });
    };
    
    $(document).ready(function() {
        var departcatalogTreeSetting = {
            edit : {
                enable : false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                selectedMulti : false
            },
            callback : {
                onClick : ondepartcatalogTreeClick
            },
            async : {
                enable : true,
                url : "departCatalogController.do?tree"
            }
        };
        $.fn.zTree.init($("#departcatalogTree"), departcatalogTreeSetting);
        
        $("#ServiceItemXzywGrid").datagrid({
            onDblClickRow: function(index, row){
                showDepartServiceItemXzywWindow(row.ITEM_ID);
            }
        });
    });
    
    function gbDepartUrl(){
        var  cname = $("#departmlmc").val();
        var zTree = $.fn.zTree.getZTreeObj("departcatalogTree");
        zTree.setting.async.url = "catalogController.do?tree";
        zTree.setting.async.otherParam = {
            "Q_SC.CATALOG_NAME_LIKE" : cname,
        }
        zTree.reAsyncChildNodes(null, "refresh");
    }
    /**
     * 点击树形节点触发的事件
     */
    function ondepartcatalogTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
            $("#DepartServicePublishItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(treeNode.id);
            AppUtil.gridDoSearch("DepartServicePublishItemToolbar","DepartServicePublishItemGrid");
        }else if(event.target.tagName == "SPAN"&&treeNode.id==0){
            $("#DepartServicePublishItemToolbar input[name='Q_T.CATALOG_CODE_EQ']").val("");
            AppUtil.gridDoSearch("DepartServicePublishItemToolbar","DepartServicePublishItemGrid");
        }
    }
    //保存排序
    function updateCsn(){
            var rows = $("#DepartServicePublishItemGrid").datagrid("getRows"); 
            var itemIds = [];
            for(var i=0;i<rows.length;i++){
            	itemIds.push(rows[i].ITEM_ID);
            }
            if(itemIds.length>0){
                AppUtil.ajaxProgress({
                    url:"serviceItemController.do?updateSn",
                    params:{
                    	itemIds:itemIds
                    },
                    callback:function(resultJson){
                          parent.art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                        $("#DepartServicePublishItemGrid").datagrid("reload");
                    }
                })
            }
        
    }
    
    function showPSelectDeparts(){
        var departId = $("input[id='PdepartId']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[id='PdepartId']").val(selectDepInfo.departIds);
                    $("input[id='PdepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    //附件格式
    function  formatAtach(val,row){
                        var  fileids=val;
                        var newhtml="";
                        var  fileName=row.FILE_NAME;
                        if(fileName!=null){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
	    		 		newhtml+=fileName+'</a>';
	    		 		newhtml+='</p>';
	    		 	    //$("#fileListDiv").html(newhtml);
	    		 	   return  newhtml;
	    		 	   }
    }
    
    
    //附件格式
    function  formatAtach2(val,row){
                        var  fileids=val;
                        var newhtml="";
                        var  fileName=row.FILE_NAME2;
                        if(fileName!=null){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
	    		 		newhtml+=fileName+'</a>';
	    		 		newhtml+='</p>';
	    		 	    //$("#fileListDiv").html(newhtml);
	    		 	   return  newhtml;
	    		 	   }
    }
  //附件格式
    function  formatAtach3(val,row){
                        var  fileids=val;
                        var newhtml="";
                        var  fileName=row.FILE_NAME3;
                        if(fileName!=null){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+fileids+'\');">';
	    		 		newhtml+=fileName+'</a>';
	    		 		newhtml+='</p>';
	    		 	    //$("#fileListDiv").html(newhtml);
	    		 	   return  newhtml;
	    		 	   }
    }
    
    
    function showPSelectSonDeparts(){
        var departId = $("input[id='PchildDepartId']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属子部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[id='PchildDepartId']").val(selectDepInfo.departIds);
                    $("input[id='PchildDepartName']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    /**
	* 查看服务事项详细信息
	*/
	function viewPublishDepartServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("DepartServicePublishItemGrid");
	 if (entityId) {
	     $.dialog.open("serviceItemController.do?detailedInfo&entityId=" + entityId, {
		     title : "详细信息",
		     width: "100%",
		     height: "100%",
		     left: "0%",
		     top: "0%",
		     fixed: true,
		     lock : true,
		     resize : false,
		     close: function () {
		         //AppUtil.gridDoSearch("ServiceItemXzywToolbar","ServiceItemXzywGrid");
		     }
		 }, false);
	 }
	}
	/**
	* 打印服务事项详细信息
	*/
	function printPublishDepartServiceItemGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("DepartServicePublishItemGrid");
	 if (entityId) {
	     var dateStr = "";
		dateStr +="&ITEM_ID="+entityId;
		dateStr +="&typeId="+3;
		dateStr +="&TemplatePath=itemdetail.doc";
		dateStr +="&TemplateName=服务事项详细信息";
		 var dateStrEncode = encodeURI(dateStr);
		//打印模版
       $.dialog.open("printAttachController.do?printItemTemplate"+dateStrEncode, {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	 }
	}
	//批量划转
	function transferPublishDepartServiceItemGridRecord() {
		var $dataGrid = $("#DepartServicePublishItemGrid");
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content : "请选择需要划转的服务事项!",
				icon : "warning",
				ok : true
			});
		} else {
			var colName = $dataGrid.datagrid('options').idField;
			var selectColNames = "";
			for (var i = 0; i < rowsData.length; i++) {
				if (i > 0) {
					selectColNames += ",";
				}
				selectColNames += eval('rowsData[i].' + colName);
			}
			$.dialog.open("departServiceItemController.do?transferView&itemIds=" + selectColNames, {
				title : "服务事项划转",
				width : "1000px",
				height : "600px",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$dataGrid.datagrid('reload');
					$dataGrid.datagrid('clearSelections').datagrid('clearChecked');
				}
			}, false);
		}
	}

	function resetPublishForm(){
    	AppUtil.gridSearchReset('ServicePublishItemForm');
    	$("input[name='Q_D.DEPART_ID_EQ']").val('');
    	$("input[name='Q_DC.DEPART_ID_EQ']").val('');
    	
    }
	
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});
	$("#DepartServicePublishItemGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
	
	//空数据，横向滚动
	$('#DepartServicePublishItemGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
</script>
<div class="easyui-layout" fit="true">
    <!-- <div data-options="region:'west',split:false"
        style="width:250px;background: #f5f5f5;">
        <div class="right-con">
            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">                
                <div class="e-toolbar-ct">
                    <div class="e-toolbar-overflow">
                        <div style="color:#005588;">
                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                style="position: relative; top:7px; float:left;" />&nbsp;服务目录树
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            目录名称：<input class="eve-input" style="width:100px;" name="mlmc" id="departmlmc"/>
        <input type="button" value="查询" class="eve-button" onclick="gbDepartUrl();" />
        <ul id="departcatalogTree" class="ztree"></ul>
    </div> -->
	<div data-options="region:'center',split:false">
		<!-- =========================查询面板开始========================= -->
		<div id="DepartServicePublishItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!-- <input type="hidden" name="Q_T.CATALOG_CODE_EQ" /> -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#"
                                class="easyui-linkbutton" reskey="EDIT_DepartServiceItemPub"
                                iconcls="eicon-pencil" plain="true"
                                onclick="editPublishDepartServiceItemGridRecord();">编辑</a>
                        <!-- <a href="#" class="easyui-linkbutton" 
                                resKey="SAVECSN_DepartServiceItem" iconCls="icon-tick" plain="true"
                                onclick="updateCsn();">保存排序</a> -->
                        <a href="#"
                                class="easyui-linkbutton" reskey="CANCEL_DepartServiceItem"
                                iconcls="eicon-remove" plain="true"
                                onclick="cancelDepartServiceItemGridRecord();">申请取消</a> 
                        <a href="#" class="easyui-linkbutton" reskey="Expor_DepartServiceItem"
                                iconcls="eicon-file-excel-o" plain="true" 
                                    onclick="showExcelExportWindow();">导出数据</a> 
						<a href="#"
                                class="easyui-linkbutton" reskey="VIEW_DepartServiceItem"
                                iconcls="eicon-file-text-o" plain="true"
                                onclick="viewPublishDepartServiceItemGridRecord();">详细信息</a>
                        <a href="#"
                                class="easyui-linkbutton" reskey="PRINT_DepartServiceItem"
                                iconcls="eicon-print" plain="true"
                                onclick="printPublishDepartServiceItemGridRecord();">打印</a>
<%--                        <a href="#" class="easyui-linkbutton" reskey="Expor_SWBDepartServiceItem"
                                iconcls="icon-excel" plain="true" 
                                    onclick="showSwbExcelExportWindow();">导出省网办数据</a> 
                        <a href="#"
                                class="easyui-linkbutton" reskey=RECEIVE_SWB_ITEM
                                iconcls="icon-disabled" plain="true"
                               onclick="receiveSwbItem();">接收省网下发事项</a> --%>
                        <a href="#"
                                class="easyui-linkbutton" reskey="TRANSFER_DepartServiceItem"
                                iconcls="eicon-arrow-right" plain="true"
                                onclick="transferPublishDepartServiceItemGridRecord();">划转</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServicePublishItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="36" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemKind',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:75px;text-align:right;">是否一窗通办</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.IS_YCTB_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
<!-- 							<td colspan="2"></td> -->
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
						</tr>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">起始日期</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								id="DepPub.OPERATE_TIME_BEGIN" 
								name="Q_L.OPERATE_TIME_&gt;=" /></td>
							<td style="width:68px;text-align:right;">截止日期</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="60" style="width:128px;"
								id="DepPub.OPERATE_TIME_END"
								name="Q_L.OPERATE_TIME_&lt;=" /></td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showPSelectDeparts();"
                                style="width:128px;" name="DEPART_NAME" id="PdepartName" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" id="PdepartId" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">所属子部门</td>
                            <td style="width:135px;"><input class="eve-input" onclick="showPSelectSonDeparts();"
                                style="width:128px;" name="SON_DEPART_NAME" id="PchildDepartName" value="" readonly="readonly"/>
                                 <input type="hidden" name="Q_T.ZBCS_ID_EQ" id="PchildDepartId" value=""  />
                            </td>
							<!-- <td colspan="2"></td> -->
							
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('DepartServicePublishItemToolbar','DepartServicePublishItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="resetPublishForm();" /></td>
						</tr>
						<!-- <tr style="height:28px;">
								
						</tr> -->
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartServicePublishItemGrid" fitcolumns="true" toolbar="#DepartServicePublishItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
<%--			data-options="onLoadSuccess:function(){--%>
<%--                      $(this).datagrid('enableDnd');--%>
<%--            }"--%>
			url="departServiceItemController.do?publishdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="16%">唯一码（事项编码）</th>
					<th data-options="field:'FWSXZT',align:'left'" width="10%" formatter="formatFwsxzt" >事项状态</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="30%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
					<th data-options="field:'SXLX',align:'left'" width="10%" >办件类型</th>								
					<th data-options="field:'OPERATE_TIME',align:'left'" width="16%" >发布时间</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'DEPART_NAMEC',align:'left'" width="15%" >所属子部门</th>
					<!--  
					<th data-options="field:'FWDX',align:'left'" width="100" formatter="formatFwdx">服务对象</th>
					-->
					<th data-options="field:'CNQXGZR',align:'left'" width="13%">承诺时限工作日</th>
					<th data-options="field:'C_SN',align:'left'" width="8%">排序值</th>
					<th data-options="field:'THYJ3',align:'left'" width="15%" nowrap="false">申请审核意见</th>
					<th data-options="field:'FILEATTACH_PATH3',align:'left'" width="15%"  formatter="formatAtach3">申请审核附件</th>
					<th data-options="field:'THYJ',align:'left'" width="15%" nowrap="false">审核意见</th>
					<th data-options="field:'FILEATTACH_PATH',align:'left'" width="15%"  formatter="formatAtach">审核附件</th>
					<th data-options="field:'NFBYJ',align:'left'" width="15%" nowrap="false">拟发布意见</th>
					<th data-options="field:'FILEATTACH_PATH2',align:'left'" width="15%"  formatter="formatAtach2">拟发布附件</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
