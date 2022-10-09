<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
    .eflowbutton {
        background: #3a81d0;
        border: none;
        padding: 0 10px;
        line-height: 26px;
        cursor: pointer;
        height: 26px;
        color: #fff;
        border-radius: 5px;
    }
</style>

<script type="text/javascript">
    $(function() {
    	
		
    });
    
        
    function setValidate(zjlx){
		if(zjlx=="SF"){
			$("#WYYF_QLRZJLX").addClass(",custom[vidcard]");
		}else{
			$("#WYYF_QLRZJLX").removeClass(",custom[vidcard]");
		}
	}
    
    //提交/保存
    function submitFwPowerPeopleInfo(type){

        var flag = validatePowerParams("fwPowerPeopleInfo","required");

        if (!flag) {
            return false;
        }

        if($("#fwPowerPeopleInfoList_blank_tr")){
            $("#fwPowerPeopleInfoList_blank_tr").remove();
        }
        var fwPowerPeopleInfo = FlowUtil.getFormEleData("fwPowerPeopleInfo");
        var trid = $("#fwPowerPeopleInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加房屋所有权(权利人)信息?", function() {
                closeinfo("fwPowerPeopleInfo");
                addinfo("fwPowerPeopleInfo");
                $("#WYYF_ZZCN").val("上述填写内容真实、完整，拟转让房产为本人家庭在福建省辖区范围内的唯一生活用房，如有隐瞒愿负相应法律责任。");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendFwPowPeopleRow(fwPowerPeopleInfo,i,trid);
            art.dialog({
                content : "房屋所有权(权利人)信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr")){
                index = $("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr").length;
                if(index > 0){
                    var trid = $("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendFwPowPeopleRow(fwPowerPeopleInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加房屋所有权(权利人)信息?", function() {
                closeinfo("fwPowerPeopleInfo");
                addinfo("fwPowerPeopleInfo");
                $("#WYYF_ZZCN").val("上述填写内容真实、完整，拟转让房产为本人家庭在福建省辖区范围内的唯一生活用房，如有隐瞒愿负相应法律责任。");
            });
        }
    }
    
    function appendFwPowPeopleRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="fwPowerPeopleInfo_tr" id="fwPowerpeopleinfotr_' + index + '">';
            html += '<input type="hidden" name="fwinfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.WYYF_QLRXM + '</td>';
            html += '<td style="text-align: center;">' + item.WYYF_QLRZJHM + '</td>';
            html += '<td style="text-align: center;">' + item.WYYF_LXDH + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadFwPowPeopleInfo(this,0);" id="djxxFwItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadFwPowPeopleInfo(this,1);" id="djxxFwItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="delFwDjxxTrQsly(this);">删除</a></td>';
            html += '</tr>';
            if(replaceTrid){
                var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
                $("#"+replaceTrid).remove();
                if(prevtrid){
                    $("#"+prevtrid).after(html)
                } else{
                    $("#fwPowerPeopleInfoList").append(html);
                }
            }else{
                $("#fwPowerPeopleInfoList").append(html);
            }
            $("#fwPowerpeopleinfotr_" + index + " input[name='fwinfo']").val(JSON.stringify(item));
            $("#fwPowerPeopleInfo").attr("trid","fwPowerpeopleinfotr_"+index);
        }
    }

    function delFwDjxxTrQsly(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
    }
    
    
     function loadFwPowPeopleInfo(obj,ptype){
        $("#fwPowerPeopleInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#fwPowerPeopleInfo").attr("trid",trid);
        var fwinfo = $(obj).closest("tr").find("input[name='fwinfo']").val();
        var fw = JSON.parse(fwinfo);
        FlowUtil.initFormFieldValue(fw,"fwPowerPeopleInfo");
        if(ptype == "0"){
            //查看
            $("#fwPowerPeopleInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#fwPowerPeopleInfo_btn").attr("style","");
        }
    }

    function getFwPowPeopleJson(){
        var datas = [];
        $("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr").each(function(){
            var fwinfo = $(this).find("input[name='fwinfo']").val();
            datas.push(JSON.parse(fwinfo));
        });
        $("#FWPOWERPEOPLEINFO_JSON").val(JSON.stringify(datas));
        return datas;
    }

    function initFwPowPeople(items){
        if(items){
            if($("#fwPowerPeopleInfoList_blank_tr")){
                $("#fwPowerPeopleInfoList_blank_tr").remove();
            }
            if($("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr")){
                $("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr").remove();
            }
            for(var i=0;i<items.length;i++){
                appendFwPowPeopleRow(items[i],(i+1),null);
            }
            $("#fwPowerPeopleInfo").attr("style","");
            var firstItem = $("#fwPowerPeopleInfoList .fwPowerPeopleInfo_tr")[0];
            var id = $(firstItem).attr("id");
            $("#fwPowerPeopleInfo").attr("trid",id);
            var fwinfo = $(firstItem).find("input[name='fwinfo']").val();
            if(fwinfo){
                var fw = JSON.parse(fwinfo);
                FlowUtil.initFormFieldValue(fw,"fwPowerPeopleInfo");
            }
            $("#fwPowerPeopleInfo_btn").attr("style","display:none;");
        }
    }
    
	
</script>
<!-- 家庭唯一生活用房承诺书信息开始 -->
<div class="bsbox clearfix" id="wyyfcns" style="display:none">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none">
                <span>家庭唯一生活用房承诺书</span>
            </li>
            <li >
                <span style="font-size: 10px;">个人转让自用达5年以上，并且是福建省范围内唯一家庭生活用房取得的所得，免征收个人所得税。个人申请免征个人所得税时，应填报《家庭唯一生活用房承诺书》</span>
            </li>
        </ul>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="保存" onclick="submitFwPowerPeopleInfo('1');"/>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加" onclick="submitFwPowerPeopleInfo('0')"/>
    </div>
    <div id="fwPowerPeopleInfo" trid="">
        <table cellpadding="0" cellspacing="0" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table class="bstable1">
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>转让房屋坐落：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="128" id="WYYF_FWZL" name="WYYF_FWZL"/>
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>房屋所有权人（权利人）姓名：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="30" id="WYYF_QLRXM" name="WYYF_QLRXM" />
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>房屋所有权人（权利人）证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
                                               dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                                               defaultEmptyText="选择证件类型" name="WYYF_QLRZJLX" id="WYYF_QLRZJLX" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>手机号：</th>
                            <td >
                                <input type="text" class="tab_text validate[required]" maxlength="32" id="WYYF_LXDH" name="WYYF_LXDH"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>房屋所有权人（权利人）身份证号码：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="30" id="WYYF_QLRZJHM" name="WYYF_QLRZJHM" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>婚姻状况：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel validate[required]" 
									dataParams="hyzk" 
									dataInterface="dictionaryService.findDatasForSelect" id="WYYF_HYZK"
									defaultEmptyText="==选择婚姻状况==" name="WYYF_HYZK"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">房屋所有权人（权利人）配偶姓名：</th>
                            <td>
                                <input type="text" class="tab_text" maxlength="30" id="WYYF_POXM" name="WYYF_POXM"/>
                            </td>
                            <th class="tab_width">房屋所有权人（权利人）配偶身份证号码：</th>
                            <td>
                                <input type="text" class="tab_text " maxlength="30" id="WYYF_POZJHM" name="WYYF_POZJHM" />
                            </td>
                       </tr>
                       <tr>
                            <th class="tab_width"><font class="tab_color">*</font>本人郑重承诺：</th>
                            <td colspan='3'>
                                <input type="text" style="width: 80%;color: red;" disabled="disabled" class="tab_text validate[required]" maxlength="128" id="WYYF_ZZCN" name="WYYF_ZZCN"
                                       placeholder="" value="上述填写内容真实、完整，拟转让房产为本人家庭在福建省辖区范围内的唯一生活用房，如有隐瞒愿负相应法律责任。"/>
                            </td>
                       </tr>
                        <!-- <tr id="fwPowerPeopleInfo_btn" style="display:none;">
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
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="fwPowerPeopleInfoList">
        <tr>
            <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">房屋所有权(权利人)名称</td>
            <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">房屋所有权(权利人)证件号码</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">房屋所有权(权利人)手机号</td>
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
        </tr>
        <tr id="fwPowerPeopleInfoList_blank_tr">
            <td colspan="5" style="text-align: center;">暂无房屋所有权(权利人信息)，请添加！</td>
        </tr>
    </table>
</div>
<!-- 家庭唯一生活用房承诺书信息结束 -->


