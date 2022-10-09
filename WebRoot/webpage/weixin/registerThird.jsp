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
			if(!myreg.test($("#realName").val())){
				$.messager.alert('警告',"真实姓名请输入全中文或者全英文字母！");
				return false;
			}
			/**
			if(!$("#ZY").val() || $("#ZY").val() == ''){
				$.messager.alert('警告',"请选择职业！");
				return false;
			}  **/
			
			//将提交按钮禁用,防止重复提交
			$("button[type='submit']").attr("disabled", "disabled");
			var formData = $("#registerThird").serialize();
			//var url = $("#registerThird").attr("action");
			//$("#registerThird").submit();//考虑换成
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
              	<input type="hidden" id="userType" name="USER_TYPE" value="${userType}">
              	<input type="hidden" id="userName" name="YHZH" value="${userName}">
              	<input type="hidden" id="userPwd" name="DLMM" value="${userPwd}">
              	<input type="hidden" id="telephone" name="telephone" value="${telephone}">
              	<input type="hidden" id="YHXB" name="YHXB" value="1">
                <div class="card">
                  <input type="text" placeholder="真实姓名" id="YHMC" name="YHMC" maxlength="50" autocomplete="on" required class="noborder" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <label class="label-left">性别</label>
                  <label class="label-right"><span class="sex sexOn" id="sexMale" onclick="sexClick('1');">男</span><span class="sex" id="sexFemale" onclick="sexClick('2');">女</span></label>
                </div>
                <div class="card tmargin20">
                	<label class="label-left">证件类型</label>
                	<eve:eveselect clazz="eve-input sel validate[required]" 
                                    dataParams="DocumentType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择证件类型====" name="ZJLX" id="ZJLX"></eve:eveselect>
                  
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="证件号码" id="ZJHM" name="ZJHM" maxlength="20" autocomplete="on" required 
                  class="eve-input validate[required,maxSize[20]],custom[onlyLetterNumber]" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="手机" id="SJHM" name="SJHM"  autocomplete="on" required style="width:100%;"
                   class="eve-input validate[required,maxSize[11]],custom[mobilePhone]" maxlength="11">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="电话号码" id="DHHM" name="DHHM" style="width:100%;" 
                  class="eve-input validate[maxSize[13]],custom[fixPhoneWithAreaCode]" maxlength="13">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="邮政编码" id="YZBM" name="YZBM" maxlength="6" class="eve-input validate[maxSize[6]],custom[chinaZip]" style="width:100%;">
                </div>
                <div class="card tmargin20">
                  <input type="text" placeholder="电子邮箱" id="DZYX" name="DZYX" autocomplete="on" style="width:100%;" 
                  class="eve-input validate[maxSize[30]],custom[email]" maxlength="50">
                </div>
                <!-- 
                <div data-role="select" class="card tmargin20" style="background:#fff;">
                  <select placeholder="选择职业" id="ZY" name="ZY">
                      <option value="">选择职业</option>
                      <option value="1">军人</option>
                      <option value="2">学生</option>
                      <option value="3">教师</option>
                      <option value="4">企事业单位人员</option>
                  </select>
                </div>  -->
                <div class="card tmargin20">
                  <input type="text" placeholder="家庭住址" id="ZTZZ" name="ZTZZ" class="noborder" maxlength="50" style="width:100%;">
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