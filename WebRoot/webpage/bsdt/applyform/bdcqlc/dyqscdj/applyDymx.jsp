<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.system.model.SysUser"%>
<%@ page language="java" import="net.evecom.platform.system.service.SysRoleService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    //判断是否银行人员
    SysUser curUser = AppUtil.getLoginUser();
    SysRoleService sysRoleService = (SysRoleService)AppUtil.getBean("sysRoleService");
    boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"}); 
    request.setAttribute("isBank", isBankRole);
%>
<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
.bdcdydacxTrRed{
	color:red;
}
</style>
<script type="text/javascript">

$(function(){
	
});

</script>
<div id="dymx">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>抵押明细&nbsp;
			</th>
			<td style="width: 200px;">		
			    <c:if test="${isBank eq 'false'}">		
				    <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcdydacx();">不动产抵押档案查询</a>
				</c:if>
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcdaxxcx();"  id="dymxAdd">查 询</a>
			</td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="dymxTable">
		<tr>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">序号</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">幢号</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">户号</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产权证号</td>
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">产权状态</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>
		
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="dymxInfo">
		
		<tr id="dymxInfo_1">
			<td>
				<table class="tab_tk_pro2">				
					<tr>					
						<td class="tab_width">不动产权证号：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]"  style="width: 500px;"
							name="BDCQZH" maxlength="100" value="${busRecord.BDCQZH}"/>
						</td>
					
					</tr>
					<tr>					
						<td class="tab_width">不动产单元号：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="BDCDYH" maxlength="40" value="${busRecord.BDCDYH}"/>
						</td>
						<td class="tab_width">产权状态：</td>
						<td>
							<input type="text" class="tab_text validate[]" 
							name="CQZT" maxlength="10" value="${busRecord.CQZT}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">权属状态：</td>
						<td >
							<eve:eveselect clazz="tab_text validate[]" dataParams="DYQSZT"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择权属状态" name="QSZT"  value="${busRecord.QSZT}">
							</eve:eveselect>
						</td>
						<td class="tab_width">抵押不动产类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="dybdclx"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择抵押不动产类型" name="DYBDCLX"  value="${busRecord.DYBDCLX}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">抵押顺位：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="DYSW" maxlength="32" value="${busRecord.DYSW}"/>
						</td>
						<td class="tab_width">幢号：</td>
						<td>
							<input type="text" class="tab_text validate[]" 
							name="ZH" maxlength="32" value="${busRecord.ZH}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">户号：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="HH" maxlength="32" value="${busRecord.HH}"/>
						</td>
						<td class="tab_width">建筑面积：</td>
						<td>
							<input type="text" class="tab_text validate[]"   onblur="onlyNumber(this);setTotlaJzmj()"
							name="JZMJ" maxlength="32" value="${busRecord.JZMJ}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">宗地面积：</td>
						<td >
							<input type="text" class="tab_text validate[]"  onblur="onlyNumber(this);setTotlaZdmj()"
							name="ZDMJ" maxlength="32" value="${busRecord.ZDMJ}"/>
						</td>
						<td class="tab_width">分摊土地面积：</td>
						<td>
							<input type="text" class="tab_text validate[]"  onblur="onlyNumber(this);setTotlaFttdmj()"
							name="FTTDMJ" maxlength="32" value="${busRecord.FTTDMJ}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">独用土地面积：</td>
						<td  colspan="3">
							<input type="text" class="tab_text validate[]" 
							name="SYQMJ" maxlength="32" value="${busRecord.SYQMJ}"/>
						</td>
					</tr>
					<tr>	
						<td class="tab_width">坐落：</td>
						<td  colspan="3">
							<input type="text" class="tab_text validate[]"   style="width: 73%"
							name="FDZL" maxlength="32" value="${busRecord.FDZL}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">不动产权利人：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="QLRMC" maxlength="128" value="${busRecord.QLRMC}"/>
						</td>
						<td class="tab_width">证件类别：</td>
						<td>
							<input type="text" class="tab_text validate[]" 
							name="ZJLX" maxlength="128" value="${busRecord.ZJLX}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">证件号码：</td>
						<td  colspan="3">
							<input type="text" class="tab_text validate[]" 
							name="ZJHM" maxlength="128" value="${busRecord.ZJHM}"/>
						</td>
					</tr>
					<tr>		
						<td class="tab_width">备注：</td>
						<td  colspan="3">
							<input type="text" class="tab_text validate[]"   style="width: 73%"
							name="BZ" value="${busRecord.BZ}"/>
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
			</td>
			<td>
				<input type="hidden" name="trid" />
				<input type="button" class="eflowbutton" style="display:none" name="deleteDymxInfoInput" value="保存" onclick="updateDymxInfo('1');">
			</td>
		</tr>
	</table>
</div>