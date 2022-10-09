<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<eve:resources loadres="jquery,easyui,artdialog,layer,laydate,apputil,ztree,json2,"></eve:resources>
<link href='plug-in/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet' />
<script src='plug-in/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='plug-in/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script>

    $(document).ready(function() {
        
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month'
            },
             monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
             monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
             dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
             dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
             today: ["今天"],
             firstDay: 1,
             buttonText: {
                 today: '今天',
                 month: '月',
                 prev: '上一月',
                 next: '下一月'
             },
            editable: false,
            weekMode:'liquid',
            aspectRatio:'2.68',
            viewRender : function (view) {//动态把数据查出，按照月份动态查询
                 var viewStart = moment(view.start).format("YYYY-MM-DD");
                 var viewEnd = moment(view.end).format("YYYY-MM-DD");
                 $("#calendar").fullCalendar('removeEvents');
                 var layload = layer.load('正在加载中…');
                $.post("workdayController.do?findData", { 'Q_T.W_DATE_>=': viewStart, 'Q_T.W_DATE_<=': viewEnd }, function (data) {
                     var resultCollection = jQuery.parseJSON(data);
                     $.each(resultCollection, function (index, term) {
                         $("#calendar").fullCalendar('renderEvent', term, true);
                     });
                     layer.close(layload);
                 }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
             },
            eventClick: function (event) {
            //alert(moment(event.start).format("YYYY-MM-DD"));
            //alert(event.W_ID);
            	$.dialog.open("workdayController.do?info&entityId=" + event.W_ID, {
                    title : "日期信息",
                    width: "400px",
                    height: "300px",
                    fixed: true,
                    lock : true,
                    resize : false,
                    close: function () {
                    	var WorkdayInfo = art.dialog.data("WorkdayInfo");
                    	if(WorkdayInfo&&WorkdayInfo.wsetId){
                    		if(WorkdayInfo.wsetId=="1"){
                                event.color = "#FF0000";
                    			event.title = "休息日";
                    		}else if(WorkdayInfo.wsetId=="2"){
                    			event.color = "#3a87ad";
                    			event.title = "工作日";
                    		}
                        $("#calendar").fullCalendar('updateEvent', event);
                        art.dialog.removeData("WorkdayInfo");
                    	}
                    }
                }, false);
            }
        });
        
    });

</script>
<style type='text/css'>
    #calendar
    {
        width: 1100px;
        margin: 0 auto;
    }
    #div1 {  
        margin-top: 10px;  
        text-align: center;  
        font-size: 16px;  
        font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;  
        }  
 </style>
<div style="padding-top: 20px;" id="div1">
	<div id='calendar'></div>
</div>
