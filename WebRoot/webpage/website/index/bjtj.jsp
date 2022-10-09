<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.ExecutionService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%

%>
<script type="text/javascript">
 $(function(){
   var mydate = new Date();
   var str = "" + mydate.getFullYear() + "-";
   str += (mydate.getMonth()+1) + "-";
   str += mydate.getDate() + "";
   $("#dqrq").text("截止："+str);
   $.post("webSiteController/bjtj.do",{}, function(responseText) {
	   if(resultJson!="websessiontimeout"){
		   var resultJson = $.parseJSON(responseText);
		   $("#sjzs").text(resultJson.sjzs);
		   $("#zrsj").text(resultJson.zrsj);
		   $("#bjzs").text(resultJson.bjzs);
		   $("#zrbj").text(resultJson.zrbj);
	   }
   });
  
 });
</script>
<div class="eui-bjtj tmargin16">
	<label id="dqrq"></label>
	<ul class="eui-bjtjli">		
		<li>累计收件：<span id="sjzs"></span> 件</li>
		<li>昨日收件：<span id="zrsj"></span> 件</li>
		<li>累计办结：<span id="bjzs"></span> 件</li>
		<li>昨日办结：<span id="zrbj"></span> 件</li>
	</ul>
</div>
