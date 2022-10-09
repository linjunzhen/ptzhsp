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

    .eflowbutton-disabled {
        background: #94C4FF;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height: 26px;
        color: #E9E9E9;
        border-radius: 5px;
    }

    .selectType {
        margin-left: -100px;
    }

    .bdcdydacxTrRed {
        color: red;
    }
	.tab_textNew{
		width: 280px;
		height: 40px;
		line-height: 40px;
		/* padding: 0 5px; */
		font-size: 16px;
		color: #000000;
		padding: 0 10px;
		box-sizing: border-box;
		border: 1px solid #c9deef;
	}
</style>
<script type="text/javascript">
    $(function() {

    });
</script>
<!-- 宗地基本信息开始 -->
<div class="bsbox clearfix" id="zdjbxx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>宗地基本信息</span></li>
        </ul>
    </div>
    <div name="zdqlxx_zdjbxx" style="padding: 25px 30px;">
        <table cellpadding="0" cellspacing="0" class="bstable1">
            <tr>
                <th class="tab_width">宗地代码：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="19" id="ZD_BM"  name="ZD_BM"
                           value="${busRecord.ZD_BM}" />
                </td>
                <th class="tab_width">权利类型：</th>
                <td>
                    <eve:eveselect clazz="tab_text w280" dataParams="ZDQLLX"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择权利类型" name="ZD_QLLX" id="ZD_QLLX" value="${busRecord.ZD_QLLX}">
                    </eve:eveselect>
                </td>
            </tr>
            <tr>
                <th class="tab_width">宗地特征码：</th>
                <td>
                    <eve:eveselect clazz="tab_text  w280" dataParams="zdtzm"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   defaultEmptyText="选择宗地特征码" name="ZD_TZM" id="ZD_TZM" value="${busRecord.ZD_TZM}">
                    </eve:eveselect>
                </td>
                <th class="tab_width">权利设定方式：</th>
                <td>
                    <eve:eveselect clazz="tab_text w280" dataParams="ZDQLSDFS"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择权利设定方式" name="ZD_QLSDFS" id="ZD_QLSDFS" value="${busRecord.ZD_QLSDFS}">
                    </eve:eveselect>
                </td>
            </tr>
            <tr>
                <th class="tab_width">坐落：</th>
                <td colspan="3">
                    <input class="easyui-combobox tab_textNew tab_text" name="ZDZL_XIAN" id="ZDZL_XIAN" data-options="prompt:'请选择区县'">
                    <input class="easyui-combobox tab_textNew tab_text" name="ZDZL_ZHENG" id="ZDZL_ZHENG" data-options="prompt:'请选择镇'">
                    <input class="easyui-combobox tab_textNew tab_text" name="ZDZL_CUN" id="ZDZL_CUN" data-options="prompt:'请选择乡村'">
                    <br>
                    <input type="text" class="tab_text" maxlength="60" name="ZD_ZL" value="${busRecord.ZD_ZL}"/>
                </td>
            </tr>
            <tr>
                <th class="tab_width">宗地面积：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_MJ"  name="ZD_MJ"
                           value="${busRecord.ZD_MJ}"  />
                </td>
                <th class="tab_width">土地用途：</th>
                <td>
                    <%--				<eve:eveselect clazz="tab_text  w280" dataParams="tdyt"--%>
                    <%--					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"--%>
                    <%--					defaultEmptyText="选择土地用途" name="ZD_TDYT" id="ZD_TDYT" value="${busRecord.ZD_TDYT}">--%>
                    <%--				</eve:eveselect>--%>
                    <input id="ZD_TDYT" class="easyui-combobox tab_textNew" name="ZD_TDYT"/>
                </td>
            </tr>
            <tr>
                <th class="tab_width">用途说明：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_YTSM" 
                           name="ZD_YTSM" value="${busRecord.ZD_YTSM}" />
                </td>
                <th class="tab_width">权利性质：</th>
                <td>
                    <%-- <eve:eveselect clazz="tab_text w280" dataParams="DocumentType"
                                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                defaultEmptyText="选择权利性质" name="ZD_QLXZ" id="ZD_QLXZ" value="${busRecord.ZD_QLXZ}">
                                </eve:eveselect> --%>
                    <input class="easyui-combobox tab_textNew" name="ZD_QLXZ" id="ZD_QLXZ"
                           value="${busRecord.ZD_QLXZ}"
                           data-options="
                                                    prompt : '请选择选择权利性质',
                                                    url: 'bdcApplyController/fluidComboBox.do',
                                                    method: 'get',
                                                    valueField:'value',
                                                    textField:'text',
                                                    groupField:'group',
                                                    editable:false
                                                ">
                </td>
            </tr>
            <tr>
                <th class="tab_width">等级：</th>
                <td>
                    <eve:eveselect clazz="tab_text w280" dataParams="ZDLEVEL"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择等级" name="ZD_LEVEL" id="ZD_LEVEL" value="${busRecord.ZD_LEVEL}">
                    </eve:eveselect>
                </td>
                <th class="tab_width">容积率：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_RJL" 
                           name="ZD_RJL" value="${busRecord.ZD_RJL}"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width">建筑限高（米）：</th>
                <td>
                    <input type="text" class="tab_text validate[custom[number]]" maxlength="30" id="ZD_JZXG" 
                           name="ZD_JZXG" value="${busRecord.ZD_JZXG}"  />
                </td>
                <th class="tab_width">建筑密度：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_JZMD" 
                           name="ZD_JZMD" value="${busRecord.ZD_JZMD}"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width">宗地四至_东：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_E"  name="ZD_E"
                           value="${busRecord.ZD_E}"  />
                </td>
                <th class="tab_width">宗地四至_南：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_S"  name="ZD_S"
                           value="${busRecord.ZD_S}"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width">宗地四至_西：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_W"  name="ZD_W"
                           value="${busRecord.ZD_W}"  />
                </td>
                <th class="tab_width">宗地四至_北：</th>
                <td>
                    <input type="text" class="tab_text " maxlength="30" id="ZD_N"  name="ZD_N"
                           value="${busRecord.ZD_N}"  />
                </td>
            </tr>
        </table>
    </div>
</div>

<!-- 国有土地使用权-权利信息开始 -->
<div class="bsbox clearfix" id="gyqlxx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>国有土地使用权-权利信息</span></li>
        </ul>
    </div>
    <div  name="zdqlxx_gyqlxx" style="padding:  25px 30px;">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <th class="tab_width">权利开始时间：</th>
                <td>
                    <input type="text" id="GYTD_BEGIN_TIME" name="GYTD_BEGIN_TIME"
                           value="${busRecord.GYTD_BEGIN_TIME}"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'GYTD_END_TIME1\')}'})"
                           class="tab_text Wdate" maxlength="60"  />
                </td>
                <th class="tab_width">权利结束时间1：</th>
                <td>
                    <input type="text" id="GYTD_END_TIME1" name="GYTD_END_TIME1"
                           value="${busRecord.GYTD_END_TIME1}"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
                           class="tab_text Wdate" maxlength="60"  />
                </td>
            </tr>
            <tr>
                <th class="tab_width">权利结束时间2：</th>
                <td>
                    <input type="text" id="GYTD_END_TIME2" name="GYTD_END_TIME2"
                           value="${busRecord.GYTD_END_TIME2}"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
                           class="tab_text Wdate" maxlength="60" />
                </td>
                <th class="tab_width">权利结束时间3：</th>
                <td>
                    <input type="text" id="GYTD_END_TIME3" name="GYTD_END_TIME3"
                           value="${busRecord.GYTD_END_TIME3}"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
                           class="tab_text Wdate" maxlength="60" />
                </td>
            </tr>
            <tr>
                <th class="tab_width">共有方式：</th>
                <td >
                    <eve:eveselect clazz="tab_text w280" dataParams="GYFS" value="${busRecord.GYTD_GYFS}"
                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                   defaultEmptyText="选择共有方式" name="GYTD_GYFS" id="GYTD_GYFS">
                    </eve:eveselect>
                </td>
                <th class="tab_width">取得价格（万元）：</th>
                <td>
                    <input type="text" class="tab_text validate[custom[number]]" value="${busRecord.GYTD_QDJG}" name="GYTD_QDJG" />
                </td>
            </tr>
            <tr>
                <th class="tab_width"><font class="tab_color">*</font> 使用权面积：</th>
                <td>
                    <input type="text" class="tab_text validate[required] " value="${busRecord.GYTD_SYQMJ}" name="GYTD_SYQMJ" />
                </td>
                <th class="tab_width">权属状态：</th>
                <td>
                    <eve:eveselect clazz="tab_text w280" dataParams="DYQSZT"
                                   dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GYTD_QSZT}"
                                   defaultEmptyText="选择权属状态" name="GYTD_QSZT" id="GYTD_QSZT">
                    </eve:eveselect>
                </td>
            </tr>
<%--            <tr>--%>
<%--                <th class="tab_width">登簿人：</th>--%>
<%--                <td>--%>
<%--                    <input type="text" readonly="true" disabled='disabled' class="tab_text " value="${busRecord.BDC_DBR}" name="BDC_DBR"--%>
<%--                    />--%>
<%--                </td>--%>
<%--                <th class="tab_width">登记时间：</th>--%>
<%--                <td>--%>
<%--                    <input type="text" readonly="true" disabled='disabled' class="tab_text " value="${busRecord.BDC_DBSJ}" name="BDC_DBSJ"--%>
<%--                    />--%>
<%--                </td>--%>
<%--            </tr>--%>
            <tr>
                <th class="tab_width">登记原因：</th>
                <td colspan="3">
                    <input type="text" class="tab_text" style="width: 838px;" maxlength="60" name="GYTD_DJYY" value="${busRecord.GYTD_DJYY}"/>
                </td>
            </tr>
            <tr>
                <th class="tab_width">附记：</th>
                <td colspan="3">
                    <textarea name="GYTD_FJ" cols="100" rows="5" name="GYTD_FJ"
                              class="validate[[maxSize[200]]" style="border: 1px solid #c9deef;width: 838px;">${busRecord.GYTD_FJ}</textarea>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- 国有土地使用权-权利信息结束 -->
