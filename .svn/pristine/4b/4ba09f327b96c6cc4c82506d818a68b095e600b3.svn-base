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
    <eve:resources loadres="apputil,easyui,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
	<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<jsp:include page="../visitlog/visitlogJs.jsp"></jsp:include>
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
    			FlowUtil.initFormFieldValue(eflowObj.busRecord,"T_BSFW_GQTZQYZDJCBA_FORM");
				
				if(eflowObj.busRecord.RUN_STATUS!=0){
					$("#userinfo_div input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#userinfo_div select").each(function(index){
						$(this).attr("disabled","disabled");
					});
				}
				if(eflowObj.busRecord.EXE_SUBBUSCLASS){					
					if(eflowObj.busRecord.EXE_SUBBUSCLASS.indexOf("新设")!=-1){
						$("#clsm_div").show();
						$("#xs_clsm_tr").show();
						$("#bg_clsm_tr").hide();
					 } else if(eflowObj.busRecord.EXE_SUBBUSCLASS.indexOf("变更")!=-1){
						 $("#clsm_div").show();
						 $("#xs_clsm_tr").hide();
						 $("#bg_clsm_tr").show();
					 };
				}
    		} else{
				$("[name='EXE_SUBBUSCLASS']").addClass(" validate[required]");	
			}
		}

		if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){				
			$("#addr").attr("style","");
			$("#addressee").attr("style","");
			$("#addrmobile").attr("style","");
			$("#addrprov").attr("style","");
			$("#addrcity").attr("style","");
			$("#addrpostcode").attr("style","");
			$("#addrremarks").attr("style","");
		}
	    $("input[name='FINISH_GETTYPE']").click(function(){
	    	var radio = document.getElementsByName("FINISH_GETTYPE"); 
	        for (i=0; i<radio.length; i++) { 
	            if (radio[i].checked) { 
					var str=radio[i].value;
					if("02"==str){
						$("#addr").attr("style","");
						$("#addressee").attr("style","");
						$("#addrmobile").attr("style","");
						$("#addrprov").attr("style","");
						$("#addrcity").attr("style","");
						$("#addrpostcode").attr("style","");
						$("#addrremarks").attr("style","");
					}else{	
						$("#addr").attr("style","display:none;");
						$("#addressee").attr("style","display:none;");
						$("#addrmobile").attr("style","display:none;");
						$("#addrprov").attr("style","display:none;");
						$("#addrcity").attr("style","display:none;");
						$("#addrpostcode").attr("style","display:none;");
						$("#addrremarks").attr("style","display:none;");
					}
	            } 
	        }  
		});
		 $("input[name='EXE_SUBBUSCLASS']").click(function(){
			 var Value = $(this).val();
			 if(Value.indexOf("新设")!=-1){
				 $("#clsm_div").show();
				 $("#xs_clsm_tr").show();
				 $("#bg_clsm_tr").hide();
			 } else if(Value.indexOf("变更")!=-1){
				 $("#clsm_div").show();
				 $("#xs_clsm_tr").hide();
				 $("#bg_clsm_tr").show();
			 };
		 });
	    $("input[name='MAT_SENDTYPE']").click(function(){
	    	var radio = document.getElementsByName("MAT_SENDTYPE"); 
	        for (i=0; i<radio.length; i++) { 
	            if (radio[i].checked) { 
					var str=radio[i].value;
					if("02"==str){
						$("#mataddr").attr("style","");
						$("#mataddrselect").attr("style","");
						$("#mataddressee").attr("style","");
						$("#mataddrmobile").attr("style","");
						$("#mataddrprov").attr("style","");
						$("#matpostcode").attr("style","");
						$("#mataddrcity").attr("style","");
						$("#mataddrpostcode").attr("style","");
						$("#mataddrremarks").attr("style","");
					}else{	
						$("#mataddr").attr("style","display:none;");
						$("#mataddrselect").attr("style","display:none;");
						$("#mataddressee").attr("style","display:none;");
						$("#mataddrmobile").attr("style","display:none;");
						$("#mataddrprov").attr("style","display:none;");
						$("#matpostcode").attr("style","display:none;");
						$("#mataddrcity").attr("style","display:none;");
						$("#mataddrpostcode").attr("style","display:none;");
						$("#mataddrremarks").attr("style","display:none;");
					}
	            } 
	        }  
		});

		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_GQTZQYZDJCBA"
		//});
	});
	

	function selectMataddr(){		
		//alert($("input[name='USER_ID']").val());
		//var userId = $("input[name='USER_ID']").val();
        parent.$.dialog.open("userInfoController/addrSelector.do", {
            title : "材料寄送地址选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectAddrInfo = art.dialog.data("selectAddrInfo");
                if(selectAddrInfo){
                    $("input[name='MAT_SEND_ADDRESSEE']").val(selectAddrInfo.recName);
                    $("input[name='MAT_SEND_MOBILE']").val(selectAddrInfo.mobile);
                    $("input[name='MAT_SEND_PROV']").val(selectAddrInfo.province);
                    $("input[name='MAT_SEND_CITY']").val(selectAddrInfo.city);
                    $("input[name='MAT_SEND_COUNTY']").val(selectAddrInfo.county);
                    $("input[name='MAT_SEND_POSTCODE']").val(selectAddrInfo.postcode);
                    $("input[name='MAT_SEND_ADDR']").val(selectAddrInfo.detailAdd);
					$('#matprovince').combobox('setValue', selectAddrInfo.province);
					$('#matcity').combobox('setValue', selectAddrInfo.city);
					$('#matcounty').combobox('setValue', selectAddrInfo.county);
                    
    				art.dialog.removeData("selectAddrInfo");
                }
            }
        }, false);
	}

	function selectResaddr(){		
		//alert($("input[name='USER_ID']").val());
		//var userId = $("input[name='USER_ID']").val();
        parent.$.dialog.open("userInfoController/addrSelector.do", {
            title : "材料寄送地址选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectAddrInfo = art.dialog.data("selectAddrInfo");
                if(selectAddrInfo){
                    $("input[name='RESULT_SEND_ADDRESSEE']").val(selectAddrInfo.recName);
                    $("input[name='RESULT_SEND_MOBILE']").val(selectAddrInfo.mobile);
                    $("input[name='RESULT_SEND_PROV']").val(selectAddrInfo.province);
                    $("input[name='RESULT_SEND_CITY']").val(selectAddrInfo.city);
                    $("input[name='RESULT_SEND_COUNTY']").val(selectAddrInfo.county);
                    $("input[name='RESULT_SEND_POSTCODE']").val(selectAddrInfo.postcode);
                    $("input[name='RESULT_SEND_ADDR']").val(selectAddrInfo.detailAdd);
					$('#province').combobox('setValue', selectAddrInfo.province);
					$('#city').combobox('setValue', selectAddrInfo.city);
					$('#county').combobox('setValue', selectAddrInfo.county);
                    
    				art.dialog.removeData("selectAddrInfo");
                }
            }
        }, false);
	}
		
</script>
</head>

<body  style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newui/head.jsp" />
	<%--结束编写头部文件 --%>
	<div  class="eui-main">
		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newui/images/new/add.png" >当前位置：</li>
				<li><a href="https://xzfwzx.pingtan.gov.cn/special">首页</a> > </li>
				<li style="font-size:16px">网上申报</li>
			</ul>
		</div>
	
		<jsp:include page="./formtitle.jsp" />
		<form id="T_BSFW_GQTZQYZDJCBA_FORM" method="post">
			<%--===================重要的隐藏域内容=========== --%>
			<%--开始引入公共隐藏域部分 --%>
			<jsp:include page="commonhidden.jsp" />
			<%--结束引入公共隐藏域部分 --%>
			<input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" /> 
			<input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
			<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
			<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
			<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
			<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
			<input type="hidden" name="PROJECTCODE" value="${projectCode}" />
			<input type="hidden" name="PROJECT_CODE" value="${projectCode}" />
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
						<tr>
							<th> 申报状态</th>
							<td colspan="3">
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" style="color: blue;" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS==null||EFLOW_FLOWEXE.CUR_STEPNAMES=='开始'||EFLOW_FLOWEXE.RUN_STATUS=='0'}">checked="checked"</c:if> />　待提交　　｜　</span>
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS=='1'&&EFLOW_FLOWEXE.CUR_STEPNAMES!='开始'}">checked="checked"</c:if> />　审核阶段　　｜　</span>
								　<span style="color: blue;"><input disabled="disabled" type="checkbox" <c:if test="${EFLOW_FLOWEXE.RUN_STATUS!=null&&EFLOW_FLOWEXE.RUN_STATUS!='1'}">checked="checked"</c:if> />　审核完成　</span>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<%--结束编写基本信息 --%>
			<div class="bsbox clearfix" style="display: none">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>公司架构</span></li>
					</ul>
				</div>				
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1">
						<tr>
							<th>基本架构：</th>
							<td>
								<input type="text" maxlength="256" name="GS_JBGJ"
								class="tab_text validate[]" value="${busRecord.GS_JBGJ}"/>
							</td>
							<th>股东关联关系:</th>
							<td>
								<input type="text" maxlength="256" name="GS_GDGLGX"
								class="tab_text validate[]" value="${busRecord.GS_GDGLGX}"/>
							</td>
						</tr>
						<tr>
							<th>股东情况介绍<br/>与说明：</th>
							<td>
								<input type="text" maxlength="256" name="GS_GDQKJSYSM"
								class="tab_text validate[]" value="${busRecord.GS_GDQKJSYSM}"/>
							</td>
							<th>股东投资项目情况:</th>
							<td>
								<input type="text" maxlength="256" name="GS_GDTZXMQK"
								class="tab_text validate[]" value="${busRecord.GS_GDTZXMQK}"/>
							</td>
						</tr>
						<tr>
							<th>公司未来业务模式：</th>
							<td>
								<input type="text" maxlength="256" name="GS_GSWLYWMS"
								class="tab_text validate[]" value="${busRecord.GS_GSWLYWMS}"/>
							</td>
							<th>内部组织架构:</th>
							<td>
								<input type="text" maxlength="256" name="GS_NBZZJG"
								class="tab_text validate[]" value="${busRecord.GS_NBZZJG}"/>
							</td>
						</tr>
						<tr>
							<th>治理结构：</th>
							<td>
								<input type="text" maxlength="256" name="GS_ZLJG"
								class="tab_text validate[]" value="${busRecord.GS_ZLJG}"/>
							</td>
							<th>风控措施:</th>
							<td>
								<input type="text" maxlength="256" name="GS_FKCS"
								class="tab_text validate[]" value="${busRecord.GS_FKCS}"/>
							</td>
						</tr>
						<tr>
							<th>章程或合伙协议：</th>
							<td>
								<input type="text" maxlength="256" name="GS_ZCHHHXY"
								class="tab_text validate[]" value="${busRecord.GS_ZCHHHXY}"/>
							</td>
							<th>拟投项目:</th>
							<td>
								<input type="text" maxlength="256" name="GS_NTXM"
								class="tab_text validate[]" value="${busRecord.GS_NTXM}"/>
							</td>
						</tr>
						<tr>
							<th>风控设置：</th>
							<td>
								<input type="text" maxlength="256" name="GS_FKSZ"
								class="tab_text validate[]" value="${busRecord.GS_FKSZ}"/>
							</td>
							<th>备注：</th>
							<td>
								<input type="text" maxlength="256" name="REMARK"
								class="tab_text validate[]" value="${busRecord.REMARK}"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<jsp:include page="./applyuserinfo.jsp" />
	

			<div class="bsbox clearfix"  
			<c:if test="${!fn:contains(serviceItem.PAPERSUB,'2')}">style="display: none;"</c:if>
			>
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>材料提交方式</span></li>
					</ul>
				</div>				
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1">
						<tr>
							<td colspan="2">
								　　<input type="radio" name="MAT_SENDTYPE" value="01" <c:if test="${busRecord.MAT_SENDTYPE=='01'||busRecord.MAT_SENDTYPE==null }">checked="checked"</c:if> />窗口提交
							</td>
							<td colspan="2">
								　　<input type="radio" name="MAT_SENDTYPE" value="02"  <c:if test="${busRecord.MAT_SENDTYPE=='02' }">checked="checked"</c:if> />快递送达
							</td>
						</tr>
						<tr id="mataddressee" style="display: none;">
							<th><span class="bscolor1">*</span> 寄件人姓名：</th>
							<td>
								<input type="text" maxlength="30" name="MAT_SEND_ADDRESSEE" id="mat_send_addressee" 
								class="tab_text validate[required]" value="${busRecord.MAT_SEND_ADDRESSEE}"/>
							</td>
							<th><span class="bscolor1">*</span> 电话号码:</th>
							<td>
								<input type="text" maxlength="30" name="MAT_SEND_MOBILE" id="mat_send_mobile" 
								class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${busRecord.MAT_SEND_MOBILE}"/>
							</td>
						</tr>
						<tr id="mataddrprov" style="display: none;">
							<th><span class="bscolor1">*</span> 所属省市：</th>
				<td colspan="3">					
					<input name="MAT_SEND_PROV" id="matprovince" class="easyui-combobox"  style="width:120px; height:26px;"
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#matcity').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#matcity').combobox('reload',url);  
							   }
							}
	                " value="" />					
					<input name="MAT_SEND_CITY" id="matcity" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#matcounty').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#matcounty').combobox('reload',url);  
							   }
							}
	                " value="" />	  	
					<input name="MAT_SEND_COUNTY" id="matcounty" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);  
							   }
							}
	                " value="" />              					
				</td>
						</tr>
						<tr id="matpostcode" style="display: none;">
							<th><span class="bscolor1">*</span> 邮政编码：</th>
							<td>
								<input type="text" maxlength="30" name="MAT_SEND_POSTCODE" id="mat_send_postcode" 
								class="tab_text validate[[],custom[chinaZip]]" value="${busRecord.MAT_SEND_POSTCODE}"/>
							</td>
						</tr>
						<tr id="mataddr" style="display: none;">
							<th><span class="bscolor1">*</span> 详细地址：</th>
							<td colspan="3">
								<input type="text" maxlength="126" class="tab_text" style="width: 82%;" name="MAT_SEND_ADDR" id="mat_send_addr"
								class="validate[required]" value="${busRecord.MAT_SEND_ADDR}"/>
							</td>
						</tr>
						<tr id="mataddrremarks" style="display: none;">
							<th><span class="bscolor1">*</span> 邮递备注：</th>
							<td colspan="2">
								<input type="text" maxlength="126" class="tab_text" style="width: 82%;" name="MAT_SEND_REMARKS" id="mat_send_remarks"
								 class="validate[]" value="${busRecord.MAT_SEND_REMARKS}"/>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="selectMataddr();"  class="btn2">选择地址</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="bsbox clearfix">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>办理结果领取方式</span></li>
					</ul>
				</div>				
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1">
						<tr>
							<td colspan="2">
								　　<input type="radio" name="FINISH_GETTYPE" value="01" <c:if test="${fn:contains(serviceItem.FINISH_GETTYPE,'01')||serviceItem.FINISH_GETTYPE=='01'||serviceItem.FINISH_GETTYPE==null }">checked="checked"</c:if> />窗口领取
							</td>
							<td colspan="2">
								　　<input type="radio" name="FINISH_GETTYPE" value="02" 
										<c:if test="${!fn:contains(serviceItem.FINISH_GETTYPE,'02')}">disabled="disabled"</c:if>
										<c:if test="${serviceItem.FINISH_GETTYPE=='02' }">checked="checked"</c:if> />快递送达
							</td>
						</tr>
						<tr id="addressee" style="display: none;">
							<th><span class="bscolor1">*</span> 收件人姓名：</th>
							<td>
								<input type="text" maxlength="30" name="RESULT_SEND_ADDRESSEE" id="result_send_addressee" 
								class="tab_text validate[required]" value="${busRecord.RESULT_SEND_ADDRESSEE}"/>
							</td>
							<th><span class="bscolor1">*</span> 电话号码:</th>
							<td>
								<input type="text" maxlength="30" name="RESULT_SEND_MOBILE" id="result_send_mobile" 
								class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${busRecord.RESULT_SEND_MOBILE}"/>
							</td>
						</tr>
						<tr id="addrprov" style="display: none;">
							<th><span class="bscolor1">*</span> 所属省市：</th>
				<td colspan="3">					
					<input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);  
							   }
							}
	                " value="" />					
					<input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);  
							   }
							}
	                " value="" />	  	
					<input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;" 
						data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);  
							   }
							}
	                " value="" />              					
				</td>
						</tr>
						<tr id="addrpostcode" style="display: none;">
							<th><span class="bscolor1">*</span> 邮政编码：</th>
							<td>
								<input type="text" maxlength="30" name="RESULT_SEND_POSTCODE" id="result_send_postcode" 
								class="tab_text validate[[],custom[chinaZip]]" value="${busRecord.RESULT_SEND_POSTCODE}"/>
							</td>
						</tr>
						<tr id="addr" style="display: none;">
							<th><span class="bscolor1">*</span> 详细地址：</th>
							<td colspan="3">
								<input type="text" maxlength="126" class="tab_text" style="width: 82%;" name="RESULT_SEND_ADDR" id="result_send_addr"
								class="validate[required]" value="${busRecord.RESULT_SEND_ADDR}"/>
							</td>
						</tr>
						<tr id="addrremarks" style="display: none;">
							<th><span class="bscolor1">*</span> 邮递备注：</th>
							<td colspan="2">
								<input type="text" maxlength="126" class="tab_text" style="width: 82%;" name="RESULT_SEND_REMARKS" id="result_send_remarks"
								 class="validate[]" value="${busRecord.RESULT_SEND_REMARKS}"/>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="selectResaddr();"  class="btn2">选择地址</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="bsbox clearfix" style="display:none;" id="clsm_div">
				<div class="bsboxT">
					<ul>
						<li class="on" style="background:none"><span>材料说明</span></li>
					</ul>
				</div>
				<div class="bsboxC">
					<table cellpadding="0" cellspacing="0" class="bstable1">
						<tr id="xs_clsm_tr">
							<th>办事指南（新设）：</th>
							<td>
								<a style="color: blue;" href="<%=path%>/attachFiles/readtemplate/files/办事指南（新设）.docx">办事指南（新设）.docx</a>
							</td>
						</tr>
						<tr id="bg_clsm_tr">
							<th>办事指南（变更）：</th>
							<td>
								<a style="color: blue;" href="<%=path%>/attachFiles/readtemplate/files/办事指南（变更）.docx">办事指南（变更）.docx</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
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
		</form>
		
		<%--开始引入提交DIV界面 --%>
		<jsp:include page="./submitdiv.jsp" >
		     <jsp:param value="submitForOK('T_BSFW_GQTZQYZDJCBA_FORM');" name="submitFn"/>
		     <jsp:param value="AppUtil.tempSaveWebSiteFlowForm('T_BSFW_GQTZQYZDJCBA_FORM');" name="tempSaveFn"/>
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
