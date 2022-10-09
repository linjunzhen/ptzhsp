<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    $(function() {
        AppUtil.initWindowForm("gcjsxmcxReviseViewForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#gcjsxmcxReviseViewForm").serialize();
                var url = $("#gcjsxmcxReviseViewForm").attr("action");
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
                            parent.$("#gcjsxmcxStatisGrid").datagrid("reload");
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
        }, "SPGL_XMJBXXB");
    });
</script>


<body class="eui-diabody" style="margin:0px;">
<form id="gcjsxmcxReviseViewForm" method="post"
      action="statisticsNewController.do?gcjsxmcxReviseUpdateAndSave">
    <div id="gcjsxmcxReviseViewDiv" data-options="region:'center',split:true,border:false">
        <div><input type="hidden" name="ID"
                    value="${subject.ID}"></div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">项目名称：</span><span>${subject.PROJECT_NAME}</span></td>
                
            </tr>
            <tr>
            	<td><span style="width: 130px;float:left;text-align:right;">是否联系业主:</span>
                <input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="SFLXYZ"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons">
            <input value="确定" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>

</body>