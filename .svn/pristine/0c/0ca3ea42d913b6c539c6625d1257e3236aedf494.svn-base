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
<title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/common/css/style.css">
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!---引入流程JS---->
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<!---引入公共JS--->
	
	<script src="<%=path%>/webpage/website/applyforms/gcjsxm/gcjssgsk/js/gcjssgsk.js"></script>
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
	<script type="text/javascript">
	//表单化附件保存返回id存储
	var store = {};
	//var haveOnLine = [];
	$(function() {	

		jQuery(".eui-menu").slide({ 
			type:"menu", //效果类型
			titCell:".syj-location", // 鼠标触发对象
			targetCell:".syj-city", // 效果对象，必须被titCell包含
			delayTime:0, // 效果时间
			defaultPlay:false,  //默认不执行
			returnDefault:true // 返回默认
		});
		jQuery(".syj-tyys").slide({trigger:"click",
		endFun:function(i,c){
			initPin();
		}});
		//初始化验证引擎的配置
		AppUtil.initDefaultValidateConfig();


	});
	$(function(){
		initPin();
	});
	
	function initPin(){		
		$("#formtabtitle").pin({
		      containerSelector: "#formcontainer"
		});
	}
	function  hideValidation(formId){
		setTimeout(function () {			
			$('.formError').fadeTo(300, 300, function() {
				 $(this).parent('.formErrorOuter').remove();
				 $(this).remove();
			 });
		}, 300);
	}
	</script>
</head>

<body  style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>
	<%--结束编写头部文件 --%>
	<div class="eui-main">


<input type="hidden" name="nextNum" value="1" />
<input type="hidden" name="applyItemMatersJson" value="${applyMatersJson}" /> 
	<input type="hidden" name="RITEMCODES" value="${requestParams.ITEMCODES}" />
	<input type="hidden" name="ITEMCODE" id="RITEMCODE" value="${itemCode}" />
<input type="hidden" name="RITEMNAMES" value="${requestParams.ITEMNAMES}" />
<div class="container" >
        <jsp:include page="./formtitleNoFlow.jsp" />
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
						<li><a href="javascript:AppUtil.validateSingleForm('INFO_FORM');" id="INFO_FORM_A">基本信息</a></li>
                        <li><a href="javascript:AppUtil.validateSingleForm('ZRZTXX_FORM');" id="ZRZTXX_FORM_A">责任主体信息</a></li>
                        <li><a href="javascript:AppUtil.validateSingleForm('GWGC_FORM');" onclick="hideValidation('GWGC_FORM')" id="DWGC_FORM_A">单位工程</a></li>
<!--                         <li><a href="javascript:AppUtil.validateSingleForm('SGRY_FORM');"  id="SGRY_FORM_A">施工人员</a></li> -->
<!--                         <li><a href="javascript:AppUtil.validateSingleForm('JLRY_FORM');"  id="JLRY_FORM_A">监理人员</a></li>     -->
                        <li><a href="javascript:AppUtil.validateSingleForm('WXY_FORM');"  id="WXY_FORM_A">危险源</a></li>    
                        <li><a href="javascript:AppUtil.validateSingleForm('SGXKJBXX_FORM');"  id="SGXKJBXX_FORM_A">施工许可基本信息</a></li>       
						<li><a href="javascript:AppUtil.validateSingleForm('SGCX_FILE_FORM');"  id="SGCX_FILE_FORM_A" >材料信息</a></li>   
                        <li><a href="javascript:AppUtil.validateSingleForm('USERINFO_FORM');"  id="USERINFO_FORM_A" >申报人信息</a></li>
						<li><a href="javascript:AppUtil.validateSingleForm('APPLY_FORM');"  id="APPLY_FORM_A" >申请表</a></li>
                    </ul>
                </div>
                <div class="bd" style="padding: 25px 30px;">
					<div>						
						<%--开始引入企业基本信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/info.jsp" />
						<%--结束引入企业基本信息界面 --%>
					</div>
                	<div>
                    	<%--开始引入责任主体信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/zrztxx.jsp" />
						<%--结束引入责任主体信息界面 --%>
                    </div>
                    <div>
                    	<%--开始引入单位工程信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/dwgc.jsp" />
						<%--结束引入单位工程信息界面 --%>
                    </div>
<%-- 					<div>
                    	开始引入施工人员信息界面
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/sgry.jsp" />
						结束引入施工人员信息界面
                    </div>
                    <div>					
                    	开始引入监理人员信息界面
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/jlry.jsp" />
						结束引入监理人员信息界面
                    </div> --%>
                    <div>					
                    	<%--开始引入危险源信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/wxy.jsp" />
						<%--结束引入危险源信息界面 --%>
                    </div>
                    <div>					
                    	<%--开始引入施工许可基本信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/sgxkjbxx.jsp" />
						<%--结束引入施工许可基本信息界面 --%>
                    </div>
					<div>
						<%--开始引入前台用户上传界面 --%>						
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/applyMaterList.jsp" >
							<jsp:param value="1" name="applyType"/>
							<jsp:param value="T_BSFW_GCJSSGXK_FORM" name="formName"/>
						</jsp:include>
						<%--结束引入前台用户上传界面 --%>
					</div>	
                    <div>								
                    	<%--开始引入经办人信息界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/userinfo.jsp" />
						<%--结束引入经办人信息界面 --%>
					</div>
					<div>
						<%--开始引入申请表界面 --%>
						<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/applyForm.jsp" />
						<%--结束引入申请表界面 --%>
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
		<!--<c:if test="${isQueryDetail!=null && EFLOWOBJ.EFLOW_CUREXERUNNINGNODENAMES!='开始'}">
		<div class="tbmargin40 syj-btn">
        	<a href="javascript:void(0);" onclick="FLOW_Recover();" class="syj-btnsave">撤回申请</a>
        </div>
		</c:if>-->
    </div>
</div>


	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
