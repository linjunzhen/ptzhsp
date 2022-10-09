<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript">
</script>
<div class="bsbox clearfix" id="fjbdDiv">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>投资项目规划选址及用地申请表</span></li>			
		</ul>
	</div>
	<div class="bsboxC">
		
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1" id="sqjg">	
			<tr>
				<th><span class="bscolor1">*</span>拟投资项目名称</th>
				<td>
				<input type="text" maxlength="100" name="ANNEX_PROJECTNAME" class="tab_text validate[required]" />
				</td>
				<th><span class="bscolor1">*</span>投资人</th>
				<td>
				<input type="text" maxlength="30" name="ANNEX_PAYER" class="tab_text validate[required]"  />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>项目负责人姓名</th>
				<td>
				<input type="text" maxlength="16" name="ANNEX_FUZEREN" class="tab_text validate[required]" />
				</td>
				<th><span class="bscolor1">*</span>项目负责人电话</th>
				<td>
				<input type="text" maxlength="13" class="tab_text validate[[required],custom[fixOrMobilePhone]]" name="ANNEX_FUZERENTEL" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>联系人姓名</th>
				<td>
				<input type="text" maxlength="16" name="ANNEX_CONTACTNAME" class="tab_text validate[required]" />
				</td>
				<th><span class="bscolor1">*</span>联系人电话</th>
				<td>
				<input type="text" maxlength="13" class="tab_text validate[[required],custom[fixOrMobilePhone]]" name="ANNEX_CONTACTTEL" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					建筑工程
				</td>
			</tr>
			<tr>
				<th>工程种类</th>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="ANNEXPROJECTTYPE"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择工程种类" name="ANNEX_PROJECTTYPE" id="annexProjecttype">
					</eve:eveselect>
				</td>
				<th>用地性质</th>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="LANDUSETYPE"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择用地性质" name="ANNEX_USELANDTYPE" id="annexUsetype">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th>主要经营业务</th>
				<td  colspan="3">
					<textarea id="annexMainBusiness" class="eve-textarea validate[],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_MAIN_BUSINESS" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th>拟选址地址</th>
				<td>
				<input type="text" maxlength="200" name="ANNEX_PLANADDRESS" class="tab_text" />
				</td>
				<th>规划用地面积(m²)</th>
				<td>
				<input type="text" maxlength="32" class="tab_text validate[[],custom[numberplus]]" name="ANNEX_PLANLANDAREA" />
				</td>
			</tr>
			<tr>
				<th>建筑面积(m²)</th>
				<td>
				<input type="text" maxlength="32" name="ANNEX_CREATEAREA" class="tab_text validate[[],custom[numberplus]]" />
				</td>
				<th>容积率</th>
				<td>
				<input type="text" maxlength="32" class="tab_text validate[[],custom[numberplus]]" name="ANNEX_PLOTRATIO" />
				</td>
			</tr>
			<tr>
				<th>投资估算(万元)</th>
				<td style="border-right-style: none;"><input type="text" class="tab_text validate[[],custom[numberplus]]" 
					name="ANNEX_LAYCOST"/></td>
				<th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
							<td style="border-left-style: none;"></td>
			</tr>
			<tr>
				<td colspan="4">
					土木工程
				</td>
			</tr>
			<tr>
				<th>工程种类</th>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="ANNEXTUMUTYPE"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择土木工程种类" name="ANNEX_TUMU_PROJECTTYPE" id="annexTumuProjecttype">
					</eve:eveselect>
				</td>
				<th>名称</th>
				<td>
				<input type="text" maxlength="100" class="tab_text" name="ANNEX_TUMU_PROJECTNAME" />
				</td>
			</tr>
			<tr>
				<th>主要经营业务</th>
				<td  colspan="3">
					<textarea id="annexTumuMainbusiness" class="eve-textarea validate[],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_TUMU_MAINBUSINESS" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th>起点</th>
				<td>
				<input type="text" maxlength="100" name="ANNEX_TUMU_START" class="tab_text" />
				</td>
				<th>终点</th>
				<td>
				<input type="text" maxlength="100" class="tab_text" name="ANNEX_TUMU_END" />
				</td>
			</tr>
			<tr>
				<th>总长度(公里)</th>
				<td>
				<input type="text" maxlength="16" name="ANNEX_TUMU_ALLLENGTH" class="tab_text validate[[],custom[numberplus]]" />
				</td>
				<th>规划用地面积(m²)</th>
				<td>
				<input type="text" maxlength="16" class="tab_text validate[[],custom[numberplus]]" name="ANNEX_TUMU_PLANLANDAREA" />
				</td>
			</tr>
			<tr>
				<th>控制线宽度(m)</th>
				<td>
				<input type="text validate[[],custom[numberplus]]" maxlength="16" name="ANNEX_TUMU_CONTROLLINEWIDTH" class="tab_text" />
				</td>
				<th>投资估算(万元)</th>
				<td>
				<input type="text" maxlength="16" class="tab_text validate[[],custom[numberplus]]" name="ANNEX_TUMU_LAYCOST" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					对接信息
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>项目对接情况</th>
				<td  colspan="3">
					<textarea id="annexDecisonDetail" class="eve-textarea validate[required],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_DECISION_DETAIL" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>对接联络员</th>
				<td>
				<input type="text" maxlength="16" name="ANNEX_JOINTNAME" class="tab_text validate[required]" />
				</td>
				<th><span class="bscolor1">*</span>联络员电话</th>
				<td>
				<input id="annexJointtel" type="text" name="ANNEX_JOINTTEL"  maxlength="16" class="tab_text validate[[],custom[fixOrMobilePhone]]" />
				</td>
			</tr>
		</table>
	</div>	
</div>
