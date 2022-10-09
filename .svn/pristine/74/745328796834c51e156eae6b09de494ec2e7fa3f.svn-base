<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>网上调查_平潭综合实验区行政服务中心</title>

	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/hd/js/tab.js"></script>
	
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,validationegine"></eve:resources>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
<![endif]-->
<style type="text/css">
.wsdc_title{padding:30px 0; text-align:center}
.wsdc_title h1{font-size:24px; font-weight:bold}
.wsdc_title h2{font-size:12px; color:#636363; font-weight:normal; padding:16px}

.tab_wsdc{background:url(<%=path%>/webpage/website/hd/images/tab_wsdc.jpg) repeat-x bottom; height:51px; margin:0 30px;}
.tab_wsdc li{background:url(<%=path%>/webpage/website/hd/images/wsdctab.jpg) no-repeat bottom center;float:left; display:inline; margin-right:12px; width:165px; text-align:center; padding:4px 0 0 0; height:47px; line-height:47px; cursor:pointer; font-size:14px; font-weight:bold; color:#333}
.tab_wsdc p{float:right; display:inline; padding:12px 0px 0 20px; background:url(<%=path%>/webpage/website/hd/images/wsdc_icon.jpg) no-repeat 0 10px}
.tab_wsdc p a{color:#497ab4}
.tab_wsdc p a:hover{text-decoration:underline}
.tab_wsdc_on{background:url(<%=path%>/webpage/website/hd/images/wsdctabOn.jpg) no-repeat bottom center!important; color:#0084ff!important;}

.wsdc_con{margin:0 30px; border:1px solid #dfdfdf; border-top:0 none; padding:50px; font-size:14px; line-height:25px}
.topic span {
    color: #F64702;
}
.mt10 {
    margin-top: 10px;
}
</style>
</head>

<body >
<script type="text/javascript">


//获取复选框的值选中几项
function getCheckBoxCheckCount(vid)
{
	var values =document.getElementsByName(vid);
	var chkValue="";
	var count=0;
	for(var i=0;i<values.length;i++)
	{
	   if(values[i].checked)
			count++;
	}
	return count;
}
//如果有选择则返回选中的值,如果没有选择,则返回null
function getRadioValue(radioName)
{
	var i;
	var radio = document.getElementsByName(radioName);
	for(i=0;i<radio.length;i++)
	{
		if(radio[i].checked)
		{
			return radio[i].value;
		}
	}
	return null;
}
function save(){
	var flag = true;
	var str = "";
	var questions = "${osQuestions}";
	<c:if test="${osQuestions != null }">
	<c:forEach items="${osQuestions}" var="question">
		if(flag && "checkbox" == "${question.type}"){
			if(getCheckBoxCheckCount("Item_${question.questionid}") == 0){
				str = "请完成[${question.title}]后再进行提交!"
				flag = false;
			}
		}
		else if(flag && "select" == "${question.type}"){
			if(getRadioValue("Item_${question.questionid}") == null){
				str = "请完成[${question.title}]后再进行提交!"
				flag = false;
			}
		}
		else if(flag && "textarea" == "${question.type}"){
			if(!checkTextArea("Item_${question.questionid}",256)){
				str = "[${question.title}]字数不能超过256个字!"
				flag = false;
			}
		}
	</c:forEach>
	</c:if>
	var vcode = document.getElementById("vcode").value;
	if(flag && vcode == ""){
		str = "请输入验证码!";
	}
	
	if(str != ""){
		art.dialog({
			content: str,
			icon:"error",
			ok: true
		});	
		flag = true;
	}else{
		document.getElementById("saveImg").style.display = 'none';
		$.post(
			'<%=path%>/osTopicController/saveWsdc.do',
			$('#wsdcsaveFrom').serialize(),
			function(data){
				if(data.success==true){
					art.dialog({
						content: data.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					//ChangDiv('dc','tab_wsdc_on',"1",'boxDc');
					window.location.href="<%=path%>/osTopicController.do?wsdc&topicid=${osTopic.topicId }&type=1";
				}else{
					art.dialog({
						content: data.msg,
						icon:"error",
						ok: true
					});	
					document.getElementById("saveImg").style.display = '';
				}
			},
			"json"
		);
		return ;
	}
}

//判断文本域输入的内容是否超长
//返回真为正常值,
function checkTextArea(textareaName,len)
{//如果有输入非空格则返回true,否则返回false
	var myText= document.getElementsByName(textareaName)[0];
	if(myText.value.replace(/(^\s*)|(\s*$)/g,"")==="")
	{
		myText.value = '';
		return true;
	}
	myText.value = myText.value.replace(/(^\s*)|(\s*$)/g,"");
	if(myText.value.length>len){
		//alert("文本域的内容不能大于128")
		return false;
	}
	return true;
}

$(document).ready(function(){
        if("${type}" != null && "${type}" != ""){
        	ChangDiv('dc','tab_wsdc_on',"${type}",'boxDc');
        }
		changeRandPic();
});

    function changeRandPic(){
    	$("#randpic").attr({
	          "src": "<%=path %>/rand.jpg?"+Math.random()
	     });
    }
</script>

<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
<%--结束编写头部文件 --%>
<div class="container lbpadding">
		<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></div>
	<div class="detailMain clearfix">
	<div class="wsdc_title">
    	<h1>${osTopic.title }<c:if test="${osTopic.datetype == 2}"><font color="red">(已结束)</font></c:if><c:if test="${osTopic.datetype == 0}"><font color="red">(未开始)</font></c:if><c:if test="${osTopic.datetype == 1 && osTopic.state == 1}"><font color="green">(进行中)</font></c:if></h1>
    	<h2>（${osTopic.startdate }至${osTopic.enddate }）</h2>
    </div>
    
    <div class="tab_wsdc">
        <ul id="dc">
            <li onclick="ChangDiv('dc','tab_wsdc_on',0,'boxDc')" class="tab_wsdc_on">调查内容</li>
            <li onclick="ChangDiv('dc','tab_wsdc_on',1,'boxDc')">投票统计</li>
            <c:if test="${osTopic.resultcontent ==null || osTopic.resultcontent==''}">
            	<li onclick="ChangDiv('dc','tab_wsdc_on',2,'boxDc')" style="display:none">结果反馈</li>
            </c:if>
            <c:if test="${osTopic.resultcontent !=null && osTopic.resultcontent!=''}">
            	<li onclick="ChangDiv('dc','tab_wsdc_on',2,'boxDc')">结果反馈</li>
            </c:if>
        </ul>
    </div>
    
    <div class="wsdc_con" style='margin-bottom:20px;'>
    
        <div id="boxDc0" style="display:">
    <form id="wsdcsaveFrom" action="<%=path%>/osTopicController/saveWsdc.do">
            ${osTopic.content }
    		<input type="hidden" name="topicId" value="${osTopic.topicId }"/>
            <c:if test="${osQuestions != null }">
           	<c:forEach items="${osQuestions}" var="question" varStatus="qs">
            	<div class="topic">
            		<div><span><span id="block_268">${qs.index+1}、${question.title }</span></span></div>
            		
            		<c:forEach items="${question.options}" var="options" varStatus="ts">
            			<c:if test="${question.type == 'checkbox'}">
            				<div><label>
							<c:if test="${osTopic.state == 1}"><input type="checkbox" value="${options.optionid }" name="Item_${question.questionId }"/></c:if>
							&nbsp;&nbsp;${options.title }.${options.content }</label></div>
            			</c:if>
            			<c:if test="${question.type == 'select'}">
            				<div><label>
							<c:if test="${osTopic.state == 1}"><input type="radio" value="${options.optionid }" name="Item_${question.questionId }"/></c:if>
							&nbsp;&nbsp;${options.title }.${options.content }</label></div>
            			</c:if>
            			<c:if test="${question.type == 'input'}">
            				<c:if test="${ts.index == 0}">
            				<div><label><input type="text" name="Item_${question.questionId }" maxlength="128"/>&nbsp;&nbsp;</label></div>
            				</c:if>
            			</c:if>
            			<c:if test="${question.type == 'textarea'}">
            				<c:if test="${ts.index == 0}">
            				<div><textarea rows="5" cols="50" name="Item_${question.questionId }"></textarea></div>
            				</c:if>
            			</c:if>
            		</c:forEach>
            	</div>
           	</c:forEach>
            </c:if>
			<c:if test="${osTopic.datetype == 1 && osTopic.state == 1}">
				<div align="center" class="mt10">
					<div id="vcodediv" style="margin-bottom: 10px;">
					<font color="red">*</font>验证码：</td>
						
					<input name="vcode" id="vcode" type="text" style="width:105px; height: 24px;" />
					
					<img name="vc" id="randpic" style="height: 24px;margin-top: -10px;" title="点击切换验证码" alt="验证码" src="<%=path %>/rand.jpg" align="middle" onclick="changeRandPic();" style="cursor:pointer"/>
					</div>
					<p/>
				
					<!--<img id="saveImg" src="<%=path%>/webpage/website/hd/images/btn_tp.jpg" onclick="javascript:save()"/>-->
					<div class="sbtbtn" id="saveImg">
						<a class="btn2" href="javascript:save();">提  交</a>
					</div>
				<!--<c:if test="${osTopic.state == 2}">
					<img src="<%=path%>/pages/hd/images/btn_tp_end.jpg" />
				</c:if>-->
				</div>
			</c:if>
	</form>
        </div>
        
        
        <div id="boxDc1" style="display:none">
<table cellspacing="0" cellpadding="0" width="100%" align="center">

        
        <tbody>
        <c:if test="${osQuestions != null }">
        <c:forEach items="${osQuestions}" var="question" varStatus="qs">
        	<tr><td colspan="3">
        		<div class="topic">
        		<div><span><span id="block_268">${qs.index+1}、${question.title }</span></span></div>
        		<div></div></div></td></tr>
            		
            	<c:forEach items="${question.options}" var="options" varStatus="ts">
            		<c:if test="${question.type == 'checkbox'}">
            			<tr><td>
            			<c:if test="${question.count == 0}">
            				&nbsp;&nbsp;${options.title }.${options.content }(票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</td><td>
            				<img width="0 px" height="8px" src="<%=path%>/webpage/website/hd/images/vote.gif" />
            				${question.count }%
            			</c:if>
            			<c:if test="${question.count > 0}">
            				&nbsp;&nbsp;${options.title }.${options.content }(票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</td><td>
            				<img width="${options.tickets * 150/question.count}px" height="8px" src="<%=path%>/webpage/website/hd/images/vote.gif" />
            				<fmt:formatNumber value="${options.tickets * 100/question.count }" pattern="#,###.##" />%
            				
            			</c:if>
            			</td><td>
            			</td></tr>
            		</c:if>
            		<c:if test="${question.type == 'select'}">
            			<tr><td>
            			<c:if test="${question.count == 0}">
            				&nbsp;&nbsp;${options.title }.${options.content }(票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</td><td>
            				<img width="0 px" height="8px" src="<%=path%>/webpage/website/hd/images/vote.gif" />
            				${question.count }%
            			</c:if>
            			<c:if test="${question.count > 0}">
            				&nbsp;&nbsp;${options.title }.${options.content }(票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</td><td>
            				<img width="${options.tickets * 150/question.count}px" height="8px" src="<%=path%>/webpage/website/hd/images/vote.gif" />
            				<fmt:formatNumber value="${options.tickets * 100/question.count }" pattern="#,###.##" />%
            			</c:if>
            			</td><td>
            			</td></tr>
            		</c:if>
            		<c:if test="${question.type == 'input'}">
            			<c:if test="${ts.index == 0}">
            			<tr><td>
            			<div><label>&nbsp;&nbsp;(总票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</label></div>
            			</td><td>
            			</td></tr>
            			</c:if>
            		</c:if>
            		<c:if test="${question.type == 'textarea'}">
            			<c:if test="${ts.index == 0}">
            			<tr><td>
            			<div><label>&nbsp;&nbsp;(总票数:<fmt:formatNumber value="${options.tickets }" pattern="#,###" />)</label></div>
            			</td><td>
            			</td></tr>
            			</c:if>
            		</c:if>
            	</c:forEach>
        </c:forEach>
        </c:if>
</tbody></table>
        </div>
    
    
        <div id="boxDc2" style="display:none">
<div class="Custom_UnionStyle">
${osTopic.resultcontent }

</div>
        </div>
        
    </div>
	</div>
        
    </div>

    
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>

</body>
</html>