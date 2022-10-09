<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>平潭综合实验区商事主体登记申报系统</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />


<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="plug-in/base64-1.0/jquery.base64.js"></script>

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
	            		  s += "<td style='text-align:center'>"+resultJson.ITEM_NAME+"</td>";
	            		  s += "<td style='text-align:center'>"+resultJson.CREATE_TIME.substr(0,10)+"</td>";
	            		  var ysName = resultJson.YS_NAME;
						  if(null==resultJson.YS_NAME){
							  ysName="";
						  }
	            		  if(resultJson.RUN_STATUS==0){
	            			  s += "<td style='text-align:center'>"+ysName+"</td><td  style='text-align:center'>";
	            			  s += "草稿";
	            		  }else if(resultJson.RUN_STATUS==1){
	            			  s += "<td style='text-align:center'>"+ysName+"</td><td  style='text-align:center'>";
	            			  s += "<b style=\"color: #008000;\">正在办理</b>";
	            		  }else{
	            			  s += "<td style='text-align:center'>"+ysName+"</td><td style='text-align:center'>";
	            			  s +="<b style=\"color:blue\">已办结</b>";
	            		  }
	            		  s += "</td><td style='text-align:center'>";
	            		  if(resultJson.RUN_STATUS!=0&&resultJson.RUN_STATUS!=1){
	            			  s += "<a href=\"javascript:void(0);\"  style=\"width: 60px;\" onclick=\"ckbjyj('"
	                          +resultJson.FINAL_HANDLEOPINION+"');\" class=\"btn1\">办结意见</a>";
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
						art.dialog({
							content: "无查询结果，烦请确认申报号及查询码！",
							icon:"error",
							ok: true
						});
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

<body  style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=tyfk" />
<div  class="eui-main">


        
		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
				<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
				<li style="font-size:16px">统一反馈</li>
			</ul>
		</div>
<div class="container" style="background:#fff; overflow:hidden; margin-bottom:20px;">

<div class="feedback">
	 <form action="webSiteController/bsjdcx.do" id="bsjdcqForm">
		<div class="feedback_p">
			<span> 申报号：</span>
			<input  type="text" maxlength="30" class="validate[required]"  name="exeId" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/>
		</div>
		<div class="feedback_p">
			<span> 查询码：</span>
			<input type="password"  class="validate[required]" name="bscxmm" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/>
		</div>
		<a href="javascript:void(0);" onclick="$('#bsjdcqForm').submit();">查询</a>
    </form>
</div>



</div>

<div class="container" style=" overflow:hidden;  background:#fff;">
  <div class="order-detail">
    <div class="syj-tyys">
      <div class="hd syj-tabtitle">
        <ul>
          <li><a href="javasrcipt:void(0)">查询结果</a></li>
        </ul>
      </div>
    </div>
		<div id="qsrsbhjcxmm" class="search_none">请先输入“申报号”及“查询码”！</div>
		<div style="padding-left: 20px; margin-top: 10px;">
			<table id="cxjgtab" cellpadding="0" cellspacing="0" class="syj-table2" style="display: none;" >
				<tr>
					<th>事项名称</th>
					<th width="100px">提交时间</th>
					<th width="100px">当前节点</th>
					<th width="50px">状态</th>
					<th width="100px">操作</th>
				</tr>
				<tr id="bscxList" >
				</tr>
			</table>
			<div id="bslc" style="margin-bottom: 20px; margin-top: 20px;">
			</div>
		<div>
		</div>
    </div>


</div>
</div>
</div>

<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
