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
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcEmsSend.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/bdcqlcygspfygdj/js/bdcqlcygspfygdj.js"></script>
</head>

<style>
    /* .tab_text{
        width: 270px;
        height: 24px;
        line-height: 24px;
        padding: 2px 3px 2px 1px;
        border: 1px solid #bbb;
    } */
	.eflowbutton{
		  background: #3a81d0;
		  border: none;
		  padding: 0 10px;
		  line-height: 26px;
		  cursor: pointer;
		  height:26px;
		  color: #fff;
		  border-radius: 5px;
		  
	}
	.eflowbutton-disabled{
		  background: #94C4FF;
		  border: none;
		  padding: 0 10px;
		  line-height: 26px;
		  cursor: pointer;
		  height:26px;
		  color: #E9E9E9;
		  border-radius: 5px;
		  
	}
	/* .bsboxC{		
        padding: 25px 30px!important;
	} */
	/* select{
		width: 275px!important;
		height: 28px!important;
		line-height: 28px!important;
	} */
</style>

<script>
    $(function () {
        //初始化验证引擎的配置
        $.validationEngine.defaults.autoHidePrompt = true;
        $.validationEngine.defaults.promptPosition = "topRight";
        $.validationEngine.defaults.autoHideDelay = "2000";
        $.validationEngine.defaults.fadeDuration = "0.5";
        $.validationEngine.defaults.autoPositionUpdate = true;
		//字段权限控制
		initFieldControl();
        //var userType = '${sessionScope.curLoginMember.USER_TYPE}';
        //initUserForm(userType);
        //获取流程信息对象JSON
        var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
        if (EFLOW_FLOWOBJ) {
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
            if (flowSubmitObj.busRecord) {
                FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_YGSPFYGDJ_FORM");
                initAutoTable(flowSubmitObj);//初始化权利信息
                //queryTypeFn();
                //checkGyfs(flowSubmitObj.busRecord.TY_XWJL2);//初始化所有情况
                /*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/

                if (flowSubmitObj.busRecord.RUN_STATUS) {
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {						
						$('#T_BDCQLC_YGSPFYGDJ_FORM').find('input,textarea').attr("readonly", true);
						$('#T_BDCQLC_YGSPFYGDJ_FORM').find('input,textarea').attr("disabled", "disabled");
						$('#T_BDCQLC_YGSPFYGDJ_FORM').find('select').attr("disabled", "disabled");
						$('#T_BDCQLC_YGSPFYGDJ_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
						$('#T_BDCQLC_YGSPFYGDJ_FORM').find(".laydate-icon").attr('disabled',"disabled");
						$(".eflowbutton").hide();
						$("#SUBMIT_BTN").remove();
                    }

                }
				
				setZjValidate(flowSubmitObj.busRecord.LZRZJLB,"LZRZJHM");
				setZjValidate(flowSubmitObj.busRecord.ZRFZJLB,"ZRFZJHM");
				setZjValidate(flowSubmitObj.busRecord.DLRZJLB,"DLRZJHM");
				
            } else {
              
				//预购商品房预告登记模块中，持证类型默认为共同持有，登记原因默认为"预购商品房预告登记"。
				//$("select[name='TAKECARD_TYPE']").val("1");
				$('#DJYY').val("预购商品房预告登记");
				//权利人、代理人（领证人）、义务人、代理人证件种类初始化为'身份证'
				$('select[name="MSFZJLB"]').val('身份证');
				$('select[name="LZRZJLB"]').val('身份证');
				$('select[name="ZRFZJLB"]').val('身份证');
				$('select[name="DLRZJLB"]').val('身份证');
				setZjValidate("身份证","MSFZJHM");
				setZjValidate("身份证","LZRZJHM");
				setZjValidate("身份证","ZRFZJHM");
				setZjValidate("身份证","DLRZJHM");
				
				//合同类型默认为'预售合同'、规划用途默认为'住宅'、房屋性质默认为'市场化商品房'
				$('select[name="HTLX"]').val('预售合同');
				$('select[name="YTSM"]').val('住宅');
				$('[name="YTMS"]').val('住宅');
				$('select[name="FWXZ"]').val('市场化商品房');
                
            }
        }
		
		$("input[name='MSFZJHM']").val($("input[name='MSFZJHM']").val().toUpperCase());
		$("input[name$='MSFZJHM']").on('blur', function(event) { 
			var ZJLX = $("[name='MSFZJLB']").val();
			if(ZJLX=='身份证'){			
				$(this).val($(this).val().toUpperCase());
			}
		});
		$("input[name='LZRZJHM']").val($("input[name='LZRZJHM']").val().toUpperCase());
		$("input[name='LZRZJHM']").on('blur', function(event) { 
			var ZJLX = $("[name='LZRZJLB']").val();
			if(ZJLX=='身份证'){			
				$(this).val($(this).val().toUpperCase());
			}
		});
		$("input[name='ZRFZJHM']").val($("input[name='ZRFZJHM']").val().toUpperCase());
		$("input[name='ZRFZJHM']").on('blur', function(event) { 
			var ZJLX = $("[name='ZRFZJLB']").val();
			if(ZJLX=='身份证'){			
				$(this).val($(this).val().toUpperCase());
			}
		});
		$("input[name='DLRZJHM']").val($("input[name='DLRZJHM']").val().toUpperCase());
		$("input[name='DLRZJHM']").on('blur', function(event) { 
			var ZJLX = $("[name='DLRZJLB']").val();
			if(ZJLX=='身份证'){			
				$(this).val($(this).val().toUpperCase());
			}
		});
    });
	/**
	*  字段权限控制初始化
	**/
	function initFieldControl(){
		$('#slxxInfo').find('input,textarea').attr("disabled", "disabled");
		$('#slxxInfo').find('select').attr("disabled", "disabled");
		$("[name='NOTIFY_NAME']").removeAttr("disabled");
		$("[name='NOTIFY_TEL']").removeAttr("disabled");
		$("[name='REMARK']").removeAttr("disabled");
		$("[name='fdchtbacxWw']").removeAttr("disabled");
		$("[name='BDCDYH']").removeAttr("disabled"); 
		
		
		$('#ygygInfo').find('input,textarea').attr("disabled", "disabled");
		$('#ygygInfo').find('select').attr("disabled", "disabled");
		
		$("[name='LZRXM']").removeAttr("disabled");  
		$("[name='LZRZJLB']").removeAttr("disabled");
		$("[name='LZRZJHM']").removeAttr("disabled");
		$("[name='LZRSJHM']").removeAttr("disabled");
		
		$("[name='DLRXM']").removeAttr("disabled");  
		$("[name='DLRZJHM']").removeAttr("disabled");
		$("[name='DLRZJLB']").removeAttr("disabled");
		$("[name='DLRSJHM']").removeAttr("disabled");
		
		$("[name='FJ']").removeAttr("disabled");
		$("[name='TDSYQR']").removeAttr("disabled");
		$("[name='SYQMJ']").removeAttr("disabled");
		
		
		$("[name='MSFSJHM']").removeAttr("disabled");
		
		$("#addQlrButton").remove();
		
		$("[name='TAKECARD_TYPE']").removeAttr("disabled"); 
		
		//权利人证件种类与义务人证件种类开放可选择
		$("[name$='MSFZJLB']").removeAttr("disabled");
		$("[name='ZRFZJLB']").removeAttr("disabled");
		//临时解除限制
	    /* $("[name='ZRFXM']").removeAttr("disabled");  
		$("[name='ZRFZJLB']").removeAttr("disabled");
		$("[name='ZRFZJHM']").removeAttr("disabled");
		$("[name$='MSFXM']").removeAttr("disabled");  
		$("[name$='MSFZJLB']").removeAttr("disabled");
		$("[name$='MSFZJHM']").removeAttr("disabled"); */
	}

</script>

<body style="background: #f0f0f0;">
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>

    <div class="eui-main">
        <jsp:include page="./formtitle.jsp" />
        <form id="T_BDCQLC_YGSPFYGDJ_FORM" method="post">
            <%--开始引入公共隐藏域部分 --%>
            <jsp:include page="commonhidden.jsp" />
            <%--结束引入公共隐藏域部分 --%>
            <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" />
            <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
            <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
            <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
            <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
            <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
			
            <input type="hidden" name="ISQCWB" value="1" /> 
            <input type="hidden" name="EXE_ID" />

            <%--开始编写基本信息 --%>
            <div class="bsbox clearfix">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>基本信息</span></li>
                    </ul>
                </div>
                <div class="bsboxC">
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                        <tr>
                            <th> 审批事项</th>
                            <td colspan="3">${serviceItem.ITEM_NAME}</td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;margin-top:-1px;">

                        <tr>
                            <th> 所属部门</th>
                            <td style="border-right-style: none;">${serviceItem.SSBMMC}</td>
                            <th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
                            <td style="border-left-style: none;"></td>
                        </tr>
                        <tr>
                            <th> 审批类型</th>
                            <td>${serviceItem.SXLX}</td>
                            <th> 承诺时间</th>
                            <td>${serviceItem.CNQXGZR}</td>
                        </tr>
                        <tr>
                            <th><span class="bscolor1">*</span> 申报名称</th>
                            <td colspan="3"><input type="text" class="tab_text validate[required]"
                                                   style="width: 50%" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}"
                                                   name="SBMC" maxlength="120"/>
                                <span class="bscolor1"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
                        </tr>
                    </table>
                </div>
            </div>
            <%--结束编写基本信息 --%>

            <%--引入申报对象信息--%>
            <jsp:include page="./applyuserinfoType1.jsp" />
            <%--引入申报对象信息--%>
			
            <%--开始引入受理信息--%>
            <jsp:include page="./bdcqlc/bdcqlcygspfygdj/T_BDCYGSPFYGDJ_ACCEPTINFO.jsp" />
            <%--结束引入受理信息--%>

            <%--开始引入预告信息--%>
            <jsp:include page="./bdcqlc/bdcqlcygspfygdj/T_BDCYGSPFYGDJ_YGYG.jsp" />
            <%--结束引入预告信息--%>

            <div class="bsbox clearfix">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>所需材料</span></li>
                    </ul>
                </div>
                <jsp:include page="../../bsdt/applyform/applyMaterList.jsp">
                    <jsp:param value="1" name="applyType" />
                    <jsp:param value="1" name="isWebsite" />
                </jsp:include>
            </div>

            <%--引入办理结果领取方式--%>
            <jsp:include page="bdcqlc/T_BDCQLC_EMS.jsp" />
            <%--引入办理结果领取方式--%>


            <%--开始引入提交DIV界面 --%>
            <jsp:include page="./submitdiv.jsp" >
                <jsp:param value="submitFlow('T_BDCQLC_YGSPFYGDJ_FORM');" name="submitFn"/>
                <jsp:param value="saveTempFlow('T_BDCQLC_YGSPFYGDJ_FORM');" name="tempSaveFn"/>
            </jsp:include>
            <%--结束引入提交DIV界面 --%>

        </form>
    </div>

	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>