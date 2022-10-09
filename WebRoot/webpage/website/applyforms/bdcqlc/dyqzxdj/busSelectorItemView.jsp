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
        
        })


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
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>是否在线签章</a></div></td>
            </tr>
            <tr>
                <td>
                    <eve:radiogroup fieldname="IS_SIGN" width="500"  typecode="YesOrNo" defaultvalue="1" value="${busRecord.IS_SIGN}"></eve:radiogroup>
                </td>
            </tr>
        </table>
    </div>

    <div data-options="region:'south',split:true,border:false"  >
        <div class="eve_buttons" style="text-align: center;">
            <input value="确认所选事项" type="button" onclick="doSelectRows();"  name="queding"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" style="margin-top: 10px;" />

        </div>
    </div>
</div>


</body>
</html>
