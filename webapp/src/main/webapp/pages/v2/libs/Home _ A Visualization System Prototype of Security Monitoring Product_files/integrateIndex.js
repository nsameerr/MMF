
var data = [{
	time: '10:01',
	'in': 200,
	'remain': 500,
	'out': 1000
}, {
	time: '10:02',
	'in': 620,
	'remain': 600,
	'out': 1000
}, {
	time: '10:03',
	'in': 300,
	'remain': 800,
	'out': 1000
}, {
	time: '10:04',
	'in': 440,
	'remain': 700,
	'out': 1000
}, {
	time: '10:05',
	'in': 900,
	'remain': 900,
	'out': 1000
}, {
	time: '10:06',
	'in': 300,
	'remain': 500,
	'out': 1000
}, {
	time: '10:07',
	'in': 50,
	'remain': 300,
	'out': 1000
}, {
	time: '10:08',
	'in': 350,
	'remain': 70,
	'out': 1000
}, {
	time: '10:09',
	'in': 750,
	'remain': 200,
	'out': 1000
}];

var category = ['in', 'remain', 'out'];

var drawLine = ['write'],
	drawBar = ['in', 'out'];

var hAxis = 10,
	mAxis = 10;

//generation function
function generate(data, id, lineType, axisNum, drawBar, category) {
	var margin = {
			top: 10,
			right: 70,
			bottom: 35,
			left: 32
		},
		width = $(id).width() - margin.left - margin.right,
		height = $(id).height() - margin.top - margin.bottom;

	var parseDate = d3.time.format("%H:%M").parse;

	var legendSize = 10,
		legendColor = {
			'in': 'rgba(0, 160, 233, .8)',
			'out': 'rgba(0, 160, 233, .2)',
			'remain': '#41DB00'
		};

	var x = d3.time.scale()
		.range([0, width]);
	// .rangeRoundBands([0, width], .1);

	var x1 = d3.scale.ordinal();

	var y = d3.scale.linear()
		.range([height, 0]);

	//data.length/10 is set for the garantte of timeseries's fitting effect in svg chart
	var xAxis = d3.svg.axis()
		.scale(x)
		.ticks(d3.time.minutes, Math.floor(data.length / axisNum))
		.tickPadding([6])
		.orient("bottom");

	var yAxis = d3.svg.axis()
		.scale(y)
		.ticks(10)
		.orient("left");

	var ddata = data;

	ddata.forEach(function(d) {
		d.ages = drawBar.map(function(name) {
			return {
				name: name,
				value: +d[name]
			};
		});
	});

	x.domain(d3.extent(ddata, function(d) {
		return parseDate(d['time']);
	}));

	// console.log(x(parseDate(10:10))-x(parseDate(10:09)));

	var tranLength = (x(parseDate('00:10')) - x(parseDate('00:09'))) / 4;

	x1.domain(drawBar).rangeRoundBands([0, tranLength * 2]);
	
	

	y.domain([
		0,
		d3.max(ddata, function(d) {
			return d3.max([d['in'], d['out'], d['remain']]);
		}) + 50
	]);

	var line = d3.svg.line()
		.interpolate(lineType)
		.x(function(d) {
			return xTransLen(d['time']);
		})
		.y(function(d) {
			return y(d['remain']);
		});

	d3.select('#svg-count').remove();

	var svg = d3.select(id).append("svg")
		.attr("id", "svg-count")
		.attr("width", width + margin.right + margin.left)
		.attr("height", height + margin.top + margin.bottom)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.append("g")
		.attr("class", "x axis")
		.attr("id", "count-x-axis")
		.attr("transform", "translate(" + tranLength + "," + height + ")")
		.call(xAxis);

	svg.append("g")
		.attr("class", "y axis")
		.call(yAxis);

	var stat = svg.selectAll(".countBpath")
		.data(ddata)
		.enter().append("g")
		.attr("class", "countBpath")
		.attr("transform", function(d) {
			return "translate(" + x(parseDate(d['time'])) + ",0)";
		});

	stat.selectAll(".countIORect")
		.data(function(d) {
			return d.ages;
		})
		.enter().append("rect")
		.attr('class', 'countIORect')
		.attr("width", x1.rangeBand())
		.attr("x", function(d) {
			return x1(d['name']);
		})
		.attr("y", function(d) {
			return y(d['value']);
		})
		.attr("height", function(d) {
			return height - y(d['value']);
		})
		.style("fill", function(d) {
			return legendColor[d['name']];
		});

	var path = svg.append("g")
		.attr("class", "countPath");

	path.append("path")
		.attr("d", line(ddata))
		.attr("class", 'countRemainPath')
		.attr('stroke', legendColor['remain']);

	var legend = svg.selectAll('.countLegend')
		.data(category)
		.enter()
		.append('g')
		.attr('class', 'countLegend')
		.attr('transform', function(d, i) {
			return 'translate(' + (i * 10 * legendSize) + ',' + (height + margin.bottom - legendSize * 1.2) + ')';
		});

	legend.append('rect')
		.attr('width', legendSize)
		.attr('height', legendSize)
		.style('fill', function(d) {
			return legendColor[d];
		});

	legend.append('text')
		.data(category)
		.attr('x', legendSize * 1.2)
		.attr('y', legendSize / 1.1)
		.text(function(d) {
			return d;
		});

	var points = svg.append("g")
		.attr("class", "countPoints");

	points.selectAll(".tipCountPoints")
		.data(ddata)
		.enter().append("circle")
		.attr("class", "tipCountPoints")
		.attr("cx", function(d) {
			return xTransLen(d['time']);
		})
		.attr("cy", function(d) {
			return y(d['remain']);
		})
		.attr("r", "6px")
		.on("mouseover", function(d) {
			d3.select(this).transition().duration(100).attr("r", '8px');

			 svg.append("g")
			   .append("line")
			   .attr("class", "tipDot")
			   .transition()
			   .duration(50)
			   .attr("x1", xTransLen(d['time']))
			   .attr("x2", xTransLen(d['time']))
			   .attr("y2", height);

			 svg.append("polyline")
			   .attr("class", "tipDot")
			   .style("fill", "black")
			   .attr("points", (xTransLen(d['time'])-3.5)+","+(0-2.5)+","+xTransLen(d['time'])+","+(0+6)+","+(xTransLen(d['time'])+3.5)+","+(0-2.5));

			 svg.append("polyline")
			   .attr("class", "tipDot")
			   .style("fill", "black")
			   .attr("points", (xTransLen(d['time'])-3.5)+","+(y(0)+2.5)+","+xTransLen(d['time'])+","+(y(0)-6)+","+(xTransLen(d['time'])+3.5)+","+(y(0)+2.5));

			$(this).popover({
					'container': 'body',
					'placement': 'top',
					'html': 'true',
					'title': d['time'],
					'content': '<table><tr><td>' + 'TIME' + '</td><td> | ' + d['time'] + '</td></tr>' + '<tr><td>' + 'REMAIN' + '</td><td> | ' + d['remain'] + '</td></tr>' + '<tr><td>' + 'IN' + '</td><td> | ' + d['in'] + '</td></tr>' + '<tr><td>' + 'OUT' + '</td><td> | ' + d['out'] + '</td></tr></table>',
					'trigger': 'hover'
				})
				.popover('show');
		})
		.on("mouseout", function(d) {
			d3.select(this).transition().duration(100).attr("r", '6px');

			d3.selectAll('.tipDot').transition().duration(100).remove();

			$(this).popover('destroy');
		});

	function xTransLen(t) {
		return x(parseDate(t)) + tranLength;
	}

	this.getOpt = function() {
		var axisOpt = new Object();
		axisOpt['x'] = x;
		axisOpt['x1'] = x;
		axisOpt['y'] = y;
		axisOpt['xAxis'] = xAxis;
		axisOpt['height'] = height;
		axisOpt['axisNum'] = axisNum;
		axisOpt['drawBar'] = drawBar;

		return axisOpt;
	}

	this.getSvg = function() {
		var svgD = new Object();
		svgD['svg'] = svg;
		svgD['points'] = points;
		svgD['stat'] = stat;
		svgD['path'] = path;
		svgD['line'] = line;
		svgD['legendColor'] = legendColor;

		return svgD;
	}
}

//redraw function
function redraw(data, id, x, y, xAxis, svg, stat, path, line, points, legendColor, height, axisNum, drawBar) {
	//format of time data
	var parseDate = d3.time.format("%H:%M").parse;

	var ddata = data;

	ddata.forEach(function(d) {
		d.ages = drawBar.map(function(name) {
			return {
				name: name,
				value: +d[name]
			};
		});
	});

	x.domain(d3.extent(ddata, function(d) {
		return parseDate(d['time']);
	}));

	var tranLength = (x(parseDate('00:10')) - x(parseDate('00:09'))) / 4;

	var x1 = d3.scale.ordinal();

	x1.domain(drawBar).rangeRoundBands([0, tranLength * 2]);

	xAxis.ticks(d3.time.minutes, Math.floor(data.length / axisNum));

	line.x(function(d) {
		return xTransLen(d['time']);
	});

	svg.select("#count-x-axis")
		.transition()
		.duration(200)
		.ease("sin-in-out")
		.call(xAxis);

	//update the stat bar
	stat = svg.selectAll(".countBpath")
		.data(ddata)
		.attr("transform", function(d) {
			return "translate(" + x(parseDate(d['time'])) + ",0)";
		});

	stat.selectAll(".countIORect")
		.data(function(d) {
			return d.ages;
		})
		.attr("width", x1.rangeBand())
		.attr("x", function(d) {
			return x1(d['name']);
		})
		.attr("y", function(d) {
			return y(d['value']);
		})
		.attr("height", function(d) {
			return height - y(d['value']);
		})
		.style("fill", function(d) {
			return legendColor[d['name']];
		});

	//append new bar
	stat = svg.selectAll(".countBpath")
		.data(ddata)
		.enter().append("g")
		.attr("class", "countBpath")
		.attr("transform", function(d) {
			return "translate(" + x(parseDate(d['time'])) + ",0)";
		});

	stat.selectAll(".countIORect")
		.data(function(d) {
			return d.ages;
		})
		.enter().append("rect")
		.attr('class', 'countIORect')
		.attr("width", x1.rangeBand())
		.attr("x", function(d) {
			return x1(d['name']);
		})
		.attr("y", function(d) {
			return y(d['value']);
		})
		.attr("height", function(d) {
			return height - y(d['value']);
		})
		.style("fill", function(d) {
			return legendColor[d['name']];
		});

	//remove the old bar
	stat.selectAll(".countIORect")
		.data(function(d) {
			return d.ages;
		})
		.exit()
		.transition()
		.duration(200)
		.remove();

	//remove the path and redraw it, in order to confirm it display in front of other elements
	d3.selectAll('.countPath').remove();

	var path = svg.append("g")
		.attr("class", "countPath");

	path.append("path")
		.attr("d", line(ddata))
		.attr("class", 'countRemainPath')
		.attr('stroke', legendColor['remain']);

	//update the path line
	 path.selectAll('.countRemainPath')
	   .data(ddata)
	   .transition()
	   .duration(200)
	   .attr("d", line(ddata))
	   .attr("class", 'countRemainPath')
	   .attr('stroke', legendColor['remain']);

	//circle updating
	 points.selectAll(".tipCountPoints")
	   .data(ddata)
	   .attr("class", "tipCountPoints")
	   .attr("cx", function (d) { return xTransLen(d['time']); })
	   .attr("cy", function (d) { return y(d['remain']); });

	//remove all the points and append new points
	d3.selectAll('.countPoints').remove();

	points = svg.append("g")
		.attr("class", "countPoints");

	points.selectAll(".tipCountPoints")
		.data(ddata)
		.enter().append("circle")
		.attr("class", "tipCountPoints")
		.attr("cx", function(d) {
			return xTransLen(d['time']);
		})
		.attr("cy", function(d) {
			return y(d['remain']);
		})
		.attr("r", "6px")
		.on("mouseover", function(d) {
			d3.select(this).transition().duration(100).attr("r", '8px');

			$(this).popover({
					'container': 'body',
					'placement': 'top',
					'html': 'true',
					'title': d['time'],
					'content': '<table><tr><td>' + 'TIME' + '</td><td> | ' + d['time'] + '</td></tr>' + '<tr><td>' + 'REMAIN' + '</td><td> | ' + d['remain'] + '</td></tr>' + '<tr><td>' + 'IN' + '</td><td> | ' + d['in'] + '</td></tr>' + '<tr><td>' + 'OUT' + '</td><td> | ' + d['out'] + '</td></tr></table>',
					'trigger': 'hover'
				})
				.popover('show');
		})
		.on("mouseout", function(d) {
			d3.select(this).transition().duration(100).attr("r", '6px');

			d3.selectAll('.tipDot').transition().duration(100).remove();

			$(this).popover('destroy');
		});

	//remove old dot
	 points.selectAll(".tipCountPoints")
	   .data(ddata)
	   .exit()
	   .transition()
	   .duration(200)
	   .remove();

	function xTransLen(t) {
		return x(parseDate(t)) + tranLength;
	}

}

//inits chart
var sca = new generate(data, "#sensor-count-multi-d3", "monotone", 5, drawBar, category);

//dynamic data and chart update
setInterval(function() {
	//update donut data
	data.push({
		time: hAxis + ":" + mAxis,
		'in': Math.random() * 700 + 200,
		'out': Math.random() * 500 + 400,
		'remain': Math.random() * 700 + 200
	});

	// console.log(tAxis);
	if (mAxis === 59) {
		hAxis++;
		mAxis = 0;
	} else {
		mAxis++;
	}

	if (Object.keys(data).length === 18) data.shift();

	redraw(
		data,
		"#sensor-disk-multi-d3",
		sca.getOpt()['x'],
		sca.getOpt()['y'],
		sca.getOpt()['xAxis'],
		sca.getSvg()['svg'],
		sca.getSvg()['stat'],
		sca.getSvg()['path'],
		sca.getSvg()['line'],
		sca.getSvg()['points'],
		sca.getSvg()['legendColor'],
		sca.getOpt()['height'],
		sca.getOpt()['axisNum'],
		sca.getOpt()['drawBar']
	);
}, 300000);