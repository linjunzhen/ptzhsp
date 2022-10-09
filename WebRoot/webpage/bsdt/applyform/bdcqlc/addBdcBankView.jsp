<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
    <script type="text/javascript">
        $(function() {
            AppUtil.initWindowForm("addBankForm", function(form, valid) {
                if (valid) {
                    //将提交按钮禁用,防止重复提交
                    $("input[type='submit']").attr("disabled","disabled");
                    var formData = $("#addBankForm").serialize();
                    var url = $("#addBankForm").attr("action");
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
            },"T_BDCQLC_BANK");
        });
    </script>
</head>

<body class="eui-diabody" style="margin:0px;">
<form id="addBankForm" method="post" action="bdcGyjsjfwzydjController.do?addBdcBank">
    <div  id="addBankFormDiv" data-options="region:'center',split:true,border:false">
        <%--==========隐藏域部分开始 ===========--%>
        <%--==========隐藏域部分结束 ===========--%>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>常用银行花名册</a></div></td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width: 100px;float:left;text-align:right;">名称：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="64"
                            class="eve-input validate[required]" name="BANK_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">营业执照：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_CODE" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">法定代表：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_LEGAL_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">法定代表联系电话：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_LEGAL_PHONE" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">法定代表证件号码：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_LEGAL_CARD" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">联系人：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_CONTENCT_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">联系人电话：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="16"
                            class="eve-input validate[required]" name="BANK_CONTENCT_PHONE" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">联系人证件号码：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="32"
                            class="eve-input validate[required]" name="BANK_CONTENCT_CARD" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="width:100px;float:left;text-align:right;">联系人地址：</span>
                    <input
                            type="text" style="width:400px;float:left;" maxlength="128"
                            class="eve-input validate[required]" name="BANK_ADDRESS" />
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
