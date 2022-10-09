<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
request.setAttribute("time", time);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>平潭综合实验区商事主体登记申报系统</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>

    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
     <!-- 新增-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<!-- 新增-->
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!---引入流程JS---->
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<!---引入公共JS--->
	<script src="<%=path%>/webpage/website/applyforms/change/js/commercial.js"></script>

	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
	<script type="text/javascript">
	//表单化附件保存返回id存储
	var store = {};
	//var haveOnLine = [];
	$(function() {
		$("input[name='FILL_DATE']").val(laydate.now());
        var isQueryDetail="${isQueryDetail}";
        if(isQueryDetail=='true'){
            $("#container1").hide();
            $("#container2").show();
        }else{
            $("#container1").show();
            $("#container2").hide();
        }

        var changeRegs=$("input[name='CHANGEREGS']").val();
        var records=$("input[name='RECORDS']").val();
        <!--动态显示字段-->
        //change
        if(changeRegs.indexOf("0")>-1){
            $(".BUS_SCOPE").show();
            $("input[name='OLD_BUS_SCOPE']").addClass("validate[required]");
            $("input[name='NEW_BUS_SCOPE']").addClass("validate[required]");
        }
        if(changeRegs.indexOf("1")>-1){
            $(".BUS_YEARS").show();
            $("input[name='OLD_BUS_YEARS']").addClass("validate[required]");
            $("input[name='NEW_BUS_YEARS']").addClass("validate[required]");
        }
        if(changeRegs.indexOf("2")>-1){
            $(".REG_ADDR").show();
            $("input[name='OLD_REG_ADDR']").addClass("validate[required]");
            $("input[name='NEW_REG_ADDR']").addClass("validate[required]");
        }


        //record
        if(records.indexOf("0")>-1){
            $(".clearQue").show();
            $("input[name='CLEAR_PEOPLE']").addClass("validate[required]");
            $("input[name='CLEAR_ZERENREN']").addClass("validate[required]");
            $("input[name='CLEAR_PHONENUM']").addClass("validate[required]");
        }
        if(records.indexOf("1")>-1){
            $(".liaison").show();
            $("input[name='LIAISON_NAME']").addClass("validate[required]");
            $("input[name='LIAISON_FIXEDLINE']").addClass("validate[required]");
            $("input[name='LIAISON_IDTYPE']").addClass("validate[required]");
            $("input[name='LIAISON_IDNO']").addClass("validate[required]");
        }
        <!--动态显示字段-->





		jQuery(".eui-menu").slide({
			type:"menu", //效果类型
			titCell:".syj-location", // 鼠标触发对象
			targetCell:".syj-city", // 效果对象，必须被titCell包含
			delayTime:0, // 效果时间
			defaultPlay:false,  //默认不执行
			returnDefault:true // 返回默认
		});
		jQuery(".syj-tyys").slide({trigger:"click"});
		//初始化验证引擎的配置
		AppUtil.initDefaultValidateConfig();

        //初始化材料列表
        //AppUtil.initNetUploadMaters({
        //    busTableName:"T_COMMERCIAL_CHANGE_ONEP"
        //});

	});
	$(function(){
		$("#formtabtitle").pin({
		      containerSelector: "#formcontainer"
		});

	});
	</script>
</head>

<body style="background: #f0f0f0;">

<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />
<div class="eui-main">


	<div class="eui-crumbs">
		<ul>
			<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
			<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
			<li style="font-size:16px">网上申报</li>
		</ul>
	</div>

	<%--开始引入公共隐藏域部分 --%>
	<jsp:include page="commonhidden.jsp" />
	<input type="hidden" name="applyItemMatersJson" value="${applyMatersJson}" />
<%--结束引入公共隐藏域部分 --%>

<input type="hidden" name="nextNum" value="1" />
<input type="hidden" name="RITEMCODES" value="${requestParams.ITEMCODES}" />
<input type="hidden" name="RITEMNAMES" value="${requestParams.ITEMNAMES}" />


	<jsp:include page="/webpage/website/applyforms/change/T_COMMERCIAL_CHOOSE.jsp" />


<div class="container"  id="container2" >
        <div class="syj-sbmain1 tmargin20" style=" margin-top:0px;"  id="lct">
       		<div class="syj-sbm1l">
            	<h3>${serviceItem.ITEM_NAME}</h3>
                <p>登记类别：<font id="companyTypeFont">${requestParams.COMPANY_TYPE}</font></p>
            </div>
            <div class="syj-sbm1r" style="width:838px;">
            	<div class="eui-steps-round" style="float:right; padding-right: 10px;">
					<%--开始引入流程图界面 --%>
					<c:if test="${EFLOWOBJ.HJMC!=null}">
						<jsp:include page="/webpage/website/applyforms/zzhy/flowchart.jsp" >
						   <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
						   <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
						</jsp:include>
					</c:if>
					<%--结束引入流程图界面 --%>
                </div>
            </div>
        </div>
        <div class="syj-sbmain2 tmargin20"  id="jbxx">
        	<div class="syj-tabtitle">
				<ul>
					<li class="on"><a href="javascript:AppUtil.validateSingleForm('SBXX_FORM');"  id="SBXX_FORM_A">事项基本信息</a></li>
				</ul>
			</div>

			<%--开始引入申报信息界面 --%>
			<jsp:include page="/webpage/website/applyforms/zzhy/sbxx.jsp" >
				<jsp:param value="0" name="registerType" />
			</jsp:include>
			<%--结束引入申报信息界面 --%>

            <div class="borderb tmargin20"></div>
            <div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
            	<div class="hd syj-tabtitle" id="formtabtitle" style="z-index: 99;">
                    <ul>
						<li><a href="javascript:AppUtil.validateSingleForm('COMPANY_FORM');"  id="COMPANY_FORM_A">基本信息</a></li>
                        <li class="itemLi1"><a href="javascript:AppUtil.validateSingleForm('ITEM_FORM');"  id="ITEM_FORM_A">变更项目</a></li>
                        <li class="recordLi1"><a href="javascript:AppUtil.validateSingleForm('RECORD_FORM');"  id="RECORD_FORM_A">备案信息</a></li>
                        <li><a href="javascript:AppUtil.validateSingleForm('LEGAL_FORM');"  id="LEGAL_FORM_A">指定或委托代理人信息</a></li>
							<li><a href="javascript:void(0);"  id="APPLYMATER_FORM_A" >附件上传</a></li>
						<li ><a href="javascript:AppUtil.validateSingleForm('USERINFO_FORM');"  id="USERINFO_FORM_A" >申报人信息</a></li>
					</ul>
                </div>
                <div class="bd margin20">
					<div>
						<%--开始引入企业基本信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/change/T_COMMERCIAL_ONEP_COMPANY.jsp" />
						<%--结束引入企业基本信息界面 --%>
					</div>

                    <div class="itemLi1">
                    	<%--开始引入企业变更项目界面 --%>
						<jsp:include page="/webpage/website/applyforms/change/T_COMMERCIAL_ONEP_ITEM.jsp" />
						<%--结束引入企业变更项目界面 --%>
                    </div>

                    <div class="recordLi1">
                    	<%--开始引入备案信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/change/T_COMMERCIAL_ONEP_RECORD.jsp" />
						<%--结束引入备案信息界面 --%>
                    </div>
                    <div>
                    	<%--开始引入被委托人 --%>
						<jsp:include page="/webpage/website/applyforms/change/T_COMMERCIAL_LEGAL.jsp" />
						<%--结束引入被委托人 --%>
                    </div>

					<div>
						<jsp:include page="../../bsdt/applyform/change/applyMaterList.jsp">
							<jsp:param value="1" name="applyType" />
							<jsp:param value="1" name="isWebsite" />
						</jsp:include>
					</div>
					<div >
						<%--开始引入申请人信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/zzhy/T_COMMERCIAL_USERINFO.jsp" />
						<%--结束引入申请人信息界面 --%>
					</div>
                </div>
            </div>
        </div>
		<c:if test="${isQueryDetail==null}">
        <div class="syj-btn" style="background:#fff;padding: 0 0 40px 0;">
			<div class="eui-FooterB eui-button">
				<button type="button" class="BtnBlue" onclick="FLOW_TempSaveFun();">保存草稿</button>
				<button type="button" class="BtnOrange" onclick="FLOW_SubmitFun();">提交</button>
			</div>
        </div>
		</c:if>
		<c:if test="${isQueryDetail!=null && EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='开始'}">
		<div class="syj-btn" style="background:#fff;padding: 0 0 40px 0;">
			<div class="eui-FooterB eui-button">
				<button type="button" class="BtnBlue" onclick="FLOW_Recover();">撤回申请</button>
			</div>
        </div>
		</c:if>
    </div>
</div>


<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
