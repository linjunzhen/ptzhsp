<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<style>
.pdl {
	padding-left: 20px;
}
</style>
<script type="text/javascript">
	$(function() {
		//单选框绑定值
		$("input[name='ZTYTDM']").click(function() {
			var ZTYTDMVALUE = $("input[name='ZTYTDM']:checked").val();
			$("#YYFSDIV").attr("style", "display:none;");
			$("input[name='YYFS']").removeClass(" validate[required]");
			$("#SFWXXSTDIV").attr("style", "display:none;");
			$("input[name='SFWXXST']").removeClass(" validate[required]");
			$("#CYLXDIV").attr("style", "display:none;");
			$("input[name='CYLX']").removeClass(" validate[required]");
			if (ZTYTDMVALUE == "1") {
				$("#YYFSDIV").attr("style", "");
				$("input[name='YYFS']").addClass(" validate[required]");
				$("input[name='YYFS']:first").validationEngine('validate');
				$("input[name='CYLX']").attr("checked", false);
				$("input[name='SFWXXST']").attr("checked", false);

				$("input[name='ZYCF'][value='0']").prop("checked",true); 
				$("input[name='ZYCF']").attr("disabled","disabled");
				$("input[name='JTYCPSDW'][value='0']").prop("checked",true);
				$("input[name='JTYCPSDW']").attr("disabled","disabled");
				
			} else if (ZTYTDMVALUE == "2") {
				$("#CYLXDIV").attr("style", "");
				$("input[name='CYLX']").addClass(" validate[required]");
				$("input[name='CYLX']:first").validationEngine('validate');
				$("input[name='YYFS']").attr("checked", false);
				$("input[name='SFWXXST']").attr("checked", false);
				
				$("input[name='ZYCF']").removeAttr("disabled","disabled");
				$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
			} else if (ZTYTDMVALUE == "3") {
				$("#SFWXXSTDIV").attr("style", "");
				$("input[name='SFWXXST']").addClass(" validate[required]");
				$("input[name='SFWXXST']:first").validationEngine('validate');
				$("input[name='YYFS']").attr("checked", false);
				$("input[name='CYLX']").attr("checked", false);
				
				$("input[name='ZYCF']").removeAttr("disabled","disabled");
				$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
			}

		});
		if ($("input[name='ZTYTDM']:checked").val() == "1") {
			$("#YYFSDIV").attr("style", "");
			$("input[name='YYFS']").addClass(" validate[required]");
			
			
			$("input[name='ZYCF'][value='0']").prop("checked",true); 
			$("input[name='ZYCF']").attr("disabled","disabled");
			$("input[name='JTYCPSDW'][value='0']").prop("checked",true);
			$("input[name='JTYCPSDW']").attr("disabled","disabled");
		} else if ($("input[name='ZTYTDM']:checked").val() == "2") {
			$("#CYLXDIV").attr("style", "");
			$("input[name='CYLX']").addClass(" validate[required]");
			
			
			$("input[name='ZYCF']").removeAttr("disabled","disabled");
			$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
		} else if ($("input[name='ZTYTDM']:checked").val() == "3") {
			$("#SFWXXSTDIV").attr("style", "");
			$("input[name='SFWXXST']").addClass(" validate[required]");
			
			$("input[name='ZYCF']").removeAttr("disabled","disabled");
			$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
		}

		$("input[name='WLJY']").click(function() {
			var WLJYVALUE = $("input[name='WLJY']:checked").val();
			if (WLJYVALUE == "0") {
				$("#WLJYDIV").attr("style", "display:none;");
			//	$("input[name='WZDZ']").removeClass(" validate[required]");
				$("input[name='STMD']").removeClass(" validate[required]");
				$("input[name='STMD']").attr("checked", false);
			} else if (WLJYVALUE == "1") {
				$("#WLJYDIV").attr("style", "");
			//	$("input[name='WZDZ']").addClass(" validate[required]");
				$("input[name='STMD']").addClass(" validate[required]");
			}
		});
		if ($("input[name='WLJY']:checked").val() == "0") {
			$("#WLJYDIV").attr("style", "display:none;");
			$("input[name='STMD']").removeClass(" validate[required]");
			//$("input[name='WZDZ']").removeClass(" validate[required]");
		} else if ($("input[name='WLJY']:checked").val() == "1") {
			$("#WLJYDIV").attr("style", "");
			$("input[name='STMD']").addClass(" validate[required]");
			//$("input[name='WZDZ']").addClass(" validate[required]");
		}
		//经营项目级联上一级
		$("input[name='YBZSPXS']").click(function() {
			$("#ybzspxsId").prop("checked", true);
			ybzspxssj($("#ybzspxsId"));
		});
		$("input[name='SZSPXS']").click(function() {
			$("#szspxsId").prop("checked", true);
			szspxssj($("#szspxsId"));
		});
		$("input[name='SZSSXS']").click(function() {
			$("#szspxsId").prop("checked", true);
			szspxssj($("#szspxsId"));
		});
		$("input[name='TSSPXS']").click(function() {
			$("#tsspxsId").prop("checked", true);
			tsspxssj($("#tsspxsId"));
		});
		$("input[name='ZNJZS']").click(function() {
			$("#znjzsId").prop("checked", true);
			zzypzzsj($("#znjzsId"));
		});
		
		$("input[name='JZCTW']").click(function() {
			if($(this).is(':checked')){
				$("#lslspzsId").prop("checked", true);
			}
			
		});
		
		$("input[name='BHDG']").click(function() {
			$("#gdlspzsId").prop("checked", true);
		});
		//经营项目回填验证
		if($("#ybzspxsId").is(':checked')){
			ybzspxssj($("#ybzspxsId"));
		}
		if($("#szspxsId").is(':checked')){
			szspxssj($("#szspxsId"));
		}
		if($("#tsspxsId").is(':checked')){
			tsspxssj($("#tsspxsId"));
		}
		if($("#znjzsId").is(':checked')){
			zzypzzsj($("#znjzsId"));
		}
		
		
		//设置只读
		//获取流程变量对象
		/* var EFLOW_VARS = FlowUtil.getFlowObj();
		if(EFLOW_VARS.EFLOW_IS_START_NODE=="false"&&EFLOW_VARS.EFLOW_IS_START_NODE!=undefined){
			$("input[name='JZCTW']").attr("disabled","disabled");
		}
		if(EFLOW_VARS.EFLOW_FROMHISTROY&&EFLOW_VARS.EFLOW_FROMHISTROY=='true'){
			$("input[name='JZCTW']").removeAttr("disabled","disabled");
		} */
	})

	function ybzspxssj(o) {
		if ($(o).is(':checked')) {
			$("input[name='YBZSPXS']").addClass(" validate[required]");
			$("input[name='YBZSPXS']:first").validationEngine('validate');
		} else {
			$("input[name='YBZSPXS']").removeClass(" validate[required]");
			$("input[name='YBZSPXS']:first").validationEngine('hide');
			$("input[name='YBZSPXS']").attr("checked", false);
		}
	}
	

	function szspxssj(o) {
		if ($(o).is(':checked')) {
			$("input[name='SZSPXS']").addClass(" validate[required]");
			$("input[name='SZSPXS']:first").validationEngine('validate');
			$("input[name='SZSSXS']").addClass(" validate[required]");
			$("input[name='SZSSXS']:first").validationEngine('validate');

		} else {
			$("input[name='SZSPXS']").removeClass(" validate[required]");
			$("input[name='SZSPXS']:first").validationEngine('hide');
			$("input[name='SZSSXS']").removeClass(" validate[required]");
			$("input[name='SZSSXS']:first").validationEngine('hide');
			$("input[name='SZSPXS']").attr("checked", false);
			$("input[name='SZSSXS']").attr("checked", false);
		}
	}
	
	function tsspxssj(o) {
		if ($(o).is(':checked')) {
			$("input[name='TSSPXS']").addClass(" validate[required]");
			$("input[name='TSSPXS']:first").validationEngine('validate');
		} else {
			$("input[name='TSSPXS']").removeClass(" validate[required]");
			$("input[name='TSSPXS']:first").validationEngine('hide');
			$("input[name='TSSPXS']").attr("checked", false);
		}
	}
	
	function zzypzzsj(o) {
		if ($(o).is(':checked')) {
			$("input[name='ZNJZS']").addClass(" validate[required]");
			$("input[name='ZNJZS']:first").validationEngine('validate');
		} else {
			$("input[name='ZNJZS']").removeClass(" validate[required]");
			$("input[name='ZNJZS']:first").validationEngine('hide');
			$("input[name='ZNJZS']").attr("checked", false);
		}
	}
	
	function lslspzssj(o){
		if ($(o).is(':checked')) {
			
		} else {
			$("input[name='JZCTW']").attr("checked", false);
		}
	}
	function gdlspzssj(o){
		if ($(o).is(':checked')) {
			
		} else {
			$("input[name='BHDG']").attr("checked", false);
		}
	}
	function txsm(){
	 	art.dialog({
	 		lock : true,
	 		title:'填写说明',
			content: '社会信用代码（身份证号码）栏参照营业执照填写社会信用代码，无社会信用代码的填写营业执照号码；<br/>无营业执照的机关、企、事业单位、社会团体以及其他组织机构，填写组织机构代码；个体经营者填写<br/>相关身份证件号码。'
		});
	}
	function cydxgfsm(){
		art.dialog({
	 		lock : true,
	 		title:'餐饮大小划分说明',
			content: '特大型餐馆：指加工经营场所使用面积在3000㎡以上（不含3000㎡），或者就餐座位数在1000座以上（不含1000座）的餐馆。<br/>大型餐馆：指加工经营场所使用面积在500～3000㎡（不含500㎡，含3000㎡），或者就餐座位数在250～1000座（不含250座，含1000座）的餐馆。<br/>中型餐馆：指加工经营场所使用面积在150～500㎡（不含150㎡，含500㎡），或者就餐座位数在75～250座（不含75座，含250座）的餐馆。<br/>小型餐馆：指加工经营场所使用面积在150㎡以下（含150㎡），或者就餐座位数在75座以下（含75座）的餐馆。包括小吃店、快餐店、饮品店、茶座、酒吧和咖啡厅。'
		});
	}
	function qtjtpz(){
		art.dialog({
	 		lock : true,
	 		title:'填写说明',
			content: '该品种应当报国家总局批准后执行'
		});
	}
</script>
<tr>
	<th rowspan="2"><font color="#ff0101">*</font>主体业态</th>
	<td>
		<p>类型:</p>
		<p>
			<label><input class="validate[required]" type="radio" name="ZTYTDM"
				value="1">食品销售经营者</label>
		</p>
		<div id="YYFSDIV" style="display: none;" class="pdl">
			<fda:fda-exradiogroup name="YYFS" width="100"
				datainterface="fdaDictionaryService.findList"
				queryparamjson="{TYPE_CODE:'YYFS',ORDER_TYPE:'ASC'}"
				allowblank="true" isstartp="true" puremode="true"></fda:fda-exradiogroup>
		</div>
		<p>
			<label><input class="validate[required]" type="radio" name="ZTYTDM"
				value="2">餐饮服务经营者</label>
		</p>
		<div id="CYLXDIV" style="display: none;" class="pdl">
			<fda:fda-exradiogroup name="CYLX" width="100"
				datainterface="fdaDictionaryService.findList"
				queryparamjson="{TYPE_CODE:'CYLX',ORDER_TYPE:'ASC'}"
				allowblank="true" isstartp="true" puremode="true"></fda:fda-exradiogroup>
			<p>
				<a href="javascript:void(0);" style="color: #0C83D3;" onclick="cydxgfsm();"
					title="特大型餐馆：指加工经营场所使用面积在3000㎡以上（不含3000㎡），或者就餐座位数在1000座以上（不含1000座）的餐馆。&#10;大型餐馆：指加工经营场所使用面积在500～3000㎡（不含500㎡，含3000㎡），或者就餐座位数在250～1000座（不含250座，含1000座）的餐馆。&#10;中型餐馆：指加工经营场所使用面积在150～500㎡（不含150㎡，含500㎡），或者就餐座位数在75～250座（不含75座，含250座）的餐馆。&#10;小型餐馆：指加工经营场所使用面积在150㎡以下（含150㎡），或者就餐座位数在75座以下（含75座）的餐馆。包括小吃店、快餐店、饮品店、茶座、酒吧和咖啡厅。">餐饮大小划分说明</a>
			</p>
		</div>
		<p>
			<label><input class="validate[required]" type="radio" name="ZTYTDM" 
				<c:if test="${EFLOW_VARS.SQFS=='1'&&sessionScope.curLoginMember!=null&&sessionScope.curLoginMember.USER_TYPE=='1'}">disabled="disabled"</c:if>
				value="3">单位食堂</label>
		</p>
		<div id="SFWXXSTDIV" style="display: none;" class="pdl">
			<p>
				是否为职业学校、普通中等学校、小学、特殊教育学校、托幼机构：
			</p>
			<fda:fda-exradiogroup name="SFWXXST" width="80"
					datainterface="fdaDictionaryService.findList"
					queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}"
					puremode="true" isstartp="true"></fda:fda-exradiogroup>
		</div>

	</td>
	<th rowspan="2"><font color="#ff0101">*</font>经营项目</th>
	<td rowspan="2" style=" padding: 0px;">
		<table style="width: 100%;" >
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;">
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="01" id="ybzspxsId" class="validate[required]" <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'01')}">checked='true'</c:if>
							onclick="ybzspxssj(this);" />预包装食品销售:</label>
					</p>
					<div class="pdl">
						<fda:fda-exradiogroup name="YBZSPXS" width="300" value="${EFLOW_VARS.EFLOW_BUSRECORD.YBZSPXS}"
							radiolables="预包装食品（含冷藏冷冻食品）销售,预包装食品（不含冷藏冷冻食品）销售"
							radiovalues="0101,0102" isstartp="true" puremode="true"></fda:fda-exradiogroup>
					</div>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;">
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="02" id="szspxsId" class="validate[required]" <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'02')}">checked='true'</c:if>
							onclick="szspxssj(this);" />散装食品销售:</label>
					</p>
					<div class="pdl">
						<fda:fda-exradiogroup name="SZSPXS" width="300" value="${EFLOW_VARS.EFLOW_BUSRECORD.SZSPXS}"
							radiolables="散装食品（含冷藏冷冻食品）销售,散装食品（不含冷藏冷冻食品）销售"
							radiovalues="0201,0202" isstartp="true" puremode="true"></fda:fda-exradiogroup>
						<p>
							是否含散装熟食销售：
							<fda:fda-exradiogroup name="SZSSXS" width="80" value="${EFLOW_VARS.EFLOW_BUSRECORD.SZSSXS}"
								datainterface="fdaDictionaryService.findList"
								queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}"
								puremode="true"></fda:fda-exradiogroup>
						</p>
					</div>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;">
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="03" id="tsspxsId" class="validate[required]" <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'03')}">checked='true'</c:if>
							onclick="tsspxssj(this);" />特殊食品销售:</label>
					</p>
					<div class="pdl">
						<fda:fda-excheckbox name="TSSPXS" width="200px" value="${EFLOW_VARS.EFLOW_BUSRECORD.TSSPXS}"
							checklables="保健食品销售,特殊医学用途配方食品销售,婴幼儿配方乳粉销售,其他婴幼儿配方食品销售"
							checkvalues="0301,0302,0303,0304" puremode="true" isstartp="true"></fda:fda-excheckbox>
					</div>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;">
					 <fda:fda-excheckbox name="DLLSPZS" width="200px;" datavalidate="validate[required]"
						checklables="其他类食品销售" checkvalues="09" puremode="true" value="${EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS}"
						isstartp="true"></fda:fda-excheckbox>
					<p>
						其他类食品销售:<input type="text" style="width: 50%"
							placeholder="请输入具体品种" maxlength="126" class="syj-input1"
							name="QTLSPXS" /><a href="javascript:void(0);" style="color: #0C83D3;" onclick="qtjtpz();"
							title="该品种应当报国家总局批准后执行">填写说明</a>
					</p>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;"><fda:fda-excheckbox name="DLLSPZS" width="200px;" datavalidate="validate[required]"
						checklables="热食类食品制售" value="${EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS}"
						checkvalues="10" puremode="true" isstartp="true"></fda:fda-excheckbox>
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="11" id="lslspzsId" class="validate[required]" <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'11')}">checked='true'</c:if>
							onclick="lslspzssj(this);" />冷食类食品制售:</label>
					</p>
					<p>
					&nbsp;
					<fda:fda-excheckbox name="JZCTW" width="200px;" datavalidate=" "
						checklables="仅拆封、调味" value="${EFLOW_VARS.EFLOW_BUSRECORD.JZCTW}"
						checkvalues="1" puremode="true" ></fda:fda-excheckbox>
					</p>
					<fda:fda-excheckbox name="DLLSPZS" width="200px;" datavalidate="validate[required]"
						checklables="生食类食品制售" value="${EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS}"
						checkvalues="12" puremode="true" isstartp="true"></fda:fda-excheckbox>
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="13" id="gdlspzsId" class="validate[required]" <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'13')}">checked='true'</c:if>
							onclick="gdlspzssj(this);" />糕点类食品制售:</label>
					</p>
					<p>
					&nbsp;
							是否含有裱花蛋糕：
							<fda:fda-exradiogroup name="BHDG" width="80" allowblank="true"
								datainterface="fdaDictionaryService.findList" value="${EFLOW_VARS.EFLOW_BUSRECORD.BHDG}"
								queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}"
								puremode="true"></fda:fda-exradiogroup>
					</p>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;border-bottom: 1px solid #84b1d9;">
					<p>
						<label><input type="checkbox" name="DLLSPZS" value="14" id="znjzsId" class="validate[required]"  <c:if test="${fn:contains(EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS,'14')}">checked='true'</c:if>
							onclick="zzypzzsj(this);">自制饮品制售</label>
					</p>
					<div class="pdl">
						<p>
							是否含自酿酒制售：
							<fda:fda-exradiogroup name="ZNJZS" width="80"
								datainterface="fdaDictionaryService.findList" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZNJZS}"
								queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}"
								puremode="true"></fda:fda-exradiogroup>
						</p>
					</div>
				</td>
			</tr>
			<tr>
				<td style="border: 0px;"><fda:fda-excheckbox name="DLLSPZS" width="80%" datavalidate="validate[required]"
						checklables="其他类食品制售" checkvalues="19" puremode="true" value="${EFLOW_VARS.EFLOW_BUSRECORD.DLLSPZS}"
						isstartp="true"></fda:fda-excheckbox>
					<p>
						其他类食品制售:<input type="text" style="width: 50%"
							placeholder="请输入具体品种" maxlength="126" class="syj-input1"
							name="QTLSPZS" /><a href="javascript:void(0);" style="color: #0C83D3;" onclick="qtjtpz();"
							title="该品种应当报国家总局批准后执行">填写说明</a>
					</p></td>
			</tr>
		</table>
	</td>
</tr>
