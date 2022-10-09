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

.haveButtonTitle {
	position: absolute;
	left: 47%;
}

.titleButton {
	float: right;
	margin: 2px 0;
	margin-right: 10px;
	padding: 0 20px !important;
}

.signButton {
	margin-left: 25% !important;
	padding: 0 40px !important;
}
</style>
<script type="text/javascript">
	$(function() {});
</script>
<!-- 登记缴费明细开始 -->
<div id="djjfxx" name="djxx_djjfxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span class="haveButtonTitle">登记缴费明细</span>
				<a href="javascript:void(0);" style="float:right; margin: 2px 10px;"
					class="eflowbutton titleButton" onclick="showSelectBdcdydacx();" name="showSelectBdcdydacx">打印缴费单子</a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addDjjfxx();"
					name="addDjjfxx">新增</a>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="updateDjjfxx();"
					name="updateDjjfxx">保存</a>
			</th>
		</tr>
	</table>

	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djjfxxInfo">
		<input type="hidden" name="djjfxxInfoTrid" id="djjfxxInfoTrid" />
		<tr id="djjfxxInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							收费类型：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="SFLX"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择收费类型"
								name="DJJFMX_SFLX" id="DJJFMX_SFLX" value="${busRecord.DJJFMX_SFLX}">
							</eve:eveselect>
						</td>
						<td class="tab_width">收费科目编码：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SFKMBM" maxlength="128"
								value="${busRecord.DJJFMX_SFKMBM}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">计费人员：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_JFRY" maxlength="32"
								value="${busRecord.DJJFMX_JFRY}" />
						</td>
						<td class="tab_width">收费科目名称：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="KMMC"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择收费科目名称"
								name="DJJFMX_SFKMMC" value="${busRecord.DJJFMX_SFKMMC}">
							</eve:eveselect>
						</td>
					</tr>

					<tr>
						<td class="tab_width">收费基数：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
								value="${busRecord.DJJFMX_SFJS}" />
						</td>
						<td class="tab_width">计费日期：</td>
						<td>
							<input type="text" id="DJJFMX_JFRQ" name="DJJFMX_JFRQ" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">是否额外收费：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收是否额外收费"
								name="DJJFMX_SFEWSF" value="${busRecord.DJJFMX_SFEWSF}">
							</eve:eveselect>
						</td>

						<td class="tab_width">
							<font class="tab_color">*</font>
							应收金额：
						</td>
						<td>
							<input type="text" class="tab_text validate[required]" name="DJJFMX_YSJE" maxlength="128"
								value="${busRecord.DJJFMX_YSJE}" id="DJJFMX_YSJE" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">折后应收金额：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_ZHYSJE" maxlength="128"
								value="${busRecord.DJJFMX_ZHYSJE}" />
						</td>
						<td class="tab_width">实际付费人：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SJFFR" maxlength="32"
								value="${busRecord.DJJFMX_SJFFR}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">付费方：</td>
						<td colspan="3">
							<input type="text" class="tab_text" name="DJJFMX_FFF" maxlength="500" style="width: 72%;"
								value="${busRecord.DJJFMX_FFF}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">收费单位：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
								value="${busRecord.DJJFMX_SFDW}" />
						</td>
						<td class="tab_width">实收金额：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SSJE" maxlength="128"
								value="${busRecord.DJJFMX_SSJE}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">收费人员：</td>
						<td>
							<input type="text" class="tab_text" name="DJJFMX_SFRY" maxlength="32"
								value="${busRecord.DJJFMX_SFRY}" />
						</td>
						<td class="tab_width">收费日期：</td>
						<td>
							<input type="text" class="tab_text" name=DJJFMX_SFRQ maxlength="32"
								value="${busRecord.DJJFMX_SFRQ}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">费用申报意见：</td>
						<td colspan="3">
							<input type="text" class="tab_text" name="DJJFMX_FYSBYJ" maxlength="500"
								style="width: 72%; float: left;" value="${busRecord.DJJFMX_FYSBYJ}" />
							<div>
								<a href="javascript:void(0);" style="margin-top: 2px;" class="eflowbutton titleButton"
									onclick="showSelectBdcdydacx();" name="showSelectBdcdydacx">确认收费</a>
							</div>
						</td>
					</tr>
				</table>

				<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="djjfxxTable">
					<tr>
						<!--<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>-->
						<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">缴费基数</td>
						<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">应收金额</td>
						<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">折扣后应收金额</td>
						<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">实收金额</td>
						<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">收费人员</td>
						<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">收费日期</td>
						<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">操作</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- 登记缴费明细结束 -->

<!-- 登记发证信息开始 -->
<div id="djfzxx" name="djxx_djfzxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span class="haveButtonTitle">登记发证信息</span>
				<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addDjfzxx();"
					name="addDjfzxx">新增</a>
			</th>
		</tr>
	</table>

	<div class="tab_height"></div>
	<table class="tab_tk_pro2" id="djfzxxInfos">
		<input type="hidden" name="djfzxxInfoTrid" id="djfzxxInfoTrid" />
		<tr>
			<!-- 发证信息开始 -->
			<td style="width:80%;">
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djfzxxInfo">
					<tr id="djfzxxInfo_1">
						<td style="padding: 0;">
							<table class="tab_tk_pro2">
								<tr>
									<td class="tab_width">发证人员：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_FZRY" maxlength="32"
											value="${busRecord.DJFZXX_FZRY}" />
									</td>
									<td class="tab_width">发证时间：</td>
									<td>
										<input type="text" id="DJFZXX_FZSJ" name="DJFZXX_FZSJ" maxlength="60" style="width:184px;"
											value="${busRecord.DJFZXX_FZSJ}"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate" />
									</td>
								</tr>

								<tr>
									<td class="tab_width">发证名称：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_FZMC" maxlength="128"
											value="${busRecord.DJFZXX_FZMC}" />
									</td>
									<td class="tab_width">发证数量：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_FZSL" maxlength="32"
											value="${busRecord.DJFZXX_FZSL}" />
									</td>
								</tr>

								<tr>
									<td class="tab_width">领证人姓名：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_LZRXM" maxlength="32"
											value="${busRecord.DJFZXX_LZRXM}" />
									</td>
									<td class="tab_width">领证人证件类型：</td>
									<td>
										<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
											dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
											name="DJFZXX_LZRZJLX" value="${busRecord.DJFZXX_LZRZJLX}">
										</eve:eveselect>
									</td>
								</tr>

								<tr>
									<td class="tab_width">领证人证件号码：</td>
									<td colspan="3">
										<input type="text" class="tab_text" name="DJFZXX_LZRZJHM" maxlength="128"
											style="width: 72%; float: left;" value="${busRecord.DJFZXX_LZRZJHM}" />
										<div>
											<a href="javascript:void(0);" class="eflowbutton titleButton"
												onclick="DjxxLzrLegalRead();" name="DjxxLzrLegalRead">读卡</a>
										</div>
									</td>
								</tr>

								<tr>
									<td class="tab_width">领证人电话：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_LZRDH" maxlength="32"
											value="${busRecord.DJFZXX_LZRDH}" />
									</td>
									<td class="tab_width">领证人邮编：</td>
									<td>
										<input type="text" class="tab_text" name="DJFZXX_LZREB" maxlength="32"
											value="${busRecord.DJFZXX_LZREB}" />
									</td>
								</tr>

								<tr>
									<td class="tab_width">领证人地址：</td>
									<td colspan="3">
										<input type="text" class="tab_text" name="DJFZXX_LZRDZ" maxlength="128"
											style="width: 72%;" value="${busRecord.DJFZXX_LZRDZ}" />
									</td>
								</tr>

								<tr>
									<td class="tab_width">发证备注：</td>
									<td colspan="3">
										<input type="text" class="tab_text" name="DJFZXX_FZBZ" maxlength="500" style="width: 72%;"
											value="${busRecord.DJFZXX_FZBZ}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<!-- 发证信息结束 -->

			<!-- 电子签名开始 -->
			<td style="width:20%; padding: 0;">
				<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djfzxxImg">
					<tr id="djfzxxImg_1">
						<input type="hidden" name="djfzxxImgId" id="djfzxxImgId" />
						<td style="padding: 0;">
							<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
								<tr>
									<th>
										<a href="javascript:void(0);" class="eflowbutton titleButton signButton"
											onclick="addDjfzxx();" name="addDjfzxx">启动签证</a>
									</th>
								</tr>
								<tr>
									<td style="height: 200px; text-align:center;">
										领证件人签名图片
										<img src="images/2_11.gif" />
									</td>
								<tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<!-- 电子签名结束 -->

		</tr>
	</table>

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="djfzxxTable">
		<tr>
			<!-- <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>  -->
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">发证名称</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">发证人员</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">发证数量</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">发证时间</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">领证人姓名</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>
	</table>
</div>
<!-- 登记发证信息结束 -->

<!-- 登记归档信息开始 -->
<div id="djgdxx" name="djxx_djgdxx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>登记归档信息</th>
		</tr>
	</table>

	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djgdxxInfo">
		<tr id="djgdxxInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">卷宗号：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="DJGDXX_JZH" maxlength="128"
								value="${busRecord.DJGDXX_JZH}" />
						</td>
						<td class="tab_width">文件数：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="DJGDXX_WJS" maxlength="32"
								value="${busRecord.DJGDXX_WJS}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">总页数：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="DJGDXX_ZYS" maxlength="32"
								value="${busRecord.DJGDXX_ZYS}" />
						</td>
						<td class="tab_width">操作人：</td>
						<td>
							<input type="text" class="tab_text validate[]" name="DJGDXX_CZR" maxlength="32"
								value="${busRecord.DJGDXX_CZR}" />
						</td>
					</tr>

					<tr>
						<td class="tab_width">归档时间：</td>
						<td>
							<input type="text" id="DJGDXX_GDSJ" name="DJGDXX_GDSJ" maxlength="60" style="width:184px;"
								value="${busRecord.DJGDXX_GDSJ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate" />
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_width">归档备注：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" style="width: 73%" name="DJGDXX_GDBZ"
								value="${busRecord.DJGDXX_GDBZ}" maxlength="500" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
			</td>
			<td>
				<input type="hidden" name="trid" />
			</td>
		</tr>
	</table>
</div>
<!-- 登记归档信息结束 -->
