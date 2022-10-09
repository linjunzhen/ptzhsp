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
    
    function validatebuyCnInfo(buyCnInfo){
        var flag = false;
        return flag;
    }
    
    //保存、添加
    function submitbuyCnInfo(type){

        var flag = validatePowerParams("buyCnInfo","required");

        if (!flag) {
            return false;
        }

        if($("#buyCnInfoList_blank_tr")){
            $("#buyCnInfoList_blank_tr").remove();
        }
        var buyCnInfo = {};
        var buyCnInfoAll = FlowUtil.getFormEleData("buyCnInfo");
        var buyCnInfoAllJson = JSON.stringify(buyCnInfoAll);
        //过滤一下信息
        buyCnInfo["MFXM"] = buyCnInfoAll.MFXM;
        buyCnInfo["MFZFQK"] = buyCnInfoAll.MFZFQK;
        buyCnInfo["MFHYZK"] = buyCnInfoAll.MFHYZK;
        buyCnInfo["MFPOXM"] = buyCnInfoAll.MFPOXM;
        buyCnInfo["MFPOZJHM"] = buyCnInfoAll.MFPOZJHM;
        //获取未成年子女信息
        var BUYCHILD_JSON = getBuyChildrenJson();
        if(BUYCHILD_JSON!=null && BUYCHILD_JSON !="" && BUYCHILD_JSON!='[]'){
        	buyCnInfo["BUYCHILD_JSON"]=BUYCHILD_JSON;
        }
        //alert("buyCnInfo:"+JSON.stringify(buyCnInfo));
        var trid = $("#buyCnInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加买方承诺信息?", function() {
                closeinfo("buyCnInfo");
                addinfo("buyCnInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendBuyCnInfoRow(buyCnInfo,i,trid);
            art.dialog({
                content : "买方承诺信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#buyCnInfoList .buyCnInfo_tr")){
                index = $("#buyCnInfoList .buyCnInfo_tr").length;
                if(index > 0){
                    var trid = $("#buyCnInfoList .buyCnInfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendBuyCnInfoRow(buyCnInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加买方承诺信息?", function() {
                closeinfo("buyCnInfo");
                addinfo("buyCnInfo");
            });
        }



    }
    
    //动态添加行
    function appendBuyCnInfoRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="buyCnInfo_tr" id="buyCnInfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.MFXM + '</td>';
            if(item.MFHYZK == "1"){
               html += '<td style="text-align: center;">未婚</td>';
            }else if(item.MFHYZK == "2"){
               html += '<td style="text-align: center;">已婚</td>';
            }else if(item.MFHYZK == "3"){
               html += '<td style="text-align: center;">丧偶</td>';
            }else if(item.MFHYZK == "4"){
               html += '<td style="text-align: center;">离婚</td>';
            }else if(item.MFHYZK == "9"){
               html += '<td style="text-align: center;">未说明的婚姻状况</td>';
            }else{
               html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadBuyCnInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadBuyCnInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delBuyCnInfo(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#buyCnInfoList").append(html);
               }       
            }else{
               $("#buyCnInfoList").append(html);
            }
            $("#buyCnInfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#buyCnInfo").attr("trid","buyCnInfotr_"+index);

        }
    }


    //删除
    function delBuyCnInfo(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
    }

    //加载
    function loadBuyCnInfo(obj,ptype){
        //清空数据
        $("#buyChidrenXx").find("input[type='text']").val("");
        $("#buyCnInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#buyCnInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"buyCnInfo");
        //先删除多余的行
		var table = document.getElementById("buyChidrenXx");
		var tableLenth = table.rows.length;
		for(var j = 2;j<tableLenth;j++){
		    document.getElementById("buyChidrenXx").rows[j].remove();
		    tableLenth = table.rows.length;//更新行数
		    j = 1;
		}
        //动态加未成年子女信息
        var buyChildrenJson = item.BUYCHILD_JSON;
		if(buyChildrenJson!=null && buyChildrenJson!="" && buyChildrenJson!="[]"){
			var buyChildrenS = eval(buyChildrenJson);
			//重置索引值
			if(buyChildrenS.length>1){
			   buyChildrenSn = 1;
			}
			for(var i=0;i<buyChildrenS.length;i++){
				if(i==0){
					FlowUtil.initFormFieldValue(buyChildrenS[i],"buyChildren_1");
				}else{
					addBuyChildren();
					FlowUtil.initFormFieldValue(buyChildrenS[i],"buyChildren_"+(i+1));
				}
			}
	    }
    }   
    
    /**==========未成年子女信息开始=======================================*/
	var buyChildrenSn = 1;
	function addBuyChildren(){
		buyChildrenSn = buyChildrenSn+1;
		var table = document.getElementById("buyChidrenXx");
		var trContent = table.getElementsByTagName("tr")[1];
		var trHtml = trContent.innerHTML;
		var findex = trHtml.indexOf("deleteBuyChildren('");
		if(buyChildrenSn>10){
			var replacestr = trHtml.substring(findex,findex+23);
		}else{
			var replacestr = trHtml.substring(findex,findex+22);
		}
		trHtml = trHtml.replace(replacestr,"deleteBuyChildren('"+buyChildrenSn+"')");
		trHtml = "<tr id=\"buyChildren_"+buyChildrenSn+"\">"+trHtml+"</tr>";
		$("#buyChidrenXx").append(trHtml);
	}
	
	function deleteBuyChildren(idSn){
		var table = document.getElementById("buyChidrenXx");
		if(table.rows.length==2){
			parent.art.dialog({
				content: "最少一个未成年子女信息！",
				icon:"warning",
				ok: true
			});
			return false;
		}
		$("#buyChildren_"+idSn).remove();
	}
	
	//获取未成年子女信息
	function getBuyChildrenJson(){
		var datas = [];
		var table = document.getElementById("buyChidrenXx");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			var o = document.getElementById("buyChidrenXx").rows[i];
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
			if(null!=trData.MFCHILDXM&& trData.MFCHILDZJHM!=""){
				var children = {};
				children.MFCHILDXM = trData.MFCHILDXM;
				children.MFCHILDZJHM = trData.MFCHILDZJHM;
				datas.push(children);
			}
		}
		//$("input[type='hidden'][name='BUY_CHILDJSON']").val(JSON.stringify(datas));
		return JSON.stringify(datas);
	}
	/**==========未成年子女信息结束=======================================*/
    
    
    //获取承诺信息
    function getBuyCnInfoJson(){
       var datas = [];
       $("#buyCnInfoList .buyCnInfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#BUYCN_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    //初始化承诺信息
    function initBuyCnInfo(items){
       if(items){
           if($("#buyCnInfoList_blank_tr")){
              $("#buyCnInfoList_blank_tr").remove();
           }
           if($("#buyCnInfoList .buyCnInfo_tr")){
             $("#buyCnInfoList .buyCnInfo_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendBuyCnInfoRow(items[i],(i+1),null);
           }
           $("#buyCnInfo").attr("style","");
           var firstItem = $("#buyCnInfoList .buyCnInfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#buyCnInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"buyCnInfo");
                //初始化未成年子女信息
		        var buyChildrenJson = item.BUYCHILD_JSON;
				if(buyChildrenJson!=null && buyChildrenJson!="" && buyChildrenJson!="[]"){
					var buyChildrenS = eval(buyChildrenJson);
					for(var i=0;i<buyChildrenS.length;i++){
						if(i==0){
							FlowUtil.initFormFieldValue(buyChildrenS[i],"buyChildren_1");
						}else{
							addBuyChildren();
							FlowUtil.initFormFieldValue(buyChildrenS[i],"buyChildren_"+(i+1));
						}
					}
			    }
           }
           $("#buyCnInfo_btn").attr("style","display:none;");
       }
    }
    
 </script>
 
 <div class="tab_height"></div>
 <div id="buyCnInfo" trid="">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
       <tr>
           <th><span>买方承诺信息</span>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitbuyCnInfo('1');"/>
           <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitbuyCnInfo('0')"/>
           </th>
       </tr>    
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2"> 
                   <tr>
                       <td class="tab_width">姓名：</td>
                       <td><input type="text" class="tab_text validate[]" 
                           name="MFXM" maxlength="32"/></td>
                       <td class="tab_width">住房情况</td>
                       <td> <eve:eveselect clazz="tab_text " dataParams="tcCns"
                                          dataInterface="dictionaryService.findDatasForSelect" 
                                          defaultEmptyText="==请选择住房情况==" name="MFZFQK" id="MFZFQK" >
                           </eve:eveselect>
                       </td>
                   </tr>
                   <tr>
                       <td class="tab_width">婚姻状况：</td>
                       <td>
                           <eve:eveselect clazz="tab_text validate[]" dataParams="hyzk"
                           dataInterface="dictionaryService.findDatasForSelect"
                           defaultEmptyText="选择婚姻状况" name="MFHYZK" id="MFHYZK" >
                           </eve:eveselect>
                       </td>
                       <td class="tab_width">配偶姓名：</td>
                       <td>
                           <input type="text" class="tab_text validate[]" 
                           name="MFPOXM" maxlength="32"/>
                       </td>
                   </tr>
                   <tr>
                       <td class="tab_width">配偶证件号码：</td>
                       <td colspan = "3" ><input type="text" class="tab_text validate[]" 
                           name="MFPOZJHM" maxlength="32"/></td>
                   </tr>
                   <tr>
                 		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="buyChidrenXx">
							<tr>
								<th colspan="4"  style="background-color: #FFD39B;">未成年子女信息</th>
							</tr>
							<tr id="buyChildren_1">
								<td>
									<table class="tab_tk_pro2">
										<tr>
											<td>姓名：</td>
											<td>
												<input type="text" class="tab_text " name="MFCHILDXM"   maxlength="32" />
											</td>
											<td>身份证件号码：</td>
											<td>	
												 <input type="text" class="tab_text" maxlength="32" name="MFCHILDZJHM"  />
											</td>
										</tr>
									</table>
									<div class="tab_height2"></div>
								</td>
								<td>
									<br>
									<input type="button" class="eflowbutton"name="deleteCurrentChildren" value="删除行" onclick="deleteBuyChildren('1');">
									<br>
									<br>
									<input type="button" class="eflowbutton" name="addOneChildren" value="增加一行" onclick="addBuyChildren();">
								</td>
							</tr>
						</table>
                  </tr>
               </table>
            </td>
        </tr>       
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="buyCnInfoList">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">姓名</td>
        <td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">婚姻状况</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="buyCnInfoList_blank_tr">
       <td colspan="5" style="text-align: center;">暂无买方承诺信息，请添加！</td>
    </tr>
</table>