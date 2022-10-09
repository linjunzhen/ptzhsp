<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils"%>

<%
	String submitFn = request.getParameter("submitFn");  
    if(StringUtils.isNotEmpty(submitFn)){
    	request.setAttribute("submitFn", submitFn);
    }
    String tempSaveFn = request.getParameter("tempSaveFn");  
    if(StringUtils.isNotEmpty(tempSaveFn)){
    	request.setAttribute("tempSaveFn", tempSaveFn);
    }
	
%>

<script type="text/javascript">
   function clickAgreeCnsnr(){
	   var checkValues = AppUtil.getCheckBoxCheckedValue("AGREE_CNSNR");
	   if(checkValues&&checkValues=="1"){
		   var submitFn = $("input[name='submitFn']").val();
		   $("#SUBMIT_BTN").attr("onclick",submitFn);
		   $("#SUBMIT_BTN").attr("class","btn2");
	   }else{
		   $("#SUBMIT_BTN").attr("onclick","");
		   $("#SUBMIT_BTN").attr("class","btn2on");
	   }
   }
   function submitForOK(formId){
     var creditlevel = $("input[name='creditlevel']").val();
     if(creditlevel=='D'){
		art.dialog({
			content: "申请人员信用评级为D级（黑名单），无网上申报办理资格，请前往行政服务中心窗口办理！",
			icon:"error",
			time:10,
			ok: true
		});
     }else{
    	var validateResult =$("#"+formId).validationEngine("validate");
	     var valifileResult=AppUtil.getSubmitMaterFileJson(formId,1);
	     if(valifileResult){
		     if(validateResult){
		         art.dialog.confirm("请确认必要材料是否提供。预审时间为1个工作日，您确定要提交该流程吗?", function() {
		              AppUtil.submitWebSiteFlowForm(formId);
		         });
		      }
	   	 }
     }


     
   }

</script>

<c:if test="${isQueryDetail==null}">
    <input type="hidden" name="submitFn" value="${submitFn}" />
	<div class="bsbox clearfix">
		<div class="Ctext clearfix lbpadding32">
		    <input type="checkbox" checked="checked" value="1" onclick="clickAgreeCnsnr();" name="AGREE_CNSNR"/>我已阅读并遵守<a href="promiseInfoController/info.do?&itemCode=${serviceItem.ITEM_CODE}" target="_blank"><font color="red">《行政审批承诺书》</font></a>
			<br/>
			<c:if test="${EFLOWOBJ.EFLOW_BUSTABLENAME != null && fn:contains(EFLOWOBJ.EFLOW_BUSTABLENAME, 'T_BDCQLC_')}">长时间未收到签署短信或遇签章问题，请拨打e签宝客服电话：4000878198</br></c:if>
			<br/>
			<a href="javascript:void(0);" onclick="${submitFn}" id="SUBMIT_BTN"  class="btn2">提
				交</a>&nbsp;&nbsp;
                <a href="javascript:void(0);" onclick="notReviewPass('T_BSFW_ZFTZSGTSJHZTB_FORM');" id="NOT_PASS"   style="display:none"   class="btn2">不通过</a>&nbsp;&nbsp;
				
			<c:if test="${EFLOW_FLOWEXE==null}">
				<a href="javascript:void(0);" onclick="${tempSaveFn}" id="TEMPSAVE_BTN"  class="btn2">暂
					存</a>&nbsp;&nbsp;
			</c:if>        
			<c:if test="${EFLOW_FLOWEXE!=null&&EFLOW_FLOWEXE.RUN_STATUS==0}">
				<a href="javascript:void(0);" onclick="${tempSaveFn}" id="TEMPSAVE_BTN"  class="btn2">暂
					存</a>&nbsp;&nbsp;
			</c:if>
		</div>
	</div>
</c:if>