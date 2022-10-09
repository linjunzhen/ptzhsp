<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    $(function() {
        AppUtil.initWindowForm("sealMaterReviseViewForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#sealMaterReviseViewForm").serialize();
                var url = $("#sealMaterReviseViewForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        console.log(resultJson)
                        if (resultJson.success) {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "succeed",
                                time : 3,
                                ok : true
                            });
                            parent.$("#sealMaterViewGrid").datagrid("reload");
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
        }, "${subject.BUS_TABLENAME}");

    });
</script>


<body style="margin:0px;background-color: #f7f7f7;">
<form id="sealMaterReviseViewForm" method="post"
      action="commercialSetController/sealMaterUpdateAndSave.do">
    <div id="sealMaterReviseViewDiv">
        <div>
        	<input type="hidden" name="BUS_TABLENAME" value="${subject.BUS_TABLENAME}">
        	<input type="hidden" name="${subject.PRIMARY_KEY}" value="${subject.PRIMARY_KEY_VAL}">
        </div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend" style="font-weight:bold;">基本配置</td>
            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">公司名称：</span><span>${subject.COMPANY_NAME}</span></td>
                <td><span style="width: 100px;float:left;text-align:right;">是否已刻制：</span>
                    <input class="easyui-combobox validate[required]" type="text" maxlength="20" style="width:78px;" name="IS_SEALED" value="${subject.IS_SEALED}"  data-options="
					url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <c:if test="${subject.ISEMAIL=='1'}">
            <tr>
                <td colspan="2"><span style="width: 100px;float:left;text-align:right;">EMS邮寄编号：</span>
                    <input type="text" style="width:150px;float:left;" maxlength="3"
                           class="eve-input validate[required]"
                           value="${subject.EMS_NUMBER}" id="EMS_NUMBER" name="EMS_NUMBER" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            </c:if>
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