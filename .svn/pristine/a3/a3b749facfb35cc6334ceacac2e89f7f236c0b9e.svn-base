<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script type="text/javascript">

    function saveDjshOpinion() {
        var params = {};
        var flowSubmitObj = FlowUtil.getFlowObj();
        if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
            params.CS_OPINION_CONTENT = $("textarea[name='CS_OPINION_CONTENT']").val();
        }
        if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '预登簿' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
            params.HZ_OPINION_CONTENT = $("textarea[name='HZ_OPINION_CONTENT']").val();
        }
        params.busRecordId = flowSubmitObj.EFLOW_BUSRECORDID;
        params.busTableName = flowSubmitObj.EFLOW_BUSTABLENAME;
        if (flowSubmitObj) {
            AppUtil.ajaxProgress({
                url: "bdcQlcSffzInfoController.do?saveBdcDjshOpinion",
                params: params,
                callback: function (data) {
                    if (data) {
                        if (data.success) {
                            if (data.TYPE == "1") {
                                $("input[name='CS_OPINION_NAME']").val(data.CS_OPINION_NAME);
                                $("input[name='CS_OPINION_TIME']").val(data.CS_OPINION_TIME);
                            } else if (data.TYPE == "2") {
                                $("input[name='HZ_OPINION_NAME']").val(data.HZ_OPINION_NAME);
                                $("input[name='HZ_OPINION_TIME']").val(data.HZ_OPINION_TIME);
                            }
                            art.dialog({
                                content: "保存成功",
                                icon: "succeed",
                                time: 3,
                                ok: true
                            });
                        } else {
                            art.dialog({
                                content: "保存失败",
                                icon: "error",
                                time: 3,
                                ok: true
                            });
                        }
                    } else {
                        art.dialog({
                            content: "保存失败",
                            icon: "error",
                            time: 3,
                            ok: true
                        });
                    }
                }
            });

        }
    }

    function cyyjmbSelector(businessName,textareaName) {
        parent.$.dialog.open("bdcDyqscdjController.do?cyyjmbSelector&businessName="+businessName, {
            title : "常用意见选择器",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
                var selectCyyjmbInfo = art.dialog.data("selectCyyjmbInfo");
                if(selectCyyjmbInfo&&selectCyyjmbInfo.opnionContent){
                    $("textarea[name='"+textareaName+"']").val(selectCyyjmbInfo.opnionContent);
                    art.dialog.removeData("selectCyyjmbInfo");
                }
            }
        }, false);
    }
</script>

<div id="djshOpinion" name="djxx_djshOpinion" style="display: none;">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th>登记审核意见 <input type="button" style="float:right;margin:2px 5px 0px 5px;" id="saveDjshOpinionx" class="eflowbutton" value="保存" onclick="saveDjshOpinion();">
                <a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="sfspb" onclick="errorAction();">审批表（双方）</a>
                <a href="javascript:void(0);" style="float:right; margin: 2px 5px;" class="eflowbutton" id="dfspb" onclick="errorAction();">审批表（单方）</a>
                <a href="javascript:void(0);" style="float:right; margin: 2px 5px;" class="eflowbutton" id="dfdymxzjgc" onclick="errorAction();">在建工程抵押明细(审批表)</a>
                <a href="javascript:void(0);" style="float:right; margin: 2px 5px;" class="eflowbutton" id="dfdymxspb" onclick="errorAction();">打印抵押明细(审批表)</a>
            </th>
        </tr>
        <tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshCsOpinionInfo" style="display: none;">
                <tr id="csyj">
                    <td class="tab_width"><font class="tab_color">*</font>审核意见：</td>
                    <td>
                        <textarea name="CS_OPINION_CONTENT" cols="140" rows="10"
                                  class="validate[[required],maxSize[512]]">${busRecord.CS_OPINION_CONTENT}</textarea>
                        <input type="button" class="eflowbutton" value="意见模板" onclick="cyyjmbSelector('bdcgyjsjfwzydj_cs','CS_OPINION_CONTENT');">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>填写人：</td>
                    <td>
                        <input type="text" disabled="disabled" class="tab_text validate[required]" name="CS_OPINION_NAME" value="${busRecord.CS_OPINION_NAME}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>填写时间：</td>
                    <td>
                        <input type="text" disabled="disabled" class="tab_text validate[required]" name="CS_OPINION_TIME" value="${busRecord.CS_OPINION_TIME}">
                    </td>
                </tr>
            </table>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshHzOpinionInfo" style="display: none;">
                <tr id="hzyj">
                    <td class="tab_width"><font class="tab_color">*</font>核定意见：</td>
                    <td>
                        <textarea name="HZ_OPINION_CONTENT" cols="140" rows="10"
                                  class="validate[[required],maxSize[512]]">${busRecord.HZ_OPINION_CONTENT}</textarea>
                        <input type="button" class="eflowbutton" value="意见模板" onclick="cyyjmbSelector('bdcgyjsjfwzydj_hz','HZ_OPINION_CONTENT');">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>填写人：</td>
                    <td>
                        <input type="text" disabled="disabled" class="tab_text validate[required]" name="HZ_OPINION_NAME" value="${busRecord.HZ_OPINION_NAME}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>填写时间：</td>
                    <td>
                        <input type="text" disabled="disabled" class="tab_text validate[required]" name="HZ_OPINION_TIME" value="${busRecord.HZ_OPINION_TIME}">
                    </td>
                </tr>
            </table>
        </tr>
    </table>
</div>