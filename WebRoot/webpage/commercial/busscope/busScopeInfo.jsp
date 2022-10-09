<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">

	$(function () {
		$("#ML_NAME").combobox({
			url: 'busScopeController/findIndustryCategory.do?parentId=0',
			method: 'post',
			valueField: 'INDU_CODE',
			textField: 'INDU_NAME',
			panelHeight: '200',
			editable: false,
			required: true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess: function (row) {

			},
			onSelect: function (row) {
				$("input[name='ML_CODE']").val(row.INDU_CODE);
				$('#DL_NAME').combobox('clear');
				$('#ZL_NAME').combobox('clear');
				$('#XL_NAME').combobox('clear');
				if (row.INDU_ID) {
					var url = 'busScopeController/findIndustryCategory.do?parentId=' + row.INDU_CODE;
					$('#DL_NAME').combobox('reload', url);
				}
			}
		});

		$("#DL_NAME").combobox({
			url: 'busScopeController/findIndustryCategory.do?parentId=${busscope.ML_NAME}',
			method: 'post',
			valueField: 'INDU_CODE',
			textField: 'INDU_NAME',
			panelHeight: '200',
			editable: false,
			required: true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess: function (row) {

			},
			onSelect: function (row) {
				$("input[name='DL_CODE']").val(row.INDU_CODE);
				$('#ZL_NAME').combobox('clear');
				$('#XL_NAME').combobox('clear');
				if (row.INDU_ID) {
					var url = 'busScopeController/findIndustryCategory.do?parentId=' + row.INDU_CODE;
					$('#ZL_NAME').combobox('reload', url);
				}
			}
		});


		$("#ZL_NAME").combobox({
			url: 'busScopeController/findIndustryCategory.do?parentId=${busscope.DL_NAME}',
			method: 'post',
			valueField: 'INDU_CODE',
			textField: 'INDU_NAME',
			panelHeight: '200',
			editable: false,
			required: true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess: function (row) {

			},
			onSelect: function (row) {
				$('#XL_NAME').combobox('clear');
				if (row.INDU_ID) {
					var url = 'busScopeController/findIndustryCategory.do?parentId=' + row.INDU_CODE;
					$('#XL_NAME').combobox('reload', url);
				}
			}
		});


		$("#XL_NAME").combobox({
			url: 'busScopeController/findIndustryCategory.do?parentId=${busscope.ZL_NAME}',
			method: 'post',
			valueField: 'INDU_CODE',
			textField: 'INDU_NAME',
			panelHeight: '200',
			editable: false,
			required: true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess: function (row) {

			},
			onSelect: function (row) {

			}
		});

		$("#ML_NAME").combobox("setValue", '${busscope.ML_NAME}');
		$("#DL_NAME").combobox("setValue", '${busscope.DL_NAME}');
		$("#ZL_NAME").combobox("setValue", '${busscope.ZL_NAME}');
		$("#XL_NAME").combobox("setValue", '${busscope.XL_NAME}');
	});


	//选择子行业
    function showSelectChildIndustry(){
    	var childIndustryIds = $("input[name='CHILD_INDUSTRYIDS']").val();
    	$.dialog.open(encodeURI("busScopeController.do?selector&allowCount=0&childIndustryIds="+childIndustryIds), {
       		title : "子行业选择器",
               width:"1000px",
               lock: true,
               resize:false,
               height:"500px",
               close: function () {
       			var selectChildIndustryInfo = art.dialog.data("selectChildIndustryInfo");
       			if(selectChildIndustryInfo){
       		    	$("input[name='CHILD_INDUSTRYIDS']").val(selectChildIndustryInfo.childIndustryIds);
        			$("input[name='CHILD_INDUSTRYNAMES']").val(selectChildIndustryInfo.childIndustryNames);
       		    	art.dialog.removeData("selectChildIndustryInfo");
       			}
       		}
       	}, false);
    }
   

	$(function() {
		AppUtil.initWindowForm("BusScopeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BusScopeForm").serialize();
				var url = $("#BusScopeForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:5,
							    ok: true
							});
							parent.$("#BusScopeGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_WSBS_BUSSCOPE");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BusScopeForm" method="post"
		action="busScopeController.do?saveOrUpdate">		
		<div id="BusScopeFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="ID" value="${busscope.ID}">
		<input type="hidden" name="CHILD_INDUSTRYIDS" value="${busscope.CHILD_INDUSTRYIDS}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:left;">条目代码：</span>
					 <c:if test="${busscope.ID!=null}">
					 ${busscope.BUSSCOPE_CODE}
					 </c:if> 
					 <c:if test="${busscope.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="64"
							class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
							value="${busscope.BUSSCOPE_CODE}" id="BUSSCOPE_CODE" name="BUSSCOPE_CODE" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
				<td><span style="width: 130px;float:left;text-align:left;">经营范围表述条目：</span>
					<input type="text" style="width:300px;float:left;" maxlength="128"
					class="eve-input validate[required]" value="${busscope.BUSSCOPE_NAME}"
					name="BUSSCOPE_NAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td><span style="width: 100px;float:left;text-align:left;">是否可用：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="YESNO"
						dataInterface="dictionaryService.findDatasForSelect" value="${busscope.IS_USABLE}"
						defaultEmptyText="==请选择==" name="IS_USABLE"></eve:eveselect><font
					class="dddl_platform_html_requiredFlag">*</font></td>
				<td><span style="width: 130px;float:left;text-align:left;">是否秒批：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="YESNO"
						dataInterface="dictionaryService.findDatasForSelect" value="${busscope.IS_MP}"
						defaultEmptyText="==请选择==" defaultEmptyValue="0" name="IS_MP"></eve:eveselect><font
					class="dddl_platform_html_requiredFlag">*</font></td>
			</tr>



			<tr>
				<td colspan="2"><span style="width: 100px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>行业门类：</span>
					<input type="text" style="width:150px;float:left;margin-right: 30px;" maxlength="8" readonly="readonly"
						   class="eve-input validate[required]" value="${busscope.ML_CODE}"
						   name="ML_CODE" />
					<input class="easyui-combobox"   style="width:250px;float:left;height: 30px;margin-left: 90px;"  name="ML_NAME" id="ML_NAME"
						   data-options="prompt:'请选择行业门类'">
					</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 100px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>行业类别：</span>
					<input type="text" style="width:100px;float:left;margin-right: 30px;" maxlength="8" readonly="readonly"
						   class="eve-input validate[required]" value="${busscope.DL_CODE}"
						   name="DL_CODE" />
					<input class="easyui-combobox validate[required]"   style="width:250px;float:left;height: 30px;margin-left: 90px;"  name="DL_NAME" id="DL_NAME"
						   data-options="prompt:'请选择行业大类'">
					<input class="easyui-combobox validate[required]"   style="width:250px;float:left;height: 30px;margin-left: 90px;"  name="ZL_NAME" id="ZL_NAME"
						   data-options="prompt:'请选择行业中类'">
					<input class="easyui-combobox validate[required]"   style="width:250px;float:left;height: 30px;margin-left: 90px;"  name="XL_NAME" id="XL_NAME"
						   data-options="prompt:'请选择行业小类'">
				</td>
			</tr>



			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">子行业名称：</span>
					<input type="text" style="width:850px;float:left;" maxlength="512" readonly="readonly"
					class="eve-input validate[]" value="${busscope.CHILD_INDUSTRYNAMES}" onclick="showSelectChildIndustry();"
					name="CHILD_INDUSTRYNAMES" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">经营范围规范条目说明：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input " value="${busscope.REMARK}"
					name="REMARK" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">对应《国民经济行业分类》编码：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input validate[required]" value="${busscope.INDU_CODE}"
					name="INDU_CODE" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">对应《国民经济行业分类》名称：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input validate[required]" value="${busscope.INDU_NAME}"
					name="INDU_NAME" /><font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">相关信息共享推送部门：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input validate[]" value="${busscope.DEPARTMENT_NAME}"
					name="DEPARTMENT_NAME" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">经营范围规范条目对应涉企行政许可事项：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input validate[]" value="${busscope.ITEM_NAME}"
					name="ITEM_NAME" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">备案事项：</span>
					<input type="text" style="width:850px;float:left;" maxlength="256"
					class="eve-input validate[]" value="${busscope.BAITEM_NAME}"
					name="BAITEM_NAME" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">数据库事项：</span>
					<input type="text" style="width:500px;float:left;" maxlength="256"
					class="eve-input validate[required]" value="${busscope.ITEM_TYPE}"
					name="ITEM_TYPE" /><font class="dddl_platform_html_requiredFlag">*(填写类型：后置事项、一般事项、前置事项)</font>
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">使用经营范围规范条目办理登记的相关活动：</span>
					<textarea name="GFTMXGHD" cols="140" rows="10" class="validate[[required],maxSize[4000]]" >${busscope.GFTMXGHD}</textarea>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="width: 210px;float:left;text-align:left;">经营范围规范条目不包含的相关活动内容：</span>
					<textarea name="FGFTMXGHD" cols="140" rows="10" class="validate[[],maxSize[1024]]" >${busscope.FGFTMXGHD}</textarea>
				</td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

