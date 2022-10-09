<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("WorkdayForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#WorkdayForm").serialize();
			var url = $("#WorkdayForm").attr("action");
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if(resultJson.success){
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
						 art.dialog.data("WorkdayInfo", {
							 wsetId:$("select[name='W_SETID']").val()
					    });
						AppUtil.closeLayer();
					}else{
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				}
			});
		}
	},"T_MSJW_SYSTEM_WORKDAY",null, $("input[name='W_ID']").val());
	
	
	
});

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="WorkdayForm" method="post" action="workdayController.do?updateWorkday">
	    <div  id="WorkdayFormDiv">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="W_ID" value="${workday.W_ID}"> 
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
		    <tr style="height:29px;"><td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td></tr>
		    <tr style="height:40px;">
		          <td><span style="width: 100px;float:left;text-align:right;">日期：</span>
                ${workday.W_DATE} 
                  </td>
            </tr>
            <tr style="height:40px;">
                  <td><span style="width: 100px;float:left;text-align:right;">类型<font class="dddl_platform_html_requiredFlag">*</font>：</span>
                <select type="select"  class="tab_text" style="width: 90px;"  name="W_SETID">
                 <option value="1" <c:if test="${workday.W_SETID==1}">selected="true"</c:if>>
                                                                                休息日
                </option>
                <option value="2" <c:if test="${workday.W_SETID==2}">selected="true"</c:if>>
                                                                                 工作日
                </option>
                </select>
                  </td>
            </tr>
            <tr style="height:40px;">
                  <td><span style="width: 100px;float:left;text-align:right;">理由：</span>
                   <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[120]]"
                                    style="width: 250px" name="W_TITLE">${workday.W_TITLE}</textarea> 
                  </td>
            </tr>
            </table>

        </div>

		<div class="eve_buttons">
		    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
