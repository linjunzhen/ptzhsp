<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript">
var myChart;
var myChart2;
var newEnterpriseDates = new Array();
var totalInvestmentDates = new Array();
var month = new Array();
var date=new Date;
var thisYear=date.getFullYear();
var lastYear=date.getFullYear()-1;
$(function(){
	
});
$(document).ready(function() { 
            
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
					myChart2  = ec.init(document.getElementById('main2'));                   
                    searchData();//初始化
                }
            );
            
        });
 
 function searchData(){
    // 填入数据
	$.ajax({
		url:"statistCommercialController.do?datagrid",
		data:{},
		dataType:"json",
		type:"post",
		success:function(data){
			for	(var i = 0 ;i< data.rows.length ;i++){
				newEnterpriseDates[data.rows[i].STATIST_TYPE]=data.rows[i].MONTH_NUM;
			}
			myChart.setOption(getOptionPie());
		},
		error:function(data){}
	})
	$.ajax({
		url:"statistCommercialController.do?getTotalInvestment",
		data:{},
		dataType:"json",
		type:"post",
		success:function(data){
			for	(var i = 0 ;i< data.rows.length ;i++){
				totalInvestmentDates[data.rows[i].MONTH]=data.rows[i].TOTAL_INVESTMENT;
			}
			myChart2.setOption(getOptionBar());
		},
		error:function(data){}
	})
 }
 
 function getOptionBar(){
	 var option = {
		title : {
			x:'center',
			text: '全年新增企业投资金额同比报表'
		},
		tooltip : {
			trigger: 'axis'
		},
		legend: {
			y:'bottom',
			data:[
				'本年投资金额(万)','上年投资金额(万)'
			]
		},
		calculable : true,
		grid: {y:70,x2:20,x:120},
		xAxis : [
			{
				type : 'category',
				data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			},
			{
				type : 'category',
				axisLine: {show:false},
				axisTick: {show:false},
				axisLabel: {show:false},
				splitArea: {show:false},
				splitLine: {show:false},
				data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'	]
			}
		],
		yAxis : [
			{
				type : 'value',
				axisLabel:{formatter:'{value} 万',margin:1}
			}
		],
		series : [
			{
				name:'本年投资金额(万)',
				type:'bar',
				itemStyle: {normal: {color:'rgba(193,35,43,1)', label:{show:true}}},
<%--				data:[totalInvestmentDates[thisYear+'-01'],155,95,75,222,115,55, 52,135,24,75, 222]--%>
				data:[
						totalInvestmentDates[thisYear+'-01'],
						totalInvestmentDates[thisYear+'-02'],
						totalInvestmentDates[thisYear+'-03'],
						totalInvestmentDates[thisYear+'-04'],
						totalInvestmentDates[thisYear+'-05'],
						totalInvestmentDates[thisYear+'-06'],
						totalInvestmentDates[thisYear+'-07'],
						totalInvestmentDates[thisYear+'-08'],
						totalInvestmentDates[thisYear+'-09'],
						totalInvestmentDates[thisYear+'-10'],
						totalInvestmentDates[thisYear+'-11'],
						totalInvestmentDates[thisYear+'-12']
				      ]
			},
			{
				name:'上年投资金额(万)',
				type:'bar',
				itemStyle: {normal: {color:'rgba(181,195,52,1)',label:{show:true,textStyle:{color:'#27727B'}}}},
<%--				data:[100,200,105,130,156,223,105,320,156,300,125,170]--%>
				data:[
						totalInvestmentDates[lastYear+'-01'],
						totalInvestmentDates[lastYear+'-02'],
						totalInvestmentDates[lastYear+'-03'],
						totalInvestmentDates[lastYear+'-04'],
						totalInvestmentDates[lastYear+'-05'],
						totalInvestmentDates[lastYear+'-06'],
						totalInvestmentDates[lastYear+'-07'],
						totalInvestmentDates[lastYear+'-08'],
						totalInvestmentDates[lastYear+'-09'],
						totalInvestmentDates[lastYear+'-10'],
						totalInvestmentDates[lastYear+'-11'],
						totalInvestmentDates[lastYear+'-12']
				      ]
			}
		]
	}
	 return option;
 }
 /**
  * 格式化
  */
 function formatState(val,row){
 	if(val=="1"){
 		return "内资";
 	}else if(val=="2"){
 		return "外资";
 	}else if(val=="3"){
 		return "个体";
 	}else if(val=="4"){
 		return "个独";
 	}else if(val=="5"){
 		return "总数";
 	}
 }
 function getOptionPie(){
	 var thisMothTotal = "0";
	 if(newEnterpriseDates[5]!=undefined&&newEnterpriseDates[5]!=null&&newEnterpriseDates[5]!=""){
		 thisMothTotal=newEnterpriseDates[5];
	 }
	 var option = {
			    title : {
			        text: "当月新增企业总数"+thisMothTotal,
			        x:'center'
			    },
			    tooltip : {
			        trigger: "item",
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : "vertical",
			        x : "left",
			        data:["内资","外资","个体","个独"]
			    },
			    series : [
			        {
			            name:"数量",
			            type:"pie",
			            radius : "55%",
			            center: ["50%", "60%"],
			            data:[
							{"name":"内资","value":newEnterpriseDates[1]},
							{"name":"外资","value":newEnterpriseDates[2]},
							{"name":"个体","value":newEnterpriseDates[3]},
							{"name":"个独","value":newEnterpriseDates[4]}
						]
			        }
			    ]
			};
	 return option;
 }
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<div>	
			<div style="width:50%;height: 250px;float:right;" id="main1">
				<table class="easyui-datagrid" rownumbers="true" pagination="false"
					id="stagetotalGrid" fitcolumns="false" 
					method="post" idfield="LOG_ID" checkonselect="false"
					selectoncheck="false" fit="true" border="false"
					url="statistCommercialController.do?datagrid">
					<thead>
						<tr>
							<th data-options="field:'STATIST_TYPE',align:'left'" width="50" formatter="formatState">类型</th>
							<th data-options="field:'MONTH_NUM',align:'center'" width="115" >当月总量</th>
							<th data-options="field:'LAST_MONTH_NUM',align:'center'" width="115" >上月总量</th>
							<th data-options="field:'ADD_NUM',align:'center'" width="115" >环比增量</th>
							<th data-options="field:'ADD_RATIO',align:'center'" width="115" >环比率</th>
						</tr>
					</thead>
				</table>
			</div>
			<div style="width:50%;height: 250px;" id="main">
			
			</div>		
		</div>
		<br/>
		<br/>
		<div style="width:98%;height: 450px;" id="main2">
		
		</div>
	</div>
</div>