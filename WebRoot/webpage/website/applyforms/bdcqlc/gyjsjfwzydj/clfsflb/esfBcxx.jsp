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
<div class="bsbox clearfix" id="esfbcxx" style="display:none">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>存量房评估补充信息</span></li>
        </ul>
    </div>
    <div name="esfbcxx">
        <table cellpadding="0" cellspacing="1" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table cellpadding="0" cellspacing="1" class="bstable1" >
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>房产地址：</th>
                            <td>
                                <input type="text" class="tab_text validate[required]" maxlength="128" id="CLF_FCDZ" name="CLF_FCDZ"
                                       value="${busRecord.CLF_FCDZ}" />
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>装修情况：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel validate[required]" 
									dataParams="clfzxqk" value="${busRecord.CLF_ZXQK}"
									dataInterface="dictionaryService.findDatasForSelect" id="CLF_ZXQK"
									defaultEmptyText="====选择装修情况====" name="CLF_ZXQK"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">自建房采光情况：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel" 
									dataParams="clfcgqk" value="${busRecord.CLF_CGQK}"
									dataInterface="dictionaryService.findDatasForSelect" id="CLF_CGQK"
									defaultEmptyText="==选择自建房采光情况==" name="CLF_CGQK"></eve:eveselect>
                            </td>
                            <th class="tab_width">自建房、别墅是否有电梯：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel" 
									dataParams="clfdtqk" value="${busRecord.CLF_DTQK}"
									dataInterface="dictionaryService.findDatasForSelect" id="CLF_DTQK"
									defaultEmptyText="====选择是否有无电梯====" name="CLF_DTQK"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">自建房沿街情况：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel" 
									dataParams="clfyjqk" value="${busRecord.CLF_YJQK}" 
									dataInterface="dictionaryService.findDatasForSelect" id="CLF_YJQK"
									defaultEmptyText="==选择自建房沿街情况==" name="CLF_YJQK"></eve:eveselect>
                            </td>
                            <th class="tab_width"><font class="tab_color" id="ljztFont" style="display:none" ></font>店面临街状态：</th>
                            <td>
                                <eve:eveselect clazz="tab_text sel " 
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
</div>
<!-- 存量房评估补充信息结束 -->

