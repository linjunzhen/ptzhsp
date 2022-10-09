<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String ITEM_CODE = request.getParameter("ITEM_CODE");
    request.setAttribute("ITEM_CODE", ITEM_CODE);
%>
<!DOCTYPE html>
<html>
<head>
    <!-- <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> -->
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>办事指南详情</title>
<!--     <link rel="stylesheet" href="css/mui.min.css"/> -->
    <link rel="stylesheet" href="css/index.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
     <script type="text/javascript" src="js/mui.min.js"></script>
    <script type="text/javascript" src="js/mobileUtil.js"></script>
</head>
<body>
    <div class="eui-ken-body-bszn" style="height: auto;">
        <div class="eui-ken-body-fgszzgl-logo" id="ITEM_NAME"></div>
        <div class="eui-ken-fgszzgl">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>事项性质</td>
                    <td style="text-align: left;" id="SXXZ"></td>
                </tr>
                <tr>
                    <td>事项类型</td>
                    <td style="text-align: left;" id="busTypenames"></td>
                </tr>
                <tr>
                    <td>办理依据</td>
                    <td style="text-align: left;" id="XSYJ"></td>
                </tr>
                <tr>
                    <td>实施部门</td>
                    <td style="text-align: left;" id="IMPL_DEPART"></td>
                </tr>
                <tr>
                    <td>主办处室</td>
                    <td style="text-align: left;" id="ZBCS"></td>
                </tr>
                <tr>
                    <td>申请条件</td>
                    <td style="text-align: left;" id="SQTJ"></td>
                </tr>
                <tr>
                    <td>申请材料</td>
                    <td style="text-align: left;" id="applyNames"></td>
                </tr>
                <tr>
                    <td>办理流程</td>
                    <td style="text-align: left;" id="BLLC"></td>
                </tr>
                <tr>
                    <td>法定时限</td>
                    <td style="text-align: left;" id="FDSX"></td>
                </tr>
                <tr>
                    <td>承诺时限</td>
                    <td style="text-align: left;" id="CNSX"></td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td style="text-align: left;" id="LXDH"></td>
                </tr>
                <tr>
                    <td>投诉电话</td>
                    <td style="text-align: left;" id="JDDH"></td>
                </tr>
                <tr>
                    <td>办理时间</td>
                    <td style="text-align: left;" id="BGSJ"></td>
                </tr>
                <tr>
                    <td>办理地点</td>
                    <td style="text-align: left;" id="BLDD"></td>
                </tr>
                <tr>
                    <td>交通指引</td>
                    <td style="text-align: left;" id="TRAFFIC_GUIDE"></td>
                </tr>
            </table>
        </div>
        <!--分公司证照管理事务-->
    </div>
    <script>
    
        itemDetail();
        /**
         * 根据事件编码获取详情
         */
        function itemDetail(){
            mobileUtil.muiAjaxForData({
                mask : true,
                <%--url : '${ctx}/declare/itemDetail',--%>
                url : '<%=path%>/declare.do?itemDetail',
                data : {
                    ITEM_CODE:'${ITEM_CODE}'
                },
                timeout:30000,
                callback : function(result) {
                    result = result||{};
                    $('#ITEM_NAME').html(result['ITEM_NAME']);
                    $('#SXXZ').html(result['SXXZ']);
                    $('#busTypenames').html(result['busTypenames']);
                    $('#XSYJ').html((result['XSYJ']||'').replace(new RegExp('\n','g'),'<br/>'));
                    $('#IMPL_DEPART').html(result['IMPL_DEPART']);
                    $('#ZBCS').html(result['ZBCS']);
                    $('#SQTJ').html((result['SQTJ']||'').replace(new RegExp('\n','g'),'<br/>'));
                    $('#applyNames').html((result['applyNames']||'').replace(new RegExp('\n','g'),'<br/>'));
                    $('#BLLC').html((result['BLLC']||'').replace(new RegExp('\n','g'),'<br/>'));
                    $('#FDSX').html(result['FDSX']);
                    $('#CNSX').html(result['CNSX']);
                    $('#LXDH').html(result['LXDH']);
                    $('#JDDH').html(result['JDDH']);
                    $('#BGSJ').html(result['BGSJ']);
                    $('#BLDD').html(result['BLDD']);
                    $('#TRAFFIC_GUIDE').html((result['TRAFFIC_GUIDE']||'').replace(new RegExp('\n','g'),'<br/>'));
                }
            });
        }
    </script>
</body>
</html>