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
<!-- 房产赠与受赠人信息开始 -->
<div id="szrxx" name="szrxx" style="display:none">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>房产受赠人信息 </span>
			</th>
		</tr>
        <tr>
            <td style="padding: 10px">
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="30" id="SZR_XM" name="SZR_XM"
                                   value="${busRecord.SZR_XM}" />
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
                        <td >
                            <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_ZJHM" style="float: left;"
                                   name="SZR_ZJHM" value="${busRecord.SZR_ZJHM}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>住址：</td>
                        <td>
                            <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_ZZ" style="float: left;"
                                   name="SZR_ZZ" value="${busRecord.SZR_ZZ}" />
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
                        <td >
                            <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_LXDH" style="float: left;"
                                   name="SZR_LXDH" value="${busRecord.SZR_LXDH}" />
                        </td>
                    </tr>
                    <tr>
                    	<td class="tab_width"><font class="tab_color">*</font>与赠与人关系：</td>
                        <td colspan="3">
                             <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_YZRGX" style="float: left;"
                                   name="SZR_YZRGX" value="${busRecord.SZR_YZRGX}" />
                        </td>
                    </tr> 
                </table>
            </td>
        </tr>
    </table>
</div>
<!-- 房产赠与受赠人信息结束 -->

