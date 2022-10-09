<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="emsDiv">
    <tr>
        <th colspan="2">办理结果领取方式</th>
    </tr>
    <tr>
        <td colspan="2">
            <eve:radiogroup fieldname="FINISH_GETTYPE" width="500" defaultvalue="01" typecode="BLJGLQFS" value="${busRecord.FINISH_GETTYPE}"></eve:radiogroup>
        </td>
    </tr>
    <tr id="addressee" style="display: none;">
        <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>收件人姓名：</span>
            <input type="text" maxlength="16" class="tab_text validate[required]"
                   class="tab_text" value="${execution.RESULT_SEND_ADDRESSEE}"
                   name="RESULT_SEND_ADDRESSEE" />
        </td>
        <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>电话号码：</span>
            <input type="text" maxlength="11" class="tab_text validate[required,custom[mobilePhoneLoose]]"
                   class="tab_text" value="${execution.RESULT_SEND_MOBILE}"
                   name="RESULT_SEND_MOBILE" />
        </td>
    </tr>
    <tr id="addrprov" style="display: none;">
        <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>所属省市：</span>
            <input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
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
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);
							   }
							}
	                " value="${execution.RESULT_SEND_PROV}" />
            <input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;"
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
	                " value="${execution.RESULT_SEND_CITY}" />
            <input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;"
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
	                " value="${execution.RESULT_SEND_COUNTY}" />
        </td>
        <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>邮政编码：</span>
            <input type="text" maxlength="6" class="tab_text validate[[required],custom[chinaZip]]"
                   class="tab_text" value="${execution.RESULT_SEND_POSTCODE}"
                   name="RESULT_SEND_POSTCODE" />
        </td>
    </tr>
    <tr id="addr" style="display: none;">
        <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>详细地址：</span>
            <textarea rows="5" cols="80" name="RESULT_SEND_ADDR" value="${execution.RESULT_SEND_ADDR}"
                      maxlength="1998" class="validate[required]"></textarea></td>
        </td>
        <td/>
    </tr>
    <tr id="addrremarks" style="display: none;">
        <td><span style="width: 90px;float:left;text-align:right;">邮递备注：</span>
            <textarea rows="5" cols="80" name="RESULT_SEND_REMARKS" value="${execution.RESULT_SEND_REMARKS}"
                      maxlength="1998" class="validate[]"></textarea></td>
        </td>
        <td/>
    </tr>
</table>