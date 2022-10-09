<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>办事进度查询</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
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
	            		  
	            		  
	            		  $("#bjname").html(resultJson.ITEM_NAME);
	            		  $("#subdate").html(resultJson.CREATE_TIME.substr(0,10));
	            		  var state;
	            		  var curnode;
	            		  if(resultJson.RUN_STATUS==0){
	            		  	  curnode=resultJson.YS_NAME;
	            		  	  state="草稿";
	            		  }else if(resultJson.RUN_STATUS==1){
	            			  state="<b style=\"color: #008000;\">正在办理</b>";
	            		  }else{
	            			  state="<b style=\"color:blue\">已办结</b>";
	            		  }
	            		  $("#curnode").html(resultJson.YS_NAME);
	            		  $("#curstate").html(state);

                  }else{
                	  $("span").html("");
                	  //$("#tips").text("无查询结果，烦请确认申报号及查询密码！");
                	  art.dialog({
							content: "无查询结果，烦请确认申报号及查询密码！",
							icon:"error",
							ok: true
					});	
                  }
	            }
	        });
		}
	}
		
$(function() {
	$("#bsjdcqForm").validationEngine("attach", { 
			promptPosition:"bottomLeft",
	        autoPositionUpdate:true,
	        autoHidePrompt: true,            //自动隐藏提示信息
	        autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
	        fadeDuration: "0.5",             //隐藏提示信息淡出的时间
	        maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
	        showOneMessage: false,
	        onValidationComplete:bsjdcq
		}); 
});	
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller"> 
            <div class="margin10">
              <form class="form-common" method="post" id="bsjdcqForm" action="webSiteController/bsjdcx.do">
                <div class="card tmargin20">
                  <ul>
                      <li>
                        <input type="text" placeholder="请输入申报号" autocomplete="on" required maxlength="30" class="eve-input validate[required]" name="exeId" style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="password" placeholder="请输入查询密码" autocomplete="on" required maxlength="8" class="textinput validate[required]" name="bscxmm"style="width:100%;">
                      </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor" onclick="$('#bsjdcqForm').submit();">查 询</button>
              </form>
              <div class="tmargin20">
                <h3 class="eveTitle">查询结果</h3>
                <div class="form-common card">
                  <ul class="listitem">
                      <li>
                        <label class="label-left" style="font-size: 14px;">办件名称</label>
                        <label class="label-right fontcolor"><span id="bjname">${flowExe.ITEM_NAME}</span> </label>
                      </li><!-- 
                      <li>
                        <label class="label-left fs14">申报号</label>
                        <label class="label-right fontcolor"><span id="sbnum"></span> </label>
                      </li>   -->
                      <li>
                        <label class="label-left" style="font-size: 14px;">提交时间</label>
                        <label class="label-right fontcolor"><span id="subdate">${flowExe.CREATE_TIME}</span> </label>
                      </li>
                      <li>
                        <label class="label-left" style="font-size: 14px;">当前节点</label>
                        <label class="label-right fontcolor"><span id="curnode">${flowExe.YS_NAME}</span> </label>
                      </li>
                      <li class="noborder">
                        <label class="label-left" style="font-size: 14px;">状态</label>
                        <label class="label-right fontcolor"><span id="curstate">${flowExe.state}</span> </label>
                      </li>
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
    
  </body>
</html>