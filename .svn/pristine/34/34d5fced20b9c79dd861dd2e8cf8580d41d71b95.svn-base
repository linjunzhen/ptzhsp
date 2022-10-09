<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <meta name="renderer" content="webkit">
	<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://static.runoob.com/assets/qrcode/qrcode.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery3.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery.qrcode.min.js"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,validationegine,autocomplete,layer"></eve:resources>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.bslogo img,.rightNav,.rightNav a,.BlcT,.BlcT1,.BlcT2');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<style>
		p{
			margin-top: 0px;
			margin-bottom: 0px;
		}
	</style>
	  <style type="text/css">
　　　　.message{
　　　　　　width:285px;
　　　　　　height:30px;
　　　　　　display:none;
　　　　　　left: 777px;
　　　　　　top: 380px;
　　　　　　position: absolute;
　　　　　　z-index:1002;
　　　　　　opacity:1;
　　　　　　background:#D6D6D6;
　　　　}

　　</style>
	<jsp:include page="../visitlog/visitlogJs.jsp"></jsp:include>
	<script type="text/javascript"> 
	function hash(id){
		window.location.hash = "#"+id;
	};
	function cjwtCallpage(itemList){
		$("#cjwtList").html("");
		var newhtml = "<ul>";
		$.each( itemList, function(index, node){
			newhtml +='<li><table cellpadding="0" cellspacing="0" class="bstableli">';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">问题标题：</th>';
			newhtml +='<td colspan="2"  style="width: 800px;color: #836fff;">'+node.PROBLEM_TITLE+'</td>';
			newhtml +='</tr>';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">问题答案：</th>';
			var answer = node.ANSWER_CONTENT;
			if(node.ANSWER_CONTENT==null||node.ANSWER_CONTENT==undefined){
				answer = "";
			}
		    newhtml +=' <td width="710px">'+answer+'</td>';
			newhtml +='<td  valign="top"><span class="bscolor">'+node.LASTER_UPDATETIME+'</span></td>';
		    newhtml +=' </tr>';
			newhtml +='</table>';
		    newhtml +=' </li>';
		});
		if(null==itemList||itemList.length<=0){
			$("#cjwtPage").hide();
			newhtml += "<li style=\"text-align:center\"><font style=\"font-size:14px;\">暂无内容</font></li>";
		}
		newhtml += "</ul>";
		$("#cjwtList").html(newhtml);
	}
	
	function gzhdCallpage(itemList){
		$("#gzhdList").html("");
		var newhtml = "<ul>";
		$.each( itemList, function(index, node){
			newhtml +='<li><table cellpadding="0" cellspacing="0" class="bstableli" style="table-layout: fixed;">';
			newhtml +='<tr><th>咨询者：</th>';
            newhtml +='<td colspan="2">'+replayName(node.CREATE_USERNAME);
			newhtml +='</td>';
            newhtml +='</tr>';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">咨询内容：</th>';
			newhtml +='<td width="710px" style="word-break: break-all;">'+node.CONSULT_CONTENT+'</td>';
			newhtml +='<td valign="top"><span class="bscolor">'+node.CREATE_TIME+'</span></td>';
			newhtml +='</tr>';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">官方答复：</th>';
			newhtml +='<td width="710px" style="word-break: break-all;">'+node.REPLY_CONTENT+'</td>';
			newhtml +='<td valign="top"><span class="bscolor">'+node.REPLY_TIME+'</span></td>';
		    newhtml +=' </tr>';
			newhtml +='</table>';
		    newhtml +=' </li>';
		});
		if(null==itemList||itemList.length<=0){
			$("#gzhdPage").hide();
			newhtml += "<li style=\"text-align:center\"><font style=\"font-size:14px;\">暂无内容</font></li>";
		}
		newhtml += "</ul>";
		$("#gzhdList").html(newhtml);
		
	}   
    function fwpjCallpage(itemList){
		$("#fwpjList").html("");
		var newhtml = "<ul>";
		$.each( itemList, function(index, node){
			newhtml +='<li><table cellpadding="0" cellspacing="0" class="bstableli" style="table-layout: fixed;">';
			newhtml +='<tr><th>评价用户：</th>';
            newhtml +='<td colspan="2" style="word-break: break-all;">'+replayName(node.YHMC)+'</td>';
            newhtml +='</tr>';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">评价内容：</th>';
			newhtml +='<td colspan="2" style="word-break: break-all;">'+node.PJNR+'</td>';
			newhtml +='</tr>';
			newhtml +='<tr>';
			newhtml +='<th  valign="top">是否满意：</th>';
			if(node.SFMY==1){				
				newhtml +='<td width="710px"><font color="green">满意</font></td>';
			}else if(node.SFMY==0){
				newhtml +='<td width="710px"><font color="red">不满意</font></td>';
			}else{
				newhtml +='<td width="710px"><font color="yellow">不评价</font></td>';
			}
			newhtml +='<td valign="top"><span class="bscolor">'+node.CREATE_TIME+'</span></td>';
		    newhtml +=' </tr>';
			newhtml +='</table>';
		    newhtml +=' </li>';
		});
		if(null==itemList||itemList.length<=0){
			$("#fwpjPage").hide();
			newhtml += "<li style=\"text-align:center\"><font style=\"font-size:14px;\">暂无内容</font></li>";
		}
		newhtml += "</ul>";
		$("#fwpjList").html(newhtml);
	}   
	/**
	 * 替姓名为张**
	 */
	function replayName(name){
		if(name==null){
			return "";
		}
		name = name.replace(/(^\s*)|(\s*$)/g,"");
		var len = name.length;
		var first = name.substring(0,1);
		var sb="";
		for(var i=0;i<len-1;i++){
			sb=sb+"*";
		}
		return first+sb;
	}$(document).ready(function(){
      //加载自动补全数据
        loadAutoCompleteDatas();
        
        $("input[name='EXE_SUBBUSCLASS']:first").attr("checked",true);
        var val=$('input:radio[name="EXE_SUBBUSCLASS"]:checked').val();
        checkHandleType(val);
    });
	function loadAutoCompleteDatas() {
	    $.post("webSiteController.do?loadbsSearch", {
	    }, function(responseText, status, xhr) {
	        var resultJson = $.parseJSON(responseText);
	        $("#AutoCompleteInput").autocomplete(
	                resultJson,
	                {
	                    matchContains : true,
	                    formatItem : function(row, i, max) {
	                        //下拉框显示
	                        return "<div>" + row.ITEM_NAME+"</div>";
	                    },
	                    formatMatch : function(row) {
	                        //查询条件
	                        return row.ITEM_NAME+row.PINYIN;
	                    },
	                    formatResult : function(row, i, max) {
	                        //显示在文本框中
	                        return row.ITEM_NAME;
	                    }
	                }).result(
					function(event, data, formatted) {
						if(null!=data){							
							$("input[name='ITEM_CODE']").val(data.ITEM_CODE);
						}
					}

					);
	        });
	    }	
	function openNewWindow(){
		window.open("<%=path%>/webSiteController/allItemSearch.do?itemName="+encodeURIComponent($("#AutoCompleteInput").val()));
	}

	function openNewConsultWindow(){
		window.open("<%=path%>/consultController/viewYwzx.do?itemId=${serviceItem.ITEM_ID }&itemName="+encodeURIComponent('${serviceItem.ITEM_NAME }'));
	};
	function scsx(code){
		$.post( "bsscController/scsx.do",{itemCode:code},
			function(responseText, status, xhr) {
			  if(responseText!="websessiontimeout"){
				  var resultJson = $.parseJSON(responseText);
					  if (resultJson.success) {
						  alert(resultJson.msg);
					  } else {
						  window.location.href="<%=path%>/userInfoController/mztLogin.do?returnUrl=serviceItemController/bsznDetail.do?itemCode="+code;
					  }
				  }
			  
			}
		);
	}
	function ywzx(obj){
		if(obj==1){
			$("#wyzx").show();
		}else{
			$("#wyzx").hide();
		}
	}
	$(document).ready(function(){
	$(".hoverDiv").mouseover(function(){
		swidth=$(this).width(); //获得鼠标经过这个div的宽度
	    sheight=$(this).height();//获得鼠标经过这个div的高度度
	 soffset=$(this).offset();
	    $('.xj_text').css({'top':soffset.top,'left':soffset.left});
	    $(".xj_text").show();
	});
	$(".hoverDiv").mouseout(function(){
		$(function (){
		    setTimeout('A()', 1500); //延迟2秒
		  })
	});
	});
	function A(){
		  $(".xj_text").hide();
	}
	
	
    function checkHandleType(val){
    	var index = 1;
    	var tds = $("#materCheckList").find(".busClass");
    	tds.each(function(){
    		var tabtext = $(this).html();
    		if(tabtext.indexOf(val)=='-1'){
    			$(this).parent().attr("style","display:none");
    		}else{
    			$(this).parent().attr("style","");
    			$(this).parent().find(".materIndex").html(index);
    			index++;
    		}
    	});
    }
	</script> 
</head>

<body  style="background: #f0f0f0;">
	<div class="rightNav">
    	<a href="javascript:hash('jbxx');">基本信息</a>
        <a href="javascript:hash('blfs');">办理方式</a>
        <a href="javascript:hash('sqtj');">申请条件及说明</a>
<!--         <a href="javascript:hash('bllc');">办理流程</a> -->
        <a href="javascript:hash('lqfs');">办理结果领取方式</a>
        <a href="javascript:hash('clqd');">材料清单</a>
        <a href="javascript:hash('cjwt');">常见问题</a>
    </div>
	<%--开始编写头部文件 --%>
	<c:if test="${projectType == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectType != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=bszn" />
	</c:if>
	<%--结束编写头部文件 --%>
    
    <div class="eui-main">
		<div class="eui-crumbs">
			<ul>
				<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
				<li><a href="https://zwfw.fujian.gov.cn/indexMain?type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105">首页</a></li>
				<li><a> > 办事指南 </a></li>
			</ul>
		</div>
		<div class="bsMain1 clearfix">
        	<div class="bsMainL1" style="width: 918px;">
            	<h2 class="bstitle1">${serviceItem.CATALOG_NAME}</h2>
            	<h3 class="bstitle2">&nbsp;&nbsp;&nbsp;&nbsp;子项名称：${serviceItem.ITEM_NAME}</h3>
                <div class="Absbtn3">
	                <c:if test="${projectType == null }">
		                <c:if test="${serviceItem.RZBSDTFS!='in01' && serviceItem.RZBSDTFS!='in02'}">
							<a href="webSiteController.do?applyItem&itemCode=${serviceItem.ITEM_CODE}" class="bsbtn3"><img src="webpage/website/common/images/bsIcon15.png" />在线办理</a>					
						</c:if>	
						 <c:if test="${serviceItem.RZBSDTFS=='in02'}">
							<a href="${serviceItem.APPLY_URL}" class="bsbtn3"><img src="webpage/website/common/images/bsIcon15.png" />在线办理</a>					
						</c:if>
					</c:if>
					<a href="javascript:scsx('${serviceItem.ITEM_CODE}');" class="bsbtn3"><img src="webpage/website/common/images/bsIcon17.png" />收&nbsp;&nbsp;藏</a>
					<a href="javascript:hash('cjwt');" class="bsbtn3"><img src="webpage/website/common/images/bsIcon18.png" />常见问题</a>
				</div>
            </div>
<%--            <div class="bsMainR1">--%>
<%--            	<div class="bstitle">办事快速查询</div>--%>
<%--                <div class="bsblsrh">--%>
<%--					<input type="hidden" name="ITEM_CODE" id="itemCode">--%>
<%--					<input type="text" placeholder="请输入事项名称" class="textinput" style="width:102px" id="AutoCompleteInput" --%>
<%--                onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"/>--%>
<%--					<a href="javascript:void(0);" onclick="openNewWindow();">确 定</a>--%>
<%--				</div>--%>
<%--                <p>输入办事事项的名称，可以快速定位到对应事项的办事指南</p>--%>
<%--            </div>--%>
			<div class="bsMainR1" id="qrcode" style="width:312px; height:135px; margin-top:15px;display: flex;justify-content: center;align-items: center;"></div>
		</div>
        <style>
        .xj_text {
    position: absolute;
    top: 360px;
    left: 575px;
    display:hidden;
    background-color: #fffcc7;
    border: #ffc340 1px solid;
    padding: 8px;
    line-height: 20px;
    font-size: 12px;
    color: #222;
    width: 200px;
}
        </style>
      
　<div style="width:450px;position: absolute;display:none;" class="xj_text">
                  	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                  	<tbody><tr>
                  		<td width="40" valign="top">一星：</td>
                  		<td width="*">仅在省网上办事大厅发布办事指南信息，不提供网上服务的事项,无法在线申请。</td>
                  	</tr>
                  	<tr>
                  		<td valign="top">二星：</td>
                  		<td> 在省网上办事大厅发布办事指南信息，并提供其他网站在线申请链接的事项。</td>
                  	</tr>
                  	<tr>
                  		<td valign="top">三星：</td>
                  		<td> 在省网上办事大厅发布办事指南信息，提供在线申请、网上预审后，在窗口进行纸质收件受理，并且最多到现场次数不超过2次。</td>
                  	</tr>
                  	<tr>
                  		<td valign="top">四星：</td>
                  		<td>在省网上办事大厅发布办事指南信息，提供在线申请、网上预审、网上受理后，在窗口进行纸质核验办结的事项，并且最多到现场次数不超过1次。（领取结果时提交纸质申请材料）</td>
                  	</tr>
                  	<tr>
                  		<td valign="top">五星：</td>
                  		<td>在省网上办事大厅发布办事指南信息，提供全流程网上办理（在线申请、网上预审、网上受理、网上办结）的事项，申请人不再提交纸质申请材料，并且最多只需到现场领取结果1次。</td>
                  	</tr>
                  	</tbody></table>
				</div>
         <div class="bsbox clearfix">
        
            <div class="bsboxC" style="border-top:2px solid #426fa4;">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                    <tr>
                        <th  rowspan="2" class="hoverDiv" style="font-size：24px;text-align: center;">${serviceItem.XJ }</th>
                        <td>窗口申请，去现场次数最多${serviceItem.CTIME}</td>
                    </tr>
                    <tr>
                    	<c:if test="${serviceItem.XJ=='一星'||serviceItem.NTIME=='跑-1趟' }"><td>网上申请，去现场次数未承诺</td></c:if>
                    	<c:if test="${serviceItem.XJ!='一星'&&serviceItem.NTIME!='跑-1趟' }"><td>网上申请，去现场次数最多${serviceItem.NTIME}</td></c:if>
<%--                        <td>网上申请，去现场次数最多${serviceItem.NTIME}</td>--%>
                    </tr>
                </table>
            </div>
        </div>  
        <div class="bsbox clearfix" id="jbxx">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>基本信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                	<tr>
                    	<th>唯一码（事项编码）</th>
                        <td><c:if test="${serviceItem.CODE==null||serviceItem.CODE==''}">无</c:if>${serviceItem.CODE}</td>
                    	<th>事项性质</th>
                        <td><c:if test="${serviceItem.SXXZ==null||serviceItem.SXXZ==''}">无</c:if>${serviceItem.SXXZ}</td>
                    </tr>
                	<tr>
<!--                     	<th>行使层级</th> -->
<!--                         <td> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL==null||serviceItem.EXERCISE_LEVEL==''}"></c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='state'}">国家级</c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='province'}">省级</c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='city'}">市级</c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='county'}">县级</c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='country'}">乡镇</c:if> -->
<!--                         	<c:if test="${serviceItem.EXERCISE_LEVEL=='village'}">村/社区</c:if> -->
<!--                         </td> -->
                    	<th>申报对象</th>
                        <td ><c:if test="${serviceItem.busTypenames==null||serviceItem.busTypenames==''}">无</c:if>${serviceItem.busTypenames}</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                    	<th>办件类型</th>
                        <td><c:if test="${serviceItem.SXLX==null||serviceItem.SXLX==''}">无</c:if>${serviceItem.SXLX}</td>
                    	<th>数量限制说明</th>
                        <td><c:if test="${serviceItem.SLXZSM==null||serviceItem.SLXZSM==''}">无</c:if>${serviceItem.SLXZSM}</td>
                    </tr>
<!--                     <tr> -->
<!--                     	<th>权责清单</th> -->
<!--                         <td><c:if test="${serviceItem.SUBITEM_NAME==null||serviceItem.SUBITEM_NAME==''}"></c:if>${serviceItem.SUBITEM_NAME}</td> -->
<!--                     	<th>权力来源</th> -->
<!--                         <td> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE==null||serviceItem.RIGHT_SOURCE==''}"></c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='01'}">法定本级行使</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='02'}">上级授权</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='03'}">同级授权</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='04'}">上级委托</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='05'}">同级委托</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='06'}">上级下放</c:if> -->
<!--                         	<c:if test="${serviceItem.RIGHT_SOURCE=='07'}">${serviceItem.RIGHT_SOURCE_OTHER}</c:if> -->
<!--                         </td> -->
<!--                     </tr> -->
                    <tr>                    	
                        <th>承诺时限(工作日)</th>
                        <td><c:if test="${(serviceItem.CNQXGZR==0||serviceItem.CNQXGZR=='')&&serviceItem.SXLX!='即办件'}">无</c:if>
	                        <c:if test="${serviceItem.SXLX=='即办件'}">当日办结</c:if>
							<c:if test="${serviceItem.CNQXGZR!=null&&serviceItem.CNQXGZR!=''&&serviceItem.SXLX!='即办件'}">
								${serviceItem.CNQXGZR}
							</c:if>
						</td>
                        <th>时限说明</th>
                        <td><c:if test="${serviceItem.CNQXSM==null||serviceItem.CNQXSM==''}">无</c:if>${serviceItem.CNQXSM}</td>
                        
                     <tr>
                        <th>法定时限</th>
                        <td>
                        ${serviceItem.FDSXGZR}
                        </td>
                        <th>法定时限说明</th>
                        <td>
	                        <c:if test="${(serviceItem.FDQX==null||serviceItem.FDQX=='')&&serviceItem.SXLX!='即办件'}">无</c:if>
	                        <c:if test="${serviceItem.SXLX=='即办件'}">当日办结</c:if>
	                        <c:if test="${serviceItem.FDQX!=null&&serviceItem.FDQX!=''&&serviceItem.SXLX!='即办件'}">
	                        	${serviceItem.FDQX}
	                        </c:if>
                        </td>
                     </tr>   
<!--                     </tr> -->
<!--                         <th>通办范围</th> -->
<!--                         <td> -->
<!--                         	<c:if test="${serviceItem.TBFW==null||serviceItem.TBFW==''}">无</c:if> -->
<!--                         	<c:if test="${serviceItem.TBFW=='01'}">全国</c:if> -->
<!--                         	<c:if test="${serviceItem.TBFW=='02'}">跨省</c:if> -->
<!--                         	<c:if test="${serviceItem.TBFW=='03'}">跨市</c:if> -->
<!--                         	<c:if test="${serviceItem.TBFW=='04'}">跨县</c:if> -->
<!--                         </td> -->
<!--                         <th>运行办理系统</th> -->
<!--                         <td> -->
<!--                         	<c:if test="${serviceItem.SYSTEM_LEVEL==null||serviceItem.SYSTEM_LEVEL==''}">无</c:if> -->
<!--                         	<c:if test="${serviceItem.SYSTEM_LEVEL=='01'}">国家级</c:if> -->
<!--                         	<c:if test="${serviceItem.SYSTEM_LEVEL=='02'}">省级</c:if> -->
<!--                         	<c:if test="${serviceItem.SYSTEM_LEVEL=='03'}">市级</c:if> -->
<!--                         	<c:if test="${serviceItem.SYSTEM_LEVEL=='04'}">本系统</c:if> -->
<!--                         </td> -->
<!--                     </tr> -->
<!--                     <tr> -->
<!--                         <th>联办机构</th> -->
<!--                         <td colspan="3"> -->
<!-- 							<c:if test="${serviceItem.LBJG==null||serviceItem.LBJG==''}"></c:if>${serviceItem.LBJG} -->
<!--                         </td> -->
<!--                     </tr> -->
                    </tr>
                        <th>是否收费</th>
                        <td><c:if test="${serviceItem.SFSF==null||serviceItem.SFSF==''}">无</c:if>${serviceItem.SFSF}</td>
                        <th>缴费方式</th>
                        <td><c:if test="${serviceItem.SFFS==null||serviceItem.SFFS==''}">无</c:if>${serviceItem.SFFS}</td>
                    </tr>
                    <tr>
                        <th>收费标准及依据</th>
                        <td colspan="3">
                        <c:choose>  
                        <c:when test="${serviceItem.SFBZJYJ==null||serviceItem.SFBZJYJ==''||serviceItem.SFSF=='否'}">无</c:when>
                        <c:otherwise>
                        ${serviceItem.SFBZJYJ}
                        </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr> 
						<th>主办处室</th>
                        <td><c:if test="${serviceItem.ZBCS==null||serviceItem.ZBCS==''}">无</c:if>${serviceItem.ZBCS}</td>
                        <th>监督电话</th>
                        <td><c:if test="${serviceItem.JDDH==null||serviceItem.JDDH==''}">无</c:if>${serviceItem.JDDH}</td>
                    </tr>
                    <tr>
                    	<th>联系人</th>
                        <td><c:if test="${serviceItem.LXR==null||serviceItem.LXR==''}">无</c:if>${serviceItem.LXR}</td>                       
                    	<th>联系电话</th>
                        <td><c:if test="${serviceItem.LXDH==null||serviceItem.LXDH==''}">无</c:if>${serviceItem.LXDH}</td>
                    </tr>
<!--                     <tr> -->
<!--                     <th>实施主体</th> -->
<!--                         <td><c:if test="${serviceItem.SSBMBM==null||serviceItem.SSBMBM==''}">无</c:if>${serviceItem.SSBMBM}</td> -->
<!--                     	<th>实施主体性质</th> -->
<!--                         <td> -->
<!--                         	<c:if test="${serviceItem.IMPL_DEPART_XZ==null||serviceItem.IMPL_DEPART_XZ==''}"></c:if> -->
<!--                         	<c:if test="${serviceItem.IMPL_DEPART_XZ=='01'}">法定机关</c:if> -->
<!--                         	<c:if test="${serviceItem.IMPL_DEPART_XZ=='02'}">授权组织</c:if> -->
<!--                         	<c:if test="${serviceItem.IMPL_DEPART_XZ=='03'}">受委托组织</c:if> -->
<!--                         </td> -->
<!--                     </tr> -->
<!--                     <tr> -->
<!--                     <th>审批结果名称</th> -->
<!--                         <td><c:if test="${serviceItem.RESULT_NAME==null||serviceItem.RESULT_NAME==''}"></c:if>${serviceItem.RESULT_NAME}</td> -->
<!--                     	<th>审批结果样本</th> -->
<!--                         <td> -->
<!-- 								<c:if test="${serviceItem.RESULT_PATH != ''&& serviceItem.RESULT_PATH !=null}"> -->
<!-- 									<a href="javascript:AppUtil.downLoadFile('${serviceItem.RESULT_PATH}');" class="tablebtn1">样表下载</a></br></br> -->
<!-- 								</c:if> -->
<!--                         </td> -->
<!--                     </tr> -->
<!--                     <tr> -->
<!--                     <th>前置审批</th> -->
<!--                         <td><c:if test="${serviceItem.PREAPPROVAL_NAME==null||serviceItem.PREAPPROVAL_NAME==''}">无</c:if>${serviceItem.PREAPPROVAL_NAME}</td> -->
<!--                     <th>中介服务</th> -->
<!--                         <td><c:if test="${serviceItem.AGENCYSERVICE_NAME==null||serviceItem.AGENCYSERVICE_NAME==''}">无</c:if>${serviceItem.AGENCYSERVICE_NAME}</td> -->
<!--                     </tr> -->
                    <tr>
                    	<th>备注</th>
                        <td colspan="3"><c:if test="${serviceItem.BZSM==null||serviceItem.BZSM==''}">无</c:if>${serviceItem.BZSM}</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="bsbox clearfix" id="sqtj">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>申请条件及说明</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<div class="bsCdetial">
                	<c:if test="${serviceItem.SQTJ==null||serviceItem.SQTJ==''}">无</c:if>${serviceItem.SQTJ}
                </div>
            </div>
        </div>
<!--         <div class="bsbox clearfix" id="sqfs"> -->
<!--         	<div class="bsboxT"> -->
<!--             	<ul> -->
<!--                 	<li class="on" style="background:none"><span>申请方式</span></li> -->
<!--                 </ul> -->
<!--             </div> -->
<!--             <div class="bsboxC"> -->
<!--             	<table cellpadding="0" cellspacing="0" class="bstable2"> -->
<!--                 	<tr> -->
<!--                 		<th width="330px">申请方式 </th> -->
<!--                 		<th width="230px">到窗口最多次数（含领取结果） </th> -->
<!--                 		<th width="130px">账户要求 </th> -->
<!--                 		<th width="230px">承诺到窗口最多次数说明 </th> -->
<!--                     </tr> -->
<!--                     <c:if test="${serviceItem.CKCS_2!=null&&serviceItem.CKCS_2!=''&&serviceItem.CKCS_2!='-1'}"> -->
<!--                 	<tr> -->
<!--                         <td>【三星】网上申请和预审，窗口纸质材料收件受理（收到纸质材料后才能受理） </td> -->
<!--                         <td>${serviceItem.CKCS_2}</td> -->
<!--                         <td> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_2==null||serviceItem.sqzhyq_2==''}">无</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_2=='1'}">普通注册账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_2=='2'}">实名认证账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_2=='3'}">CA账号</c:if> -->
<!--                         </td> -->
<!--                         <td>${serviceItem.cnsm_2}</td> -->
<!--                     </tr> -->
<!--                     </c:if> -->
<!--                     <c:if test="${serviceItem.CKCS_3!=null&&serviceItem.CKCS_3!=''&&serviceItem.CKCS_3!='-1'}"> -->
<!--                 	<tr> -->
<!--                         <td>【四星】网上申请、预审和受理，窗口纸质核验办结  </td> -->
<!--                         <td>${serviceItem.CKCS_3}</td> -->
<!--                         <td> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_3==null||serviceItem.sqzhyq_3==''}">无</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_3=='1'}">普通注册账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_3=='2'}">实名认证账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_3=='3'}">CA账号</c:if> -->
<!--                         </td> -->
<!--                         <td>${serviceItem.cnsm_3}</td> -->
<!--                     </tr> -->
<!--                     </c:if> -->
<!--                     <c:if test="${serviceItem.CKCS_4!=null&&serviceItem.CKCS_4!=''&&serviceItem.CKCS_4!='-1'}"> -->
<!--                 	<tr> -->
<!--                         <td>【五星】提供全流程网上办理，申请人在办结后到窗口领取结果   </td> -->
<!--                         <td>${serviceItem.CKCS_4}</td> -->
<!--                         <td> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_4==null||serviceItem.sqzhyq_4==''}">无</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_4=='1'}">普通注册账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_4=='2'}">实名认证账号</c:if> -->
<!--                 			<c:if test="${serviceItem.sqzhyq_4=='3'}">CA账号</c:if> -->
<!--                         </td> -->
<!--                         <td>${serviceItem.cnsm_4}</td> -->
<!--                     </tr> -->
<!--                     </c:if> -->
<!--                 </table> -->
<!--             </div> -->
<!--         </div> -->
        <div class="bsbox clearfix" id="blyj">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>办理依据</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<div class="bsCdetial">
                	<c:if test="${serviceItem.XSYJ==null||serviceItem.XSYJ==''}">无</c:if>${serviceItem.XSYJ}
                </div>
            </div>
        </div>
        <div class="bsbox clearfix" id="blfs">
        	<div class="bsboxT">
            	<ul>
                	<li><a href="javascript:void(0);">窗口办理方式</a></li>
                    <li style="background:none"><a href="javascript:void(0);">网上办理方式</a></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<div>
                	<table cellpadding="0" cellspacing="0" class="bstable1">
                    	<tr>
                            <th>办理窗口</th>
                            <td><c:if test="${serviceItem.SSZTMC==null||serviceItem.SSZTMC==''}">无</c:if>${serviceItem.SSZTMC}</td>
                        </tr>
                        <tr>
                            <th>受理时间</th>
                            <td><c:if test="${serviceItem.BGSJ==null||serviceItem.BGSJ==''}">无</c:if>${serviceItem.BGSJ}</td>
                        </tr>
                        <tr>
                            <th>受理地点</th>
                            <td><c:if test="${serviceItem.BLDD==null||serviceItem.BLDD==''}">无</c:if>${serviceItem.BLDD}</td>
                        </tr>
                        <tr>
                            <th>交通指引</th>
                            <td><c:if test="${serviceItem.TRAFFIC_GUIDE==null||serviceItem.TRAFFIC_GUIDE==''}">无</c:if>${serviceItem.TRAFFIC_GUIDE}</td>
                        </tr>
                    </table>
                </div>    
				<div>
                	<table cellpadding="0" cellspacing="0" class="bstable1">
                    	<tr>
                            <th>办理方式</th>
                            <td ><c:if test="${serviceItem.WSSQFS==null||serviceItem.WSSQFS==''}"></c:if>${serviceItem.WSSQFS}
                            
                            <c:if test="${serviceItem.RZBSDTFS!='in01' && serviceItem.RZBSDTFS!='in02'}">
					<a href="webSiteController.do?applyItem&itemCode=${serviceItem.ITEM_CODE}" class="bsbtn33"><img src="webpage/website/common/images/bsIcon15.png" />在线办理</a>					
				</c:if>	
				 <c:if test="${serviceItem.RZBSDTFS=='in02'}">
					<a href="${serviceItem.APPLY_URL}" class="bsbtn33"><img src="webpage/website/common/images/bsIcon15.png" />在线办理</a>					
				</c:if>	
				 <c:if test="${serviceItem.RZBSDTFS=='in01'}">
					&nbsp;无				
				</c:if>	
                            
                            </td>
                            
                        </tr>
                    </table>
                </div> 				
            </div>
        </div>
<%--         <c:if test="${serviceItem.BDLCDYID!=null}">
			<!-- 开始引入办理流程图jsp -->
			<jsp:include page="bsznbllc.jsp" >
	           <jsp:param value="${serviceItem.BDLCDYID}" name="defId"/>
	        </jsp:include>
	        <!-- 开始引入办理流程图jsp -->
        </c:if> --%>
		<c:if test="${!empty specialLink}">
        <div class="bsbox clearfix" id="lqfs">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>特殊环节</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<div>
                	<table cellpadding="0" cellspacing="0" class="bstable2">
                    	<tr>
                        	<th width="140px">环节名称</th>
                            <th width="135px">经办人</th>
                            <th width="135px">时限</th>
                            <th width="525px">环节依据</th>
                        </tr>
						<c:forEach items="${specialLink}" var="link" varStatus="s">
                        <tr>
                        	<td>${link.LINK_NAME}</td>
                        	<td>${link.OPERATOR_NAME}</td>
                            <td style="text-align:centen">
							<c:if test="${link.LINK_LIMITTIME==0}">无期限</c:if>
							<c:if test="${link.LINK_LIMITTIME!=0}">${link.LINK_LIMITTIME}个工作日</c:if>
							</td>
                            <td style="text-align:left">${link.LINK_BASIS}</td>
                        </tr>
						</c:forEach>
                    </table>
                </div>
            </div>
        </div> 
        </c:if>
		
        <div class="bsbox clearfix" id="lqfs">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>办理结果领取方式</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                    <tr>
                        <th>支持方式</th>
                        <td><c:if test="${serviceItem.FINISH_GETTYPE==null||serviceItem.FINISH_GETTYPE==''}">无</c:if>${serviceItem.FINISH_GETTYPE}</td>
                    </tr>
                </table>
            </div>
        </div>  
        <div class="bsbox clearfix" id="lqfs">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>纸质材料提交方式</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                    <tr>
                        <th>支持方式</th>
                        <td><c:if test="${serviceItem.PAPERSUB==null||serviceItem.PAPERSUB==''}">无</c:if>${serviceItem.PAPERSUB}</td>
                    </tr>
                </table>
            </div>
        </div>  
		<div class="bsbox clearfix" id="clqd">
        	<div class="bsboxT">
            	<ul>
                	<li><a href="javascript:void(0);">材料清单</a></li> 
					<li style="background:none"><a href="javascript:void(0);">法律依据</a></li>
                </ul>
            </div>
   <style>
		.bstable2{width:100%; font-size:14px; border-collapse:collapse; table-layout:fixed;}
        .bstable2 tr th{height:47px; line-height:47px; color:#434343; text-align:center; background:#f8f9fa; border:1px dotted #a0a0a0;}
        .bstable2 tr td{color:#434343; line-height:21px; text-align:center; border:1px dotted #a0a0a0; word-break:break-all; padding:0 6px;}
        .tablebtn1{display:inline-block; width:70px; height:26px; line-height:26px; text-align:center; margin:0 3px; color:#fff;}
	</style>
            <div class="bsboxC">
            	<div>
	            	<table cellpadding="0" cellspacing="1" class="bstable2">
					    <c:if test="${serviceItem.handleTypes!=null&&fn:length(serviceItem.handleTypes)>0}">
							<tr style="height: 50px;">
								<td width="220px"> 业务办理子项：</td>
								<td style="text-align: left;">
									<c:forEach items="${serviceItem.handleTypes}" var="handleType">
										<input type="radio" name="EXE_SUBBUSCLASS" onclick="checkHandleType(this.value)" value="${handleType.bus_handle_type }">${handleType.bus_handle_type }&nbsp;
									</c:forEach>
								</td>
							</tr>
						</c:if>
					</table>
                	<table cellpadding="0" cellspacing="0" class="bstable2" id="materCheckList">
                	
                    	<tr>
                        	<th width="47px">序号</th>
<%--                            <th width="96px">材料编码</th>--%>
                            <th width="184px">材料名称</th>
                            <th width="120px">材料要求</th>
                            <th width="240px">材料说明</th>
                            <th>材料来源</th>
                            <th>是否必须提供</th>
                            <th>是否支持电子<br/>证照共享获取</th>
                            <th width="90px">操作</th>
                        </tr>
						<c:forEach items="${serviceItem.applyMaters}" var="maters" varStatus="vs">
                        <tr>
                        	<td class="materIndex">${vs.index+1}</td>
                            <!--<td><c:if test="${maters.CLLX==null||maters.CLLX==''}">无</c:if>${maters.CLLX}</td>-->
<%--                            <td style="text-align:left">${maters.MATER_CODE}</td>--%>
                            <td style="text-align:left" <c:if test="${maters.BUSCLASS_NAME!=null&&maters.BUSCLASS_NAME!=''}">class="busClass"</c:if>>
                            <c:if test="${maters.BUSCLASS_NAME==null||maters.BUSCLASS_NAME==''||maters.BUSCLASS_NAME=='无'}">${maters.MATER_NAME}</c:if>
							<c:if test="${maters.BUSCLASS_NAME!=null&&maters.BUSCLASS_NAME!=''&&maters.BUSCLASS_NAME!='无'}">【${maters.BUSCLASS_NAME}】${maters.MATER_NAME}</c:if>
                            </td>
							<td style="text-align:center">
								<c:if test="${maters.PAGECOPY_NUM==null&&maters.PAGE_NUM==null}">
									<c:if test="${maters.MATER_CLSMLX==null||maters.MATER_CLSMLX==''}">无</c:if>
									<c:if test="${maters.MATER_CLSMLX=='复印件'}">原件1份&nbsp;</c:if>
									<c:if test="${maters.MATER_CLSMLX!=null&&maters.MATER_CLSMLX!=''}">${maters.MATER_CLSMLX}${maters.MATER_CLSM}份</c:if>
								</c:if>
								<c:if test="${maters.PAGECOPY_NUM!=null||maters.PAGE_NUM!=null}">
									<c:if test="${maters.PAGECOPY_NUM!=null}">复印件${maters.PAGECOPY_NUM}份</br></c:if>
									<c:if test="${maters.PAGE_NUM!=null}">原件${maters.PAGE_NUM}份</br>${maters.GATHERORVER}</br></c:if>
									<c:if test="${fn:indexOf(maters.MATER_CLSMLX, '电子文档') != -1}">电子文档</c:if>
								</c:if>
							</td>
							<td style="text-align:left">
								<c:if test="${maters.MATER_DESC==null||maters.MATER_DESC==''}">无</c:if>
								<c:if test="${maters.MATER_DESC!=null&&maters.MATER_DESC!=''}">${maters.MATER_DESC}</c:if>
							</td>
							<td style="text-align:left">
								<c:if test="${maters.MATER_SOURCE_TYPE==null||maters.MATER_SOURCE_TYPE==''}">无</c:if>
								<c:if test="${maters.MATER_SOURCE_TYPE=='本单位'}">本单位</c:if>
								<c:if test="${maters.MATER_SOURCE_TYPE=='非本单位'&&maters.MATER_SOURCE!=null&&maters.MATER_SOURCE!=''}">${maters.MATER_SOURCE}</c:if>
							</td>
							<td style="text-align:left">
								<c:if test="${maters.MATER_ISNEED==null||maters.MATER_ISNEED==''||maters.MATER_ISNEED=='0'}">否</c:if>
								<c:if test="${maters.MATER_ISNEED=='1'}">是</c:if>
							</td>
							<td style="text-align:left">
								<c:if test="${fn:contains(maters.COLLECT_METHOD,'03')}">是</c:if>
<!-- 								<c:if test="${!fn:contains(maters.COLLECT_METHOD,'03')}">否</c:if> -->
							</td>
                            <td>
								<c:if test="${maters.MATER_PATH != ''&& maters.MATER_PATH !=null}">
									<a href="javascript:AppUtil.downLoadFile('${maters.MATER_PATH}');" class="tablebtn1">模板下载</a></br></br>
								</c:if>
								<c:if test="${maters.MATER_PATH2 != ''&& maters.MATER_PATH2 !=null}">
									<a href="javascript:AppUtil.downLoadFile('${maters.MATER_PATH2}');" class="tablebtn1">样表下载</a>
								</c:if>
							</td>
                        </tr>
						</c:forEach>
                    </table>
                </div>
				<div>
                	<div class="bsCdetial">
						<c:if test="${serviceItem.XSYJ==null||serviceItem.XSYJ==''}">无</c:if>${serviceItem.XSYJ}
					</div>
                </div>
            </div>
        </div>
        <div class="bsbox clearfix">
        	<div class="bsboxT">
            	<ul>
                	<li class="on" style="background:none"><span>其他信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
            	<table cellpadding="0" cellspacing="0" class="bstable1">
                    <tr>
                        <th>最后更新时间</th>
                        <td>${serviceItem.UPDATE_TIME}</td>
                    </tr>
                </table>
            </div>
        </div>  
        <div class="bsbox clearfix" id="cjwt">
        	<div class="bsboxT">
            	<ul>
                    <li><a href="javascript:void(0);">常见问题</a></li>
                    <li style="background:none"><a href="javascript:void(0);">服务评价</a></li>
                </ul>
            </div>
            <div class="bsboxC">     
                <div>
                	<div class="xxlist" id="cjwtList">                    	
                    </div>
					<div class="page" id="cjwtPage">
						<%--开始引入分页JSP --%>
						<jsp:include page="../common/page.jsp" >
						<jsp:param value="commonProblemController/pagelist.do?itemId=${serviceItem.ITEM_ID}" name="pageURL"/>
						<jsp:param value="cjwtCallpage" name="callpage"/>
						<jsp:param value="5" name="limitNum"/>
						<jsp:param value="cjwt" name="morename"/>
						</jsp:include>
						<%--结束引入分页JSP--%>
					</div>
                </div>
                <div>
                	<div class="xxlist" id="fwpjList">                    
                    </div>
					<div class="page" id="fwpjPage">
						<%--开始引入分页JSP --%>
						<jsp:include page="../common/page.jsp" >
						<jsp:param value="bspjController/bsznPagelist.do?itemCode=${serviceItem.ITEM_CODE}" name="pageURL"/>
						<jsp:param value="fwpjCallpage" name="callpage"/>
						<jsp:param value="5" name="limitNum"/>
						<jsp:param value="fwpj" name="morename"/>
						</jsp:include>
						<%--结束引入分页JSP--%>
					</div>
                </div>
            </div>
       
        </div>  
		<div class="Consul_publish" id="wyzx" <c:if test="${null==sessionScope.curLoginMember}">style="display:none;"</c:if>>
			<form id="consultForm" method="post" action="consultController/saveConsult.do">	
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="CONSULT_DEPTID" value="${serviceItem.deptId}">
				<input type="hidden" name="CONSULT_DEPT" value="${serviceItem.SSBMBM}">
				<input type="hidden" name="CONSULT_ITEMID" value="${serviceItem.ITEM_ID}">
				<input type="hidden" name="CONSULT_ITEMS" value="${serviceItem.ITEM_NAME}">
				<input type="hidden" name="CONSULT_TYPE" value="0">
				<input type="hidden" name="GSRF" value="${GSRF}">
				<input type="hidden" name="CONSULT_TITLE" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}">
				<!--==========隐藏域部分结束 ===========-->
				<div class="pub_title">
					<img src="webpage/website/common/images/wyhys_wz.png" />
				</div>
				<div class="pub_con">
					<div class="pub_left">
						<div class="pub_name">
							<img src="webpage/website/common/images/publish_name.png" />
							<p>
							${sessionScope.curLoginMember.YHMC}
							</p>
						</div>
					</div>
					<div class="pub_right">
						<textarea style="width:809px; height:136px; font-size:14px;" class="validate[required],maxSize[500]"  name="CONSULT_CONTENT"></textarea>
						<div class="pub_btn">
						
						<a href="javascript:ywzx(0);" style="color: #b3b3b3;font-size: 14px;margin-right: 10px;">取消咨询</a>
						<c:if test="${null!=sessionScope.curLoginMember}">
							<a href="javascript:void(0);" onclick="$('#consultForm').submit();" class="btn2">发表</a>
						</c:if>
						</div>
					</div>
				</div>
			</form>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
    <script type="text/javascript">jQuery(".bsbox").slide({titCell:".bsboxT li",mainCell:".bsboxC"})</script>
    <script type="text/javascript"> 
    var qrcode = new QRCode(document.getElementById("qrcode"), {
    	width : 100,
    	height : 100
    });
    function makeCode () {
    	var elText = __ctxPath + "/serviceItemController/bsznDetail.do?itemCode=${serviceItem.ITEM_CODE}";
    	qrcode.makeCode(elText);
    }
    makeCode();
		$(function() {
			AppUtil.initWindowForm("consultForm", function(form, valid) {
				if (valid) {
					var formData = $("#consultForm").serialize();
					var url = $("#consultForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
								resetForm();
							} else {
								art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});								
							}
						}
					});
				}
			}, "T_WSBS_SERVICEITEM");
		});
		//右侧导航
		var rightNav=$(".rightNav");
		var isIE6 = !!window.ActiveXObject&&!window.XMLHttpRequest;
		$(window).scroll(function(){ 
			if(isIE6){ rightNav.css("top", $(document).scrollTop()+192+"px")}
		});
		//窗口改变大小事件
		$(window).resize(function() {
			var width = $(this).width();
			windowLink(width);
		});
		//初始化右边滚动位置
		windowLink($(window).width());
		resetScroll();
		function windowLink(width){
			rightNav.css("right","");
			var linkWidth = parseInt(rightNav.css("width"));
			var containerWidth = parseInt($(".container").css("width"));
			var linkLeft = (Number(width) - Number(containerWidth))/2 + Number(containerWidth)-30;
			rightNav.css("left",(Number(linkLeft)+"px"));
		}
		function resetScroll(){
			var scrollTop = $(document).scrollTop();
			if(Number(scrollTop)<=Number(192)){
				rightNav.css("top",Number(192)-Number(scrollTop)+"px");
			}else{
				var height = ($(window).height()-288)/2;
				if(Number(height)<=167){
					height = ($(window).height()-288)-130;
				}
				rightNav.css("top",height+"px");
			}
		}
		function resetForm(){
			$("textarea[name='CONSULT_CONTENT']").val("");		
		}
	</script> 
</body>
</html>
