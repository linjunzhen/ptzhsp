<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil,validationegine,layer,artdialog"></eve:resources>
	<script type="text/javascript">
	$(function() {
		$("#twzyzgcxzsForm").validationEngine("attach", { 
			promptPosition:"topLeft",
	        autoPositionUpdate:true,
	        autoHidePrompt: true,            //自动隐藏提示信息
	        autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
	        fadeDuration: "0.5",             //隐藏提示信息淡出的时间
	        maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
	        showOneMessage: false,
	        onValidationComplete:twzyzgcxzscx
		}); 
	});
	
	//台湾地区职业资格采信证书查询
	function twzyzgcxzscx(form, valid){
		if(valid){
			var formData = $("#twzyzgcxzsForm").serialize();
	        var url = $("#twzyzgcxzsForm").attr("action");
	        AppUtil.ajaxProgress({
	            url : url,
	            params : formData,
	            callback : function(resultJson) {
	             if(resultJson.length > 0){
	             	var s = "";
	             	for(var i=0;i<resultJson.length;i++){
	             		if(i!=0){
	             			s+="<div style=\"width:100%;height:2px;background-color:#8aabe5;margin-bottom:5px;\"></div>";
	             		}
	             		s+="<div class=\"clearfix\">";
	             		s+="<div class=\"eui-infoL\">";	             		
	             		<%-- if(resultJson[i].PHOTO_PATH == null){
	             			var src = "<%=path%>/webpage/cms/module/images/twQualificationPhoto.jpg";
	             			s+="<img src=\""+src+"\" style=\"width:110px;height:154px;\" class=\"pic\"/>";
	             		}else{
	             			var src = "<%=path%>/"+resultJson[i].PHOTO_PATH;
	             			s+="<img src=\""+src+"\" style=\"width:110px;height:154px;\" class=\"pic\"/>";
	             		} --%>
	             		s+="<div class=\"txt\">";
	             		s+="<p><span>姓名</span>"+resultJson[i].USERNAME+"</p>";
	             		s+="<p><span>性别</span>"+resultJson[i].ZSSEX+"</p>";
	             		s+="<p><span>出生日期</span>"+resultJson[i].BIRTHDAY+"</p>";
	             		s+="<p><span>台胞证号/台湾身份证号</span>"+resultJson[i].TB_ZJHM+"</p>";
	             		s+="<p><span>台湾地区职业名称及等级</span>"+resultJson[i].ZYZG_LEVEL+"</p>";
	            		s+="</div></div>";
	            		s+="<div class=\"eui-infoR\">";
	            		s+="<h3>经比对采信，持证人具备大陆地区以下水平：</h3>";
	            		s+="<div class=\"txt\">";
	            		s+="<p><span>职业(工种)及等级</span>"+resultJson[i].ZY_LEVEL+"</p>";
	            		s+="<p><span>证书编号</span>"+resultJson[i].ZS_NUMBER+"</p>";
	            		s+="<p><span>证书有效期</span>"+resultJson[i].ZS_VALIDITY+"</p>";
	            		s+="</div></div></div>";
	            		s+="<div class=\"eui-infoTxt\">";
	            		s+="<p>平潭综合实验区行政审批局</p>";
	            		s+="<p>"+resultJson[i].CXZS_TIME+"</p>";
	            		s+="</div>";
	             	}
	            	$("#cxjg").html(s);
	             }else{
	             	var s = "<div class=\"clearfix\">";
	             	s += "<div id=\"tbzjhm\" style=\"text-align: center;font-size: 14px;line-height: 70px;\" class=\"bscolor3\">无查询结果，烦请确认姓名、证件编号！</div></div>";
            		$("#cxjg").html(s);
	             	
	             }
	            }
	        });
		}
	}
	</script>
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp?currentNav=twzyzgcxzscx" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
    	<div class="current"><span>您现在所在的位置：</span><a href="#">首页</a> > <i>台湾职业资格采信证书查询</i></div>
    	<div class="bsMain1 clearfix">
    	  <form action="twQualificationController/twzyzgcxzscx.do" id="twzyzgcxzsForm">
        	<table cellpadding="0" cellspacing="0" class="jdsrhtable" style="width:80%;">
            	<tr>
            		<th>姓名：</th>
                    <td><input type="text" class="textinput" name='USERNAME' value="请输入姓名" style="width:204px;" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/></td>
                    <th>证书编号：</th>
                    <td><input type="text" class="textinput" name='ZS_NUMBER' value="请输入证书编号" style="width:204px;" onclick="if(value==defaultValue){value='';this.style.color='#000'}" onblur="if(!value){value=defaultValue;this.style.color='#b5b5b5'}"/></td>
                    <td><a href="javascript:;" class="bsbtn2" style="height: 28px; line-height: 28px; background-size: 100% 28px;" onclick="$('#twzyzgcxzsForm').submit();">查  询</a></td>
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
            <div class="bsboxC" id="cxjg">
            	<div class="clearfix">
	             	<div id="tbzjhm" style="text-align: center;font-size: 14px;line-height: 70px;" class="bscolor3">请输入姓名、证书编号！</div>
	            </div>
            </div> 
        </div>    
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
