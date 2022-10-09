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
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil"></eve:resources>
	<style type="text/css">
	.serveImg ul li a.on{
	   color: #3188d2;
	}
	</style>
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
	        TabHeads("#listContainer li","bslistOn","#listContainer h3");
	        changeCss("#grlb li a","on");
	    })
	    function setTypeId(typeId){
        $("#busTypeId").val(typeId);
        reload();
        }
	    
	    function changeCss(c,a){
	    	$(c).click(function(){
                $(c).each(function(){
                    $(this).removeClass(a)
                });
                $(this).addClass(a);
            })
	    }
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>便民服务</i></div>
        <div class="bsMain1 clearfix" style="padding:10px 20px 0 30px;">
            <div class="serveImg clearfix">
                <ul id="grlb">
                    <li><a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122da62b4001f')" class="serveC on"><p>升学</p></a></li>
                    <li>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dab42e0020')" class="serveC1"><p>工作</p></a>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dadb2e0021')" class="serveC2"><p>购房</p></a>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122db01050022')" class="serveC3"><p>结婚</p></a>
                    </li>
                    <li>
                        <div style="width:270px; height:88px;">
                            <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122db275a0023')" class="serveC4"><p>生育</p></a>
                            <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122db7e200024')" class="serveC5"><p>失业</p></a>
                        </div>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dba0fb0025')" class="serveC6"><p>创业</p></a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dbc1f30026')" class="serveC1"><p>迁居</p></a>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dbf9290027')" class="serveC2"><p>退休</p></a>
                        <a href="javascript:void(0)" onclick="setTypeId('4028818c512273e7015122dc28b10028')" class="serveC3"><p>后事</p></a>
                    </li>
                </ul>
            </div>
            <div style="padding:0 10px 0 0;">
                <form id="bsForm">
                <input type="hidden" id="busTypeId" name="busTypeIds" value="4028818c512273e7015122da62b4001f" /> 
                <div class="listinfo">
				    <span class="lfloat"><input type="checkbox" name="SFZX" value="0" onclick="reload();" /> 仅显示可以在线办理的事项</span><span
				        class="rfloat">共 <i id="zgbsts">0</i> 个服务事项，其中 <i id="kzxbsts">0</i> 个可以在线办事
				    </span>
				    <script type="text/javascript">
				    function jskzx(){
				        var formData = $("#bsForm").serialize();
				        $.post("serviceItemController.do?jskzx&busType=grbs",formData, function(resultJson) {
				            var itemList = jQuery.parseJSON(resultJson);
				            var zgbsts = "";
				            var kzxbsts = "";
				            zgbsts = itemList[1].total;
				            kzxbsts =  itemList[0].total;
				            $("#zgbsts").text(zgbsts);
				            $("#kzxbsts").text(kzxbsts);
				        });
				    }
				    
				    </script>
				   </div>
				</form>
                <%--开始映入列表页 --%>
	            <jsp:include page="../common/bslist.jsp" />
	            <%--开始映入列表页 --%>
                <%--开始引入分页JSP --%>
	            <jsp:include page="../common/page.jsp" >
	            <jsp:param value="serviceItemController.do?pagelist&busType=grbs" name="pageURL"/>
	            <jsp:param value="callpage" name="callpage"/>
	            <jsp:param value="10" name="limitNum"/>
	            <jsp:param value="bsForm" name="pageFormId"/>
	            </jsp:include>
	            <%--结束引入分页JSP--%>
            </div>
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
