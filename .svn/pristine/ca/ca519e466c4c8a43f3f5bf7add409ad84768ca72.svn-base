<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
    request.setAttribute("nowDate", nowDate);
    String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    request.setAttribute("nowTime", nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/jtjsfw/js/jtjsfw.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
    <script type="text/javascript">
        $(function() {

            $("#ZD_TDYT").combobox({
                url:'bdcGyjsjfwzydjController/loadTdytData.do',
                method:'post',
                valueField:'VALUE',
                textField:'TEXT',
                prompt:'请选择土地用途',
                panelHeight:'200',
                multiple:true,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
                },
                onLoadSuccess: function (row) {  //下拉框数据加载成功调用
                    var opts = $(this).combobox('options');
                    var target = this;
                    var values = $(target).combobox('getValues');//获取选中的值的values
                    $.map(values, function (value) {
                        var el = opts.finder.getEl(target, value);
                        el.find('input.combobox-checkbox')._propAttr('checked', true);
                    })
                },
                onSelect: function (row) { //选中一个选项时调用
                    var opts = $(this).combobox('options');
                    //设置选中值所对应的复选框为选中状态
                    var el = opts.finder.getEl(this, row[opts.valueField]);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                    //获取选中的值的values
                    // $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
                    $("#ZD_YTSM").val($(this).combobox('getText'))
                },
                onUnselect: function (row) {
                    //不选中一个选项时调用
                    var opts = $(this).combobox('options');
                    var el = opts.finder.getEl(this, row[opts.valueField]);
                    el.find('input.combobox-checkbox')._propAttr('checked', false);
                    //获取选中的值的values
                    // $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
                    $("#ZD_YTSM").val($(this).combobox('getText'))
                }
            });

            $("#ZDZL_XIAN").combobox({
                url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
                method:'post',
                valueField:'VALUE',
                textField:'TEXT',
                panelHeight: '200',
                editable: false,
                required:true,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField]
                },
                onLoadSuccess:function(row) {
                    $('#ZDZL_XIAN').combobox('setValue','351001');
                },
                onSelect:function (row) {
                    $('#ZDZL_ZHENG').combobox('clear');
                    $('#ZDZL_CUN').combobox('clear');
                    if (row.VALUE) {
                        var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
                        $('#ZDZL_ZHENG').combobox('reload',url);
                    }
                }
            });

            $("#ZDZL_ZHENG").combobox({
                url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
                method:'post',
                valueField:'VALUE',
                textField:'TEXT',
                panelHeight: '200',
                editable: false,
                required:true,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField]
                },
                onSelect:function (row) {
                    $('#ZDZL_CUN').combobox('clear');
                    if (row.VALUE) {
                        var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
                        $('#ZDZL_CUN').combobox('reload',url);
                    }
                }
            });

            $("#ZDZL_CUN").combobox({
                url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
                method:'post',
                valueField:'VALUE',
                textField:'TEXT',
                panelHeight: '200',
                editable: false,
                required:true,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField]
                },
                onSelect:function (row) {

                }
            });

            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;
            var flowSubmitObj = FlowUtil.getFlowObj();
            var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
            dealItems = "," + dealItems + ",";
            $("#qlxxTitle").text("集体建设用地使用权-权利信息");
            //设置权利类型默认为'集体建设用地使用权/房屋所有权'
            $('#QLLX').prop("disabled", "disabled");
            $('#QLLX').val('8');
            hideDjxx('jtjsfw');
            notPrintSpb();
            yjmbSelect();
            if (flowSubmitObj) {
                //获取表单字段权限控制信息
                var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                var exeid = flowSubmitObj.EFLOW_EXEID;
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);

                //初始化表单字段权限控制
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_JTJSFW_FORM");
                //初始化表单字段值
                if (flowSubmitObj.busRecord) {
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_JTJSFW_FORM");

                    /*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
                    if (flowSubmitObj.busRecord.ZD_TDYT) {
                        $("#ZD_TDYT").combobox("setValues", flowSubmitObj.busRecord.ZD_TDYT.split(","));
                    }
                    if (flowSubmitObj.busRecord.ZDZL_XIAN) {
                        $("#ZDZL_XIAN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_XIAN);
                    }
                    if (flowSubmitObj.busRecord.ZDZL_ZHENG) {
                        $("#ZDZL_ZHENG").combobox("setValue", flowSubmitObj.busRecord.ZDZL_ZHENG);
                    }
                    if (flowSubmitObj.busRecord.ZDZL_CUN) {
                        $("#ZDZL_CUN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_CUN);
                    }

                    initAutoTable(flowSubmitObj);
                    if (flowSubmitObj.busRecord.RUN_STATUS) {
                        if (flowSubmitObj.busRecord.RUN_STATUS != 0) {
                            /*其它环节审批表可以进行打印*/
                            if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始' && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '受理'){
								$("#printBtn").show();
							}
                            $("#spbdf").attr("onclick","goPrintTemplate('JTJSYDSYQJFWSYQBGDJSPB','3');");
                            $("#spbsf").attr("onclick","goPrintTemplate('JTJSYDSYQJFWSYQBGDJSPB','3');");

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
                                //申报对象信息不可修改
                                $("#userinfo_div input").each(function (index) {
                                    $(this).attr("disabled", "disabled");
                                });
                                $("#userinfo_div select").each(function (index) {
                                    $(this).attr("disabled", "disabled");
                                });
                                //事项基本信息不可修改
                                $("#baseinfo input").each(function (index) {
                                    $(this).attr("disabled", "disabled");
                                });
                                //经办人信息可修改
                                $("#JBRXX input").each(function (index) {
                                    $(this).attr("disabled", false);
                                });
                                $("#JBRXX select").each(function (index) {
                                    $(this).attr("disabled", false);
                                });
                            } else {
                                disabledForm();
                                if ($("input[name='SBMC']").val()) {
                                } else {
                                    $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
                                }
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {

                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {

                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '复审') {

                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
                                $("#bdcqzh_tr").attr("style", "");
                                $("#bdcczr_tr").attr("style", "");
                                $("#bdcqzbsm_tr").show();
                                $("#BDC_QZVIEW").show();
                            }

                            if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {

                            }
                        }

                        /*办结状态*/
                        var isEndFlow = false;
                        if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
                            isEndFlow = true;
                        }
                        // 流程办理结束页面只读
                        if (isEndFlow) {
                            //当流程结束
                            $("#bdcqzh_tr").attr("style","");
                            $("#BDC_DBBTN").remove();
                            $("#bdcqzbsm_tr").attr("style","");
                            $("#dy_bdcqzbsm_tr").attr("style","");
                            $("#bdcczr_tr").attr("style","");
                            $("#qzbsmsavebtn").hide();
                            //证书预览
                            $("#BDC_QZVIEW").attr("style","");
                            $("#BDC_QZVIEW").removeAttr("disabled");
                            //登记证书预览
                            $("#DY_BDC_QZVIEW").attr("style","");
                            $("#DY_BDC_QZVIEW").removeAttr("disabled");

                            //登记缴费和发证信息
                            $("#djjfxx_jtjsfw").attr("style","");
                            $("#djfzxx_jtjsfw").attr("style","");

                            $("#T_BDCQLC_JTJSFW_FORM").find("input[type='text']").attr("disabled", "disabled");
                            $("#T_BDCQLC_JTJSFW_FORM").find("input[type='button']").attr("disabled", "disabled");
                            $("#T_BDCQLC_JTJSFW_FORM").find("input[type='checkbox']").attr("disabled", "disabled");
                            $("#T_BDCQLC_JTJSFW_FORM").find("input[type='radio']").attr("disabled", "disabled");
                            $("#T_BDCQLC_JTJSFW_FORM").find("input,select").attr("disabled", "disabled");
                        }
                    }
                } else {
                    $("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
                }

            }
        });


        //------------------------------------身份身份证读取开始---------------------------------------------------
        function MyGetData() //OCX读卡成功后的回调函数
        {
            //          POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>
            //          POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>
        }

        function MyClearData() //OCX读卡失败后的回调函数
        {
            alert("未能有效识别身份证，请重新读卡！");
            $("input[name='POWERPEOPLENAME']").val("");
            $("input[name='POWERPEOPLEIDNUM']").val("");
        }

        function MyGetErrMsg() //OCX读卡消息回调函数
        {
            //          Status.value = GT2ICROCX.ErrMsg;
        }

        function PowerPeopleRead(o) //开始读卡
        {
            var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name)
                $("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo)
            }
        }
        function PowLegalRead(o) //开始读卡
        {
            var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name)
                $("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo)
            }
        }
        function PowAgentRead(o) //开始读卡
        {
            var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
            GT2ICROCX.PhotoPath = "c:"
            //GT2ICROCX.Start() //循环读卡
            //单次读卡(点击一次读一次)
            if (GT2ICROCX.GetState() == 0) {
                GT2ICROCX.ReadCard();
                $("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name)
                $("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo)
            }
        }

        function print() //打印
        {
            GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
        }
        //------------------------------------身份身份证读取结束---------------------------------------------------
    </script>
    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>    //设置回调函数
    MyGetData()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>  //设置回调函数
    MyGetErrMsg()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>  //设置回调函数
    MyClearData()
    </SCRIPT>
    <style>
        .sel {
            border: solid 1px red;
        }
    </style>
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
<form id="T_BDCQLC_JTJSFW_FORM" method="post">
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" />
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
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
    <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
    <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
    <input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
    <input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
    <%--登记发证信息明细--%>
    <input type="hidden" name="DJFZXX_JSON" id="DJFZXX_JSON"/>
    <%--登记缴费信息明细--%>
    <input type="hidden" name="DJJFXX_JSON" id="DJJFXX_JSON"/>
    <!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->
    <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />

    <%--===================重要的隐藏域内容=========== --%>
    <%--开始引入不动产基本信息--%>
    <jsp:include page="./bdcqlc/bdcJbxx.jsp" />
    <%--开始引入不动产基本信息 --%>

    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>

    <div id="slxxInfo">
        <%--开始引入受理信息--%>
        <jsp:include page="./bdcqlc/jtjsfw/T_ESTATE_ACCEPTINFO.jsp" />
        <%--结束引入受理信息--%>

        <%--开始引入领证人信息--%>
        <jsp:include page="./bdcqlc/lzrInfo.jsp" />
        <%--结束引入领证人信息--%>
    </div>
    <%--开始引入公用上传材料界面 --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="1" name="isWebsite" />
    </jsp:include>
    <%--结束引入公用上传材料界面 --%>
    <div id="publicInfo">
        <%--开始引入权利人信息--%>
        <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlrxx.jsp" />
        <%--结束引入权利人信息--%>

        <%--开始引入权属来源信息--%>
        <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQsly.jsp" />

        <%--开始引入宗地信息-国有权力人信息--%>
        <jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
        <%--开始引入宗地信息-国有权力人信息--%>

        <%--开始引入房产基本信息--%>
        <jsp:include page="./bdcqlc/bdcFwjbxx.jsp" />
        <%--开始引入房产基本信息--%>

        <%--不动产EMS模块--%>
        <jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>
        
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
		
		<%--开始登记审核意见信息（不动产通用）--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" /> 
		<%--结束登记审核意见信息（不动产通用）--%>

        <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
        <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djgdxx:登记归档信息 -->
        <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
        <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
            <jsp:param value="jtjsfw" name="domId" />
            <jsp:param value="djjfxx,djfzxx,djgdxx" name="initDomShow" />
        </jsp:include>
        <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
    </div>

</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
        CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
