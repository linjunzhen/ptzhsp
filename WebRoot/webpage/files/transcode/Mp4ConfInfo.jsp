<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
    <eve:resources loadres="jquery,easyui,apputil,artdialog"></eve:resources>
    <link rel="stylesheet" type="text/css" href="plug-in/easyui-1.4/extension/fieldset/lq.fieldset.css"/>
	<script type="text/javascript" src="plug-in/easyui-1.4/extension/fieldset/lq.fieldset.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
	    	$("#Mp4VideoConfArea").lqfieldset({
				title: '视频',
				collapsible: false,
				collapsed: false,
				checkboxToggle: false
			});
	    	$("#Mp4AudioConfArea").lqfieldset({
				title: '音频',
				collapsible: false,
				collapsed: false,
				checkboxToggle: false
			});
	    });
		
		function onSubmitConfForm(){
			if($('#Mp4TranscodeConfForm').form('validate')){
				var formData = $("#Mp4TranscodeConfForm").serialize();
				var url = $("#Mp4TranscodeConfForm").attr("action");
				AppUtil.ajaxProgress({
					url: url,
					params: formData,
					callback: function(resultJson){
						if(resultJson.success){
							parent.art.dialog({
								content: resultJson.msg,
								icon: "succeed",
							    ok: true
							});
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon: "error",
							    ok: true
							});
						}
					}
				});
			}
		}
	</script>
	<style>
	    .ConfInfoTable{width:100%;}
	    .ConfInfoTable tr{height:27px;}
	    .text-align-right{width:100px;text-align:right;}
	</style>
</head>
<body>
<div class="easyui-layout" fit="true">
    <form id="Mp4TranscodeConfForm" method="post" action="<%=path %>/transcodeController.do?saveOrUpdateConf">
    <input type="hidden" name="CONFIG_ID" value="${transcodeConf.CONFIG_ID }"/>
    <input type="hidden" name="MEDIA_TYPE" value="mp4"/>
        <div region="center">
            <div style="padding:10px 10px 0 10px;">
                <div style="width:50%;float:left;margin-right:10px;">
                    <div id="Mp4VideoConfArea" style="padding:0px;">
                        <table class="ConfInfoTable">
                            <tr>
                                <td class="text-align-right">视频编码器：</td>
	    		                <td>
	    		                    <input class="easyui-combobox" style="width:98%;" name="VIDEO_CODEC"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoCodec',
							               editable:false,value:'${transcodeConf.VIDEO_CODEC }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
	    		                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">视频尺寸：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="VIDEO_SIZE"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoSize',
							               editable:false,value:'${transcodeConf.VIDEO_SIZE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">比特率(kb/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="VIDEO_BITRATE"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoBitRate',
							               editable:false,value:'${transcodeConf.VIDEO_BITRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">帧率(f/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="VIDEO_FRAMERATE"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoFrameRate',
							               editable:false,value:'${transcodeConf.VIDEO_FRAMERATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div style="width:48%;float:left;">
                    <div id="Mp4AudioConfArea" style="padding:0px;">
                        <table class="ConfInfoTable">
                            <tr>
                                <td class="text-align-right">音频编码器：</td>
	    		                <td>
	    		                    <input class="easyui-combobox" style="width:98%;" name="AUDIO_CODEC"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioCodec',
							               editable:false,value:'${transcodeConf.AUDIO_CODEC }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145"/>
	    		                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">比特率(kb/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="AUDIO_BITRATE"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioBitRate',
							               editable:false,value:'${transcodeConf.AUDIO_BITRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">采样率(Hz)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="AUDIO_SAMPLINGRATE"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioSamplingRate',
							               editable:false,value:'${transcodeConf.AUDIO_SAMPLINGRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145" />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">声道：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="AUDIO_CHANNELS"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioChannels',
							               editable:false,value:'${transcodeConf.AUDIO_CHANNELS }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:'auto'" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="clearFloat"></div>
            </div>
        </div>
        <div class="eve_buttons">
			<input value="确定" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="onSubmitConfForm();" />
			<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</div>
</body>
</html>