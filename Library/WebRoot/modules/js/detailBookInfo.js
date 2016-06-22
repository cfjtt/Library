app.controller("detailBookInfo",function($scope,$http,$state,$stateParams,$sce,$window,$rootScope){
	//获得传送过来的参数
	console.log($stateParams.bookinfo);
	$stateParams.bookinfo.description=$sce.trustAsHtml($stateParams.bookinfo.description);
	
	$scope.item=$stateParams.bookinfo;
	/**
	 * 初始化，加载当前用户的书籍的评价的等级
	 */
	$http({
		url:"/Library/findOneStar.do",
		method:"post",
		params:{"loadId":$window.sessionStorage.getItem("loadId"),"bookId":$scope.item.bookId}
	}).success(function(data){
		
		if(data.flag){
			$scope.myStar=data.result;
		}
	});
	
	/**
	 * 评价鼠标事件
	 */
	$("#myCommit span").mouseenter(function(){
		
		var index=$("#myCommit span").index($(this));
		$(this).prevAll().attr("class","glyphicon glyphicon-star");
		$(this).attr("class","glyphicon glyphicon-star");
		$(this).nextAll().attr("class","glyphicon glyphicon-star-empty");
		
	});
	$("#myCommit span").mouseleave(function() {
		$("#myCommit span").attr("class","glyphicon glyphicon-star-empty");
	});
	$("#myCommit span").click(function() {
		$(this).prevAll().attr("class","glyphicon glyphicon-star");
		$(this).attr("class","glyphicon glyphicon-star");
		$(this).nextAll().attr("class","glyphicon glyphicon-star-empty");
		$("#myCommit span").unbind("mouseenter");
		$("#myCommit span").unbind("mouseleave");
		var star=$("#myCommit span[class='glyphicon glyphicon-star']").size();
		//修改或添加star
		$http({
			method:"post",
			url:"/Library/operateEvaluation.do",
			params:{"loadId":$window.sessionStorage.getItem("loadId"),"bookId":$scope.item.bookId+"","star":star}
		}).success(function(data){
			if(!data.flag){
				$scope.errorMessage=data.message;
			} else {
				if(data.message=="insert"){
					$scope.item.evaluationNum+=1;
					$scope.item.star+=star;
				} 
				
			}
		}).error(function(){
			$scope.errorMessage="提交评价失败，请稍候重试";
		});
	});
	
	
	/**
	 * 收藏按钮
	 */
	$scope.sureSave=function(item) {
		$http({
			method:"post",
			url:"/Library/updateSaveTime.do",
			params:{"bookId":item.bookId,"userId":$window.sessionStorage.getItem("loadId")}
		}).success(function(data){
			if(data.flag){
				$scope.message="收藏成功";
				$("#alert").modal("show");
			} else {
				$scope.message="收藏失败";
				$("#alert").modal("show");
			}
		}).error(function(){
			$scope.message=data.message;
			$("#alert").modal("show");
		});
	}
	
	//初始化编辑器
	var um = UM.getEditor('myEditor');
	  $scope.$on('$destroy', function() {
	        um.destroy();
	  });
	//初始化图表
	  $('#charts').highcharts({
		    chart:{
		    	height:200
		    },
	        title: {
	            text: '',
	            x: -20 //center
	        },
	        credits:{
	        	enabled:false
	        },
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: '次数'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '次'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	            name: '次数',
	            data: []
	        }]
	    });
	  /**
	   * 动态给借书趋势赋值
	   */
	  $http({
		  method:"post",
		  url:"/Library/findBorrowRecordByTime.do",
		  params:{"bookId":$scope.item.bookId}
	  }).success(function(data){
		 console.log($scope.item.bookId);
		  if(data.flag){
			  var times=[];
			  var year=[];
			  for(var time in data.result){
				  year.push(time);
				  times.push(data.result[time]);
			  }
			 
			  console.log($("#charts").highcharts());
			  $("#charts").highcharts().series[0].setData(times);
			 $("#charts").highcharts().xAxis[0].setCategories(year);
		  }
	  });
	  /**
	   * 初始化评论
	   */
	  $scope.findAllCommit=function(){
		  $http({
			  method:"post",
			  url:"/Library/findAllCommit.do",
			  params:{"bookId":$scope.item.bookId}
		  }).success(function(data){
			  if(data.flag){
				  if(data.result.length==0){
					  $scope.isNull="暂无评论";
				  } else {
					  $scope.evalutions=data.result;
					  //$scope.imgPath="img.do?loadId="+data.result.loadId;
				  }
			  }
		  }).error(function(){
			  
		  }); 
		  
	  }
	 $scope.findAllCommit();
	  //预约申请
	  $scope.sureOrder=function(item){
		  if($window.sessionStorage.getItem("loadId")==null){
			  $scope.message="请先登录";
			  $("#alert").modal("show");
			  return;
		  }
		  if($window.sessionStorage.getItem("loadId")!=$scope.loadId){
			  $scope.message="请输入正确的借书证";
			  $("#alert").modal("show");
			  return;
		  }
		  if($scope.bookId!=$scope.item.bookId){
			  $scope.message="请输入正确的图书索引号";
			  $("#alert").modal("show");
			  return;
		  }
		  $http({
			  method:"post",
			  url:"/Library/sureOrder.do",
			  params:{"userId":$window.sessionStorage.getItem("loadId"),"bookId":$scope.bookId}
		  }).success(function(data){
			  if(data.flag){
				  $scope.message="预约成功";
				  $("#alert").modal("show");
			  } else {
				  $scope.message=data.message;
				  $("#alert").modal("show");
			  }
		  }).error(function(){
			  $scope.message="系统繁忙，请稍后重试";
			  $("#alert").modal("show");
		  });
	  };
	  
	  /**
	   * 提价评价
	   */
	  $scope.sureCommit=function(item){
		 var description=um.getContent();
		  $http({
			  method:"post",
			  url:"/Library/updateAllEval.do",
			  params:{"description":description,"userId":$window.sessionStorage.getItem("loadId"),"bookId":$scope.item.bookId}
		  }).success(function(data){
			   if(data.flag){
				  $scope.findAllCommit();
			   }
		  }).error(function(){
			  $scope.message="提交评价失败,请稍候重试";
			  $("#alert").modal("show");
		  });
	  }
})

app.filter('to_trusted',['$sce',function($sce){
	return function(text){
		return $sce.trustAsHtml(text);
	}
}]);