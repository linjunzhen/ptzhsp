<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
    <meta name="renderer" content="webkit">
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <script type="text/javascript">

        $(function () {
            selectVal(1);
        })

        function selectVal(val) {
            if (val == 1) {
                $("tr[name='isWq']").hide();
            } else if (val == 4) {
                $("tr[name='isWq']").show();
            }
        }

        function doSelectRows() {
            var formData = FlowUtil.getFormEleData('busSelectItem');
            art.dialog.data("busSelectData", formData);
            AppUtil.closeLayer();
        }

    </script>
    <style>
        .flexBox{
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>

<body  style="margin:0px;background-color: #f7f7f7;">

<div class="easyui-layout" fit="true" id="busSelectItem">
    <div data-options="region:'center',split:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="gcjsxmcxTable">
            <%--<tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请选择业务类型</a></div></td>
            </tr>
             <tr>
                <td>
                    <eve:radiogroup fieldname="ZYDJ_TYPE" width="500" onclick="selectVal(this.value);" typecode="zydjlx4" defaultvalue="1" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
                </td>
            </tr> --%>
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请选择办理结果领取方式</a></div></td>
            </tr>
            <tr>
                <td>
                    <eve:radiogroup fieldname="FINISH_GETTYPE" width="500" defaultvalue="01" typecode="BLJGLQFS" value="${busRecord.FINISH_GETTYPE}"></eve:radiogroup>
                </td>
            </tr>
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>所有方式</a></div></td>
            </tr>
            <tr>
                <td>
                    <eve:radiogroup fieldname="GYFS" width="500" defaultvalue="0" typecode="GYFS" value="${busRecord.GYFS}"></eve:radiogroup>
                </td>
            </tr>
            <tr name="isWq" style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请确认是否已完成网签</a></div></td>
            </tr>
            <tr name="isWq">
                <td>
                    <eve:radiogroup fieldname="IS_WQ" width="500" defaultvalue="1" typecode="YesOrNo" value="${busRecord.IS_WQ}"></eve:radiogroup>
                </td>
            </tr>
        </table>
    </div>

    <div data-options="region:'south',split:true,border:false"  >
        <div class="eve_buttons" style="text-align: center;">
            <input value="确认所选" type="button" onclick="doSelectRows();"  name="queding"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" style="margin-top: 10px;" />

        </div>
    </div>
</div>


</body>
</html>
