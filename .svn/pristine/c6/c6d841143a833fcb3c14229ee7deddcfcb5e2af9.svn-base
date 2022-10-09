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
    
	
</script>
<!-- 存量房评估补充信息开始 -->
<div id="esfbcxx" name="esfbcxx" style="display:none">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>存量房评估补充信息</span>
			</th>
		</tr>
        <tr>
            <td style="padding: 10px">
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>房产地址：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="128" id="CLF_FCDZ" name="CLF_FCDZ"
                                   value="${busRecord.CLF_FCDZ}" />
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>装修情况：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[required]" 
					dataParams="clfzxqk" value="${busRecord.CLF_ZXQK}"
					dataInterface="dictionaryService.findDatasForSelect" id="CLF_ZXQK"
					defaultEmptyText="====选择装修情况====" name="CLF_ZXQK"></eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">自建房采光情况：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[]" 
					dataParams="clfcgqk" value="${busRecord.CLF_CGQK}"
					dataInterface="dictionaryService.findDatasForSelect" id="CLF_CGQK"
					defaultEmptyText="==选择自建房采光情况==" name="CLF_CGQK"></eve:eveselect>
                        </td>
                        <td class="tab_width">自建房、别墅是否有电梯：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[]" 
					dataParams="clfdtqk" value="${busRecord.CLF_DTQK}"
					dataInterface="dictionaryService.findDatasForSelect" id="CLF_DTQK"
					defaultEmptyText="====选择是否有无电梯====" name="CLF_DTQK"></eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">自建房沿街情况：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[]" 
					dataParams="clfyjqk" value="${busRecord.CLF_YJQK}"
					dataInterface="dictionaryService.findDatasForSelect"  id="CLF_YJQK"
					defaultEmptyText="==选择自建房沿街情况==" name="CLF_YJQK"></eve:eveselect>
                        </td>
                        <td class="tab_width"><font class="tab_color" id="ljztFont" style="display:none" >*</font>店面临街状态：</td>
                        <td>
                            <eve:eveselect clazz="tab_text sel validate[]" 
					dataParams="clfljzt" value="${busRecord.CLF_LJZT}"
					dataInterface="dictionaryService.findDatasForSelect" id="CLF_LJZT"
					defaultEmptyText="====选择店面临街状态====" name="CLF_LJZT"></eve:eveselect>
                        </td>
                   </tr>
                </table>
            </td>
        </tr>
    </table>
 </div>
<!-- 存量房评估补充信息结束 -->

