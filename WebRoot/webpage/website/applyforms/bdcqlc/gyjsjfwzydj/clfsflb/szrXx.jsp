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
    
   
	
	
</script>
<!-- 房产赠与受赠人信息开始 -->
<div class="bsbox clearfix" id="szrxx" style="display:none">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>房产受赠人信息</span></li>
        </ul>
    </div>
    <div name="szrxx">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" >
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>姓名：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="30" id="SZR_XM" name="SZR_XM"
                                       value="${busRecord.SZR_XM}" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>证件号码：</th>
                            <td >
                                <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_ZJHM" style="float: left;"
                                       name="SZR_ZJHM" value="${busRecord.SZR_ZJHM}" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>住址：</th>
                            <td>
                                <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_ZZ" style="float: left;"
                                       name="SZR_ZZ" value="${busRecord.SZR_ZZ}" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>联系电话：</th>
                            <td >
                                <input type="text" class="tab_text validate[required] " maxlength="30" id="SZR_LXDH" style="float: left;"
                                       name="SZR_LXDH" value="${busRecord.SZR_LXDH}" />
                            </td>
                        </tr>
                        <tr>
                        	<th class="tab_width"><font class="tab_color">*</font>与赠与人关系：</th>
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
</div>
<!-- 房产赠与受赠人信息结束 -->

