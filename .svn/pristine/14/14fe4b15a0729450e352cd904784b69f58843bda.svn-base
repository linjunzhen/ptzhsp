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
    	var rows = $("#bdcdaxxcxGrid").datagrid("getChecked");
		var allowCount = $("input[name='allowCount']").val();
		if((rows.length>allowCount)&&allowCount!=0){
			alert("最多只能选择"+allowCount+"条记录!");
			return;
		}
		var datas = [];
		var ydydyh = "";
		var unaccept = false;
		for(var i = 0;i<rows.length;i++){			
			var cqzt = rows[i].CQZT;
			if(Trim(cqzt).indexOf("查封")>-1 || Trim(cqzt).indexOf("预查封")>-1 ||Trim(cqzt).indexOf("限制")>-1 || Trim(cqzt).indexOf("无效")>-1
					|| Trim(cqzt).indexOf("查封办文中")>-1 || Trim(cqzt).indexOf("裁定过户")>-1){
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
		//统计不动产单元号对应的抵押顺位
		for(var i = 0;i<datas.length;i++){
			var dysw = getDyswByDyh(datas[i].BDCDYH);
			//alert("单元号："+datas[i].BDCDYH+"-抵押顺位为："+dysw);
			datas[i].DYSW = dysw;
		}
		
		if(ydydyh.length>0){
			parent.art.dialog.confirm("不动产单元号"+ydydyh+"已抵押!是否继续办理?",function() {
				art.dialog.data("bdcdaxxcxInfo", datas);
				AppUtil.closeLayer();
			});
		}else{
			art.dialog.data("bdcdaxxcxInfo", datas);
			AppUtil.closeLayer();
		}
	
    }
    
    
    //根据不动产单元号统计抵押顺位（查询不动产抵押档案查询接口、权属状态为现势的数据+1）
    function getDyswByDyh(bdcdyh){
        var dysw = 1;
    	$.ajax({
	         type: "POST",
		     url: "bdcDyqscdjController.do?datagrid&bdcdyh="+bdcdyh,
		     async: false, //采用同步方式进行数据判断
		     success: function (responseText) {
		    	var resultJson = responseText;
				if(resultJson.total>0){	
					var rows = resultJson.rows;
					for(var i=0;i<rows.length;i++){
					   if(Trim(rows[i].QSZT)=="现势"){
					   		dysw +=1;
					   }
					}	
				}
		     }
		 });
	   return dysw;
    }
    
	function Trim(str)
	 { 
	  return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	/*序号列宽度自适应-----开始-----*/
	function fixRownumber(){
		var grid = $('#bdcdaxxcxGrid');  
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
	$('#bdcdaxxcxGrid').datagrid({
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
	function bdcdaxxcxSearch(){
	    $('#bdcdaxxcxGrid').datagrid('clearChecked')=='none';
	    var validateResult =$("#bdcdaxxcxForm").validationEngine("validate");
	    if(validateResult){
		    var count = 0;
	        AppUtil.gridDoSearch('bdcdaxxcxToolbar','bdcdaxxcxGrid');
	        $('#bdcdaxxcxGrid').datagrid({
	            onLoadSuccess:function(){
	                //确保初始化后只执行一次
	                if(count == 0){
	                    var rows = $("#bdcdaxxcxGrid").datagrid("getRows");
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
			<div id="bdcdaxxcxToolbar">
				<form action="#" name="bdcdaxxcxForm" id="bdcdaxxcxForm">
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
							<td style="width:120px;text-align:right;">不动产单元号：</td>
							<td style="width:300px;"><input class="eve-input" 
								type="text" maxlength="50" style="width:300px;" id="bdcdyh"
								name="bdcdyh" /></td>
							<td style="width:120px;text-align:right;"><c:if test="${isBank eq 'true'}"><font class="tab_color ">*</font></c:if>不动产权证号：</td>
							<td style="width:300px;"><input class="eve-input validate[${required}]"
								type="text" maxlength="50" style="width:300px;" id="bdcqzh"
								name="bdcqzh" /></td>
							<td style="width:120px;text-align:right;"><c:if test="${isBank eq 'true'}"><font class="tab_color ">*</font></c:if>权利人：</td>
							<td style="width:300px;"><input class="eve-input validate[${required}]"
								type="text" maxlength="50" style="width:300px;" id="qlrmc"
								name="qlrmc" /></td>
						</tr>
						<tr>
							<td style="width:120px;text-align:right;"><c:if test="${isBank eq 'true'}"><font class="tab_color ">*</font></c:if>证件号码：</td>
							<td style="width:300px;"><input class="eve-input validate[${required}]"
								type="text" maxlength="50" style="width:300px;" id="zjhm"
								name="zjhm" /></td>
							<td style="width:120px;text-align:right;">房屋编码：</td>
							<td style="width:300px;"><input class="eve-input"
								type="text" maxlength="50" style="width:300px;"
								name="fwbm" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="bdcdaxxcxSearch()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('bdcdaxxcxForm');" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="bdcdaxxcxGrid" fitColumns="true" toolbar="#bdcdaxxcxToolbar"
				method="post"  checkOnSelect="false"
				selectOnCheck="false" fit="true" border="false" nowrap="false"
				url="bdcDyqscdjController.do?bdcdaxxcxDatagrid&noAuth=${noAuth}">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'YWH',hidden:true" width="80">业务号</th>
						<th data-options="field:'CQZT',align:'left'" width="80">产权状态</th>
						<th data-options="field:'ZH',align:'left'" width="50">幢号</th>
						<th data-options="field:'HH',align:'left'" width="50">户号</th>
						<th data-options="field:'BDCQZH',hidden:true" width="100">不动产权证号</th>
						<th data-options="field:'BDCDYH',align:'left'" width="100">不动产单元号</th>
						<th data-options="field:'QLRMC',align:'left'" width="100">权利人</th>
						<th data-options="field:'QSZT',align:'left'" width="80">权属状态</th>
						<th data-options="field:'FWBM',align:'left'" width="100">房屋编码</th>


						<th data-options="field:'ZJLX',align:'left',hidden:true" width="100">证件类型</th>
						<th data-options="field:'ZJHM',align:'left',hidden:true" width="100">证件号码</th>
						<th data-options="field:'QLRXZ',align:'left',hidden:true" width="100">权利人性质</th>
						<th data-options="field:'YWH',align:'left',hidden:true" width="100">业务号</th>
						<th data-options="field:'GYFS',align:'left',hidden:true" width="100">共有方式</th>
						<th data-options="field:'QLQSSJ',align:'left',hidden:true" width="100">权利起始时间</th>
						<th data-options="field:'QLJSSJ',align:'left',hidden:true" width="100">权利结束时间</th>
						<th data-options="field:'QLJSSJ1',align:'left',hidden:true" width="100">权利结束时间1</th>
						<th data-options="field:'DBR',align:'left',hidden:true" width="100">登簿人</th>
						<th data-options="field:'DBSJ',align:'left',hidden:true" width="100">登簿时间</th>
						<th data-options="field:'ZDDM',align:'left',hidden:true" width="100">宗地代码</th>
						<th data-options="field:'ZDQLLX',align:'left',hidden:true" width="100">宗地_权利类型</th>
						<th data-options="field:'ZDTZM',align:'left',hidden:true" width="100">宗地特征码</th>
						<th data-options="field:'QLSDFS',align:'left',hidden:true" width="100">权利设定方式</th>
						<th data-options="field:'QLXZ',align:'left',hidden:true" width="100">权利性质</th>
						<th data-options="field:'GLTDZH',align:'left',hidden:true" width="100">关联土地证号</th>
						<th data-options="field:'XZQ',align:'left',hidden:true" width="100">行政区</th>
						<th data-options="field:'DJQ',align:'left',hidden:true" width="100">地籍区</th>
						<th data-options="field:'DJZQ',align:'left',hidden:true" width="100">地籍子区</th>
						<th data-options="field:'TDZL',align:'left',hidden:true" width="100">土地坐落</th>
						<th data-options="field:'TDYT',align:'left',hidden:true" width="100">土地用途</th>
						<th data-options="field:'TDYTSM',align:'left',hidden:true" width="100">土地用途说明</th>
						<th data-options="field:'ZDMJ',align:'left',hidden:true" width="100">宗地面积</th>
						<th data-options="field:'SYQMJ',align:'left',hidden:true" width="100">使用权面积</th>
						<th data-options="field:'TD_SZ_D',align:'left',hidden:true" width="100">土地四至_东</th>
						<th data-options="field:'TD_SZ_N',align:'left',hidden:true" width="100">土地四至_南</th>
						<th data-options="field:'TD_SZ_X',align:'left',hidden:true" width="100">土地四至_西</th>
						<th data-options="field:'TD_SZ_B',align:'left',hidden:true" width="100">土地四至_北</th>
						<th data-options="field:'BZ',align:'left',hidden:true" width="100">备注</th>
						<th data-options="field:'JG',align:'left',hidden:true" width="100">土地价格</th>
						<th data-options="field:'DJ',align:'left',hidden:true" width="100">土地等级</th>
						<th data-options="field:'RJL',align:'left',hidden:true" width="100">容积率</th>
						<th data-options="field:'JZXG',align:'left',hidden:true" width="100">建筑限高（米）</th>
						<th data-options="field:'JZMD',align:'left',hidden:true" width="100">建筑密度</th>
						<th data-options="field:'FW_QLLX',align:'left',hidden:true" width="100">房屋_权利类型</th>
						<th data-options="field:'FDZL',align:'left',hidden:true" width="100">房地坐落</th>

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

