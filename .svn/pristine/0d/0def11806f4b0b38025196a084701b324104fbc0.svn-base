<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>

<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>抵押权人信息</span></li>
        </ul>
    </div>
    <table cellpadding="0" cellspacing="1" class="bstable1">
        <tr>
            <td style="padding: 25px 30px">
                <table class="bstable1">
                    <tr>
                        <th class="tab_width">抵押权人性质：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYQRXZ" defaultEmptyValue="2" onchange="dyqrChangeFun(this.value,true);"
                                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择抵押权人性质"
                                           name="DYQRXX_NATURE" value="${busRecord.DYQRXX_NATURE}">
                            </eve:eveselect>
                        </td>
                        <th class="tab_width"><font class="tab_color ">*</font>抵押权人：</th>
                        <td>
                            <div id="dyr_gr" style="display:none;">
                                <input type="text" class="tab_text w280 validate[required]" maxlength="128" id="DYQRXX_NAME2"
                                       name="DYQRXX_NAME"    value="${busRecord.DYQRXX_NAME}"/>
                            </div>
                            <div id="dyr_qy" style="">
                                <input class="easyui-combobox tab_text_1 tab_text w280 validate[required]" name="DYQRXX_NAME" id="DYQRXX_NAME" maxlength="128"
                                       data-options="
                                    prompt : '请输入或者选择抵押权人',
                                    url: 'bdcGyjsjfwzydjController.do?loadBdcBankList',
                                    method: 'get',
                                    valueField : 'DIC_NAME',
                                    textField : 'DIC_NAME',
                                    filter : function(q, row) {
                                        var opts = $(this).combobox('options');
                                        return row[opts.textField].indexOf(q) > -1;
                                    },
                                    onSelect:function(record){
                                        $('#DYQRXX_IDNO').val(record.DIC_CODE);
                                        $('#DYQRXX_LEGAL_NAME').val(record.BANK_LEGAL_NAME);
                                        $('#DYQRXX_LEGAL_IDNO').val(record.BANK_LEGAL_CARD);
                                        $('#DYQRXX_AGENT_NAME').val(record.BANK_CONTECT_NAME);
                                        $('#DYQRXX_AGENT_IDNO').val(record.BANK_CONTECT_CARD);
                                        $('#DYQRXX_AGENT_PHONE').val(record.BANK_CONTECT_PHONE);
                                    }
                                "/>
<%--                                <span style="width:25px;display:inline-block;text-align:right;">--%>
<%--                                        <img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','DYQRXX_NAME');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">--%>
<%--                                  </span>--%>
<%--                                <span style="width:25px;display:inline-block;text-align:right;">--%>
<%--                                        <img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('DYQRXX_NAME');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">--%>
<%--                                  </span>--%>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font class="tab_color">*</font>证件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DYQRXX_IDNO');"
                                           defaultEmptyText="选择证件类型" name="DYQRXX_IDTYPE" value="营业执照" >
                            </eve:eveselect>
                        </td>
                        <th class="tab_width"><font class="tab_color ">*</font>证件号码：</th>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQRXX_IDNO"
                                   name="DYQRXX_IDNO"    value="${busRecord.DYQRXX_IDNO}"/>
                        </td>
                    </tr>
                    <tr id="dyqrdh">
                        <th class="tab_width">抵押权人电话：</th>
                        <td colspan="3">
                            <input type="text" class="tab_text" maxlength="32" id="DYQRXX_PHONE"
                                   name="DYQRXX_PHONE" value="${busRecord.DYQRXX_PHONE}" />
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">地址：</th>
                        <td>
                            <input type="text" class="tab_text" maxlength="500" id="DYQRXX_ADDR"
                                   name="DYQRXX_ADDR" value="${busRecord.DYQRXX_ADDR}" />
                        </td>
                        <th class="tab_width">电子邮件：</th>
                        <td>
                            <input type="text" class="tab_text" maxlength="128" id="DYQRXX_EMAIL"
                                   name="DYQRXX_EMAIL" value="${busRecord.DYQRXX_EMAIL}" />
                        </td>
                    </tr>

                    <tr>
                        <th class="tab_width"><font id="DYQRXX_LEGAL_NAME_font" class="tab_color">*</font>负责人姓名：</th>
                        <td ><input type="text" class="tab_text w280 validate[required]" id="DYQRXX_LEGAL_NAME"
                                    name="DYQRXX_LEGAL_NAME" maxlength="32" value="${busRecord.DYQRXX_LEGAL_NAME}"/></td>
                        <th class="tab_width">证件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyValue="身份证" name="DYQRXX_LEGAL_IDTYPE" id="DYQRXX_LEGAL_IDTYPE"  value="${busRecord.DYQRXX_LEGAL_IDTYPE}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">证件号码：</th>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="DYQRXX_LEGAL_IDNO"
                                   name="DYQRXX_LEGAL_IDNO" value="${busRecord.DYQRXX_LEGAL_IDNO}" />
                        </td>
                    </tr>
                    <tr id="bdcqzh_tr" style="display: none;">
                        <th class="tab_width">不动产登记证明号：</th>
                        <td colspan="3">
                            <input type="text" class="tab_text validate[]" maxlength="500" id="BDC_BDCDJZMH" readonly="readonly"
                                   style="float: left;width: 850px;" name="BDC_BDCDJZMH" value="${busRecord.BDC_BDCDJZMH}" />
                        </td>
                    </tr>
                    <tr id="bdcqzbsm_tr" style="display:none;">
                        <th class="tab_width">权证标识码：</th>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QZBSM"
                                   name="BDC_QZBSM" value="${busRecord.BDC_QZBSM}" />
                            <input id="qzbsmsavebtn" style="display:none;" type="button" class="eflowbutton" value="保存" onclick="saveQzbsm();" />
                        </td>

                        <th class="tab_width">操作人：</th>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_CZR"
                                   name="BDC_CZR" value="${busRecord.BDC_CZR}" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr id="bdcczr_tr" style="display:none;">
                        <th class="tab_width">操作时间：</th>
                        <td>
                            <input type="text" class="tab_text" maxlength="32" id="BDC_CZSJ"
                                   name="BDC_CZSJ" value="${busRecord.BDC_CZSJ}" readonly="readonly"/>
                        </td>
                    </tr>

                    <tr>
                        <th class="tab_width"><font id="DYQRXX_AGENT_NAME_font" class="tab_color ">*</font>代理人姓名：</th>
                        <td ><input type="text" class="tab_text w280 validate[required]"
                                    name="DYQRXX_AGENT_NAME" id="DYQRXX_AGENT_NAME" maxlength="128" value="${busRecord.DYQRXX_AGENT_NAME}"/></td>
                        <th class="tab_width"><font id="DYQRXX_AGENT_IDTYPE_font" class="tab_color ">*</font>证件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyValue="身份证" name="DYQRXX_AGENT_IDTYPE" id="DYQRXX_AGENT_IDTYPE"  value="${busRecord.DYQRXX_AGENT_IDTYPE}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font id="DYQRXX_AGENT_IDNO_font" class="tab_color ">*</font>证件号码：</th>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQRXX_AGENT_IDNO"
                                   name="DYQRXX_AGENT_IDNO" id="DYQRXX_AGENT_IDNO"  value="${busRecord.DYQRXX_AGENT_IDNO}"/>
                        </td>
                        <th class="tab_width dlrdh"><font id="DYQRXX_AGENT_PHONE_font" class="tab_color ">*</font>电话：</th>
                        <td class="dlrdh">
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="DYQRXX_AGENT_PHONE"
                                   name="DYQRXX_AGENT_PHONE" id="DYQRXX_AGENT_PHONE"  value="${busRecord.DYQRXX_AGENT_PHONE}"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">备注：</th>
                        <td colspan="3"><input type="text" class="tab_text" maxlength="60"
                                               name="DYQRXX_REMARK" value="${busRecord.DYQRXX_REMARK}" style="width: 850px;"  />
                        </td>
                    </tr>
                    <input type="hidden" name="DYQRXX_FILE_URL" >
                    <input type="hidden" name="DYQRXX_FILE_ID">
                </table>
            </td>
        </tr>
    </table>
</div>

<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>抵押权登记</span></li>
        </ul>
    </div>
    <table cellpadding="0" cellspacing="1" class="bstable1" id="powerInfo">
        <tr id="powerInfo_1">
            <td style="padding: 25px 30px">
                <table class="bstable1">
                    <tr>
                        <th class="tab_width"><font class="tab_color ">*</font>抵押人：</th>
                        <td ><input type="text" class="tab_text validate[required]"
                                    name="DYQDJ_NAME" maxlength="128" value="${busRecord.DYQDJ_NAME}" onblur="checkLimitPerson();"/>
                        </td>
                        <th class="tab_width"><font class="tab_color ">*</font>证件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件类型" name="DYQDJ_IDTYPE" id="DYQDJ_IDTYPE"  value="${busRecord.DYQDJ_IDTYPE}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font class="tab_color ">*</font>证件号码：</th>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQDJ_IDNO"
                                   name="DYQDJ_IDNO"  value="${busRecord.DYQDJ_IDNO}" onblur="checkLimitPerson();"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">代理人姓名：</th>
                        <td ><input type="text" class="tab_text validate[]"
                                    name="DYQDJ_AGENT_NAME" maxlength="128" value="${busRecord.DYQDJ_AGENT_NAME}"/></td>
                        <th class="tab_width">证件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件类型" name="DYQDJ_AGENT_IDTYPE" id="DYQDJ_AGENT_IDTYPE"  value="${busRecord.DYQDJ_AGENT_IDTYPE}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">证件号码：</th>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="DYQDJ_AGENT_IDNO"
                                   name="DYQDJ_AGENT_IDNO"  value="${busRecord.DYQDJ_AGENT_IDNO}"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font class="tab_color ">*</font>是否最高额抵押：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YesOrNo"
                                           dataInterface="dictionaryService.findDatasForSelect" onchange="setSfzgedy(this.value);"
                                           defaultEmptyValue="0" name="DYQDJ_SFZGEDY" id="DYQDJ_SFZGEDY"  value="${busRecord.DYQDJ_SFZGEDY}">
                            </eve:eveselect>
                        </td>
                        <th class="tab_width"><font class="tab_color ">*</font>抵押方式：</th>
                        <td >
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="BDCDYFS"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择抵押方式" name="DYQDJ_DYFS" id="DYQDJ_DYFS"  value="${busRecord.DYQDJ_DYFS}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr id="dywjz_tr" style="display: none;">
                        <th class="tab_width"><font class="tab_color " id="dywjzFont">*</font>抵押物价值：</th>
                        <td colspan="3">
                            <input type="text" class="tab_text  validate[required]" maxlength="128"
                                   name="DYQDJ_DYWJZ" value="${busRecord.DYQDJ_DYWJZ}" style="float: left;width: 850px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font class="tab_color " id="bdbzzqseFont">*</font>被担保主债权数额(单位：万元)：</th>
                        <td>
                            <input type="text" class="tab_text validate[required] custom[JustNumber]" maxlength="16"
                                   name="DYQDJ_BDBZZQSE" value="${busRecord.DYQDJ_BDBZZQSE}"  />
                        </td>
                        <th class="tab_width"><font class="tab_color " style="display: none;" id="zgzqeFont">*</font>最高债权额(单位：万元)：</th>
                        <td >
                            <input type="text" class="tab_text custom[JustNumber] " maxlength="16"
                                   name="DYQDJ_ZGZQE" value="${busRecord.DYQDJ_ZGZQE}"  />
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">抵押宗数：</th>
                        <td>
                            <input type="text" class="tab_text validate[],custom[integerplus]" maxlength="16"
                                   name="DYQDJ_DYZS" value="${busRecord.DYQDJ_DYZS}"  />
                        </td>
                        <th class="tab_width dyxxHide">抵押宗地总面积：</th>
                        <td class="dyxxHide">
                            <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
                                   name="DYQDJ_DYZSZMJ" value="${busRecord.DYQDJ_DYZSZMJ}" disabled="disabled"/>
                        </td>
                    </tr>
                    <tr class="dyxxHide">
                        <th class="tab_width">抵押建筑总面积：</th>
                        <td>
                            <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
                                   name="DYQDJ_DYJZZMJ" value="${busRecord.DYQDJ_DYJZZMJ}" disabled="disabled"/>
                        </td>
                        <th class="tab_width">抵押分摊土地总面积：</th>
                        <td >
                            <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
                                   name="DYQDJ_DYFTTDZMJ" value="${busRecord.DYQDJ_DYFTTDZMJ}" disabled="disabled" />
                        </td>
                    </tr>
                    <tr class="dyxxHide">
                        <th class="tab_width">抵押独用土地总面积：</th>
                        <td colspan="3"><input type="text" class="tab_text validate[]" maxlength="16"
                                               name="DYQDJ_DYTDMJ" value="${busRecord.DYQDJ_DYTDMJ}" style="width: 850px;" disabled="disabled" />
                        </td>
                    </tr>

                    <tr>
                        <th class="tab_width"><font class="tab_color ">*</font>债务起始时间：</th>
                        <td>
                            <input type="text" id="DYQDJ_ZWQSSJ" name="DYQDJ_ZWQSSJ" value="${busRecord.DYQDJ_ZWQSSJ}"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'DYQDJ_ZWJSSJ\')}'})"
                                   class="tab_text Wdate validate[required]" maxlength="32" style="height: 40px;border: 1px solid #c9deef;" />
                        </td>
                        <th class="tab_width"><font class="tab_color ">*</font>债务结束时间：</th>
                        <td>
                            <input type="text" id="DYQDJ_ZWJSSJ" name="DYQDJ_ZWJSSJ" value="${busRecord.DYQDJ_ZWJSSJ}"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'DYQDJ_ZWQSSJ\')}'})"
                                   class="tab_text Wdate validate[required]" maxlength="32" style="height: 40px;border: 1px solid #c9deef;" />
                        </td>
                    </tr>
                    
                    <tr>
						<th class="tab_width"><font class="tab_color ">*</font>担保范围：</th>
						<td ><input type="text" class="tab_text validate[required]" maxlength="128"
							name="DYQDJ_DBFW" value="${busRecord.DYQDJ_DBFW}" />
						</td>
						<th class="tab_width"><font class="tab_color ">*</font>是否存在禁止或限制转让抵押不动产业务：</th>
						<td>
							<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YesOrNo"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="请选择" name="DYQDJ_JZDY" id="DYQDJ_JZDY"  value="${busRecord.DYQDJ_JZDY}">
							</eve:eveselect>
						</td>
				   </tr>
                    
                    <tr>
                        <th class="tab_width"><font class="tab_color ">*</font>登记原因：</th>
                        <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
                                               name="DYQDJ_DJYY" value="${busRecord.DYQDJ_DJYY}" style="width: 850px;"  />
                        </td>
                    </tr>
                    <tr class="dyxxHide">
                        <th class="tab_width"><font class="tab_color ">*</font>最高债权确定事实：</th>
                        <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
                                               name="DYQDJ_ZGZQQDSS" value="${busRecord.DYQDJ_ZGZQQDSS}" style="width: 850px;"  />
                        </td>
                    </tr>

                    <tr class="dyxxHide">
                        <th class="tab_width"><font class="tab_color ">*</font>抵押范围：</th>
                        <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="500"
                                               name="DYQDJ_DYFW" value="${busRecord.DYQDJ_DYFW}" style="width: 850px;"  />
                        </td>
                    </tr>

                    <tr class="dyxxHide">
                        <th class="tab_width"><font class="tab_color ">*</font>附记：</th>
                        <td colspan="3">
                            <textarea name="DYQDJ_FJ" cols="140" rows="6"
                                      class="validate[[required],maxSize[128]]" style="width: 850px;border: 1px solid #c9deef;" >${busRecord.DYQDJ_FJ}</textarea>
                        </td>
                    </tr>

                    <tr id="bdcdbc_tr" style="display:none">
                        <th class="tab_width">登簿人：</th>
                        <td>
                            <input type="text" class="tab_text" maxlength="128" id="BDC_DBR" readonly="readonly"
                                   name="BDC_DBR" value="${busRecord.BDC_DBR}" />
                        </td>

                        <th class="tab_width">登记时间：</th>
                        <td>
                            <input type="text" class="tab_text" maxlength="32" id="BDC_DBSJ" readonly="readonly"
                                   name="BDC_DBSJ" value="${busRecord.BDC_DBSJ}" />
                        </td>
                    </tr>

                    <input type="hidden" name="DYQDJ_FILE_URL" >
                    <input type="hidden" name="DYQDJ_FILE_ID">
                </table>
            </td>
        </tr>
    </table>
</div>