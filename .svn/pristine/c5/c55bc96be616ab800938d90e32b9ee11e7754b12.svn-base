<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
    loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog"></eve:resources>
<script type="text/javascript">
    $(function() {
        AppUtil.initWindowForm("qzprintAuditForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#qzprintAuditForm").serialize();
                var url = $("#qzprintAuditForm").attr("action");
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
                            top.opener.$("#BdcQlcQzPrintGrid").datagrid('reload');
                            top.opener.$("#BdcQzjffzGrid").datagrid('reload');
                            top.opener.$("#MyBdcQlcQzPrintGrid").datagrid('reload');
                            top.opener.$("#BdcMyQzjffzGrid").datagrid('reload');
                            top.window.opener = null;   
                            top.window.open('','_self'); 
                            top.window.close(); 
                        } else {
                            $("input[type='submit']").removeAttr("disabled");
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
                        }
                    }
                });
            }
        }, "T_BDCQLC_SFFZLOG");
        
    });

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
    <form id="qzprintAuditForm" method="post"
        action="bdcQlcSffzInfoController.do?saveBdcAudit">
        <div id="qzprintAuditFormDiv">
            <!--==========隐藏域部分开始 ===========-->
            <input type="hidden" name="LOGID"
                value="${auditConfig.LOGID}">
            <input type="hidden" name="EVEID"
                value="${auditConfig.EVEID}">
            <input type="hidden" name="CZR_USER"
                value="${auditConfig.CZR_USER}">

            <!--==========隐藏域部分结束 ===========-->
            <table style="width:100%;border-collapse:collapse;"
                class="dddl-contentBorder dddl_table">
                <tr style="height:29px;">
                    <td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>环节信息</a></div></td>
                </tr>
                <tr>
                    <td><span style="width: 100px;float:left;text-align:right;">环节名称：</span>
                        <input type="text" style="width:150px;float:left;" maxlength="30"
                        class="eve-input validate[required]" readonly="readonly"
                        value="${auditConfig.CZ_HJMC}" name="CZ_HJMC" /><font
                        class="dddl_platform_html_requiredFlag">*</font></td>
                    <td><span style="width: 100px;float:left;text-align:right;">操作人：</span> 
                        <input type="text" style="width:150px;float:left;" maxlength="30"
                        class="eve-input validate[required]" readonly="readonly"
                        value="${auditConfig.CZR_USERNAME}" name="CZR_USERNAME" />
                        <font class="dddl_platform_html_requiredFlag">*</font>
                    </td>
                </tr>
            </table>
            <table style="width:100%;border-collapse:collapse;"
                class="dddl-contentBorder dddl_table">
                <tr style="height:29px;">
                    <td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>审核信息</a></div></td>
                </tr>
                <c:if test="${auditConfig.OPTYPE eq '1' }">
	                <tr>
	                    <td><span style="width: 100px;float:left;text-align:right;">权证是否打印：</span>
	                    <eve:radiogroup fieldname="IS_FINISH" width="100"  typecode="YesOrNo" defaultvalue="0"></eve:radiogroup>
	                        </td>
	                </tr>
                </c:if>
                <c:if test="${auditConfig.OPTYPE eq '2' }">
                    <tr>
                        <td><span style="width: 100px;float:left;text-align:right;">权证是否领取：</span>
                        <eve:radiogroup fieldname="IS_FINISH" width="100"  typecode="YesOrNo" defaultvalue="0"></eve:radiogroup>
                            </td>
                    </tr>
                </c:if>
                <tr>
                    <td><span style="width: 100px;float:left;text-align:right;">审核意见：</span>
                        <textarea name="CZ_DESC" cols="70" rows="5"
                        class="validate[[required],maxSize[500]]"></textarea></td>
                </tr>
            </table>


        </div>
        <div class="eve_buttons">
            <input value="确定" type="submit"
                class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </form>

</body>

