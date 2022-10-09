<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
<html lang="zh-CN">
<head></head>
<script type="text/javascript">
    function  toUrl(url) {
        window.location.href=__ctxPath+url;
    }
</script>

<body>

<div class="eui-main">
    <jsp:include page="head.jsp"></jsp:include>
    <jsp:include page="banner.jsp"></jsp:include>
 <!-- 隐藏域开始-->
<input name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" type="hidden" />
    <!-- 隐藏域结束-->


    <!-- 主体 -->
    <div class="eui-con">

        <div class="eui-tit round">
            <b>类型</b>
        </div>
        <div class="eui-filter i1 eui-flex wrap">
            <e:for var="efor" dsid="237" filterid="1" end="16"  >

                <span onclick="toUrl('projectWebsiteController.do?bsznView&typeId=${efor.TYPE_ID}')"
                        <c:if test="${efor.TYPE_ID==typeId}"> class="on"</c:if>>
                        ${efor.TYPE_NAME}
                </span>

            </e:for>
        </div>

        <div class="eui-tit round">
            <b>阶段</b>
        </div>
        <div class="eui-filter i2 eui-flex wrap">
            <e:for var="efor" dsid="1004" filterid="${typeId}" end="6" >
            <span  <c:if test="${efor.STAGE_ID==stageId}"> class="on" </c:if>
                    onclick="toUrl('projectWebsiteController.do?bsznView&typeId=${typeId}&stageId=${efor.STAGE_ID}');setLocation('${efor.STAGE_ID}');">
                ${efor.NAME}阶段</span>
            </e:for>
        </div>
        <input name="stageId" type="hidden"/>
        <div class="eui-flex bt vc" style="margin-top: 30px;">
            <div class="eui-crumb" id="location">当前位置：政府投资房建类项目-立项用地规划许可阶段</div>
            <a class="eui-btn round"  onclick="toApplyUrl()">我要申报 ></a>
        </div>
        <div class="slideTxtBox eui-tab4">
            <div class="hd">
                <ul class="eui-flex">
                    <li>基本信息</li>
                    <li>事项目录</li>
                    <li>申请材料</li>
                    <li>设定依据</li>
                    <li>受理条件</li>
                    <li>办事流程</li>
                    <li>流程图</li>
                </ul>
            </div>
            <div class="bd">
                <!-- 1基本信息 -->
                <div class="eui-table-info">
                    <table>
                        <tr>
                            <th>事项名称</th>
                            <td colspan="3">${serviceItem.ITEM_NAME}</td>
                        </tr>
                        <tr>
                            <th>事项编码</th>
                            <td>${serviceItem.ITEM_CODE}</td>
                            <th>事项性质</th>
                            <td>${serviceItem.SXXZ}</td>
                        </tr>
                        <tr>
                            <th>事项类型</th>
                            <td>${serviceItem.SXLX}</td>
                            <th>办件类型</th>
                            <td><c:if test="${serviceItem.busTypenames==null||serviceItem.busTypenames==''}">无</c:if>${serviceItem.busTypenames}</td>
                        </tr>
                        <tr>
                            <th>法定时限</th>
                            <td>${serviceItem.FDSX}</td>
                            <th>法定时限说明</th>
                            <td> <c:if test="${(serviceItem.FDQX==null||serviceItem.FDQX=='')&&serviceItem.SXLX!='即办件'}">无</c:if>
                                <c:if test="${serviceItem.SXLX=='即办件'}">当日办结</c:if>
                                <c:if test="${serviceItem.FDQX!=null&&serviceItem.FDQX!=''&&serviceItem.SXLX!='即办件'}">
                                    ${serviceItem.FDQX}
                                </c:if></td>
                        </tr>
                        <tr>
                            <th>承诺时限</th>
                            <td>${serviceItem.CNSX}</td>
                            <th>承诺时限说明</th>
                            <td><c:if test="${serviceItem.CNQXSM==null||serviceItem.CNQXSM==''}">无</c:if>${serviceItem.CNQXSM}</td>
                        </tr>
                        <tr>
                            <th>特殊环节时限</th>
                            <td>${serviceItem.CNSX}</td>
                            <th>审查方式</th>
                            <td>${serviceItem.CNQXSM}</td>
                        </tr>
                        <tr>
                            <th>是否收费</th>
                            <td><c:if test="${serviceItem.SFSF==null||serviceItem.SFSF==''}">无</c:if>${serviceItem.SFSF}</td>
                            <th>收费标准</th>
                            <td> <c:choose>
                                <c:when test="${serviceItem.SFBZJYJ==null||serviceItem.SFBZJYJ==''||serviceItem.SFSF=='否'}">无</c:when>
                                <c:otherwise>
                                    ${serviceItem.SFBZJYJ}
                                </c:otherwise>
                            </c:choose></td>
                        </tr>
                        <tr>
                            <th>办理结果</th>
                            <td><c:if test="${serviceItem.RESULT_NAME==null||serviceItem.RESULT_NAME==''}"></c:if>${serviceItem.RESULT_NAME}</td>
                            <th>结果获取方式</th>
                            <td>${serviceItem.FINISH_GETTYPE}</td>
                        </tr>
                        <tr>
                            <th>办理地点</th>
                            <td>${serviceItem.BLDD}</td>
                            <th>办理时间</th>
                            <td>${serviceItem.BGSJ}</td>
                        </tr>
                        <tr>
                            <th>温馨提示</th>
                            <td colspan="3">
                                <c:if test="${serviceItem.BZSM==null||serviceItem.BZSM==''}">无</c:if>${serviceItem.BZSM}
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- 2事项目录 -->
                <div class="eui-index-bjgs in">
                    <table>
                        <tr>
                            <th width="8%">序号</th>
                            <th>事项名称</th>
                            <th width="24%">适用情形</th>
                            <th width="20%">实施主体</th>
                            <th width="12%">操作</th>
                        </tr>
                        <e:for var="efor" dsid="1000" filterid="${stageId}" end="100">
                        <tr>
                            <td align="center">${efor.ROWNUM_}</td>
                            <td>${efor.ITEM_NAME}
                                <c:if test="${efor.IS_KEY_ITEM=='1'}">
                                <span>必经事项</span>
                                </c:if>
                            </td>
                            <td></td>
                            <td>${efor.IMPL_DEPART}</td>
                            <td align="center"><a href="serviceItemController/bsznDetail.do?itemCode=${efor.ITEM_CODE}&projectType=1">办事指南</a></td>
                        </tr>
                        </e:for>
                    </table>


                </div>
                <!-- 3申请材料 -->
                <div class="eui-index-bjgs in">
                    <c:if test="${!empty stageId}">
                    <e:for filterid="${stageId}" end="100" var="efor" dsid="1000">
                    <div class="eui-tit round">
                        <b>事项>${efor.ROWNUM_}：${efor.ITEM_NAME}</b>
                    </div>
                    <table id="${efor.ITEM_CODE}itemMaterTable">
                        <script type="text/javascript">
                            $(function(){
                                $.post("projectWebsiteController/applyItemMaterList.do",{
                                    itemCode:'${efor.ITEM_CODE}',
                                    exeId:'${efor.S_EXE_ID}',
                                    STAGE_ID:'${stageId}',
                                    PROJECT_CODE:$("[name='PROJECT_CODE']").val(),
                                    isWebsite:'${isWebsite}'
                                }, function(responseText, status, xhr) {
                                        $("#${efor.ITEM_CODE}itemMaterTable").append(responseText);
                                });
                            });
                        </script>

                    </table>
                    </e:for>
                    </c:if>
                </div>
                <!-- 4设定依据 -->
                <div class="detail">
                    <p><c:if test="${serviceItem.XSYJ==null||serviceItem.XSYJ==''}">无</c:if>${serviceItem.XSYJ}</p>
                </div>
                <!-- 5受理条件 -->
                <div class="detail">
                 <p>   <c:if test="${serviceItem.SQTJ==null||serviceItem.SQTJ==''}">无</c:if>${serviceItem.SQTJ}</p>
                </div>
                <!-- 6办事流程 -->
                <div class="detail">
                    <p>受理 —— 审查 —— 决定</p>
                </div>
                <!-- 8流程图 -->
                <div style="text-align: center;">
                    <img src="http://1.huangy.applinzi.com/PingtanGCJS/images/办事指南/u1020.jpg" >
                </div>
            </div>
        </div>

    </div>
    <!-- 主体 end -->




    <!-- 底部 -->
    <div class="eui-footer">
        <iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no"
                allowtransparency="true"  src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
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
        $(".eui-filter span").click(function(){
            $(this).addClass("on").siblings().removeClass("on");
        })
        setLocation();//设置位置信息
    });
    //设置位置信息
    function setLocation(stageId){
        var typeName=$("body > div.eui-main  div.eui-filter.i1.eui-flex.wrap > span.on").text();
        var stageName=$("body > div.eui-main   div.eui-filter.i2.eui-flex.wrap > span.on").text();
        var locationName=typeName+"-"+stageName;
        $("#location").text(locationName);
        $("input[name='stageId']").val(stageId);
    }
    function toApplyUrl(){
        var username = '${sessionScope.curLoginMember.YHMC}';
        var stageId="${stageId}";
        var typeId="${typeId}";
        var itemCode=$("input[name='ITEM_CODE']").val();
        var applyUrl="webSiteController.do?applyItem&itemCode="+itemCode;
        if(typeof (stageId)=='undefined'){
            applyUrl+="&typeId="+typeId;
        }else{
            applyUrl+="&typeId="+typeId+"&stageId="+stageId;
        }
        if(null==username||""==username){
            applyUrl=encodeURI("projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638d343d0024");
            window.location.href = "<%=path%>/userInfoController/mztLogin.do?returnUrl="+applyUrl;
        }else{
            window.location.href=__ctxPath+applyUrl;
        }
    }
</script>

</body>
</html>