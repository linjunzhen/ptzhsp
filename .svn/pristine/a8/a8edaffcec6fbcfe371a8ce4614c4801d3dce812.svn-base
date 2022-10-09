<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.system.model.SysUser"%>
<%@ page language="java" import="net.evecom.platform.system.service.SysRoleService" %>
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
    .bdcdydacxTrRed{
        color:red;
    }
</style>
<script type="text/javascript">

    $(function(){

    });

</script>
<div class="bsbox clearfix" id="dymx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>抵押明细</span></li>
        </ul>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="不动产抵押档案查询" onclick="showSelectBdcdydacx();"/>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="查 询" onclick="showSelectBdcdaxxcx();" id="dymxAdd"/>
    </div>
    <div>
        <table cellpadding="0" cellspacing="1" class="bstable1" id="dymxInfo" style="position: relative;">
            <tr id="dymxInfo_1">
                <td style="padding: 25px 30px; width: 100%;">
                    <table class="bstable1">
                        <tr>
                            <th class="tab_width">不动产权证号：</th>
                            <td>
                                <input type="text" class="tab_text validate[]" name="BDCQZH" maxlength="100" value="${busRecord.BDCQZH}"/>
                            </td>
                            <th class="tab_width">产权状态：</th>
                            <td>
                                <input type="text" class="tab_text validate[]" name="CQZT" maxlength="10" value="${busRecord.CQZT}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">不动产单元号：</th>
                            <td colspan="3">
                                <input type="text" class="tab_text validate[required]" style="width: 850px"
                                       name="BDCDYH" maxlength="40" value="${busRecord.BDCDYH}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">权属状态：</th>
                            <td >
                                <eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYQSZT"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择权属状态" name="QSZT"  value="${busRecord.QSZT}">
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">抵押不动产类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text w280 validate[]" dataParams="dybdclx"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择抵押不动产类型" name="DYBDCLX"  value="${busRecord.DYBDCLX}">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">抵押顺位：</th>
                            <td >
                                <input type="text" class="tab_text validate[]"
                                       name="DYSW" maxlength="32" value="${busRecord.DYSW}"/>
                            </td>
                            <th class="tab_width">幢号：</th>
                            <td>
                                <input type="text" class="tab_text validate[]"
                                       name="ZH" maxlength="32" value="${busRecord.ZH}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">户号：</th>
                            <td >
                                <input type="text" class="tab_text validate[]"
                                       name="HH" maxlength="32" value="${busRecord.HH}"/>
                            </td>
                            <th class="tab_width">建筑面积：</th>
                            <td>
                                <input type="text" class="tab_text validate[]"   onblur="onlyNumber(this);setTotlaJzmj()"
                                       name="JZMJ" maxlength="32" value="${busRecord.JZMJ}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">宗地面积：</th>
                            <td >
                                <input type="text" class="tab_text validate[]"  onblur="onlyNumber(this);setTotlaZdmj()"
                                       name="ZDMJ" maxlength="32" value="${busRecord.ZDMJ}"/>
                            </td>
                            <th class="tab_width">分摊土地面积：</th>
                            <td>
                                <input type="text" class="tab_text validate[]"  onblur="onlyNumber(this);setTotlaFttdmj()"
                                       name="FTTDMJ" maxlength="32" value="${busRecord.FTTDMJ}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">独用土地面积：</th>
                            <td  colspan="3">
                                <input type="text" class="tab_text validate[]"
                                       name="SYQMJ" maxlength="32" value="${busRecord.SYQMJ}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">坐落：</th>
                            <td  colspan="3">
                                <input type="text" class="tab_text validate[]"   style="width: 850px"
                                       name="FDZL" maxlength="32" value="${busRecord.FDZL}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">不动产权利人：</th>
                            <td >
                                <input type="text" class="tab_text validate[]"
                                       name="QLRMC" maxlength="128" value="${busRecord.QLRMC}"/>
                            </td>
                            <th class="tab_width">证件类别：</th>
                            <td>
                                <input type="text" class="tab_text validate[]"
                                       name="ZJLX" maxlength="128" value="${busRecord.ZJLX}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">证件号码：</th>
                            <td  colspan="3">
                                <input type="text" class="tab_text validate[]"
                                       name="ZJHM" maxlength="128" value="${busRecord.ZJHM}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">备注：</th>
                            <td  colspan="3">
                                <input type="text" class="tab_text validate[]"  style="width: 850px"
                                       name="BZ" value="${busRecord.BZ}"/>
                            </td>
                        </tr>
                    </table>
                    <!-- <div class="tab_height2"></div> -->
                </td>
	            <td style="position: absolute; right: 25px;">
					<input type="hidden" name="trid" />
					<input type="button" class="eflowbutton" name="deleteDymxInfoInput" value="保存" onclick="updateDymxInfo('1');">
				</td>
            </tr>
        </table>
    </div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="dymxTable">
        <tr>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">序号</td>
            <td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">幢号</td>
            <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">户号</td>
            <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产权证号</td>
            <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
            <td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">产权状态</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
        </tr>
    </table>
</div>