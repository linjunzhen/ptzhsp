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
<!-- 家庭住房情况书面查询结果开始 -->
<div id="jtzfSmxx" name="jtzfSmxx" style="display:none">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>家庭住房情况书面查询结果信息</span>
			</th>
		</tr>
        <tr>
            <td style="padding: 10px">
                  <table cellpadding="0" cellspacing="1" class="bstable1" >
                      <tr>
                          <th style="width:20%" class="tab_width"><font class="tab_color">*（必传）</font>上传附件：</th>
                          <td colspan='3'>
                              <div onclick="openJtzfSmxxFileUploadDialog();" style="margin-left: 10px;cursor: pointer;">
                           <img src="webpage/bsdt/applyform/images/tab_btn_sc.png"
                                style="width:60px;height:22px;"/>
                        </div>
                        <div style="margin-left: 10px;">
                            <span id=fileListDiv2></span>
                        </div>
                          </td>
                      </tr>
                  </table>
              </td>
          </tr>
      </table>
</div>
<!-- 家庭住房情况书面查询结果结束 -->

