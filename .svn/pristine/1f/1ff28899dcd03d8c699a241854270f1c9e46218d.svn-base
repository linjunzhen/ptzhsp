<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript">

	
	/* function formatSubject(val,row){
		var fileId = row.FILE_ID;
        var href = "<a href='";
		if(fileId!=""){
            href += "DownLoadServlet?fileId="+fileId;
		}else{
			href += "javascript:void(0);";
		}
        href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	} */
	
	function formatSubject(val,row){
		var href = "";
		href += "<a href=\'javascript:void(0);\' onclick=\"openFlowResult('"+row.EXE_ID+"');\"><span style=\'text-decoration:underline;color:#0368ff;\'>"+val+"</span></a>";
		return href;
	}
	
	//显示办结结果信息
	function openFlowResult(exeId){
		$.dialog.open("executionController.do?flowResultDetail&exeId="+exeId, {
			title : "办结结果信息",
			width : "70%",
			lock : true,
			resize : false,
			height : "65%",
		}, false);
	}
	
	function formatdownFile(val,row){
		var str = "";
		str += "<a href='ktStampController.do?downLoadStampFile&exeId=" + row.EXE_ID + "&stampCode=shbxdjb";
		str += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+"社会保险登记表"+"</span></a>";
		str += "，";
		str += "<a href='ktStampController.do?downLoadStampFile&exeId=" + row.EXE_ID + "&stampCode=ylgsbxsqb";
		str += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+"企业养老和工伤保险申请表"+"</span></a>";
		return str;
    }
    
    //员工参保信息材料下载
    function formatdownCbFile(val,row){
	      var newhtml = "";
	      if(row.FILE_ID3!=null && row.FILE_ID3!=undefined && row.FILE_ID3!=""){
	     	 $.ajax({
		         type: "POST",
			     url: "executionController.do?getResultFiles&fileIds="+row.FILE_ID3,
			     async: false, //采用同步方式进行数据判断
			     success: function (resultJson) {
			    	   if(resultJson!="websessiontimeout"){
		                 var list=resultJson.rows;
		                 for(var i=0; i<list.length; i++){
		                     newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
		                     newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
		                     newhtml+=list[i].FILE_NAME+'</a>';
		                     newhtml+='</p>';
		                 }
		             }
			     }
			 });
	     }
	     return newhtml;
    }

	$(document).ready(function() {
		var start1 = {
			elem : "#SocialT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#SocialT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);
	});
    /**
	 * 重新生成附件
     */
    function updateSocialFile() {
        var $dataGrid = $("#SocialGrid");
        var url="exeButtonController.do?updateSocialFile";
        var rowsData = $dataGrid.datagrid('getChecked');
        if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
            art.dialog({
                content: "请选择需要被更新的记录!",
                lock : true,
                icon:"warning",
                ok: true
            });
        }else if(rowsData.length > 1){
            art.dialog({
                content: "只能选择一条记录!",
                lock : true,
                icon:"warning",
                ok: true
            });
        } else{
            art.dialog.confirm("您确定要更新该记录吗?", function() {
                var layload = layer.load('正在提交请求中…');
                var colName = $dataGrid.datagrid('options').idField;
                var selectColNames = "";
                for ( var i = 0; i < rowsData.length; i++) {
                    if(i>0){
                        selectColNames+=",";
                    }
                    selectColNames+=eval('rowsData[i].'+colName);
                }
                $.post(url,{
                    selectColNames:selectColNames
                }, function(responseText, status, xhr) {
                    layer.close(layload);
                    var resultJson = $.parseJSON(responseText);
                    if(resultJson.success == true){
                        art.dialog({
                            content: resultJson.msg,
                            icon:"succeed",
                            time:3,
                            ok:function(){
                                $dataGrid.datagrid('reload');
                                $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
                            },
                        });
                        if(callback!=null){
                            callback.call(this,resultJson);
                        }else{
                            $dataGrid.datagrid('reload');
                            $dataGrid.datagrid('clearSelections').datagrid('clearChecked');
                        }
                    }else{
                        art.dialog({
                            content: resultJson.msg,
                            icon:"error",
                            ok: true
                        });
                    }
                });
            });
        }
    }
</script>
<div class="easyui-layout eui-jh-box" fit="true">

	<div region="center">

		<!-- =========================查询面板开始========================= -->
		<div id="SocialToolbar">
<%--			<div class="right-con">--%>
<%--				<div class="e-toolbar style="z-index: 1111;top: 0px;left: 0px;">--%>
<%--					<div class="e-toolbar-ct">--%>
<%--						<div class="e-toolbar-overflow">--%>
<%--							<a href="#" class="easyui-linkbutton" plain="true"--%>
<%--							   iconCls="eicon-refresh"--%>
<%--							   onclick="updateSocialFile()">重新生成附件</a>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--				</div>--%>
<%--			</div>--%>
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="SocialForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
						    <td style="width:68px;text-align:right;">申报号</td>
							<td style="width:135px;">
							<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.EXE_ID_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">办件名称</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.SUBJECT_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
															style="width:128px;float:left;" class="laydate-icon"
															id="SocialT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
															style="width:128px;float:left;" class="laydate-icon"
															id="SocialT.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>

							<td colspan="2"><input type="button" value="查询"
												   class="eve-button"
												   onclick="AppUtil.gridDoSearch('SocialToolbar','SocialGrid')" />
								<input type="button" value="重置" class="eve-button"
									   onclick="AppUtil.gridSearchReset('SocialForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="SocialGrid" fitcolumns="false" toolbar="#SocialToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="flowTaskController.do?socialNeedMeHandle">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'EXE_ID',align:'left'" width="15%">申报号</th>
					<th data-options="field:'SUBJECT',align:'left'" width="40%" formatter="formatSubject">办件名称</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="20%">申请时间</th>
					<th data-options="field:'FILE_ID',align:'left'" width="22%" formatter="formatdownFile">下载</th>
					<th data-options="field:'FILE_ID3',align:'left'" width="22%" formatter="formatdownCbFile">员工参保信息下载</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
