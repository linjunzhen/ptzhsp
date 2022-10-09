<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!--[if lte IE 6]> 
	<script src="js/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.bslogo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<script type="text/javascript">
		function callpage(itemList){
		    $("#listContainer").html("");
		    var newhtml = "<ul>";
		    for(var i=0; i<itemList.length; i++){
		    newhtml += "<li><a target=\"_blank\" href=\"serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE+"\" >"+itemList[i].ITEM_NAME+"</a></li>"
		    } 
		     newhtml += "</ul>";
		    $("#listContainer").html(newhtml);
		    jskzx();
		}
		function loadAutoCompleteDatas() {
			var btype = $("#busType").val();
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
		                }).result(function(event, data, formatted) {//如选择后给其他控件赋值，触发别的事件等等  
		                	reload();
		                }); ;
		        });
		    }
		function changeDiv(type){
			changeClass(type);
			changeBigDiv(type);
			$("#AutoCompleteInput").val("");
			$("#busType").val(type);
			removeAllSelect();
			jskzx();
			reload();
		}
		function changeClass(type){
			$("#grbsli").removeClass("on");
			$("#frbsli").removeClass("on");
			$("#bmbsli").removeClass("on");
			if(type=="grbs"){
				$("#grbsli").addClass("on");
			}else if(type=="frbs"){
				$("#frbsli").addClass("on");
            }else if(type=="bmbs"){
            	$("#bmbsli").addClass("on");
            }
		}
		function changeBigDiv(type){
			$("#grbsdiv").css("display","none");
            $("#frbsdiv").css("display","none");
            $("#bmbsdiv").css("display","none");
            if(type=="grbs"){
            	$("#grbsdiv").css("display","");
            }else if(type=="frbs"){
            	$("#frbsdiv").css("display","");
            }else if(type=="bmbs"){
            	$("#bmbsdiv").css("display","");
            }
        }
</script>
</head>

<body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="../bsdt/head.jsp" />
    <%--结束编写头部文件 --%>
    
    <div class="container">
    	<div class="current"><span>您现在所在的位置：</span><a href="#">首页</a> > <i>办事指南</i></div>
    	<div class="bsMain clearfix">
        	<div class="bshome">
            	<div class="bshTitle">
                	<ul>
                    	<li id="grbsli" class="on" ><a href="javascript:void(0)" onclick="changeDiv('grbs');">个人办事</a></li>
                        <li id="frbsli" class=""><a href="javascript:void(0)" onclick="changeDiv('frbs')">法人办事</a></li>
                        <li id="bmbsli" calss=""><a href="javascript:void(0)" onclick="changeDiv('bmbs')">部门服务</a></li>
                    </ul>
                </div>
                <form id="znform" action="javascript:void(0);">
                <div class="bsSearch">
                <input type="text" id="AutoCompleteInput" 
                onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" 
                name="ITEM_NAME"   /><a href="javascript:void(0);" onclick="reload();" >检 索</a>
                <input type="hidden" id="busType" name="busType" value="grbs" />
                <input type="hidden" id="busTypeId" name="busTypeIds" value=""/> 
                </div>
                <div class="bshCon">
                	<div id="grbsdiv">
                    	<%--开始引入查下界面 --%>
                        <jsp:include page="./znsearch.jsp" >
                        <jsp:param value="grbs" name="busType"/>
                        </jsp:include>
                        <%--结束引入查下界面 --%>
                    </div>
                    <div id="frbsdiv" style="display: none;">
                    	<%--开始引入查下界面 --%>
                        <jsp:include page="./znsearch.jsp" >
                        <jsp:param value="frbs" name="busType"/>
                        </jsp:include>
                        <%--结束引入查下界面 --%>
                    </div>
                    <div id="bmbsdiv" style="display: none;">
                        <%--开始引入查下界面 --%>
                        <jsp:include page="./znsearch.jsp" >
                        <jsp:param value="bmbs" name="busType"/>
                        </jsp:include>
                        <%--结束引入查下界面 --%>
                    </div>
                </div>
                </form>
                <div class="borderpad clearfix">
				    <ul class="dlesearch">
				        <li><p>已选条件：</p>
				            <div id="yxtj">
				                
				            </div>
				            <a herf="javascript:void(0);" onclick="removeAllSelect()" 
				            style="cursor: pointer;" class="btndelete">删除条件</a></li>
				    </ul>
				</div>
                <div class="listinfo">
				    <span class="lfloat">办事指南：</span><span
				        class="rfloat">共 <i id="zgbsts">0</i> 个服务事项，其中 <i id="kzxbsts">0</i> 个可以在线办事
				    </span>
				    <script type="text/javascript">
				    function jskzx(){
				        var formData = $("#znform").serialize();
				        $.post("serviceItemController.do?jskzx",formData, function(resultJson) {
				            var itemList = jQuery.parseJSON(resultJson);
				            var zgbsts = "";
				            var kzxbsts = "";
				            zgbsts = itemList[1].total;
				            kzxbsts =  itemList[0].total;
				            $("#zgbsts").text(zgbsts);
				            $("#kzxbsts").text(kzxbsts);
				        });
				    }
				    
				    </script>
                </div>
                <%--结束引入查下界面 --%>
                <div class="bslist" id="listContainer">
                </div>
                <%--开始引入分页JSP --%>
                <jsp:include page="../common/page.jsp" >
                <jsp:param value="serviceItemController.do?pagelist" name="pageURL"/>
                <jsp:param value="callpage" name="callpage"/>
                <jsp:param value="10" name="limitNum"/>
                <jsp:param value="znform" name="pageFormId"/>
                </jsp:include>
                <%--结束引入分页JSP--%>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
