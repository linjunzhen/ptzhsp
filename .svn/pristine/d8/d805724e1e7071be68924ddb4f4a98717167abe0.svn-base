<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>流程明细-${EFLOW_FLOWEXE.SUBJECT}</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
<link type="text/css" href="plug-in/eveflowdesign-1.0/css/design.css"
	rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 开始引入artdialog -->
<link type="text/css" href="plug-in/artdialog-4.1.7/skins/default.css"
	rel="stylesheet">
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript"
	src="plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
<!-- 结束引入artdialog -->
<script type="text/javascript"
	src="plug-in/layer-1.8.5/layer.min.js"></script>
<script type="text/javascript"
	src="plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript"
	src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>

<script type="text/javascript">	
	$(function() {
	    var url=encodeURI('${IFRAME_URL}');
		$("#EFLOWFORM_IFRAME").attr('src',url);//设置src属性
	});
</script>	
</head>

<body class="easyui-layout bodySelectNone" fit="true" >
    <input type="hidden" name="EFLOW_FLOWOBJ" id="EFLOW_FLOWOBJ" value="${EFLOW_FLOWOBJ}"/>
    
    <c:if test="${EFLOW_QUERYBTNRIGHTS!=null&&EFLOW_QUERYBTNRIGHTS.size()>0}">
	<div data-options="region:'north',collapsible:false" style="height: 42px;" split="true" border="false" >
	     <div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
					    <div style="text-align:center;">
					       <c:forEach items="${EFLOW_QUERYBTNRIGHTS}" var="EFLOW_QUERYBTNRIGHT">
				           <button class="eflowbutton" id="${EFLOW_QUERYBTNRIGHT.BUTTON_KEY}" onclick="FlowUtil.${EFLOW_QUERYBTNRIGHT.BUTTON_FUN}();" >
                                ${EFLOW_QUERYBTNRIGHT.BUTTON_NAME}
				           </button>
				           </c:forEach>
						</div>
					</div>
				</div>
			</div>
		 </div>
	</div>
	</c:if>

    <div data-options="region:'center'" >
       
		<div class="easyui-tabs eve-tabs" fit="true" >
		
			<div title="申请表单" >
			    <iframe id="EFLOWFORM_IFRAME" scrolling="auto" width="100%" height="90%" frameborder="0" >
			    </iframe>
			</div>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
			<div title="流转公文列表" href="fileAttachController.do?flowFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}">
            </div>
            </c:if>
			<c:forEach items="${bindForms}" var="bindForm">
			   <div title="${bindForm.FORM_NAME}" >
				    <iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="${bindForm.FORM_URL}" >
				    </iframe>
			   </div>
			</c:forEach>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='020'
			&&EFLOWOBJ.EFLOW_DEFKEY!='007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='zhtz0301'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
			<div title="审核过程明细" href="flowTaskController.do?view&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
			</div>
			</c:if>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='zhtz0301' 
			|| EFLOWOBJ.EFLOW_DEFKEY=='007'
			|| EFLOWOBJ.EFLOW_DEFKEY=='020'}">
			<div title="审核过程明细" href="flowTaskController.do?tzxmView&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
			</div>
			</c:if>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='ss001' 
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss002'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss003'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss004'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss005'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss006'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss007'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ss008'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ssqyzxlc'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ssnzqybalc'
			|| EFLOWOBJ.EFLOW_DEFKEY=='ssnzqyzxlc'}">
			<div title="审核过程明细" href="flowTaskController.do?zzhyView&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
			</div>
			</c:if>
            <c:if test="${EFLOWOBJ.EFLOW_EXEID=='FJPT210119564371'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210531648689'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210607653467'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210201571600'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210317598760'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210226586110'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210317598761'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210201571570'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210317598758'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210128570016'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210628670589'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210412612471'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210416616974'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210421620810'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210224584405'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210506629544'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210607653383'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210618662485'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210119564417'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210421620621'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210401606984'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210519639603'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210811727339'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT211020857402'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210618663080'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210715687020'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210518639255'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210726709477'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210622665316'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT201231553706'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210104554311'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210823761488'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210728712717'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210128570493'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210618663106'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT211112880657'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT211019856155'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210622665299'
            ||EFLOWOBJ.EFLOW_EXEID=='FJPT210421620933'}">
            <div title="审批事项办理明细（建设中）" href="flowTaskController.do?gcjsItemView&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
            </div>
            </c:if> 			
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
			<div title="补件信息列表" >
			     <iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="bjxxController.do?bjxxFiles&isFiled=${isFiled}&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
                </iframe>
            </div>
            </c:if>
			<div title="流程图" href="flowDefController.do?flowimg&isFiled=${isFiled}&defId=${EFLOW_FLOWDEF.DEF_ID}&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='ss001' 
				|| EFLOWOBJ.EFLOW_DEFKEY=='ss003'
				|| EFLOWOBJ.EFLOW_DEFKEY=='ss004'
				|| EFLOWOBJ.EFLOW_DEFKEY=='ss005'
				|| EFLOWOBJ.EFLOW_DEFKEY=='ss006'
				|| EFLOWOBJ.EFLOW_DEFKEY=='ss007'}">
			<div title="旧版流程图" href="webpage/hflow/flowdef/zzhyFlowimg.jsp?img=nzimg" >
			</div>
			</c:if>
			<c:if test="${
					EFLOWOBJ.EFLOW_DEFKEY=='ss002'
					|| EFLOWOBJ.EFLOW_DEFKEY=='ss008'}">
			<div title="旧版流程图" href="webpage/hflow/flowdef/zzhyFlowimg.jsp?img=wzimg" >
			</div>
			</c:if>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
			<div title="查看打印日志" href="printAttachController.do?printFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}">
            </div>
            </c:if>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
			<div title="邮递信息" href="fileAttachController.do?mailInfo&exeId=${EFLOWOBJ.EFLOW_EXEID}">
            </div>
            </c:if>
            <c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'}">
                <c:if test="${EFLOW_FLOWEXE.RUN_STATUS=='2'||EFLOW_FLOWEXE.RUN_STATUS=='3'}">
		            <div title="办件结果信息">
		            	<iframe id="FLOWRESULT_IFRAME" scrolling="auto" width="100%" height="90%" frameborder="0" src="executionController.do?flowResultDetail&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
					    </iframe>
		            </div>
	            </c:if>
            </c:if>
			<c:if test="${busRecord.ISNEEDSIGN==1}">
			<div title="查看面签信息" href="exeDataController.do?commercialSignView&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
			</c:if>
			
			<!-- 省网回流数据开始 -->
			<!-- 只对回流完成的展示页签 -->
            <c:if test="${busRecord.IS_PROVINCE_ACCEPTED == '1'}">
	            <div title="省网数据回流信息">
	            	<iframe id="PROVINCEDATA_IFRAME" scrolling="auto" width="100%" height="95%" frameborder="0" src="executionController.do?provinceDataDetail&exeId=${EFLOWOBJ.EFLOW_EXEID}&isFiled=${isFiled}" >
				    </iframe>
	            </div>
            </c:if>
            <!-- 省网回流数据结束 -->
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='T_TEST_KEY2'
			||EFLOWOBJ.EFLOW_DEFKEY=='项目申报施工许可'
			||EFLOWOBJ.EFLOW_DEFKEY=='xmsbsgxk'
			||EFLOWOBJ.EFLOW_DEFKEY=='xmsbsgxkbg'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsgxkfqzbl'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsgxkbg'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsbjgxk'
			||EFLOWOBJ.EFLOW_DEFKEY=='gczljgysjd'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcjsxfsjsc001'
			||EFLOWOBJ.EFLOW_DEFKEY=='JSGCXFYSHJSGCJGYSXFBALC'
			||EFLOWOBJ.EFLOW_DEFKEY=='JSGCXFYSJBACC'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcjstsxfys'
            ||EFLOWOBJ.EFLOW_DEFKEY=='GCJSQTXFYS'   
			}">
				<div title="生成材料列表" >
					<iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="scclController.do?scclFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
					</iframe>
				</div>
			</c:if>
			
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcgytdsyfwsyqzydj'
			||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcgyjszydj'
			||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcygspfdyqygdj'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcygspfygdj'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcdyqscdj'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcdyqscdj20200927'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcdyqzxdj'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcgyjsscdj' 
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcgyjszydjclfsflb'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcqlcgyjszydjfh'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcgeneralflow'
            ||EFLOWOBJ.EFLOW_DEFKEY=='bdcgeneralflow2' 
			}">
				<div title="签章材料列表">
					<iframe scrolling="auto" width="100%" height="90%" frameborder="0"
						src="bdcQlcMaterGenAndSignController.do?signFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}">
					</iframe>
				</div>
			</c:if>

			<%--查看此办件上传过得材料--%>
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY!='ss001'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss002'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss003'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss004'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss005'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss006'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss007'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ss008'
			&&EFLOWOBJ.EFLOW_DEFKEY!='OldAgeCardNewFlow'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssqyzxlc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqybalc'
			&&EFLOWOBJ.EFLOW_DEFKEY!='ssnzqyzxlc'}">
				<div title="已上传材料" href="fileAttachController.do?queryAllUploadFilesView&exeId=${EFLOWOBJ.EFLOW_EXEID}">
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>
