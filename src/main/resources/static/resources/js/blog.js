//Main blog angularJs module
var blogApp = angular.module("blogApp",  [ 'ngSanitize', 'ui.select' ,'ui.bootstrap' ,'ngCookies'  ]);



//Main blog angularJs module-controller
blogApp.controller("blogAppController", function($timeout ,$scope, $http, $rootScope,$window, $location,$cookies) {
	
	/**
	 * Global variables
	 */
	$scope.username
	$scope.searchError = "" ; 

	$scope.showLoader = false;
	$scope.Tags = '';
	$scope.loggedinUser ;
	$scope.deleteBlogId ;
	$scope.alertMessage ;
	  $scope.alerts = {
		  empty : { type: 'danger', msg: 'Please fill in all the required fields and try submitting again !' },
		  created : { type: 'success', msg: 'Successfully created new entry' },
		  deleted : { type: 'info', msg: 'Successfully deleted entry' },
		  updeted : { type: 'info', msg: 'Successfully updated entry' },
		  fatal : { type: 'danger', msg: 'An error has occured, Please try again later' },
	  		};
	$scope.restUrl = $location.protocol() + '://' + $location.host() + ':'+ $location.port() + "/codeblock";
	var token = $cookies.get('XSRF-TOKEN');
	jQuery('#alerts-messages').hide();
	


	/**
	 * Handler Functions 
	 */	

	$scope.showAlert = function(alert){
		jQuery('#alerts-messages').removeClass('alert-danger');
		jQuery('#alerts-messages').removeClass('alert-success');
		jQuery('#alerts-messages').removeClass('alert-info');
		$scope.alertMessage = alert.msg
		jQuery('#alerts-messages').attr('ng-class' , 'alert-'+alert.type)
		jQuery('#alerts-messages').addClass('alert-'+alert.type)
		setTimeout(() => {
			
			jQuery('#alerts-messages').slideDown('slow', function(){
			    $(this).delay(2000).slideUp('slow', function(){
			    	$scope.hideTheLoader();
			    });
			});
		}, 500);
	}
	
	$scope.closeAlert = function($index){
		jQuery('#alerts-messages').hide();
		$scope.hideTheLoader();
	}
	

	$scope.clean = function() {
		$scope.formMessage == "";
		$scope.topic = "";
		$scope.codeblock = "";
		$scope.selectedLanguage = "";
		$('body').removeClass("modal-open");
	}
	
	$scope.showTheLoader = function(){
		jQuery('#cover-spin').show();
	}
	
	$scope.hideTheLoader = function(){
		jQuery('#cover-spin').hide();
	}
	
	$scope.confDeleteBlogId = function(requestedId){
		$scope.deleteBlogId = requestedId ;
	}
	
	$scope.isValidEntry = function(blog){
		return blog.codeblock && blog.topic ;
	}
	

	/**
	 * HTTP Ajax Functions 
	 */	
	$scope.getUserInfo = function() {
		url = $scope.restUrl + "/getUserInfo/";
		$http.get(url).then(function(response) {
			$scope.loggedinUser = response.data.entity;
			$scope.username  = $scope.loggedinUser.username ;
			$scope.username  = $scope.username.substring(0, $scope.username.indexOf('@'));
			$scope.getAllBlogsByUserId();
			$scope.getAvalibaleLanguages() ;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response.data.message;
			$scope.hideTheLoader();
		});
	};

	$scope.getAllBlogsByUserId = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllBlogsByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllBlogs = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response;
			$scope.hideTheLoader();
		});

	};
	

	$scope.getAllLanguagesByUserId = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllLanguagesByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllUserLanguages = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response;
			$scope.hideTheLoader();
		});

	};

	$scope.getAvalibaleLanguages = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAvalibaleLanguages/";
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response.data.message;
			$scope.hideTheLoader();
		});

	}

	$scope.createBlog = function() {
		$scope.showTheLoader();
		$scope.blog = {
			topic : $scope.topic,
			codeblock : $scope.codeblock,
			language : $scope.printAllAvalibaleLanguages.selected,
		}
		if(!$scope.isValidEntry($scope.blog)){
			$scope.showAlert($scope.alerts.empty);
			return 
		}
		
		$http({
			method : 'POST',
			data : $scope.blog,
			url : $scope.restUrl + "/createBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {

			jQuery('.modal').slideUp(function() {

			});
			$scope.getAllBlogsByUserId();
			$scope.getAllLanguagesByUserId();
			$scope.hideTheLoader();
			$scope.showAlert($scope.alerts.created);
		}).error(function(response, data, status, headers, config) {
			$scope.formMessage = response.message;
			$scope.hideTheLoader();
			$scope.showAlert($scope.alerts.fatal);
		});
	};

	
	
	$scope.updateBlog = function(id, code , topic) {
		$scope.showTheLoader();
		$scope.blog = {
			blogId : id,
			topic : topic,
			codeblock : code,
		}
		
		if(!$scope.isValidEntry($scope.blog)){
			$scope.showAlert($scope.alerts.empty);
			return 
		}
		
		$http({
			method : 'PUT',
			data : $scope.blog,
			url : $scope.restUrl + "/updateBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			jQuery('#myModal').slideUp('slow');
			$("#myModal").modal("hide");
			$scope.getAllBlogsByUserId();
			$scope.hideTheLoader();
			$scope.showAlert($scope.alerts.updeted);
		}).error(function(response, data, status, headers, config) {
			$scope.formMessage = response.message;
			$scope.hideTheLoader();
			$scope.showAlert($scope.alerts.fatal);
		});
	};

	$scope.SearchAllBlogs = function(search) {
		$scope.showTheLoader();
		search = search.toUpperCase();
		url = $scope.restUrl + "/SearchAllBlogs/" + search;
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data.entity;
			$scope.printAllBlogs = $scope.printAllAvalibaleLanguages;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.searchError = response.data.message ;
			$scope.hideTheLoader();
			$scope.showAlert($scope.alerts.fatal);
			
		});
	}

	$scope.deleteBlog = function(id) {
		$scope.showTheLoader();
		$scope.blog = {
				blogId : id,
			}
		$http({
			method : 'DELETE',
			data : $scope.blog,
			url : $scope.restUrl + "/deleteBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			jQuery('#myModal2').slideUp('slow');
			$scope.getAllBlogsByUserId();
			$scope.getAllLanguagesByUserId();
			$scope.showAlert($scope.alerts.deleted);
		}).error(function(response, data, status, headers, config) {
			$scope.hideTheLoader();
			$scope.formMessage = response;
			$scope.showAlert($scope.alerts.fatal);

		});

	};

	$scope.getAllBlogsByLanguage = function(val) {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllBlogsByLanguage/" + val;
		$http.get(url).then(function(response) {
			$scope.printAllBlogsByLanguage = response.data.entity;
			$scope.printAllBlogs = $scope.printAllBlogsByLanguage;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.hideTheLoader();
		});

	}


	$scope.logout = function() {
		$scope.showTheLoader();
		$http({
			method : 'GET',
			data : $scope.blog,
			url : $scope.restUrl + "/logout/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			$scope.formMessage = response
			$scope.hideTheLoader();
		}).error(function(response, data, status, headers, config) {
			$scope.hideTheLoader();
			$scope.formMessage = response.message;

		});

	};
});
