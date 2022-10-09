<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="OTHER_FORM"  >
	<div class="syj-title1 ">
		<span>社会保险登记信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>社保经办人：</th>
				<td><input type="text" class="syj-input1 validate[required]" name="INSURANCE_OPERRATOR"  
					maxlength="16"  placeholder="请输入社保经办人"  value="${busRecord.INSURANCE_OPERRATOR}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[mobilePhoneLoose]" maxlength="11"
					name="INSURANCE_PHONE"  placeholder="请输入移动电话" value="${busRecord.INSURANCE_PHONE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>身份证号码：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[vidcard]"  
					name="INSURANCE_IDNO" placeholder="请输入身份证号码" maxlength="18"value="${busRecord.INSURANCE_IDNO}"/></td>				
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>参加险种：</th>
				<td colspan="3">
					<eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect"
						name="INSURANCE_TYPE" width="749px" clazz="checkbox validate[required]"
						dataParams="insuranceType"  value="${busRecord.INSURANCE_TYPE}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
		<script>
            $(function(){
                var runStatus="${busRecord.RUN_STATUS}";
                if(runStatus=="0"||runStatus=="1"||runStatus==""){
                    var insrranceTypes= document.getElementsByName("INSURANCE_TYPE");
                    for(i=0;i<insrranceTypes.length;i++){
                        if(insrranceTypes[i].value=='1'||insrranceTypes[i].value=='5'){
                            insrranceTypes[i].checked=true;
                            insrranceTypes[i].disabled="disabled";
                        }
                    }
                }
            });
		</script>
        <span style="margin-top:5px;">
        <font style="color: red;font-family: serif;font-size: 16px;line-height: 40px;">
        根据《中华人民共和国社会保险法》用人单位应当自成立之日起三十日内持单位印章，向当地社会保险经办机构申请办理养老、工伤保险登记。</font>
        </span>
	</div>
</form>
