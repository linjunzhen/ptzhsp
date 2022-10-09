<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>业务咨询</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	function showSelectDeparts(){
			var departId = $("input[name='CONSULT_DEPTID']").val();
			var url = "departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
			+"checkCascadeN=";
			$.dialog.open(url, {
				title : "部门选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if(selectDepInfo){
						$("input[name='CONSULT_DEPTID']").val(selectDepInfo.departIds);
						$("input[name='CONSULT_DEPT']").val(selectDepInfo.departNames);
					}
				}
			}, false);
		};
		
		function viewZX(){
  			window.location.href = "<%=path%>/busInteractController.do?toZXList";
  		}
  		function getTextv(obj){
  			var txt=$(obj).find("option:selected").text();
  			$("input[name='CONSULT_DEPT']").val(txt);
  		}
    $(function() {
	AppUtil.initWindowForm("consultForm", function(form, valid) {
		if (valid) {
			
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#consultForm").serialize();
			var url = $("#consultForm").attr("action");
			//$("#consultForm").submit();
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
	}, "T_LCJC_BUS_DEPLOY");
});	
    </script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <form class="form-group form-common" method="post" id="consultForm" action="busInteractController.do?zxSubmit">
                <div data-role="select" class="card" style="background:#fff;">
                <input type="hidden" id="CONSULT_DEPT" name="CONSULT_DEPT">
                <label class="label-left" style="font-size: 14px;margin-top:8px;">咨询处室</label>
                  <eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="OrgType" onchange="getTextv(this);"
                                    dataInterface="departmentService.findDatasForSelect"
                                    defaultEmptyText="=====请选择咨询处室====" name="CONSULT_DEPTID" id="CONSULT_DEPTID"></eve:eveselect>
                <!--  
                  <select placeholder="请选择咨询处室">
                      <option>请选择投诉单位</option>
                      <option>1</option>
                      <option>2</option>
                  </select>  -->
                </div>
                <div style="color:#949494; font-size:14px; line-height:25px;"><span class="fontcolor1">注：</span>若不了解该咨询哪个处室，请选择<span style="color:#000">“行政服务中心”</span></div>
                <div class="card tmargin20" style="background:#fff;">
						<input class="validate[required],maxSize[30]"  type="text" name="CONSULT_TITLE" placeholder="咨询标题" style="color: #000;" maxlength="30"/>
				</div>
                <div class="card tmargin20" style="background:#fff;">
                  <textarea rows="10" class="validate[required],maxSize[500]" name="CONSULT_CONTENT" style="padding:8px;" placeholder="请输入咨询内容"></textarea>
                </div>
                <button class="block tmargin20 btnbgcolor" style="font-size: 15px;" type="submit">提交咨询</button>
                <span class="right btncolor"><a href="#" class="fontcolor1" style="font-size: 15px;" onclick="viewZX();">查看已提交咨询</a></span>
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
    
  </body>
</html>