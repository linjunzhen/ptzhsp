<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aab001']").addClass("validate[required]");
           $("#unitBasicForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#unitBasicGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			});	
      });
    
     //查询
     function psnBasicSearch(){
            var validateResult =$("#unitBasicForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#unitBasicGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('unitBasicToolbar','unitBasicGrid');
            $('#unitBasicGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#unitBasicGrid").datagrid("getRows");
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
        
		/**
		 * 删除列表记录
		 */
		function deleteunitBasicGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"unitBasicGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editunitBasicGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("unitBasicGrid");
			if (entityId) {
				showExecutionWindow(entityId);
			}
		}
		/**
		 * 显示对话框
		 */
		function showExecutionWindow(entityId) {
			$.dialog.open("executionController.do?info&entityId=" + entityId, {
				title : "流程实例信息",
				width : "600px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		};

	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="unitBasicToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="unitBasicForm" id="unitBasicForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位编号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aab001" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="psnBasicSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('unitBasicForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="unitBasicGrid" fitcolumns="true" toolbar="#unitBasicToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?unitBasicGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aaa149',align:'left',formatter:formatHyfxlb" width="12%">行业风险类别</th>
					<th data-options="field:'aab001',align:'left'" width="15%">单位ID</th>
					<th data-options="field:'aab002',align:'left'" width="11%">社会保险登记证编码</th>
					<th data-options="field:'aab003',align:'left'" width="15%">组织机构代码</th>
					<th data-options="field:'aab004',align:'left'" width="10%">单位名称</th>
					<th data-options="field:'aab006',align:'left',formatter:formatGsdjzzzl" width="15%" >工商登记执照种类</th>
					<th data-options="field:'aab007',align:'left'" width="10%">工商登记执照号码</th>
					<th data-options="field:'aab008',align:'left'" width="10%">工商登记发照日期</th>
					
					<th data-options="field:'aab009',align:'left'" width="15%">工商登记有效期限（年）</th>
					<th data-options="field:'aab013',align:'left'" width="15%">法定代表人姓名</th>
					<th data-options="field:'aab014',align:'left'" width="15%">法定代表人公民身份号码</th>
					<th data-options="field:'aab017',align:'left'" width="11%">专管员所在部门</th>
					<th data-options="field:'aab019',align:'left',formatter:formatDwlx" width="10%">单位类型</th>
					<th data-options="field:'aab020',align:'left',formatter:formatJjlx" width="15%">经济类型</th>
					
					<th data-options="field:'aab021',align:'left',formatter:formatLsgx" width="10%">隶属关系</th>
					<th data-options="field:'aab022',align:'left',formatter:formatHydm" width="15%">行业代码</th>
					<th data-options="field:'aab023',align:'left'" width="15%">主管部门或主管机构</th>
					<th data-options="field:'aab030',align:'left'" width="11%">税号</th>
					<th data-options="field:'aab031',align:'left'" width="10%">税务机构编码</th>
					<th data-options="field:'aab032',align:'left'" width="15%">税务机构名称</th>
					
					<th data-options="field:'aab065',align:'left',formatter:formatTsdwlb" width="10%">特殊单位类别</th>
					<th data-options="field:'aab069',align:'left'" width="15%">名称</th>
					<th data-options="field:'aab078',align:'left'" width="15%">工商登记注册地所在行政区划</th>
					<th data-options="field:'aab082',align:'left'" width="11%">在职总人数</th>
					<th data-options="field:'aab083',align:'left'" width="10%">退休总人数</th>
					<th data-options="field:'aab090',align:'left'" width="15%">统一社会信用代码</th>
					
					<th data-options="field:'aab301',align:'left'" width="10%">所属行政区划代码</th>
					<th data-options="field:'aab999',align:'left'" width="15%">单位管理码</th>
					<th data-options="field:'aae004',align:'left'" width="15%">联系人姓名</th>
					<th data-options="field:'aae005',align:'left'" width="11%">联系电话</th>
					<th data-options="field:'aae006',align:'left'" width="10%">地址</th>
					<th data-options="field:'aae007',align:'left'" width="15%">邮政编码</th>
					
					<th data-options="field:'aae013',align:'left'" width="10%">备注</th>
					<th data-options="field:'aae047',align:'left'" width="15%">成立日期</th>
					<th data-options="field:'aae048',align:'left'" width="15%">批准成立部门</th>
					<th data-options="field:'aae049',align:'left'" width="11%">批准成立日期</th>
					<th data-options="field:'aae051',align:'left'" width="10%">批准成立文号</th>
					<th data-options="field:'aae159',align:'left'" width="15%">联系电子邮箱</th>
					
					<th data-options="field:'aaz001',align:'left'" width="10%">组织ID</th>
					<th data-options="field:'aaz002',align:'left'" width="15%">业务日志ID</th>
					<th data-options="field:'aaz066',align:'left'" width="15%">税务机构ID</th>
					<th data-options="field:'bab500',align:'left',formatter:formatZw" width="11%">法人职务</th>
					<th data-options="field:'bab509',align:'left'" width="10%">工商登记发照机关</th>
					<th data-options="field:'bab510',align:'left',formatter:formatDsbdjg" width="15%">地税比对结果</th>
					
					<th data-options="field:'bab511',align:'left'" width="10%">单位档案号</th>
					<th data-options="field:'bae516',align:'left'" width="15%">单位总人数</th>
					<th data-options="field:'bae517',align:'left'" width="15%">医保在职总人数</th>
					<th data-options="field:'bae518',align:'left'" width="11%">生育在职总人数</th>
					<th data-options="field:'baf110',align:'left'" width="10%">合同号</th>
					<th data-options="field:'orgcode',align:'left',formatter:formatSubCenter" width="15%">经办机构代码</th>
					<th data-options="field:'verified',align:'left',formatter:formatHdbj" width="10%">单位核对标记</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
