<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" style="font-size: 67px;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>全国一体化在线政务服务平台“好差评”</title>
    <script src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
    <script src="<%=path%>/webpage/callnew/queuing/js/sign.js"></script>
    <script src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script>
    <script src="<%=path%>/plug-in/layer-1.8.5/layer.min.js"></script>
    <link rel="stylesheet" href="<%=path%>/webpage/callnew/evaluate/css/index.css"></link>
    <link rel="stylesheet" href="<%=path%>/webpage/callnew/evaluate/css/layui.css"></link>
</head>
<script>
    $(function () {

        $(".links a").click(function () {
            var id = $(this).attr("data-id");
            $(".layui-input-block").hide();
            $(".layui-input-block[data-id='" + id + "']").show();
            $(".layui-input-block input").attr("checked", false);
            $(".links a").removeAttr("class");
            $(".links a[data-id='" + id + "']").attr("class", "on");
            $(".s-photo span").removeAttr("class");
            for (let i = 1; i <= id; i++) {
                $(".s-photo span[data-id='" + i + "']").attr("class", "on");
            }
        });

    });

    function CloseURL() {
        var eval = $(".links a[class='on']").attr("data-id");
        var infoDiv = $(".layui-input-block[data-id='" + eval + "'] input[name='SELECT_INFO']:checked");
        var str = "";
        infoDiv.each(function () {
            str += $(this).next().text()+",";
            str += $(this).val();
            str += "|";

        });

        var evalText = $("#writeText").val();

        if (evalText && evalText != "") {
            str += evalText;
        } else {
            if (str.length > 0) {
                str = str.substring(0, str.length - 1);
            }
        }

        var recordId = '${recordId}';

        $.ajax({
            method:"post",
            async:false,
            url:"newCallController.do?evaluate",
            data:{
                recordId : recordId,
                evaln : eval,
                strInfo : str
            },
            success:function (data) {

            }
        });

        ZCCloseHtmlAB();
    }

    function ZCShowOpinion() {
        var data = JSON.stringify({ 'function': 'ZCShowOpinion' });
        connected ? sendMessage(data) : ConnectServer(sendMessage, data)
    }

    function produceMessage(jsonObj){
        if (jsonObj.functionName == "ZCShowOpinion" && jsonObj.success == 1) {
            var txt = jsonObj.txt;
            $("#writeText").val(txt);
            $(".wordsNum").text(txt.length + "/150");

        }
    }

</script>
<body>
<div class="main">
    <div class="box ">
        <h3 class="title" style="background: #f2f2f2;">全国一体化在线政务服务平台“好差评”</h3>
        <div class="flex textCnt">
            <div class="label">办件评价</div>
            <div class="content">
                <div class="links">
                    <a href="#" class="on" data-id="5">非常满意</a>
                    <a href="#" data-id="4">满意</a>
                    <a href="#" data-id="3">基本满意</a>
                    <a href="#" data-id="2">不满意</a>
                    <a href="#" data-id="1">非常不满意</a>
                </div>
            </div>
        </div>
        <div class="flex textCnt">
            <div class="label">评分星数</div>
            <div class="content">
                <div class="start">
                    <div class="s-photo">
                        <span class="on" data-id="1"></span>
                        <span class="on" data-id="2"></span>
                        <span class="on" data-id="3"></span>
                        <span class="on" data-id="4"></span>
                        <span class="on" data-id="5"></span>
                    </div>
                </div>
                <p>感谢您的评价！请勾选或录入您评价的理由，以帮助各地区各部门更好地提升政务服务绩效。</p>
                <div class="checks" id="content" style="margin-bottom: 20px;margin-top: 20px;">
                    <div class="layui-form-item" style="font-size: 20px;">
                        <div class="layui-input-block" data-id="5" style="margin-left: 0px;margin-right: 105px;line-height: 40px;">
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="510"><span>一窗受理一次办结</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="511"><span>可以先受理后补材料</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="512"><span>不用提交证明</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="513"><span>可以全程网上办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="514"><span>可以使用手机办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="515"><span>可以就近办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="516"><span>无需材料直接办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="517"><span>服务热情效率高</span>
                        </div>
                        <div class="layui-input-block" data-id="4" style="display: none;margin-left: 0px;margin-right: 105px;line-height: 40px;">
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="406"><span>填写一张表单就可完成申报</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="407"><span>在线提交材料窗口核验</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="408"><span>一张清单告知全部申报材料</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="409"><span>用告知承诺减免申报材料</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="410"><span>可以在线预约办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="411"><span>跑大厅一次办完</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="412"><span>可以使用自助机办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="413"><span>服务态度较好</span>
                        </div>
                        <div class="layui-input-block" data-id="3" style="display: none;margin-left: 0px;margin-right: 105px;line-height: 40px;">
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="307"><span>一次性告知需要补正的材料</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="308"><span>提供申报材料样本</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="309"><span>在承诺的时间内办结</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="310"><span>办事指南指引准确</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="311"><span>按办事指南要求的材料即可办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="312"><span>可以快递送达</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="313"><span>跑动次数与承诺的一致</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="314"><span>服务态一般</span>
                        </div>
                        <div class="layui-input-block" data-id="2" style="display: none;margin-left: 0px;margin-right: 105px;line-height: 40px;">
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="207"><span>没有提供材料样本</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="208"><span>没有提供材料清单</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="209"><span>未在承诺时间内办结</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="210"><span>同样内容的证明材料被要求多次提交</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="211"><span>承诺网办但无法在线办理</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="212"><span>在线预约办理后到实体大厅重复取号排队</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="213"><span>窗口人员业务不熟练</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="214"><span>服务态度生硬</span>
                        </div>
                        <div class="layui-input-block" data-id="1" style="display: none;margin-left: 0px;margin-right: 105px;line-height: 40px;">
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="108"><span>在办事指南之外增加新的审批条件</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="109"><span>需提供办事指南之外的申报材料</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="110"><span>无理由超过法定办理时间</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="111"><span>办事指南提供样本有错</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="112"><span>承诺在线收取申报材料实际无法收取</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="113"><span>多头跑窗口和部门</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="114"><span>跑动次数与承诺的不一致</span>
                            <input type="checkbox" name="SELECT_INFO" lay-skin="primary" value="115"><span>服务态度差效率低</span>
                        </div>
                    </div>
                </div>
                <div class="switch_tab" onclick="ZCShowOpinion();">
                    <ul class="tabHead">
                        <li class="sel">我要评价</li>
                    </ul>
                    <ul class="tabBody">
                        <li>
                            <div class="textareaBox">
                                <textarea placeholder="评价内容，字数不要超过150个。" id="writeText" maxlength="150"></textarea>
                                <span class="wordsNum">0/150</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="fixedBox">
        <a id="commitValue" onclick="CloseURL()">提交评价</a>
    </div>
</div>
</body>
</html>