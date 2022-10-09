<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
      href="plug-in/easyui-1.4/extension/jquery-easyui-portal/portal.css" />
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="webpage/main/js/center.js"></script>



<script>

    loadSysNoticeDatasa();

    //获取系统公告数据
    function loadSysNoticeDatasa(){
        $.post("sysNoticeController.do?indexView&start=0&limit=10",null,
            function(resultJson, status, xhr) {
                var newHtmlContent = "";
                if(resultJson.total!=0){
                    var rows = resultJson.rows;
                    for(var index in rows){
                        var noticeInfo = rows[index];
                        var noticeId = noticeInfo.NOTICE_ID;
                        var exeId = noticeInfo.EXE_ID;
                        var subject = noticeInfo.NOTICE_TITLE;
                        var createTime = noticeInfo.CREATE_TIME;
                        var workDayCount = noticeInfo.LEFT_WORKDAY;
                        var li = "<li style=\"border-bottom: 1px solid #a3b0bd\">";
                        li+=("<span>"+createTime+"</span>");
                        li+="<a href=\"javascript:void(0);\" onclick=\"showSysNoticeDetailWindow(\'"+noticeId+"\');\"";
                        li+=(" >"+subject+"</a></li>");
                        newHtmlContent+=li;
                    }
                    $("#announcementList").html(newHtmlContent)
                }
            });

    }




</script>

<div style="width:100%;" id="systemContent">
    <div class="main_con" style="margin-top: 10px;">
        <div class="main_list1">
            <ul id="announcementList" class="main_ul1">
            </ul>
        </div>
    </div>
</div>

