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
		<jsp:include page="head.jsp"  flush="true"></jsp:include>
		<!-- banner -->
		<div class="eui-banner-s"></div>
		<!-- banner end -->
		
		<!-- 主体 -->
		<div class="eui-con">


			<div class="eui-tit round line">
				<b>下载专区<small>DOWNLOAD ZONE</small></b>
			</div>

			<div class="eui-list-page">
				<div class="eui-list">
					<ul class="eui-download"  id="wraper">

					</ul>
				</div>

				<div class="eui-page pager clearfix" id="pager" style="margin-left: 150px;">

				</div>
			</div>



		</div>
		<!-- 主体 end -->
		
		<!-- 底部 -->
		<div class="eui-footer">
			<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
		</div>
		<!-- 底部 end -->
	</div>

	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>


	<!--分页开始-->
	<input name="totalData"  hidden="hidden" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/page.css">
	<script src="<%=basePath%>/webpage/website/project/js/jquery.z-pager.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$("#pager").zPager({
			pageData:10,
			htmlBox: $('#wraper'),
			btnShow: true,
			ajaxSetData: false
		});

		function currentPage(currentPage){
			var start=(currentPage-1)*10;
			var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start='+start+
					'&paras=:${downFileModuleId}'+'&limit=10&dsid=235';
			url=encodeURI(url);
			$.ajax({
				url: url,
				type: 'post',
				async: false,//此处必须是同步
				dataType: 'json',
				success: function (data) {
					var result=JSON.parse(data.jsonString);
					var total=result.total;
					$("input[name='totalData']").val(total)
					var rows=result.data;
					var html="";
					for(var i=0; i<rows.length; i++){
						var itemtitle=rows[i].ITEMTITLE;
						var tid=rows[i].TID;
						var itemReldate=rows[i].RELEASE_TIME;
						var linkurl=rows[i].LINKURL;
						html=html+"<li class=\"eui-flex\">\n" +
								"\t\t\t\t\t\t\t<span class=\"flex1\">• "+itemtitle+"</span>\n" +
								"\t\t\t\t\t\t\t<span>"+itemReldate+"</span>\n" +
								"\t\t\t\t\t\t\t<a href=\""+linkurl+"\">下载</a>\n" +
								"\t\t\t\t\t\t</li>";
					}
					$("#wraper").html(html);
				}
			});
			/*
                触发页码数位置： Page/js/jquery.z-pager.js 64行
            */
			console.log("当前页码数：" + currentPage);
		}
		function settotal(){
			var keyWord=$("input[name='keyWord']").val();

			var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start=0'+
					'&paras=:${downFileModuleId}'+'&limit=10&dsid=235';
			url=encodeURI(url);
			$.ajax({
				url: url,
				type: 'post',
				async: false,//此处必须是同步
				dataType: 'json',
				success: function (data) {
					var result=JSON.parse(data.jsonString);
					var total=result.total;
					$("input[name='totalData']").val(total)
				}
			});
		}
		function resetTotal() {
			$("#pager").zPager({

			});
		}
	</script>
	<!--分页结束-->
</body>
</html>