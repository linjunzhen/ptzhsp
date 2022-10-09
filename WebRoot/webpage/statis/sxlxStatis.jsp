<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil"></eve:resources>
<script type="text/javascript" src="plug-in/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript">
var myChart;

$(document).ready(function() {
            var start1 = {
                elem : "#Sjattj_BEGIN_TIME",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='BEGIN_TIME']")
                            .val();
                    var endTime = $("input[name='END_TIME']")
                            .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                                .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                                .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='BEGIN_TIME']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#Sjattj_END_TIME",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='BEGIN_TIME']")
                            .val();
                    var endTime = $("input[name='END_TIME']")
                            .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                                .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                                .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='END_TIME']").val("");
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
                    var LX_NAME = [];
                    var SXLX_COUNT =[];
                    var o = getOptionBar(SXLX_COUNT,LX_NAME,0);
                    // 填入数据
                    myChart.setOption(o);
                    searchData();//初始化
                }
            );
            
        });
 function goSearchData(){
	 searchData();
 }
 
 function searchData(){
	 myChart.showLoading();
	 var beginTime = $("#Sjattj_BEGIN_TIME").val();
	 if(beginTime){
		 $("input[name='Q_T.CREATE_TIME_>=']").val(beginTime+" 00:00:00");
	 }else{
		 $("input[name='Q_T.CREATE_TIME_>=']").val("");
	 }
	 var endTime = $("#Sjattj_END_TIME").val();
	 if(endTime){
		 $("input[name='Q_T.CREATE_TIME_<=']").val(endTime+" 23:59:59");
	 }else{
		 $("input[name='Q_T.CREATE_TIME_<=']").val("");
	 }
	 var formData = $("form[name='sjattjForm']").serialize();
	 $.post("statisticsController.do?sxlxData",formData, function(responseText) {
		 var resultJson = $.parseJSON(responseText);
         myChart.hideLoading();
         myChart.clear();
         var o ;
         if(resultJson.echartType=="1"){
           o = getOptionBar(resultJson.LX_NAME,resultJson.SXLX_COUNT,resultJson.TOTAL_COUNT);
         }else if(resultJson.echartType=="2"){
           o = getOptionPie(resultJson.LX_NAME,resultJson.nameAndValueList,resultJson.TOTAL_COUNT);
         }
         // 填入数据
         myChart.setOption(o);
         
     });
 }
 
 function getOptionBar(xAxisData,seriesData,TOTAL_COUNT){
	 var option = {
             title: {
                 text: "时限类型统计(总数为:"+TOTAL_COUNT+")",
                 x:'center'
             },
             tooltip : {
                 trigger: "axis"
             },
             xAxis : [
                  {
                  type : "category",
                  name:"（时限类型）",
                  axisLabel:{
                      interval : 0,
                      rotate :30
                  },
                    data: xAxisData
                 }
             ],
             yAxis : [{
                 type : "value",
                 name:"（收件数）"
             }],
             series: [{
                 name: "收件数",
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
 function getOptionPie(xAxisData,seriesData,TOTAL_COUNT){
	 var option = {
			    title : {
			    	text: "时限类型统计(总数为:"+TOTAL_COUNT+")",
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
			            name:"收件数",
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
		<div id="bjtjbStatisToolBar">
			<form action="#" name="sjattjForm">
			    <input type="hidden" name="Q_T.CREATE_TIME_>=">
			    <input type="hidden" name="Q_T.CREATE_TIME_<=">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">开始日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Sjattj_BEGIN_TIME" name="BEGIN_TIME" /></td>
							<td style="width:68px;text-align:right;">结束日期</td>
							<td style="width:135px;"><input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="Sjattj_END_TIME" name="END_TIME"  /></td>
							<td style="width:5px;"></td>
							<td style="width:105px;">
							     <select name="echartType">
									  <option value ="1" selected="selected">柱状图</option>
									  <option value ="2">饼状图</option>
								</select>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="goSearchData();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('sjattjForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div style="width:100%;height: 450px;" id="main">
		
		</div>
	</div>
</div>