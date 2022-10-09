<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style>
.gridtable {
	width: 100%;
	height: 100%
}

.gridtable .datagrid-htable {
	border-top: 1px solid #ccc;
	border-left: 1px solid #ccc
}

.gridtable .datagrid-btable {
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc
}

.gridtable .datagrid-header-row td {
	border-width: 0;
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.gridtable .datagrid-header-row td:last-child {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}

.gridtable .datagrid-body td {
	border-width: 0;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
</style>
<script type="text/javascript">

	$(document).ready(
			function() {

			});
			
	$('#sxslylStatisGrid').datagrid({ pagination: false,
		onLoadSuccess: function (data) {
			if (data.rows.length > 0) {
				//调用mergeCellsByField()合并单元格
				mergeCellsByField("sxslylStatisGrid", "PARENT_DEPART_NAME");
			}
		}
	});		
	/**
	* EasyUI DataGrid根据字段动态合并单元格
	* 参数 tableID 要合并table的id
	* 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
	*/
	function mergeCellsByField(tableID, colList) {
		var ColArray = colList.split(",");
		var tTable = $("#" + tableID);
		var TableRowCnts = tTable.datagrid("getRows").length;
		var tmpA;
		var tmpB;
		var PerTxt = "";
		var CurTxt = "";
		var alertStr = "";
		for (j = ColArray.length - 1; j >= 0; j--) {
			PerTxt = "";
			tmpA = 1;
			tmpB = 0;

			for (i = 0; i <= TableRowCnts; i++) {
				if (i == TableRowCnts) {
					CurTxt = "";
				}
				else {
					CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
				}
				if (PerTxt == CurTxt) {
					tmpA += 1;
				}
				else {
					tmpB += tmpA;
					
					tTable.datagrid("mergeCells", {
						index: i - tmpA,
						field: ColArray[j],　　//合并字段
						rowspan: tmpA,
						colspan: null
					});
					tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
						index: i - tmpA,
						field: "Ideparture",
						rowspan: tmpA,
						colspan: null
					});
				   
					tmpA = 1;
				}
				PerTxt = CurTxt;
			}
		}
	}
    function showSelectDeparts(){
        var departId = $("input[name='Q_D.DEPART_ID_EQ']").val();
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
                    $("input[name='Q_D.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function showSelectSonDeparts(){
        var departId = $("input[name='Q_DC.DEPART_ID_EQ']").val();
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
                    $("input[name='Q_DC.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='SON_DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
	function rzblformater(value,row,index){
		if(value != ''&&value != undefined&&value != null){
			var num = value.toFixed(2); 
			return num+'%';
		}else{
			return value;
		}
	}
	function gridSearchReset(){
        $("input[name='TJLX']").val("0");
        $("input[name='DEPART_NAME']").val("");
        $("input[name='Q_D.DEPART_ID_EQ']").val("");
        $("input[name='SON_DEPART_NAME']").val("");
        $("input[name='Q_DC.DEPART_ID_EQ']").val("");
	}

	function exportSxslylExcel() {
		var departId = $("input[name='Q_D.DEPART_ID_EQ']").val();
		var childDepartId = $("input[name='Q_DC.DEPART_ID_EQ']").val();
		var type = $("input[name='TJLX']").val();
		window.location.href = 'statisticsController.do?exportSxslylExcel&Q_D.DEPART_ID_EQ=' + departId + '&Q_DC.DEPART_ID_EQ=' + childDepartId + '&TJLX=' + type;
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="sxslylStatisToolBar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
								<a href="#" class="easyui-linkbutton" iconcls="eicon-file-excel-o" plain="true" onclick="exportSxslylExcel();">导出数据</a>
							
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="SxslylForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:98px;text-align:right;">统计类型</td>
							<td>
								<select class="easyui-combobox" name="TJLX">
									<option value="0">所有事项</option>
									<option value="1">清单内事项</option>
								</select>
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							
                            <td style="width:135px;"><input class="eve-input" onclick="showSelectDeparts();"
                                style="width:128px;" name="DEPART_NAME" value="" readonly="readonly"
                                 />
                                 <input type="hidden" name="Q_D.DEPART_ID_EQ" value=""  />
                            </td>
                            <td style="width:68px;text-align:right;">所属处室 </td>
                            <td style="width:135px;"><input class="eve-input" onclick="showSelectSonDeparts();"
                                style="width:128px;" name="SON_DEPART_NAME" value="" readonly="readonly"/>
                                 <input type="hidden" name="Q_DC.DEPART_ID_EQ" value=""  />
                            </td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('sxslylStatisToolBar','sxslylStatisGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="gridSearchReset()" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="sxslylStatisGrid" fitcolumns="true" toolbar="#sxslylStatisToolBar"
			method="post" idfield="LOG_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="statisticsController.do?sxslylData">
			<thead>
				<tr>
<%--					<th data-options="field:'TJLX',width:150" rowspan="2">所属部门</th>--%>
					<th data-options="field:'PARENT_DEPART_NAME'" width="15%" rowspan="2">所属部门</th>
					<th data-options="field:'DEPART_NAME'" width="15%" rowspan="2">所属处室</th>
					<th data-options="field:'CTOTAL',align:'center'" width="10%" rowspan="2">目录数</th>
					<th data-options="field:'TOTAL',align:'center'" width="10%" rowspan="2">事项数</th>
<%--					<th data-options="field:'YDJ',align:'center'" width="100" rowspan="2">是否清单</th>--%>
					<th colspan="2">行政许可</th>
					<th colspan="2">公共服务</th>
					<th colspan="2">其他</th>
					<th data-options="field:'JBJNUM',align:'center'" width="8%" rowspan="2">即办件数</th>
					<th colspan="2">承诺件</th>
					<th data-options="field:'RZSWNUM',align:'center'" width="9%" rowspan="2">入驻省网数</th>
					<th data-options="field:'WRZSWNUM',align:'center'" width="9%" rowspan="2">未入驻省网数</th>
					<th data-options="field:'RZBL',align:'center',formatter:rzblformater" width="20%" rowspan="2" >入驻比率<br/>（入驻省网数/事项总数*100%）</th>
					<th data-options="field:'KTWSNUM',align:'center'" width="9%" rowspan="2">开通网申数</th>
					<th data-options="field:'WKTWSNUM',align:'center'" width="9%" rowspan="2">未开通网申数</th>
					<th data-options="field:'KTBL',align:'center',formatter:rzblformater" width="20%" rowspan="2">开通比率<br/>（开通网申数/事项总数*100%）</th>
					<th data-options="field:'ZDPYT',align:'center'" width="10%" rowspan="2">最多跑一趟数</th>
					<th data-options="field:'ZDPLT',align:'center'" width="10%" rowspan="2">一趟不用跑数</th>
				</tr>
				</tr>
				<tr>
					<th data-options="field:'CXKNUM',align:'center'" width="5%" >目录数</th>
					<th data-options="field:'XKNUM',align:'center'" width="5%" >子项数</th>
					
					<th data-options="field:'CGFNUM',align:'center'" width="5%" >目录数</th>
					<th data-options="field:'GFNUM',align:'center'" width="5%" >子项数</th>

					<th data-options="field:'CQTNUM',align:'center'" width="5%" >目录数</th>
					<th data-options="field:'QTNUM',align:'center'" width="5%" >子项数</th>
					
					<th data-options="field:'CNJNUM',align:'center'" width="8%" >普通件数</th>
					<th data-options="field:'TSJNUM',align:'center'" width="8%" >特殊件数</th>
				</tr>
			</thead>
		</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>