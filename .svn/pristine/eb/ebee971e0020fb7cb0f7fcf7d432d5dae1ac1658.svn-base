<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2,validationegine"></eve:resources>

<style type="text/css" media=print>
.noprint{display : none }
</style>

<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript">
$(function() {
    var needCheckIds= $("input[name='BUS_TYPEIDS']").val();
    var busTypeTreeSetting = {
            check:{
                enable: true,
                chkboxType :{
                    "Y":"ps",
                    "N":"s"
                    }
            },
            edit : {
                enable : false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            view : {
                selectedMulti : false
            },
            async : {
                enable : true,
                url : "baseController.do?tree",
                otherParam : {
                    "tableName" : "T_WSBS_BUSTYPE",
                    "idAndNameCol" : "TYPE_ID,TYPE_NAME",
                    "targetCols" : "PARENT_ID,PATH,TYPE_CODE",
                    "rootParentId" : "0",
                    "needCheckIds":needCheckIds,
                    "isShowRoot" : "false",
                    "rootName" : "服务类别树"
                }
            }
        };
        $.fn.zTree.init($("#busTypeTree"), busTypeTreeSetting);
        
        
        //办件类型
        var bjlx = "${serviceItem.SXLX}";
        if(bjlx=="1"){
            $("#bjlx").html($("#bjlx").html()+"即办件");
        }else if(bjlx=="2"){
            $("#bjlx").html($("#bjlx").html()+"普通件");
        }else if(bjlx=="3"){
            $("#bjlx").html($("#bjlx").html()+"特殊件");
        }else if (bjlx == '4') {
            $("#bjlx").html($("#bjlx").html()+"联办件");
        }else if (bjlx == '5') {
             $("#bjlx").html($("#bjlx").html()+"承诺上报件");
        }
        
        //是否收费
        var sfsf = "${serviceItem.SFSF}";
        if(sfsf=="0"){
            $("#sfsf").html($("#sfsf").html()+"否");
        }else if(sfsf=="1"){
            $("#sfsf").html($("#sfsf").html()+"是");
        }
        
        
        //是否承接
        var isUndertake = "${serviceItem.IS_UNDERTAKE}";
        if(isUndertake=="0"){
            $("#isUndertake").html($("#isUndertake").html()+"否");
        }else if(isUndertake=="1"){
            $("#isUndertake").html($("#isUndertake").html()+"是");
        }
        //入驻办事大厅方式
        var rzbsdtfs = "${serviceItem.RZBSDTFS}";
        var ckcs2 = "${serviceItem.CKCS_2}";
        var ckcs3 = "${serviceItem.CKCS_3}";
        if(rzbsdtfs=="in01"){
            $("#rzbsdtfs").html($("#rzbsdtfs").html()+"指南式（一星）");
        }else if(rzbsdtfs=="in02"){
            $("#rzbsdtfs").html($("#rzbsdtfs").html()+"链接式（二星）");
        }else if(rzbsdtfs=="in04"&&(ckcs3=='-1'||ckcs3=='')){
            $("#rzbsdtfs").html($("#rzbsdtfs").html()+"收办分离式（三星）");
        }else if(rzbsdtfs=="in04"&&(ckcs2=='-1'||ckcs2=='')){
            $("#rzbsdtfs").html($("#rzbsdtfs").html()+"收办分离式（四星）");
        }else if(rzbsdtfs=="in05"){
            $("#rzbsdtfs").html($("#rzbsdtfs").html()+"全流程（五星）");
        }
        
       
});

	function PrintTable(Id) {
		var mStr;
		mStr = window.document.body.innerHTML;
		var mWindow = window;
		$("#title").attr("style","height:40px;");
		//document.getElementById(Id+"Table").style.fontSize="18px";
		/* for(var i=0;i<26;i++){
			if(document.getElementById("span"+i)){
				document.getElementById("span"+i).style.width="160px";
			}			
		} */
		//window.document.body.innerHTML = "<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>  "+document.getElementById(Id).innerHTML;
		window.document.body.innerHTML = document.getElementById(Id).innerHTML;
		HKEY_Root="HKEY_CURRENT_USER";
		HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";	
		var Wsh=new ActiveXObject("WScript.Shell");		
		HKEY_Key="header";		
		//设置页眉（为空） 根据你自己要设置的填入		
		Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");		
		HKEY_Key="footer";		
		//设置页脚（为空） 根据你自己要设置的填入		
		Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		//wb.execwb(8,1);
		mWindow.print();
		window.document.body.innerHTML = mStr;
	}
	function PrintTableNew(Id) {
		var dateStr = "";
		dateStr +="&ITEM_ID="+Id;
		dateStr +="&typeId="+3;
		dateStr +="&TemplatePath=itemdetail.doc";
		dateStr +="&TemplateName=服务事项详细信息";
		//打印模版
       $.dialog.open("printAttachController.do?printItemTemplate"+dateStr, {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	}
	function DetailPrintTableNew(Id) {
		var dateStr = "";
		dateStr +="&ITEM_ID="+Id;
		dateStr +="&typeId="+3;
		dateStr +="&TemplatePath=itemdetailinfo.doc";
		dateStr +="&TemplateName=服务事项详细信息";
		var dataStrEncode = encodeURI(dateStr)
		//打印模版
       $.dialog.open("printAttachController.do?printItemDetailTemplate"+dataStrEncode, {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <div class="easyui-layout" fit="true" >
    <!--==========隐藏域部分开始 ===========-->
    <input type="hidden" name="BUS_TYPEIDS" value="${serviceItem.BUS_TYPEIDS}" >
    <!--==========隐藏域部分结束 ===========-->
        <div data-options="region:'west',split:false"
                        style="width:250px;">
                        <div class="right-con">
                            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                                <div class="e-toolbar-ct">
                                    <div class="e-toolbar-overflow">
                                        <div style="color:#005588;">
                                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                                style="position: relative; top:7px; float:left;" />&nbsp;服务类别树
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <ul id="busTypeTree" class="ztree"></ul>
         </div>
        <div data-options="region:'center'" id="itemInfo">
        	<!--<input style="height:24px;width:50px;" type="button" value="打 印" onclick="return PrintTableNew('${serviceItem.ITEM_ID}')" class="noprint">-->
        	<input style="height:24px;width:55px;" type="button" value="内部打印" onclick="return DetailPrintTableNew('${serviceItem.ITEM_ID}')" class="noprint">
            <table style="width:100%;border-collapse:collapse;" id="itemInfoTable"
                        class="dddl-contentBorder dddl_table">
                        <tr style="height:40px;display: none;" id="title">
                        	<td colspan="4" style="font-weight:bold;font-size: 30px;text-align: center;">办事指南信息</td>
                        </tr>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>事项目录名称：</b>
                            </td>
                            <td colspan="3">
                                ${serviceItem.CATALOG_NAME}
                            </td>
                        </tr>
                        <tr class="noprint">
                            <td  style="text-align:right;width:120px;"><b>事项目录编码：</b>
                                </td>
                            <td colspan="3">
                                ${serviceItem.CATALOG_CODE}
                                </td>
                        </tr>
                        <tr>
                            <td  style="text-align:right;width:120px;"><b>事项名称：</b>
                                </td>
                            <td style="width:400px;">
                                ${serviceItem.ITEM_NAME}
                                </td>
                            <td style="text-align:right;width:150px;"><b>事项属地：</b>
                                </td>
                            <td id="sxsx">
                            ${serviceItem.sxsx}
                             </td>
                        </tr>
                        <tr class="noprint">
                            <td  style="text-align:right;width:150px;"><b>唯一码（事项编码）：</b>
                                </td>
                            <td >
                                ${serviceItem.CODE}
                            </td>
                           <td  style="text-align:right;width:150px;"><b>事项编码：</b>
                                </td>
                            <td >
                                ${serviceItem.ITEM_CODE}
                            </td>
                        </tr>
                       <tr>
                            <td style="text-align:right;width:120px;"><b>事项性质：</b>
                             </td>
                            <td id="sxxz">
                            	${serviceItem.SXXZ}
                             </td>
                             <td style="text-align:right;width:120px;"><b>办件类型：</b>
                             </td>
                             <td id="bjlx">
                             </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>主办处室：</b>
                             </td>
                            <td >
                                ${serviceItem.ZBCS}
                             </td>
                             <td style="text-align:right;width:120px;"><b>实施单位：</b>
                             </td>
                            <td>
                                ${serviceItem.IMPL_DEPART}
                             </td>
                        </tr>
                        <tr>
                             <td style="text-align:right;width:120px;"><b>联系人：</b>
                             </td>
                            <td colspan="3">
                                ${serviceItem.LXR}
                             </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>联系电话：</b>
                             </td>
                            <td >
                                ${serviceItem.LXDH}
                             </td>
                             <td style="text-align:right;width:120px;"><b>监督电话：</b>
                             </td>
                             <td >
                                ${serviceItem.JDDH}
                             </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>关键字：</b>
                             </td>
                            <td >
                                ${serviceItem.KEYWORD}
                             </td>
                             <td style="text-align:right;width:120px;"><b>数量限制说明：</b>
                             </td>
                             <td >
                                ${serviceItem.NSPSLXZ}
                             </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>备注说明：</b>
                             </td>
                            <td colspan="3">
                                ${serviceItem.BZSM}
                             </td>
                        </tr>
                        <tr style="height:29px;">
                            <td colspan="4" class="dddl-legend" style="font-weight:bold;">面向对象</td>
                        </tr> 
						<c:forEach items="${serviceItem.busTypenames}" var="names" varStatus="ns">
                        <tr>
                        	<td colspan="1" style="text-align:right;width:120px;">
                        	<b>${names.PNAME}:</b>
                        	</td>
                        	<td colspan="3">
                        	<span>${names.TYPE_NAME}</span>
                        	</td>
                        </tr>
						</c:forEach>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>时限配置</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>承诺时限：</b>
                                </td>
                            <td colspan="3">
                                  ${serviceItem.CNQXGZR}
                                </td>
                        </tr>
                         <tr>
                            <td style="text-align:right;width:120px;"><b>法定时限：</b>
                                </td>
                            <td colspan="3">
                                  ${serviceItem.FDSXGZR}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>承诺时限说明：</b>
                                </td>
                            <td colspan="3">${serviceItem.CNQXSM}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>法定时限说明：</b>
                                </td>
                            <td colspan="3">${serviceItem.FDQX}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>外网预审时限：</b>
                                </td>
                            <td colspan="3">${serviceItem.PREDAY}工作日
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>收件时限：</b>
                                </td>
                            <td colspan="3">${serviceItem.RECEIVEDAY}工作日
                                </td>
                        </tr>
                    	<tr>
                            <td style="text-align:right;width:120px;"><b>入驻办事大厅方式：</b>
                             </td>
                            <td colspan="3" id="rzbsdtfs">
                             </td>
                        </tr>
                        <c:if test="${serviceItem.RZBSDTFS==in02}">
                        <tr>
                            <td style="text-align:right;width:120px;"><b>群众办事网址：</b>
                                </td>
                            <td colspan="3">${serviceItem.APPLY_URL}
                                </td>
                        </tr>
                        </c:if>
                    	<tr>
                            <td style="text-align:right;width:120px;"><b>去现场次数最多：</b>
                             </td>
                            <td colspan="3">
                                ${serviceItem.SBFSQXCCS}
                             </td>
                        </tr>
                        
                          <tr>
                            <td style="text-align:right;width:120px;"><b>特殊环节：</b>
                             </td>
                            <td colspan="3">
                                ${serviceItem.tshj}
                             </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>收费配置</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>是否收费：</b>
                                </td>
                            <td colspan="3" id="sfsf">
                                </td>
                        </tr>
                        <c:if test="${serviceItem.SFSF==1}">
                        <tr>
                            <td style="text-align:right;width:120px;"><b>收费方式：</b>
                                </td>
                            <td colspan="3" id="sffs">
                            ${serviceItem.sffs}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>收费标准及依据：</b>
                                </td>
                            <td colspan="3">${serviceItem.SFBZJYJ}
                                </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>承接信息</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>是否已承接：</b>
                                </td>
                            <td colspan="3" id="isUndertake">
                                </td>
                        </tr>
                        <c:if test="${serviceItem.IS_UNDERTAKE==0}">
                        <tr>
                            <td style="text-align:right;width:120px;"><b>来函名称：</b>
                                </td>
                            <td colspan="3">${serviceItem.WCJLHMC}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>来函文号：</b>
                                </td>
                            <td colspan="3">${serviceItem.WCJLHWH}
                                </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>办公指引</a></div></td>
                        </tr>
                         <tr>
                            <td style="text-align:right;width:120px;"><b>办理窗口：</b>
                             </td>
                            <td colspan="3">
                            ${serviceItem.SSZTMC}
                             </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>对外办公时间：</b>
                                </td>
                            <td colspan="3">${serviceItem.BGSJ}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>办理地点：</b>
                                </td>
                            <td colspan="3">${serviceItem.BLDD}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>交通指引：</b>
                                </td>
                            <td colspan="3">${serviceItem.TRAFFIC_GUIDE}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>网上办理方式：</b>
                                </td>
                            <td colspan="3">${serviceItem.WSSQFS}
                                </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>申请方式</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>申请方式：</b>
                                </td>
                            <td colspan="3">${serviceItem.ckcString}
                                </td>
                        </tr>
                        
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>依据条件</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>法定依据：</b></td>
                            <td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${serviceItem.XSYJ}</pre></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>申请条件：</b>
                            </td>
                            <td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${serviceItem.SQTJ}</pre>
                            </td>
                        </tr>
<%--                        <tr>--%>
<%--                            <td style="text-align:right;width:120px;"><b>办理流程：</b></td>--%>
<%--                            <td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${serviceItem.BLLC}</pre></td>--%>
<%--                        </tr>--%>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>流程人员配置</a></div></td>
                        </tr>                     
                        <tr>
                            <td style="text-align:right;width:120px;"><b>预审人员：</b>
                                </td>
                            <td colspan="3">${serviceItem.MESSAGE_REC}
                                </td>
                        </tr>
						<c:forEach items="${serviceItem.nodeInfos}" var="nodes" varStatus="ns">
                        <tr>
                        	<td colspan="1" style="text-align:right;width:120px;">
                        	<b>${nodes.NODE_NAME}:</b>
                        	</td>
                        	<td colspan="3">
                        	<span>${nodes.USER_NAME}</span>
                        	</td>
                        </tr>
						</c:forEach>
                        <tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>纸质材料提交方式</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>纸质材料提交方式：</b></td>
                            <td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${serviceItem.papersubString}</pre></td>
                        </tr>
                        
                        <tr style="height:29px;">
                            <td colspan="24" class="dddl-legend" style="font-weight:bold;">申请材料</td>
                        </tr>
						<c:forEach items="${serviceItem.applyMaters}" var="maters" varStatus="vs">
                        <tr>
                        	<td colspan="2">
                        	<span style="width: 50px;float:left;text-align:right;"><b>${vs.index+1}、</b></span>
                        	<c:if test="${maters.BUSCLASS_NAME!=null&&maters.BUSCLASS_NAME!=undefined&&maters.BUSCLASS_NAME!=''&&maters.BUSCLASS_NAME!='无'}">
                        		<span>【${maters.BUSCLASS_NAME}】</span>
                        	</c:if>
                        	<span>${maters.MATER_NAME}</span>
                        	<c:if test="${maters.MATER_PATH!=null&&maters.MATER_PATH!=undefined&&maters.MATER_PATH!=''&&maters.MATER_PATH!='无'}">
                        		<span>（存在提交材料模板）</span>
                        	</c:if>
                        	<c:if test="${maters.MATER_PATH2!=null&&maters.MATER_PATH2!=undefined&&maters.MATER_PATH2!=''&&maters.MATER_PATH2!='无'}">
                        		<span>（存在材料填写示例）</span>
                        	</c:if>
                        	</td>
                        	<td colspan="2">
                                    <c:if test="${maters.MATER_ISNEED==null||maters.MATER_ISNEED==''||maters.MATER_ISNEED=='0'}">非必需；</c:if>
                                    <c:if test="${maters.MATER_ISNEED=='1'}">必需；</c:if>
                                <c:if test="${maters.PAGECOPY_NUM==null&&maters.PAGE_NUM==null}">
                                    <c:if test="${maters.MATER_CLSMLX==null||maters.MATER_CLSMLX==''}">无</c:if>
                                    <c:if test="${maters.MATER_CLSMLX=='复印件'}">原件1份&nbsp;</c:if>
                                    <c:if test="${maters.MATER_CLSMLX!=null&&maters.MATER_CLSMLX!=''}">${maters.MATER_CLSMLX}${maters.MATER_CLSM}份</c:if>
                                </c:if>
                                <c:if test="${maters.PAGECOPY_NUM!=null||maters.PAGE_NUM!=null}">
                                    <c:if test="${maters.PAGECOPY_NUM!=null}">复印件${maters.PAGECOPY_NUM}份</c:if>
                                    <c:if test="${maters.PAGE_NUM!=null}">原件${maters.PAGE_NUM}份（${maters.GATHERORVER}）</c:if>
                                    <c:if test="${fn:indexOf(maters.MATER_CLSMLX, '电子文档') != -1}">电子文档</c:if>
                                </c:if>
	                        	<c:if test="${maters.MATER_SOURCE_TYPE!=null&&maters.MATER_SOURCE_TYPE!=undefined&&maters.MATER_SOURCE_TYPE!=''
	                        	&&maters.MATER_SOURCE_TYPE!='无'&&maters.MATER_SOURCE_TYPE=='本单位'}">
	                        		<span>；&nbsp;&nbsp;出具单位：本单位</span>
	                        	</c:if>
	                        	<c:if test="${maters.MATER_SOURCE_TYPE!=null&&maters.MATER_SOURCE_TYPE!=undefined&&maters.MATER_SOURCE_TYPE!=''
	                        	&&maters.MATER_SOURCE_TYPE!='无'&&maters.MATER_SOURCE_TYPE=='非本单位'}">
	                        		<span>；&nbsp;&nbsp;出具单位：${maters.MATER_SOURCE}</span>
	                        	</c:if>
                            </td>
                        </tr>
						</c:forEach>
						<tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>办理结果信息</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>办理结果：</b>
                                </td>
                            <td id="finishType">
                            
        							${serviceItem.finishType}
                                </td>
                            <td style="text-align:right;width:120px;"><b>获取方式：</b>
                                </td>
                            <td id="finishGettype">
                            ${serviceItem.finishGettype}
                                </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>结果获取说明：</b>
                                </td>
                            <td colspan="3">
                            	${serviceItem.FINISH_INFO}
                            </td>
                        </tr>
						<tr>
                            <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>其他信息</a></div></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;width:120px;"><b>最后更新时间：</b>
                                </td>
                            <td colspan="3">
                            	${serviceItem.UPDATE_TIME}
                            </td>
                        </tr>
                        <c:if test="${!empty serviceItem.uplogList}">
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;">修改日志</td>
						    </tr>
						</c:if>
						<c:forEach items="${serviceItem.uplogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
							<tr>
								<td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>流转日志</a></div></td>
						    </tr>
						<c:forEach items="${serviceItem.fabulogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
						<%-- <c:forEach items="${serviceItem.canclogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作人员：</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">操作时间：</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">维护内容：</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="1" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach> --%>
            </table>
        </div>
    </div>	
</body>
