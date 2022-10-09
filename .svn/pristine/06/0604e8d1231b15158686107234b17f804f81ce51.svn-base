<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String typeId = request.getParameter("typeId");
request.setAttribute("typeId", typeId);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/base64-1.0/jquery.base64.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil"></eve:resources>
	<script type="text/javascript">
    function setBusTypeId(typeId){
    	$("#busTypeId").val(typeId);
    	changeBszn();
    	changeBgxz();
    	changeCjwt();
    	changeBszx();
    }
    function changeBszn(){
    	var result = {};
    	result["page"]="1";
        result["rows"]="5";
    	result["busTypeIds"] = $("#busTypeId").val();
    	result["busType"] = "grbs";
    	$.post("serviceItemController.do?pagelist",result, function(resultJson) {
            if(resultJson!="websessiontimeout"){
            	var itemList = resultJson.rows;
            	$("#bszn").html("");
                var newhtml = "<ul>";
                for(var i=0; i<itemList.length; i++){
                newhtml += "<li>·<a target=\"_blank\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" title=\""+itemList[i].ITEM_NAME
                		+"\" >";
                		if(itemList[i].ITEM_NAME.length>18){
                			newhtml += itemList[i].ITEM_NAME.substr(0,18)+"...</a>";
                		}else{
                			newhtml += itemList[i].ITEM_NAME+"</a>";
                		}
                }
                newhtml += "</ul>";
                $("#bszn").html(newhtml);
            }
        });
    }
    function changeBgxz(){
        var result = {};
        result["page"]="1";
        result["rows"]="5";
        result["busTypeIds"] = $("#busTypeId").val();
        $.post("applyMaterController/bgxzPagelist.do",result, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                var itemList = resultJson.rows;
                $("#bgxz").html("");
                var newhtml = "<ul>";
                for(var i=0; i<itemList.length; i++){
                	newhtml += "<li>·<a  href=\"javascript:AppUtil.downLoadFile('"+itemList[i].MATER_PATH+"');\" title=\""+itemList[i].MATER_NAME+
                	"【"+itemList[i].ITEM_NAME+"】"+"\" >";
                var t =itemList[i].MATER_NAME+"【"+itemList[i].ITEM_NAME+"】";
	                if(t.length>18){
	                	newhtml += t.substr(0,18)+"...</a></li>";
	                }else{
	                	newhtml += itemList[i].MATER_NAME+"【"+itemList[i].ITEM_NAME+"】</a></li>";
	                }
                }
                newhtml += "</ul>";
                $("#bgxz").html(newhtml);
            }
        });
    }
    function changeCjwt(){
        var result = {};
        result["page"]="1";
        result["rows"]="5";
        result["busTypeIds"] = $("#busTypeId").val();
        $.post("commonProblemController/cjwtPagelist.do",result, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                var itemList = resultJson.rows;
                $("#cjwt").html("");
                var newhtml = "<ul>";
                for(var i=0; i<itemList.length; i++){
                	newhtml += "<li><a target=\"_blank\" href=\"commonProblemController/cjwtDetail.do?entityId="+itemList[i].PROBLEM_ID+"\" >";
                var t =itemList[i].PROBLEM_TITLE+"【"+itemList[i].ITEM_NAME+"】";
                    if(t.length>18){
                        newhtml += t.substr(0,18)+"...</a></li>";
                    }else{
                        newhtml += +itemList[i].PROBLEM_TITLE+
                        "【"+itemList[i].ITEM_NAME+"】</a></li>";
                    }
                }
                newhtml += "</ul>";
                $("#cjwt").html(newhtml);
            }
        });
    }
    function changeBszx(){
    	var result = {};
        result["page"]="1";
        result["rows"]="5";
        result["busTypeIds"] = $("#busTypeId").val();
        $.post("consultController/bsznLstdlist.do",result, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                var itemList = resultJson.rows;
                $("#bszx").html("");
                var newhtml = "<ul>";
                for(var i=0; i<itemList.length; i++){
                newhtml += "<li>·<a target=\"_blank\" href=\"consultController/detail.do?entityId="+itemList[i].CONSULT_ID+"\" title=\""+itemList[i].CONSULT_TITLE
                        +"\" >";
                        if(itemList[i].CONSULT_TITLE.length>18){
                            newhtml += itemList[i].CONSULT_TITLE.substr(0,18)+"...</a>";
                        }else{
                            newhtml += itemList[i].CONSULT_TITLE+"</a>";
                        }
                }
                newhtml += "</ul>";
                $("#bszx").html(newhtml);
            }
        });
    }
    function openZxbs(){
    	 window.open("<%=path%>/webSiteController/view.do?navTarget=website/grbs/grbs&sybusTypeId="+$("#busTypeId").val());
    }
    function openBszn(){
        window.open("<%=path%>/webSiteController/view.do?navTarget=website/bszn/bszn&sybusTypeId="+$("#busTypeId").val());
   }
   $(function(){
	   changeBszn();
       changeBgxz();
       changeCjwt();
       changeBszx();
       if("${typeId}"!=""){
           $("#"+"${typeId}").trigger("click");
       }
   });
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>绿色通道</i></div>
        <div class="bsMain1 clearfix">
            <div class="greenOL"> 
                <div class="greenOLi">
                    <div class="greenOt"><img src="webpage/website/common/images/green.png" /></div>
                    <input type="hidden" id="busTypeId" name="busTypeIds" value="4028818c512273e7015122dcad0c002a" /> 
                    <ul>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dcad0c002a')" id="4028818c512273e7015122dcad0c002a">高校毕业生</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dde52b002f')" id="4028818c512273e7015122dde52b002f">儿童青少年</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dcd4d7002b')" id="4028818c512273e7015122dcd4d7002b">人 才</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122de05960030')" id="4028818c512273e7015122de05960030">妇 女</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dcffec002c')" id="4028818c512273e7015122dcffec002c">老年人</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122de3e710031')" id="4028818c512273e7015122de3e710031">农 民</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dd334b002d')" id="4028818c512273e7015122dd334b002d">残疾人</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122de922c0032')" id="4028818c512273e7015122de922c0032">外国人</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122dd8de8002e')" id="4028818c512273e7015122dd8de8002e">特困家庭</a></li>
                        <li><a href="javascript:void(0)" onclick="setBusTypeId('4028818c512273e7015122df1ff90033')" id="4028818c512273e7015122df1ff90033">港澳台侨</a></li>
                    </ul>
                </div>
                <div class="greenOimg">
                    <div class="greenCimg"><p>高校毕业生</p></div>
                    <div class="greenCimg1"><p>儿童青少年</p></div>
                    <div class="greenCimg2"><p>人 才</p></div>
                    <div class="greenCimg3"><p>妇 女</p></div>
                    <div class="greenCimg4"><p>老年人</p></div>
                    <div class="greenCimg5"><p>农 民</p></div>
                    <div class="greenCimg6"><p>残疾人</p></div>
                    <div class="greenCimg7"><p>外国人</p></div>
                    <div class="greenCimg8"><p>特困家庭</p></div>
                    <div class="greenCimg9"><p>港澳台侨</p></div>
                </div>
            </div>
            <div class="greenOR">
                <div class="greenORt">
                    <ul>
                        <li><a href="javascript:void(0)" onclick="openZxbs()">在线办理</a></li>
                        <li><a href="webSiteController/view.do?navTarget=website/hd/zxhd" target="_blank">在线咨询</a></li>
                        <li><a href="webSiteController/view.do?navTarget=website/bmfw/bmfw" target="_blank">服务机构</a></li>
                    </ul>
                </div>
                <div class="greenORb">
                    <div class="greentitle">
                        <a href="javascript:void(0)" onclick="openBszn()" class="more">更多>></a>
                        <h3>办事指南</h3>
                    </div>
                    <div class="greenLi" id="bszn">
                    </div>
                </div>
            </div>
        </div>
        <div class="bsMain1 clearfix" style="height: 211px;">
            <div class="greenWidth">
                <div class="greentitle">
                    <a href="webSiteController/view.do?navTarget=website/bsdt/bgxzlb" target="_blank" class="more">更多>></a>
                    <h3>表格下载</h3>
                </div>
                <div class="greenLi" id="bgxz">
                </div>
            </div>
            <div class="greenWidth Lmargin30">
                <div class="greentitle">
                    <a href="webSiteController/view.do?navTarget=website/bsdt/cjwtlb" target="_blank" class="more">更多>></a>
                    <h3>常见问题</h3>
                </div>
                <div class="greenLi" id="cjwt">
                </div>
            </div>
            <div class="greenWidth Lmargin30">
                <div class="greentitle">
                    <a href="webSiteController/view.do?navTarget=website/hd/ywzxList" target="_blank" class="more">更多>></a>
                    <h3>在线咨询</h3>
                </div>
                <div class="greenLi" id="bszx">
                </div>
            </div>
        </div>
    </div>
	<script type="text/javascript">jQuery(".greenOL").slide({titCell:".greenOLi li",mainCell:".greenOimg",trigger:"click"})</script>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
