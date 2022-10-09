<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css"/>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,artdialog,json2"></eve:resources>
<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>

</head>

<body style="margin:0px;background-color: #f7f7f7;">

		<div id="BackFlowFormDiv">
			<!--==========隐藏域部分开始 ===========-->

            
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;" id="SHYJTABLE"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend" style="font-weight:bold;">补件意见信息</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">补件意见：</span>
					     <textarea rows="5" cols="5" class="eve-textarea " 
							style="width: 400px" name="BJYJ" readonly="readonly">${bjxx.BJYJ}</textarea>
					</td>
				</tr>
			</table>
            <table style="width:100%;border-collapse:collapse;" 
                class="dddl-contentBorder dddl_table">
                <tr style="height:29px;"><td colspan="1" class="dddl-legend"  style="font-weight:bold;" >补件材料信息</td></tr>
                  <tr >
                      <td>
                          <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
                                <tr>
                                    <td style="text-align: center;font-weight:bold;">材料名称</td>
                                    <td style="text-align: center;font-weight:bold;">补件要求</td>
                                </tr>
                                <c:forEach var="bjcl" items="${bjxx.bjclList}">
                                <tr>
                                    <td style="text-align: center;">${bjcl.MATER_NAME}</td>
                                    <td style="text-align: center;">${bjcl.BJYJ}</td>
                                </tr>
                                </c:forEach>
                          </table>
                      </td>
                  </tr>
            </table>

		</div>
		<div class="eve_buttons">
			<input value="确定" type="button"
                class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="AppUtil.closeLayer();" /> 
		</div>
	</form>

</body>

