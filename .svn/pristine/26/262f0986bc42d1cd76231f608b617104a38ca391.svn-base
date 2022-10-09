<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.pdl{
	padding-left: 20px;
}
.extract-button {
    background: #0c83d3 none repeat scroll 0 0;
    border: 1px solid #0c83d3;
    border-radius: 3px;
    color: #fff;
    cursor: pointer;
    height: 26px;
    padding: 0 20px;
}
</style>
<script type="text/javascript">
/**初始化绑定select事件*/
$(function(){
	  //初始化网站上传图片
	  initUploadPic();
});

var swfUploadPic;
function initUploadPic(){
	var ztFilepath = $("input[name='WZJTLJ']").val();
	var successfulUploads = 0;
	if(ztFilepath.length>0){
		successfulUploads = 1;
	}
	swfUploadPic = FdaAppUtil.bindSwfUpload({
        file_types : "*.jpg;*.jpeg;*.png;*.png;",
        file_size_limit :"10 MB",
        post_params : {
            "uploadPath":"WebsiteScreenshot"
        },
        file_upload_limit :1,
        button_placeholder_id:"WZJTLJ_UPLOAD",
        successful_uploads:successfulUploads,
        upload_success_handler:function(resultJson){
            if(resultJson.success==true){
                //获取文件ID
                var fileId = resultJson.fileId;
                //获取文件名称
                var fileName = resultJson.fileName;
                //获取文件路径
                var filePath = resultJson.filePath;
                
                var spanHtml = "<a href=\"javascript:void(0);\" title=\""+fileName+"\" ";
                spanHtml+=(" onclick=\"FdaAppUtil.downLoadFile('"+fileId+"');\">");
                spanHtml +="<font color=\"blue\">下载</font></a>&nbsp;"
                spanHtml +="<a href=\"javascript:void(0);\"   onclick=\"delPicFile('"+fileId+"');\" ><font color=\"red\">删除</font></a>";
                $("#WZJTLJ_DIV").append(spanHtml);
                $("input[name='WZJTLJ']").val(fileId);
                
            }
        }
    });
}

function delPicFile(fileId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
        var layload = layer.load("正在提交请求中…");
        $.post("fileAttachController.do?multiDel",{
                selectColNames :fileId
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success == true){
                	$("#WZJTLJ_DIV").html("");
                    $("input[name='WZJTLJ']").val("");
                    swfUploadPic.setStats({successful_uploads:0});
                }else{
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
        });
    });
	
}



/**
 * 添加
 */
function addFoodSafetyDiv(){
	var newhtml = "<div style=\"position:relative;\">";
	newhtml += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"syj-table2 tmargin20\">";
	newhtml += "<tr><th><font color=\"#ff0101\">*</font>设备名称：</th><td><input type=\"text\" name=\"SBMC\" placeholder=\"请输入设备名称\" class=\"syj-input1 validate[required]\" /></td>";
	newhtml += "<th><font color=\"#ff0101\">*</font>数量：</th><td><input type=\"text\" name=\"SL\" placeholder=\"请输入数量\" class=\"syj-input1 validate[required,custom[integer],min[0]] \" /></td></tr>";
    newhtml += "<tr><th><font color=\"#ff0101\">*</font>位置：</th><td><input type=\"text\" name=\"WZ\" placeholder=\"请输入位置\" class=\"syj-input1 validate[required]\" /></td>";
    newhtml += "<th>备注：</th><td><input type=\"text\" name=\"BZ\" placeholder=\"请输入备注\" class=\"syj-input1\" /></td></tr>";
    newhtml += "</table><a href=\"javascript:void(0);\" onclick=\"delFoodSafetyDiv(this);\" class=\"syj-closebtn\"></a></div>";
	$("#FoodSafetyDiv").append(newhtml);
}
/**
 * 删除
 */
 function delFoodSafetyDiv(o){
	$(o).closest("div").remove();
}

function getFacilityEquipmentJson(){
	var facilityEquipmentArray = [];
	$("#FoodSafetyDiv table").each(function(i){
		var sbmc = $(this).find("[name='SBMC']").val();
		var sl = $(this).find("[name='SL']").val();
		var wz = $(this).find("[name='WZ']").val();
		var bz = $(this).find("[name='BZ']").val();
		var facilityEquipment = {};
		facilityEquipment.SBMC = sbmc;
		facilityEquipment.SL = sl;
		facilityEquipment.WZ = wz;
		facilityEquipment.BZ = bz;
		facilityEquipmentArray.push(facilityEquipment);
	});
	if(facilityEquipmentArray.length>0){
		return JSON.stringify(facilityEquipmentArray);
	}else{
		return "";
	}
}


</script>
<!--开始信用记录查询页面 -->
<jsp:include page="../common/queryFailureRecord.jsp"></jsp:include>
<!--结束信用记录查询页面 -->
<!--开始引入回填方法共有界面 -->
<jsp:include page="../common/backFillData.jsp"></jsp:include>
<!--结束引入回填方法共有界面 -->
<form action="" id="BASEINFO_FORM"  >
	<div class="syj-title1" style="margin: 15px 0 6px 0;"><span>基本信息</span>
	<%-- <c:if test="${empty EFLOW_VARS.EFLOW_EXEID&&EFLOW_VARS.SQFS=='2'}">
	办件流水号:
	<input type="text" name="busExeIdToFill" class="syj-input1" style="width: 200px;height: 26px;border-radius:10px;line-height: 26px;border:1px solid #c9deef;"  />
	<input type="button" onclick="busExeIdToFillFun();" class="extract-button" value="读取数据">
	说明：输入办件流水号，可将该办件数据复制至本办件中
	</c:if>
	<c:if test="${EFLOW_VARS.SQFS=='1'}">
	<a href="javascript:void(0);" onclick="FrontFormTxsm();"
			title="1.经营者名称应当与营业执照上标注的名称一致。&#10;2.社会信用代码（身份证号码）栏参照营业执照填写社会信用代码，无社会信用代码的填写营业执照号码；无营业执照的机关、企、事业单位、社会团体以及其他组织机构，填写组织机构代码；个体经营者填写相关身份证件号码。&#10;3.本申请书内所称法定代表人（负责人）包括：①企业法人的法定代表人；②个人独资企业的投资人；③分支机构的负责人；④合伙企业的执行事务合伙人（委派代表）；⑤个体工商户业主；⑥农民专业合作社的法定代表人。&#10;4.填写住所、经营场所时要具体表述所在位置，明确到门牌号、房间号，住所应与营业执照（或组织机构证、相关身份证件）内容一致。&#10;5.申请人应选择主体业态和经营项目，并在□中打√。&#10;6.本申请书内所称食品安全管理人员是指企业内部专职或兼职的食品安全负责人。"
			style="width: 20%;color: #0C83D3;">填写说明</a>
	</c:if> --%>
	</div>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font color="#ff0101">*</font>经营者名称</th>
			<td><input type="text" style="width:70%"
				 maxlength="40" class="syj-input1 validate[required]" placeholder="请输入名称"
				name="JYZMC" />
				<a href="javascript:void(0);" onclick="JYZMCTxsm();"
			title="应与营业执照标注的名称保持一致"
			style="width: 20%;color: #0C83D3;">填写说明</a>
			</td>
			<th><font color="#ff0101">*</font>统一社会信用代码<br/>(身份证号码)</th>
			<td><input type="text" placeholder="请参照右侧填写说明" style="width: 55%;" 
			    onblur="ajaxShxydmsfzhmExist('${EFLOW_VARS.ITEM_CODE}')"
				maxlength="50" class="syj-input1 validate[required]" name="SHXYDMSFZHM" />
			 <!-- <input type="button" style="float:right;" onclick="backFillCreditCodeData();" class="extract-button" value="读取主体数据"/> -->
			<a href="javascript:void(0);" onclick="txsm();" 
			title="社会信用代码（身份证号码）栏参照营业执照填写社会信用代码，无社会信用代码的填写营业执照号码；无营业执照的机关、企、事业单位、社会团体以及其他组织机构，填写组织机构代码；个体经营者填写相关身份证件号码。"
			style="width: 20%;color: #0C83D3;">填写说明</a>	
			<%-- <input style="border-color:red;background-color: red;float:right;" type="button" onclick="queryFailureRecord('SHXYDMSFZHM','JYZMC','1','${ITEM.ITEM_NAME}');" class="extract-button" value="查询失信黑名单记录"> --%>
			</td>
		</tr>
    	<jsp:include page="../common/address.jsp">
    		<jsp:param value="${EFLOW_VARS_JSON}" name="EFLOW_VARS_JSON"/>
    	</jsp:include>
		<jsp:include page="../common/jyxm.jsp"></jsp:include>
		<tr>
			<td>
				<p>备注:</p>
				<p>
					1.是否含网络经营：
					<fda:fda-exradiogroup name="WLJY" width="100"
						datainterface="fdaDictionaryService.findList" allowblank="false"
						queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}" value="0"
						puremode="true"></fda:fda-exradiogroup>
				</p>
				<div id="WLJYDIV" style="display: none;">
					<p>
						网站地址:<input type="text" name="WZDZ" placeholder="请输入网站地址"
							class="syj-input1" style="width: 200px;" />
					</p>
					<p>
						网站截图 <input type="hidden" name="WZJTLJ" id="WZJTLJ" value="" /> <label
							style="padding-top: 6px;" id="WZJTLJ_DIV"> <c:if
								test="${!empty WZJTLJ_FILE_NAME}">
								<a href="javascript:void(0);" title="${WZJTLJ_FILE_NAME}"
									onclick="FdaAppUtil.downLoadFile('${WZJTLJ_FILE_ID}');"
									style="cursor: pointer;"> <font color="blue"> 下载 </font>
								</a>
								<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE!='false'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
									<a href="javascript:void(0);"
										onclick="delPicFile('${WZJTLJ_FILE_ID}');"> <font
										color="red">删除</font></a>
								</c:if>
							</c:if>
						</label> <label
							<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='false'&&EFLOW_VARS.EFLOW_FROMHISTROY!='true'}">style="display:none;" </c:if>>
							<img id="WZJTLJ_UPLOAD" src="webpage/common/images/tab_btn_sc.png" />
						</label>
					</p>
					<p>
						是否同时具有实体门店：<fda:fda-exradiogroup name="STMD" width="100"
						datainterface="fdaDictionaryService.findList" allowblank="true"
						queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}" 
						puremode="true"></fda:fda-exradiogroup>
					</p>
				</div>
				<p>
					2.中央厨房：
					<fda:fda-exradiogroup name="ZYCF" width="80"
						datainterface="fdaDictionaryService.findList" allowblank="false"
						queryparamjson="{TYPE_CODE:'HaveAndNoHave',ORDER_TYPE:'ASC'}" value="0"
						puremode="true"></fda:fda-exradiogroup>
				</p>
				<p>
					3.用餐配送单位：
					<fda:fda-exradiogroup name="JTYCPSDW" width="80"
						datainterface="fdaDictionaryService.findList" allowblank="false"
						queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}" value="0"
						puremode="true"></fda:fda-exradiogroup>
				</p>
				<p>
					4.利用自动售货设备从事食品销售：
					<fda:fda-exradiogroup name="LYZDSHSBCSSPXS" width="80"
						datainterface="fdaDictionaryService.findList" allowblank="false"
						queryparamjson="{TYPE_CODE:'YesOrNo',ORDER_TYPE:'ASC'}" value="0"
						puremode="true"></fda:fda-exradiogroup>
				</p>
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>申请副本数（份）</th>
			<td><input type="text" placeholder="请输入申请副本数" value="1" 
				 maxlength="4" class="syj-input1 validate[required,custom[integer],min[0]]"
				name="SQFBS" /></td>
			<th><font color="#ff0101">*</font>有 效 期（年）</th>
			<td>
				<input type="text" placeholder="请输入有 效 期" readonly="readonly"
				 maxlength="4" class="syj-input1 validate[required,custom[integer],min[0],max[5]]"
				 name="YXQ" value="5" />
			</td>
		</tr>
		
		<tr>
			
			<th><font color="#ff0101">*</font>经济性质</th>
			<td colspan="3">
					<fda:fda-exradiogroup name="JJXZ" width="400" puremode="true"
					datainterface="fdaDictionaryService.findList"
					allowblank="false"
					queryparamjson="{TYPE_CODE:'economicNature',ORDER_TYPE:'ASC'}"></fda:fda-exradiogroup>
			</td>
			
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>职工人数(人)</th>
			<td>
			<input type="text"  placeholder="请输入职工人数"
				maxlength="10" class="syj-input1 validate[required,custom[integer],min[0]]" name="ZGRS" />
			</td>
			<th><font color="#ff0101">*</font>应体检人数(人):</th>
			<td>
				<input type="text"  placeholder="请输入应体检人数"
				maxlength="10" class="syj-input1 validate[required,custom[integer],min[0]]" name="YTJRS" />
			</td>
			
		</tr>
		<tr>
			<th>邮政编码</th>
			<td>
			<input type="text" placeholder="请输入邮政编码"
				maxlength="16" class="syj-input1 validate[custom[chinaZip]]" name="YZBM" />
			</td>
			<th>E-mail</th>
			<td><input type="text" placeholder="请输入E-mail"
				maxlength="256" class="syj-input1 validate[custom[email]]" name="EMAIL" /></td>
		</tr> 
	</table>
</form>
