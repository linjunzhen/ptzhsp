<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
    .eflowbutton {
        background: #3a81d0;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height: 26px;
        color: #fff;
        border-radius: 5px;
    }
</style>

<script type="text/javascript">
    $(function() {});
</script>
<!-- 房屋基本开始 -->
<div class="bsbox clearfix" id="fwjbxx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>房屋基本信息</span></li>
        </ul>
    </div>
    <div name="fwjbxx">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" id="djshxxInfo">
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>房地坐落：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="60" id="FW_ZL" name="FW_ZL"
                                       value="${busRecord.FW_ZL}" />
                            </td>
                            <th class="tab_width">幢号：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_ZH" style="float: left;" name="FW_ZH"
                                       value="${busRecord.FW_ZH}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">所在层：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_SZC" style="float: left;"
                                       name="FW_SZC" value="${busRecord.FW_SZC}" />
                            </td>
                            <th class="tab_width">户号：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_HH" style="float: left;" name="FW_HH"
                                       value="${busRecord.FW_HH}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">总层数：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" id="FW_ZCS" style="float: left;"
                                       name="FW_ZCS" value="${busRecord.FW_ZCS}" />
                            </td>
                            <th class="tab_width">规划用途：</th>
                            <td>
                                <eve:eveselect clazz="tab_text" dataParams="GHYT"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择规划用途" name="FW_GHYT" id="FW_GHYT" value="${busRecord.FW_GHYT}">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">竣工时间：</th>
                            <td>
                                <input type="text" id="FW_JGSJ" name="FW_JGSJ"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate"
                                       maxlength="60" style="width:184px;" />
                            </td>
                            <th class="tab_width">用途说明：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_YTSM" style="float: left;"
                                       name="FW_YTSM" value="${busRecord.FW_YTSM}"  />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">备案合同号：</th>
                            <td colspan="3">
                                <input type="text" class="tab_text validate[]" maxlength="32" style="float: left;width: 72%;" id="FW_BAHTH"
                                       name="FW_BAHTH" value="${busRecord.FW_BAHTH}" />
<%--                                <input type="button" style="margin-left: 50px;" class="eflowbutton" value="查询备案信息" onclick="showSelectFdchtbacx();"/>--%>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">房屋性质：</th>
                            <td>
                                <eve:eveselect clazz="tab_text" dataParams="FWXZ"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择房屋性质" name="FW_XZ" id="FW_XZ" value="${busRecord.FW_XZ}">
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">房屋结构：</th>
                            <td>
                                <eve:eveselect clazz="tab_text" dataParams="FWJG"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择房屋结构" name="FW_FWJG" id="FW_FWJG" value="${busRecord.FW_FWJG}">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">交易价格（万）：</th>
                            <td>
                                <input type="text" class="tab_text validate[]" maxlength="30" id="FW_JYJG" style="float: left;"
                                       name="FW_JYJG" value="${busRecord.FW_JYJG}" />
                            </td>
                            <th class="tab_width">独用土地面积（平方米）：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_DYDYTDMJ" style="float: left;"
                                       name="FW_DYDYTDMJ" value="${busRecord.FW_DYDYTDMJ}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">分摊土地面积（平方米）：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_FTTDMJ" style="float: left;"
                                       name="FW_FTTDMJ" value="${busRecord.FW_FTTDMJ}" />
                            </td>
                            <th class="tab_width">建筑面积（平方米）：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="FW_JZMJ" style="float: left;"
                                       name="FW_JZMJ" value="${busRecord.FW_JZMJ}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">房屋共有情况：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="GYFS"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择房屋共有情况" name="FW_GYQK" id="FW_GYQK" value="${busRecord.FW_GYQK}">
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">专有建筑面积（平方米）：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" id="FW_ZYJZMJ" style="float: left;"
                                       name="FW_ZYJZMJ" value="${busRecord.FW_ZYJZMJ}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">分摊建筑面积（平方米）：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" id="FW_FTJZMJ" style="float: left;"
                                       name="FW_FTJZMJ" value="${busRecord.FW_FTJZMJ}" />
                            </td>
                            <th class="tab_width">权利类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text" dataParams="ZDQLLX"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择权利类型" name="FW_QLLX" id="FW_QLLX" value="${busRecord.FW_QLLX}">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">土地使用权人：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" id="FW_TDSYQR" style="float: left;"
                                       name="FW_TDSYQR" value="${busRecord.FW_TDSYQR}" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>是否单登记房产：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyValue="0" name="FW_SFDJ" id="FW_SFDJ" value="${busRecord.FW_SFDJ}">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">登记时间：</th>
                            <td>
                                <input type="text" id="FW_DJRQ" name="FW_DJRQ"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text Wdate"
                                       maxlength="60" value="${busRecord.FW_DJRQ}" />
                            </td>
                            <th class="tab_width">登簿人：</th>
                            <td>
                                <input type="text" class="tab_text " value="${busRecord.FW_DBRMC}" name="FW_DBRMC" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">登记原因：</th>
                            <td colspan="3">
                                <input type="text" class="tab_text" maxlength="80" name="FW_DJYY" style="width: 72%;"
                                       value="${busRecord.FW_DJYY}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">附记：</th>
                            <td colspan="3">
                                <textarea name="FW_FJ" cols="100" rows="5" name="FW_FJ"
                                          class="validate[[maxSize[200]]">${busRecord.FW_FJ}</textarea>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- 房屋基本结束 -->

