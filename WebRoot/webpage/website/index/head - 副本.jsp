<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
  //获取当前定位菜单
  String currentNav = request.getParameter("currentNav");
  request.setAttribute("currentNav", currentNav);
  
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="eui-header">
	<div class="header1">
		<div class="container">			
		<jsp:include page="../common/tophead.jsp"></jsp:include>
		</div>
	</div>
	<div class="indswf">
		<object width="1000" height="228" name="top2016" id="top2016" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
			<param name="movie" value="<%=path%>/webpage/website/common/swf/top2016.swf"> 
			<param name="quality" value="High"> 
			<param name="wmode" value="transparent"> 
			<param name="allowScriptAccess" value="sameDomain"> 
			<embed src="<%=path%>/webpage/website/common/swf/top2016.swf" quality="High" pluginspage="http://www.macromedia.com/go/getflashplayer" width="1000" height="228" name="top2016" id="top2016" wmode="transparent" allowscriptaccess="sameDomain" type="application/x-shockwave-flash"></object>
		</embed>
	 </div>
	<div class="eui-navm">
	 <div class="eui-nav">
		<ul>
			<li  <c:if test="${currentNav=='sy'}">class="on"</c:if>>
				<a href="webSiteController/view.do">首页</a>
			</li>
			<li <c:if test="${currentNav=='xwdt'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=50">新闻动态</a>
			</li>
			<li <c:if test="${currentNav=='jgsz'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=23">机构设置</a>
			</li>
			<li <c:if test="${currentNav=='zcfg'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=41">政策法规</a>
			</li>
			<li <c:if test="${currentNav=='wsbs'}">class="on"</c:if>>
				<a href="webSiteController/view.do?navTarget=website/bsdt/index" target="_blank">网上办事</a>
			</li>
			<li <c:if test="${currentNav=='zxhd'||currentNav=='yhzn'}">class="on"</c:if>>
				<a href="webSiteController/view.do?navTarget=website/hd/xsqx">便民服务</a>
			</li>
			<li <c:if test="${currentNav=='wjzl'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=49">中心公告</a>
			</li>
			<li <c:if test="${currentNav=='xxgk'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=142">信息公开</a>
			</li>
			<li <c:if test="${currentNav=='djgz'}">class="on"</c:if>>
				<a href="contentController/list.do?moduleId=46">党建工作</a>
			</li>
		</ul>
	 </div>
	 <div class="indSearch" id="menu">
		<div  class="container">
			<div  class="lfloat">
				<p style="width: 200px; margin-top: 0px; margin-bottom: 0px;margin-left: 20px;">
					<span class="headWlm">今天是： </span>
					<span class="headWlm" id="shownowtime"></span>					
				</p>
			</div>
			<div  class="lfloat"   style="margin-left: 20px;">
				<iframe width="280" scrolling="no" height="25" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=34&icon=1&num=3&py=pingtan"></iframe>
			</div>
			<div class="rfloat">
				<span class="lfloat " style="width: 60px;">
					<select id="searchType" name="searchType" style="width:80px;height:28px;">
						<option value="1">搜文章</option>
						<option value="2">搜事项</option>
					</select>
				</span>
				<input type="text" name="key" id="contenKey" placeholder="输入关键词"/>
				<a href="javascript:openNewWindow()" class="Searchbtn">搜索</a>
			</div> 
		</div>
		<!--新闻动态-->
		<div class="container">
			<ul class="eui-navs" style="margin-left:128px;">				
				<e:for dsid="7" filterid="50" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
		
		<!--机构设置-->
		<div class="container">
			<ul class="eui-navs" style="margin-left:238px;">
				<e:for dsid="7" filterid="22" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
		
		<!--政策法规-->
		<div class="container">
			<ul class="eui-navs" style="margin-left:348px;">	
				<e:for dsid="7" filterid="41" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
		
		<!--网上办事-->
		<div class="container">
			<ul class="eui-navs" style="margin-left:248px;">
				<li><a href="webSiteController/view.do?navTarget=website/grbs/grbs" target="_blank">个人办事</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/frbs/frbs" target="_blank">法人办事</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/bmfw/bmfw" target="_blank">部门服务</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/qlqd/qlqdnew" target="_blank">权力清单</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/bsjdcx/bsjdcx" target="_blank">办事进度查询</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/bsdt/xnjc" target="_blank">效能监察</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/bsdt/sceneNaviIndex" target="_blank">场景式导航</a></li>
			</ul>
		</div>
		
		<!--便民服务-->
		<div class="container">
			<ul class="eui-navs rfloat" style="margin-right:247px;">
				<li><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/hd/wsdc">网上调查</a></li>
				<li>|</li>
				<li><a href="contentController/list.do?moduleId=52">用户指南</a></li>
				<li>|</li>
				<li><a href="webSiteController/view.do?navTarget=website/bsdt/cjwtlb" target="_blank">常见问题</a></li>
			</ul>
		</div>
		
		<!--中心公告-->
		<div class="container">
			<ul class="eui-navs rfloat" style="margin-right:247px;">	
				<e:for dsid="7" filterid="49" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
		
		<!--信息公开-->
		<div class="container">
			<ul class="eui-navs rfloat" style="margin-right:136px;">					
				<e:for dsid="7" filterid="141" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
		
		<!--党建工作-->
		<div class="container">
			<ul class="eui-navs rfloat" style="margin-right:24px;">				
				<e:for dsid="7" filterid="46" end="200" var="efor">
					<li <c:if test="${ efor.module_id == module.MODULE_ID}">class="subOn"</c:if>>
						<a href="contentController/list.do?moduleId=${efor.module_id}" <c:if test="${ efor.property_class == 5}">target="_blank"</c:if>>
							<e:sub  str="${efor.module_name}" endindex="8"></e:sub>
						</a>
					</li>
					<li>|</li>
				</e:for> 
			</ul>
		</div>
	 </div>
	 <script type="text/javascript">
		jQuery(".eui-navm").slide({ titCell:".eui-nav li", mainCell:"#menu"});
	 </script>
 </div>
</div>

