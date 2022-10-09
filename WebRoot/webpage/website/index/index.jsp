<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.DataAbutmentService"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="net.evecom.platform.hflow.service.ExecutionService"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

<body>
	<jsp:include page="/webpage/website/index/head.jsp?currentNav=sy" />
	
<!--  产业奖补全程网办飘窗开始 -->
	<div showtime="10" endtime="2099-11-25 23:59:59"
  starttime="2016-03-21 23:59:59" class="ad_float eve_ad">
  <div class="eve_ad_con" style="padding:10px;background:#154b71;color:#fff;opacity:0.85"  >
      <div style="height:170px;width:170px">
      	<a href="webSiteController.do?applyItem&itemCode=345071904GF00301" target="_blank">
      		<img src="<%=path%>/webpage/website/index/images/cyjbpc.png" />
      	</a>
      <br>
   </div>
  </div>
  <a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>
 </div>
<!--  产业奖补全程网办飘窗结束 -->
<div showtime="10" endtime="2099-11-25 23:59:59"
	 starttime="2016-03-21 23:59:59" class="ad_float eve_ad">
	<div class="eve_ad_con" style="padding:10px;background:#154b71;color:#fff;opacity:0.85"  >
		<div style="height:200px;width:400px">
			<a href="http://gjzwfw.www.gov.cn/col/col644/index.html" target="_blank">
				<img src="<%=path%>/webpage/website/index/images/20200310.jpg" />
			</a>
			<br>
		</div>
	</div>
	<a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>
</div>

<div showtime="10" endtime="2099-11-25 23:59:59"
	 starttime="2016-03-21 23:59:59" class="ad_float eve_ad">
	<div class="eve_ad_con" style="padding:10px;background:#154b71;color:#fff;opacity:0.85"  >
		<a href="<%=path%>/contentController/view.do?contentId=9593" target="_blank">
			<div style="height:100px;width:300px;font-size: 40px;display: flex;justify-content: center;align-items: center;flex-wrap: wrap;color: #ffffff">
				<span >门户以及公众号</span>
				<span >改版升级公告</span>
			</div>
		</a>
	</div>
	<a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>
</div>


<%--<div showtime="10" endtime="2099-11-25 23:59:59"--%>
<%--	 starttime="2016-03-21 23:59:59" class="ad_float eve_ad">--%>
<%--	<div class="eve_ad_con" style="padding:10px;background:#154b71;color:#fff;opacity:0.85"  >--%>
<%--		<div>--%>
<%--			<a href="http://www.gov.cn/zhuanti/2020qglh/index.htm" target="_blank">--%>
<%--				<img style="height:130px;width: auto;" src="<%=path%>/webpage/website/index/images/2020052501.jpg" />--%>
<%--			</a>--%>
<%--			<br>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	<a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>--%>
<%--</div>--%>




<%--<!--  网站域名迁移飘窗开始 -->--%>
<%--<div showtime="10" endtime="2029-11-31 23:59:59"--%>
<%--	 starttime="2019-08-13 23:59:59" class="ad_float eve_ad">--%>
<%--	<div class="eve_ad_con" style="padding:10px;background:#154b71;color:#fff;opacity:0.85"  >--%>
<%--		<div style="height:170px;width:420px">--%>
<%--			<span style="font-size: 18px;text-align: center;margin-left:100px;">关于网站域名迁移的公告</span><br>--%>
<%--			各位网友朋友：<br>--%>
<%--			&nbsp;&nbsp;&nbsp;因网站迁移，平潭综合实验区不动产登记中心门户网站将于2019年10月14日起，迁移至新域名，--%>
<%--			请登录<a href="http://xzfwzx.pingtan.gov.cn/webpage/site/bdc/info/index.jsp" style="color: red;">http://xzfwzx.pingtan.gov.cn/webpage/site/bdc/info/index.jsp</a>首页进行访问，--%>
<%--			敬请访问并收藏。届时原网站域名http://bdcdj.pingtan.gov.cn/将停用。<br>--%>
<%--			特此公告。<br>--%>
<%--			<br>--%>
<%--			平潭综合实验区行政服务中心<br>--%>
<%--			2019年10月14日--%>
<%--			<br>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	<a target="_blank" href="javascript:void(0);" class="eve_ad_close">X关闭</a>--%>
<%--</div>--%>
<%--<!--  网站域名迁移飘窗结束 -->--%>
 
    <div class="container lbpadding"><img src="<%=path%>/webpage/website/index/images/ad.png" /></div>
	<div class="container lbpadding">	
		<div class="indOne clearfix tmargin10">
			<div class="eui-fdp1"><e:imgplayer dsid="76" skin="bar" width="354" height="260" titlelenght="40" filterid="1" end="5"></e:imgplayer></div>
            <div class="eui-mainc">
            	<div class="hd eui-newstitle">
                	<span class="eui-more">
                    	<a href="contentController/list.do?moduleId=344" target="_blank" class="more">更多&gt;</a>
                        <a href="contentController/list.do?moduleId=41" target="_blank" class="more">更多&gt;</a>
                    </span>
                	<ul>
                    	<li><a target="_blank" href="contentController/list.do?moduleId=344">中心动态</a></li>
                        <li><a target="_blank" href="contentController/list.do?moduleId=41">政策法规</a></li>
                    </ul>
                </div>
                <div class="bd">
                	<ul class="eui-list">
						
						<e:for filterid="344" end="7" var="efor" dsid="2">
							<li><span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="17" ></e:sub></a></li>
						</e:for>
					</ul>
					<ul class="eui-list">
						<e:for filterid="41" end="7" var="efor" dsid="8">
							<li><span><fmt:formatDate value="${efor.itemReldate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="17" ></e:sub></a></li>
						</e:for>
					</ul>
                </div>
                <script type="text/javascript">jQuery(".eui-mainc").slide({targetCell:".eui-more a"})</script>
            </div>
            <div class="eui-xxgg">
            	<div class="eui-indTz">
                    <a target="_blank" href="contentController/list.do?moduleId=49" class="more">更多>></a><b><a target="_blank" href="contentController/list.do?moduleId=49">中心公告</a></b>
                </div>
                <div class="eui-xxggli">
                    <ul>
                    	<e:for filterid="49" end="4" var="efor" dsid="8">
							<li>
							<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
							<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="32" ></e:sub></a></li>
						</e:for>
                    </ul>
                </div>
            </div>
		</div>
		<div class="clearfix">
			<div class="eui-title">
            	<h1><a href="http://ptsy.zwfw.fujian.gov.cn" target="_blank"><img src="<%=path%>/webpage/website/common/images/eui-tle-wz.png" /></a></h1>
            	<div class="indRsearch" style="margin-right: 6px;margin-top: 16px;">
            		<a href="http://ptsy.zwfw.fujian.gov.cn" target="_blank">
            			<img src="<%=path%>/webpage/website/index/images/shengonline.png" />
            		</a>
            </div>
            	<%--
                <div class="eui-newsxx"><b>最新消息：</b>
                	<div class="bd">
                        <ul>
                            <li><span>2016-05-24</span><a href="#">平潭海峡公铁两用大桥首个主塔墩钢吊箱围堰吊装成功</a></li>
                            <li><span>2016-05-24</span><a href="#">平潭海峡公铁两用大桥首个主塔墩钢吊箱围堰吊装成功</a></li>
                            <li><span>2016-05-24</span><a href="#">平潭海峡公铁两用大桥首个主塔墩钢吊箱围堰吊装成功</a></li>
                        </ul>
                    </div>
                    <script type="text/javascript">
                    	jQuery(".eui-newsxx").slide({mainCell:".bd ul",autoPage:true,effect:"topLoop",autoPlay:true,vis:1});
                    </script>
                </div>
            --%>
            </div>
            <div class="eui-main1 clearfix tmargin10">

               <div class="eui-bsl">
				 <a href="http://ptsy.zwfw.fujian.gov.cn" target="_blank"> <img src="<%=path%>/webpage/website/index/images/banner2019.png"></a>
			   <%----%>
                <%--&lt;%&ndash;开始引入我要办的JSP &ndash;%&gt;--%>
					<%--<jsp:include page="./wsbs.jsp" />--%>
					<%--&lt;%&ndash;结束引入我要办的JSP &ndash;%&gt;--%>
            </div>
                
					
                <div class="eui-gov">
                	<a href="contentController/list.do?moduleId=141" class="eui-click" style="color: #626262;">进入&gt;</a>
                    <ul>
                        <li><a href="contentController/list.do?moduleId=142" target="_blank">信息公开指南</a></li>
                        <li><a href="contentController/list.do?moduleId=143" target="_blank">信息公开制度</a></li>
                        <li><a href="contentController/list.do?moduleId=144" target="_blank">信息公开目录</a></li>
                        <li><a href="contentController/list.do?moduleId=145" target="_blank">依申请公开</a></li>
                        <li><a href="contentController/list.do?moduleId=146" target="_blank">信息公开邮箱</a></li>
                    </ul>
                </div>
            </div>
<%--			<div class="eui-main2" style="margin-left: 4px;">--%>
<%--				<ul>--%>
<%--					<li style="margin-right: 8px;"><a href="webpage/tzxm/index.jsp" target="_blank"><img src="webpage/website/common/images/2019101803.png" /></a></li>--%>
<%--					<li style="margin-right: 8px;"><a href="<%=basePath%>zzhy.jsp" target="_blank"><img src="webpage/website/common/images/2019101801.png" /></a></li>--%>
<%--					<li style="margin-right: 8px;"><a href="http://61.154.11.191/usermana/login.do?method=index" target="_blank"><img src="webpage/website/common/images/2019101804.png" /></a></li>--%>
<%--				&lt;%&ndash;                    <li><a href="http://zzhy.ptxzfwzx.gov.cn/" target="_blank"><img src="webpage/website/common/images/eui-img2.png" /></a></li>&ndash;%&gt;--%>
<%--					&lt;%&ndash;  <li><a href="webSiteController/view.do?navTarget=website/bsdt/zdfwlb" target="_blank"><img src="webpage/website/common/images/eui-img3.png" /></a></li> &ndash;%&gt;--%>
<%--					&lt;%&ndash;                    <li style="margin-right:0px;"><a href="webSiteController/view.do?navTarget=website/bsdt/lstd" target="_blank"><img src="webpage/website/common/images/eui-img4.png" /></a></li>&ndash;%&gt;--%>
<%--				</ul>--%>
<%--			</div>--%>
<%--			<div class="eui-main2" style="margin-left: 4px;">--%>
<%--				<ul>--%>
<%--					<li style="margin-right: 8px;"><a href="http://fj.122.gov.cn" target="_blank"><img src="webpage/website/common/images/2019101802.png" /></a></li>--%>
<%--					<li style="margin-right: 8px;"><a href="<%=path%>/webpage/site/bdc/info/index.jsp" target="_blank"><img src="webpage/website/common/images/2019091603.png" /></a></li>--%>
<%--&lt;%&ndash;					<li><a href="<%=path%>/webpage/site/gjj/info/index.jsp" target="_blank"><img src="webpage/website/common/images/2019091607.png" /></a></li>&ndash;%&gt;--%>
<%--					<li style="margin-right: 8px;"><img src="webpage/website/common/images/2019091608.png" /></li>--%>
<%--				</ul>--%>
<%--			</div>--%>
			<div class="eui-main2">
				<ul>
					<li><a href="webpage/tzxm/index.jsp" target="_blank"><img src="webpage/website/common/images/2019091604.png" /></a></li>
					<li><a href="<%=basePath%>zzhy.jsp" target="_blank"><img src="webpage/website/common/images/ptqykb.png" /></a></li>
					<li><a href="http://61.154.11.191/usermana/login.do?method=index" target="_blank"><img src="webpage/website/common/images/2019091605.png" /></a></li>
					<%--                    <li><a href="http://zzhy.ptxzfwzx.gov.cn/" target="_blank"><img src="webpage/website/common/images/eui-img2.png" /></a></li>--%>
					<li  style="margin-right:0px;"><a href="http://fj.122.gov.cn" target="_blank"><img src="webpage/website/common/images/2019091602.png" /></a></li>
					<%--  <li><a href="webSiteController/view.do?navTarget=website/bsdt/zdfwlb" target="_blank"><img src="webpage/website/common/images/eui-img3.png" /></a></li> --%>
					<%--                    <li style="margin-right:0px;"><a href="webSiteController/view.do?navTarget=website/bsdt/lstd" target="_blank"><img src="webpage/website/common/images/eui-img4.png" /></a></li>--%>
				</ul>
			</div>
			<div class="eui-main2">
				<ul>
					<li><a href="<%=path%>/webpage/site/bdc/info/index.jsp" target="_blank"><img src="webpage/website/common/images/2020032202.png" /></a></li>
					<li style="margin-right:12px;"><a href="<%=path%>/contentController/list.do?moduleId=543" target="_blank"><img src="webpage/website/common/images/2019091608.png" /></a></li>
					<li style="margin-right:12px;"><a href="http://220.160.52.229:7002" target="_blank"><img src="webpage/website/common/images/2020020401.png" /></a></li>
					<li style="margin-right:0px;"><a href="http://fz.edtsoft.com/fjqyy/" target="_blank"><img src="webpage/website/common/images/2020020402.png" /></a></li>
				</ul>
			</div>
<!-- 			<div class="eui-main2"> -->
<!-- 				<ul style="display: flex;justify-content: center;"> -->
<!-- 					<li style="margin-right: 10px;margin-left: 0px;"><a href="http://220.160.52.229:7002" target="_blank"><img src="webpage/website/index/images/2020020401.jpg" style="margin-right: 10px;"/></a></li> -->
<!-- 					<li style="margin-right: 0px;"><a href="http://fz.edtsoft.com/fjqyy/" target="_blank"><img src="webpage/website/index/images/2020020402.jpg" /></a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
           	<div class="eui-bjmain tmargin10">
           	<%
				ExecutionService executionService = (ExecutionService)AppUtil.getBean("executionService");
			    List<Map<String,Object>> bjsxList = executionService.getEndExecution();
			    request.setAttribute("bjsxList", bjsxList);
			%>
            	<div class="eui-bgl">
                	<table cellpadding="0" cellspacing="0" class="eui-table1">
                    	<tr>
                        	<th width="200px">受理部门</th>
                            <th>受理事项</th>
                            <th width="110px">申报日期</th>
                            <th width="110px">应办结日期</th>
                            <th width="80px">办件状态</th>
                        </tr>
                    </table>
                    <div class="bd">
                    	<ul>
	                    	<c:forEach items="${bjsxList}" var="bjsxInfo">
	                    		<li>
	                            	<table cellpadding="0" cellspacing="0" class="eui-table1">
										<tr>
											<td width="200px">${bjsxInfo.DEPART_NAME}</td>
											<td>
												<a href="javascript:return false;" title="${bjsxInfo.ITEM_NAME}">
													<e:sub  str="${bjsxInfo.ITEM_NAME}" endindex="14" ></e:sub>
												</a>
											</td>
											<td width="110px">${bjsxInfo.CREATE_TIME}</td>
											<td width="110px">${bjsxInfo.END_TIME}</td>
											<td width="80px">${bjsxInfo.STATUS}</td>
										</tr>
								    </table>
	                            </li>
							</c:forEach>
                        </ul>
                    </div>
                </div>
				<div class="eui-bgr1">
					<a href="<%=path%>/webpage/website/twzyzgcxzs/twzyzgcxzscx.jsp"  target="_blank">
						<img  src="<%=path%>/webpage/website/common/images/twcxzy.png"/>
					</a>
				</div>
                <div class="eui-bgr">
                	<div class="eui-bgrtitle">办件查询</div>
                    <div class="eui-bgrcon">
                    	<input type="text" maxlength="30" class="textinput" id="sbh"onfocus="if(this.value=='请输入申报号'){this.value='';}this.style.color='#999';"
                            value="请输入申报号" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/>
                        <input type="password" maxlength="8" class="textinput" id="cxmm" placeholder="请输入查询密码" autocomplete="off"
                              onfocus="if(this.value=='请输入查询密码') {this.value='';}this.style.color='#999';" onblur="if(this.value==''){this.value='';this.style.color='#999';}" value=""/>
                        <a href="javascript:bskscx();"></a>
                    </div>
                </div>
            </div>
            <div class="eui-bjtj tmargin10">
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
						   $(".sjzs").text(resultJson.sjzs);
						   $(".zrsj").text(resultJson.zrsj);
						   $(".bjzs").text(resultJson.bjzs);
						   $(".zrbj").text(resultJson.zrbj);
						   $(".ndsj").text(resultJson.ndsj);
						   $(".ndbj").text(resultJson.ndbj);
						   $(".bysj").text(resultJson.bysj);
						   $(".bybj").text(resultJson.bybj);
					   }
				   });
				  
				 });
				</script>
               <label id="dqrq"></label>
               <div id="formaring" >
                <ul class="eui-bjtjli">		
					<li>昨日收件：<span class="zrsj"></span> 件</li>
					<li>昨日办结：<span class="zrbj"></span> 件</li>
					<li>本月收件：<span class="bysj"></span> 件</li>
					<li>本月办结：<span class="bybj"></span> 件</li>
					<li>本年度收件：<span class="ndsj"></span> 件</li>
					<li>本年度办结：<span class="ndbj"></span> 件</li>
					<li>累计收件：<span class="sjzs"></span> 件</li>
					<li>累计办结：<span class="bjzs"></span> 件</li>
				</ul>
				</div>
            </div>
		</div>
		<%--ss--%>
        <div class="eui-title"><h1><a target="_blank" href="webSiteController/view.do?navTarget=website/hd/wyjc">
        <img src="<%=path%>/webpage/website/common/images/eui-tle-wz1.png" /></a>
        </h1>
<%--            <div class="indRsearch" style="margin-right: 6px;">--%>
<%--            <b>信件编号：</b>--%>
<%--            <input type="text" style="width: 126px;"  maxlength="30" class="textinput" autocomplete="off" id="LETTER_CODE" name="LETTER_CODE" value="" placeholder="请输入信件编号" />--%>
<%--            --%>
<%--            <b>查询密码：</b>--%>
<%--             <input type="text" style="width: 126px;"  maxlength="30" class="textinput" autocomplete="off" id="LETTER_PWD" name="LETTER_PWD" value="" placeholder="请输入查询密码" />--%>
<%--            --%>
<%--            <a href="javascript:void(0);"  class="btn" onclick="checkformLetter();">查 询</a>--%>
<%--            <a href="<%=path%>/webSiteController/view.do?navTarget=website/hd/xsqxList" target="_blank" class="more">&nbsp;&nbsp;&nbsp;&nbsp;更多>></a>--%>
<%--            </div>--%>
        </div>
        <div class="eui-bjmain tmargin10" style="height: 90px;">
<%--        	<div class="eui-bgl">--%>
<%--                <table cellpadding="0" cellspacing="0" class="eui-table1">--%>
<%--                    <tr>--%>
<%--                        <th width="200px">信件编号</th>--%>
<%--                        <th>信件标题</th>--%>
<%--                        <th width="110px">信件类型</th>--%>
<%--                        <th width="110px">处理情况</th>--%>
<%--                    </tr>--%>
<%--                </table>--%>
<%--                <div>--%>
<%--                    <ul>--%>
<%--	                    <e:for filterid="1" end="18" var="efor" dsid="75">--%>
<%--		                    <li>--%>
<%--			                    <table cellpadding="0" cellspacing="0" class="eui-table1">--%>
<%--			                        <tr>--%>
<%--			                            <td width="200px">${efor.LETTER_CODE}</td>--%>
<%--			                            <td><a  target="_blank" href="<%=path%>/letterController/detail.do?entityId=${efor.LETTER_ID}" title="${efor.LETTER_TITLE}">--%>
<%--										<e:sub  str="${efor.LETTER_TITLE}" endindex="18" ></e:sub></a></td>--%>
<%--			                            <td width="110px">${efor.LETTER_TYPE}</td>--%>
<%--			                            <td width="110px"><c:if test="${efor.REPLY_FLAG==1}">已回复</c:if>--%>
<%--											<c:if test="${efor.REPLY_FLAG!=1}">正在办理</c:if>--%>
<%--										</td>--%>
<%--			                            &lt;%&ndash;<td>${efor.CREATE_TIME}</td>--%>
<%--			                        &ndash;%&gt;</tr>--%>
<%--		                        </table>--%>
<%--	                        </li>--%>
<%--						</e:for>--%>
<%--                           --%>
<%--                    </ul>--%>
<%--                </div>--%>
<%--                <script>--%>
<%--                    jQuery(".eui-bgl").slide({mainCell:".bd ul",autoPage:true,effect:"topLoop",autoPlay:true,vis:7});--%>
<%--                </script>--%>
<%--            </div>--%>
            <div class="eui-xj1" style="width: 100%">
            	<ul>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank" class="eui-bg1"><img src="<%=path%>/webpage/website/common/images/eui-icon5.png" /><p>我要咨询</p></a></li>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank" class="eui-bg2"><img src="<%=path%>/webpage/website/common/images/eui-icon6.png" /><p>我要举报</p></a></li>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank" class="eui-bg3"><img src="<%=path%>/webpage/website/common/images/eui-icon7.png" /><p>我要投诉</p></a></li>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank" class="eui-bg4"><img src="<%=path%>/webpage/website/common/images/eui-icon8.png" /><p>我要建议</p></a></li>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank" class="eui-bg5"><img src="<%=path%>/webpage/website/common/images/eui-icon9.png" /><p>我要感谢</p></a></li>
                    <li><a href="http://pi12345.fujian.gov.cn/" target="_blank"  class="eui-bg6"><img src="<%=path%>/webpage/website/common/images/eui-icon10.png" /><p>更多</p></a></li>
                </ul>
            </div>
        </div>
        <div class="indlink tmargin10">
        	<ul>
            	<li><select onchange="skipLink(this.value);"><option value="0">——————————国家部委网————————————</option>
            	<e:for filterid="61" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
            	</select></li>
                <li><select onchange="skipLink(this.value);"><option value="0">——————————省内机构单位网站—————————</option>
                <e:for filterid="62" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
                </select></li>
                <li><select onchange="skipLink(this.value);"><option value="0">——————————平潭综合实验区网站————————</option>
                 <e:for filterid="63" end="1000" var="efor" dsid="7">
							<option value="${efor.linkurl}" title="${efor.module_name}">
								<e:sub str="${efor.module_name}" endindex="30"></e:sub>
							</option>
						 </e:for>
                </select></li>
            </ul>
        </div>
	</div>
    <jsp:include page="/webpage/website/index/foot.jsp" />
</body>
</html>

