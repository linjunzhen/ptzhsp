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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>
<script type="text/javascript">
	$(function(){
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate =true;
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	if(flowSubmitObj){
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_ZFTZSGTSJHZTB_FORM");
    			
    			//获取表单字段权限控制信息
        		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        		 //初始化表单字段权限控制
        		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_ZFTZSGTSJHZTB_FORM");
        		 
        		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ==null&& flowSubmitObj.busRecord.GSSXMM!=null){
        			setGSXX();
        		}
        		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ==null&& flowSubmitObj.busRecord.GSJG!=null){
                    $("#YWCZ_TABLE").attr("style", "");
                    $("#GSJG_TR").attr("style", "");
        		}
    		}
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="汇总意见"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#SFXYXT_TR").attr("style","");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="协调结果"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#SFXYXT_TR").attr("style","");
                    $("#SFXTYZ_TR2").attr("style","");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="填写意见"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#SFXYXT_TR").attr("style","");
                    $("#SFXTYZ_TR2").attr("style","");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="区审批局领导签发"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#QFSFTG_TR").attr("style","");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="填写公示结果" || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "集中公示"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#QFSFTG_TR").attr("style","");
                    $("#GSSFTG_TR2").attr("style","");
                    $("#GSJG_TR").attr("style", "");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="异议协调"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#GSSFTG_TR2").attr("style","");
                    $("#GSJG_TR").attr("style", "");
                    $("#GSXT_TR").attr("style", "");
                } else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "拟批复《施工图联合审查意见书》"
                        || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区项目投资处领导审批"
                        || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区审批局领导审签"
                        || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区管委会领导签发"
                        || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "批复《施工图联合审查意见书》") {
                    $("#YWCZ_TABLE").attr("style", "");
                    $("#GSSFTG_TR2").attr("style","");
                    $("#GSJG_TR").attr("style", "");
					if('${busRecord.GSSFTG}'==-1){
						$("#GSXT_TR").attr("style", "");
					}
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="批复《施工图联合审查意见书》"){
	    				$("#PF_TR").attr("style","");
	    			}
                }
                if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "填写公示材料"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区项目投资处领导审核"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区审批局领导签发"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "填写公示结果"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "集中公示"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "拟批复《施工图联合审查意见书》"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区项目投资处领导审批"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区审批局领导审签"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区管委会领导签发"
                    || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "批复《施工图联合审查意见书》") {
                	setGSXX();
            	}

                /*签发环节添加签发是否通过按钮*/
                if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "签发") {
                    $("#QFSFTG").attr("style", "");
                    $("#YWCZ_TABLE").attr("style", "");
                }

                /*公示阶段自动回填内容*/
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '填写公示材料'
                || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '集中公示') {
                    if(flowSubmitObj.busRecord) {
                        /*根据事项编码获取工程建设项目所对应的阶段*/
                        $.post("projectApplyController/getProjectJDByItemCode.do",{ITEM_CODE:flowSubmitObj.busRecord.ITEM_CODE},function (data) {
                            if (data) {
                                var dataObj = JSON.parse(data)
                                $("input[name='GSSXMM']").val(flowSubmitObj.busRecord.PROJECT_NAME + dataObj.NAME + '审批前公示');
                            } else {
                                $("input[name='GSSXMM']").val(flowSubmitObj.busRecord.PROJECT_NAME + '审批前公示');
                            }
                        })
                        $("input[name='SBDW']").val(flowSubmitObj.busRecord.ENTERPRISE_NAME);
                        $("input[name='JSDW']").val(flowSubmitObj.busRecord.ENTERPRISE_NAME);
                        $("input[name='JSDD']").val('平潭综合实验区');
                        $("input[name='GSKSSJ']").val(getDateStr(0));
                        $("input[name='GSJSSJ']").val(getDateStr(5));
                        $("input[name='SPCS']").val('平潭综合实验区项目投资处');
                        $("input[name='LXDH']").val('12345转');
                        $("input[name='EMAIL']").val('pt23155007@163.com');
                        $("input[name='POSTCODE']").val('350400');
                        $("input[name='ADDRESS']").val('平潭综合实验区行政审批局（区政务中心大楼）');
                    }
                }
            }
    	}

        /*获取当前年月日并用-隔开*/
        function getDateStr(AddDayCount) {
            var dd = new Date();
            dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
            var y = dd.getFullYear();
            var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
            var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
            return y+"-"+m+"-"+d;
        }
    	
    	function setGSXX(){
    		 $("#GS_TABLE").attr("style", "");
             $("#GS_TABLE :input[type='text']")
                 .each(
                         function(i) {
                             if (this.name == "ZTZ") {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "tab_text validate[required,custom[number]]");
                             } else if (this.name == "LXDH") {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "tab_text");
                             } else if (this.name == "EMAIL") {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "tab_text");
                             } else if (this.name == "POSTCODE") {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "tab_text validate[required,custom[chinaZip]]");
                             } else if (this.name == "GSKSSJ"
                                     || this.name == "GSJSSJ") {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "easyui-datebox validate[required]");
                             } else {
                                 $("input[name='" + this.name + "']")
                                         .attr("class",
                                                 "tab_text validate[required]");
                             }
                         });
    	}

		var start = {
				elem : "#_GSKSSJ",
				format : "YYYY-MM-DD",
				istime : false,
				min:laydate.now(),
				choose : function(datas) {
					if(datas==undefined){
						end.min=laydate.now();		
						end.start = datas;
					}else{
						end.min=datas;
						end.start = datas;
					}
				}
			};
			var end = {
				elem : "#_GSJSSJ",
				format : "YYYY-MM-DD",
				istime : false,
				min:laydate.now(),
				choose : function(datas) {
					if(datas==undefined){
						start.max=null;
					}else{
						start.max=datas;
					}
				}
			};
			laydate(start);
			laydate(end);
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_ZFTZSGTSJHZTB"
		//});
		if('${flowstage}'==1||'${flowstage}'==''||'${flowstage}'==null){
			document.getElementById("fjbdDiv1").style.display="none";
		}
		if('${flowstage}'==2){
			document.getElementById("fjbdDiv2").style.display="none";
		}
	});
	function FLOW_SubmitFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_ZFTZSGTSJHZTB_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFTZSGTSJHZTB_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
	
	function FLOW_TempSaveFun(flowSubmitObj){
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_ZFTZSGTSJHZTB_FORM").validationEngine("validate");
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_ZFTZSGTSJHZTB_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				 return flowSubmitObj;
			 }else{
				 return null;
			 }
		 }else{
			 return null;
		 }
	}
	
	function FLOW_BackFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
	function FLOW_ViewSumOpinionFun(flowSubmitObj){
		return flowSubmitObj;
	}
	
</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_BSFW_ZFTZSGTSJHZTB_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <input type="hidden" name="flowStage" />
    <%--===================重要的隐藏域内容=========== --%>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./tzjbxx.jsp" />
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入对接表信息部分：项目《一阶段施工图》（送审版）审查申请表，项目《一阶段施工图》（修编版）联合审查申请表--%>
			<c:if test="${flowstage=='1'||flowstage==null}">
				<jsp:include page="./annexBuildPlanSS.jsp" />
			</c:if>
			<c:if test="${flowstage=='2'}">
				<jsp:include page="./annexBuildPlanJointCheck.jsp" />
			</c:if>
		<%--结束引入对接表信息部分 --%>
		
    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>		
		
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./matterListZF.jsp" >
           <jsp:param value="T_BSFW_ZFTZSGTSJHZTB_FORM" name="formName"/>
     </jsp:include>

	<%--结束引入公用上传材料界面 --%>
		<table cellpadding="0" cellspacing="0" class="tab_tk_pro">
		<tr>
             <td colspan="4" align="center"> 
             <div  style="width:98%" align="left">
             <span style="color:gray;font-size:10pt;">注：经图审中心或“双院制”审查的《项目一阶段施工图》（含初步设计+工程量清单及预算）附件为必传，附件格式为WORD、PDF，如您还有其他附件，也可以在此提交</span></div></td>
		</tr>
	</table>
	

	
    <div class="tab_height"></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="GS_TABLE" style="display: none;">
        <tr>
            <th colspan="4">项目审批前公示</th>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 项目名称：</td>
            <td colspan="3"><input type="text" maxlength="62" style="width: 40%"
                name="GSSXMM" /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 申报单位：</td>
            <td><input type="text" maxlength="62" 
                name="SBDW" /></td>
            <td class="z_tab_width"><font class="tab_color">*</font> 建设单位：</td>
            <td><input type="text" maxlength="62"  
                name="JSDW" /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 建设地点：</td>
            <td><input type="text" maxlength="62"
                name="JSDD" /></td>
            <td class="z_tab_width"><font class="tab_color">*</font> 建设期限：</td>
            <td><input type="text" maxlength="17"
                name="JSQX" /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 总投资（万元）：</td>
            <td><input type="text" maxlength="14"
                name="ZTZ" /></td>
            <td class="z_tab_width"><font class="tab_color">*</font> 资金来源：</td>
            <td><input type="text" maxlength="16"
                name="ZJLY" /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 建设规模及主要内容：</td>
            <td colspan="3"><textarea rows="5" cols="140" maxlength="1998" name="JSGMJZYNR"></textarea> </td>
        </tr>
        <tr>
           <td class="z_tab_width"><font class="tab_color">*</font> 公示起始时间：</td>
			<td><input type="text" name="GSKSSJ"  id="_GSKSSJ" style="width:190px;height:24px;line-height: 24px;"
						class="laydate-icon" /></td>
			<td class="z_tab_width"><font class="tab_color">*</font> 公示结束时间：</td>
			<td><input type="text" name="GSJSSJ" id="_GSJSSJ" style="width:190px;height:24px;line-height: 24px;"
						class="laydate-icon" /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 审批处室：</td>
            <td><input type="text" name="SPCS" maxlength="60"
                class="tab_text" value="平潭综合实验区行政审批局项目投资处" /></td>
            <td class="z_tab_width"><font class="tab_color">*</font> 联系电话：</td>
            <td><input type="text" name="LXDH"  maxlength="20" value="12345转"
                /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 电子邮箱：</td>
            <td><input type="text" name="EMAIL" maxlength="30" value="pt23155007@163.com"
                /></td>
            <td class="z_tab_width"> 邮政编码：</td>
            <td><input type="text" name="POSTCODE"  maxlength="8" value="350400"
                /></td>
        </tr>
        <tr>
            <td class="z_tab_width"><font class="tab_color">*</font> 邮政地址：</td>
            <td colspan="3"><input type="text" maxlength="60" style="width: 40%"
                name="ADDRESS" value="平潭综合实验区行政审批局（区政务中心大楼）" /></td>
        </tr>
    </table>
    <div class="tab_height" ></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" style="display: none;">
        <tr>
            <th colspan="4">业务操作</th>
        </tr>
        <tr id="SFXYXT_TR" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>是否需要协调：</td>
            <td><eve:radiogroup typecode="SHSFXYXT" width="130px"
                    fieldname="SFXYXT" defaultvalue="-1"></eve:radiogroup></td>
        </tr>
        <tr id="SFXTYZ_TR2" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>协调是否一致：</td>
            <td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
                    fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
        </tr>
        
        <tr id="QFSFTG_TR" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>签发结果：</td>
            <td><eve:radiogroup typecode="SHSFTG" width="130px"
                    fieldname="QFSFTG" defaultvalue="1"></eve:radiogroup></td>
        </tr>
		<tr id="GSSFTG_TR2" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>公示结果：</td>
			<td><eve:radiogroup typecode="GSJG" width="130px"
					fieldname="GSSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
        <tr id="GSJG_TR" style="display: none;">
            <td class="z_tab_width"><font class="tab_color">*</font>公示情况：</td>
            <td><textarea rows="5" cols="140" maxlength="1998" class="tab_textarea validate[required]]" name="GSJG"></textarea> </td>
        </tr>
		<tr id="GSXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>协调结果：</td>
			<td><eve:radiogroup typecode="SHSFTG" width="130px"
					fieldname="XTSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
        <tr id="QFSFTG" style="display: none;">
            <td class="tab_width"><font class="tab_color">*</font>签发是否通过：</td>
            <td><eve:radiogroup typecode="QFSFTG" width="130px"
                                fieldname="QFSFTG" defaultvalue="1"></eve:radiogroup></td>
        </tr>
        
		<tr id="PF_TR" style="display: none;">
			<td class="tab_width">批复文号：</td>
			<td><input type="text" class="tab_text" name="APPROVAL_NUMBER"  maxlength="30" /> </td>
			<td class="tab_width">批复文件有效期：</td>
			<td><input type="text" class="laydate-icon"
				onclick="laydate({istime: false,min:laydate.now(), format: 'YYYY-MM-DD'})" style="width:130px; height:26px;" name="VALIDITY_DATA" /> </td>
		</tr>
    </table>
	</form>
</body>
</html>
