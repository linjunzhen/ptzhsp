<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>办事指南</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
	<script type="text/javascript"
	src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
    
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <header>
            <div>
              <dl class="retrie"> 
                <dt>
                  <a href="javascript:void(0);" onclick="selectType('zhuti');">按主题</a>
                  <a href="javascript:selectType('rensheng');">按对象</a>
                  <a href="javascript:selectType('renqun');">按经营活动</a>
                </dt> 
                <dd> 
                  <ul class="slide downlist" id="zhuti"> 
                  	<c:forEach items="${ztlist}" var="ztinfo">
						<li><a herf="javascript:void(0);" onclick="addSelect('${ztinfo.TYPE_ID}','zhuti')"  style="cursor: pointer;"
							id="${ztinfo.TYPE_ID}">${ztinfo.TYPE_NAME}</a></li>
					</c:forEach>
                  </ul> 
                </dd> 
                <dd> 
                  <ul class="slide downlist" id="rensheng"> 
                  	<c:forEach items="${dxlist}" var="rsinfo">
                		<li><a herf="javascript:void(0);" onclick="addSelect('${rsinfo.TYPE_ID}','rensheng')"  style="cursor: pointer;"
                			id="${rsinfo.TYPE_ID}" >${rsinfo.TYPE_NAME}</a></li>
                	</c:forEach>
                  </ul> 
                </dd>
                <dd> 
                  <ul class="slide downlist" id="renqun"> 
                  	<c:forEach items="${jylist}" var="rqinfo">
                		<li><a herf="javascript:void(0);" onclick="addSelect('${rqinfo.TYPE_ID}','renqun')"  style="cursor: pointer;"
                		id="${rqinfo.TYPE_ID}" >${rqinfo.TYPE_NAME}</a></li>
                	</c:forEach>
                  </ul> 
                </dd>     
              </dl> 
            </div>
        </header>
        <!--做这个把下面那个top改成50px-->
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:56px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <div class="form-group" >
              <form id="znform" action="javascript:void(0);">
                <input type="hidden" id="busType" name="busType" value="${busType}" />
                <input type="hidden" id="busTypeId" name="busTypeIds" value=""/> 
                <div class="card form-square" >
                  <label class="label-right nomargin noborder nopadding">
                      <input type="text" class="eveinput" name="ITEM_NAME" id="itemName"/>
                  </label>
                  <label class="label-left nomargin noborder"><button class="block btnbgcolor" style="border-radius: 0;" onclick="loadSearchData();">搜 索</button></label>
                </div>
               </form>
                <div class="card tmargin20 nomargin" id="listItemContain">
                  <ul class="listitem">
                  	<c:forEach items="${itemList}" var="iteminfo">
                		<li>
                			 <div class="text">
                        		<div class="eveoverflow">
									<a target="_blank" href="busInteractController.do?bsguideInfo&itemCode=${iteminfo.ITEM_CODE}&busType=${busType}">${iteminfo.ITEM_NAME}</a>
								</div>
                        	<small data-col="2">
                          		<span>办理机构：<font class="fontcolor1">${iteminfo.DEPART_NAME}</font></span>
                          		<span class="right">申报对象：<font class="fontcolor1">${shenqiObj}</font></span>
                        	</small>
                      </div>
                      </li>
                	</c:forEach>
                  </ul>
                </div>
              </div>
            <div>
          </div>  
        </article>
      </section>
    </div>
    
    <!--- third -->
    <script src="<%=path%>/webpage/weixin/agile-lite/third/zepto/zepto.min.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/iscroll/iscroll-probe.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/arttemplate/template-native.js"></script>
    <!-- agile -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/agile/js/agile.js"></script>   
    <!--- bridge -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/exmobi.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/agile.exmobi.js"></script>
    <!-- app -->
    <script src="<%=path%>/webpage/weixin/agile-lite/component/timepicker/agile.timepicker.js"></script> 
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/component/extend.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/app/js/app.js"></script>
    <script type="text/javascript">
    	var refresh=A.Scroll('#normal_article');
		//var url="serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE;
		var selectObj='${shenqiObj}';
		function selectType(obj){
			//$("ul[class='slide downlist']").css("display","none");
			var dis=document.getElementById(obj).style.display;
			if(dis=='block'){
				document.getElementById("zhuti").style.display="none";
				document.getElementById("rensheng").style.display="none";
				document.getElementById("renqun").style.display="none";
				document.getElementById(obj).style.display="none";
			}else{
				document.getElementById("zhuti").style.display="none";
				document.getElementById("rensheng").style.display="none";
				document.getElementById("renqun").style.display="none";
				document.getElementById(obj).style.display="block";
			}
		}
		function searchG(itemList){
			$("#listItemContain").html("");
		    var newhtml = "<ul class=\"listitem\">";
		    for(var i=0; i<itemList.length; i++){
		    	newhtml+="<li><div class=\"text\"><div class=\"eveoverflow\">"
		    		+"<a target=\"_blank\" href=\"busInteractController.do?bsguideInfo&itemCode="+itemList[i].ITEM_CODE+"\" >"+itemList[i].ITEM_NAME+"</a>"
		    		+"</div><small data-col=\"2\"><span>办理机构：<font class=\"fontcolor1\">"
		    		+itemList[i].DEPART_NAME+"</font></span><span class=\"right\">申报对象：<font class=\"fontcolor1\">"
		    		+selectObj+"</font></span></small></div></li>";
		    } 
		     newhtml += "</ul>";
		    $("#listItemContain").html(newhtml);
		    refresh.refresh();
		}
		function loadSearchData(){
			var btype = $("#busType").val();
			var itemname = $("#itemName").val();
			var busTypeId = $("#busTypeId").val();
			$.post("busInteractController.do?loadbsSearch", {
					busType:btype,
					ITEM_NAME:itemname,
					busTypeIds:busTypeId},
			 	function(responseText, status, xhr) {
		        	var resultJson = $.parseJSON(responseText);
		        	searchG(resultJson);
		        }	
		    );
		}
		function addSelect(typeid,obj){
    		var busTypeId = $("#busTypeId").val();
    		if(busTypeId!=''&&busTypeId!=null&&busTypeId.indexOf(typeid)==-1){
    			$("#busTypeId").val(typeid);
    			$("#"+typeid).addClass("select");
    			$("#"+busTypeId).removeClass("select");
    		}else{
    			$("#busTypeId").val(typeid);
    			$("#"+typeid).addClass("select");
    		}
    		document.getElementById(obj).style.display="none";
    		loadSearchData();
       }
	   function removeSelect(typeid){
    		$("#"+typeid).removeClass("select");
    		$("."+typeid).remove();
    		$("#busTypeId").val($("#busTypeId").val().replace(typeid+",",""));
    		reload();
       }
    </script>
  </body>
</html>