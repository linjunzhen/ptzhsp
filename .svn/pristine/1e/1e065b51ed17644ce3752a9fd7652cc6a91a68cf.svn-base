<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<eve:resources
		loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<script type="text/javascript">
	var TYPE_CODE = "";
	var TYPE_NAME = "";
	$().ready(function () {
		$("#placeCode_CD").combotree({
			url: 'dicTypeController/placeTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='PLACE_CODE_CD']").parent().children('input').eq(0).toggleClass('');
				$("input[name='PLACE_CODE_CD']").parent().css("overflow", "visible");

			},
			onClick: function (node) {
				TYPE_CODE2 = $("#placeCode_CD").combotree("getValue");
				TYPE_NAME2 = $("#placeCode_CD").combotree("getText");
			},
			onExpand: function (node) {
				$("input[name='PLACE_CODE_CD']").parent().children('input').eq(0).toggleClass('');
				$("input[name='PLACE_CODE_CD']").parent().css("overflow", "visible");
			}
		});
	});

	$(function(){

		var projectCode = document.getElementById("projectCode").value;
		if(projectCode == null || projectCode.length == 0 || projectCode == "null"){
			projectCode = document.getElementById("project_code").value;
		}
		if(projectCode!=null && projectCode.length>0 && projectCode != "null"){
			loadTZXMXXData();
		}
	});

	function loadTZXMXXData() {
		var code = $("input[name='PROJECTCODE']").val();
		document.getElementById("projectCode").value = code;
		if (null == code || '' == code) {
			art.dialog({
				content: "请填写投资项目编号",
				icon: "error",
				ok: true
			});
		} else {
			var layload = layer.load('正在提交校验中…');
			$.post("webSiteController/loadTZXMXXData.do", {
						projectCode: code
					},
					function (responseText, status, xhr) {
						layer.close(layload);
						var resultJson = $.parseJSON(responseText);
						if (resultJson.result) {
							for (var key in resultJson.tzProject) {
								if (key == 'industry') {
									var typeCode = resultJson.tzProject[key];
									$.post("dicTypeController/info.do", {
												typeCode: typeCode, path: "4028819d51cc6f280151cde6a3f00027"
											},
											function (responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if (null != resultJson1) {
													$("#industry").combotree("setValue", resultJson1.TYPE_CODE);
													$("#industry").combotree("setText", resultJson1.TYPE_NAME);
												}
											});
								} else if (key == 'industry_CD') {
									var typeCode = resultJson.tzProject[key];
									$.post("dicTypeController/info.do", {
												typeCode: typeCode, path: "4028819d51cc6f280151cde6a3f00027"
											},
											function (responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if (null != resultJson1) {
													$("#industry_CD").combotree("setValue", resultJson1.TYPE_CODE);
													$("#industry_CD").combotree("setText", resultJson1.TYPE_NAME);
												}
											});
								} else if (key == 'industryStructure') {
									var typeCode = resultJson.tzProject[key];
									$.post("dicTypeController/info.do", {
												typeCode: typeCode
											},
											function (responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if (null != resultJson3) {
													$("#industryStructure").combotree("setValue", resultJson3.TYPE_CODE);
													$("#industryStructure").combotree("setText", resultJson3.TYPE_NAME);
												}
											});
								} else if (key == 'industryStructure_CD') {
									var typeCode = resultJson.tzProject[key];
									$.post("dicTypeController/info.do", {
												typeCode: typeCode
											},
											function (responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if (null != resultJson3) {
													$("#industryStructure_CD").combotree("setValue", resultJson3.TYPE_CODE);
													$("#industryStructure_CD").combotree("setText", resultJson3.TYPE_NAME);
												}
											});
								} else if (key == 'placeCode') {
									var typeCode2 = resultJson.tzProject[key];
									$.post("dicTypeController/placeInfo.do", {typeCode: typeCode2},
											function (responseText2, status, xhr) {
												var resultJson2 = $.parseJSON(responseText2);
												if (null != resultJson2) {
													$("#placeCode").combotree("setValue", resultJson2.TYPE_CODE);
													$("#placeCode").combotree("setText", resultJson2.TYPE_NAME);
												}
											});
								} else if (key == 'placeCode_CD') {
									var typeCode2 = resultJson.tzProject[key];
									if (typeCode2 != "") {
										$.post("dicTypeController/info.do", {typeCode: typeCode2},
												function (responseText2, status, xhr) {
													var resultJson2 = $.parseJSON(responseText2);
													if (null != resultJson2) {
														$("#placeCode_CD").combotree("setValue", resultJson2.TYPE_CODE);
														$("#placeCode_CD").combotree("setText", resultJson2.TYPE_NAME);
													}
												});
									}
								} else {
									$("#" + key).val(resultJson.tzProject[key]);
								}
							}
							//项目投资表基本信息
							loadXmjbxxb();
							//初始化法人信息
							initXmdwxx();
							//基本信息
							$("#tzjbxx input").attr("disabled", "disabled");
							$("#tzjbxx textarea").attr("disabled", "disabled");
							$("#tzjbxx select").attr("disabled", "disabled");
							$("#tzjbxx textarea").attr("disabled", "disabled");

							$("#tzjbxx input[name$=CD]").attr("disabled", false);
							$("#tzjbxx textarea[name$=CD]").attr("disabled", false);
							$("#tzjbxx select[name$=CD]").attr("disabled", false);
							$("#tzjbxx textarea[name$=CD]").attr("disabled", false);
							$("input[name=INDUSTRY_CD]").prev().attr("disabled", false);
							$("input[name=PLACE_CODE_CD]").prev().attr("disabled", false);
							$("input[name=INDUSTRY_STRUCTURE_CD]").prev().attr("disabled", false);
							//法人单位信息
							$("#xmdwxxDiv input").attr("disabled", "disabled");
							$("#xmdwxxDiv textarea").attr("disabled", "disabled");
							$("#xmdwxxDiv select").attr("disabled", "disabled");

							$("#xmdwxxDiv input[name$=CD]").attr("disabled", false);
							$("#xmdwxxDiv textarea[name$=CD]").attr("disabled", false);
							$("#xmdwxxDiv select[name$=CD]").attr("disabled", false);
						} else {
							art.dialog({
								content: "校验失败",
								icon: "error",
								ok: true
							});
						}
					});
		}
	}
        function lerepContributionData() {
        }
        function loadXmjbxxb() {
			var code = $("input[name='PROJECTCODE']").val();
            $.post("webSiteController/loadXMJBXXBData.do", {
                    projectCode: code
                },
                function (responseText, status, xhr) {
                    var resultJson = $.parseJSON(responseText);
                    for (var key in resultJson) {
						$("input[name='"+key+"']").val(resultJson[key]);
                        $("select[name='"+key+"']").find("option[value='" + resultJson[key]+ "']").attr("selected", "selected");
                    }
                }
            );
        }

        function onSelectClass(o) {
            if (o == 1) {
                $("#deAreaName").removeClass("validate[]");
                $("#deAreaName").toggleClass('');
            } else {
                $("#deAreaName").removeClass("");
                $("#deAreaName").toggleClass('validate[]');
            }
        }

        function showSecurityApprovalNumber(o) {
            if (o == 1) {
                $("#securityApprovalNumberName").attr("style", "display:;");
                $("#securityApprovalNumber").attr("style", "display:;");
                $("#securityApprovalNumberNameBlank").attr("style", "display:none;");
                $("#securityApprovalNumberBlank").attr("style", "display:none");
            } else {
                $("#securityApprovalNumberName").attr("style", "display:none;");
                $("#securityApprovalNumber").attr("style", "display:none;");
                $("#securityApprovalNumberNameBlank").attr("style", "display:;");
                $("#securityApprovalNumberBlank").attr("style", "display:");
            }
        }

        function showOther(o) {
            if ("A00006" == o) {
                $("#otherInvestmentApplyInfoTr").attr("style", "display:");
                $("#transactionBothInfoTr").attr("style", "display:");
                $("#mergerPlanTr").attr("style", "display:");
                $("#mergerManagementModeScopeTr").attr("style", "display:");
            } else {
                $("#otherInvestmentApplyInfoTr").attr("style", "display:none;");
                $("#transactionBothInfoTr").attr("style", "display:none;");
                $("#mergerPlanTr").attr("style", "display:none;");
                $("#mergerManagementModeScopeTr").attr("style", "display:none;");
            }
        }

        $().ready(function () {

            $("#industry_CD").combotree({
                url: 'dicTypeController/selectTree.do',
                multiple: false,
                cascadeCheck: false,
                onlyLeafCheck: true,
                onLoadSuccess: function () {
                    $("input[name='INDUSTRY_CD']").parent().children('input').eq(0).toggleClass('');
                    $("input[name='INDUSTRY_CD']").parent().css("overflow", "visible");
                    var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
                    if (EFLOW_FLOWOBJ) {
                        //将其转换成JSON对象
                        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
                        //初始化表单字段值
                        if (eflowObj.busRecord) {
                           var   TYPE_CODE = eflowObj.busRecord.INDUSTRY_CD;
                            if (TYPE_NAME == "") {
                                $.post("dicTypeController/info.do", {
                                        typeCode: TYPE_CODE, path: "4028819d51cc6f280151cde6a3f00027"
                                    },
                                    function (responseText1, status, xhr) {
                                        var resultJson1 = $.parseJSON(responseText1);
                                        $("#industry_CD").combotree("setValue", resultJson1.TYPE_CODE);
                                        $("#industry_CD").combotree("setText", resultJson1.TYPE_NAME);
                                        isok = false;
                                    });
                            } else {
                                $("#industry_CD").combotree("setValue", TYPE_CODE);
                                $("#industry_CD").combotree("setText", TYPE_NAME);
                            }
                        }
                    }
                },
                onClick: function (node) {
                    // 返回树对象
                    var tree = $(this).tree;
                    // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                    var isLeaf = tree('isLeaf', node.target);
                    if (!isLeaf) {
                        // 清除选中
                        $("#industry_CD").combotree('clear');
                    } else {
                        TYPE_CODE = $("#industry_CD").combotree("getValue");
                        TYPE_NAME = $("#industry_CD").combotree("getText");
                        //$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
                    }
                },
                onExpand: function (node) {
                    $("input[name='INDUSTRY_CD']").parent().children('input').eq(0).toggleClass('');
                    $("input[name='INDUSTRY_CD']").parent().css("overflow", "visible");
                }

            });
        });

        $().ready(function () {
            var TYPE_CODE = "";
            var TYPE_NAME = "";
            $("#industryStructure_CD").combotree({
                url: 'dicTypeController/selectIndustryStructureTree.do',
                multiple: false,
                cascadeCheck: false,
                id: 'INDUSTRY_STRUCTURE_CD',
                onlyLeafCheck: true,
                onLoadSuccess: function () {
                    $("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('');
                    $("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow", "visible");
                    var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
                    if (EFLOW_FLOWOBJ) {
                        //将其转换成JSON对象
                        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
                        //初始化表单字段值
                        if (eflowObj.busRecord) {
                            TYPE_CODE = eflowObj.busRecord.INDUSTRY_STRUCTURE_CD;
                            if (TYPE_NAME == ""&&TYPE_CODE!=''&&typeof(TYPE_CODE)!='undefined'&&TYPE_CODE!='undefined') {
                                $.post("dicTypeController/info.do", {
                                        typeCode: TYPE_CODE
                                    },
                                    function (responseText3, status, xhr) {
										if(responseText3.success == true) {
											var resultJson3 = $.parseJSON(responseText3);
											$("#industryStructure_CD").combotree("setValue", resultJson3.TYPE_CODE);
											$("#industryStructure_CD").combotree("setText", resultJson3.TYPE_NAME);
											isok = false;
										}
                                    });
                            } else {
                                $("#industryStructure_CD").combotree("setValue", TYPE_CODE);
                                $("#industryStructure_CD").combotree("setText", TYPE_NAME);
                            }
                        }
                    }

                },
                onClick: function (node) {
                    // 返回树对象
                    var tree = $(this).tree;
                    // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                    var isLeaf = tree('isLeaf', node.target);
                    if (!isLeaf) {
                        // 清除选中
                        $("#industryStructure_CD").combotree('clear');
                    } else {
                        TYPE_CODE = $("#industryStructure_CD").combotree("getValue");
                        TYPE_NAME = $("#industryStructure_CD").combotree("getText");
                        //$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
                    }
                },
                onExpand: function (node) {
                    $("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('');
                    $("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow", "visible");
                }

            });
        });

        function changeInfo() {
            var a = $("input[name='LEREP_INFO']").val();
            var lerepInfoTxt = '{ "lerepInfo" : ' + a + '}';
            var lerepInfoObj = eval("(" + lerepInfoTxt + ")");
            lerepInfoObj.lerepInfo[0].enterprise_name = $("#enterpriseName").val();
            lerepInfoObj.lerepInfo[0].lerep_certtype = $("#lerepCertType").val();
            lerepInfoObj.lerepInfo[0].lerep_certno = $("#lerepCertNo").val();
            lerepInfoObj.lerepInfo[0].contact_name = $("#contactName").val();
            lerepInfoObj.lerepInfo[0].contact_tel = $("#contactTel").val();
            lerepInfoObj.lerepInfo[0].contact_email = $("#contactEmail").val();
            $("#lerepInfo").val(JSON2.stringify(lerepInfoObj.lerepInfo));
            //变更后
            var a_CD = $("input[name='LEREP_INFO_CD']").val();
            var lerepInfoTxt_CD = '{ "lerepInfo_CD" : ' + a_CD + '}';
            var lerepInfoObj_CD = eval("(" + lerepInfoTxt_CD + ")");
            lerepInfoObj_CD.lerepInfo[0].enterprise_name = $("#enterpriseName").val();
            lerepInfoObj_CD.lerepInfo[0].lerep_certtype = $("#lerepCertType").val();
            lerepInfoObj_CD.lerepInfo[0].lerep_certno = $("#lerepCertNo").val();
            lerepInfoObj_CD.lerepInfo[0].contact_name = $("#contactName").val();
            lerepInfoObj_CD.lerepInfo[0].contact_tel = $("#contactTel").val();
            lerepInfoObj_CD.lerepInfo[0].contact_email = $("#contactEmail").val();
            $("#lerepInfo_CD").val(JSON2.stringify(lerepInfoObj_CD.lerepInfo));

        };

        function totalMoney() {
            if ($("#totalMoney").val() == 0) {
                $("#totalMoneyExplain").removeClass("");
                $("#totalMoneyExplain").toggleClass('');
            } else {
                $("#totalMoneyExplain").removeClass("");
                $("#totalMoneyExplain").toggleClass('');
            }
        }

        function changeHTIndustry(value) {
            $.post("webSiteController/getHTProject.do", {industry: value}, function (data) {
                var data = JSON.parse(data);
                var str = '';
                for (var i = 0; i < data.length; i++) {
                    str += '<option value="' + data[i].PROJECT_NAME + '">' + data[i].PROJECT_NAME + '</option>'
                }
                $("#HTPROJECT").html(str);
                changeHTProjectDetail(data[0].PROJECT_NAME)
            })
        }

        function changeHTProjectDetail(value) {
            $.post("webSiteController/getHTProjectDetail.do", {project: value}, function (data) {
                var data = JSON.parse(data);
                var str = "";
                if (data.REPORT != null) {
                    str += '<option value="' + data.REPORT + '">' + data.REPORT + '</option>'
                }
                if (data.REPORT_FORM != null) {
                    str += '<option value="' + data.REPORT_FORM + '">' + data.REPORT_FORM + '</option>'
                }
                if (data.REGISTRAT_FORM != null) {
                    str += '<option value="' + data.REGISTRAT_FORM + '">' + data.REGISTRAT_FORM + '</option>'
                }
                $("#HTPROJECT_DETAILS").html(str)
            })
        }

        //初始化项目信息
        function initXmdwxx() {
            var lerepInfo = $("input[name='LEREP_INFO']").val();
            if (lerepInfo) {
                var lerepInfoList = $.parseJSON(lerepInfo);
                for (var i = 0; i < lerepInfoList.length; i++) {
                    if (i > 0) {
                        $("#xmdwxxDiv").html(xmdwxxDivHtml);
                    }
                    initFormObjValue(lerepInfoList[i], $("#xmdwxxDiv").eq(i));
                }
            }

			var lerepInfo = $("input[name='LEREP_INFO_CD']").val();
			if (lerepInfo) {
				var lerepInfoList = $.parseJSON(lerepInfo);
				for (var i = 0; i < lerepInfoList.length; i++) {
					if (i > 0) {
						$("#xmdwxxDiv").html(xmdwxxDivHtml);
					}
					initFormObjValue(lerepInfoList[i], $("#xmdwxxDiv").eq(i));
				}
			}
        }

        var xmdwxxDivHtml = $("#xmdwxxDiv").html();

        function getXmdwxxJson() {
            var infoArray = [];
            $("#xmdwxxDiv").each(function (i) {
				var enterprise_id = $(this).find("[name$='enterprise_id']").val();//单位id
                var enterprise_name = $(this).find("[name$='enterprise_name']").val();//单位名称
                var dwlx = $(this).find("[name$='dwlx']").val();//单位类型
                var lerep_certtype = $(this).find("[name$='lerep_certtype']").val();//证照类型
                var lerep_certno = $(this).find("[name$='lerep_certno']").val();//证件号码
                var contact_name = $(this).find("[name$='contact_name']").val();	//联系人名称
                var contact_tel = $(this).find("[name$='contact_tel']").val();//联系电话
                var contact_email = $(this).find("[name$='contact_email']").val();// 联系人邮箱
                var enterprise_place = $(this).find("[name$='enterprise_place']").val();//注册地址
                var enterprise_nature = $(this).find("[name$='enterprise_nature']").val();//单位性质
                var china_foreign_share_ratio = $(this).find("[name$='china_foreign_share_ratio']").val();//持股比例是否与资本金相同
                var business_scope = $(this).find("[name$='business_scope']").val();//主要经营范围
                var contact_phone = $(this).find("[name$='contact_phone']").val();//联系手机
                var contact_fax = $(this).find("[name$='contact_fax']").val();//传真
                var correspondence_address = $(this).find("[name$='correspondence_address']").val();//通讯地址
                //变更后
                var enterprise_name_CD = $(this).find("[name$='enterprise_name_CD']").val();//单位名称
                var dwlx_CD = $(this).find("[name$='dwlx_CD']").val();//单位类型
                var lerep_certtype_CD = $(this).find("[name$='lerep_certtype_CD']").val();//证照类型
                var lerep_certno_CD = $(this).find("[name$='lerep_certno_CD']").val();//证件号码
                var contact_name_CD = $(this).find("[name$='contact_name_CD']").val();	//联系人名称
                var contact_tel_CD = $(this).find("[name$='contact_tel_CD']").val();//联系电话
                var contact_email_CD = $(this).find("[name$='contact_email_CD']").val();// 联系人邮箱
                var enterprise_place_CD = $(this).find("[name$='enterprise_place_CD']").val();//注册地址
                var enterprise_nature_CD = $(this).find("[name$='enterprise_nature_CD']").val();//单位性质
                var china_foreign_share_ratio_CD = $(this).find("[name$='china_foreign_share_ratio_CD']").val();//持股比例是否与资本金相同
                var business_scope_CD = $(this).find("[name$='business_scope_CD']").val();//主要经营范围
                var contact_phone_CD = $(this).find("[name$='contact_phone_CD']").val();//联系手机
                var contact_fax_CD = $(this).find("[name$='contact_fax_CD']").val();//传真
                var correspondence_address_CD = $(this).find("[name$='correspondence_address_CD']").val();//通讯地址



                var info = {};
				info.enterprise_id = enterprise_id;
                info.enterprise_name = enterprise_name;
                info.dwlx = dwlx;
                info.lerep_certtype = lerep_certtype;
                info.lerep_certno = lerep_certno;
                info.contact_name = contact_name;
                info.contact_tel = contact_tel;
                info.contact_email = contact_email;
                info.enterprise_place = enterprise_place;
                info.enterprise_nature = enterprise_nature;
                info.china_foreign_share_ratio = china_foreign_share_ratio;
                info.business_scope = business_scope;
                info.contact_phone = contact_phone;
                info.contact_fax = contact_fax;
                info.correspondence_address = correspondence_address;
                //变更后
                info.enterprise_name_CD = enterprise_name_CD ;
                info.dwlx_CD  = dwlx_CD ;
                info.lerep_certtype_CD  = lerep_certtype_CD ;
                info.lerep_certno_CD  = lerep_certno_CD ;
                info.contact_name_CD  = contact_name_CD ;
                info.contact_tel_CD  = contact_tel_CD ;
                info.contact_email_CD  = contact_email_CD ;
                info.enterprise_place_CD  = enterprise_place_CD ;
                info.enterprise_nature_CD  = enterprise_nature_CD ;
                info.china_foreign_share_ratio_CD  = china_foreign_share_ratio_CD ;
                info.business_scope_CD  = business_scope_CD ;
                info.contact_phone_CD  = contact_phone_CD ;
                info.contact_fax_CD  = contact_fax_CD;
                info.correspondence_address_CD  = correspondence_address_CD;

                infoArray.push(info);
            });
            $("[name='LEREP_INFO']").val(JSON.stringify(infoArray));
        }

        /**
         * 初始化表单字段值
         * @param {} fieldValueObj
         * @param {} elementObj
         */
        function initFormObjValue(fieldValueObj, elementObj) {
            for (var fieldName in fieldValueObj) {
                //获取目标控件对象
                var targetFields = elementObj.find("[name$='" + fieldName + "']");
                targetFields.each(function () {
                    var targetField = $(this);
                    var type = targetField.attr("type");
                    var tagName = targetField.get(0).tagName;
                    var fieldValue = fieldValueObj[fieldName];

                    if (type == "radio") {
                        var radioValue = targetField.val();
                        if (radioValue == fieldValue) {
                            $(this).attr("checked", "checked");
                        }
                    } else if (type == "checkbox") {
                        var checkBoxValue = targetField.val();
                        var isChecked = AppUtil.isContain(fieldValue.split(","), checkBoxValue);
                        if (isChecked) {
                            $(this).attr("checked", "checked");
                        }
                    } else if (tagName == "SELECT") {
                        targetField.children("option[value='" + fieldValueObj[fieldName] + "']")
                            .attr("selected", "selected");
                    } else {
                        targetField.val(fieldValueObj[fieldName]);
                    }
                });
            }
        }

        function isShowWztzxx() {
            if ($("#foreignabroadFlag").val() == 1) {
                $("#jwtzxx").attr("style", "display:none;");
                $("#wstzxx").attr("style", "display:;");
            } else if ($("#foreignabroadFlag").val() == 2) {
                $("#wstzxx").attr("style", "display:none;");
                $("#jwtzxx").attr("style", "display:;");
            } else {
                $("#wstzxx").attr("style", "display:none;");
                $("#jwtzxx").attr("style", "display:none;");
            }
            if ($("#totalMoney").val() == 0) {
                $("#totalMoneyExplain").removeClass("");
                $("#totalMoneyExplain").toggleClass('');
            } else {
                $("#totalMoneyExplain").removeClass("");
                $("#totalMoneyExplain").toggleClass('');
            }
        }

        function getDicNames(typeCode, dicCodes) {
            $.post("dictionaryController/textname.do",
                {
                    typeCode: typeCode, dicCodes: dicCodes
                },
                function (responseText3, status, xhr) {
                    return responseText3;
                });
    }

</script>
<style>
	.textbox{
		width: 280px!important;
		height: 40px!important;
		border: none!important;
	}
	.textbox .textbox-text{
		width: 280px!important;
		height: 40px!important;
		line-height: 40px!important;
		font-size: 16px!important;
		color: #000000!important;
		padding: 0 10px!important;
		box-sizing: border-box!important;
		border: 1px solid #c9deef!important;
	}
	/* .lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	.permitIndustryStructureSelect{
		width: auto !important;
	} */
</style>
<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
<input type="hidden" id="lerepInfo_CD" name="LEREP_INFO_CD" value='${busRecord.LEREP_INFO}'/>
<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG"/>
<input type="hidden" id="theIndustry" name="THE_INDUSTRY"/>
<%--<input type="hidden" id="projectAttributes" name="PROJECT_ATTRIBUTES"/>	--%>
<%--<input type="hidden" id="industryStructure" name="INDUSTRY_STRUCTURE"/>	--%>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>基本信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">

		<table cellpadding="0" cellspacing="0" class="bstable1" style="border: 0 dotted #a0a0a0;">
			<tr>
				<th> 投资项目编号</th>
				<td colspan="3">
					<input type="hidden"  id="project_code" name="PROJECT_CODE"  value="${projectCode}"/>
					<c:if test="${projectCode == null }">
						<input type="text" maxlength="32" class="tab_text" name="PROJECTCODE" />
						<a href="javascript:loadTZXMXXData();" class="projectBtnA">校 验</a><font color="red">（请先输入投资项目编号进行校验）</font> <a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><省级投资项目申报登记入口></a>
					</c:if>
					<c:if test="${projectCode != null }">
						<input type="text" maxlength="32" class="tab_text" id="projectCode" name="PROJECTCODE" value="${projectCode}" readonly="true"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th rowspan="2"> 项目名称</th>
				<td  colspan="3">
					<input id="projectName" type="text" class="tab_text w838" name="PROJECT_NAME" readonly="true"  maxlength="128"/>
				</td>
			</tr>

			<tr>
				<td   colspan="3">
					<input id="projectName_CD" type="text" class="tab_text w838" name="PROJECT_NAME_CD"   maxlength="128"/>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

		</table><br/>
		<table cellpadding="0" cellspacing="0" class="bstable1"  id="tzjbxx">


			<tr>
				<th rowspan="2"> 项目类型</th>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams="PROJECTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
					</eve:eveselect>
				</td>
				<th rowspan="2"> 项目所属行政区划</th>
				<td>
					<input id="divisionCode" type="text" maxlength="6" class="tab_text " readonly="true" name="DIVISION_CODE" />
				</td>
			</tr>

			<tr>
				<td>
					<eve:eveselect clazz="tab_text  w280" dataParams="PROJECTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE_CD" id="projectType">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<input id="divisionCode_CD" type="text" maxlength="6" class="tab_text "  name="DIVISION_CODE_CD" />
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>


			<tr>
				<th rowspan="2"> 投资项目目录编码</th>
				<td>
					<input id="PERMIT_ITEM_CODE" type="text" maxlength="64" class="tab_text " readonly="true" name="PERMIT_ITEM_CODE" />
				</td>
				<th rowspan="2"> 建设性质</th>
				<td>
					<eve:eveselect clazz="tab_text  w280" dataParams="PROJECTNATURE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="projectNature">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<td>
					<input id="PERMIT_ITEM_CODE_CD" type="text" maxlength="64" class="tab_text "  name="PERMIT_ITEM_CODE_CD" />
				</td>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams="PROJECTNATURE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE_CD" id="projectNature_CD">
					</eve:eveselect>
				</td>
			</tr>


			<tr>
				<th rowspan="2"> 建设地点详情</th>
				<td  colspan="3">
					<input id="placeCodeDetail" type="text" class="tab_text maxSize[512] w838"  name="PLACE_CODE_DETAIL" />
				</td>
			</tr>

			<tr>
				<td   colspan="3">
					<input id="placeCodeDetail_CD" type="text" class="tab_text maxSize[512] w838" name="PLACE_CODE_DETAIL_CD" />
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

			<tr>
				<th rowspan="2"> 拟开工时间</th>
				<td>
					<input id="startYear" type="text" maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})" readonly="true" name="START_YEAR" />
				</td>
				<th rowspan="2"> 拟建成时间</th>
				<td>
					<input id="endYear" type="text"  maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})" readonly="true" name="END_YEAR" />
				</td>
			</tr>

			<tr>
				<td>
					<input id="startYear_CD" type="text" maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR_CD" />
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<input id="endYear_CD" type="text"  maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR_CD" />
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>
			<tr>
				<th rowspan="2"> 申报日期</th>
				<td>
					<input id="applyDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})" readonly="true" class="tab_text  Wdate" name="APPLY_DATE" />
				</td>
				<th rowspan="2"> 总投资（万元）</th>
				<td>
					<input id="totalMoney" type="text"  maxlength="16" class="tab_text " name="TOTAL_MONEY"  readonly="true"/>
				</td>
			</tr>

			<tr>
				<td>
					<input id="applyDate_CD" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text Wdate" name="APPLY_DATE_CD" />
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<input id="totalMoney_CD" type="text"  maxlength="16" class="tab_text " name="TOTAL_MONEY_CD"  />
					<span style="color:red;">(变更后)</span>
				</td>

			</tr>

			<tr>
				<td rowspan="2" class="tab_width"> 总投资额为“0”时说明：</td>
				<td  colspan="3">
					<textarea id="totalMoneyExplain" class="tab_text  w838" rows="2" cols="200"
							  name="TOTAL_MONEY_EXPLAIN" style=" height:100px;"  ></textarea>
				</td>
			</tr>
			<tr>
				<td  colspan="3">
					<textarea id="totalMoneyExplain_CD" class="tab_text  w838" rows="2" cols="200"
							  name="TOTAL_MONEY_EXPLAIN_CD" style=" height:100px;"  ></textarea>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>


			<tr>
				<th rowspan="2"> 建设地点</th>
				<td>
					<input type="text"  id="placeCode"  name="PLACE_CODE"
						   class="tab_text  easyui-combotree"  panelHeight="20px"/>
				</td>
				<th rowspan="2"> 国标行业</th>
				<td>
					<input type="text" id="industry"  name="INDUSTRY"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text"   id="placeCode_CD"  name="PLACE_CODE_CD"
						   class="tab_text  easyui-combotree"  panelHeight="220px"/>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<input type="text"   id="industry_CD"  name="INDUSTRY_CD"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

			<tr>
				<th rowspan="2"> 建设规模及内容</th>
				<td  colspan="3">
					<textarea id="scaleContent" class="eve-textarea ,maxSize[2000] w838" rows="2" cols="200"
							  name="SCALE_CONTENT" style=" height:100px;"  readonly="true"></textarea>
				</td>
			</tr>
			<tr>
				<td  colspan="3">
					<textarea id="scaleContent_CD" class="eve-textarea maxSize[2000] w838" rows="2" cols="200"
							  name="SCALE_CONTENT_CD" style="height:100px;" ></textarea>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

			<tr>
				<th rowspan="2"> 项目属性</th>
				<td >
					<eve:eveselect clazz="tab_text w280" dataParams="PROJECTATTRIBUTES"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES" id="projectAttributes">
					</eve:eveselect>
				</td>
				<th rowspan="2"> 产业结构调整指导目录</th>
				<td>
					<input type="text" id="industryStructure"  name="INDUSTRY_STRUCTURE"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>

				</td>
			</tr>

			<tr>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams="PROJECTATTRIBUTES"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES_CD" id="projectAttributes_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<input type="text" id="industryStructure_CD"  name="INDUSTRY_STRUCTURE_CD"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

			<tr>
				<th rowspan="2"> 土地获取方式</th>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="getLandMode"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
					</eve:eveselect>
				</td>
				<th rowspan="2"> 项目投资来源</th>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="XMTZLY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="getLandMode"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE_CD" id="getLandMode_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="XMTZLY"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择项目投资来源" name="XMTZLY_CD" id="XMTZLY_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>

			<tr>
				<th rowspan="2"> 工程分类</th>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="GCFL"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
					</eve:eveselect>
				</td>
				<th rowspan="2"> 是否完成区域评估</th>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="SFWCQYPG"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="GCFL"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择工程分类" name="GCFL_CD" id="GCFL_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<eve:eveselect  clazz="tab_text w280" dataParams="SFWCQYPG"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG_CD" id="SFWCQYPG_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>


			<tr>
				<th rowspan="2"> 土地是否带设计方案</th>
				<td colspan="3">
					<eve:eveselect  clazz="tab_text w838" dataParams="TDSFDSJFA"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<eve:eveselect  clazz="tab_text  w838" dataParams="TDSFDSJFA"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA_CD" id="TDSFDSJFA_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>
		</table>

		<table   cellpadding="0" cellspacing="0" class="bstable1"	 id="xmdwxxDiv" >
<%--			<tr>--%>
<%--				<th colspan="4"> 单位名称</th>--%>
<%--			</tr>--%>

			<tr>
				<input type="hidden" maxlength="100"
					   class="tab_text  " name="enterprise_id" />
				<th rowspan="2"> 单位名称</th>
				<td>
					<input type="text" maxlength="100"
						   class="tab_text  " name="enterprise_name" />
				</td>
				<th rowspan="2"> 单位类型</th>
				<td>
					<eve:eveselect clazz="tab_text whf_input w280" dataParams="DWLX"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择单位类型" name="dwlx" id="dwlx">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<td>
					<input type="text" maxlength="100"
						   class="tab_text  " name="enterprise_name_CD"   onchange="getXmdwxxJson()"/>
					<span style="color:red;">(变更后)</span>
				</td>
				<td>
					<eve:eveselect clazz="tab_text whf_input  w280" dataParams="DWLX" onchange="getXmdwxxJson()"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择单位类型" name="dwlx_CD" id="dwlx_CD">
					</eve:eveselect>
					<span style="color:red;">(变更后)</span>
				</td>
			</tr>
			<tr>
				<th rowspan="2"> 证照类型</th>
				<td>
					<eve:eveselect clazz="tab_text whf_input  w280" dataParams="LEREPCERTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择证照类型" name="lerep_certtype" id="lerep_certtype">
					</eve:eveselect>
				</td>
				<th rowspan="2"> 证照号码</th>
				<td>
					<input type="text" maxlength="32"
						   class="tab_text " name="lerep_certno" />
				</td>
			</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text whf_input w280" dataParams="LEREPCERTTYPE"
						   dataInterface="dictionaryService.findDatasForSelect" onchange="getXmdwxxJson()"
						   defaultEmptyText="请选择证照类型" name="lerep_certtype_CD" id="lerep_certtype_CD">
			</eve:eveselect>
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<input type="text" maxlength="32" onchange="getXmdwxxJson()"
				   class="tab_text " name="lerep_certno_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>
			<tr>
				<th rowspan="2"> 联系人名称</th>
				<td>
					<input type="text" maxlength="16"
						   class="tab_text " name="contact_name" />
				</td>
				<th rowspan="2"> 联系电话</th>
				<td>
					<input type="text" maxlength="16"
						   class="tab_text " name="contact_tel" />
				</td>
			</tr>
	<tr>
		<td>
			<input type="text" maxlength="16" onchange="getXmdwxxJson()"
				   class="tab_text " name="contact_name_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<input type="text" maxlength="16"  onchange="getXmdwxxJson()"
				   class="tab_text " name="contact_tel_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>

			<tr>
				<th rowspan="2">联系人邮箱</th>
				<td>
					<input type="text"
						   class="tab_text validate[]" name="contact_email" />
				</td>
				<th rowspan="2">注册地址</th>
				<td>
					<input type="text"  maxlength="128"
						   class="tab_text" name="enterprise_place" />
				</td>
			</tr>

	<tr>
		<td>
			<input type="text" onchange="getXmdwxxJson()"
				   class="tab_text validate[]" name="contact_email_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<input type="text"  maxlength="128" onchange="getXmdwxxJson()"
				   class="tab_text" name="enterprise_place_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>

			<tr>
				<th rowspan="2">持股比例是否相同</th>
				<td>
					<eve:eveselect clazz="tab_text whf_input w280" dataParams="chinaForeignShareRatio"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio" id="china_foreign_share_ratio">
					</eve:eveselect>
				</td>
				<th ROWSPAN="2">单位性质</th>
				<td>
					<eve:eveselect clazz="tab_text whf_input w280" dataParams="enterpriseNature"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择单位性质" name="enterprise_nature" id="enterprise_nature">
					</eve:eveselect>
				</td>
			</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text w280" dataParams="chinaForeignShareRatio"
						   dataInterface="dictionaryService.findDatasForSelect" onchange="getXmdwxxJson()"
						   defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio_CD" id="china_foreign_share_ratio_CD">
			</eve:eveselect>
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<eve:eveselect clazz="tab_text w280" dataParams="enterpriseNature"
						   dataInterface="dictionaryService.findDatasForSelect" onchange="getXmdwxxJson()"
						   defaultEmptyText="请选择单位性质" name="enterprise_nature_CD" id="enterprise_nature_CD">
			</eve:eveselect>
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>

			<tr>
				<th rowspan="2">主要经营范围</th>
				<td>
					<input type="text" maxlength="512"
						   class="tab_text" name="business_scope" />
				</td>
				<th ROWSPAN="2">联系手机</th>
				<td>
					<input type="text" maxlength="16"
						   class="tab_text" name="contact_phone" />
				</td>
			</tr>
	<tr>
		<td>
			<input type="text" maxlength="512" onchange="getXmdwxxJson()"
				   class="tab_text" name="business_scope_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<input type="text" maxlength="16" onchange="getXmdwxxJson()"
				   class="tab_text" name="contact_phone_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>

			<tr>
				<th rowspan="2">传真</th>
				<td>
					<input type="text"
						   class="tab_text validate[]" name="contact_fax" />
				</td>
				<th rowspan="2">通讯地址</th>
				<td>
					<input type="text" maxlength="64"
						   class="tab_text validate[]" name="correspondence_address" />
				</td>
			</tr>

	<tr>
		<td>
			<input type="text" onchange="getXmdwxxJson()"
				   class="tab_text validate[]" name="contact_fax_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
		<td>
			<input type="text" maxlength="64" onchange="getXmdwxxJson()"
				   class="tab_text validate[]" name="correspondence_address_CD" />
			<span style="color:red;">(变更后)</span>
		</td>
	</tr>
		</table>
	</div>
</div>
<div id="lerepInfoTable"></div>
<div id="lerepInfoTable_CD"></div>
<div id="dnyTable"></div>
<div id="dnyTable_CD"></div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->