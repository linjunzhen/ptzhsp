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
		if (valid) {/**
			var myreg=/^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
			if(!myreg.test($("#YHMC").val())){
				$.messager.alert('警告',"机构名称请输入全中文或者全英文字母！");
				return false;
			}
			if(!$("#JGLX").val() || $("#JGLX").val() == ''){
				$.messager.alert('警告',"机构类型不能为空！");
				return false;
			} **/
			
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
              <form class="form-group form-common" method="post" id="registerThird" action="wregisterController.do?registerQy"> 
              	<input type="hidden" id="userType" name="USER_TYPE" value="2">
              	<input type="hidden" id="userName" name="userName" value="${userName}">
              	<input type="hidden" id="YHZH" name="YHZH" value="${userName}">
              	<input type="hidden" id="userPwd" name="userPwd" value="${userPwd}">
              	<input type="hidden" id="telephone" name="telephone" value="${telephone}">
                <div class="card">
                  <input type="text" placeholder="机构名称" id="YHMC" name="YHMC" class="noborder" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <label class="label-left">机构类型</label>
                  <eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="OrgType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择机构类型====" name="JGLX" id="JGLX"></eve:eveselect>
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="单位机构代码" id="ZZJGDM" name="ZZJGDM" 
                  class=" validate[required,maxSize[30]]" maxlength="30" style="width:100%;">
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="法人代表姓名" id="FRDB" name="FRDB" maxlength="10"
                                    class="validate[required,maxSize[10]],custom[onlychineseLetter]" style="width:100%;">
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                                	法人代表性别：</span>
                                <eve:eveselect clazz="eve-input sel validate[required]"
                                    dataParams="sex"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="======选择性别=====" name="YHXB"></eve:eveselect>
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="法人代表身份证" id="ZJHM" name="ZJHM" maxlength="20" 
                                    class=" validate[required,maxSize[20]],custom[vidcard]" style="width:100%;">
                                    <input type="hidden" name="ZJLX" value="SF"/>
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="法人代表手机号码" id="SJHM" name="SJHM" maxlength="11"
                                    class="eve-input validate[required,maxSize[11]],custom[mobilePhone]" style="width:100%;">
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="电话号码" id="DHHM" name="DHHM" maxlength="13"
                  class="eve-input validate[maxSize[13]],custom[fixPhoneWithAreaCode]" style="width:100%;">
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="邮政编码" id="YZBM" name="YZBM" maxlength="6"
                                    class="eve-input validate[maxSize[6]],custom[chinaZip]" style="width:100%;">
                </div>
                <div class="card tmargin20" style="margin-top:15px;">
                  <input type="text" placeholder="单位住址" id="DWDZ" name="DWDZ" class="eve-textarea validate[maxSize[50]]" style="width:100%;">
                </div>
                <!-- 
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
                </div>    -->
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