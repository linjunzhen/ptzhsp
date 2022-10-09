<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
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
	
	.textbox{
		width: 280px!important;
		height: 40px!important;
		border: none!important;
	}
	.textbox .textbox-text{
		width: 280px!important;
		height: 40px!important;
		line-height: 40px!important;
		font-size: 16px!important;
		color: #000000!important;
		padding: 0 10px!important;
		box-sizing: border-box!important;
		border: 1px solid #c9deef!important;
	}
	.w838{
		width: 838px!important;
	}
</style>
<script type="text/javascript">

    $(function(){

    });

</script>


<div class="bsbox clearfix" id="zxxx">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>注销信息</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">		 	
			<%-- <tr>				
				<th>注销人：</th>
				<td><input type="text" class="tab_text " maxlength="32"
						   name="BDC_ZXR" value="${busRecord.BDC_ZXR}"/></td>
				<th>注销时间：</th>
				<td><input type="text" class="tab_text validate[]" maxlength="32"
					name="BDC_ZXSJ" value="${busRecord.BDC_ZXSJ}" /></td>		
			</tr>
			<tr>
				<th>注销宗数：</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[]" maxlength="32" name="BDC_ZXZS" id="BDC_ZXZS"
						value="${busRecord.BDC_ZXZS}" disabled="disabled"/>
				</td>
			</tr> --%>
			<tr>
				<th>
					<span class="bscolor1">*</span>
					注销原因：
				</th>
				<td colspan="3">
					<div class="">
						<!--<input type="text" class="tab_text validate[required]" maxlength="128" name="ZXYY"
							value="${busRecord.ZXYY}" style="width:72%;" />
						 <input type="button" class="eflowbutton" value="意见模板"
							onclick="AppUtil.cyyjmbSelector('dyqzxyy','ZXYY');">  -->
						<eve:eveselect clazz="tab_text w838 validate[required]" dataParams="DYZXYY" 
								dataInterface="dictionaryService.findDatasForSelect" 
								defaultEmptyText="====选择注销原因====" name="ZXYY" value="${busRecord.ZXYY}" >
						</eve:eveselect>
					</div>
				</td>
			</tr> 
		 	<tr>
				<th>
					<span class="bscolor1">*</span>
					注销附记：
				</th>
				<td colspan="3">
					<div class="">
						<!--<input type="text" class="tab_text validate[required]" maxlength="128" name="ZXYY"
							value="${busRecord.ZXYY}" style="width:72%;" />
						 <input type="button" class="eflowbutton" value="意见模板"
							onclick="AppUtil.cyyjmbSelector('dyqzxyy','ZXYY');">  -->
						<eve:eveselect clazz="tab_text w838 validate[required]" dataParams="DYZXFJ" 
								dataInterface="dictionaryService.findDatasForSelect" 
								defaultEmptyText="====选择注销附记====" name="ZXFJ" value="${busRecord.ZXFJ}" >
						</eve:eveselect>
					</div>
				</td>
			</tr> 		
		</table>
	</div>
</div>



<div class="bsbox clearfix" id="zxmx">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>注销明细</span><font color="red">(新增完，请点击保存按钮！)</font></li>
        </ul>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 20px; padding: 0 20px;" value='新增' onclick="showSelectBdcdydacx();"  id="zxmxAdd" />
    </div>
    <div style="padding: 25px 30px">
        <table cellpadding="0" cellspacing="1" class="bstable1" id="zxmxInfo" style="position: relative;">
            <tr id="zxmxInfo_1">
                <td colspan="9" style="padding-right: 0; width: 100%;">
                    <table class="bstable1">  
                        <input name="ZGZQQDSS" type="hidden" value="${busRecord.ZGZQQDSS}" />  
                        <input name="ZWR" type="hidden" value="${busRecord.ZWR}" />                      
						<tr>					
							<th class="tab_width">不动产单元号：</th>
							<td ><input type="text" class="tab_text validate[]" disabled="disabled" 
								name="BDCDYH" value="${busRecord.BDCDYH}"/></td>
							<th class="tab_width">不动产登记证明号：</th>
							<td><input type="text" class="tab_text " disabled="disabled" 
							    name="BDCDJZMH" value="${busRecord.BDCDJZMH}"/> </td>
						</tr>
						<tr>					
							<th class="tab_width">不动产权证号：</th>
							<td colspan="3">
								<input type="text" class="tab_text validate[]"  
								name="BDCQZH" disabled="disabled" value="${busRecord.BDCQZH}" style="width: 850px;"/>
							</td>
						</tr>
						<tr>
							<th class="tab_width">权属状态：</th>
							<td><input type="text" class="tab_text validate[]" disabled="disabled"  
								 name="QSZT"  value="${busRecord.QSZT}"/></td>
							<th class="tab_width">抵押顺位：</th>	
							<td><input type="text" class="tab_text validate[]" disabled="disabled"  
								name="DYSW"  value="${busRecord.DYSW}"/></td>
						</tr>	
						<tr>							
						    <th class="tab_width"><span class="bscolor1">*</span>抵押权人：</th>
						    <td colspan='3'>
							     <input class="tab_text easyui-combobox tab_text_1 validate[required]" name="DYQR" id="DYQR" maxlength="128"
                                       data-options="
                                    prompt : '请输入或者选择抵押权人',
                                    url: 'bdcGyjsjfwzydjController.do?loadBdcBankList',
                                    method: 'get',
                                    valueField : 'DIC_NAME',
                                    textField : 'DIC_NAME',
                                    filter : function(q, row) {
                                        var opts = $(this).combobox('options');
                                        return row[opts.textField].indexOf(q) > -1;
                                    },
                                    onSelect:function(record){                                                   
                                        $('#DYQRXX_IDNO').val(record.DIC_CODE);                                       
                                        $('#DYQRXX_JB').val(record.BANK_CONTECT_NAME);
                                        $('#DYQRXX_JBZJHM').val(record.BANK_CONTECT_CARD);
                                        $('#DYQRXX_JBDH').val(record.BANK_CONTECT_PHONE);
                                    }
                                "/>
							</td>							
						</tr>
						<tr>
	                        <th class="tab_width"><span class="bscolor1">*</span>证照类型：</th>
	                        <td>
	                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
	                                           dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value,'DYQRXX_IDNO');"
	                                           defaultEmptyText="选择证照类型" name="DYQRXX_ZJZL" value="营业执照" >
	                            </eve:eveselect>
	                        </td>
	                        <th class="tab_width"><span class="bscolor1">*</span>证照号码：</th>
	                        <td>
	                            <input type="text" class="tab_text validate[required]" maxlength="128" id="DYQRXX_IDNO" style="float: left;"
	                                   name="DYQRXX_IDNO"  value="${busRecord.DYQRXX_IDNO}"/>
	                        </td>
	                    </tr>
						<tr>
							<th class="tab_width"><span class="bscolor1">*</span>经办人：</th>
							<td><input type="text" class="tab_text validate[required]"  
							    name="DYQRXX_JB" id="DYQRXX_JB" value="${busRecord.DYQRXX_JB}"/></td>							
						    <th class="tab_width"><span class="bscolor1">*</span>经办人电话：</th>
						    <td><input type="text" class="tab_text validate[required]"  
							     name="DYQRXX_JBDH" id="DYQRXX_JBDH" value="${busRecord.DYQRXX_JBDH}"/>
							</td>
							
						</tr>
						<tr>
							<th><span class="bscolor1">*</span>经办人证件类型</th>
							<td>				
							<eve:eveselect clazz="tab_text w280  sel validate[required]" onchange="setValidate(this.value,'DYQRXX_JBZJHM');"
												dataParams="DYZJZL" value="${busRecord.DYQRXX_JBZJLX}"
												dataInterface="dictionaryService.findDatasForSelect" id="DYQRXX_JBZJLX"
												defaultEmptyText="====选择证件类型====" name="DYQRXX_JBZJLX"></eve:eveselect>
							</td>
							<th class="tab_width"><span class="bscolor1">*</span>经办人证件号码：</th>
							<td><input type="text" class="tab_text validate[required]" 
							    name="DYQRXX_JBZJHM" id="DYQRXX_JBZJHM" value="${busRecord.DYQRXX_JBZJHM}"/></td>
						</tr>
						<tr>
						    <th class="tab_width"><span class="bscolor1">*</span>抵押人：</th>
							<td><input type="text" class="tab_text validate[required]" 
								name="DYR"  value="${busRecord.DYR}"/></td>
							<th class="tab_width"><span class="bscolor1">*</span>抵押人电话号码：</th>
							<td><input type="text" class="tab_text validate[required]"  
								name="DYRXX_DHHM"  value="${busRecord.DYRXX_DHHM}"/>
								<br><span class="bscolor1">(多个人之间用英文逗号","隔开)</span></td>
	                        
						</tr>
						<tr>	
							<th class="tab_width"><span class="bscolor1">*</span>抵押人证件类型：</th>
	                        <td><eve:eveselect clazz="tab_text sel  w280  validate[required]" onchange="setValidate(this.value,'ZJHM');"
												dataParams="DYZJZL" value="${busRecord.ZJLB}"
												dataInterface="dictionaryService.findDatasForSelect" id="ZJLB"
												defaultEmptyText="====选择证件类型====" name="ZJLB"></eve:eveselect></td>					    
							<th class="tab_width"><span class="bscolor1">*</span>抵押人证件号码：</th>
	                        <td><input type="text" class="tab_text validate[required]"  
	                                   name="ZJHM" id="ZJHM" value="${busRecord.ZJHM}"/></td>        
						</tr>
						<tr>
							<th class="tab_width">幢号：</th>
							<td><input type="text" class="tab_text validate[]" disabled="disabled"  
								name="ZH"  value="${busRecord.ZH}"/></td>
							<th class="tab_width">户号：</th>
							<td ><input type="text" class="tab_text validate[]" disabled="disabled"  
								 name="HH"  value="${busRecord.HH}"/></td>	
						</tr>
						<tr>
							<th class="tab_width">抵押不动产类型：</th>
							<td><input type="text" class="tab_text validate[]" disabled="disabled"  
								 name="DYBDCLX"  value="${busRecord.DYBDCLX}"/>
							</td>
							 <th class="tab_width">抵押范围：</th>
							 <td><input type="text" class="tab_text " disabled="disabled"  
							   name="ZJJZWDYFW"  value="${busRecord.ZJJZWDYFW}" /></td>
						</tr>
						<tr>
							<th class="tab_width">债务起始时间：</th>
							<td><input type="text" class="tab_text " disabled="disabled"  
									   name="QLQSSJ" value="${busRecord.QLQSSJ}"/></td>
							<th class="tab_width">债务结束时间：</th>
							<td><input type="text" class="tab_text " disabled="disabled"  
									   name="QLJSSJ" value="${busRecord.QLJSSJ}"/></td>
						</tr>		
						<tr>
							<th class="tab_width">被担保主债权数额：</th>
							<td><input type="text" class="tab_text " disabled="disabled"  
								 name="BDBZZQSE" value="${busRecord.BDBZZQSE}"/></td>
							<th class="tab_width">最高债权数额：</th>
							<td><input type="text" class="tab_text " disabled="disabled"  
							    name="ZGZQSE" value="${busRecord.ZGZQSE}"/></td>
						</tr>
						<tr>
						    <th class="tab_width">坐落：</th>
							<td ><input type="text" class="tab_text " disabled="disabled"
									   name="ZJJZWZL"  value="${busRecord.ZJJZWZL}"/></td>
						    <th class="tab_width">担保范围：</th>
							<td ><input type="text" class="tab_text "  maxlength="1000" 
							   name="DBFW"  value="${busRecord.DBFW}"/></td>
						</tr>
						<tr>						   							
						    <th class="tab_width">产权状态:</th>
							<td colspan='3'><input type="text" class="tab_text validate[]"  disabled="disabled" 
								name="CQZT" value="${busRecord.CQZT}" style="width: 850px;" /></td>
						</tr>
	                    <tr>
	                        <th class="tab_width">抵押方式：</th>
	                        <td colspan="3"><input type="text" class="tab_text " disabled="disabled"  
	                                   name="DYFS" value="${busRecord.DYFS}"  style="width: 850px;" /></td>
	                    </tr>
						<tr>
							<th class="tab_width">附记：</th>
							<td colspan="3"><input type="text" class="tab_text validate[]" 
								name="FJ" maxlength="500" value="${busRecord.FJ}"  style="width: 850px;"/></td>
						</tr>	
                    </table>
                </td>
                <td colspan="1" style="position: absolute;right: 0;top: 50%;transform: translateY(-50%);">
		 		   <input type="hidden" name="trid" />
				   <input type="button" class="eflowbutton" name="updateZxmxInfoInput" value="保存" onclick="updateZxmxInfo('1');">
				</td>
            </tr>
        </table>
    </div>
   <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="zxmxTable">
		<tr>
			<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">幢号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">户号</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">不动产登记证明号</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">抵押权人</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">抵押人</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>	
	</table>
     
</div>