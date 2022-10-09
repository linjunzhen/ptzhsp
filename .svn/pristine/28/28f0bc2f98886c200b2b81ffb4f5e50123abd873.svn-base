<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
	
<style type="text/css">
	.textbox{
		height: 40px!important;
		border-color:#c9deef!important;
		
	}
    .textbox .textbox-text{
        font-size:16px;
    }
	.textbox .textbox-prompt{
		margin: 0;
		padding: 0;
		height: 40px;
	}
</style>



<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>办理结果领取方式</span></li>
        </ul>
    </div>
    <div class="bsboxC">
        <table cellpadding="0" cellspacing="1" class="bstable1" style="table-layout: auto;">
            <tr>
                <td colspan="4">
                    <eve:radiogroup fieldname="FINISH_GETTYPE" width="500" defaultvalue="01" typecode="BLJGLQFS" value="${busRecord.FINISH_GETTYPE}"></eve:radiogroup>
                </td>
            </tr>
            <tr id="addressee" style="display: none;">
                <th><span class="bscolor1">*</span> 收件人姓名：</th>
                <td>
                    <input type="text" maxlength="30" name="RESULT_SEND_ADDRESSEE" id="result_send_addressee" class="tab_text validate[required]" value="${busRecord.RESULT_SEND_ADDRESSEE}" />
                </td>
                <th><span class="bscolor1">*</span> 电话号码:</th>
                <td>
                    <input type="text" maxlength="30" name="RESULT_SEND_MOBILE" id="result_send_mobile" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${busRecord.RESULT_SEND_MOBILE}" />
                </td>
            </tr>
            <tr id="addrprov" style="display: none;">
                <th><span class="bscolor1">*</span> 所属省市：</th>
                <td colspan="3">
                    <input name="RESULT_SEND_PROV" id="province" class="easyui-combobox tab_text" 
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);
							   }
							}
	                " value="" />
                    <input name="RESULT_SEND_CITY" id="city" class="easyui-combobox tab_text"
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);
							   }
							}
	                " value="" />
                    <input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox tab_text"
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);
							   }
							}
	                " value="" />
                </td>
            </tr>
            <tr id="addrpostcode" style="display: none;">
				<th><span class="bscolor1">*</span> 邮政编码：</th>
                <td>
                    <input type="text" maxlength="30" name="RESULT_SEND_POSTCODE" id="result_send_postcode" class="tab_text validate[[required],custom[chinaZip]]" value="${busRecord.RESULT_SEND_POSTCODE}" />
                </td>
            </tr>
            <tr id="addr" style="display: none;">
                <th><span class="bscolor1">*</span> 详细地址：</th>
                <td colspan="3">
                    <input type="text" maxlength="126" class="tab_text validate[required]" name="RESULT_SEND_ADDR" id="result_send_addr" style="width: 850px;" value="${busRecord.RESULT_SEND_ADDR}"/>
                </td>
            </tr>
            <tr id="addrremarks" style="display: none;">
                <th><span class="bscolor1">*</span> 邮递备注：</th>
                <td colspan="3">
                    <input type="text" maxlength="126" class="tab_text validate[required]" name="RESULT_SEND_REMARKS" id="result_send_remarks" style="width: 850px;" value="${busRecord.RESULT_SEND_REMARKS}"/>
                </td>
            </tr>
        </table>
    </div>
</div>