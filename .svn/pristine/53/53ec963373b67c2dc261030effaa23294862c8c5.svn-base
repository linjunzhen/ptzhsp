<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript"
            src="plug-in/jqueryUpload/AppUtilNew.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/lsylwtcz/js/lsylwtcz.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
    <script type="text/javascript">
        $(function () {
            initBeforForm();
            initEasyUiForm();
            delChildItem();
            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;
            var flowSubmitObj = FlowUtil.getFlowObj();
            var disabledFieldArr1 = ['LOCATED', 'CHGS_ID', 'SFSCCH'];
            var disabledFieldArr2 = ['FW_GHYT', 'FW_YTSM', 'FW_XZ' , 'FW_FWJG','FW_SFDJ','ZD_TZM',
                'ZD_ZL','ZD_YTSM','ZD_LEVEL','ZD_RJL','ZD_QLLX','ZD_QLSDFS','ZD_MJ','ZD_TDYT',
                'ZD_QLXZ','FW_GYQK','FW_FJ'];
            if (flowSubmitObj) {
                //获取表单字段权限控制信息
                var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
                //初始化表单字段权限控制
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_LSYLWTCZ_FORM");
                //初始化表单字段值
                if (flowSubmitObj.busRecord) {
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_LSYLWTCZ_FORM");
                    initAutoTable(flowSubmitObj);//初始化权利信息
                    /*登簿后按钮无法点击*/
                    disabledDbBtn("BDC_DBBTN");
                    /*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
                    fillInEasyUiForm(flowSubmitObj);
                    /*默认收费科目为补缴土地价款*/
                    $("#DJJFMX_SFKMMC").val('6');
                    /*测绘材料列表展示*/
                    initDrawFileList(flowSubmitObj.busRecord.DRAW_FILE_ID,flowSubmitObj.EFLOW_CURUSEROPERNODENAME);
                    /*初始化计算器信息*/
                    initBdcLsyljfjs(flowSubmitObj);
                    /*不动产受理通知单打印*/
                    $("#SPBDF").attr("onclick","goPrintTemplate('LSYLWTCXDJSPB','3');");
                    $("#SPBSF").attr("onclick","goPrintTemplate('LSYLWTCXDJSPB','3');");
                    if (flowSubmitObj.busRecord.RUN_STATUS) {
                        if (flowSubmitObj.busRecord.RUN_STATUS != 0) {

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
                                $("#printBtn").show();
                                $("input[name='EXE_SUBBUSCLASS']").attr("disabled", true);
                            } else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
                                hideForm1();
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
                                hideForm1();
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '测绘') {
                                hideForm1();
                                isDisabledForm("T_BDCQLC_LSYLWTCZ_FORM", true);
                                isDisabledFields(disabledFieldArr1, true);
                                $("#uploadFileBtn").show();
                                $(".delFile").show();
                                $("#printBtn").hide();
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '权籍调查') {
                                hideForm1();
                                isDisabledForm("T_BDCQLC_LSYLWTCZ_FORM", true);
                                isDisabledFields(disabledFieldArr1, true);
                                $("#printBtn").hide();
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
                                $("#fwdyxxBtn").show();
                                isDisabledFields(disabledFieldArr1, true);
                                isDisabledFields(disabledFieldArr2, false);
                                $("#bdcdyhFont").show();
                                $("input[name='ESTATE_NUM']").attr("class","tab_text validate[required,custom[estateNum]]");
                                var EXE_SUBBUSCLASS = flowSubmitObj.busRecord.EXE_SUBBUSCLASS;
                                if (EXE_SUBBUSCLASS == '单位集资房' || EXE_SUBBUSCLASS == '国有土地上自建房屋分割销售' || EXE_SUBBUSCLASS == '旧城改造') {
                                    $("#lsyljfjs").show();
                                }
                                var rjl1 = $("input[name='RJL1']").val();
                                if (!(rjl1 && rjl1 != "")) {
                                    $("input[name='RJL1']").val(2.5);
                                }
                                var rjl2 = $("input[name='RJL2']").val();
                                if (!(rjl2 && rjl2 != "")) {
                                    $("input[name='RJL2']").val(4.0);
                                }
                                
                                //设置宗地信息&房屋基本信息必填项
                                $("#font1").attr("style","");
                                $("input[name='ZD_BM']").addClass(" validate[required]");
                                $("#font2").attr("style","");
                                $("select[name='ZD_TZM']").addClass(" validate[required]");
                                $("#font3").attr("style","");
                                $("select[name='ZD_QLSDFS']").addClass(" validate[required]");
                                $("#font4").attr("style","");
                                $("input[name='ZD_MJ']").addClass(" validate[required]");
                                $("#font5").attr("style","");
                                $("input[name='ZD_TDYT']").addClass(" validate[required]");
                                $("#font6").attr("style","");
                                $("input[name='ZD_QLXZ']").addClass(" validate[required]");
                                $("#font7").attr("style","");
                                $("input[name='FW_ZL']").addClass(" validate[required]");
                                $("#font8").attr("style","");
                                $("input[name='FW_GHYT']").addClass(" validate[required]");
                                $("#font9").attr("style","");
                                $("input[name='FW_YTSM']").addClass(" validate[required]");
                                $("#font10").attr("style","");
                                $("select[name='FW_XZ']").addClass(" validate[required]");
                                $("#font11").attr("style","");
                                $("select[name='FW_FWJG']").addClass(" validate[required]");
                                $("#font12").attr("style","");
                                $("select[name='FW_GYQK']").addClass(" validate[required]");
                                $("#font13").attr("style","");
                                $("input[name='FW_ZYJZMJ']").addClass(" validate[required]");
                                
                                //证书预览
                                $("#bdcqzh_tr").show();
                                $("#BDC_QZVIEW").show();
                                $("#BDC_DBBTN").hide();
                                $("input[name='BDC_SZZH']").attr("disabled","disabled");
                                
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '预登簿') {
                                $("#bdcqzh_tr").show();
                                $("#BDC_QZVIEW").show();
                                $("#BDC_DBBTN").hide();
                                isDisabledFields(disabledFieldArr1, true);
                                $("#lsyljfjs select , #lsyljfjs input").each(function () {
                                    $(this).attr("disabled", true)
                                });
                                var HZ_OPINION_CONTENT = $("[name='HZ_OPINION_CONTENT']").val();
                                if (HZ_OPINION_CONTENT == "") {
                                    $("[name='HZ_OPINION_CONTENT']").val("待缴清税费后予以登记。");
                                }
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '收费') {
                                $("#djjfxx_wtcz").show();
                                isDisabledFields(disabledFieldArr1, true);
                                $("#lsyljfjs select , #lsyljfjs input").each(function () {
                                    $(this).attr("disabled", true)
                                });
                                //权利开始时间、权利结束时间必填
                                $("#beginTime").attr("style","");
                                $("#GYTD_BEGIN_TIME").addClass(" validate[required]");
                                $("#endTime1").attr("style","");
                                $("#GYTD_END_TIME1").addClass(" validate[required]");
                                
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {
                                $("#djjfxx_wtcz").show();
                                isDisabledFields(disabledFieldArr1, true);
                                $("#lsyljfjs select , #lsyljfjs input").each(function () {
                                    $(this).attr("disabled", true)
                                });
                            }

                            /*已办结*/
                            var isEndFlow = false;
                            if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
                                isEndFlow = true;
                            }
                            if (isEndFlow) {
                                $("#powerSourceInfo").show();
                                $("#powerSourceInfoList").show();
                                $("#zdjbxx").show();
                                $("#gyqlxx").show();
                                $("#fwjbxx").show();
                                $("#lsyljfjs").show();
                                $("#printBtn").show();
                            }

                            /*测绘公司查看办件单独隐藏部分数据*/
                            var username = $("input[name='username']").val();
                            if (username.indexOf("chgs") != -1) {
                                hideForm1();
                                $("#printBtn").hide();
                            }

                        } else {
                            hideForm1();
                        }
                    } else {
                        hideForm1();
                    }
                } else {
                    $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
                    hideForm1();
                    $("#upload").hide();
                }
            }
        })
    </script>


    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
    MyGetData()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
    MyGetErrMsg()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
    MyClearData()
    </SCRIPT>
    <OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
            CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
            CODEBASE="IdrOcx.cab#version=1,0,1,2">
    </OBJECT>
</head>


<body>
    <input type="hidden" id="sxtsx" name="sxtsx" value="0" />
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
    <div class="detail_title">
        <h1>${serviceItem.ITEM_NAME}</h1>
    </div>
    <form id="T_BDCQLC_LSYLWTCZ_FORM" method="post">
        <%--===================重要的隐藏域内容=========== --%>
        <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
        <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
        <input type="hidden" name="username" value="${sessionScope.curLoginUser.username}" />
        <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
        <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
        <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
        <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
        <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
        <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
        <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
        <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
        <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
        <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
        <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
        <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />

        <%--===================重要的隐藏域内容=========== --%>
        <input type="hidden" name="POWERINFO_JSON" />
        <input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
        <input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
        <input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
        <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
        <!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
        <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

        <%--登记发证信息明细--%>
        <input type="hidden" name="DJFZXX_JSON" id="DJFZXX_JSON"/>
        <%--登记缴费信息明细--%>
        <input type="hidden" name="DJJFMX_JSON" id="DJJFMX_JSON"/>

        <%--测绘材料ID--%>
        <input type="hidden" name="DRAW_FILE_ID" id="DRAW_FILE_ID"/>

        <%--开始引入不动产基本信息--%>
        <jsp:include page="./bdcqlc/bdcJbxx.jsp" />
        <%--开始引入不动产基本信息 --%>

        <%--开始引入公用申报对象--%>
        <jsp:include page="./applyuserinfo.jsp" />
        <%--结束引入公用申报对象 --%>

        <%--开始引入受理信息--%>
        <jsp:include page="./bdcqlc/lsylwtcz/lsylwtczSlxx.jsp" />
        <%--结束引入受理信息--%>

        <%--开始引入领证人信息--%>
        <jsp:include page="./bdcqlc/lzrInfo.jsp" />
        <%--结束引入领证人信息--%>

        <%--开始引入公用上传材料界面 --%>
        <jsp:include page="./applyMaterList.jsp">
            <jsp:param value="2" name="isWebsite" />
        </jsp:include>
        <%--结束引入公用上传材料界面 --%>

        <%--开始引入权利信息--%>
        <jsp:include page="./bdcqlc/lsylwtcz/bdcQlrxx.jsp" />
        <%--结束引入权利信息--%>

        <%--开始引入权属来源信息--%>
        <jsp:include page="./bdcqlc/lsylwtcz/bdcQslyType1.jsp" />
        <%--开始引入权属来源信息--%>

        <%--开始引入宗地信息-国有权力人信息--%>
        <jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
        <%--开始引入宗地信息-国有权力人信息--%>

        <%--开始引入房屋基本信息--%>
        <jsp:include page="./bdcqlc/bdcFwjbxx.jsp" />
        <%--开始引入房屋基本信息--%>

        <%--不动产地价计算器--%>
        <jsp:include page="./bdcqlc/lsylwtcz/bdcLsyljfjs.jsp" />
        <%--不动产地价计算器--%>

        <%--开始登记审核意见信息（不动产通用）--%>
        <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" />
        <%--结束登记审核意见信息（不动产通用）--%>

        <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
        <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
        <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
        <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
            <jsp:param value="wtcz" name="domId" />
            <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
        </jsp:include>
        <%-- 引入登记审核、缴费信息、发证、归档信息 --%>

        <%--开始审批表打印按钮--%>
        <div id="printBtn" name="printBtn" style="display:none;">
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <th>
                        <a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="SPBDF"
                        >审批表（单方）</a>
                        <a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="SPBSF"
                        >审批表（双方）</a>
                    </th>
                </tr>
            </table>
        </div>
        <%--结束审批表打印按钮--%>

        <%--开始引入不动产EMS模块--%>
        <jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>
        <%--开始引入不动产EMS模块--%>

        <%--开始引入不动产备注模块--%>
        <jsp:include page="./bdcqlc/bdcRemark.jsp" />
        <%--开始引入不动产备注模块--%>

    </form>
</body>
</html>