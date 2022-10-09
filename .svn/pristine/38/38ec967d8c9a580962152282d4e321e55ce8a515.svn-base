<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil,autocomplete"></eve:resources>
	<script type="text/javascript">
	function TabHeads(c,a,b){
        $(c).hover(function(){
            $(c).each(function(){
                $(this).removeClass(a)
            });
            $(b).each(function(){
                $(this).hide()
            });
            $(this).addClass(a);
            var d=$(c).index(this);
            $(b).eq(d).each(function(){
                $(this).show();
            });
        })
    }
	$(document).ready(function(){
      //加载自动补全数据
        loadAutoCompleteDatas();
    });
	function loadAutoCompleteDatas() {
	    $.post("webSiteController.do?loadbsSearch", {
	    }, function(responseText, status, xhr) {
	        var resultJson = $.parseJSON(responseText);
	        $("#AutoCompleteInput").autocomplete(
	                resultJson,
	                {
	                    matchContains : true,
	                    formatItem : function(row, i, max) {
	                        //下拉框显示
	                        return "<div>" + row.ITEM_NAME+"</div>";
	                    },
	                    formatMatch : function(row) {
	                        //查询条件
	                        return row.ITEM_NAME+row.PINYIN;
	                    },
	                    formatResult : function(row, i, max) {
	                        //显示在文本框中
	                        return row.ITEM_NAME;
	                    }
	                });
	        });
	    }
	function jskzx(){
		//list加载后完成
	}
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
    	<div class="bsMain clearfix">
        	<div class="bsSearch">
        	   <form id="bsForm" >
                <input type="text" id="AutoCompleteInput" value="${itemName}" name="ITEM_NAME"
                onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"/>
                <a href="javascript:void(0);" onclick="reload();">检 索</a>
               </form>
            </div>
           <%--开始映入列表页 --%>
            <jsp:include page="../common/bslist.jsp" />
            <%--开始映入列表页 --%>
            
            <%--开始引入分页JSP --%>
            <jsp:include page="../common/page.jsp" >
            <jsp:param value="serviceItemController/allItemList.do" name="pageURL"/>
            <jsp:param value="callpage" name="callpage"/>
            <jsp:param value="10" name="limitNum"/>
            <jsp:param value="bsForm" name="pageFormId"/>
            </jsp:include>
            <%--结束引入分页JSP--%>
            
        </div>
    </div>
    
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
