<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.platform.system.model.SysUser"%>
<%@ page import="net.evecom.core.util.AppUtil"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    request.setAttribute("webRoot", basePath);
    String projectCode = request.getParameter("projectCode");
    request.setAttribute("projectCode", projectCode);
    String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">
        <script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
        <eve:resources 
            loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
        <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
        <!-- my97 begin -->
        <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
        
        <link rel="stylesheet"
            href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
        <link rel="stylesheet"
            href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
        <link rel="stylesheet"
            href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
        <!-- my97 end -->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <title>平潭综合实验区工程建设项目管理平台</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
        <link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/projectBaseInfo.css">
        <script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
        <script type="text/javascript">
            $(function() {
                $.post("webSiteController/loadTZXMXXData.do",{
                       projectCode : "${projectCode}"
                    },
                    function(responseText, status, xhr) {
                        var resultJson = $.parseJSON(responseText);
                        if (resultJson.result) {
                            var proinfo = resultJson.tzProject;
                            $("input[name='PROJ_COD']").val("${projectCode}");
                            $("input[name='PROJ_NAME']").val(proinfo.projectName);
                        } 
                    }
                );
                
                AppUtil.initWindowForm("rfBaseInfoForm", function(form, valid) {
                    if (valid) {
                        //将提交按钮禁用,防止重复提交
                        $("input[type='submit']").attr("disabled", "disabled");
                        var formData = $("#rfBaseInfoForm").serialize();
                        var url = $("#rfBaseInfoForm").attr("action");
                        AppUtil.ajaxProgress({
                            url : url,
                            params : formData,
                            callback : function(resultJson) {
                                if (resultJson.success) {
                                    //window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/wdxm";
                                    parent.art.dialog({
                                        content: "提交成功",
                                        icon:"succeed",
                                        ok: function(){window.close();}
                                    });
                                    
                                } else {
                                    parent.art.dialog({
                                        content: resultJson.msg,
                                        icon:"error",
                                        ok: true
                                    });
                                    $("input[type='submit']").attr("disabled", false);
                                }
                            }
                        });
                    }
                }, "SPGL_XMJBXXB");
            });
        </script>
    </head>
    <body>
        <!--头部开始-->
        <div class="eui-head">
            <ul>
                <li><a href="${webRoot}webpage/tzxm/index.jsp">首页</a></li>
                <li><a href="${webRoot}webpage/tzxm/sbzy.jsp">申报指引</a></li>
                <li class="on"><a href="${webRoot}/govIvestController/govIvestlList.do?type=1">办事指南</a></li>
                <li><a href="<%=userCenter%>">我的项目</a></li>
                <li><a href="${webRoot}webpage/tzxm/promulgateInfo/handleResult.jsp">公示信息</a></li>
                <li><a href="${webRoot}govIvestController/policiesRegulations.do">政策法规</a></li>
            </ul>
            <c:if test="${sessionScope.curLoginMember==null}">
                <div class="eui-login">
                    <a href="<%=path%>/userInfoController/mztLogin.do?type=tzxm">登录</a>
                    <a href="<%=path%>/webSiteController/mztRegist.do">注册</a>
                </div>
            </c:if>
        </div>
        <!--头部结束-->
        <!--内容开始-->
        <form id="rfBaseInfoForm" method="post"
            action="webSiteController.do?rfBaseSaveOrUpdate">
            <div class="eui-main">
                <div class="eui-logo">
                    <img src="<%=path%>/webpage/tzxm/images/logo.png"/>
                </div>
                <div class="eui-content">
                    <div class="eui-crumbs">
                        <ul>
                            <li><a href="${webRoot}webpage/tzxm/index.jsp">首页</a> > </li>
                            <li><a href="${webRoot}govIvestController/govIvestlList.do?type=1">办事指南</a> > </li>
                            <li><a>人防项目信息表</a> </li>
                        </ul>
                    </div>
                    <div class="eui-instruction">
                        <div class="slideTxtBox">
                            <input type="hidden" name="ID" value="${entityId}">
                            <input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
                            <input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
                            <input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${projectApply.FOREIGN_ABROAD_FLAG}"/>
                            <input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${projectApply.THE_INDUSTRY}"/>
                            <input type="hidden" id="FLOW_CATE_ID" name="FLOW_CATE_ID" value="${projectApply.FLOW_CATE_ID}"/>
                            <di0000000v class="bd">
                                <div class="eui-table-input">
                                    <div class="eui-table">
                                        <p>基本信息</p>
                                    </div>
                                    <table>
                                        <tr>
                                            <td ><span>*</span> 投资项目编号</td>
                                            <td>
                                                <input class="eve-input validate[required]" type="text" name="PROJ_COD"  readonly/>
                                            </td>
                                            <td><span>*</span> 项目名称</td>
                                            <td>
                                                <input type="text" class="eve-input validate[required]"  maxlength="64" name="PROJ_NAME" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td ><span>*</span> 项目名称英文缩写</td>
                                            <td>
                                                <input class="eve-input validate[required]" type="text" name="PROJ_NAME_EN" />
                                            </td>
                                            <td><span>*</span> 项目类别</td>
                                            <td>
                                                <input type="text" class="eve-input validate[required]"  maxlength="64" name="PROJ_CLASS" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <input value="提交" type="submit" class="eui-submit-btn" /> 
                    </div>
                </div>
        </form>
        <!--内容结束-->
    </body> 
</html>