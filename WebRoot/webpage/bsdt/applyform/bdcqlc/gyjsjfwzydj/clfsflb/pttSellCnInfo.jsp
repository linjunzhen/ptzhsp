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
    
    function validatesellCnInfo(sellCnInfo){
        var flag = false;
        return flag;
    }
    
    //保存、添加
    function submitsellCnInfo(type){

        var flag = validatePowerParams("sellCnInfo","required");

        if (!flag) {
            return false;
        }

        if($("#sellCnInfoList_blank_tr")){
            $("#sellCnInfoList_blank_tr").remove();
        }
        var sellCnInfo = {};
        var sellCnInfoAll = FlowUtil.getFormEleData("sellCnInfo");
        var sellCnInfoAllJson = JSON.stringify(sellCnInfoAll);
        //过滤一下信息
        sellCnInfo["SELLXM"] = sellCnInfoAll.SELLXM;
        sellCnInfo["SELLHYZK"] = sellCnInfoAll.SELLHYZK;
        sellCnInfo["SELLPOXM"] = sellCnInfoAll.SELLPOXM;
        sellCnInfo["SELLPOZJHM"] = sellCnInfoAll.SELLPOZJHM;
        //获取未成年子女信息
        var SELLCHILD_JSON = getSellChildrenJson();
        if(SELLCHILD_JSON!=null && SELLCHILD_JSON !="" && SELLCHILD_JSON!='[]'){
        	sellCnInfo["SELLCHILD_JSON"]=SELLCHILD_JSON;
        }
        //alert("sellCnInfo:"+JSON.stringify(sellCnInfo));
        var trid = $("#sellCnInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加卖方承诺信息?", function() {
                closeinfo("sellCnInfo");
                addinfo("sellCnInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendSellCnInfoRow(sellCnInfo,i,trid);
            art.dialog({
                content : "卖方承诺信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#sellCnInfoList .sellCnInfo_tr")){
                index = $("#sellCnInfoList .sellCnInfo_tr").length;
                if(index > 0){
                    var trid = $("#sellCnInfoList .sellCnInfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendSellCnInfoRow(sellCnInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加卖方承诺信息?", function() {
                closeinfo("sellCnInfo");
                addinfo("sellCnInfo");
            });
        }



    }
    
    //动态添加行
    function appendSellCnInfoRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="sellCnInfo_tr" id="sellCnInfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.SELLXM + '</td>';
            if(item.SELLHYZK == "1"){
               html += '<td style="text-align: center;">未婚</td>';
            }else if(item.SELLHYZK == "2"){
               html += '<td style="text-align: center;">已婚</td>';
            }else if(item.SELLHYZK == "3"){
               html += '<td style="text-align: center;">丧偶</td>';
            }else if(item.SELLHYZK == "4"){
               html += '<td style="text-align: center;">离婚</td>';
            }else if(item.SELLHYZK == "9"){
               html += '<td style="text-align: center;">未说明的婚姻状况</td>';
            }else{
               html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadsellCnInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadsellCnInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delsellCnInfo(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#sellCnInfoList").append(html);
               }       
            }else{
               $("#sellCnInfoList").append(html);
            }
            $("#sellCnInfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#sellCnInfo").attr("trid","sellCnInfotr_"+index);

        }
    }


    //删除
    function delsellCnInfo(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
    }

    //加载
    function loadsellCnInfo(obj,ptype){
        //清空数据
        $("#sellChidrenXx").find("input[type='text']").val("");
        $("#sellCnInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#sellCnInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"sellCnInfo");
        //先删除多余的行
		var table = document.getElementById("sellChidrenXx");
		var tableLenth = table.rows.length;
		for(var j = 2;j<tableLenth;j++){
		    document.getElementById("sellChidrenXx").rows[j].remove();
		    tableLenth = table.rows.length;//更新行数
		    j = 1;
		}
        //动态加未成年子女信息
        var sellChildrenJson = item.SELLCHILD_JSON;
		if(sellChildrenJson!=null && sellChildrenJson!="" && sellChildrenJson!="[]"){
			var sellChildrenS = eval(sellChildrenJson);
			//重置索引值
			if(sellChildrenS.length>1){
			   sellChildrenSn = 1;
			}
			for(var i=0;i<sellChildrenS.length;i++){
				if(i==0){
					FlowUtil.initFormFieldValue(sellChildrenS[i],"sellChildren_1");
				}else{
					addSellChildren();
					FlowUtil.initFormFieldValue(sellChildrenS[i],"sellChildren_"+(i+1));
				}
			}
	    }
    }   
    
    /**==========未成年子女信息开始=======================================*/
	var sellChildrenSn = 1;
	function addSellChildren(){
		sellChildrenSn = sellChildrenSn+1;
		var table = document.getElementById("sellChidrenXx");
		var trContent = table.getElementsByTagName("tr")[1];
		var trHtml = trContent.innerHTML;
		var findex = trHtml.indexOf("deleteSellChildren('");
		if(sellChildrenSn>10){
			var replacestr = trHtml.substring(findex,findex+24);
		}else{
			var replacestr = trHtml.substring(findex,findex+23);
		}
		trHtml = trHtml.replace(replacestr,"deleteSellChildren('"+sellChildrenSn+"')");
		trHtml = "<tr id=\"sellChildren_"+sellChildrenSn+"\">"+trHtml+"</tr>";
		$("#sellChidrenXx").append(trHtml);
	}
	
	function deleteSellChildren(idSn){
		var table = document.getElementById("sellChidrenXx");
		if(table.rows.length==2){
			parent.art.dialog({
				content: "最少一个未成年子女信息！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		$("#sellChildren_"+idSn).remove();
	}
	
	//获取未成年子女信息
	function getSellChildrenJson(){
		var datas = [];
		var table = document.getElementById("sellChidrenXx");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			var o = document.getElementById("sellChidrenXx").rows[i];
			$(o).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			//过滤一下
			if(null!=trData.SELLCHILDXM&& trData.SELLCHILDZJHM!=""){
				var children = {};
				children.SELLCHILDXM = trData.SELLCHILDXM;
				children.SELLCHILDZJHM = trData.SELLCHILDZJHM;
				datas.push(children);
			}
		}
		//$("input[type='hidden'][name='BUY_CHILDJSON']").val(JSON.stringify(datas));
		return JSON.stringify(datas);
	}
	/**==========未成年子女信息结束=======================================*/
    
    
    //获取承诺信息
    function getSellCnInfoJson(){
       var datas = [];
       $("#sellCnInfoList .sellCnInfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#SELLCN_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    //初始化承诺信息
    function initSellCnInfo(items){
       if(items){
           if($("#sellCnInfoList_blank_tr")){
              $("#sellCnInfoList_blank_tr").remove();
           }
           if($("#sellCnInfoList .sellCnInfo_tr")){
             $("#sellCnInfoList .sellCnInfo_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendSellCnInfoRow(items[i],(i+1),null);
           }
           $("#sellCnInfo").attr("style","");
           var firstItem = $("#sellCnInfoList .sellCnInfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#sellCnInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"sellCnInfo");
                //初始化未成年子女信息
		        var sellChildrenJson = item.SELLCHILD_JSON;
				if(sellChildrenJson!=null && sellChildrenJson!="" && sellChildrenJson!="[]"){
					var sellChildrenS = eval(sellChildrenJson);
					for(var i=0;i<sellChildrenS.length;i++){
						if(i==0){
							FlowUtil.initFormFieldValue(sellChildrenS[i],"sellChildren_1");
						}else{
							addSellChildren();
							FlowUtil.initFormFieldValue(sellChildrenS[i],"sellChildren_"+(i+1));
						}
					}
			    }
           }
           $("#sellCnInfo_btn").attr("style","display:none;");
       }
    }
    
 </script>
 
 <div class="tab_height"></div>
 <div id="sellCnInfo" trid="">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
       <tr>
           <th><span>卖方承诺信息</span>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitsellCnInfo('1');"/>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitsellCnInfo('0')"/>
           </th>
       </tr>    
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2"> 
                   <tr>
                       <td class="tab_width">姓名：</td>
                       <td><input type="text" class="tab_text validate[]" 
                           name="SELLXM" maxlength="32"/></td>
                       <td class="tab_width">婚姻状况：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="hyzk"
                           dataInterface="dictionaryService.findDatasForSelect"
                           defaultEmptyText="选择婚姻状况" name="SELLHYZK" id="SELLHYZK" >
                           </eve:eveselect>
                       </td>
                   </tr>
                   <tr>
                       <td class="tab_width">配偶姓名：</td>
                       <td>
                           <input type="text" class="tab_text validate[]" 
                           name="SELLPOXM" maxlength="32"/>
                       </td>
                       <td class="tab_width">配偶证件号码：</td>
                       <td ><input type="text" class="tab_text validate[]" 
                           name="SELLPOZJHM" maxlength="32"/></td>
                   </tr>
                   <tr>
                 		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="sellChidrenXx">
							<tr>
								<th colspan="4"  style="background-color: #FFD39B;">未成年子女信息</th>
							</tr>
							<tr id="sellChildren_1">
								<td>
									<table class="tab_tk_pro2">
										<tr>
											<td>姓名：</td>
											<td>
												<input type="text" class="tab_text " name="SELLCHILDXM"   maxlength="32" />
											</td>
											<td>身份证件号码：</td>
											<td>	
												 <input type="text" class="tab_text" maxlength="32" name="SELLCHILDZJHM"  />
											</td>
										</tr>
									</table>
									<div class="tab_height2"></div>
								</td>
								<td>
									<br>
									<input type="button" class="eflowbutton"name="deleteCurrentChildren" value="删除行" onclick="deleteSellChildren('1');">
									<br>
									<br>
									<input type="button" class="eflowbutton" name="addOneChildren" value="增加一行" onclick="addSellChildren();">
								</td>
							</tr>
						</table>
                  </tr>
               </table>
            </td>
        </tr>       
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="sellCnInfoList">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">姓名</td>
        <td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">婚姻状况</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="sellCnInfoList_blank_tr">
       <td colspan="5" style="text-align: center;">暂无卖方承诺信息，请添加！</td>
    </tr>
</table>