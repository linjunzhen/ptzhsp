<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script>
    $(function () {
        $("input[name='EXE_SUBBUSCLASS']").click(function () {
            var EXE_SUBBUSCLASS = $("input[name='EXE_SUBBUSCLASS']:checked").val();
            chooseBdcLsyljfjsType(EXE_SUBBUSCLASS);
        });
    })

    function calculation() {
        var bllx = $("[name='EXE_SUBBUSCLASS']:checked").val();
        var formId;
        if (bllx == '单位集资房') {
            formId = 'dwjzf';
            bllx = 1;
        } else if (bllx == '国有土地上自建房屋分割销售') {
            formId = 'zjfwfgxs';
            bllx = 2;
        } else if (bllx == '旧城改造') {
            formId = 'jcgzxm';
            bllx = 3;
        }
        var formData = FlowUtil.getFormEleData(formId);
        formData['BLLX'] = bllx;
        formData['TYPE'] = 1;
        var textArr = [];
        var numberArr = [];
        $.each(formData, function (name, value) {
            var text = $("[name='" + name + "']").parent().prev().text().replace('*', '').replace('：','');
            var classValue = $("[name='"+name+"']").attr("class");
            if (!(value && value != '')) {
                if (classValue && classValue.indexOf("required") != -1) {
                    textArr.push(text);
                }
            }
            if (value && value != '') {
                if (classValue && classValue.indexOf("number") != -1) {
                    if (!isNumber(value)) {
                        numberArr.push(text);
                    }
                }
            }
        });
        if (textArr.length > 0) {
            art.dialog({
                content:"请输入" + textArr[0],
                icon:"warning",
                ok:true
            });
            return;
        }
        if (numberArr.length > 0) {
            art.dialog({
                content:numberArr[0]+"必须为数字",
                icon:"warning",
                ok:true
            });
            return;
        }
        $.ajax({
            url:"bdcApplyController.do?bdcLandPrcCalculator",
            method:"post",
            async:false,
            data:formData,
            success:function (data) {
                if (data){
                    var json = JSON.parse(data);
                    if (json.success){
                        var value = json.value;
                        $("[name='YDCRJ']").val(json.value)
                        $("[name='WJMJBJ']").val(json.wjmjbj)
                        if (value.indexOf("-") != -1) {
                            art.dialog({
                                content:"请检查输入值是否正确",
                                icon:"warning",
                                ok:true
                            })
                        }
                    } else {
                        art.dialog({
                            content:"系统错误，请联系管理员",
                            icon:"error",
                            ok:true
                        })
                    }
                }
            }
        });
    }

    function isNumber(val) {
        if(val === "" || val ==null){
            return false;
        }
        var reg = new RegExp('^[0-9]+([.]{1}[0-9]+){0,1}$');
        var flag = reg.test(val);
        return flag;
    }
</script>


<div id="lsyljfjs" style="display: none;">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th colspan="4">
                <span>不动产地价计算器 </span>
            </th>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="dwjzf">
        <tr>
            <td class="tab_width">土地性质：</td>
            <td>
                <eve:eveselect clazz="tab_text required" dataParams="JSQTDXZ"
                               dataInterface="dictionaryService.findDatasForSelect"
                               name="TDXZ" value="${busRecord.TDXZ}">
                </eve:eveselect>
            </td>
            <td class="tab_width">土地使用权面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="TDSYQMJ" value="${busRecord.TDSYQMJ}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">建筑面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="JZMJ" value="${busRecord.JZMJ}"/>
            </td>
            <td class="tab_width">土地用途：</td>
            <td>
                <eve:eveselect clazz="tab_text" dataParams="JSQTDYT"
                               dataInterface="dictionaryService.findDatasForSelect"
                               name="TDYT" value="${busRecord.TDYT}">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <td class="tab_width">用地出让金（元）：</td>
            <td colspan="3">
                <input type="text" class="tab_text" style="float: left;" maxlength="16" name="YDCRJ" value="${busRecord.YDCRJ}"/>
                <a href="javascript:void(0)" class="eflowbutton" onclick="calculation();">计算</a>
            </td>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="zjfwfgxs">
        <tr>
            <td class="tab_width">土地转让情形：</td>
            <td>
                <eve:eveselect clazz="tab_text required" dataParams="JSQTDZRLX"
                               dataInterface="dictionaryService.findDatasForSelect"
                               name="TDZRQX1" onchange="changeTdzrqx(this.value);" value="${busRecord.TDZRQX1}">
                </eve:eveselect>
            </td>
            <td class="tab_width">总建筑面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="JJZMJ1" value="${busRecord.JJZMJ1}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">占地面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="ZDMJ1" value="${busRecord.ZDMJ1}"/>
            </td>
            <td class="tab_width">用地面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="YDMJ" value="${busRecord.YDMJ}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">容积率：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="RJL1" value="${busRecord.RJL1}"/>
            </td>
            <td class="tab_width">违建面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text number" maxlength="16" name="WJMJ" value="${busRecord.WJMJ}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">补缴标准（元）：</td>
            <td>
                <input type="text" class="tab_text" style="float: left;" maxlength="16" name="YDCRJ" value="${busRecord.YDCRJ}"/>
                <a href="javascript:void(0)" class="eflowbutton" onclick="calculation();">计算</a>
            </td>
            <td class="tab_width">违建面积补价（元）：</td>
            <td>
                <input type="text" class="tab_text" style="float: left;" maxlength="16" name="WJMJBJ" value="${busRecord.WJMJBJ}"/>
            </td>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="jcgzxm">
        <tr>
            <td class="tab_width">土地转让情形：</td>
            <td>
                <eve:eveselect clazz="tab_text required" dataParams="JSQTDZRLX"
                               dataInterface="dictionaryService.findDatasForSelect"
                               name="TDZRQX2" value="${busRecord.TDZRQX2}">
                </eve:eveselect>
            </td>
            <td class="tab_width">总建筑面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="JJZMJ2" value="${busRecord.JJZMJ2}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">单元建筑面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="DYJZMJ" value="${busRecord.DYJZMJ}"/>
            </td>
            <td class="tab_width">占地面积（平方米）：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="ZDMJ2" value="${busRecord.ZDMJ2}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">容积率：</td>
            <td>
                <input type="text" class="tab_text required number" maxlength="16" name="RJL2" value="${busRecord.RJL2}"/>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td class="tab_width">补缴标准（元）：</td>
            <td colspan="3">
                <input type="text" class="tab_text" style="float: left;" maxlength="16" name="YDCRJ" value="${busRecord.YDCRJ}"/>
                <a href="javascript:void(0)" class="eflowbutton" onclick="calculation();">计算</a>
            </td>
        </tr>
    </table>
</div>