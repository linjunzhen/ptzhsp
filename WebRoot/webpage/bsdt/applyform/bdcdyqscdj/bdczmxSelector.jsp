<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    	var rows = $("#bdczmxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>=allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		var datas = [];
		var ydydyh = "";
		var unaccept = false;
		for(var i = 0;i<rows.length;i++){			
			var cqzt = rows[i].CQZT;
			if(Trim(cqzt).indexOf("查封")>-1 || Trim(cqzt).indexOf("预查封")>-1 ||Trim(cqzt).indexOf("限制")>-1 || Trim(cqzt).indexOf("无效")>-1
					|| Trim(cqzt).indexOf("查封办文中")>-1){
				parent.art.dialog({
					content: "请注意，该不动产单元号（"+rows[i].BDCDYH+"）状态为"+cqzt+"，不可办理抵押权登记！",
					icon:"warning",
					ok: true
				});
				unaccept = true;
				break;
			}else if(Trim(cqzt).indexOf("抵押")>-1){
			    ydydyh += "（"+rows[i].BDCDYH+"）";
				datas.push(rows[i]);
			}else if(Trim(cqzt) == "权属登记" || Trim(cqzt) == "正常"){
				datas.push(rows[i]);
			} else {
				parent.art.dialog({
					content: "请注意，该不动产单元号（"+rows[i].BDCDYH+"）状态为"+cqzt+"，不可办理抵押权登记！",
					icon:"warning",
					ok: true
				});
				unaccept = true;
				break;
			}						
		}
		if(unaccept){
			return false;
		}
		if(ydydyh.length>0){
			parent.art.dialog.confirm("不动产单元号"+ydydyh+"已抵押!是否继续办理?",function() {
				art.dialog.data("bdczmxInfo", datas);
				AppUtil.closeLayer();
			});
		}else{
			art.dialog.data("bdczmxInfo", datas);
			AppUtil.closeLayer();
		}
	
    }
    
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdczmxGrid');  
		var options = grid.datagrid('getPager').data("pagination").options;
		var maxnum = options.pageNumber*options.pageSize;
		var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '12px, Microsoft YaHei');
        var width = currentObj.width();
		var panel = grid.datagrid('getPanel');
        if(width>25){
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
			grid.datagrid("resize");
		}else{			
			$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
			grid.datagrid("resize");
		}
	}
	$('#bdczmxGrid').datagrid({
		onLoadSuccess: fixRownumber
	});

	/**返回时间格式YYYY-MM-DD*/
	function DateConvertFun(str){
		var time = "";
		if(str){
			/*var result=str.match(/\d+/g);
            var year = result[0];

            var day = result[2];
            time = year +"-";
            if(result[1]){
                var month = result[1];
                if(parseInt(month) > 9){
                    time = time + month +"-";
                }else {
                    time = time + "0"+ month + "-";
                }
            }
            if(result[2]){
                var day = result[2];
                if(parseInt(day) > 9){
                    time = time + day;
                }else {
                    time = time + "0"+ day;
                }
            }*/
			time=str;
		}
		return time;
	}
	function bdczmxSearch(){
	    var validateResult =$("#bdczmxForm").validationEngine("validate");
	    if(validateResult){
		    var count = 0;
	        AppUtil.gridDoSearch('bdczmxToolbar','bdczmxGrid');
	        $('#bdczmxGrid').datagrid({
	            onLoadSuccess:function(){
	                //确保初始化后只执行一次
	                if(count == 0){
	                    var rows = $("#bdczmxGrid").datagrid("getRows");
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
		
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <input type="hidden" name="allowCount" value="${allowCount}">
	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" >
			<div id="bdczmxToolbar">
				<form action="#" name="bdczmxForm" id="bdczmxForm">
					<!--====================开始编写隐藏域============== -->
					
					<!--====================结束编写隐藏域============== -->
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat:repeat;width:100%;border-collapse:collapse;">
						<c:if test="${isBank eq 'true'}">
	                    <tr>
	                       <td colspan="6" class="dddl-legend"><div class="eui-dddltit"></div></td>
	                    </tr>
	                    </c:if>
						<tr style="height:28px;">
							<td style="width:120px;text-align:right;">幢号：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:180px;"
								name="zh" /></td>
							<td style="width:120px;text-align:right;">项目名称：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="50" style="width:180px;"
								name="xmmc" /></td>
							<td style="width:120px;text-align:right;">不动产单元号：</td>
							<td style="width:135px;"><input class="eve-input" 
								type="text" maxlength="50" style="width:180px;" id="bdcdyh"
								name="bdcdyh" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="bdczmxSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('bdczmxForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
		bdczmxdaxxcxGrid" fitColumns="true" toolbar="#bdczmxToolbar"
				method="post" idField="YWH" checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqscdjController.do?bdczmxDatagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'ZDMJ',align:'left',hidden:true" width="100">宗地面积</th>
						<th data-options="field:'SYQMJ',align:'left',hidden:true" width="100">使用权面积</th>
						<th data-options="field:'TD_SZ_D',align:'left',hidden:true" width="100">土地四至_东</th>
						<th data-options="field:'TD_SZ_N',align:'left',hidden:true" width="100">土地四至_南</th>
						<th data-options="field:'TD_SZ_X',align:'left',hidden:true" width="100">土地四至_西</th>
						<th data-options="field:'TD_SZ_B',align:'left',hidden:true" width="100">土地四至_北</th>
						<th data-options="field:'ZH',align:'left'" width="100">幢号</th>
						<th data-options="field:'ZCS',align:'left'" width="100">总层数</th>
						<th data-options="field:'111',align:'left'" width="100">幢占地面积</th>
						<th data-options="field:'111',align:'left'" width="100">用地面积</th>
						<th data-options="field:'111',align:'left'" width="100">总建筑面积</th>
						<th data-options="field:'111',align:'left'" width="100">总套数</th>
						<th data-options="field:'XMMC',align:'left'" width="100">项目名称</th>
						<th data-options="field:'BDCDYH',align:'left'" width="100">不动产单元号</th>
						<th data-options="field:'FWXZ',align:'left'" width="100">房屋性质</th>
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

