<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<title>eWebEditor �� ��Javascript���ı����DIV�滻Ϊ�༭��</title>
<meta http-equiv=Content-Type content="text/html; charset=GBK">
<script type="text/javascript" src="../ewebeditor.js"></script>
<link rel='stylesheet' type='text/css' href='example.css'>

<script type="text/javascript">
// ������EWEBEDITOR.Replace(s_Id, o_Config)
// ������s_Id     : �ַ��ͣ�Ҫ�滻��Textarea��id��name, Div��id
//       o_Config : �����ͣ�ָ���༭�����ã���ѡ��������δ������Ĭ�� style=coolblue,width=100%,height=350


//ɾ���༭��
function RemoveEditor(){
	if (!EWEBEDITOR.Instances["content1"]){return;}

	EWEBEDITOR.Instances["content1"].Remove();
	EWEBEDITOR.Instances["div1"].Remove();
}

//�滻�༭��
function ReplaceEditor(){
	if (EWEBEDITOR.Instances["content1"]){return;}

	EWEBEDITOR.Replace("content1", {style:"coolblue",width:"550",height:"300"} );
	EWEBEDITOR.Replace("div1");
}
</script>

</head>
<body>

<p><b>���� �� <a href="default.jsp">ʾ����ҳ</a> &gt; ��Javascript���ı����DIV�滻Ϊ�༭��</b></p>
<p>������ʾ�������Javascript����� &lt;TEXTAREA&gt; �� &lt;INPUT&gt; �� &lt;DIV&gt; �滻Ϊ eWebEditor �༭��ʵ����������Ҫ����һ�м򵥴��룬���Ϳ�ʵ���ı��򵽱༭����ת������ϸʹ�÷�������ο���ҳԴ�ļ�������˵�����û��ֲᡣ</p>
<div class=code>EWEBEDITOR.Replace(&quot;TextareaOrInputOrDiv_id&quot;);</div>
<p>ʾ������˵������ҳ������ʱ�����Կ����������༭������ʵ�ڴ����У�һ����&ltTEXTAREA&gt;��һ����&lt;DIV&gt;��������ʱ��Javascript�����滻�ġ������Ե����ɾ������ť����ԭ����������滻����ť�ر༭�����档ͬʱ����ɾ��ʱ�����ڱ༭���б༭�����ݽ����ص�ԭ�����С�</p>

<p>
	<input type="button" value="ɾ���༭��" onclick="RemoveEditor()">
	<input type="button" value="�滻�༭��" onclick="ReplaceEditor()">
</p>

<p><b>ʾ��1��</b>�滻&lt;TEXTAREA&gt;��ָ���༭������(style=coolblue,width=550,height=300)</p>
<textarea cols="80" name="content1" rows="10">&lt;p&gt;����&lt;strong&gt;�滻TEXTAREA&lt;/strong&gt;�� ������ʹ�� &lt;a href=&quot;http://www.ewebeditor.net/&quot;&gt;eWebEditor&lt;/a&gt;.&lt;/p&gt;</textarea>
<script type="text/javascript">
	// ��Ҳ������ window.onload �¼��д���
	// �滻 <textarea id="content1"> �� <textarea name="content1"> Ϊ�༭��ʵ��
	EWEBEDITOR.Replace("content1", {style:"coolblue",width:"550",height:"300"} );
</script>

<p><b>ʾ��2��</b>�滻&lt;DIV&gt;��ʹ��Ĭ������(style=coolblue,width=100%,height=350)</p>
<div id="div1" rows="10"><p>����<strong>�滻DIV</strong>�� ������ʹ�� <a href="http://www.ewebeditor.net/">eWebEditor</a>.</p></div>
<script type="text/javascript">
	// ��Ҳ������ window.onload �¼��д���
	// �滻 <div id="div1"> Ϊ�༭��ʵ��
	EWEBEDITOR.Replace("div1");
</script>

</body>
</html>