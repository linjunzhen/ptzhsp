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
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>   
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/gyjsjfwzydj/js/gyjsjfwzydj.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
<script type="text/javascript">
$(function(){
	/*规划用途自动改变*/
	$("#FW_GHYT").change(function () {
		$("#FW_YTSM").val($("#FW_GHYT").val());
	})
     //默认企业法人
     //qyclick();
     //$("input:radio[name='BSYHLX'][value='2']").attr("checked",true); 
     $("input[name='JBR_NAME']").removeAttr('readonly');
     $("input[name='JBR_ZJHM']").removeAttr('readonly');
     
     $("#djjfxx_zydj").attr("style","display:none;");
     $("#djfzxx_zydj").attr("style","display:none;");
	/* 容积率及等级在开始和受理阶段变成可填*/
	$("#ZD_RJL").removeAttr("readonly");

	initEasyUiForm();

    //初始化验证引擎的配置
    $.validationEngine.defaults.autoHidePrompt = true;
    $.validationEngine.defaults.promptPosition = "topRight";
    $.validationEngine.defaults.autoHideDelay = "2000";
    $.validationEngine.defaults.fadeDuration = "0.5";
    $.validationEngine.defaults.autoPositionUpdate =true;
    var flowSubmitObj = FlowUtil.getFlowObj();
    var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
    dealItems = "," + dealItems + ",";
    /*权属来源部分字段不可填写*/
	disabledQslyForm();
	ssdjSelect();
	delChildItem();
    if(flowSubmitObj){
        //获取表单字段权限控制信息
        var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        var exeid = flowSubmitObj.EFLOW_EXEID;
        $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
         //初始化表单字段权限控制
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDCQLC_GYJSJFWZYDJ_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDCQLC_GYJSJFWZYDJ_FORM");
            //表单内打印按钮是否可以操作
			isButtonAvailable();
			//动态切换抵押信息
			queryTypeFn();
            initAutoTable(flowSubmitObj);//初始化权利信息
			isFinishTax();
            
            //初始化电力水力燃气过户信息
			checkIsPowTransfer(flowSubmitObj.busRecord.IS_POWTRANSFER);
			checkIsWaterTransfer(flowSubmitObj.busRecord.IS_WATERTRANSFER);
			checkIsGasTransfer(flowSubmitObj.busRecord.IS_GASTRANSFER);
			
			/*初始化涉税登记材料列表*/
			initTaxRelatedFileList(flowSubmitObj.busRecord.SSDJ_BUY_FILEID);
			/*判断涉税登记申报情况*/
			initSsdjStatus(flowSubmitObj);
			/*登簿后按钮无法点击*/
			disabledDbBtn("BDC_DBBTN");
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
			/*片区名称回填*/
			if (flowSubmitObj.busRecord.SSDJ_FWXX_PQMC_S) {
				$("#SSDJ_FWXX_PQMC_S").combobox("setValue", flowSubmitObj.busRecord.SSDJ_FWXX_PQMC_S);
			}

			if (flowSubmitObj.busRecord.RUN_STATUS) {
				if (flowSubmitObj.busRecord.RUN_STATUS != 0) {
					/*房屋附记自动填写*/
					var ZYDJ_TYPE = flowSubmitObj.busRecord.ZYDJ_TYPE;
					var IS_FINISHTAX = flowSubmitObj.busRecord.IS_FINISHTAX;

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '网上预审') {
						$("#taxRelatedInfo").hide();
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '待受理') {
						$("#taxRelatedInfo").hide();
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
						var EXE_ID = flowSubmitObj.busRecord.EXE_ID;
						var bdcqdfs = "";
						if (ZYDJ_TYPE == "5") {
							bdcqdfs = "国有建设用地使用权及房屋所有权转移登记（分户）（抵押联办）";
						} else if (ZYDJ_TYPE == "4") {
							bdcqdfs = "国有建设用地使用权及房屋所有权转移登记（存量房税费联办）";
						} else if (ZYDJ_TYPE == "1") {
							bdcqdfs = "国有建设用地使用权及房屋所有权转移登记（分户）";
						} else if (ZYDJ_TYPE == "2") {
							bdcqdfs = "国有建设用地使用权及房屋所有权（多幢）转移登记";
						} else if (ZYDJ_TYPE == "6") {
							$("#powerSourceInfo select , #powerSourceInfo input").each(function (index) {
								$(this).attr("disabled", false);
							})
							bdcqdfs = "国有建设用地使用权及房屋所有权转移登记（其他）";
						} else if (ZYDJ_TYPE == "3") {
							$("#powerSourceInfo select , #powerSourceInfo input").each(function (index) {
								$(this).attr("disabled", false);
							})
							bdcqdfs = "国有建设用地使用权及房屋所有权转移登记（权利限制）";
						}
						var POWERSOURCEINFO_JSON = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
						var text = "业务编号：" + EXE_ID.substr(4) + "；\r";
						text += "不动产取得方式：" + bdcqdfs + "；\r";
						var POWERSOURCE_QLRMC = "";
						var POWERSOURCE_QSWH = "";
						if (POWERSOURCEINFO_JSON) {
							var sourceParse = JSON.parse(POWERSOURCEINFO_JSON);
							for (let i = 0; i < sourceParse.length; i++) {
								POWERSOURCE_QLRMC += sourceParse[i].POWERSOURCE_QLRMC + ",";
								POWERSOURCE_QSWH += sourceParse[i].POWERSOURCE_QSWH + ",";
							}
							text += "原权利人：" + POWERSOURCE_QLRMC.substring(0, POWERSOURCE_QLRMC.length - 1) + "；\r";
							text += "原权证号：" + POWERSOURCE_QSWH.substring(0, POWERSOURCE_QSWH.length - 1) + "。";
						}
						if (flowSubmitObj.busRecord.FW_FJ) {
							$("textarea[name='FW_FJ']").val(flowSubmitObj.busRecord.FW_FJ);
						} else {
							$("textarea[name='FW_FJ']").val(text);
						}
						$("#taxRelatedInfo").hide();
						$("#djshxx").hide();
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
						$("#bdcqzh_tr").attr("style", "");
						$("#bdcczr_tr").attr("style", "");
						showDjzm();
						$("#bdcqzbsm_tr").show();
						$("#BDC_QZVIEW").show();
						$("#djshOpinion").show();
						$("#djshHzOpinionInfo").show();
						setFjRevise();
						$("#taxRelatedInfo").hide();
						$("#djshxx").hide();
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '预登簿') {
						$("#bdcqzh_tr").show();
						$("#bdcqzbsm_tr").show();
						showDjzm();
						$("#BDC_DBBTN").hide();
						$("#BDC_QZVIEW").show();
						$("#djshOpinion").show();
						$("#djshHzOpinionInfo").show();
						$("#djshCsOpinionInfo").show();
						$("#djshCsOpinionInfo textarea").each(function (index) {
							$(this).attr("disabled", "disabled");
						})
						setFjRevise();
						/*存量房税费联办预登簿环节可申报和查询涉税信息*/
						if (ZYDJ_TYPE == '4' || ZYDJ_TYPE == '5' || ZYDJ_TYPE == '1' || ZYDJ_TYPE == '6') {
							showSsdjForm();
						}
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
						$("#BDC_QZVIEW").show();
						$("#bdcqzh_tr").attr("style", "");
						$("#BDC_DBBTN").hide();
						$("#djshOpinion").show();
						$("#djshCsOpinionInfo").show();
						setFjRevise();
						/*存量房税费联办初审环节可填写涉税申报数据*/
						if (ZYDJ_TYPE == '4') {
							showSsdjForm();
							calSinglePrice();
							/*初审环节只负责填写数据，无申报按钮*/
							/* $("#sendSsdjInfoBtn").hide();
							$("#sendSsdjFileInfoBtn").hide(); */
						}
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '涉税申报') {

						$("#djshOpinion").show();
						$("#djshCsOpinionInfo").show();
						$("#djshHzOpinionInfo").show();
						$("#saveDjshOpinionx").hide();
						$("#djshOpinion textarea").each(function (data) {
							$(this).attr("disabled", "disabled");
						});

						showSsdjForm();
						calSinglePrice();
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '收费' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {
						if (flowSubmitObj.busRecord.ZYDJ_TYPE == "5") {
							showDjzm();
						}

						$("#djshOpinion").show();
						$("#djshCsOpinionInfo").show();
						$("#djshHzOpinionInfo").show();
						$("#saveDjshOpinionx").hide();
						$("#djshOpinion textarea").each(function (index) {
							$(this).attr("disabled", "disabled");
						});

						showSsdjForm();
						$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
							$(this).attr("disabled", true);
						});
						$("#djshxx input , #djshxx select , #djshxx textarea").each(function () {
							$(this).attr("disabled", true);
						});
						$("#sendSsdjInfoBtn").hide();
						$("#sendSsdjFileInfoBtn").hide();
						$("#getSsdjInfoBtn").attr("disabled", false);
						if (ZYDJ_TYPE == '3' || IS_FINISHTAX == '1') {
							$("#djshxx").hide();
						}
					}

					//分户抵押联办处于受理时需要人工判断是否完税
					if ((flowSubmitObj.busRecord.ZYDJ_TYPE == "5" ||
							flowSubmitObj.busRecord.ZYDJ_TYPE == "1" || flowSubmitObj.busRecord.ZYDJ_TYPE == "6")
							&& flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
						$("#isfinishtax_tr").attr("style", "");
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始' && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '受理') {
						$("#powerSourceInfo select , #powerSourceInfo input").each(function (index) {
							$(this).attr("disabled", "disabled");
						});
					}

					if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
						if ($("input[name='SBMC']").val()) {
						} else {
							$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
						}
						$("input[name='ZYDJ_TYPE']").each(function (index) {
							$(this).attr("disabled", "disabled");
						});
						if (flowSubmitObj.HJMC != '开始') {
							$("#powTr input").attr("disabled", true);
							$("#waterTr input").attr("disabled", true);
							$("#gasTr input").attr("disabled", true);
						}

					} else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
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
						$("#taxRelatedInfo").hide();
						$("#djshxx").hide();
						/*权属来源手动填写按钮*/
						$("#sdtxBtn").show();
					}

				} else {
					$("#taxRelatedInfo").hide();
					$("#djshxx").hide();
					$("#sdtxBtn").show();
				}
			} else {
				$("#taxRelatedInfo").hide();
				$("#djshxx").hide();
				$("#sdtxBtn").show();
			}

			var isEndFlow = false;
			if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
				isEndFlow = true;
			}
			if(isEndFlow){
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
				$("#djjfxx_zydj").attr("style","");
				$("#djfzxx_zydj").attr("style","");

				$("#T_BDCQLC_GYJSJFWZYDJ_FORM").find("input[type='text']").attr("disabled", "disabled");
				$("#T_BDCQLC_GYJSJFWZYDJ_FORM").find("input[type='button']").attr("disabled", "disabled");
				$("#T_BDCQLC_GYJSJFWZYDJ_FORM").find("input[type='checkbox']").attr("disabled", "disabled");
				$("#T_BDCQLC_GYJSJFWZYDJ_FORM").find("input[type='radio']").attr("disabled", "disabled");
				$("#T_BDCQLC_GYJSJFWZYDJ_FORM").find("input,select").attr("disabled", "disabled");

				/*展示涉税登记信息*/
				showSsdjForm();
				$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
					$(this).attr("disabled", true);
				});
				$("#sendSsdjInfoBtn").hide();
				$("#sendSsdjFileInfoBtn").hide();
				$("#getSsdjInfoBtn").attr("disabled",false);

				$("#djshOpinion").show();
				$("#djshCsOpinionInfo").show();
				$("#djshHzOpinionInfo").show();
				$("#saveDjshOpinionx").hide();
				$("#djshOpinion textarea").each(function (index) {
					$(this).attr("disabled", "disabled");
				});
			}

            //若从后台收费发证功能进入
            var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
            if(BDC_OPTYPE != null && BDC_OPTYPE !=""){
                $("#T_BDCQLC_GYJSJFWZYDJ_FORM input").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                $("#T_BDCQLC_GYJSJFWZYDJ_FORM select").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                //填写权证标识码
                if(BDC_OPTYPE == "1"){
                    $("#bdcqzh_tr").attr("style","");
                    $("#BDC_DBBTN").remove();
                    $("#bdcqzbsm_tr").attr("style","");
                    $("#bdcczr_tr").attr("style","");
                    $("#dy_bdcqzbsm_tr").attr("style","");
                    //权证打印按钮
                    $("#BDC_QZVIEW").attr("style","");
                    $("#BDC_QZPRINT").attr("style","");
                    $("#BDC_QZVIEW").removeAttr("disabled");
                    $("#BDC_QZPRINT").removeAttr("disabled");
                    //登记证明打印与预览按钮
                    $("#DY_BDC_QZVIEW").attr("style","");
                    $("#DY_BDC_QZPRINT").attr("style","");
                    $("#DY_BDC_QZVIEW").removeAttr("disabled");
                    $("#DY_BDC_QZPRINT").removeAttr("disabled");
                    //
                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");
                    $("input[name='DY_BDCQZBSM']").removeAttr("disabled");
                    $("input[name='DY_BDCQZBSM']").addClass(" validate[required]");
                    $("#qzbsmsavebtn").attr("style","");
                    $("#qzbsmsavebtn").removeAttr("disabled");

                    showDjzm();

					/*隐藏登记缴费和登记发证信息*/
					$("#djjfxx_zydj").hide();
					$("#djfzxx_zydj").hide();

					$("#djshOpinion").show();
					$("#djshCsOpinionInfo").show();
					$("#djshHzOpinionInfo").show();
					$("#saveDjshOpinionx").hide();
					$("#djshOpinion textarea").each(function (index) {
						$(this).attr("disabled","disabled");
					});

					$("#taxRelatedInfo").show();
					$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
						$(this).attr("disabled", true);
					})

                }else if(BDC_OPTYPE == "2"){
                    $("#bdcqzh_tr").attr("style","");
                    $("#BDC_DBBTN").remove();
                    $("#bdcqzbsm_tr").attr("style","");
                    $("#dy_bdcqzbsm_tr").attr("style","");
                    $("#bdcczr_tr").attr("style","");
                    $("#qzbsmsavebtn").remove();
                    //证书预览
                    $("#BDC_QZVIEW").attr("style","");
                    $("#BDC_QZVIEW").removeAttr("disabled");
                    //登记证书预览
                    $("#DY_BDC_QZVIEW").attr("style","");
                    $("#DY_BDC_QZVIEW").removeAttr("disabled");

                    //登记缴费和发证信息
                    $("#djjfxx_zydj").attr("style","");
                    $("#djfzxx_zydj").attr("style","");

                    $("#djjfxx_zydj input").each(function(index){
                        $(this).removeAttr("disabled");
	                });
	                $("#djjfxx_zydj select").each(function(index){
	                    $(this).removeAttr("disabled");
	                });

	                $("#djfzxx_zydj input").each(function(index){
                        $(this).removeAttr("disabled");
                    });
                    $("#djfzxx_zydj select").each(function(index){
                        $(this).removeAttr("disabled");
                    });

                    var currentUser = $("input[name='uploadUserName']").val();
                    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
                    $("input[name='DJFZXX_FZRY']").val(currentUser);
                    $("input[name='DJFZXX_FZSJ']").val(currentTime);
                    $("input[name='DJJFMX_SFRQ']").val(currentTime);

					$("#djshOpinion").show();
					$("#djshCsOpinionInfo").show();
					$("#djshHzOpinionInfo").show();
					$("#saveDjshOpinionx").hide();
					$("#djshOpinion textarea").each(function (index) {
						$(this).attr("disabled","disabled");
					});

					$("#taxRelatedInfo").show();
					$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
						$(this).attr("disabled", true);
					})
                }else if(BDC_OPTYPE == "flag1"){
                    $("#bdcqzh_tr").attr("style","");
                    $("#BDC_DBBTN").remove();
                    $("#bdcqzbsm_tr").attr("style","");
                    $("#bdcczr_tr").attr("style","");
                    $("#dy_bdcqzbsm_tr").attr("style","");
                    //权证打印按钮
                    $("#BDC_QZVIEW").attr("style","");
                    //$("#BDC_QZPRINT").attr("style","");
                    $("#BDC_QZVIEW").removeAttr("disabled");
                    //$("#BDC_QZPRINT").removeAttr("disabled");
                    //登记证明打印与预览按钮
                    $("#DY_BDC_QZVIEW").attr("style","");
                    //$("#DY_BDC_QZPRINT").attr("style","");
                    $("#DY_BDC_QZVIEW").removeAttr("disabled");
                    //$("#DY_BDC_QZPRINT").removeAttr("disabled");
                    //
                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");
                    //$("input[name='DY_BDCQZBSM']").removeAttr("disabled");
                    $("input[name='DY_BDCQZBSM']").addClass(" validate[required]");
                    //$("#qzbsmsavebtn").attr("style","");
                    //$("#qzbsmsavebtn").removeAttr("disabled");

                    showDjzm();

					/*隐藏登记缴费和登记发证信息*/
					$("#djjfxx_zydj").hide();
					$("#djfzxx_zydj").hide();

					$("#djshOpinion").show();
					$("#djshCsOpinionInfo").show();
					$("#djshHzOpinionInfo").show();
					$("#saveDjshOpinionx").hide();
					$("#djshOpinion textarea").each(function (index) {
						$(this).attr("disabled","disabled");
					});

					$("#taxRelatedInfo").show();
					$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
						$(this).attr("disabled", true);
					})
                }else if(BDC_OPTYPE == "flag2"){
                    $("#bdcqzh_tr").attr("style","");
                    $("#BDC_DBBTN").remove();
                    $("#bdcqzbsm_tr").attr("style","");
                    $("#dy_bdcqzbsm_tr").attr("style","");
                    $("#bdcczr_tr").attr("style","");
                    $("#qzbsmsavebtn").remove();
                    //证书预览
                    $("#BDC_QZVIEW").attr("style","");
                    $("#BDC_QZVIEW").removeAttr("disabled");
                    //登记证书预览
                    $("#DY_BDC_QZVIEW").attr("style","");
                    $("#DY_BDC_QZVIEW").removeAttr("disabled");

                    //登记缴费和发证信息
                    $("#djjfxx_zydj").attr("style","");
                    $("#djfzxx_zydj").attr("style","");

					$("#djshOpinion").show();
					$("#djshCsOpinionInfo").show();
					$("#djshHzOpinionInfo").show();
					$("#saveDjshOpinionx").hide();
					$("#djshOpinion textarea").each(function (index) {
						$(this).attr("disabled","disabled");
					});

					$("#taxRelatedInfo").show();
					$("#taxRelatedInfo input , #taxRelatedInfo select").each(function () {
						$(this).attr("disabled", true);
					})
                }
            }

            
        }else{
			//动态切换抵押信息
			queryTypeFn();
            $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
            //默认抵押情况栏中抵押权人证件类型为'统一社会信用代码'
            $("select[name='POWERSOURCE_DYQRZJLX']").val("统一社会信用代码");
            $("input[name='FW_DJYY']").val("转移登记");
            $("input[name='DY_DJJG']").val("平潭综合实验区不动产登记与交易");
            $("input[name='DY_DYDJYY']").val("预购商品房抵押权预告登记转抵押权首次登记");
            setSfzgedy("0");
            /*若是有开发商标志，则保存*/
			var isKfsywsl = '${param.isKfsywsl}';
			if (isKfsywsl && isKfsywsl == '1') {
				$("#IS_KFSYWSL").val('1');
			}
			/*权属来源手动填写按钮*/
			$("#sdtxBtn").show();
        }

        var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
        if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems&&dealItems!=""){
            if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
                $("input[name='JBR_NAME']").removeAttr('readonly');
            }
        }
    }
    
    //初始化材料列表
    //AppUtil.initNetUploadMaters({
    //  busTableName:"T_BDCQLC_GYJSJFWZYDJ"
    //});
    
    //初始化字段不可修改
     $("select[name='POWERSOURCE_FRZJLX']").attr('disabled','disabled');
     
    
    $("#djgdxx_zydj").attr("style","display:none;");
    
  	//默认房源信息-当期应收款所属月份值
	var SSDJ_FYXX_DQYSKSSYF = $("input[name='SSDJ_FYXX_DQYSKSSYF']").val();
	if(SSDJ_FYXX_DQYSKSSYF==null || SSDJ_FYXX_DQYSKSSYF=="null" || SSDJ_FYXX_DQYSKSSYF==""){
		var date = new Date();
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		month =(month<10 ? "0"+month:month);
		var mydate = (year.toString()+"-"+month.toString());
		$("input[name='SSDJ_FYXX_DQYSKSSYF']").val(mydate);
	}
	
	//计算房源信息-单价值
	var SSDJ_FYXX_DJ = $("input[name='SSDJ_FYXX_DJ']").val();
	if(SSDJ_FYXX_DJ==null || SSDJ_FYXX_DJ=="null" || SSDJ_FYXX_DJ=="" || SSDJ_FYXX_DJ=="NaN"){
		var SSDJ_FYXX_JYJG = $("input[name='SSDJ_FYXX_JYJG']").val();
		var SSDJ_FYXX_JZMJ = $("input[name='SSDJ_FYXX_JZMJ']").val();
		var SSDJ_FYXX_DJ = (SSDJ_FYXX_JYJG/SSDJ_FYXX_JZMJ).toFixed(2);
		$("input[name='SSDJ_FYXX_DJ']").val(SSDJ_FYXX_DJ);
	}
});



/********************************身份证读卡开始**********************************/
function LZRRead(){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0) {
        GT2ICROCX.ReadCard();
        $("input[name='QZR_NAME']").val(GT2ICROCX.Name);
        $("input[name='QZR_ZJH']").val(GT2ICROCX.CardNo);
    }
}
/********************************身份证读卡结束**********************************/
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
    <style>
		.sel{
			border:solid 1px red;
		}
		.tab_text {
		    width: 350px;
		    height: 24px;
		    line-height: 24px;
		    /* padding: 0 5px; */
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
    </style>
</head>

<body>
	<input type="hidden" id="sxtsx" name="sxtsx" value="0"/>
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_GYJSJFWZYDJ_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="uploadUserId"
			value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
			name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
		<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
			type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
			type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
			type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BELONG_DEPTNAME"
			value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
			value="${serviceItem.SXLX}" /> <input type="hidden"
			name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
	    <input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
	    <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />   		
		<!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->	
		<input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		
		<%-- 权利人信息明细 --%>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<%-- 权属来源信息明细 --%>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<%-- 权属来源限制明细 --%>
		<input type="hidden" name="POWERLIMITINFO_JSON" id="POWERLIMITINFO_JSON"/>
		<%-- 当前可打印的阅办模板列表 --%>
		<input type="hidden" name="TEMPLATE_LIST" id="TEMPLATE_LIST"/>
		<%--是否开发商业务办理--%>
		<input type="hidden" name="IS_KFSYWSL" id="IS_KFSYWSL"/>
		<%--是否开发商业务办理（url）--%>
		<input type="hidden" name="isKfsywsl" id="isKfsywsl" value="${param.isKfsywsl}"/>
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td > 所属部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面x --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>
		
		<%--开始引入权属来源信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQslyType1.jsp" />
		<%--结束引入权属来源信息--%>
		
		<%--开始引入权利限制信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlxz.jsp" />
		<%--结束引入权利限制信息--%>
		
		<%--开始引入抵押情况信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/T_ESTATE_ZYDJ_OBLIGEEINFO.jsp" />
		<%--结束引入抵押情况信息--%>
		
		<%--开始引入宗地信息-国有权力人信息--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--开始引入宗地信息-国有权力人信息--%>

		<%--开始引入房屋基本信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcFwjbxx.jsp" />
		<%--开始引入房屋基本信息--%>
		
		<%--开始引入登记审核信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcDjshxx.jsp" />
		<%--开始引入登记审核信息--%>

		<%--涉税登记信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/taxRelatedInfo.jsp"/>

		<%--引入登记审核意见信息--%>
		<jsp:include page="./bdcqlc/bdcDjshOpinion.jsp"/>

		<%--不动产EMS模块--%>
		<jsp:include page="./bdcqlc/bdcEmsSend.jsp"/>

		<%--开始不动产询问记录基本信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/bdcXwjl.jsp" />
		<%--开始不动产询问记录基本信息--%>

		<%--开始银行询问记录基本信息--%>
		<jsp:include page="./bdcqlc/gyjsjfwzydj/yhXwjl.jsp" />
		<%--开始银行询问记录基本信息--%>

<%--		&lt;%&ndash;开始银行询问记录基本信息&ndash;%&gt;--%>
<%--		<jsp:include page="./bdcqlc/gyjsjfwzydj/tyXwjl.jsp" />--%>
		
	    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
	    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   
	    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
	        <jsp:param value="zydj" name="domId" />
	        <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
	    </jsp:include>
	    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>

		<jsp:include page="./bdcqlc/bdcRemark.jsp" />

	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0"  type="hidden"
			CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
			CODEBASE="IdrOcx.cab#version=1,0,1,2">			
			</OBJECT>	
</html>
