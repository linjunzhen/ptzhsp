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
</style>
<script type="text/javascript">
	$(function() {});
</script>
<!-- 登记审核开始 -->
<div id="djshxx" name="djxx_djshxx" style="display:none;">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>登记审核 
			</th>
		</tr>
		<tr>
		  <td id="ws_tr" style="padding: 10px;">
		      <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshxxInfo">
		        <tr>
		            <td class="tab_width"><font class="tab_color">*</font>是否完税：</td>
		            <td>
		                <eve:radiogroup fieldname="IS_FINISHTAX_AUDIT" width="500" defaultvalue="0" typecode="YesOrNo" onclick="isFinishTax();" value="${busRecord.IS_FINISHTAX_AUDIT}"></eve:radiogroup>
		            </td>
		        </tr>
		        <tr>
		            <td class="tab_width"><font class="tab_color" id="sssbFont">*</font>税费申报意见：</td>
                    <td>
                        <textarea name="TAX_AUDITCOMMENTS" cols="140" rows="10"
                        class="validate[[required],maxSize[500]]">${busRecord.TAX_AUDITCOMMENTS}</textarea>
                        <%-- <input type="text" class="tab_text validate[required]" maxlength="500" name="TAX_AUDITCOMMENTS"
                            value="${busRecord.TAX_AUDITCOMMENTS}" id="TAX_AUDITCOMMENTS" placeholder="清人工确认是否完成涉税申报并填写相应色涉税申报意见。"/> --%>
                    </td>
		        </tr>
		    </table>
		  </td>
		</tr>
	</table>
	
</div>
<!-- 登记审核结束 -->
