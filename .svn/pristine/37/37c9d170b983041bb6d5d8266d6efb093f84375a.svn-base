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
    
    /**
     * 权属来源权利人等多条编辑的公用方法
     */
    function addinfo(id){
        $("#"+id).attr("style","");
        //$("#"+id+"_btn").attr("style","");
        $("#"+id+" input[type='text']").val('');
        $("#"+id+" input[type='hidden']").val('');
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
    
    function validatePowerPeopleInfo(powerPeopleInfo){
        var flag = false;
        
        return flag;
    }
    
    function submitPowerPeopleInfo(type){
    	var flag = true;
		//验证权利人姓名是否输入	   
	   /*  var POWERPEOPLENAME = $("#qlr_gr input[name='POWERPEOPLENAME']").val();
		var POWERPEOPLENAME2 = $("#qlr_qy input[name='POWERPEOPLENAME']").val();
	    if((POWERPEOPLENAME==null || POWERPEOPLENAME=="" || POWERPEOPLENAME==undefined ) 
		   && (POWERPEOPLENAME2==null || POWERPEOPLENAME2=="" || POWERPEOPLENAME2==undefined )){
			art.dialog({
                content : "请填写权利人姓名",
                icon : "warning",
                ok: true
            })
            return ;
		}else{
			flag = validatePowerParams("powerPeopleInfo","required");
		}  */
		flag = validatePowerParams("powerPeopleInfo","required");
        if (!flag) {
            return false;
        }

        if($("#powerPeopleInfoList_blank_tr")){
            $("#powerPeopleInfoList_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerPeopleInfo");
         //判断权利人性质
       /*  if(powerPeopleInfo.POWERPEOPLENATURE!='1'){//非个人
        	powerPeopleInfo.POWERPEOPLENAME = $("#qlr_qy input[name='POWERPEOPLENAME']").val();
        }else{
        	powerPeopleInfo.POWERPEOPLENAME = $("#qlr_gr input[name='POWERPEOPLENAME']").val();
        } */
        var trid = $("#powerPeopleInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo");
                addinfo("powerPeopleInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowPeopleRow(powerPeopleInfo,i,trid);
            art.dialog({
                content : "权利人信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerPeopleInfoList .powerpeopleinfo_tr")){
                index = $("#powerPeopleInfoList .powerpeopleinfo_tr").length;
                if(index > 0){
                    var trid = $("#powerPeopleInfoList .powerpeopleinfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowPeopleRow(powerPeopleInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo");
                addinfo("powerPeopleInfo");
            });
        }



    }
    
    function appendPowPeopleRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="powerpeopleinfo_tr" id="powerpeopleinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.POWERPEOPLENAME + '</td>';
            html += '<td style="text-align: center;">' + item.POWERPEOPLEIDNUM + '</td>';
            if(item.POWERPEOPLESEX == "1"){
               html += '<td style="text-align: center;">男</td>';
            }else if(item.POWERPEOPLESEX == "2"){
               html += '<td style="text-align: center;">女</td>';
            }else{
               html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTrQsly(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#powerPeopleInfoList").append(html);
               }       
            }else{
               $("#powerPeopleInfoList").append(html);
            }
            $("#powerpeopleinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#powerPeopleInfo").attr("trid","powerpeopleinfotr_"+index);

            fillInFwGyqk();
        }
    }

    function delDjxxTrQsly(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
            fillInFwGyqk();
        });
    }

    /*查询出有多少条权利人信息，根据权利人信息条数回填房屋公用情况*/
    function fillInFwGyqk() {
        var flowSubmitObj = FlowUtil.getFlowObj();
        if (!(flowSubmitObj && flowSubmitObj.busRecord)) {
            var len = $(".powerpeopleinfo_tr").length;
            if (len == 0) {
                $("[name='FW_GYQK']").val('');
            } else if (len == 1) {
                $("[name='FW_GYQK']").val(0);
            } else {
                $("[name='FW_GYQK']").val(1);
            }
        }
    }
    
    function loadPowPeopleInfo(obj,ptype){
        $("#powerPeopleInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerPeopleInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"powerPeopleInfo");
        //设置权利人
        qlrChangeFun(item.POWERPEOPLENATURE,"true");
        if(item.POWERPEOPLENATURE!='1'){//非个人
			$("#POWERPEOPLENAME").combobox("setValue", item.POWERPEOPLENAME);
		}
        if(ptype == "0"){
            //查看
            $("#powerPeopleInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#powerPeopleInfo_btn").attr("style","");
        }
    }   
    
    function getPowPeopleJson(){
       var datas = [];
       $("#powerPeopleInfoList .powerpeopleinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERPEOPLEINFO_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowPeopleInfo(items){
       if(items){
           if($("#powerPeopleInfoList_blank_tr")){
              $("#powerPeopleInfoList_blank_tr").remove();
           }
           if($("#powerPeopleInfoList .powerpeopleinfo_tr")){
             $("#powerPeopleInfoList .powerpeopleinfo_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendPowPeopleRow(items[i],(i+1),null);
           }
           $("#powerPeopleInfo").attr("style","");
           var firstItem = $("#powerPeopleInfoList .powerpeopleinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerPeopleInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"powerPeopleInfo");
                //设置权利人
		        qlrChangeFun(item.POWERPEOPLENATURE,"true");
		        if(item.POWERPEOPLENATURE!='1'){//非个人
					$("#POWERPEOPLENAME").combobox("setValue", item.POWERPEOPLENAME);
				}
           }
           $("#powerPeopleInfo_btn").attr("style","display:none;");
       }
    }
    
    function loadDataPowPeople(){
        var datastr = $("#POWERPEOPLEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowPeopleInfo(items);
        }
    }
 </script>
 
 <div class="tab_height"></div>
 <div id="powerPeopleInfo" trid="">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
       <tr>
           <th><span>权利人信息</span>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitPowerPeopleInfo('1');"/>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitPowerPeopleInfo('0')"/>
           </th>
       </tr>    
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2"> 
                   <tr>
                       <td class="tab_width"><font class="tab_color">*</font>权利人姓名：</td>
                      <%--  <td>
                       		<div id="qlr_gr" style="display:none;">
                              		 <input type="text" class="tab_text validate[]" maxlength="128" id="POWERPEOPLENAME2" style="float: left;"
                                      name="POWERPEOPLENAME"   value="${busRecord.POWERPEOPLENAME}"/>
                          		</div>
                          		 <div id="qlr_qy" style="">
                                <input class="easyui-combobox tab_text_1 validate[]" style="width:250px;" name="POWERPEOPLENAME" id="POWERPEOPLENAME" maxlength="128"
                                       data-options="
                                    prompt : '请输入或者选择权利人',
                                    url: 'bdcGyjsjfwzydjController.do?loadBdcBankList',
                                    method: 'get',
                                    valueField : 'DIC_NAME',
                                    textField : 'DIC_NAME',
                                    filter : function(q, row) {
                                        var opts = $(this).combobox('options');
                                        return row[opts.textField].indexOf(q) > -1;
                                    },
                                    onSelect:function(record){
                                        $('#POWERPEOPLEIDNUM').val(record.DIC_CODE);
                                        $('#POWLEGALNAME').val(record.BANK_LEGAL_NAME);
                                        $('#POWLEGALIDNUM').val(record.BANK_LEGAL_CARD);
                                        $('#POWLEGALTEL').val(record.BANK_LEGAL_PHONE);
                                        $('#POWAGENTNAME').val(record.BANK_CONTECT_NAME);
                                        $('#POWAGENTIDNUM').val(record.BANK_CONTECT_CARD);
                                        $('#POWAGENTTEL').val(record.BANK_CONTECT_PHONE);
                                    }
                                "/>
                            </div>
                       </td>--%>
                       <td><input type="text" class="tab_text validate[required]"
                           name="POWERPEOPLENAME" maxlength="128" /></td>
                       <td class="tab_width"><font class="tab_color" id="qlblFont" style="display:none">*</font>权利比例：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="POWERPEOPLEPRO" maxlength="32"/></td> 
                           
                     
                   </tr>
                   <tr>
                       <td class="tab_width"><font class="tab_color">*</font>权利人性质：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[required]" dataParams="QLRXZ"
                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="2"  onchange="qlrChangeFun(this.value,true);"
                           defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">性别：</td>
                       <td>
                           <eve:eveselect clazz="tab_text"
                                           dataParams="sex"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择性别" name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
                       </td>
                   </tr>
                   <tr>
                       <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                       <td>
                         <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect"
                           defaultEmptyText="选择证件类型" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
                       <td id="test"><input type="text" class="tab_text validate[required]" maxlength="30" id="POWERPEOPLEIDNUM" style="float: left;"
                           name="POWERPEOPLEIDNUM"  /><a href="javascript:void(0);" onclick="PowerPeopleRead(this);" class="eflowbutton">身份证读卡</a></td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">电话</td>
                       <td>
                       <input type="text" class="tab_text validate[]" 
                           name="POWERPEOPLEMOBILE" maxlength="11"/>
                       </td>
                       <td class="tab_width">邮政编码：</td>
                       <td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
                           name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">地址：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="POWERPEOPLEADDR" maxlength="256" style="width:300px;"/></td>
                       <td class="tab_width">电子邮件：</td>
                       <td> <input type="text" class="tab_text validate[]"
                                                   name="POWERPEOPLEEMAIL" maxlength="32"/>
                       </td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">法定代表人姓名：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="POWLEGALNAME" id="POWLEGALNAME" maxlength="62"/></td>
                       <td class="tab_width">法定代表人电话：</td>
                       <td><input type="text" class="tab_text validate[]"
                                 id="POWLEGALTEL"   name="POWLEGALTEL" maxlength="13"/></td>
                   </tr>
                   <tr>
                       <td class="tab_width">证件类型：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'POWLEGALIDNUM');"
                           defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">证件号码：</td>
                       <td>
                         <input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM" style="float: left;"
                           name="POWLEGALIDNUM"  /><a href="javascript:void(0);" onclick="PowLegalRead(this);" class="eflowbutton">身份证读卡</a>
                       </td>
                   </tr>
                   <tr>                    
                       <td class="tab_width">代理人姓名：</td>
                       <td><input type="text" class="tab_text validate[]"
                           name="POWAGENTNAME"  id="POWAGENTNAME" maxlength="62"/></td>
                       <td class="tab_width">代理人电话：</td>
                       <td><input type="text" class="tab_text validate[]"
                                              name="POWAGENTTEL" id="POWAGENTTEL" maxlength="13"/></td>
                   </tr>
                   <tr>
                       <td class="tab_width">证件类型：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                           dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'POWAGENTIDNUM');"
                           defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">证件号码：</td>
                       <td>
                         <input type="text" class="tab_text validate[]" maxlength="30" id="POWAGENTIDNUM" style="float: left;"
                           name="POWAGENTIDNUM"  /><a href="javascript:void(0);" onclick="PowAgentRead(this);" class="eflowbutton">身份证读卡</a>
                       </td>
                   </tr>
                   <tr id="bdcqzh_tr" style="display:none;">                    
                       <td class="tab_width">缮证证号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" value="${busRecord.BDC_SZZH}"
                           name="BDC_SZZH"  style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                           <input type="button" id="BDC_DBBTN" class="eflowbutton" style="background:red;color:#fff;" value="确认登簿" onclick="BDCQLC_bdcdb();"/>
                           <input type="button" id="BDC_QZVIEW" class="eflowbutton" style="display:none;" value="证书预览" onclick="bdcqlcCQZSprint(1)"/>
                           <input type="button" id="BDC_QZPRINT" class="eflowbutton" style="display:none;" value="证书打印" onclick="bdcqlcCQZSprint(2)"/>
                       </td>
                   </tr>
                   <tr id="bdcqzbsm_tr" style="display:none;">                    
                       <td class="tab_width"><font class="tab_color">*</font>权证标识码：</td>
                       <td colspan="3"><input type="text" class="tab_text " value="${busRecord.BDC_QZBSM}"
                           name="BDC_QZBSM" style="width:60%" placeholder="请输入该权利人对应的权证标识码。"/>
                           <input type="button" class="eflowbutton" style="display:none;" id="qzbsmsavebtn" onclick="submitPowerPeopleInfo('1');"  value="保存" />
                           <!-- <button class="eflowbutton" style="display:none;" id="qzbsmsavebtn" onclick="submitPowerPeopleInfo('1');">保存</button> -->
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
                   <tr>
                       <td class="tab_width">备注：</td>
                       <td colspan="3" class="tab_width"><input type="text" class="tab_text" 
                           name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 60%;"  />
                       </td>
                   </tr>
                   <!-- <tr id="powerPeopleInfo_btn" style="display:none;">
                       <th colspan="4"> 
                           <input type="button" class="eflowbutton" value="确定" onclick="submitPowerPeopleInfo();"/>
                       </th>
                    </tr> -->
                </table>
            </td>
            <!-- <td>
                <input type="button" class="eflowbutton" name="deleteCurrentPowerSourceInfo" value="删除" onclick="deletePowerSourceInfo('1');">
            </td> -->
        </tr>       
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerPeopleInfoList">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人名称</td>
        <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">性别</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerPeopleInfoList_blank_tr">
       <td colspan="5" style="text-align: center;">暂无权利人信息，请添加！</td>
    </tr>
</table>