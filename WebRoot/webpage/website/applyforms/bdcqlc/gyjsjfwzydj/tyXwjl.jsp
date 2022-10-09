<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script>
    function checkGyfs(val) {
        if (val) {
            if (val == 'ddsy') {
                $("#tyxwjl3").hide();
                $("#tyxwjl4").hide();
            } else {
                $("#tyxwjl3").show();
                $("#tyxwjl4").show();
            }
        }
    }
</script>

<%--询问记录页面开始--%>
<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>通用询问记录</span></li>
        </ul>
    </div>
    <div id="tyxwjlxx" name="tyxwjlInfo">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" id="tyxwjlInfo">
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>1．申请登记事项是否为申请人的真实意思表示？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL1" width="500" typecode="YesOrNo" value="${busRecord.TY_XWJL1}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>2．申请登记的不动产是共有，还是单独所有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL2" width="500" typecode="SYFS" onclick="checkGyfs(this.value);" value="${busRecord.TY_XWJL2}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr style="display: none;" id="tyxwjl3">
                            <td colspan="3">
                                <font class="tab_color">*</font>3．申请登记的不动产是按份共有，还是共同共有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL3" width="500" typecode="GYFS1" value="${busRecord.TY_XWJL3}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr style="display: none;" id="tyxwjl4">
                            <td>
                                <font class="tab_color">*</font>4．申请登记的不动产共有份额情况？
                            </td>
                            <td colspan="3">
                                <input type="text" class="tab_text validate[required]" maxlength="256" style="width: 80%;"
                                       name="TY_XWJL4" value="${busRecord.TY_XWJL4}"/></td>
                            </td>
                        </tr>
                        <tr style="display: none;" id="tyxwjl5">
                            <td colspan="3">
                                <font class="tab_color">*</font>5．申请异议登记时，权利人是否不同意办理更正登记？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL5" width="500" typecode="YesOrNo" value="${busRecord.TY_XWJL5}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr style="display: none;" id="tyxwjl6">
                            <td colspan="3">
                                <font class="tab_color">*</font>6．申请异议登记时，是否已知悉异议不当应承担的责任？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL6" width="500" typecode="YesOrNo" value="${busRecord.TY_XWJL6}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr style="display: none;" id="tyxwjl7">
                            <td colspan="3">
                                <font class="tab_color">*</font>7．申请本次转移登记时，其他按份共有人是否同意。
                            </td>
                            <td>
                                <eve:radiogroup fieldname="TY_XWJL7" width="500" typecode="YesOrNo" value="${busRecord.TY_XWJL7}"></eve:radiogroup>
                            </td>
                        </tr>
<%--                        <tr>--%>
<%--                            <td>--%>
<%--                                其他需要询问的有关事项：--%>
<%--                            </td>--%>
<%--                            <td colspan="3">--%>
<%--                                <input type="text" class="tab_text" maxlength="256" style="width: 80%;"--%>
<%--                                       name="TY_XWJL8" value="${busRecord.TY_XWJL8}"/></td>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>