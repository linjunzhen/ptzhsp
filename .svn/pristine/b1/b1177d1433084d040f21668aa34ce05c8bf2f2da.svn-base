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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<style>
.z_tab_width{width:135px; background:#fbfbfb;}
</style>
<script type="text/javascript">
	function initDisplay(flowSubmitObj){
		//设置初始化回显 是否重点项目值
		$("input[type='radio'][name='isImport'][value='"+$("input[name='IS_IMPORTANT_PROJECT']").val()+"']").attr("checked",'checked');
		//设置初始化回显 招标项目类型值
		var zbxmTypeAttr=$("input[name='ZBXM_TYPE']").val().split(",");
		if(zbxmTypeAttr.length>0){
			for(var i=0;i<zbxmTypeAttr.length;i++){
				if(parseInt(zbxmTypeAttr[i])>=1){
					$("input[name='"+types[parseInt(zbxmTypeAttr[i])-1][0]+"']").click();
				}
			}
			if(flowSubmitObj&&flowSubmitObj.busRecord!=null){
				//设置六类子项对应记录值初始化回显
				$("input[name='kcXM_Record']").val(flowSubmitObj.busRecord.KCXM_RECORD);
				$("input[name='sjXM_Record']").val(flowSubmitObj.busRecord.SJXM_RECORD);
				$("input[name='jlXM_Record']").val(flowSubmitObj.busRecord.JLXM_RECORD);
				$("input[name='gcXM_Record']").val(flowSubmitObj.busRecord.GCXM_RECORD);
				$("input[name='zysbXM_Record']").val(flowSubmitObj.busRecord.ZYSBXM_RECORD);
				$("input[name='zyclXM_Record']").val(flowSubmitObj.busRecord.ZYCLXM_RECORD);
				for(var i=0;i<types.length;i++){
					if(!$("input[name='"+types[i][0]+"_Record']").val()==""){
						var vType=$("input[name='"+types[i][0]+"_Record']").val().split("_");
						//回显 招标开式
						$("input[type=radio][name='"+types[i][0]+"zbfs'][value="+vType[0]+"]").click();
						//回显 招标金额
						$("input[name='"+types[i][0]+"zbje']").val(vType[1]);
						//回显 招标项目理由
						var vLys=vType[2].split(",");
						for(var j=0;j<vLys.length;j++){
							if(vType[0]==1){
								$("input[name='"+types[i][0]+"yqzbly"+vLys[j]+"']").click();
								if(vLys[j]=='5'&&vType.length==4){
									//回显性质
									var vXzs=vType[3].split(",");
									for(var m=0;m<vXzs.length;m++){
										$("input[name='"+types[i][0]+"yqzbXz"+vXzs[m]+"']").click();	
									}
								}
							}else if(vType[0]==2){
								$("input[name='"+types[i][0]+"bzbly"+vLys[j]+"']").click();				
							}
							
						}
					}
				}
			}
		}
	}
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
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_TZXMZBTB_FORM");
    			//获取表单字段权限控制信息
        		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        		 //初始化表单字段权限控制
        		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_TZXMZBTB_FORM");
        		 
        		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ==null&& flowSubmitObj.busRecord.GSSXMM!=null){
        			setGSXX();
        		}
        		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ==null&& flowSubmitObj.busRecord.GSJG!=null){
                    $("#YWCZ_TABLE").attr("style", "");
                    $("#GSJG_TR").attr("style", "");
        		}
    		}
    		//初始化回显招标、投标信息
    		initDisplay(flowSubmitObj);
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
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="公示"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#GSSFTG_TR2").attr("style","");
                    $("#GSJG_TR").attr("style", "");
                }else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="异议复核"){
                    $("#YWCZ_TABLE").attr("style","");
                    $("#GSSFTG_TR2").attr("style","");
                    $("#GSJG_TR").attr("style", "");
                    $("#GSXT_TR").attr("style", "");
                }
            }
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
			//laydate(start);
			//laydate(end);
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_TZXMZBTB"
		//});

		$(".isCanHide").attr("style","display:none;");	
		isHide=true;
		for(var i=0;i<types.length;i++){
			$(".tr_zbtb"+types[i][0]+"0001").css('display','none');
			$(".tr_zbtb"+types[i][0]+"0002").css('display','none');
		}//设置理由7对应的4项材料
		$(".tr_zbtbcl0002").css('display','none');
		$(".tr_zbtbcl0003").css('display','none');
		$(".tr_zbtbcl0004").css('display','none');
		$(".tr_zbtbcl0005").css('display','none');
		setFileByBZBLY();
	});
	function FLOW_SubmitFun(flowSubmitObj){
		if(!setHiddenField()) return null;
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_TZXMZBTB_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_TZXMZBTB_FORM");
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
		if(!setHiddenField()) return null;
		 //先判断表单是否验证通过
		 var validateResult =$("#T_BSFW_TZXMZBTB_FORM").validationEngine("validate");
		 if(validateResult){
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BSFW_TZXMZBTB_FORM");
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
						$("input[name='"+types[i][0]+"zbje']").focus().select();
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
	
</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_BSFW_TZXMZBTB_FORM" method="post" >
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
	
	<%--投资项目基本信息页面引入 --%>
	<jsp:include page="./tzjbxx_zbtb.jsp" />
	<%--结束引入投资项目基本信息页面 --%>
	
	<%--招标投标核准申报表页面引入 --%>
	<jsp:include page="./zbtbsbxx.jsp" />
	<%--结束引入招标投标核准申报表页面 --%>
		
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./matterListTZXM_ZBTB.jsp" >
    <jsp:param value="T_BSFW_TZXMZBTB_FORM" name="formName"/>
    </jsp:include>
	
	<%--结束引入公用上传材料界面 --%>
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	
    <div class="tab_height"></div>
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
			<td class="tab_width"><font class="tab_color">*</font>是否驳回异议：</td>
			<td><eve:radiogroup typecode="YesOrNo" width="130px"
					fieldname="XTSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
    </table>
	</form>
</body>
</html>
