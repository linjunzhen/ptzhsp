<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<%--预购商品房预告登记 预购预告信息 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='ygygInfo'>
	<tr>
		<th>基本信息</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
					<td >
						<input type="text" class="tab_text validate[required]" maxlength="128"
						name="BDCDYH" value="${busRecord.BDCDYH}" style="width: 72%;"  />
					</td>
					<td colspan='2'>
					<a href="javascript:void(0);" class="eflowbutton">异议档案查询</a>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>不动产权证号：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[required]" maxlength="128"
						name="BDCQZH" value="${busRecord.BDCQZH}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>权利人：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="QLR_MC" maxlength="32" value="${busRecord.QLR_MC}"/></td>
					
				    <td class="tab_width"><font class="tab_color">*</font>坐落：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="ZL" maxlength="100" value="${busRecord.ZL}"/></td>
				</tr>
				
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>权力类型：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="QLLX" maxlength="32" value="${busRecord.QLLX}"/></td>
					
				    <td class="tab_width"><font class="tab_color">*</font>宗地面积：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="ZDMJ" maxlength="100" value="${busRecord.ZDMJ}"/></td>
				</tr>
				
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>申请人姓名：</td>
					<td ><input type="text" class="tab_text  validate[required]" 
						name="SQR_MC" maxlength="32" value="${busRecord.SQR_MC}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>申请人证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="SQR_ZJLX" id="SQR_ZJLX"  value="身份证">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>申请人证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="SQR_ZJNO" style="float: left;"
						name="SQR_ZJNO" value="${busRecord.SQR_ZJNO}" />
					</td>
					<td><a href="javascript:void(0);" onclick="YgygYwrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
					<td class="tab_width">起始时间：</td>
					<td>
						<input type="text" id="QSSJ" name="QSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'JSSJ\')}'})" 
							class="tab_text Wdate validate[]" maxlength="60"  style="width: 160px"/>
					</td>
					<td class="tab_width">结束时间：</td>
					<td>
						<input type="text" id="JSSJ" name="JSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QSSJ\')}'})" 
							class="tab_text Wdate validate[]" maxlength="60"  style="width: 160px"/>
					</td>
				</tr>	
				<tr>
					<td class="tab_width">登记机构：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="25"
						name="DJJG" value="${busRecord.DJJG}" style="width: 72%;"  />
					</td>
					<!--<td class="tab_width"><font class="tab_color">*</font>区县代码：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[required]" dataParams="htlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择区县" name="QXDM" id="QXDM"  value="${busRecord.QXDM}">
						</eve:eveselect>
					</td>-->
				</tr>
				<tr>
					<td class="tab_width">不动产登记证明号：</td>
					<td colspan='3' >
						<input type="text" class="tab_text validate[]" maxlength="64"
						name="BDC_BDCDJZMH" value="${busRecord.BDC_BDCDJZMH}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="DJYY" id="DJYY" value="预售商品房预告登记" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">异议事项：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="YYSX" id="YYSX" value="${busRecord.YYSX}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">附记：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="FJ" value="${busRecord.FJ}" style="width: 72%;"  />
					</td>
				</tr>	
				<tr class="dbxxtr" style="display:none">
					<td class="tab_width"><font class="tab_color">*</font>注销异议原因：</td>
					<td>
					  <input type="text" class="tab_text" maxlength="64" id="YYZXYY" style="width: 72%;float: left;"
						name="YYZXYY" value="${busRecord.YYZXYY}" />
					</td>
					<td>
						<a href="javascript:void(0);" class="eflowbutton">确认登簿</a>
					</td>
					<td></td>
				</tr>
				<tr class="dbxxtr" style="display:none">
					<td class="tab_width">登簿时间：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="BDC_DJSJ" value="${busRecord.BDC_DJSJ}" style="width: 72%;"  />
					</td>
					<td class="tab_width">登簿人：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="BDC_DBR" value="${busRecord.BDC_DBR}" style="width: 72%;"  />
					</td>
				</tr>
				
				<input type="hidden" name="YYDJZX_FILE_URL" >
				<input type="hidden" name="YYDJZX_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="YGYG_FILE_DIV"></div>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:YGYGchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
						<a href="javascript:void(0);" onclick="openSlxxFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=YGYG_fileListDiv></div>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>