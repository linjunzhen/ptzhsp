<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
 
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>抵押权人信息</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">
			    <tr>
                    <td class="tab_width">抵押权人性质：</td>
                    <td colspan="3">
                        <eve:eveselect clazz="tab_text validate[]" dataParams="DYQRXZ"
                        dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="2" onchange="dyqrChangeFun(this.value,true);"
                        defaultEmptyText="选择抵押权人性质" name="DYQRXX_NATURE"  value="${busRecord.DYQRXX_NATURE}">
                        </eve:eveselect>
                    </td>
                </tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>抵押权人：</td>
					<td colspan="3">
						<!--<span style="position:relative;height: 30px; margin-top: 50px;" class="col-xs-6">
							<span style="margin-left:115px;width:18px;overflow:hidden;height: 30px;">
								<style>
									.dyrSelect{
										width:240px;margin-left:-115px;height: 30px;
									}
								</style>
								<eve:eveselect clazz="dyrSelect tab_text validate[]" dataParams="CYYHHMC"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setDyqrxxName();"
								defaultEmptyText="选择抵押权人" name="DYQRXX_NAME_SELECT">
								</eve:eveselect>
							</span>
							<input id="DYQRXX_NAME" class="validate[required]" name="DYQRXX_NAME" value="${busRecord.DYQRXX_NAME}" type="text"
							style="width:220px;position:absolute;left:0px;height:24px;border-left-width: 0px;border-top-width: 0px;
							border-bottom-width: 0px;border-right-width: 0px;margin-top: 3px;margin-left: 3px;"
							 placeholder="请输入或选择抵押权人">
						</span>-->
						<div id="dyr_gr" style="display:none;">
						  <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQRXX_NAME2" style="float: left;"
                            name="DYQRXX_NAME"    value="${busRecord.DYQRXX_NAME}"/>
						</div>
						<div id="dyr_qy" style="">
							<input class="easyui-combobox tab_text_1 validate[required]" style="width:320px;" name="DYQRXX_NAME" id="DYQRXX_NAME" maxlength="128"
							data-options="
							    prompt : '请输入或者选择抵押权人',
								url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
								method: 'get',
								valueField : 'DIC_NAME',
								textField : 'DIC_NAME',
								filter : function(q, row) {
									var opts = $(this).combobox('options');
									return row[opts.textField].indexOf(q) > -1; 
								},
								onSelect:function(record){								
									$('#DYQRXX_IDNO').val(record.DIC_CODE);
									$('#DYQRXX_LEGAL_NAME').val(record.DIC_DESC);
								}									
							"/><span style="width:25px;display:inline-block;text-align:right;">
									<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','DYQRXX_NAME');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
							  </span>
							  <span style="width:25px;display:inline-block;text-align:right;">
									<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('DYQRXX_NAME');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
							  </span>
						  </div>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DYQRXX_IDNO');"
						defaultEmptyText="选择证件类型" name="DYQRXX_IDTYPE" value="统一社会信用代码" >
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQRXX_IDNO" style="float: left;"
						name="DYQRXX_IDNO"    value="${busRecord.DYQRXX_IDNO}"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width"><font id="DYQRXX_LEGAL_NAME_font" class="tab_color">*</font>法定代表人姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" id="DYQRXX_LEGAL_NAME"
						name="DYQRXX_LEGAL_NAME" maxlength="32" value="${busRecord.DYQRXX_LEGAL_NAME}"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="DYQRXX_LEGAL_IDTYPE" id="DYQRXX_LEGAL_IDTYPE"  value="${busRecord.DYQRXX_LEGAL_IDTYPE}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="DYQRXX_LEGAL_IDNO" style="float: left;"
						name="DYQRXX_LEGAL_IDNO" value="${busRecord.DYQRXX_LEGAL_IDNO}" />
					</td>
					<td><a href="javascript:void(0);" onclick="PowLegalRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width"><font id="DYQRXX_AGENT_NAME_font" class="tab_color ">*</font>代理人姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="DYQRXX_AGENT_NAME" maxlength="128" value="${busRecord.DYQRXX_AGENT_NAME}"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="DYQRXX_AGENT_IDTYPE" id="DYQRXX_AGENT_IDTYPE"  value="${busRecord.DYQRXX_AGENT_IDTYPE}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="DYQRXX_AGENT_IDNO" style="float: left;"
						name="DYQRXX_AGENT_IDNO"  value="${busRecord.DYQRXX_AGENT_IDNO}"/>
					</td>
					<td><a href="javascript:void(0);" onclick="PowAgentRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="60"
						name="DYQRXX_REMARK" value="${busRecord.DYQRXX_REMARK}" style="width: 72%;"  />
					</td>
				</tr>
				
				<input type="hidden" name="DYQRXX_FILE_URL" >
				<input type="hidden" name="DYQRXX_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="DYQRXX_FILE_DIV"></div>
						
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:DYQRXXchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
						<a href="javascript:void(0);" onclick="openDyqrxxxFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=DYQRXX_fileListDiv></div>
					</td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerInfo">
	<tr>
		<th>抵押权登记</th>
	</tr>
	<tr id="powerInfo_1">
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width"><font class="tab_color ">*</font>抵押人：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="DYQDJ_NAME" maxlength="256" value="${busRecord.DYQDJ_NAME}" onblur="checkLimitPerson();"/>
						<font class="tab_color">(多个抵押人以顿号区别)</font>
						</td>
					<td class="tab_width"><font class="tab_color ">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择证件类型" name="DYQDJ_IDTYPE" id="DYQDJ_IDTYPE"  value="${busRecord.DYQDJ_IDTYPE}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="256" id="DYQDJ_IDNO" style="float: left;"
						name="DYQDJ_IDNO"  value="${busRecord.DYQDJ_IDNO}" onblur="checkLimitPerson();"/>
					</td>
					<td><a href="javascript:void(0);" onclick="PowDyqdjDyrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td ><input type="text" class="tab_text validate[]" 
						name="DYQDJ_AGENT_NAME" maxlength="128" value="${busRecord.DYQDJ_AGENT_NAME}"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="DYQDJ_AGENT_IDTYPE" id="DYQDJ_AGENT_IDTYPE"  value="${busRecord.DYQDJ_AGENT_IDTYPE}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="DYQDJ_AGENT_IDNO" style="float: left;"
						name="DYQDJ_AGENT_IDNO"  value="${busRecord.DYQDJ_AGENT_IDNO}"/>
					</td>
					<td><a href="javascript:void(0);" onclick="PowDyqdjAgentRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				
				<tr>					
					<td class="tab_width"><font class="tab_color ">*</font>是否最高额抵押：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setSfzgedy(this.value);"
						defaultEmptyText="选择是否最高额抵押" name="DYQDJ_SFZGEDY" id="DYQDJ_SFZGEDY"  value="${busRecord.DYQDJ_SFZGEDY}">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color ">*</font>抵押方式：</td>
					<td >						
						<eve:eveselect clazz="tab_text validate[required]" dataParams="BDCDYFS"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择抵押方式" name="DYQDJ_DYFS" id="DYQDJ_DYFS"  value="${busRecord.DYQDJ_DYFS}">
						</eve:eveselect>
					</td>
				</tr>
				
				<tr>					
					<td class="tab_width"><font class="tab_color ">*</font>被担保主债权数额：</td>
					<td>
						<input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="16"
						name="DYQDJ_BDBZZQSE" value="${busRecord.DYQDJ_BDBZZQSE}" style="width: 72%;"  />
					</td>
					<td class="tab_width"><font class="tab_color ">*</font>最高债权额：</td>
					<td >	
						<input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="16"
						name="DYQDJ_ZGZQE" value="${busRecord.DYQDJ_ZGZQE}" style="width: 72%;"  />
					</td>
				</tr>
				
				<tr>					
					<td class="tab_width">抵押宗数：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[integerplus]" maxlength="16"
						name="DYQDJ_DYZS" value="${busRecord.DYQDJ_DYZS}" style="width: 72%;"  />
					</td>
					<td class="tab_width">抵押宗地总面积：</td>
					<td >	
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYQDJ_DYZSZMJ" value="${busRecord.DYQDJ_DYZSZMJ}" style="width: 72%;"  disabled="disabled"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押建筑总面积：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYQDJ_DYJZZMJ" value="${busRecord.DYQDJ_DYJZZMJ}" style="width: 72%;"  disabled="disabled"/>
					</td>
					<td class="tab_width">抵押分摊土地总面积：</td>
					<td >	
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYQDJ_DYFTTDZMJ" value="${busRecord.DYQDJ_DYFTTDZMJ}" style="width: 72%;" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">抵押独用土地总面积：</td>
					<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="16"
						name="DYQDJ_DYTDMJ" value="${busRecord.DYQDJ_DYTDMJ}" style="width: 72%;" disabled="disabled" />
					</td>
				</tr>
				
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>债务起始时间：</td>
					<td>
						<input type="text" id="DYQDJ_ZWQSSJ" name="DYQDJ_ZWQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'DYQDJ_ZWJSSJ\')}'})" 
							 class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" />
					</td>
					<td class="tab_width"><font class="tab_color ">*</font>债务结束时间：</td>
					<td>
						<input type="text" id="DYQDJ_ZWJSSJ" name="DYQDJ_ZWJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'DYQDJ_ZWQSSJ\')}'})" 
							 class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>担保范围：</td>
					<td ><input type="text" class="tab_text validate[required]" maxlength="128"
						name="DYQDJ_DBFW" value="${busRecord.DYQDJ_DBFW}" style="width: 72%;"  />
					</td>
						<td class="tab_width"><font class="tab_color ">*</font>是否存在禁止或限制转让抵押不动产业务：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择" name="DYQDJ_JZDY" id="DYQDJ_JZDY"  value="${busRecord.DYQDJ_JZDY}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>登记原因：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="DYQDJ_DJYY" value="${busRecord.DYQDJ_DJYY}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">最高债权确定事实：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="128"
						name="DYQDJ_ZGZQQDSS" value="${busRecord.DYQDJ_ZGZQQDSS}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">附记：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="128"
						name="DYQDJ_FJ" value="${busRecord.DYQDJ_FJ}" style="width: 72%;"  />
						<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('bdcdyqscdj','DYQDJ_FJ');">
					</td>
				</tr>
				
				<input type="hidden" name="DYQDJ_FILE_URL" >
				<input type="hidden" name="DYQDJ_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="DYQDJ_FILE_DIV"></div>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:DYQDJchooseCtrl()"><img id="${applyMater.MATER_CODE}"
							src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
						</div>
						<a href="javascript:void(0);" onclick="openDyqdjFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
						</a>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="3">
						<div style="width:100%;" id=DYQDJ_fileListDiv></div>
					</td>
				</tr>
				
			</table>
			<div class="tab_height2"></div>
		</td>
	</tr>
</table>