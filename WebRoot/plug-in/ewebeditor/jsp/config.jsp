<%
//License = "2:47247:3:2:1::mztapp.fujian.gov.cn:9b9dd60358548b4c15541b5cb20cb19a"

//Username = "evecom"
//Password = "evecom123"

//Style = "standard650||||||office2003|||uploadfile/|||650|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||650px宽度界面下的标准工具栏按钮|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||73|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "standard600||||||office2003|||D:/Users/chao/Workspaces/MyEclipse2016/ptsp/ptzhspFile/WebRoot/uploadfile/|||600|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||50000|||10000|||10000|||10000|||10000|||1|||1|||EDIT|||3|||1|||||||||http://172.16.111.189/ptzhspFile/uploadfile/|||1|||0|||600px宽度界面下的标准工具栏按钮(推荐)|||1|||zh-cn|||1|||1024|||1024|||1|||平潭审批平台|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||10000|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||2||||||{yyyy}/{mm}/{dd}/|||1|||{page}|||0|||2000|||1|||2||||||0|||500|||1|||4|||1|||1|||1|||0|||{name}-{sn}|||0|||eimage/|||eflash/|||emedia/|||eattach/|||eremote/|||elocal/|||1|||||||||300|||1|||1|||||||||||||||1||||||1|||1|||1|||0||||||http://127.0.0.1/ptzhspFile/plugins/ewebeditor/"
//Style = "standard550||||||office2003|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||550px宽度界面下的标准工具栏按钮(推荐)|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "standard500||||||office2003|||uploadfile/|||500|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||500px宽度界面下的标准工具栏按钮|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "full650||||||office2003|||uploadfile/|||650|||400|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||650px宽度界面下的所有功能按钮展示,功能按钮有可能重复|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||73|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "mini500||||||office2003|||uploadfile/|||500|||300|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||500px宽度界面下的最简工具栏按钮,适合于邮件系统留言系统等只需最简单功能的应用|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "menu400||||||office2003|||uploadfile/|||400|||250|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||400px宽度,工具栏全部菜单按钮,所有功能,占位小|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||FF0000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||73|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "coolblue||||||office2003|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||V4.x版保留,standard550标配,office2003皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "gray||||||office2000|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,office2000皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "light||||||light1|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,light1皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "blue||||||blue2|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,blue2皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "green||||||green1|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,green1皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "red||||||red1|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,red1皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "yellow||||||yellow1|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,yellow1皮肤|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "mini||||||office2003|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,下拉框及菜单,全功能,占位小|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||FF0000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||73|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "popup||||||office2003|||uploadfile/|||550|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1|||0|||||||||1|||0|||V4.x版保留,standard550标配,office2003皮肤,弹窗模式调用|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "expand650||||||office2003|||uploadfile/|||650|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||650px宽度界面下的扩展工具栏按钮,默认只显示一行工具栏,点击扩展按钮,显示更多|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||73|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"
//Style = "expand600||||||office2003|||uploadfile/|||600|||350|||rar|zip|pdf|doc|xls|ppt|chm|hlp|||swf|||gif|jpg|jpeg|bmp|png|||rm|flv|wmv|asf|mov|mpg|mpeg|avi|mp3|mp4|wav|mid|midi|ra|wma|||gif|jpg|bmp|png|||500|||100|||100|||100|||100|||1|||1|||EDIT|||1|||1||||||||||||1|||0|||600px宽度界面下的扩展工具栏按钮(推荐),默认只显示一行工具栏,点击扩展按钮,显示更多|||1|||zh-cn|||0|||300|||120|||0|||版权所有...|||000000|||12|||宋体||||||0|||jpg|jpeg|||100|||FFFFFF|||1|||1|||gif|jpg|bmp|wmz|png|||100|||100|||1|||66|||17|||5|||5|||0|||100|||100|||1|||5|||5|||88|||31|||1|||0|||1|||1|||1|||1|||1|||0|||0|||0|||||||||1|||{page}|||0|||2000|||1|||0||||||0|||200|||1|||2|||1|||1|||1|||0||||||0|||||||||||||||||||||1|||||||||300|||1||||||||||||||||||1||||||1|||1|||1|||0||||||"

//Toolbar = "0|||TBHandle|FormatBlock|FontName|FontSize|Cut|Copy|Paste|PasteText|FormatBrush|TBSep|Delete|RemoveFormat|TBSep|FindReplace|TBSep|UnDo|ReDo|TBSep|SelectAll|UnSelect|TBSep|TextIndent|LineHeightMenu|MarginTopMenu|MarginBottomMenu|||Toolbar1|||1"
//Toolbar = "0|||TBHandle|Bold|Italic|UnderLine|StrikeThrough|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|ForeColor|BackColor|TBSep|BgColor|BackImage|TBSep|Fieldset|Iframe|HorizontalRule|Marquee|TBSep|CreateLink|Anchor|Map|Unlink|||Toolbar2|||2"
//Toolbar = "0|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormMenu|TBSep|RemoteUpload|LocalUpload|ImportWord|ImportExcel|ImportPPT|ImportPDF|Capture|TBSep|QuickFormat|ParagraphAttr|TBSep|Template|Symbol|Emot|NowDate|NowTime|Quote|TBSep|Print|TBSep|ShowBorders|ZoomMenu|Maximize|About|||Toolbar3|||3"
//Toolbar = "1|||TBHandle|FormatBlock|FontName|FontSize|Cut|Copy|Paste|PasteText|FormatBrush|TBSep|Delete|RemoveFormat|TBSep|FindReplace|TBSep|UnDo|ReDo|TBSep|SelectAll|UnSelect|TBSep|UpperCase|LowerCase|||工具栏1|||1"
//Toolbar = "1|||TBHandle|Bold|Italic|UnderLine|StrikeThrough|SuperScript|SubScript|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|ParagraphAttr|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|Fieldset|HorizontalRule|Marquee|TBSep|CreateLink|Unlink|Map|Anchor|||工具栏2|||2"
//Toolbar = "1|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|RemoteUpload|LocalUpload|ImportWord|ImportExcel|ImportPPT|ImportPDF|Capture|TBSep|TableMenu|TableTemplate|FormMenu|TBSep|QuickFormat|TBSep|Template|Symbol|Emot|NowDate|NowTime|TBSep|Quote|ShowBorders|TBSep|ZoomMenu|Maximize|About|||工具栏3|||3"
//Toolbar = "2|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "2|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "2|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "3|||TBHandle|FontNameMenu|FontSizeMenu|Bold|Italic|UnderLine|StrikeThrough|SuperScript|SubScript|UpperCase|LowerCase|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|OrderedList|UnOrderedList|Indent|Outdent|||工具栏1|||1"
//Toolbar = "3|||TBHandle|Cut|Copy|Paste|PasteText|FormatBrush|FindReplace|Delete|RemoveFormat|QuickFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|Fieldset|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|ParagraphAttr|FormatBlockMenu|||工具栏2|||2"
//Toolbar = "3|||TBHandle|Image|Flash|Media|File|GalleryMenu|RemoteUpload|LocalUpload|ImportWord|ImportExcel|ImportPPT|ImportPDF|Capture|TBSep|TableMenu|TBSep|Template|Symbol|Emot|PrintBreak|Quote|ShowBorders|TBSep|ZoomMenu|Maximize|About|||工具栏3|||3"
//Toolbar = "4|||TBHandle|FormatBlock|FontName|FontSize|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|FindReplace|SpellCheck|TBSep|UnDo|ReDo|TBSep|SelectAll|UnSelect|TBSep|absolutePosition|zIndexBackward|zIndexForward|||Toolbar1|||1"
//Toolbar = "4|||TBHandle|Bold|Italic|UnderLine|StrikeThrough|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|ForeColor|BackColor|TBSep|BgColor|BackImage|TBSep|Fieldset|Iframe|HorizontalRule|Marquee|TBSep|CreateLink|Anchor|Map|Unlink|||Toolbar2|||2"
//Toolbar = "4|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|FormMenu|TBSep|RemoteUpload|LocalUpload|ImportWord|ImportExcel|TBSep|BR|Paragraph|ParagraphAttr|TBSep|Symbol|Emot|Art|NowDate|NowTime|Excel|Quote|TBSep|PrintBreak|Print|TBSep|ShowBorders|ZoomMenu|Refresh|Maximize|About|||Toolbar3|||3"
//Toolbar = "4|||TBHandle|FontMenu|ParagraphMenu|ComponentMenu|ObjectMenu|ToolMenu|FileMenu|TBSep|TableMenu|TableInsert|TableProp|TableCellProp|TableCellSplit|TableRowProp|TableRowInsertAbove|TableRowInsertBelow|TableRowMerge|TableRowSplit|TableRowDelete|TableColInsertLeft|TableColInsertRight|TableColMerge|TableColSplit|TableColDelete|TBSep|FormMenu|FormText|FormTextArea|FormRadio|FormCheckbox|FormDropdown|FormButton|||Toolbar4|||4"
//Toolbar = "4|||TBHandle|TBSep|GalleryMenu|GalleryImage|GalleryFlash|GalleryMedia|GalleryFile|TBSep|Code|MathFlowEQ|TBSep|Big|Small|TBSep|ModeCode|ModeEdit|ModeText|ModeView|TBSep|SizePlus|SizeMinus|TBSep|ZoomSelect|TBSep|Template|QuickFormat|Capture|FontSizeMenu|FontNameMenu|FormatBlockMenu|TBSep|Pagination|PaginationInsert|TBSep|ShowBlocks|FormatBrush|Site|||Toolbar5|||5"
//Toolbar = "4|||TBHandle|TableTemplate|TBSep|TextIndent|LineHeightMenu|MarginBottomMenu|MarginTopMenu|TBSep|ImportPDF|||Toolbar6|||6"
//Toolbar = "5|||TBHandle|Cut|Copy|Paste|TBSep|FontSizeMenu|FontNameMenu|TBSep|Bold|Italic|UnderLine|TBSep|JustifyLeft|JustifyCenter|JustifyRight|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|CreateLink|Unlink|TBSep|HorizontalRule|ForeColor|BackColor|TBSep|About|||工具栏1|||1"
//Toolbar = "6|||TBHandle|FontNameMenu|FontSizeMenu|FormatBlockMenu|TBSep|EditMenu|FontMenu|ParagraphMenu|ComponentMenu|ObjectMenu|ToolMenu|FormMenu|TableMenu|FileMenu|GalleryMenu|TBSep|ZoomMenu|Maximize|About|||mini toolbar|||1"
//Toolbar = "7|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "7|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "7|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "8|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "8|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "8|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "9|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "9|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "9|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "10|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "10|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "10|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "11|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "11|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "11|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "12|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "12|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "12|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "13|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "13|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "13|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Maximize|About|||工具栏3|||3"
//Toolbar = "14|||TBHandle|FontName|FontSize|TBSep|EditMenu|FontMenu|ParagraphMenu|ComponentMenu|ObjectMenu|ToolMenu|FormMenu|TableMenu|FileMenu|GalleryMenu|FormatBlockMenu|ZoomMenu|TBSep|Maximize|About|||mini toolbar|||1"
//Toolbar = "15|||TBHandle|FormatBlock|FontName|FontSize|Bold|Italic|UnderLine|StrikeThrough|TBSep|SuperScript|SubScript|UpperCase|LowerCase|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|||工具栏1|||1"
//Toolbar = "15|||TBHandle|Cut|Copy|Paste|PasteText|FindReplace|Delete|RemoveFormat|TBSep|UnDo|ReDo|SelectAll|UnSelect|TBSep|OrderedList|UnOrderedList|Indent|Outdent|ParagraphAttr|TBSep|ForeColor|BackColor|BgColor|BackImage|TBSep|ImportWord|ImportExcel|ImportPPT|ImportPDF|||工具栏2|||2"
//Toolbar = "15|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|FormatBrush|QuickFormat|Capture|RemoteUpload|TBSep|Fieldset|HorizontalRule|Marquee|CreateLink|Unlink|Map|Anchor|TBSep|Template|Symbol|Emot|Quote|ShowBorders|TBSep|Save|About|||工具栏3|||3"
//Toolbar = "16|||TBHandle|FontName|FontSize|Cut|Copy|Paste|PasteText|FormatBrush|TBSep|Bold|Italic|UnderLine|StrikeThrough|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|ForeColor|BackColor|TBSep|Maximize|ExpandToolbar|||Toolbar1|||1"
//Toolbar = "16|||TBHandle|UnDo|ReDo|TBSep|FormatBlockMenu|ParagraphAttr|TextIndent|LineHeightMenu|MarginTopMenu|MarginBottomMenu|TBSep|PasteWord|SuperScript|SubScript|UpperCase|LowerCase|Delete|RemoveFormat|TBSep|SelectAll|UnSelect|TBSep|FindReplace|TBSep|BgColor|BackImage|TBSep|Fieldset|Iframe|HorizontalRule|Marquee|TBSep|CreateLink|Anchor|Map|Unlink|||Toolbar2|||2"
//Toolbar = "16|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|TableMenu|TableTemplate|FormMenu|TBSep|RemoteUpload|LocalUpload|ImportWord|ImportExcel|ImportPPT|ImportPDF|Capture|TBSep|QuickFormat|TBSep|Template|Symbol|Emot|NowDate|NowTime|Excel|Quote|TBSep|Print|TBSep|ShowBorders|ShowBlocks|ZoomMenu|About|||Toolbar3|||3"
//Toolbar = "17|||TBHandle|FontName|FontSize|Cut|Copy|Paste|PasteText|PasteWord|FormatBrush|TBSep|Bold|Italic|UnderLine|StrikeThrough|TBSep|JustifyLeft|JustifyCenter|JustifyRight|JustifyFull|TBSep|ForeColor|BackColor|TBSep|Maximize|ExpandToolbar|||工具栏1|||1"
//Toolbar = "17|||TBHandle|UnDo|ReDo|TBSep|FormatBlockMenu|ParagraphAttr|TBSep|OrderedList|UnOrderedList|Indent|Outdent|TBSep|SuperScript|SubScript|TBSep|Delete|RemoveFormat|TBSep|SelectAll|UnSelect|TBSep|FindReplace|SpellCheck|TBSep|BgColor|BackImage|TBSep|Fieldset|HorizontalRule|Marquee|TBSep|CreateLink|Unlink|Map|Anchor|||工具栏2|||2"
//Toolbar = "17|||TBHandle|Image|Flash|Media|File|GalleryMenu|TBSep|RemoteUpload|LocalUpload|ImportWord|ImportExcel|ImportPPT|ImportPDF|Capture|TBSep|TableMenu|FormMenu|TBSep|QuickFormat|TBSep|Template|Symbol|Emot|PrintBreak|NowDate|NowTime|TBSep|Quote|ShowBorders|ShowBlocks|ZoomMenu|About|||工具栏3|||3"
%>