<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">
	$(function(){
      	$(".treelist1>ul>li").each(function(){
        	$(this).attr('zon',$(this).find("li").length);
        	$(this).attr('xuan',$(this).find("li.onon").length);
        	var tt=$(this);
        	$(this).find('p').bind('click',function(){
          		var temp=0;
          		if(tt.attr('zon')>tt.attr('xuan')){
            		temp=1;
          		}
		        if(tt.attr('zon')==tt.attr('xuan')){
		           	temp=2;
		        }
          		if(temp==1){
            		tt.find('li').each(function(){
              			$(this).attr('check','true');
              			$(this).removeClass('onon').addClass('onon');
              			tt.removeClass('on onon').addClass('onon');
            		});
            		tt.attr('xuan',tt.attr('zon'));
          		}
          		if(temp==2){
            		tt.find('li').each(function(){
	              		$(this).attr('check','false');
	              		$(this).removeClass('onon');
	              		tt.removeClass('on onon');
            		});
            		tt.attr('xuan',0);
          		}
       		});
			$(this).find('li').bind('click', function() {
				if ($(this).attr('check') == 'true') {
					$(this).removeClass('onon');
					$(this).attr('check', 'false');
					if (tt.attr('zon') == tt.attr('xuan') && tt.attr('zon') > 1) {
						tt.removeClass('onon').addClass('on');
					}
					tt.attr('xuan', parseInt(tt.attr('xuan')) - 1);
					if (tt.attr('xuan') == 0) {
						tt.removeClass('on onon');
					}
				} else {
					$(this).addClass('onon');
					$(this).attr('check', 'true');
					tt.attr('xuan', parseInt(tt.attr('xuan')) + 1);
					tt.addClass('on');
					if (tt.attr('zon') == tt.attr('xuan')) {
						tt.removeClass('on onon').addClass('onon');
					}
				}
			});
		});
	});

	jQuery(".eui-menu").slide({
		type : "menu", //效果类型
		titCell : ".syj-location", // 鼠标触发对象
		targetCell : ".syj-city", // 效果对象，必须被titCell包含
		delayTime : 0, // 效果时间
		defaultPlay : false, //默认不执行
		returnDefault : true // 返回默认
	});
	jQuery(".syj-tyys").slide({
		trigger : "click"
	});
	
	String.prototype.Trim = function() 
    { 
        return this.replace(/(^\s*)|(\s*$)/g, ""); 
    };
	function toCommercial(){
		var itemCode = "";
		var itemName = "";
		$(".treelist1>ul>li").each(function(){
			var tt=$(this);
			tt.find('li').each(function(){
       			if($(this).attr('check')){
       				itemCode += $(this).attr("date-value")+",";
       				itemName += $(this).text().Trim()+",";
       			}
     		});
		});
		if(itemCode==""){			
			art.dialog({
				content: "请选择1+N证合一事项！",
				icon:"error",
				ok: true
			});
			return;
		}
		itemCode = itemCode.substring(0, itemCode.length-1);
		itemName = itemName.substring(0, itemName.length-1);
		$("input[name='itemCodes']").val(itemCode);
		$("input[name='itemNames']").val(itemName);
		$("#RELATED_FORM").submit();
	}
</script>

</head>

<body>

	<jsp:include page="/webpage/website/zzhy/head.jsp?currentNav=wssb" />
	<div style="float:left; width:100%;">

		<div class="container">
			<div class="syj-crumb">
				<span>所在位置：</span><a
					href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a>
				> <i>网上申报</i>
			</div>
		</div>

		<div class="container"
			style=" overflow:hidden; margin-bottom:20px;background:url(<%=path%>/webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
			<div class="net-detail">
				<div class="syj-tyys">
					<div class="hd syj-tabtitle">
						<ul>
							<li><a href="javascript:void(0)">1+N证合一</a></li>
						</ul>
					</div>
					<div class="bd">

						<div class="related">
		  					<form id="RELATED_FORM" action="<%=path%>/webSiteController.do?zzhywssb" method="post">
		  						<input type="hidden" name="itemNames"/>
		  						<input type="hidden" name="itemCodes"/>
								<div class="top-tap">
									<div class="top-container">
										<div class="treelist1" id="wzqy0">
											<ul>
												<e:for filterid="1" end="100" var="efor" dsid="155">
													<li date-item='1' date-value="${efor.DEPART_CODE}"><p>${efor.DEPART_NAME}</p>
														<e:for filterid="${efor.DEPART_CODE}" end="100" var="efor1" dsid="156">
															<ul>
									                        	<li date-item='2' date-value="${efor1.ITEM_CODE}">${efor1.ITEM_NAME}
									                        	<div class="line_r"><img src="<%=path%>/webpage/website/zzhy/images/line_r.png" /></div>
									                        	<div class="line_c"><img src="<%=path%>/webpage/website/zzhy/images/line_c.png" /></div></li>
									
									                        </ul>
														</e:for>
													</li>
												</e:for>
											</ul>
											<div class="tap-btn">

												<a class="tap_next" href="javascript:;" onclick="toCommercial();">下一步</a>
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
