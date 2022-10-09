<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.AppUtil" %>
<%@ page import="net.evecom.platform.system.model.SysUser" %>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<script>
    $(function() {
        AppUtil.initWindowForm("addJxjfkfViewForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#addJxjfkfViewForm").serialize();
                var url = $("#addJxjfkfViewForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "succeed",
                                time : 3,
                                ok : true
                            });
                            parent.$("#jxjfkfStatisGrid").datagrid("reload");
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
                        }
                    }
                });
            }
        }, "JBPM6_JXJFKFSTATIST");
    });

    /*人员选择器*/
    function selectUser(){
        var userIds = $("input[name='JFKF_USERIDS']").val();
        parent.$.dialog.open("sysUserController.do?selector&allowCount=30&userIds="
            + userIds, {
            title : "人员选择器",
            width : "1000px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectUserInfo = art.dialog.data("selectUserInfo");
                if(selectUserInfo&&selectUserInfo.userIds){
                    $("input[name='JFKF_USERIDS']").val(selectUserInfo.userIds);
                    $("input[name='JFKF_USERNAMES']").val(selectUserInfo.userNames);
                    art.dialog.removeData("selectUserInfo");
                }
            }
        }, false);
    }

    /*获取当前年月日并用-隔开*/
    function getDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
        var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
        var h = dd.getHours()<10?"0"+dd.getHours():dd.getHours();
        var x = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
        var s = dd.getSeconds()<10?"0"+dd.getSeconds():dd.getSeconds();
        return y+"-"+m+"-"+d + " " + h + ":" + x + ":" + s;
    }

    $(function () {
        $("input[name='JFKF_DATE']").val(getDateStr(0))
    })


</script>


<body style="margin:0px;background-color: #f7f7f7;">
<form id="addJxjfkfViewForm" method="post"
      action="statisticsNewController.do?jxjfkfUpdateAndSave">
    <div id="addJxjfkfViewDiv">
        <input type="hidden" name="JFKF_USERIDS" value="${subject.JFKF_USERIDS}">
        <input type="hidden" name="JFKF_ID" value="${subject.JFKF_ID}">
        <input type="hidden" name="JFKF_DATE" value="${subject.JFKF_DATE}">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td colspan="1"><span style="width: 100px;float:left;text-align:right;">加分扣分：</span>
                    <eve:eveselect clazz="eve-input validate[required]" dataParams="JXFSLX"
                                   defaultEmptyValue="${subject.JFKF_TYPE}"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   name="JFKF_TYPE" onchange="showJFLX();">
                    </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td colspan="1"><span style="width: 100px;float:left;text-align:right;">类型：</span>
                    <eve:eveselect clazz="eve-input validate[required]" dataParams="JFLX"
                                   defaultEmptyValue="${subject.JF_TYPE}"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   name="JF_TYPE">
                    </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
            </tr>
            <tr>
                <td colspan="1">
                    <span style="width: 100px;float:left;text-align:right;">分值：</span>
                    <input style="width:148px;float:left;" type="text" class="eve-input validate[required]" name="JFKF_FZ" placeholder="请输入整数或者小数" value="${subject.JFKF_FZ}">
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td colspan="1"><span style="width: 100px;float:left;text-align:right;">生效时间：</span>
                    <input style="width:148px;float:left;" readonly="true" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="eve-input validate[required] Wdate" name="JFKF_EFFECT_DATE" value="${subject.JFKF_EFFECT_DATE}"/>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span
                        style="width: 80px;float:left;text-align:right;">人员：</span> <input
                        type="text" style="width:570px;float:left;" readonly="readonly"
                        class="eve-input validate[required]" name="JFKF_USERNAMES" onclick="selectUser();" value="${subject.JFKF_USERNAMES}"/> <font
                        class="dddl_platform_html_requiredFlag">*</font></td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width: 80px;float:left;text-align:right;">原因：</span>
                    <input style="width:570px;float:left;" type="text" class="eve-input validate[required]" name="JFKF_CONTENT" value="${subject.JFKF_CONTENT}">
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>

        </table>
        <div class="eve_buttons">
            <input value="确定" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>

</body>