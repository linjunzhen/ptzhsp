<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
	<script type="text/javascript"> 
    $(function(){
    
    $("#ZJLX").attr("disabled","disabled");
    $("#ZY").attr("disabled","disabled");
    $("#jglx").attr("disabled","disabled");
    });
    </script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="UserInfoForm" method="post" action="userInfoController.do?saveOrUpdate">
		<div id="UserInfoFormDiv">
			<!--==========隐藏域部分开始 ===========-->

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">用户帐号：</span>
						${userInfo.YHZH}</td>
				</tr>
				<c:if test="${userInfo.USER_TYPE=='1'}">
				    <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">
                                                                            姓名：
                      </span>
                        ${userInfo.YHMC}</td>
                    </tr>
                    <tr>
                       <td ><span style="width: 130px;float:left;text-align:right;">证件类型：</span><eve:eveselect 
                                    dataParams="DocumentType" id="ZJLX"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择证件类型====" name="ZJLX" value="${userInfo.ZJLX}"></eve:eveselect></td>
                    </tr>
                    <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">证件号码：</span>
                        ${userInfo.ZJHM}</td>
                    </tr>
                    <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">手机号码：</span>
                        ${userInfo.SJHM}</td>
                    </tr>
                    <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">电话号码：</span>
                        ${userInfo.DHHM}</td>
                    </tr>
				    <tr>
                        <td><span style="width: 130px;float:left;text-align:right;">性别：</span>
                            <c:if test="${userInfo.YHXB=='1'}">
                                                                                                            男
                            </c:if>
                            <c:if test="${userInfo.YHXB=='2'}">
                                                                                                            女
                            </c:if>
                            </td>
                    </tr>
					<tr>
						<td><span style="width: 130px;float:left;text-align:right;">邮箱地址：</span>
							${userInfo.DZYX}</td>
					</tr>
				</c:if>
				<c:if test="${userInfo.USER_TYPE=='2'}">
				<tr>
                    <td><span style="width: 130px;float:left;text-align:right;">
                                                                        机构名称：
                      </span>
                        ${userInfo.YHMC}</td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">
                                                                        机构类型：
                      </span>
                        <eve:eveselect clazz="validate[required]" id="jglx"
                        dataParams="OrgType"
                        dataInterface="dictionaryService.findDatasForSelect"
                        defaultEmptyText="=====选择机构类型====" name="JGLX" value="${userInfo.JGLX}"></eve:eveselect></td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">
                                                                        单位机构代码：
                      </span>
                        ${userInfo.ZZJGDM}</td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">
                                                                        机构代码证图片：
                      </span>
                      <c:if test="${!empty  userInfo.FILE_ID}">
                        <span style="width:370px;text-align:left;" id="${userInfo.FILE_ID}">
                                <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${userInfo.FILE_ID}')"><font color="blue">查看</font></a>
                                </span>
                        </c:if>
                     </td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">法人代表姓名：</span>
                        ${userInfo.FRDB}</td>
                </tr>
                <tr>
                        <td><span style="width: 130px;float:left;text-align:right;">法人代表性别：</span>
                            <c:if test="${userInfo.YHXB=='1'}">
                                                                                                            男
                            </c:if>
                            <c:if test="${userInfo.YHXB=='2'}">
                                                                                                            女
                            </c:if>
                            </td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">法人代表身份证：</span>
                        ${userInfo.ZJHM}</td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">法人代表手机：</span>
                        ${userInfo.SJHM}</td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">电话号码：</span>
                        ${userInfo.DHHM}</td>
                </tr>
                <tr>
                        <td><span style="width: 130px;float:left;text-align:right;">邮政编码：</span>
                            ${userInfo.YZBM}</td>
                </tr>
                <tr>
                    <td><span style="width: 130px;float:left;text-align:right;">单位住址：</span>
                        ${userInfo.DWDZ}</td>
                </tr>
				<tr>
                        <td><span style="width: 130px;float:left;text-align:right;">经办人邮箱地址：</span>
                            ${userInfo.JBRYXDZ}</td>
                </tr>
                <tr>
                        <td><span style="width: 130px;float:left;text-align:right;">经办人姓名：</span>
                            ${userInfo.JBRXM}</td>
                </tr>
                <tr>
                        <td><span style="width: 130px;float:left;text-align:right;">经办人身份证：</span>
                            ${userInfo.JBRSFZ}</td>
                </tr>
				</c:if>
				
			</table>


		</div>
	</form>

</body>

