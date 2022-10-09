<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources
            loadres="jquery,easyui,apputil,layer,validationegine,laydate,artdialog"></eve:resources>
    <script type="text/javascript">
        $(function() {
            AppUtil.initWindowForm("bdcCallDataRefreshWin", function(form, valid) {
                if (valid) {
                    var formData = $("#bdcCallDataRefreshWin").serialize();
                    var url = $("#bdcCallDataRefreshWin").attr("action");
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

                                parent.$("#bdcCallStatisGrid").datagrid("reload");
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
            }, null);
            var start1 = {
                elem : "#SysLogL.OPERATE_TIME_BEGIN",
                format : "YYYY-MM-DD",
                istime : false
            };
            var end1 = {
                elem : "#SysLogL.OPERATE_TIME_END",
                format : "YYYY-MM-DD",
                istime : false
            };
            laydate(start1);
            laydate(end1);


        });
    </script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<form id="bdcCallDataRefreshWin" method="post"
      action="statisticsNewController.do?refreshBdcCallBjxl">
    <div id="bdcCallDataRefreshWinDiv">
        <!--==========隐藏域部分结束 ===========-->
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">开始日期：</span>
                    <input type="text"
                           style="width:128px;float:left;" class="laydate-icon"
                           id="SysLogL.OPERATE_TIME_BEGIN" name="Q_T.STATIST_DATE_>=" />
                </td>

            </tr>
            <tr>
                <td><span style="width: 100px;float:left;text-align:right;">结束日期：</span>
                    <input type="text"
                           style="width:128px;float:left;" class="laydate-icon"
                           id="SysLogL.OPERATE_TIME_END" name="Q_T.STATIST_DATE_<=" />
                </td>
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

