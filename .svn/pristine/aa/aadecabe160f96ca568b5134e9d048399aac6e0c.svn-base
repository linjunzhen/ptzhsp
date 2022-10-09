<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<div id="powerDY_div" style="display: none;">
    <div class="bsbox clearfix">
        <div class="bsboxT">
            <ul>
                <li class="on" style="background:none"><span>抵押权登记（概况信息）</span></li>
            </ul>
            <input type="button" style="float:right; margin: 10px 5px; padding: 0 20px;" class="eflowbutton" value="查询" onclick="showSelectBdcygdjcx();">
        </div>
        <div style="padding: 10px">
            <table cellpadding="0" cellspacing="0" class="bstable1" id="powerDY">

                            <tr>
                                <th><span class="bscolor1">*</span>登记机构：</th>
                                <td>
                                    <input type="text" class="tab_text validate[required]" maxlength="128"
                                           name="DY_DJJG"/>
                                </td>
                                <th><span class="bscolor1">*</span>抵押权人：</th>
                                <td>
                                    <input class=" w280 easyui-combobox tab_text" name="DY_DYQR" id="DY_DYQR"
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
                                            onSelect:function(){
                                                var value = $('#DY_DYQR').combobox('getValue');
                                                setDYQLName(value);
                                            }
                                        "/>
                                </td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span>证件类型：</th>
                                <td>
                                    <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL" defaultEmptyValue="营业执照"
                                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_DYQRZJHM');"
                                                   defaultEmptyText="选择证件类型" name="DY_DYQRZJLX" id="DY_DYQRZJLX">
                                    </eve:eveselect>
                                </td>
                                <th>证件号码：</th>
                                <td>
                                    <input type="text" class="tab_text validate[]" maxlength="30" id="DY_DYQRZJHM"
                                           name="DY_DYQRZJHM"  />
                                </td>
                            </tr>
                            <tr>
                                <th>债务人：</th>
                                <td>
                                    <input type="text" class="tab_text" maxlength="30"
                                           name="DY_ZWR" />
                                </td>
                                <th>债务人证件种类：</th>
                                <td>
                                    <eve:eveselect clazz="tab_text w280" dataParams="DYZJZL"
                                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_ZWRZJHM');"
                                                   defaultEmptyText="选择证件类型" name="DY_ZWRZJLX" id="DY_ZWRZJLX">
                                    </eve:eveselect>
                                </td>
                            </tr>
                            <tr>
                                <th>债务人证件号码：</th>
                                <td>
                                    <input type="text" class="tab_text" maxlength="128" id="DY_ZWRZJHM"
                                           name="DY_ZWRZJHM"  />
                                </td>
                                <th></th>
                                <td></td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span>抵押人：</th>
                                <td>
                                    <input type="text" class="tab_text validate[required]" maxlength="128" id="DY_DYR"
                                           name="DY_DYR"  />
                                </td>
                                <th>抵押物价值</th>
                                <td><input type="text" class="tab_text" maxlength="128" id="DY_DYWJZ"
                                           name="DY_DYWJZ"  /></td>
                            </tr>
                            <tr>
                                <th>抵押人证件种类：</th>
                                <td>
                                    <eve:eveselect clazz="tab_text w280" dataParams="DYZJZL"
                                                   dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_DYRZJHM1');"
                                                   defaultEmptyText="选择证件类型" name="DY_DYRZJLX1" id="DY_DYRZJLX1">
                                    </eve:eveselect>
                                </td>
                                <th>抵押人证件号码：</th>
                                <td>
                                    <input type="text" class="tab_text" maxlength="128" id="DY_DYRZJHM1"
                                           name="DY_DYRZJHM1"/>
                                </td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span>是否最高额抵押：</th>
                                <td>
                                    <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YesOrNo" onchange="setSfzgedy(this.value);"
                                                   dataInterface="dictionaryService.findDatasForSelect"
                                                   defaultEmptyText="选择是否最高额抵押" name="DY_SFZGEDY" id="DY_SFZGEDY">
                                    </eve:eveselect>
                                </td>
                                <th><font class="tab_color" style="display:none;" id="bdbzzqseFont">*</font>被担保主债权数额：</th>
                                <td>
                                    <input type="text" class="tab_text" maxlength="30" name="DY_DBSE"  />
                                </td>
                            </tr>
                            <tr>
                                <th><font class="tab_color" style="display: none;" id="zgzqeFont">*</font>最高债权额：</th>
                                <td>
                                    <input type="text" class="tab_text validate[]" maxlength="30" name="DY_ZGZQSE"  />
                                </td>
                                <th>抵押方式：</th>
                                <td>
                                    <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="BDCDYFS"
                                                   dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="0"
                                                   defaultEmptyText="选择证件类型" name="DY_DYFS" id="DY_DYFS">
                                    </eve:eveselect>
                                </td>
                            </tr>
                            <tr>
                                <th>债务起始时间：</th>
                                <td>
                                    <input type="text" id="DY_QLQSSJ" name="DY_QLQSSJ"
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'DY_QLJSSJ\')}'})"
                                           class="tab_text Wdate validate[]" maxlength="32" />
                                </td>
                                <th>债务结束时间：</th>
                                <td>
                                    <input type="text" id="DY_QLJSSJ" name="DY_QLJSSJ"
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'DY_QLQSSJ\')}'})"
                                           class="tab_text Wdate validate[]" maxlength="32" />
                                </td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span>主债权合同贷款期限（月）：</th>
                                <td>
                                    <input type="text" class="tab_text validate[required,custom[number]]" maxlength="5"
                                           name="DY_ZZQHTDKQX"  />
                                </td>
                                <th>抵押宗数：</th>
                                <td>
                                    <input type="text" class="tab_text validate[]" maxlength="30"
                                           name="DY_DYZS"  />
                                </td>
                            </tr>
                            <tr>
                                <th>登记原因：</th>
                                <td colspan="3">
                                    <input type="text" class="tab_text1 validate[]" maxlength="256" id="DY_DYDJYY"  style="width: 838px;"
                                           name="DY_DYDJYY" />
                                </td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span>最高债权确定事实：</th>
                                <td colspan="3">
                                    <input type="text" class="tab_text1 validate[required] " style="width: 838px;" maxlength="500" id="DY_ZGZQQDSS" name="DY_ZGZQQDSS" />
                                </td>
                            </tr>
                            <tr id="dy_bdcdjzmh_tr" style="display:none;">
                                <th>不动产登记证明号：</th>
                                <td colspan="3">
                                    <input type="text" class="tab_text1 validate[]" maxlength="64" id="DY_BDCDJZMH" name="DY_BDCDJZMH" />
                                    <input type="button" id="DY_BDC_QZVIEW" class="eflowbutton" style="display:none;" value="登记证明预览" onclick="bdcDJZMprint(1)"/>
                                    <input type="button" id="DY_BDC_QZPRINT" class="eflowbutton" style="display:none;" value="登记证明打印" onclick="bdcDJZMprint(2)"/>
                                    <!-- <a href="javascript:void(0);" class="eflowbutton" onclick="viewCertificate(3)"
                                          name="dydjzm" id="dydjzm">打印登记证明</a> -->
                                </td>
                            </tr>
                            <tr id="dy_bdcqzbsm_tr" style="display:none;">
                                <th><span class="bscolor1">*</span>权证标识码：</th>
                                <td colspan="3">
                                    <input type="text" class="tab_text1 validate[] " style="width: 838px;" maxlength="64" id="DY_BDCQZBSM" name="DY_BDCQZBSM" />
                                </td>
                            </tr>
    <%--                        <tr id="dy_bdcqzdbr_tr" style="display:none;">--%>
    <%--                            <th>登簿人：</th>--%>
    <%--                            <td>--%>
    <%--                                <input type="text" class="tab_text validate[]" maxlength="500" id="DY_DBR"--%>
    <%--                                       name="DY_DBR" readonly="readonly "/>--%>
    <%--                            </td>--%>
    <%--                            <th>登记时间：</th>--%>
    <%--                            <td>--%>
    <%--                                <input type="text" id="DY_DJSJ" name="DY_DJSJ" readonly="readonly"--%>
    <%--                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"--%>
    <%--                                       class="tab_text Wdate validate[]" maxlength="32" />--%>
    <%--                            </td>--%>
    <%--                        </tr>--%>
                            <tr>
                                <th><span class="bscolor1">*</span>抵押范围：</th>
                                <td>
                                    <input type="text" class="tab_text1 validate[required]" maxlength="500" id="DY_DYFW"
                                           name="DY_DYFW" />
                                </td>
                                <th><span class="bscolor1">*</span>关联号：</th>
                                <td>
                                    <input type="text" class="tab_text1 validate[required]" maxlength="128" id="DY_GLH"
                                           name="DY_GLH" />
                                </td>
                            </tr>
                            <tr>
                                <th>附记：</th>
                                <td colspan="3">
                                    <input type="text" class="tab_text1 validate[]" maxlength="500" id="DY_FJ" style="width: 838px;" name="DY_FJ" />
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="DY_BDCDYH" />
                        <input type="hidden" name="DY_YWH" />
                        <input type="hidden" name="DY_DYCODE" />
        </div>
    </div>
</div>
<script type="text/javascript">
    function newDicInfoWin(typeCode,combId){
        $.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
            title : "新增",
            width:"600px",
            lock: true,
            resize:false,
            height:"180px",
            close: function(){
                $("#"+combId).combobox("reload");
            }
        }, false);
    }

    function removeDicInfo(combId){
        var datas = $("#"+combId).combobox("getData");
        var val = $("#"+combId).combobox("getValue");
        var id = "";
        for(var i=0;i<datas.length;i++){
            //var id = datas[i]
            if(datas[i].DIC_CODE==val){
                id = datas[i].DIC_ID;
                break;
            }
        }
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            var layload = layer.load('正在提交请求中…');
            $.post("dictionaryController.do?multiDel",{
                selectColNames:id
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success == true){
                    art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                    $("#"+combId).combobox("reload");
                    $("#"+combId).combobox("setValue","");
                }else{
                    $("#"+combId).combobox("reload");
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
            });
        });
    }
</script>

