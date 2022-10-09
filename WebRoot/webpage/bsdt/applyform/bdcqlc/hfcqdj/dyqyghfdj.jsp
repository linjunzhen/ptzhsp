<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcQlcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    BdcQlcApplyService bdcQlcApplyService = (BdcQlcApplyService)AppUtil.getBean("bdcQlcApplyService");
    String ywId = request.getParameter("BDC_SUB_YWID");
    String hftype = request.getParameter("hftype");
    String itemcode = request.getParameter("ITEM_CODE");
    if(ywId != null && hftype != null){
        Map<String,Object> record = bdcQlcApplyService.getSubRecordInfo(itemcode,hftype,ywId);
        request.setAttribute("recordinfo", record);
    }
%>

<%--换发抵押权预告登记证明--%>

<%--权属来源不动产预告信息接口需回传字段--%>
<input type="hidden" name="BDCDYH" value="${recordinfo.BDCDYH}"/>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<%--
<input type="hidden" name="YG_YWH" value="${recordinfo.YG_YWH}"/>
<input type="hidden" name="YG_GLH" value="${recordinfo.YG_GLH}"/>
--%>
<div class="tab_height"></div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="qslyList">
    <tr>
       <th colspan="8"><span style="float:left;margin-left:10px">权属来源</span></th>
   </tr>
<%--    <tr>--%>
<%--        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>--%>
<%--        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人</td>--%>
<%--        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">证件号</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利开始时间</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利结束时间</td>--%>
<%--        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">来源关联号</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>--%>
<%--    </tr>--%>
<%--    <tr id="qslyList_blank_tr">--%>
<%--       <td colspan="8" style="text-align: center;"">暂无权属来源信息，请添加！</td>--%>
<%--    </tr>--%>
</table>
<div id="qsly">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
		   <td style="padding: 10px;">
		       <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
				    <tr>
				        <td class="tab_width">不动产单元号：</td>
				            <td>
				                <input type="text" class="tab_text" maxlength="30" style="float: left;"
				            name="QS_DYH"  value="${recordinfo.QS_DYH}"  />
				        </td>
				        <td class="tab_width">原不动产证明号：</td>
				        <td>
				          <input type="text" class="tab_text" maxlength="40" style="float: left;"
				            name="QS_CQZH"  value="${recordinfo.QS_CQZH}"  readonly='readonly'/>
				        </td>
				    </tr>   
				    <tr>
				        <td class="tab_width">权利人：</td>
				            <td>
				                <input type="text" class="tab_text" maxlength="30" style="float: left;"
				            name="QS_QLR_MC"  value="${recordinfo.QS_QLR_MC}"  />
				        </td>
				        <td class="tab_width">权属状态：</td>
				        <td>
				          <eve:eveselect clazz="tab_text  " dataParams="DYQSZT"
				                dataInterface="dictionaryService.findDatasForSelect" 
				                defaultEmptyText="选择权属状态" name="QS_QSZT" id="QS_QSZT" value="${recordinfo.QS_QSZT}"   > 
				                </eve:eveselect>
				        </td>
				    </tr>
				    <tr>
				        <td class="tab_width"> 证件类型：</td>
				            <td>
				                <eve:eveselect clazz="tab_text  " dataParams="DYZJZL"
				                dataInterface="dictionaryService.findDatasForSelect" 
				                defaultEmptyText="选择证件类型" name="QLR_ZJLX" value="${recordinfo.QLR_ZJLX}"  > 
				                </eve:eveselect>
				        </td>
				        <td class="tab_width"> 证件号码：</td>
				        <td>
				          <input type="text" class="tab_text  " maxlength="30" style="float: left;"
				            name="QS_QLR_ZJNO" value="${recordinfo.QS_QLR_ZJNO}"  readonly='readonly'/>
				        </td>
				    </tr>
				    <tr>
				        <td class="tab_width">义务人：</td>
				            <td>
				                <input type="text" class="tab_text" maxlength="40" style="float: left;"
				            name="QS_YWR_MC"  value="${recordinfo.QS_YWR_MC}"  />
				        </td>
				        <td class="tab_width">证件号码：</td>
				        <td>
				          <input type="text" class="tab_text" maxlength="30" id="YWR_ZJNO" style="float: left;"
				            name="QS_YWR_ZJNO"  value="${recordinfo.QS_YWR_ZJNO}"  readonly='readonly'/>
				        </td>
				    </tr>
				    <tr>
				        <td class="tab_width">坐落：</td>
				            <td>
				                <input type="text" class="tab_text" maxlength="120" id="QS_ZL" style="float: left;"
				            name="QS_ZL"  value="${recordinfo.QS_ZL}"  readonly='readonly'/>
				        </td>
				        <td class="tab_width">建筑面积：</td>
				        <td>
				          <input type="text" class="tab_text  " maxlength="30" style="float: left;"
				            name="QS_JZAREA"  value="${recordinfo.QS_JZAREA}"  readonly='readonly'/>
				        </td>
				    </tr>
				    <tr>
				        <td class="tab_width">抵押合同号：</td>
				            <td>
				                <input type="text" class="tab_text" maxlength="30" style="float: left;"
				            name="QS_DYHTH"  value="${recordinfo.QS_DYHTH}" readonly='readonly'/>
				        </td>
				        <td class="tab_width">被担保主债权数额：</td>
				        <td>
				          <input type="text" class="tab_text  " maxlength="30" style="float: left;"
				            name="QS_DBSE"  value="${recordinfo.QS_DBSE}"  readonly='readonly'/>
				        </td>
				    </tr>
				    <tr>
				        <td class="tab_width">债务起始时间：</td>
				        <td>
				            <input type="text" id="QS_BEGIN" name="QS_BEGIN" value="${recordinfo.ZW_BEGIN }"
				                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'QS_END\')}'})" 
				                class="tab_text Wdate" maxlength="60"  style="width: 160px"  />
				        </td>
				        <td class="tab_width">债务结束时间：</td>
				        <td>
				            <input type="text" id="QS_END" name="QS_END" value="${recordinfo.QS_END }"
				                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QS_BEGIN\')}'})" 
				                class="tab_text Wdate" maxlength="60"  style="width: 160px" />
				        </td>
				    </tr>
				</table>
		   </td>
		</tr>
	</table>
</div>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='ysdyqyg'>
	<tr>
		<th><span style="float:left;margin-left:10px">预售抵押权预告</span></th>
	</tr>
	<tr>
	   <td style="padding: 10px;">
	       <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='ysdyqyg'>
	           <tr>
	                <td class="tab_width">预告登记种类：</td>
                    <td colspan="3">
                      <input type="text" class="tab_text" maxlength="30" style="width: 62%" readonly="readonly" value="预购商品房抵押预告登记" />
                    </td>
                 </tr> 
                 <tr>
		             <th colspan="4"><span style="float:left;margin-left:10px">权利人</span></th>
			    </tr>
				<tr>
			        <td class="tab_width"><font class="tab_color">*</font>权利人：</td>
			        <td colspan="3">
			            <input class="easyui-combobox tab_text_1 validate[required]" name="QLR_MC" id="QLR_MC" value="${recordinfo.QLR_MC}"  
			                data-options="
			                    prompt : '请输入或者选择权利人',
			                    url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
			                    method: 'get',
			                    valueField : 'DIC_NAME',
			                    textField : 'DIC_NAME',
			                    filter : function(q, row) {
			                        var opts = $(this).combobox('options');
			                        return row[opts.textField].indexOf(q) > -1; 
			                    },
			                    onSelect:function(){
			                        var value = $('#QLR_MC').combobox('getValue');
			                        setQLRName(value);
			                    }                                   
			                "/><span style="width:25px;display:inline-block;text-align:right;">
			                        <img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="新建权利人">
			                  </span>
			                  <span style="width:25px;display:inline-block;text-align:right;">
			                        <img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="删除选中的权利人">
			                  </span>
			        </td>               
			    </tr>
			    <tr>
			        <td class="tab_width"> 证件类型：</td>
			            <td>
			                <eve:eveselect clazz="tab_text  " dataParams="DYZJZL"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择证件类型" name="QLR_ZJLX" id="QLR_ZJLX" value="${recordinfo.QLR_ZJLX}" > 
			                </eve:eveselect>
			        </td>
			        <td class="tab_width"> 证件号码：</td>
			        <td>
			          <input type="text" class="tab_text  " maxlength="30" id="QLR_ZJNO" style="float: left;"
			            name="QLR_ZJNO"  value="${recordinfo.QLR_ZJNO }" /><!-- <a href="javascript:void(0);" onclick="QLR_Read(this);" class="eflowbutton">身份证读卡</a> -->
			        </td>
			    </tr> 
			    <tr>
                     <th colspan="4"><span style="float:left;margin-left:10px">义务人</span></th>
                </tr>  
			    <tr>
			        <td class="tab_width"><font class="tab_color">*</font>义务人：</td>
			        <td colspan="3"><input type="text" class="tab_text validate[required]" 
			            name="YWR_MC" value="${recordinfo.YWR_MC}" /></td>      
			    </tr>
			    <tr>
			        <td class="tab_width"> 证件类型：</td>
			            <td>
			                <eve:eveselect clazz="tab_text  " dataParams="DocumentType"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择证件类型" name="YWR_ZJLX" id="YWR_ZJLX" value="${recordinfo.YWR_ZJLX}">
			                </eve:eveselect>
			        </td>
			        <td class="tab_width"> 证件号码：</td>
			        <td>
			          <input type="text" class="tab_text  " maxlength="30" id="YWR_ZJNO" style="float: left;"
			            name="YWR_ZJNO"  value="${recordinfo.YWR_ZJNO}" /><a href="javascript:void(0);" onclick="YWR_Read(this);" class="eflowbutton">身份证读卡</a>
			        </td>
			    </tr>   
			    <tr>
			        <td class="tab_width">合同号：</td>
			        <td>
			          <input type="text" class="tab_text validate[]" maxlength="30" id="ZWR_ZJNO" style="float: left;"
			            name="ZWR_ZJNO" value="${recordinfo.ZWR_ZJNO }" />
			        </td>
			        <td class="tab_width">合同类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text" dataParams="DocumentType"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                            defaultEmptyText="选择证件类型" name="ZWR_ZJLX" id="ZWR_ZJLX" value="${recordinfo.ZWR_ZJLX }" >
                            </eve:eveselect>
                    </td>
			    </tr>
			    <tr>
			        <td class="tab_width"><font class="tab_color">*</font>被担保主债权数额：</td>
			        <td>
			          <input type="text" class="tab_text validate[required]" maxlength="30" id="DBSE" style="float: left;"
			            name="DBSE"  value="${recordinfo.DBSE }" readonly='readonly'/>
			        </td>
			        <td class="tab_width"></td>
			        <td></td>               
			    </tr>
			    <tr>
			        <td class="tab_width"><font class="tab_color">*</font>债务起始时间：</td>
			        <td>
			            <input type="text" id="ZW_BEGIN" name="ZW_BEGIN" value="${recordinfo.ZW_BEGIN }"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZW_END\')}'})" 
			                class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px" />
			        </td>
			        <td class="tab_width"><font class="tab_color">*</font>债务结束时间：</td>
			        <td>
			            <input type="text" id="ZW_END" name="ZW_END" value="${recordinfo.ZW_END }"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZW_BEGIN\')}'})" 
			                class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px" />
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
			        <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60" value="${recordinfo.FW_ADDR}"
			            name="FW_ADDR" style="width: 72%;"  readonly='readonly'/>
			        </td>
			    </tr>
			    <tr name="bdcqzh_tr" style="display:none;">                    
                     <td class="tab_width">不动产登记证明号：</td>
                     <td colspan="3"><input type="text" class="tab_text validate[]" value="${recordinfo.BDC_BDCDJZMH}"
                         name="BDC_BDCDJZMH"  style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                         <input type="button" class="eflowbutton disabledButton" id="zsyl4" onclick="bdcHfcqdjDJZMprint4(1);" style="background:red;color:#fff;" value="证书预览" />
                         <input type="button" onclick="page4Dbcz();" id="qrdb4" class="eflowbutton disabledButton" style="background:red;color:#fff;" value="确认登簿" />
						 <input type="button" class="eflowbutton disabledButton" id="zsdy4" onclick="bdcHfcqdjDJZMprint4(2);" style="background:red;color:#fff;" value="证书打印" />
                     </td>                        
                 </tr>
                 <tr>
                     <td class="tab_width">权证标识码：</td>
                     <td colspan="3"><input type="text" class="tab_text validate[]"
                         name="BDC_QZBSM" id="BDC_QZBSM4" style="width:60%" placeholder="由内网不动产登记系统完成缮证（打证），通过接口将权证标识码推送至审批平台。"/>
                     </td>
                 </tr>
                 <tr name="bdcczr_tr" style="display:none;">                    
                     <td class="tab_width">操作人：</td>
                     <td>
                     <input type="text" class="tab_text validate[]" value="${recordinfo.BDC_CZR}"
                         name="BDC_CZR" maxlength="11"/>
                     </td>
                     <td class="tab_width">操作时间：</td>
                     <td><input type="text" name="BDC_CZSJ" 
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'BDC_CZSJ\')}'})" 
                             class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value="${recordinfo.BDC_CZSJ}" /> 
                     </td>
                 </tr>
			    <tr>
			        <td class="tab_width">登记原因：</td>
			        <td colspan="3"><input type="text" class="tab_text" maxlength="60" value="抵押预告换发登记"
			            name="BDC_DJYY" style="width: 72%;" readonly='readonly' />
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">附记：</td>
			        <td colspan="3"><input type="text" class="tab_text" maxlength="60" value="${recordinfo.FJ}"
			            name="FJ" style="width: 72%;" readonly='readonly' />
			        </td>
			    </tr>
			</table>
	   </td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="ygdjinfoList">
    <tr>
         <th colspan="7"><span style="float:left;margin-left:10px">预告登记信息</span></th>
     </tr>
    <tr>
<%--        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>--%>
<%--        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">不动产证明号</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人</td>--%>
<%--        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">建筑面积</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">备注</td>--%>
<%--        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>--%>
<%--    </tr>--%>
<%--    <tr id="ygdjinfoList_blank_tr">--%>
<%--       <td colspan="7" style="text-align: center;"">暂无预告登记信息，请添加！</td>--%>
<%--    </tr>--%>
</table>
<div id="ygdjinfo">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">	    
        <tr>
           <td style="padding: 10px;">
               <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width">不动产单元号：</td>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" style="float: left;"
                            name="QS_DYH"  value="${recordinfo.QS_DYH}"  />
                        </td>
                        <td class="tab_width">不动产证明号：</td>
                        <td>
                          <input type="text" class="tab_text" maxlength="40" style="float: left;"
                            name="QS_CQZH"  value="${recordinfo.QS_CQZH}"  readonly='readonly'/>
                        </td>
                    </tr>   
                    <tr>
                        <td class="tab_width">权利人：</td>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" style="float: left;"
                            name="QS_QLR_MC"  value="${recordinfo.QS_QLR_MC}"  />
                        </td>
                        <td class="tab_width">建筑面积：</td>
                        <td>
                          <input type="text" class="tab_text" maxlength="30" style="float: left;"
                            name="QS_QLR_MC"  value="${recordinfo.QS_QLR_MC}"  />
                        </td>
                    </tr>                    
                    <tr>
                        <td class="tab_width">坐落：</td>
                        <td colspan="3">
                                <input type="text" class="tab_text" maxlength="120" id="QS_ZL" style="float: left;width:62%;"
                            name="QS_ZL"  value="${recordinfo.QS_ZL}"  readonly='readonly'/>
                        </td>                        
                    </tr>  
                    <tr>
                        <td class="tab_width">备注：</td>
                        <td colspan="3">
                          <input type="text" class="tab_text  " maxlength="30" style="float: left;width:62%;"
                            name="QS_JZAREA"  value="${recordinfo.QS_JZAREA}"  readonly='readonly'/>
                        </td>
                    </tr>                  
                </table>
           </td>
        </tr>
    </table>
</div>


<%-- 引入登记审核、缴费信息、发证、归档信息 --%>

<!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
<!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
<jsp:include page="./../common/djauditinfo.jsp">
	<jsp:param value="Page4" name="domId" />
	<jsp:param value="djshxx,djjfxx,djfzxx,djdaxx" name="initDomShow" />
</jsp:include>


