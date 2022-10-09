<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<eve:resources loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<div class="bsbox clearfix">
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="xftable tab_tk_pro1" style="border: 0 dotted #a0a0a0;">
			<tr>
				<td colspan="7" style="text-align: center;">
					<label>其他信息单元</label>
				</td>
			</tr>
			<tr>
				<th>储罐</th>
				<td colspan="6" >
					<table cellpadding="0" cellspacing="0" class="xftable tab_tk_pro1" style="border: 0 dotted #a0a0a0;">
						<tr>
							<th>设置位置</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="SET_LOCATION" />
							</td>
							<th>总容量(m³)</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="CAPACITY" />
							</td>
						</tr>
						<tr>
							<th>设置形式</th>
							<td colspan="3">
							    <eve:excheckbox
									dataInterface="dictionaryService.findDatasForSelect"
									name="SET_TYPE" width="99%;" clazz="checkbox"
									dataParams="TBFCSETTYPEDIC" value="${execution.SET_TYPE}">
								</eve:excheckbox>
							</td>
						</tr>
						<tr>
							<th>储存形式</th>
							<td>
						        <eve:excheckbox
									dataInterface="dictionaryService.findDatasForSelect"
									name="STORAGE_TYPE" width="98%;" clazz="checkbox"
									dataParams="TBFCSTORAGETYPEDIC" value="${execution.STORAGE_TYPE}">
								</eve:excheckbox>
							</td>
							<th>储存物质名称</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="CG_STORAGE_NAME" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th>堆场</th>
				<td colspan="6" >
					<table cellpadding="0" cellspacing="0" class="xftable" style="border: 0 dotted #a0a0a0;">
						<tr>
							<th>储量</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="STORAGE_CAPACITY" />
							</td>
							<th>储存物质名称</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="DC_STORAGE_NAME" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th>建筑保温</th>
				<td colspan="6" >
					<table cellpadding="0" cellspacing="0" class="xftable" style="border: 0 dotted #a0a0a0;">
						<tr>
							<th>材料类别</th>
							<td>
						        <eve:excheckbox
									dataInterface="dictionaryService.findDatasForSelect"
									name="MATERIAL_CATEGORY" width="98%" clazz="checkbox"
									dataParams="TBFCMCDIC" value="${execution.MATERIAL_CATEGORY}">
								</eve:excheckbox>
							</td>
							<th>保温层数</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="INSULATION_LAYER" />
							</td>
						</tr>
						<tr>
							<th>使用性质</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="BW_USE_CHARACTER" />
							</td>
							<th>原有用途</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="BW_ORIGINAL_USE" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th>装修工程</th>
				<td colspan="6" >
					<table cellpadding="0" cellspacing="0" class="xftable" style="border: 0 dotted #a0a0a0;" id="zxgcTable">
						<tr>
							<th>装修部位</th>
							<td colspan="3">
							    <eve:excheckbox
									dataInterface="dictionaryService.findDatasForSelect"
									name="DECORATION_PART" width="98%" clazz="checkbox"
									dataParams="TBFCDPDIC" value="${execution.DECORATION_PART}">
								</eve:excheckbox>
							</td>
						</tr>
						<tr>
							<th>装修面积(㎡)</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="DECORATION_AREA" />
							</td>
							<th>装修层数</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="DECORATION_LAYER"/>
							</td>
						</tr>
						<tr>
							<th>使用性质</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="ZX_USE_CHARACTER"/>
							</td>
							<th>原有用途</th>
							<td>
								<input type="text" maxlength="100" class="tab_text" name="ZX_ORIGINAL_USE" />
							</td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<th>消防设施</th>
				<td colspan="6">
				    <eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect"
						name="FC_FACILITIES" width="98%" clazz="checkbox"
						dataParams="TBFCFACDIC" value="${execution.FC_FACILITIES}">
					</eve:excheckbox>
				</td>
			</tr>
			<tr>
				<th>其他说明情况</th>
				<td colspan="6">
					<textarea id="" class="tab_text " cols="150" name="OTHER_INSTRUCTIONS" style="width:99%;height:100px;"  ></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>