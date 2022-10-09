<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>受理信息</span></li>
        </ul>
    </div>
    <div style="padding: 25px 30px">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>登记类型：</th>
                <td><input type="text" class="tab_text " disabled="disabled"
                           name="CATALOG_NAME" value="首次登记"/></td>
                <th class="tab_width"><span class="bscolor1">*</span>权利类型：</th>
                <td><input type="text" class="tab_text " disabled="disabled"
                           name="ITEM_NAME" value="抵押权首次登记"/></td>
            </tr>
            <tr>
                <th class="tab_width"><font class="tab_color ">*</font>类型：</th>
                <td colspan="3">
                    <input type="radio" name="TAKECARD_TYPE" value="1" <c:if test="${busRecord.TAKECARD_TYPE != null && busRecord.TAKECARD_TYPE!='2'}">checked="checked"</c:if> >抵押权首次登记
                    <input type="radio" name="TAKECARD_TYPE" value="2" <c:if test="${busRecord.TAKECARD_TYPE != null && busRecord.TAKECARD_TYPE=='2'}">checked="checked"</c:if> >抵押权首次登记（转本）
                </td>
            </tr>
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>申请人(单位)：</th>
                <td colspan="3">
                    <div style="display: flex;align-items: center">
                        <input type="text" class="tab_text validate[required]"
                               name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/>
                        <div onclick="openXxcjFileUploadDialog();" style="margin-left: 10px;cursor: pointer;">
                            <img src="webpage/bsdt/applyform/images/tab_btn_sc.png"
                                 style="width:60px;height:22px;"/>
                        </div>
                        <div style="margin-left: 10px;">
                            <span id=fileListDiv></span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>坐落：</th>
                <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
                                       name="LOCATED" value="${busRecord.LOCATED}" style="width: 850px;"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>通知人姓名：</th>
                <td><input type="text" class="tab_text validate[required]"
                           name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
                <th class="tab_width"><span class="bscolor1">*</span>通知人电话：</th>
                <td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
                           name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
            </tr>
            <tr id="lzrInfo">
                <th class="tab_width">领证人姓名：</th>
                <td><input type="text" class="tab_text "
                           name="QZR_NAME" value="${busRecord.QZR_NAME }"/></td>
                <th class="tab_width">领证人证件号：</th>
                <td>
                    <input type="text" class="tab_text" maxlength="64" name="QZR_ZJH" value="${busRecord.QZR_ZJH}"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width">备注：</th>
                <td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
                                       name="REMARK" value="${busRecord.REMARK}" style="width: 850px;"  />
                </td>
            </tr>
        </table>
    </div>
</div>
