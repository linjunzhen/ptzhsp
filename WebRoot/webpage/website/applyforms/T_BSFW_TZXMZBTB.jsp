<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
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
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_TZXMZBTB_FORM");
    		}
		}

		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_TZXMZBTB"
		//});
	});
	
	function setHiddenField(){
		//判断是否重点项目必须选择
		if($("input[name='isImport']:checked").length==0){
			art.dialog({
				content: "是否是国家、省重点建设项目选项未选择，请选择后再提交!",
				icon:"warning",
			    ok: true
			});
			return false;
		}else{
			$("input[name='IS_IMPORTANT_PROJECT']").val($("input[name='isImport']:checked").val());
		}
		//判断项目类型必须选择
		var isChooseType=false;
		for(var i=0;i<types.length;i++){
			if($("input[name='"+types[i][0]+"']").is(':checked')){
				isChooseType=true;
				break;
			}
		}
		if(!isChooseType){
			art.dialog({
				content: "请选择招标项目类型!",
				icon:"warning",
			    ok: true
			});
			return false;
		}
		//判断如果是不招标，那么不招标理由必须选择，如果是邀请招标，邀请招标理由必须选择
		for(var i=0;i<types.length;i++){
			if($("input[name='"+types[i][0]+"']").is(':checked')){
				if($("input[name='"+types[i][0]+"zbfs']:checked").length==0){
					art.dialog({
						content: "招标项目类型为【"+types[i][1]+"】的项目，招标形式未选择!",
						icon:"warning",
					    ok: true
					});
					return false;
				}else{
					var record="";
					var _lys="";
					var _xzs="";
					var reasonisChoose=false;
					var hasXZ=false;
					if($("input[name='"+types[i][0]+"zbje']").val()==""){
						art.dialog({
							content: "招标项目类型为【"+types[i][1]+"】的项目，项目估算金额未填写!",
							icon:"warning",
						    ok: true
						});
						return false;
					}
					var zbxs="";
					if($("input[name='"+types[i][0]+"zbfs']:checked").val()==1){
						zbxs="邀请招标";
						for(var j=0;j<7;j++){
							if($("input[name='"+types[i][0]+"yqzbly"+(j+1)+"']").is(':checked')){
								if(j==4){
									var xzisChoose=false;
									for(var m=0;m<8;m++){
										if($("input[name='"+types[i][0]+"yqzbXz"+(m+1)+"']").is(':checked')){
											xzisChoose=true;
											_xzs=_xzs==""?m+1:_xzs+","+(m+1);
										}
									}
									if(!xzisChoose){
										art.dialog({
											content: "招标项目类型为【"+types[i][1]+"】的项目，"+zbxs+"理由对应的“项目性质”还未选择!",
											icon:"warning",
										    ok: true
										});
										return false;
									}
									hasXZ=true;
								}								
								reasonisChoose=true;
								_lys=_lys==""?j+1:_lys+","+(j+1);
								
							}
						}
					}else if($("input[name='"+types[i][0]+"zbfs']:checked").val()==2){
						zbxs="不招标";
						for(var j=0;j<8;j++){
							if($("input[name='"+types[i][0]+"bzbly"+(j+1)+"']").is(':checked')){
								reasonisChoose=true;
								_lys=_lys==""?j+1:_lys+","+(j+1);
							}
						}
					}
					if(!reasonisChoose){
						art.dialog({
							content: "招标项目类型为【"+types[i][1]+"】的项目，"+zbxs+"理由还未选择!",
							icon:"warning",
						    ok: true
						});
						return false;
					}
					//金额不能低于50万
					var vje=parseFloat($("input[name='"+types[i][0]+"zbje']").val());
					if(!isNaN(vje)&&vje<50){
						art.dialog({
							content: "招标项目类型为【"+types[i][1]+"】的项目，项目估算金额底于50万，不需要线上审批!",
							icon:"warning",
						    ok: true
						});
						return false;
					}
					record=$("input[name='"+types[i][0]+"zbfs']:checked").val()+"_";
					record=record+$("input[name='"+types[i][0]+"zbje']").val()+"_";
					record=record+_lys;
					if(hasXZ)record=record+"_"+_xzs;
					$("input[name='"+types[i][0]+"_Record']").val(record);
				}
			}
		}
		return true;
	}
	
	function ZBTB_FLOW_SubmitFun(){
		if(!setHiddenField()) return null;
		AppUtil.submitWebSiteFlowForm('T_BSFW_TZXMZBTB_FORM');
			
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
    	<jsp:include page="./formtitle.jsp" />
        
        <form id="T_BSFW_TZXMZBTB_FORM" method="post" >
        
        <%--===================重要的隐藏域内容=========== --%>
	    <%--开始引入公共隐藏域部分 --%>
		<jsp:include page="./commonhidden.jsp" />
	    <%--结束引入公共隐藏域部分 --%>
	    <%--===================重要的隐藏域内容=========== --%>
    	
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./tzjbxx_zbtb.jsp" />
		<%--结束引入基本信息部分 --%>
		
		
        <%--开始引入基本信息部分 --%>
			<jsp:include page="./zbtbsbxx.jsp" />
		<%--结束引入基本信息部分 --%>
			
			
		<div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListTZXM_ZBTB.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_TZXMZBTB_FORM" name="formName"/>
            </jsp:include>
        </div>
        
        <jsp:include page="./applyuserinfo.jsp" />
        
        </form>
        
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="ZBTB_FLOW_SubmitFun();" name="submitFn"/>
		     <jsp:param value="AppUtil.tempSaveWebSiteFlowForm('T_BSFW_TZXMZBTB_FORM');" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
        <c:if test="${EFLOWOBJ.HJMC!=null}">
        <jsp:include page="xmlct.jsp" >
           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
        </jsp:include>
        </c:if>
        <%-- 结束引入阶段流程图 并且当前节点不在开始或结束节点--%>
        
        <%--开始引入回执界面 --%>
		<jsp:include page="./hzxx.jsp">
			<jsp:param value="${EFLOWOBJ.EFLOW_EXEID}" name="exeId" />
		</jsp:include>
		<%--结束引入回执界面 --%>
    </div>
    
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>