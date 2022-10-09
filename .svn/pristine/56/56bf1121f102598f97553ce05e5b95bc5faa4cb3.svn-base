<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.haveButtonTitle {
	position: absolute;
	left: 47%;
}
.titleButton {
	float: right;
	margin: 2px 0;
	margin-right: 10px;
	padding: 0 20px !important;
}
</style>

 <script>
    function addinfo(id){
        $("#"+id).attr("style","");
        //$("#"+id+"_btn").attr("style","");
        $("#"+id+" input[type='text']").val('');
        $("#"+id+" select").val('');
    }

    function closeinfo(id){
        //$("#"+id).attr("style","display:none;");
        $("#"+id).attr("trid","");
    }

    function delDjxxTr(obj){
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
    }
    function validatePeopleInfo(ywPeopleInfo){
        var flag = false;
        
        return flag;
    }
   
    
    function submitywPeopleInfo(type){

        var flag = validatePowerParams("ywPeopleInfo","required");

        if (!flag) {
            return false;
        }

        if($("#ywPeopleInfoList_blank_tr")){
            $("#ywPeopleInfoList_blank_tr").remove();
        }
        var ywPeopleInfo = FlowUtil.getFormEleData("ywPeopleInfo");
        var trid = $("#ywPeopleInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加义务人信息?", function() {
                closeinfo("ywPeopleInfo");
                addinfo("ywPeopleInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendYWPeopleRow(ywPeopleInfo,i,trid);
            art.dialog({
                content : "义务人信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#ywPeopleInfoList .ywPeopleInfo_tr")){
                index = $("#ywPeopleInfoList .ywPeopleInfo_tr").length;
                if(index > 0){
                    var trid = $("#ywPeopleInfoList .ywPeopleInfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendYWPeopleRow(ywPeopleInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加义务人信息?", function() {
                closeinfo("ywPeopleInfo");
                addinfo("ywPeopleInfo");
            });
        }



    }
    
    function appendYWPeopleRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="ywPeopleInfo_tr" id="ywPeopleInfotr_' + index + '">';
            html += '<input type="hidden" name="ywiteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.YWPEOPLENAME + '</td>';
            html += '<td style="text-align: center;">' + item.YWPEOPLEIDNUM + '</td>';
            if(item.YWPEOPLESEX == "1"){
               html += '<td style="text-align: center;">男</td>';
            }else if(item.YWPEOPLESEX == "2"){
               html += '<td style="text-align: center;">女</td>';
            }else{
               html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadYwPeopleInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadYwPeopleInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delywDjxxTrQsly(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#ywPeopleInfoList").append(html);
               }       
            }else{
               $("#ywPeopleInfoList").append(html);
            }
            $("#ywPeopleInfotr_" + index + " input[name='ywiteminfo']").val(JSON.stringify(item));
            $("#ywPeopleInfo").attr("trid","ywPeopleInfotr_"+index);
            
            //fillInFwGyqk();
        }
    }

    function delywDjxxTrQsly(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
            /* fillInFwGyqk(); */
        });
    }

    /*查询出有多少条义务人信息，根据义务人信息条数回填房屋公用情况*/
    function fillInFwGyqk() {
        var flowSubmitObj = FlowUtil.getFlowObj();
        if (!(flowSubmitObj && flowSubmitObj.busRecord)) {
            var len = $(".ywPeopleInfo_tr").length;
            if (len == 0) {
                $("[name='FW_GYQK']").val('');
            } else if (len == 1) {
                $("[name='FW_GYQK']").val(0);
            } else {
                $("[name='FW_GYQK']").val(1);
            }
        }
    }
    
    function loadYwPeopleInfo(obj,ptype){
        $("#ywPeopleInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#ywPeopleInfo").attr("trid",trid);
        var ywiteminfo = $(obj).closest("tr").find("input[name='ywiteminfo']").val();
        var item = JSON.parse(ywiteminfo);
        FlowUtil.initFormFieldValue(item,"ywPeopleInfo");
        if(ptype == "0"){
            //查看
            $("#ywPeopleInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#ywPeopleInfo_btn").attr("style","");
        }
    }   
    
    function getYwPeopleJson(){
       var datas = [];
       $("#ywPeopleInfoList .ywPeopleInfo_tr").each(function(){
            var ywiteminfo = $(this).find("input[name='ywiteminfo']").val();
            datas.push(JSON.parse(ywiteminfo));          
       });
       $("#YWPEOPLEINFO_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    function initYwPeople(items){
       if(items){
           if($("#ywPeopleInfoList_blank_tr")){
              $("#ywPeopleInfoList_blank_tr").remove();
           }
           if($("#ywPeopleInfoList.ywPeopleInfo_tr")){
             $("#ywPeopleInfoList.ywPeopleInfo_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendYWPeopleRow(items[i],(i+1),null);
           }
           $("#ywPeopleInfo").attr("style","");
           var firstItem = $("#ywPeopleInfoList .ywPeopleInfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#ywPeopleInfo").attr("trid",id);
           var ywiteminfo = $(firstItem).find("input[name='ywiteminfo']").val();
           if(ywiteminfo){
                var item = JSON.parse(ywiteminfo);
                FlowUtil.initFormFieldValue(item,"ywPeopleInfo");
           }
           $("#ywPeopleInfo_btn").attr("style","display:none;");
       }
    }
    function initYwrInfo(qzinfos,dialog) {
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var qlrs = getywPeopleJson();
	if(qzinfos.length == qlrs.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<qlrs.length;j++){
					if(qzinfos[i].qlr_mc == qlrs[j].YWERPEOPLENAME
						&& qzinfos[i].qlr_zh == qlrs[j].YWERPEOPLEIDNUM){
						qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
						qlrs[j].BDC_CZR = currentUser;
						qlrs[j].BDC_CZSJ = currentTime;
						iflag = iflag + 1;
					}
				}
			}
		}
		initYWPeople(qlrs);
		}
		}
    
    function loadDataYwPeople(){
        var datastr = $("#YWPEOPLEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initYwPeople(items);
        }
    }
 </script>
 
 <div class="tab_height"></div>
 <div id="ywPeopleInfo" trid="">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
       <tr>
           <th><span>义务人信息</span>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitywPeopleInfo('1');"/>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitywPeopleInfo('0')"/>
           </th>
       </tr>    
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2"> 
                   <tr>
                       <td class="tab_width">义务人姓名：</td>
                       <td><input type="text" class="tab_text validate[]" 
                           name="YWPEOPLENAME" maxlength="62"/></td>
                       <td class="tab_width">权利比例：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="YWPEOPLEPRO" maxlength="32"/></td>
                   </tr>
                   <tr>
                       <td class="tab_width">义务人性质：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="QLRXZ"
                           dataInterface="dictionaryService.findDatasForSelect"
                           defaultEmptyText="选择义务人性质" name="YWPEOPLENATURE" id="YWPEOPLENATURE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">性别：</td>
                       <td>
                           <eve:eveselect clazz="tab_text"
                                           dataParams="sex"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择性别" name="YWPEOPLESEX" id="YWPEOPLESEX"></eve:eveselect>
                       </td>
                   </tr>
                   <tr>
                       <td class="tab_width">证件类型：</td>
                       <td>
                         <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect" 
                           defaultEmptyText="选择证件类型" name="YWPEOPLEIDTYPE" id="YWPEOPLEIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">证件号码：</td>
                       <td id="test"><input type="text" class="tab_text validate[]" maxlength="30" id="YWPEOPLEIDNUM" style="float: left;"
                           name="YWPEOPLEIDNUM"  /><a href="javascript:void(0);" onclick="YWPeopleRead(this);" class="eflowbutton">身份证读卡</a></td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">电话</td>
                       <td>
                       <input type="text" class="tab_text validate[,custom[mobilePhoneLoose]]" 
                           name="YWPEOPLEMOBILE" maxlength="11"/>
                       </td>
                       <td class="tab_width">邮政编码：</td>
                       <td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
                           name="YWPEOPLEPOSTCODE" maxlength="6"/></td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">地址：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="YWPEOPLEADDR" maxlength="120"/></td>
                       <td class="tab_width">是否未成年人：</td>
                       <td>
                           <eve:eveselect clazz="tab_text " dataParams="YesOrNo"
                                          dataInterface="dictionaryService.findDatasForSelect"
                                          defaultEmptyValue="0" name="YWPEOPLEISWCNR" id="YWPEOPLEISWCNR">
                           </eve:eveselect>
                       </td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">负责人姓名：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="YWFZLEGALNAME" maxlength="62"/></td>
                       <td class="tab_width">负责人电话：</td>
                       <td><input type="text" class="tab_text validate[,custom[mobilePhoneLoose]]"
                                              name="YWFZLEGALTEL" maxlength="13"/></td>
                   </tr>
                   <tr>
                       <td class="tab_width">证件类型：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect" 
                           defaultEmptyText="选择证件类型" name="YWFZLEGALIDTYPE" id="YWFZLEGALIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">证件号码：</td>
                       <td>
                         <input type="text" class="tab_text validate[]" maxlength="30" id="YWFZLEGALIDNUM" style="float: left;"
                           name="YWFZLEGALIDNUM"  /><a href="javascript:void(0);" onclick="YWDLLegalRead(this);" class="eflowbutton">身份证读卡</a>
                       </td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">代理人姓名：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="YWDLAGENTNAME" maxlength="62"/></td>
                       <td class="tab_width">代理人电话：</td>
                       <td><input type="text" class="tab_text validate[,custom[mobilePhoneLoose]]"
                                              name="YWDLAGENTTEL" maxlength="13"/></td>
                   </tr>
                   <tr>
                       <td class="tab_width">证件类型：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect" 
                           defaultEmptyText="选择证件类型" name="YWDLAGENTIDTYPE" id="YWDLAGENTIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">证件号码：</td>
                       <td>
                         <input type="text" class="tab_text validate[]" maxlength="30" id="YWDLAGENTIDNUM" style="float: left;"
                           name="YWDLAGENTIDNUM"  /><a href="javascript:void(0);" onclick="YWAgentRead(this);" class="eflowbutton">身份证读卡</a>
                       </td>
                   </tr>
                  <%--  <tr id="bdcqzh_tr" style="display:none;">                    
                       <td class="tab_width">缮证证号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" value="${busRecord.BDC_SZZH}"
                           name="BDC_SZZH"  style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                           <input type="button" id="BDC_DBBTN" class="eflowbutton" style="background:red;color:#fff;" value="确认登簿" onclick="BDCQLC_bdcdbcz();"/>
                           <input type="button" id="BDC_QZVIEW" class="eflowbutton" style="display:none;" value="证书预览" onclick="bdcCQZSprint(1)"/>
                           <input type="button" id="BDC_QZPRINT" class="eflowbutton" style="display:none;" value="证书打印" onclick="bdcCQZSprint(2)"/>
                       </td>
                   </tr>
                   <tr id="bdcqzbsm_tr" style="display:none;">                    
                       <td class="tab_width"><font class="tab_color">*</font>权证标识码：</td>
                       <td colspan="3"><input type="text" class="tab_text " value="${busRecord.BDC_QZBSM}"
                           name="BDC_QZBSM" style="width:60%" placeholder="请输入该义务人对应的权证标识码。"/>
                           <input type="button" class="eflowbutton" style="display:none;" id="qzbsmsavebtn" onclick="submitywPeopleInfo('1');"  value="保存" />
                       </td>                        
                   </tr>
                   <tr id="bdcczr_tr" style="display:none;">                    
                       <td class="tab_width">操作人：</td>
                       <td>
                       <input type="text" class="tab_text validate[]" value="${busRecord.BDC_CZR}"
                           name="BDC_CZR" maxlength="11"/>
                       </td>
                       <td class="tab_width">操作时间：</td>
                       <td><input type="text" class="tab_text validate[]" value="${busRecord.BDC_CZSJ}"
                           name="BDC_CZSJ" maxlength="6"/></td>
                   </tr>
                   <tr id="tccns" style="display:none">
                       <td class="tab_width">套次承诺书：</td>
                       <td colspan="3" >
                           <eve:eveselect clazz="tab_text " dataParams="tcCns"
                                          dataInterface="dictionaryService.findDatasForSelect" 
                                          defaultEmptyText="==请选择套次承诺书==" name="YWPERPEOPLECNS" id="YWPERPEOPLECNS" >
                           </eve:eveselect>
                       </td>
                   </tr>   --%>
                   <tr id="ywPeopleInfo_btn" style="display:none;">
                       <th colspan="4"> 
                           <input type="button" class="eflowbutton" value="确定" onclick="submitywPeopleInfo();"/>
                       </th>
                    </tr>
                </table>
            </td>
            <!-- <td>
                <input type="button" class="eflowbutton" name="deleteCurrentYWSourceInfo" value="删除" onclick="deleteYWSourceInfo('1');">
            </td> -->
        </tr>       
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="ywPeopleInfoList">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">义务人名称</td>
        <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">性别</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="ywPeopleInfoList_blank_tr">
       <td colspan="5" style="text-align: center;">暂无义务人信息，请添加！</td>
    </tr>
</table>