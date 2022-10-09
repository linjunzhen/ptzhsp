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
    function submitPowSourceInfo(type){

        var flag = validatePowerParams("powerSourceInfo","required");

        if (!flag) {
            return false;
        }

        if($("#powerSourceInfoList_blank_tr")){
            $("#powerSourceInfoList_blank_tr").remove();
        }
        var powerSourceInfo = FlowUtil.getFormEleData("powerSourceInfo");
        var trid = $("#powerSourceInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权属来源信息?", function() {
                closeinfo("powerSourceInfo");
                addinfo("powerSourceInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowSourceRow(powerSourceInfo,i,trid);
            art.dialog({
                content : "权属来源信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerSourceInfoList .powersourceinfo_tr")){
                index = $("#powerSourceInfoList .powersourceinfo_tr").length;
                if(index > 0){
                    var trid = $("#powerSourceInfoList .powersourceinfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowSourceRow(powerSourceInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权属来源信息?", function() {
                closeinfo("powerSourceInfo");
                addinfo("powerSourceInfo");
            });
        }
    }

    function appendPowSourceRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="powersourceinfo_tr" id="powersourceinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.QLR + '</td>';
            html += '<td style="text-align: center;">' + item.BDC_QLRZJHM + '</td>';
            if(item.POWERSOURCENATURE == "0"){
                html += '<td style="text-align: center;">土地_出让合同</td>';
            }else if(item.POWERSOURCENATURE == "1"){
                html += '<td style="text-align: center;">土地_划拨决定书</td>';
            }else if(item.POWERSOURCENATURE == "2"){
                html += '<td style="text-align: center;">土地_原土地证</td>';
            }else if(item.POWERSOURCENATURE == "3"){
                html += '<td style="text-align: center;">不动产权证书</td>';
            }else if(item.POWERSOURCENATURE == "4"){
                html += '<td style="text-align: center;">其他批准文件</td>';
            }else if(item.POWERSOURCENATURE == "5"){
                html += '<td style="text-align: center;">初始登记证明号</td>';
            }else if(item.POWERSOURCENATURE == "6"){
                html += '<td style="text-align: center;">安置协议</td>';
            }else{
                html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">' + item.BDC_BDCDYH + '</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCEIDNUM + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
                var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
                $("#"+replaceTrid).remove();
                if(prevtrid){
                    $("#"+prevtrid).after(html)
                } else{
                    $("#powerSourceInfoList").append(html);
                }
            }else{
                $("#powerSourceInfoList").append(html);
            }
            /*若item中没有法定代表人字段，则新增法定代表人，代理人字段*/
            if (!item['BDC_FDDBRXM']) {
                item['BDC_FDDBRXM'] = '';
            }
            if (!item['BDC_FDDBRDH']) {
                item['BDC_FDDBRDH'] = '';
            }
            if (!item['BDC_DLRXM']) {
                item['BDC_DLRXM'] = '';
            }
            if (!item['BDC_DLRDH']) {
                item['BDC_DLRDH'] = '';
            }
            if (!item['BDC_DLJGMC']) {
                item['BDC_DLJGMC'] = '';
            }

            $("#powersourceinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#powerSourceInfo").attr("trid","powersourceinfotr_"+index);
        }
    }

    function loadPowSourceInfo(obj,ptype){
        $("#powerSourceInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerSourceInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"powerSourceInfo");
        if(ptype == "0"){
            //查看
            $("#powerSourceInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#powerSourceInfo_btn").attr("style","");
        }
    }

    function getPowSourceJson(){
        var datas = [];
        $("#powerSourceInfoList .powersourceinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));
        });
        $("#POWERSOURCEINFO_JSON").val(JSON.stringify(datas));
        return datas;
    }

    function initPowSource(items){
        $(".powersourceinfo_tr").each(function (index) {
            $(this).remove();
        });
        $("#powerSourceInfo").attr("trid", "");
        if(items){
            if($("#powerSourceInfoList_blank_tr")){
                $("#powerSourceInfoList_blank_tr").remove();
            }
            for(var i=0;i<items.length;i++){
                appendPowSourceRow(items[i],(i+1),null);
            }
            $("#powerSourceInfo").attr("style","");
            var firstItem = $("#powerSourceInfoList .powersourceinfo_tr")[0];
            var id = $(firstItem).attr("id");
            $("#powerSourceInfo").attr("trid",id);
            var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
            var item = JSON.parse(iteminfo);
            FlowUtil.initFormFieldValue(item,"powerSourceInfo");
            formatDic("POWERSOURCEIDTYPE", item.POWERSOURCEIDTYPE);
            if (item.POWERSOURCE_SDTXLY && item.POWERSOURCE_SDTXLY != "") {
                $("#sdtxly").show();
            } else {
                $("#sdtxly").hide();
            }
            $("#powerSourceInfo_btn").attr("style","display:none;");
        }
    }

    function loadDataPowSource(){
        var datastr = $("#POWERSOURCEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowSource(items);
        }
    }

    /**
     * 权属来源权利人等多条编辑的公用方法
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

    function fillInQsly() {
        $("#powerSourceInfo select , #powerSourceInfo input").each(function () {
            $(this).attr("disabled", false);
        });
        $("#sdtxly").show();
        $("#sdtxFont").show();
        $("input[name='POWERSOURCE_SDTXLY']").addClass(" validate[required]");
    }
	
	/**
	 * 国有建设用地及房屋所有权转移登记查询不动产档案信息
	 */
	function showSelectBdcfwzdcx(){
		var isBank = $("input[name='isBank']").val();
		var url = "bdcApplyController.do?bdcDocInfoSelector&allowCount=1&isAllClo=1";
		if (isBank == 'true') {
			url = "bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0";
		}
		$.dialog.open(url, {
			title : "不动产档案信息查询",
			width:"100%",
			lock: true,
			resize:false,
			height:"100%",
			close: function () {
				var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
				console.log(bdcdaxxcxInfo);
				if(bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1){
					var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
					var result = {
						"isPass":true
					}
					/*抵押、限制、查封状态中的房子可以在二手房交易中读取，并无提示弹窗*/
					/*需要对房子状态进行提示，一手房(分户)提示，二手房（存量）不允许办理，数据不会填*/
					var zydjType = $('input[name="ZYDJ_TYPE"]:checked').val();
 					if (zydjType == '5' || zydjType == '1') {
						if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
							result = {
								"isPass": true,
								"msg": "请注意，该不动产单元号为" + cqzt + "状态",
								"type": "0"
							};
						}
					} else if (zydjType == '4' || zydjType == '6' || zydjType == '3') {
						if (zydjType == '4' || zydjType == '6') {
							if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
								result = {
									"isPass": false,
									"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
									"type": "0"
								};
							}
						} else if (zydjType == '3') {
							if (cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
								result = {
									"isPass": false,
									"msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
									"type": "0"
								};
							}
						}
	
					}
					if(cqzt.indexOf("注销")!=-1 || cqzt.indexOf("无效")!=-1){
						result = {
							"isPass":false,
							"msg":"请注意，该不动产单元号为"+cqzt+"状态，不可受理此登记。",
							"type":"0"
						};
					}
					var info = daxxFillInfoJson(bdcdaxxcxInfo[0]);
					/*创建权属来源信息*/
					var powerSourceItems = daxxFillPowerSource(info);
					console.log(info)
					if(result.isPass == true){
						$('input[name="BDC_BDCDYH"]').val(info.BDCDYH);
						$('input[name="QLR"]').val(info.POWERSOURCE_QLRMC);
						$('input[name="BDC_GLH"]').val(info.GLH);
						$('input[name="BDC_QLRZJHM"]').val(info.POWERSOURCE_ZJHM);
						$('input[name="POWERSOURCEIDNUM"]').val(info.POWERSOURCE_QSWH);
						$('input[name="BDC_QLKSSJ"]').val(info.GYTD_BEGIN_TIME);
						$('input[name="BDC_QLJSSJ1"]').val(info.GYTD_END_TIME1);
						$('input[name="BDC_QLJSSJ2"]').val(info.GYTD_END_TIME2);
						
						if(result.type=="0"){
							parent.art.dialog({
								content: result.msg,
								icon:"warning",
								ok: true
							});
						}
					}else{
						if(result.type=="0"){
							parent.art.dialog({
								content: result.msg,
								icon:"warning",
								ok: true
							});
						}
					}
					art.dialog.removeData("bdcdaxxcxInfo");
				}else{
					parent.art.dialog({
						content: "请根据查询选择一条不动产登记信息。",
						icon:"warning",
						ok: true
					});
				}
			}
		}, false);
	};
	
	/*创建权属来源信息*/
	function daxxFillPowerSource(info) {
		//定义个startWithOwn
		String.prototype.startWithOwn = function(s) {
			if (s == null || s == "" || this.length == 0 || s.length > this.length)
				return false;
			if (this.substr(0, s.length) == s)
				return true;
			else
				return false;
			//return true;
		}
		var powerSource = {};
		for(var name in info){
			if(name.startWithOwn("POWERSOURCE_")){
				powerSource[name]=info[name];
			}
		}
	
		var powerSourceItems = [];
		powerSourceItems.push(powerSource);
		return powerSourceItems;
	}
	
	
	function daxxFillInfoJson(bdcdaxxcxInfo) {
		var info = {
			"ESTATE_NUM":bdcdaxxcxInfo.BDCDYH,
			"LOCATED":bdcdaxxcxInfo.FDZL,
			"POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
			"POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
			"POWERSOURCE_QSZT":bdcdaxxcxInfo.QSZT,
			"POWERSOURCE_ZJLX":bdcdaxxcxInfo.ZJLX,
			"POWERSOURCE_ZJHM":bdcdaxxcxInfo.ZJHM,
			"POWERSOURCE_ZDDM":bdcdaxxcxInfo.ZDDM,
			"POWERSOURCE_GLH":bdcdaxxcxInfo.GLH,
			"POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo.QLQSSJ,//权利开始时间
			"POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo.QLJSSJ,//权利结束时间
			"POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo.QLJSSJ1,
			"POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo.QLJSSJ2,
			"ZD_BM":bdcdaxxcxInfo.ZDDM,
			"ZD_QLLX":bdcdaxxcxInfo.ZDQLLX,//宗地权利类型
			"ZD_QLSDFS":bdcdaxxcxInfo.QLSDFS,
			"ZD_ZL":bdcdaxxcxInfo.TDZL,
			"ZD_MJ":bdcdaxxcxInfo.ZDMJ,
			"ZD_YTSM":bdcdaxxcxInfo.TDYTSM,
			"ZD_QLXZ":bdcdaxxcxInfo.QLXZ,//权利性质
			"ZD_LEVEL":bdcdaxxcxInfo.DJ,
			"ZD_RJL":bdcdaxxcxInfo.RJL,
			"ZD_JZXG":bdcdaxxcxInfo.JZXG,
			"ZD_JZMD":bdcdaxxcxInfo.JZMD,
			"ZD_E":bdcdaxxcxInfo.TD_SZ_D,
			"ZD_W":bdcdaxxcxInfo.TD_SZ_X,
			"ZD_N":bdcdaxxcxInfo.TD_SZ_B,
			"ZD_S":bdcdaxxcxInfo.TD_SZ_N,
			"GYTD_BEGIN_TIME":bdcdaxxcxInfo.QLQSSJ,
			"GYTD_END_TIME1":bdcdaxxcxInfo.QLJSSJ,
			"GYTD_END_TIME2":bdcdaxxcxInfo.QLJSSJ1,
			"GYTD_END_TIME3":bdcdaxxcxInfo.QLJSSJ2,
			"GYTD_GYFS":bdcdaxxcxInfo.GYFS,
			"GYTD_QDJG":bdcdaxxcxInfo.JG,
			"GYTD_SYQMJ":bdcdaxxcxInfo.SYQMJ,
			"GYTD_QSZT":bdcdaxxcxInfo.QSZT,
			"FW_ZL":bdcdaxxcxInfo.FDZL,
			"FW_ZH":bdcdaxxcxInfo.ZH,
			"FW_SZC":bdcdaxxcxInfo.SZC,
			"FW_HH":bdcdaxxcxInfo.HH,
			"FW_ZCS":bdcdaxxcxInfo.ZCS,
			"FW_GHYT":bdcdaxxcxInfo.FW_GHYT,
			"FW_YTSM":bdcdaxxcxInfo.GHYTSM,
			"FW_XZ":bdcdaxxcxInfo.FWXZ,
			"FW_FWJG":bdcdaxxcxInfo.FWJG,//房屋结构
			"FW_DYDYTDMJ":bdcdaxxcxInfo.DYTDMJ,
			"FW_FTTDMJ":bdcdaxxcxInfo.FTTDMJ,
			"FW_JZMJ":bdcdaxxcxInfo.JZMJ,
			"FW_GYQK":bdcdaxxcxInfo.GYFS,//房屋共有情况
			"FW_ZYJZMJ":bdcdaxxcxInfo.ZYJZMJ,
			"FW_FTJZMJ":bdcdaxxcxInfo.FTJZMJ,
			"FW_QLLX":bdcdaxxcxInfo.FW_QLLX,
			"FW_JGSJ":bdcdaxxcxInfo.JGSJ,
			"POWERSOURCE_DYH":bdcdaxxcxInfo.BDCDYH,
			"POWERSOURCE_QSWH":bdcdaxxcxInfo.BDCQZH,//证书文号（权属来源）
			"POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
			"BDCDYH":bdcdaxxcxInfo.BDCDYH,//不动产单元号
			"ZDDM":bdcdaxxcxInfo.ZDDM,//宗地代码
			"FWBM":bdcdaxxcxInfo.FWBM,//房屋编码
			"YWH":bdcdaxxcxInfo.YWH,//业务号
			"ZXYWH":bdcdaxxcxInfo.ZXYWH,//注销业务号
			"GLH":bdcdaxxcxInfo.GLH//关联号
		};
		return info;
	}
</script>

<!-- 权属来源信息开始 -->
<div class="tab_height"></div>
<div id="powerSourceInfo" trid="">
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th><span>权属来源</span>
                <div id="bdcdacxButton" style="display:none; float: right;">
                    <a href="javascript:void(0);" class="eflowbutton titleButton"
                       onclick="showSelectBdcdaxxcx_TBG();">房屋查询 </a>
                </div>
             	<input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="查询不动产档案信息" onclick="showSelectBdcfwzdcx();"/>
                <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitPowSourceInfo('1');"/>
                <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitPowSourceInfo('0');"/>
                <%--	           <input type="button" class="eflowbutton" style="display:none;float:right; margin: 2px 10px; padding: 0 20px;" value="手动填写" id="sdtxBtn" onclick="fillInQsly();"/>--%>
            </th>
        </tr>
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width">权属来源类型：</td>
                        <td>
                            <%-- <eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setQSLYLX(this.value);"
								defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
							</eve:eveselect> --%>
                            <eve:eveselect clazz="tab_text validate[]" dataParams="QSLYLX"
                                           dataInterface="dictionaryService.findDatasForSelect" onchange="setQSLYLX(this.value);"
                                           defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">权属文号：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="30" id="POWERSOURCEIDNUM"
                                   style="float: left;" name="POWERSOURCEIDNUM" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">不动产单元号：</td>
                        <td>
                            <input type="text" class="tab_text validate[custom[estateNum]]" maxlength="128" id="BDC_BDCDYH"
                                   style="float: left;" name="BDC_BDCDYH" />
                        </td>
                        <td class="tab_width">权属状态：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="QSZT"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">权利人：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128"
                                   style="float: left;" id="QLR" name="QLR" />
                        </td>
                        <td class="tab_width">宗地代码：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_ZDDM"
                                   style="float: left;" name="BDC_ZDDM" />
                        </td>
                    </tr>
                    <tr>
                    	<td class="tab_width">关联号：</td>
                    	<td colspan="3">
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_GLH"
                                   style="float: left;" name="BDC_GLH" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="DocumentType" onchange="bdcSetValidate(this.value,'BDC_QLRZJHM')"
                                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
                                           name="BDC_QLRZJLX" id="BDC_QLRZJLX">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">证件号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLRZJHM"
                                   style="float: left;" name="BDC_QLRZJHM" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">法定代表人姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRXM"
                                   style="float: left;" name="BDC_FDDBRXM" />
                        </td>
                        <td class="tab_width">法定代表人电话：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="11" id="BDC_FDDBRDH"
                                   style="float: left;" name="BDC_FDDBRDH" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">代表人证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="DocumentType" onchange="bdcSetValidate(this.value,'BDC_FDDBRZJHM')"
                                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
                                           name="BDC_FDDBRZJLX" id="BDC_FDDBRZJLX">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">代表人证件号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRZJHM"
                                   style="float: left;" name="BDC_FDDBRZJHM" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">代理人姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRXM"
                                   style="float: left;" name="BDC_DLRXM" />
                        </td>
                        <td class="tab_width">代理人电话：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRDH"
                                   style="float: left;" name="BDC_DLRDH" />
                        </td>
                    </tr>

                    <tr>
                        <td class="tab_width">代理人证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="DocumentType" onchange="bdcSetValidate(this.value,'BDC_DLRZJHM')"
                                           dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
                                           name="BDC_DLRZJLX" id="BDC_DLRZJLX">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">代理人证件号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="18" id="BDC_DLRZJHM"
                                   style="float: left;" name="BDC_DLRZJHM" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">代理机构名称：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLJGMC"
                                   style="float: left;" name="BDC_DLJGMC" />
                        </td>
                        <td class="tab_width">权力开始时间：</td>
                        <td>
                            <input type="text" id="BDC_QLKSSJ" name="BDC_QLKSSJ" maxlength="60" style="width:184px;"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'BDC_QLJSSJ1\')}'})" class="tab_text_1 Wdate"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="tab_width">权力结束时间1：</td>
                        <td>
                            <input type="text" id="BDC_QLJSSJ1" name="BDC_QLJSSJ1" maxlength="60" style="width:184px;"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'BDC_QLKSSJ\')}'})" class="tab_text_1 Wdate"/>
                        </td>
                        <td class="tab_width">权力结束时间2：</td>
                        <td>
                            <input type="text" id="BDC_QLJSSJ2" name="BDC_QLJSSJ2" maxlength="60" style="width:184px;"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'BDC_QLKSSJ\')}'})" class="tab_text_1 Wdate"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">备注：</td>
                        <td colspan="3">
                            <input type="text" class="tab_text validate[]" maxlength="512" id="BDC_QSLYBZ"
                                   style="width: 60%;" name="BDC_QSLYBZ" />
                        </td>
                    </tr>
                    <tr id="sdtxly" style="display: none;">
                        <td class="tab_width"><font class="tab_color" id="sdtxFont" style="display: none;">*</font>手动填写理由：</td>
                        <td colspan="3"><input type="text" class="tab_text" maxlength="256"
                                               name="POWERSOURCE_SDTXLY" id="POWERSOURCE_SDTXLY" style="width: 72%;"  />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerSourceInfoList">
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">权属来源类型</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">证书（文号）</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerSourceInfoList_blank_tr">
        <td colspan="7" style="text-align: center;">暂无权属来源信息，请添加！</td>
    </tr>
</table>