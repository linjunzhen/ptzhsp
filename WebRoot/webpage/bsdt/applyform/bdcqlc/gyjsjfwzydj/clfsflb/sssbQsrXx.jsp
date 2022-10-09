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
</style>

<script type="text/javascript">
    $(function() {
		
    });
    
    function setValidate(zjlx){
		if(zjlx=="SF"){
			$("#NSR_ZJLX").addClass(",custom[vidcard]");
		}else{
			$("#NSR_ZJLX").removeClass(",custom[vidcard]");
		}
	}
	
	
</script>
<!-- 涉税申报签署人信息开始 -->
<div id="sssbqsrxx" name="sssbqsrxx" style="display:none">
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>涉税申报签署人信息
			</th>
		</tr>
        <tr>
            <td style="padding: 10px">
                 <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                    	<td class="tab_width"><font class="tab_color">*</font>签署人姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="30" id="SSSB_QSRXM" name="SSSB_QSRXM"
                                   value="${busRecord.SSSB_QSRXM}" style="float: left;width:240px" />
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>签署人证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[required]" onchange="setValidate(this.value);"
					dataParams="DocumentType" value="${busRecord.SSSB_QSRZJLX}"
					dataInterface="dictionaryService.findDatasForSelect" id="SSSB_QSRZJLX"
					defaultEmptyText="====选择证件类型====" name="SSSB_QSRZJLX"></eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                    	<td class="tab_width"><font class="tab_color">*</font>签署人证件号码：</td>
                        <td >
                            <input type="text" class="tab_text validate[required] " maxlength="30" id="SSSB_QSRZJHM" style="float: left;width:240px"
                                   name="SSSB_QSRZJHM" value="${busRecord.SSSB_QSRZJHM}" />
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>签署人手机号码：</td>
                        <td >
                            <input type="text" class="tab_text validate[required] " maxlength="30" id="SSSB_QSRSJHM" style="float: left;width:240px"
                                   name="SSSB_QSRSJHM" value="${busRecord.SSSB_QSRSJHM}" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
   </table>
</div>
<!-- 涉税申报签署人信息结束 -->

