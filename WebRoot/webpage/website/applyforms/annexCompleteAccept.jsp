<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript">
</script>
<div class="bsbox clearfix" id="fjbdDiv1">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>项目统一竣工验收申请表</span></li>			
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
				<th><span class="bscolor1">*</span>投资项目概况</th>
				<td  colspan="3">
					<textarea id="annexProjectOverview" class="eve-textarea validate[required],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_PROJECT_OVERVIEW" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>规划选址及用地情况</th>
				<td  colspan="3">
					<textarea id="annexAddressUseland" class="eve-textarea validate[required],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_ADDRESS_USELAND" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>《工程可行性研究报告+》批复情况</th>
				<td  colspan="3">
					<textarea id="annexReportReply" class="eve-textarea validate[required],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_REPORT_REPLY" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>《一阶段施工图》审查情况</th>
				<td  colspan="3">
					<textarea id="annexBuildeCheck" class="eve-textarea validate[required],maxSize[300]" rows="3" cols="200"  
					  name="ANNEX_BUILDE_CHECK" style="width:660px;height:100px;" ></textarea>
				</td>
			</tr>
		</table>
	</div>	
</div>
