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
		var COMPANY_TYPECODE = $("#NZQY_FORM input[name='COMPANY_TYPECODE']").val().Trim();
			$.dialog.open("webSiteController/view.do?navTarget=website/hd/commercialSelectorItemOfMp", {
				title : "平潭综合实验区企业开办全程电子化服务平台",
				width:"800px",
				esc: false,//取消esc键
				lock: true,
				resize:false,
				height:"600px",
				close: function () {
					var selectInfo = art.dialog.data("itemInfo");
					if(selectInfo){
						$("input[name='IS_ACCOUNT_OPEN']").val(selectInfo.IS_ACCOUNT_OPEN);
						$("input[name='ISNEEDSIGN']").val(selectInfo.ISNEEDSIGN);
						$("input[name='IS_PREAPPROVAL_PASS']").val(selectInfo.IS_PREAPPROVAL_PASS);
						$("input[name='ISSOCIALREGISTER']").val(selectInfo.ISSOCIALREGISTER);
						$("input[name='ISMEDICALREGISTER']").val(selectInfo.ISMEDICALREGISTER);
						$("input[name='ISFUNDSREGISTER']").val(selectInfo.ISFUNDSREGISTER);
						$("input[name='ISFIRSTAUDIT']").val(selectInfo.ISFIRSTAUDIT);
						$("input[name='ISGETBILL']").val(selectInfo.ISGETBILL);
						$("input[name='ISEMAIL']").val(selectInfo.ISEMAIL);
                        $("input[name='IS_FREE_ENGRAVE_SEAL']").val(selectInfo.IS_FREE_ENGRAVE_SEAL);
						art.dialog.removeData("itemInfo");
						doNext();
					}
				}
			}, false);
	}

	function  doNext() {
		var COMPANY_TYPE = $("#NZQY_FORM input[name='COMPANY_TYPE']").val().Trim();
		var COMPANY_TYPECODE = $("#NZQY_FORM input[name='COMPANY_TYPECODE']").val().Trim();
		if(COMPANY_TYPECODE=='pthhqy'||COMPANY_TYPECODE=='yxhhqy'){
			$("input[name='COMPANY_SETNATURE']").val(COMPANY_TYPECODE);
			$("input[name='COMPANY_SETTYPE']").val('nzhyqy');
			$("#NZQY_FORM input[name='itemCode']").val('201605170002XK00105');
		}
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
		stopPropagation();
		var COMPANY_TYPECODE=$("#NZQY_FORM input[name='COMPANY_TYPECODE']").val();
		if(COMPANY_TYPECODE!=''&&COMPANY_TYPECODE!=null&&COMPANY_TYPECODE!=typecode){
			$("#"+COMPANY_TYPECODE).children().children().removeClass('on');
			$("#NZQY_FORM input[name='COMPANY_SETNATURE']").val('');			
		}
		$("#NZQY_FORM input[name='COMPANY_TYPECODE']").val(typecode);
		$("#NZQY_FORM input[name='COMPANY_TYPE']").val(typename);
        var  alterStr="请确认股东是否已在省内外注册过</br>有限责任公司（自然人独资），如已注册，</br>不得再投资设立该企业类型的公司。"
		if(typecode=='zrrdz'&&typename=='有限责任公司（自然人独资）'){
			art.dialog({
				content: alterStr,
				icon:"warning",
				ok: true
			});
		}

		if(typecode=='frdz'&&typename=='有限责任公司（法人独资）'){
			art.dialog({
				content: "以下情形不得担任有限责任公司（法人独资）的股东：</br>1.不具备独立法人资格的市场主体，如合伙企业、</br>个人独资企业、个体工商户、分支机构；</br>2.企业类型为“有限责任公司（自然人独资）的公司",
				icon:"warning",
				ok: true
			});
		}
	}
	function sethyType(typecode,typename){
		$("#NZQY_FORM input[name='COMPANY_TYPECODE']").val(typecode);
		$("#NZQY_FORM input[name='COMPANY_TYPE']").val(typename);
	}

	function stopPropagation(e) {
		e = e || window.event;
		if(e.stopPropagation) { //W3C阻止冒泡方法
			e.stopPropagation();
		} else {
			e.cancelBubble = false; //IE阻止冒泡方法
		}
	}
	function setWzqyType(typecode,typename){
		$("#WZQY_FORM input[name='COMPANY_TYPECODE']").val(typecode);
		$("#WZQY_FORM input[name='COMPANY_TYPE']").val(typename);
	}
</script>

</head>

<body style="background: #f0f0f0;">

<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />

<div  class="eui-main">

		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
				<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
				<li style="font-size:16px">网上申报</li>
			</ul>
		</div>
        
<div class="container" style=" overflow:hidden; margin-bottom:20px;background:url(<%=path%>/webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
    <div class="net-detail">
      <div class="syj-tyys">
        <div class="hd syj-tabtitle">
          <ul>

            <li><a href="javascript:void(0)">内资企业</a></li>
			<li><a href="javascript:void(0)">个体商户</a></li>
          </ul>
        </div>
        <div class="bd">
          
        <div class="nzqy">
			<form id="NZQY_FORM" action="domesticControllerController/txsqbOfMp.do" method="post">
				<input type="hidden" name="COMPANY_TYPE" />
				<input type="hidden" name="COMPANY_TYPECODE" />
				<input type="hidden" name="COMPANY_SETNATURE" />
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode" value="201605170002XK00101"/>
				<input type="hidden" name="formKey" value="txsqb"/>
		  		<input type="hidden" name="itemNames" value="${itemNames }"/>
		  		<input type="hidden" name="itemCodes" value="${itemCodes }"/>
				<input type="hidden" name="IS_ACCOUNT_OPEN" />
				<input type="hidden" name="ISNEEDSIGN" />
				<input type="hidden" name="IS_PREAPPROVAL_PASS" />
				<input type="hidden" name="ISSOCIALREGISTER" />
				<input type="hidden" name="ISMEDICALREGISTER" />
				<input type="hidden" name="ISFUNDSREGISTER" />
				<input type="hidden" name="ISFIRSTAUDIT" />
				<input type="hidden" name="ISGETBILL" />
				<input type="hidden" name="ISEMAIL" />
				<input type="hidden" name="IS_FREE_ENGRAVE_SEAL" />
				<div class="top-tap">
				  <div class="top-container">
					<div class="treelist" id="nzqy0">
					  <ul>
						<e:for filterid="nzgs" end="100" var="efor" dsid="115">
							<c:if test="${efor.TYPE_CODE!='fgs'&&efor.TYPE_CODE!='gfgs'}">
						<li date-item='1' date-value="${efor.TYPE_CODE}"  class="on">${efor.TYPE_NAME}
							<e:for filterid="${efor.TYPE_CODE}" end="100" var="efor1" dsid="115">
								<c:if test="${efor1.TYPE_CODE!='gykg'&&efor1.TYPE_CODE!='gydz'}">
							<ul>
								<li date-item='2' id="${efor1.TYPE_CODE}"
										<c:if test="${efor.TYPE_CODE=='nzhyqy'}" >
											onclick="sethyType('${efor1.TYPE_CODE}','${efor1.TYPE_NAME}')"
										</c:if>
								        >
										${efor1.TYPE_NAME}
									<div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div>
									<div class="line_c"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
									<ul>
										<e:for filterid="${efor1.TYPE_CODE}" end="100" var="efor2" dsid="40">
										<li date-item='3' date-value='${efor2.dic_code}' class="nzqyRbnei"    onclick="setType('${efor1.TYPE_CODE}','${efor1.TYPE_NAME}')">${efor2.dic_name}
											<div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div>
											<div class="line_c"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
											<div class="line_c1"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div>
										</li>									
										</e:for>
									</ul>
								</li>
							</ul>
								</c:if>
							</e:for>
						</li>
							</c:if>
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
          
	    <div class="gtsf">
			<form id="GTSF_FORM" action="domesticControllerController/ptxxxzmp.do" method="post">
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

<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
