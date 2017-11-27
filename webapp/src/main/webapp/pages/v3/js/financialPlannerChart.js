        // <!-- High Charts Script -->
        var assetJson;
        var assetJsonFDOnly;
        var expenditureJson;
        var ligoalsJson;
        var xStart;
        var xEnd;
        var chart;
        var fpArray;
        // var myLifeGoals;
        function initFinancialPlannerData(data,chartId){
            // console.log(data);
            assetJson = "";
            assetJsonFDOnly = "";
            expenditureJson = "";
            ligoalsJson = "";
            // myLifeGoals = "";
            xStart = data.age[0];
            xEnd = data.age[data.age.length-1];
         
            for(i=0; i<data.age.length; i++){
                if(assetJson!=""){
                    assetJson = assetJson + ",";
                    assetJsonFDOnly = assetJsonFDOnly + ",";
                    expenditureJson = expenditureJson + ",";
                }
                
                tempAsset = data.totalFinAsset[i];
                if(tempAsset<0){
                    tempAsset=0;
                }
                
                tempAssetFDOnly = data.totalFinAssetOnlyFD[i];
                
                //tempAssetFDOnly = tempAssetFDOnly/2;
                if(tempAssetFDOnly<0){
                    tempAssetFDOnly=1000;
                }

                assetJsonFDOnly  = assetJsonFDOnly + '{"x": ' + data.age[i] + ', "y" : ' + tempAssetFDOnly + ',"asset" : ' + tempAsset + ',"expense" : ' + data.totalLiabilities[i] + ',  "goal": null, "name": "", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
                assetJson = assetJson + '{"x": ' + data.age[i] + ', "y" : ' + tempAsset + ',"asset" : ' + tempAsset + ',"expense" : ' + data.totalLiabilities[i] + ',  "goal": null, "name": "", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
                expenditureJson = expenditureJson + '{"x": ' + data.age[i] + ', "y" : ' + data.totalLiabilities[i] + ', "asset" : ' + tempAsset + ',"expense" : '+ data.totalLiabilities[i] + ', "goal": null, "name": "", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
            }
            assetJson = JSON.parse("["+assetJson+"]");
            assetJsonFDOnly = JSON.parse("["+assetJsonFDOnly+"]");
            expenditureJson = JSON.parse("["+expenditureJson+"]");
            // console.log(data.lifeGoals);
            for(i=0; i<data.lifeGoals.length; i++){
                if(ligoalsJson!=""){
                    ligoalsJson = ligoalsJson + ",";
                }
                ligoalsJson = ligoalsJson + '{"y": 32 , "shape": "url(images/lifeGoalIcons/goal_icon_'+data.lifeGoals[i].goalDescriptionId+'.png)", "x": ' + data.lifeGoals[i].age + ', "title":".", "frequency": '+data.lifeGoals[i].frequency+', "frequencyDesc" : "'+data.lifeGoals[i].frequencyDesc+'","goalDescriptionId" : '+data.lifeGoals[i].goalDescriptionId+',  "goalDescription" : "'+data.lifeGoals[i].goalDescription+'", "loanYesNo" : "'+data.lifeGoals[i].loanYesNo+'", "yearofRealization": '+data.lifeGoals[i].yearofRealization+', "inflatedAmount": "' +data.lifeGoals[i].inflatedAmount+ '" }';
            }
            ligoalsJson = JSON.parse("["+ligoalsJson+"]");

            //Make life goals array for New Graph
            /*for(i=0; i<data.lifeGoals.length; i++){
            	if(myLifeGoals != ""){
            		myLifeGoals = myLifeGoals + ",";
            	}
            	myLifeGoals = myLifeGoals +
            	'{ name : \'Life Goals\', color : \'transparent\' , connectEnds : false, type : \'line\', '+
            	' marker : { symbol : \''+ligoalsJson[i].shape+'\'} , data : [['+ligoalsJson[i].x+','+ligoalsJson[i].y+']], '+
            	' tooltip : { '+
            		'headerFormat : \'<span style=\"font-size: 11px;color:#666\"><b>Age : </b>'+ligoalsJson[i].x+'</span><br>\', '+
            		'pointFormat : \'<b>Goal : </b>'+ligoalsJson[i].goalDescription+'<br>'+
            						'<b>Frequency : </b>'+ligoalsJson[i].frequencyDesc+'<br>'+
            						'<b>Loan : </b>'+ligoalsJson[i].loanYesNo+'<br>'+
            						'<b>Goal Year : </b>'+ligoalsJson[i].yearofRealization+'<br>'+
            						'<b>Future Amount : </b>'+ligoalsJson[i].inflatedAmount+'\'}'+
            	'}';
            }*/
            
            // console.log(myLifeGoals);
            // console.log(ligoalsJson);
            drawFinancialPlannerChart(chartId);    
            try{
            	drawFinancialPlannerTable();        	
            }catch(e){
            	console.log('Failed to display FP Table View');
            }
            
        }
     
        function drawFinancialPlannerChart(chartId){
            chart =  Highcharts.chart(chartId, {
                chart: {
                    height: 260,
                    zoomType : 'x',
                },
                exporting: {
         			enabled: false
				},
                xAxis: {
                    min: xStart,
                    max: 85,
                    lineWidth: 0,
                    tickWidth: 0,
                    title: {
                        text: 'Age'//''
                    },
                    labels: {
                        x:0,
                        y:30
                    },
                },
                yAxis: {
                    min: 0,
                    tickInterval: 10,
                    gridLineWidth: 2,
                    //gridLineDashStyle: 'dash',
                    title: {
                        text: 'Rs (Cr)'//''
                    },
                    labels: {
                        x:-10,
                        y:3,
                        formatter: function () {
                            if(this.value > 9999999)
                            {
                            	return(parseInt(this.value/10000000));// + ' cr') 
                            }else{
				return(parseFloat(this.value/10000000).toFixed(2));
			    }
                            // else 
                            //    if(this.value > 99999){
                            //    return(parseInt((this.value/100000)) ) //+ ' Lakh'
                            //}
                            //return this.value;// + ' Cr';
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    // valueSuffix: ' Rs',//'Cr',
                    enabled:true,
                    // shared:  false,
                    // formatter: function() {                        
                    //         var text = '';
                    //         if(this.series.name != 'Life Goals'){
                    //             text='Age : <b>' + this.x + '</b> <br>'+this.series.name +' : <b> Rs ' + this.y + '</b>';
                    //         }
                    //         return text;
                    // }
                },
                plotOptions: {
                    line: {
                        lineWidth: 3,
                        states: {
                            hover: {
                                enabled:false
                            }
                        },
                    }
                },
                series: [{
                    name: 'Annual Expenditure',
                    id : 'seriesExpenses',
                    color: '#fdba18',
                    type: 'column',
                    borderRadius: 2,
                    pointWidth: 6,
                    data : expenditureJson,
                    // data: [[45, 4], [46, 4], [47, 5], [48, 7], [49, 10], [50, 8], [51, 5], [52, 5], [53, 5], [54, 5], [55, 10], [56, 8], [57, 6], [58, 5], [59, 4], [60, 3], [61, 7], [62, 8], [63, 5], [64, 5], [65, 5], [66, 6], [67, 7], [68, 8], [69, 7], [70, 6], [71, 5], [72, 5], [73, 5], [74, 5], [75, 5], [76, 5], [77, 6], [78, 6], [79, 6], [80, 7], [81, 7], [82, 10], [83, 10], [84, 20], [85, 25], [86, 10], [87, 7], [88, 5], [89, 5], [90, 5], [91, 5], [91, 5], [92, 5], [93, 5], [94, 6], [95, 7]],
                    marker: {
                            enabled: false
                    },
                    tooltip: {
                        headerFormat: '<b>Age : </b>{point.x}<br>',
                        pointFormat: '<b>Annual Expenditure : </b> Rs {point.y}',               
                    },
                }, {
                    name: 'Total Wealth', //'Wealth with MMF'
                    color: '#4aae3c',
                    type: 'line',
                    data : assetJson,
                    // data: [[45, 15], [60, 30], [68, 25], [70, 38], [72, 40], [80, 60], [82, 60], [88, 68], [90, 70], [95, 73]],
                    marker: {
                            enabled: false,
                    },
                    tooltip: {
                        headerFormat: '<b>Age : </b>{point.x}<br>',
                        pointFormat: '<b>Total Wealth : </b> Rs {point.y}',               
                    },
                }, 

                // {
                //     name: 'Wealth with FD Investments',
                //     color: '#0072bc',
                //     type: 'line',
                //     data : assetJsonFDOnly,
                //     // data: [[45, 1], [60, 15], [68, 20], [70, 18], [72, 30], [80, 32], [82, 35], [88, 38], [90, 40], [95, 45]],
                //     marker: {
                //             enabled: false
                //     },
                //     tooltip: {
                //         headerFormat: '<b>Age : </b>{point.x}<br>',
                //         pointFormat: '<b>Wealth with FD Investments : </b> Rs {point.y}',               
                //     },
                // }, 
         		
                // {
                //     name: 'Life Goals',
                //     color: 'transparent',
                //     connectEnds: false,
                //     type: 'line',
                //     marker: {
                //         // symbol: 'url(images/car_goal_icon.png)'//'url(http://server/narasimman/mmf/website_progressing/images/target_shp_1.png)'
                //     },
                //     data: [[58, 13]],
                //     onSeries : 'seriesExpenses',
                //  }, 
         		

         		// myLifeGoals
                 {
                    name: 'Life Goals',
                    color: 'transparent',
                    // connectEnds: false,
                    type: 'flags',//'line',
                    shape : 'circlepin',
                    // marker: {                
                    //     symbol:  
                    //     	'url(images/lifeGoalIcons/goal_icon_1.png)'
                    //     	//'url(images/flagOrange.png)'
                    //         // 'url(images/goal_vacation.png)'          Vacation
                    //         //'url(images/goal_icon_eduaction.png)'     Education
                    //         // 'url(images/target_shp_2.png)'          Marriage
                    //         //'url(images/car_goal_icon.png)'       car
                    //         //'url(images/home_goal_icon.png)'      Home
                    //         //'url(images/goal_icon_bike.png)'      Bike
                    //         // 'url(images/flagOrange.png)'     // Other
                    // },
                    // data: [[82, 10]]
                    data : ligoalsJson,
                    tooltip: {
                        headerFormat: '<span style="font-size: 11px;color:#666"><b>Age : </b>{point.x}</span><br>',
                        pointFormat: '<b>Goal : </b>{point.goalDescription}<br></b><b>Frequency : </b>{point.frequencyDesc}<br></b><b>Loan : </b>{point.loanYesNo}<br></b><b>Goal Year : </b>{point.yearofRealization}<br> <b>Future Amount : </b>{point.inflatedAmount}</b>',                
                    },
                    onSeries : 'seriesExpenses',
                    showInLegend:false
                 }
                 ],
            });
         	// console.log(chart);
            chart.xAxis[0].setExtremes(
                    xStart-1,
                    xEnd+1
            );
            
         
        }

        function drawFinancialPlannerTable(){
            fpArray =chart.getDataRows();
            // console.log(fpArray);
            // $('#preview').html('Hello');
            //getTable());
            //getCSV());            
        }
