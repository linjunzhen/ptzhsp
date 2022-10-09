<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否拥有入驻方式更改权限
    boolean isRzfsgg = resKey.contains("RZBSDTFSGG");
	//判断是否超级管理员或经营范围备注描述管理员
	if("__ALL".equals(resKey)||isRzfsgg){
	    request.setAttribute("isRzfsgg",true);
	}else{
	    request.setAttribute("isRzfsgg",false);
	}
%>
<head>
<style type="text/css">
.layout-panel-west{
 overflow: visible;
 box-shadow:0px 0px 15px rgba(62,139,255,0.2);
 height: 100%;
}
</style>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,swfupload,json2,validationegine"></eve:resources>

<link rel="stylesheet" href="webpage/main/css/fonticon.css">
<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">

	$(function() {
		//申请方式/纸质材料提交方式回显
		var SQFS = '${serviceItem.SQFS}';
		var SQZHYQ_2 = '${serviceItem.SQZHYQ_2}';
		var SQZHYQ_3 = '${serviceItem.SQZHYQ_3}';
		var SQZHYQ_4 = '${serviceItem.SQZHYQ_4}';
		var PAPERSUB = '${serviceItem.PAPERSUB}';
		checkEcho(SQFS, "SQFS");
		checkEcho(SQZHYQ_2, "SQZHYQ_2");
		checkEcho(SQZHYQ_3, "SQZHYQ_3");
		checkEcho(SQZHYQ_4, "SQZHYQ_4");
		checkEcho(PAPERSUB, "PAPERSUB");		
		
		if('${serviceItem.BGSJ}'==null||'${serviceItem.BGSJ}'==''){
			$("textarea[name='BGSJ']").val("工作日 8:15-12:00 14:00-17:30");
		}
		if('${serviceItem.TRAFFIC_GUIDE}'==null||'${serviceItem.TRAFFIC_GUIDE}'==''){
			$("textarea[name='TRAFFIC_GUIDE']").val("乘坐平潭闽运公交2、3、9、K1路到区管委会公交站下车后步行");
		} 
		if('${serviceItem.BLDD}'==null||'${serviceItem.BLDD}'==''){
			$("textarea[name='BLDD']").val("平潭综合实验区行政服务中心大厅");
		}
		if('${serviceItem.JDDH}'==null||'${serviceItem.JDDH}'==''){
			$("textarea[name='JDDH']").val("0591-23171110");
		}
		var isRzfsgg = '${isRzfsgg}';
		if(isRzfsgg=='false'){
			$("input:radio[name='RZBSDTFS'][value='in01']").attr("disabled",true);
			$("input:radio[name='RZBSDTFS'][value='in02']").attr("disabled",true);
		}
	});
	function checkEcho(checkeds, name) {
		var checkArray = checkeds.split(",");
		var checkBoxAll = $("input[name='" + name + "']");
		for (var i = 0; i < checkArray.length; i++) {
			$.each(checkBoxAll, function(j, checkbox) {
				var checkValue = $(checkbox).val();
				if (checkArray[i] == checkValue) {
					$(checkbox).attr("checked", true);
				}
			})
		}
	}
</script>
<script type="text/javascript"
	src="webpage/wsbs/departServiceItem/js/itemInfo.js"></script>
</head>

<style type="text/css">
	/*****增加CSS****/
	.btnOneP {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 107px;
	}
</style>
<script type="text/javascript">
    /**
     * 电子证照名称选择器
     **/
    function showSelectEvidence(){
        var induIds = $("input[name='RESULT_IDS']").val();
        var url = "domesticControllerController/evidenceSelector.do?noAuth=true&induIds="+induIds+"&allowCount=0&checkCascadeY=&"
            +"checkCascadeN=&ISTZINDUSTRY=-1";
        $.dialog.open(url, {
            title : "电子证照名称选择器",
            width:"100%",
            lock: true,
            resize:false,
            height:"100%",
            close: function () {
                var selectInduInfo = art.dialog.data("selectInduInfo");
                if(selectInduInfo){
                    $("[name='RESULT_IDS']").val(selectInduInfo.induIds);
                    $("[name='RESULT_NAME']").val(selectInduInfo.induNames);
                    art.dialog.removeData("selectInduInfo");
                }
            }
        }, false);
    };

    function statusFormatter(val,row) {
		if (val == 'Y') {
			return "启用";
		} else {
			return "禁用";
		}
	}

</script>
<body class="eui-diabody" style="margin:0px;">

	<div id="SysRoleGrantFormDiv" class="easyui-layout" fit="true">
		<input type="hidden" name="CurrentStep" value="1">
		<div data-options="region:'north',split:true,border:false"
			style="height: 100px;">
			<div class="flow_container">
				<ul>
					<li class="flow_active" id="flow_node_1"><div class="pro_base">1</div>
						<span onclick="jumpToTargetDiv(1);">基本信息</span></li>
					<li class="flow_nodone" id="flow_node_2"><div class="pro_base">2</div>
						<span onclick="jumpToTargetDiv(2);">申请方式</span></li>
					<li class="flow_nodone" id="flow_node_3"><div class="pro_base">3</div>
						<span onclick="jumpToTargetDiv(3);">时限配置</span></li>
					<li class="flow_nodone" id="flow_node_4"><div class="pro_base">4</div>
						<span onclick="jumpToTargetDiv(4);">办公指引</span></li>
					<li class="flow_nodone" id="flow_node_5"><div class="pro_base">5</div>
						<span onclick="jumpToTargetDiv(5);">申请材料</span></li>
					<li class="flow_nodone" id="flow_node_6"><div class="pro_base">6</div>
						<span onclick="jumpToTargetDiv(6);">邮递服务</span></li>
					<li class="flow_nodone" id="flow_node_7"><div class="pro_base">7</div>
						<span onclick="jumpToTargetDiv(7);">清单配置</span></li>
					<li class="flow_nodone" id="flow_node_8"><div class="pro_base">8</div>
						<span onclick="jumpToTargetDiv(8);">办理流程</span></li>
					<li class="flow_nodone" id="flow_node_9"><div class="pro_base">9</div>
						<span onclick="jumpToTargetDiv(9);">收费配置</span></li>
					<li class="flow_nodone" id="flow_node_10"><div class="pro_base">10</div>
						<span onclick="jumpToTargetDiv(10);">预审人员</span></li>
					<li class="flow_nodone" id="flow_node_11"><div class="pro_base">11</div>
						<span onclick="jumpToTargetDiv(11);">常见问题</span></li>
					<li class="flow_nodone" id="flow_node_12"><div class="pro_base">12</div>
						<span onclick="jumpToTargetDiv(12);">其他配置</span></li>
					<li class="flow_nodone" id="flow_node_13"><div class="pro_base">13</div>
						<span onclick="jumpToTargetDiv(13);">操作日志</span></li>
<%--					<li class="flow_nodone" id="flow_node_14"><div class="pro_base">14</div>--%>
<%--						<span onclick="jumpToTargetDiv(14);">数据质检</span></li>--%>
<%--					<li class="flow_nodone" id="flow_node_15"><div class="pro_base">15</div>--%>
<%--						<span onclick="jumpToTargetDiv(15);">海外版事项配置</span></li>--%>
				</ul>
				<div class="flow_progress">
					<div class="flow_progress_bar">
						<span class="flow_progress_highlight" id="progress_bar"
							style="width:0%;"></span>
					</div>
				</div>
			</div>
		</div>

		<div data-options="region:'center',split:true,border:false">
			<div title="基本信息" id="StepDiv_1" style="width:100%;height:100%;">
				<div class="easyui-layout" fit="true">

					<div data-options="region:'center'">
						<form action="departServiceItemController.do?saveOrUpdate"
							id="StepForm_1">
							<!--==========隐藏域部分开始 ===========-->
							<input type="hidden" name="RIGHT_ID" value="${serviceItem.RIGHT_ID}">
							<input type="hidden" name="ITEM_ID" value="${serviceItem.ITEM_ID}">
							<input type="hidden" name="DEPART_ID" value="${serviceItem.DEPART_ID}">
							<input type="hidden" name="CATALOG_CODE" value="${serviceItem.CATALOG_CODE}">
							<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}">
							<input type="hidden" name="NUM_CODE" value="${serviceItem.NUM_CODE}">
							<input type="hidden" name="BUS_TYPEIDS" value="${serviceItem.BUS_TYPEIDS}">
							<input type="hidden" name="FWSXZT" value="${serviceItem.FWSXZT}">
							<input type="hidden" name="shan" value="${shan}">
							<input type="hidden" name="papersub" value="${serviceItem.PAPERSUB}">
							<input type="hidden" name="preAuditer" value="${serviceItem.PREAUDITER_NAMES}">
							<input type="hidden" name="bdlcdyName" value="${serviceItem.BDLCDYNAME}">
							<input type="hidden" name="RESULT_IDS" value="${serviceItem.RESULT_IDS}">
							<input type="hidden" name="RESULT_PATH" value="${serviceItem.RESULT_PATH}">
							<input type="hidden" name="STANDARD_CATALOG_ID" value="${serviceItem.STANDARD_CATALOG_ID}">
							<%--<input type="hidden" name="ITEMSTAR" value="3">
							<input type="hidden" name="IMPL_DEPART">
							<input type="hidden" name="CKCS_1" value="">--%>
							<!--==========隐藏域部分结束 ===========-->
							<table style="width:100%;border-collapse:collapse;"
								class="dddl-contentBorder dddl_table">
								<tr>
									<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">所属权责名称：</span>
										<input type="text" style="width:500px;float:left;"
										class="eve-input validate[]" readonly="readonly"
										value="${serviceItem.RIGHT_NAME}" name="RIGHT_NAME" />
										
										<input value="选择权责" type="button" class="eve_inpbtn"
											onclick="selectBillOfRight()" />
										<input value="清除权责" type="button" class="eve_inpbtn"
											   onclick="clearBillOfRight()" />
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">权责子项：</span>--%>
<%--										<input type="text" style="width:500px;float:left;"--%>
<%--										class="eve-input" disabled="disabled"--%>
<%--										value="${serviceItem.SUBITEM_NAME}" name="SUBITEM_NAME" /></td>--%>
<%--								</tr>--%>
								<tr>
									<td>
										<span
											style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>事项名称：</span>
										<input type="text" style="width:500px;float:left;"
											   maxlength="126" class="eve-input validate[required]"
											   value="${serviceItem.ITEM_NAME}" name="ITEM_NAME" /> <%-- <c:if test="${serviceItem.ITEM_ID==null}">
											<input value="选择事项" type="button" class="eve_inpbtn"
												onclick="selectBillItem()" />
										</c:if> --%></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>事项类型：</span>
										<eve:radiogroup typecode="ServiceItemKind" width="900"
														value="${serviceItem.SXXZ}" fieldname="SXXZ">
										</eve:radiogroup></td>
								</tr>
								<tr id="gftype" style="display: none;">
									<td><span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>公共服务类别：</span>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="GFTYPE" width="650px;" clazz="checkbox"
											dataParams="GFTYPE" value="${serviceItem.GFTYPE}">
										</eve:excheckbox></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;">标准化事项目录：</span>
										<input type="text" style="width:500px;float:left;"
											   class="eve-input" disabled="disabled"
											   value="${serviceItem.STANDARD_CATALOG_NAME}" name="STANDARD_CATALOG_NAME" />
										<input value="选择标准化事项目录" type="button" class="eve_inpbtn"
											   onclick="selectStdCatalog()" />
									</td>
								</tr>
								<tr>
									<td><span
										style="width: 130px;float:left;text-align:right;">目录名称：</span>
										<input type="text" style="width:500px;float:left;"
										class="eve-input"
										value="${serviceItem.CATALOG_NAME}" name="CATALOG_NAME"
										readonly="readonly" /><c:if
											test="${serviceItem.ITEM_ID==null}">
											<input value="选择目录" type="button" class="eve_inpbtn"
												onclick="selectCatalog()" />
										</c:if></td>
								</tr>
								<tr>
									<td>
									<c:if test="${serviceItem.ITEM_ID != null}">
									<span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>本地事项编码：</span>
										<input type="text" style="width:150px;float:left;"
										maxlength="30"
										class="eve-input validate[required,custom[onlyLetterNumber]]" value="${serviceItem.ITEM_CODE}"
										name="ITEM_CODE" disabled="disabled"/>
									</c:if>
										<span style="width: 100px;text-align:right; margin-left:68px;"><font class="dddl_platform_html_requiredFlag">*</font>唯一码：</span>
										<input type="text" style="width:150px;margin-bottom:4px;"
										maxlength="30"
										class="eve-input validate[custom[onlyLetterNumber]]"
										<%--										readonly="readonly" --%>
<%--										<c:if test="${serviceItem.SWB_ITEM_CODE==null||serviceItem.SWB_ITEM_CODE=='' }">value="无"</c:if>--%>
										value="${serviceItem.SWB_ITEM_CODE}"
										name="SWB_ITEM_CODE" /> <font
										class="dddl_platform_html_requiredFlag">*唯一码来自省网上办事大厅事项编码。友情链接：
											<a target="_blank"
											href="http://zwfw.fujian.gov.cn/service.action?fn=theirGuide&userType=3">
												http://zwfw.fujian.gov.cn/service.action?fn=theirGuide&userType=3
										</a>
									</font></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>唯一码二次确认：</span>
										<input type="text" style="width:150px;margin-bottom:4px;"
										maxlength="64" class="eve-input validate[required]"
										value="${serviceItem.SWB_ITEM_CODE_ECQR}"
										name="SWB_ITEM_CODE_ECQR" />
										若有唯一码请填写唯一码，若无唯一码，录入内容视同红字部分： <font
										class="dddl_platform_html_requiredFlag">
											经本人核实该事项不入驻省网办，故无唯一码，由此产生后果由本人负责。 </font></td>
								</tr>
								<tr>
									<td>
										<span
											style="width: 130px;float:left;text-align:right;">省大厅事项编号：</span>
										<input type="text" style="width:150px;float:left;"
											   maxlength="40"
											   class="eve-input validate[custom[onlyLetterNumber]]"
											   readonly="readonly" value="${serviceItem.ROOM_ITEM_CODE}"
											   name="ROOM_ITEM_CODE" />
										<span style="width: 100px;text-align:right; margin-left:68px;">原属地系统事项编码：</span>
										<input type="text" style="width:150px;margin-bottom:4px;"
											   maxlength="40" value="${serviceItem.AREA_ITEM_CODE}" name="AREA_ITEM_CODE"
											   class="eve-input validate[custom[onlyLetterNumber]]">
									</td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>办件类型：</span>
										<eve:eveselect clazz="eve-input validate[required]"
													   dataParams="ServiceItemType" value="${serviceItem.SXLX}"
													   dataInterface="dictionaryService.findDatasForSelect"
													   defaultEmptyText="请选择办件类型" name="SXLX"
													   onchange="setJBJCNSX(this.value)">
										</eve:eveselect> <font class="dddl_platform_html_requiredFlag">(承诺件请选择普通件。)</font>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td>--%>
<%--										<span style="width: 130px;float:left;text-align:right;">是否转报上级：</span>--%>
<%--										<eve:radiogroup typecode="YesOrNo" width="250px"--%>
<%--														defaultvalue="0" value="${serviceItem.IS_ZBSJ}"--%>
<%--														fieldname="IS_ZBSJ">--%>
<%--										</eve:radiogroup>--%>
<%--									</td>--%>
<%--								</tr>--%>
								<tr>
									<td>
										<span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>行使层级：</span>
										<eve:radiogroup
												typecode="xscj" width="130px" value="${serviceItem.EXERCISE_LEVEL}"
												fieldname="EXERCISE_LEVEL">
										</eve:radiogroup>
									</td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>权利来源：</span>
										<eve:radiogroup onclick="qllyCheck(this.value)" typecode="qlly"
														width="130px" value="${serviceItem.RIGHT_SOURCE}"
														fieldname="RIGHT_SOURCE" defaultvalue="01">
										</eve:radiogroup> <input type="text" style="width:235px;float:left;"
																 disabled="disabled" value="${serviceItem.RIGHT_SOURCE_OTHER}"
																 maxlength="16" class="eve-input"
																 name="RIGHT_SOURCE_OTHER" />
									</td>
								</tr>
								<tr>
									<td colspan="2"><span
											style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>实施主体：</span> <input
											type="text" style="width:400px;float:left;" maxlength="126"
											class="eve-input validate[required]" onclick="selectSSDW()"
											value="${serviceItem.IMPL_DEPART}" name="IMPL_DEPART"
											id="IMPL_DEPART" />
										<input type="hidden" name="IMPL_DEPART_ID" id="IMPL_DEPART_ID" value="${serviceItem.IMPL_DEPART_ID}">
										<span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>实施主体性质：</span>
										<eve:eveselect clazz="eve-input validate[required]"
													   defaultEmptyText="请选择实施主体性质" dataParams="SSZTXZ"
													   value="${serviceItem.IMPL_DEPART_XZ}"
													   dataInterface="dictionaryService.findDatasForSelect"
													   name="IMPL_DEPART_XZ">
										</eve:eveselect>
									</td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">联系人：</span>
										<input type="text" style="width:400px;float:left;"
											   maxlength="200" class="eve-input"
											   value="${serviceItem.LXR}" name="LXR" />
										<span style="width: 130px;float:left;text-align:right;">委托部门：</span>
										<input type="text" style="width:400px;float:left;display: none;"
											   maxlength="128" class="eve-input"
											   value="${serviceItem.WTBM}" name="WTBM" />
									</td>
								</tr>

								<tr>
									<td><span style="width: 130px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>主办处室：</span>
										<input type="text" style="width:400px;float:left;"
											   maxlength="200" readonly="readonly"
											   class="eve-input validate[required]" value="${serviceItem.ZBCS}"
											   name="ZBCS" onclick="selectDepartName2()" />
										<input type="hidden" name="ZBCS_ID" value="${serviceItem.ZBCS_ID}">
										<span style="width: 130px;float:left;text-align:right;">会办处室：</span>
											<input type="text" style="width:400px;float:left;"
												   maxlength="62" readonly="readonly" class="eve-input "
												   value="${serviceItem.HBCS}" name="HBCS"
												   onclick="selectDepartName()" />
								</tr>
<%--								<tr>--%>
<%--									<td colspan="2"><span--%>
<%--											style="width: 120px;float:left;text-align:right;">实施单位：</span> <input--%>
<%--											type="text" style="width:400px;float:left;" maxlength="126"--%>
<%--											class="eve-input validate[required]" onclick="selectSSDW()"--%>
<%--											value="${serviceItem.IMPL_DEPART}" name="IMPL_DEPART"--%>
<%--											id="IMPL_DEPART" />--%>
<%--										<input type="hidden" name="IMPL_DEPART_ID" id="IMPL_DEPART_ID" value="${serviceItem.IMPL_DEPART_ID}">--%>
<%--										<font class="dddl_platform_html_requiredFlag">*</font></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td><span style="width: 120px;float:left;text-align:right;">主办处室：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--											   maxlength="200" readonly="readonly"--%>
<%--											   class="eve-input validate[required]" value="${serviceItem.ZBCS}"--%>
<%--											   name="ZBCS" onclick="selectDepartName2()" />--%>
<%--										<input type="hidden" name="ZBCS_ID" value="${serviceItem.ZBCS_ID}">--%>
<%--										<font class="dddl_platform_html_requiredFlag">*</font></td>--%>
<%--									<td><span style="width: 120px;float:left;text-align:right;">会办处室：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--											   maxlength="62" readonly="readonly" class="eve-input "--%>
<%--											   value="${serviceItem.HBCS}" name="HBCS"--%>
<%--											   onclick="selectDepartName()" /></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									&lt;%&ndash; <td><span style="width: 120px;float:left;text-align:right;">实施主体：</span>--%>
<%--                                        <input type="text" style="width:400px;float:left;"--%>
<%--                                        maxlength="62" class="eve-input validate[required]"--%>
<%--                                        value="${serviceItem.SUBJECT}" name="SUBJECT" /> <font--%>
<%--                                        class="dddl_platform_html_requiredFlag">*</font></td> &ndash;%&gt;--%>
<%--									<td colspan="2"><span style="width: 120px;float:left;text-align:right;">实施主体性质：</span>--%>
<%--										<eve:eveselect clazz="eve-input validate[]"--%>
<%--													   defaultEmptyText="请选择实施主体性质" dataParams="SSZTXZ"--%>
<%--													   value="${serviceItem.IMPL_DEPART_XZ}"--%>
<%--													   dataInterface="dictionaryService.findDatasForSelect"--%>
<%--													   name="IMPL_DEPART_XZ">--%>
<%--										</eve:eveselect></td>--%>
<%--								</tr>--%>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;">是否需要中介服务：</span>
										<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="0"
														value="${serviceItem.IS_NEED_AGENCY}" fieldname="IS_NEED_AGENCY">
										</eve:radiogroup></td>
								</tr>
								<tr id="agencyService">
									<td><span
											style="width: 130px;float:left;text-align:right;">中介服务：</span>
										<input type="text" style="width:500px;float:left;"
											   value="${serviceItem.AGENCYSERVICE_NAME}" readonly="readonly"
											   class="eve-input validate[required]" name="AGENCYSERVICE_NAME" />
										<input type="hidden" name="AGENCYSERVICE_ID"
											   value="${serviceItem.AGENCYSERVICE_ID}" /> <font
												class="dddl_platform_html_requiredFlag">*</font>&nbsp;<input
												type="button" value="选择中介服务" class="eve-button"
												style="vertical-align: middle" onclick="selectAgency()" /></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;">是否支持一窗通办：</span>
										<eve:radiogroup typecode="YesOrNo" width="150px"
														defaultvalue="0" value="${serviceItem.IS_YCTB}"
														fieldname="IS_YCTB">
										</eve:radiogroup></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;">事项属地：</span>
										<eve:radiogroup typecode="isFta" width="130px" defaultvalue="3"
														value="${serviceItem.FTA_FLAG}" fieldname="FTA_FLAG">
										</eve:radiogroup></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">年审批数量限制：</span>
										<input type="text" style="width:400px;float:left;"
											   maxlength="200" class="eve-input"
											   value="${serviceItem.NSPSLXZ}" name="NSPSLXZ" /></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;">备注说明：</span>
										<textarea class="eve-textarea validate[],maxSize[1000]"
												  style="width: 400px;height:100px;" name="BZSM">${serviceItem.BZSM}</textarea></td>
								</tr>
								<tr>
									<td><span
											style="width: 130px;float:left;text-align:right;"><font
											class="dddl_platform_html_requiredFlag">*</font>面向用户对象：</span> <eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="MXYHDX" width="650px;" clazz="checkbox"
											dataParams="faceUserType" value="${serviceItem.MXYHDX}">
									</eve:excheckbox></td>
								</tr>
								<tr id="ptheme" style="display: none;">
									<td><span
											style="width: 130px;float:left;text-align:right;">个人主题分类：</span>
										<eve:excheckbox
												dataInterface="busTypeService.findDatasByClassForSelect"
												name="GRZTFL" width="650px;"
												clazz="checkbox validate[required]" dataParams="GRZTFL"
												value="${serviceItem.GRZTFL}">
										</eve:excheckbox></td>
								</tr>
								<tr id="ltheme" style="display: none;">
									<td><span
											style="width: 130px;float:left;text-align:right;">法人主题分类：</span>
										<eve:excheckbox
												dataInterface="busTypeService.findDatasByClassForSelect"
												name="FRZTFL" width="650px;"
												clazz="checkbox validate[required]" dataParams="FRZTFL"
												value="${serviceItem.FRZTFL}">
										</eve:excheckbox></td>
								</tr>

								<tr>
									<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>办理结果</a></div></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;"><font
											class="dddl_platform_html_requiredFlag">*</font>审批结果类型：</span> <eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="FINISH_TYPE" width="350px;"
											clazz="checkbox validate[required]" dataParams="FinishType"
											value="${serviceItem.FINISH_TYPE}">
									</eve:excheckbox></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;"><font
											class="dddl_platform_html_requiredFlag">*</font>结果获取方式：</span> <eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="FINISH_GETTYPE" width="550px;"
											clazz="checkbox validate[required]" dataParams="FinishGetType"
											value="${serviceItem.FINISH_GETTYPE}">
									</eve:excheckbox></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">结果获取说明：</span>
										<textarea rows="5" cols="5"
												  class="eve-textarea validate[maxSize[250]]"
												  style="width: 550px" name="FINISH_INFO">${serviceItem.FINISH_INFO}</textarea>

										<p style="margin-left: 130px;">填写是否可以代领，需要提供什么证明材料,如要邮递材料还应说明邮递地址、邮递接收人、收费方式、接收人电话、是否有指定的快递等说明;
										</p></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">审批结果名称：</span>
										<input type="text" style="width:400px;float:left;"
											   maxlength="1000" class="eve-input"
											   value="${serviceItem.RESULT_NAME}" name="RESULT_NAME" />
										<a href="javascript:showSelectEvidence();" class="btnOneP BUS_SCOPE" style="width:112px;" >电子证照名称查询</a>
										<p  class="dddl_platform_html_requiredFlag">若未找到对应证照，请联系智慧岛中心，林振兴，‭15959016804‬。 </p>
									</td>
								</tr>
								<tr>
									<td><span style="width: 170px;float:left;text-align:right;">审批结果样本(仅限图片)：</span>

										<a href="javascript:void(0);" onclick="openResultFileUploadDialog()">
											<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
										</a>
										<div id="RESULT_PATH_DIV"></div></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">审批结果说明：</span>
										<input type="text" style="width:400px;float:left;"
											   maxlength="64" class="eve-input"
											   value="${serviceItem.RESULT_DESC}" name="RESULT_DESC" /></td>
								</tr>
								<tr>
									<td><span style="width: 130px;float:left;text-align:right;">绑定共享材料目录：</span>
										<input type="hidden" value="${serviceItem.RESULT_BINDCATALOG_CODE}" name="RESULT_BINDCATALOG_CODE" />
										<input type="text" style="width:400px;float:left;"
											   value="${serviceItem.RESULT_BINDCATALOG_NAME}" class="eve-input" name="RESULT_BINDCATALOG_NAME"
											   readonly="readonly" onclick="selectLicenseCatalog();" /> &nbsp;<a
												href="javascript:;" class="btnOneP" style="width: 30px;"
												onclick="$('input[name=\'RESULT_BINDCATALOG_CODE\']').val('');$('input[name=\'RESULT_BINDCATALOG_NAME\']').val('');">清除</a>
									</td>
								</tr>








<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">事项属地：</span>--%>
<%--										<eve:radiogroup typecode="isFta" width="130px" defaultvalue="3"--%>
<%--											value="${serviceItem.FTA_FLAG}" fieldname="FTA_FLAG">--%>
<%--										</eve:radiogroup></td>--%>
<%--								</tr>--%>
								<%-- <tr id="undertake" style="display: none;">
									<td><span
										style="width: 130px;float:left;text-align:right;">是否已承接：</span>
										<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="1"
											value="${serviceItem.IS_UNDERTAKE}" fieldname="IS_UNDERTAKE">
										</eve:radiogroup></td>
								</tr> --%>
<%--								<tr id="billtype" style="display: none;">--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">清单属性：</span>--%>
<%--										<eve:radiogroup typecode="billType" width="130px"--%>
<%--											defaultvalue="ql" value="${serviceItem.BILL_TYPE}"--%>
<%--											fieldname="BILL_TYPE">--%>
<%--										</eve:radiogroup> <input type="text" style="float:left;" maxlength="16"--%>
<%--										class="eve-input validate[required]" disabled="disabled"--%>
<%--										value="${serviceItem.BILL_TYPE_OTHER}" name="BILL_TYPE_OTHER" />--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">数量限制说明：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--										maxlength="126" class="eve-input"--%>
<%--										value="${serviceItem.SLXZSM}" name="SLXZSM" /></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">备注说明：</span>--%>
<%--										<textarea class="eve-textarea" maxlength="1024"--%>
<%--											style="width: 400px;height:80px;" name="BZSM">${serviceItem.BZSM}</textarea></td>--%>
<%--								</tr>--%>

<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">关键字：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--										maxlength="126" class="eve-input"--%>
<%--										value="${serviceItem.KEYWORD}" name="KEYWORD" /><font--%>
<%--										class="dddl_platform_html_requiredFlag">(多个关键字以“|”分割)</font></td>--%>
<%--								</tr>--%>
							</table>
<%--							<table style="width:100%;border-collapse:collapse;"--%>
<%--								class="dddl-contentBorder dddl_table">--%>
<%--								<tr>--%>
<%--									<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>承接信息</a></div></td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">是否已承接：</span>--%>
<%--										<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--											defaultvalue="0" value="${serviceItem.IS_UNDERTAKE}"--%>
<%--											fieldname="IS_UNDERTAKE">--%>
<%--										</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font></td>--%>
<%--								</tr>--%>
<%--								<tr id="IS_UNDERTAKEMC_TR"--%>
<%--									<c:if test="${serviceItem.IS_UNDERTAKE=='1'}">style="display: none;"</c:if>>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">来函名称：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--										maxlength="126" class="eve-input"--%>
<%--										value="${serviceItem.WCJLHMC}" name="WCJLHMC" /></td>--%>
<%--								</tr>--%>
<%--								<tr id="IS_UNDERTAKEWH_TR"--%>
<%--									<c:if test="${serviceItem.IS_UNDERTAKE=='1'}">style="display: none;"</c:if>>--%>
<%--									<td><span--%>
<%--										style="width: 130px;float:left;text-align:right;">来函文号：</span>--%>
<%--										<input type="text" style="width:400px;float:left;"--%>
<%--										maxlength="126" class="eve-input"--%>
<%--										value="${serviceItem.WCJLHWH}" name="WCJLHWH" /></td>--%>
<%--								</tr>--%>
<%--							</table>--%>
						</form>
					</div>
				</div>
			</div>

			<div title="申请方式" style="display: none;" id="StepDiv_2">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_2">
					<input type="hidden" name="OLDRZBSDTFS_TEXT" value="">
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>申请方式</a></div></td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>是否进驻政务大厅：
								</span>
								<eve:radiogroup typecode="YesOrNo" width="150px" defaultvalue="1"
												value="${serviceItem.SFJZZWDT}" fieldname="SFJZZWDT" onclick="changeSfjzzwdt(this.value)">
								</eve:radiogroup>
								<span style="width: 150px;float:left;text-align:right;" id="rzfsText"><font class="dddl_platform_html_requiredFlag">*</font>入驻方式：
								</span>
								<eve:eveselect
										clazz="eve-input validate[required]" dataParams="zwdtrzfs"
										value="${serviceItem.ZWDTRZFS}"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择入驻方式" name="ZWDTRZFS" id="ZWDTRZFS">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">入驻办事大厅方式：
								</span>
								<eve:radiogroup typecode="RZBSDTFS" width="150px" defaultvalue="in04"
												value="${serviceItem.RZBSDTFS}" fieldname="RZBSDTFS">
								</eve:radiogroup>
								(备注：入驻方式更改为“指南式“或”链接式“需来函申请)
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>业务办理系统：</span>
								<input type="text" style="width:400px;float:left;"
									   class="eve-input" value="福州市----平潭县"
									   name="YWBLXT" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">必须现场办理原因：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="64" class="eve-input"
									   value="${serviceItem.BXXCBLYY}" name="BXXCBLYY" />
							</td>
						</tr>

						<%--						<tr id="APPLY_URL_TR">--%>
						<%--							<td><span style="width: 150px;float:left;text-align:right;">群众办事网址--%>
						<%--									<font class="dddl_platform_html_requiredFlag" id="applyurltag" style="display: none;">*</font>:--%>
						<%--								</span> --%>
						<%--								<input type="text" style="width:400px;float:left;"--%>
						<%--								maxlength="254"--%>
						<%--								class="eve-input"--%>
						<%--								value="${serviceItem.APPLY_URL}" id="APPLY_URL" name="APPLY_URL" />--%>
						<%--								<font class="dddl_platform_html_requiredFlag">仅限互联网</font>--%>
						<%--							</td>--%>
						<%--						</tr>--%>
					</table>
					<%--					<p class="info">--%>
					<%--						1、指南式：仅在平潭办事大厅发布详细的办事指南信息，必须到物理窗口申请（无法网上申报）；<br>--%>
					<%--						2、链接式：在平潭办事大厅发布详细的办事指南信息且提供网上申请链接；（选择该方式，必须填写有效的互联网申请链接用于申报人申请跳转）<br>--%>
					<%--						3、收办分离式：审批人员在平潭办事大厅进行网上预审，预审通过后分发办件到部门自建的业务系统或者各级行政服务中心业务办理系统进行办理；--%>
					<%--						（选择该方式，必须选择与省网上办事大厅已实现对接的业务办理系统）<br>--%>
					<%--						4、全流程：事项入驻办事大厅，并在平潭办事大厅的行政审批和公共服务事项业务办理系统进行网上申报预审和业务办理。--%>
					<%--					</p>--%>
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td rowspan="5" style="width: 10%;text-align: center;">申请方式<font
									class="dddl_platform_html_requiredFlag">*</font></td>
							<td style="width: 30%;text-align: center;">申请方式</td>
							<td style="width: 8%;text-align: center;">到窗口最多次数（含领取结果）</td>
							<td style="width: 20%;text-align: center;">申请账户要求</td>
							<td style="width: 14%;text-align: center;">承诺到窗口最多次数说明</td>
						</tr>
						<tr>
							<td><input type="checkbox" class="checkbox" name="SQFS" disabled="disabled"
									   id="SQFS1" onclick="smallChange1(this)" checked="checked"
									   value="1">行政服务中心窗口受理（即办件请选择1次）</td>
							<td style="text-align: center;"><eve:eveselect
									clazz="eve-input validate[required]" dataParams="CKCS"
									value="${serviceItem.CKCS_1}"
									dataInterface="dictionaryService.findDatasForSelect"
									name="CKCS_1">
							</eve:eveselect></td>
							<td style="text-align: center;"></td>
							<td style="text-align: center;"><input type="text"
																   name="CNSM_1" value="${serviceItem.CNSM_1}" class="eve-input"
																   maxlength="16" style="width:90%;"></td>
						</tr>
						<tr id="threestar">
							<td><input type="checkbox" class="checkbox"
							<%--								onclick="alert('该选项无法取消！');return false;" --%>
									   <c:if test="${serviceItem.RZBSDTFS=='in01'}">checked="checked"</c:if>
									   name="SQFS" id="SQFS2" onclick="smallChange2(this)"
									    
									   value="2">【三星】网上申请和预审，窗口纸质材料收件受理（收到纸质材料后才能受理）
							</td>
							<td style="text-align: center;">
								<%-- <input type="text"
								name="CKCS_2"
								<c:if test="${serviceItem.RZBSDTFS=='in01'}">value="-1"</c:if>
								<c:if test="${serviceItem.RZBSDTFS!='in01'}">value="${serviceItem.CKCS_2}"</c:if>
								class="eve-input validate[[],custom[integer],max[2],min[-1]]"
								style="width:30px;">次 --%> <eve:eveselect
									clazz="eve-input validate[required]" dataParams="CKCS"
									value="${serviceItem.CKCS_2}"
									dataInterface="dictionaryService.findDatasForSelect"
									name="CKCS_2">
							</eve:eveselect>
							</td>
							<td style="text-align: center;"><input type="checkbox"
																   class="checkbox" name="SQZHYQ_2" value="1">普通注册账号&nbsp;&nbsp;
								<input type="checkbox" class="checkbox" name="SQZHYQ_2"
									   value="2">实名认证账号&nbsp;&nbsp; <input
										type="checkbox" class="checkbox" name="SQZHYQ_2" value="3">CA账号
							</td>
							<td style="text-align: center;"><input type="text"
																   name="CNSM_2" value="${serviceItem.CNSM_2}" class="eve-input"
																   maxlength="16" style="width:90%;"></td>
						</tr>
						<tr id="fourstar">
							<td><input type="checkbox" class="checkbox"
							<%--								onclick="alert('该选项无法取消！');return false;" --%>
									   name="SQFS"
									   id="SQFS3" onclick="smallChange3(this)"  
									   value="3">【四星】网上申请、预审和受理，窗口纸质核验办结
							</td>
							<td style="text-align: center;">
								<%-- <input type="text"
								name="CKCS_3" value="${serviceItem.CKCS_3}"
								class="eve-input validate[[],custom[integer],max[1],min[0]]"
								style="width:30px;">次 --%> <eve:eveselect
									clazz="eve-input validate[]" dataParams="CKCS"
									value="${serviceItem.CKCS_3}"
									dataInterface="dictionaryService.findDatasForSelect"
									name="CKCS_3">
							</eve:eveselect>
							</td>
							<td style="text-align: center;"><input type="checkbox"
																   class="checkbox" name="SQZHYQ_3" value="1">普通注册账号&nbsp;&nbsp;
								<input type="checkbox" class="checkbox" name="SQZHYQ_3"
									   value="2">实名认证账号&nbsp;&nbsp; <input
										type="checkbox" class="checkbox" name="SQZHYQ_3" value="3">CA账号
							</td>
							<td style="text-align: center;"><input type="text"
																   name="CNSM_3" value="${serviceItem.CNSM_3}" class="eve-input"
																   maxlength="16" style="width:90%;"></td>
						</tr>
						<tr id="fivestar">
							<td><input type="checkbox" class="checkbox" name="SQFS"
									   id="SQFS4" onclick="smallChange4(this)"  
									   value="4">【五星】提供全流程网上办理，申请人在办结后到窗口领取结果
							</td>
							<td style="text-align: center;">
								<%-- <input type="text"
								name="CKCS_4" value="${serviceItem.CKCS_4}"
								class="eve-input validate[[],custom[integer],max[1],min[0]]"
								style="width:30px;">次 --%> <eve:eveselect
									clazz="eve-input validate[]" dataParams="CKCS"
									value="${serviceItem.CKCS_4}"
									dataInterface="dictionaryService.findDatasForSelect"
									name="CKCS_4">
							</eve:eveselect>
							</td>
							<td style="text-align: center;"><input type="checkbox"
																   class="checkbox" name="SQZHYQ_4" value="1">普通注册账号&nbsp;&nbsp;
								<input type="checkbox" class="checkbox" name="SQZHYQ_4"
									   value="2">实名认证账号&nbsp;&nbsp; <input
										type="checkbox" class="checkbox" name="SQZHYQ_4" value="3">CA账号
							</td>
							<td style="text-align: center;"><input type="text"
																   name="CNSM_4" value="${serviceItem.CNSM_4}" class="eve-input"
																   maxlength="16" style="width:90%;"></td>
						</tr>
					</table>
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>其他</a></div></td>
						</tr>

						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">是否开通预约服务：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												defaultvalue="0" value="${serviceItem.IS_APPOINT}"
												fieldname="IS_APPOINT">
								</eve:radiogroup>
								<span style="width: 160px;float:left;text-align:right;">是否支持自助终端办理：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												defaultvalue="0" value="${serviceItem.SFZCZZZDBL}"
												fieldname="SFZCZZZDBL">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">
									<font class="dddl_platform_html_requiredFlag">*</font>是否通办（就近办）：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												value="${serviceItem.SFTBJJB}"
												fieldname="SFTBJJB">
								</eve:radiogroup>
								<span style="width: 160px;float:left;text-align:right;">高频事项主题配置：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="256" class="eve-input"
									   value="${serviceItem.GPSXZTPZ}" name="GPSXZTPZ" />
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">
									<font class="dddl_platform_html_requiredFlag">*</font>是否异地代收：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												defaultvalue="0" value="${serviceItem.SFYDDS}"
												fieldname="SFYDDS">
								</eve:radiogroup>
								<span style="width: 160px;float:left;text-align:right;">跨省通办高频事项清单：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="256" class="eve-input"
									   value="${serviceItem.KSTBGPSXQD}" name="KSTBGPSXQD" />
							</td>
						</tr>
					</table>
					<%--					<p class="info">--%>
					<%--						一星：仅在省网上办事大厅发布办事指南信息，不提供网上服务的事项,无法在线申请，选择该星级必须向区行政服务中心提交书面申请。<br />--%>
					<%--						二星：在省网上办事大厅发布办事指南信息，并提供其他网站在线申请链接的事项，选择该星级必须向区行政服务中心提交书面申请。<br />--%>
					<%--						三星：在省网上办事大厅发布办事指南信息，提供在线申请、网上预审后，在窗口进行纸质收件受理，并且最多到现场次数不超过2次。（申请人提交纸质材料的环节为受理时）<br />--%>
					<%--						四星：在省网上办事大厅发布办事指南信息，提供在线申请、网上预审、网上受理后，在窗口进行纸质核验办结的事项，并且最多到现场次数不超过1次。（申请人提交纸质材料的环节为领取结果时）<br />--%>
					<%--						五星：在省网上办事大厅发布办事指南信息，提供全流程网上办理（在线申请、网上预审、网上受理、网上办结）的事项，申请人不再提交纸质申请材料，并且最多只需到现场领取结果1次。（申请人不必提交纸质材料）<br />--%>
					<%--					</p>--%>
				</form>
<%--				<form action="departServiceItemController.do?saveOrUpdate"--%>
<%--					id="StepForm_2">--%>
<%--					<table style="width:100%;border-collapse:collapse;"--%>
<%--						class="dddl-contentBorder dddl_table">--%>
<%--						<tr>--%>
<%--							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>依据条件</a></div></td>--%>
<%--						</tr>--%>


<%--						&lt;%&ndash; <tr>--%>
<%--							<td><span style="width: 120px;float:left;text-align:right;">办理流程：</span>--%>
<%--								<textarea rows="5" cols="5" class="eve-textarea"--%>
<%--									style="width: 600px;height:180px;" name="BLLC" id='bllc'>${serviceItem.BLLC}</textarea><font--%>
<%--								class="dddl_platform_html_requiredFlag">*</font></td>--%>
<%--						</tr> &ndash;%&gt;--%>
<%--					</table>--%>
<%--				</form>--%>
			</div>
			<div title="时限配置" style="display: none;" id="StepDiv_3">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_3">
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>时限配置</a></div></td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">承诺时限：</span>
								<input type="text" style="width:150px;float:left;" maxlength="3"
									   class="eve-input validate[required,custom[integer],min[0]]"
										<c:if test="${serviceItem.SXLX==1}"> readonly="readonly" value="0" </c:if>
									   value="${serviceItem.CNQXGZR}" name="CNQXGZR" />
								<eve:radiogroup typecode="FDSXLX" width="150px" defaultvalue="gzr"
												value="${serviceItem.CNSXLX}" fieldname="CNSXLX">
								</eve:radiogroup><font class="dddl_platform_html_requiredFlag">*(若即办件类型，承诺时限为0个工作日。)</font></td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">承诺时限说明：</span>
								<textarea rows="3" cols="5"
										  class="eve-textarea validate[maxSize[500]]"
										  style="width: 400px" name="CNQXSM">${serviceItem.CNQXSM}</textarea></td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">法定时限：</span>
								<input type="text" style="width:150px;float:left;" maxlength="3"
									   class="eve-input validate[required,custom[integer],min[-1]]"
									   value="${serviceItem.FDSXGZR}"
									   name="FDSXGZR" />
								<eve:radiogroup typecode="FDSXLX" width="150px" defaultvalue="gzr"
												value="${serviceItem.FDSXLX}" fieldname="FDSXLX">
								</eve:radiogroup>
								<font class="dddl_platform_html_requiredFlag">*(-1代表无法定时限)</font>
							</td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">法定时限说明：</span>
								<textarea rows="3" cols="5"
										  class="eve-textarea validate[maxSize[500]]"
										  style="width: 400px" name="FDQX">${serviceItem.FDQX}</textarea></td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">外网预审时限：</span>
								<input type="text" style="width:150px;float:left;" maxlength="3"
									   class="eve-input validate[[required],custom[integer],min[0]]" value="${serviceItem.PREDAY}" name="PREDAY" />工作日
								<font
										class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr style="display: none;">
							<td><span style="width: 120px;float:left;text-align:right;">收件时限：</span>
								<input type="text" style="width:150px;float:left;" maxlength="3"
									   class="eve-input validate[custom[integer],min[0]]"
									   readonly="readonly" value="2" name="RECEIVEDAY" />工作日</td>
						</tr>
					</table>
				</form>
				<div id="tshjToolbar">
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#" class="easyui-linkbutton" iconCls="eicon-plus"
									   plain="true" onclick="showTshjWindow();">新增特殊环节</a> <a
										href="#" class="easyui-linkbutton" iconCls="eicon-pencil"
										plain="true" onclick="editTshjGridRecord();">编辑特殊环节</a><a
										href="#" class="easyui-linkbutton" iconCls="eicon-trash-o"
										plain="true" onclick="deleteTshjGridRecord();">删除特殊环节</a>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div style="height:250px;">
					<table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="5" pageList="[5,10,15]"
						   id="tshjGrid" fitColumns="true" toolbar="#tshjToolbar" nowrap="false" striped="true"
						   method="post" idField="RECORD_ID" checkOnSelect="true" fit="true"
						   selectOnCheck="false" border="false"
					>
						<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
							<th data-options="field:'LINK_NAME',align:'left'" width="7%">特殊环节名称</th>
							<th data-options="field:'LINK_LIMITTIME',align:'left'"
								width="7%" formatter="dayformatter">特殊环节时限</th>
							<th data-options="field:'OPERATOR_NAME',align:'left'" width="7%">经办人</th>
							<th data-options="field:'LINK_BASIS',align:'left'" width="14%">特殊环节设定依据</th>
							<th data-options="field:'SUIT_SITUATION',align:'left'" width="14%">适用情形</th>
							<th data-options="field:'REVIEW_CONTENT',align:'left'" width="14%">（法定）审查内容</th>
							<th data-options="field:'REVIEW_STANDARD',align:'left'" width="14%">审查标准</th>
							<th data-options="field:'RESPONSIBILITY',align:'left'" width="13%">办理责任</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
			<div title="办公指引" style="display: none;height: 450px;" id="StepDiv_4">
				<form action="departServiceItemController.do?saveOrUpdate" id="StepForm_4">
					<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>办公指引</a></div></td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">办理窗口：</span> <input
									type="text" style="width:400px;float:left;" maxlength="62"
									class="eve-input validate[required]"
									value="${serviceItem.SSZTMC}" name="SSZTMC" />
								<font class="dddl_platform_html_requiredFlag">备注：已在中心窗口受理的事项，若按中心统一时间考勤的无需修改。 </font></td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">申请条件：</span>
								<textarea rows="5" cols="5"
										  class="eve-textarea validate[required,maxSize[4000]]"
										  style="width: 600px;height:180px;" name="SQTJ">${serviceItem.SQTJ}</textarea><font
										class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">办理依据：</span>
								<textarea rows="5" cols="5"
										  class="eve-textarea validate[required,maxSize[4000]]"
										  style="width: 600px;height:180px;" name="XSYJ">${serviceItem.XSYJ}</textarea>
								<font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">受理地址：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[required,maxSize[256]]"
									style="width: 400px" name="BLDD">${serviceItem.BLDD}</textarea>
								<font class="dddl_platform_html_requiredFlag">*
									备注：已入驻中心窗口受理的事项，无需修改，若不是，请填写实际受理地点</font>
							</td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">交通指引：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[required,maxSize[250]]"
									style="width: 400px" name="TRAFFIC_GUIDE">${serviceItem.TRAFFIC_GUIDE}</textarea>
								<font class="dddl_platform_html_requiredFlag">*
									备注：已入驻中心内窗口受理的事项，无需修改，若不是，请填写实际公交路线</font>
							</td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">办公时间：</span>
								<textarea rows="3" cols="5"
										  class="eve-textarea validate[required,maxSize[500]]"
										  style="width: 400px" name="BGSJ">${serviceItem.BGSJ}</textarea>
								<font class="dddl_platform_html_requiredFlag">*备注：已在中心窗口受理的事项，若按中心统一时间考勤的无需修改。 </font>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">联系人：</span> <input
									type="text" style="width:150px;float:left;" maxlength="8"
									class="eve-input" style="width: 400px"
									value="${serviceItem.SYNC_LXR}" name="SYNC_LXR" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><span
									style="width: 150px;float:left;text-align:right;">咨询电话：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[required,maxSize[120]]"
									style="width: 400px" name="LXDH">${serviceItem.LXDH}</textarea>
								<font class="dddl_platform_html_requiredFlag">*
									备注：座机请加区号，参考格式0591-88888888</font></td>
							<td colspan="2"><span
									style="width: 150px;float:left;text-align:right;">投诉电话：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[required,maxSize[120]]"
									style="width: 400px" name="JDDH">${serviceItem.JDDH}</textarea>
								<font class="dddl_platform_html_requiredFlag">*
									备注:不可与联系电话一致。座机请加区号，参考格式0591-88888888</font>
							</td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">监督部门：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="62" class="eve-input" value="${serviceItem.JDBM}"
									   name="JDBM" /></td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">窗口描述：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[[],maxSize[126]]"
									style="width: 400px" name="CKMS">${serviceItem.CKMS}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">地图坐标：</span> <input
									type="text" style="width:400px;float:left;" maxlength="64"
									class="eve-input" value="${serviceItem.COORDINATES}"
									name="COORDINATES" /> <font
									class="dddl_platform_html_requiredFlag">(如：49.6549750000,132.0468510000)</font>
							</td>
						</tr>
						<tr>
							<td colspan="4"><span
									style="width: 150px;float:left;text-align:right;">备注信息：</span> <textarea
									rows="3" cols="5"
									class="eve-textarea validate[[],maxSize[126]]"
									style="width: 400px" name="ZY_REMARK">${serviceItem.ZY_REMARK}</textarea>
							</td>
						</tr>

					</table>
				</form>
			</div>
			<div title="申请材料" style="display:none;height: 100%;" id="StepDiv_5">
				<div id="ApplyMaterToolbar">
					<input type="hidden" name="Q_SM.ITEM_ID_EQ"
						   value="${serviceItem.ITEM_ID}">
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#" class="easyui-linkbutton" iconCls="eicon-check" plain="true" onclick="updateDicSn();">保存排序</a>
									<a href="#" class="easyui-linkbutton" iconCls="eicon-remove" plain="true" onclick="deleteBindApplyMater();">移除材料</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef" iconCls="eicon-plus" plain="true" onclick="showApplyMaterWindow();">新建材料</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef" iconCls="eicon-pencil" plain="true" onclick="editApplyMaterGridRecord();">编辑材料</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef" iconCls="eicon-lock" plain="true" onclick="isneedApplyMaterGridRecord();">设置必须提供</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef" iconCls="eicon-unlock" plain="true" onclick="noneedApplyMaterGridRecord();">设置非必须提供</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div style="height:100%;">
					<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
						   id="ApplyMaterGrid" fitColumns="true"
						   toolbar="#ApplyMaterToolbar" method="post" idField="MATER_ID"
						   checkOnSelect="true" fit="true" nowrap="false"
						   selectOnCheck="true" border="false"
						   data-options="onLoadSuccess:function(){
								 $(this).datagrid('enableDnd');
							}"
						   url="applyMaterController.do?forItemDatas&itemId=${serviceItem.ITEM_ID}">
						<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'MATER_ID',hidden:true">MATER_ID</th>
							<th data-options="field:'MATER_SN',align:'left',hidden:true" width="3%" >排序值</th>
							<th data-options="field:'MATER_CODE',align:'left'" width="11%">材料编码</th>
							<th data-options="field:'MATER_NAME',align:'left'" width="20%">材料名称</th>
							<th data-options="field:'BUSCLASS_NAME',align:'left'"
								width="10%" formatter="formatBusclassName">事项子项</th>
							<th data-options="field:'MATER_ISNEED',align:'left'" width="4%"
								formatter="formatIsNeed">必须提供</th>
							<th data-options="field:'MATER_DESC',align:'left'" width="10%">材料说明</th>
							<!-- <th data-options="field:'MATER_CLSM',align:'left'" width="80">材料份数</th> -->
							<th data-options="field:'MATER_CLSMLX',align:'left'" width="7%">材料类型</th>
							<th data-options="field:'MATER_XZ',align:'left'" width="4%"
								formatter="formatclfl">材料分类</th>
							<th data-options="field:'MATER_SOURCE',align:'left'" width="10%"
								formatter="formatMaterSource">材料来源/出具单位</th>

							<!-- <th data-options="field:'RAW_NUM',align:'left'" width="80" >原件份数</th>
						<th data-options="field:'GATHERORVER',align:'left'" width="80" >收取或者核验</th>
						<th data-options="field:'CLMB',align:'left'" width="120">材料模板</th>
						<th data-options="field:'CLYB',align:'left'" width="120">材料样板</th> -->
							<th data-options="field:'PAGE_NUM',align:'left'" width="4%">原件份数</th>
							<th data-options="field:'PAGECOPY_NUM',align:'left'" width="5%">复印件份数</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
			<div title="邮递服务" style="display: none;height:100%;" id="StepDiv_6">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_6">
					<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>纸质材料提交</a></div></td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;"><font class="tab_color">*</font>是否支持物流快递：</span>
								<eve:radiogroup typecode="YesOrNo" width="140px"
												value="${serviceItem.EXPRESS_SUPPORT}"
												fieldname="EXPRESS_SUPPORT">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;">纸质材料提交方式：</span>
								<input type="checkbox" class="checkbox" name="PAPERSUB"
									   value="1" checked="checked" disabled="disabled">窗口收取&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" class="checkbox" name="PAPERSUB"
									   value="2">邮递收取</td>
						</tr>
						<tr id="addressee" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;"><font
									class="tab_color">*</font>收件人：</span> <input type="text"
																				 style="width:200px;float:left;"
																				 class="eve-input validate[required]" maxlength="30"
																				 value="${serviceItem.ITEM_SEND_ADDRESSEE}"
																				 name="ITEM_SEND_ADDRESSEE" /></td>
						</tr>
						<tr id="addrmobile" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;"> <font
									class="tab_color">*</font>移动电话：</span>
								<input type="text"
									   style="width:200px;float:left;"
									   class="eve-input validate[[require],custom[mobilePhoneLoose]]"
									   maxlength="11" value="${serviceItem.ITEM_SEND_MOBILE}"
									   name="ITEM_SEND_MOBILE" /></td>
						</tr>
						<tr id="addrprov" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;"><font
									class="tab_color">*</font>所属省市：</span>
								<input name="ITEM_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
									   data-options="
						                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
						                method:'post',
						                valueField:'TYPE_NAME',
						                textField:'TYPE_NAME',
						                panelHeight:'400',
						                required:true,
						                editable:false,
						                onSelect:function(rec){
										   $('#city').combobox('clear');
										   if(rec.TYPE_CODE){
										       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
										       $('#city').combobox('reload',url);
										   }
										}
				                " value="${serviceItem.ITEM_SEND_PROV}" />
								<input name="ITEM_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;"
									   data-options="
						                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
						                method:'post',
						                valueField:'TYPE_NAME',
						                textField:'TYPE_NAME',
						                panelHeight:'400',
						                required:true,
						                editable:false,
						                onSelect:function(rec){
										   $('#county').combobox('clear');
										   if(rec.TYPE_CODE){
										       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
										       $('#county').combobox('reload',url);
										   }
										}
				                " value="${serviceItem.ITEM_SEND_CITY}" />
							</td>
							<td />
						</tr>
						<tr id="addr" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;"><font
									class="tab_color">*</font>详细地址：</span> <input type="text"
																				  style="width:400px;float:left;"
																				  class="eve-input  validate[required]" maxlength="62"
																				  value="${serviceItem.ITEM_SEND_ADDR}" name="ITEM_SEND_ADDR" /></td>
						</tr>
						<tr id="addrpostcode" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;"><font
									class="tab_color">*</font>邮政编码：</span>
								<input type="text" style="width:200px;float:left;" class="eve-input validate[[required],custom[chinaZip]]"
									   maxlength="6" value="${serviceItem.ITEM_SEND_POSTCODE}"
									   name="ITEM_SEND_POSTCODE" /></td>
						</tr>
<%--						<tr id="yjbsfsf" style="display: none;">--%>
<%--							<td><span style="width: 150px;float:left;text-align:right;"><font--%>
<%--									class="tab_color">*</font>邮寄办是否收费：</span>--%>
<%--								<eve:radiogroup typecode="yjbsfsf" width="140px"--%>
<%--												value="${serviceItem.YJBSFSF}"--%>
<%--												fieldname="YJBSFSF">--%>
<%--								</eve:radiogroup>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="mfydblx" style="display: none;">--%>
<%--							<td><span style="width: 150px;float:left;text-align:right;"><font--%>
<%--									class="tab_color">*</font>免费邮递办类型：</span>--%>
<%--								<eve:excheckbox--%>
<%--										dataInterface="dictionaryService.findDatasForSelect"--%>
<%--										name="MFYDBLX" width="550px;" clazz="checkbox "--%>
<%--										dataParams="mfydblx" value="${serviceItem.MFYDBLX}">--%>
<%--								</eve:excheckbox>--%>
<%--							</td>--%>
<%--						</tr>--%>
						<tr id="addrremarks" style="display: none;">
							<td><span style="width: 150px;float:left;text-align:right;">备注：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="128" class="eve-input"
									   value="${serviceItem.ITEM_SEND_REMARKS}"
									   name="ITEM_SEND_REMARKS" /></td>
						</tr>
						<tr>
							<td><span style="width: 150px;float:left;text-align:right;"><font
									class="dddl_platform_html_requiredFlag">*</font>结果获取方式：</span> <eve:excheckbox
									dataInterface="dictionaryService.findDatasForSelect"
									name="FINISH_GETTYPE" width="550px;"
									clazz="checkbox validate[required]" dataParams="FinishGetType"
									value="${serviceItem.FINISH_GETTYPE}">
							</eve:excheckbox></td>
						</tr>
					</table>
				</form>

			</div>
			<div title="清单配置" style="display: none;height: 100%;" id="StepDiv_7">
				<form action="departServiceItemController.do?saveOrUpdate" id="StepForm_7">
					<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>是否免政府材料：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												value="${serviceItem.SFMZFCL}" defaultvalue="0"
												fieldname="SFMZFCL">
								</eve:radiogroup>
							</td>
							<td>
								<span style="width: 150px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>是否网上即审：</span>
								<eve:radiogroup typecode="YesOrNo" width="550"
												value="${serviceItem.SFWSJS}"
												fieldname="SFWSJS" defaultvalue="0">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否容缺受理：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												value="${serviceItem.SFRQSL}"
												fieldname="SFRQSL" defaultvalue="0">
								</eve:radiogroup>
							</td>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否智能秒办：</span>
								<eve:radiogroup typecode="YesOrNo" width="550"
												value="${serviceItem.SFZNMB}"
												fieldname="SFZNMB" defaultvalue="0">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">邮寄办是否收费：</span>
								<eve:radiogroup typecode="yjbsfsf" width="550"
												value="${serviceItem.YJBSFSF}"
												fieldname="YJBSFSF" onclick="changeYjbsfsf(this.value)">
								</eve:radiogroup>
							</td>
							<td style="display: none;" id="mfydblx">
								<span style="width: 150px;float:left;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>免费邮递办类型：</span>
								<eve:excheckbox
										dataInterface="dictionaryService.findDatasForSelect"
										name="MFYDBLX" width="550" clazz="checkbox validate[required]"
										dataParams="mfydblx" value="${serviceItem.MFYDBLX}">
								</eve:excheckbox>
							</td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否提供代办服务：</span>
								<eve:radiogroup typecode="YesOrNo" width="140"
												value="${serviceItem.SFTGDBFW}" defaultvalue="0"
												fieldname="SFTGDBFW" onclick="changeSftgdbfw(this.value)">
								</eve:radiogroup>
							</td>
							<td id="dbsfsf">
								<span style="width: 150px;float:left;text-align:right;">代办是否收费：</span>
								<eve:radiogroup typecode="dbsfsf" width="140"
												value="${serviceItem.DBSFSF}"
												fieldname="DBSFSF" defaultvalue="0">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td>

							</td>
						</tr>
					</table>
				</form>
			</div>
			<div title="办理流程" style="display: none;height: 100%;" id="StepDiv_8">
				<input type="hidden" name="BDLCDYID" value="${serviceItem.BDLCDYID}">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_8">
					<table style="width:100%;border-collapse:collapse;font-size:14px;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td width="15%" style="text-align: center;"><font
									class="dddl_platform_html_requiredFlag">*</font>流程配置：</td>
							<td width="25%" id="defname"></td>
							<td width="60%"><input value="请选择流程定义" type="button"
												   class="eve_inpbtn" onclick="selectFlowDef()" />(数字代表环节，起始“1”代表“受理”环节，末尾的“0”代表“办结”环节)</td>
						</tr>
						<tr>
							<td style="text-align: center;"><font
									class="dddl_platform_html_requiredFlag">*</font>是否需要挂起：</td>
							<%--	                    	<c:if test="${list2.isOverTime==1 && list2.bespeakNumber<list2.ALLOW_BESPEAK_NUMBER}">--%>
							<td colspan="2"><eve:eveselect
									clazz="eve-input validate[required]" dataParams="YesOrNo"
									value="${serviceItem.SFZCGQ}"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择是否挂起" name="SFZCGQ" id="SFZCGQ">
							</eve:eveselect> <font class="dddl_platform_html_requiredFlag">*(如果存在现场勘查、公示等不包含在承诺时限范围的，需要挂起并到“4-时限配置”里配置"特殊环节"。)</font>
							</td>
						</tr>
					</table>
				</form>
				<div id="defToolbar">
					<input type="hidden" name="Q_T.ITEM_ID_EQ"
						   value="${serviceItem.ITEM_ID}">
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#" class="easyui-linkbutton" iconCls="eicon-pencil"
									   plain="true" onclick="editNodeAuditer();">编辑</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div style="height:370px;">
					<table class="easyui-datagrid" rownumbers="true" pagination="false" nowrap="false"
						   id="defGrid" fitcolumns="true" toolbar="#defToolbar" method="post"
						   idfield="RECORD_ID" checkonselect="true" selectoncheck="true" striped="true"
						   fit="true" border="false"
						   url="departServiceItemController.do?defNodeData&itemId=${serviceItem.ITEM_ID}">
						<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
							<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
							<th data-options="field:'DEF_ID',hidden:true">DEF_ID</th>
							<th data-options="field:'NODE_NAME',align:'left'" width="6%">当前环节</th>
							<th data-options="field:'USER_ACCOUNT',hidden:true">USER_ACCOUNT</th>
							<th data-options="field:'USER_NAME',align:'left'" width="10%">办理人员</th>
							<th data-options="field:'TIME_LIMIT',align:'left'"
								formatter="blsxformater" width="6%">审查时限</th>
							<th data-options="field:'TIME_TYPE',hidden:true">TIME_TYPE</th>
							<th data-options="field:'REVIEW_CONTENT',align:'left'" width="17%">法定审查内容</th>
							<th data-options="field:'REVIEW_STANDARD',align:'left'" width="18%">审查标准</th>
							<!-- 							<th data-options="field:'EXAMINANT',align:'left'" width="200">审查程序及审查人</th> -->
							<th data-options="field:'HANDLE_OPINIONS',align:'left'" width="17%">审查意见（结果）</th>
							<th data-options="field:'RESPONSIBILITY',align:'left'" width="17%">审查责任</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>

			<div title="收费配置" style="display: none;" id="StepDiv_9">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_9">
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>收费信息</a></div></td>
						</tr>
						<tr>
							<td><span style="width: 120px;float:left;text-align:right;">是否收费：</span>
								<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="0"
												value="${serviceItem.SFSF}" fieldname="SFSF" onclick="changeSfsf(this.value)">
								</eve:radiogroup> <font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr id="SFFS_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费方式：</span>
								<eve:excheckbox
										dataInterface="dictionaryService.findDatasForSelect"
										name="SFFS" width="250px;" clazz="checkbox validate[required]"
										dataParams="ChargeType" value="${serviceItem.SFFS}">
								</eve:excheckbox>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr id="SFFSSM_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费方式说明：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="64" class="eve-input" value="${serviceItem.SFFSSM}"
									   name="SFFSSM" /></td>
						</tr>
						<tr id="SFXMMC_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费项目名称：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="64" class="eve-input validate[required]" value="${serviceItem.SFXMMC}"
									   name="SFXMMC" /><font class="dddl_platform_html_requiredFlag">*</font></td>
						</tr>
						<tr id="SFBZJYJ_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费标准及依据：</span>
								<textarea style="width: 400px;height: 120px;"
										  class="eve-textarea validate[]" name="SFBZJYJ">${serviceItem.SFBZJYJ}</textarea>
							</td>
						</tr>
						<tr id="SFBZJYJ_TR1" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费标准：</span>
								<textarea style="width: 400px;height: 120px;"
										  class="eve-textarea validate[required]" name="CHARGESTANDARD">${serviceItem.CHARGESTANDARD}</textarea>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr id="SFBZJYJ_TR2" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">收费依据：</span>
								<textarea style="width: 400px;height: 120px;"
										  class="eve-textarea validate[required]" name="CHARGEACCORD">${serviceItem.CHARGEACCORD}</textarea>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr id="SFYXJM_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">是否允许减免：</span>
								<eve:radiogroup typecode="YesOrNo" width="130px" defaultvalue="0"
												value="${serviceItem.CHARGEDERATEIF}" fieldname="CHARGEDERATEIF" >
								</eve:radiogroup> </td>
						</tr>
						<tr id="JMYJ_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">允许减免依据：</span>
								<textarea style="width: 400px;height: 120px;"
										  class="eve-textarea validate[]" name="CHARGEDERATEACCORD">${serviceItem.CHARGEDERATEACCORD}</textarea>
							</td>
						</tr>

						<tr id="SFZFQD_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">支付渠道：</span>
								<eve:radiogroup typecode="payWay" width="130px"
												value="${serviceItem.PAY_WAY}" fieldname="PAY_WAY">
								</eve:radiogroup></td>
						</tr>
						<tr id="CHARGECFGNAME_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">支付渠道对应的站点名称：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="64" class="eve-input" value="${serviceItem.CHARGECFGNAME}"
									   name="CHARGECFGNAME" /></td>
						</tr>
						<tr id="CHARGECFGCODE_TR" class="sfsfC">
							<td><span style="width: 120px;float:left;text-align:right;">支付渠道对应的站点代码：</span>
								<input type="text" style="width:400px;float:left;"
									   maxlength="64" class="eve-input" value="${serviceItem.CHARGECFGCODE}"
									   name="CHARGECFGCODE" /></td>
						</tr>
					</table>
				</form>
				<div id="sfmxToolbar">
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#" class="easyui-linkbutton" iconCls="eicon-plus"
									   plain="true" onclick="showSfmxWindow();">新增明细</a> <a href="#"
																							class="easyui-linkbutton" iconCls="eicon-pencil"
																							plain="true" onclick="editSfmxGridRecord();">编辑明细</a><a
										href="#" class="easyui-linkbutton" iconCls="eicon-trash-o"
										plain="true" onclick="deleteSfmxGridRecord();">删除明细</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div style="height:250px;" id="sfmxtable">
					<table class="easyui-datagrid" rownumbers="true" pagination="true" pageSize="5" pageList="[5,10,15]"
						   id="sfmxGrid" fitColumns="true" toolbar="#sfmxToolbar"
						   method="post" idField="RECORD_ID" checkOnSelect="true" fit="true" striped="true"
						   selectOnCheck="false" border="false"
						   url="departServiceItemController.do?chargeData&itemId=${serviceItem.ITEM_ID}">
						<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th data-options="field:'RECORD_ID',hidden:true" width="200">明细ID</th>
							<th data-options="field:'CHARGEDETAILORGNAME',align:'center'" width="200">执行单位名称</th>
							<th data-options="field:'CHARGEDETAILNAME',align:'center'" width="200">收费项目名称</th>
							<th data-options="field:'CHARGEDETAILCODE',align:'center'" width="200">收费项目代码</th>
							<th data-options="field:'UNITS',align:'center'" width="200">计量单位</th>
							<th data-options="field:'CHARGEDETAILSTANDARD',align:'center'" width="200">执收标准</th>
							<th data-options="field:'CHARGEDETAILAMOUNT',align:'center'" width="200">执收数量</th>
							<th data-options="field:'CHARGEDETAILORDERID',align:'center'" width="200">排序号</th>
							<th data-options="field:'CHARGEDETAILSTATUS',align:'center'" formatter="statusFormatter" width="200">状态</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>

			<div title="预审人员" style="display: none;" id="StepDiv_10">
				<form action="departServiceItemController.do?saveOrUpdate"
					id="StepForm_10">
					<input type="hidden" name="PREAUDITER_IDS"
						   value="${serviceItem.PREAUDITER_IDS}">
					<input type="hidden" name="MESSAGE_REC" value="${serviceItem.MESSAGE_REC}">
					<table style="width:100%;border-collapse:collapse;font-size:14px;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td style="text-align: center;" width="20%"><font
									class="dddl_platform_html_requiredFlag">*</font>预审（含短信）接收人员：</td>
							<td id="auditusernames" width="60%"></td>
							<td width="20%"><input value="请选择人员" type="button"
												   class="eve_inpbtn" onclick="selectAuditUser();" /></td>
						</tr>
					</table>
<%--					<form action="departServiceItemController.do?saveOrUpdate"--%>
<%--						  id="StepForm_9">--%>
<%--						<table--%>
<%--								style="width:100%;border-collapse:collapse;font-size:14px;display:none;"--%>
<%--								class="dddl-contentBorder dddl_table">--%>
<%--							<tr>--%>
<%--								<td style="text-align: center;" width="15%"><font--%>
<%--										class="dddl_platform_html_requiredFlag">*</font>短信接收人员：</td>--%>
<%--								<td width="85%"><input type="text"--%>
<%--													   style="width:600px;float:left;" maxlength="128"--%>
<%--													   class="eve-input validate[required]"--%>
<%--													   value="${serviceItem.MESSAGE_REC}" name="MESSAGE_REC" />格式：张三(13xxxxxxxxx),李四(13xxxxxxxxx)--%>
<%--								</td>--%>
<%--							</tr>--%>
<%--						</table>--%>
<%--					</form>--%>

				</form>
			</div>
			<div title="常见问题" style="display: none;height: 100%;" id="StepDiv_11">
				<div id="CommonProblemToolbar">
					<div class="right-con">
						<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
							<div class="e-toolbar-ct">
								<div class="e-toolbar-overflow">
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef"
									   iconCls="eicon-plus" plain="true"
									   onclick="showCommonProblemWindow('');">新建问题</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef"
									   iconCls="eicon-pencil" plain="true"
									   onclick="editCommonProblemGridRecord();">编辑问题</a>
									<a href="#" class="easyui-linkbutton" resKey="EDIT_FlowDef"
									   iconCls="eicon-trash-o" plain="true"
									   onclick="deleteCommonProblemGridRecord();">删除问题</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<table class="easyui-datagrid" rownumbers="true" pagination="true"
					   id="CommonProblemGrid" fitcolumns="true"
					   toolbar="#CommonProblemToolbar" method="post" idfield="PROBLEM_ID"
					   checkonselect="false" selectoncheck="false" fit="true"
					   border="false">
					<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'PROBLEM_ID',hidden:true" width="80">PROBLEM_ID</th>
						<th data-options="field:'PROBLEM_TITLE',align:'left'" width="300">问题标题</th>
						<th data-options="field:'LASTER_UPDATETIME',align:'left'"
							width="100">最后更新时间</th>
					</tr>
					</thead>
				</table>
<%--				<div class="easyui-layout" fit="true" id="layout_11">--%>
<%--					<div data-options="region:'west',split:false" style="width:250px;">--%>
<%--						<div class="right-con">--%>
<%--							<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">--%>
<%--								<div class="e-toolbar-ct">--%>
<%--									<div class="e-toolbar-overflow">--%>
<%--										<div style="color:#005588;">--%>
<%--											<img src="plug-in/easyui-1.4/themes/icons/script.png"--%>
<%--												style="position: relative; top:7px; float:left;" />&nbsp;业务办理分类--%>
<%--										</div>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>
<%--						</div>--%>
<%--						<ul id="handleTypeTree" class="ztree"></ul>--%>
<%--					</div>--%>
<%--					<div data-options="region:'center',split:false">--%>
<%--						<div id="handleTypeToolbar">--%>
<%--							<!--====================开始编写隐藏域============== -->--%>
<%--							<input type="hidden" name="Q_t.BUS_HANDLE_TYPE_=" />--%>
<%--							<!--====================结束编写隐藏域============== -->--%>
<%--							<div class="right-con">--%>
<%--								<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">--%>
<%--									<div class="e-toolbar-ct">--%>
<%--										<div class="e-toolbar-overflow">--%>
<%--											<a href="#" class="easyui-linkbutton"--%>
<%--												resKey="ADD_selfExam" iconCls="eicon-plus"--%>
<%--												plain="true" onclick="showSelfExamWindow();">新建</a> <a--%>
<%--												href="#" class="easyui-linkbutton"--%>
<%--												resKey="EDIT_selfExam" iconCls="eicon-pencil"--%>
<%--												plain="true" onclick="editSelfExamGridRecord();">编辑</a>--%>
<%--											<a href="#" class="easyui-linkbutton"--%>
<%--												resKey="DEL_selfExam" iconCls="eicon-trash-o"--%>
<%--												plain="true" onclick="deleteSelfExamGridRecord();">删除</a>--%>
<%--										</div>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>

<%--						</div>--%>

<%--						<!-- =========================表格开始==========================-->--%>
<%--						<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"--%>
<%--							id="handleTypeGrid" fitColumns="false"toolbar="#handleTypeToolbar" method="post"--%>
<%--							idField="RECORD_ID" checkOnSelect="true" selectOnCheck="true"--%>
<%--							fit="true" border="false" nowrap="false"--%>
<%--							url="departServiceItemController.do?selfExamData&itemId=${serviceItem.ITEM_ID}">--%>
<%--							<thead>--%>
<%--								<tr>--%>
<%--									<th field="ck" checkbox="true"></th>--%>

<%--									<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>--%>
<%--									<th data-options="field:'EXAM_TITLE',align:'left'" width="93%">标题</th>--%>
<%--								</tr>--%>
<%--							</thead>--%>
<%--						</table>--%>

<%--					</div>--%>
<%--				</div>--%>
			</div>
			<div title="其他配置" style="display: none;height: 100%;"
				id="StepDiv_12">
				<form action="departServiceItemController.do?saveOrUpdate"
					  id="StepForm_12">
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>工程建设项目配置</a></div></td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否为工程建设项目：</span>
								<eve:radiogroup typecode="YesOrNo" width="140px"
												value="${serviceItem.SFGCJSXM}"
												fieldname="SFGCJSXM" defaultvalue="0">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>在线投资监管配置</a></div></td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否投资类项目：</span>
								<eve:radiogroup typecode="YesOrNo" width="140px"
												value="${serviceItem.IS_INVEST}"
												fieldname="IS_INVEST" defaultvalue="0">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>事项是否需要上报国家政务服务平台配置</a></div></td>
						</tr>
						<tr>
							<td>
								<span style="width: 150px;float:left;text-align:right;">是否需要上报：</span>
								<eve:radiogroup typecode="YesOrNo" width="140px"
												value="${serviceItem.SFXYSB}"
												fieldname="SFXYSB" defaultvalue="1">
								</eve:radiogroup>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>其他</a></div></td>
						</tr>

					</table>
				</form>
			</div>

			<div title="操作日志" style="display: none;height:450px;" id="StepDiv_13" >
				<form action="serviceItemController.do?saveOrUpdate"
					  id="StepForm_13">
					<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
						<c:if test="${!empty serviceItem.uplogList}">
							<tr>
								<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>修改日志</a></div></td>
							</tr>
						</c:if>
						<c:forEach items="${serviceItem.uplogList}" var="logInfo"
								   varStatus="varStatus">
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作人员：</span>

								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作时间：</span>

								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">维护内容：</span>
								</td>
									<%--子项日志说明 --%>
								<c:choose>
									<c:when
											test="${logInfo.BUS_TABLENAME=='T_WSBS_SERVICEITEM_LINK'||logInfo.BUS_TABLENAME=='T_WSBS_APPLYMATER'}">
										<td colspan="3">${logInfo.OPERATE_CONTENT} <img
												src="<%=basePath%>/plug-in/easyui-1.4/themes/icons/eye.png"
												style='display:inline;'
												onclick="showDetailExplain('${logInfo.BUS_TABLENAME}','${logInfo.OPERATE_TIME}','${logInfo.BUS_INDEX}');">
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="3"><pre
												style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
									</c:otherwise>
								</c:choose>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend"
									style="font-weight:bold;background:#FFFFF0 ;"></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>流转日志</a></div></td>
						</tr>
						<c:forEach items="${serviceItem.fabulogList}" var="logInfo"
								   varStatus="varStatus">
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作人员：</span>

								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作时间：</span>

								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre
										style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend"
									style="font-weight:bold;background:#FFFFF0 ;"></td>
							</tr>
						</c:forEach>
						<%-- <c:forEach items="${serviceItem.canclogList}" var="logInfo"
							varStatus="varStatus">
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作人员：</span>

								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">操作时间：</span>

								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 120px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre
										style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="1" class="dddl-legend"
									style="font-weight:bold;background:#FFFFF0 ;"></td>
							</tr>
						</c:forEach> --%>
					</table>
				</form>
			</div>

<%--			<div title="数据质检" style="display: none;" id="StepDiv_14">--%>
<%--				<form action="departServiceItemController.do?saveOrUpdate"--%>
<%--					id="StepForm_14">--%>
<%--					<table style="width:100%;border-collapse:collapse;"--%>
<%--						class="dddl-contentBorder dddl_table">--%>
<%--						<tr>--%>
<%--							<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>其他配置</a></div></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">通办范围：</span>--%>
<%--								<eve:eveselect clazz="eve-input" dataParams="tbfw"--%>
<%--									value="${serviceItem.TBFW}"--%>
<%--									dataInterface="dictionaryService.findDatasForSelect"--%>
<%--									defaultEmptyText="请选择" name="TBFW">--%>
<%--								</eve:eveselect></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">办理形式：</span>--%>
<%--								<eve:excheckbox--%>
<%--									dataInterface="dictionaryService.findDatasForSelect"--%>
<%--									name="HANDLING_FORM" width="650px;" clazz="checkbox"--%>
<%--									dataParams="blxs" value="${serviceItem.HANDLING_FORM}">--%>
<%--								</eve:excheckbox></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">是否支持预约办理：</span>--%>
<%--								<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--									value="${serviceItem.IS_APPOINT}" defaultvalue="1" --%>
<%--									fieldname="APPOINTMENT_SUPPORT">--%>
<%--								</eve:radiogroup></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">是否支持网上支付：</span>--%>
<%--								<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--									value="${serviceItem.ONLINEPAY_SUPPORT}" defaultvalue="0"--%>
<%--									fieldname="ONLINEPAY_SUPPORT">--%>
<%--								</eve:radiogroup></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">是否支持物流快递：</span>--%>
<%--								<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--									value="${serviceItem.EXPRESS_SUPPORT}" defaultvalue="0"--%>
<%--									fieldname="EXPRESS_SUPPORT">--%>
<%--								</eve:radiogroup></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">是否限制申报时段：</span>--%>
<%--								<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--									value="${serviceItem.TIMELIMIT_SUPPORT}" defaultvalue="0"--%>
<%--									fieldname="TIMELIMIT_SUPPORT">--%>
<%--								</eve:radiogroup></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">运行办理系统（即业务系统）：</span>--%>
<%--								<eve:eveselect clazz="eve-input" dataParams="yxblxt"--%>
<%--									value="${serviceItem.SYSTEM_LEVEL}"--%>
<%--									dataInterface="dictionaryService.findDatasForSelect"--%>
<%--									defaultEmptyText="请选择" name="SYSTEM_LEVEL">--%>
<%--								</eve:eveselect></td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">最后同步时间：</span>--%>
<%--								${serviceItem.LAST_SYNCHRONIZE_TIME}</td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">同步对接业务数据：</span>--%>
<%--								<eve:radiogroup typecode="YesOrNo" width="130px"--%>
<%--									value="${serviceItem.IS_SYNCHRONIZE}" defaultvalue="0"--%>
<%--									fieldname="IS_SYNCHRONIZE">--%>
<%--								</eve:radiogroup>--%>
<%--							</td>--%>
<%--						</tr>					--%>
<%--						<tr id="syncperson" style="display:none;">--%>
<%--							<td>--%>
<%--								<span--%>
<%--								style="width: 180px;float:left;text-align:right;">联系人：</span> <input--%>
<%--								type="text" style="width:150px;float:left;" maxlength="8"--%>
<%--								class="eve-input validate[required]"--%>
<%--								value="${serviceItem.SYNC_LXR}" name="SYNC_LXR" />--%>
<%--								<span--%>
<%--								style="width: 120px;float:left;text-align:right;">手机号码：</span> <input--%>
<%--								type="text" style="width:150px;float:left;" maxlength="11"--%>
<%--								class="eve-input validate[required,custom[mobilePhoneLoose]]"--%>
<%--								value="${serviceItem.SYNC_MOBILE}" name="SYNC_MOBILE" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr>--%>
<%--							<td><span style="width: 180px;float:left;text-align:right;">事项备注：</span>--%>

<%--								<textarea style="width: 400px;height: 100px;"--%>
<%--									class="eve-textarea validate[[],maxSize[500]]" name="ITEM_REMARK">${serviceItem.ITEM_REMARK}</textarea>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</form>--%>
<%--			</div>--%>
<%--			<div title="海外版事项配置" style="display: none;" id="StepDiv_15">--%>

<%--			</div>--%>
		</div>

		<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons">
				<input value="上一步" type="button" id="preStepButton"
					disabled="disabled" onclick="doPre();"
					class="z-dlg-button z-dialog-cancelbutton" /><input value="下一步"
					type="button" id="nextStepNoSaveButton"
					class="z-dlg-button z-dialog-cancelbutton" onclick="toNext();" />
				<input value="保存并下一步" type="button" id="nextStepButton"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight"
					onclick="doNext();" />
				<c:if test="${shan!=null&&shan!=''}">
					<input value="审核" type="button" id="backButton"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight"
						onclick="doPassOrBack();" />
				</c:if>
			</div>
		</div>
	</div>


</body>

