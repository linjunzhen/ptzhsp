<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>业务咨询列表_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">
    function callpage(itemList){
		$("#listContainer").html("");
		var newhtml = "<ul>";
		$.each(itemList, function(index, node){			
			newhtml += "<li><a target=\"_blank\" href=\"letterController/detail.do?entityId="+node.LETTER_ID+"\" >"+node.LETTER_TITLE+"</a></li>";
		});
		 newhtml += "</ul>";
		$("#listContainer").html(newhtml);
    }
    function queryXSQ(){
		reload();
	}
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" />
    <%--结束编写头部文件 --%>
    
     <div class="container lbpadding">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>业务咨询列表</i></div>
         <div class="bsMain1 clearfix">
    	   <form id="letterForm">
        	<table cellpadding="0" cellspacing="0" class="jdsrhtable">
            	<tr>
                	<th>信件编号：</th>
                    <td><input type="text" style="width:304px;"  maxlength="30" class="textinput" autocomplete="off" id="LETTER_CODE" name="LETTER_CODE" value="" placeholder="请输入信件编号" /></td>
                    <td rowspan="3" valign="top"><a href="javascript:void(0);" class="bsbtn2" onclick="queryXSQ();">查  询</a></td>
                </tr>
                <tr>
                	<th>信件标题：</th>
                    <td><input type="text" style="width:304px;"  maxlength="30" class="textinput" autocomplete="off" id="LETTER_TITLE" name="LETTER_TITLE" value="" placeholder="请输入信件标题" /></td>
                </tr>
                <tr>
                	<td></td>
                    <td><span class="bscolor3" id="tips"></span></td>
                </tr>
            </table>
            </form>
        </div>  
           
    	<div class="bsMain clearfix">
            <div class="bslist" id="listContainer">
			</div>
            <%--开始引入分页JSP --%>
		    <jsp:include page="../common/page.jsp" >
		    <jsp:param value="letterController/xsqxPagelist.do" name="pageURL"/>
		    <jsp:param value="callpage" name="callpage"/>
		    <jsp:param value="10" name="limitNum"/>
		    <jsp:param value="letterForm" name="pageFormId"/>
		    </jsp:include>
		    <%--结束引入分页JSP--%>
		    
        </div>
         <script type="text/javascript">
             $(function(){
                 var mydate = new Date();
                 var str = "" + mydate.getFullYear() + "-";
                 str += (mydate.getMonth()+1) + "-";
                 str += mydate.getDate() + "";
                 $("#dqrq").text("截止："+str);
                 $.post("letterController/sqtj.do",{}, function(responseText) {
                     if(resultJson!="websessiontimeout"){
                         var resultJson = $.parseJSON(responseText);
                         $(".sqTotal").text(resultJson.sqTotal);
                         $(".sqReplyTotal").text(resultJson.sqReplyTotal);
                     }
                 });

             });
         </script>
         <div class="bsMain1 clearfix">
             <span style="margin-left: 30%">累计诉求件：
                 <span class="sqTotal">10764</span> 件；累计办理完毕件：  <span  class="sqReplyTotal">10764</span> 件</span>
         </div>
     </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
