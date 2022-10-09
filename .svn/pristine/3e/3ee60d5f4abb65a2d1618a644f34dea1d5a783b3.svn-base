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
<div class="bsbox clearfix" id="blswdj" style="display:none">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>实名办税信息</span></li>
        </ul>
    </div>
    <div name="blswdj">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" >
                        <tr>
                            <th style="width:20%" class="tab_width"><font class="tab_color">*（必传）</font>申请人员上传名单：</th>
                            <td colspan='3'>
                                <div onclick="openSwdjFileUploadDialog();" style="margin-left: 10px;cursor: pointer;">
	                            <img src="webpage/bsdt/applyform/images/tab_btn_sc.png"
	                                 style="width:60px;height:22px;"/>
		                        </div>
		                        <div style="margin-left: 10px;">
		                            <span id=fileListDiv></span>
		                        </div>
                            </td>
                        </tr>
                        <tr>
                            <th style="width:20%" class="tab_width">上传人员名单附件模板下载：</th>
                            <td colspan='3'>
                               <a href="webpage/website/applyforms/bdcqlc/gyjsjfwzydj/clfsflb/实名办税授权委托书.xls">实名办税授权委托书模板</a>
                            </td>
                        </tr>
                       <%--  <tr>
                        	<th class="tab_width"><font class="tab_color">*</font>代理人姓名：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="30" id="SWDJ_XM" name="SWDJ_XM"
                                       value="${busRecord.SWDJ_XM}" style="float: left;width:240px" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>代理人证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel validate[required]" onchange="setValidate(this.value);"
									dataParams="DocumentType" value="${busRecord.SWDJ_ZJLX}"
									dataInterface="dictionaryService.findDatasForSelect" id="SWDJ_ZJLX"
									defaultEmptyText="====选择证件类型====" name="SWDJ_ZJLX"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                        	<th class="tab_width"><font class="tab_color">*</font>代理人证件号码：</th>
                            <td >
                                <input type="text" class="tab_text validate[required] " maxlength="30" id="SWDJ_ZJHM" style="float: left;width:240px"
                                       name="SWDJ_ZJHM" value="${busRecord.SWDJ_ZJHM}" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>代理人手机号码：</th>
                            <td >
                                <input type="text" class="tab_text validate[required] " maxlength="30" id="SWDJ_SJHM" style="float: left;width:240px"
                                       name="SWDJ_SJHM" value="${busRecord.SWDJ_SJHM}" />
                            </td>
                        </tr> --%>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>
<!-- 实名办税信息结束 -->

