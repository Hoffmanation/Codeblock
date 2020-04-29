var loginApp = angular.module("loginApp", [ 'ui.bootstrap' ,'ngCookies' ])


loginApp.controller("loginAppController",function($scope, $http, $rootScope, $window,$location,$cookies) {

					$scope.restUrl = $location.protocol() + '://'+ $location.host() +':'+  $location.port()+ "/codeblock";
					var token = $cookies.get('XSRF-TOKEN');
					var name= "spacial" ;
					$scope.registrationErrorMessage = [] ;
					
					$scope.login = function() {	
						$scope.userDetails = {
								username : $scope.username,
								password : $scope.password,
								passwordConfirm : $scope.passwordConfirm,
							
							}
						$http({
							method : 'POST',
							data: $scope.userDetails,
							url : $scope.restUrl + "/login",
							headers : {
								'X-CSRF-TOKEN' : token,
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(
								function(response, data, status, headers,config) {
									$scope.loginMessage = response.entity.message ; 
									if ($scope.loginMessage == 'you successfully logged in!') {
										$window.location.href = '/index.html';
									}
								}).error(
								function(response, data, status, headers,
										config) {
								});
					};
									

					$scope.register = function() {
						$scope.userDetails = {
							username : $scope.username,
							password : $scope.password,
							passwordConfirm : $scope.passwordConfirm,
						
						}
						
						$http({
							method : 'POST',
							data : $scope.userDetails,
							url : $scope.restUrl + "/registration",
							headers : {
								'X-CSRF-TOKEN' : token,
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(
								function(response, data, status, headers,
										config) {
									$scope.registrationMessage = response.entity.message;
									  if ($scope.registrationMessage == 'you successfully logged in!') {
										$window.location.href = '/index.html';
									}
									$scope.registrationErrorMessage = response.entity ;
								}).error(
								function(response, data, status, headers,
										config) {
									$scope.registrationErrorMessage ="*Plaese fill out all the empty fields." ;
								});
					};



				});


