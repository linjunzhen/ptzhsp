<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.GenPlatReqUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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
	$(document).ready(
		function() {
			readXZ();
			$(".top-tap>ul>li").click(
				function() {
					$(this).addClass("li_on").siblings(
							".top-tap ul li")
							.removeClass("li_on");
			});
			$(".treelist li").click(function(){
				var dataitem = $(this).attr('date-item');
				if(dataitem==1||dataitem==2||dataitem==3){
					$(this).addClass('on').siblings().removeClass('on');
				}
				if(dataitem==3){
					var comptypecode = $(this).parent().parent().attr('comptypecode');
					if(comptypecode=='zrrdz'){
						art.dialog({
							content: "请确认股东是否已在省内外注册过</br>有限责任公司（自然人独资），如已注册，</br>不得再投资设立该企业类型的公司。",
							icon:"warning",
							ok: true
						});
					}

					if(comptypecode=='frdz'){
						art.dialog({
							content: "以下情形不得担任有限责任公司（法人独资）的股东：</br>1.不具备独立法人资格的市场主体，如合伙企业、</br>个人独资企业、个体工商户、分支机构；</br>2.企业类型为“有限责任公司（自然人独资）的公司",
							icon:"warning",
							ok: true
						});
					}
				}
				var itemcode = $(this).attr("itemcode");
				if(itemcode){
					$(".treelist").find("li[itemcode!=''][itemcode!='"+itemcode+"']").removeClass('on');
				}
			});

			jQuery(".eui-menu").slide({
				type : "menu", //效果类型
				titCell : ".syj-location", // 鼠标触发对象
				targetCell : ".syj-city", // 效果对象，必须被titCell包含
				delayTime : 0, // 效果时间
				defaultPlay : false, //默认不执行
				returnDefault : true
			// 返回默认
			});
			jQuery(".syj-tyys").slide({
				trigger : "click"
			});
		});

    function commercialSelector(compTypeCode){
        var selectItemLi = $("#"+compTypeCode).find("li[itemcode!=''][class='on']");
        var itemcode = selectItemLi.attr("itemcode");
        if('201605170002XK00101'==itemcode){
            $.dialog.open("webSiteController/view.do?navTarget=website/hd/commercialSelectorItem", {
                title : "平潭综合实验区企业开办全程电子化服务平台",
                width:"1000px",
                esc: false,//取消esc键
                lock: true,
                resize:false,
                height:"600px",
                close: function () {
                    var selectInfo = art.dialog.data("itemInfo");
                    if(selectInfo){
                        $("input[name='IS_ACCOUNT_OPEN']").val(selectInfo.IS_ACCOUNT_OPEN);
                        $("input[name='IS_ENGRAVE_SEAL']").val(selectInfo.IS_ENGRAVE_SEAL);
                        $("input[name='IS_FREE_ENGRAVE_SEAL']").val(selectInfo.IS_FREE_ENGRAVE_SEAL);
                        $("input[name='ISNEEDSIGN']").val(selectInfo.ISNEEDSIGN);
                        $("input[name='IS_PREAPPROVAL_PASS']").val(selectInfo.IS_PREAPPROVAL_PASS);
                        $("input[name='ISSOCIALREGISTER']").val(selectInfo.ISSOCIALREGISTER);
                        $("input[name='ISMEDICALREGISTER']").val(selectInfo.ISMEDICALREGISTER);
                        $("input[name='ISFUNDSREGISTER']").val(selectInfo.ISFUNDSREGISTER);
                        $("input[name='ISFIRSTAUDIT']").val(selectInfo.ISFIRSTAUDIT);
                        $("input[name='ISGETBILL']").val(selectInfo.ISGETBILL);
                        $("input[name='ISEMAIL']").val(selectInfo.ISEMAIL);
                        $("input[name='ISTAX']").val(selectInfo.ISTAX);
                        art.dialog.removeData("itemInfo");
                        doNext(compTypeCode);
                    }
                }
            }, false);
        }else if('201605170002XK00105'==itemcode||'201605170002XK00106'==itemcode||'201605170002XK00107'==itemcode||'201605170002XK00103'==itemcode){
			$.dialog.open("webSiteController/view.do?navTarget=website/hd/commercialSelectorItemOfYbsb", {
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
                        $("input[name='IS_ENGRAVE_SEAL']").val(selectInfo.IS_ENGRAVE_SEAL);
                        $("input[name='IS_FREE_ENGRAVE_SEAL']").val(selectInfo.IS_FREE_ENGRAVE_SEAL);
						$("input[name='ISNEEDSIGN']").val('0');
						$("input[name='IS_PREAPPROVAL_PASS']").val(selectInfo.IS_PREAPPROVAL_PASS);
						$("input[name='ISSOCIALREGISTER']").val(selectInfo.ISSOCIALREGISTER);
						$("input[name='ISMEDICALREGISTER']").val(selectInfo.ISMEDICALREGISTER);
						$("input[name='ISFUNDSREGISTER']").val(selectInfo.ISFUNDSREGISTER);
						$("input[name='ISFIRSTAUDIT']").val(selectInfo.ISFIRSTAUDIT);
						$("input[name='ISGETBILL']").val(selectInfo.ISGETBILL);
						$("input[name='ISEMAIL']").val(selectInfo.ISEMAIL);
						art.dialog.removeData("itemInfo");
						doNext(compTypeCode);
					}
				}
			}, false);
		} else{
            doNext(compTypeCode);
		}
    }
	function readXZ(){
		$.dialog.open("webSiteController/view.do?navTarget=website/hd/ykblNotice", {
			title : "平潭综合实验区经济发展局平潭综合实验区市场监督管理局关于全面实施外商投资企业设立的商务备案与工商登记“一口办理”的公告",
			width:"800px",
			esc: false,//取消esc键  
			cancel:false,
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectInfo = art.dialog.data("noticeInfo");
				if(selectInfo){
					$("input[name='smxz']").val(selectInfo.smxz);
					art.dialog.removeData("noticeInfo");
				}
			}
		}, false);
	}
	function doNext(compTypeCode){
		var selectItemLi = $("#"+compTypeCode).find("li[itemcode!=''][class='on']");
		var itemcode = selectItemLi.attr("itemcode");
		if("12345678"==itemcode){
            var key=selectItemLi.attr("comtypename");
            key=key.replace("其他","");
            var type="2c93f48251a4c13a0151a4c13e750008";
            //var url="http://xzfwzx.pingtan.gov.cn/webSiteController/view.do?navTarget=website/bmfw/bmfw"+"&key="+key+"&type="+type;
            var url="http://ptsy.zwfw.fujian.gov.cn";
			window.location.href =url;
            return;
		}

		if(itemcode){
			var selectTypeCode = selectItemLi.attr("comptypecode");
			var itemid = selectItemLi.attr("itemid");
			if(compTypeCode=="nzgs"){
				if(selectItemLi.closest("li[date-item='2']").attr("comptypecode")!=undefined){
					$("input[name='COMPANY_TYPE']").val(selectItemLi.closest("li[date-item='2']").attr("comtypename"));
					$("input[name='COMPANY_TYPECODE']").val(selectItemLi.closest("li[date-item='2']").attr("comptypecode"));
				}else{
					$("input[name='COMPANY_TYPE']").val(selectItemLi.attr("comtypename"));
					$("input[name='COMPANY_TYPECODE']").val(selectItemLi.attr("comptypecode"));
				}
				$("input[name='COMPANY_SETNATURE']").val(selectTypeCode);
				$("input[name='COMPANY_SETTYPE']").val(selectItemLi.closest("li[date-item='1']").attr("comptypecode"));
			}else{
				$("input[name='COMPANY_TYPE']").val(selectItemLi.attr("comtypename"));
				$("input[name='COMPANY_TYPECODE']").val(selectItemLi.attr("comptypecode"));
				$("input[name='COMPANY_SETNATURE']").val(selectTypeCode);
				$("input[name='COMPANY_SETTYPE']").val(selectItemLi.closest("li[date-item='1']").attr("comptypecode"));
			}
			$("input[name='itemCode']").val(itemcode);
			$("input[name='itemId']").val(itemid);
			$("#baseform").submit();
		}else{
			art.dialog({
				content: "请选择公司类型！",
				icon:"error",
				ok: true
			});
		}
	}
</script>
</head>

<body style="background: #f0f0f0;">

	<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=wssb" />
	<div class="eui-main">

		
		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newzzhy/images/new/add.png" >当前位置：</li>
				<li><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > </li>
				<li style="font-size:16px">网上申报</li>
			</ul>
		</div>
		
		<div class="container"
			style=" overflow:hidden; margin-bottom:20px;background:url(<%=path%>/webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
			<form id="baseform" action="domesticControllerController/txsqb.do" method="post">
			    <input type="hidden" name="COMPANY_TYPE" />
				<input type="hidden" name="COMPANY_TYPECODE" />
				<input type="hidden" name="COMPANY_SETNATURE" />
				<input type="hidden" name="COMPANY_SETTYPE" />
				<input type="hidden" name="itemCode"/>
				<input type="hidden" name="itemId" />
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
				<input type="hidden" name="ISTAX" />
				<input type="hidden" name="IS_ENGRAVE_SEAL" />
				<input type="hidden" name="IS_FREE_ENGRAVE_SEAL" />
			</form>
			
			<div class="net-detail">
				<div class="syj-tyys">
					<div class="hd syj-tabtitle">
						<ul>
						    <c:forEach items="${compTypeList}" var="compType">
							<c:if test="${compType.COMTYPE_CODE!='wzgs'}">
							<li><a>${compType.COMTYPE_NAME}</a></li>
							</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="bd">
                      <c:forEach items="${compTypeList}" var="topType">
						<c:if test="${topType.COMTYPE_CODE!='wzgs'}">
                        <div class="toptype">
                          <div class="top-tap">
								<div class="top-container">
									<div class="treelist" id="${topType.COMTYPE_CODE}">
									<ul>
									    <c:if test="${(topType.children)!= null && fn:length(topType.children) > 0}">
									    <c:forEach items="${topType.children}" var="child">
									        <li date-item='1' comtypename="${child.COMTYPE_NAME}" itemcode="${child.COMTYPE_ITEMCODE}" itemid="${child.COMTYPE_ID}" comptypecode="${child.COMTYPE_CODE}">${child.COMTYPE_NAME}
									            <c:if test="${(child.children)!= null && fn:length(child.children) > 0}">
									              <ul>
									                 <c:forEach items="${child.children}" var="child2">
									                   <li date-item='2' comtypename="${child2.COMTYPE_NAME}" itemcode="${child2.COMTYPE_ITEMCODE}" itemid="${child2.COMTYPE_ID}"  comptypecode="${child2.COMTYPE_CODE}">${child2.COMTYPE_NAME}
															<div class="line_r">
																<img
																	src="webpage/website/zzhy/images/line_r.png" />
															</div>
															<div class="line_c">
																<img
																	src="webpage/website/zzhy/images/line_c.png" />
															</div>
															<c:if test="${(child2.children)!= null && fn:length(child2.children) > 0}">
															   <ul>
															     <c:forEach items="${child2.children}" var="child3">
															        <li date-item='3' comtypename="${child3.COMTYPE_NAME}" itemcode="${child3.COMTYPE_ITEMCODE}" itemid="${child3.COMTYPE_ID}" comptypecode="${child3.COMTYPE_CODE}">${child3.COMTYPE_NAME}
																		<div class="line_r">
																			<img src="webpage/website/zzhy/images/line_r.png" />
																		</div>
																		<div class="line_c">
																			<img src="webpage/website/zzhy/images/line_c.png" />
																		</div>
																		<div class="line_c1">
																			<img src="webpage/website/zzhy/images/line_c.png" />
																		</div>
																	</li>
															     </c:forEach>
															   </ul>
															</c:if>
															
														</li>
									                 </c:forEach>
									              </ul>
									            </c:if>
									        </li>
									    </c:forEach>
									    </c:if>
									</ul>
								    <div class="tap-btn"><a class="tap_next" href="javascript:void(0);" onclick="commercialSelector('${topType.COMTYPE_CODE}');">下一步</a></div>
	                				</div>
								</div>
						  </div>
                        </div>
							</c:if>
                      </c:forEach>
					</div>
				</div>
			</div>
		</div>


	</div>

	<jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
