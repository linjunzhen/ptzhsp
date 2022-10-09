<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%--询问记录页面开始--%>
<div class="bsbox clearfix" style="display: none">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>不动产申请表询问记录</span></li>
        </ul>
    </div>
    <div id="xwjlxx" name="xwjlInfo">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" id="xwjlInfo">
                        <tr>
                            <th colspan="4" style="text-align: center;">
                                <span style="color: #426fa4;font-weight: 700;">义务人</span>
                            </th>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>1、申请登记事项是否为申请人的真实意思表达？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_YWR1" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_YWR1}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>2、申请登记的不动产是否有争议、纠纷或是被查封、冻结、抵押？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_YWR2" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_YWR2}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>3、申请登记的不动产是否为单独所有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_YWR3" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_YWR3}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>4、申请登记的不动产是否为共同共有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_YWR4" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_YWR4}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>5、申请登记的不动产是否为按份共有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_YWR5" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_YWR5}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                所占份额分别为：
                            </td>
                            <td colspan="3">
                                <input type="text" class="tab_text" maxlength="128" style="width: 80%;"
                                       name="BDC_XWJL_YWRSZFE" value="${busRecord.BDC_XWJL_YWRSZFE}"/></td>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                6、其他需要询问的有关事项：
                            </td>
                            <td colspan="3">
                                <input type="text" class="tab_text" maxlength="128" style="width: 80%;"
                                       name="BDC_XWJL_YWR6" value="${busRecord.BDC_XWJL_YWR6}"/></td>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="4" style="text-align: center;">
                                <span style="color: #426fa4;font-weight: 700;">权利人</span>
                            </th>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>1、申请登记事项是否为申请人的真实意思表达？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_QLR1" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_QLR1}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>2、申请登记的不动产是否有争议、纠纷或是被查封、冻结、抵押？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_QLR2" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_QLR2}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>3、申请登记的不动产是否为单独所有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_QLR3" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_QLR3}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>4、申请登记的不动产是否为共同共有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_QLR4" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_QLR4}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <font class="tab_color">*</font>5、申请登记的不动产是否为按份共有？
                            </td>
                            <td>
                                <eve:radiogroup fieldname="BDC_XWJL_QLR5" width="500" typecode="YesOrNo" value="${busRecord.BDC_XWJL_QLR5}"></eve:radiogroup>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                所占份额分别为：
                            </td>
                            <td colspan="3">
                                <input type="text" class="tab_text" maxlength="128" style="width: 80%;"
                                       name="BDC_XWJL_QLRSZFE" value="${busRecord.BDC_XWJL_QLRSZFE}"/></td>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                6、其他需要询问的有关事项：
                            </td>
                            <td colspan="3">
                                <input type="text" class="tab_text" maxlength="128" style="width: 80%;"
                                       name="BDC_XWJL_QLR6" value="${busRecord.BDC_XWJL_QLR6}"/></td>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="4" style="text-align: center;">
                                <span style="color: #426fa4;font-weight: 700;">备注</span>
                            </th>
                        </tr>
                        <tr>
                            <td colspan="4" style="padding: 8px;text-align: center;">
                                <textarea cols="140" rows="5" maxlength="256" style="width: 90%;"
                                       name="BDC_XWJL_BZ" value="${busRecord.BDC_XWJL_BZ}"></textarea>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>