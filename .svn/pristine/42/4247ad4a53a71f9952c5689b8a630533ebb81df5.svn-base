<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("SysEhcacheForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#SysEhcacheForm").serialize();
				var url = $("#SysEhcacheForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							parent.$("#SysEhcacheGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "T_MSJW_SYSTEM_EHCACHE");
		
		$("input[name='EHCACHE_STATUE']").click(function(){
            //获取值
            var czlxValue = $(this).val();
            if(czlxValue=="1"){
                $("#CZLX_TR").attr("style","display:none;");
            }else{
                $("#CZLX_TR").attr("style","");
            }
        });

	});
	
	function selectDelEhcache(){
        var delEhcacheKeys = $("input[name='EHCACHE_DEL_KEY']").val();
        parent.$.dialog.open("sysEhcacheController.do?selector&allowCount=10&delEhcacheKeys="+delEhcacheKeys, {
            title : "缓存选择器",
            width:"800px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var delEhcacheKeysInfo = art.dialog.data("delEhcacheKeysInfo");
                if(delEhcacheKeysInfo){
                    $("input[name='EHCACHE_DEL_KEY']").val(delEhcacheKeysInfo.delEhcacheKeys);
                    $("textarea[name='EHCACHE_DEL_NAME']").text(delEhcacheKeysInfo.delEhcacheNames);
                    art.dialog.removeData("delEhcacheKeysInfo");
                }
            }
        }, false);
    }
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SysEhcacheForm" method="post"
		action="sysEhcacheController.do?saveOrUpdate">
		<div id="SysEhcacheFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="EHCACHE_ID"
				value="${sysEhcache.EHCACHE_ID}"> <input type="hidden"
				name="EHCACHE_KEY" value="${sysEhcache.EHCACHE_KEY}">

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">缓存类名<font
							class="dddl_platform_html_requiredFlag">*</font>：
					</span> <input type="text" style="width:350px;float:left;"
						maxlength="1000"
						class="eve-input validate[required],custom[onlyLetterNumberSpec]"
						value="${sysEhcache.EHCACHE_CLASS_NAME}" name="EHCACHE_CLASS_NAME" /></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">缓存名称<font
							class="dddl_platform_html_requiredFlag">*</font>：
					</span> <input type="text" style="width:350px;float:left;" maxlength="100"
						class="eve-input validate[required]"
						value="${sysEhcache.EHCACHE_NAME}" name="EHCACHE_NAME" /></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">操作类型
							<font class="dddl_platform_html_requiredFlag">*</font>：
					</span> <eve:radiogroup typecode="EhcacheStatue" width="300"
							fieldname="EHCACHE_STATUE" defaultvalue="1"
							value="${sysEhcache.EHCACHE_STATUE}"></eve:radiogroup></td>
				</tr>
				<tr id="CZLX_TR" <c:if test="${sysEhcache.EHCACHE_STATUE=='1'}">style="display: none;"</c:if> >
                            <td>
                            <span style="width: 100px;float:left;text-align:right;">删除缓存<font
                            class="dddl_platform_html_requiredFlag">*</font>：
                            </span>
                            <input type="hidden"
                            name="EHCACHE_DEL_KEY" value="${sysEhcache.EHCACHE_DEL_KEY}">
                            <textarea rows="5" cols="5" class="eve-textarea validate[required]" 
                                    style="width: 350px;" name="EHCACHE_DEL_NAME" onclick="selectDelEhcache();">${sysEhcache.EHCACHE_DEL_NAME}</textarea>
                            </td>
                </tr>
			</table>


		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

