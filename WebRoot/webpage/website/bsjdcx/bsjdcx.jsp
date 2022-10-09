<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sbh = request.getParameter("sbh");
String cxmm = request.getParameter("cxmm");
request.setAttribute("sbh", sbh);
request.setAttribute("cxmm", cxmm);
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
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil,validationegine,layer,artdialog"></eve:resources>
	<script type="text/javascript">
	$(function() {
		$("#bsjdcqForm").validationEngine("attach", { 
			promptPosition:"topLeft",
	        autoPositionUpdate:true,
	        autoHidePrompt: true,            //自动隐藏提示信息
	        autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
	        fadeDuration: "0.5",             //隐藏提示信息淡出的时间
	        maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
	        showOneMessage: false,
	        onValidationComplete:bsjdcq
		}); 
	});
	function bsjdcq(form, valid){
		if(valid){
			var formData = $("#bsjdcqForm").serialize();
	        var url = $("#bsjdcqForm").attr("action");
	        AppUtil.ajaxProgress({
	            url : url,
	            params : formData,
	            callback : function(resultJson) {
	              if(resultJson.success){
	            		  $("#tips").text("");
	            		  $("#bscxList").html("");
	            		  var s = "";
	            		  s += "<td>"+resultJson.ITEM_NAME+"</td>";
	            		  s += "<td>"+resultJson.CREATE_TIME.substr(0,10)+"</td>";
	            		  
	            		  if(resultJson.RUN_STATUS==0){
	            			  s += "<td>"+resultJson.YS_NAME+"</td><td>";
	            			  s += "草稿";
	            		  }else if(resultJson.RUN_STATUS==1){
	            			  s += "<td>"+resultJson.YS_NAME+"</td><td>";
	            			  s += "<b style=\"color: #008000;\">正在办理</b>";
	            		  }else{
	            			  s += "<td>"+resultJson.YS_NAME+"</td><td>";
	            			  s +="<b style=\"color:blue\">已办结</b>";
	            		  }
	            		  s += "</td><td>";
	            		  if(resultJson.RUN_STATUS!=0&&resultJson.RUN_STATUS!=1){
	            			  s += "<a href=\"javascript:void(0);\" onclick=\"ckbjyj('"
	                          +resultJson.FINAL_HANDLEOPINION+"');\" class=\"userbtn3\">办结意见</a>";
	            		  }
	                      s += "</td>";
	            		  $("#bscxList").html(s);
	            		  $("#cxjgtab").css('display','');
	            		  $("#qsrsbhjcxmm").css('display','none');
	            		  $("#bslc").html("<iframe src=\"webpage/website/bsjdcx/bsjdcxxmlct.jsp?defId="
	            				  +resultJson.DEF_ID+"&nodeName="+encodeURI(resultJson.CUR_STEPNAMES)+"\" scrolling=\"no\" frameborder=\"0\" width=\"100%\" height= \"159px\"></iframe>");
                  }else{
                	  $("#bscxList").html("");
                	  $("#bslc").html("")
                	  $("#tips").text("无查询结果，烦请确认申报号及查询密码！");
                	  $("#cxjgtab").css('display','none');
                	  $("#qsrsbhjcxmm").css('display','block');
                  }
	            }
	        });
		}
	}
	function ckbjyj(con){
		if(null==con||''==con||'undefined'==con){
			con = '无办结意见'
		}
        art.dialog({
            title: '办结意见',
            content: con,
            lock : true,
            width : "400px",
            ok: true
        });
    }
	$(function(){
		if("${sbh}"!=""&&"${cxmm}"!=""){
			//alert("${sbh}"+"---"+$.base64.decode("${cxmm}"));
			$("input[name='exeId']").val("${sbh}");
			$("input[name='bscxmm']").val($.base64.decode("${cxmm}"));
			$('#bsjdcqForm').submit();
		}
	});
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp?currentNav=bsjdcx" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
    	<div class="current"><span>您现在所在的位置：</span><a href="#">首页</a> > <i>办事进度查询</i></div>
    	<div class="bsMain1 clearfix">
    	   <form action="webSiteController/bsjdcx.do" id="bsjdcqForm">
        	<table cellpadding="0" cellspacing="0" class="jdsrhtable">
            	<tr>
                	<th>申报号：</th>
                    <td><input type="text" maxlength="30" class="textinput validate[required]" name="exeId" style="width:304px;" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/></td>
                    <td rowspan="3" valign="top"><a href="javascript:void(0);" class="bsbtn2" onclick="$('#bsjdcqForm').submit();">查  询</a></td>
                </tr>
                <tr>
                	<th>查询密码：</th>
                    <td><input type="password" maxlength="8" class="textinput validate[required]" name="bscxmm" style="width:304px;" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/></td>
                </tr>
                <tr>
                	<td></td>
                    <td><span class="bscolor3" id="tips"></span></td>
                </tr>
            </table>
            </form>
        </div>
        <div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>查询结果</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <div id="qsrsbhjcxmm" style="text-align: center;font-size: 14px;line-height: 70px;" class="bscolor3">请先输入申报号及查询密码</div>
            	<table id="cxjgtab" cellpadding="0" cellspacing="0" class="bstable2" style="border-collapse:separate ;display: none;" >
                    <tr>
                        <th>事项名称</th>
                        <th width="100px">提交时间</th>
                        <th width="200px">当前节点</th>
                        <th width="80px">状态</th>
                        <th width="150px">操作</th>
                    </tr>
                    <tr id="bscxList" >
                    </tr>
                </table>
            </div>
        </div>
        <div id="bslc" style="margin-bottom: 20px;">
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
