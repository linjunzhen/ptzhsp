<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<title>eWebEditor �� AjaxӦ��֮������ɾ���༭��ʵ��</title>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<script type="text/javascript" src="../ewebeditor.js"></script>
<link rel='stylesheet' type='text/css' href='example.css'>


<script type="text/javascript">
var editor, html;
function CreateEditor(){
	if (editor){return;}

	//�� <div id="div_editor1"> �д���һ���༭��ʵ�������������ĳ�ʼֵΪ html��
	editor = EWEBEDITOR.Append("div_editor1", {style:"coolblue",width:"550",height:"350"}, html);
}

function RemoveEditor(){
	if (!editor){return;}

	//���ձ༭�������ݡ���һ��AjaxӦ���У����԰����ݷ����������ϣ���������������Ҫ�ĵط���
	//��Ҳ������ var editor=EWEBEDITOR.Instances["div_editor1"]; �������Ѵ����༭���Ľӿڶ���
	document.getElementById("div_content1").innerHTML = html = editor.getHTML();

	//ɾ���༭��
	editor.Remove();
	editor = null;
}
</script>

</head>
<body>

<p><b>���� �� <a href="default.jsp">ʾ����ҳ</a> &gt; AjaxӦ��֮������ɾ���༭��ʵ��</b></p>
<p>������ʾ����δ�����ɾ�� eWebEditor �༭��ʵ�����༭��ɾ���������༭�����ݽ���ʾ��һ�� &lt;div&gt; �С�������ϸ��ʹ�÷�������ο���ҳ��Դ�ļ���</p>
<p>����˵�����������������ť����һ�� eWebEditor ʵ�����ڱ༭��������һЩ���ݣ�Ȼ������ɾ������ť�����༭�����ݽ���ʾ��һ�� &lt;div&gt; �С�</p>

<p>
	<input type="button" value="�����༭��" onclick="CreateEditor()">
	<input type="button" value="ɾ���༭��" onclick="RemoveEditor()">
</p>

<!-- �༭�����������div��. -->
<div id="div_editor1"></div>

<!-- ��ʾ�༭������. -->
<hr>
<p><b>�༭�����ݣ�</b></p>
<div id="div_content1"></div>

</body>
</html>