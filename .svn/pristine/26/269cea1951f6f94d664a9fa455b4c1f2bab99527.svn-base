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
<title>办理流程-${EFLOW_FLOWEXE.SUBJECT}</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
<link type="text/css"
	href="plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
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
		$("#EFLOWFORM_IFRAME").attr('src','${IFRAME_URL}');//设置src属性
	});
</script>
	
</head>

<body class="easyui-layout bodySelectNone" fit="true" >
    <input type="hidden" name="EFLOW_FLOWOBJ" id="EFLOW_FLOWOBJ" value="${EFLOW_FLOWOBJ}"/>
    <!-- 开始编写头部工具栏 -->
	<div data-options="region:'north',collapsible:false" style="height: 42px;" split="true" border="false" >
	     <div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
					    <div style="text-align:center;">
					    <!-- 遍历可控操作的按钮 -->
				        <c:forEach items="${EFLOW_BUTTONRIGHTS}" var="EFLOW_BUTTONRIGHT">
				           <button class="eflowbutton" id="${EFLOW_BUTTONRIGHT.BUTTON_KEY}" 
				           onclick="FlowUtil.${EFLOW_BUTTONRIGHT.BUTTON_FUN}();">
				           ${EFLOW_BUTTONRIGHT.BUTTON_NAME}
				           </button>
				        </c:forEach>	
						</div>
					</div>
				</div>
			</div>
		 </div>
	</div>
	<!-- 结束编写头部工具栏 -->

    <div data-options="region:'center'" >
       
		<div class="easyui-tabs eve-tabs" id="EVEFLOW_TAB" fit="true" >
			<div title="申请表单" >
			    <iframe id="EFLOWFORM_IFRAME" scrolling="auto" width="100%" height="98%" frameborder="0" >
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
				    <iframe scrolling="auto" width="100%" height="98%" frameborder="0" src="${bindForm.FORM_URL}" >
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
			<div title="审核过程明细" href="flowTaskController.do?view&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
			</div>
			</c:if>
			
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='zhtz0301' 
			|| EFLOWOBJ.EFLOW_DEFKEY=='007'
			|| EFLOWOBJ.EFLOW_DEFKEY=='020'}">
			<div title="审核过程明细" href="flowTaskController.do?tzxmView&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
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
			<div title="审核过程明细" href="flowTaskController.do?zzhyView&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
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
                 <iframe scrolling="auto" width="100%" height="90%" frameborder="0" src="bjxxController.do?bjxxFiles&exeId=${EFLOWOBJ.EFLOW_EXEID}" >
                </iframe>
            </div>
            </c:if>
			<div title="流程图" href="flowDefController.do?flowimg&defId=${EFLOW_FLOWDEF.DEF_ID}&exeId=${EFLOWOBJ.EFLOW_EXEID}">
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
			<c:if test="${busRecord.ISNEEDSIGN==1}">
			<div title="查看面签信息" href="exeDataController.do?commercialSignView&exeId=${EFLOWOBJ.EFLOW_EXEID}">
			</div>
			</c:if>
			
			<c:if test="${EFLOWOBJ.EFLOW_DEFKEY=='T_TEST_KEY2'
			||EFLOWOBJ.EFLOW_DEFKEY=='项目申报施工许可'
			||EFLOWOBJ.EFLOW_DEFKEY=='xmsbsgxk'
			||EFLOWOBJ.EFLOW_DEFKEY=='xmsbsgxkbg'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsgxkfqzbl'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsgxkbg'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcxmsbjgxk'
			||EFLOWOBJ.EFLOW_DEFKEY=='gcjsxfsjsc001'
			||EFLOWOBJ.EFLOW_DEFKEY=='gczljgysjd'
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
