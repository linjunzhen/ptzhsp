<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("input[name='aab001']").addClass("validate[required]");
           $("#zgInsuForm").validationEngine({
               promptPosition:"bottomLeft"
           });
         	 //空数据，横向滚动
			$('#zgInsuGrid').datagrid({
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
     function zgInsuSearch(){
            var validateResult =$("#zgInsuForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#bdcygdacxGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('zgInsuToolbar','zgInsuGrid');
            $('#zgInsuGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#zgInsuGrid").datagrid("getRows");
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
		function deletezgInsuGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"zgInsuGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editzgInsuGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("zgInsuGrid");
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
		<div id="zgInsuToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="zgInsuForm" id="zgInsuForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>公民身份号码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aac002" /></td>
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aac003" /></td>
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位编号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aab001" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="zgInsuSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('zgInsuForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="zgInsuGrid" fitcolumns="true" toolbar="#zgInsuToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?zgInsuGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aaa027',align:'left'" width="10%">统筹区编码</th>
					<th data-options="field:'aab001',align:'left'" width="10%">单位ID</th>
					<th data-options="field:'aac001',align:'left'" width="10%">人员ID</th>
					<th data-options="field:'aac003',align:'left'" width="10%">姓名</th>
					<th data-options="field:'aac002',align:'left'" width="10%">公民身份证号码</th>
					<th data-options="field:'aab033',align:'left',formatter:formatZsfs" width="10%">征收方式</th>
					<th data-options="field:'aab301',align:'left'" width="10%">所属行政区划代码</th>
					<th data-options="field:'aab401',align:'left'" width="10%" >户口簿编号</th>
					<th data-options="field:'aac004',align:'left',formatter:formatSex" width="10%">性别</th>
					<th data-options="field:'aac005',align:'left',formatter:formatMz" width="10%">民族</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="10%">险种类型</th>
					<th data-options="field:'aae030',align:'left'" width="10%">开始日期</th>
					<th data-options="field:'aae031',align:'left'" width="10%">终止日期</th>
					<th data-options="field:'aae100',align:'left',formatter:formatYxbz" width="10%">有效标志</th>
					
					<th data-options="field:'aac006',align:'left'" width="10%">出生日期</th>
					<th data-options="field:'aac007',align:'left'" width="10%">首次参加工作时间</th>
					<th data-options="field:'aac009',align:'left',formatter:formatHkxz" width="10%">户口性质</th>
					
					<th data-options="field:'aac010',align:'left'" width="10%">户口所在地址</th>
					<th data-options="field:'aac011',align:'left',formatter:formatWhcd" width="11%">文化程度</th>
					<th data-options="field:'aac012',align:'left',formatter:formatGrsf" width="10%">个人身份</th>
					<th data-options="field:'aac013',align:'left',formatter:formatYgxs" width="10%">用工形式</th>
					
					<th data-options="field:'aac014',align:'left',formatter:formatZyjs" width="10%">专业技术</th>
					<th data-options="field:'aac015',align:'left',formatter:formatJsdj" width="10%">技术等级</th>
					<th data-options="field:'aac016',align:'left',formatter:formatJyzk" width="10%">就业状态</th>
					<th data-options="field:'aac017',align:'left',formatter:formatHyzk" width="10%">婚姻状况</th>
					
					<th data-options="field:'aac020',align:'left',formatter:formatZw" width="10%">职务</th>
					<th data-options="field:'aac028',align:'left',formatter:formatNmgbs" width="10%">农民工标识</th>
					<th data-options="field:'aac031',align:'left',formatter:formatGrjfzt" width="10%">个人缴费状态</th>
					<th data-options="field:'aac033',align:'left'" width="10%">异地参保开始时间</th>
					
					<th data-options="field:'aac040',align:'left'" width="10%">工资</th>
					<th data-options="field:'aac058',align:'left',formatter:formatZjlx" width="10%">身份证件类型</th>
					<th data-options="field:'aac060',align:'left',formatter:formatSczt" width="10%">生存状态</th>
					<th data-options="field:'aac066',align:'left',formatter:formatCbsf" width="10%">参保身份</th>
					
					<th data-options="field:'aac084',align:'left',formatter:formatLtxbz" width="10%">离退休标志</th>
					<th data-options="field:'aac114',align:'left'" width="10%">异地安置人员标识</th>
					<th data-options="field:'aac115',align:'left'" width="10%">居民医保参保属性</th>
					<th data-options="field:'aae004',align:'left'" width="10%">联系人姓名</th>
					<th data-options="field:'aae005',align:'left'" width="10%">联系电话</th>
					<th data-options="field:'aae006',align:'left'" width="10%">地址</th>
					<th data-options="field:'aae007',align:'left'" width="10%">邮政编码</th>
					<th data-options="field:'aae013',align:'left'" width="10%">备注</th>
					
					<th data-options="field:'aae146',align:'left'" width="10%">社会化管理形式</th>
					<th data-options="field:'aae159',align:'left'" width="10%">联系电子邮箱</th>
					<th data-options="field:'aae180',align:'left'" width="10%">人员缴费基数</th>
					<th data-options="field:'aaz002',align:'left'" width="10%">业务日志ID</th>
					<th data-options="field:'aaz003',align:'left'" width="10%">当事人银行账号ID</th>
					
					<th data-options="field:'aaz113',align:'left'" width="10%">浮动费率参数ID</th>
					<th data-options="field:'aaz157',align:'left'" width="10%">人员社会保险明细ID</th>
					<th data-options="field:'aaz159',align:'left'" width="10%">人员参保关系ID</th>
					<th data-options="field:'aaz177',align:'left'" width="10%">人员登记事件ID</th>
					<th data-options="field:'aaz289',align:'left'" width="10%">征缴规则参数ID</th>
					
					<th data-options="field:'aic160',align:'left'" width="10%">待遇开始日期</th>
					<th data-options="field:'ajc050',align:'left'" width="10%">本次参加工作日期</th>
					<th data-options="field:'bab505',align:'left'" width="10%">居民单位管理码</th>
					<th data-options="field:'bab506',align:'left'" width="10%">居民单位名称</th>
					<th data-options="field:'bab507',align:'left'" width="10%">居民单位id</th>
					
					<th data-options="field:'bab508',align:'left',formatter:formatDwlx" width="10%">单位类型</th>
					<th data-options="field:'bac503',align:'left'" width="10%">个人管理码</th>
					<th data-options="field:'bac504',align:'left'" width="10%">解除劳动关系时间</th>
					<th data-options="field:'bac505',align:'left',formatter:formatJzgbbz" width="10%">军转干部标志</th>
					<th data-options="field:'bac519',align:'left',formatter:formatXse" width="10%">是否新生儿标志</th>
					
					<th data-options="field:'bae430',align:'left'" width="10%">户主证件号码</th>
					<th data-options="field:'bae431',align:'left'" width="10%">户主姓名</th>
					<th data-options="field:'bae432',align:'left'" width="10%">与户主关系</th>
					<th data-options="field:'bae433',align:'left'" width="10%">组名</th>
					<th data-options="field:'bae521',align:'left',formatter:formatXzztlb" width="10%">险种主体类别</th>
					
					<th data-options="field:'bae522',align:'left',formatter:formatXzlb" width="10%">险种类别</th>
					<th data-options="field:'bae523',align:'left'" width="10%">保险险种</th>
					<th data-options="field:'bae524',align:'left'" width="10%">最大做账期号</th>
					<th data-options="field:'bae525',align:'left'" width="10%">最大建账批次</th>
					<th data-options="field:'bae526',align:'left'" width="10%">最大建账日期</th>
					
					<th data-options="field:'bae527',align:'left'" width="10%">规则核定日期</th>
					<th data-options="field:'bae528',align:'left'" width="10%">手机号码</th>
					<th data-options="field:'bae917',align:'left'" width="10%">贫困生标志</th>
					<th data-options="field:'orgcode',align:'left'" width="10%">经办机构代码</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
