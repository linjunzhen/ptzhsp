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
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script>   
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcEmsSend.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/gyjsscdj/js/gyjsscdj.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcQlrxx.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/js/gyjsydsyqjfwsyqbgdj.js"></script> 
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcQslyNew.js"></script> --%>

<script type="text/javascript">
	$(function() {

		//初始化下拉框动态选值（宗地基本信息-土地用途、坐落）
		initCombox();

		<%-- 登记缴费信息、发证、归档信息隐藏 --%>
	    $("#djjfxx_scdj").attr("style","display:none;");
    	$("#djfzxx_scdj").attr("style","display:none;");
    	$("#djgdxx_scdj").attr("style","display:none;");
    	
		/*审批表在开始环节无法打印*/
		$("#SPBDF").attr("onclick", "notPrint();");
		$("#SPBSF").attr("onclick", "notPrint();");
		
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if (flowSubmitObj) {
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_GYJSSCDJ_FORM");
			//初始化表单字段值
			if (flowSubmitObj.busRecord) {
				initAutoTable(flowSubmitObj); //初始化JSON格式信息
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_GYJSSCDJ_FORM");
				disabledDbBtn("BDC_DBBTN");
				if( flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
					//除开始环节外，审批表均可进行打印
					$("#printBtn").show();
					$("#SPBDF").attr("onclick","goPrintTemplate('GYJSSCDJSPB','3')");
					$("#SPBSF").attr("onclick","goPrintTemplate('GYJSSCDJSPB','3')");
					//开始、受理环节之后数据均不可编辑
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="受理" || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
						$("#T_BDCQLC_GYJSSCDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");
						//a标签按钮禁用
				 		//$("#dfspb").find(".easyui-linkbutton").attr("disabled",true).css("pointer-events","none");		 	
					}
					if($("input[name='SBMC']").val()){
					}else{						
					  $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					}
				}
				
				
				/*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
				if (flowSubmitObj.busRecord.ZD_TDYT) {
					$("#ZD_TDYT").combobox("setValues", flowSubmitObj.busRecord.ZD_TDYT.split(","));
				}
				
				//坐落-乡
				if (flowSubmitObj.busRecord.ZDZL_XIAN) {
					$("#ZDZL_XIAN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_XIAN);
				}
				//坐落-镇
				if (flowSubmitObj.busRecord.ZDZL_ZHENG) {
					$("#ZDZL_ZHENG").combobox("setValue", flowSubmitObj.busRecord.ZDZL_ZHENG);
				}
				//坐落-村
				if (flowSubmitObj.busRecord.ZDZL_CUN) {
					$("#ZDZL_CUN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_CUN);
				}
				
				//初审环节
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
					$("input[name='ESTATE_NUM']").attr("disabled", false);
					$("input[name='LOCATED']").attr("disabled", false);
					/* $("#qslyxx input , #qslyxx select").each(function () {
						$(this).attr("disabled", false);
					})
					 */
				}
				
				//登簿环节确认登簿
				if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {					
					$("#bdcqzh_tr").attr("style","");
		            $("#BDC_DBBTN").removeAttr("disabled");	
		            $("#bdcczr_tr").attr("style",""); 
				}
				
				//若从后台收费发证功能进入
	             var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	             if(BDC_OPTYPE != null && BDC_OPTYPE !=""){	 	             	
	                //填写权证标识码
	                if(BDC_OPTYPE == "1"){//缮证（权证打印）                    
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");
	                   
	                    //权证打印、预览按钮
	                    $("#BDC_QZVIEW").attr("style","");
	                    //$("#BDC_QZPRINT").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");
	                    //$("#BDC_QZPRINT").removeAttr("disabled");
	                   
	                    //权证标识码
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                  
	                    $("#qzbsmsavebtn").attr("style","");
	                    $("#qzbsmsavebtn").removeAttr("disabled");

	                }else if(BDC_OPTYPE == "2"){//打证（缴费发证）	                     
	                   	$("#bdcqzh_tr").attr("style","");
	                   	$("#BDC_DBBTN").remove();
	                   	$("#bdcqzbsm_tr").attr("style","");	                  
	                  	$("#bdcczr_tr").attr("style","");
	                   	$("#qzbsmsavebtn").remove();
	                   	//证书预览
	                  	$("#BDC_QZVIEW").attr("style","");
	                   	$("#BDC_QZVIEW").removeAttr("disabled");
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_scdj").attr("style","");
    					$("#djfzxx_scdj").attr("style","");
    					$("#djgdxx_scdj").attr("style","");	                   	    					
    					$("#djjfxx_scdj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djfzxx_scdj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djgdxx_scdj").find("input,select,.Wdate").removeAttr("disabled");	                  
	                   
	                    var currentUser = $("input[name='uploadUserName']").val();
	                    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	                    $("input[name='DJFZXX_FZRY']").val(currentUser);
	                    $("input[name='DJFZXX_FZSJ']").val(currentTime);
	                    $("input[name='DJJFMX_SFRQ']").val(currentTime);
	                }else if(BDC_OPTYPE == "flag1"){//权证打印 -我的打证                 	                   
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");	                   
	                    //权证预览按钮
	                    $("#BDC_QZVIEW").attr("style","");	                   
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                    
	                    //权证标识码
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                    
	                    //$("#qzbsmsavebtn").attr("style","");
	                    //$("#qzbsmsavebtn").removeAttr("disabled");	                
	                }else if(BDC_OPTYPE == "flag2"){//缴费发证 -我的发证                   	                      					
    					$("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");	                   
	                    $("#bdcczr_tr").attr("style","");
	                    $("#qzbsmsavebtn").remove();
	                    //证书预览
	                    $("#BDC_QZVIEW").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                   
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_scdj").attr("style","");
    					$("#djfzxx_scdj").attr("style","");
    					$("#djgdxx_scdj").attr("style","");
		             }
	            }
				
				
				//办结
				var isEndFlow = false;
				if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
					isEndFlow = true;
				}
				// 流程办理结束页面只读
				if (isEndFlow) {
					$("#T_BDCQLC_GYJSSCDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");
					$("#bdcqzh_tr").attr("style","");
	                $("#BDC_DBBTN").remove();
	                $("#bdcqzbsm_tr").attr("style","");	                
	                $("#bdcczr_tr").attr("style","");
	                $("#qzbsmsavebtn").remove();
	                //证书预览
	                $("#BDC_QZVIEW").attr("style","");
	                $("#BDC_QZVIEW").removeAttr("disabled");
	               
	                //登记缴费、发证、归档信息
                    $("#djjfxx_scdj").attr("style","");
    				$("#djfzxx_scdj").attr("style","");
    				$("#djgdxx_scdj").attr("style","");
				}
		
				var isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
				//设置文件是否合规
				if (isAuditPass == "-1") {
					$("input:radio[name='isAuditPass'][value='-1']").attr("checked", true);
				}
			} else {
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
				 //1、权利人证件类型默认"工商营业执照"；法定代表人、代理人证件类型默认为"身份证"可修改。
                 $("select[name='POWERPEOPLEIDTYPE']").val("YYZZ");
                 //$("select[name='POWERPEOPLEIDTYPE']").attr("disabled","disabled"); 
                 $("select[name='POWLEGALIDTYPE']").val("SF");                   
                 $("select[name='POWAGENTIDTYPE']").val("SF"); 
			}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
				$("#ptcyjb").attr("style", "");
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
		}
	});
	
        //------------------权证打印字段控制-------------------------
		function isqzdy(flowSubmitObj) {
            $("input[name='BDC_QZBSM']").removeAttr("disabled");
            var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
            if (BDC_OPTYPE) {
                if (BDC_OPTYPE == '1' || BDC_OPTYPE == 'flag1') {
                    $("#zsyl").show();
                    $("#zsdy").show();
                } else if (BDC_OPTYPE == '2' || BDC_OPTYPE == 'flag2') {
                    /*展示登记信息*/
                    $("#djjfxx").show();
                    $("#djfzxx").show();
                    $("#djgdxx").show();
                    $("#djjfxx select,#djjfxx input").each(function (index) {
                        $(this).attr("disabled", false);
                    });
                    $("#djfzxx select,#djfzxx input").each(function (index) {
                        $(this).attr("disabled", false);
                    });
                    $("#djgdxx select,#djgdxx input").each(function (index) {
                        $(this).attr("disabled", false);
                    });
                }
            }
		}
        //------------------权证打印字段控制-------------------------
		
		function isPowerPeople() {
			var powerPeopleInfoSn = $("input[name='POWERPEOPLEINFO_JSON']").val();
			var sqrmc = $("input[name='SQRMC']").val();
			var flag = false;
			$("input[name='POWERPEOPLENAME']").each(function(j, item) {
				console.log("value值:" + item.value);
				if (item.value != "") {
					if (sqrmc.indexOf(item.value) > -1) {
						flag = true;
						return true;
					}
				}
			});
			$("input[name='POWLEGALNAME']").each(function(j, item) {
				console.log("value值:" + item.value);
				if (item.value != "") {
					if (sqrmc.indexOf(item.value) > -1) {
						flag = true;
						return true;
					}
				}
			});
			$("input[name='POWAGENTNAME']").each(function(j, item) {
				console.log("value值:" + item.value);
				if (item.value != "") {
					if (sqrmc.indexOf(item.value) > -1) {
						flag = true;
						return true;
					}
				}
			});
			if (flag) {
				return true;
			} else {
				if (powerPeopleInfoSn.indexOf(sqrmc) > -1) {
					return true;
				} else {
					return false;
				}
			}
		}
	
	
	
		function setFileNumber(serialNum) {
			var enterprise = '${execution.SQJG_CODE}';
			var idCard = '${execution.SQRSFZ}';
			var fileNum;
			if (enterprise != "") {
				fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
			} else {
				fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
			}
			$("#fileNumber").val(fileNum);
		}
		
		/**=================权利信息开始================================*/
		function changePower(val) {
			if (val == "0") {
				$("input[name='POWERPEOPLEPRO']").val("100");
			} else {
				$("input[name='POWERPEOPLEPRO']").val("");
			}
		}
		var powerInfoSn = 1;
		function addPowerInfo() {
			powerInfoSn = powerInfoSn + 1;
			var table = document.getElementById("powerInfo");
			var trContent = table.getElementsByTagName("tr")[1];
			var trHtml = trContent.innerHTML;
			var findex = trHtml.indexOf("deletePowerInfo('");
			if (powerInfoSn > 10) {
				var replacestr = trHtml.substring(findex, findex + 21);
			} else {
				var replacestr = trHtml.substring(findex, findex + 20);
			}
			trHtml = trHtml.replace(replacestr, "deletePowerInfo('" + powerInfoSn + "')");
			trHtml = "<tr id=\"powerInfo_" + powerInfoSn + "\">" + trHtml + "</tr>";
			$("#powerInfo").append(trHtml);
		}
	
		function deletePowerInfo(idSn) {
			var table = document.getElementById("powerInfo");
			if (table.rows.length == 2) {
				parent.art.dialog({
					content : "最少一个权利人信息！",
					icon : "warning",
					ok : true
				});
				return false;
			}
			$("#powerInfo_" + idSn).remove();
		}
	
		function getPowerInfoJson() {
			var datas = [];
			var table = document.getElementById("powerInfo");
			for (var i = 1; i <= table.rows.length - 1; i++) {
				var trData = {};
				$("#powerInfo_" + i).find("*[name]").each(function() {
					var inputName = $(this).attr("name");
					var inputValue = $(this).val();
					//获取元素的类型
					var fieldType = $(this).attr("type");
					if (fieldType == "radio") {
						var isChecked = $(this).is(':checked');
						if (isChecked) {
							trData[inputName] = inputValue;
						}
					} else if (fieldType == "checkbox") {
						var inputValues = FlowUtil.getCheckBoxValues(inputName);
						trData[inputName] = inputValues;
					} else {
						trData[inputName] = inputValue;
					}
				});
				datas.push(trData);
			}
			$("input[type='hidden'][name='POWERINFO_JSON']").val(JSON.stringify(datas));
		}
		/**=================权利信息结束================================*/
		/**=================权利人信息开始================================*/
		var powerPeopleInfoSn = 1;
		function addPowerPeopleInfo() {
			powerPeopleInfoSn = powerPeopleInfoSn + 1;
			var table = document.getElementById("powerPeopleInfo");
			var trContent = table.getElementsByTagName("tr")[1];
			var trHtml = trContent.innerHTML;
			var findex = trHtml.indexOf("deletePowerPeopleInfo('");
			if (powerPeopleInfoSn > 10) {
				var replacestr = trHtml.substring(findex, findex + 27);
			} else {
				var replacestr = trHtml.substring(findex, findex + 26);
			}
			trHtml = trHtml.replace(replacestr, "deletePowerPeopleInfo('" + powerPeopleInfoSn + "')");
			trHtml = "<tr id=\"powerPeopleInfo_" + powerPeopleInfoSn + "\">" + trHtml + "</tr>";
			$("#powerPeopleInfo").append(trHtml);
		}
	
		function deletePowerPeopleInfo(idSn) {
			var table = document.getElementById("powerPeopleInfo");
			if (table.rows.length == 2) {
				parent.art.dialog({
					content : "最少一个权利人信息！",
					icon : "warning",
					ok : true
				});
				return false;
			}
			$("#powerPeopleInfo_" + idSn).remove();
		}

		function getPowerPeopleInfoJson() {
			var datas = [];
			$("#PowerPeopleTable .bdcdydacxTr").each(function() {
				var iteminfo = $(this).find("input[name='PowerPeopleTableTrMx']").val();
				datas.push(JSON.parse(iteminfo));
			});
			$("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(JSON.stringify(datas));
		}
	
		/**=================权利人信息开始================================*/
	
		// 确认登簿后赋值登簿人，审核人，登簿时间，审核日期
		function doBooking() {
			$("#POWER_DBR").val('${sessionScope.curLoginUser.fullname}');
			$("#POWER_DBSJ").val('${nowTime}');
			BdcUtil.doBooking();
		}
	
		// 证明预览
		function provePreview() {
			alert("未开发");
		}
	
		/**=================权属来源信息开始================================*/
		var powerSourceInfoSn = 1;
		function addPowerSourceInfo() {
			powerSourceInfoSn = powerSourceInfoSn + 1;
			var table = document.getElementById("powerSourceInfo");
			var trContent = table.getElementsByTagName("tr")[1];
			var trHtml = trContent.innerHTML;
			var findex = trHtml.indexOf("deletePowerSourceInfo('");
			if (powerSourceInfoSn > 10) {
				var replacestr = trHtml.substring(findex, findex + 27);
			} else {
				var replacestr = trHtml.substring(findex, findex + 26);
			}
			trHtml = trHtml.replace(replacestr, "deletePowerSourceInfo('" + powerSourceInfoSn + "')");
			trHtml = "<tr id=\"powerSourceInfo_" + powerSourceInfoSn + "\">" + trHtml + "</tr>";
			$("#powerSourceInfo").append(trHtml);
		}
	
		function deletePowerSourceInfo(idSn) {
			var table = document.getElementById("powerSourceInfo");
			if (table.rows.length == 2) {
				parent.art.dialog({
					content : "最少一个权属来源信息！",
					icon : "warning",
					ok : true
				});
				return false;
			}
			$("#powerSourceInfo_" + idSn).remove();
		}
	
		function getPowerSourceInfoJson() {
			var datas = [];
			var table = document.getElementById("PowerSourceInfo");
			for (var i = 1; i <= table.rows.length; i++) {
				var trData = {};
				$("#PowerSourceInfo_" + i).find("*[name]").each(function() {
					var inputName = $(this).attr("name");
					var inputValue = $(this).val();
					//获取元素的类型
					var fieldType = $(this).attr("type");
					if (fieldType == "radio") {
						var isChecked = $(this).is(':checked');
						if (isChecked) {
							trData[inputName] = inputValue;
						}
					} else if (fieldType == "checkbox") {
						var inputValues = FlowUtil.getCheckBoxValues(inputName);
						trData[inputName] = inputValues;
					} else {
						trData[inputName] = inputValue;
					}
				});
				datas.push(trData);
			}
			$("input[type='hidden'][name='POWERSOURCEINFO_JSON']").val(JSON.stringify(datas));
		}
		/**=================权属来源信息开始================================*/


	//------------------------------------身份身份证读取开始---------------------------------------------------
	function MyGetData() //OCX读卡成功后的回调函数
	{
		// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>		
		// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
	}

	function MyClearData() //OCX读卡失败后的回调函数
	{
		alert("未能有效识别身份证，请重新读卡！");
		$("input[name='POWERPEOPLENAME']").val("");
		$("input[name='POWERPEOPLEIDNUM']").val("");
	}

	function MyGetErrMsg() //OCX读卡消息回调函数
	{
		// 			Status.value = GT2ICROCX.ErrMsg;	   
	}

	function LZRRead(){//领证人读卡
	    GT2ICROCX.PhotoPath = "c:"
	    //GT2ICROCX.Start() //循环读卡
	    //单次读卡(点击一次读一次)
	    if (GT2ICROCX.GetState() == 0) {
	        GT2ICROCX.ReadCard();
	        $("input[name='QZR_NAME']").val(GT2ICROCX.Name);
	        $("input[name='QZR_ZJH']").val(GT2ICROCX.CardNo);
	    }
	}
	
	function PowerPeopleRead(o) //开始读卡
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
			/* $("[name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
			$("[name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo); */
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
	//------------------------------------不动产档案信息查询---------------------------------------------------
function showSelectBdcdaxxcx_TBG() {
	$.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
		title : "不动产档案信息查询",
		width : "100%",
		lock : true,
		resize : false,
		height : "100%",
		close : function() {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo) {
				var html = "";
				for (var i = 0; i < bdcdaxxcxInfo.length; i++) {
					html += '<tr class="bdcdaxxcxTr" id="bdcdaxxcxTr_' + bdcdaxxcxTr + '">';
					html += '<input type="hidden" name="bdcdaxxcx"/>';
					html += '<td style="text-align: center;">' + bdcdaxxcxTr + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCDYH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLRMC + '</td>';
					html += '<td>不动产权证书</td>';
// 					html += '<td>' + bdcdaxxcxInfo[i].ZH + '</td>';
// 					html += '<td>' + bdcdaxxcxInfo[i].HH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QSZT + '</td>';
					html += '<td>' + bdcdaxxcxInfo[i].QLQSSJ + '</td>';
					html += '<td>';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delTableTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html += '</tr>';
					$("#PowerSourceTable").append(html);
					$("#bdcdaxxcxTr_" + bdcdaxxcxTr + " input[name='bdcdaxxcx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
					html="";
					bdcdaxxcxTr++;
				}
				var data=bdcdaxxcxInfo[0];
				data.ESTATE_NUM=data.BDCDYH,
					data.LOCATED=data.FDZL,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					data.POWERSOURCE_QLRMC=data.QLRMC,
					data.POWERSOURCE_QSZT=data.QSZT,//权属状态
					data.POWERSOURCEIDTYPE=data.QSZT,//权属状态
					data.POWERSOURCE_ZJLX=data.ZJLX,
					data.POWERSOURCE_ZJHM=data.ZJHM,
					data.POWERSOURCE_ZDDM=data.ZDDM,
					data.POWERSOURCE_EFFECT_TIME=data.QLQSSJ,//权利开始时间
					data.ZQQ_QLQSSJ=data.QLQSSJ,//权利开始时间
					data.POWERSOURCE_CLOSE_TIME1=data.QLJSSJ,//权利结束时间
					data.ZQQ_QLJSSJ1=data.QLJSSJ,//权利结束时间
					data.POWERSOURCE_CLOSE_TIME2=data.QLJSSJ,//权利结束时间
					data.ZQQ_QLJSSJ2=data.QLJSSJ,//权利结束时间
					data.ZD_BM=data.ZDDM,
					data.ZQQ_ZDDM=data.ZDDM,
					data.ZD_QLLX=data.ZDQLLX,//宗地权利类型
					data.ZQQ_ZDDZM=data.ZDTZM,
					data.ZD_QLSDFS=data.QLSDFS,
					data.ZQQ_QLSDFS=data.QLSDFS,
					data.ZD_ZL=data.TDZL,
					data.ZD_MJ=data.ZDMJ,
					data.ZD_MJ=data.ZQQ_ZDMJ,
					data.ZD_TDYT=data.TDYT,
					data.ZQQ_TDYT=data.TDYT,
					data.ZD_YTSM=data.TDYTSM,
					data.ZQQ_YTSM=data.TDYTSM,
					data.ZD_QLXZ=data.QLXZ,//权利性质
					data.ZQQ_QLXZ=data.QLXZ,//权利性质
					data.ZD_LEVEL=data.DJ,
					data.ZD_RJL=data.RJL,
					data.ZD_JZXG=data.JZXG,
					data.ZD_JZMD=data.JZMD,
					data.ZD_E=data.TD_SZ_D,
					data.ZD_W=data.TD_SZ_X,
					data.ZD_N=data.TD_SZ_B,
					data.ZD_S=data.TD_SZ_N,
					data.ZQQ_E=data.TD_SZ_D,
					data.ZQQ_W=data.TD_SZ_X,
					data.ZQQ_N=data.TD_SZ_B,
					data.ZQQ_S=data.TD_SZ_N,

					data.BDC_QLKSSJ=DateConvertFun(data.QLQSSJ),
					data.BDC_QLJSSJ1=DateConvertFun(data.QLJSSJ1),
					data.BDC_QLJSSJ2=DateConvertFun(data.QLJSSJ2),

					data.GYTD_BEGIN_TIME=DateConvertFun(data.QLQSSJ),
					data.GYTD_END_TIME1=DateConvertFun(data.QLJSSJ1),
					data.GYTD_END_TIME2=DateConvertFun(data.QLJSSJ2),
					data.GYTD_GYFS=data.GYFS,
					data.GYTD_QDJG=data.JG,
					data.GYTD_SYQMJ=data.SYQMJ,
					data.ZQQ_SYQMJ=data.SYQMJ,
					data.GYTD_QSZT=data.QSZT,
					data.FW_ZL=data.FDZL,
					data.FW_ZH=data.ZH,
					data.FW_SZC=data.SZC,
					data.FW_HH=data.HH,
					data.FW_ZCS=data.ZCS,
					data.FW_GHYT=data.FW_GHYT,
					data.FW_YTSM=data.GHYTSM,
					data.FWXZ=data.FWXZ,
					data.FW_XZ=data.FWXZ,
					data.FW_FWJG=data.FWJG,//房屋结构
					data.FW_JYJG=data.JYJG,//交易价格
					data.FW_DYDYTDMJ=data.DYTDMJ,
					data.FW_FTTDMJ=data.FTTDMJ,
					data.FW_JZMJ=data.JZMJ,
					data.FW_GYQK=data.GYFS,//房屋共有情况
					data.ZQQ_GYFS=data.GYFS,
					data.FW_ZYJZMJ=data.ZYJZMJ,
					data.FW_FTJZMJ=data.ZYJZMJ,
					data.FW_QLLX=data.FW_QLLX,
					data.POWERSOURCE_DYH=data.BDCDYH,
					data.POWERSOURCE_QSWH=data.BDCQZH,//证书文号（权属来源）
					data.POWERSOURCEIDNUM=data.BDCQZH,
					data.POWERSOURCE_QSLYLX="3"//证书文号（权属来源）
					data.BDCDYH=data.BDCDYH,//不动产单元号
					data.ZDDM=data.ZDDM,//宗地代码
					data.FWBM=data.FWBM,//房屋编码
					data.YWH=data.YWH,//业务号
					data.ZXYWH=data.ZXYWH,//注销业务号
					data.GLH=data.GLH//关联号
				FlowUtil.initFormFieldValue(data,"T_BDCQLC_GYJSSCDJ_FORM");
				formatDic("ZD_TZM", data.ZDTZM); //宗地特征码
				art.dialog.removeData("bdcdaxxcxInfo");
			}
		}
	}, false);
}
</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//设置回调函数
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
	<form id="T_BDCQLC_GYJSSCDJ_FORM" method="post">
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
		
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="POWERINFO_JSON" id="POWERINFO_JSON"/>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
        <!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->   
        <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
        
        <input type="hidden" name="ISQCWB" value="${busRecord.ISQCWB}">


		<%--===================重要的隐藏域内容=========== --%>
		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/gyjsscdj/T_ESTATE_SCDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>
		
		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入权利信息--%>
		<%-- <jsp:include page="./bdcqlc/bdcQlxx.jsp" /> --%>
		<%--结束引入权利信息--%>

		<%--开始引入权利人信息 --%>
		<jsp:include page="./bdcqlc/gyjsscdj/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>

		<%--开始引入权属来源信息--%>
		<%-- <jsp:include page="./bdcqlc/bdcQslyNew_GYJSSCDJ.jsp" /> --%>
		<jsp:include page="./bdcqlc/gyjsscdj/bdcQsly.jsp" />
		<%--开始引入权属来源信息--%>

		<%--开始引入宗地信息-国有权力人信息--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--开始引入宗地信息-国有权力人信息--%>

		<%--开始登簿审核信息  page="./bdcqlc/gyjsscdj/bdcDjshxx.jsp--%>
		 <%-- <jsp:include page="./bdcqlc/bdcDjshxx.jsp" />  --%>
		<%--开始登簿审核信息--%>
		
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
		
		<%--开始登记审核意见信息（不动产含初审->登簿环节通用）--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" />
		<%--结束登记审核意见信息（不动产通用）--%>

		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
	    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   
	    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
	        <jsp:param value="scdj" name="domId" />
	        <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
	    </jsp:include>
	    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
