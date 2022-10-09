<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
  String registerType = request.getParameter("registerType");
  request.setAttribute("registerType", registerType);
%>
<script>
	function next() {

		<!--事项必选一个 begin-->
        //change
        var changeRegs="";
        $(".changeReg").each(function(index,val){
            var check=$(this).attr("check");
            if(check=='true'){
                changeRegs=changeRegs+index+",";
			}
        });
        changeRegs=changeRegs.substr(0, changeRegs.length - 1);
        $("input[name='CHANGEREGS']").val(changeRegs);


        //record
        var records="";
        $(".record").each(function(index,val){
            var check=$(this).attr("check");
            if(check=='true'){
                records=records+index+",";
            }
        });
        records=records.substr(0, records.length - 1);
        $("input[name='RECORDS']").val(records);
        if(changeRegs==""&&records==""){
            alert("事项必须至少选一个");
            return;
        }
        <!--事项必选一个end -->
        <!--隐藏模块div-->
         if(changeRegs==''){
            $(".itemLi1").hide();
		 }
		if(records==''){
             $(".recordLi1").hide();
		}

        <!--显示模块div-->
        <!--显示隐藏Form-->
        $("#container2").show();
        $("#container1").hide();
        <!--显示隐藏-->
		//初始化
		$(".BUS_SCOPE").hide();
		$(".BUS_YEARS").hide();
		$(".REG_ADDR").hide();
		$("input[name='OLD_BUS_SCOPE']").removeClass("validate[required]");
		$("input[name='OLD_BUS_YEARS']").removeClass("validate[required]");
		$("input[name='OLD_REG_ADDR']").removeClass("validate[required]");
		$("input[name='NEW_BUS_SCOPE']").removeClass("validate[required]");
		$("input[name='NEW_BUS_YEARS']").removeClass("validate[required]");
		$("input[name='NEW_REG_ADDR']").removeClass("validate[required]");
		<!--动态显示字段-->
        //change
        if(changeRegs.indexOf("0")>-1){
            $(".BUS_SCOPE").show();
            $("input[name='OLD_BUS_SCOPE']").addClass("validate[required]");
            $("input[name='NEW_BUS_SCOPE']").addClass("validate[required]");
		}
        if(changeRegs.indexOf("1")>-1){
            $(".BUS_YEARS").show();
            $("input[name='OLD_BUS_YEARS']").addClass("validate[required]");
            $("input[name='NEW_BUS_YEARS']").addClass("validate[required]");
        }
        if(changeRegs.indexOf("2")>-1){
            $(".REG_ADDR").show();
            $("input[name='OLD_REG_ADDR']").addClass("validate[required]");
            $("input[name='NEW_REG_ADDR']").addClass("validate[required]");
        }


        //record
        if(records.indexOf("0")>-1){
            $(".clearQue").show();
            $("input[name='CLEAR_PEOPLE']").addClass("validate[required]");
            $("input[name='CLEAR_ZERENREN']").addClass("validate[required]");
            $("input[name='CLEAR_PHONENUM']").addClass("validate[required,custom[mobilePhoneLoose]]");
        }
        if(records.indexOf("1")>-1){
            $(".liaison").show();
            $("input[name='LIAISON_NAME']").addClass("validate[required]");
            $("input[name='LIAISON_MOBILE']").addClass("validate[required,custom[mobilePhoneLoose]]");
            $("input[name='LIAISON_IDTYPE']").addClass("validate[required]");
            $("input[name='LIAISON_IDNO']").addClass("validate[required]");
        }
        <!--动态显示字段-->
    }


    function setValidate(inputId){
            $("#"+inputId).addClass(",custom[vidcard]");
    }


</script>
<div class="container" id="container1" style=" display:none;overflow:hidden; margin-bottom:20px;background:url(webpage/website/applyforms/change/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
	<div class="net-detail">
		<div class="syj-tyys">
			<div class="hd syj-tabtitle">
				<ul>
					<li><a href="javasrcipt:void(0)">变更事项</a></li>
				</ul>
			</div>
			<div class="bd">
				<div class="wzqy">
					<div class="top-tap">

						<div class="top-container">
							<div class="treelist1" id="wzqy0">
								<ul>
									<li date-item='1' ><p>变更登记</p>
										<ul>
											<li date-item='2' class="changeReg">经营范围<div class="line_r"><img src="webpage/website/applyforms/change/images/line_r.png" /></div><div class="line_c"><img src="webpage/website/applyforms/change/images/line_c.png" /></div></li>
											<li date-item='2' class="changeReg">营业期限<div class="line_r"><img src="webpage/website/applyforms/change/images/line_r.png" /></div><div class="line_c"><img src="webpage/website/applyforms/change/images/line_c.png" /></div></li>
											<li date-item='2' class="changeReg">住所
												<div class="line_r"><img src="webpage/website/applyforms/change/images/line_r.png" /></div><div class="line_c"><img src="webpage/website/applyforms/change/images/line_c.png" /></div></li>


										</ul>
									</li>
									<li date-item='1' ><p>备案登记</p>
										<ul>
											<li date-item='2' class="record">清算组 <div class="line_r"><img src="webpage/website/applyforms/change/images/line_r.png" /></div><div class="line_c"><img src="webpage/website/applyforms/change/images/line_c.png" /></div></li>
											<li date-item='2' class="record">联络员 <div class="line_r"><img src="webpage/website/applyforms/change/images/line_r.png" /></div><div class="line_c"><img src="webpage/website/applyforms/change/images/line_c.png" /></div></li>

										</ul>
									</li>
								</ul>
								<div class="tap-btn">

									<a class="tap_next" onclick="next()">下一步</a>
								</div>
							</div>

						</div>


					</div>

					<div style="margin-left: 30px; margin-bottom: 30px;"><span style=" color: #999; font-size: 12px; font-weight: bold;">备注</span><P style=" color: #999; font-size: 12px;">1.变更登记和备案登记可多选；<br>2.备案登记打钩清算组和联络员时，以下表单出现备案信息和联络员信息表单</P></div>

				</div>

			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    jQuery(".eui-menu").slide({
        type:"menu", //效果类型
        titCell:".syj-location", // 鼠标触发对象
        targetCell:".syj-city", // 效果对象，必须被titCell包含
        delayTime:0, // 效果时间
        defaultPlay:false,  //默认不执行
        returnDefault:true // 返回默认
    });
    jQuery(".syj-tyys").slide({trigger:"click"});
</script>
<script type="text/javascript">
    function getChageReg(){
        $(".changeReg").each(function(index,val) {
            var flag=$(this).attr("check");
            if(flag=='true') {
                alert(index);
            }
        });
    }
    //查看详情时，初始化
	$(function(){
		var changeRegs=$("input[name='CHANGEREGS']").val();
		var records=$("input[name='RECORDS']").val();
		$(".changeReg").each(function(index,val){
			if(changeRegs.indexOf(index)>-1){
				$(this).attr("check","true");
				$(this).addClass("onon");
			}
		});

		$(".record").each(function(index,val){
			if(records.indexOf(index)>-1){
				$(this).attr("check","true");
				$(this).addClass("onon");
			}
		});

	});

    $(function(){
        $(".treelist1>ul>li").each(function(){
            $(this).attr('zon',$(this).find("li").length);
            $(this).attr('xuan',$(this).find("li.onon").length);
            var tt=$(this);
            $(this).find('p').bind('click',function(){
                var temp=0;
                if(tt.attr('zon')>tt.attr('xuan')){
                    temp=1;
                }
                if(tt.attr('zon')==tt.attr('xuan')){
                    temp=2;
                }
                if(temp==1){
                    tt.find('li').each(function(){
                        $(this).attr('check','true');
                        $(this).removeClass('onon').addClass('onon');
                        tt.removeClass('on onon').addClass('onon');
                    })
                    tt.attr('xuan',tt.attr('zon'));
                }
                if(temp==2){
                    tt.find('li').each(function(){
                        $(this).attr('check','false');
                        $(this).removeClass('onon');
                        tt.removeClass('on onon')
                    })
                    tt.attr('xuan',0);
                }
            })
            $(this).find('li').bind('click',function(){
                if($(this).attr('check')=='true'){
                    $(this).removeClass('onon');
                    $(this).attr('check','false');
                    if(tt.attr('zon')==tt.attr('xuan')&&tt.attr('zon')>1){
                        tt.removeClass('onon').addClass('on');
                    }
                    tt.attr('xuan',parseInt(tt.attr('xuan'))-1);
                    if(tt.attr('xuan')==0){
                        tt.removeClass('on onon');
                    }
                }else{
                    $(this).addClass('onon');
                    $(this).attr('check','true');
                    tt.attr('xuan',parseInt(tt.attr('xuan'))+1);
                    tt.addClass('on');
                    if(tt.attr('zon')==tt.attr('xuan')){
                        tt.removeClass('on onon').addClass('onon');
                    }
                }
            })
        })
    })
</script>