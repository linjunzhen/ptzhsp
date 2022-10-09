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
<!-- 基本信息开始 -->
<div id="jbxx">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
        <tr>
            <th colspan="4">事项基本信息</th>
        </tr>
        <tr>
            <td>审批事项：</td>
            <td>${serviceItem.ITEM_NAME}</td>
            <td style="width: 170px;background: #fbfbfb;">承诺时间(工作日):</td>
            <td>${serviceItem.CNQXGZR}</td>
        </tr>
        <tr>
            <td>所属部门：</td>
            <td>${serviceItem.SSBMMC}</td>
            <td style="width: 170px;background: #fbfbfb;">审批类型：</td>
            <td>${serviceItem.SXLX}</td>
        </tr>

        <tr>
            <td style="width: 170px;background: #fbfbfb;">
                <font class="tab_color">*</font>
                申报名称：
            </td>
            <td colspan="3">
                <input type="text" class="tab_text validate[required]" style="width: 70%" maxlength="60"
                       name="SBMC" value="${busRecord.SBMC }" />
                <span style="color: red;">
					<strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span>
            </td>
        </tr>
        <tr>
            <td style="width: 170px;background: #fbfbfb;">申报号：</td>
            <td id="EXEID" colspan="3" ></td>
        </tr>
    </table>
</div>
<!-- 基本信息结束 -->
