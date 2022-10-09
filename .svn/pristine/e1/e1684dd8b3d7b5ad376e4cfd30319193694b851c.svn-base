<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>网上调查_平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,validationegine"></eve:resources>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript"> 
	function callpage(itemList){
		$("#wsdcList").html("");
		var newhtml = "<ul>";
		$.each( itemList, function(index, node){
			newhtml +='<li>';
			newhtml +='<table cellpadding="0" cellspacing="0" class="tableli">';
			newhtml +='<tr>';
            newhtml +='<td colspan="4">';		
			
			if(node.DATETYPE==2){
				newhtml +='<a href="<%=path%>/osTopicController.do?wsdc&topicid='+node.TOPICID+'" class="bold">'+node.TITLE+'</a><span>【已结束】</span>';
			}else{
				newhtml +='<a href="<%=path%>/osTopicController.do?wsdc&topicid='+node.TOPICID+'" class="nowab">'+node.TITLE+'</a>';
			}
			newhtml +='</td>';
            newhtml +='</tr>';	
			var content = "";
			if(null!=node.CONTENT&&undefined!=node.CONTENT){
				content = delHtmlTag(node.CONTENT);
				if(content.length>100){
					content = content.substring(0,100);
				}
			}
			newhtml +='<tr><td valign="top" colspan="4" ><b>主题概况：</b>'+content+'<a href="<%=path%>/osTopicController.do?wsdc&topicid='+node.TOPICID+'" class="bscolor3">[查看详细]</a></td>';		
            newhtml +='</tr>';
			newhtml +='<tr><td>开始时间：'+node.STARTDATE+'</td>';
            newhtml +='<td>结束时间：'+node.ENDDATE+'</td>';
            newhtml +=' <td style="width: 104px;"><a href="<%=path%>/osTopicController.do?wsdc&topicid='+node.TOPICID+'&type=1" class="bscolor3">投票统计</a></td>';
            newhtml +='<td style="width: 104px;">';
			if(null!=node.RESULTCONTENT&&undefined!=node.RESULTCONTENT){
				newhtml +='<a href="<%=path%>/osTopicController.do?wsdc&topicid='+node.TOPICID+'&type=2">结果反馈</a>';
			}
			newhtml +='</td>';
            newhtml +='</tr>';
			newhtml +='</table>';
			newhtml +='</li>';
		});
		newhtml += "</ul>";
		$("#wsdcList").html(newhtml);
	}
	function delHtmlTag(str){
		return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	} 
	</script>
</head>

<body style="word-break: break-all;">
	<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>网上调查</i></div>
    	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
                    	<li><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>
                        <li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
            	<div class="xxlist" id="wsdcList">                	
                </div>
                <div class="page">
					<%--开始引入分页JSP --%>
					<jsp:include page="../common/page.jsp" >
					<jsp:param value="osTopicController/pagelist.do" name="pageURL"/>
					<jsp:param value="callpage" name="callpage"/>
					<jsp:param value="5" name="limitNum"/>
					</jsp:include>
					<%--结束引入分页JSP--%>
				</div>
            </div>
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
