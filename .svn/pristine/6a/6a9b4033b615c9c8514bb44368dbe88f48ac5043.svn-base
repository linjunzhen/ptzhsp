<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>

<script>
    $(function() {
        AppUtil.initWindowForm("reviseHTIndustryForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#reviseHTIndustryForm").serialize();
                var url = $("#reviseHTIndustryForm").attr("action");
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
                            parent.$("#reviseHTIndustryGrid").datagrid("reload");
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
        }, "T_MSJW_SYSTEM_HTINDUSTRY");

    });
</script>



<body class="eui-diabody" style="margin:0px;">
<form id="reviseHTIndustryForm" method="post"
      action="statisticsNewController.do?reviseHTIndustryUpdateAndSave">
    <div id="reviseHTIndustryDiv" data-options="region:'center',split:true,border:false">
        <div><input type="hidden" name="INDUSTRY_ID"
                    value="${industry.INDUSTRY_ID}"></div>
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
            </tr>
            <tr>
                <td><span style="width: 150px;float:left;text-align:right;">INDUSTRY_CODE：</span>
                    <input type="text" style="width:180px;float:left;"
                           class="eve-input "
                           value="${industry.INDUSTRY_CODE}" id="INDUSTRY_CODE" name="INDUSTRY_CODE" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td><span style="width: 150px;float:left;text-align:right;">INDUSTRY_NAME：</span>
                    <input type="text" style="width:180px;float:left;"
                           class="eve-input "
                           value="${industry.INDUSTRY_NAME}" id="INDUSTRY_NAME" name="INDUSTRY_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td><span style="width: 150px;float:left;text-align:right;">PROJECT_CODE：</span>
                    <input type="text" style="width:180px;float:left;"
                           class="eve-input "
                           value="${industry.PROJECT_CODE}" id="PROJECT_CODE" name="PROJECT_CODE" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
                <td><span style="width: 150px;float:left;text-align:right;">PROJECT_NAME：</span>
                    <input type="text" style="width:180px;float:left;"
                           class="eve-input "
                           value="${industry.PROJECT_NAME}" id="PROJECT_NAME" name="PROJECT_NAME" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">PRIORITY：</span>
                    <input type="text" style="width:180px;float:left;"
                           class="eve-input "
                           value="${industry.PRIORITY}" id="PRIORITY" name="PRIORITY" />
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">REPORT：</span>
                    
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[500]]"
						style="width: 570px" name="REPORT" id="REPORT">${industry.REPORT}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">REPORT_FORM：</span>
                           
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[500]]"
						style="width: 570px" name="REPORT_FORM" id="REPORT_FORM">${industry.REPORT_FORM}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">REGISTRAT_FORM：</span>
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[500]]"
						style="width: 570px" name="REGISTRAT_FORM" id="REGISTRAT_FORM">${industry.REGISTRAT_FORM}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">EXEMPT：</span>
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[250]]"
						style="width: 570px" name="EXEMPT" id="EXEMPT">${industry.EXEMPT}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">SENSITIVE_MEAN：</span>
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[500]]"
						style="width: 570px" name="SENSITIVE_MEAN" id="SENSITIVE_MEAN">${industry.SENSITIVE_MEAN}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span style="width: 150px;float:left;text-align:right;">CLAUSE_CONTENT：</span>
					<textarea rows="4" cols="5" class="eve-textarea validate[[],maxSize[500]]"
						style="width: 570px" name="CLAUSE_CONTENT" id="CLAUSE_CONTENT">${industry.CLAUSE_CONTENT}</textarea>
                    <font class="dddl_platform_html_requiredFlag">*</font>
                </td>
            </tr>
        </table>
    </div>
		<div data-options="region:'south'" style="height:46px;">
        <div class="eve_buttons">
            <input value="确定" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
        </div>
</form>

</body>