<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<div class="eui-card eui-declaration wysb2" id="projectInfoForm_B">
<div class="eui-tit round">
    <b>基本信息</b>
</div>
    <form id="projectInfoForm" action="">
<div class="eui-table-import" onmouseover="validateForm('projectInfoForm')" >
    <table id="tzjbxx">
        <colgroup>
            <col>
            <col width="200">
            <col>
            <col width="200">
        </colgroup>
        <tr>
            <th><i>*</i>投资项目编号：</th>
            <td>
                <div class="eui-flex bt">
                    <div class="flex1"><input class="ipt error validate[required]" type="text" id="project_code" name="PROJECTCODE"
                                              readonly="readonly" value="${busRecord.PROJECTCODE}" /></div>
                    <input type="hidden" name="PROJECT_CODE"  value="${busRecord.PROJECT_CODE}" />
                    <a id="showXmxz" class="eui-btn" href="javascript:void(0)" >选择项目</a>
                </div>
                <span class="tooltips" style="display: ;" id="tooltipsProjectCode">请输入正确的项目编号</span>
            </td>
            <th></th>
            <td>

            </td>
        </tr>
        <tr>
            <th><i>*</i>项目名称：</th>
            <td><input class="ipt  validate[required]" type="text" id="projectName" maxlength="128" name="PROJECT_NAME"   value="${busRecord.PROJECT_NAME}" /></td>
            <th>项目类型：</th>
            <td><eve:eveselect clazz="ipt " dataParams="PROJECTTYPE"  value="${busRecord.PROJECT_TYPE}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
            </eve:eveselect></td>
        </tr>
        <tr>
            <th>项目所属区划：</th>
            <td><input readonly="true" id="divisionCode" type="text" maxlength="6" class="ipt "  name="DIVISION_CODE"  value="${busRecord.DIVISION_CODE}"/></td>
            <th>投资项目目录编码：</th>
            <td><input readonly="true"  type="text"  class="ipt "  name="PERMIT_ITEM_CODE"  value="${busRecord.PERMIT_ITEM_CODE}"  /></td>
        </tr>
        <tr>
            <th>建设性质：</th>
            <td><eve:eveselect clazz="ipt " dataParams="PROJECTNATURE"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_NATURE}"
                               defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="projectNature">
            </eve:eveselect></td>
            <th>建设地点详情：</th>
            <td><input  readonly="true" id="placeCodeDetail" type="text" class="ipt "    value="${busRecord.PLACE_CODE_DETAIL}"
                        name="PLACE_CODE_DETAIL"   maxlength="128"/></td>
        </tr>
        <tr>
            <th>拟开工时间：</th>
            <td><input readonly="true" id="startYear" type="text" maxlength="4" class="ipt  Wdate"   value="${busRecord.START_YEAR}"
<%--                       onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  --%>
                       name="START_YEAR" /></td>
            <th>拟建成时间：</th>
            <td><input readonly="true" id="endYear" type="text"  maxlength="4" class="ipt  Wdate"  value="${busRecord.END_YEAR}"
<%--                       onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})" --%>
                       name="END_YEAR" /></td>
        </tr>
        <tr>
            <th>总投资(万元)：</th>
            <td>	<input  readonly="true" id="totalMoney" type="text"  maxlength="16" class="ipt validate[]" name="TOTAL_MONEY"   value="${busRecord.TOTAL_MONEY}" /></td>
            <th>申报时间：</th>
            <td><input readonly="true" id="applyDate" type="text"  value="${busRecord.APPLY_DATE}"
            <%--                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"  --%>
                       class="ipt  Wdate" name="APPLY_DATE" />
            </td>
        </tr>
        <tr>
            <th>总投资额为0时说明：</th>
            <td colspan="3">
                                    <textarea id="totalMoneyExplain"  rows="6"  value="${busRecord.TOTAL_MONEY_EXPLAIN}"
                                              name="TOTAL_MONEY_EXPLAIN"   ></textarea>

            </td>
        </tr>
        <tr>
            <th>建设地点：</th>
            <td>	<select id="placeCode" name="PLACE_CODE"   value="${busRecord.PLACE_CODE}"  class="ipt  " >
                <option value="">请选择建设地点</option>
            </select></td>
            <th>国标行业：</th>
            <td>
                <select id="industry" name="INDUSTRY" class="ipt  ">
                    <option value="">请选择国标行业</option>
                </select>

            </td>
        </tr>
        <tr>
            <th>建设规模及内容：</th>
            <td colspan="3"><textarea rows="6" cols=""  id="scaleContent"  class=",maxSize[2000]"
                                      name="SCALE_CONTENT"> </textarea></td>
        </tr>
        <tr>
            <th>项目属性：</th>
            <td><eve:eveselect clazz="ipt " dataParams="PROJECTATTRIBUTES"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES" id="projectAttributes">
            </eve:eveselect></td>
            <th>产业结构指导目录：</th>
            <td>		<select id="industryStructure" name="INDUSTRY_STRUCTURE" class="ipt  " style="width:80%;">
                <option value="">请选择产业结构调整指导目录</option>
            </select></td>
        </tr>
        <tr>
            <th>土地获取方式：</th>
            <td>	<eve:eveselect clazz="ipt " dataParams="getLandMode"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
            </eve:eveselect></td>
            <th>项目投资来源：</th>
            <td><eve:eveselect clazz="ipt " dataParams="XMTZLY"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
            </eve:eveselect></td>
        </tr>
        <tr>
            <th>工程分类：</th>
            <td><eve:eveselect clazz="ipt " dataParams="GCFL"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
            </eve:eveselect></td>
            <th>是否完成区域评估：</th>
            <td>	<eve:eveselect clazz="ipt " dataParams="SFWCQYPG"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
            </eve:eveselect></td>
        </tr>
        <tr>
            <th>土地是否带设计方案：</th>
            <td>	<eve:eveselect clazz="ipt " dataParams="TDSFDSJFA"
                                   dataInterface="dictionaryService.findDatasForSelect"
                                   defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
            </eve:eveselect></td>
            <th></th>
            <td></td>
        </tr>
    </table>
</div>
<div  id="wstzxx">
<div class="eui-tit round" >
    <b>外商投资信息</b>
</div>
<div class="eui-table-import">
    <table>
        <colgroup>
            <col>
            <col width="200">
            <col>
            <col width="200">
        </colgroup>
        <tr>
            <th ><font class="dddl_platform_html_requiredFlag">*</font>是否涉及国家安全</th>
            <td>
                <eve:eveselect clazz="ipt   " dataParams="YesOrNo"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.IS_COUNTRY_SECURITY}" onchange="showSecurityApprovalNumber(this.value)"
                               defaultEmptyText="请选择是否涉及国家安全" name="IS_COUNTRY_SECURITY" id="IS_COUNTRY_SECURITY">
                </eve:eveselect>
            </td>
            <th >安全审查决定文号</th>
            <td>
                <input id="SECURITY_APPROVAL_NUMBER" type="text"  maxlength="16"
                       class="ipt"  name="SECURITY_APPROVAL_NUMBER"  value="${busRecord.SECURITY_APPROVAL_NUMBER}" value="${busRecord.SECURITY_APPROVAL_NUMBER}" />
            </td>
        </tr>

        <tr>
            <th > 投资方式</th>
            <td>
                <eve:eveselect clazz="ipt   " dataParams="investmentMode"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INVESTMENT_MODE}"
                               defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE" id="investmentMode"
                               onchange="showOther(this.value)">
                </eve:eveselect>
            </td>
            <th > 总投资额折合美元(万元)</th>
            <td>
                <input id="totalMoneyDollar" type="text"  maxlength="18"
                       class="ipt  validate[custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR"  value="${busRecord.TOTAL_MONEY_DOLLAR}" />
            </td>
        </tr>
        <tr>
            <th > 总投资额使用的汇率<br/>（人民币/美元）</th>
            <td>
                <input id="totalMoneyDollarRate" type="text" maxlength="100"
                       class="ipt  validate[custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR_RATE"  value="${busRecord.TOTAL_MONEY_DOLLAR_RATE}"  />
            </td>
            <th > 项目资本金(万元)</th>
            <td>
                <input id="projectCapitalMoney" type="text"  maxlength="18"
                       class="ipt  validate[custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY"  value="${busRecord.PROJECT_CAPITAL_MONEY}" />
            </td>
        </tr>
        <tr>
            <th > 项目资本金折合美元<br/>(万元)</th>
            <td>
                <input id="projectCapitalMoneyDollar" class="ipt  validate[custom[numberp6plus]]"  value="${busRecord.PROJECT_CAPITAL_MONEY_DOLLAR}"
                       maxlength="100" class="ipt  " name="PROJECT_CAPITAL_MONEY_DOLLAR"  />
            </td>
            <th > 项目资本金使用的汇率<br/>（人民币/美元）</th>
            <td>
                <input id="projectCapitalMoneyRate" type="text"  maxlength="18"
                       class="ipt  validate[custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY_RATE"  value="${busRecord.PROJECT_CAPITAL_MONEY_RATE}" />
            </td>
        </tr>
        <tr>
            <th > 适用产业政策条目类型</th>
            <td>
                <eve:eveselect clazz="ipt  " dataParams="industrialPolicyType"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INDUSTRIAL_POLICY_TYPE}"
                               defaultEmptyText="请选择产业政策条目类型" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
                </eve:eveselect>
            </td>
            <th >适用产业政策条目</th>
            <td>
                <eve:eveselect clazz="ipt  validate[]" dataParams="industrialPolicy"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INDUSTRIAL_POLICY}"
                               defaultEmptyText="请选择产业政策条目" name="INDUSTRIAL_POLICY" id="industrialPolicy">
                </eve:eveselect>
            </td>
        </tr>
        <tr id="otherInvestmentApplyInfoTr" >
            <th >其他投资方式需予以<br/>申报的情况</th>
            <td  colspan="3">
				<textarea id="otherInvestmentApplyInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"
                          name="OTHER_INVESTMENT_APPLY_INFO"  style="width:80%;height:75px;" >${busRecord.OTHER_INVESTMENT_APPLY_INFO}</textarea>
            </td>
        </tr>
        <tr id="transactionBothInfoTr" >
            <th >提供交易双方情况</th>
            <td  colspan="3">
			<textarea id="transactionBothInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"
                      name="TRANSACTION_BOTH_INFO"  style="width:80%;height:75px;" >${busRecord.TRANSACTION_BOTH_INFO}</textarea>
            </td>
        </tr>
        <tr id="mergerPlanTr" >
            <th >并购安排</th>
            <td  colspan="3">
			<textarea id="mergerPlan" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"
                      name="MERGER_PLAN"  style="width:80%;height:75px;" >${busRecord.MERGER_PLAN}</textarea>
            </td>
        </tr>
        <tr id="mergerManagementModeScopeTr" >
            <th >并购后经营方式及<br/>经营范围</th>
            <td  colspan="3">
			<textarea id="mergerManagementModeScope" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"
                      name="MERGER_MANAGEMENT_MODE_SCOPE"  style="width:80%;height:75px;" >${busRecord.MERGER_MANAGEMENT_MODE_SCOPE}</textarea>
            </td>
        </tr>
        <tr>
            <th > 总建筑面积（平方米）</th>
            <td>
                <input id="builtArea" type="text" maxlength="100"
                       class="ipt  validate[custom[numberp6plus]]" name="BUILT_AREA"   value="${busRecord.BUILT_AREA}"/>
            </td>
            <th > 总用地面积(平方米)</th>
            <td>
                <input id="landArea" type="text"  maxlength="18"
                       class="ipt  validate[custom[numberp6plus]]" name="LAND_AREA"  value="${busRecord.LAND_AREA}" />
            </td>
        </tr>
        <tr>
            <th > 是否新增设备</th>
            <td>
                <eve:eveselect clazz="ipt  " dataParams="YesOrNo"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.IS_ADD_DEVICE}"
                               defaultEmptyText="请选择是否新增设备" name="IS_ADD_DEVICE" id="IS_ADD_DEVICE">
                </eve:eveselect>
            </td>
            <th > 拟进口设备数量及金额</th>
            <td>
                <input id="importDeviceNumberMoney" type="text" maxlength="100" class="ipt   " name="IMPORT_DEVICE_NUMBER_MONEY"   value="${busRecord.IMPORT_DEVICE_NUMBER_MONEY}"/>
            </td>
        </tr>
    </table>
</div>
</div>
    <div id="jwtzxx">
<div class="eui-tit round"  >
    <b>外商投资信息</b>
</div>
<div class="eui-table-import">
    <table>
        <colgroup>
            <col>
            <col width="200">
            <col>
            <col width="200">
        </colgroup>
        <tr>
            <th > 项目所在地</th>
            <td>
                <input id="PROJECT_SITE" type="text" maxlength="100"
                       class="ipt  " name="PROJECT_SITE"  value="${busRecord.PROJECT_SITE}" />
            </td>
            <th > 中方投资额(万元)</th>
            <td>
                <input id="chinaTotalMoney" type="text"  maxlength="18"
                       class="ipt validate[custom[numberp6plus]]" name="CHINA_TOTAL_MONEY" value="${busRecord.CHINA_TOTAL_MONEY}" />
            </td>
        </tr>
    </table>
</div>
    </div>
<div class="eui-tit round">
    <b>项目法人单位</b>
</div>
<div class="eui-table-import" id="xmdwxxDiv">
    <table>
        <colgroup>
            <col>
            <col width="200">
            <col>
            <col width="200">
        </colgroup>
        <tr>
            <th > 单位名称</th>
            <td >
                <input type="text"  maxlength="100" value="${busRecord.ENTERPRISE_NAME}"
                       class="ipt " name="enterprise_name" />
            </td>
            <th > 单位类型</th>
            <td>
                <eve:eveselect clazz="ipt  " dataParams="DWLX" value="${busRecord.DWLX}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择单位类型" name="dwlx" id="dwlx">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <th > 证照类型</th>
            <td>
                <eve:eveselect clazz="ipt  " dataParams="LEREPCERTTYPE" value="${busRecord.LEREP_CERTTYPE}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择证照类型" name="lerep_certtype" id="lerep_certtype">
                </eve:eveselect>
            </td>
            <th > 证照号码</th>
            <td>
                <input type="text"  maxlength="32" value="${busRecord.LEREP_CERTNO}"
                       class="ipt " name="lerep_certno" />
            </td>
        </tr>
        <tr>
            <th > 联系人名称</th>
            <td>
                <input type="text"  maxlength="16" value="${busRecord.CONTACT_NAME}"
                       class="ipt " name="contact_name" />
            </td>
            <th > 联系电话</th>
            <td>
                <input type="text"  maxlength="16" value="${busRecord.CONTACT_TEL}"
                       class="ipt " name="contact_tel" />
            </td>
        </tr>
        <tr>
            <th >联系人邮箱</th>
            <td>
                <input type="text"  maxlength="32" value="${busRecord.CONTACT_EMAIL}"
                       class="ipt validate[]" name="contact_email" />
            </td>
            <th >注册地址</th>
            <td>
                <input type="text"  maxlength="128" value="${busRecord.ENTERPRISE_PLACE}"
                       class="ipt validate[]" name="enterprise_place" />
            </td>
        </tr>
        <tr>
            <th >单位性质</th>
            <td>
                <eve:eveselect clazz="ipt  validate[]" dataParams="enterpriseNature" value="${busRecord.ENTERPRISE_NATURE}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择单位性质" name="enterprise_nature" id="enterprise_nature">
                </eve:eveselect>
            </td>
            <th >持股比例是否与资本金相同</th>
            <td>
                <eve:eveselect clazz="ipt  validate[]" dataParams="chinaForeignShareRatio"
                               dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.CHINA_FOREIGN_SHARE_RATIO}"
                               defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio" id="china_foreign_share_ratio">
                </eve:eveselect>
            </td>
        </tr>
        <tr>
            <th >主要经营范围</th>
            <td>
                <input type="text"  maxlength="512"
                       class="ipt validate[]" name="business_scope" />
            </td>
            <th >联系手机</th>
            <td>
                <input type="text"  maxlength="16"
                       class="ipt validate[]" name="contact_phone" />
            </td>
        </tr>
        <tr>
            <th >传真</th>
            <td>
                <input type="text"  maxlength="16"
                       class="ipt validate[]" name="contact_fax" />
            </td>
            <th >通讯地址</th>
            <td>
                <input type="text"  maxlength="64"
                       class="ipt validate[]" name="correspondence_address" />
            </td>
        </tr>
    </table>
</div>
    </form>
<div class="eui-flex tc eui-sx-btn">
    <a id="back1" class="eui-btn light" href="javascript:void(0)">上一步</a>
    <c:if test="${isQueryDetail!='true'}">
    <a class="eui-btn o"  onclick="tempSaveWebSiteFlowForm('T_GCXM_XMJBXX_FORM')">保存草稿</a>
    </c:if>
    <a id="goto3" class="eui-btn" href="javascript:void(0)">下一步</a>
</div>
</div>