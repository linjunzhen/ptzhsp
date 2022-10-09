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
<html lang="zh-CN">


<body>

<div class="eui-main">

    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
    <jsp:include page="head.jsp"></jsp:include>
    <jsp:include page="banner.jsp"></jsp:include>

    <!-- 主体 -->
    <div class="eui-con">
        <!-- 投资建设项目 -->
        <div class="slideTxtBox eui-tab eui-index-tzjs">
            <div class="hd eui-flex bt vt">
                <ul class="eui-flex">
                    <li>政府投资建设项目</li>
                    <li>社会投资建设项目</li>
                </ul>
                <a class="eui-btn round" href="projectWebsiteController/registerProject.do?">项目登记</a>
            </div>
            <div class="bd">
                <!-- 1政府投资建设项目 -->
                <div class="eui-tab2">
                    <div class="inhd">
                        <ul class="eui-flex">
                            <li>线性工程类</li>
                            <li>房屋建筑类</li>
                        </ul>
                    </div>
                    <div class="inbd">
                        <div class="eui-flex eui-tzjs">
                            <e:for var="efor" dsid="1004" filterid="4028e3816b637dc3016b6389ca6e0018" end="6" >
                                <a href="projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b6389ca6e0018&stageId=${efor.STAGE_ID}">
                                    <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i${efor.rownum_}.png" ></i>
                                    <b>${efor.NAME}阶段</b>
                                    <span>${efor.SPJDSX}个工作日</span>
                                </a>
                            </e:for>

                            <a href="<%=basePath%>/webpage/website/project/#">
                                <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i5.png" ></i>
                                <b>市政公共服务</b>
                                <span>8个工作日</span>
                            </a>
                        </div>
                        <div class="eui-flex eui-tzjs">
                            <e:for var="efor" dsid="1004" filterid="4028e3816b632938016b636eba710030" end="6" >
                                <a href="projectWebsiteController.do?bsznView&typeId=4028e3816b632938016b636eba710030&stageId=${efor.STAGE_ID}">
                                    <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i${efor.rownum_}.png" ></i>
                                    <b>${efor.NAME}阶段</b>
                                    <span>${efor.SPJDSX}个工作日</span>
                                </a>
                            </e:for>

                            <a href="<%=basePath%>/webpage/website/project/#">
                                <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i5.png" ></i>
                                <b>市政公共服务</b>
                                <span>8个工作日</span>
                            </a>
                        </div>
                    </div>
                </div>
                <!-- 2社会投资建设项目 -->
                <div  class="eui-tab2">

                    <div class="inhd">
                        <ul class="eui-flex">
                            <li>带方案出让用地</li>
                            <li>小型社会投资</li>
                            <li>社会投资工程建设</li>
                        </ul>
                    </div>
                    <div class="inbd">
                        <div class="eui-flex eui-tzjs">
                            <e:for var="efor" dsid="1004" filterid="4028e3816b637dc3016b638d343d0024" end="6" >
                                <a href="projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638d343d0024&stageId=${efor.STAGE_ID}">
                                    <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i${efor.rownum_}.png" ></i>
                                    <b>${efor.NAME}阶段</b>
                                    <span>${efor.SPJDSX}个工作日</span>
                                </a>
                            </e:for>

                            <a href="<%=basePath%>/webpage/website/project/#">
                                <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i5.png" ></i>
                                <b>市政公共服务</b>
                                <span>8个工作日</span>
                            </a>
                        </div>
                        <div class="eui-flex eui-tzjs">
                            <e:for var="efor" dsid="1004" filterid="4028e3816b637dc3016b638c43520020" end="6" >
                                <a href="projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638c43520020&stageId=${efor.STAGE_ID}">
                                    <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i${efor.rownum_}.png" ></i>
                                    <b>${efor.NAME}阶段</b>
                                    <span>${efor.SPJDSX}个工作日</span>
                                </a>
                            </e:for>

                            <a href="<%=basePath%>/webpage/website/project/#">
                                <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i5.png" ></i>
                                <b>市政公共服务</b>
                                <span>8个工作日</span>
                            </a>
                        </div>
                        <div class="eui-flex eui-tzjs">
                            <e:for var="efor" dsid="1004" filterid="4028e3816b637dc3016b638b5690001c" end="6" >
                                <a href="projectWebsiteController.do?bsznView&typeId=4028e3816b637dc3016b638b5690001c&stageId=${efor.STAGE_ID}">
                                    <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i${efor.rownum_}.png" ></i>
                                    <b>${efor.NAME}阶段</b>
                                    <span>${efor.SPJDSX}个工作日</span>
                                </a>
                            </e:for>

                            <a href="<%=basePath%>/webpage/website/project/#">
                                <i><img src="<%=basePath%>/webpage/website/project/images/tzjs-i5.png" ></i>
                                <b>市政公共服务</b>
                                <span>8个工作日</span>
                            </a>
                        </div>

                    </div>

                </div>
            </div>
        </div>

        <!-- 主题式服务 -->
        <div class="eui-tit">
            <b>主题式服务</b>
        </div>
        <div class="eui-index-grid eui-index-ztfw eui-flex wrap">
            <a class="item bg1">
                <b>项目可行性研究报</b>
                <p>通过对项目的主要内容和配套条件等方面进行调查研究和分析比较</p>
            </a>
            <a class="item bg2">
                <b>用地预审</b>
                <p>通过对项目的主要内容和配套对建设项目涉及的土地利用事项进行的审查</p>
            </a>
            <a class="item bg3">
                <b>选址</b>
                <p>通过对项目的主要内容和配套条件等方面进行调查研究和分析比较</p>
            </a>
            <a class="item bg4">
                <b>环境影响评价</b>
                <p>对人为活动可能造成的环境影响采取的防治措施和对策</p>
            </a>
            <a class="item bg5">
                <b>安全评价</b>
                <p>对企业的生产场地、过程等进行现场检查</p>
            </a>
            <a class="item bg6">
                <b>水土保持评价</b>
                <p>对生态影响及其他环境影响评估</p>
            </a>
        </div>

        <!-- 专项改革 -->
        <div class="eui-tit">
            <b>主题式服务</b>
        </div>
        <div class="eui-index-grid eui-index-zxgg eui-flex bt wrap">
            <a class="item" href="<%=basePath%>/webpage/website/project/#">
                <i><img src="<%=basePath%>/webpage/website/project/images/zxgg-i1.png" ></i>
                <b>多规合一</b>
                <p>理论探索与实践创新</p>
            </a>
            <a class="item" href="<%=basePath%>/webpage/website/project/#">
                <i><img src="<%=basePath%>/webpage/website/project/images/zxgg-i2.png" ></i>
                <b>多图联审</b>
                <p>让企业申办“一键加速”</p>
            </a>
            <a class="item" href="<%=basePath%>/webpage/website/project/#">
                <i><img src="<%=basePath%>/webpage/website/project/images/zxgg-i3.png" ></i>
                <b>多评合一</b>
                <p>联合审查推动审批大提速</p>
            </a>
            <a class="item" href="<%=basePath%>/webpage/website/project/#">
                <i><img src="<%=basePath%>/webpage/website/project/images/zxgg-i4.png" ></i>
                <b>多测合一</b>
                <p>最多跑一次</p>
            </a>
        </div>

        <!-- 办件公示 -->
        <div class="eui-tit">
            <b>办件公示</b>
            <a href="<%=basePath%>/webpage/website/project/#">查看更多 &gt;</a>
        </div>
        <div class="slideTxtBox eui-tab3">
            <div class="hd eui-flex bt">
                <ul class="eui-flex">
                    <li>批前公示</li>
                    <li>批后公示</li>
                </ul>
                <div class="eui-flex eui-search">
                    <input class="ipt" type="text" placeholder="请输入项目代码" />
                    <a class="eui-btn" href="<%=basePath%>/webpage/website/project/javascript:void(0)">项目代码查验</a>
                </div>
            </div>
            <div class="bd">
                <div class="eui-index-bjgs">
                    <table>
                        <tr>
                            <th width="25%">项目代码</th>
                            <th align="left">项目名称</th>
                            <th width="16%">项目类型</th>
                            <th width="12%">办理结果</th>
                        </tr>
                        <e:for var="efor" dsid="239" filterid="0" end="5" >
                        <tr>
                            <td align="center">${efor.PROJECT_CODE}</td>
                            <td>${efor.PROJECT_NAME}</td>
                            <td align="center">${efor.DIC_NAME}</td>
                            <td align="center">审批中</td>
                        </tr>
                        </e:for>
                    </table>
                </div>

                <div class="eui-index-bjgs">
                    <table>
                        <tr>
                            <th width="25%">项目代码</th>
                            <th align="left">项目名称</th>
                            <th width="16%">项目类型</th>
                            <th width="12%">办理结果</th>
                        </tr>
                        <e:for var="efor" dsid="239" filterid="1" end="5" >
                            <tr>
                                <td align="center">${efor.PROJECT_CODE}</td>
                                <td>${efor.PROJECT_NAME}</td>
                                <td align="center">${efor.DIC_NAME}</td>
                                <td align="center">办结</td>
                            </tr>
                        </e:for>
                    </table>
                </div>
            </div>


        </div>

        <!-- 政策法规 -->
        <div class="eui-tit">
            <b>政策法规</b>
            <a href="<%=basePath%>contentController/list.do?moduleId=${zcfgModuleId}&currentNav=5">查看更多 &gt;</a>
        </div>
        <div class="eui-table eui-index-zcfg">
            <div class="th vt min">
                <e:for filterid="${zcfgModuleId}" end="1" var="efor" dsid="15">
                <img src="<%=basePath%>/webpage/website/project/images/zcfg-pic.png" >
                </e:for>
            </div>
            <div class="td vt">
                <div class="eui-topline">
                    <e:for filterid="${zcfgModuleId}" end="1" var="efor" dsid="2">
                    <a href="<%=path%>/contentController/view.do?contentId=${efor.tid}&currentNav=5">
                        <b>${efor.itemTitle}</b>
                        <p>${efor.content_summary}</p>
                    </a>
                    </e:for>
                </div>
                <div class="eui-list">
                    <ul>
                        <e:for filterid="${zcfgModuleId}" end="5" var="efor" dsid="2" begin="2">
                        <li><a href="<%=path%>/contentController/view.do?contentId=${efor.tid}&currentNav=5">
                            <span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
                            <e:sub str="${efor.itemTitle}" endindex="35" ellipsis="false"/>
                               </a></li>
                        </e:for>
                    </ul>
                </div>
            </div>
        </div>

        <!-- 中介服务 -->
        <div class="eui-tit">
            <b>中介服务</b>
            <a href="contentController/list.do?moduleId=625">查看更多 &gt;</a>
        </div>
        <div class="eui-list">
            <ul>
                <e:for filterid="625" end="4" var="efor" dsid="2" begin="1">
                <li><a href="<%=path%>/contentController/view.do?contentId=${efor.tid}">
                    <span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
                    <e:sub str="${efor.itemTitle}" endindex="35" ellipsis="false"/></a></li>
                </e:for>
            </ul>
        </div>

    </div>
    <!-- 主体 end -->

    <!-- 底部 -->
    <div class="eui-footer">
        <iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
    </div>
    <!-- 底部 end -->
</div>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
    $(function() {
        // banner切换
        jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
        // 选项卡切换
        jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
        jQuery(".eui-tab2").slide({titCell:".inhd li",mainCell:".inbd",delayTime:0,triggerTime: 0,trigger:"click"});
    });
</script>

</body>
</html>