<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>出租方信息   </th>
		<table class="tab_tk_pro2">
			    <tr>
                    <td class="tab_width"><font class="tab_color ">*</font>出租人：</td>
                   	<td colspan="1"><input type="text"  class="tab_text validate[required]"
				   name="CZR_NAME" value="${busRecord.CZR_NAME}"/></td>
                
                 <td colspan="2" class="tab_width"></td> 
                </tr>
                 <tr>
                    <td class="tab_width">出租人性质：</td>
                  <td colspan="1"><input type="text" class="tab_text validate[]"
				   name="CZRXZ" value="${busRecord.CZRXZ}"/></td>
				   
                    <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'CZR_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="CZR_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
                </tr>
				
				<tr>
					
					<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="CZR_CARD_NO" style="float: left;" name="CZR_CARD_NO" value="${busRecord.CZR_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowLegalRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>
                    <td class="tab_width">性别：</td>
                    <td colspan="1">
                         <select name="CZR_SEX" class="tab_text validate[required]">
                      <option>请选择性别</option>
                      <option>男</option>
                      <option>女</option>
                      </select> 
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>	电话：</td>
					<td>
			<input type="text" class="tab_text validate[required]" maxlength="128" id="CZR_PHONE" style="float: left;" name="CZR_PHONE" value="${busRecord.CZR_PHONE}"/>
					</td>
                </tr>
                	<tr>
                    <td class="tab_width">地址：</td>
                    <td colspan="1">
                 <input type="text" class="tab_text validate[required]" maxlength="128" id="CZR_ADDRESS" style="float: left;" name="CZR_ADDRESS" value="${busRecord.CZR_ADDRESS}"/>

                    </td>
                    <td class="tab_width"><font class="tab_width"> 	邮编：</td>
					<td>
				<input type="text" class="tab_text validate[required]" maxlength="128" id="CZR_EMAIL" style="float: left;" name="CZR_EMAIL" value="${busRecord.CZR_EMAIL}"/>
					</td>
                </tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td><input type="text" class="tab_text validate[]" id="CZ_FDDBR_NAME" name="CZ_FDDBR_NAME" maxlength="32" value="${busRecord.CZ_FDDBR_NAME}"></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'CZ_FDDBR_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="CZ_FDDBR_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="CZ_FDDBR_CARD_NO" style="float: left;" name="CZ_FDDBR_CARD_NO" value="${busRecord.CZ_FDDBR_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowFddbrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width"><font id="DYQRXX_AGENT_NAME_font" class="tab_color "></font>代理人姓名：</td>
					<td><input type="text" class="tab_text validate[]" name="CZ_DLR_NAME" maxlength="128" value="${busRecord.CZ_DLR_NAME}"></td>
					<td class="tab_width">证件类型：</td>
					<td>
					   <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'CZ_DLR_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="CZ_DLR_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="CZ_DLR_CARD_NO" style="float: left;" name="CZ_DLR_CARD_NO" value="${busRecord.CZ_DLR_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowCzdlrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
			</table>
	</tr>
	<tr>
		
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>承租方信息</th>
		
		<table class="tab_tk_pro2">
		
		<tr>
                    <td class="tab_width">承租人：</td>
                    <td colspan="1">
                      <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_NAME" style="float: left;" name="LESSEE_NAME" value="${busRecord.LESSEE_NAME}">

                    </td>
                    <%--  <td class="tab_width">权利比例：</td>
                     <td colspan="3">
                    <input type="text" class="tab_text validate[]" maxlength="128" id="QLBL" style="float: left;" name="QLBL" value="${busRecord.QLBL}"> --%>
                     <td colspan="3">
                    </td>
                </tr>
                 <tr>
                    <td class="tab_width">承租人性质：</td>
                    <td colspan="1">
                    <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_TYPE" style="float: left;" name="LESSEE_TYPE" value="${busRecord.LESSEE_TYPE}">
                       
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						 <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'LESSEE_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="LESSEE_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
                </tr>
				
				<tr>
					
					<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_CARD_NO" style="float: left;" name="LESSEE_CARD_NO" value="${busRecord.LESSEE_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowlessRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>
                    <td class="tab_width"><font class="tab_color ">*</font>性别：</td>
                    <td colspan="1">
                      <select name="LESSEE_SEX" class="tab_text validate[]">
                      <option>请选择性别</option>
                      <option>男</option>
                      <option>女</option>
                      </select>  
                    </td>
                    <td class="tab_width"> 电话：</td>
					<td>
				 <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_PHONE" style="float: left;" name="LESSEE_PHONE" value="${busRecord.LESSEE_PHONE}"/>
					
					</td>
                </tr>
                	<tr>
                    <td class="tab_width">地址：</td>
              <td colspan="1">
                 <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_ADDRESS" style="float: left;" name="LESSEE_ADDRESS" value="${busRecord.LESSEE_ADDRESS}"/>

                    </td>
                    <td class="tab_width"><font class="tab_width"> 邮编：</td>
					<td>
			<input type="text" class="tab_text validate[]" maxlength="128" name="LESSEE_EMAIL" style="float: left;" id="LESSEE_EMAIL" value="${busRecord.LESSEE_EMAIL}"/>

					</td>
                </tr>
                <tr> 		
                <input type="button" id="addczr" class="eflowbutton" onclick="addCzfxx();" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加">  </tr>
             </table>   
             <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="czrxx_tab">
	
       <tbody> <tr >
            <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">序号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">承租人</td>
			<!-- <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利比例</td> -->
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">承租人性质</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">证件类型</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">性别</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">电话</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">地址</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">邮编</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>
		</tbody>
		   
           	</table>  
           	<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2"> 
           	 <th>居（同）住方信息</th> 
           	 
             <table class="tab_tk_pro2">
          
             <tr> 
			 <input type="button"  id="addtzf" class="eflowbutton"  onclick="addCzffddbrxx();" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加">  
			 </tr>   
				<tr>
					<tr>
					<td class="tab_width">居（同）住人：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="JTZR" style="float: left;" name="JTZR" value="${busRecord.JTZR}">
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="JTZR_CARD_NO" style="float: left;" name="JTZR_CARD_NO" value="${busRecord.JTZR_CARD_NO}">
					</td>
					
				</tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td><input type="text" class="tab_text validate[]" id="LESSEE_FDDBR_NMAE" name="LESSEE_FDDBR_NMAE" maxlength="32" value="${busRecord.LESSEE_FDDBR_NMAE}"></td>
					<td class="tab_width">证件类型：</td>
					<td>
					  <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'LESSEE_FDDBR_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="LESSEE_FDDBR_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_FDDBR_CARD_NO" style="float: left;" name="LESSEE_FDDBR_CARD_NO" value="${busRecord.LESSEE_FDDBR_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowLessFddbrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td><input type="text" class="tab_text validate[]" id="LESSEE_DLR_NAME" name="LESSEE_DLR_NAME" maxlength="128" value="${busRecord.LESSEE_DLR_NAME}"></td>
					<td class="tab_width">证件类型：</td>
					<td>
						 <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'LESSEE_DLR_CARD_TYPE');"
						defaultEmptyText="选择证件类型" name="LESSEE_DLR_CARD_TYPE" value="选择证件类型" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="LESSEE_DLR_CARD_NO" style="float: left;" name="LESSEE_DLR_CARD_NO" value="${busRecord.LESSEE_DLR_CARD_NO}">
					</td>
					<td><a href="javascript:void(0);" onclick="PowLesseeddbrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				
				
		
				
				
			</table>
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="ry_tab">
			<tbody><tr>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">序号</td>
		    <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">人员类型</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">人员姓名</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">证件类型</td>
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		        </tr>
		
	      </tbody>
	      </table>
	</tr>
	<tr>
		
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>房产基本信息（独幢、户） </th>   
		<table class="tab_tk_pro2">
			    <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋坐落：</td>
                    <td colspan="1">
                      <input type="text" class="tab_text validate[required]" maxlength="60"
			name="FW_ZL" value="${busRecord.FW_ZL}" style="width: 72%;"  />
                    </td>
                     <td class="tab_width">幢号：</td>
                     <td colspan="3">
                       <input type="text"  class="tab_text validate[]" maxlength="60"
			name="FW_ZH" value="${busRecord.FW_ZH}"  />
                    </td>
                </tr>
                 <tr>
                    <td class="tab_width">所在层：</td>
                    <td colspan="1">
                         <input type="text" class="tab_text validate[required]" maxlength="60"
			name="FW_SZC" value="${busRecord.FW_SZC}" />
                    </td>
                    <td class="tab_width">户号：</td>
					<td>
						<input type="text"  class="tab_text validate[required]"  maxlength="60" 
			 name="FW_HH" value="${busRecord.FW_HH}"    /> 
					</td>
                </tr>
				
				<tr>
					
					<td class="tab_width">总层数：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="FW_ZCS" style="float: left;" name="FW_ZCS" value="${busRecord.FW_ZCS}">
					</td>
					<td class="tab_width"><font class="tab_color">*</font>规划用途：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="FW_GHYT" style="float: left;" name="FW_GHYT" value="${busRecord.FW_GHYT}">
					</td>
				</tr>
				<tr>
                    <td class="tab_width">竣工时间：</td>
                    <td colspan="1">
                       <input type="text" class="tab_text validate[]" maxlength="128" id="JGSJ" style="float: left;" name="JGSJ" value="${busRecord.JGSJ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>	用途说明：</td>
					<td>
				      <input type="text" class="tab_text validate[required]" maxlength="128" id="YTSM" style="float: left;" name="YTSM" value="${busRecord.YTSM}">

					</td>
                </tr>
                	<tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋性质：</td>
                    <td colspan="1">
                  	 <input type="text" class="tab_text validate[required]" maxlength="128" id="FW_XZ" style="float: left;" name="FW_XZ" value="${busRecord.FW_XZ}">
                    </td>
                    <td class="tab_width">	<font class="tab_color">*</font>房屋结构：</td>
					<td>
			          <input type="text" class="tab_text validate[required]" maxlength="128" id="FW_JG" style="float: left;" name="FW_JG" value="${busRecord.FW_JG}">
					</td>
                </tr>
				<tr>					
					<td class="tab_width">交易价格：</td>
					<td><input type="text" class="tab_text validate[required]" id="JYJG" name="JYJG" maxlength="32" value="${busRecord.JYJG}"></td>
					<td class="tab_width">独用土地面积：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="DYTDMJ" style="float: left;" name="DYTDMJ" value="${busRecord.DYTDMJ}">
					</td>
				</tr>
				<tr>
					<td class="tab_width">分摊土地面积：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="FTTDMJ" style="float: left;" name="FTTDMJ" value="${busRecord.FTTDMJ}">
					</td>
					<td class="tab_width">建筑面积：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="JZMJ" style="float: left;" name="JZMJ" value="${busRecord.JZMJ}">
					</td>
				</tr>
				<tr>					
					<td class="tab_width">房屋共有情况：</td>
					<td><input type="text" class="tab_text validate[required]" name="FWGYQK" maxlength="128" value="${busRecord.FWGYQK}"></td>
					<td class="tab_width">专有建筑面积：</td>
					<td>
					 <input type="text" class="tab_text validate[]" maxlength="128" id="ZYJZMJ" style="float: left;" name="ZYJZMJ" value="${busRecord.ZYJZMJ}">

					</td>
				</tr>
				<tr>
					<td class="tab_width">分摊建筑面积：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="FTJZMJ" style="float: left;" name="FTJZMJ" value="${busRecord.FTJZMJ}">
					</td>
					<td class="tab_width">权利类型：</td>
					<td>
					<input type="text" class="tab_text validate[]" maxlength="128" id="QLLX" style="float: left;" name="QLLX" value="${busRecord.QLLX}">

					</td>
				</tr>
				<tr>
					<tr>
					<td class="tab_width">土地使用权人：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="TDSYQR" style="float: left;" name="TDSYQR" value="${busRecord.TDSYQR}">
					</td>
					<td class="tab_width">权证号：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="128" id="QZH" style="float: left;" name="QZH" value="${busRecord.QZH}">
					</td>
					
				</tr>
					<tr>
					<td colspan='1' class="tab_width">备注：</td>
					<td colspan='5'>
					  <textarea  type="text" class="tab_text validate[]" maxlength="128" id="FW_BZ" style="float: left;" name="FW_BZ" value="${busRecord.FW_BZ}">
					   </textarea>
                </td>
					
					
				</tr>
				</tr>
				
				
					
				
				
				
			</table>
	</tr>
	<tr>
		<input type="hidden" name="DYQDJ_FILE_URL" />
				<input type="hidden" name="DYQDJ_FILE_ID" />
				
	</tr>
	<tr>
		<td colspan="3">
						<div style="width:100%;" id=DYQDJ_fileListDiv></div>
					</td>
					</tr>
</table>	