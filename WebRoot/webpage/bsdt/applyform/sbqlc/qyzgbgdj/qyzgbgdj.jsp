<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);	
%>
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
</style>
<script type="text/javascript">
	//电子证照信息
	function license(){
		  var sqfs = "2";//收取方式默认为纸质上传，加载列表时只显示查看，打印按钮。
		  var attachKey = "02800100";//电子证照默认营业执照（其对应证照的编码为02800100）
		  var searchType = "LICENCE_CODE";//查询类型默认为电子证照编码，"MATER_CODE"为根据材料编码查询
		  var onlineBusTableName = "${serviceItem.FORM_KEY}";
		  var itemName="${serviceItem.ITEM_NAME}";
	      var itemId="${serviceItem.ITEM_CODE}";
	      var transactor="${sessionScope.curLoginUser.fullname}";
	      var credCodes=$("input[name='GJXX_ZZHM']").val();//统一社会信用代码
	      var credNames=$("input[name='JBXX_DWMC']").val();//企业名称
	      //alert(onlineBusTableName+"-"+itemName+"-"+itemId+"-"+transactor+"-"+credCodes+"-"+credNames);
	      if((credCodes==""&&credNames=="") || (credCodes=="undefined"&&credNames=="undefined")
	    		 ||(credCodes==null&&credNames==null)){
	          alert("请填写单位名称和证照号码！");
	          return ;
	      }
	      var type="enterprise";//默认企业
	      $("#showLicenseMsgSpan").html("");
	      var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
	          "&itemName="+itemName+"&transactor="+transactor+"&itemId="+itemId+
				"&sqfs="+sqfs+"&attachKey="+attachKey+"&busTableName="+onlineBusTableName+"&searchType="+searchType);
	      //alert("电子证照请求地址："+url);
	      $.dialog.open(url, {
	          title : "电子证照信息列表   电子证照生成联系人:智慧岛中心，林振兴，‭15959016804‬",
	          width:"880px",
	          lock: true,
	          resize:false,
	          height:"550px",
	          close: function () {
	              var resultJsonInfo = art.dialog.data("resultJsonInfo");
	              if (resultJsonInfo) {
	                  initLicenseUploadMaters(resultJsonInfo);
	                  art.dialog.removeData("resultJsonInfo");
	              }
	          }
	      }, false);
	   
	}
</script>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<input name="PUSH_FLAG" type="hidden"/>
	<input name="JBXX_DWBH" type="hidden"/>
	<input name="JBXX_DWZT" type="hidden"/>
	<input name="CBXX_JBJGDM" type="hidden"/>
	<tr>
		<th colspan="6">单位基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>单位管理码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="JBXX_DWGLM" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="dwjbxxcx();"></a>			
			<input type="button" value="查询" class="eflowbutton" onclick="dwxxcx()" />  			
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_DWMC" disabled/>
		</td>
		<td class="tab_width"> 单位简称：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_DWJC" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系电话：</td>
		<td>
			<input type="text" class="tab_text"  name="JBXX_LXDH" />
		</td>		
		<td class="tab_width">联系电子邮箱：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_DZYX" />
		</td>
		<td class="tab_width">邮政编码：</td>
		<td>
			<input type="text" class="tab_text" name=JBXX_YZBM />
		</td>
	</tr>
	<tr>
		<td class="tab_width">传真电话：</td>
		<td>
			<input type="text" class="tab_text"  name="JBXX_CZDH" />
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>地址：</td>
		<td colspan='3'> 
			<input type="text" class="tab_text validate[required]" name="JBXX_DZ" style="width:500px"/>
		</td>
	</tr>	
	<tr>
		<td class="tab_width">工商登记执照种类：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGSDJZZZL"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'ZJHM');"
			defaultEmptyText="--请选择--" name="JBXX_ZZZL">
			</eve:eveselect>			
		</td>
		<td class="tab_width">工商登记执照号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_ZZHM" disabled/>
		</td>
		<td class="tab_width">工商登记发照日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_FZRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">工商登记有限年限：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="JBXX_YXNX" disabled/>			
		</td>
		<td class="tab_width">工商有效期限开始日期：</td>
		<td>
			<input type="text"  id="JBXX_KSRQ" name="JBXX_KSRQ" 
			onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,maxDate:'#F{$dp.$D(\'JBXX_JZRQ\')}'})" 
			class="tab_text Wdate validate[]" readonly="true"/>
		</td>
		<td class="tab_width">工商有效期限截止日期：</td>
		<td>
			<input type="text" id="JBXX_JZRQ"  name="JBXX_JZRQ" 
			onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true,minDate:'#F{$dp.$D(\'JBXX_KSRQ\')}'})" 
			class="tab_text Wdate validate[]" readonly="true"/>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">单位关键信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证照类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBZZLX"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'GJXX_ZZHM');"
			defaultEmptyText="--请选择--" name="GJXX_ZZLX">
			</eve:eveselect>			
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证照号码：</td>
		<td>	
			<input type="text" class="tab_text validate[required]" name="GJXX_ZZHM" disabled/>		
			<input type="button" value="打印营业执照" class="eflowbutton" onclick="license()" />  			
		</td>
		<td class="tab_width"><font class="tab_color">*</font>所属行政区域：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="XZQY"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="GJXX_XZQY">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBDWLX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_DWLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>单位管理类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBDWGLLX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_DWGLLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>统计类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBTJLX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_TJLX">
			</eve:eveselect>
		</td>		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBLSGX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_LSGX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>所属行业：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBHYDM"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_SSHY">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>行业风险类别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBHYFXLB"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_FXLB">
			</eve:eveselect>
		</td>		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经济类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBJJLX"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="GJXX_JQLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 批准成立日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="GJXX_PZCLRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"> 成立日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="GJXX_CLRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRXM" disabled/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="zjlx"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="GJXX_FRZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRZJHM" disabled/>
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color">*</font>法定代表人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_FRLXDH" disabled/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>专管员姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="GJXX_ZGYXM" />
		</td>
		<td class="tab_width">专管员身份证号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="GJXX_ZGYZJHM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>专管员联系号码：</td>
		<td>
			<input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" name="GJXX_ZGYLXHM" />
		</td>
		<td class="tab_width"> 专管员所属部门：</td>
		<td>
			<input type="text" class="tab_text"  name="GJXX_ZGYSSBM" disabled/>
		</td>
		<td class="tab_width">专管员所属部门负责人：</td>
		<td>
			<input type="text" class="tab_text"  name="GJXX_ZGYBMFZR" disabled/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">专管员部分负责人电话：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]" name="GJXX_BMFZRDH" disabled/>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">税务机构信息</th>
	</tr>
	<tr>
		<td class="tab_width">税务电脑编码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="SWJGXX_SH" disabled/>
		</td>
		<td class="tab_width"> 税务机构代码：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[]" name="SWJGXX_JGDM" disabled/>
		</td>	
	</tr>
</table>
<!-- <div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">养老银行信息</th>
	</tr>
	<tr>
		<td class="tab_width">缴费开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_JFKHHMC" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="yhxxcx();"></a>				
		</td>
		<td class="tab_width">缴费账户名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_JFZHMC" /> 
		</td>
		<td class="tab_width">缴费账号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_JFZH" /> 
		</td>
	</tr>
	<tr>
		<td class="tab_width">发放开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_FFKHHMC" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="yhxxcx();"></a>	
		</td>
		<td class="tab_width">发放账户名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_FFZHMC" /> 
		</td>
		<td class="tab_width">发放账号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="YLYHXX_FFZH" /> 
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">工伤银行信息</th>
	</tr>
	<tr>
		<td class="tab_width">缴费开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_JFKHHMC" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="yhxxcx();"></a>	 
		</td>
		<td class="tab_width">缴费账户名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_JFZHMC" /> 
		</td>
		<td class="tab_width">缴费账号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_JFZH" /> 
		</td>
	</tr>
	<tr>
		<td class="tab_width">发放开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_FFKHHMC" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="yhxxcx();"></a>	
		</td>
		<td class="tab_width">发放账户名称：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_FFZHMC" /> 
		</td>
		<td class="tab_width">发放账号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="GSYHXX_FFZH" /> 
		</td>
	</tr>
</table> -->
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">变更信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>变更日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="BGXX_BGRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>变更原因：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]"  name="BGXX_BGYY" /> 
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]"  name="BGXX_BZ" style="width:600px"/> 
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">单位参保</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>变更日期：</td>
		<td colspan='5'>
			<input type="text" class="tab_text Wdate validate[required]" name="DWCB_BGRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">已参保险种信息</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" style="width:100%">
	<tr>
		<td style="height:150px;">
			<table class="easyui-datagrid" rownumbers="false" pagination="false"
					id="xzxxGrid" fitcolumns="true" 
					checkonselect="true" style="width:100%;height:auto;"
					selectoncheck="true" fit="true" border="false"
					data-options="autoSave:true,method:'post',url:'sbQyzgbgdjController.do?findXZXX&ywId=${ywId}'">
					<thead>
						<tr>				
							<th data-options="field:'aae140',width:'20%',align:'center',formatter:formatXzlx">险种类型</th>
							<th data-options="field:'aab033',width:'20%',align:'center',formatter:formatZsfs">征收方式</th>						
							<th data-options="field:'aab050',width:'15%',align:'center'">首次参保日期</th>
							<th data-options="field:'aab051',width:'20%',align:'center',formatter:formatCbzt">参保状态</th>						
							<th data-options="field:'aab066',width:'20%',align:'center',formatter:formatZtjfbz">暂停缴费标志</th>						
							<!-- <th data-options="field:'aae140',width:'15%',align:'center'">是否开通网报</th>	 -->					
						</tr>
					</thead>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">	
	//数据格式化
	function dataFormat(value,json){
		var data = JSON.parse(json);
		var rtn = "";
		$.each(data, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
	//险种类型格式化
	var xzlxObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBXZLX'}
	});
	function formatXzlx(value,row,index){
		var json = xzlxObj.responseText;
		return dataFormat(value,json);
	}
	
	//征收方式格式化
	var zsfsObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBZSFS'}
	});
	function formatZsfs(value,row,index){
		var json = zsfsObj.responseText;
		return dataFormat(value,json);
	}	
	
	//参保状态格式化
	var cbztObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBCBZT'}
	});
	function formatCbzt(value,row,index){
		var json = cbztObj.responseText;
		return dataFormat(value,json);
	}
	
	//单位暂停缴费标志格式化
	var ztjfbzObj = $.ajax({
        method:'post',
        url:'dictionaryController.do?load',
        async:false,
        dataType:'json',
        data:{typeCode:'SBZTJFBZ'}
	});
	function formatZtjfbz(value,row,index){
		var json = ztjfbzObj.responseText;
		return dataFormat(value,json);
	}
</script>



