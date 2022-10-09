<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>
<script>
    $(function() {
        AppUtil.initWindowForm("fzxsszChangeRatioForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#fzxsszChangeRatioForm").serialize();
                var url = $("#fzxsszChangeRatioForm").attr("action");
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
                            parent.$("#fzxsszStatisGrid").datagrid("reload");
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
        }, "T_WSBS_SERVICEITEM");

    });
</script>


<body style="margin:0px;background-color: #f7f7f7;">
<form id="fzxsszChangeRatioForm" method="post"
      action="statisticsNewController.do?fzxsszChangeRatioUpdateAndSave">
    <div id="fzxsszChangeRatioDiv">
        <div><input type="hidden" name="ITEM_ID"
                    value="${subject.ITEM_ID}"></div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend" style="font-weight:bold;">基本配置</td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">服务事项名称：</span><span>${subject.ZXMC}</span></td>
                <td><span style="width: 100px;float:left;text-align:right;">系数：</span>
                        <input type="text" style="width:150px;float:left;" maxlength="3"
                               class="eve-input validate[required]"
                               value="${subject.FZXSSZ}" id="FZXSSZ" name="FZXSSZ" />
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