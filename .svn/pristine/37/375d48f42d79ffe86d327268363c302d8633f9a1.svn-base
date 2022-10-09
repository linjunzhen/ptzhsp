$(function () {
    var flowSubmitObj = FlowUtil.getFlowObj();
    if (flowSubmitObj){
        if (flowSubmitObj.busRecord && flowSubmitObj.busRecord.FINISH_GETTYPE && flowSubmitObj.busRecord.FINISH_GETTYPE == '02') {
            $("#addr").attr("style", "");
            $("#addressee").attr("style", "");
            $("#addrmobile").attr("style", "");
            $("#addrprov").attr("style", "");
            $("#addrcity").attr("style", "");
            $("#addrpostcode").attr("style", "");
            $("#addrremarks").attr("style", "");
            $("tr[name='lzrTr']").show();
			
			var RESULT_SEND_PROV =  flowSubmitObj.busRecord.RESULT_SEND_PROV;
			if(RESULT_SEND_PROV){			

				$.post( "dicTypeController/getInfoToName.do",{
				TYPE_NAME:RESULT_SEND_PROV,PARENT_ID:"4028f64a677c078401677c49cbc300de"},
				function(responseText, status, xhr) {									
					var resultJson = $.parseJSON(responseText);
					if(null!=resultJson){	
						var url = 'dicTypeController/load.do?parentTypeCode='+resultJson.TYPE_CODE;
						$('#city').combobox('reload',url);						
						var RESULT_SEND_CITY =  flowSubmitObj.busRecord.RESULT_SEND_CITY;
						if(RESULT_SEND_CITY){	
							$.post( "dicTypeController/getInfoToName.do",{
							TYPE_NAME:RESULT_SEND_CITY,PARENT_ID:resultJson.TYPE_ID},
							function(responseText1, status, xhr) {									
								var resultJson1 = $.parseJSON(responseText1);
								if(null!=resultJson1){	
									var url = 'dicTypeController/load.do?parentTypeCode='+resultJson1.TYPE_CODE;
									$('#county').combobox('reload',url);
								}
							});
						}
					}
				});			
			}
        } else if (flowSubmitObj.busRecord && flowSubmitObj.busRecord.FINISH_GETTYPE && flowSubmitObj.busRecord.FINISH_GETTYPE == '03') {
            $("tr[name='lzrTr']").hide();
        } else {
            $("tr[name='lzrTr']").show();
        }
    }

    $("input[name='FINISH_GETTYPE']").click(function(){
        selectResultFetch();
    });
})

function selectResultFetch() {
    var radio = document.getElementsByName("FINISH_GETTYPE");
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            var str=radio[i].value;
            if("02"==str){
                $("#addr").attr("style","");
                $("#addressee").attr("style","");
                $("#addrmobile").attr("style","");
                $("#addrprov").attr("style","");
                $("#addrcity").attr("style","");
                $("#addrpostcode").attr("style","");
                $("#addrremarks").attr("style","");
                $("tr[name='lzrTr']").show();
            }else if ("03" == str) {
                $("#addr").attr("style", "display:none;");
                $("#addressee").attr("style", "display:none;");
                $("#addrmobile").attr("style", "display:none;");
                $("#addrprov").attr("style", "display:none;");
                $("#addrcity").attr("style", "display:none;");
                $("#addrpostcode").attr("style", "display:none;");
                $("#addrremarks").attr("style", "display:none;");
                $("tr[name='lzrTr']").hide();
            } else {
                $("#addr").attr("style", "display:none;");
                $("#addressee").attr("style", "display:none;");
                $("#addrmobile").attr("style", "display:none;");
                $("#addrprov").attr("style", "display:none;");
                $("#addrcity").attr("style", "display:none;");
                $("#addrpostcode").attr("style", "display:none;");
                $("#addrremarks").attr("style", "display:none;");
                $("tr[name='lzrTr']").show();
            }
        }
    }
}