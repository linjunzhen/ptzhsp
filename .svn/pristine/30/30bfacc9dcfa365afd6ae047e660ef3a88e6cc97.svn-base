/**
 * 数据接口：不动产档案信息查询接口
 * 功能：回填宗地和房屋的表单信息
 */
function showSelectBdcfwzdcx(){
	$.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
		title : "不动产档案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close : function() {
			var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
			if (bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1) {
				backFill(bdcdaxxcxInfo);
				art.dialog.removeData("bdcdaxxcxInfo");
			}
		}
	}, false);
}
/**
 * 对接口与表单匹配的信息回填
 */
function backFill(bdcdaxxcxInfo){
	$("input[name='ESTATE_NUM']").val(bdcdaxxcxInfo[0].BDCDYH);
	$("input[name='ZD_BM']").val(bdcdaxxcxInfo[0].ZDDM);
	formSelect("ZD_QLLX",bdcdaxxcxInfo[0].ZDQLLX);
    formSelect("ZD_TZM", bdcdaxxcxInfo[0].ZDTZM);
    formSelect("ZD_QLSDFS",bdcdaxxcxInfo[0].QLSDFS);
    formSelect("ZD_QLXZ",bdcdaxxcxInfo[0].QLXZ);
    $("input[name='ZD_ZL']").val(bdcdaxxcxInfo[0].TDZL);
    formSelect("ZD_LEVEL",bdcdaxxcxInfo[0].DJ);
    $("input[name='ZD_RJL']").val(bdcdaxxcxInfo[0].RJL);
    $("input[name='ZD_JZXG']").val(bdcdaxxcxInfo[0].JZXG);
    $("input[name='ZD_JZMD']").val(bdcdaxxcxInfo[0].JZMD);			    
    formSelect("ZD_TDYT",bdcdaxxcxInfo[0].TDYT);
    $("input[name='ZD_YTSM']").val(bdcdaxxcxInfo[0].TDYTSM);  
    $("input[name='ZD_MJ']").val(bdcdaxxcxInfo[0].ZDMJ);			    
    $("input[name='ZD_E']").val(bdcdaxxcxInfo[0].TD_SZ_D);
    $("input[name='ZD_S']").val(bdcdaxxcxInfo[0].TD_SZ_N);
    $("input[name='ZD_W']").val(bdcdaxxcxInfo[0].TD_SZ_X);
    $("input[name='ZD_N']").val(bdcdaxxcxInfo[0].TD_SZ_B);
    $("input[name='GYTD_BEGIN_TIME']").val(bdcdaxxcxInfo[0].QLQSSJ);
    $("input[name='GYTD_END_TIME1']").val(bdcdaxxcxInfo[0].QLJSSJ);
    formSelect("GYTD_GYFS",bdcdaxxcxInfo[0].GYFS);
    $("input[name='GYTD_QDJG']").val(bdcdaxxcxInfo[0].JG);
    $("input[name='GYTD_SYQMJ']").val(bdcdaxxcxInfo[0].SYQMJ);
    formSelect("GYTD_QSZT",bdcdaxxcxInfo[0].QSZT);			    
    formSelect("FW_QLLX",bdcdaxxcxInfo[0].FW_QLLX);
    $("input[name='FW_ZL']").val(bdcdaxxcxInfo[0].FDZL);			    
    $("input[name='FW_ZH']").val(bdcdaxxcxInfo[0].ZH);
    $("input[name='FW_HH']").val(bdcdaxxcxInfo[0].HH);
    $("input[name='FW_ZCS']").val(bdcdaxxcxInfo[0].ZCS);
    $("input[name='FW_SZC']").val(bdcdaxxcxInfo[0].SZC);
    $("input[name='FW_JZMJ']").val(bdcdaxxcxInfo[0].JZMJ);
    $("input[name='FW_FTTDMJ']").val(bdcdaxxcxInfo[0].FTJZMJ);		    
    $("input[name='FW_ZYJZMJ']").val(bdcdaxxcxInfo[0].ZYJZMJ);
    $("input[name='FW_FTJZMJ']").val(bdcdaxxcxInfo[0].ZYJZMJ);	
    formSelect("FW_XZ",bdcdaxxcxInfo[0].FWXZ);
    formSelect("FW_FWJG",bdcdaxxcxInfo[0].FWJG);
    formSelect("FW_GHYT",bdcdaxxcxInfo[0].FW_GHYT);
    formSelect("FW_GYQK",bdcdaxxcxInfo[0].GYFS);
    $("input[name='FW_JYJG']").val(bdcdaxxcxInfo[0].JYJG);
    $("input[name='FW_YTSM']").val(bdcdaxxcxInfo[0].GHYTSM);
    $("input[name='FW_JGSJ']").val(bdcdaxxcxInfo[0].JGSJ);
    $("[name='FW_DYDYTDMJ']").val(bdcdaxxcxInfo[0].DYTDMJ);
    $("input[name='FW_FJ']").val(bdcdaxxcxInfo[0].FJ);
}
/**
 * 根据option的text回填select选择器的value
 */
function formSelect(name,optionText){
	 $("[name='" + name + "']" + " option:contains('" + optionText + "')").map(function(){
		 if(optionText == $(this).text()){
			 $(this).attr("selected",true);
	     }
	 });
}