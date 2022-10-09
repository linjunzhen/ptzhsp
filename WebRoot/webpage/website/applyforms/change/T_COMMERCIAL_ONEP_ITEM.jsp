<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style type="text/css">
	/*****增加CSS****/
	.btnOneP {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 34px;
		line-height: 34px;
		margin-top: 10px;
		text-align: center;
		width: 106px;
	}
</style>
<form action="" id="ITEM_FORM"  >

	
	<div class="syj-title1 tmargin20">
		<span>原变更内容</span>
	</div>
	
	<div id="jsxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr class="REG_ADDR" style="display: none">
					<th><font class="syj-color2">*</font>住所：</th>
					<td><input type="text" class="syj-input1"
						name="OLD_REG_ADDR"  value="${busRecord.OLD_REG_ADDR}"   maxlength="256"/></td>

				</tr>
				<tr class="BUS_YEARS" style="display: none">

					<th><font class="syj-color2">*</font>营业期限：</th>
					<td><input type="text" class="syj-input1 "
						name="OLD_BUS_YEARS"  value="${busRecord.OLD_BUS_YEARS}"  maxlength="256"/></td>
				</tr>
				<tr class="BUS_SCOPE" style="display: none">

					<th><font class="syj-color2">*</font>经营范围：</th>
					<td>
						<textarea rows="3" cols="200" name="OLD_BUS_SCOPE"
								  class="eve-textarea maxSize[2000]"
								  style="width:90%;height:100px;" >${busRecord.OLD_BUS_SCOPE}</textarea>

					</td>
				</tr>

			</table>

		</div>
	</div>
	
	
	<div class="syj-title1 tmargin20">
		<span>申请变更登记内容</span>
	</div>
	<div id="jlxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr class="REG_ADDR" style="display: none">
					<th><font class="syj-color2">*</font>住所：</th>
					<td><input type="text" class="syj-input1 "
							   name="NEW_REG_ADDR" value="${busRecord.NEW_REG_ADDR}"  maxlength="256"/></td>

				</tr>
				<tr class="BUS_YEARS" style="display: none">

					<th ><font class="syj-color2">*</font>营业期限：</th>
					<td><input type="text" class="syj-input1"
							   name="NEW_BUS_YEARS"  value="${busRecord.NEW_BUS_YEARS}"  maxlength="256"/></td>
				</tr>
				<tr class="BUS_SCOPE" style="display: none">

					<th><font class="syj-color2">*</font>经营范围：</th>

					<td >
				<textarea rows="3" cols="200" name="NEW_BUS_SCOPE"
						  class="eve-textarea maxSize[2000]" readonly="readonly"
						  style="width:90%;height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwNew();">${busRecord.NEW_BUS_SCOPE}</textarea>
						<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
						<a href="javascript:showSelectJyfwNew();" class="btn1 BUS_SCOPE"  style="display: none">选 择</a>
						<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
							<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
							中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
							可以参照政策文件、行业习惯或者专业文献等提出申请</div>
					</td>

				</tr>
			</table>
		</div>
	</div>
</form>
