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
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
	padding: 0 10px;
}

.tab_tk_pro2 tr td a {
	margin-top: 2px;
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
<!-- 总确权信息开始 -->
<div id="bdc_zqqxx" name="bdc_zqqxx">
<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>总确权项目基本信息</span>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="ZqqxxInfo">
		<input type="hidden" name="ZqqxxInfoTrid" id="ZqqxxInfoTrid" />
		<tr id="ZqqxxInfo_1">
			<td>
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							宗地代码：
						</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="ZQQ_ZDDM"
								value="${busRecord.ZQQ_ZDDM}" id="ZQQ_ZDDM" />
						</td>
						<td class="tab_width">
							<font class="tab_color">*</font>
							宗地特征码：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="zdtzm"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_ZDDZM" id="ZQQ_ZDDZM" value="${busRecord.ZQQ_ZDDZM}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							宗地面积：
						</td>
						<td>
							<input type="text" class="tab_text " maxlength="32" name="ZQQ_ZDMJ"
								value="${busRecord.ZQQ_ZDMJ}" id="ZQQ_ZDMJ" />
						</td>
						<td class="tab_width">
							<font class="tab_color">*</font>
							使用权面积：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="syqmj"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_SYQMJ" id="ZQQ_SYQMJ" value="${busRecord.ZQQ_SYQMJ}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							权利设定方式：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="qlsdfs"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_QLSDFS" id="ZQQ_QLSDFS" value="${busRecord.ZQQ_QLSDFS}">
							</eve:eveselect>
						</td>
						<td class="tab_width">权利起始时间：</td>
						<td>
							<input type="text" id="ZQQ_QLQSSJ" name="ZQQ_QLQSSJ" value="${busRecord.ZQQ_QLQSSJ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZQQ_QLJSSJ1\')}'})"
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">权利结束时间1：</td>
						<td>
							<input type="text" id="ZQQ_QLJSSJ1" name="ZQQ_QLJSSJ1" value="${busRecord.GYTD_END_TIME1}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZQQ_QLQSSJ\')}'})"
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>
						<td class="tab_width">权利结束时间2：</td>
						<td>
							<input type="text" id="ZQQ_QLJSSJ2" name="ZQQ_QLJSSJ2" value="${busRecord.GYTD_END_TIME1}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZQQ_QLQSSJ\')}'})"
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							土地用途：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="tdyt"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_TDYT" id="ZQQ_TDYT" value="${busRecord.ZQQ_TDYT}">
							</eve:eveselect>
						</td>
						<td class="tab_width">
							<font class="tab_color">*</font>
							权利性质：
						</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="qlxz"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_QLXZ" id="ZQQ_QLXZ" value="${busRecord.ZQQ_QLXZ}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">用途说明：</td>
						<td>
							<input type="text" class="tab_text " maxlength="2000" name="ZQQ_YTSM"
								value="${busRecord.ZQQ_YTSM}" style="width: 90%;" id="ZQQ_YTSM" />
						</td>
						<td class="tab_width">共有方式：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="gyfs"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_GYFS" id="ZQQ_GYFS" value="${busRecord.ZQQ_GYFS}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">土地四至_东：</td>
						<td>
							<input type="text" class="tab_text " maxlength="30" id="ZQQ_E" name="ZQQ_E"
								value="${busRecord.ZQQ_E}" />
						</td>
						<td class="tab_width">土地四至_南：</td>
						<td>
							<input type="text" class="tab_text " maxlength="30" id="ZQQ_S" name="ZQQ_S"
								value="${busRecord.ZQQ_S}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">土地四至_西：</td>
						<td>
							<input type="text" class="tab_text " maxlength="30" id="ZQQ_W" name="ZQQ_W"
								value="${busRecord.ZQQ_W}" />
						</td>
						<td class="tab_width">土地四至_北：</td>
						<td>
							<input type="text" class="tab_text " maxlength="30" id="ZQQ_N" name="ZQQ_N"
								value="${busRecord.ZQQ_N}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							工程名称：
						</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" id="ZQQ_GCMC" name="ZQQ_GCMC"
								value="${busRecord.ZQQ_GCMC}" />
						</td>
						<td class="tab_width">是否注销土地产权：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="sfzxtdcq"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
								name="ZQQ_SFZXTDCQ" id="ZQQ_SFZXTDCQ" value="${busRecord.ZQQ_SFZXTDCQ}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							工程地址：
						</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="1000" id="ZQQ_GCDZ"
								style="width: 72%;float: left;" name="ZQQ_GCDZ" value="${busRecord.ZQQ_GCDZ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">工程规划许可证：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" id="ZQQ_GCGHXKZ" name="ZQQ_GCGHXKZ"
								value="${busRecord.ZQQ_GCGHXKZ}" />
						</td>
						<td class="tab_width">规划验收日期：</td>
						<td>
							<input type="text" id="ZQQ_GHYSRQ" name="ZQQ_GHYSRQ" value="${busRecord.ZQQ_GHYSRQ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text Wdate"
								maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">工程施工许可证：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" id="ZQQ_GCSGXKZ" name="ZQQ_GCSGXKZ"
								value="${busRecord.ZQQ_GCSGXKZ}" />
						</td>
						<td class="tab_width">开工日期：</td>
						<td>
							<input type="text" id="ZQQ_KGRQ" name="ZQQ_KGRQ" value="${busRecord.ZQQ_KGRQ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text Wdate"
								maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">工程竣工验收证书号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" id="ZQQ_GCJGYSZSH" name="ZQQ_GCJGYSZSH"
								value="${busRecord.ZQQ_GCJGYSZSH}" />
						</td>
						<td class="tab_width">工程竣工日期：</td>
						<td>
							<input type="text" id="ZQQ_GCJGRQ" name="ZQQ_GCJGRQ" value="${busRecord.ZQQ_GCJGRQ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text Wdate"
								maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">
							<font class="tab_color">*</font>
							工程竣工验收日期：
						</td>
						<td>
							<input type="text" id="ZQQ_GCJGYSRQ" name="ZQQ_GCJGYSRQ" value="${busRecord.ZQQ_GCJGYSRQ}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text Wdate"
								maxlength="60" style="width:160px;" />
						</td>
						<td class="tab_width">总建筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_ZJZMJ" name="ZQQ_ZJZMJ"
								value="${busRecord.ZQQ_ZJZMJ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">确权楼栋数：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_QQLDS" name="ZQQ_QQLDS"
								value="${busRecord.ZQQ_QQLDS}" />
						</td>
						<td class="tab_width">总套数：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_ZTS" name="ZQQ_ZTS"
								value="${busRecord.ZQQ_ZTS}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">确权总套数（套间）：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_QQZTS" name="ZQQ_QQZTS"
								value="${busRecord.ZQQ_QQZTS}" />
						</td>
						<td class="tab_width">确权总建筑面积（套间）：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_QQZJZMJ" name="ZQQ_QQZJZMJ"
								value="${busRecord.ZQQ_QQZJZMJ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">业主共有部分套数：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_YZGYBFTS" name="ZQQ_YZGYBFTS"
								value="${busRecord.ZQQ_YZGYBFTS}" />
						</td>
						<td class="tab_width">业主共有部分总面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_YZGYBFZMJ" name="ZQQ_YZGYBFZMJ"
								value="${busRecord.ZQQ_YZGYBFZMJ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">共建分摊说明：</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="2000" id="ZQQ_GJFTSM"
								style="width:72%; float: left;" name="ZQQ_GJFTSM" value="${busRecord.ZQQ_GJFTSM}" />
							<a href="javascript:void(0);" class="eflowbutton" onclick="showSelectBdcdydacx();">计算总面积/总套数</a>
						</td>
					</tr>
					<tr>
						<td class="tab_width">总确权证书号：</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_ZQQZSH"
								style="width:72%; float: left;" name="ZQQ_ZQQZSH" value="${busRecord.ZQQ_ZQQZSH}" />
							<a href="javascript:void(0);" class="eflowbutton" onclick="showSelectBdcdydacx();">确认登簿</a>
						</td>
					</tr>
					<tr>
						<td class="tab_width">登簿人：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_DBR" name="ZQQ_DBR"
								value="${busRecord.ZQQ_DBR}" />
						</td>
						<td class="tab_width">登簿时间：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" id="ZQQ_DBSJ" name="ZQQ_DBSJ"
								value="${busRecord.ZQQ_DBSJ}" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">备注：</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="2000" id="ZQQ_BZ"
								style="width:72%; float: left;" name="ZQQ_BZ" value="${busRecord.ZQQ_BZ}" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- 总确权信息结束 -->
