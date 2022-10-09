
/**
 * 流程当前进行到的节点的颜色；
 */
var curNodeColor="red";
/**
 * 当前节点之前的节点颜色
 */
var upperNodeColor="green";
/**
 * 流过的线显示颜色
 */
var upperLinkColor="green";
/**
 * 节点id对应的序号
 */
var index=1;

var names = {};
/**
 * 节点默认颜色
 */
var nodefill="#00A9C9";

var defheight="720";
var viewCenterX=-1;
/**
 * 初始化流程图
 * @param diagramName绘图区ID
 * @param isReadOnly是否只读
 * @param paleteName 组件区ID
 * @param toolbar工具区展示区ID
 * @param toolIds要展示的工具按钮序号ID,以逗号分隔的字符串
 * @param scale缩放的尺寸，大于1放大，小于1缩小
 * @param hasRightMenu
 * @param clkCallbak 单击节点回调方法
 * @param stateColor 任务节点颜色
 * @param linkwidth 线条宽度
 * @param highkey 高亮显示的节点的key
 * @return
 */
function init(diagramName,isReadOnly,scale,paleteName,toolbar,toolIds,hasRightMenu,clkCallbak,stateColor,linkwidth,highkey,viewName){
	if (window.goSamples) goSamples(); 
    var gojs= go.GraphObject.make;  
    if(diagramName==null||typeof(diagramName)== "undefined"||diagramName==""){
    	/**art.dialog({
				content: "提供的绘图区ID不能为空!",
				icon:"warning",
			    ok: true
			});  **/
    	alert("提供的绘图区ID不能为空");
    	return;
    }
    var diagramObj=document.getElementById(diagramName);
    if(diagramObj==undefined||typeof(diagramObj)== "undefined"){
    	alert("提供的绘图区域不存在，请先添加该HTML元素");
    	return;
    }
    
    myDiagram =
      gojs(go.Diagram, diagramName,  
        {
          initialContentAlignment: go.Spot.Center,
          allowDrop: true,
          //allowVerticalScroll:false,
          "LinkDrawn": showLinkLabel,
          "LinkRelinked": showLinkLabel,
          "toolManager.mouseWheelBehavior": go.ToolManager.WheelNone,//.WheelScroll,
          "animationManager.isEnabled": false, 
          "undoManager.isEnabled": true
        });
    
    myDiagram.initialPosition.x=0;
    myDiagram.initialPosition.y=0;
    //myDiagram.initialPosition.width=760;
    //myDiagram.initialPosition.height=720;
    //myDiagram.fixedBounds.width=760;
    //myDiagram.fixedBounds.height=1020;
    //myDiagram.fixedBounds.x=0;
    //myDiagram.fixedBounds.y=0; 
    
 // when the document is modified, add a "*" to the title and enable the "Save" button
    
    myDiagram.addDiagramListener("Modified", function(e) {
      var button = document.getElementById("SaveButton");
      if (button) button.disabled = !myDiagram.isModified;
      var idx = document.title.indexOf("*");
      if (myDiagram.isModified) {
        if (idx < 0) document.title += "*";
      } else {
        if (idx >= 0) document.title = document.title.substr(0, idx);
      }
    }); 
    
    myDiagram.addDiagramListener("ViewportBoundsChanged", function(e) {
        var dia = e.diagram;
        var viewb = dia.viewportBounds;
        dia.currentTool.doCancel();
      });
    
    myDiagram.addChangedListener(function(e) {
        // when a Node is moved, invalidate the route for all MultiNodePathLinks that go through it
    	
    	/**
        if (e.change === go.ChangedEvent.Property && e.propertyName === "text" && e.object instanceof go.Node) {
          var diagram = e.diagram;
          var node = e.object;
          if(e.oldValue!=null&&e.oldValue!=""){
        	  alert(e.oldValue);
          }
        }else if (e.change === go.ChangedEvent.Remove&&e.object instanceof go.Node) {alert(e.oldValue);
            var diagram = e.diagram;
            var node = e.object;
            
        }**//** else if (e.change === go.ChangedEvent.Remove && e.object instanceof go.Layer) {
          // when a Node is deleted that has MultiNodePathLinks going through it, invalidate those link routes
          if (e.oldValue instanceof go.Node) {
            var node = e.oldValue;
            if (node._PathLinks) {
              node._PathLinks.each(function(l) { l.invalidateRoute(); });
            }
          } else if (e.oldValue instanceof MultiNodePathLink) {
            // when deleting a MultiNodePathLink, remove all references to it in Node._PathLinks
            var link = e.oldValue;
            var diagram = e.diagram;
            var midkeys = link.data.path;
            if (Array.isArray(midkeys)) {
              for (var i = 0; i < midkeys.length; i++) {
                var node = diagram.findNodeForKey(midkeys[i]);
                if (node !== null && node._PathLinks) node._PathLinks.remove(link);
              }
            }
           }
          }**/
      });
    myDiagram.addDiagramListener("BackgroundSingleClicked", function(e) {
        var dia = e.diagram;
        //e.cancel=true;
        dia.selection.each(function(p) { 
    		//if(p instanceof go.Node){
    			p.isSelected=false;
    		//}
    	});
        //alert("Double-clicked at " + e.diagram.lastInput.documentPoint);
        //dia.currentTool.doCancel();
     });
    function nodeStyle() {
    	if(hasRightMenu){
    		return [
    		          new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
    		          //new go.Binding("name", "key",function(v){return v>0? v: -v;}).makeTwoWay(),
    		          new go.Binding("text", "text"),
    		          {
    		            locationSpot: go.Spot.Center,
    		            mouseEnter: function (e, obj) { showPorts(obj.part, true); },
    		            mouseLeave: function (e, obj) { showPorts(obj.part, false); },
    		            contextMenu: nodeMenu
    		            //toolTip:nodeToolTip
    		            //doubleClick:function(e,node){alert(node.data.key);}
    		          }
    		        ];
    	}else{
    		return [
    		          new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
    		          //new go.Binding("name", "key",function(v){return v>0? v: -v;}).makeTwoWay(),
    		          new go.Binding("text", "text"),
    		          {
    		            locationSpot: go.Spot.Center,
    		            mouseEnter: function (e, obj) { showPorts(obj.part, true); },
    		            mouseLeave: function (e, obj) { showPorts(obj.part, false); }
    		            //toolTip:nodeToolTip
    		            //doubleClick:function(e,node){alert(node.data.key);}
    		          }
    		        ];
    	}
        
      }
    var nodeToolTip=
    	gojs(go.Adornment, "Auto",
     		gojs(go.Shape, { fill: "#CCFFCC" }),
     		gojs(go.TextBlock, { margin: 4 },new go.Binding("text", "key"))
     	);
    
    var nodeMenu =  // context menu for each Node
 		gojs(go.Adornment, "Vertical",
    		gojs("ContextMenuButton",
      			gojs(go.TextBlock, "Copy"),
      			{ click: function (e, obj) {e.diagram.commandHandler.copySelection();} }),
          	makeButton("Paste",
                     function(e, obj) { e.diagram.commandHandler.pasteSelection(e.diagram.lastInput.documentPoint);},
                     function(o) { return o.diagram.commandHandler.canPasteSelection(); }),	
            makeButton("Delete",
                     function(e, obj) { e.diagram.commandHandler.deleteSelection(); },
                     function(o) { return o.diagram.commandHandler.canDeleteSelection(); })
      	);
    function makePort(name, spot, output, input) {
        return gojs(go.Shape, "Circle",
                 {
                    fill: "transparent",
                    stroke: null,  // this is changed to "white" in the showPorts function
                    desiredSize: new go.Size(8, 8),
                    alignment: spot, alignmentFocus: spot,  // align the port on the main Shape
                    portId: name,  // declare this object to be a "port"
                    fromSpot: spot, toSpot: spot,  // declare where links may connect at this port
                    fromLinkable: output, toLinkable: input,  // declare whether the user may draw links to/from here
                    cursor: "pointer"  // show a different cursor to indicate potential link point
                 });
      }
    /**
    myDiagram.mouseDragOver = function(e) {
        myDiagram.currentCursor = "no-drop";
      };
     **/
    myDiagram.mouseDrop = function(e) {
    	var selnode=myDiagram.selection.first();
    	var height=document.getElementById("diagramHeight")
    	if(height!=undefined||typeof(height)!= "undefined"){
    		var old=document.getElementById("diagramHeight").value;
        	var bordsize=parseInt(old)-60;
        	if(selnode instanceof go.Node) {
        		if(selnode.location.y>=bordsize){
        			//alert("节点要在可视范围内");
        			//myDiagram.currentTool.doCancel();
        			myDiagram.startTransaction("cheight");
        			myDiagram.div.style.height=parseInt(old)+100+"px";
        			//document.getElementById("sample").style.height=parseInt(old)+106+"px";
        			document.getElementById("diagramHeight").value=parseInt(old)+100+"px";
        			myDiagram.commitTransaction("cheight");
        		}
        	}
        }
    }; 
    
    var lightText = 'whitesmoke';
    var linkthick=2;//线默认宽度
    
    if(stateColor!=null&&typeof(stateColor)!= "undefined"&&stateColor!=""){	
    	nodefill=stateColor;
    }

    if(typeof(linkwidth)== "number"){	
    	linkthick=linkwidth;
    }

    var statetemple=
      gojs(go.Node, "Spot", nodeStyle(),
        gojs(go.Panel, "Auto",
          gojs(go.Shape, "Rectangle",{ fill: nodefill, stroke: null,name:"shape" },
        		  new go.Binding("fill", "color"),
        		  new go.Binding("stroke", "highlight", function(v) { return v ? "red" : "blue"; }),
        		  new go.Binding("strokeWidth", "highlight", function(v) { return v ? 5 : 2; }),
        		  //new go.Binding("fill", "isSelected", function(sel) {if (sel) return "red"; else return "#00A9C9";}).ofObject(""),
        		  new go.Binding("figure", "figure")),
          gojs(go.TextBlock,
            {
              font: "bold 10pt Helvetica, Arial, sans-serif",
              stroke: lightText,
              margin: 8,
              name:"txtNode",
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: true,
              click:function(e,obj){
              	var part = obj.part;
    			if(part instanceof go.Node) {
    				var node = part;
    				if(typeof(clkCallbak)=="function"){
    					clkCallbak(node.data);
    				}
    			}
              }
               //doubleClick:function(e,node){}  
            },
            new go.Binding("editable", "editable", function(v) {return v ? true: false; }),
            new go.Binding("text", "text").makeTwoWay())
        ),
        {
            click: function(e, obj) {
    	  		var part = obj.part;
    	  		if(part instanceof go.Node) {
    	  			var node = part;
    	  			if(typeof(clkCallbak)=="function"){
    	  				clkCallbak(node.data);
    	  			}
    	  		}
      		},
            selectionChanged: function(part) {
                //var old = part.elt(0);
    	  		if (part.isSelected) {
    	  			var txtNode=part.findObject("txtNode");
	  				//txtNode.visible=false;
	  				var shape=part.findObject("shape");
	  				shape.fill="red";
	  				txtNode.font="bold 11pt Helvetica, Arial, sans-serif";
    	  		}else{
    	  			var txtNode=part.findObject("txtNode");
    	  			txtNode.font="bold 10pt Helvetica, Arial, sans-serif";
    	  			var shape=part.findObject("shape");
    	  			if(part.data.color){
    	  				shape.fill=part.data.color;
    	  			}else{
    	  				shape.fill="#00A9C9";
    	  			}
    	  		}   
              }
          }, 
        // four named ports, one on each side:
        makePort("T", go.Spot.Top, true, true),
        makePort("L", go.Spot.Left, true, true),
        makePort("R", go.Spot.Right, true, true),
        makePort("B", go.Spot.Bottom, true, true)
      );
    var starttemp=gojs(go.Node, "Spot", nodeStyle(),
            gojs(go.Panel, "Auto",
                    gojs(go.Shape, "Circle",
                      { minSize: new go.Size(40, 60), fill: "#79C900", stroke: null }),
                    gojs(go.TextBlock, "开始",
                      { margin: 5, font: "bold 11pt Helvetica, Arial, sans-serif", stroke: lightText })
                  ),{
        				click: function(e, obj) {
						var part = obj.part;
						if(part instanceof go.Node) {
							var node = part;
							if(typeof(clkCallbak)=="function"){
								clkCallbak(node.data);
							}
						}
	 				}
			  },
                  // three named ports, one on each side except the top, all output only:
                  makePort("L", go.Spot.Left, true, false),
                  makePort("R", go.Spot.Right, true, false),
                  makePort("B", go.Spot.Bottom, true, false)
                );
    var endtemp=gojs(go.Node, "Spot", nodeStyle(),
            gojs(go.Panel, "Auto",
                    gojs(go.Shape, "Circle",
                      { minSize: new go.Size(40, 60), fill: "#DC3C00", stroke: null }),
                    gojs(go.TextBlock, "结束",
                      { margin: 5, font: "bold 11pt Helvetica, Arial, sans-serif", stroke: lightText })
                  ),{
        				click: function(e, obj) {
						var part = obj.part;
						if(part instanceof go.Node) {
							var node = part;
							if(typeof(clkCallbak)=="function"){
								clkCallbak(node.data);
							}
						}
	 				}
			  },
                  // three named ports, one on each side except the bottom, all input only:
                  makePort("T", go.Spot.Top, false, true),
                  makePort("L", go.Spot.Left, false, true),
                  makePort("R", go.Spot.Right, false, true)
                );
    var commenttemp=gojs(go.Node, "Auto", nodeStyle(),
            gojs(go.Shape, "File",
                    { fill: "#EFFAB4", stroke: null }),
                  gojs(go.TextBlock,
                    {
                      margin: 5,
                      maxSize: new go.Size(200, NaN),
                      wrap: go.TextBlock.WrapFit,
                      textAlign: "center",
                      editable: true,
                      font: "bold 12pt Helvetica, Arial, sans-serif",
                      stroke: '#454545'
                    },
                    new go.Binding("text", "text").makeTwoWay())
                );
    var pstatetemple= 
        gojs(go.Node, "Spot", nodeStyle(),
          // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
          gojs(go.Panel, "Auto",
            gojs(go.Shape, "Rectangle",{ fill: nodefill, stroke: null },
          		  new go.Binding("fill", "color"),
          		  new go.Binding("figure", "figure")),
            gojs(go.TextBlock,
              {
                font: "bold 10pt Helvetica, Arial, sans-serif",
                stroke: lightText,
                margin: 8,
                name:"txtNode",
                maxSize: new go.Size(160, NaN),
                wrap: go.TextBlock.WrapFit,
                editable: true 
              },  
              new go.Binding("text", "text").makeTwoWay())
          ),
          // four named ports, one on each side:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, true),
          makePort("R", go.Spot.Right, true, true),
          makePort("B", go.Spot.Bottom, true, false)
        );
    myDiagram.nodeTemplateMap.add("",statetemple);
    myDiagram.nodeTemplateMap.add("start",starttemp);
    myDiagram.nodeTemplateMap.add("end",endtemp);
    myDiagram.nodeTemplateMap.add("Comment",commenttemp);


    // replace the default Link template in the linkTemplateMap
    myDiagram.linkTemplate =
      gojs(go.Link,  // the whole link panel
        {
          routing: go.Link.AvoidsNodes,
          curve: go.Link.JumpOver,
          corner: 5, toShortLength: 4,
          relinkableFrom: true,
          relinkableTo: true,
          reshapable: true,
          doubleClick:function(e,obj){
    	  	if(!myDiagram.isReadOnly&&confirm("确定删除连接线吗？")){
    	  		e.diagram.commandHandler.deleteSelection();
    	  	}
      	  }
        },
        new go.Binding("points").makeTwoWay(),
        gojs(go.Shape,  // the link path shape
          { isPanelMain: true, stroke: "gray", strokeWidth: linkthick },
             new go.Binding("strokeWidth", "linkwidth"),
             new go.Binding("stroke", "color")),
        gojs(go.Shape,  // the arrowhead
          { toArrow: "standard", stroke: null, fill: "gray"}),
        gojs(go.Panel, "Auto",  // the link label, normally not visible
          { visible: false, name: "LABEL", segmentIndex: 2, segmentFraction: 0.5},
          new go.Binding("visible", "visible").makeTwoWay(),
          gojs(go.Shape, "RoundedRectangle",{ fill: "#F8F8F8", stroke: null }),// the label shape
          gojs(go.TextBlock, "是",  // the label
            {
              textAlign: "center",
              width:26,
              font: "10pt helvetica, arial, sans-serif",
              stroke: "#333333",
              editable: true
            },
            new go.Binding("text", "text").makeTwoWay())
        )
      );

	
	function makeButton(text, action, visiblePredicate) {
      if (visiblePredicate === undefined) visiblePredicate = function() { return true; };
      return gojs("ContextMenuButton",
               gojs(go.TextBlock, text),
               { click: action },
               new go.Binding("visible", "", visiblePredicate).ofObject());
    }

	// Make link labels visible if coming out of a "conditional" node.
    // This listener is called by the "LinkDrawn" and "LinkRelinked" DiagramEvents.
    function showLinkLabel(e) {
      var label = e.subject.findObject("LABEL");
      if (label !== null) label.visible = (e.subject.fromNode.data.figure === "Diamond");
    }
    function showPorts(node, show) {
        var diagram = node.diagram;
        if (!diagram || diagram.isReadOnly || !diagram.allowLink) return;
        node.ports.each(function(port) {
            port.stroke = (show ? "white" : null);
          });
      }

    myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;
    
    if(scale!=null&&scale!=""){
    	var num=parseFloat(scale);
    	if(!isNaN(num)){
    		myDiagram.scale=num;
    	}
 	}
    
    if(isReadOnly){
 		myDiagram.isReadOnly=true;
 		//myDiagram.allowDragOut=false;
 		//myDiagram.allowMove=false;
 		//myDiagram.isEnabled=false;
 		//myDiagram.allowSelect=true;
 		myDiagram.model.isReadOnly=true;
 		
 	}else{
 		var paleteObj=document.getElementById(paleteName);
 		if(paleteName!=null&&typeof(paleteName)!= "undefined"&&paleteName!=""){//load palette area
 			if(paleteObj==undefined||typeof(paleteObj)== "undefined"){
 		    	alert("提供的组件区域不存在，请确认存在该HTML元素");
 		    }else{
 		    	myPalette =gojs(go.Palette, paleteName,{ "animationManager.duration": 800});
 	 			var pmodel=[  // specify the contents of the Palette
 	 			            { category: "start", text: "Start" },
 	 			            { text: "过程" },
 	 			            { text: "判断", figure: "Diamond" },
 	 			            { category: "end", text: "结束" },
 	 			            { category: "Comment", text: "标注", figure: "RoundedRectangle" }];
 	 			myPalette.nodeTemplateMap.add("",pstatetemple);
 	 			myPalette.nodeTemplateMap.add("start",starttemp);
 	 			myPalette.nodeTemplateMap.add("end",endtemp);
 	 			myPalette.nodeTemplateMap.add("Comment",commenttemp);
 	 			myPalette.model = new go.GraphLinksModel(pmodel);
 	 			myPalette.layout.sorting = go.GridLayout.Forward;
 		    }
 		}

 		var toolbarObj=document.getElementById(toolbar);
 		if(toolbar!=null&&typeof(toolbar)!= "undefined"&&toolbar!=""&&toolbarObj!= undefined){
 			if(toolbarObj==undefined||typeof(toolbarObj)== "undefined"){
 		    	alert("提供的工具区域不存在，请确认存在该HTML元素");
 		    }else{
 		    	var html="<b>工具栏:</b><div style='float:right;display:block;'>";
 		    	if(toolIds!=""&&toolIds!=null){
 		    		var ids=toolIds.split(",");
 		    		var btns=new Array();
 		    		btns.push('<button style="cursor: pointer;background-color: white;width:80px;padding-bottom:10px;" onclick="nodeAlign(\'v\')">垂直对齐</button>&nbsp;');
 		    		btns.push('<button style="cursor: pointer;background-color: white;width:80px;padding-bottom:10px;" onclick="nodeAlign(\'h\')">水平对齐</button>&nbsp;');
 		    		btns.push('<button style="cursor: pointer;background-color: white;width:80px;padding-bottom:10px;" onclick="myDiagram.commandHandler.undo()">撤销</button>&nbsp;');
 		    		btns.push('<button style="cursor: pointer;background-color: white;width:80px;padding-bottom:10px;" onclick="myDiagram.commandHandler.redo()">&nbsp;重做&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.selectAll()">&nbsp;全选&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.increaseZoom()">&nbsp;放大&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.decreaseZoom()">&nbsp;缩小&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.resetZoom()">还原大小</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="deleteNode()">&nbsp;删除&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.cutSelection()">&nbsp;剪切&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.copySelection()">&nbsp;复制&nbsp;</button>&nbsp;');
	 				btns.push('<button style="cursor: pointer;background-color: white;width:50px;padding-bottom:10px;" onclick="myDiagram.commandHandler.pasteSelection()">&nbsp;黏贴&nbsp;</button>&nbsp;');
 		    		for(var i=0;i<ids.length;i++){
 		    			if(!isNaN(ids[i])&&ids[i]<12){
 		    				html+=btns[ids[i]];
 		    				//if(i%2) html+='<br/>';
 		    			}
 		    		}
 		    	}else{
 		    		html='<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="nodeAlign(\'v\')">垂直对齐</button>&nbsp;';
 	 	 			html+='<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="nodeAlign(\'h\')">水平对齐</button>&nbsp;'
 	 	 				+'<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="myDiagram.commandHandler.undo()">&nbsp;撤销&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="myDiagram.commandHandler.redo()">&nbsp;重做&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="myDiagram.commandHandler.selectAll()">&nbsp;全选&nbsp;</button>'
 	 					+'<button style="cursor: pointer;background-color: white;margin-bottom:5px;" onclick="myDiagram.commandHandler.increaseZoom()">&nbsp;放大&nbsp;</button>&nbsp;'
 	 					+'<br/>'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="myDiagram.commandHandler.decreaseZoom()">&nbsp;缩小&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="myDiagram.commandHandler.resetZoom()">还原大小</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="deleteNode()">&nbsp;删除&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="myDiagram.commandHandler.cutSelection()">&nbsp;剪切&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="myDiagram.commandHandler.copySelection()">&nbsp;复制&nbsp;</button>&nbsp;'
 	 					+'<button style="cursor: pointer;background-color: white;" onclick="myDiagram.commandHandler.pasteSelection()">&nbsp;黏贴&nbsp;</button>&nbsp;';
 		    	}
 		    	html+='</div>';
 				document.getElementById(toolbar).innerHTML=html;
 		    }
 		} 
 		var viewObj=document.getElementById(viewName);
 		if(viewName!=null&&typeof(viewName)!= "undefined"&&viewName!=""&&viewObj!= undefined){//load view area
 			if(viewObj==undefined||typeof(viewObj)== "undefined"){
 		    	alert("提供的概述区域不存在，请确认存在该HTML元素");
 		    }else{
 		    	myOverview =
 	 		        gojs(go.Overview, viewName,
 	 		          {
 	 		            observed: myDiagram,
 	 		            contentAlignment: go.Spot.Center
 	 		         });
 		    }
 		}
 	}
    /**
    var tool = myDiagram.toolManager.clickSelectingTool;
    tool.standardMouseSelect = function() {
     alert(111);
      go.ClickSelectingTool.prototype.standardMouseSelect.call(tool);
    } **/
    
    myDiagram.commandHandler.deleteSelection = function() {
    	var items=myDiagram.selection;
        if(items.count > 0) {  // if there are any selected items, delete them
        	var flg=true;
        	myDiagram.selection.each(function(p) { 
        		if(p instanceof go.Node){
        			if(p.data.editable==false){
        				flg=false;
        			}
        		}
        	});
        	if(flg){
        		 myDiagram.startTransaction("delete items");
        		 myDiagram.removeParts(items,true);
        		 myDiagram.commitTransaction("delete items");
        	}else{
        		alert("节点包含关联数据，不能进行删除！");
        	}	
         }/**else{
        	 go.CommandHandler.prototype.deleteSelection.call(myDiagram.commandHandler); 
         }**/
      };
    
    load();
    if(highkey!=null&&typeof(highkey)!= "undefined"&&highkey!=""){
    	Twinkle(highkey);
    } 
}
/*************************************************************/


function deleteNode(){
	var flg=true;
	//myDiagram.commandHandler.deleteSelection();
	myDiagram.selection.each(function(p) { 
		if(p instanceof go.Node){
			if(p.data.editable==false){
				flg=false;
			}
		}
	});
	if(flg){
		myDiagram.commandHandler.deleteSelection();
	}else{
		alert("节点包含关联数据，不能进行删除！");
	} 
}

function designTool(num) {
	if(num=='1'){
		nodeAlign("v");
	}else if(num=='2'){
		nodeAlign("h");
	}else if(num=='3'){
		myDiagram.commandHandler.undo();
	}else if(num=='4'){
		myDiagram.commandHandler.redo();
	}else if(num=='5'){
		myDiagram.commandHandler.selectAll();
	}else if(num=='6'){
		myDiagram.commandHandler.increaseZoom();
	}else if(num=='7'){
		myDiagram.commandHandler.decreaseZoom();
	}else if(num=='8'){
		myDiagram.commandHandler.cutSelection();
	}else if(num=='9'){
		myDiagram.commandHandler.pasteSelection();
	}else if(num=='10'){
		myDiagram.commandHandler.copySelection();
	}else if(num=='11'){
		deleteNode();
	}else if(num=='12'){
		myDiagram.commandHandler.selectAll();
		deleteNode();
	}
}

function save() {
   	handleNode();
   	handleLink();
    document.getElementById("mySavedModel").value = myDiagram.model.toJson();
    myDiagram.isModified = false;
 }
/**
 * 默认加载流程数据
 * @return
 */
function load() {
   myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
}
/**
 * 鼠标事件
 * @return
 */
function diagramOut(){
	if(myDiagram!=null&&typeof(myDiagram)!= "undefined"&&myDiagram!=""&&myDiagram.isModified){
		document.getElementById("mySavedModel").value = myDiagram.model.toJson();
		var flowdata=document.getElementById("mySavedModel").value;
	}
}


/**
 * 
 * @param flowdata 
 * @return
 */
function loadflowData(flowdata) {
	myDiagram.model = go.Model.fromJson(flowdata);
	document.getElementById("mySavedModel").value=myDiagram.model.toJson();
}

/**
 * 获取当前流程图的图数据
 * @return
 */
function getCurrentflowData() {
   	handleNode();
   	handleLink();
    document.getElementById("mySavedModel").value = myDiagram.model.toJson();
    return myDiagram.model.toJson();
 }

/**
 * 改变节点颜色
 * @param color
 * @return
 */
function changeAllNodeColor(color){
	var nodeArray=myDiagram.model.nodeDataArray;
  	var model=myDiagram.model;
	for(var i=0;i<nodeArray.length;i++){
		model.startTransaction("updatecolor");
		model.setDataProperty(nodeArray[i], "color", color);
		model.commitTransaction("updatecolor");
	}
}

/**
 * 改变线条宽度
 * @param width
 * @return
 */
function changeAllLinkWidth(width){
	var linkArray=myDiagram.model.linkDataArray;
  	var model=myDiagram.model;
	for(var i=0;i<linkArray.length;i++){
		model.startTransaction("updatecolor");
		model.setDataProperty(linkArray[i], "linkwidth", width);
		model.commitTransaction("updatecolor");
	}
}

/**
 * 调整线条色彩
 * @param color
 * @return
 */
function changeAllLinkColor(color){
	var linkArray=myDiagram.model.linkDataArray;
  	var model=myDiagram.model;
	for(var i=0;i<linkArray.length;i++){
		model.startTransaction("updatecolor");
		model.setDataProperty(linkArray[i], "color", color);
		model.commitTransaction("updatecolor");
	}
}
/**
 * 设置正在处理的节点颜色
 * @param key
 * @return
 */
function setCurNodeColor(key){
  	var model=myDiagram.model;
  	var curnode=myDiagram.findNodeForKey(key);
  	if(curnode instanceof go.Node){
  		var data=myDiagram.model.findNodeDataForKey(key);
  		model.startTransaction("updatecurcolor");
  		model.setDataProperty(data, "color", curNodeColor);
  		model.commitTransaction("updatecurcolor");
  	}
}

/**
 * 设置当前节点之前的节点颜色
 * @param key 当前节点的key
 * @return
 */
function setUpperNode(key){
	var curnode=myDiagram.findNodeForKey(key);
	var model=myDiagram.model;
	if(curnode instanceof go.Node){
		var data=myDiagram.model.findNodeDataForKey(key);
		model.startTransaction("updateupperNodecolor");
		model.setDataProperty(data, "color", upperNodeColor);
		model.commitTransaction("updateupperNodecolor");
		var pre=key-1;
		if(pre>0){
			setUpperNode(pre);
		}
	}
}

/**
 * 设置当前节点以上的流程线颜色样式
 * @param key
 * @return
 */
function setUpperLink(key){
	var curnode=myDiagram.findNodeForKey(key);
	var model=myDiagram.model;
	var keys=new Array();
	if(curnode instanceof go.Node){
		var it = curnode.findLinksInto();
		while (it.next()) {//设置节点的子节点key值
			var child = it.value;
				if(child.data) {
					model.startTransaction("updatelinkcolor");
					model.setDataProperty(child.data, "color", upperLinkColor);
					model.commitTransaction("updatelinkcolor");
					if(child.data.from<child.data.to){
						setUpperLink(child.data.from);
					}
				}
		}
	}
}


/**
 * 调整节点数据
 * @return
 */
function handleNode(){
  	var nodeArray=myDiagram.model.nodeDataArray;
  	var model=myDiagram.model;
   	for(var i=0;i<nodeArray.length;i++){
   		var locs=nodeArray[i].loc;
    	var array=locs.split(" ");
    	var xinx=array[0].indexOf(".");
    	var yinx=array[1].indexOf(".");
    	var x=array[0];
    	var y=array[1];
    	if(xinx>0){
    		x=array[0].substr(0, xinx);
    	}
    	if(yinx>0){
    		y=array[1].substr(0, yinx);
    	}
    	//nodeArray[i].loc=x+" "+y;
    	model.startTransaction("handleNode");
		model.setDataProperty(nodeArray[i], "loc", x+" "+y);
		model.commitTransaction("handleNode");
   	}
  }
  /**
   * 
   * @return
   */
  function handleLink(){
  	var linkArray=myDiagram.model.linkDataArray;
  	var model=myDiagram.model;
   	for(var i=0;i<linkArray.length;i++){
   		var points=linkArray[i].points;//List(Point);
   		var it = points.iterator;
  		while (it.next()) {//setElt(i, val) 
    		//window.console.log("#" + it.key + " is " + it.value);
    		var x=it.value.x;
   			var y=it.value.y;
   			var xstr=x.toString();
   			var ystr=y.toString();
   			var xinx=xstr.indexOf(".");
    		var yinx=ystr.indexOf(".");
    		model.startTransaction("updatelink");
    		if(xinx>0){
    			x=x.toFixed(1);
    			it.value.x=parseFloat(x);
    		}
    		if(yinx>0){
    			y=y.toFixed(1);
    			it.value.y=parseFloat(y);
    		}
	   		model.commitTransaction("updatelink");
  		}
   	  } 
  }
  
  /**
   * 流程图提交审批时进行编号 FJ00116003
   * @return
   */
  function handleNodeKey(courseId){
	  	var nodeArray=myDiagram.model.nodeDataArray;
	  	var model=myDiagram.model;
	  	var start,num=0;
	   	for(var i=0;i<nodeArray.length;i++){
	   		var cat=nodeArray[i].category;
	    	if(cat=="start"){
	    		start=nodeArray[i];
	    		num++;
	    	}
	   	}
	   	if(num>1||num==0){
	   		alert("开始节点有且只能有一个,请进行调整后再提交");
	   		return;
	   	}else{//开始节点
	   		var data = myDiagram.model.findNodeDataForKey(start.key);
	   		//alert(JSON.stringify(data));
	   		model.startTransaction("updatekey");
	   		model.setDataProperty(data, "id", courseId+'.1');
	   		model.commitTransaction("updatekey");
	   		
	   		var startnode=myDiagram.findNodeForKey(start.key);
	    	var it = startnode.findNodesOutOf();
	    	var j=1;
	    	index=j;
	    	var keys=new Array();
			while (it.next()) {//设置节点的子节点key值 -- 1
				var child = it.value;
	  			if(child.data) {
	  				j=j+1;
	  				index=j;
	  				model.startTransaction("updatekey");
	   				model.setDataProperty(child.data, "id",courseId+"."+j);
	   				model.setDataProperty(child.data, "isdeal", "true");
	   				model.commitTransaction("updatekey");
	   				keys.push(child.data.key);
	  			}
	  		}	
	  		updateKey(courseId,keys,j);
	  		
	  		for(var i=0;i<nodeArray.length;i++){
	  			if(typeof(nodeArray[i].isdeal)== "undefined"||nodeArray[i].isdeal== undefined){
	  				
	  				model.startTransaction("updatedeal");
	  				index=index+1;
		   			model.setDataProperty(nodeArray[i], "isdeal", "false");
		   			model.setDataProperty(nodeArray[i], "key",courseId+"."+index);
		   			model.setDataProperty(nodeArray[i], "id",courseId+"."+index);
		   			model.commitTransaction("updatedeal"); 
	  			}else{
	  				model.startTransaction("updatedeal");
		   			model.setDataProperty(nodeArray[i], "isdeal", "false");
		   			model.setDataProperty(nodeArray[i], "key", nodeArray[i].id);
		   			model.commitTransaction("updatedeal");
	  			}
	  		}
	   	} 
	  }
	  
function updateKey(courseId,keys,j){
		var ckeys=new Array();
		var model=myDiagram.model;
	    for(var i=0;i<keys.length;i++){
	    	var node = myDiagram.findNodeForKey(keys[i]);
	    	if(node){
	  			var itc = node.findNodesOutOf();
	  			while (itc.next()) {//设置节点的子节点key值
					var child = itc.value;
	  				if(child.data) {
	  					var into = child.findNodesInto();
	  					if(into.count>1){
	  						var count=into.count;
	  						var counter=0;
	  						while (into.next()) {
	  							var parent = into.value;
	  							if(parent.data.isdeal){counter=counter+1;}	
	  						}
	  						if(count==counter&&child.data.isdeal!="true"){
	  							j=j+1;
	  							index=j;
	  							model.startTransaction("updatekey");
	   							model.setDataProperty(child.data, "id",courseId+"."+j);
	   							model.setDataProperty(child.data, "isdeal", "true");
	   							model.commitTransaction("updatekey");
	   							ckeys.push(child.data.key);
	  						}
	  					}else{
	  						j=j+1;
	  						index=j;
	  						model.startTransaction("updatekey");
	   						model.setDataProperty(child.data, "id",courseId+"."+j);
	   						model.setDataProperty(child.data, "isdeal", "true");
	   						model.commitTransaction("updatekey");
	   						ckeys.push(child.data.key);	
	  					}
	  				}
	  			}
	    	}	
	  	}
	  	if(ckeys.length>0){
	  		updateKey(courseId,ckeys,j);
	  	}
}
	
/**
 * 设置node algin
 * @param flag
 * @return
 */
  function nodeAlign(flag){
   	var sset=myDiagram.selection;
   	var it = sset.iterator;
   	var myArray=new Array();
   	var keys=new Array();
   	var i=0;
   	var index=0;
   	if(flag=="h"){index=1};
    	while (it.next()) {
      	if(it.value instanceof go.Node){
      		var locs=it.value.data.loc;
      		var array=locs.split(" ");
      		myArray[i]=parseInt(array[index]);
      		keys[i]=it.value.data.key;
      		i++;
      	}
    	}
    	myArray=myArray.sort(function (a,b){return a-b;}); 
    	var mid=parseInt(myArray.length/2);
    	var midx=myArray[mid];
    
    	//var nodes=myDiagram.nodes;
    	//myDiagram.moveParts(myDiagram.selection, go.Point.parse(""+0+" "+midx+""), false);
    	for(var j=0;j<keys.length;j++){
    		var curnode=myDiagram.findNodeForKey(keys[j]);
    		var data = myDiagram.model.findNodeDataForKey(keys[j]);
    		//if (data !== null) myDiagram.model.setDataProperty(data, "color", "green");
    		var list=new go.List(go.Node);
    		list.add(curnode);
    		if(flag=="h"){
    			var xy=midx-curnode.location.y;
    			myDiagram.moveParts(list, go.Point.parse("0 "+xy+""), false);
    		}else{
    			myDiagram.moveParts(list, go.Point.parse(""+midx-curnode.location.x+" "+0+""), false);
    		}
    	} 
   }  
var timer;  
function flash(key) {
	var model = myDiagram.model;
	var data = myDiagram.model.findNodeDataForKey(key);
	if(data==null||data==undefined){
		clearTimeout(timer);
	}else{
		model.startTransaction("flash");
		if(data.highlight!=null&&typeof(data.highlight)!= "undefined"&&data.highlight!=""){
			model.setDataProperty(data, "highlight", !data.highlight);
		}else{
			model.setDataProperty(data, "highlight", true);
		}
		model.commitTransaction("flash");
	}
}
/**
 * 闪烁节点
 * @param key节点键值
 * @return
 */
function Twinkle(key) {
	timer=setTimeout(function() { flash(key); Twinkle(key); }, 500);
}  

/**
 * 输出svg流程图
 * @param svgArea
 * @return
 */
function makeSVG(svgArea) {
    var svg = myDiagram.makeSvg({
        scale: 0.5
      });
    //svg.style.border = "1px solid black";
    obj = document.getElementById(svgArea);
    obj.appendChild(svg);
    if (obj.children.length > 0) {
    	obj.replaceChild(svg, obj.children[0]);
    }
    //changeAllNodeColor("red");
  }

function makeImage(imageArea) {
    var image = myDiagram.makeImage({
        scale: 0.5
      });
    image.style.border = "1px solid black";
    obj = document.getElementById(imageArea);
    obj.appendChild(image);
    if (obj.children.length > 0) 
      obj.replaceChild(image, obj.children[0]);
    //changeAllLinkWidth("5");
    //changeAllLinkColor("green");
  }

function generateImageData(id){
	var img = document.getElementById(id);
	img.src = myDiagram.makeImageData({
	    scale: 1,
	    // PhantomJS tends to handle transparency poorly in the images it renders,
	    // so we prefer to use a white background:
	    background: "white"
	 })
}

/**
 * cancel selected;
 * @return
 */
function cancelSelected(){
	var nodeArray=myDiagram.model.nodeDataArray;
	for(var i=0;i<nodeArray.length;i++){
		var cat=nodeArray[i].category;
		if(cat!="end"&&cat!="start"&&cat!="Comment"){
			var node=myDiagram.findNodeForKey(nodeArray[i].key);
			var txtNode=node.findObject("txtNode");
			txtNode.font="bold 10pt Helvetica, Arial, sans-serif";
			var shape=node.findObject("shape");
			if(node.data.color){
  				shape.fill=node.data.color;
  			}else{
  				shape.fill="#00A9C9";
  			}
		}
	}
}

function findJsonDataForKey(key){
	var jsonstr=myDiagram.model.toJson();
	var jsonObj=eval('('+jsonstr+')');
	var nodes=jsonObj.nodeDataArray;
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].key==key){
			return nodes[i];
		}
	}
	return "";
	//alert(jsonObj.nodeDataArray[0]);
	/**
	var nodeArray=myDiagram.model.nodeDataArray;
  	var model=myDiagram.model;
	for(var i=0;i<nodeArray.length;i++){
		if(nodeArray[i].key==key){
			return nodeArray[i];
		}
	}  **/
}

function findNodeForId(id){
	var nodeArray=myDiagram.model.nodeDataArray;
	var model=myDiagram.model;
	for(var i=0;i<nodeArray.length;i++){
		var cat=nodeArray[i].category;
		if(nodeArray[i].id==id){
			var node=myDiagram.findNodeForKey(nodeArray[i].key);
			//model.startTransaction("setselected");
			//model.setDataProperty(nodeArray[i], "isSelected",true);
			//model.commitTransaction("setselected");
			myDiagram.select(node);
			break;
		}
	}
}	
function centerDiagram(){
	var cb=myDiagram.computeBounds();
	var b = myDiagram.computePartsBounds(myDiagram.nodes);
	var view=myDiagram.viewportBounds;
	viewCenterX=view.width/2;
	var cha=b.centerX-viewCenterX;
	//处理图形过左的情况
	if(b.x<0){
		myDiagram.initialPosition.x=b.x;
	}else if(cb.width>view.width){
		myDiagram.initialPosition.x=b.left;
	}else{
		myDiagram.initialPosition.x=cha;
	}
	//处理居中显示
   //myDiagram.fixedBounds.centerX=b.centerX;
}
