<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/eui.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.min.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/perfect-scrollbar.jquery.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/echarts.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/js/jquery.SuperSlide.2.1.3.js" ></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/layer-1.8.5/layer.min.js" ></script>
</head>
<script>

    $(function () {
        replaceFormData();
        queryData(1);

    });

    function dump(e) {
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            //回车执行查询
            var dumpPage = $("#dumpPage").val();
            var maxPage = $("#maxPage").val();
            if (dumpPage && maxPage) {
                dumpPage = parseInt(dumpPage);
                maxPage = parseInt(maxPage);
                if (dumpPage > maxPage) {
                    queryData(maxPage);
                } else {
                    queryData(dumpPage);
                }
            }

        }
    }

    function replaceFormData() {
        var requestMap = '${requestMap}';
        if (requestMap) {
            var json = JSON.parse(requestMap);
            for (var key in json) {
                if (json[key] && json[key] != '') {
                    $("[name='"+key+"']").val(json[key]);
                }
            }
        }
    }


    function queryData(val) {
        var layload = layer.load('正在查询中…');
        var formData = {};
        var IS_YQ = $("[name='IS_YQ']").val();
        if (IS_YQ && IS_YQ != '') {
            if (IS_YQ == '1') {
                formData['Q_D.EFFI_DESC_EQ'] = 3;
            } else {
                formData['Q_D.EFFI_DESC_NEQ'] = 3;
            }
        }
        var JBR = $("[name='JBR']").val()
        if (JBR && JBR != '') {
            formData['Q_A.CREATOR_ACCOUNT_EQ'] = JBR;
        }
        var PJXX = $("[name='PJXX']").val()
        if (PJXX && PJXX != '') {
            formData['Q_E.EVALUATE_EQ'] = PJXX;
        }
        var SQQD = $("[name='SQQD']").val();
        if (SQQD && SQQD != '') {
            if (SQQD == 1) {
                formData['Q_A.SQFS_EQ'] = 1;
            } else if (SQQD == 2) {
                formData['Q_A.SQFS_IN'] = '2,3';
            }
        }
        if (val && val != '') {
            formData['page'] = val;
        }
        formData['rows'] = 20;
        $.ajax({
            url: "bdcApplyController.do?bdcbjmxbData",
            type: "post",
            async: false,
            data: formData,
            success: function (data) {
                if (data) {
                    var json = JSON.parse(data);
                    initListBody(json);
                    initPage(json)
                }
                layer.close(layload);
            }
        });
    }


    function initListBody(json) {
        var str = "";
        var exeList = json.exeList;
        if (exeList && exeList.length > 0) {
            for (let i = 0; i < exeList.length; i++) {
                str += "<tr>"
                str += "<td width='210px'>"+exeList[i].EXE_ID+"</td>";
                str += "<td width=\"250\">"+exeList[i].SUBJECT+"</td>";
                str += "<td width=\"250\">"+exeList[i].ITEM_NAME+"</td>";
                str += "<td width=\"150\">"+exeList[i].SXXZ+"</td>";
                var jbr = exeList[i].CREATOR_NAME;
                var sqfs = exeList[i].SQFS;
                if (sqfs == 2) {
                    str += "<td>"+jbr+"</td>";
                } else if (sqfs == 1){
                    str += "<td>平潭综合实验区</td>";
                } else if (sqfs == 3) {
                    str += "<td>省网上办事大厅</td>";
                }

                str += "<td width=\"150\">"+(exeList[i].SQFS == 1 ? '线上申请' : '窗口申请')+"</td>";
                str += "<td width=\"150\">"+(exeList[i].EVALUATE ? exeList[i].EVALUATE : '')+"</td>";
                str += "<td width='150'>"+(exeList[i].EFFI_DESC ? (exeList[i].EFFI_DESC == 3 ? '是' : '否') : '')+"</td>";
                str += "</tr>";
            }
        }
        $("#listBody").html(str);
    }

    function initPage(json) {
        $("#nowPage").text(json.nowPage);
        $("#pageSize").text(json.pageSize);
        $("#maxPage").val(json.maxPage);
        var html = "";
        var pageList = json.pageList;
        if (pageList && pageList.length > 0) {
            html += "<li onclick=\"prevPage("+(parseInt(json.nowPage) - 1)+");\"><img src=\"<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/prev.png\"/></li>"
            for (let i = 0; i < pageList.length; i++) {
                html += "<li onclick='queryData("+pageList[i]+")'>"+pageList[i]+"</li>";
            }
            html += "<li onclick=\"nextPage("+(parseInt(json.nowPage) + 1)+","+parseInt(json.maxPage)+");\"><img src=\"<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/next.png\"/></li>";
            html += "<li class=\"noBg\">跳至 <input id='dumpPage' onkeydown='dump(event)'/>页 共<span id='maxPage1'></span>页</li>";
        }
        $("#pageList").html(html);
        $("#maxPage1").text(json.maxPage);
    }

    function reset() {
        $("[name='SSPQ']").val('');
        $("[name='JBR']").val('');
        $("[name='PJXX']").val('');
        $("[name='IS_YQ']").val('');
        $("[name='SQQD']").val('');
    }
    function prevPage(val) {
        if (val < 1) {
            queryData(1);
        } else {
            queryData(val);
        }
    }
    function nextPage(val,max) {
        if (val <= max) {
            queryData(val);
        } else {
            queryData(max);
        }
    }
    function backToParentUrl() {
        window.location.href="javascript:history.go(-1)";
    }
</script>
<body>
<div class="eui-bodyBox">
    <img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/btn_back.png" style="position: absolute;cursor:pointer;left: 0px;top: 25px;z-index: 9999;" onclick="backToParentUrl()">
    <div class="eui-head"><img src="<%=basePath%>/webpage/bdcqlc/bdcdjsjjg/images/toptitle.png" /></div>
    <!--查询-->
    <div class="eui-sxtjSearch eui-mx">
        <ul>
            <li style="width: 410px;">
                <span>事项名称</span>
                <div class="info"><input type="" name="" id="" value="行政服务中心（不动产登记交易科）" /></div>
            </li>
            <li style="width: 410px;">
                <span>起始时间</span>
                <div class="info">
                    <div class="eui-data">${dateStart}</div>
                </div>
            </li>
            <li style="width: 410px;">
                <span>结束时间</span>
                <div class="info">
                    <div class="eui-data">${dateEnd}</div>
                </div>
            </li>
            <li style="width: 270px;">
                <span>是否逾期</span>
                <div class="info">
                    <select name="IS_YQ">
                        <option value="">请选择是否逾期</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </li>
        </ul>

        <ul>
            <li style="width: 410px;">
                <span>所属片区</span>
                <div class="info">
                    <select name="SSPQ">
                        <option value="">请选择所属片区</option>
                        <c:forEach items="${ryfzList}" var="list">
                            <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li style="width: 410px;">
                <span>经办人</span>
                <div class="info">
                    <select name="JBR">
                        <option value="">请选择经办人</option>
                        <c:forEach items="${userList}" var="list">
                            <option value="${list.USERNAME}">${list.FULLNAME}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li style="width: 410px;">
                <span>申请渠道</span>
                <div class="info">
                    <select name="SQQD">
                        <option value="">请选择申请类型</option>
                        <option value="2">窗口申请</option>
                        <option value="1">线上申请</option>
                    </select>
                </div>
            </li>
            <li style="width: 270px;">
                <span>评价信息</span>
                <div class="info">
                    <select name="PJXX">
                        <option value="">请选择评价信息</option>
                        <c:forEach items="${pjxxList}" var="list">
                            <option value="${list.DIC_CODE}">${list.DIC_NAME}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
        </ul>
        <ul>
            <input type="hidden" id="maxPage">
            <li class="btn" style="width: 130px;" onclick="queryData(1);"><span>查询</span></li>
            <li class="btn" style="width: 130px;" onclick="reset()"><span>重置</span></li>
        </ul>
    </div>
    <!--查询-->
    <div class="eui-mxTable">
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="210px">申请号</th>
                <th width="250">办件名称</th>
                <th width="250">审批服务项目名称</th>
                <th width="150">事件性质</th>
                <th>经办人</th>
                <th width="150">申请类型</th>
                <th width="150">评价信息</th>
                <th width="150">是否逾期</th>
            </tr>
            <tbody id="listBody">

            </tbody>
        </table>
    </div>
    <div class="eui-page">
        <ul>
            <li>当前第 <span id="nowPage"></span> 页  共 <span id="pageSize"></span>条记录</li>
        </ul>
        <ul class="right" id="pageList">
        </ul>
    </div>
</div>
</body>

<script>
    $(".sc").perfectScrollbar();
</script>
</html>