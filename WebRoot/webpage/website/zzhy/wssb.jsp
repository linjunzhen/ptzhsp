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
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".top-tap>ul>li").click(function(){
			$(this).addClass("li_on").siblings(".top-tap ul li").removeClass("li_on");
			$("#nzqy"+$(this).index()).css({'display':'block'}).siblings().css({'display':'none'});
			$("#wzqy"+$(this).index()).css({'display':'block'}).siblings().css({'display':'none'});
		});
		$(".nzqy .treelist li").click(function(){
			if($(this).attr('date-item')==1){				
				var COMPANY_SETTYPE=$("#NZQY_FORM input[name='COMPANY_SETTYPE']").val();
				var value = $(this).attr('date-value');
				$(this).addClass('on').siblings().removeClass('on');
				if(COMPANY_SETTYPE!=''&&COMPANY_SETTYPE!=null&&COMPANY_SETTYPE!=value){
					$(this).children().children().removeClass('on');
				}
				$("#NZQY_FORM input[name='COMPANY_SETTYPE']").val(value)	
			}
			if($(this).attr('date-item')==3){
				$(this).addClass('on').siblings().removeClass('on');	
				var value = $(this).attr('date-value');
				$("#NZQY_FORM input[name='COMPANY_SETNATURE']").val(value);
			}
			if($(this).attr('date-item')==2){
			  $(this).addClass('on').parent().siblings().children().removeClass('on');
			}
		});
		$(".wzqy .treelist li").click(function(){
			if($(this).attr('date-item')==1){
			  $(this).addClass('on').siblings().removeClass('on');			  		
				var COMPANY_SETTYPE=$("#WZQY_FORM input[name='COMPANY_SETTYPE']").val();
				var value = $(this).attr('date-value');
				if(COMPANY_SETTYPE!=''&&COMPANY_SETTYPE!=null&&COMPANY_SETTYPE!=value){
					$(this).children().children().removeClass('on');
				}
				$("#WZQY_FORM input[name='COMPANY_SETTYPE']").val(value)	
			}
			if($(this).attr('date-item')==2){
			  $(this).addClass('on').parent().siblings().children().removeClass('on');
			}		
		});
		
		$(".grdz .treelist li").click(function(){
			if($(this).attr('date-item')==1){
			  $(this).addClass('on').siblings().removeClass('on');			  		
				var COMPANY_SETTYPE=$("#GRDZ_FORM input[name='COMPANY_SETTYPE']").val();
				var value = $(this).attr('date-value');
				$("#GRDZ_FORM input[name='COMPANY_SETTYPE']").val(value)	
			}	
		});
		$(".gtsf .treelist li").click(function(){
			if($(this).attr('date-item')==1){
			  $(this).addClass('on').siblings().removeClass('on');			  		
				var COMPANY_SETTYPE=$("#GTSF_FORM input[name='COMPANY_SETTYPE']").val();
				var value = $(this).attr('date-value');
				$("#GTSF_FORM input[name='COMPANY_SETTYPE']").val(value)	
			}	
		});
	});
	$(function() {
		
		jQuery(".eui-menu").slide({ 
			type:"menu", //效果类型
			titCell:".syj-location", // 鼠标触发对象
			targetCell:".syj-city", // 效果对象，必须被titCell包含
			delayTime:0, // 效果时间
			defaultPlay:false,  //默认不执行
			returnDefault:true // 返回默认
		});
		jQuery(".syj-tyys").slide({trigger:"click"});
	});
	function nzqysq(){
		var COMPANY_TYPE = $("#NZQY_FORM input[name='COMPANY_TYPE']").val().Trim();
		var COMPANY_TYPECODE = $("#NZQY_FORM input[name='COMPANY_TYPECODE']").val().Trim();
		var COMPANY_SETNATURE = $("#NZQY_FORM input[name='COMPANY_SETNATURE']").val().Trim();
		var COMPANY_SETTYPE = $("#NZQY_FORM input[name='COMPANY_SETTYPE']").val().Trim();
		if(COMPANY_TYPE==""||COMPANY_TYPE==null
			||COMPANY_TYPECODE==""||COMPANY_TYPECODE==null
			||COMPANY_SETNATURE==""||COMPANY_SETNATURE==null
			||COMPANY_SETTYPE==""||COMPANY_SETTYPE==null){
			art.dialog({
				content: "请选择公司类型！",
				icon:"error",
				ok: true
			});
			return;
		}		
		$('#NZQY_FORM').submit();
	}
	function wzqysq(){
		var COMPANY_TYPE = $("#WZQY_FORM input[name='COMPANY_TYPE']").val().Trim();
		var COMPANY_TYPECODE = $("#WZQY_FORM input[name='COMPANY_TYPECODE']").val().Trim();
		var COMPANY_SETTYPE = $("#WZQY_FORM input[name='COMPANY_SETTYPE']").val().Trim();
		if(COMPANY_TYPE==""||COMPANY_TYPE==null
			||COMPANY_TYPECODE==""||COMPANY_TYPECODE==null
			||COMPANY_SETTYPE==""||COMPANY_SETTYPE==null){
			art.dialog({
				content: "请选择公司类型！",
				icon:"error",
				ok: true
			});
			return;
		}		
		$('#WZQY_FORM').submit();
	}
	function grdzsq(){
		var COMPANY_SETTYPE = $("#GRDZ_FORM input[name='COMPANY_SETTYPE']").val().Trim();
		if(COMPANY_SETTYPE==""||COMPANY_SETTYPE==null){
			art.dialog({
				content: "请选择公司类型！",
				icon:"error",
				ok: true
			});
			return;
		}		
		$('#GRDZ_FORM').submit();
	}
	function gtsfsq(){
		var COMPANY_SETTYPE = $("#GTSF_FORM input[name='COMPANY_SETTYPE']").val().Trim();
		if(COMPANY_SETTYPE==""||COMPANY_SETTYPE==null){
			art.dialog({
				content: "请选择公司类型！",
				icon:"error",
				ok: true
			});
			return;
		}		
		$('#GTSF_FORM').submit();
	}
	String.prototype.Trim = function() 
    { 
        return this.replace(/(^\s*)|(\s*$)/g, ""); 
    };
	function setType(typecode,typename){
		var COMPANY_TYPECODE=$("#NZQY_FORM input[name='COMPANY_TYPECODE']").val();
		if(COMPANY_TYPECODE!=''&&COMPANY_TYPECODE!=null&&COMPANY_TYPECODE!=typecode){
			$("#"+COMPANY_TYPECODE).children().children().removeClass('on');
			$("#NZQY_FORM input[name='COMPANY_SETNATURE']").val('');			
		}
		$("#NZQY_FORM input[name='COMPANY_TYPECODE']").val(typecode);
		$("#NZQY_FORM input[name='COMPANY_TYPE']").val(typename);
	}
	function setWzqyType(typecode,typename){
		$("#WZQY_FORM input[name='COMPANY_TYPECODE']").val(typecode);
		$("#WZQY_FORM input[name='COMPANY_TYPE']").val(typename);
	}
</script>

</head>

<body>

<jsp:include page="/webpage/website/zzhy/head.jsp?currentNav=wssb" />
<div  style="float:left; width:100%;">

<div class="container">
        	<div class="syj-crumb">
                <span>所在位置：</span><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> >  <i>网上申报</i>
            </div>
        </div>
        
<div class="container" style=" overflow:hidden; margin-bottom:20px;background:url(<%=path%>/webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
    <div class="net-detail">
      <div class="syj-tyys">
        <div class="hd syj-tabtitle">
          <ul>
            <li><a href="javascript:void(0)">外资企业</a></li>
            <li><a href="javascript:void(0)">内资企业</a></li>
            <li><a href="javascript:void(0)">个人独资</a></li>
            <li><a href="javascript:void(0)">个体商户</a></li>
          </ul>
        </div>
        <div class="bd">
          <div class="wzqy">
		  <form id="WZQY_FORM" action="domesticControllerController/txsqb.do" method="post">
				<input type="hidden" name="COMPANY_TYPE" />
				<input type="hidden" name="COMPANY_TYPECODE" />
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode" value="201605170002XK00102"/>
				<input type="hidden" name="formKey" value="wztxsqb"/>
		  		<input type="hidden" name="itemNames" value="${itemNames }"/>
		  		<input type="hidden" name="itemCodes" value="${itemCodes }"/>
			   <div class="top-tap">
				  <div class="top-container">
					<div class="treelist" id="wzqy0">
					  <ul>
							<e:for filterid="wzgs" end="100" var="efor" dsid="115">
								<li date-item='1' date-value="${efor.TYPE_CODE}">${efor.TYPE_NAME}
									<e:for filterid="${efor.TYPE_CODE}" end="100" var="efor1" dsid="115">
									<ul>
										<li date-item='2' onclick="setWzqyType('${efor1.TYPE_CODE}','${efor1.TYPE_NAME}')">${efor1.TYPE_NAME} 
										<div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div>
										<div class="line_c">
										<img src="<%=path%>/webpage/website/zzhy/images/line_c.png" />
										</div>
										</li>
									</ul>							   
									</e:for>
								</li>
							</e:for>

					  </ul>
					  <div class="tap-btn">
					  
					  <a class="tap_next" href="javascript:void(0);" onclick="wzqysq();">下一步</a>
					  </div>
					</div>
				  </div>
				  
				  
				</div>
			</form>
          </div>
          
        <div class="nzqy">
			<form id="NZQY_FORM" action="domesticControllerController/txsqb.do" method="post">
				<input type="hidden" name="COMPANY_TYPE" />
				<input type="hidden" name="COMPANY_TYPECODE" />
				<input type="hidden" name="COMPANY_SETNATURE" />
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode" value="201605170002XK00101"/>
				<input type="hidden" name="formKey" value="txsqb"/>
		  		<input type="hidden" name="itemNames" value="${itemNames }"/>
		  		<input type="hidden" name="itemCodes" value="${itemCodes }"/>
				<div class="top-tap">
				  <div class="top-container">
					<div class="treelist" id="nzqy0">
					  <ul>
						<e:for filterid="nzgs" end="100" var="efor" dsid="115">
						<li date-item='1' date-value="${efor.TYPE_CODE}">${efor.TYPE_NAME}
							<e:for filterid="${efor.TYPE_CODE}" end="100" var="efor1" dsid="115">
							<ul>
								<li date-item='2' onclick="setType('${efor1.TYPE_CODE}','${efor1.TYPE_NAME}')" id="${efor1.TYPE_CODE}">${efor1.TYPE_NAME} <div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div><div class="line_c"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
									<ul>
										<e:for filterid="${efor1.TYPE_CODE}" end="100" var="efor2" dsid="40">
										<li date-item='3' date-value='${efor2.dic_code}' class="nzqyRbnei">${efor2.dic_name} 
											<div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div>
											<div class="line_c"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
											<div class="line_c1"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
										</li>									
										</e:for>
									</ul>
								</li>
							</ul>
						   
							</e:for>
						</li>
						</e:for>
					  </ul>
					  <div class="tap-btn">
					  
					  <a class="tap_next" href="javascript:void(0);" onclick="nzqysq();">下一步</a>
					  </div>
					</div>
				  </div>
				</div>
			</form>
        </div>
          
	    <div class="grdz">
			<form id="GRDZ_FORM" action="domesticControllerController/txsqb.do" method="post">
				<input type="hidden" name="COMPANY_TYPE" value="个人独资"/>
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode" value="201605170002XK00103"/>
				<input type="hidden" name="formKey" value="grdztxsqb"/>
		  		<input type="hidden" name="itemNames" value="${itemNames }"/>
		  		<input type="hidden" name="itemCodes" value="${itemCodes }"/>
			   <div class="top-tap">
				  <div class="top-container">
					<div class="treelist" id="grdz0">
					  <ul>
							<li date-item='1' date-value="1">
								个人独资
							</li>
					  </ul>
					  <div class="tap-btn">
					  
					  <a class="tap_next" href="javascript:void(0);" onclick="grdzsq();">下一步</a>
					  </div>
					</div>
				  </div>
				  
				  
				</div>
			</form>
		</div>
	    <div class="gtsf">
			<form id="GTSF_FORM" action="domesticControllerController/txsqb.do" method="post">
				<input type="hidden" name="COMPANY_TYPE" value="个体商户"/>
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode" value="201605170002XK00104"/>
				<input type="hidden" name="formKey" value="gtdztxsqb"/>
			   <div class="top-tap">
				  <div class="top-container">
					<div class="treelist" id="grsf0">
					  <ul>
							<li date-item='1' date-value="1">
								个体商户
							</li>
					  </ul>
					  <div class="tap-btn">
					  
					  <a class="tap_next" href="javascript:void(0);" onclick="gtsfsq();">下一步</a>
					  </div>
					</div>
				  </div>
				  
				  
				</div>
			</form>
		</div>
        </div>
      </div>
    </div>
  </div>

</div>

<jsp:include page="/webpage/website/zzhy/foot.jsp" />
</body>
</html>
