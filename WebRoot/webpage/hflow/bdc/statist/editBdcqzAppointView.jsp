<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>

<script>
    $(function() {
        AppUtil.initWindowForm("editBdcAppointViewForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#editBdcAppointViewForm").serialize();
                var url = $("#editBdcAppointViewForm").attr("action");
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
                            parent.$("#bdcqzAppointStatisGrid").datagrid("reload");
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
<form id="editBdcAppointViewForm" method="post"
      action="bdcAppointController.do?editBdcAppointUpdateAndSave">
    <div id="editBdcAppointViewDiv" data-options="region:'center',split:true,border:false">
        <div><input type="hidden" name="ID"
                    value="${bdcMap.ID}"></div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">产权人姓名：</span><span>${bdcMap.CQR_NAME}</span></td>
            </tr>
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">产权人身份证：</span><span>${bdcMap.CQR_CARDNO}</span></td>
            </tr>
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">预约日期：</span><span>${bdcMap.APPOINTMENT_DATE}</span></td>
            </tr>
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">领证方式：</span><span>${LZ_TYPE}</span></td>
            </tr>
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">是否领证：</span>
                    <eve:eveselect clazz="eve-input validate[required]" dataParams="BDCQZLZZT"
                                   defaultEmptyValue="${bdcMap.STATUS}"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   name="STATUS">
                    </eve:eveselect><font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td><span style="width: 130px;float:left;text-align:right;">说明：</span><input type="text" class="eve-input" style="width: 186px;" name="REASON" value="${bdcMap.REASON}"></td>
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