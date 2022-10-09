<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String exeId = (String)request.getAttribute("exeId");
    String busTableName = (String)request.getAttribute("busTableName");
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
</head>

<body>
		<!-- =========================表格开始==========================-->
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro1">
            <thead>
                <tr>
                    <td class="tab_width1" width="50px">序号</td>
                    <td class="tab_width1" width="350px">材料名称</td>
                    <td class="tab_width1">材料附件列表</td>
                    <td class="tab_width1" width="250px">操作</td>
                </tr>
            </thead>
            <c:if test="${busTableName == 'T_BSFW_GCJSSGXK'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(1);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    福建省房建和市政基础设施工程施工许可申请表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(1);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">建筑工程施工许可证</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:showTemplate();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建筑施工许可证套打
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:showTemplate();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    套打
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
            </c:if>
			  <c:if test="${busTableName == 'T_BSFW_GCJSSGXKBG'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">建筑工程施工许可证</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:printTemplateBg();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建筑施工许可证套打
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:printTemplateBg();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    套打
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
            </c:if>
			  <c:if test="${busTableName == 'T_BSFW_GCJSSGXKFQZBL'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">建筑工程施工许可证</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:printTemplateFqzbl();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建筑施工许可证套打
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:printTemplateFqzbl();"
                               style="cursor: pointer;">
                                <font color="blue">
                                    套打
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
            </c:if>
            <c:if test="${busTableName == 'TB_PROJECT_FINISH_MANAGE'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(21);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    福建省房屋建筑和市政基础设施工程竣工验收备案表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(21);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(22);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    竣工验收备案表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(22);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
            </c:if>
            <c:if test="${busTableName == 'TB_FC_PROJECT_INFO'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(3);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建设工程消防设计审查申报表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(3);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
                
             <c:forEach var="xfsj" items="${xfsjMap}" varStatus="varStatus">
              <tr>
                 <td>${varStatus.index+2}</td>
                 <td style="text-align: center;">申请表</td>
                 <td>
                    <p>
                    <a href="javascript:void(0);"
                            onclick="AppUtil.downLoadFile('${xfsj.FILE_ID}');"
                             style="cursor: pointer;">
                              <font color="blue">
                                           ${xfsj.FILE_NAME}
                              </font>
                    </a>
                    </p>
                 </td>
                 <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="AppUtil.downLoadFile('${xfsj.FILE_ID}');"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
              </tr>
            </c:forEach>              
            </c:if>
            
            <c:if test="${busTableName == 'T_BSFW_GCJSXFYS'}">
                <tr>
                    <td>1</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(41);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建设工程消防验收申报表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(41);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>2</td>
                    <td style="text-align: center;">申请表</td>
                    <td>
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(42);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    建设工程竣工验收消防备案申报表.docx
                                </font>
                            </a>
                        </p>
                    </td>
                    <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="javascript:downloadDoc(42);"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
                </tr>
                
                
             <c:forEach var="xfsj" items="${xfsjMap}" varStatus="varStatus">
              <tr>
                 <td>${varStatus.index+3}</td>
                 <td style="text-align: center;">申请表</td>
                 <td>
                    <p>
                    <a href="javascript:void(0);"
                            onclick="AppUtil.downLoadFile('${xfsj.FILE_ID}');"
                             style="cursor: pointer;">
                              <font color="blue">
                                           ${xfsj.FILE_NAME}
                              </font>
                    </a>
                    </p>
                 </td>
                 <td style="text-align: center;">
                        <p>
                            <a href="javascript:void(0);"
                               onclick="AppUtil.downLoadFile('${xfsj.FILE_ID}');"
                               style="cursor: pointer;">
                                <font color="blue">
                                    下载
                                </font>
                            </a>
                        </p>
                    </td>
              </tr>
            </c:forEach> 
                
                
            </c:if>

            <!--  申请表（前台用户盖章后上传）-->
            <c:if test="${!empty APPLYFORMFILEFONT}">
            <tr>
                <td>2</td>
                <td style="text-align: center;">申请表</td>
                <td>
                    <p>
                        <a href="javascript:void(0);"
                           style="cursor: pointer;">
                            <font color="blue">
                                申请表（前台用户盖章后上传）.edc
                            </font>
                        </a>
                    </p>
                </td>
                <td style="text-align: center;">
                    <p>
                        <a href="${APPLYFORMFILEFONT}"
                                style="cursor: pointer;">
                            <font color="blue">
                                下载
                            </font>
                        </a>


                    </p>
                </td>
            </tr>
            </c:if>
            <%--<c:forEach var="file" items="${fileList}" varStatus="varStatus">--%>
              <%--<tr>--%>
                 <%--<td>${varStatus.index+1}</td>--%>
                 <%--<td style="text-align: center;">${file.MATER_NAME}</td>--%>
                 <%--<td>--%>
                    <%--<p>--%>
                    <%--<a href="javascript:void(0);"--%>
                            <%--onclick="AppUtil.downLoadFile('${file.FILE_ID}');"--%>
                             <%--style="cursor: pointer;">--%>
                              <%--<font color="blue">--%>
                                           <%--${file.FILE_NAME}--%>
                              <%--</font>--%>
                    <%--</a>--%>
                    <%--</p>--%>
                 <%--</td>--%>
                 <%--<td style="text-align: center;">${file.BJYJ}</td>--%>
              <%--</tr>--%>
            <%--</c:forEach>--%>
        </table>
		<!-- =========================表格结束==========================-->
</body>
<script type="text/javascript">
    function downloadDoc(materCode){
        var exeId = '${exeId}';
        if(materCode==1){
            window.location.href=__ctxPath+"/scclController/downLoadSGXKMater.do?exeId="+exeId+"&fileName="+encodeURIComponent('福建省房建和市政基础设施工程施工许可申请表');
        }else if(materCode==21){
            window.location.href=__ctxPath+"/scclController/downLoadJGYS1Mater.do?exeId="+exeId+"&fileName="+encodeURIComponent('福建省房屋建筑和市政基础设施工程竣工验收备案表');
        }else if(materCode==22){
            window.location.href=__ctxPath+"/scclController/downLoadJGYS2Mater.do?exeId="+exeId+"&fileName="+encodeURIComponent('竣工验收备案表');
        }else if(materCode==3){
            window.location.href=__ctxPath+"/scclController/downLoadXFSJMater.do?exeId="+exeId+"&fileName="+encodeURIComponent('建设工程消防设计审查申报表');
        }else if(materCode==41){
            window.location.href=__ctxPath+"/scclController/downLoadXFYS1Mater.do?exeId="+exeId+"&fileName="+encodeURIComponent('建设工程消防验收申报表');
        }else if(materCode==42){
            window.location.href=__ctxPath+"/scclController/downLoadXFYS2Mater.do?exeId="+exeId+"&fileName="+encodeURIComponent('建设工程竣工验收消防备案申报表');
        }

    }


    function showTemplate(){
        //var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());

        var exeId = '${exeId}';
        //var url = __ctxPath+"/scclController/printTemplate.do?EFLOW_EXEID="+flowVars.EFLOW_EXEID;
        var url=encodeURI("scclController/printTemplate.do?exeId="+exeId);
        //打印模版
        $.dialog.open(url, {
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


    function printTemplateBg(){
        //var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());

        var exeId = '${exeId}';
        //var url = __ctxPath+"/scclController/printTemplate.do?EFLOW_EXEID="+flowVars.EFLOW_EXEID;
        var url=encodeURI("scclController/printTemplateBg.do?exeId="+exeId);
        //打印模版
        $.dialog.open(url, {
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
    function printTemplateFqzbl(){
        //var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());

        var exeId = '${exeId}';
        //var url = __ctxPath+"/scclController/printTemplate.do?EFLOW_EXEID="+flowVars.EFLOW_EXEID;
        var url=encodeURI("scclController/printTemplateFqzbl.do?exeId="+exeId);
        //打印模版
        $.dialog.open(url, {
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
</html>
