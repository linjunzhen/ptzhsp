<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
BusTypeService busTypeService = (BusTypeService)AppUtil.getBean("busTypeService");
List<Map<String,Object>> bmfwList = busTypeService.findByTypeCodeForWebSite("BMFW");
request.setAttribute("bmfwList", bmfwList);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style type="text/css">
	.selected{
	   color:#3188d2;
	}
	</style>
	<script type="text/javascript">
	function changeCss(c,a){
        $(c).click(function(){
            $(c).each(function(){
                $(this).removeClass(a)
            });
            $(this).addClass(a);
        })
    }
	$(document).ready(function(){
       
    });
	$(function() {
		changeCss(".sublist1 li ","sublist1On");
        $(".typeBg li a").click(function() {
            $(".typeBg li a.selected").removeClass("selected")
            $(this).addClass("selected");
        });
        if($(".sublist1 li").length>0){
            $(".sublist1 li:first a").trigger("click");
        }
    });
	function addBm(bmId){
		$(".typeBg li a.selected").removeClass("selected")
		$('#sxxz').val("");
		$('#busTypeId').val(bmId);
		reload();
	}
	function changeSXXZ(sxxz){
		$('#sxxz').val(sxxz);
		reload();
	}
	function callpage(itemList){
        $("#listContainer").html("");
        var newhtml = "<ul>";
        for(var i=0; i<itemList.length; i++){
        newhtml += "<li><a target=\"_blank\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" >"+itemList[i].ITEM_NAME+"</a></li>"
        } 
        newhtml += "</ul>";
        $("#listContainer").html(newhtml);
    }
    </script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp?currentNav=qlqd" />
    <%--结束编写头部文件 --%>
    <div class="container">
     <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>权责清单</i></div>
        <div class="bsMain1 clearfix">
            <div class="bsLeft">
                <div class="subTitle"><img src="webpage/website/common/images/bsIcon14.png" /> 办 事 部 门</div>
                <form id="qlqdform">
                    <input type="hidden" id="busTypeId" name="busTypeIds" value=""/> 
                    <input type="hidden" id="sxxz" name="SXXZ" value=""  />
                </form>
                <div class="sublist1">
                    <ul>
                        <c:forEach items="${bmfwList}" var="bmfwinfo">
                        <li>
                        <a href="javascript:void(0);" onclick="addBm('${bmfwinfo.TYPE_ID}')" >${bmfwinfo.TYPE_NAME}</a>
                        </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="bsRight">
                <div class="typeBg">
                    <span>类 型</span>
                    <ul>
                        <li><a id="XK" onclick="changeSXXZ('XK')" href="javascript:void(0);">行政许可</a></li>
                        <li><a id="GF" onclick="changeSXXZ('GF')" href="javascript:void(0);">公共服务</a></li>
                    </ul>
                </div>
                <div class="list4" id="listContainer">
                </div>
                <%--开始引入分页JSP --%>
                <jsp:include page="../common/page.jsp" >
                <jsp:param value="serviceItemController/qlqdlist.do" name="pageURL"/>
                <jsp:param value="callpage" name="callpage"/>
                <jsp:param value="10" name="limitNum"/>
                <jsp:param value="qlqdform" name="pageFormId"/>
                </jsp:include>
                <%--结束引入分页JSP--%>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
