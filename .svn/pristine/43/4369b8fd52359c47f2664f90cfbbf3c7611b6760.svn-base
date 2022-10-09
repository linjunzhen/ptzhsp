<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>

  <head>
    <title>账号绑定</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript">
   
$(function() {
    gryhChoose();

});

    function gryhChoose() {
      $("#fryh").css("color","#000000");
      $("#gryh").css("color", "#0a90f5");
      $("#gryhForm").show();
      $("#fryhForm").hide();
      $("input[name='USER_TYPE']").val('1');
    }

    function fryhChoose() {
      $("#gryh").css("color","#000000");
      $("#fryh").css("color", "#0a90f5");
      $("#fryhForm").show();
      $("#gryhForm").hide();
      $("input[name='USER_TYPE']").val('2');
    }

    function checksubmit() {
        var userType = $("input[name='USER_TYPE']").val();
        var formData;
        if (userType == '1') {
            formData = checkForm('gryhForm');
        } else {
            formData = checkForm('fryhForm');
        }
        if (!formData) {
          return false;
        }
        formData['USER_TYPE'] = userType;
        checkUser(formData);
    }

    function checkUser(formData) {
        AppUtil.ajaxProgress({
            url : "userBindController/newBindSave.do",
            params : formData,
            callback : function (resultJson) {
                if (resultJson.success){
                    art.dialog({
                        content : resultJson.msg,
                        icon : "succeed",
                        lock : true,
                        ok:function(){
                            window.top.location.href="<%=path%>/userBindController.do?toBinded&userName=" + resultJson.userName;
                        },
                        close: function(){
                            window.top.location.href="<%=path%>/userBindController.do?toBinded&userName=" + resultJson.userName;
                        }
                    });
                } else {
                  art.dialog({
                    content : resultJson.msg,
                    icon : "error",
                    ok : true
                  });
                }
            }
        });
    }

    function checkForm(idName) {
        var flag = true;
        var formData = {};
        $("#" + idName + " input").each(function (index) {
            if ($(this).val() && $(this).val().trim() != '') {
                var name = $(this).attr("name");
                var value = $(this).val();
                formData[name] = value;
            } else {
                $.messager.alert('警告' , $(this).attr("placeholder"));
                flag = false;
                return false;
            }
        })
        if (flag) {
          return formData;
        } else {
          return flag;
        }
        return formData;
    }

</script>
  </head>
  
  <body>
    <div id="section_container">
      <section id="form_section" data-role="section" class="active">
        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:0px;bottom:0px; background:#ebebeb;">
          <div class="scroller">
            <div class="margin10"> 
              <h4 class="eveTitle1">您尚未绑定账号</h4>
              <form class="form-common tmargin20" method="post" id="bindForm" action="">
                <div style="height: 50px;line-height: 50px;">
                  <div style="display: flex;justify-content: center;align-items: center;">
                    <span id="gryh" onclick="gryhChoose();" style="color: #0a90f5">个人用户</span>
                    <span style="width: 60px;"></span>
                    <span id="fryh" onclick="fryhChoose();">法人用户</span>
                  </div>
                </div>
                <input type="hidden" name="USER_TYPE">
                <div id="gryhForm" style="display: none;">
                  <div class="card">
                    <ul class="listitem">
                        <li>
                          <input type="text" placeholder="请输入手机号码" id="SJHM" name="SJHM" autocomplete="on" class="noborder" style="width:100%;">
                        </li>
                        <li>
                          <input type="text" placeholder="请输入证件号码" id="ZJHM" name="ZJHM"  autocomplete="on" class="noborder" style="width:100%;">
                        </li>
                        <li class="noborder">
                          <input type="text" placeholder="请输入用户名称" id="YHMC" name="YHMC"  autocomplete="on" class="noborder" style="width:100%;">
                        </li>
                    </ul>
                  </div>
                </div>
                <div id="fryhForm" style="display: none;">
                  <div class="card">
                    <ul class="listitem">
                      <li>
                        <input type="text" placeholder="请输入经办人手机号码" id="MOBILE_PHONE" name="MOBILE_PHONE" autocomplete="on" class="noborder" style="width:100%;">
                      </li>
                      <li>
                        <input type="text" placeholder="请输入单位机构代码" id="ZZJGDM" name="ZZJGDM"  autocomplete="on" class="noborder" style="width:100%;">
                      </li>
                      <li class="noborder">
                        <input type="text" placeholder="请输入机构名称" id="ORG_NAME" name="ORG_NAME"  autocomplete="on" class="noborder" style="width:100%;">
                      </li>
                    </ul>
                  </div>
                </div>
                <button class="block tmargin20 btnbgcolor" type="button" onclick="checksubmit();">立即绑定</button>
              </form>
            </div>
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