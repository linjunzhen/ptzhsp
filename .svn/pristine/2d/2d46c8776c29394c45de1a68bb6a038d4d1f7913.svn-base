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
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/gyjsjfwzydj/clfsflb/js/clfsflb.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcEmsSend.js"></script>

</head>

<style>
    .tab_text{
        width: 270px;
        height: 24px;
        line-height: 24px;
        padding: 2px 3px 2px 1px;
        border: 1px solid #bbb;
    }
</style>

<script>
    $(function () {
        //初始化验证引擎的配置
        $.validationEngine.defaults.autoHidePrompt = true;
        $.validationEngine.defaults.promptPosition = "topRight";
        $.validationEngine.defaults.autoHideDelay = "2000";
        $.validationEngine.defaults.fadeDuration = "0.5";
        $.validationEngine.defaults.autoPositionUpdate = true;

        initEasyUiForm();
        disabledQslyForm();
        var userType = '${sessionScope.curLoginMember.USER_TYPE}';//1、个人 2 、企业
        //限定只能企业账号办理，个人账号不允许办理
	    if (userType != '1') {
	         alert("企业账号无法办理此事项!")
	         location.href = __ctxPath + "/contentController/list.do?moduleId=605";//跳转至全程网办入口
	    }
        initUserForm(userType);
        initNormalForm();
        delChildItem();
        //获取流程信息对象JSON
        var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
        if (EFLOW_FLOWOBJ) {
            //申报对象信息,不可编辑
            /* $("input[name='SQRMC']").attr("disabled","disabled");
            $("select[name='SQRZJLX']").attr("disabled","disabled");
            $("input[name='SQRSFZ']").attr("disabled","disabled"); */
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
            if (flowSubmitObj.busRecord) {
                FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_GYJSJFWZYDJCLFSFLB_FORM");
                initAutoTable(flowSubmitObj);//初始化权利信息
                queryTypeFn();
                
              	//初始化电力水力燃气过户信息
                checkIsPowTransfer(flowSubmitObj.busRecord.IS_POWTRANSFER);
    			checkIsWaterTransfer(flowSubmitObj.busRecord.IS_WATERTRANSFER);
    			checkIsGasTransfer(flowSubmitObj.busRecord.IS_GASTRANSFER);
    			checkIsSVATransfer(flowSubmitObj.busRecord.IS_SVATRANSFER);
    			
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

                if (flowSubmitObj.busRecord.RUN_STATUS) {
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0) {

                    }

                }
            } else {
            	//初始化默认业务类型为'4'-存量房税费联办-对应字典类别编码为zydjlx1
                $("input[name='ZYDJ_TYPE']").val("4");
                $("select[name='POWERSOURCE_DYQRZJLX']").val("统一社会信用代码");
                $("input[name='FW_DJYY']").val("转移登记");
                $("input[name='DY_DJJG']").val("平潭综合实验区不动产登记与交易");
                $("input[name='DY_DYDJYY']").val("预购商品房抵押权预告登记转抵押权首次登记");
                setSfzgedy("0");
                openSelector();
            }
        }
        
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
    	
   	   /*初始化材料（申请人上传实名办税授权委托书）*/
       var fileids=$('input[name="SWDJ_FILE_ID"]').val();
       if(fileids!=null && fileids!="" && fileids!=undefined){
           $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
               if(resultJson!="websessiontimeout"){
                   $("#fileListDiv").html("");
                   var newhtml = "";
                   var list=resultJson.rows;
                   for(var i=0; i<list.length; i++){
                       newhtml+="<p style='margin-left: 5px; margin-right: 5px;line-height: 20px;' id='"+list[i].FILE_ID+"' >";
                       newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                       newhtml+=list[i].FILE_NAME+'</a>';
                       newhtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a>";
                       newhtml+='</p>';

                   }
                   $("#fileListDiv").html(newhtml);
               }
           });
        }
           
        /*初始化材料（家庭住房情况书面查询结果附件）*/
        var fileid=$('input[name="JTZF_FILE_ID"]').val();
        if(fileid!=null && fileid!="" && fileid!=undefined){
         $.post("executionController.do?getResultFiles&fileIds="+fileid,{fileIds:fileid}, function(resultJson) {
             if(resultJson!="websessiontimeout"){
                 $("#fileListDiv1").html("");
                 var newhtml = "";
                 var list=resultJson.rows;
                 for(var i=0; i<list.length; i++){
                     newhtml+="<p style='margin-left: 5px; margin-right: 5px;line-height: 20px;' id='"+list[i].FILE_ID+"' >";
                     newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                     newhtml+=list[i].FILE_NAME+'</a>';
                     newhtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a>";
                     newhtml+='</p>';
                 }
                 $("#fileListDiv1").html(newhtml);
             }
         });
        }
    });

</script>

<body style="background: #f0f0f0;"> 
	<%--开始编写头部文件 --%>
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if> 
	<%--结束编写头部文件 --%>
	<div class="eui-main">
        <jsp:include page="./formtitle.jsp" />
        <form id="T_BDCQLC_GYJSJFWZYDJCLFSFLB_FORM" method="post">
            <%--开始引入公共隐藏域部分 --%>
            <jsp:include page="commonhidden.jsp" />
            <%--结束引入公共隐藏域部分 --%>
            <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" />
            <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
            <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
            <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
            <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
            <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />

            <%-- 权利人信息明细 --%>
            <input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
            <%-- 权属来源信息明细 --%>
            <input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
            <%-- 权属来源限制明细 --%>
            <input type="hidden" name="POWERLIMITINFO_JSON" id="POWERLIMITINFO_JSON"/>
            <%--是否全程网办--%>
            <input type="hidden" id="ISQCWB" name="ISQCWB" value="1" />
            
            <%--业务办理子项--%>
		    <input type="hidden" name="ZYDJ_TYPE" id="ZYDJ_TYPE" value="${busRecord.ZYDJ_TYPE}"/>
		    
		    <%--是否同意接收税务机关根据项目申请人员填报的信息生成数据--%>
		    <input type="hidden" name="IS_AGREE" id="IS_AGREE" value="${busRecord.IS_AGREE}"/>
		    
		    <%--是否知晓--%>
		    <input type="hidden" name="IS_KNOW" id="IS_KNOW" value="${busRecord.IS_KNOW}"/>
		    
		    <%--提供购房证明--%>
		    <input type="hidden" name="GF_ZM" id="GF_ZM" value="${busRecord.GF_ZM}"/>
		    
		    <%--二手房交易--%>
		    <input type="hidden" name="ESF_JY" id="ESF_JY" value="${busRecord.ESF_JY}"/>
		    
		    <%--个人所得税征收方式--%>
		    <input type="hidden" name="GRSDS_ZSFS" id="GRSDS_ZSFS" value="${busRecord.GRSDS_ZSFS}"/>
		    
		    <%--个人所得税免征--%>
		    <input type="hidden" name="GRSDS_MZ" id="GRSDS_MZ" value="${busRecord.GRSDS_MZ}"/>
		    
		    <%--个人无偿赠与--%>
		    <input type="hidden" name="WCZY_GR" id="WCZY_GR" value="${busRecord.WCZY_GR}"/>
		    
		    
		    <%--开具税收完税证明--%>
		    <input type="hidden" name="IS_SFWS" id="IS_SFWS" value="${busRecord.IS_SFWS}"/>
		    
		    
		    <%--开具企业办理税务登记--%>
		    <input type="hidden" name="IS_SWDJ" id="IS_SWDJ" value="${busRecord.IS_SWDJ}"/>
		    
		    
		    <%--纳税人承诺-未成年子女信息--%>
		    <input type="hidden" name="NSR_CHILDJSON" id="NSR_CHILDJSON" value="${busRecord.NSR_CHILDJSON}"/>
		    
		    <%--企业办理税务登记 附件信息--%>
		    <input type="hidden" name="SWDJ_FILE_URL">
            <input type="hidden" name="SWDJ_FILE_ID">
            
            <%--家庭住房情况书面查询信息 附件信息--%>
		    <input type="hidden" name="JTZF_FILE_URL">
            <input type="hidden" name="JTZF_FILE_ID">
            
            <%--家庭唯一生活用房承诺书信息--%>
		    <input type="hidden" name="FWPOWERPEOPLEINFO_JSON" id="FWPOWERPEOPLEINFO_JSON" value="${busRecord.FWPOWERPEOPLEINFO_JSON}"/>
		    
		    <%--买方承诺信息JSON--%>
			<input type="hidden" name="BUYCN_JSON" id="BUYCN_JSON" value="${busRecord.BUYCN_JSON}"/>
			
			<%--卖方承诺信息JSON--%>
			<input type="hidden" name="SELLCN_JSON" id="SELLCN_JSON" value="${busRecord.SELLCN_JSON}"/>

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

            <%--开始引入受理信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
            <%--结束引入受理信息--%>

            <%--开始引入权利人信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/bdcQlrxx.jsp" />
            <%--结束引入权利人信息--%>

            <%--开始引入权属来源信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/bdcQsly.jsp" />
            <%--结束引入权属来源信息--%>

            <%--开始引入抵押情况信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/T_ESTATE_ZYDJ_OBLIGEEINFO.jsp" />
            <%--结束引入抵押情况信息--%>

            <%--开始引入宗地信息-国有权力人信息--%>
            <jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
            <%--开始引入宗地信息-国有权力人信息--%>

            <%--开始引入房屋基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/bdcFwjbxx.jsp" />
            <%--开始引入房屋基本信息--%>
            
            <%--开始引入纳税签章人信息--%>
            <%-- <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/sssbQsrXx.jsp" /> --%>
            <%--结束引入纳税签章人信息--%>
            
            <%--开始引入纳税人信息--%>
            <%-- <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/nsrJbxx.jsp" /> --%>
            <%--结束引入纳税人信息--%>
            
            <%--开始引入家庭住房书面查询自助上传附件信息--%>
            <%-- <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/jtzfSmxx.jsp" /> --%>
            <%--结束引入家庭住房书面查询自助上传附件信息--%>
            
            <%--开始引入存量房评估补充信息表信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/esfBcxx.jsp" /> 
            <%--结束引入存量房评估补充信息表信息--%>
            
            <%--开始引入家庭唯一生活用房承诺书信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/wyyfCns.jsp" /> 
            <%--结束引入家庭唯一生活用房承诺书信息--%>
            
            <%--开始引入受赠人信息--%>
            <%-- <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/szrXx.jsp" /> --%>
            <%--结束引入受赠人信息--%>
              
            <%--开始引入企业办理税务登记信息--%>
            <%-- <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/blSwdj.jsp" /> --%>
            <%--结束引入企业办理税务登记信息--%>
            
            
            <%--开始不动产询问记录基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/bdcXwjl.jsp" />
            <%--开始不动产询问记录基本信息--%>

            <%--开始银行询问记录基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/yhXwjl.jsp" />
            <%--开始银行询问记录基本信息--%>

<%--            &lt;%&ndash;开始银行询问记录基本信息&ndash;%&gt;--%>
<%--            <jsp:include page="./bdcqlc/gyjsjfwzydj/tyXwjl.jsp" />--%>
<%--            &lt;%&ndash;开始银行询问记录基本信息&ndash;%&gt;--%>

            <%--涉税登记信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/clfsflb/taxRelatedInfo.jsp"/>
            <%--涉税登记信息--%>
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

            <%--引入申报对象信息--%>
            <%-- <jsp:include page="./applyuserinfo.jsp" /> --%>
            <jsp:include page="./applyuserinfoType1.jsp" />
            <%--引入申报对象信息--%>

            <%--引入申报对象信息--%>
            <jsp:include page="bdcqlc/T_BDCQLC_EMS.jsp" />
            <%--引入申报对象信息--%>

            <%--开始引入提交DIV界面 --%>
            <jsp:include page="./submitdiv.jsp" >
                <jsp:param value="submitFlow('T_BDCQLC_GYJSJFWZYDJCLFSFLB_FORM');" name="submitFn"/>
                <jsp:param value="saveFlow('T_BDCQLC_GYJSJFWZYDJCLFSFLB_FORM');" name="tempSaveFn"/>
            </jsp:include>
            <%--结束引入提交DIV界面 --%>

        </form>
    </div>

	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>