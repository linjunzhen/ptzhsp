<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>用户注册-资料信息</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
    	function sexClick(sex){
    		if(sex=='1'){
    			$("#sexMale").removeClass("sex").addClass("sex sexOn");
    			$("#sexFemale").removeClass("sex sexOn").addClass("sex");
    			$("#YHXB").val(1);
    		}else{
    			$("#sexFemale").removeClass("sex").addClass("sex sexOn");
    			$("#sexMale").removeClass("sex sexOn").addClass("sex");
    			$("#YHXB").val(2);
    		}
    	}
    $(function() {
	AppUtil.initWindowForm("registerThird", function(form, valid) {
		if (valid) {
			var myreg=/^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
			if(!myreg.test($("#YHMC").val())){
				$.messager.alert('警告',"机构名称请输入全中文或者全英文字母！");
				return false;
			}
			if(!$("#JGLX").val() || $("#JGLX").val() == ''){
				$.messager.alert('警告',"机构类型不能为空！");
				return false;
			}
			
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#registerThird").serialize();
			var url = $("#registerThird").attr("action");
			//$("#registerThird").submit();
			return true;
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
              <form class="form-group form-common" method="post" id="registerThird" action="wregisterController.do?registerSave"> 
              	<input type="hidden" id="userType" name="USER_TYPE" value="2">
              	<input type="hidden" id="YHZH" name="YHZH" value="${YHZH}">
              	<input type="hidden" id="userPwd" name="DLMM" value="${userPwd}">
              	<input type="hidden" id="telephone" name="telephone" value="${telephone}">
              	<input type="hidden" id="YHMC" name="YHMC" value="${YHMC}">
              	<input type="hidden" id="JGLX" name="JGLX" value="${JGLX}">
              	<input type="hidden" id="ZZJGDM" name="ZZJGDM" value="${ZZJGDM}">
              	
              	<input type="hidden" id="YHXB" name="YHXB" value="${YHXB}">
              	<input type="hidden" id="FRDB" name="FRDB" value="${FRDB}">
              	<input type="hidden" id="ZJHM" name="ZJHM" value="${ZJHM}">
              	<input type="hidden" id="SJHM" name="SJHM" value="${SJHM}">
              	<input type="hidden" id="DHHM" name="DHHM" value="${DHHM}">
              	<input type="hidden" id="YZBM" name="YZBM" value="${YZBM}">
              	<input type="hidden" id="DWDZ" name="DWDZ" value="${DWDZ}">
              	<div class="card tmargin20">
              		<span>机构名称：${YHMC}</span>
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="经办人邮箱地址" id="JBRYXDZ" name="JBRYXDZ" autocomplete="on" 
                  class="eve-input validate[maxSize[30]],custom[email]" class="noborder" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="经办人姓名" id="JBRXM" name="JBRXM" maxlength="10"
                                    class=" validate[required,maxSize[10]],custom[onlychineseLetter]" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="经办人身份证" id="JBRSFZ" name="JBRSFZ" maxlength="20" 
                                    class=" validate[required,maxSize[20]],custom[vidcard]" style="width:100%;">
                </div>
                <button class="block tmargin20 btnbgcolor" type="submit">提 交</button>
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