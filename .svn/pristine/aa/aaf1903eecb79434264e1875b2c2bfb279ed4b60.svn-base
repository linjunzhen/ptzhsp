<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<form action="" id="SEAL_FORM"  >
	<c:if test="${busRecord.IS_FREE_ENGRAVE_SEAL==1||requestParams.IS_FREE_ENGRAVE_SEAL==1}">
	<div class="syj-title1 ">
		<span>企业公章<font class="syj-color2">*</font>(<font style="width:105%;">说明：两个套餐区别为法定代表人名章材质不同：套餐一材质为牛角，套餐二材质为橡胶。</font>)</span>
	</div>
	<div style="font-size: 16px;color: red;font-weight: bold;">
		选择免费刻制印章的，请携带法定代表人和经办人身份证原件到窗口
	</div>
	<div style="position:relative;">	
		<div style="margin-top: 10px;margin-bottom: 10px;">
			<input type="radio" name="SEAL_PACKAGE" value="2" class="validate[required]" <c:if test="${busRecord.SEAL_PACKAGE==2}"> checked="checked"</c:if>/>套餐一
		</div>
		<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3">
			<tr>
				<th>采购内容</th>
				<th>型号</th>
				<th>材质</th>
				<th>数量</th>
			</tr>
			<tr>
				<td style="text-align:center;">企业公章</td>
				<td style="text-align:center;">直径40mm圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">财务专用章</td>
				<td style="text-align:center;">直径38mm圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">发票专用章</td>
				<td style="text-align:center;">40mm*30mm椭圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">法定代表人名章</td>
				<td style="text-align:center;">18mm*18mm正方形</td>
				<td style="text-align:center;color:red;">牛角</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<th>供应商</th>
				<td colspan="3"  style="text-align:center;">
					平潭综合实验区鑫楚印章制作有限公司
				</td>
			</tr>
		</table>
	</div>
	<div style="position:relative;">
		<div style="margin-top: 10px;margin-bottom: 10px;">
			<input type="radio" name="SEAL_PACKAGE" value="1" class="validate[required]" <c:if test="${busRecord.SEAL_PACKAGE==1}"> checked="checked"</c:if>/>套餐二
		</div>
		<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3">
			<tr>
				<th>采购内容</th>
				<th>型号</th>
				<th>材质</th>
				<th>数量</th>
			</tr>
			<tr>
				<td style="text-align:center;">企业公章</td>
				<td style="text-align:center;">直径40mm圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">财务专用章</td>
				<td style="text-align:center;">直径38mm圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">发票专用章</td>
				<td style="text-align:center;">40mm*30mm椭圆形</td>
				<td style="text-align:center;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<td style="text-align:center;">法定代表人名章</td>
				<td style="text-align:center;">18mm*18mm正方形</td>
				<td style="text-align:center;color:red;">橡胶</td>
				<td style="text-align:center;">1枚</td>
			</tr>
			<tr>
				<th>供应商</th>
				<td colspan="3"  style="text-align:center;">
				漳州市盾安印章制作服务有限公司
				</td>
			</tr>
		</table>
	</div>
	</c:if>
	<c:if test="${busRecord.IS_FREE_ENGRAVE_SEAL==0||requestParams.IS_FREE_ENGRAVE_SEAL==0}">
		<div class="syj-title1 ">
			<span>企业公章<font class="syj-color2">*</font>(<font style="color:red;width:105%;">以下为平潭综合实验区印章刻制单位名单，可自主选择自费刻制印章（名单仅供参考）</font>)</span>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3">
				<tr>
					<th>供应商（按照成立日期排序）</th>
					<th>联系电话</th>
				</tr>
				<tr>
					<td style="text-align:center;">平潭县鑫星印章刻制社</td>
					<td style="text-align:center;">13950473363</td>
				</tr>
				<tr>
					<td style="text-align:center;">平潭宏业印章有限公司</td>
					<td style="text-align:center;">13559150565</td>
				</tr>
				<tr>
					<td style="text-align:center;">平潭综合实验区鑫楚印章制作有限公司</td>
					<td style="text-align:center;">13559151202</td>
				</tr>
				<tr>
					<td style="text-align:center;">红正（平潭）防伪科技有限公司</td>
					<td style="text-align:center;">15859404471</td>
				</tr>
				<tr>
					<td style="text-align:center;">平潭腾安刻章店</td>
					<td style="text-align:center;">13906909988</td>
				</tr>
			</table>
		</div>
	</c:if>
	<c:if test="${busRecord.ISEMAIL==1||requestParams.ISEMAIL==1}">
		<div class="syj-title1 ">
			<%--非秒批 --%>
			<c:if test="${requestParams.SSSBLX!='1'&&busRecord.SSSBLX!='1'}">
				<span>邮寄营业执照及公章信息</span>
			</c:if>
			<%--秒批 --%>
			<c:if test="${requestParams.SSSBLX=='1'||busRecord.SSSBLX=='1'}">
				<span>邮寄营业执照</span>
			</c:if>
	
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th> 是否邮寄营业执照<c:if test="${requestParams.SSSBLX!='1'&&busRecord.SSSBLX!='1'}">和公章</c:if>：</th>
					<td>
						<input type="radio" name="ISEMAIL" value="1" <c:if test="${busRecord.ISEMAIL==1}"> checked="checked"</c:if>/>是
						<input type="radio" name="ISEMAIL" value="0" <c:if test="${busRecord.ISEMAIL!=1}"> checked="checked"</c:if>/>否
					</td>
				</tr>
			</table>
		</div>
		<div style="position:relative;display: none;" id="emailInfo">
	
			<table cellpadding="0" cellspacing="0" class="syj-table2">
	
				<tr>
					<th><font class="syj-color2">*</font>收件人姓名：</th>
					<td>
						<input type="text" class="syj-input1 " maxlength="16"
							   name="EMS_RECEIVER"  value="${busRecord.EMS_RECEIVER}"/>
					</td>
					<th><font class="syj-color2">*</font>收件人电话：</th>
					<td>
						<input type="text" class="syj-input1 validate[custom[mobilePhone]]" maxlength="16"
							   name="EMS_PHONE" value="${busRecord.EMS_PHONE}"/>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>寄送地址：</th>
					<td colspan="3" >
						<input type="text" class="syj-input1 w878" maxlength="100"
							   name="EMS_ADDRESS" value="${busRecord.EMS_ADDRESS}"/>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>寄送城市：</th>
					<td colspan="3" >
						<input type="text" class="syj-input1 w878" maxlength="64"
							   name="EMS_CITY" value="${busRecord.EMS_CITY}"/>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
</form>
