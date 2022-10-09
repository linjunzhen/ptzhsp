<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<div id="applyLhdjDiv">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th>申请人双方信息</th>
        </tr>
        <tr style="padding: 10px;">
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>男方姓名：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="8" id="MALE_NAME" name="MALE_NAME"
                               value="${busRecord.MALE_NAME}" />
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>身份证号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required,custom[vidcard]]" maxlength="18" id="MALE_CARD" style="float: left;" name="MALE_CARD"
                               value="${busRecord.MALE_CARD}" />
                        <a href="javascript:void(0);" onclick="maleRead(this);" class="eflowbutton">身份证读卡</a>
                    </td>

                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>手机号码：</td>
                    <td>
                        <input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11" id="MALE_PHONE" name="MALE_PHONE"
                               value="${busRecord.MALE_PHONE}" />
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>女方姓名：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="8" id="FEMALE_NAME" name="FEMALE_NAME"
                               value="${busRecord.FEMALE_NAME}" />
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>身份证号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required,custom[vidcard]]" maxlength="18" id="FEMALE_CARD" style="float: left;" name="FEMALE_CARD"
                               value="${busRecord.FEMALE_CARD}" />
                        <a href="javascript:void(0);" onclick="femaleRead(this);" class="eflowbutton">身份证读卡</a>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>手机号码：</td>
                    <td>
                        <input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11" id="FEMALE_PHONE" name="FEMALE_PHONE"
                               value="${busRecord.FEMALE_PHONE}" />
                    </td>
                </tr>
            </table>
        </tr>
    </table>
</div>