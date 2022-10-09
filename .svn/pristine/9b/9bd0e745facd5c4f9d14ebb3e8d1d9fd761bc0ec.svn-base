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
            appendYwPeopleRow(ywPeopleInfo,i,trid);
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
            appendYwPeopleRow(ywPeopleInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加义务人信息?", function() {
                closeinfo("ywPeopleInfo");
                addinfo("ywPeopleInfo");
            });
        }
    }


    function appendYwPeopleRow(item,index,replaceTrid){
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
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="delDjxxTrQsly(this);">删除</a></td>';
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
        }
    }

	function delywDjxxTrQsly(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
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


	function initYwPeople(items) {
		 if(items){
	           if($("#ywPeopleInfoList_blank_tr")){
	              $("#ywPeopleInfoList_blank_tr").remove();
	           }
	           if($("#ywPeopleInfoList.ywPeopleInfo_tr")){
	             $("#ywPeopleInfoList.ywPeopleInfo_tr").remove();
	           }
	           for(var i=0;i<items.length;i++){
	              appendYwPeopleRow(items[i],(i+1),null);
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

	function loadDataYwPeople(){
        var datastr = $("#YWPEOPLEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initYwPeople(items);
        }
    }

    /**
     * 义务人等多条编辑的公用方法
     */
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
</script>

<!-- 义务人信息开始 -->
<div class="bsbox clearfix" id="ywrInfo">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>义务人信息</span></li>
            <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="保存" onclick="submitywPeopleInfo('1');"/>
            <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加" onclick="submitywPeopleInfo('0');"/>
        </ul>
    </div>
    <div id="ywPeopleInfo" trid="">
        <table cellpadding="0" cellspacing="0" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table class="bstable1">
                        <tr>
                            <th class="tab_width">义务人姓名：</th>
                            <td>
                               <input type="text" class="tab_text " maxlength="62" style="float: left;"
                                       name="YWPEOPLENAME" id="YWPEOPLENAME" />
                            </td>
                            <th class="tab_width">权利比例：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="32" style="float: left;"
                                       name="YWPEOPLEPRO" id="YWPEOPLEPRO" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">义务人性质：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="QLRXZ"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择义务人性质" name="YWPEOPLENATURE" id="YWPEOPLENATURE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">性别：</th>
                            <td>
                                 <eve:eveselect clazz="tab_text"
                                               dataParams="sex"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择性别" name="YWPEOPLESEX" id="YWPEOPLESEX"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                                               dataInterface="dictionaryService.findDatasForSelect" 
                                               defaultEmptyValue="SF" name="YWPEOPLEIDTYPE" id="YWPEOPLEIDTYPE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">证件号码：</th>
                            <td id="test"><input type="text" class="tab_text validate[]" maxlength="30" id="YWPEOPLEIDNUM" name="YWPEOPLEIDNUM" style="float: left;"></td>
                        </tr>
                        <tr>
                            <th class="tab_width">电话</th>
                            <td>
                                <input type="text" class="tab_text validate[custom[mobilePhoneLoose]]"
                                       name="YWPEOPLEMOBILE" maxlength="11"/><font class="tab_color">（用于接收短信提醒）</font>
                            </td>
                            <th class="tab_width">邮政编码：</th>
                            <td><input type="text" class="tab_text validate[,custom[chinaZip]]"
                                       name="YWPEOPLEPOSTCODE" maxlength="6"/></td>
                        </tr>
                        <tr>
                           <th class="tab_width">地址：</th>
	                       <td><input type="text" class="tab_text validate[]"
	                           name="YWPEOPLEADDR" maxlength="120"/></td>
	                       <th class="tab_width">是否未成年人：</th>
	                       <td>
	                           <eve:eveselect clazz="tab_text " dataParams="YesOrNo"
	                                          dataInterface="dictionaryService.findDatasForSelect"
	                                          defaultEmptyValue="0" name="YWPEOPLEISWCNR" id="YWPEOPLEISWCNR">
	                           </eve:eveselect>
	                       </td>
                        </tr>
                        <tr>
                             <th class="tab_width">负责人姓名：</th>
	                         <td><input type="text" class="tab_text validate[]"
	                           name="YWFZLEGALNAME" maxlength="62"/></td>
	                         <th class="tab_width">负责人电话：</th>
	                         <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]"
	                                              name="YWFZLEGALTEL" maxlength="11"/></td>
                        </tr>
                        <tr>
                             <th class="tab_width">证件类型：</th>
	                       	 <td>
	                           <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
	                           dataInterface="dictionaryService.findDatasForSelect" 
	                           defaultEmptyText="选择证件类型" name="YWFZLEGALIDTYPE" id="YWFZLEGALIDTYPE" >
	                           </eve:eveselect>
	                         </td>
	                         <th class="tab_width">证件号码：</th>
	                         <td>
	                           <input type="text" class="tab_text validate[]" maxlength="30" id="YWFZLEGALIDNUM" style="float: left;"
	                           name="YWFZLEGALIDNUM"  /><!-- <a href="javascript:void(0);" onclick="YWDLLegalRead(this);" class="eflowbutton">身份证读卡</a> -->
	                         </td>
                        </tr>
                        <tr>
                            <th class="tab_width">代理人姓名：</th>
	                        <td><input type="text" class="tab_text validate[]"
	                           name="YWDLAGENTNAME" maxlength="62"/></td>
	                        <th class="tab_width">代理人电话：</th>
	                        <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]"
	                                              name="YWDLAGENTTEL" maxlength="11"/></td>
                        </tr>
                        <tr>
                            <th class="tab_width">证件类型：</th>
	                        <td>
	                            <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
	                            dataInterface="dictionaryService.findDatasForSelect"
	                            defaultEmptyText="选择证件类型" name="YWDLAGENTIDTYPE" id="YWDLAGENTIDTYPE" >
	                            </eve:eveselect>
	                        </td>
	                        <th class="tab_width">证件号码：</th>
	                        <td>
	                          <input type="text" class="tab_text validate[]" maxlength="30" id="YWDLAGENTIDNUM" style="float: left;"
	                           name="YWDLAGENTIDNUM"  />
	                        </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="ywPeopleInfoList">
        <tr>
            <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">义务人名称</td>
            <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
            <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">性别</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
        </tr>
        <tr id="ywPeopleInfoList_blank_tr">
            <td colspan="7" style="text-align: center;">暂无义务人信息，请添加！</td>
        </tr>
    </table>
</div>