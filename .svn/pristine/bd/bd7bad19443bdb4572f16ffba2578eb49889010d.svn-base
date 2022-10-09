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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript">
	
	
		$(function(){
			if($('#prjType_editPrj').is(':checked')) {
				$(".childBox").attr("disabled",false);
			}else{
				$(".childBox").attr("disabled",true);
			}
			$("#editPrj input").attr("disabled",true);
			//初始化验证引擎的配置
			$.validationEngine.defaults.autoHidePrompt = true;
			$.validationEngine.defaults.promptPosition = "topRight";
			$.validationEngine.defaults.autoHideDelay = "2000";
			$.validationEngine.defaults.fadeDuration = "0.5";
			$.validationEngine.defaults.autoPositionUpdate =true;
			//初始化表单个性化字段权限
			initForm();
			//获取流程信息对象JSON
			var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
			if(EFLOW_FLOWOBJ){
				//将其转换成JSON对象
				var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
				//初始化表单字段值
	    		if(eflowObj.busRecord){
	    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"TB_FC_PROJECT_INFO_FORM");
	    			var busRecord = eflowObj.busRecord;
	    			var sgtscInfo = busRecord.SGTSCHGZHBH;
	    			var zygcxfInfo = busRecord.ZYGCXFSCHGSBH;
	    			if(sgtscInfo!=null && sgtscInfo!=""){
	    				var sgtscArr = sgtscInfo.split(",");
	    				for(var i=0;i<sgtscArr.length;i++){
	    					var sgtscValue = sgtscArr[i];
	    					var idValue = "SGTSCID_" + i;
	    					var inputText = '<div id="sgdiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="SGTSC_NO" value="'+(sgtscValue)+'"/>';
	    					var asign='<a href="javascript:void(0);" onclick="delSgtsNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
	    					$('#sgtsc').append(inputText+asign);
	    				}
	    			}
	    			if(zygcxfInfo!=null && zygcxfInfo!=""){
	    				var zygcxfArr = zygcxfInfo.split(",");
	    				for(var i=0;i<zygcxfArr.length;i++){
	    					var zygcxfValue = zygcxfArr[i];
	    					var idValue = "ZYGCXFID_" + i;
	    					var inputText = '<div id="zydiv'+(i)+'"><input type="text" id="'+(idValue)+'"  name="ZYGCXF_NO" value="'+(zygcxfValue)+'"/>';
	    					var asign='<a href="javascript:void(0);" onclick="delZygcxfNo('+i+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
	    					$('#zygcxf').append(inputText+asign);
	    				}
	    			}
	    		
		    		var PRJ_NUM =busRecord.PRJ_NUM;
		    		if(PRJ_NUM!=null && sgtscInfo!=""){
		    			$('input[name=PRJ_NUM]').val(PRJ_NUM);
			    		$('#zrztxxGrid').datagrid({url:'xfDesignController.do?findZrztxxList&prj_code=${projectCode}&prj_num=' + PRJ_NUM + ' '   
						});
						$('#dtjzwxxGrid').datagrid({url:'xfDesignController.do?findDtjzwList&prj_code=${projectCode}&prj_num=' + PRJ_NUM + ' '   
						});	
		    		}else{
		    			//获取报建编号
		    			loadPRJNUM();
		    		}	    		
	    		}else{
	    			//获取报建编号
	    			loadPRJNUM();
	    		}
	    		
	    		


			}
			
			$('input[name=FC_CHARACTER]').on('click',function(){
				var isCheck =  $('#ifSpecialBuilding input[name=FC_CHARACTER]').is(':checked');
				console.log(isCheck);
				if(isCheck){
					$("input[name='IFSPECIALBUILDING']").val(1);
				}else{
					$("input[name='IFSPECIALBUILDING']").val(0);
				}
			});
			// document.getElementById("fjbdDiv").style.display="none";
		});
		//表单个性字段初始化
		function initForm(){
			$("select[name='ADMIN_DIVISION']").append("<option value='350128' selected='true'>平潭综合实验区</option>");
		}
		//提交表单
		function submitFlow() {
		
		    var isChecked = $("input[name='FC_PRJ_TYPE']").is(':checked');
		    if(isChecked == 1){
		    }else{
		         art.dialog({
                    content : "请选者项目类别！",
                    icon : "warning",
                    ok : true
                });
                return;
		    }
            //判断是否选中使用性质单元
            var fcVal=$("input[name='FC_CHARACTER']").is(':checked');
            if(fcVal==1){
            }else{
                art.dialog({
                    content : "请选择使用性质单元！",
                    icon : "warning",
                    ok : true
                });
                return;             
            }		    
		
			//责任主体
			var zrztRows = $("#zrztxxGrid").datagrid("getData");
    		var zrztJson = JSON.stringify(zrztRows.rows);
    		$("input[name='ZRZTXX_JSON']").val(zrztJson);
    		//单体建筑
    		var dtjzRows = $("#dtjzwxxGrid").datagrid("getData");
    		var dtjzJson = JSON.stringify(dtjzRows.rows);
    		$("input[name='DTXX_JSON']").val(dtjzJson);
    		//获取施工图审查合格证书编号值
    		var sgtscText = $("[name='SGTSC_NO']");
    		var sgtscValue = "";
    		$.each(sgtscText,function () {
    			var input = $(this);
    			var id = input.attr("id");
    			var idValue = document.getElementById(id).value;
    			if(idValue!=""){
    				sgtscValue += idValue+",";
    			}
    		});
    		if(sgtscValue!=""){
    			sgtscValue = sgtscValue.substring(0,sgtscValue.length-1);
    			$("input[name='SGTSCHGZHBH']").val(sgtscValue);
    		}
    		//获取专业工程消防审查合格书编号值
    		var zygcxfText = $("[name='ZYGCXF_NO']");
    		var zygcxfValue = "";
    		$.each(zygcxfText,function () {
    			var input = $(this);
    			var id = input.attr("id");
    			var idValue = document.getElementById(id).value;
    			if(idValue!=""){
    				zygcxfValue += idValue+",";
    			}
    		});
    		if(zygcxfValue!=""){
    			zygcxfValue = zygcxfValue.substring(0,zygcxfValue.length-1);
    			$("input[name='ZYGCXFSCHGSBH']").val(zygcxfValue);
    		}
			/*保存project_name字段*/
			var PRJ_NAME = $("input[name='PRJ_NAME']").val()
			if (PRJ_NAME) {
				$("input[name='PROJECT_NAME']").val(PRJ_NAME);
			}
			AppUtil.submitWebSiteFlowForm('TB_FC_PROJECT_INFO_FORM');
		}

		//暂存表单
		function tempSaveFn() {
		     //判断项目类别是否选中
            var isChecked = $("input[name='FC_PRJ_TYPE']").is(':checked');
            if(isChecked == 1){
            }else{
                 art.dialog({
                    content : "请选者项目类别！",
                    icon : "warning",
                    ok : true
                });
                return;
            }
            //判断是否选中使用性质单元
            var fcVal=$("input[name='FC_CHARACTER']").is(':checked');
            if(fcVal==1){
            }else{
                art.dialog({
                    content : "请选择使用性质单元！",
                    icon : "warning",
                    ok : true
                });
                return;             
            }
            
                        		
			//责任主体
			var zrztRows = $("#zrztxxGrid").datagrid("getData");
    		var zrztJson = JSON.stringify(zrztRows.rows);
    		$("input[name='ZRZTXX_JSON']").val(zrztJson);
    		//单体建筑
    		var dtjzRows = $("#dtjzwxxGrid").datagrid("getData");
    		var dtjzJson = JSON.stringify(dtjzRows.rows);
    		$("input[name='DTXX_JSON']").val(dtjzJson);
    		//获取施工图审查合格证书编号值
    		var sgtscText = $("[name='SGTSC_NO']");
    		var sgtscValue = "";
    		$.each(sgtscText,function () {
    			var input = $(this);
    			var id = input.attr("id");
    			var idValue = document.getElementById(id).value;
    			if(idValue!=""){
    				sgtscValue += idValue+",";
    			}
    		});
    		if(sgtscValue!=""){
    			sgtscValue = sgtscValue.substring(0,sgtscValue.length-1);
    			$("input[name='SGTSCHGZHBH']").val(sgtscValue);
    		}
    		//获取专业工程消防审查合格书编号值
    		var zygcxfText = $("[name='ZYGCXF_NO']");
    		var zygcxfValue = "";
    		$.each(zygcxfText,function () {
    			var input = $(this);
    			var id = input.attr("id");
    			var idValue = document.getElementById(id).value;
    			if(idValue!=""){
    				zygcxfValue += idValue+",";
    			}
    		});
    		if(zygcxfValue!=""){
    			zygcxfValue = zygcxfValue.substring(0,zygcxfValue.length-1);
    			$("input[name='ZYGCXFSCHGSBH']").val(zygcxfValue);
    		}
			/*保存project_name字段*/
			var PRJ_NAME = $("input[name='PRJ_NAME']").val()
			if (PRJ_NAME) {
				$("input[name='PROJECT_NAME']").val(PRJ_NAME);
			}
			tempSaveWebSiteFlowForm('TB_FC_PROJECT_INFO_FORM');
		}


		
function loadPRJNUM(){
	$.post("projectSgxkController/getPrjCode.do",{XZQHCode:'350128',codetype:1},
		function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if (resultJson.status) {
				$('input[name=PRJ_NUM]').val(resultJson.code);
				//获取prjnum后重新改变责任主体和单位的查询url
	    			var prjnum = $('input[name=PRJ_NUM]').val();
		    		$('#zrztxxGrid').datagrid({url:'xfDesignController.do?findZrztxxList&prj_code=${projectCode}&prj_num=' + prjnum + ' '   
					});	
		    		$('#dtjzwxxGrid').datagrid({url:'xfDesignController.do?findDtjzwList&prj_code=${projectCode}&prj_num=' + prjnum + ' '   
					});						
			} else {
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		}
	);
}



    /**
     * 暂存网站的流程表单信息
     * @param {} formId
     */
    function tempSaveWebSiteFlowForm(formId){
        //先判断表单是否验证通过
         var validateResult =$("#"+formId).validationEngine("validate");
         if(validateResult){         
         var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,1);
         //获取流程信息对象JSON
         var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
         //将其转换成JSON对象
         var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
         $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
         //先获取表单上的所有值
         var formData = FlowUtil.getFormEleData(formId);
         for(var index in formData){
             flowSubmitObj[eval("index")] = formData[index];
         }
         flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
         var postParam = $.param(flowSubmitObj);
         AppUtil.ajaxProgress({
                url : "webSiteController.do?submitApply",
                params : postParam,
                callback : function(resultJson) {
                    if(resultJson.OPER_SUCCESS){
                        art.dialog({
                            content :"保存成功,草稿数据只保存90天，过期系统自动清理,申报号是:"+resultJson.EFLOW_EXEID,// resultJson.OPER_MSG,
                            icon : "succeed",
                            lock : true,
                            ok:function(){
                                window.top.location.href=__newUserCenter;
                            },
                            close: function(){
                                window.top.location.href=__newUserCenter;
                            }
                        });
                    }else{
                        art.dialog({
                            content : resultJson.OPER_MSG,
                            icon : "error",
                            ok : true
                        });
                    }
                }
            });
	    }
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
        <form id="TB_FC_PROJECT_INFO_FORM" method="post" >
			<input type="hidden" name="IFSPECIALBUILDING" />
			<input type="hidden" name="SGTSCHGZHBH"/>
			<input type="hidden" name="ZYGCXFSCHGSBH"/>
			<input type="hidden" name="DTXX_JSON"/>
			<input type="hidden" name="ZRZTXX_JSON"/>
			<input type="hidden" id="PRJ_NUM" name="PRJ_NUM" >
			<input type="hidden" id="PRJ_CODE" name="PRJ_CODE" value="${projectCode}">
			
			<%--===================重要的隐藏域内容=========== --%>
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
							<td>${serviceItem.CNQXGZR}工作日</td>
						</tr>
						<tr>
							<th><span class="bscolor1">*</span> 申报名称</th>
							<td colspan="3"><input type="text" class="tab_text validate[required]"
								style="width: 50%" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}"
								name="SBMC" maxlength="120"/>
								<span class="bscolor1"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
						</tr>
						<%-- <tr>
							<th> 申报状态</th>
							<td colspan="3">
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" style="color: blue;" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS==null}">checked="checked"</c:if> />　待提交　　｜　</span>
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS=='1'}">checked="checked"</c:if> />　审核阶段　　｜　</span>
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS!=null&&EFLOW_FLOWEXE.RUN_STATUS!='1'}">checked="checked"</c:if> />　审核完成　</span>
							</td>
						</tr> --%>
					</table>
				</div>
			</div>
			<%--结束编写基本信息 --%>
	        <%--===================重要的隐藏域内容=========== --%>
			    <%--开始引入公共隐藏域部分 --%>
				<jsp:include page="./commonhidden.jsp" />
			    <%--结束引入公共隐藏域部分 --%>
		    <%--===================重要的隐藏域内容=========== --%>
	    	
	        <%--开始引入消防设计基本信息部分 --%>
				<jsp:include page="./xfsj/xfsjjbxx.jsp" />
			<%--结束引入消防设计基本信息部分 --%>
			
			<%--开始引入使用性质单元部分 --%>
				<jsp:include page="./xfsj/syxzdy.jsp" />
			<%--结束引入使用性质单元部分 --%>
			
			<%--开始引入其他信息单元部分 --%>
				<jsp:include page="./xfsj/qtxxdy.jsp" />
			<%--结束引入其他信息单元部分 --%>
			
			<%--开始引入责任主体信息部分 --%>
				<jsp:include page="./xfsj/zrztxx.jsp" />
			<%--结束引入责任主体信息部分 --%>
			
			<%--开始引入单体建筑物信息部分 --%>
				<jsp:include page="./xfsj/dtjzwxx.jsp" />
			<%--结束引入单体建筑物信息部分 --%>

			<%--申报信息--%>
			<jsp:include page="./applyuserinfo.jsp" />
			<%--申报信息--%>

			<%--开始引入申请表信息部分 --%>
			<jsp:include page="/webpage/website/applyforms/xfsj/applyForm.jsp" >
				<jsp:param value="TB_FC_PROJECT_INFO_FORM" name="formId"/>
				<jsp:param value="3" name="materCode"/>
			</jsp:include>
			<%--结束引入申请表信息部分 --%>
<%--			<jsp:include page="/webpage/website/applyforms/gcjsxm/gcjssgsk/applyForm.jsp" />--%>

			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>所需材料</span></li>
					</ul>
				</div>
				<jsp:include page="./matterListZF.jsp" >
                	<jsp:param value="1" name="applyType"/>
                	<jsp:param value="TB_FC_PROJECT_INFO_FORM" name="formName"/>
            	</jsp:include>
			</div>
        </form>
        <%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitFlow();" name="submitFn"/>
		     <jsp:param value="tempSaveFn();" name="tempSaveFn"/>
		</jsp:include>
		<%--结束引入提交DIV界面 --%>
            
        <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
<%--         <c:if test="${EFLOWOBJ.HJMC!=null}">
	        <jsp:include page="xmlct.jsp" >
	           <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
	           <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
	        </jsp:include>
        </c:if> --%>
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