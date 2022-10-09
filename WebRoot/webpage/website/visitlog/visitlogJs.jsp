<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@page import="net.evecom.core.web.config.WebConstants"%>
<%
String statSiteId = "1";
String statServerDomain = WebConstants.getStatServerDomain();
String statDest= statServerDomain+"/stat/deal";
//String statDest= "http://192.168.6.4:8080/fzqsngStat/stat/deal";
String statCnPath = request.getAttribute("cnPath")==null?"":"&cnPath="+request.getAttribute("cnPath");
String statCnId = request.getAttribute("cnId")==null?"":"&cnId="+request.getAttribute("cnId");
String statCnType = request.getAttribute("cnType")==null?"":"&cnType="+request.getAttribute("cnType");
String statLeafId = request.getAttribute("itemCode")==null?"":"&leafId="+request.getAttribute("itemCode");
String statLeafType = request.getAttribute("typeName")==null?"":"&leafType="+request.getAttribute("typeName");
StringBuffer statParam = new StringBuffer("siteId="+statSiteId+"&dest="+statDest);
statParam.append(statCnPath);
statParam.append(statCnId);
statParam.append(statCnType);
statParam.append(statLeafId);
statParam.append(statLeafType);
if(AppUtil.getLoginMember()!=null){
String userId = AppUtil.getLoginMember().get("USER_ID")==null?"":"&userId="+AppUtil.getLoginMember().get("USER_ID");
statParam.append(userId);
}
request.setAttribute("statServerDomain", statServerDomain);
request.setAttribute("statParam", statParam.toString());
%>

<script type="text/javascript" src="${statServerDomain}/plugins/stat.js" async="async"></script>
<script type="text/javascript">
	$(document).ready(function(){
		setTimeout(function(){
			if(window._ecms_stat){
				_ecms_stat('${statParam}');
			}
        }, 1);
	});
</script>
