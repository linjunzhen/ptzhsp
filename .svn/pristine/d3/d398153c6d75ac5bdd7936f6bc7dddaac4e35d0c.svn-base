<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>办事指南详情</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>

<style type="text/css">
.
</style>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller"> 
            <div class="margin10">
              <div class="tmargin20">
                <h3 class="eveTitle">事项名称</h3>
                <div class="form-common card">
                  <ul class="listitem">
                    <li class="noborder">
                        <label class="label-left" style="width:100%;font-size: 14px;">${serviceItem.ITEM_NAME}</label>
                      </li>
                  </ul>
                </div>
                <h3 class="eveTitle tmargin20">基本信息</h3>
                <div class="form-common card">
                  <ul class="listitem">
                      <li>
                        <label class="label-left" style="font-size: 14px;">事项编码</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.ITEM_CODE==null||serviceItem.ITEM_CODE==''}">无</c:if>${serviceItem.ITEM_CODE}</label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">事项类别</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.SXLX==null||serviceItem.SXLX==''}">无</c:if>${serviceItem.SXLX}</label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">申报对象</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.busTypenames==null||serviceItem.busTypenames==''}">无</c:if>${serviceItem.busTypenames}</label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">事项性质</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.SXXZ==null||serviceItem.SXXZ==''}">无</c:if>${serviceItem.SXXZ}</label>
                      </li>
                      <li class="conthide">
                        <label class="label-left" style="font-size: 14px;">主办处室</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.ZBCS==null||serviceItem.ZBCS==''}">无</c:if>${serviceItem.ZBCS}</label>
                      </li>
                      <li class="conthide">
                        <label class="label-left" style="font-size: 14px;">联系人</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.LXR==null||serviceItem.LXR==''}">无</c:if>${serviceItem.LXR}</label>
                      </li>
                      <li class="conthide">
                        <label class="label-left" style="font-size: 14px;">联系电话</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.LXDH==null||serviceItem.LXDH==''}">无</c:if>${serviceItem.LXDH}</label>
                      </li>
                  </ul>
                  <div class="center"><a href="javascript:void(0);" class="more1" style="margin-top: -8px;height:26px; line-height:26px;" onclick="viewZX();">查看更多</a></div>
                </div>
                <h3 class="eveTitle tmargin20" style="">
                	<span onclick="showBsMethod('win')" id="winspan" style="border-bottom: 2px solid #426fa4;"> 窗口办理方式</span>
                	<span class="right" onclick="showBsMethod('online')" style="left:0;right:auto;" id="onlinespan">
                	 &nbsp;&nbsp;&nbsp;&nbsp;网上办理方式</span>
                </h3>
                <div class="form-common card" id="winstyle">
                  <ul class="listitem">
                      <li>
                        <label class="label-left" style="font-size: 14px;">办理部门</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.SSZTMC==null||serviceItem.SSZTMC==''}">无</c:if>${serviceItem.SSZTMC}</label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">受理时间</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.BGSJ==null||serviceItem.BGSJ==''}">无</c:if>${serviceItem.BGSJ}</label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">受理地点</label>
                        <label class="label-right fontcolor">
                        <c:if test="${serviceItem.BLDD==null||serviceItem.BLDD==''}">无</c:if>${serviceItem.BLDD}</label>
                      </li>
                  </ul>
                </div>
                <div class="form-common card" style="display: none;width: 100%;" id="onlinestyle">
                	<ul class="listitem">
                      <li>
                      	<label class="label-left" style="font-size: 14px;">办理方式</label>
                      	<label class="label-right fontcolor">
                      		<c:if test="${serviceItem.WSSQFS==null||serviceItem.WSSQFS==''}">无</c:if>${serviceItem.WSSQFS}
                      	</label>
                      </li>
                    </ul>  
                </div> 
                <h3 class="eveTitle tmargin20">申请条件及说明</h3>
                <div class="form-common card">
                  <ul class="listitem">
                    <li class="noborder">
                        <label class="label-left" style="width:100%;font-size: 14px;">
                        <c:if test="${serviceItem.SQTJ==null||serviceItem.SQTJ==''}">无</c:if>${serviceItem.SQTJ}</label>
                      </li>
                  </ul>
                </div>
                <h3 class="eveTitle tmargin20">办事结果领取方式</h3>
                <div class="form-common card">
                  <ul class="listitem">
                    <li class="noborder">
                        <label class="label-left" style="font-size: 14px;">支持方式</label>
                        <label class="label-right fontcolor">
                            <c:if test="${serviceItem.FINISH_GETTYPE==null||serviceItem.FINISH_GETTYPE==''}">无</c:if>${serviceItem.FINISH_GETTYPE}
                        </label>
                      </li>
                  </ul>
                </div>
                <h3 class="eveTitle tmargin20">办事流程</h3>
                <div class="form-common card">
                  <ul class="listitem">
                    <li class="noborder">
                      
                       		<c:if test="${serviceItem.BDLCDYID!=null}">
							<!-- 开始引入办理流程图jsp -->
								<jsp:include page="bsguidelc.jsp" >
	           						<jsp:param value="${serviceItem.BDLCDYID}" name="defId"/>
	        					</jsp:include>
	        				<!-- 开始引入办理流程图jsp -->
        					</c:if>
                      
                    </li>
                  </ul>
                </div>
                <h3 class="eveTitle tmargin20">材料清单</h3>
                <div class="form-common card">
                  <ul class="listitem">
                  	<c:forEach items="${serviceItem.applyMaters}" var="maters" varStatus="vs">
                  		<li>
                  			${vs.index+1}、${maters.MATER_NAME}
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

		
		function viewZX(){
  			$("li[class='conthide']").removeClass("conthide");
  			refresh.refresh(); 
  		}
  		function getTextv(obj){
  			var txt=$(obj).find("option:selected").text();
  			$("input[name='CONSULT_DEPT']").val(txt);
  		}
    $(function() {
		
	});	
function showBsMethod(obj){
	document.getElementById("winstyle").style.display="none";
	document.getElementById("onlinestyle").style.display="none";
	document.getElementById(obj+"style").style.display="block";
	document.getElementById("winspan").style.borderBottom="0px";
	document.getElementById("onlinespan").style.borderBottom="0px";
	document.getElementById(obj+"span").style.borderBottom="2px solid #426fa4";
}
//jQuery(".bsbox").slide({titCell:". li",mainCell:".bsboxC"})
</script>
  </body>
</html>