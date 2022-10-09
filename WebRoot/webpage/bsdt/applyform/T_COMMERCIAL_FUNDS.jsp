<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<c:if test="${busRecord.ISFUNDSREGISTER == '1'}">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th colspan="4" >公积金登记信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>类型：</td>
            <td>
                <eve:radiogroup typecode="GJJLX" width="130px" fieldname="FUNDS_GJJLX" value="${busRecord.FUNDS_GJJLX}" defaultvalue="ZFGJJ" onclick="initGjjdjxxForm(this.value)"></eve:radiogroup>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>单位发薪日：</td>
            <td>
                <input type="text" class="tab_text validate[required]"
                       name="FUNDS_GJJDWFXR"  placeholder="请选择单位发薪日" value="${busRecord.FUNDS_GJJDWFXR}"/>&nbsp;日
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>启缴年月：</td>
            <td>
                <input type="text" class="Wdate tab_text validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})"
                       name="FUNDS_GJJQJNY"  placeholder="请选择启缴年月" value="${busRecord.FUNDS_GJJQJNY}"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>单位电子邮箱：</td>
            <td>
                <input type="text" class="tab_text validate[required],custom[email]" maxlength="64"
                       name="FUNDS_GJJDWDZYX"  placeholder="请输入单位电子邮箱" value="${busRecord.FUNDS_GJJDWDZYX}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>受托银行：</td>
            <td colspan="3">
                <eve:radiogroup typecode="GJJSTYH" width="130px" value="${busRecord.FUNDS_GJJSTYH}" fieldname="FUNDS_GJJSTYH"></eve:radiogroup>
            </td>
        </tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>隶属关系：</td>
            <td colspan="3">
                <eve:radiogroup typecode="GJJLSGX" width="130px" value="${busRecord.FUNDS_GJJLSGX}" fieldname="FUNDS_GJJLSGX"></eve:radiogroup>
            </td>
        </tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>单位性质：</td>
            <td colspan="3">
                <eve:radiogroup typecode="GJJDWXZ" width="130px" value="${busRecord.FUNDS_GJJDWXZ}" fieldname="FUNDS_GJJDWXZ"></eve:radiogroup>
            </td>
        </tr>
		<tr>
			<td class="tab_width" colspan="1"><font class="tab_color">*</font>单位类型：</td>
			<td colspan="3">
				<eve:radiogroup typecode="GJJDWLX" width="130px" value="${busRecord.FUNDS_GJJDWLX}" fieldname="FUNDS_GJJDWLX"></eve:radiogroup>
			</td>
		</tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>经济类型：</td>
            <td colspan="3">
                <eve:radiogroup typecode="GJJJJLX" width="130px" value="${busRecord.FUNDS_GJJJJLX}" fieldname="FUNDS_GJJJJLX"></eve:radiogroup>
            </td>
        </tr>
        <tr>
            <td class="tab_width" colspan="1"><font class="tab_color">*</font>行业分类：</td>
            <td colspan="3">
                <eve:radiogroup typecode="GJJHYFL" width="130px" value="${busRecord.FUNDS_GJJHYFL}" fieldname="FUNDS_GJJHYFL"></eve:radiogroup>
            </td>
		</tr>
		<tr>
			<td class="tab_width" ><font class="tab_color">*</font>单位所属区划：</td>
			<td colspan="3"><input type="text" class="tab_text inputBackgroundCcc validate[required]"  readonly="readonly"
				name="FUNDS_DWGSQH" placeholder="请输入单位所属区划" value="平潭综合实验区行政服务中心"  maxlength="32"/></td>
		</tr>
		<tr>
			<td class="tab_width" >经办人姓名：</td>
			<td><input type="text" class="tab_text validate[]" 
				name="FUNDS_NAME"  placeholder="未填写的默认同经办人"  maxlength="8"  value="${busRecord.FUNDS_NAME}"/></td>
			<td class="tab_width" >经办人手机号码：</td>
			<td><input type="text" class="tab_text  validate[],custom[mobilePhone]"
				name="FUNDS_MOBILE"  placeholder="请输入手机号码" value="${busRecord.FUNDS_MOBILE}"  maxlength="16"/></td>
		</tr>
		<tr>
			<td class="tab_width" >经办人固定电话：</td>
			<td colspan="3"><input type="text" class="tab_text validate[]" style="width:37%;"
				name="FUNDS_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.FUNDS_FIXEDLINE}"  maxlength="16"/></td>
		</tr>
		<tr>
			<td class="tab_width" >经办人证件类型：</td>
			<td>
				<eve:eveselect clazz="input-dropdown validate[]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'FUNDS_IDNO');"
				defaultEmptyText="请选择证件类型" name="FUNDS_IDTYPE" value="${busRecord.FUNDS_IDTYPE}">
				</eve:eveselect>
			</td>
			<td class="tab_width" >经办人证件号码：</td>
			<td><input type="text" class="tab_text validate[]"
				name="FUNDS_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.FUNDS_IDNO}" /></td>
		</tr>
		<tr>
			<td class="tab_width" ><font class="tab_color">*</font>在职员工人数：</td>
			<td colspan="3"><input type="text" class="tab_text validate[required],custom[numberplus]"
				name="FUNDS_ZZYGRS"  placeholder="请输入在职员工人数"  maxlength="8"  value="${busRecord.FUNDS_ZZYGRS}"/></td>
		</tr>
        <c:if test="${busRecord.FUNDS_GJJLX == 'ZFGJJ'}">
            <tr class="zfgjj">
                <td class="tab_width"><font class="tab_color">*</font>单位缴存比例（5%-12%）：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJDWJCBL"  placeholder="请输入单位缴存比例" value="${busRecord.FUNDS_GJJDWJCBL}"/>
                </td>
                <td class="tab_width"><font class="tab_color">*</font>个人缴存比例：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJGRJCBL"  placeholder="请输入个人缴存比例" value="${busRecord.FUNDS_GJJGRJCBL}"/>
                </td>
            </tr>
            <tr class="zfgjj">
                <td class="tab_width"><font class="tab_color">*</font>汇缴人数：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJJCRS"  placeholder="请输入汇缴人数" value="${busRecord.FUNDS_GJJJCRS}"/>
                </td>
                <td class="tab_width">工资总额：</td>
                <td>
                    <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJGJZE"  placeholder="请输入工资总额" value="${busRecord.FUNDS_GJJGJZE}"/>
                </td>
            </tr>
            <tr class="zfgjj">
                <td class="tab_width">月汇缴总额：</td>
                <td colspan="3">
                    <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJYJCZE" placeholder="根据各员工缴存金额相加" value="${busRecord.FUNDS_GJJYJCZE}"/>
                </td>
            </tr>
        </c:if>
        <c:if test="${busRecord.FUNDS_GJJLX == 'ZFBT'}">
            <tr class="zfbt">
                <td class="tab_width"><font class="tab_color">*</font>汇缴人数：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJBTJCRS"  placeholder="请输入汇缴人数" value="${busRecord.FUNDS_GJJBTJCRS}"/>
                </td>
                <td class="tab_width">月汇缴总额：</td>
                <td>
                    <input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJBTYJCZE"  placeholder="根据各员工缴存金额相加" value="${busRecord.FUNDS_GJJBTYJCZE}"/>
                </td>
            </tr>
            <tr class="zfbt">
                <td class="tab_width"><font class="tab_color">*</font>一次性发放人数：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJBTYCXFFRS"  placeholder="请输入一次性发放人数" value="${busRecord.FUNDS_GJJBTYCXFFRS}"/>
                </td>
                <td class="tab_width"><font class="tab_color">*</font>一次性发放金额：</td>
                <td>
                    <input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="8"
                           name="FUNDS_GJJBTYCXFFJE"  placeholder="请输入一次性发放金额" value="${busRecord.FUNDS_GJJBTYCXFFJE}"/>
                </td>
            </tr>
        </c:if>
			<tr>
				<td class="tab_width">月缴存基础总额：</td>
				<td><input type="text" class="tab_text validate[],custom[JustNumber]" 
					name="FUNDS_YJCJCZE"  placeholder="请输入月缴存基础总额"  maxlength="16"  value="${busRecord.FUNDS_YJCJCZE}"/></td>
				<td class="tab_width"><font class="tab_color">*</font>单位首次汇缴时间：</td>
				<td>
					<input type="radio" class="validate[required]" name="FUNDS_DWSCHJSJ" value="1" 
					<c:if test="${busRecord.FUNDS_DWSCHJSJ==1 }"> checked="checked"</c:if>>本月
					<input type="radio" class="validate[required]" name="FUNDS_DWSCHJSJ" value="2" 
					<c:if test="${busRecord.FUNDS_DWSCHJSJ==2}"> checked="checked"</c:if>>次月
				</td>
			</tr>
    </table>
</c:if>