
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
    loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>
<style>
    .s_width {
        width:200px
    }
</style>
<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("zjkInfoForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#zjkInfoForm").serialize();
                var url = $("#zjkInfoForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            parent.art.dialog({
                                content: resultJson.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                            parent.$("#GcjsxfZjkGrid").datagrid("reload");
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content: resultJson.msg,
                                icon:"error",
                                ok: true
                            });
                        }
                    }
                });
            }
        }, "T_BSFW_GCJSXF_ZJK");

    });
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <form id="zjkInfoForm" method="post"
        action="gcjsxfZjkController.do?saveOrUpdate">
        <div id="zjkInfoFormDiv" data-options="region:'center',split:true,border:false">
            <!--==========隐藏域部分开始 ===========-->
            <input type="hidden" name="JBXX_ID" value="${zjkinfo.JBXX_ID}">

            <!--==========隐藏域部分结束 ===========-->
            <table style="width:100%;border-collapse:collapse;"
                class="dddl-contentBorder dddl_table">
                <tr style="height:29px;">
                    <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
                </tr>
                <tr>
                    <td><span style="width: 100px;float:left;text-align:right;">专家姓名：</span>
                        <input type="text" style="width:186px;;float:left;" maxlength="20"
                        class="eve-input validate[required]" value="${zjkinfo.ZJXM}"
                        name="ZJXM" /><font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                    <td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
                        <input type="text" style="width:186px;;float:left;" maxlength="11"
                        class="eve-input validate[required,custom[mobilePhone]]" value="${zjkinfo.SJH}"
                        name="SJH" /><font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                </tr>
                <tr>
                    <td><span style="width: 100px;float:left;text-align:right;">工作单位：</span>
                        <input type="text" style="width:186px;;float:left;" maxlength="80"
                        class="eve-input validate[required]" value="${zjkinfo.WORK_UNIT}"
                        name="WORK_UNIT" /><font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                    <td><span style="width: 100px;float:left;text-align:right;">专业技术职称：</span>
                        <input type="text" style="width:186px;float:left;" maxlength="80"
                        class="eve-input validate[required]" value="${zjkinfo.JOB_DESC}"
                        name="JOB_DESC" /><font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="width: 100px;float:left;text-align:right;">是否启用：</span>
	                    <div class="eve-chcekbox" style="width:200px;">
	                        <ul>
	                            <li>
	                            <input class="validate[required]" type="radio" name="STATE" value="1" <c:if test="${zjkinfo.STATE!=0}">checked="checked"</c:if>>启用
	                            </li>
	                            <li>
	                            <input class="validate[required]" type="radio" name="STATE" value="0" <c:if test="${zjkinfo.STATE==0}">checked="checked"</c:if>>禁用
	                            </li>
	                        </ul>
	                    </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="width: 100px;float:left;text-align:right;">备注：</span>
                        <textarea rows="3" cols="90" name="BZ" maxlength="60">${zjkinfo.BZ}</textarea>
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

