<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js" ></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" ></script>
<script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" ></script>
<script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js" ></script>

<script>
    function randomCheck() {
        var checkNum = $("input[name='checkNum']").val()
        if (checkNum > 0 && checkNum <= 100) {
            art.dialog.data("checkNum", checkNum);
            AppUtil.closeLayer();
        } else {
            alert("请输入正确的数字");
        }
    }
</script>

<body style="margin:0px;background-color: #f7f7f7;" class="easyui-layout" fit="true">
<form id="randomCheckByXfysForm" method="post" action="">
    <div id="randomCheckByXfysFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table">
            <tr style="height:29px;">
                <td colspan="2" class="dddl-legend"><div class="eui-dddltit">请填写1-100以内的随机数字</div></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="text" class="eve-input" name="checkNum" style="width: 100%;text-align: center;height: 40px;font-size: 20px;font-weight: 700;">
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons">
            <input value="确定" onclick="randomCheck();" style="width: 20px;"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>

</body>