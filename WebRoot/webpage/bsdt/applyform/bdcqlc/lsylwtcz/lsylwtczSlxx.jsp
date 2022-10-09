<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='slxx'>
    <tr>
        <th colspan="4">受理信息</th>
    </tr>
    <tr>
        <td class="tab_width">
            <font class="tab_color">*</font>
            登记类型：
        </td>
        <td>
            <input type="text" class="tab_text " disabled="disabled" name="CATALOG_NAME"
                   value="建设用地使用权及房屋所有权首次登记" />
        </td>
        <td class="tab_width">
            <font class="tab_color">*</font>
            权利类型：
        </td>
        <td>
            <input type="text" class="tab_text " disabled="disabled" style="width: 72%;"
                   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/>
        </td>
    </tr>
    <tr>
        <td class="tab_width">
            <font class="tab_color" id="bdcdyhFont" style="display: none;">*</font>不动产单元号：
        </td>
        <td>
            <input type="text" class="tab_text validate[custom[estateNum]]" name="ESTATE_NUM"
                   value="${busRecord.ESTATE_NUM }" style="width: 72%;"/>
            <input type="button" class="eflowbutton" style="display: none;" value="查询房屋单元信息" id="fwdyxxBtn" onclick="showSelectFwdyxx();"/>
        </td>
        <td class="tab_width">
            <font class="tab_color">*</font>
            持证类型：
        </td>
        <td style="width:430px">
            <eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
                           dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                           defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE"
                           value="${busRecord.TAKECARD_TYPE}">
            </eve:eveselect>
        </td>
    </tr>
    <tr>
        <td class="tab_width"><font class="tab_color">*</font>坐落（门牌号）：</td>
        <td>
            <input type="text" class="tab_text validate[required]" maxlength="128" id="LOCATED"
                   name="LOCATED" value="${busRecord.LOCATED}" style="width: 72%;" onblur="findDrawOrg(this.value)"/>
        </td>
        <td class="tab_width"><font class="tab_color">*</font>定着物权利类型：</td>
        <td>
            <eve:eveselect clazz="tab_text validate[required]" dataParams="dzwlx"
                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
                           defaultEmptyText="选择定着物权利类型" name="DZWQLLX" id="DZWQLLX" value="${busRecord.DZWQLLX}">
            </eve:eveselect>
        </td>
    </tr>
    <tr>
        <td class="tab_width">
            <font class="tab_color " id='sqrId' >*</font>
            申请人(单位)：
        </td>
        <td>
            <input type="text" class="tab_text validate[required]" name="APPLICANT_UNIT"
                   value="${busRecord.APPLICANT_UNIT }" maxlength="64" style="width: 72%;"/>
        </td>
        <td class="tab_width">
            <font class="tab_color ">*</font>
            测绘公司
        </td>
        <td>
            <eve:eveselect clazz="tab_text validate[required]" dataParams="" onchange="changeDrawOrg(this.value);"
                           dataInterface="bdcExecutionService.getDrawOrgList"
                           defaultEmptyText="选择测绘公司" name="CHGS_ID" id="CHGS_ID"
                           value="${busRecord.CHGS_ID}">
            </eve:eveselect>
        </td>
    </tr>
    <tr>
        <td class="tab_width">
            <font class="tab_color ">*</font>本幢是否首次测绘
        </td>
        <td>
            <eve:radiogroup typecode="YesOrNo" width="130px" fieldname="SFSCCH"></eve:radiogroup>
        </td>
        <td></td>
        <td></td>
    </tr>
    <tr id="upload">
        <td class="tab_width">测绘材料上传：</td>
        <td colspan="3">
            <div style="margin-top: 4px;">
                <a href="javascript:void(0);" onclick="openDrawFileUploadDialog()" id="uploadFileBtn" style="display: none;">
                    <img src="webpage/bsdt/applyform/images/tab_btn_sc.png" style="width:60px;height:22px;">
                </a>
                <span id="drawFileUpload"></span>
            </div>
        </td>
    </tr>
    <tr>
        <td class="tab_width">通知人姓名：</td>
        <td><input type="text" class="tab_text " style="width: 72%;"
                   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
        <td class="tab_width">通知人电话：</td>
        <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11" style="width: 80%;"
                   name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
    </tr>
    <tr>
        <td class="tab_width">备注：</td>
        <td colspan="3"><input type="text" class="tab_text validate[]" maxlength="128"
                               name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
        </td>
    </tr>

</table>