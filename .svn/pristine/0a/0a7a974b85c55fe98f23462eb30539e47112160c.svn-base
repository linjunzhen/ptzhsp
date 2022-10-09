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
                           name="ITEM_NAME" value="国有建设用地使用权首次登记"/></td>
            </tr>
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>申请人(单位)：</th>
                <td colspan="3">
                    <div style="display: flex;align-items: center">
                        <input type="text" class="tab_text validate[required]"
                               name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT}"/>
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
             	<th><span class="bscolor1">*</span>持证类型：</th>
                <td>
                    <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="CZLX"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
                    </eve:eveselect>
                </td>
                <th class="tab_width"><span class="bscolor1">*</span>共有方式：</th>
                <td>
                 	<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="GYFS"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择共有方式" name="GYTD_GYFS" id="GYTD_GYFS" value="${busRecord.GYTD_GYFS}">
                    </eve:eveselect>
                </td>
            </tr>
            <tr>
                <th class="tab_width"><span class="bscolor1">*</span>坐落：</th>
                <td ><input type="text" class="tab_text validate[required]" maxlength="128"
                                       name="LOCATED" value="${busRecord.LOCATED}"/>
                </td>
                <th class="tab_width">委托期限：</th>
                <td ><%-- <input type="text" class="tab_text validate[required]" maxlength="32"
                                       name="WTQX" value="${busRecord.WTQX}"/> --%>
                    <input type="text" class="tab_text" id="WTQX_BEGIN" name="WTQX_BEGIN"
					value="${busRecord.WTQX_BEGIN}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'WTQX_END\')}'})"
					class="tab_text Wdate" maxlength="32" style="width:130px;background: #fff url(/plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;"/>至
					<input type="text" class="tab_text" id="WTQX_END" name="WTQX_END"
					value="${busRecord.WTQX_END}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'WTQX_BEGIN\')}'})"
					class="tab_text Wdate" maxlength="32" style="width:130px;background: #fff url(/plug-in/My97DatePicker/skin/datePicker.gif) no-repeat right;"/>
                </td>
            </tr>
            <tr>
                <th class="tab_width">通知人姓名：</th>
                <td><input type="text" class="tab_text validate[]"
                           name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
                <th class="tab_width">通知人电话：</th>
                <td><input type="text" class="tab_text validate[,custom[mobilePhoneLoose]]" maxlength="11"
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
                <td colspan="3">
                <input type="text" class="tab_text validate[]" maxlength="60" style="width: 850px;" name="REMARK" value="${busRecord.REMARK}"/>
                </td>
            </tr>
        </table>
    </div>
</div>
