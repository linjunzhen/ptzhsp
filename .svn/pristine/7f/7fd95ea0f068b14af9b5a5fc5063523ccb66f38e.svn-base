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
<%--换发抵押权证明登记 --%>
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
</style>

<script type="text/javascript">

    /*-------------------权利人信息相关开始--------------------------*/
    function submitPowerPeopleInfo_Page2(type){
        if($("#powerPeopleInfoList2_blank_tr")){
            $("#powerPeopleInfoList2_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerPeopleInfo2");
        var trid = $("#powerPeopleInfo2").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo2");  
                addinfo('powerPeopleInfo2');
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowPeopleRow_Page2(powerPeopleInfo,i,trid);
            art.dialog({
                content : "权利人信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerPeopleInfoList2 .powerpeopleinfo_tr")){
                index = $("#powerPeopleInfoList2 .powerpeopleinfo_tr").length;
                if(index > 0){
                   var trid = $("#powerPeopleInfoList2 .powerpeopleinfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowPeopleRow_Page2(powerPeopleInfo,index,null);
        }  
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo2");  
                addinfo('powerPeopleInfo2');
            });
        }
    }
    
    function appendPowPeopleRow_Page2(item,index,replaceTrid){        
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="powerpeopleinfo_tr" id="powerpeopleinfo2tr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.QLR_DYQR + '</td>';
            html += '<td style="text-align: center;">' + item.QLR_DYQRZJLX + '</td>';
            html += '<td style="text-align: center;">' + item.QLR_DYQRZJHM + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo_Page2(this,0);">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo_Page2(this,1);">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#powerPeopleInfoList2").append(html);
               }       
            }else{
               $("#powerPeopleInfoList2").append(html);
            }
            $("#powerpeopleinfo2tr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#powerPeopleInfo2").attr("trid","powerpeopleinfo2tr_"+index);
        }
    }
    
    function loadPowPeopleInfo_Page2(obj,ptype){
        //$("#powerPeopleInfo2").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerPeopleInfo2").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        console.info(item);
        FlowUtil.initFormFieldValue(item,"powerPeopleInfo2"); 
        
        $("#QLR_DYQR").combobox({
            "onLoadSuccess":function(){
                $("#QLR_DYQR").combobox("clear"); 
                $("#QLR_DYQR").combobox("setValue",item.QLR_DYQR); 
            }
        });
        
        $("select[name='QLR_DYQRZJLX']").val(item.QLR_DYQRZJLX);
        $("select[name='QLR_LEGALZJLX']").val(item.QLR_LEGALZJLX);
        $("select[name='QLR_DLRZJLX']").val(item.QLR_DLRZJLX);
        
        if(ptype == "0"){
            //查看
            //$("#powerPeopleInfo2_btn").val("添加");
            //closeinfo("powerPeopleInfo2");
            //$("#powerPeopleInfo2_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            //$("#powerPeopleInfo2_btn").val("保存");
            //$("#powerPeopleInfo2_btn").attr("style","");
        }
    }   
    
    function getPowPeopleJson_Page2(){
       var datas = [];
       $("#powerPeopleInfoList2 .powerpeopleinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERPEOPLEINFO_Page2").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowPeople_Page2(items){
       if(items){
           if($("#powerPeopleInfoList2_blank_tr")){
              $("#powerPeopleInfoList2_blank_tr").remove();
           }
           
           if($("#powerPeopleInfoList2 .powerpeopleinfo_tr")){
               $("#powerPeopleInfoList2 .powerpeopleinfo_tr").remove();
           }
           
           for(var i=0;i<items.length;i++){
              appendPowPeopleRow_Page2(items[i],(i+1),null);
           }
           $("#powerPeopleInfo2").attr("style","");
           var firstItem = $("#powerPeopleInfoList2 .powerpeopleinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerPeopleInfo2").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"powerPeopleInfo2");
           }
           //$("#powerPeopleInfo_btn").attr("style","display:none;");
       }
    }
    
    function loadDataPowPeoplePage2(){
        var datastr = $("#POWERPEOPLEINFO_Page2").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowPeople_Page2(items);
        }
    }
    
    function initselectqlr(){
        $("select[name='QLR_DYQRZJLX']").val("统一社会信用代码");
        $("select[name='QLR_LEGALZJLX']").val("身份证");
        $("select[name='QLR_DLRZJLX']").val("身份证");
    }
    
    /*-------------------权利人信息相关结束--------------------------*/

</script>


<input type="hidden" name="POWERPEOPLEINFO_JSON" value='${recordinfo.POWERPEOPLEINFO_JSON}' id="POWERPEOPLEINFO_Page2"/>
<%--不动产抵押信息接口需回传字段 --%>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="DYCODE" value="${recordinfo.DYCODE}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<%--不动产档案信息接口需回传字段 --%>
<%-- <input type="hidden" name="ZDDM" value="${recordinfo.ZDDM}"/>
<input type="hidden" name="FWBM" value="${recordinfo.FWBM}"/>
<input type="hidden" name="DA_YWH" value="${recordinfo.DA_YWH}"/>
<input type="hidden" name="ZXYWH" value="${recordinfo.ZXYWH}"/>
<input type="hidden" name="DA_GLH" value="${recordinfo.DA_GLH}"/> --%>


<div class="tab_height"></div>
<%--权利人开始 --%>
<div id="powerPeopleInfo2" trid="">
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <tr>
	       <th><span style="float:left;margin-left:10px">权利人信息</span>
	           <input type="button" id="powerPeopleInfo2_btn" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitPowerPeopleInfo_Page2('1');"/>
	           <input type="button" id="powerPeopleInfo2_btn" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitPowerPeopleInfo_Page2('0');"/>
	       </th>
	   </tr>
        <tr>
            <td style="padding: 10px"> 
                <table class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>抵押权人：</td>
                        <td colspan="3">
                            <input class="easyui-combobox tab_text_1 validate[required]" name="QLR_DYQR" id="QLR_DYQR"
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
                                onSelect:function(){
                                    var value = $('#QLR_DYQR').combobox('getValue');
                                    setDYQLName(value);
                                    initselectqlr();                                    
                                }                                   
                            "/><span style="width:25px;display:inline-block;text-align:right;">
                                    <img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR_DYQR');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
                              </span>
                              <span style="width:25px;display:inline-block;text-align:right;">
                                    <img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR_DYQR');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
                              </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_DYQRZJHM');"
                            defaultEmptyText="选择证件类型" name="QLR_DYQRZJLX" id="QLR_DYQRZJLX" value='${recordinfo.QLR_DYQRZJLX}'>
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">证件号码：</td>
                        <td>
                          <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_DYQRZJHM" style="float: left;"
                            name="QLR_DYQRZJHM" value='${recordinfo.QLR_DYQRZJHM}' " /><a href="javascript:void(0);" onclick="QLR1_Read(this);" class="eflowbutton">身份证读卡</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">地址：</td>
                        <td colspan='3'> <input type="text" class="tab_text validate[]" maxlength="64" id="QLR_DZ" style="float: left;width:560px"
                            name="QLR_DZ"  value='${recordinfo.QLR_DZ}' " />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">抵押权人性质：</td>
                        <td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DYQRXZ" style="float: left;"
                            name="QLR_DYQRXZ"  value='${recordinfo.QLR_DYQRXZ}' "/>
                        </td>   
                        <td class="tab_width">抵押权人电话：</td>
                        <td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DYQRDH" style="float: left;"
                            name="QLR_DYQRDH"  value='${recordinfo.QLR_DYQRDH}' "/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">电子邮件：</td>
                        <td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DZYJ" style="float: left;"
                            name="QLR_DZYJ"  value='${recordinfo.QLR_DZYJ}' "/>
                        </td>   
                        <td class="tab_width">邮编：</td>
                        <td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_YB" style="float: left;"
                            name="QLR_YB"  value='${recordinfo.QLR_YB}' "/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">法定代表人姓名：</td>
                        <td colspan='3'><input type="text" class="tab_text validate[]" 
                            name="QLR_LEGALNAME" maxlength="32" value="${recordinfo.QLR_LEGALNAME}" style="width:560px"/></td>
                    </tr>
                    <tr>
                        <td class="tab_width">证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_LEGALZJHM');"
                            defaultEmptyText="选择证件类型" name="QLR_LEGALZJLX" id="QLR_LEGALZJLX" value='${recordinfo.QLR_LEGALZJLX}'>
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">证件号码：</td>
                        <td>
                          <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_LEGALZJHM" style="float: left;"
                            name="QLR_LEGALZJHM" value='${recordinfo.QLR_LEGALZJHM}' /><a href="javascript:void(0);" onclick="QLR2_Read(this);" class="eflowbutton">身份证读卡</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">代理人姓名：</td>
                        <td colspan='3'><input type="text" class="tab_text validate[]" 
                            name="QLR_DLRNAME" maxlength="32" value="${recordinfo.QLR_DLRNAME}" style="width:560px"/></td>
                    </tr>
                    <tr>
                        <td class="tab_width">证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_DLRZJHM');"
                            defaultEmptyText="选择证件类型" name="QLR_DLRZJLX" id="QLR_DLRZJLX" value='${recordinfo.QLR_DLRZJLX}'>
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">证件号码：</td>
                        <td>
                          <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_DLRZJHM" style="float: left;"
                            name="QLR_DLRZJHM" value='${recordinfo.QLR_DLRZJHM}' /><a href="javascript:void(0);" onclick="QLR3_Read(this);" class="eflowbutton">身份证读卡</a>
                        </td>
                    </tr>
                    <tr name="bdcqzh_tr" style="display:none;">                    
                       <td class="tab_width">不动产登记证明号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" 
                           name="BDC_SZZH"  style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                           <input type="button" class="eflowbutton disabledButton" id="zsyl2" onclick="bdcHfcqdjDJZMprint2(1);" style="background:red;color:#fff;" value="证书预览" />
                           <input type="button" class="eflowbutton disabledButton" id="qrdb2" onclick="page2Dbcz();" style="background:red;color:#fff;" value="确认登簿" />
						   <input type="button" class="eflowbutton disabledButton" id="zsdy2" onclick="bdcHfcqdjDJZMprint2(2);" style="background:red;color:#fff;" value="证书打印" />
					   </td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">权证标识码：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" 
                           name="BDC_QZBSM" id="BDC_QZBSM2" style="width:60%" placeholder="由内网不动产登记系统完成缮证（打证），通过接口将权证标识码推送至审批平台。"/>
                       </td>                        
                   </tr>
                   <tr name="bdcczr_tr" style="display:none;">                    
                       <td class="tab_width">操作人：</td>
                       <td>
                       <input type="text" class="tab_text validate[]" 
                           name="POWERPEOPLEMOBILE" maxlength="11"/>
                       </td>
                       <td class="tab_width">操作时间：</td>
                       <td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
                           name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
                   </tr>
                    <tr>
                        <td class="tab_width">备注：</td>
                        <td colspan='3'><input type="text" class="tab_text validate[]" style="width:72%;"
                            name="QLR_REMARK" maxlength="64" value="${recordinfo.QLR_REMARK}"/></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerPeopleInfoList2">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">抵押权人 </td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">证件类型</td>
        <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerPeopleInfoList2_blank_tr">
       <td colspan="5" style="text-align: center;"">暂无权利人信息，请添加！</td>
    </tr>
</table>
<%--权利人结束 --%>

<%--权属来源开始 --%>
<div class="tab_height"></div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro2" id="qslyInfo">
	<tr>
		<th>
		      <span style="float:left;margin-left:10px">权属来源</span><input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="不动产抵押档案查询" onclick="showSelectBdcdydacx();"/>
		</th>
	</tr>
	<tr>
		<td style="padding: 10px;">
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">不动产单元号：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width:300px;" 
						name="BDCDYH" maxlength="64" value="${recordinfo.BDCDYH}"/>
					</td>
					<td class="tab_width">原不动产证明号：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width: 300px;" 
						name="BDCDJZMH" maxlength="64" value="${recordinfo.BDCDJZMH}"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">不动产权证号：</td>
					<td >
						<input type="text" class="tab_text validate[]" style="width: 300px;" 
						name="BDCQZH" maxlength="64" value="${recordinfo.BDCQZH}" />
					</td>
					<td class="tab_width">产权状态：</td>
					<td>
                        <input type="text" class="tab_text validate[]" 
                        name="QSZT" maxlength="32" value="${recordinfo.QSZT}" style="width: 300px;" />
                    </td>
				</tr>
				<tr>
				    <td class="tab_width">坐落：</td>
                    <td colspan="3">
                        <input type="text" class="tab_text validate[]" 
                        name="ZJJZWZL" maxlength="256" value="${recordinfo.ZJJZWZL}" style="width: 72%;" />
                    </td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押权人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYQR" maxlength="64" value="${recordinfo.DYQR}" style="width: 300px;" />
					</td>				
					<td class="tab_width">权属状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QSZT" maxlength="32" value="${recordinfo.QSZT}" style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">证件类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJLB" maxlength="10" value="${recordinfo.ZJLB}" style="width: 300px;" />
					</td>				
					<td class="tab_width">证件号码：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJHM" maxlength="32" value="${recordinfo.ZJHM}" style="width: 300px;" />
					</td>
				</tr>
				<tr>    
                    <td class="tab_width">被担保主债权数额：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" 
                        name="QSLY_BDBZQSE" maxlength="64" value="${recordinfo.QSLY_BDBZQSE}" style="width: 300px;" />
                    </td>               
                    <td class="tab_width">抵押方式：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" 
                        name="QSLY_DYFS" maxlength="32" value="${recordinfo.QSLY_DYFS} " style="width: 300px;" />
                    </td>
                </tr>
                <tr>    
                    <td class="tab_width">债务起始时间：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" 
                        name="QSLY_ZWQSSJ" maxlength="64" value="${recordinfo.QSLY_ZWQSSJ}" style="width: 300px;" />
                    </td>               
                    <td class="tab_width">债务结束时间：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" 
                        name="QSLY_ZWJSSJ" maxlength="32" value="${recordinfo.QSLY_ZWJSSJ} " style="width: 300px;" />
                    </td>
                </tr>
				<tr>	
				    <td class="tab_width">抵押人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYR" maxlength="64" value="${recordinfo.DYR}" style="width: 300px;" />
					</td>				
					<td class="tab_width">抵押人证件号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYRZJH" maxlength="32" value="${recordinfo.DYRZJH} " style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">债务人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZWR" maxlength="64" value="${recordinfo.ZWR} " style="width: 300px;" />
					</td>				
					<td class="tab_width">债务人证件号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZWRZJH" maxlength="32" value="${recordinfo.ZWRZJH} " style="width: 300px;" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%--权属来源结束 --%>

<%--基本信息 --%>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro2" id="jbxxInfo">
	<tr>
		<th><span style="float:left;margin-left:10px">基本信息</span></th>
	</tr>
	<tr>
		<td style="padding: 10px">
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">抵押人：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]"  style="width:490px;" 
						name="JBXX_DYR" maxlength="64" value="${recordinfo.JBXX_DYR}"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押人证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DYQRZJHM');"
						defaultEmptyText="选择证件类型" name="DYRZJLX" id="DYRZJLX" value='${recordinfo.DYZJZL}'>
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="DYQRZJHM" style="float: left;"
						name="DYRZJHM" value='${recordinfo.DYRZJHM}' />
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押物价值：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="DYWJZ" style="float: left;width: 300px;"
						name="DYWJZ" value='${recordinfo.DYWJZ}' />
					</td>
					<td class="tab_width">是否最高额抵押：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setSfzgedy(this.value);"
						defaultEmptyText="选择是否最高额抵押" name="SFZGEDY" id="SFZGEDY"  value="${recordinfo.SFZGEDY}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押方式：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYFS" maxlength="32" value=" ${recordinfo.DYFS}" style="width: 300px;" readonly='readonly'/>
					</td>				
					<td class="tab_width">被担保主债权数额：：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[numberp6plus]" 
						name="BDBZZQSE" maxlength="16" value="${recordinfo.BDBZZQSE} " style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">最高债权额：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[numberp6plus]" 
						name="ZGZQSE" maxlength="16" value=" ${recordinfo.ZGZQSE}" style="width: 300px;" />
					</td>				
					<td class="tab_width">抵押宗数：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[integerplus]" maxlength="16"
						name="DYZS" value="${recordinfo.DYZS}" style="width:300px;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">债务起始时间：</td>
					<td>
						<input type="text" id="QLQSSJ" name="QLQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'QLJSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value='${recordinfo.QLQSSJ}' readonly='readonly'/>
					</td>
					<td class="tab_width">债务结束时间：</td>
					<td>
						<input type="text" id="QLJSSJ" name="QLJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QLQSSJ\')}'})" 
						    class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value="${recordinfo.QLJSSJ}" readonly='readonly'/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'><input type="text" class="tab_text" maxlength="128"
						name="DJYY" value="${recordinfo.DJYY}" style="width:72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">最高债权确定事实：</td>
					<td colspan='3'><input type="text" class="tab_text" maxlength="128"
						name="ZGZQQDSS" value="${recordinfo.ZGZQQDSS}" style="width:72%"  />
					</td>
				</tr>
				<tr>
                    <td class="tab_width">附记：</td>
                    <td colspan='3'><input type="text" class="tab_text" maxlength="128"
                         value="${recordinfo.ZGZQQDSS}" style="width:72%"  />
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">抵押范围：</td>
                    <td colspan='3'><input type="text" class="tab_text" maxlength="128"
                         value="${recordinfo.ZGZQQDSS}" style="width:72%"  />
                    </td>
                </tr>
                <tr>    
                    <td class="tab_width">登簿人：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" placeholder="登簿环节登簿人名。"
                         maxlength="16" style="width: 300px;" />
                    </td>               
                    <td class="tab_width">登记时间：</td>
                    <td>
                        <input type="text" class="tab_text validate[]" maxlength="16" placeholder="登簿环节登记时间。"
                         value="${recordinfo.DYZS}" style="width:300px;"  />
                    </td>
                </tr>
			</table>
		</td>
	</tr>
</table>
<%--基本信息 --%>
    
<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
<!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
<!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
<jsp:include page="./../common/djauditinfo.jsp">
    <jsp:param value="Page2" name="domId" />
    <jsp:param value="djshxx,djjfxx,djfzxx,djdaxx" name="initDomShow" />
</jsp:include>


