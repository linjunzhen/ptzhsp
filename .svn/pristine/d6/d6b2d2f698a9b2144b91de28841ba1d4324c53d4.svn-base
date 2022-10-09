<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=9;IE=8" />
    <title>文章编辑</title>
    <!-- jquery begin -->
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery3.min.js"></script>
    <!-- jquery end -->
	<eve:resources loadres="laydate,layer,artdialog"></eve:resources>
    
    <!-- jqueryEasyui begin -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/jquery/plugin/jqueryEasyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/jquery/plugin/jqueryEasyui/themes/icon.css" />
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryEasyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryEasyui/jquery.easyui.ext.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryEasyui/locale/easyui-lang-zh_CN.js"></script>
    <!-- jqueryEasyui end -->

    <!-- jqueryZtree begin -->
    <link href="<%=path%>/plug-in/jquery/plugin/jqueryZtree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryZtree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryZtree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryZtree/js/jquery.ztree.exedit-3.5.min.js"></script>
    <!-- jqueryZtree end -->
    
    <!-- jqueryUpload begin -->
    <link href="<%=path%>/plug-in/jquery/plugin/jqueryUpload/theme/default/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryUpload/jquery.uploadImg.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery/plugin/jqueryUpload/jquery.uploadPanel.js"></script>
    <!-- jqueryUpload end -->
    
    <!-- jqueryJcrop begin -->
    <link rel="stylesheet" href="<%=path%>/plug-in/jcrop/css/jquery.Jcrop.min.css" type="text/css" />
    <script type="text/javascript" src="<%=path%>/plug-in/jcrop/js/jquery.Jcrop.min.js"></script>
    <!-- jqueryJcrop end -->
    
    <!-- my97 begin -->
    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <!-- my97 end -->
    
    <!-- juicer begin -->
    <script type="text/javascript" src="<%=path%>/plug-in/juicer/juicer-0.6.5.min.js"></script>
    <!-- juicer end -->

	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <link href="<%=path%>/webpage/cms/article/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/webpage/cms/article/scripts/infoForm.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/cms/article/scripts/add.js"></script>
    <script type="text/javascript">
   $().ready(function() {
	//   alert(${IsPost});
	   });
   </script>
</head>

<body style="background:#e9eff3">
    <form id="articleFrom" method="post" >
        <!-- 隐藏域 -->
        <input type="hidden" id="rootPath" value="<%=path%>" />
        <input id="contentSiteId" type="hidden" value="1" />
        <input type="hidden" name="CONTENT_ID" value="${content.CONTENT_ID}"/>
        <input type="hidden" name="CONTENT_DELETE" value="0" />
        <input type="hidden" name="CONTENT_MODAL" value="0" />
        <input type="hidden" name="CONTENT_STATUS" value="-1" />
        <input type="hidden" name="CREATEUSERNAME" value="${sessionScope.curLoginUser.fullname}" />
        <input type="hidden" name="CREATEUSERID" value="${sessionScope.curLoginUser.userId}" />
        <input type="hidden" name="attachFileIds" />
        <input type="hidden" name="ISPUBLIC" id="content.isPublic" value="1" />
		<input type="hidden" id="curdate" value="${curdate}"/>
        <!-- 隐藏域 -->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td> 
                   <div class="editor_top">
                        <table width="615px" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <th>标题：</th>
                            <td colspan="4"><input type="text" id="title" name="CONTENT_TITLE" class="easyui-validatebox" data-options="required:true,validType:'maxLength[150]'" style="width:445px" /><span>*</span></td>
                          </tr>
						  <tr>
                            <th>副标题：</th>
                            <td colspan="4"><input type="text" id="subhead" name="CONTENT_SUBHEAD" class="easyui-validatebox" data-options="validType:'maxLength[150]'" style="width:445px" /></td>
                          </tr>
                          <tr>
                            <th>所属栏目：</th>
                            <td>
                                <input type="text" id="moduleName" name="moduleName" class="easyui-validatebox" data-options="required:true" style="width:170px" readonly="readonly" value="${content.MODULE_NAME}"/><span>*</span>
                                <input type="hidden" id="moduleNameHidden" name="MODULE_ID" value="${content.MODULE_ID}"/>
                            </td>
                            <th>类型：</th>
                            <td>
                                <select id="selContentType" name="CONTENTTYPE" style="width:174px">
                                    <option value="0">信息类型</option>
                               <option value="1">文件类型</option>
                                    <option value="2">网页链接</option>
                                    <!--<option value="3">视频新闻</option>-->
                                </select><span>*</span>
                            </td>
                            <th></th>
                          </tr>
                          <tr>
                            <th>责任编辑：</th>
                            <td><input type="text" name="AUTHOR" class="easyui-validatebox" data-options="required:true" style="width:170px" value="${sessionScope.curLoginUser.fullname}" readonly="readonly"/><span>*</span></td>
                            <th></th>
                          </tr>
                          <tr id="trLinkUrl" style="display:none">
                            <th>网页链接：</th>
                            <td colspan="3"><input type="text" class="easyui-validatebox" data-options="validType:'maxLength[2000]'"  name="LINKURL" style="width:442px" /></td>
                            <th></th>
                          </tr>
                          <tr id="trFileUrl" style="display:none">
                            <th>文件地址：</th>
                            <td colspan="3"><input type="text" class="easyui-validatebox" data-options="validType:'maxLength[2000]'"  value="${content.LINKURL}" name="LINKURL" style="width:442px" readonly="readonly"/><span>*</span></td>
                            <th>
								<input type="button" value="上传文件..." id="openAttach"/>							
								<input type="hidden"  name="FILENAME" value="${content.FILENAME}"/>
                                <input type="hidden"  name="FILEURL" value="${content.FILEURL}"/>

                            </th>
                          </tr>
                          <tr id="trVideoUrl" style="display:none">
                            <th>视频地址：</th>
                            <td colspan="3"><input type="text" class="easyui-validatebox" data-options="validType:'maxLength[2000]'"  value="${content.VIDEOURL}" name="VIDEOURL" style="width:442px" readonly="readonly" /><span>*</span></td>
                            <th>
								<input type="button" value="上传视频..." id="openVideo"/>													
								<input type="hidden"  name="VIDEONAME" value="${content.VIDEONAME}"/>
							</th>
                          </tr>
                        </table>
                    </div>
                </td>
                <td valign="top" width="280" rowspan="2">
                    <div class="editor_boxR">
                        <div class="editor_titleR" width="280">基本属性</div>
                        <div class="editor_table"> 
                            <table border="0" cellspacing="0" cellpadding="0"> 
                                <tr>
                                    <th><span style="color: red">*</span> 来源：</th>
                                    <td> 
                                        <input id="source" name="COMEFROM" type="text" class="easyui-validatebox" data-options="required:true,validType:'maxLength[30]',tipPosition:'left'" style="width:170px"  />
                                    </td>
                                </tr>
                                <tr>
                                    <th><span style="color: red">*</span> 发布时间：</th>
                                    <td>
                                        <input type="text" style="width:172px"  readonly="ture" name="RELEASE_TIME" value="${curdate}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'${curdate}'})"/>
                                    </td>
                                </tr>   
                                <tr>
                                    <th width="80">关键字：</th>
                                    <td><input type="text" class="easyui-validatebox" name="KEYWORD" style="width:170px"  data-options="required:false,validType:'maxLength[1000]',tipPosition:'left'"/></td>
                                </tr>                            
                                <!--<tr>
                                    <th>引至栏目：</th>
                                    <td>  
                                        <input id="refNames" name="TARGET_MODULE_NAME" type="text" class="easyui-validatebox" style="width:115px;margin-right: 10px;" readonly="readonly" />
										<input id="btnQuote" type="button" value="删除" />
                                        <input id="refNamesHidden" name="TARGET_MODULE_ID" type="hidden" />
                                    </td>
                                </tr>-->
								<tr>
                                    <th width="80">是否置顶：</th>
                                    <td>
                                        <select id="isTop" name= "ISTOP" style="width:172px">
                                            <option value="1">是</option>
                                            <option value="0" selected="selected">否</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>是否备注：</th>
                                    <td>
                                        <label><input name="isNote" type="radio" value="1" class="editor_table_input" />是</label>
                                        <label><input name="isNote" type="radio" value="0" checked="checked" class="editor_table_input" />否</label>
                                    </td>
                                </tr>      
                                <tr id="trNote" style="display:none">
                                    <th>备注：</th>
                                    <td><textarea id="note" name="REMARK" cols="" rows="4" style="width:170px" class="easyui-validatebox" data-options="tipPosition:'left',validType:'maxLength[500]'"></textarea></td>
                                </tr>  
                               <!-- <tr>
                                    <th width="80">关键字：</th>
                                    <td><input type="text" class="easyui-validatebox" name="KEYWORD" style="width:170px" data-options="validType:'maxLength[30]'"/></td>
                                </tr>  -->
                                <tr class="hideCtrl">
                                    <th>摘要：</th>
                                    <td><textarea name="CONTENT_SUMMARY" rows="4" style="width:170px; font-size:12px" class="easyui-validatebox" data-options="tipPosition:'left',validType:'maxLength[500]'"></textarea></td>
                                </tr>
                                <tr class="hideCtrl">
                                    <th></th>
                                    <td><input id="btnAutoSummury" type="button" value="自动信息摘要"/></td>
                                </tr>
                                <tr class="hideCtrl">
                                    <th width="80">文章附件：</th>
                                    <td><input id="btnAttach" type="button" value="附件管理" /></td>
                                </tr>    
								<c:forEach items="${fieldList}" var="field">						
									<tr>
										<th width="80">${field.FIELD_NAME}：</th>
										<td><input type="text" class="easyui-validatebox" name="${field.FIELD_COLUMN}" value="${field.DEFAULT_VALUE}" style="width:170px"  data-options="required:false,validType:'maxLength[2000]',tipPosition:'left'"/></td>
									</tr>   
								</c:forEach>               
                            </table>
                        </div>
                        <div class="editor_titleR" width="280">缩略图属性</div>
                        <div class="editor_table" style="display:none">
                            <table border="0" cellspacing="0" cellpadding="0">                               
                                <tr>
                                    <th width="50">第一步：</th>
                                    <td>
										<input type="button" value="上传图片..." id="openImage">
                                    </td>
                                </tr>
                                <tr>
                                    <th>第二步：</th>
                                    <td><input id="btnimgdel" type="button" value="图片删除" /></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <!-- border-width:1px;border-style:solid;border-color: black;vertical-align: middle; -->
                                        <div style="width:260px; height: 190px; margin-left: 5px">
                                            <img id="titleImg_tag" style="max-width:260px; max-height:190px; display: none"/>
                                            <input id="artTitleImgUrl" type="hidden" name="TITLEIMG" />
                                        </div>
                                    </td>
                                </tr>                                
                            </table>
                        </div>
                    </div>
                </td>
            </tr>
            <tr  id="eWebEditContent" >
                <td valign="top">
                    <!-- 信息类型 -->
                    <input type="hidden" name="CONTENT_TEXT">
                    <iframe   id="eWebEditor1" src="<%=path%>/plug-in/ewebeditor/ewebeditor.htm?id=CONTENT_TEXT&style=standard600" frameborder="0" scrolling="no" width="" height=""></IFRAME>
                    <div id="ewebmain" style="display: none">
                    </div>
                </td>
            </tr>
            <tr >
                <td valign="top">
                    <div class="editor_btn">
                        <ul>
                            <li>
                                <a id="btnWord" href="javascript:void(0);"><img src="<%=path%>/webpage/cms/article/images/editor_btn2.jpg" /></a> 
                                <a id="btnExcel" href="javascript:void(0);"><img src="<%=path%>/webpage/cms/article/images/editor_btn3.jpg" /></a> 
								<a id="btnOnekey" href="javascript:void(0);"><img src="<%=path%>/webpage/cms/article/images/editor_btn4.jpg" /></a> 
                                <a id="btnSave" href="javascript:void(0);"><img src="<%=path%>/webpage/cms/article/images/editor_btn10.jpg" /></a> 
                                <a id="btnClose" href="javascript:void(0);"><img src="<%=path%>/webpage/cms/article/images/editor_btn11.jpg" /></a>
                            </li>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </form> 
    <div id="artModuleDialog">
        <UL id="artModuleTree" class=ztree></UL>
    </div>
    <div id="artSpecialDialog">
        <UL id="artSpecialTree" class=ztree></UL>
    </div>
    <div id="artRefModuleDialog">
        <UL id="artRefModuleTree" class=ztree></UL>
    </div>
    <div id="infoSourceDialog">
        <UL id="infoSourceTree" class=ztree></UL>
    </div>
    <div id="attachDialog">
        <input id="attachPanel" type="file" style="display:none"/>
    </div>
</body>
</html>
