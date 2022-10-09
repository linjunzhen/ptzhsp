<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
<eve:resources
        loadres="apputil,jquery,easyui,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
<%--<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>--%>
<%--<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>--%>
<%--<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>--%>
<%--<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
</head>
<script type="text/javascript">
    function  toUrl(url) {
        window.location.href=__ctxPath+url;
    }

    $(function() {
        //初始化验证引擎的配置
        initDefaultValidateConfig();
    });
    /**
     * 初始化缺省验证配置信息
     */
    function initDefaultValidateConfig(config){
        //初始化验证引擎的配置
        $.validationEngine.defaults.scroll = false;
        var forms = $("form[id]");
        forms.each(function(){
            var formId = $(this).attr("id");
            $("#"+formId).validationEngine("attach",{
                promptPosition:"topLeft",
                autoPositionUpdate:true,
                validateNonVisibleFields:true,
                autoHidePrompt: false,
                autoHideDelay: "10000",
                fadeDuration: "0.2",
                maxErrorsPerField: "1",
                showOneMessage: false
                //onValidationComplete:config.onValidationComplete
            });
        });
    }

</script>



<body>

<div class="eui-main mine" >
    <jsp:include page="../project/head.jsp"></jsp:include>
<%--    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/Select.css">--%>
    <!--  隐藏域 开始 -->
    <form id="T_GCXM_XMJBXX_FORM" method="post" >
        <%--开始引入公共隐藏域部分 --%>
        <jsp:include page="./commonhidden.jsp" />
        <%--结束引入公共隐藏域部分 --%>
    <input name="username" value="${sessionScope.curLoginMember.YHMC}"  type="hidden"/>
    <c:if test="${isQueryDetail!='true'}" >
        <input name="TYPE_ID" value="${typeId}" type="hidden" />
        <input name="STAGE_ID" value="${stageId}" type="hidden" />
        <input name="TOPIC_CODE" value="" type="hidden" />
    </c:if>
    <c:if test="${isQueryDetail=='true'}" >
        <input name="STAGE_ID" value="${busRecord.STAGE_ID}" type="hidden" />
        <input name="TYPE_ID" value="${busRecord.TYPE_ID}" type="hidden" />
        <input name="TOPIC_CODE" value="${busRecord.TOPIC_CODE}" type="hidden" />
    </c:if>
    <input type="hidden" name="ID" value="${busRecord.ID}">
    <input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
    <input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
    <input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${busRecord.FOREIGN_ABROAD_FLAG}"/>
    <input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${busRecord.THE_INDUSTRY}"/>
    <input type="hidden" id="GBHYDMFBND" name="GBHYDMFBND" value="2017"/>
    <input type="hidden" id="itemCodesSelected" value="${busRecord.itemCodesSelected}" name="itemCodesSelected"/>
    <input type="hidden" id="itemNamesSelected" value="${busRecord.itemNamesSelected}" name="itemNamesSelected"/>
</form>
    <!--  隐藏域 结束 -->
    <!-- 主体 -->
    <div class="eui-con">


        <div class=" eui-flex eui-mine">
            <div class="flex1">

                <div class="eui-card eui-mb eui-table-project">
                    <table>
                        <tr>
                            <th>事项编码</th>
                            <td>7820006000</td>
                            <th>事项名称</th>
                            <td colspan="5">政府投资房建类-立项用地规划许可阶段</td>
                        </tr>
                        <tr>
                            <th>事项类型</th>
                            <td>工程建设联办</td>
                            <th>办件类型</th>
                            <td>承诺件</td>
                            <th>法定时限</th>
                            <td>30个工作日</td>
                            <th>承诺时限</th>
                            <td>16个工作日</td>
                        </tr>
                    </table>
                </div>

                <div class="eui-card eui-mb eui-flex bt eui-step">
                    <span  class="i1 on" ><i>1</i>合同说明</span>
                    <span class="i2"  id="projectInfoForm_A" ><i>2</i>申报信息</span>
                    <span class="i3"  id="applyuserinfoForm_A"><i>3</i>业务表单</span>
                    <span class="i4"  id="applyMaterListForm_A"><i>4</i>上传材料</span>
                    <span class="i5"><i>5</i>完成申报</span>
                </div>

                <!-- 选择项目弹窗 -->
                <div class="eui-popup eui-porject-select" style="display: none;z-index: 9999999" >
                    <div class="close">×</div>
                    <div class="tit eui-flex bt vt">
                        <b>选择项目</b>
                    </div>
                    <div class="con">
                        <div class="eui-card eui-table-info bjgs">
                            <table>
                                <tr>
                                    <form id="myProjectInfoForm" >
                                        <th>项目编码</th>
                                        <td><input class="ipt" type="text" name="myProjectCode" value=""  placeholder="请输入项目编码"></td>
                                        <th>项目名称</th>
                                        <td><input class="ipt" type="text" name="myProjectName" value="" placeholder="请输入项目名称"></td>
                                    </form>
                                    <th>
                                        <a class="eui-btn round" href="javascript:void(0)" onclick="searchMyProjectInfo()">查询</a>
                                        <a class="eui-btn round lightblue" href="javascript:void(0)" onclick="restForm('myProjectInfoForm')">重置</a>
                                    </th>
                                </tr>
                            </table>
                        </div>
                        <div class="eui-sx-list">
                            <table>
                                <thead>
                                <tr>
                                    <th><input class="eui-checkbox" type="checkbox" /></th>
                                    <td>项目编码</td>
                                    <td>项目名称</td>
                                </tr>
                                </thead>
                                <tbody id="myProjectInfo">

                                </tbody>
                            </table>
                        </div>
                        <div class="eui-flex end eui-btn-wrap">
                            <a class="eui-btn o" href="projectWebsiteController/registerProject.do?" >项目登记</a>
                            <a class="eui-btn" href="javascript:void(0)" onclick="myProjectInfoOk()">确 定</a>
                        </div>
                    </div>
                </div>

                <!-- 1选择申报的事项 -->
                <jsp:include page="../project/selectServiceItem.jsp"></jsp:include>
                <!-- 2申报信息 -->
                <jsp:include page="../project/ProjectInfo.jsp"></jsp:include>
                <!-- 3业务表单 -->
                <jsp:include page="../project/applyuserinfo.jsp"></jsp:include>

                <!-- 4上传材料 -->
                <!-- 4.1主事项上传材料 -->
                <jsp:include page="../project/applyMaterList1.jsp">
                    <jsp:param value="1" name="applyType" />
                    <jsp:param value="1" name="isWebsite" />
                </jsp:include>
                <!-- 4.2副事项上传材料 -->
                <!-- 5完成申报 -->
                <jsp:include page="../project/applyCompleteView.jsp"></jsp:include>

            </div>
        </div>


    </div>
    <!-- 主体 end -->

    <!-- 底部 -->
    <div class="eui-footer">
        <iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no"
                allowtransparency="true"  src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
    </div>
    <!-- 底部 end -->
</div>

<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>--%>
<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/Select.js"></script>--%>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
    $(function() {
        // $("#goto2").click(function(){
        //     $(".wysb1").removeClass("on");
        //     $(".wysb2").addClass("on");
        //     $(".eui-step .i2").addClass("on");
        //     $('html, body').animate({scrollTop: 0},300);return false;
        // })
        $("#goto3").click(function(){
            $(".wysb2").removeClass("on");
            $(".wysb3").addClass("on");
            $(".eui-step .i3").addClass("on");
            $('html, body').animate({scrollTop: 0},300);
            //validateSingleForm('projectInfoForm');
            return false;
        })
        $("#goto4").click(function(){
            $(".wysb3").removeClass("on");
            $(".wysb4").addClass("on");
            $(".eui-step .i4").addClass("on");
            $('html, body').animate({scrollTop: 0},300);return false;
        })
        $("#goto5").click(function(){

        })
        $("#back1").click(function(){
            $(".wysb2").removeClass("on");
            $(".wysb1").addClass("on");
            $(".eui-step .i2").removeClass("on");
            $('html, body').animate({scrollTop: 0},300);return false;
        })
        $("#back2").click(function(){
            $(".wysb3").removeClass("on");
            $(".wysb2").addClass("on");
            $(".eui-step .i3").removeClass("on");
            $('html, body').animate({scrollTop: 0},300);return false;
        })
        $("#back3").click(function(){
            $(".wysb4").removeClass("on");
            $(".wysb3").addClass("on");
            $(".eui-step .i4").removeClass("on");
            $('html, body').animate({scrollTop: 0},300);return false;
        })
        $("#showXmxz").click(function(){
            $(".eui-porject-select").fadeIn(300);
            // parent.$.dialog.open("fileTypeController.do?selector&needCheckIds="+$("#targetTypeId").val(), {
            //     title: "多媒体类别选择器",
            //     width: "320px",
            //     height: "500px",
            //     lock: true,
            //     resize: false,
            //     ok: function(){
            //         var returnObj = this.iframe.contentWindow.onArtDialogCallback();
            //         if(returnObj){
            //             setSelectFileType(returnObj.checkIds, returnObj.checkNames);
            //         }else{
            //             return false;
            //         }
            //     },
            //     cancel: true
            // });

        })

        $(".eui-porject-select .close").click(function(){
            $(this).parent().fadeOut(300);
        })
        //下拉框
        // 筛选
        $(".eui-filter span").click(function(){
            $(this).addClass("on").siblings().removeClass("on");
        })
        // 已选事项
        $(".eui-sx-selected b").click(function(){
            $(this).toggleClass("on");
        })
        $(".eui-sx-popup .close").click(function(){
            $(".eui-sx-selected b").removeClass("on");
        })
    });
</script>

<script type="text/javascript"  charset="utf-8"  src="<%=basePath%>webpage/website/project/js/applyProject.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

    var xmdwxxDivHtml = "";
    $(function() {

        var lerepInfo = '${busRecord.lerep_info}';
        if(lerepInfo){
            var lerepInfoList = $.parseJSON(lerepInfo);
            for(var i=0 ; i<lerepInfoList.length; i++){
                initXmdwxx(lerepInfoList[i],i)
            }
        }
        //setTpl(lerepInfo);
        xmdwxxDivHtml = $("#xmdwxxDiv").html();

        //清除前后空格
        $("input,textarea").on('blur', function(event) {
            $(this).val(trim($(this).val()));
        });
    });
    //清除前后空格
    function trim(str){
        return str.replace(/(^\s*)|(\s*$)/g,"");
    }

    function onSelectClass(o){
        if(o==1){
            $("#resultcontent_tr").show();
            $("#resultcontent").attr("disabled",false);
        }else{
            $("#resultcontent_tr").hide();
            $("#resultcontent").attr("disabled",true);
        }
    }
    $().ready(function() {
        //var busRecord = '${busRecord.PROJECTCODE}';

        //if(busRecord){
        //	loadTZXMXXData();
        //}
        $("input[name='LEREP_INFO']").val('${busRecord.lerep_info}');
        $("input[name='CONTRIBUTION_INFO']").val('${busRecord.contribution_info}');
        isShowWztzxx();
    });
    function isShowWztzxx(){
        if($("#foreignabroadFlag").val()==1){
            $("#jwtzxx").attr("style","display:none;");
            $("#wstzxx").attr("style","display:;");
        }else if($("#foreignabroadFlag").val()==2){
            $("#wstzxx").attr("style","display:none;");
            $("#jwtzxx").attr("style","display:;");
        }else{
            $("#wstzxx").attr("style","display:none;");
            $("#jwtzxx").attr("style","display:none;");
        }
        // if($("#totalMoney").val()==0){
        //     $("#totalMoneyExplain").removeClass("");
        //     // $("#totalMoneyExplain").toggleClass('validate[required]');
        // }else{
        //     $("#totalMoneyExplain").removeClass("validate[required]");
        //     $("#totalMoneyExplain").toggleClass('');
        // }
    }
</script>
<script type="text/javascript">
    $(function(){
        //初始化验证引擎的配置
        $.validationEngine.defaults.autoHidePrompt = true;
        $.validationEngine.defaults.promptPosition = "topRight";
        $.validationEngine.defaults.autoHideDelay = "2000";
        $.validationEngine.defaults.fadeDuration = "0.5";
        $.validationEngine.defaults.autoPositionUpdate =true;

        //获取流程信息对象JSON
        var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
        if(EFLOW_FLOWOBJ){
            //将其转换成JSON对象
            var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
            //初始化表单字段值
            if(eflowObj.busRecord){
                FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_PROJECT_CODEINFOCHANGE_FORM");
            }else{
            }
        }
        //初始化材料列表
        //AppUtil.initNetUploadMaters({
        //	busTableName : "T_PROJECT_CODEINFOCHANGE"
        //});
    });

    function submitFlow(flowSubmitObj){
        AppUtil.submitWebSiteFlowForm('T_GCXM_XMJBXX_FORM');
    }
</script>
</body>
</html>