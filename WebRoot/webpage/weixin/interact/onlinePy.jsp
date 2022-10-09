<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>网上评议</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	function setComment(obj,name,value){
			$("input[name='"+name+"']").val(value);
			$("#"+obj).parent().children('a').removeClass("grid-cell evePjon").addClass("grid-cell");
			//$("#"+obj).toggleClass('grid-cell evePjon');
			$("#"+obj).removeClass("grid-cell").addClass("grid-cell evePjon");
		}
		
  		function getTextv(obj){
  			var txt=$(obj).find("option:selected").text();
  			$("input[name='COMMENT_DEPT']").val(txt);
  		}
  		function resetForm(){
			$("input[type='text']").val("");
			$("input[type='hidden']").val("");
			$("input[type='password']").val("");
			$("textarea[name='COMMENT_REMARK']").val("");	
			$("select[name='COMMENT_DEGREE']").val("");	
			$("select[name='COMMENT_JOB']").val("");					
			setComment('fzzc_jbmy','COMMENT_FZZC',1);
			setComment('bsxl_jbmy','COMMENT_BSXL',1);
			setComment('ljqz_jbmy','COMMENT_LJQZ',1);
			setComment('yfxz_jbmy','COMMENT_YFXZ',1);
			setComment('fwzl_jbmy','COMMENT_FWZL',1);
		}
    $(function() {/**
	AppUtil.initWindowForm("commentForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#commentForm").serialize();
			var url = $("#commentForm").attr("action");
			//$("#consultForm").submit();
			if(!$("#COMMENT_DEPTID").val() || $("#COMMENT_DEPTID").val() == ''){
				$.messager.alert('警告',"委办局不能为空！");
				return false;
			} 
			AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content: "提交成功",
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
	}, "T_HD_COMMENT"); **/
});	 
function clkBtn(){
	//将提交按钮禁用,防止重复提交
			$("button[type='button']").attr("disabled", "disabled");
			var formData = $("#commentForm").serialize();
			var url = $("#commentForm").attr("action");
			//$("#consultForm").submit();
			if(!$("#COMMENT_DEPTID").val() || $("#COMMENT_DEPTID").val() == ''){
				$.messager.alert('警告',"委办局不能为空！");
				return false;
			} else{
				$.post("busInteractController/saveComment.do",formData,
			 	function(responseText, status, xhr) {
		        	var resultJson = $.parseJSON(responseText);
		        	if(resultJson.success){
		        		art.dialog({
									content: "提交成功",
									icon:"succeed",
									time:3,
									ok: true
								});
								//resetForm();
		        	}else{
		        		art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
		        	}
		        }	
		    );
			}
}
    </script>
  </head>
  
  <body style="position:absolute;">
    <div id="section_container" >
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article"  class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller" style="position: absolute;">
            <div class="margin10"> 
            <form id="commentForm" method="post" action="" class="form-group form-common">
					<!--==========隐藏域部分开始 ===========-->
					<input type="hidden" name="COMMENT_FZZC" value="1"><!--贯彻落实中央和市委市政府重大决策部署和方针政策-->	
					<input type="hidden" name="COMMENT_BSXL" value="1"><!--办事效率-->	
					<input type="hidden" name="COMMENT_LJQZ" value="1"><!--廉洁勤政-->	
					<input type="hidden" name="COMMENT_YFXZ" value="1"><!--依法行政-->					
					<input type="hidden" name="COMMENT_FWZL" value="1"><!--服务质量-->	
              		<input type="hidden" id="COMMENT_DEPT" name="COMMENT_DEPT">
                <div class="card tmargin20">
                	<label class="label-left" style="font-size: 14px;margin-top:8px;">委办局</label>
                  <eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="OrgType" onchange="getTextv(this);"
                                    dataInterface="departmentService.findDatasForSelect"
                                    defaultEmptyText="=====请选择委办局====" name="COMMENT_DEPTID" id="COMMENT_DEPTID"></eve:eveselect>
                </div>
                <div class="card tmargin20 nomargin">
                  <ul class="listitem">
                    <li>
                      <div class="text">
                        1、贯彻落实中央和市委，市政府重大决策部署和方针政策
                        <small data-col="4"><a id="fzzc_my" href="javascript:setComment('fzzc_my','COMMENT_FZZC',2);" class="grid-cell">满意</a>
                        <a id="fzzc_jbmy" href="javascript:setComment('fzzc_jbmy','COMMENT_FZZC',1);" class="grid-cell evePjon">基本满意</a>
                        <a id="fzzc_bmy" href="javascript:setComment('fzzc_bmy','COMMENT_FZZC',0);" class="grid-cell">不满意</a>
                        <a id="fzzc_blj" href="javascript:setComment('fzzc_blj','COMMENT_FZZC',-1);" class="grid-cell">不了解</a></small>
                      </div>
                    </li>
                    <li>
                      <div class="text">
                        2、办事效率
                        <small data-col="4"><a id="bsxl_my" href="javascript:setComment('bsxl_my','COMMENT_BSXL',2);" class="grid-cell">满意</a>
                        <a id="bsxl_jbmy" href="javascript:setComment('bsxl_jbmy','COMMENT_BSXL',1);" class="grid-cell evePjon">基本满意</a>
                        <a id="bsxl_bmy" href="javascript:setComment('bsxl_bmy','COMMENT_BSXL',0);" class="grid-cell">不满意</a>
                        <a id="bsxl_blj" href="javascript:setComment('bsxl_blj','COMMENT_BSXL',-1);" class="grid-cell">不了解</a></small>
                      </div>
                    </li>
                    <li>
                      <div class="text">
                        3、廉洁勤政
                        <small data-col="4"><a id="ljqz_my" href="javascript:setComment('ljqz_my','COMMENT_LJQZ',2);" class="grid-cell">满意</a>
                        <a id="ljqz_jbmy" href="javascript:setComment('ljqz_jbmy','COMMENT_LJQZ',1);" class="grid-cell evePjon">基本满意</a>
                        <a id="ljqz_bmy" href="javascript:setComment('ljqz_bmy','COMMENT_LJQZ',0);" class="grid-cell">不满意</a>
                        <a id="ljqz_blj" href="javascript:setComment('ljqz_blj','COMMENT_LJQZ',-1);" class="grid-cell">不了解</a></small>
                      </div>
                    </li>
                    <li>
                      <div class="text">
                        4、依法行政
                        <small data-col="4"><a id="yfxz_my" href="javascript:setComment('yfxz_my','COMMENT_YFXZ',2);" class="grid-cell">满意</a>
                        <a id="yfxz_jbmy" href="javascript:setComment('yfxz_jbmy','COMMENT_YFXZ',1);" class="grid-cell evePjon">基本满意</a>
                        <a id="yfxz_bmy" href="javascript:setComment('yfxz_bmy','COMMENT_YFXZ',0);" class="grid-cell">不满意</a>
                        <a id="yfxz_blj" href="javascript:setComment('yfxz_blj','COMMENT_YFXZ',-1);" class="grid-cell">不了解</a></small>
                      </div>
                    </li>
                    <li >
                      <div class="text">
                        5、服务质量
                        <small data-col="4">
                        <a id="fwzl_my" href="javascript:setComment('fwzl_my','COMMENT_FWZL',2);" class="grid-cell">满意</a>
                        <a id="fwzl_jbmy" href="javascript:setComment('fwzl_jbmy','COMMENT_FWZL',1);" class="grid-cell evePjon">基本满意</a>
                        <a id="fwzl_bmy" href="javascript:setComment('fwzl_bmy','COMMENT_FWZL',0);" class="grid-cell">不满意</a>
                        <a id="fwzl_blj" href="javascript:setComment('fwzl_blj','COMMENT_FWZL',-1);" class="grid-cell">不了解</a>
			<a id="fwzl_blj_test" href="javascript:setComment('fwzl_blj','COMMENT_FWZL',-1);" style="display:none;"></a></small>
                      </div>
                    </li>
                    <li class="noborder">
                      
			<div class="text">
                        6、补充说明
                        <small>
                          <textarea rows="3" name="COMMENT_REMARK" onfocus="textFofus(this);" onblur="textBlur(this);" class="noborder fontcolor" maxlength="500"  placeholder="请输入要补充的意见"></textarea>
                        </small>
                      </div>
                    </li>
                  </ul>
                </div>
                <button class="block tmargin20 btnbgcolor" onclick="clkBtn();" type="button">提 交</button>
              </form>
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
        var refresh=A.Scroll("#normal_article");
	var index=0;
    	function textBlur(obj){
    		//refresh.refresh();
    		//document.activeElement.blur();
    		 $("body").trigger("click");
    		  $("body").resize();
    		 document.body.removeEventListener("touchend", function(event) { 
			   //$("textarea[name='COMMENT_REMARK']").blur();
			   refresh.refresh();
			   $("body").resize();
		 }, false); 
    	}

    	function textFofus(obj){
    		 document.body.addEventListener("touchend", function(event) { 
			   $("textarea[name='COMMENT_REMARK']").blur();
			   $("body").trigger("click");
			   refresh.refresh();
			   $("#fwzl_blj_test").focus();
		 }, false); 
    	}
    </script>	
  </body>
</html>