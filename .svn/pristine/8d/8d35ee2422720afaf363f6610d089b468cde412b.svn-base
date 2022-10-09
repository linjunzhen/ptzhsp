<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--询问记录页面开始--%>
<div class="bsbox clearfix" id="yhxwjlxx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>银行询问记录</span></li>
        </ul>
    </div>
    <div name="yhxwjlInfo">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 25px 30px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" id="yhxwjlInfo">
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>1、提交的有关材料及本表是否为你本人或代理人自愿签署的？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="YH_XWJL1" width="500" typecode="YesOrNo" value="${busRecord.YH_XWJL1}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>2、除申请人之外，申请登记的不动产是否还有其他共有人？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="YH_XWJL2" width="500" typecode="YesOrNo" value="${busRecord.YH_XWJL2}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                3、按份共有的（1）是否知悉共有的不动产可由占份额三分之二以上的按份共有人进行处分的相关规定？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="YH_XWJL3" width="500" typecode="YesOrNo" value="${busRecord.YH_XWJL3}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                4、按份共有的（2）对按份共有的不动产处分，共有人之间是否另有约定？有约定的，申请人应当提交约定协议书
                            </td>
                            <td>
                                <eve:radiogroup fieldname="YH_XWJL4" width="500" typecode="YesOrNo" value="${busRecord.YH_XWJL4}"></eve:radiogroup>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>