<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<!--[if lte IE 6]>
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script>
	<script type="text/javascript">
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script>
	<![endif]-->
	<eve:resources loadres="apputil,autocomplete"></eve:resources>
	<script type="text/javascript">
	$(document).ready(function(){
        $(location).attr('href',"http://ptsy.zwfw.fujian.gov.cn");
    });
    </script>
	<%--function loadAutoCompleteDatas() {--%>
	    <%--$.post("webSiteController.do?loadbsSearch", {--%>
	    <%--}, function(responseText, status, xhr) {--%>
	        <%--var resultJson = $.parseJSON(responseText);--%>
	        <%--$("#AutoCompleteInput").autocomplete(--%>
	                <%--resultJson,--%>
	                <%--{--%>
	                    <%--matchContains : true,--%>
	                    <%--formatItem : function(row, i, max) {--%>
	                        <%--//下拉框显示--%>
	                        <%--return "<div>" + row.ITEM_NAME+"</div>";--%>
	                    <%--},--%>
	                    <%--formatMatch : function(row) {--%>
	                        <%--//查询条件--%>
	                        <%--return row.ITEM_NAME+row.PINYIN;--%>
	                    <%--},--%>
	                    <%--formatResult : function(row, i, max) {--%>
	                        <%--//显示在文本框中--%>
	                        <%--return row.ITEM_NAME;--%>
	                    <%--}--%>
	                <%--});--%>
	        <%--});--%>
	    <%--}--%>
	<%--function openNewWindow(){--%>
		<%--window.open("<%=path%>/webSiteController/allItemSearch.do?itemName="+encodeURIComponent($("#AutoCompleteInput").val()));--%>
	<%--}--%>

	<%--function bskscx(){--%>
		<%--var sbh = $("#sbh").val().Trim();--%>
		<%--var cxmm = $("#cxmm").val().Trim();--%>
		<%--if(sbh==""||sbh=="请输入申报号"||sbh==null){--%>
			<%--alert("请输入申报号");--%>
			<%--$("#sbh").focus();--%>
			<%--return ;--%>
		<%--}--%>
		<%--if(cxmm==""||cxmm=="请输入查询密码"||null==cxmm){--%>
            <%--alert("请输入查询密码");--%>
            <%--$("#cxmm").focus();--%>
            <%--return ;--%>
        <%--}--%>
		<%--window.open("<%=path%>/webSiteController/view.do?navTarget=website/bsjdcx/bsjdcx&sbh="+sbh+"&cxmm="+$.base64.encode(cxmm));--%>
	<%--}--%>
	<%--String.prototype.Trim = function()--%>
    <%--{--%>
        <%--return this.replace(/(^\s*)|(\s*$)/g, "");--%>
    <%--};--%>
	<%--</script>--%>
  <%--</head>--%>

  <%--<body class="bsbody">--%>
    <%--&lt;%&ndash;开始编写头部文件 &ndash;%&gt;--%>
    <%--<jsp:include page="./head.jsp?currentNav=sy" />--%>
    <%--&lt;%&ndash;结束编写头部文件 &ndash;%&gt;--%>

     <%--<div class="container">--%>
    	<%--<div class="bsMain clearfix">--%>
        	<%--<div class="bsSearch">--%>
                <%--<input placeholder="请输入办事事项名称、目录名称或事项编码" type="text" id="AutoCompleteInput"--%>
                <%--onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"/>--%>
                <%--<a href="javascript:void(0);" onclick="openNewWindow();" >检 索</a>--%>
            <%--</div>--%>
            <%--<div class="bsBmflow">--%>
                <%--<div class="bsMainL">--%>
                    <%--<div class="bstitle">办事快速查询</div>--%>
                    <%--<table cellpadding="0" cellspacing="0" class="bstable">--%>
                        <%--<tr>--%>
                            <%--<th>申报号：</th>--%>
                            <%--<td><input type="text" maxlength="30" style="width:138px;" id="sbh" class="textinput"--%>
                            <%--onfocus="if(this.value=='请输入申报号'){this.value='';}this.style.color='#999';"--%>
                            <%--value="请输入申报号" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>查询密码：</th>--%>
                            <%--<td><input type="password" maxlength="8" style="width:138px;" id="cxmm"  class="textinput" placeholder="请输入查询密码"--%>
                              <%--onfocus="if(this.value=='请输入查询密码') {this.value='';}this.style.color='#999';" onblur="if(this.value==''){this.value='';this.style.color='#999';}" value=""/></td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                    <%--<div class="btncenter"><a href="javascript:void(0);" class="btn2" onclick="bskscx();">提  交</a></div>--%>
                <%--</div>--%>
                <%--<div class="bsMainC">--%>
                    <%--<div class="bstitle"><span class="more"><a href="contentController/list.do?moduleId=101" target="_blank">更多>></a></span>公示公告</div>--%>
                    <%--<div class="list3">--%>
                        <%--<ul>--%>
						<%--<e:for filterid="101" end="5" var="efor" dsid="2">--%>
							<%--<li><span>[<fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate>]</span><a target="_blank" href="contentController/view.do?contentId=${efor.tid}" title="${efor.itemTitle}"><e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="20" ></e:sub></a></li>--%>
						<%--</e:for>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;开始引入我要办的JSP &ndash;%&gt;--%>
                <%--<jsp:include page="./rdbs.jsp" />--%>
                <%--&lt;%&ndash;结束引入我要办的JSP &ndash;%&gt;--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="bsMainbtm">--%>
    	<%--<div class="container">--%>
        	<%--<div class="bsMbL">--%>
            	<%--<div class="bsMbox">--%>
                	<%--<div class="bsMbtitle"><img src="webpage/website/common/images/bsIcon.png" /> 我要办</div>--%>
                    <%--<div class="bsMli">--%>
                    	<%--<a href="webSiteController/view.do?navTarget=website/grbs/grbs">个人办事</a><span>|</span><a href="webSiteController/view.do?navTarget=website/frbs/frbs">法人办事</a><br />--%>
                        <%--<a href="webSiteController/view.do?navTarget=website/bmfw/bmfw">部门服务</a><span>|</span><a href="webSiteController/view.do?navTarget=website/bsdt/sceneNaviIndex">场景式导航</a><br />--%>
                        <%--<a href="webSiteController/view.do?navTarget=website/index/newtzxmsb">投资项目申报</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="bsMbox">--%>
                	<%--<div class="bsMbtitle"><img src="webpage/website/common/images/bsIcon1.png" /> 我要查</div>--%>
                    <%--<div class="bsMli">--%>
                    	<%--&lt;%&ndash;<a href="webSiteController/view.do?navTarget=website/bsjdcx/bsjdcx">办事进度查询</a><br />--%>
                        <%--&ndash;%&gt;<a href="webSiteController/view.do?navTarget=website/bszn/bszn">办事指南</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="bsMbR">--%>
            	<%--<div class="bsMbox">--%>
                	<%--<div class="bsMbtitle"><img src="webpage/website/common/images/bsIcon2.png" /> 我要看</div>--%>
                    <%--<div class="bsMli">--%>
                    	<%--<a href="webSiteController/view.do?navTarget=website/bsdt/cjwtlb">常见问题</a><span>|</span>--%>
                    	<%--<a href="webSiteController/view.do?navTarget=website/bsdt/bgxzlb">表格下载</a><br />--%>
                        <%--<a href="contentController/list.do?moduleId=142" target="_blank">信息公开</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="bsMbox">--%>
                	<%--<div class="bsMbtitle"><img src="webpage/website/common/images/bsIcon3.png" /> 我要问</div>--%>
                    <%--<div class="bsMli">--%>
                    	<%--<a href="http://pi12345.fujian.gov.cn/">咨询</a><span>|</span>--%>
                    	<%--<a href="http://pi12345.fujian.gov.cn/">举报</a><br />--%>
                        <%--<a href="http://pi12345.fujian.gov.cn/">投诉</a><span>|</span>--%>
                        <%--<a href="http://pi12345.fujian.gov.cn/">建议</a><br />--%>
                        <%--<a href="http://pi12345.fujian.gov.cn/">感谢</a><span>|</span>--%>
                        <%--<a href="http://www.pingtan.gov.cn/site/main/info/gov_sugbox_search.jsp" target="_blank">公开信息意见箱</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

	<%--&lt;%&ndash;开始编写尾部文件 &ndash;%&gt;--%>
	<%--<jsp:include page="../index/foot.jsp" />--%>
	<%--&lt;%&ndash;结束编写尾部文件 &ndash;%&gt;--%>
//  </body>
//</html>
