/**
 * 报表管理
 */
app.controller("reportForm", function($scope, $http) {
	$scope.downUserInfo = function(type) {
		window.location.href = "downUserMsg.do?type=" + type;
	}
	
	
	// 初始化第二个页面
	$("#secondCharts").highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			type : 'pie'
		},
		title : {
			text : ''
		},
		credits : {
			enabled : false
		},
		tooltip : {
			pointFormat : '图书数量: <b> {point.y}本</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					style : {
						color : 'black',
						'font-weight' : 'bold'
					},
					format : '{point.name}: {point.percentage:.1f}%'
				},
				showInLegend : true
			}
		},
		series : [{
			colorByPoint : true,
			data : []
		}]
		
	});
	// 显示图书分类图表
	$("#charts").highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false,
			type : 'pie'
		},
		title : {
			text : '馆藏分类比例表'
		},
		credits : {
			enabled : false
		},
		tooltip : {
			pointFormat : '图书数量: <b> {point.y}本</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				point : {
					events : {
						click : function(e) {
							// 发起请求，将drilldown填满数据
							$.ajax({
								type : "post",
								url : "/Library/findBookNumByClassId.do",
								data : {
									"sort" : 1,
									"firstClassId" : e.point.id
								},
								dataType : "json",
								success : function(data) {
									var results = data.result;
									var series = [];
									for ( var result in results) {
										series.push({
											'name' : results[result].ClassName,
											'y' : results[result].BookNum
										});
									}
									
									$("#secondCharts").highcharts().series[0].setData(series);
									$("#secondCharts").highcharts().redraw();
								
								}
							});
						}
					}
				},
				dataLabels : {
					enabled : true,
					style : {
						color : 'black',
						'font-weight' : 'bold'
					},
					format : '{point.name}: {point.percentage:.1f}%'
				},
				showInLegend : true
			}
		},
		series : [{
			colorByPoint : true,
			data : []
		}]
	});

	
	// 初始化查找数据，获得
	$http({
		method : "post",
		url : "/Library/findBookNumByClassId.do",
		params : {
			"sort" : 0
		}
	}).success(function(data) {
		if (data.flag) {
			var results = data.result;
			var values = [];
			var a = [];
			for ( var result in results) {
				values.push({
					'name' : results[result].ClassName,
					'y' : results[result].BookNum,
					"id":results[result].ClassId
				});
			}
			$("#charts").highcharts().series[0].setData(values);
		}
	});

	
	/**
	 * 下载催还统计表
	 */
	$scope.downReturn=function(){
		window.location.href="/Library/downReturn.do?type=0";
	}
	/**
	 * 下载图书遗失报表
	 */
	$scope.downMiss=function(){
		window.location.href="/Library/downReturn.do?type=2";
	}
	/**
	 * 下载赔偿信息报表
	 */
	$scope.downPay=function(){
		window.location.href="/Library/downReturn.do?type=1";
	}
});