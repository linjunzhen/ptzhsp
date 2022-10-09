<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>

    .eflowbutton{
        background: #3a81d0;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height:26px;
        color: #fff;
        border-radius: 5px;

    }
    .eflowbutton-disabled{
        background: #94C4FF;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height:26px;
        color: #E9E9E9;
        border-radius: 5px;

    }
    .selectType{
        margin-left: -100px;
    }
</style>
<script type="text/javascript">

    $(function(){

    });

</script>
<div class="bsbox clearfix" id="qsly">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none">
                <span>权属来源</span>
            </li>
        </ul>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="新 增" onclick="showSelectBdcygdacx();"/>
    </div>
    <table cellpadding="0" cellspacing="1" class="bstable1" id="qslyInfo" style="position: relative;">
        <tr id="qslyInfo_1" style="position: relative;">
            <td style="padding: 25px 30px; width: 100%;" colspan="9">
                <table class="bstable1">
                    <tr>
                        <th class="tab_width">原不动产证明号：</th>
                        <td >
                            <input type="text" class="tab_text validate[]"
                                   name="BDCDJZMH" maxlength="40"/>
                        </td>
                        <th class="tab_width">权属状态：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYQSZT"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择权属状态" name="QSZT" >
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width"><font class="tab_color">*</font>不动产单元号：</th>
                        <td colspan="3">
                            <input type="hidden" name="bdcqsly" />
                            <input type="text" class="tab_text validate[required]" style="width: 850px"
                                   name="BDCDYH" maxlength="100" value="${busRecord.BDCDYH}"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">权利人：</th>
                        <td >
                            <input type="text" class="tab_text validate[]"
                                   name="QLR" maxlength="32" />
                        </td>
                        <th class="tab_width">权利人证件种类：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件种类" name="QLRZJZL" >
                            </eve:eveselect>

                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">权利人证件号码：</th>
                        <td colspan="3">
                            <input type="text" class="tab_text validate[]"
                                   name="QLRZJH" maxlength="32" style="width: 850px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">义务人：</th>
                        <td >
                            <input type="text" class="tab_text validate[]"
                                   name="YWR" maxlength="32"/>
                        </td>
                        <th class="tab_width">义务人证件种类：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件种类" name="YWRZJZL" >
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">义务人证件号码：</th>
                        <td colspan="3">
                            <input type="text" class="tab_text validate[]"
                                   name="YWRZJH" maxlength="32" style="width: 850px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="tab_width">抵押合同号：</th>
                        <td >
                            <input type="text" class="tab_text validate[]"
                                   name="HTH" maxlength="32"/>
                        </td>
                        <th class="tab_width">被担保主债权数额：</th>
                        <td>
                            <input type="text" class="tab_text validate[]"
                                   name="QDJG" maxlength="32" />
                        </td>
                    </tr>

                    <tr>
                        <th class="tab_width"><font class="tab_color">*</font>债务起始时间：</th>
                        <td>
                            <input type="text" name="QSSJ"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"
                                   class="tab_text Wdate validate[required]" maxlength="32" style="height: 40px;border: 1px solid #c9deef;"/>
                        </td>
                        <th class="tab_width"><font class="tab_color">*</font>债务结束时间：</th>
                        <td>
                            <input type="text" name="JSSJ"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"
                                   class="tab_text Wdate validate[required]" maxlength="32" style="height: 40px;border: 1px solid #c9deef;"/>
                        </td>
                    </tr>
                </table>
            </td>
            <td style="position: absolute; right: 25px;">
                <input type="button" class="eflowbutton" style="padding: 0 20px;" name="deleteQslyInfoInput" value="删除" onclick="deleteQslyInfo('1');">
            </td>
        </tr>
    </table>
</div>