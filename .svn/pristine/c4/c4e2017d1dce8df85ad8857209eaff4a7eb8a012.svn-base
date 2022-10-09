
function bjlist(itemList){
    var newhtml = "";
    var artFlag="0";
    for(var i=0; i<itemList.length; i++){
        newhtml +="<tr>";
        newhtml += "<td >"+(i+1)+"</td>";
        newhtml += "<td  style=\"text-align:center;\">"+itemList[i].EXE_ID+"</td>";
        newhtml += "<td >"+itemList[i].SUBJECT+"</td>";
        newhtml += "<td  style=\"text-align:center;\" >"+itemList[i].CREATE_TIME.substr(0,10)+"</td>";
        var isneedSign1 = itemList[i].ISNEEDSIGN;
        var zt =itemList[i].runStatusStr;
        if(isneedSign1=="1"&&zt.indexOf("窗口办理")>0){
            zt=zt.replace("窗口办理","执照审核");
        }
        // getZt(itemList[i]);//getZt(itemList[i].RUN_STATUS);
        newhtml += "<td  style=\" text-align: center;\">"+zt;
        newhtml +="</td>";
        newhtml += "<td align='center' id=\"list"+i+"\">";
        if(itemList[i].RUN_STATUS==1){//正在办理
            if(itemList[i].TASK_STATUS=='1'){//正常流程流转待办任务===OK
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  >办理</a>";
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  >查看回执</a>";
                if(itemList[i].BACKOPINION){
                    newhtml += "<a href=\"javascript:void(0);\"  onclick=\"thsm('"+itemList[i].BJXX_ID
                        +"','"+encodeURIComponent(itemList[i].BACKOPINION)+"','"+itemList[i].APPLY_STATUS+"')\"  >退回说明</a>";
                }
                newhtml +="</td>";
            }else if(itemList[i].TASK_STATUS=='-1'){//退回补件-待办任务==OK
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  >办理</a>";
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"thsm('"+itemList[i].BJXX_ID
                    +"','"+encodeURIComponent(itemList[i].BACKOPINION)+"','"+itemList[i].APPLY_STATUS+"')\"  >退回说明</a>";
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  >查看回执</a></td>";
            }else if(itemList[i].preAuditState=='1'){//预审待审核，详情、撤办；===OK
                newhtml += "<a href=\"javascript:void(0);\" onclick=\"revokeFlow('"
                    +itemList[i].EXE_ID+"');\" >撤回</a>";
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\"  target=\"_blank\">详情</a>";
            }else if(itemList[i].APPLY_STATUS=='7'){//退件待办===OK
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  >办理</a>";
                newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckbjyj('"
                    +encodeURIComponent(itemList[i].BACKOPINION)+"');\" >退件意见</a>";
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\"  target=\"_blank\">详情</a>";
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  >查看回执</a></td>";
            }else{//正在办理===OK
                newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\"  target=\"_blank\" >详情</a> ";
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"
                    +itemList[i].EXE_ID+"')\"  >查看回执</a>";
                var CUR_STEPNAMES = itemList[i].CUR_STEPNAMES;
                if(CUR_STEPNAMES == '窗口办理'){
                    newhtml += "<a href=\"javascript:void(0);\"  onclick=\"clxz('"+itemList[i].EXE_ID+"','"+
                        itemList[i].ITEM_CODE+"');\"  >材料下载</a>";
                }
                var ITEM_CODE = itemList[i].ITEM_CODE;
                if(ITEM_CODE.indexOf("201605170002XK001")>-1){
                    //					 newhtml += "<a href=\"javascript:void(0);\"  onclick=\"sqcb('"+itemList[i].EXE_ID+"','"+
                    //					itemList[i].ITEM_CODE+"');\"  >申请撤办</a>";
                    newhtml += "<a href=\"javascript:void(0);\"  onclick=\"showOpinion('"
                        +itemList[i].EXE_ID+"')\"  >审核意见</a>";
                }
                var ISNEEDSIGN = itemList[i].ISNEEDSIGN;
                //面签信息展示
                if(CUR_STEPNAMES == '身份认证'&&ISNEEDSIGN=='1'){
                    newhtml += "<a href=\"javascript:void(0);\"  onclick=\"signInfo('"+itemList[i].EXE_ID+"','"+
                        itemList[i].ITEM_CODE+"');\"  >面签信息</a>";
                    //身份认证提醒
                    if(artFlag=="0"){
                        artFlag="1";
                        parent.art.dialog({
                            content: "您有待身份认证办件，请点击待身份认证办件面签信息查看详情。",
                            icon: "succeed",
                            time: 300,
                            ok: function () {
                                //AppUtil.closeLayer();
                            }
                        })
                    }
                }
                var ISFACESIGN = itemList[i].ISFACESIGN;
                if(CUR_STEPNAMES == '身份认证'&&ISNEEDSIGN=='1'&&ISFACESIGN!='1'){
                    newhtml += "<a href=\"javascript:void(0);\"  onclick=\"uploadCompanyFile('"+itemList[i].EXE_ID+"','"+
                        itemList[i].ITEM_CODE+"');\"  >上传公章</a>";
                }
                newhtml += "</td>";
            }
        }else if(itemList[i].RUN_STATUS==0){//草稿===PRE--OK
            newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                +"\"  target=\"_blank\">编辑</a>";
            newhtml += "<a href=\"javascript:void(0);\" onclick=\"deleteFlow('"
                +itemList[i].EXE_ID+"');\" >删除</a></td>";
        }else if(itemList[i].RUN_STATUS==7){//已办结(预审不通过)===OK
            newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckysyj('"
                +itemList[i].EXE_ID+"');\" >预审意见</a>";
            /*if(itemList[i].isPj!='1'){
                newhtml += "<a href=\"javascript:void(0);\"   onclick=\"bjpj('"+itemList[i].EXE_ID+"','"+
                itemList[i].ITEM_CODE+"');\">评价</a>";
            }else{
                newhtml += "<a href=\"javascript:void(0);\"   >已评价</a>";
            }*/
            newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                +"&isQueryDetail=true\"  target=\"_blank\">详情</a>";
            newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  >查看回执</a></td>";
        }else{//已办结===OK
            newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckbjyj('"
                +encodeURIComponent(itemList[i].FINAL_HANDLEOPINION)+"');\" >办结意见</a>";
            var ISGETBILL = itemList[i].ISGETBILL;
            if(itemList[i].ISGETBILL=='1'){
                newhtml += "<a href='https://etax.fujian.chinatax.gov.cn/xxmh/html/index_login.html'   target='_blank'>申领发票</a>";
            }
            /*if(itemList[i].isPj!='1'){
                newhtml += "<a href=\"javascript:void(0);\"   onclick=\"bjpj('"+itemList[i].EXE_ID+"','"+
                itemList[i].ITEM_CODE+"');\">评价</a>";
            }else{
                newhtml += "<a href=\"javascript:void(0);\"   >已评价</a>";
            }*/
            newhtml += "<a href=\"webSiteController.do?applyItemOfProject&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                +"&isQueryDetail=true\"  target=\"_blank\">详情</a>";
            newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  >查看回执</a>";

            var IS_OPEN = itemList[i].IS_OPEN;
            if(IS_OPEN==1){
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"ckjg('"+itemList[i].EXE_ID+"')\"  >查看结果</a>";
            }
            var ITEM_CODE = itemList[i].ITEM_CODE;
            if(ITEM_CODE.indexOf("201605170002XK001")>=0){
                newhtml += "<a href=\"javascript:void(0);\"  onclick=\"clxz('"+itemList[i].EXE_ID+"','"+
                    itemList[i].ITEM_CODE+"');\"  >材料下载</a>";
            }
            newhtml += "</td>";
        }
        newhtml +="</tr>";
    }
    return newhtml;
}

function revokeFlow(exeId){
    if (exeId) {
        art.dialog.confirm("您确定要撤回该流程吗?", function() {
            var layload = layer.load("正在提交请求中…");
            $.post("executionController.do?getBackMyApply",{
                exeId:exeId
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success){
                    art.dialog({
                        content : resultJson.msg,
                        icon : "succeed",
                        lock : true,
                        ok:function(){
                            reload();
                        },
                        close: function(){
                            reload();
                        }
                    });
                }else{
                    art.dialog({
                        content : resultJson.msg,
                        icon : "error",
                        ok : true
                    });
                }
            });
        });
    }
}
/**
 *  流程回退操作
 **/
function FLOW_Recover(exeId){
    if(exeId){
        $.post("executionController.do?getBackMyApply",{
            exeId:exeId
        }, function(responseText, status, xhr) {
            var resultJson = $.parseJSON(responseText);
            if(resultJson.success){
                art.dialog({
                    content : resultJson.msg,
                    icon : "succeed",
                    lock : true,
                    ok:function(){
                        reload();
                    },
                    close: function(){
                        reload();
                    }
                });
            }else{
                art.dialog({
                    content : resultJson.msg,
                    icon : "error",
                    ok : true
                });
            }
        });
    }
}

function deleteFlow(exeId){
    art.dialog.confirm("您确定要删除该记录吗?", function() {
        var layload = layer.load('正在提交请求中…');
        var selectColNames =exeId;
        $.post("executionController.do?multiDelDraft",{
            selectColNames:selectColNames
        }, function(responseText, status, xhr) {
            layer.close(layload);
            var resultJson = $.parseJSON(responseText);
            if(resultJson.success == true){
                art.dialog({
                    content: resultJson.msg,
                    icon:"succeed",
                    time:3,
                    ok: true
                });
                reload();
            }else{
                art.dialog({
                    content: resultJson.msg,
                    icon:"error",
                    ok: true
                });
            }
        });
    });
}
function getZt(itemList){
    var status=itemList.RUN_STATUS;
    var taskStatus=itemList.TASK_STATUS;
    var applyStatus=itemList.APPLY_STATUS;
    var preAuditState=itemList.preAuditState;
    var s = "";
    if(status=='0'){
        s="草稿";
    }else if(status=='1'){
        if(taskStatus=='1'||taskStatus=='-1'){
            s="<b style=\"color: #008000;\">待办</b>";
        }else if(applyStatus=='1'){
            s="<b style=\"color: #008000;\">预审中</b>";
        }else if(applyStatus=='2'&&itemList.preAuditState=='1'){
            s="<b style=\"color: #008000;\">预审通过</b>";
        }else{
            s="<b style=\"color: #008000;\">正在办理</b>";
        }
    }else if(status=='2'){
        s="<b style=\"color:blue\">已办结</b>";
    }else if(status=='3'){
        s="<b style=\"color: #008000;\">审核通过</b>";
    }else if(status=='4'){
        s="<b style=\"color: #008000;\">审核不通过</b>";
    }else if(status=='5'){
        s="<b style=\"color: #008000;\">退件办结</b>";
    }else if(status=='6'){
        s="<b style=\"color:blue\">已办结</b>";
    }else if(status=='7'){
        s="<b style=\"color:blue\">预审不通过</b>";
    }

    return s;
}
function showOpinion(exeId) {
    $.dialog.open("webSiteController/showOpinion.do?exeId="+exeId, {
        title: "审核信息",
        width: "900px",
        height: "400px",
        lock: true,
        resize: false
    }, false);
}
function thsm(bjid,backinfo,applyStatus){
    backinfo = decodeURIComponent(backinfo);
    if(bjid!=''&&backinfo!=''&&(applyStatus=='4'||applyStatus=='7')){
        $.dialog.open("webSiteController/thbjxx.do?&BJID="+bjid, {
            title : "退回补件信息",
            width : "900px",
            height : "400px",
            lock : true,
            resize : false
        }, false);
    }else if(bjid==''&&backinfo!=''&&applyStatus=='1'){
        art.dialog({
            title: '退回意见',
            content: backinfo,
            lock : true,
            width : "400px",
            ok: true
        });
    }else if(bjid!=''&&backinfo!=''&&applyStatus=='1'){
        art.dialog({
            title: '退回意见',
            content: backinfo,
            lock : true,
            width : "400px",
            ok: true
        });
    }else if(backinfo!=''){
        art.dialog({
            title: '退回意见',
            content: backinfo,
            lock : true,
            width : "400px",
            ok: true
        });
    }
}

function ckbjyj(con){
    con = decodeURIComponent(con)
    if(con=="undefined"){
        con="无";
    }
    art.dialog({
        title: '办结意见',
        content: con,
        lock : true,
        width : "400px",
        ok: true
    });
}
function bjpj(exeId,itemCode){
    $.dialog.open("bspjController/pjxx.do?exeId="+exeId+"&itemCode="+itemCode, {
        title : "评价信息",
        width : "600px",
        height : "200px",
        lock : true,
        resize : false,
        close: function () {
            reload();
        }
    }, false);
}
function ckhz(exeId){
    $.dialog.open("webSiteController/hzxx.do?&exeId="+exeId, {
        title : "查看回执信息",
        width : "800px",
        height : "400px",
        lock : true,
        resize : false
    }, false);
}
function ckjg(exeId){
    $.dialog.open("webSiteController/jgxx.do?&exeId="+exeId, {
        title : "查看结果信息",
        width : "800px",
        height : "500px",
        lock : true,
        resize : false
    }, false);
}
function clxz(exeId,itemCode){
    $.dialog.open("webSiteController/clxz.do?itemCode="+itemCode+"&exeId="+exeId, {
        title : "材料下载",
        width : "1000px",
        height : "400px",
        lock : true,
        resize : false
    }, false);
}
function uploadCompanyFile(exeId,itemCode){
    $.dialog.open("exeDataController/uploadCompanyFileView.do?itemCode="+itemCode+"&exeId="+exeId, {
        title : "上传公司公章",
        width : "1000px",
        height : "500px",
        lock : true,
        ok:function(){
            window.top.location.href=__newUserCenter;
        },
        close: function(){
            window.top.location.href=__newUserCenter;
        },
        resize : false
    }, false);
}
function sqcb(exeId,itemCode){
    $.dialog.open("userInfoController.do?sqcb&exeId="+exeId, {
        title : "申请撤办",
        width : "600px",
        height : "240px",
        lock : true,
        resize : false,
        close: function(){
            reload();
        }
    }, false);
}
function signInfo(exeId,itemCode){
    $.dialog.open("exeDataController/fontSignInfo.do?exeId="+exeId, {
        title : "面签信息",
        width : "80%",
        height : "800px",
        lock : true,
        resize : false,
        close: function(){
            reload();
        }
    }, false);
}


function ckysyj(exeId){
    $.dialog.open("webSiteController/ystjxx.do?&exeId="+exeId, {
        title : "预审退回信息",
        width : "900px",
        height : "400px",
        lock : true,
        resize : false
    }, false);
}





$("#pager").zPager({
    pageData:5,
    htmlBox: $('#wraper'),
    btnShow: true,
    ajaxSetData: false
});

function currentPage0(currentPage){
    $("input[name='methodNum']").val("0");
    var start=(currentPage-1)*5;
    var projectCode=$("input[name='PROJECT_CODE']").val();
    var projectName=$("input[name='PROJECT_NAME']").val();
    var projectType=$("select[name='PROJECT_TYPE']").val();
    var url= 'projectWebsiteController/findMyProjectList.do?start='+start+
        '&projectCode='+projectCode+'&projectName='+projectName+'&projectType='+projectType+'&limit=5';
    url=encodeURI(url);
    $.ajax({
        url: url,
        type: 'post',
        async: true,//此处必须是同步
        dataType: 'json',
        success: function (data) {
            var result=JSON.parse(data.jsonString);
            var total=result.total;
            $("input[name='totalData']").val(total)
            var rows=result.data;
            var html="<tr>" +
                "<th width=\"60\">序号</th>" +
                "<th width=\"20%\">项目编码</th>" +
                "<th align=\"left\">项目名称</th>" +
                "<th width=\"16%\">创建时间</th>" +
                "<th width=\"24%\">操作</th>" +
                "</tr>";
            for(var i=0; i<rows.length; i++){
                var projectCode=rows[i].PROJECT_CODE;
                var projectName=rows[i].PROJECT_NAME;
                var createTime=rows[i].CREATE_TIME;
                var id=rows[i].ID;
                //var projectDetailUrl="<%=path%>projectWebsiteController.do?projectInfoDetail&id="+id;
                var projectDetailUrl="<%=path%>projectWebsiteController/registerProject.do?id="+id;
                var projectApplyUrl="<%=path%>webSiteController.do?applyItemOfProject&itemCode=345071904XK03701&typeId=4028e3816b637dc3016b638d343d0024&stageId=2c90f0246d47627c016d47796a34004c";
                var j=i+1;
                html=html+"<tr>" +
                    "<td align=\"center\">"+j+"</td>" +
                    "<td align=\"center\">"+projectCode+"</td>" +
                    "<td>"+projectName+"</td>" +
                    "<td align=\"center\">"+createTime+"</td>" +
                    "<td align=\"center\">" +
                    "\t<a href=\""+projectDetailUrl+"\">项目信息</a>" +
                    "\t<a href=\""+projectApplyUrl+"\">项目申报</a>" +
                    "</td>" +
                    "</tr>";
            }
            $("#wraper").html(html);
        }
    });
    /*
        触发页码数位置： Page/js/jquery.z-pager.js 64行
    */
    console.log("当前页码数：" + currentPage);
}
function settotal0(){
    var projectCode=$("input[name='PROJECT_CODE']").val();
    var projectName=$("input[name='PROJECT_NAME']").val();
    var projectType=$("select[name='PROJECT_TYPE']").val();
    var url= 'projectWebsiteController/findMyProjectList.do?start=0'+
        '&projectCode='+projectCode+'&projectName='+projectName+'&projectType='+projectType+'&limit=5';
    url=encodeURI(url);
    $.ajax({
        url: url,
        type: 'post',
        async: false,//此处必须是同步
        dataType: 'json',
        success: function (data) {
            var result=JSON.parse(data.jsonString);
            var total=result.total;
            $("input[name='totalData']").val(total)
        }
    });
}



$("#pagerMyexecution").zPager({
    pageData:5,
    htmlBox: $('#wraperMyexecution'),
    btnShow: true,
    ajaxSetData: false
});

function currentPage1(currentPage){
    $("input[name='methodNum']").val("1");
    var start=(currentPage-1)*5;
    var exeId=$("input[name='EXE_ID']").val();
    var subject=$("input[name='SUBJECT']").val();
    var projectName=$("select[name='PROJECT_NAME']").val();
    var url= 'projectWebsiteController/findMyExecutionList.do?start='+start+
        '&exeId='+exeId+'&subject='+subject+'&projectName='+projectName+'&limit=5';
    url=encodeURI(url);
    $.ajax({
        url: url,
        type: 'post',
        async: true,//此处必须是同步
        dataType: 'json',
        success: function (data) {
            var result=JSON.parse(data.jsonString);
            var total=result.total;
            $("input[name='totalData']").val(total)
            var rows=result.data;
            var html="<tr>" +
                "<th width=\"60\">序号</th>" +
                "<th width=\"20%\">申报号</th>" +
                "<th align=\"center\">申报名称</th>" +
                "<th width=\"16%\">创建时间</th>" +
                "<th width=\"10%\">状态</th>" +
                "<th width=\"24%\">操作</th>" +
                "</tr>";
            html+=bjlist(rows);
            $("#wraperMyexecution").html(html);
        }
    });
    /*
        触发页码数位置： Page/js/jquery.z-pager.js 64行
    */
    console.log("当前页码数：" + currentPage);
}
function settotal1(){
    var exeId=$("input[name='EXE_ID']").val();
    var subject=$("input[name='SUBJECT']").val();
    var projectName=$("select[name='PROJECT_NAME']").val();
    var url= 'projectWebsiteController/findMyExecutionList.do?start=0'+
        '&exeId='+exeId+'&subject='+subject+'&projectName='+projectName+'&limit=5';
    url=encodeURI(url);
    $.ajax({
        url: url,
        type: 'post',
        async: false,//此处必须是同步
        dataType: 'json',
        success: function (data) {
            var result=JSON.parse(data.jsonString);
            var total=result.total;
            $("input[name='totalData']").val(total)
        }
    });
}
function resetTotal(pagerId) {
    $("#"+pagerId).zPager({

    });
}

function  restForm(searchFormName){
    $("#"+searchFormName)[0].reset();
}
