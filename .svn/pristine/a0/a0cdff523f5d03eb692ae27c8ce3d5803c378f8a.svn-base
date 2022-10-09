<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2"></eve:resources>
<script type="text/javascript" src="plug-in/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript">
var myChart;

$(document).ready(function() {
            var start1 = {
                elem : "#Sjattj.CREATE_TIME_BEGIN",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
                            .val();
                    var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
                            .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                                .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                                .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='Q_T.CREATE_TIME_>=']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#Sjattj.CREATE_TIME_END",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='Q_T.CREATE_TIME_>=']")
                            .val();
                    var endTime = $("input[name='Q_T.CREATE_TIME_<=']")
                            .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                                .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                                .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='Q_T.CREATE_TIME_<=']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
            
            
         // 路径配置
            require.config({
                paths: {
                    echarts: "plug-in/echarts-2.2.7/build/dist"
                }
            });
            // 使用
            require(
                [
                    "echarts",
                    "echarts/chart/bar", // 使用柱状图就加载bar模块，按需加载
                    "echarts/chart/pie"
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    myChart = ec.init(document.getElementById('main')); 
                    var tjsj = [];
                    var tjs =[];
                    var o = getOptionBar(tjsj,tjs,0);
                    // 填入数据
                    myChart.setOption(o);
                    searchData();//初始化
                }
            );
            
        });
 
 function searchData(){
	 myChart.showLoading();
	 var url = "statisticalReportController.do?pyjgtjData&Q_T.CREATE_TIME_LE="+$("input[name='Q_T.CREATE_TIME_<=']").val()+" 23:59:59&Q_T.CREATE_TIME_GE="+$("input[name='Q_T.CREATE_TIME_>=']").val()+" 00:00:00&echartType="+$("#echartType").val();
	 $.post(url,{
	 }, function(responseText) {
		 var resultJson = $.parseJSON(responseText);
         myChart.hideLoading();
         myChart.clear();
         var o ;
         if(resultJson.echartType=="1"){
           o = getOptionBar(resultJson.name,resultJson.tjs,resultJson.sumtjs);
         }else if(resultJson.echartType=="2"){
           o = getOptionPie(resultJson.name,resultJson.tjs,resultJson.sumtjs);
         }
         // 填入数据
         myChart.setOption(o);
         
     });
 }
 
 function getOptionBar(xAxisData,seriesData,sumtjs){
	 var option = {
             title: {
                 text: "评议结果统计(总数为:"+sumtjs+")",
                 x:'center'
             },
             tooltip : {
                 trigger: "axis"
             },
             xAxis : [
                  {
                  type : "category",
                  name:"（名称）",
                  axisLabel:{
                      interval : 0,
                      rotate :30
                  },
                    data: xAxisData
                 }
             ],
             yAxis : [{
                 type : "value",
                 name:"（数量）"
             }],
             series: [{
                 name: "数量",
                 type: "bar",
                 itemStyle: {
                     normal: {
                         label: {
                             show: true,
                             textStyle: {
                                 color: '#800080'
                             }
                         }
                     }
                 },
                 data: seriesData
                 
             }]
          };
	 return option;
 }
 function getOptionPie(xAxisData,seriesData,sumtjs){
	 var option = {
			    title : {
			        text: "评议结果统计(总数为:"+sumtjs+")",
			        x:'center'
			    },
			    tooltip : {
			        trigger: "item",
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : "vertical",
			        x : "left",
			        data:xAxisData
			    },
			    series : [
			        {
			            name:"数量",
			            type:"pie",
			            radius : "55%",
			            center: ["50%", "60%"],
			            data:seriesData
			        }
			    ]
			};
	 return option;
 }
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="pyjgtjStatisToolBar">
			<form action="#" name="pyjgtjForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Sjattj.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Sjattj.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>
							<td style="width:5px;"></td>
							<td style="width:105px;">
							     <select name="echartType" id="echartType">
									  <option value ="1" selected="selected">柱状图</option>
									  <option value ="2">饼状图</option>
								</select>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="searchData();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('pyjgtjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div style="width:100%;height: 450px;" id="main">
		
		</div>
	</div>
</div>