<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ScheduleForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#ScheduleForm").serialize();
				var url = $("#ScheduleForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							 parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});

							parent.$("#ScheduleGrid").datagrid("reload");
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
		}, "T_MSJW_SYSTEM_SCHEDULE");
	});
	function genCron(){
		
		var cronType = $("input[name='CRON_TYPE']").val();
		var firstRunhour = $("input[name='FIRST_RUN_HOUR']").val();
		var firstRunminute = $("input[name='FIRST_RUN_MINUTE']").val();
		var intervalNum = $("input[name='INTERVAL_NUM']").val();
		var jobCron = $("input[name='JOB_CRON']").val();
		if(cronType==1){
			jobCron = "0 "+firstRunminute+"/"+intervalNum+" "+firstRunhour+"/1 * * ?";
		}else if(cronType==2){
		    jobCron = "0 "+firstRunminute+" "+firstRunhour+"/"+intervalNum+" * * ?";
		}else if(cronType==3){
			jobCron = "0 "+firstRunminute+" "+firstRunhour+" 1/"+intervalNum+" * ?";
		}else if(cronType==4){
			var checkBoxValues = AppUtil.getCheckBoxCheckedValue("WEEK_CHOISE");
			if(checkBoxValues!=""){
				jobCron = "0 "+firstRunminute+" "+firstRunhour+" ? * "+checkBoxValues+" * ";
			}else{
				alert("请选择时间!");
				return ;
			}
		}else if(cronType==5){
		    var interMonthNum= $("input[name='INTERMONTH_NUM']").val();
			var dayNum= $("input[name='DAY_NUM']").val();
			jobCron = "0 "+firstRunminute+" "+firstRunhour+" "+dayNum+" 1/"+interMonthNum+" ? * ";
		}
		$("input[name='JOB_CRON']").val(jobCron);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ScheduleForm" method="post"
		action="scheduleController.do?saveOrUpdate">
		<div id="ScheduleFormDiv">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="JOB_ID" value="${schedule.JOB_ID}">
            <input type="hidden" name="JOB_STATUS" value="${schedule.JOB_STATUS}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;" 
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>配置信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">任务名称：</span>
					    <c:if test="${schedule.JOB_ID!=null}">
						   ${schedule.JOB_NAME}
						   <input type="hidden" name="JOB_NAME" value="${schedule.JOB_NAME}">
						</c:if>
						<c:if test="${schedule.JOB_ID==null}">
						<input type="text" style="width:150px;float:left;" maxlength="60"
						id="JOB_NAME"
						class="eve-input validate[required],ajax[ajaxVerifyValueExist]"
						value="${schedule.JOB_NAME}" name="JOB_NAME" />
						<font class="dddl_platform_html_requiredFlag">*</font>
						</c:if>
					</td>
					<td><span style="width: 100px;float:left;text-align:right;">表达式类型：</span>
						<input style="width:150px;height:25px;float:left;"
						class="easyui-combobox" value="${schedule.CRON_TYPE}"
						name="CRON_TYPE"
						data-options="url:'dictionaryController.do?load&typeCode=JobType',
						editable:false,method: 'post',valueField:'DIC_CODE',
						textField:'DIC_NAME',panelWidth: 150,
						panelHeight: 'auto',
						onSelect:function(rec){
						    if(rec.DIC_CODE=='1'){
						         $('#INTERVAL_NUM').attr('style','');
						         $('#INTERVAL_NUMLABLE').html('每隔N分钟：');
						         $('#WEEK_CHOISE').attr('style','display: none;');
						         $('#MONTH_SET').attr('style','display: none;');
						    }else if(rec.DIC_CODE=='2'){
						         $('#INTERVAL_NUM').attr('style','');
						         $('#INTERVAL_NUMLABLE').html('每隔N小时：');
						         $('#WEEK_CHOISE').attr('style','display: none;');
						         $('#MONTH_SET').attr('style','display: none;');
						    }else if(rec.DIC_CODE=='3'){
						         $('#INTERVAL_NUM').attr('style','');
						         $('#INTERVAL_NUMLABLE').html('每隔N天：');
						         $('#WEEK_CHOISE').attr('style','display: none;');
						         $('#MONTH_SET').attr('style','display: none;');
						    }else if(rec.DIC_CODE=='4'){
						         $('#WEEK_CHOISE').attr('style','');
						         $('#INTERVAL_NUM').attr('style','display: none;');
						         $('#MONTH_SET').attr('style','display: none;');
						    }else if(rec.DIC_CODE=='5'){
						         $('#MONTH_SET').attr('style','');
						         $('#INTERVAL_NUM').attr('style','display: none;');
						         $('#WEEK_CHOISE').attr('style','display: none;');
						    }
						}
						" />
					</td>
				</tr>
				
				<tr style="display: none;" id="INTERVAL_NUM">
					<td><span style="width: 100px;float:left;text-align:right;" id="INTERVAL_NUMLABLE"></span>
						<input type="text" style="width:150px;float:left;"
						class="easyui-numberbox" value="${schedule.INTERVAL_NUM}"
						name="INTERVAL_NUM" />
					</td>
					<td></td>
				</tr>
				
				<tr style="display: none;" id="WEEK_CHOISE" >
					<td colspan="2"><span style="width: 100px;float:left;text-align:right;" >周选择：</span>
					    <eve:checkboxgroup typecode="OneWeek" value="${schedule.WEEK_CHOISE}" width="370" fieldname="WEEK_CHOISE">
					    </eve:checkboxgroup>
					</td>
				</tr>
				
				<tr style="display: none;" id="MONTH_SET" >
					<td colspan="2"><span style="width: 100px;float:left;text-align:right;" >月时间设置：</span>
					每隔<input type="text" style="width:50px;float:left;"
						class="easyui-numberbox" value="${schedule.INTERMONTH_NUM}"
						name="INTERMONTH_NUM" />个月的第
					   <input type="text" style="width:50px;float:left;"
						class="easyui-numberbox" value="${schedule.DAY_NUM}"
						name="DAY_NUM" />天
					</td>
				</tr>
				
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">初始运行小时：</span>
						<input style="width:150px;height:25px;float:left;"
						class="easyui-combobox validate[required]"
						value="${schedule.FIRST_RUN_HOUR}" name="FIRST_RUN_HOUR"
						data-options="url:'scheduleController.do?hours',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: '200'" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
					<td><span style="width: 100px;float:left;text-align:right;">初始运行分钟：</span>
						<input style="width:150px;height:25px;float:left;"
						class="easyui-combobox validate[required]"
						value="${schedule.FIRST_RUN_MINUTE}" name="FIRST_RUN_MINUTE"
						data-options="url:'scheduleController.do?minutes',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: '200'" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
			<table style="width:100%;border-collapse:collapse;" 
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">CRON表达式：</span>
						<input type="text" style="width:180px;float:left;" maxlength="28"
						class="eve-input validate[required]" value="${schedule.JOB_CRON}"
						name="JOB_CRON" /><font class="dddl_platform_html_requiredFlag">*</font>
						<input type="button" onclick="genCron();" value="生成表达式" class="eve-button" style="margin:3px 0 0 10px;"/>
						</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">对应CLASS名称：</span>
						<input type="text" style="width:350px;float:left;" maxlength="124"
						class="eve-input validate[required]"
						value="${schedule.CLASS_NAME}" name="CLASS_NAME" /><font
						class="dddl_platform_html_requiredFlag">*</font></td>
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
