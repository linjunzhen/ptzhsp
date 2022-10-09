<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<body>
	
	<div class="eui-main">
		<!-- 头部 -->
		<jsp:include page="head.jsp" ></jsp:include>
		<!-- 头部 end -->

		<!-- 主体 -->
		<e:obj filterid="${content.ctid}" var="eobj" dsid="4">
		<div class="eui-con">
			
			<div class="eui-detail">
				<div class="crumb"><a href="<%=basePath%>projectWebsiteController.do?index">首页</a>&gt;<a href="<%=basePath%>contentController/list.do?moduleId=${zcfgModuleId}&currentNav=5">政策法规</a></div>
				<div class="card">

					<div class="hd">
						<strong>${eobj.content_title}</strong>
						<span>信息来源：${eobj.comefrom}    &nbsp; &nbsp; &nbsp; &nbsp;发布时间： <e:sub str="${eobj.release_time}" endindex="5" ellipsis="false"/>&nbsp;</span>
					</div>
					<div class="bd">
							${eobj.CONTENT_TEXT}
						<br/><br/>
						<ul>
							<e:for filterid="${eobj.tid}" end="100" var="efor" dsid="13">
								<li style="margin-top: 5px;">
									附件${efor.rownum_}:<a style="color: blue;" href="${_file_Server}${efor.filepath}" title="${efor.filename}">
										${efor.filename}
								</a>
								</li>
							</e:for>
						</ul>
					</div>

				</div>
			</div>
			
		</div>
		</e:obj>
		<!-- 主体 end -->

		<!-- 底部 -->
		<div class="eui-footer">
			<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
		</div>
		<!-- 底部 end -->
	</div>
	
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
	
</body>
</html>