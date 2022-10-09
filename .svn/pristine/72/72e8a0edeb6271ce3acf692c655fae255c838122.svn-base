<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">

<body>

<div class="eui-main">


    <jsp:include page="head.jsp"></jsp:include>
    <jsp:include page="banner.jsp"></jsp:include>
    <input name="PROJECTCODE" type="hidden" value="${project.PROJECT_CODE}" />
    <!-- 主体 -->
    <div class="eui-con">


        <div class="eui-tit round">
            <b>项目基本信息</b>
        </div>
        <div class="eui-table-info" id="tzjbxx">
            <table>
                <tr>
                    <th>项目编码</th>
                    <td>${project.PROJECT_CODE}</td>
                    <th>项目名称</th>
                    <td>${project.PROJECT_NAME}</td>
                </tr>
                <tr>
                    <th>项目(法人)单位</th>
                    <td colspan="3">${project.ENTERPRISE_NAME}</td>
                </tr>
                <tr>
                    <th>项目类型</th>
                    <td>${project.DIC_NAME}</td>
                    <th>事项属性</th>
                    <td>${project.SXLX}</td>
                </tr>
                <tr>
                    <th>国标行业</th>
                    <td>${project.INDUSTRY}</td>
                    <th>所属行业</th>
                    <td>${project.THE_INDUSTRY}
                    </td>
                </tr>
                <tr>
                    <th>所属产业结构<br>调整目录</th>
                    <td colspan="3">
                        ${project.INDUSTRY_STRUCTURE}
                    </td>
                </tr>
                <tr>
                    <th>投资目录分类</th>
                    <td colspan="3">
                       ${project.PERMIT_ITEM_CODE}
                    </td>
                </tr>
                <tr>
                    <th>建设地点</th>
                    <td>
                     ${project.PLACE_CODE}
                    </td>
                    <th>建设性质</th>
                    <td>
                        ${project.PROJECT_NATURE}
                    </td>
                </tr>
                <tr>
                    <th>建设地点详情</th>
                    <td colspan="3">
                        	${project.PLACE_CODE_DETAIL}
                    </td>
                </tr>
                <tr>
                    <th>项目总投资及<br>资金来源</th>
                    <td colspan="3">
                        <textarea rows="3" cols=""> ${project.TOTAL_MONEY}</textarea>
                    </td>

                </tr>
                <tr>
                    <th>拟开工时间<br>(年月)</th>
                    <td>
                        ${project.START_YEAR}
                    </td>
                    <th>拟建成时间<br>(年月)</th>
                    <td>
                    ${project.END_YEAR}
                    </td>
                </tr>
                <tr>
                    <th>项目总投资及<br>资金来源</th>
                    <td colspan="3">
                        <textarea rows="4" cols="">   ${project.TOTAL_MONEY}</textarea>

                    </td>
                </tr>
            </table>
        </div>

        <div class="eui-tit round">
            <b>审批结果信息</b>
        </div>
        <div class="slideTxtBox eui-tab4">
            <div class="hd">
                <ul class="eui-flex">
                    <li>立项用地规划许可阶段</li>
                    <li>工程建设许可阶段</li>
                    <li>施工许可阶段</li>
                    <li>竣工验收阶段</li>
                </ul>
            </div>
            <div class="bd">
                <!-- 1立项用地规划许可阶段 -->
                <div class="eui-index-bjgs in">
                    <table>
                        <tr>
                            <th width="20%">事项编号</th>
                            <th align="left">审批事项</th>
                            <th width="20%">受理时间</th>
                            <th width="15%">办理结果</th>
                        </tr>
                        <tr>
                            <td align="center">00010XK03702</td>
                            <td>建设用地规划许可证及划拨决定书核发</td>
                            <td align="center">施工许可</td>
                            <td align="center">已受理</td>
                        </tr>
                        <tr>
                            <td align="center">00010XK03702</td>
                            <td>建设用地规划许可证及划拨决定书核发</td>
                            <td align="center">施工许可</td>
                            <td align="center">已受理</td>
                        </tr>
                        <tr>
                            <td align="center">00010XK03702</td>
                            <td>建设用地规划许可证及划拨决定书核发</td>
                            <td align="center">施工许可</td>
                            <td align="center">已受理</td>
                        </tr>
                    </table>
                </div>
                <!-- 2工程建设许可阶段 -->
                <div class="">

                </div>
                <!-- 3施工许可阶段 -->
                <div class="">

                </div>
                <!-- 4竣工验收阶段 -->
                <div class="">

                </div>
            </div>
        </div>


    </div>
    <!-- 主体 end -->

    <!-- 底部 -->
    <div class="eui-footer">
        <iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
    </div>
    <!-- 底部 end -->
</div>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
    $(function() {
        // banner切换
        jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
        // 选项卡切换
        jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
    });
</script>
<script type="text/javascript">
    $(function(){
        $.post( "webSiteController/loadTZXMXXData.do",{
                projectCode:'2020-350128-92-01-065353'},
            function(responseText, status, xhr) {

            });
    });

</script>
</body>
</html>