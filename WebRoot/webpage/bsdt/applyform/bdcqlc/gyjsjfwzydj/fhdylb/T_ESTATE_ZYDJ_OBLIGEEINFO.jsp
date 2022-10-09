<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<style>
.tab_text_1 {
    width: 380px;
    height: 24px;
    line-height: 24px;
    /* padding: 0 5px; */
    padding: 2px 3px 2px 1px;
    border: 1px solid #bbb;
}

.tab_text1 {
		    width: 50%;
		    height: 25px;
		    line-height: 25px;
		    padding: 0 5px;
		    padding: 2px 3px 2px 1px;
		    border: 1px solid #bbb;
		}
</style>

<div id="powerDY_div" style="display:none;">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerDY">
	<tr>
		<th>抵押权登记（概况信息）
		<input type="button" style="float:right" class="eflowbutton" value="查询" onclick="showSelectBdcygdjcx();">
		</th>
	</tr>
	<tr id="powerDYInfo_1">
		<td style="padding: 10px">
			<table class="tab_tk_pro2">
			    <tr>
                    <td class="tab_width"><font class="tab_color">*</font>登记机构：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text validate[required]" maxlength="128" style="float: left;"
                        name="DY_DJJG"/>
                    </td>
                </tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>抵押权人：</td>
					<td colspan='3'>					
						<input class="easyui-combobox tab_text_1" name="DY_DYQR" id="DY_DYQR"
								data-options="
								    prompt : '请输入或者选择抵押权人',
									url: 'dictionaryController.do?load&typeCode=CYYHHMC',
									method: 'get',
									valueField : 'DIC_NAME',
									textField : 'DIC_NAME',
									filter : function(q, row) {
										var opts = $(this).combobox('options');
										return row[opts.textField].indexOf(q) > -1; 
									},	
									onSelect:function(){
										var value = $('#DY_DYQR').combobox('getValue');
										setDYQLName(value);
									}								
								"/><span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','DY_DYQR');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
								  </span>
								  <span style="width:25px;display:inline-block;text-align:right;">
										<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('DY_DYQR');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
								  </span>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL" defaultEmptyValue="营业执照"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_DYQRZJHM');"
						defaultEmptyText="选择证件类型" name="DY_DYQRZJLX" id="DY_DYQRZJLX">
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="DY_DYQRZJHM" style="float: left;"
						name="DY_DYQRZJHM"  />
					</td>
				</tr>
				<tr>
                    <td class="tab_width">债务人：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="30" style="float: left;"
                        name="DY_ZWR" />
                    </td>
                    <td class="tab_width">债务人证件种类：</td>
                    <td>
                      <eve:eveselect clazz="tab_text" dataParams="DYZJZL"
                        dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_ZWRZJHM');"
                        defaultEmptyText="选择证件类型" name="DY_ZWRZJLX" id="DY_ZWRZJLX">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">债务人证件号码：</td>
                    <td>
                      <input type="text" class="tab_text" maxlength="128" id="DY_ZWRZJHM" style="float: left;"
                        name="DY_ZWRZJHM"  />
                    </td>
                    <td></td>
                    <td></td>   
                </tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>抵押人：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="DY_DYR" style="float: left;"
						name="DY_DYR"  />
					</td>
					<td class="tab_width">抵押物价值</td>
					<td><input type="text" class="tab_text" maxlength="128" id="DY_DYWJZ" style="float: left;"
                        name="DY_DYWJZ"  /></td>
				</tr>
				<tr>
					<td class="tab_width">抵押人证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text" dataParams="DYZJZL"
									   dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_DYRZJHM1');"
									   defaultEmptyText="选择证件类型" name="DY_DYRZJLX1" id="DY_DYRZJLX1">
						</eve:eveselect>
					</td>
					<td class="tab_width">抵押人证件号码：</td>
					<td>
						<input type="text" class="tab_text" maxlength="128" id="DY_DYRZJHM1" style="float: left;"
							   name="DY_DYRZJHM1"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>是否最高额抵押：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo" onchange="setSfzgedy(this.value);"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择是否最高额抵押" name="DY_SFZGEDY" id="DY_SFZGEDY">
						</eve:eveselect>
					</td>
					<td class="tab_width"><font class="tab_color" style="display:none;" id="bdbzzqseFont">*</font>被担保主债权数额：</td>
					<td>
					  <input type="text" class="tab_text" maxlength="30"  style="float: left;"
                        name="DY_DBSE"  />
					</td>
				</tr>
				<tr>
                    <td class="tab_width"><font class="tab_color" style="display: none;" id="zgzqeFont">*</font>最高债权额：</td>
                    <td>
                      <input type="text" class="tab_text validate[]" maxlength="30"  style="float: left;"
                        name="DY_ZGZQSE"  />
                    </td>
                    <td class="tab_width">抵押方式：</td>
                    <td>
                      <eve:eveselect clazz="tab_text validate[required]" dataParams="BDCDYFS"
                        dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="0"
                        defaultEmptyText="选择证件类型" name="DY_DYFS" id="DY_DYFS">
                        </eve:eveselect>
                    </td>
                </tr>
				<tr>
					<td class="tab_width">债务起始时间：</td>
					<td>
						<input type="text" id="DY_QLQSSJ" name="DY_QLQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'DY_QLJSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;"/>
					</td>
					<td class="tab_width">债务结束时间：</td>
					<td>
						<input type="text" id="DY_QLJSSJ" name="DY_QLJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'DY_QLQSSJ\')}'})" 
						    class="tab_text Wdate validate[]" maxlength="32" style="width:160px;"/>
					</td>	
				</tr>
				<tr>
					<td class="tab_width" style="display: none;"><font class="tab_color">*</font>主债权合同贷款期限（月）：</td>
					<td style="display: none;">
						<input type="text" class="tab_text validate[]" maxlength="5"  style="float: left;"
							   name="DY_ZZQHTDKQX"  />
					</td>
					<td class="tab_width">抵押宗数：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30"  style="float: left;"
						name="DY_DYZS"  />
					</td>					
				</tr>
				<tr>
				    <td class="tab_width">登记原因：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[]" maxlength="256" id="DY_DYDJYY" style="float: left;"
                        name="DY_DYDJYY" />
                    </td>
				</tr>
				<tr>
                    <td class="tab_width"><font class="tab_color">*</font>最高债权确定事实：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[required]" maxlength="500" id="DY_ZGZQQDSS" style="float: left;"
                        name="DY_ZGZQQDSS" />
                    </td>
                </tr>
                <tr id="dy_bdcdjzmh_tr" style="display:none;">
                    <td class="tab_width">不动产登记证明号：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[]" maxlength="64" id="DY_BDCDJZMH" style="float: left;"
                        name="DY_BDCDJZMH" />
                        <input type="button" id="DY_BDC_QZVIEW" class="eflowbutton" style="display:none;" value="登记证明预览" onclick="bdcDJZMprint(1)"/>
                        <input type="button" id="DY_BDC_QZPRINT" class="eflowbutton" style="display:none;" value="登记证明打印" onclick="bdcDJZMprint(2)"/>
                      <!-- <a href="javascript:void(0);" class="eflowbutton" onclick="viewCertificate(3)" 
							name="dydjzm" id="dydjzm">打印登记证明</a> -->  
                    </td>
                </tr>
                <tr id="dy_bdcqzbsm_tr" style="display:none;">
                    <td class="tab_width"><font class="tab_color">*</font>权证标识码：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[]" maxlength="64" id="DY_BDCQZBSM" style="float: left;"
                        name="DY_BDCQZBSM" />
                    </td>
                </tr>
                <tr id="dy_bdcqzdbr_tr" style="display:none;">
                    <td class="tab_width">登簿人：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" maxlength="500" id="DY_DBR" style="float: left;"
                        name="DY_DBR" readonly="readonly "/> 
                    </td>
                    <td class="tab_width">登记时间：</td>
                    <td>
                        <input type="text" id="DY_DJSJ" name="DY_DJSJ" readonly="readonly"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
                            class="tab_text Wdate validate[]" maxlength="32" style="width:160px;"/>
                    </td>   
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>抵押范围：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[required]" maxlength="500" id="DY_DYFW" style="float: left;"
                        name="DY_DYFW" />
                    </td>
                </tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>关联号：</td>
					<td colspan="3">
						<input type="text" class="tab_text1 validate[required]" maxlength="128" id="DY_GLH" style="float: left;"
							   name="DY_GLH" />
					</td>
				</tr>
                <tr>
                    <td class="tab_width">附记：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text1 validate[]" maxlength="500" id="DY_FJ" style="float: left;"
                        name="DY_FJ" />
                    </td>
                </tr>
			</table>
			<input type="hidden" name="DY_BDCDYH" />
			<input type="hidden" name="DY_YWH" />
			<input type="hidden" name="DY_DYCODE" />
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
	function newDicInfoWin(typeCode,combId){
		$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
			title : "新增",
	        width:"600px",
	        lock: true,
	        resize:false,
	        height:"380px",
	        close: function(){
				$("#"+combId).combobox("reload");
			}
		}, false);
	}
	
	function removeDicInfo(combId){
		var datas = $("#"+combId).combobox("getData");
		var val = $("#"+combId).combobox("getValue");
		var id = "";
		for(var i=0;i<datas.length;i++){
			if(datas[i].DIC_NAME==val){
				id = datas[i].DIC_ID;
				break;
			}
		}
		art.dialog.confirm("您确定要删除该记录吗?", function() {
			var layload = layer.load('正在提交请求中…');
			$.post("dictionaryController.do?multiDel",{
				   selectColNames:id
			    }, function(responseText, status, xhr) {
			    	layer.close(layload);
			    	var resultJson = $.parseJSON(responseText);
					if(resultJson.success == true){
						art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						$("#"+combId).combobox("reload");
						$("#"+combId).combobox("setValue","");
					}else{
						$("#"+combId).combobox("reload");
						art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
			});
		});
	}
</script>

