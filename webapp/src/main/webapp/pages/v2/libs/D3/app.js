d3.json('data.json', function(error, rawData) {
	if (error) {
		console.error(error);
		return;
	}

	var data = rawData.map(function(d) {
		return {
			age: d[0],
			assets: d[1],
			liability: d[2],
			goal : d[3]
			
		};
	});

	

	makeChart(data);
});

function makeChart(data) {
	var svgWidth = 960,
		svgHeight = 500,
		margin = {
			top: 20,
			right: 20,
			bottom: 40,
			left: 40
		},
		chartWidth = svgWidth - margin.left - margin.right,
		chartHeight = svgHeight - margin.top - margin.bottom;	
}