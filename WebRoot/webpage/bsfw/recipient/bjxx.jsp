<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript"
    src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("bjxxForm", function(form, valid) {
		if (valid) {
			var bjcllbJson = getbjcllbJson();
            if(bjcllbJson!="[]"){
            	$("input[name='BJCLLB']").val(bjcllbJson);
            }else{
            	alert("请选择补件材料!");
                return ;
            }
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#bjxxForm").serialize();
			var url = $("#bjxxForm").attr("action");
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
						parent.$("#SjglGrid").datagrid("reload");
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
	},"T_WSBS_BJXX",null, $("input[name='BJXX_ID']").val());
		
});

	function getbjcllbJson(){
		var bjcllbList = [];
		var applyMatersJson = $("input[name='applyMatersJson']").val();
        var applyMatersObj = JSON2.parse(applyMatersJson);
        for(var index in applyMatersObj){
            //获取材料编码
            var MATER_CODE = applyMatersObj[index].MATER_CODE;
            var selectValue = $("#SFBJ_"+MATER_CODE).val();
            if(selectValue&&selectValue=="1"){
            	var bjcllb = {};
            	bjcllb.MATER_CODE = $("#mc_"+MATER_CODE).val();
            	bjcllb.MATER_NAME = $("#mn_"+MATER_CODE).text();
            	bjcllb.BJYJ = $("#bjyq_"+MATER_CODE).val();
            	bjcllbList.push(bjcllb);
            }
            
         }
        return JSON2.stringify(bjcllbList);
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="bjxxForm" method="post" action="recipientController.do?savebjxx">
	    <div  id="TicketsFormDiv">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="BJXX_ID" value="${bjxxId}">
		    <input type="hidden" name="TASK_ID" value="${taskId}"> 
		    <input type="hidden" name="EXE_ID" value="${exeId}">
		    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
		    <input type="hidden" name="BJCLLB" />
		    <input type="hidden" name="TASK_STATUS" value="${TASK_STATUS}">
            <input type="hidden" name="APPLY_STATUS" value="${APPLY_STATUS}">
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
			      <tr style="height:29px;"><td colspan="1" class="dddl-legend"  style="font-weight:bold;" >基本信息</td></tr>
			      <tr style="height:40px;">
                        <td>
                        <span style="width: 200px;float:left;text-align:right;">补件意见：</span>
                            <textarea rows="5" cols="5" class="eve-textarea validate[maxSize[500]]"
                                    style="width: 600px;height:50px;" name="BJYJ"></textarea>
                        </td>
                  </tr>
			      <tr style="height:40px;">
					    <td>
					    <span style="width: 200px;float:left;text-align:right;">是否发送短信申报人<font class="dddl_platform_html_requiredFlag">*</font>：</span>
						是<input type="radio" checked="checked" value="1" name="SFFDX">
						否<input type="radio"  value="0" name="SFFDX">
						</td>
				  </tr>
				  <tr style="height:29px;"><td colspan="1" class="dddl-legend"  style="font-weight:bold;" >补件信息</td></tr>
				  <tr >
                      <td>
                          <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
                                <tr>
                                    <td style="text-align: center;font-weight:bold;">材料名称</td>
                                    <td style="text-align: center;font-weight:bold;width: 100px;">是否补件</td>
                                    <td style="text-align: center;font-weight:bold;">补件要求</td>
                                </tr>
                                <c:forEach var="mater" items="${materList}">
                                <tr>
                                    <td style="text-align: center;">
                                    <input type="hidden" id="mc_${mater.MATER_CODE}" value="${mater.MATER_CODE}" />
                                    <span id="mn_${mater.MATER_CODE}">${mater.MATER_NAME}</span>
                                    </td>
                                    <td>
                                        <select type="select" class="tab_text" style="width: 90px;"  id="SFBJ_${mater.MATER_CODE}">
							               <option value="-1" >
							                                                                否
							               </option>
							               <option value="1" >
                                                                                                                                                                 是
                                           </option>
							           </select>
                                    </td>
                                    <td>
                                    <input type="text" id="bjyq_${mater.MATER_CODE}" class="eve-input" maxlength="60" style="width:95%;" />
                                    </td>
                                </tr>
                                </c:forEach>
                          </table>
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
