var moduloProductos=angular.module('productos',[]);

moduloProductos.controller('ProductosController',
	function($scope, $rootScope, productosService) {
		$scope.title="Productos";
		
		$scope.data=[];
		
		$scope.refresh=function(){
			productosService.list().then(
				function(resp){
					$scope.data=resp.data;
				},
				function(err){}
			);
		};
	
		$scope.refresh();
	} 
); //End ProductosController


moduloProductos.factory('productosService',
	function($http, URL_API_BASE, URL_BASE) {
		return {
			list: function() {
				return $http.get(URL_API_BASE+"productos");
			}
		}
	}
); //End productosService