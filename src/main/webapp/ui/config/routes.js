angular.module('iw3').config(
		function($routeProvider, $locationProvider, 
		$httpProvider, $logProvider, $localStorageProvider){
			 
			$localStorageProvider.setKeyPrefix('iw3/');
			
			$logProvider.debugEnabled(true);
			
			//$httpProvider.defaults.withCredentials = true;

			$httpProvider.interceptors.push('APIInterceptor');
			
			$locationProvider.hashPrefix('!');
			$routeProvider
				.when('/main', {
					templateUrl : 'ui/views/main.html',
					controller : 'Main'
				})
				.otherwise({
					redirectTo : '/main'
				});
				
		});
