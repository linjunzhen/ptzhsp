<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>

    <!-- 涉税申报页面-->
    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String domId = request.getParameter("domId");
    request.setAttribute("domId", domId);
    String initDomShow = request.getParameter("initDomShow");
    request.setAttribute("initDomShow",initDomShow);
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

.eflowbutton-disabled {
    background: #94C4FF;
    border: none;
    padding: 0 10px;
    line-height: 26px;
    cursor: pointer;
    height: 26px;
    color: #E9E9E9;
    border-radius: 5px;
}

.selectType {
    margin-left: -100px;
}

.bdcdydacxTrRed {
    color: red;
}
</style>
<div id="taxinfo_${param.domId}">
 <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
     <tr>
         <th>
             <span style="float:left;margin-left:10px">涉税登记</span>
         </th>
     </tr>
     <tr>
        <td>
            <eve:radiogroup fieldname="TAXDJ_TYPE" width="500"  typecode="TAXDJTYPE" value="${busRecord.TAXDJ_TYPE}"></eve:radiogroup>
        </td>
     </tr>
     <tr id="tax_type1_tr" name="TAX_TYPE1_TR">
        <td style="padding: 10px;">
            <table class="tab_tk_pro2">
                 <tr>
			         <th colspan="4">
			             <span style="float:left;margin-left:10px">房源信息</span>
			         </th>
			     </tr>
                 <tr>
                     <td class="tab_width">
                         <font class="tab_color">*</font>
                         不动产单元号：
                     </td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFKMBM" maxlength="128"
                             value="${busRecord.DJJFMX_SFKMBM}"/>                         
                     </td>
                     <td class="tab_width">房屋属地纳税机关：</td>
                     <td>
                         <eve:eveselect clazz="tab_text validate[required]" dataParams="SFLX"
                             dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择房屋属地纳税机关"
                             name="DJJFMX_SFLX" value="${busRecord.DJJFMX_SFLX}">
                         </eve:eveselect>
                     </td>
                 </tr>

                 <tr>
                     <td class="tab_width">权属转移对象：</td>
                     <td>
                         <eve:eveselect clazz="tab_text validate[required]" dataParams="SFLX"
                             dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择房屋属地纳税机关"
                             name="DJJFMX_SFLX" value="${busRecord.DJJFMX_SFLX}">
                         </eve:eveselect>
                     </td>
                     <td class="tab_width">权属转移用途：</td>
                     <td>
                         <eve:eveselect clazz="tab_text validate[]" dataParams="KMMC"
                             dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收费科目名称"
                             name="DJJFMX_SFKMMC" value="${busRecord.DJJFMX_SFKMMC}">
                         </eve:eveselect>
                     </td>
                 </tr>

                 <tr>
                     <td class="tab_width">房屋所在地行政区划：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">房屋所在地乡镇街道：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">开发企业纳税识别号：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">开发企业名称：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">房屋地址：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">房屋总楼层：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">建筑面积：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">朝向代码：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">交易合同编号：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">交易价格：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">单价：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                     <td class="tab_width">合同金额：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
                             value="${busRecord.DJJFMX_SFJS}" />
                     </td>
                 </tr>

                 <tr>
                     <td class="tab_width">权属转移方式：</td>
                     <td>
                         <eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo" defaultEmptyValue="0"
                             dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收是否额外收费"
                             name="DJJFMX_SFEWSF" value="${busRecord.DJJFMX_SFEWSF}">
                         </eve:eveselect>
                     </td>

                     <td class="tab_width">
                         <font class="tab_color">*</font>当期应收款金额：
                     </td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_YSJE" maxlength="128"
                             value="${busRecord.DJJFMX_YSJE}" />
                     </td>
                 </tr>

                 <tr>
                     <td class="tab_width">当期应收款所属月份：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_ZHYSJE" maxlength="128"
                             value="${busRecord.DJJFMX_ZHYSJE}" />
                     </td>
                     <td class="tab_width">备注：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SJFFR" maxlength="32"
                             value="${busRecord.DJJFMX_SJFFR}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <th colspan="4">
                         <span style="float:left;margin-left:10px">买方信息</span>
                     </th>
                 </tr>

                 <tr>
                     <td class="tab_width">姓名：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
                             value="${busRecord.DJJFMX_SFDW}" />
                     </td>
                     <td class="tab_width"><font class="tab_color">*</font>国籍：</td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_SSJE" maxlength="128"
                             value="${busRecord.DJJFMX_SSJE}" />
                     </td>
                 </tr>

                 <tr>
                     <td class="tab_width">证件类型：</td>
                     <td>
                         <eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL" defaultEmptyValue="营业执照"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DY_DYQRZJHM');"
                            defaultEmptyText="选择证件类型" name="DY_DYQRZJLX" id="DY_DYQRZJLX">
                        </eve:eveselect>
                     </td>
                     <td class="tab_width">证件号码：</td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_SSJE" maxlength="128"
                             value="${busRecord.DJJFMX_SSJE}" />
                     </td>
                 </tr>
                    
                 <tr>
                     <td class="tab_width">联系电话：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
                             value="${busRecord.DJJFMX_SFDW}" />
                     </td>
                     <td class="tab_width"><font class="tab_color">*</font>联系地址：</td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_SSJE" maxlength="128"
                             value="${busRecord.DJJFMX_SSJE}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">所占份额(%)：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
                             value="${busRecord.DJJFMX_SFDW}" />
                     </td>
                     <td class="tab_width">交易份额(%)：</td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_SSJE" maxlength="128"
                             value="${busRecord.DJJFMX_SSJE}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">房屋套次：</td>
                     <td>
                         <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
                             value="${busRecord.DJJFMX_SFDW}" />
                     </td>
                     <td class="tab_width">是否完税：</td>
                     <td>
                         <input type="text" class="tab_text validate[require]" name="DJJFMX_SSJE" maxlength="128"
                             value="${busRecord.DJJFMX_SSJE}" />
                     </td>
                 </tr>
                 
                 <tr>
                     <td class="tab_width">涉税联系单号：</td>
                     <td colspan="3">
                         <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
                             value="${busRecord.DJJFMX_SFDW}" />
                     </td>
                 </tr>
                 
             </table>
        </td>
     </tr>
 </table>
</div>