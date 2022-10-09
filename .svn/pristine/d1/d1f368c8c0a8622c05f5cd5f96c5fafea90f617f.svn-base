<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/e-tags.tld"%> --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
    String itemCode = request.getParameter("itemCode");
    request.setAttribute("itemCode", itemCode);
    String param7 = request.getParameter("param7");
    request.setAttribute("param7", param7);
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>${param7}</title>
    <link rel="stylesheet" href="css/mui.min.css"/>
    <link rel="stylesheet" href="css/eui.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/mui.min.js"></script>
    <script src="js/mobileUtil.js"></script>
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/zepto.js"></script>
    <!-- 必引cordova.js，实现与原生的交互 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/cordova.js"></script>
    <!-- 含闽政通获取地市信息，网络状态等方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/bingotouch.js"></script>
    <!-- 含闽政通获取用户信息，分享，获取版本号，人脸识别方法 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jmtplugins.js"></script> 
    <!-- js加解密 -->
    <script src="https://mztapp.fujian.gov.cn:8190/mztAppWeb/app/js/jsencrypt.js"></script>
</head>
<body class="eui-bodyBg">
    <!--页面主体-->
    <div class="mui-content">
        
        <div class="eui-wrap">
            <div class="eui-tit" onclick="javascript:onToggle(0)">基本信息<i class="mui-icon mui-icon-arrowup" id="icon0"></i></div>
            <div class="eui-sub" id="div0">
                <div class="eui-display-table eui-table-view">
                    <div class="tr">
                        <div class="th">审批事项</div>
                        <div class="td" id="ITEM_NAME"></div>
                    </div>
                    <div class="tr">
                        <div class="th">所属部门</div>
                        <div class="td" id="SSBMMC"></div>
                    </div>
                    <div class="tr">
                        <div class="th">审批类型</div>
                        <div class="td" id="SXLX"></div>
                    </div>
                    <div class="tr">
                        <div class="th">承诺时间</div>
                        <div class="td" id="CNQXGZR"></div>
                    </div>
                    <div class="tr">
                        <div class="th">申报名称</div>
                        <div class="td" id="DECLARE_NAME"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="eui-wrap">
            <div class="eui-tit" onclick="javascript:onToggle(1)">申报对象信息<i class="mui-icon mui-icon-arrowup" id="icon1"></i></div>
            <div class="eui-sub" id="div1">
                <div class="eui-display-table eui-table-view">
                    <div class="tr">
                        <div class="th">申请人</div>
                        <div class="td" id="userName"></div>
                    </div>
                    <div class="tr">
                        <div class="th">身份证号码</div>
                        <div class="td" id="userCard"></div>
                    </div>
                    <div class="tr">
                        <div class="th">性别</div>
                        <div class="td" id="userSex"></div>
                    </div>
                    <div class="tr">
                        <div class="th">联系手机号</div>
                        <div class="td"><input type="text" name="userMobile" id="userMobile" placeholder="（*必填）" maxlength="11"/></div>
                    </div>
                    <div class="tr">
                        <div class="th">查询码</div>
                        <div class="td"><input type="text" placeholder="（*必填）" name="BJCXMM" id="BJCXMM" maxlength="30"/></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="eui-wrap">
            <div class="eui-tit" onclick="javascript:onToggle(2)">联系人信息<i class="mui-icon mui-icon-arrowup" id="icon2"></i></div>
            <div class="eui-sub" id="div2">
                <div class="eui-display-table eui-table-view">
                    <div class="tr">
                        <div class="th">姓名</div>
                        <div class="td">
                          <input type="text" name="JBR_NAME" id="JBR_NAME" placeholder="（*必填）" maxlength="30"/>
                        </div>
                    </div>
                    <div class="tr">
                        <div class="th">身份证号码</div>
                        <div class="td">
                          <input type="text" name="JBR_ZJHM" id="JBR_ZJHM" placeholder="（*必填）" maxlength="18"/>
                        </div>
                    </div>
                    <div class="tr">
                        <div class="th">联系手机号</div>
                        <div class="td">
                          <input type="text" name="JBR_MOBILE" id="JBR_MOBILE" placeholder="（*必填）" maxlength="11"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="eui-wrap">
            <div class="eui-tit" onclick="javascript:onToggle(3)">办理结果领取方式<i class="mui-icon mui-icon-arrowup" id="icon3"></i></div>
            <div class="eui-sub" id="div3">
                <div class="eui-display-table eui-table-view">
                    <div class="tr">
                        <div class="th">领取方式</div>
                        <div class="td mui-navigate-right">
                            <select name="FINISH_GETTYPE_NAME" id="FINISH_GETTYPE_NAME">
                                <option value="01" selected="selected">窗口领取</option>
                                <option value="02">快递送达</option>
                            </select>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME0">
                        <div class="th">收件人姓名</div>
                        <div class="td">
                          <input type="text" name="RESULT_SEND_ADDRESSEE" id="RESULT_SEND_ADDRESSEE" placeholder="（*必填）" maxlength="30"/>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME1">
                        <div class="th">手机号码</div>
                        <div class="td">
                          <input type="text" name="RESULT_SEND_MOBILE" id="RESULT_SEND_MOBILE" placeholder="（*必填）" maxlength="11"/>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME2">
                        <div class="th">所属省</div>
                        <div class="td">
                            <select name="RESULT_SEND_PROV_NAME" id="RESULT_SEND_PROV_NAME">
                                <option value="" selected="selected">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME3">
                        <div class="th">所属市</div>
                        <div class="td">
                            <select name="RESULT_SEND_CITY" id="RESULT_SEND_CITY">
                                <option value="" selected="selected">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME4">
                        <div class="th">邮政编码</div>
                        <div class="td">
                          <input type="text" name="RESULT_SEND_POSTCODE" id="RESULT_SEND_POSTCODE" placeholder="请输入邮政编码" maxlength="6"/>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME5">
                        <div class="th">详细地址</div>
                        <div class="td">
                          <input type="text" name="RESULT_SEND_ADDR" id="RESULT_SEND_ADDR" placeholder="（*必填）" maxlength="200"/>
                        </div>
                    </div>
                    <div class="tr" style="display:none;" id="FINISH_GETTYPE_NAME6">
                        <div class="th">邮递备注</div>
                        <div class="td">
                          <input type="text" name="RESULT_SEND_REMARKS" id="RESULT_SEND_REMARKS" placeholder="请输入邮递备注" maxlength="500"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <span id="applyMaters"></span>
        <!--底部-->
        <div class="eui-footer-zxsb">
            <div class="mui-input-row mui-checkbox mui-left">
                <label>
                  <input name="checkbox" type="checkbox" id="chkProtocol" style="top: 18px;">我已阅读并遵守
                  <a href="javascript:void(0);" id="onProtocol">《行政审批承诺书》</a>
                </label>
            </div>
            <div class="eui-btn">
                <a href="javascript:onSubmit();" class="eui-btn-blue" style="width: 90%;">提交</a>
            </div>
        </div>
    </div>
    <script>
        var userInfo = {};//用户信息
        var serviceItem = {};//申报基本信息
        var applyMaters = [];//材料数据
        var EFLOWOBJ = {};//环节数据
        var uploadUserName = '';//上传用户
        var uploadUserId = '';//上传用户ID
        
      //省市数据
 	   var citiesData = {
 	        "福建" : [ "平潭", "福州", "南平", "三明", "莆田", "泉州", "厦门", "漳州", "龙岩", "宁德", "邵武", "武夷山市", "浦城", "建瓯", "福鼎", "泰宁", "长汀", "上杭", "永安", "屏南", "崇武", "东山" ],
 		    "北京" : [ "北京" ],
 		    "上海" : [ "上海" ],
 		    "天津" : [ "天津", "塘沽" ],
 		    "重庆" : [ "重庆", "涪陵", "江津", "巫山" ],
 		    "河北" : [ "石家庄", "张家口", "承德", "秦皇岛", "唐山", "廊坊", "保定", "沧州", "衡水", "邢台", "邯郸", "张北", "蔚县", "丰宁", "围场", "怀来", "遵化", "青龙", "坝县", "乐亭", "饶阳", "黄骅", "南宫" ],
 		    "山西" : [ "太原", "大同", "朔州", "阳泉", "长治", "晋城", "忻州", "晋中", "临汾", "运城", "吕梁", "右玉", "河曲", "五台山", "五寨", "兴县", "原平", "离石", "榆社", "隰县", "介休", "候马", "阳城" ],
 		    "内蒙古" : [ "呼和浩特", "包头", "乌海", "赤峰", "通辽", "呼伦贝尔", "鄂尔多斯", "乌兰察布", "巴彦淖尔", "兴安盟", "锡林郭勒盟", "阿拉善盟", "额尔古纳右旗", "图里河", "满州里", "海拉尔", "小二沟", "新巴尔虎右旗", "新巴尔虎左旗", "博克图", "扎兰屯", "科前旗阿尔山", "索轮", "乌兰浩特", "东乌珠穆沁旗", "额济纳旗", "拐子湖", "巴音毛道", "阿拉善右旗", "二连浩特", "那仁宝力格", "满都拉", "阿巴嘎旗", "苏尼特左旗", "海力素", "朱日和", "乌拉特中旗", "百灵庙", "四子王旗", "化德", "集宁", "吉兰太", "临河", "鄂托克旗", "东胜", "伊金霍洛旗", "阿拉善左旗", "西乌珠穆沁旗", "扎鲁特旗", "巴林左旗", "锡林浩特", "林西", "开鲁", "多伦", "翁牛特旗", "宝国图" ],
 		    "辽宁" : [ "沈阳", "朝阳", "阜新", "铁岭", "抚顺", "本溪", "辽阳", "鞍山", "丹东", "大连", "营口", "盘锦", "锦州", "葫芦岛", "彰武", "开原", "清原", "叶柏寿", "新民", "黑山", "章党", "桓仁", "绥中", "兴城", "岫岩", "宽甸", "瓦房店", "庄河" ],
 		    "吉林" : [ "长春", "白城", "松原", "吉林", "四平", "辽源", "通化", "白山", "延吉", "乾安", "前郭尔罗斯", "通榆", "长岭", "三岔河", "双辽", "蛟河", "敦化", "汪清", "梅河口", "桦甸", "靖宇", "东岗", "松江", "临江", "集安", "长白" ],
 		    "黑龙江" : [ "哈尔滨", "齐齐哈尔", "黑河", "大庆", "伊春", "鹤岗", "佳木斯", "双鸭山", "七台河", "鸡西", "牡丹江", "绥化", "大兴安岭", "漠河", "塔河", "新林", "呼玛", "嫩江", "孙吴", "北安", "克山", "富裕", "海伦", "明水", "富锦", "泰来", "安达", "铁力", "依兰", "宝清", "肇州", "通河", "尚志", "虎林", "绥芬河" ],
 		    "江苏" : [ "南京", "徐州", "连云港", "宿迁", "淮安", "盐城", "扬州", "泰州", "南通", "镇江", "常州", "无锡", "苏州", "赣榆", "盱眙", "淮阴", "射阳", "高邮", "东台", "吕泗", "溧阳", "吴县东山" ],
 		    "浙江" : [ "杭州", "湖州", "嘉兴", "舟山", "宁波", "绍兴", "衢州", "金华", "台州", "温州", "丽水", "平湖", "慈溪", "嵊泗", "定海", "嵊县", "鄞县", "龙泉", "洪家", "玉环" ],
 		    "安徽" : [ "合肥", "宿州", "淮北", "阜阳", "亳州", "蚌埠", "淮南", "滁州", "马鞍山", "芜湖", "铜陵", "安庆", "黄山", "六安", "巢湖", "池州", "宣城", "砀山", "宿县", "寿县", "霍山", "桐城", "芜湖县", "宁国", "屯溪" ],
 		    "江西" : [ "南昌", "九江", "景德镇", "鹰潭", "新余", "萍乡", "赣州", "上饶", "抚州", "宜春", "吉安", "修水", "宁冈", "遂川", "庐山", "波阳", "樟树", "贵溪", "玉山", "南城", "广昌", "寻乌" ],
 		    "山东" : [ "济南", "聊城", "德州", "东营", "淄博", "潍坊", "烟台", "威海", "青岛", "日照", "临沂", "枣庄", "济宁", "泰安", "莱芜", "滨州", "菏泽", "惠民县", "羊角沟", "长岛", "龙口", "成山头", "朝城", "泰山", "沂源", "莱阳", "海阳", "石岛", "兖州", "莒县" ],
 		    "河南" : [ "郑州", "三门峡", "洛阳", "焦作", "新乡", "鹤壁", "安阳", "濮阳", "开封", "商丘", "许昌", "漯河", "平顶山", "南阳", "信阳", "周口", "驻马店", "济源", "卢氏", "孟津", "栾川", "西峡", "宝丰", "西华", "固始" ],
 		    "湖北" : [ "武汉", "十堰", "襄樊", "荆门", "孝感", "黄冈", "鄂州", "黄石", "咸宁", "荆州", "宜昌", "随州", "仙桃", "天门", "潜江", "神农架", "恩施", "郧西", "房县", "老河口", "枣阳", "巴东", "钟祥", "广水", "麻城", "五峰", "来风", "嘉鱼", "英山" ],
 		    "湖南" : [ "长沙", "张家界", "常德", "益阳", "岳阳", "株洲", "湘潭", "衡阳", "郴州", "永州", "邵阳", "怀化", "娄底", "吉首", "桑植", "石门", "南县", "沅陵", "安化", "沅江", "平江", "芷江", "双峰", "南岳", "通道", "武冈", "零陵", "常宁", "道县" ],
 		    "广东" : [ "广州", "清远", "韶关", "河源", "梅州", "潮州", "汕头", "揭阳", "汕尾", "惠州", "东莞", "深圳", "珠海", "中山", "江门", "佛山", "肇庆", "云浮", "阳江", "茂名", "湛江", "南雄", "连县", "佛冈", "连平", "广宁", "增城", "五华", "惠来", "南澳", "信宜", "罗定", "台山", "电白", "徐闻" ],
 		    "广西" : [ "南宁", "桂林", "柳州", "梧州", "贵港", "玉林", "钦州", "北海", "防城港", "崇左", "百色", "河池", "来宾", "贺州", "融安", "凤山", "都安", "蒙山", "那坡", "靖西", "平果", "桂平", "龙州", "灵山", "东兴", "涠洲岛" ],
 		    "海南" : [ "海口", "三亚", "文昌", "琼海", "万宁", "东方", "澄迈", "定安", "儋县", "琼中", "陵水", "西沙", "昌江", "乐东", "白沙", "临高" ],
 		    "四川" : [ "成都", "广元", "绵阳", "德阳", "南充", "广安", "遂宁", "内江", "乐山", "自贡", "泸州", "宜宾", "攀枝花", "巴中", "达川", "资阳", "眉山", "雅安", "阿坝", "甘孜", "西昌", "石渠", "若尔盖", "德格", "色达", "道孚", "马尔康", "红原", "小金", "松潘", "都江堰", "平武", "巴塘", "新龙", "理塘", "稻城", "康定", "峨眉山", "木里", "九龙", "越西", "昭觉", "雷波", "盐源", "会理", "万源", "阆中", "奉节", "梁平", "万县市", "叙永", "酉阳" ],
 		    "贵州" : [ "贵阳", "六盘水", "遵义", "安顺", "毕节", "铜仁", "凯里", "都匀", "兴义", "威宁", "盘县", "桐梓", "习水", "湄潭", "思南", "黔西", "三穗", "兴仁", "望谟", "罗甸", "独山", "榕江" ],
 		    "云南" : [ "昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "思茅", "临沧", "德宏", "怒江", "迪庆", "大理", "楚雄", "红河", "文山州", "德钦", "贡山", "中甸", "维西", "华坪", "会泽", "腾冲", "元谋", "沾益", "瑞丽", "景东", "泸西", "耿马", "澜沧", "景洪", "元江", "勐腊", "江城", "蒙自", "屏边", "广南", "勐海" ],
 		    "西藏" : [ "拉萨", "那曲", "昌都", "林芝", "山南", "日喀则", "阿里", "狮泉河", "改则", "班戈", "安多", "普兰", "申扎", "当雄", "拉孜", "尼木", "泽当", "聂拉木", "定日", "江孜", "错那", "隆子", "帕里", "索县", "丁青", "嘉黎", "洛隆", "波密", "左贡", "察隅" ],
 		    "陕西" : [ "西安", "延安", "铜川", "渭南", "咸阳", "宝鸡", "汉中", "榆林", "安康", "商洛", "定边", "吴旗", "横山", "绥德", "长武", "洛川", "武功", "华山", "略阳", "佛坪", "镇安", "石泉" ],
 		    "甘肃" : [ "兰州", "嘉峪关", "金昌", "白银", "天水", "武威", "酒泉", "张掖", "庆阳", "安西", "陇南", "临夏", "甘南", "马鬃山", "敦煌", "玉门镇", "金塔", "高台", "山丹", "永昌", "民勤", "景泰", "靖远", "榆中", "临洮", "环县", "平凉", "西峰镇", "玛曲", "夏河合作", "岷县", "定西" ],
 		    "青海" : [ "西宁", "海东", "海北", "海南", "黄南", "果洛", "玉树", "海西", "茫崖", "冷湖", "祁连", "大柴旦", "德令哈", "刚察", "门源", "格尔木", "都兰", "共和县", "贵德", "民和", "兴海", "同德", "同仁", "杂多", "曲麻莱", "玛多", "清水河", "达日", "河南", "久治", "囊谦", "班玛" ],
 		    "宁夏" : [ "银川", "石嘴山", "吴忠", "固原", "中卫", "惠农", "陶乐", "中宁", "盐池", "海源", "同心", "西吉" ],
 		    "新疆" : [ "乌鲁木齐", "克拉玛依", "石河子", "阿拉尔", "喀什", "阿克苏", "和田", "吐鲁番", "哈密", "克孜勒", "博尔塔拉", "昌吉", "库尔勒", "伊犁", "塔城", "阿勒泰", "哈巴河", "吉木乃", "福海", "富蕴", "和布克赛尔", "青河", "阿拉山口", "托里", "北塔山", "温泉", "精河", "乌苏", "蔡家湖", "奇台", "昭苏", "巴仑台", "达板城", "七角井", "库米什", "巴音布鲁克", "焉耆", "拜城", "轮台", "库车", "吐尔尕特", "乌恰", "阿合奇", "巴楚", "柯坪", "铁干里克", "若羌", "塔什库尔干", "莎车", "皮山", "民丰", "且末", "于田", "巴里坤", "伊吾", "伊宁" ],
 		    "香港" : [ "香港" ],
 		    "澳门" : [ "澳门" ],
 		    "台湾" : [ "台北", "台中", "高雄" ]
 	   };
        
        app.page.onLoad = function() {
            app.link.getLoginInfo(function(result) {
                var encrypt = new JSEncrypt();
		        encrypt.setPrivateKey(keyUtil.getPrivateKey());
		        userInfo = {
		          userCode : encrypt.decrypt(result['userId']),
		          userName : encrypt.decrypt(result['name']),
                  userCard : encrypt.decrypt(result['cardNum']),
                  userMobile : encrypt.decrypt(result['mobile']),
                  userSex : encrypt.decrypt(result['sex'])
		        };
		        initData();
		        getDeclareInfo();
            });
        }
        
       /*  initData();
        getDeclareInfo(); */
        
        /**
         * 初始化数据
         */
        function initData(){
            $('#userName').html(userInfo['userName']);
            $('#userCard').html(userInfo['userCard']);
            $('#userSex').html(userInfo['userSex']==1?'男':'女');
            $('#userMobile').val(userInfo['userMobile']);
            $("#JBR_NAME").val(userInfo['userName']);
            $("#JBR_ZJHM").val(userInfo['userCard']);
            $("#JBR_MOBILE").val(userInfo['userMobile']);
            for(var key in citiesData){
  		      $('#RESULT_SEND_PROV_NAME').append('<option value="'+key+'">'+key+'</option>');
            }
         /*    Object.keys(citiesData).forEach((key)=>{
		    }); */
        }
        
        /**
         * 根据申报编码及用户信息获取申报信息
         */
        function getDeclareInfo(){
            userInfo['itemCode'] = '${itemCode}';
/*             userInfo['userCode'] = '1';
            userInfo['userName'] = '111';
            userInfo['userCard'] = '350322198801121111';
            userInfo['userMobile'] = '13511111111'; */
            mobileUtil.muiAjaxForData({
                mask : true,
                url : '<%=path%>/declare.do?getDeclareInfo',
                data : userInfo,
                timeout:30000,
                callback : function(result) {
                    result = result||{};
                    serviceItem = result['serviceItem']||{};
                    applyMaters = result['applyMaters']||[];
                    EFLOWOBJ = result['EFLOWOBJ']||{};
                    uploadUserName = result['uploadUserName']||'';//上传用户
                    uploadUserId = result['uploadUserId']||'';//上传用户ID
                    $('#ITEM_NAME').html(serviceItem['ITEM_NAME']);
                    $('#SSBMMC').html(serviceItem['SSBMMC']);
                    $('#SXLX').html(serviceItem['SXLX']);
                    $('#CNQXGZR').html(serviceItem['CNQXGZR']+'工作日');
                    $('#DECLARE_NAME').html(userInfo['userName'] + '-' + serviceItem['ITEM_NAME']);
                    initApplyMaters();
                }
            });
        }
        
        /**
         * 伸缩
         * @param index 索引
         */
        function onToggle(index){
            $('#div'+index).toggle();
            var display = $('#div'+index).css('display');
            if(display == 'none'){
                $('#icon'+index).removeClass("mui-icon-arrowup");
                $('#icon'+index).addClass("mui-icon-arrowdown");
            }else{
                $('#icon'+index).removeClass("mui-icon-arrowdown");
                $('#icon'+index).addClass("mui-icon-arrowup");
            }
        }
        
        /**
         * 提交
         */
        function onSubmit(){
            var userMobile = $('#userMobile').val();
            if(!(/^1[3456789]\d{9}$/.test(userMobile))){  
               mui.alert('[申报对象信息-联系手机号]格式有误!', '提示');
               return; 
            }
            
            var BJCXMM = $("#BJCXMM").val();
            BJCXMM = BJCXMM.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
            if(BJCXMM==''){
               mui.alert('[申报对象信息-查询码]不能为空!', '提示');
               return;
            }
            
            var JBR_NAME = $("#JBR_NAME").val();
            JBR_NAME = JBR_NAME.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
            if(JBR_NAME==''){
               mui.alert('[联系人信息-姓名]不能为空!', '提示');
               return;
            }
            
            var JBR_ZJHM = $("#JBR_ZJHM").val();
            var reg = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X|x)$/;
            if(!reg.test(JBR_ZJHM)){
               mui.alert('[联系人信息-身份证号码]格式有误!', '提示');
               return;
            }
             
            var JBR_MOBILE = $("#JBR_MOBILE").val();
            if(!(/^1[3456789]\d{9}$/.test(JBR_MOBILE))){  
               mui.alert('[联系人信息-联系手机号]格式有误!', '提示');
               return;
            }
            
            var FINISH_GETTYPE_NAME= $('#FINISH_GETTYPE_NAME').val();
            if(FINISH_GETTYPE_NAME=='02'){
                var RESULT_SEND_ADDRESSEE = $("#RESULT_SEND_ADDRESSEE").val();
	            RESULT_SEND_ADDRESSEE = RESULT_SEND_ADDRESSEE.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
	            if(RESULT_SEND_ADDRESSEE==''){
	               mui.alert('[办理结果领取方式-收件人姓名]不能为空!', '提示');
	               return;
	            }
	            
	            var RESULT_SEND_MOBILE = $("#RESULT_SEND_MOBILE").val();
	            if(!(/^1[3456789]\d{9}$/.test(RESULT_SEND_MOBILE))){  
	               mui.alert('[办理结果领取方式-手机号码]格式有误!', '提示');
	               return;
	            }
	            
	            var RESULT_SEND_ADDR = $("#RESULT_SEND_ADDR").val();
                RESULT_SEND_ADDR = RESULT_SEND_ADDR.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
                if(RESULT_SEND_ADDR==''){
                   mui.alert('[办理结果领取方式-详细地址]不能为空!', '提示');
                   return;
                }
            }
            
            if(applyMaters!=null&&applyMaters.length>0){
                var errMsg = '';
                for(var i=0;i<applyMaters.length;i++){
                    if(applyMaters[i]['MATER_ISNEED']==1&&applyMaters[i]['imageData'].length<1){
                        errMsg = '请上传第['+(i+1)+']条材料!';
                        break;
                    }
                }
                
                if(errMsg!=''){
                    mui.alert(errMsg, '提示');
                    return;
                }
            }
            
            if(!$('#chkProtocol').is(':checked')){
                mui.alert('请勾选《行政审批承诺书》!', '提示');
                return;
            }
            
            mui.confirm('您是否要提交?', '提示', ['否', '是'], function(e) {
                if (e.index == 1) {
                    var params = {
                        EFLOW_CREATORID : uploadUserId,
                        BELONG_DEPT : serviceItem['SSBMBM'],
                        BELONG_DEPTNAME : serviceItem['SSBMMC'],
                        SXLX : serviceItem['SXLX'],
                        PROMISE_DATE : serviceItem['CNQXGZR'],
                        uploadUserId : uploadUserId,
                        uploadUserName : uploadUserName,
                        ITEM_NAME : serviceItem['ITEM_NAME'],
                        ITEM_CODE : serviceItem['ITEM_CODE'],
                        SSBMBM : serviceItem['SSBMBM'],
                        SQFS : serviceItem['APPLY_TYPE'],
                        //EFLOW_FLOWOBJ : serviceItem['APPLY_TYPE'],
                        EFLOW_DEFKEY : EFLOWOBJ['EFLOW_DEFKEY'],
                        EFLOW_BUSTABLENAME : EFLOWOBJ['EFLOW_BUSTABLENAME'],
                        EFLOW_CUREXERUNNINGNODENAMES : EFLOWOBJ['EFLOW_CUREXERUNNINGNODENAMES'],
                        EFLOW_CURUSEROPERNODENAME : EFLOWOBJ['EFLOW_CURUSEROPERNODENAME'],
                        EFLOW_DEFID : EFLOWOBJ['EFLOW_DEFID'],
                        EFLOW_DEFVERSION : EFLOWOBJ['EFLOW_DEFVERSION'],
                        itemName : serviceItem['ITEM_NAME'],
                        departName : serviceItem['SSBMMC'],
                        itemType : serviceItem['SXLX'],
                        promiseTime : serviceItem['CNQXGZR'] + '工作日',
                        SBMC : uploadUserName + '-' + serviceItem['ITEM_NAME'],
                        EFLOW_CREATORACCOUNT : userInfo['userCode'],
                        EFLOW_CREATORPHONE : userInfo['userMobile'],
                        EFLOW_CREATORNAME : userInfo['userName'],
                        BSYHLX : '1' ,
                        FINISH_GETTYPE_NAME : jQuery('#FINISH_GETTYPE_NAME option:selected').text(),
                        FINISH_GETTYPE : $('#FINISH_GETTYPE_NAME').val(),
                        SQRXB : userInfo['userSex'],
                        SQRXB_NAME : userInfo['userSex']==1?'男':'女',
                        BJCXMM : BJCXMM,
                        SQRSJH : userMobile,
                        JBR_NAME : JBR_NAME,
                        JBR_ZJHM : JBR_ZJHM,
                        JBR_MOBILE : JBR_MOBILE,
                    };
                    
                    if(FINISH_GETTYPE_NAME=='02'){
                        params['RESULT_SEND_ADDRESSEE'] = $('#RESULT_SEND_ADDRESSEE').val();
                        params['RESULT_SEND_MOBILE'] = $('#RESULT_SEND_MOBILE').val();
                        params['RESULT_SEND_PROV_NAME'] = $('#RESULT_SEND_PROV_NAME').val()==''?'':jQuery('#RESULT_SEND_PROV_NAME option:selected').text();
                        params['RESULT_SEND_PROV'] = $('#RESULT_SEND_PROV_NAME').val();
                        params['RESULT_SEND_CITY_NAME'] = $('#RESULT_SEND_CITY').val()==''?'':jQuery('#RESULT_SEND_CITY option:selected').text();
                        params['RESULT_SEND_CITY'] = $('#RESULT_SEND_CITY').val();
                        params['RESULT_SEND_POSTCODE'] = $('#RESULT_SEND_POSTCODE').val();
                        params['RESULT_SEND_ADDR'] = $('#RESULT_SEND_ADDR').val();
                        params['RESULT_SEND_REMARKS'] = $('#RESULT_SEND_REMARKS').val();
                    }
                    
                    var submitMaterList = [];
                    if(applyMaters!=null&&applyMaters.length>0){
                        for(var m = 0;m<applyMaters.length;m++){
                            var imageData = applyMaters[m]['imageData']||[];
                            for(var n = 0;n<imageData.length;n++){
                                submitMaterList.push({
                                    ATTACH_KEY:imageData[n]['attachKey'],
	                                SQFS:'1',
	                                SFSQ:'1',
	                                FILE_ID:imageData[n]['fileId']
	                            });
                            }
                        }
                    }
                    params['EFLOW_SUBMITMATERFILEJSON'] = JSON.stringify(submitMaterList);
                    mobileUtil.muiAjaxForData({
	                    mask : true,
	                    url : '<%=path%>/declare.do?submitApply',
	                    data : params,
	                    timeout:30000,
	                    callback : function(result) {
	                        result = result||{};
	                        if(result['OPER_SUCCESS']){
	                           //alert(result['EFLOW_EXEID']);
	                           //alert(result['BJCXMM']);
	                           window.location.href='<%=path%>/webpage/declare/result.jsp';
	                        }else{
	                           mui.alert('提交失败,请稍等再尝试!', '提示');
	                        }
	                    }
	                });
                }
            });
        }
         
         /**
          * 领取方式 显示 隐藏
          */
        $('#FINISH_GETTYPE_NAME').change(function(){
            var len = 7;
		    var FINISH_GETTYPE_NAME= $(this).val();
		    if(FINISH_GETTYPE_NAME=='01'){
	           for(var i = 0;i<len;i++){
	              $('#FINISH_GETTYPE_NAME'+i).hide();
	           }
			}else{
		        for(var i = 0;i<len;i++){
		          $('#FINISH_GETTYPE_NAME'+i).show();
                }
			}
	   });
	   
	   /**
        * 省选择事件
        */
       $('#RESULT_SEND_PROV_NAME').change(function(){
            $('#RESULT_SEND_CITY').empty();
            $('#RESULT_SEND_CITY').append('<option value="" selected="selected">请选择</option>');
            var cityData = citiesData[$(this).val()].forEach(item=>{
                $('#RESULT_SEND_CITY').append('<option value="'+item+'">'+item+'</option>');
            });
       });
	   
	   /**
	    * 初始化材料
	    */
	   function initApplyMaters(){
	       var html = "";
	       if(applyMaters!=null&&applyMaters.length>0){
	           for(var i=0;i<applyMaters.length;i++){
	               html = html + '<div class="eui-wrap">'
                        + '<div class="eui-tit" onclick="javascript:onToggle('+(i+10)+')">'+(i+1)+'、'+ applyMaters[i]['MATER_NAME'];
                   if(applyMaters[i]['MATER_ISNEED']==1){
                        html = html + '<span style="color:red;">&nbsp;&nbsp;*(必填)</span>'
                             + '<i class="mui-icon mui-icon-arrowup" id="icon'+(i+10)+'"></i></div>'
                             + '<div class="eui-sub" id="div'+(i+10)+'">';
                   }else{
                        html = html + '<i class="mui-icon mui-icon-arrowdown" id="icon'+(i+10)+'"></i></div>'
                             + '<div class="eui-sub" style="display:none;" id="div'+(i+10)+'">';
                   }
                   html = html + '<div class="eui-upload-pic" id="showImages'+i+'">'
                        +      '<a onclick="javascript:onSelectImages('+i+')" id="selectImages'+i+'"></a>'
                        +   '</div>'
                        + '</div>'
                        + '</div>';
                   applyMaters[i]['imageData'] = [];
	           }
	       }
           $('#applyMaters').html(html);
	   }
	   
	   /**
	    * 选择照片
	    * @param index 索引
	    */
	   function onSelectImages(index){
	       app.link.getTakePictures(function(res) {
	           res = res ||{};
	           var arrImages = res['images']||[];
	           if(arrImages!=null&&arrImages.length>0){
	               var html = '';
	               var initLength = applyMaters[index]['imageData'].length;//原有图片个数
	               for(var i=0;i<arrImages.length;i++){
	                   applyMaters[index]['imageData'].push({base64:arrImages[i]});
	               }
	               
	               if(applyMaters[index]['imageData']!=null&&applyMaters[index]['imageData'].length>0){
                       for(var m = 0;m<applyMaters[index]['imageData'].length;m++){
                           html = html + '<a><img src="data:image/jpg;base64,'+applyMaters[index]['imageData'][m]['base64']+'"/><i class="mui-icon mui-icon-closeempty" onclick="javascript:onDelImages('+index+','+m+')"></i></a>';
                       }
                   }
	               
	               if(applyMaters[index]['imageData'].length<9){
	                   html = html + '<a onclick="javascript:onSelectImages('+index+')" id="selectImages'+index+'"></a>';
                   }
	               $('#showImages'+index).html(html);
	               uploadImages(arrImages,index,initLength);
	           }
	       },{
	           pictureNum : 9 - applyMaters[index]['imageData'].length
	       });
	   }
	   
	   /**
        * 删除照片
        * @param index 区块索引
        * @param delIndex 删除索引
        */
	   function onDelImages(index,delIndex){
	       applyMaters[index]['imageData'] = applyMaters[index]['imageData'].filter((item,currIndex)=>delIndex!=currIndex);
	       var html = '';
           if(applyMaters[index]['imageData']!=null&&applyMaters[index]['imageData'].length>0){
               for(var m = 0;m<applyMaters[index]['imageData'].length;m++){
                   html = html + '<a><img src="data:image/jpg;base64,'+applyMaters[index]['imageData'][m]['base64']+'"/><i class="mui-icon mui-icon-closeempty" onclick="javascript:onDelImages('+index+','+m+')"></i></a>';
               }
           }
           
           if(applyMaters[index]['imageData'].length<9){
               html = html + '<a onclick="javascript:onSelectImages('+index+')" id="selectImages'+index+'"></a>';
           }
           $('#showImages'+index).html(html);
	   }
	   
	   /**
        * 上传图片
        * @param arrImages 图片数据
        * @param index 区块索引
        * @param initLength 原有图片个数
        */
	   function uploadImages(arrImages,index,initLength){
	       for(var i = 0;i<arrImages.length;i++){
	           var params = {
			      busTableName : EFLOWOBJ['EFLOW_BUSTABLENAME'],
			      uploadUserName : uploadUserName,
			      attachKey: applyMaters[index]['MATER_CODE'],
			      uploadUserId: uploadUserId,
			      URL:'http://msjw.fjgat.gov.cn/msjw/UploadServlet',
			      localFilePath: arrImages[i],
			      uploadPath:'applyform'
			    };
			    
			    mobileUtil.muiAjaxForData({
	                mask : true,
	                url : '<%=path%>/declare.do?uploadImages',
	                data : params,
	                async:false,
	                timeout:30000,
	                callback : function(result) {
	                    result = result||{};
	                    applyMaters[index]['imageData'][initLength+i]['fileId'] = result['fileId'];
	                    applyMaters[index]['imageData'][initLength+i]['attachKey'] = result['attachKey'];
	                }
	            });
	       }
	   }


        //用户授权协议
        document.getElementById("onProtocol").addEventListener('tap', function(e) {
            app.link.getVersion(function(result){
                var isNativeOpenWin = 1;
                if(result['type']==1){//android
                    if(result['version']<=139){
                        isNativeOpenWin = 0;
                    }
                }else if(result['type']==2){//ios
                    if(result['version']<=41){
                        isNativeOpenWin = 0;
                    }
                }

                if(isNativeOpenWin==1){
                    app.link.goOpenNewPageOrExit({
                        exit:1,
                        closePrepage:true,
                        url:'<%=basePath%>/webpage/declare/protocol.jsp?ITEM_NAME='+serviceItem['ITEM_NAME']
                    });
                }else{
                    window.location.href='<%=path%>/webpage/declare/protocol.jsp?ITEM_NAME='+serviceItem['ITEM_NAME'];
                }
            });
        });
	   
	   
    </script>
</body>
</html>