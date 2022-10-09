<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
        loadres="jquery,easyui,apputil,layer,validationegine,ztree,artdialog,laydate"></eve:resources>



<script>
    $(function () {
        $(".payNum").change(function () {
            var paySinglePrice = $(this).parent().parent().find(".paySinglePrice").text();
            var payNum = $(this).val();
            var payPrice = paySinglePrice * payNum;
            $(this).parent().parent().find(".payPrice").text(payPrice);
        })
    })
    function savePayData() {
        var checkRow =  $("input[type='checkbox']:checked");
        var flag = true;
        $(".userForm").each(function (index,ele) {
            if (!$(this).val()) {
                art.dialog({
                    content: "请填写" + $(this).parent().prev().html(),
                    icon:"warning",
                    time:3,
                    ok:true
                })
                flag = false;
                return false;
            }
        })
        if (flag) {
            if (checkRow.length > 0) {
                var arr = [];
                var EXE_ID;
                $.each(checkRow,function (k, v) {
                    var payNum = $(v).parent().parent().find(".payNum").val();
                    var RECORD_ID = $(v).parent().parent().find(".RECORD_ID").val();
                    EXE_ID = $(v).parent().parent().find(".EXE_ID").val();
                    var json = {"payNum":payNum,"RECORD_ID":RECORD_ID, "EXE_ID": EXE_ID};
                    arr.push(json);
                })
                var arrStr = JSON.stringify(arr);
                var BEGINTIME = $("input[name='BEGINTIME']").val();
                var ENDTIME = $("input[name='ENDTIME']").val();
                var REMARK = $("input[name='REMARK']").val();
                var PROCESSEDOPINION = $("input[name='PROCESSEDOPINION']").val();
                $.post("payController/initiatePayment.do",
                    {payStr:arrStr,EXE_ID:EXE_ID,BEGINTIME:BEGINTIME,ENDTIME:ENDTIME,REMARK:REMARK,PROCESSEDOPINION:PROCESSEDOPINION},
                    function (data) {
                    if (data) {
                        var dataObj = JSON.parse(data);
                        if (dataObj.success) {
                            parent.art.dialog({
                                content: dataObj.msg,
                                icon:"succeed",
                                time:3,
                                ok: true
                            });
                            AppUtil.closeLayer();
                        }
                    }
                })
            } else {
                art.dialog({
                    content:"请选择至少一条收费清单",
                    icon:"warning",
                    time:3,
                    ok:true
                })
            }
        }
    }


    /*时间设置*/
    $(document).ready(
        function() {
            var start1 = {
                elem : "#SysLogL.BEGINTIME_PAYLIST",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='BEGINTIME']")
                        .val();
                    var endTime = $("input[name='ENDTIME']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("开始时间必须小于等于结束时间,请重新输入!");
                            $("input[name='BEGINTIME']").val("");
                        }
                    }
                }
            };
            var end1 = {
                elem : "#SysLogL.ENDTIME_PAYLIST",
                format : "YYYY-MM-DD",
                istime : false,
                choose : function(datas) {
                    var beginTime = $("input[name='BEGINTIME']")
                        .val();
                    var endTime = $("input[name='ENDTIME']")
                        .val();
                    if (beginTime != "" && endTime != "") {
                        var start = new Date(beginTime.replace("-", "/")
                            .replace("-", "/"));
                        var end = new Date(endTime.replace("-", "/")
                            .replace("-", "/"));
                        if (start > end) {
                            alert("结束时间必须大于等于开始时间,请重新输入!");
                            $("input[name='ENDTIME']").val("");
                        }
                    }
                }
            };
            laydate(start1);
            laydate(end1);
        });

</script>

<body class="eui-diabody" style="margin:0px;">
<form id="payListDealItemForm" method="post" action="">
    <div id="payListDealItemFormDiv" data-options="region:'center',split:true,border:false">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="payListTable"  >
            <tr style="height:29px;">
                <td colspan="6" class="dddl-legend"><div class="eui-dddltit"><a>收费清单</a></div></td>
            </tr>
            <tr>
                <td style="text-align: center;font-weight: bold;width:6%;"></td>
                <td style="text-align: center;font-weight: bold;width:6%;">序号</td>
                <td style="text-align: center;font-weight: bold;width:32%;">收费科目</td>
                <td style="text-align: center;font-weight: bold;width:18%;">收费金额（单位：元）</td>
                <td style="text-align: center;font-weight: bold;width:18%;">执收标准（单位：元）</td>
                <td style="text-align: center;font-weight: bold;width:20%;">数量（默认1）</td>
            </tr>
            <c:forEach items="${payList}" var="payList">
                <tr>
                    <td style="text-align: center;"> <input type="checkbox"></td>
                    <td style="display: none;"><input class="RECORD_ID" type="hidden" value="${payList.RECORD_ID}"></td>
                    <td style="display: none;"><input class="EXE_ID" type="hidden" value="${payList.EXE_ID}"></td>
                    <td style="text-align: center;">${payList.rownum}</td>
                    <td style="text-align: center;">${payList.CHARGEDETAILNAME}</td>
                    <td style="text-align: center;" class="payPrice">${payList.CHARGEDETAILSTANDARD * payList.CHARGEDETAILAMOUNT}</td>
                    <td style="text-align: center;" class="paySinglePrice">${payList.CHARGEDETAILSTANDARD}</td>
                    <td style="text-align: center;"><input class="payNum" type="number" style="height: 26px;text-align: center;" value="${payList.CHARGEDETAILAMOUNT}"></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div style="margin-top: 40px">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" >
            <tr>
                <th><span style="color:red;">*</span> 缴费开始时间</th>
                <td>
                    <input type="text"
                           style="width:128px;float:left;" class="laydate-icon userForm"
                           id="SysLogL.BEGINTIME_PAYLIST" name="BEGINTIME" />
                </td>
                <th><span style="color:red;">*</span> 缴费（预估）结束时间</th>
                <td>
                    <input type="text"
                           style="width:128px;float:left;" class="laydate-icon userForm"
                           id="SysLogL.ENDTIME_PAYLIST" name="ENDTIME" />
                </td>
            </tr>
            <tr>
                <th><span style="color:red;">*</span> 办理意见</th>
                <td><input type="text" class="eve-input userForm" name="PROCESSEDOPINION"></td>
                <th>备注</th>
                <td><input type="text" class="eve-input" name="REMARK"></td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south'" style="height:46px;" >
        <div class="eve_buttons">
            <input value="确定" type="button"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="savePayData();"/> <input
                value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
                onclick="AppUtil.closeLayer();" />
        </div>
    </div>
</form>

</body>