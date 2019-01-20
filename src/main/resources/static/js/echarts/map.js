var flylineMap = function(option) {
	var defaults = {
		id : "flyline",
		geoJsonFile : "china.json",
		speed : 20, // 每帧移动的像素大小，每帧（对于大部分显示屏）大约16~17毫秒
		curvature : 0.0008, // 实际指焦点到准线的距离，你可以抽象成曲率，这里模拟扔物体的抛物线，因此是开口向下的
		lineLength : 30,// 飞线长度（占总长度的百分比）
		arcSpeed : 30,// 圆圈扩散时间，越大越慢
		cp : [ 107, 31 ],// 地图中心点
		size : 850,// 地图比例大小
		boundaryColor : "#fff",// 边界线颜色
		boundaryWidth : 1,// 边界线宽度
		backgroundColor : "#2342",// 背景色
		flyLineRgba : "255,0,0",// 飞线颜色
		flyLimit : 3,// 在飞飞线数量
		cacheLimit : 2000,// 缓存飞线数量
		complete : function() {},// 飞线飞行结束回调函数
		areaClick : function() {},// 点击区域事件函数
		start : function() {},// 开始飞线函数
		arrived : function() {},// 到达函数
		showContentType : "start",// 详情显示方式：start(飞线开始时)，complate(飞线完成时),arrived(飞线到达时)
		showContent : false,// 是否显示详情
		showContentNum : 1,// 显示个数
		content : "",// 详情内容
		contentChangeTime : 3000,// 详情更换时间间隔
		contentBackgroudColor : "transparent",
		contentBorderColor : "#36e4e4",
		contentWidth : 300,
		contentFontColor : "#fff",
		tipBackground : "",
		showRandomTipContentNum : 1,// 随机弹出框显示个数
		randomTipContentChangeTime : 3000,// 随机弹出框更换时间间隔
		randomTipContentBackgroudColor : "transparent",
		randomTipContentBorderColor : "#36e4e4",
		randomTipContentWidth : 300,
		randomTipContentFontColor : "#000",
		areaStatus : true,//是否在区域内显示内容
		showAreaTootip : true,
		areaInfo : [],//区域显示内容，格式[{id:33,value:3232,backgroundColor:"",content:"",tip:""},{}]
		areaTipBackgroudColor : "#fff",
		areaTipBorderColor : "#4a7de4",
		areaTipWidth : 300,
		areaTipFontColor : "#000",//区域tip字体颜色
		areaContentFontColor : "#000",//区域内内容颜色
		areaBackgroundColorGradient : false,//区域颜色是否渐变
		areaBackgroundColorGradientStep : 10,//颜色阶级
		areaColorRange : ["#0093d7", "#003c59"],//颜色范围
		areaCountRange : [0,100],//数值范围
		heatMapRadius : 10,//heatmap每个点大小
		heatMapMaxValue : 500//最大值
		
	};

	var params = {};
	option = option || {};

	for ( var key in defaults) {
		params[key] = option[key] || defaults[key];
	}
	var width = $("#" + params.id).width(), height = $("#" + params.id).height();
	var projection = d3.geo.mercator().center(params.cp).scale(params.size).translate([ width / 2, height / 2 ]);
	var path = d3.geo.path().projection(projection);
	var svg;
	var area;
	var randomTipData = [];
	var areaColors = gradientColor(params.areaColorRange[0],params.areaColorRange[1],params.areaBackgroundColorGradientStep);
	d3.json("./" + params.geoJsonFile, function(error, root) {
		if (error)
			return console.error(error);
		svg = d3.select("#"+params.id).append("svg").attr("width", width).attr("height", height).append("g");
		area = svg.selectAll("path")
			.data(root.features)
			.enter().append("path")
			.style("cursor", "hand")
			.attr("stroke", params.boundaryColor)
			.attr("stroke-width", params.boundaryWidth)
			.attr("fill",function(d, i) {
				for(var infoId = 0 ; infoId < params.areaInfo.length; infoId ++){
					var info = params.areaInfo[infoId];
					if(d.properties.id == info.id){
						var xy = projection([ d.properties.cp[0], d.properties.cp[1] ]);
						svg.append("text")
						.attr("id","area-info-content-"+i)
						.attr("x",xy[0])
						.attr("y",xy[1])
						.html(info.content);
						d3.select("#area-info-content-"+i).attr("x",function(){
							return xy[0] - $("#area-info-content-"+i).width()/2;
						})
						var colorIndex = Math.floor((info.value-params.areaCountRange[0])/((params.areaCountRange[1] - params.areaCountRange[0])/params.areaBackgroundColorGradientStep));
						if(colorIndex > params.areaBackgroundColorGradientStep-1)
							colorIndex = params.areaBackgroundColorGradientStep-1;
						if(info.backgroundColor)
							return info.backgroundColor;
						else
							return areaColors[colorIndex];
					}
				}
				return params.backgroundColor;
			})
			.attr("d", path).on("mouseover", function(d, i) {
				d3.select(this).attr("fill", getColorShade($(this).css("fill"), -0.1));
			}).on("mouseout", function(d, i) {
				d3.select(this).attr("fill", function(){
					for(var infoId = 0 ; infoId < params.areaInfo.length; infoId ++){
						var info = params.areaInfo[infoId];
						if(d.properties.id == info.id){
							var xy = projection([ d.properties.cp[0], d.properties.cp[1] ]);
							var colorIndex = Math.floor((info.value-params.areaCountRange[0])/((params.areaCountRange[1] - params.areaCountRange[0])/params.areaBackgroundColorGradientStep));
							if(colorIndex > params.areaBackgroundColorGradientStep-1)
								colorIndex = params.areaBackgroundColorGradientStep-1;
							if(info.backgroundColor)
								return info.backgroundColor;
							else
								return areaColors[colorIndex];
						}
					}
					return params.backgroundColor;	
				});
			}).on("click", function(d, i) {
				params.areaClick(d);
			});
	});

	var hc = document.getElementById(params.id + 'HeatCanvas');
	hc.width = width;
	hc.height = height;
	var heatmapInstance = h337.create({
		maxOpacity:0.5,
		canvas: document.querySelector("#"+params.id+"HeatCanvas"),
		radius: params.heatMapRadius,
	    container: document.querySelector('#'+params.id)
	});
	
	
	var c = document.getElementById(params.id + 'Canvas');
	c.width = width;
	c.height = height;
	var ctx = c.getContext('2d');
	var funParabola = function(centerElement, centerTarget, options) {
		var exports = {
			m : 1,
			init : function() {
				return this;
			},
			draw : function() {
				return this;
			},
			state : 1,
			linePath : [],
			color : params.flyLineRgba,
			startPoint : {},
			endPoint : {},
			data : {}
		};

		// 初始化方法
		exports.init = function() {
			var a = params.curvature, b = 0, c = 0;
			var coordElement = {}, coordTarget = {};

			// 转换成相对坐标位置
			coordElement = {
				x : 0,
				y : 0
			};
			coordTarget = {
				x : -1 * (centerElement.x - centerTarget.x),
				y : -1 * (centerElement.y - centerTarget.y)
			};
			b = (coordTarget.y - a * coordTarget.x * coordTarget.x) / coordTarget.x;
			var startx = 0, rate = coordTarget.x > 0 ? 1 : -1;
			var px = centerElement.x, py = centerElement.y;
			try {
				var step = function() {
					// 切线 y'=2ax+b
					var tangent = 2 * a * startx + b; // = y / x
					// y*y + x*x = speed
					// (tangent * x)^2 + x*x = speed
					// x = Math.sqr(speed / (tangent * tangent + 1));
					startx = startx + rate * Math.sqrt(params.speed / (tangent * tangent + 1));

					// 防止过界
					if ((rate == 1 && startx > coordTarget.x) || (rate == -1 && startx < coordTarget.x)) {
						startx = coordTarget.x;
					}
					var x = startx, y = a * x * x + b * x;

					var xy = {};
					xy.x = centerElement.x + x;
					xy.y = centerElement.y + y;
					exports.linePath.push(xy);
					if (startx !== coordTarget.x) {
						step();
					}
				};
				step();
				exports.startPoint = exports.linePath[0];
				exports.endPoint = exports.linePath[exports.linePath.length - 1];
			} catch (e) {
				return null;
			}
			return this;
		};

		exports.draw = function() {
			var lineLength = Math.ceil(exports.linePath.length * params.lineLength / 100);
			var colorSpec = 1 / lineLength;
			if (exports.m == 1) {
				params.start(exports);
				if (params.showContentType == "start") {
					tooltip(exports.startPoint, getContent(exports));
				}
			} else if (exports.m == exports.linePath.length - 1) {
				params.arrived(exports);
				if (params.showContentType == "arrived") {
					tooltip(exports.endPoint, getContent(exports));
				}
			}
			if (exports.m < exports.linePath.length) {
				var l = lineLength < exports.m ? lineLength : exports.m;
				ctx.beginPath();
				var lGrd = ctx.createLinearGradient(exports.linePath[exports.m - l].x, exports.linePath[exports.m - l].y, exports.linePath[exports.m - 1].x, exports.linePath[exports.m - 1].y);
				lGrd.addColorStop(0, 'rgba(' + exports.color + ',0)');
				lGrd.addColorStop(1, 'rgba(' + exports.color + ',1)');
				ctx.strokeStyle = lGrd;
				ctx.lineWidth = 3;
				ctx.lineCap = "round";
				for (var i = 1; i < l; i++) {
					var opic = (colorSpec * i).toFixed(2);
					var index = exports.m - l + i;
					if (index >= 1 && index < exports.linePath.length) {
						ctx.moveTo(exports.linePath[index - 1].x, exports.linePath[index - 1].y);
						ctx.lineTo(exports.linePath[index].x, exports.linePath[index].y);
					}
				}
				ctx.stroke();
				ctx.closePath();
				exports.m++;
			} else if (exports.m >= exports.linePath.length && exports.m < exports.linePath.length + (lineLength > params.arcSpeed ? lineLength : params.arcSpeed)) {
				if (exports.linePath.length > 1 && exports.m < exports.linePath.length + lineLength) {
					ctx.beginPath();
					var lGrd = ctx.createLinearGradient(exports.linePath[exports.linePath.length - (lineLength - (exports.m - exports.linePath.length))].x, exports.linePath[exports.linePath.length
							- (lineLength - (exports.m - exports.linePath.length))].y, exports.linePath[exports.linePath.length - 1].x, exports.linePath[exports.linePath.length - 1].y);
					lGrd.addColorStop(0, 'rgba(' + exports.color + ',0)');
					lGrd.addColorStop(1, 'rgba(' + exports.color + ',1)');
					ctx.strokeStyle = lGrd;
					ctx.lineWidth = 3;
					for (var n = 1; n < lineLength - (exports.m - exports.linePath.length); n++) {
						var opic = (colorSpec * n).toFixed(2);
						var index = exports.linePath.length - (lineLength - (exports.m - exports.linePath.length)) + n;
						if (index >= 1 && index < exports.linePath.length) {
							ctx.moveTo(exports.linePath[index - 1].x, exports.linePath[index - 1].y);
							ctx.lineTo(exports.linePath[index].x, exports.linePath[index].y);
						}
					}
					ctx.stroke();
					ctx.closePath();
				}

				if (exports.m < exports.linePath.length + params.arcSpeed) {
					ctx.beginPath();
					ctx.strokeStyle = 'rgba(' + exports.color + ',1)';
					ctx.lineWidth = 3;
					ctx.arc(exports.linePath[exports.linePath.length - 1].x, exports.linePath[exports.linePath.length - 1].y, 10 * ((exports.m - exports.linePath.length) / params.arcSpeed), 0,
							2 * Math.PI);
					ctx.stroke();
					ctx.closePath();
				} else {
					ctx.beginPath();
					ctx.strokeStyle = 'rgba(' + exports.color + ',1)';
					ctx.lineWidth = 3;
					ctx.arc(exports.linePath[exports.linePath.length - 1].x, exports.linePath[exports.linePath.length - 1].y, 10, 0, 2 * Math.PI);
					ctx.stroke();
					ctx.closePath();
				}
				exports.m++;
			} else {
				exports.m = 1;
				exports.state = 1;
				params.complete(exports);
				if (params.showContentType == "complete") {
					tooltip(exports.endPoint, getContent(exports));
				}
			}
		}
		return exports;
	};

	var flyLineAll = [];
	var flyIndex = 0;
	var flyingCount = 0;
	var flyFun = {};
	flyFun.pushData = function(data) {
		for (var i = 0; i < data.length; i++) {
			var startJwd = {};
			var xy = projection([ data[i].from.lon, data[i].from.lat ]);
			startJwd.x = xy[0];
			startJwd.y = xy[1];
			var endJwd = {};
			xy = projection([ data[i].to.lon, data[i].to.lat ]);
			endJwd.x = xy[0];
			endJwd.y = xy[1];
			var parabola = funParabola(startJwd, endJwd).init();
			if (parabola != null) {
				if(data[i].color)
					parabola.color = data[i].color;
				parabola.data = data[i];
				flyLineAll.push(parabola);
			}
		}
	}
	
	String.prototype.colorRgb = function(){
	    var sColor = this.toLowerCase();
	    if(sColor && reg.test(sColor)){
	        if(sColor.length === 4){
	            var sColorNew = "#";
	            for(var i=1; i<4; i+=1){
	                sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));   
	            }
	            sColor = sColorNew;
	        }
	        //处理六位的颜色值
	        var sColorChange = [];
	        for(var i=1; i<7; i+=2){
	            sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));  
	        }
	        return "RGB(" + sColorChange.join(",") + ")";
	    }else{
	        return sColor;  
	    }
	};
	
	flyFun.setAreaInfo = function(data){
		area.attr("fill",function(d, i) {
			if(params.areaBackgroundColorGradient){
				var xy = projection([ d.properties.cp[0], d.properties.cp[1] ]);
				for(var infoId = 0 ; infoId < data.length; infoId ++){
					var info = data[infoId];
					
					
					var validTemp = $("#area-info-content-"+i).html();
					if(validTemp != ""){
						//$("#area-info-content-"+i).html("");
						$("#area-info-content-"+i).remove();
					}
					//console.log("backgroundColor=" + info.backgroundColor);
					var myReaFontColor = params.areaContentFontColor;
					if(info.backgroundColor == "yellow"){
						myReaFontColor = "rgb(0,0,255)";//yellow的对比色
					}else if(info.backgroundColor == "red"){
						myReaFontColor = "rgb(0,255,255)";//red的对比色
					}
					var myXy1 = xy[1];
					var myXy0 = xy[0];
					if(info.content == "舟山"){
						myXy0 = myXy0 - 25;
						myXy1 = myXy1 + 50;
					}
					if(d.properties.id == info.id){
						svg.append("text")
						.attr("id","area-info-content-"+i)
						.attr("fill",myReaFontColor)
						.attr("x",myXy0)
						.attr("y",myXy1)
						.attr("font-size",16)
						.text(info.content);
						d3.select("#area-info-content-"+i).attr("x",function(){
							return myXy0 - $("#area-info-content-"+i).width()/2;
						})
						var colorIndex = Math.floor((info.value-params.areaCountRange[0])/((params.areaCountRange[1] - params.areaCountRange[0])/params.areaBackgroundColorGradientStep));
						if(colorIndex > params.areaBackgroundColorGradientStep-1)
							colorIndex = params.areaBackgroundColorGradientStep-1;
						if(info.backgroundColor)
							return info.backgroundColor;
						else
							return areaColors[colorIndex];
					}
				}
				return params.backgroundColor;
			}else{
				return params.backgroundColor;
			}
		})
		.on("mouseover", function(d, i) {
			var xy = projection([ d.properties.cp[0], d.properties.cp[1] ]);
			for(var infoId = 0 ; infoId < data.length; infoId ++){
				var info = data[infoId];
				if(d.properties.id == info.id){
					showAreaTootip(d3.mouse(getRootNode(this)), info.tip);
					break;
				}
			}
			d3.select(this).attr("fill", getColorShade($(this).css("fill"), -0.3));
		}).on("mouseout", function(d, i) {
			$("#areaTootip").remove();
			if(params.areaBackgroundColorGradient){
				var xy = projection([ d.properties.cp[0], d.properties.cp[1] ]);
				for(var infoId = 0 ; infoId < data.length; infoId ++){
					var info = data[infoId];
					if(d.properties.id == info.id){
						var colorIndex = Math.floor((info.value-params.areaCountRange[0])/((params.areaCountRange[1] - params.areaCountRange[0])/params.areaBackgroundColorGradientStep));
						if(colorIndex > params.areaBackgroundColorGradientStep-1)
							colorIndex = params.areaBackgroundColorGradientStep-1;
						if(info.backgroundColor)
							d3.select(this).attr("fill",info.backgroundColor);
						else
							d3.select(this).attr("fill",areaColors[colorIndex]);
						return;
					}
				}
				d3.select(this).attr("fill",params.backgroundColor);
			}else{
				d3.select(this).attr("fill",params.backgroundColor);
			}
		})
        .on('mousemove', function (d, i, m) {
        	moveAreaTootip(d3.mouse(getRootNode(this)));
        });
	}
	
	//设置随机弹出框：格式[{lon:12.2121,lat:134.1231231,content:""},{}]
	flyFun.setRandomTip = function(data){
		randomTipData = data;
		
	}
	flyFun.setHeatValue = function(hv){
		params.heatMapMaxValue = hv;
	}
	flyFun.setHeatmapData = function(hdata){
		var heatmapData = [];
		for(var i = 0; i < hdata.length; i++){
			var xy = projection([ hdata[i].center_lon, hdata[i].center_lat ]);
			var point = {
				 x: Math.floor(xy[0]),
			     y: Math.floor(xy[1]),
			     value: hdata[i].heatmap_value
			};
			heatmapData.push(point);
		}
		var datas = {
			    max: params.heatMapMaxValue,
			    data: heatmapData
			};
		//console.log(datas);
		heatmapInstance.setData(datas);
	}
	
	
	function showRandomTip(){
		if(randomTipData.length > 0){
			var index = Math.floor(Math.random() * randomTipData.length);
			var xy = projection([ randomTipData[index].lon, randomTipData[index].lat ]);
			var ss = {x:xy[0],y:xy[1]};
			randomTooltip(ss,randomTipData[index].content);
		}
	}
	window.setInterval(showRandomTip, 3000); 
	var requestAnimFrame = function() {
		return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(a) {
			window.setTimeout(a, 1E3 / 60)
		}
	}();
	/*
	 * var stats; if ( typeof Stats === 'function' ) {
	 * document.body.appendChild( ( stats = new Stats() ).domElement ); }
	 */
	var start = function() {
		var f = function() {
			// if ( stats ) stats.begin();
			var overNum = flyLineAll.length - params.cacheLimit;
			c.width = c.width;
			for (var i = 0; i < flyLineAll.length; i++) {
				if (flyIndex == flyLineAll.length) {
					flyIndex = 0;
				}
				if (flyingCount < params.flyLimit) {
					for (var m = 0; m < flyLineAll.length; m++) {
						if (flyIndex >= flyLineAll.length) {
							flyIndex = 0;
						}
						if (flyLineAll[flyIndex].state == 1 && flyingCount < params.flyLimit) {
							flyLineAll[flyIndex].state = 0;
							flyingCount++;
						}
						flyIndex++;
						if (flyingCount == params.flyLimit)
							break;
					}
				}
				if (flyLineAll[i].state == 0) {
					flyLineAll[i].draw();
					if (flyLineAll[i].state == 1) {
						flyingCount--;
						if (overNum > 0 && i < overNum) {
							flyLineAll.splice(i, 1);
							i--;
							flyIndex--;
							overNum--;
						}
					}
				}
			}
			// if ( stats ) stats.end();
			requestAnimationFrame(f);
		};
		requestAnimationFrame(f);
	}
	start();

	var tooltips = [];
	var tooltip = function(mouseCoords, content) {
		if (params.showContent) {
			var tipJquery;
			var id;
			if (tooltips.length < params.showContentNum) {
				var nvtooltipid = "flylineTootip_" + Math.round(Math.random() * 10000);
				var tip = {};
				tip.time = new Date();
				tip.id = nvtooltipid;
				id = nvtooltipid;
				tooltips.push(tip);
				var tipStyle = 'background-size:100%;pointer-events:none;position:absolute;width:'
					+ params.contentWidth + 'px;height:auto;min-height:162px;line-height:25px;border:0px solid '
					+ params.contentBorderColor + ';border-radius:4px; text-align:left;color:'
					+ params.contentFontColor+';';
				if(params.tipBackground){
					tipStyle += params.tipBackground;
				}
				var tipHtml = '<div id="' + nvtooltipid + '" style="'+tipStyle+'">';
				tipHtml += '<div style="width:100%" id="' + nvtooltipid + 'Content"></div>';
				tipHtml += '<div style="position:absolute;left:30px;overflow:hidden;width:0;height:0;border-width:10px;border-style:solid dashed dashed dashed;bottom:-20px;border-color:#152a72 transparent transparent transparent; "></div>';
				tipHtml += '<div style="position:absolute;left:30px;overflow:hidden;width:0;height:0;border-width:10px;border-style:solid dashed dashed dashed;bottom:-19px;border-color:transparent transparent transparent transparent; "></div></div>';
				tipJquery = $(tipHtml);
				tipJquery.appendTo('body');
			} else {
				var now = new Date();
				var lastChangeTime = tooltips[0].time;
				var earlyChangeTime = tooltips[0].time;
				id = tooltips[0].id;
				var changeIndex = 0;
				for (var i = 0; i < tooltips.length; i++) {
					if (tooltips[i].time > lastChangeTime)
						lastChangeTime = tooltips[i].time;
					if (tooltips[i].time < earlyChangeTime) {
						earlyChangeTime = tooltips[i].time;
						id = tooltips[i].id;
						changeIndex = i;
					}
				}
				if (now - lastChangeTime >= params.contentChangeTime) {
					tipJquery = $("#" + id);
					tooltips[changeIndex].time = now;
				}
			}
			if (tipJquery) {
				tipJquery.find("#" + id + "Content").html(content);
				var height = tipJquery.height(), width = tipJquery.width(), windowWidth = $(window).width(), windowHeight = $(window).height(), scrollTop = $('body').scrollTop(), left, top;
				var xi = mouseCoords.x;
				var yi = mouseCoords.y;
				var marginleft = parseInt($("#" + params.id).css("margin-left").replace("px",""));
				left = xi - 40 + marginleft;
				top = yi - height + $("#" + params.id).position().top;
				tipJquery.css({
					left : left+540,
					top : top-50,
					opacity : 1,
					"z-index" : 999999
				});
			}
		}
	};
	var randomTooltips = [];
	var randomTooltip = function(mouseCoords, content) {
		var tipJquery;
		var id;
		if (randomTooltips.length < params.showRandomTipContentNum) {
			var nvtooltipid = "randomTootip_" + Math.round(Math.random() * 10000);
			var tip = {};
			tip.time = new Date();
			tip.id = nvtooltipid;
			id = nvtooltipid;
			randomTooltips.push(tip);
			var tipHtml = '<div id="' + nvtooltipid + '" style="pointer-events:none;position:absolute;width:' + params.randomTipContentWidth + 'px;height:auto;min-height:50px;line-height:60px;background:'
					+ params.randomTipContentBackgroudColor + ';border:2px solid ' + params.randomTipContentBorderColor + ';border-radius:4px; text-align:center;color:' + params.randomTipContentFontColor + '">';
			tipHtml += '<div style="width:100%" id="' + nvtooltipid + 'Content"></div>';
			tipHtml += '<div style="position:absolute;left:30px;overflow:hidden;width:0;height:0;border-width:10px;border-style:solid dashed dashed dashed;bottom:-20px;border-color:'
					+ params.randomTipContentBorderColor + ' transparent transparent transparent; "></div>';
			tipHtml += '<div style="position:absolute;left:30px;overflow:hidden;width:0;height:0;border-width:10px;border-style:solid dashed dashed dashed;bottom:-19px;border-color:transparent transparent transparent transparent; "></div></div>';
			tipJquery = $(tipHtml);
			tipJquery.appendTo('body');
		} else {
			var now = new Date();
			var lastChangeTime = randomTooltips[0].time;
			var earlyChangeTime = randomTooltips[0].time;
			id = randomTooltips[0].id;
			var changeIndex = 0;
			for (var i = 0; i < randomTooltips.length; i++) {
				if (randomTooltips[i].time > lastChangeTime)
					lastChangeTime = randomTooltips[i].time;
				if (randomTooltips[i].time < earlyChangeTime) {
					earlyChangeTime = randomTooltips[i].time;
					id = randomTooltips[i].id;
					changeIndex = i;
				}
			}
			if (now - lastChangeTime >= params.randomTipContentChangeTime) {
				tipJquery = $("#" + id);
				randomTooltips[changeIndex].time = now;
			}
		}
		if (tipJquery) {
			tipJquery.find("#" + id + "Content").html(content);
			var height = tipJquery.height(), width = tipJquery.width(), windowWidth = $(window).width(), windowHeight = $(window).height(), scrollTop = $('body').scrollTop(), left, top;
			var xi = mouseCoords.x;
			var yi = mouseCoords.y;
			var marginleft = parseInt($("#" + params.id).css("margin-left").replace("px",""));
			left = xi - 40 + marginleft;
			top = yi - height + $("#" + params.id).position().top;
			tipJquery.css({
				left : left,
				top : top,
				opacity : 1,
				"z-index" : 999999
			});
		}
	};
	var showAreaTootip = function(mouseCoords, content) {
		if (params.showAreaTootip) {
			var tipJquery;
			var id;
			var nvtooltipid = "areaTootip";
			var tip = {};
			tip.time = new Date();
			tip.id = nvtooltipid;
			id = nvtooltipid;
			var tipHtml = '<div id="' + nvtooltipid + '" style="position:absolute;width:' + params.areaTipWidth + 'px;height:auto;min-height:20px;line-height:30px;background:'
					+ params.areaTipBackgroudColor + ';border:2px solid ' + params.areaTipBorderColor + ';border-radius:4px; text-align:left;color:' + params.areaTipFontColor + '">';
			tipHtml += '<div style="width:100%" id="' + nvtooltipid + 'Content"></div>';
			tipHtml += '</div>';
			tipJquery = $(tipHtml);
			tipJquery.appendTo('body');
			if (tipJquery) {
				tipJquery.find("#" + id + "Content").html(content);
				var height = tipJquery.height(), width = tipJquery.width(), windowWidth = $(window).width(), windowHeight = $(window).height(), scrollTop = $('body').scrollTop(), left, top;
				var xi = mouseCoords[0];
				var yi = mouseCoords[1];
				left = xi - width/2;
				top = yi - height;
				tipJquery.css({
					left : left,
					top : top,
					opacity : 1,
					"z-index" : 999999
				});
			}
		}
	};
	var moveAreaTootip = function(mouseCoords) {
		if (params.showAreaTootip) {
			var tipJquery = $("#areaTootip");
			//console.log("name:"+name+";mouseCoords="+mouseCoords+";tipJquery"+tipJquery.height());
			
			if (tipJquery) {
				var height = tipJquery.height(), width = tipJquery.width(), windowWidth = $(window).width(), windowHeight = $(window).height(), scrollTop = $('body').scrollTop(), left, top;
				var xi = mouseCoords[0];
				var yi = mouseCoords[1];
				left = xi - width/2;
				if(yi <= height){
					top = yi + 60;
				}else{
					top = yi - height - 20;
				}
				
				tipJquery.css({
					left : left,
					top : top,
					opacity : 1,
					"z-index" : 999999
				});
			}
			
		}
	};
	var getContent = function(d) {
		var content = params.content;
		while (content.indexOf("%{") >= 0) {
			var element = content.substring(content.indexOf("%{") + 2, content.indexOf("}"));
			content = content.replace(new RegExp("%\\{" + element + "\\}", "gm"), eval("d." + element));
		}
		return content;
	}

    function getRootNode(elem){
    	if(elem.parentNode && elem.parentNode && elem.parentNode.tagName != "BODY")
    		return getRootNode(elem.parentNode);
    	else {
    		return elem.parentNode;
    	}
    }
    
    function getHexBackgroundColor(rgb) 
    {  
	    rgb=rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);  
	    function hex(x)  
	    {  
	      return ("0"+parseInt(x).toString(16)).slice(-2);  
	    }  
	    rgb="#"+hex(rgb[1])+hex(rgb[2])+hex(rgb[3]);  
	    return rgb;  
    }
    function getColorShade(hex, lum) {
    	hex = getHexBackgroundColor(hex);
        // validate hex string
        hex = String(hex).replace(/[^0-9a-f]/gi, '');
        if (hex.length < 6) {
            hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2];
        }
        lum = lum || 0;

        // convert to decimal and change luminosity
        var newHex = "#";
        for (var i = 0; i < 3; i++) {
            var c = parseInt(hex.substr(i * 2, 2), 16);
            c = Math.round(Math.min(Math.max(0, c + (c * lum)), 255)).toString(16);
            newHex += ("00" + c).substr(c.length);
        }

        return newHex;
    }
    
    function gradientColor(startColor,endColor,step){
        startRGB = getColorRgb(startColor);//转换为rgb数组模式
        startR = startRGB[0];
        startG = startRGB[1];
        startB = startRGB[2];

        endRGB = getColorRgb(endColor);
        endR = endRGB[0];
        endG = endRGB[1];
        endB = endRGB[2];

        sR = (endR-startR)/step;//总差值
        sG = (endG-startG)/step;
        sB = (endB-startB)/step;

        var colorArr = [];
        for(var i=0;i<step;i++){
 		   //计算每一步的hex值 
            var hex = getColorHex('rgb('+parseInt((sR*i+startR))+','+parseInt((sG*i+startG))+','+parseInt((sB*i+startB))+')');
            colorArr.push(hex);
        }
        return colorArr;
    }

    // 将hex表示方式转换为rgb表示方式(这里返回rgb数组模式)
    function getColorRgb(sColor){
        var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
        var sColor = sColor.toLowerCase();
        if(sColor && reg.test(sColor)){
            if(sColor.length === 4){
                var sColorNew = "#";
                for(var i=1; i<4; i+=1){
                    sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));
                }
                sColor = sColorNew;
            }
            //处理六位的颜色值
            var sColorChange = [];
            for(var i=1; i<7; i+=2){
                sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));
            }
            return sColorChange;
        }else{
            return sColor;
        }
    };

    // 将rgb表示方式转换为hex表示方式
    function getColorHex(rgb){
        var _this = rgb;
        var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
        if(/^(rgb|RGB)/.test(_this)){
            var aColor = _this.replace(/(?:(|)|rgb|RGB)*/g,"").split(",");
            var strHex = "#";
            for(var i=0; i<aColor.length; i++){
                var hex = Number(aColor[i]).toString(16);
                hex = hex<10 ? 0+''+hex :hex;// 保证每个rgb的值为2位
                if(hex === "0"){
                    hex += hex;
                }
                strHex += hex;
            }
            if(strHex.length !== 7){
                strHex = _this;
            }
            return strHex;
        }else if(reg.test(_this)){
            var aNum = _this.replace(/#/,"").split("");
            if(aNum.length === 6){
                return _this;
            }else if(aNum.length === 3){
                var numHex = "#";
                for(var i=0; i<aNum.length; i+=1){
                    numHex += (aNum[i]+aNum[i]);
                }
                return numHex;
            }
        }else{
            return _this;
        }
    }
	return flyFun;
}