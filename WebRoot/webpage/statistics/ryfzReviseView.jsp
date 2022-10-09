<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    $(function() {
        AppUtil.initWindowForm("ryfzReviseViewForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#ryfzReviseViewForm").serialize();
                var url = $("#ryfzReviseViewForm").attr("action");
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
                            parent.$("#ryfzStatisGrid").datagrid("reload");
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
        }, "T_MSJW_SYSTEM_SYSUSER");
    });
</script>


<body style="margin:0px;background-color: #f7f7f7;">
<form id="ryfzReviseViewForm" method="post"
      action="statisticsNewController.do?ryfzReviseUpdateAndSave">
    <div id="ryfzReviseViewDiv">
        <div><input type="hidden" name="USER_ID"
                    value="${subject.USER_ID}"></div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td style="width:80px;">组别:</td>
                <td>
                    <input type="text" style="width:150px"
                           class="eve-input "
                           value="${subject.ZB}" id="ZB" name="ZB" />
                </td>
            </tr>
            <tr>
                <td style="width:80px;">岗位类型:</td>
                <td style="width:135px;"> <input class="easyui-combobox" type="text" maxlength="20" style="width:150px;" name="GWLX"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=GWLX',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto' " /></td>
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