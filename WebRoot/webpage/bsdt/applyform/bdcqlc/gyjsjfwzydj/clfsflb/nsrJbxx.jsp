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
    	//初始化家庭住房条件
    	/* $('input[name="NSR_ZFQK"]').each(function() {
			var checked = '${busRecord.NSR_ZFQK}';
			if(checked.indexOf($(this).val())>=0){
		        $(this).attr('checked', true);
			}
		}); */
		
    });
    
    function setValidate(zjlx){
		if(zjlx=="SF"){
			$("#NSR_ZJLX").addClass(",custom[vidcard]");
		}else{
			$("#NSR_ZJLX").removeClass(",custom[vidcard]");
		}
	}
	
	//动态添加未成年子女信息
	var childrenSn = 1;
	function addChidrenXxDiv(){
		childrenSn = childrenSn+1;
		var innerHTML = "<div  style=\"position:relative;margin:30px;\" > ";
	    innerHTML += $("#chidrenXxDiv").children("div").eq(0).html();
	    innerHTML += "</div>";
		var findex = innerHTML.indexOf("children_");
		var replacestr = innerHTML.substring(findex,findex+10);
		innerHTML = innerHTML.replace(replacestr,"children_"+childrenSn);
		$("#chidrenXxDiv").append(innerHTML);
	}
	//删除未成年子女信息
	function delChidrenXxDiv(o){
	      var length = $("#chidrenXxDiv div").find("[name$='NSR_ZNXM']").length;
		  if(length>1){
		  	$(o).closest("div").remove();
		  }else{
		  	art.dialog({
		          content : "至少一个未成年子女信息！",
		          icon : "warning",
		          ok : true
		      });
		  }
	}
	
</script>
<!-- 纳税人信息开始 -->
<div id="nsrxx" name="nsrxx" style="display:none">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>纳税人承诺信息 </span>
			</th>
		</tr>
        <tr>
              <td style="padding: 10px">
                  <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                      <tr>
                          <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
                          <td>
                              <input type="text" class="tab_text validate[required]" maxlength="30" id="NSR_XM" name="NSR_XM"
                                     value="${busRecord.NSR_XM}" />
                          </td>
                          <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                          <td>
                              <eve:eveselect clazz="tab_text sel validate[required]" onchange="setValidate(this.value);"
							dataParams="DocumentType" value="${busRecord.NSR_ZJLX}"
							dataInterface="dictionaryService.findDatasForSelect" id="NSR_ZJLX"
							defaultEmptyText="====选择证件类型====" name="NSR_ZJLX"></eve:eveselect>
                          </td>
                      </tr>
                      <tr>
                          <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
                          <td colspan="3">
                              <input type="text" class="tab_text validate[required] " maxlength="30" id="NSR_ZJHM" style="float: left;"
                                     name="NSR_ZJHM" value="${busRecord.NSR_ZJHM}" />
                          </td>
                      </tr>
                      <tr>
                          <td class="tab_width"><font class="tab_color">*</font>房产地址：</td>
                          <td colspan="3">
                              <input type="text" class="tab_text validate[required] " maxlength="128" id="NSR_FCDZ" style="float: left;"
                                     name="NSR_FCDZ" value="${busRecord.NSR_FCDZ}" />
                          </td>
                      </tr>
                      <tr>
                      	<td class="tab_width"><font class="tab_color">*</font>家庭住房情况：</td>
                          <td colspan="3">
                              <input type="radio" class="validate[required]" name="NSR_ZFQK"  value="0" <c:if test="${busRecord.NSR_ZFQK=='0'}">checked="checked"</c:if> >个人购买家庭（成员范围包括购房人、配偶以及未成年子女，下同）唯一住房，面积为90平方米及以下；
                           	<br/>
						<input type="radio" class="validate[required]" name="NSR_ZFQK"  value="1" <c:if test="${busRecord.NSR_ZFQK=='1'}">checked="checked"</c:if> >个人购买家庭唯一住房，面积为90平方米以上；
						<br/>
						<input type="radio" class="validate[required]" name="NSR_ZFQK"  value="2" <c:if test="${busRecord.NSR_ZFQK=='2'}">checked="checked"</c:if> >个人购买家庭第二套改善性住房，面积为90平方米及以下；
						<br/>
						<input type="radio" class="validate[required]" name="NSR_ZFQK"  value="3" <c:if test="${busRecord.NSR_ZFQK=='3'}">checked="checked"</c:if> >个人购买家庭第二套改善性住房，面积为90平方米以上；
						<br/>
						<input type="radio" class="validate[required]" name="NSR_ZFQK"  value="4" <c:if test="${busRecord.NSR_ZFQK=='4'}">checked="checked"</c:if> >个人首次购买90平方米及以下改造安置住房；
						<br/>
						<input type="radio" class="validate[required]" name="NSR_ZFQK"  value="5" <c:if test="${busRecord.NSR_ZFQK=='5'}">checked="checked"</c:if> >个人首次购买90平方米以上符合普通住房标准的改造安置住房。
                          </td>
                      </tr> 
                      <tr>
                          <td class="tab_width">配偶姓名：</td>
                          <td>
                              <input type="text" class="tab_text" maxlength="30" id="NSR_POXM" style="float: left;"
                                     name="NSR_POXM" value="${busRecord.NSR_POXM}" />
                          </td>
                          <td class="tab_width">配偶身份证件号码：</td>
                          <td >
                              <input type="text" class="tab_text" maxlength="30" id="NSR_POZJHM" style="float: left;"
                                     name="NSR_POZJHM" value="${busRecord.NSR_POZJHM}" />
                          </td>
                      </tr>
                      <tr>
                      	<td colspan='4'>
                      		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="nsrChidrenXx">
								<tr>
									<th colspan="4"  style="background-color: #FFD39B;">未成年子女信息</th>
								</tr>
								<tr id="children_1">
									<td>
										<table class="tab_tk_pro2">
											<tr>
												<td>姓名：</td>
												<td>
													<input type="text" class="tab_text " name="NSR_ZNXM"   maxlength="30" />
												</td>
												<td>身份证件号码：</td>
												<td>	
													 <input type="text" class="tab_text" maxlength="30" id="NSR_ZNZJHM" 
					                                       name="NSR_ZNZJHM"  />
												</td>
											</tr>
										</table>
										<div class="tab_height2"></div>
									</td>
									<td>
										<br>
										<input type="button" class="eflowbutton"name="deleteCurrentChildren" value="删除行" onclick="deleteChildren('1');">
										<br>
										<br>
										<input type="button" class="eflowbutton" name="addOneChildren" value="增加一行" onclick="addChildren();">
									</td>
								</tr>
							</table>
                      	</td>
                      </tr>	
                    </table>
             </td>
         </tr>
    </table>
</div>
<!-- 纳税人信息基本结束 -->

