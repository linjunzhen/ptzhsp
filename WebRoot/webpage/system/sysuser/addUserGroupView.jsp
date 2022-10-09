<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
    <script type="text/javascript">
        $(function() {
            AppUtil.initWindowForm("WtItemForm", function(form, valid) {
                if (valid) {
                    //将提交按钮禁用,防止重复提交
                    $("input[type='submit']").attr("disabled","disabled");
                    var formData = $("#WtItemForm").serialize();
                    var url = $("#WtItemForm").attr("action");
                    AppUtil.ajaxProgress({
                        url : url,
                        params : formData,
                        callback : function(resultJson) {
                            if(resultJson.success){
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"succeed",
                                    time:3,
                                    ok: true
                                });
                                AppUtil.closeLayer();
                            }else{
                                parent.art.dialog({
                                    content: resultJson.msg,
                                    icon:"error",
                                    ok: true
                                });
                            }
                        }
                    });
                }
            },null);
        });
    </script>
</head>

<body class="eui-diabody" style="margin:0px;">
<form id="WtItemForm" method="post" action="sysUserController.do?saveUserGroup">
    <div  id="DictionaryFormDiv" data-options="region:'center',split:true,border:false">
        <%--==========隐藏域部分开始 ===========--%>
        <input type="hidden" name="DIC_ID" value="${dictionary.DIC_ID}">
        <input type="hidden" name="TYPE_ID" value="${dictionary.TYPE_ID}">
        <input type="hidden" name="TYPE_NAME" value="${dictionary.TYPE_NAME}">
        <%--==========隐藏域部分结束 ===========--%>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>${dictionary.TYPE_NAME}</a></div></td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width: 100px;float:left;text-align:right;">名称：</span>
                    <input
                            type="text" style="width:400px;float:left;"
                            value="${dictionary.DIC_NAME}" maxlength="60"
                            class="eve-input validate[required]" name="DIC_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
                <tr>
                    <td colspan="2">
                        <span style="width:100px;float:left;text-align:right;">编码：</span>
                        <input
                                type="text" style="width:400px;float:left;"
                                value="${dictionary.DIC_CODE}" maxlength="36"
                                class="eve-input validate[required]" name="DIC_CODE" />
                        <font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                </tr>
        </table>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons">
            <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
            <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
        </div>
    </div>
</form>
</body>
