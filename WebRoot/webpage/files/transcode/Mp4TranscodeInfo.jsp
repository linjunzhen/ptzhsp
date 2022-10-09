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
		
		function onSubmitTanscodeForm(){
			if($('#Mp4TranscodeForm').form('validate')){
				var formData = $("#Mp4TranscodeForm").serialize();
				var url = '${videoFile.FILESERVER_URL}transcode/trans2Mp4';
				var layload = art.dialog({content: '正在提交请求中…'}).lock();
				$.post(url, formData, function(resultJson, status, xhr){
					layload.close();
					if(resultJson && resultJson!="websessiontimeout"){
						if(resultJson.success){
							parent.art.dialog({
								content: resultJson.msg,
								icon: "succeed",
							    ok: true
							});
							parent.$("#VideoFileViewGrid").datagrid("reload");
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
    <form id="Mp4TranscodeForm" method="post">
    <input type="hidden" name="videoId" value="${requestScope.videoFile.VIDEO_ID }"/>
        <div region="center">
            <div style="padding:10px 10px 0 10px;">
                <div style="width:50%;float:left;margin-right:10px;">
                    <div id="Mp4VideoConfArea" style="padding:0px;">
                        <table class="ConfInfoTable">
                            <tr>
                                <td class="text-align-right">视频编码器：</td>
	    		                <td>
	    		                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.videoCodec"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoCodec',
							               editable:false,value:'${transcodeConf.VIDEO_CODEC }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
	    		                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">视频尺寸：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.videoSize"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoSize',
							               editable:false,value:'${transcodeConf.VIDEO_SIZE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">比特率(kb/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.videoBitRate"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4VideoBitRate',
							               editable:false,value:'${transcodeConf.VIDEO_BITRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:175,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">帧率(f/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.videoFrameRate"  
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
	    		                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.audioCodec"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioCodec',
							               editable:false,value:'${transcodeConf.AUDIO_CODEC }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145"/>
	    		                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">比特率(kb/s)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.audioBitRate"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioBitRate',
							               editable:false,value:'${transcodeConf.AUDIO_BITRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">采样率(Hz)：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.audioSamplingRate"  
							               data-options="url:'dictionaryController.do?load&amp;typeCode=Mp4AudioSamplingRate',
							               editable:false,value:'${transcodeConf.AUDIO_SAMPLINGRATE }',method:'post',required:true,
							               valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth:165,panelHeight:145" />
                                </td>
                            </tr>
                            <tr>
                                <td class="text-align-right">声道：</td>
                                <td>
                                    <input class="easyui-combobox" style="width:98%;" name="transcodeConf.audioChannels"  
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
			<input value="转码" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="onSubmitTanscodeForm();" />
			<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</div>
</body>
</html>