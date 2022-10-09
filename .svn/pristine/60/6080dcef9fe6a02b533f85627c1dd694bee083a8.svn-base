<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.DataAbutmentService"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/base64-1.0/jquery.base64.js"></script>
	
	<link rel="stylesheet" type="text/css" href="webpage/website/index/css/selectpick.css">
	<script type="text/javascript" src="webpage/website/index/js/selectpick.js"></script>
	<script type="text/javascript" src="webpage/website/index/js/common.js"></script>
	<script src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" type="text/javascript"></script>
	<script src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
	<link type="text/css" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" rel="stylesheet">
	<script src="<%=path%>/plug-in/layer-1.8.5/layer.min.js" type="text/javascript"></script>
	
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">
	function bskscx(){
        var sbh = $("#sbh").val().Trim();
        var cxmm = $("#cxmm").val().Trim();
        if(sbh==""||sbh=="请输入申报号"||sbh==null){
            alert("请输入申报号");
            $("#sbh").focus();
            return ;
        }
        if(cxmm==""||cxmm=="请输入查询密码"||null==cxmm){
            alert("请输入查询密码");
            $("#cxmm").focus();
            return ;
        }  
        window.open("<%=path%>/webSiteController/view.do?navTarget=website/bsjdcx/bsjdcx&sbh="+sbh+"&cxmm="+$.base64.encode(cxmm));
    }
	String.prototype.Trim = function() 
    { 
        return this.replace(/(^\s*)|(\s*$)/g, ""); 
    };
	/**下拉框链接选择JS开始**/
	function skipLink(like){
		if(like!=0){
			window.open(like,'','')
		}else{
			alert("请选择相关链接");
		}
	};
	function openNewWindow(){
		var searchType = $("#searchType").val();
		if(searchType==1){			
			window.open("<%=path%>/luceneConfigController/search.do?key="+encodeURIComponent($("#contenKey").val()));
		}else{
			window.open("<%=path%>/webSiteController/allItemSearch.do?itemName="+encodeURIComponent($("#contenKey").val()));
		}
	};
	$(function() {
		//悬浮飘窗
		ad123();
	});
	function nowtime(ctimes){
		var now = new Date(ctimes);  
		var year = now.getFullYear();       //年
		var month = now.getMonth() + 1;     //月
		var day = now.getDate();            //日       
		var hh = now.getHours();            //时
		var mm = now.getMinutes();          //分
		var ss = now.getSeconds();
		 var clock = year + "年";
		 if(month < 10)
			 clock += "0";
		 clock += month + "月";
		 if(day < 10)
			 clock += "0";
		 clock += day + "日 ";
		 clock +=" 星期"+"日一二三四五六".charAt(now.getDay());
		//等待一秒钟后调用time方法，由于settimeout在time方法内，所以可以无限调用
		 $('#shownowtime').html(clock);
		 setTimeout('nowtime('+(ctimes+1000)+')',1000);
	}
	nowtime(new Date().getTime());
	
	/**信件查询**/
	function checkformLetter(){
		var LETTER_CODE = document.getElementById("LETTER_CODE").value;
		var LETTER_PWD = document.getElementById("LETTER_PWD").value;
		
		if(null==LETTER_CODE||""==LETTER_CODE){
			art.dialog({
				content: "请输入信件编号!",
				icon:"error",
				ok: true
			});	
			document.getElementById("LETTER_CODE").focus();
		}else if(null==LETTER_PWD||""==LETTER_PWD){
			art.dialog({
				content: "请输入查询密码!",
				icon:"error",
				ok: true
			});	
			document.getElementById("LETTER_PWD").focus();
		}else{
			var result = {};
			result["LETTER_CODE"]=LETTER_CODE;
			result["LETTER_PWD"]=LETTER_PWD;
			$.post("<%=path%>/letterController/queryLetter.do", result, 
			function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success) {					
					$('#letterForm').submit();
					document.getElementById("LETTER_CODE").value="";
					document.getElementById("LETTER_PWD").value="";
				} else {
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});	
				}
			});
		}
			
	}
	</script>
  </head>
  
  <body><div showtime="15" endtime="2017-01-01 00:00:00"
		starttime="2015-03-06 00:00:00" class="ad_float eve_ad">
		<div class="eve_ad_con">
            <img width="70px" height="201" src="<%=path%>/webpage/website/index/images/sxsyx.png" />
		</div>
		<a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>
	</div>
    <%--开始编写头部文件 --%>
    <jsp:include page="./head1.jsp?currentNav=sy" /> 
    <%--结束编写头部文件 --%>
    
		<div class="indSearch">		
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
        </div>
    <div class="container lbpadding">

		<div class="indOne clearfix tmargin10">
			<div class="mainOL">
            	<div class="eui-fdp">
					<e:imgplayer dsid="15" skin="bar" width="520" height="290" titlelenght="40" filterid="21" end="5"></e:imgplayer>
				</div>
            	<div class="mainOR">
                    <div class="indTz">
                        <a target="_blank" href="contentController/list.do?moduleId=101" class="more">更多>></a><b>公示公告</b>	
                    </div>
                    <div class="indTzli">
                        <ul>
						<e:for filterid="101" end="8" var="efor" dsid="2">
							<li>
								<span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
								<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
								<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="28" ></e:sub>
								</a>
							</li>
						</e:for>
                        </ul>
                    </div>
					
					<script type="text/javascript">
						jQuery(".mainOR").slide({mainCell:".indTzli ul",autoPage:true,effect:"topLoop",autoPlay:true,vis:4});
					</script>
                </div>
            </div>
			<div class="mainOC">
            	<div class="eui-news">
				<e:obj filterid="50" var="eobj" dsid="3">
                	<h3><a target="_blank" href="contentController/view.do?contentId=${eobj.tid}">
							<e:sub objdate="${eobj.itemReldate}" timeout="2"   str="${eobj.itemTitle}" endindex="20" ></e:sub></a></h3>
                    <p><e:sub str="${eobj.itemText}" endindex="70" ></e:sub><a target="_blank" href="contentController/view.do?contentId=${eobj.tid}">[详情]</a></p>
				</e:obj>
                </div>
				<div class="newshd">
					<ul>
						<li><a target="_blank" href="contentController/list.do?moduleId=50">新闻动态</a></li>
						<li><a target="_blank" href="contentController/list.do?moduleId=41">政策法规</a></li>
						<li style="margin-right:0px;"><a target="_blank" href="contentController/list.do?moduleId=51">通知公告</a></li>
					</ul>
				</div>
				<div class="newsbd">
					<ul class="list">
						<e:for filterid="50" end="11" begin="2" var="efor" dsid="2">
							<li><span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="24" ></e:sub></a></li>
						</e:for>
					</ul>
					<ul class="list">
						<e:for filterid="41" end="10" var="efor" dsid="8">
							<li><span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="24" ></e:sub></a></li>
						</e:for>
					</ul>
					<ul class="list">
						<e:for filterid="51" end="10" var="efor" dsid="2">
							<li><span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="24" ></e:sub></a></li>
						</e:for>
					</ul>
					<script type="text/javascript">jQuery(".mainOC").slide({titCell:".newshd li",mainCell:".newsbd"})</script>
				</div>
			</div>
		</div>
		<!--<div class="tmargin10 wsbsbg"><a href="webSiteController/view.do?navTarget=website/bsdt/index">more<i>+</i></a></div>-->
		
		<%--开始引入我要办的JSP --%>
		<jsp:include page="./wyb.jsp" />
		<%--结束引入我要办的JSP --%>
		<!--修改-->
        <div class="indTwo tmargin10">
			<div class="title"><h1><img src="webpage/website/common/images/icon1.png" />我要问</h1></div>
			<div class="indContent clearfix">
            	<div class="mainL1">
                    <div class="zdcon">
                    	<ul>
                        	<li><a href="<%=path%>/webpage/website/hd/xsqx.jsp?type=zx" target="_blank">
							<img src="webpage/website/common/images/new-icon.png" />咨 询</a></li>
                            <li><a href="<%=path%>/webpage/website/hd/xsqx.jsp?type=jb" target="_blank">
							<img src="webpage/website/common/images/new-icon1.png" />举 报</a></li>
                            <li><a href="<%=path%>/webpage/website/hd/xsqx.jsp?type=ts" target="_blank">
							<img src="webpage/website/common/images/new-icon2.png" />投 诉</a></li>
                            <li><a href="<%=path%>/webpage/website/hd/xsqx.jsp?type=jy" target="_blank">
							<img src="webpage/website/common/images/new-icon3.png" />建 议</a></li>
                            <li><a href="<%=path%>/webpage/website/hd/xsqx.jsp?type=gx" target="_blank">
							<img src="webpage/website/common/images/new-icon4.png" />感 谢</a></li>
                            <li><a href="<%=path%>/webSiteController/view.do?navTarget=website/hd/xsqx" target="_blank">更多...</a></li>
                        </ul>
                    </div>
                    <div class="title1" style="margin-right:26px;">
						<ul>
							<li class="on"><a href="javascript:void(0)">信件信息查询</a></li>
						</ul>
					</div>
                    <div class="eui-sqxx">
						 <form class="eui-form"  id="letterForm" target="_blank"  method="post" action="<%=path%>/letterController/detail.do">
							<div class="eui-sqxxl">
								<p><input type="text" autocomplete="off" id="LETTER_CODE" name="LETTER_CODE" value="" placeholder="请输入信件编号" /></p>
								<p><input type="text" autocomplete="off" id="LETTER_PWD" name="LETTER_PWD" value="" placeholder="请输入查询密码" /></p>
							</div>
							<a href="javascript:checkformLetter();" class="eui-sqxxr">
							<img src="webpage/website/common/images/new-btn.png" /></a>
						</form>
                    </div>
                </div>
                <div class="mainR1">
                	<table cellpadding="0" cellspacing="0" class="indtable">
                    	<tr>
                            <th width="110px">信件编号</th>
                            <th>信件标题</th>
                            <th width="110px">信件类型</th>
                            <th width="80px">处理情况</th>
                            <th width="120px">来信时间</th>
                        </tr>
						
						<e:for filterid="1" end="9" var="efor" dsid="75">
                        <tr>
                            <td>${efor.LETTER_CODE}</td>
                            <td><a  target="_blank" href="<%=path%>/letterController/detail.do?entityId=${efor.LETTER_ID}" title="${efor.LETTER_TITLE}">
							<e:sub  str="${efor.LETTER_TITLE}" endindex="18" ></e:sub></a></td>
                            <td>${efor.LETTER_TYPE}</td>
                            <td><c:if test="${efor.REPLY_FLAG==1}">已回复</c:if>
								<c:if test="${efor.REPLY_FLAG!=1}">未回复</c:if>
							</td>
                            <td>${efor.CREATE_TIME}</td>
                        </tr>
						</e:for>
                       
                    </table>
                </div>
            </div>
        </div>
        <!--修改-->       
        <div class="indTwo tmargin10">
			<div class="title"><h1><img src="webpage/website/common/images/icon2.png" />我要查</h1>
            	<div class="indRsearch"><b>申报号：</b>
            	<input type="text" maxlength="30" class="textinput" id="sbh"onfocus="if(this.value=='请输入申报号'){this.value='';}this.style.color='#999';"
                            value="请输入申报号" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/>
            	<b>查询密码：</b>
            	<input type="password" maxlength="8" class="textinput" id="cxmm" placeholder="请输入查询密码"
                              onfocus="if(this.value=='请输入查询密码') {this.value='';}this.style.color='#999';" onblur="if(this.value==''){this.value='';this.style.color='#999';}" value=""/>
                <a href="javascript:void(0);" class="btn" onclick="bskscx();">查 询</a>
                </div>
            </div>
			<div class="indContent clearfix">
		
				<%--开始引入我要办的JSP --%>
				<jsp:include page="./wyclist.jsp" />
				<%--结束引入我要办的JSP --%>

                
            </div>
        </div>
        <div class="indTwo tmargin10">
			<div class="title"><h1><img src="webpage/website/common/images/icon3.png" />我要看</h1>
            	
            </div>
			<div class="indContent clearfix">
            	<div class="mainL2">
                	<%--开始引入首页常用问题JSP --%>
                    <jsp:include page="./cjwt.jsp" />
                    <%--结束引入首页常用问题JSP --%>
                    
                    <%--开始引入办件统计JSP --%>
                    <jsp:include page="./bjtj.jsp" />
                    <%--结束引入办件统计JSP --%>
                </div>
                <div class="mainR2">
                	<div class="title2">办事指南</div>
                    <div class="list1 tmargin12">
                        <ul>
							<e:for filterid="1" end="5" var="efor" dsid="36">
								<li>
									<a target="_blank" href="serviceItemController/bsznDetail.do?itemCode=${efor.item_code}" 
									style="width: 285px;"  target="_blank" title="${efor.item_name}">
									<e:sub str="${efor.item_name}" endindex="34" ></e:sub>
									</a>
								</li>
							</e:for>
                        </ul>
                    </div>
                    <div class="govinfo">
                    	<ul>
                        	<li><a href="contentController/list.do?moduleId=143" class="govimg">信息公开制度</a></li>
                            <li><a target="_blank" href="http://www.pingtan.gov.cn/site/main/info/gov_ml.jsp" class="govimg1">信息公开指南及目录</a></li>
                            <li><a target="_blank" href="http://www.pingtan.gov.cn/site/main/info/gov_apply.jsp" class="govimg2">依申请公开</a></li>
                            <li><a target="_blank" href="http://www.pingtan.gov.cn/site/main/info/gov_sugbox_search.jsp" class="govimg3">信息公开意见箱</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="indlink tmargin10">
        	<ul>
            	<li>
					<select onchange="skipLink(this.value);">					
						<option value="0">——————————国家部委网————————————</option>
						 <e:for filterid="61" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
					</select>
				</li>
                <li>
					<select onchange="skipLink(this.value);">
						<option value="0">——————————省内机构单位网站—————————</option>
						 <e:for filterid="62" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
					</select>
				</li>
                <li>
					<select onchange="skipLink(this.value);">
						<option value="0">——————————平潭综合实验区网站————————</option>
						 <e:for filterid="63" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
					</select>
				</li>
            </ul>
        </div>
	</div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="./foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
