<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
    <style>
        .layout-split-south{border-top-width:0px !important;}
        .eve_buttons{position: relative !important;}
    </style>
    <script type="text/javascript">

        //确定按钮
        function doSelectRows(){
            var rows = $("#fdchtbaxxcxGrid").datagrid("getChecked");
            var allowCount = $("input[name='allowCount']").val();
            if((rows.length>allowCount)&&allowCount!=0){
                alert("最多只能选择"+allowCount+"条记录!");
                return;
            }
            //获取指定不动产单元号对应的房屋产权状态
            if(rows.length>0){
                var data=[];
                $.post("<%=basePath%>/bdcYgspfygdjController.do?getCqztByBdcdyh",{bdcdyh:rows[0].BDCDYH},
                    function(responseText, status, xhr) {
                        var resultJson = $.parseJSON(responseText);
                        if(resultJson.success == true){
                            var obj = JSON.parse(resultJson.jsonString);
                            for(var i=0;i<obj.length;i++){
                                if(obj[i].CQZT.indexOf("正常")!=-1){
                                    data.push(rows[i]);
                                    art.dialog.data("fdchtbaxxInfo",data);
                                    AppUtil.closeLayer();
                                }else{
                                    data.push(rows[i]);
                                    //询问是否需要办理此登记
                                    parent.art.dialog.confirm("请注意，该不动产单元号为"+obj[i].CQZT+"状态，是否继续办理业务?", function() {
                                        art.dialog.data("fdchtbaxxInfo",data);
                                        AppUtil.closeLayer();
                                    });

                                }
                            }
                        }
                    });
            }else{
                AppUtil.closeLayer();
            }

        }

        //去除字符串的空串
        function Trim(str)
        {
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }
        /*序号列宽度自适应-----开始-----*/
        function fixRownumber(){
            var grid = $('#fdchtbaxxcxGrid');
            var options = grid.datagrid('getPager').data("pagination").options;
            var maxnum = options.pageNumber*options.pageSize;
            var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ maxnum + '</span>').hide().appendTo(document.body);
            $(currentObj).css('font', '12px, Microsoft YaHei');
            var width = currentObj.width();
            var panel = grid.datagrid('getPanel');
            if(width>25){
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width+5);
                grid.datagrid("resize");
            }else{
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(25);
                grid.datagrid("resize");
            }
        }
        $('#fdchtbaxxcxGrid').datagrid({
            onLoadSuccess: fixRownumber
        });

        //查询
        function search(){
            $("#fdchtbaxxcxGrid").datagrid('clearChecked');
            var count=1;
            var fwmmhth = $("input[name='fwmmhth']").val();
            var msfzjhm = $("input[name='msfzjhm']").val();
            if (!(fwmmhth && fwmmhth != "")) {
                art.dialog({
                    content : "请填写合同编号。",
                    icon : "warning",
                    ok : true
                });
                return;
            }
            if (!(msfzjhm && msfzjhm != "")) {
                art.dialog({
                    content : "请填写买受人证件号码。",
                    icon : "warning",
                    ok : true
                });
                return;
            }
            AppUtil.gridDoSearch('fdchtbaxxcxToolbar','fdchtbaxxcxGrid');
            $('#fdchtbaxxcxGrid').datagrid({
                onLoadSuccess:function(data){
                    //确保数据初始化后只执行一次该方法
                    if((count=='1')&& data.total==0){
                        art.dialog({
                            content : "无匹配数据返回，查询记录为空。",
                            icon : "warning",
                            ok : true
                        });
                        count ++;
                    }
                }
            });
        }
    </script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<input type="hidden" name="allowCount" value="${allowCount}">
<div class="easyui-layout" fit="true" >
    <div data-options="region:'center',split:false" >
        <div id="fdchtbaxxcxToolbar">
            <form action="#" name="fdchtbaxxcxForm">
                <!--====================开始编写隐藏域============== -->

                <!--====================结束编写隐藏域============== -->
                <table class="dddl-contentBorder dddl_table"
                       style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tr style="height:28px;">
                        <td style="width:120px;text-align:right;"><font class="tab_color">*</font>合同编号：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="fwmmhth" /></td>
                        <td style="width:120px;text-align:right;">预售证号：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="ysxkzbh" /></td>
                        <td style="width:120px;text-align:right;">买受人名称：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="msfxm" /></td>
                    </tr>
                    <tr style="height:28px;">
                        <td style="width:120px;text-align:right;"><font class="tab_color">*</font>买受人证件号码：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="64" style="width:300px;"
                                                        name="msfzjhm" /></td>
                        <td style="width:120px;text-align:right;">项目名称：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="xmmc" /></td>
                        <td style="width:120px;text-align:right;">出卖人名称：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="zrfxm" /></td>
                    </tr>
                    <tr>
                        <td style="width:120px;text-align:right;">房屋坐落：</td>
                        <td style="width:300px;"><input class="eve-input"
                                                        type="text" maxlength="50" style="width:300px;"
                                                        name="zl" /></td>
                        <td colspan="4"><input type="button" value="查询"
                                               class="eve-button"
                                               onclick="search()" />
                            <input type="button" value="重置" class="eve-button"
                                   onclick="AppUtil.gridSearchReset('fdchtbaxxcxForm');" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- =========================查询面板结束========================= -->

        <!-- =========================表格开始==========================-->
        <table class="easyui-datagrid" rownumbers="true" pagination="false"
               id="fdchtbaxxcxGrid" fitColumns="true" toolbar="#fdchtbaxxcxToolbar"
               method="post" idField="FWMMHTH" checkOnSelect="false"
               selectOnCheck="false" fit="true" border="false" nowrap="false"
               url="bdcYgspfygdjController.do?fdchtbaxxcxDatagrid&noAuth=${noAuth}">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'FWMMHTH',align:'left'" width="50">合同编号</th>
                <th data-options="field:'BDCDYH',align:'left'" width="120">不动产单元号</th>
                <th data-options="field:'ZL',align:'left'" width="120">房屋坐落</th>
                <th data-options="field:'MSFXM',align:'left'" width="40">买受方</th>
                <th data-options="field:'MSFZJHM',align:'left'" width="70">买受方证件号码</th>
                <th data-options="field:'ZRFXM',align:'left'" width="60">出卖方</th>
                <th data-options="field:'ZRFZJHM',align:'left'" width="80">出卖方证件号码</th>
            </tr>
            </thead>
        </table>

    </div>


    <div data-options="region:'south',split:true,border:false"  >
        <div class="eve_buttons">
            <input value="确定" type="button" onclick="doSelectRows();"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
            <input
                    value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                    onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</div>

</body>
</html>

