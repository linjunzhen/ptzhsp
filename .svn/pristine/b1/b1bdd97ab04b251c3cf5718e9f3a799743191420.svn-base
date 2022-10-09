<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>自助取号系统</title>
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/style.css">
    <link rel="stylesheet" type="text/css" href="webpage/callYctb/takeNo/css/idangerous.swiper.css">
    <link rel="stylesheet" type="text/css" href="webpage/callYqyz/takeNo/css/style.css">
    <script type="text/javascript" src="webpage/callYctb/takeNo/js/jquery.min.js"></script>

</head>

<script>
    //办件类型
    var bjlx = "${serviceItem.SXLX}";
    if(bjlx=="1"){
        $("#bjlx").html($("#bjlx").html()+"即办件");
    }else if(bjlx=="2"){
        $("#bjlx").html($("#bjlx").html()+"普通件");
    }else if(bjlx=="3"){
        $("#bjlx").html($("#bjlx").html()+"特殊件");
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

</script>

<body class="bg-none macw" ondragstart="return false" oncontextmenu="return false" onselectstart="return false">

<div class="eui-title">
    <a class="back" href="javascript:void(0);" onclick="backToParent();"><img src="webpage/callYctb/takeNo/images/back.png"></a>
    目前仅支持试点业务！
</div>

<div class="eui-sx-qrcode" style="height: 1675px; overflow-y: auto;">
    <div class="eui-tit">基本信息</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>事项目录名称</th>
                <td colspan="3">${serviceItem.CATALOG_NAME}</td>
            </tr>
            <tr>
                <th>事项目录编号</th>
                <td colspan="3">${serviceItem.CATALOG_CODE}</td>
            </tr>
            <tr>
                <th>事项名称</th>
                <td>${serviceItem.ITEM_NAME}</td>
                <th>事项属地</th>
                <td>${serviceItem.sxsx}</td>
            </tr>
            <tr>
                <th>唯一码(事项编号)</th>
                <td>${serviceItem.CODE}</td>
                <th>事项编码</th>
                <td>${serviceItem.ITEM_CODE}</td>
            </tr>
            <tr>
                <th>事项性质</th>
                <td>${serviceItem.SXXZ}</td>
                <th>办件类型</th>
                <td id="bjlx"></td>
            </tr>
            <tr>
                <th>主办处室</th>
                <td>${serviceItem.ZBCS}</td>
                <th>实施单位</th>
                <td>${serviceItem.IMPL_DEPART}</td>
            </tr>
            <tr>
                <th>联系人</th>
                <td colspan="3">${serviceItem.LXR}</td>
            </tr>
            <tr>
                <th>联系电话</th>
                <td>${serviceItem.LXDH}</td>
                <th>监督电话</th>
                <td>${serviceItem.JDDH}</td>
            </tr>
            <tr>
                <th>关键词</th>
                <td>${serviceItem.KEYWORD}</td>
                <th>数量限制说明</th>
                <td>${serviceItem.SLXZSM}</td>
            </tr>
            <tr>
                <th>备注说明</th>
                <td colspan="3">${serviceItem.BZSM}</td>
            </tr>
        </table>
    </div>
    <div class="eui-tit">面向对象</div>
    <div class="eui-con">
        <table>
            <c:forEach items="${serviceItem.busTypenames}" var="names" varStatus="ns">
                <tr>
                    <th>${names.PNAME}:</th>
                    <td colspan="3">${names.TYPE_NAME}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="eui-tit">时限配置</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>承诺时限</th>
                <td colspan="3">${serviceItem.CNQXGZR}</td>
            </tr>
            <tr>
                <th>法定时限</th>
                <td colspan="3">${serviceItem.FDSXGZR}</td>
            </tr>
            <tr>
                <th>承诺时限说明</th>
                <td colspan="3">${serviceItem.CNQXSM}</td>
            </tr>
            <tr>
                <th>法定时限说明</th>
                <td colspan="3">${serviceItem.FDQX}</td>
            </tr>
            <tr>
                <th>外网预审时限</th>
                <td colspan="3">${serviceItem.PREDAY}</td>
            </tr>
            <tr>
                <th>入驻办事大厅方式</th>
                <td colspan="3" id="rzbsdtfs"></td>
            </tr>
            <c:if test="${serviceItem.RZBSDTFS==in02}">
                <tr>
                    <th>群众办事网址</th>
                    <td colspan="3">${serviceItem.APPLY_URL}</td>
                </tr>
            </c:if>
            <tr>
                <th>去现场次数最多</th>
                <td colspan="3">${serviceItem.SBFSQXCCS}</td>
            </tr>
            <tr>
                <th>特殊环节</th>
                <td colspan="3">${serviceItem.tshj}</td>
            </tr>
        </table>
    </div>
    <div class="eui-tit">收费配置</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>是否收费</th>
                <td colspan="3" id="sfsf"></td>
            </tr>
            <c:if test="${serviceItem.SFSF==1}">
                <tr>
                    <th>收费方式</th>
                    <td colspan="3" id="sffs">${serviceItem.sffs}</td>
                </tr>
                <tr>
                    <th>收费标准及依据</th>
                    <td colspan="3">${serviceItem.SFBZJYJ}</td>
                </tr>
            </c:if>
        </table>
    </div>
    <div class="eui-tit">承接信息</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>是否已承接</th>
                <td colspan="3" id="isUndertake"></td>
            </tr>
            <c:if test="${serviceItem.IS_UNDERTAKE==0}">
                <tr>
                    <th>来函名称</th>
                    <td colspan="3">${serviceItem.WCJLHMC}</td>
                </tr>
                <tr>
                    <th>来函文号</th>
                    <td colspan="3">${serviceItem.WCJLHWH}</td>
                </tr>
            </c:if>
        </table>
    </div>
    <div class="eui-tit">办公指引</div>
    <div class="eui-con">
        <table>
            <tr>
            <th>办理窗口</th>
            <td colspan="3">${serviceItem.SSZTMC}</td>
        </tr>
            <tr>
                <th>对外办公时间</th>
                <td colspan="3">${serviceItem.BGSJ}</td>
            </tr>
            <tr>
                <th>办理地点</th>
                <td colspan="3">${serviceItem.BLDD}</td>
            </tr>
            <tr>
                <th>交通指引</th>
                <td colspan="3">${serviceItem.TRAFFIC_GUIDE}</td>
            </tr>
            <tr>
                <th>网上办理方式</th>
                <td colspan="3">${serviceItem.WSSQFS}</td>
            </tr>
        </table>
    </div>
    <div class="eui-tit">申请方式</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>申请方式</th>
                <td colspan="3">${serviceItem.ckcString}</td>
            </tr>
        </table>
    </div>
    <div class="eui-tit">依据条件</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>法定依据</th>
                <td colspan="3">${serviceItem.XSYJ}</td>
            </tr>
            <tr>
                <th>申请条件</th>
                <td colspan="3">${serviceItem.SQTJ}</td>
            </tr>
        </table>
    </div>
    <div class="eui-tit">申请材料</div>
    <div class="eui-con">
        <table>
            <c:forEach items="${serviceItem.applyMaters}" var="maters" varStatus="vs">
                <tr>
                    <td colspan="2">
                        <span><b>${vs.index+1}、</b></span>
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
        </table>
    </div>
    <div class="eui-tit">办理结果信息</div>
    <div class="eui-con">
        <table>
            <tr>
                <th>办理结果</th>
                <td colspan="3">${serviceItem.finishType}</td>
            </tr>
            <tr>
                <th>获取方式</th>
                <td colspan="3" id="finishGettype">${serviceItem.finishGettype}</td>
            </tr>
            <tr>
                <th>结果获取说明</th>
                <td colspan="3">${serviceItem.FINISH_INFO}</td>
            </tr>
        </table>
    </div>

</div>



<script type="text/javascript">

    function backToParent() {
        var url = encodeURI("callYqyzController/toMaterChooseMacW.do?roomNo=${roomNo}&ifMaterPrint=${ifMaterPrint}&departId=${departId}&zzType=${zzType}&itemCode=${itemCode}&itemName=${itemName}&defKey=${defKey}&businessCode=${businessCode}&businessName=${businessName}");
        window.parent.document.getElementById('takeFrame').src=url;
    }
</script>
</body>
</html>
