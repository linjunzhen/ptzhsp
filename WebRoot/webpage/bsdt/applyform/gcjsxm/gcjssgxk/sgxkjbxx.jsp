<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">施工许可基本信息</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<td class="tab_width">施工许可发证机关</td>
		<td colspan="3"><input type="text" class="tab_text inputBackgroundCcc validate[required]" name="CONSTRORG" maxlength="100" placeholder="请输入施工许可发证机关" value="平潭综合实验区行政审批局"  readonly="readonly" style="width:80%"/></td>				
	</tr>
	
</table>
<input type="hidden" name= "CHECKDEPARTNAME" value="${serviceItem.SSBMMC}"/>
<input type="hidden" name= "CHECKPERSONNAME" value="${sessionScope.curLoginUser.fullname}"/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" style="margin-top: -1px;">
	<tr id="constrNumTr" style="display: none;">
		<td class="tab_width"><font class="tab_color">*</font>施工许可证号</td>
		<td><input type="text" class="tab_text" name="CONSTRNUM" maxlength="32" placeholder="请输入施工许可证号" value="${busRecord.CONSTRNUM}"style="width:50%"/></td>			
		<td class="tab_width"><font class="tab_color">*</font>发证日期</td>
		<td><input type="text" class="tab_text Wdate" name="RELEASEDATE" maxlength="32" placeholder="请选择发证日期" value="${busRecord.RELEASEDATE}" 
		style="width:50%" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>			
	</tr>
    <tr id="certNumTr" style="display: none;">
        <td class="tab_width"><font class="tab_color">*</font>施工许可电子证照号</td>
        <td><input type="text" class="tab_text" name="CERT_NUM" maxlength="32" placeholder="施工许可电子证照号" value="${busRecord.CERT_NUM}"style="width:50%" readonly="readonly" />
                    <input id="HQZZ" type="button" class="eflowbutton"  
                   style="" onclick="getCertificate();"  value="获取施工许可电子证照"/>
        </td>
        <td class="tab_width" id="SGXKDZ">施工许可电子证照ofd文件</td>
        <td id="SGXKDZZZ"><input type="text" class="tab_text" name="CERTIFICATEIDENTIFIERFILENAME" maxlength="32" placeholder="施工许可电子证照号" value="${busRecord.CERTIFICATEIDENTIFIERFILENAME}" style="width:50%" readonly="readonly" />
                    <input type="button" class="eflowbutton"  
                   style="" onclick="downloadCertificate();"  value="下载施工许可电子证照"/>
        </td>
    </tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>建设用地规划许可证编号</td>
		<td><input type="text" class="tab_text validate[required]" name="BULDPLANNUM" maxlength="32" placeholder="请输入建设用地规划许可证编号" value="${busRecord.BULDPLANNUM}"/></td>				
		<td class="tab_width"><font class="tab_color">*</font>建设工程规划许可证编号</td>
		<td><input type="text" class="tab_text validate[required]" name="PROJECTPLANNUM" maxlength="32" placeholder="请输入建设工程规划许可证编号" value="${busRecord.PROJECTPLANNUM}"/></td>
	</tr>
	<tr>
		<td class="tab_width">建设单位</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="CONUNIT1" maxlength="2000" placeholder="自动回填" value="${busRecord.BUILDCORPNAME}"  readonly="readonly"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>所有制性质</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="OWNERSHIP"
			dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.OWNERSHIP}"
			defaultEmptyText="请选择所有制性质" name="OWNERSHIP" id="OWNERSHIP">
			</eve:eveselect>
		</td>	
	</tr>
	<tr>	
		<td class="tab_width">建设单位机构代码</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="CONORGCODE" maxlength="2000" placeholder="自动回填" value="${busRecord.BUILDCORPCODE}"  readonly="readonly"/></td>	
		<td class="tab_width">法人代表</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" name="LEGALPERSON" maxlength="512" placeholder="自动回填" value="${busRecord.LEGALPERSON}"  readonly="readonly"/></td>			
	</tr>
	<tr>
		<td class="tab_width">建设单位项目负责人</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" name="CONPERSON" maxlength="512" placeholder="自动回填" value="${busRecord.CONPERSON}"  readonly="readonly"/></td>	
		<td class="tab_width"><font class="tab_color">*</font>负责人联系电话</td>
		<td><input type="text" class="tab_text validate[required]" name="CONPERSONTEL" maxlength="512" placeholder="请输入负责人联系电话" value="${busRecord.CONPERSONTEL}"/></td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>建设单位地址</td>
		<td><input type="text" class="tab_text validate[required]" name="CONADDRESS" maxlength="512" placeholder="请输入建设单位地址" value="${busRecord.CONADDRESS}"/></td>	
		<td class="tab_width"><font class="tab_color">*</font>建设单位经办人姓名</td>
		<td><input type="text" class="tab_text validate[required]" name="JBRNAME" maxlength="32" placeholder="请输入建设单位经办人姓名" value="${busRecord.JBRNAME}"/></td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>建设单位经办人证件类型</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TBIDCARDTYPEDIC"
			dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'JBRIDENTITY');"
			defaultEmptyText="请选择证件类型" name="JBRIDENTITYTYPENUM" id="JBRIDENTITYTYPENUM"   value="${busRecord.JBRIDENTITYTYPENUM}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>建设单位经办人证件号码</td>
		<td><input type="text" class="tab_text validate[required]"
			name="JBRIDENTITY"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.JBRIDENTITY}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>建设单位经办人联系电话</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" name="JBRTEL" maxlength="11" placeholder="请输入建设单位经办人联系电话" value="${busRecord.JBRTEL}"/></td>	
	</tr>
	<tr>
		<td class="tab_width">工程项目名称</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="PRONAME" maxlength="100" placeholder="自动回填" value="${busRecord.TITLE}"  readonly="readonly"/>
			<div style="color:red;width:90%;">*自动回填“项目基本信息”中的本次“申报项名称”
			</div>
		</td>	
		<td class="tab_width">工程地点</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="BELONG" maxlength="100" placeholder="自动回填" value="${busRecord.PROADDRESS}"  readonly="readonly"/></td>	
	</tr>
	<tr>
		<td class="tab_width">合同开工日期</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="planBegDate" maxlength="16" placeholder="自动回填" value="${busRecord.PLANBEGDATE}"  readonly="readonly"/>
		</td>	
		<td class="tab_width">合同竣工日期	</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="planEndDate" maxlength="16" placeholder="自动回填" value="${busRecord.PLANENDDATE}"  readonly="readonly"/></td>	
	</tr>
	<tr>
		<td class="tab_width">合同工期</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="workDays" maxlength="16" placeholder="自动回填" value="${busRecord.WORKDAYS}"  readonly="readonly"/>
		</td>	
		<td class="tab_width">合同价格（万元）</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="proCost" maxlength="16" placeholder="自动回填" value="${busRecord.PROCOST}"  readonly="readonly"/></td>	
	</tr>
	<tr>
		<td class="tab_width">建设规模</td>
		<td colspan="3">
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="PURIDJS" maxlength="22" placeholder="自动回填" value="${busRecord.PRJSIZE}"/>
		</td>	
	</tr>
	<tr>
		<td class="tab_width">房建建筑面积（m<sup style="vertical-align: super;font-size: smaller;">2</sup>）</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="proArea" maxlength="16" value="${busRecord.PROAREA}"  readonly="readonly"/>
		</td>	
		<td class="tab_width">±0.000以上层数</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="structUpfloorNum" maxlength="16" value="${busRecord.STRUCTUPFLOORNUMS}"  readonly="readonly"/></td>	
	</tr>
	<tr>
		<td class="tab_width">市政长度（m）</td>
		<td>
			<input type="text" class="tab_text inputBackgroundCcc validate[]" id="muniLength" maxlength="16" value="${busRecord.MUNILENGTHS}"  readonly="readonly"/>
		</td>	
		<td class="tab_width">±0.000以下层数</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[]" id="structDwfloorNum" maxlength="16" value="${busRecord.STRUCTDWFLOORNUMS}"  readonly="readonly"/></td>	
	</tr>
	<tr>
		<td class="tab_width">工程类型</td>
		<td colspan="3">	
			<div>房屋建筑类：</div>
			<eve:excheckbox
				dataInterface="dictionaryService.findTwoDatasForSelect" 
				name="sgxkgclx" width="749px;" clazz="checkbox validate[required]"
				dataParams="TBPRJFUNCTIONDIC:1" value="${busRecord.PRJFUNCTIONNUM}"  >
			</eve:excheckbox>
			<br/>
			<div>市政工程类：</div>
			<eve:excheckbox
				dataInterface="dictionaryService.findTwoDatasForSelect" 
				name="sgxkgclx" width="749px;" clazz="checkbox validate[required]"
				dataParams="TBPRJFUNCTIONDIC:2"  value="${busRecord.PRJFUNCTIONNUM}"  >
			</eve:excheckbox>
			</span>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"> 结构类型</td>
		<td colspan="3">
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" name="sgxkjglx"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.STRUCTQUALTYPES}" style="width:80%"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>工程质量安全监督机构</td>
		<td colspan="3">
			<input type="text" class="tab_text inputBackgroundCcc validate[required]" id="qualSuperOrg" maxlength="22" placeholder="请输工程质量安全监督机构" value="平潭综合实验区城乡建设与交通运输服务中心" readonly="readonly" style="width:80%"/>
		</td>	
	</tr>	
</table>		
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sgxkjbxxTable">
	<tr>
		<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">参建各方责任主体类型</td>
		<td class="tab_width1" style="width: 35%;color: #000; font-weight: bold;text-align: center;">参建各方责任主体名称</td>
		<td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">法定代表人</td>
		<td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 17%;color: #000; font-weight: bold;text-align: center;">项目负责人联系电话</td>
	</tr>
	<c:forEach items="${sgdwList}" var="sgdw" varStatus="s">
	<tr class="sgxkjbxxTr">
		<td style="text-align: center;">施工单位</td>
		<td style="text-align: center;">${sgdw.CORPNAME}</td>
		<td style="text-align: center;">${sgdw.LEGAL_NAME}</td>
		<td style="text-align: center;">${sgdw.PERSONNAME}</td>
		<td style="text-align: center;">${sgdw.PERSONPHONE}</td>
	</tr>
	</c:forEach>
	<c:forEach items="${sjdwList}" var="sjdw" varStatus="s">
	<tr class="sgxkjbxxTr">
		<td style="text-align: center;">设计单位</td>
		<td style="text-align: center;">${sjdw.CORPNAME}</td>
		<td style="text-align: center;">${sjdw.LEGAL_NAME}</td>
		<td style="text-align: center;">${sjdw.PERSONNAME}</td>
		<td style="text-align: center;">${sjdw.PERSONPHONE}</td>
	</tr>
	</c:forEach>
	<c:forEach items="${jldwList}" var="jldw" varStatus="s">
	<tr class="sgxkjbxxTr">
		<td style="text-align: center;">监理单位</td>
		<td style="text-align: center;">${jldw.CORPNAME}</td>
		<td style="text-align: center;">${jldw.LEGAL_NAME}</td>
		<td style="text-align: center;">${jldw.PERSONNAME}</td>
		<td style="text-align: center;">${jldw.PERSONPHONE}</td>
	</tr>
	</c:forEach>
	<c:forEach items="${kcdwList}" var="kcdw" varStatus="s">
	<tr class="sgxkjbxxTr">
		<td style="text-align: center;">勘察单位</td>
		<td style="text-align: center;">${kcdw.CORPNAME}</td>
		<td style="text-align: center;">${kcdw.LEGAL_NAME}</td>
		<td style="text-align: center;">${kcdw.PERSONNAME}</td>
		<td style="text-align: center;">${kcdw.PERSONPHONE}</td>
	</tr>
	</c:forEach>
</table>
<table cellpadding="0" cellspacing="0" class="tab_tk_pro">
	<tr>
		<td class="tab_width"> 申请意见</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="APPLYUNITREMARK" 
			class="eve-textarea validate[],maxSize[512]"
			style="width:80%;height:100px;"  placeholder="请输入申请意见" >${busRecord.APPLYUNITREMARK}
			</textarea>
		</td>				
	</tr>
</table>
