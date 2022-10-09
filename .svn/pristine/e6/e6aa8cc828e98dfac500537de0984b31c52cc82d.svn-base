<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>

<script type="text/javascript">
    //选择所属目录
    function selectCatalog(){
        var catalogCode = $("input[name='CATALOG_CODE']").val();
        var catalogName = $("input[name='CATALOG_NAME']").val();
        parent.$.dialog.open(encodeURI("catalogController.do?selector&allowCount=1&catalogCode="
            + catalogCode+"&catalogName="+catalogName), {
            title : "目录选择器",
            width : "1000px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectCatalogInfo = art.dialog.data("selectCatalogInfo");
                if (selectCatalogInfo) {
                    $("input[name='CATALOG_CODE']").val(selectCatalogInfo.catalogCode);
                    $("input[name='CATALOG_NAME']").val(selectCatalogInfo.catalogName);
                }
            }
        }, false);
    }
    $(function() {
        AppUtil.initWindowForm("LinkCatalogForm", function(form, valid) {
            if (valid) {
                //将提交按钮禁用,防止重复提交
                $("input[type='submit']").attr("disabled", "disabled");
                var formData = $("#LinkCatalogForm").serialize();
                var url = $("#LinkCatalogForm").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        art.dialog.data("resultJson", resultJson);
                        if (resultJson.success) {
                            AppUtil.closeLayer();
                        } else {
                            parent.art.dialog({
                                content : resultJson.msg,
                                icon : "error",
                                ok : true
                            });
                            $("input[type='submit']").attr("disabled", false);
                        }
                    }
                });
            }
        }, "T_WSBS_SERVICEITEM_BUSCLASS");
    });

</script>

<body class="eui-diabody" style="margin:0px;" class="easyui-layout" fit="true">
<form id="LinkCatalogForm" method="post"
      action="serviceItemController.do?saveOrUpdateLinkCatalog">
    <div id="LinkCatalogFormDiv" data-options="region:'center',split:true,border:false">
        <!--==========隐藏域部分开始 ===========-->
        <input type="hidden" name="UNID" value="${UNID}">
        <input type="hidden" name="CATALOG_CODE">
        <!--==========隐藏域部分结束 ===========-->
        <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
            <tr>
                <td class="dddl-legend"><div class="eui-dddltit"><a>关联目录</a></div></td>
            </tr>
            <tr>
                <td><span
                        style="width: 130px;float:left;text-align:right;">所属目录：</span>
                    <input type="text" style="width:300px;float:left;"
                           class="eve-input validate[required]" name="CATALOG_NAME"
                           readonly="readonly" /><font
                            class="dddl_platform_html_requiredFlag">*</font>
                        <input value="选择目录" type="button" class="eve_inpbtn" style="padding: 3px 10px;"
                               onclick="selectCatalog()" />
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons" >
            <input value="确定" type="submit"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>
</body>